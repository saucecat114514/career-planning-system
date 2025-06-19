package com.careerplanningsystem.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "assessments")
public class Assessment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "recommended_career")
    private String recommendedCareer; // 推荐职业领域
    
    @Column(name = "personality_type")
    private String personalityType; // 性格类型
    
    @Column(name = "recommended_path", columnDefinition = "TEXT")
    private String recommendedPath; // 推荐学习路径
    
    @Column(name = "scores", columnDefinition = "TEXT")
    private String scores; // 各分类得分
    
    @Column(name = "assessment_date")
    private LocalDateTime assessmentDate; // 测评日期
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public Assessment() {}
    
    public Assessment(User user) {
        this.user = user;
        this.assessmentDate = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public String getRecommendedCareer() {
        return recommendedCareer;
    }
    
    public void setRecommendedCareer(String recommendedCareer) {
        this.recommendedCareer = recommendedCareer;
    }
    
    public String getPersonalityType() {
        return personalityType;
    }
    
    public void setPersonalityType(String personalityType) {
        this.personalityType = personalityType;
    }
    
    public String getRecommendedPath() {
        return recommendedPath;
    }
    
    public void setRecommendedPath(String recommendedPath) {
        this.recommendedPath = recommendedPath;
    }
    
    public String getScores() {
        return scores;
    }
    
    public void setScores(String scores) {
        this.scores = scores;
    }
    
    public LocalDateTime getAssessmentDate() {
        return assessmentDate;
    }
    
    public void setAssessmentDate(LocalDateTime assessmentDate) {
        this.assessmentDate = assessmentDate;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
} 