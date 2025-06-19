package com.careerplanningsystem.repository;

import com.careerplanningsystem.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    
    /**
     * 查找所有启用的课程
     */
    List<Course> findByActiveOrderByCreatedAtDesc(boolean active);
    
    /**
     * 根据分类查找课程
     */
    List<Course> findByCategoryAndActiveOrderByRatingDesc(String category, boolean active);
    
    /**
     * 根据难度查找课程
     */
    List<Course> findByDifficultyAndActiveOrderByRatingDesc(String difficulty, boolean active);
    
    /**
     * 查找推荐课程（按评分排序）
     */
    @Query("SELECT c FROM Course c WHERE c.active = true ORDER BY c.rating DESC, c.enrollmentCount DESC")
    List<Course> findRecommendedCourses();
    
    /**
     * 根据关键词搜索课程
     */
    @Query("SELECT c FROM Course c WHERE c.active = true AND " +
           "(LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.tags) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Course> searchCourses(@Param("keyword") String keyword);
    
    /**
     * 查找所有课程分类
     */
    @Query("SELECT DISTINCT c.category FROM Course c WHERE c.active = true")
    List<String> findDistinctCategories();
    
    /**
     * 查找热门课程（报名人数最多）
     */
    @Query("SELECT c FROM Course c WHERE c.active = true ORDER BY c.enrollmentCount DESC")
    List<Course> findPopularCourses();
} 