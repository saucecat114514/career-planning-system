package com.careerplanningsystem.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "learning_resources")
public class LearningResource {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "career_plan_id")
    private CareerPlan careerPlan;
    
    @Column(name = "title", nullable = false)
    private String title; // 资源标题
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description; // 资源描述
    
    @Column(name = "resource_type")
    @Enumerated(EnumType.STRING)
    private ResourceType resourceType; // 资源类型
    
    @Column(name = "url")
    private String url; // 资源链接
    
    @Column(name = "provider")
    private String provider; // 提供者
    
    @Column(name = "duration")
    private String duration; // 学习时长
    
    @Column(name = "difficulty_level")
    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficultyLevel; // 难度级别
    
    @Column(name = "tags")
    private String tags; // 标签，逗号分隔
    
    @Column(name = "is_free")
    private Boolean isFree = true; // 是否免费
    
    @Column(name = "price")
    private Double price; // 价格
    
    @Column(name = "rating")
    private Double rating; // 评分
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum ResourceType {
        COURSE("课程"),
        TUTORIAL("教程"),
        BOOK("书籍"),
        VIDEO("视频"),
        ARTICLE("文章"),
        PRACTICE("实践项目"),
        CERTIFICATION("认证"),
        BOOTCAMP("训练营");
        
        private final String displayName;
        
        ResourceType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public enum DifficultyLevel {
        BEGINNER("初级"),
        INTERMEDIATE("中级"),
        ADVANCED("高级"),
        EXPERT("专家级");
        
        private final String displayName;
        
        DifficultyLevel(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public LearningResource() {}
    
    public LearningResource(String title, ResourceType resourceType) {
        this.title = title;
        this.resourceType = resourceType;
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
    
    public CareerPlan getCareerPlan() {
        return careerPlan;
    }
    
    public void setCareerPlan(CareerPlan careerPlan) {
        this.careerPlan = careerPlan;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public ResourceType getResourceType() {
        return resourceType;
    }
    
    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getProvider() {
        return provider;
    }
    
    public void setProvider(String provider) {
        this.provider = provider;
    }
    
    public String getDuration() {
        return duration;
    }
    
    public void setDuration(String duration) {
        this.duration = duration;
    }
    
    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }
    
    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }
    
    public String getTags() {
        return tags;
    }
    
    public void setTags(String tags) {
        this.tags = tags;
    }
    
    public Boolean getIsFree() {
        return isFree;
    }
    
    public void setIsFree(Boolean isFree) {
        this.isFree = isFree;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    public Double getRating() {
        return rating;
    }
    
    public void setRating(Double rating) {
        this.rating = rating;
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