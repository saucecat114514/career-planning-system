package com.careerplanningsystem.repository;

import com.careerplanningsystem.entity.Assessment;
import com.careerplanningsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
    
    /**
     * 查找用户的所有测评结果，按日期降序
     */
    List<Assessment> findByUserOrderByAssessmentDateDesc(User user);
    
    /**
     * 查找用户最新的测评结果
     */
    @Query("SELECT a FROM Assessment a WHERE a.user = :user ORDER BY a.assessmentDate DESC")
    List<Assessment> findLatestAssessmentByUser(@Param("user") User user);
    
    /**
     * 根据推荐职业查找测评结果
     */
    List<Assessment> findByRecommendedCareerOrderByAssessmentDateDesc(String recommendedCareer);
    
    /**
     * 查找指定时间范围内的测评结果
     */
    @Query("SELECT a FROM Assessment a WHERE a.assessmentDate BETWEEN :startDate AND :endDate ORDER BY a.assessmentDate DESC")
    List<Assessment> findByAssessmentDateBetween(@Param("startDate") LocalDateTime startDate, 
                                               @Param("endDate") LocalDateTime endDate);
    
    /**
     * 统计用户的测评次数
     */
    @Query("SELECT COUNT(a) FROM Assessment a WHERE a.user = :user")
    long countByUser(@Param("user") User user);
    
    /**
     * 统计各职业推荐的数量
     */
    @Query("SELECT a.recommendedCareer, COUNT(a) FROM Assessment a GROUP BY a.recommendedCareer ORDER BY COUNT(a) DESC")
    List<Object[]> countByRecommendedCareer();
} 