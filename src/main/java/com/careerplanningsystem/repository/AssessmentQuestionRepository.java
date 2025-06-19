package com.careerplanningsystem.repository;

import com.careerplanningsystem.entity.AssessmentQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessmentQuestionRepository extends JpaRepository<AssessmentQuestion, Long> {
    
    /**
     * 查找所有启用的问题，按顺序排列
     */
    List<AssessmentQuestion> findByActiveOrderByOrderIndex(boolean active);
    
    /**
     * 根据分类查找问题
     */
    List<AssessmentQuestion> findByCategoryAndActiveOrderByOrderIndex(String category, boolean active);
    
    /**
     * 查找所有分类
     */
    @Query("SELECT DISTINCT q.category FROM AssessmentQuestion q WHERE q.active = true")
    List<String> findDistinctCategories();
    
    /**
     * 根据分类统计问题数量
     */
    @Query("SELECT COUNT(q) FROM AssessmentQuestion q WHERE q.category = :category AND q.active = true")
    long countByCategoryAndActive(String category);
} 