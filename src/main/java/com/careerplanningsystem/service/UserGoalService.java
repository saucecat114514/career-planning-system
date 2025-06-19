package com.careerplanningsystem.service;

import com.careerplanningsystem.entity.User;
import com.careerplanningsystem.entity.UserGoal;
import com.careerplanningsystem.repository.UserGoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserGoalService {
    
    @Autowired
    private UserGoalRepository userGoalRepository;
    
    /**
     * 获取用户的所有目标
     */
    public List<UserGoal> getUserGoals(User user) {
        return userGoalRepository.findByUserOrderByCreatedAtDesc(user);
    }
    
    /**
     * 获取用户的活跃目标
     */
    public List<UserGoal> getActiveGoals(User user) {
        return userGoalRepository.findByUserAndStatusOrderByPriorityDescCreatedAtDesc(user, UserGoal.GoalStatus.ACTIVE);
    }
    
    /**
     * 根据分类获取用户目标
     */
    public List<UserGoal> getGoalsByCategory(User user, String category) {
        return userGoalRepository.findByUserAndCategoryOrderByCreatedAtDesc(user, category);
    }
    
    /**
     * 获取即将到期的目标
     */
    public List<UserGoal> getUpcomingGoals(User user, int days) {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(days);
        return userGoalRepository.findUpcomingGoals(user, startDate, endDate);
    }
    
    /**
     * 获取高优先级目标
     */
    public List<UserGoal> getHighPriorityGoals(User user) {
        return userGoalRepository.findHighPriorityGoals(user, 4); // 优先级4及以上
    }
    
    /**
     * 创建新目标
     */
    public UserGoal createGoal(User user, String goalDescription, String category, 
                              LocalDate targetDate, int priority) {
        UserGoal goal = new UserGoal(user, goalDescription, category);
        goal.setTargetDate(targetDate);
        goal.setPriority(priority);
        return userGoalRepository.save(goal);
    }
    
    /**
     * 更新目标进度
     */
    public UserGoal updateGoalProgress(Long goalId, int progress) {
        Optional<UserGoal> goalOpt = userGoalRepository.findById(goalId);
        if (goalOpt.isPresent()) {
            UserGoal goal = goalOpt.get();
            goal.setProgress(progress);
            
            // 如果进度达到100%，自动标记为完成
            if (progress >= 100) {
                goal.setStatus(UserGoal.GoalStatus.COMPLETED);
            }
            
            return userGoalRepository.save(goal);
        }
        return null;
    }
    
    /**
     * 更新目标状态
     */
    public UserGoal updateGoalStatus(Long goalId, UserGoal.GoalStatus status) {
        Optional<UserGoal> goalOpt = userGoalRepository.findById(goalId);
        if (goalOpt.isPresent()) {
            UserGoal goal = goalOpt.get();
            goal.setStatus(status);
            return userGoalRepository.save(goal);
        }
        return null;
    }
    
    /**
     * 根据ID获取目标
     */
    public Optional<UserGoal> getGoalById(Long id) {
        return userGoalRepository.findById(id);
    }
    
    /**
     * 保存目标
     */
    public UserGoal saveGoal(UserGoal goal) {
        return userGoalRepository.save(goal);
    }
    
    /**
     * 删除目标
     */
    public void deleteGoal(Long id) {
        userGoalRepository.deleteById(id);
    }
    
    /**
     * 根据推荐路径创建目标
     */
    public List<UserGoal> createGoalsFromPath(User user, String recommendedPath) {
        List<UserGoal> goals = new java.util.ArrayList<>();
        
        // 解析推荐路径，创建对应的目标
        String[] steps = recommendedPath.split("→");
        
        for (int i = 0; i < steps.length; i++) {
            String step = steps[i].trim();
            if (step.startsWith(String.valueOf(i + 1) + ".")) {
                step = step.substring(2).trim(); // 移除序号
            }
            
            UserGoal goal = new UserGoal(user, step, "学习路径");
            goal.setPriority(5 - i); // 优先级递减
            goal.setTargetDate(LocalDate.now().plusMonths(i + 1)); // 每个目标间隔一个月
            
            goals.add(userGoalRepository.save(goal));
        }
        
        return goals;
    }
    
    /**
     * 获取目标统计信息
     */
    public GoalStatistics getGoalStatistics(User user) {
        List<Object[]> statusCounts = userGoalRepository.countGoalsByStatus(user);
        
        GoalStatistics stats = new GoalStatistics();
        for (Object[] row : statusCounts) {
            UserGoal.GoalStatus status = (UserGoal.GoalStatus) row[0];
            Long count = (Long) row[1];
            
            switch (status) {
                case ACTIVE:
                    stats.setActiveCount(count.intValue());
                    break;
                case COMPLETED:
                    stats.setCompletedCount(count.intValue());
                    break;
                case PAUSED:
                    stats.setPausedCount(count.intValue());
                    break;
                case CANCELLED:
                    stats.setCancelledCount(count.intValue());
                    break;
            }
        }
        
        return stats;
    }
    
    /**
     * 目标统计信息类
     */
    public static class GoalStatistics {
        private int activeCount;
        private int completedCount;
        private int pausedCount;
        private int cancelledCount;
        
        // Getters and Setters
        public int getActiveCount() { return activeCount; }
        public void setActiveCount(int activeCount) { this.activeCount = activeCount; }
        
        public int getCompletedCount() { return completedCount; }
        public void setCompletedCount(int completedCount) { this.completedCount = completedCount; }
        
        public int getPausedCount() { return pausedCount; }
        public void setPausedCount(int pausedCount) { this.pausedCount = pausedCount; }
        
        public int getCancelledCount() { return cancelledCount; }
        public void setCancelledCount(int cancelledCount) { this.cancelledCount = cancelledCount; }
        
        public int getTotalCount() {
            return activeCount + completedCount + pausedCount + cancelledCount;
        }
    }
} 