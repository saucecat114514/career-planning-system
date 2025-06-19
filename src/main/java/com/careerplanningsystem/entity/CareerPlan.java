package com.careerplanningsystem.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "career_plans")
public class CareerPlan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "plan_name", nullable = false)
    private String planName; // 规划名称
    
    @Column(name = "career_goal", columnDefinition = "TEXT")
    private String careerGoal; // 职业目标
    
    @Column(name = "current_position")
    private String currentPosition; // 当前职位
    
    @Column(name = "target_position")
    private String targetPosition; // 目标职位
    
    @Column(name = "industry")
    private String industry; // 行业
    
    @Column(name = "timeline")
    private String timeline; // 时间规划
    
    @Column(name = "skills_to_develop", columnDefinition = "TEXT")
    private String skillsToDevelop; // 需要发展的技能
    
    @Column(name = "action_steps", columnDefinition = "TEXT")
    private String actionSteps; // 行动步骤
    
    @Column(name = "milestones", columnDefinition = "TEXT")
    private String milestones; // 里程碑
    
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PlanStatus status = PlanStatus.ACTIVE; // 规划状态
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "careerPlan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LearningResource> learningResources;
    
    public enum PlanStatus {
        ACTIVE("进行中"),
        COMPLETED("已完成"),
        PAUSED("暂停"),
        CANCELLED("已取消");
        
        private final String displayName;
        
        PlanStatus(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public CareerPlan() {}
    
    public CareerPlan(User user, String planName) {
        this.user = user;
        this.planName = planName;
        this.status = PlanStatus.ACTIVE;
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
    
    public String getPlanName() {
        return planName;
    }
    
    public void setPlanName(String planName) {
        this.planName = planName;
    }
    
    public String getCareerGoal() {
        return careerGoal;
    }
    
    public void setCareerGoal(String careerGoal) {
        this.careerGoal = careerGoal;
    }
    
    public String getCurrentPosition() {
        return currentPosition;
    }
    
    public void setCurrentPosition(String currentPosition) {
        this.currentPosition = currentPosition;
    }
    
    public String getTargetPosition() {
        return targetPosition;
    }
    
    public void setTargetPosition(String targetPosition) {
        this.targetPosition = targetPosition;
    }
    
    public String getIndustry() {
        return industry;
    }
    
    public void setIndustry(String industry) {
        this.industry = industry;
    }
    
    public String getTimeline() {
        return timeline;
    }
    
    public void setTimeline(String timeline) {
        this.timeline = timeline;
    }
    
    public String getSkillsToDevelop() {
        return skillsToDevelop;
    }
    
    public void setSkillsToDevelop(String skillsToDevelop) {
        this.skillsToDevelop = skillsToDevelop;
    }
    
    public String getActionSteps() {
        return actionSteps;
    }
    
    public void setActionSteps(String actionSteps) {
        this.actionSteps = actionSteps;
    }
    
    public String getMilestones() {
        return milestones;
    }
    
    public void setMilestones(String milestones) {
        this.milestones = milestones;
    }
    
    public PlanStatus getStatus() {
        return status;
    }
    
    public void setStatus(PlanStatus status) {
        this.status = status;
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
    
    public List<LearningResource> getLearningResources() {
        return learningResources;
    }
    
    public void setLearningResources(List<LearningResource> learningResources) {
        this.learningResources = learningResources;
    }
} 