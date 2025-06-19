package com.careerplanningsystem.service;

import com.careerplanningsystem.entity.Course;
import com.careerplanningsystem.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseService {
    
    @Autowired
    private CourseRepository courseRepository;
    
    /**
     * 获取所有启用的课程
     */
    public List<Course> getAllActiveCourses() {
        return courseRepository.findByActiveOrderByCreatedAtDesc(true);
    }
    
    /**
     * 根据分类获取课程
     */
    public List<Course> getCoursesByCategory(String category) {
        return courseRepository.findByCategoryAndActiveOrderByRatingDesc(category, true);
    }
    
    /**
     * 根据难度获取课程
     */
    public List<Course> getCoursesByDifficulty(String difficulty) {
        return courseRepository.findByDifficultyAndActiveOrderByRatingDesc(difficulty, true);
    }
    
    /**
     * 获取推荐课程
     */
    public List<Course> getRecommendedCourses() {
        return courseRepository.findRecommendedCourses();
    }
    
    /**
     * 搜索课程
     */
    public List<Course> searchCourses(String keyword) {
        return courseRepository.searchCourses(keyword);
    }
    
    /**
     * 获取热门课程
     */
    public List<Course> getPopularCourses() {
        return courseRepository.findPopularCourses();
    }
    
    /**
     * 获取所有课程分类
     */
    public List<String> getAllCategories() {
        return courseRepository.findDistinctCategories();
    }
    
    /**
     * 根据ID获取课程
     */
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }
    
    /**
     * 保存课程
     */
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }
    
    /**
     * 删除课程
     */
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
    
    /**
     * 增加课程报名人数
     */
    public void incrementEnrollmentCount(Long courseId) {
        Optional<Course> courseOpt = courseRepository.findById(courseId);
        if (courseOpt.isPresent()) {
            Course course = courseOpt.get();
            course.setEnrollmentCount(course.getEnrollmentCount() + 1);
            courseRepository.save(course);
        }
    }
    
    /**
     * 根据推荐职业获取相关课程
     */
    public List<Course> getCoursesByRecommendedCareer(String recommendedCareer) {
        // 根据推荐职业映射到课程分类
        String category = mapCareerToCategory(recommendedCareer);
        return getCoursesByCategory(category);
    }
    
    /**
     * 映射职业到课程分类
     */
    private String mapCareerToCategory(String recommendedCareer) {
        switch (recommendedCareer) {
            case "软件开发工程师":
                return "编程开发";
            case "数据分析师":
                return "数据分析";
            case "UI/UX设计师":
                return "设计创意";
            case "产品经理":
                return "产品管理";
            case "项目管理师":
                return "项目管理";
            case "算法工程师":
                return "算法研究";
            default:
                return "综合技能";
        }
    }
} 