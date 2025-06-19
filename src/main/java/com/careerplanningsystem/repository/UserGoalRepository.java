package com.careerplanningsystem.repository;

import com.careerplanningsystem.entity.User;
import com.careerplanningsystem.entity.UserGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserGoalRepository extends JpaRepository<UserGoal, Long> {
    
    /**
     * 查找用户的所有目标
     */
    List<UserGoal> findByUserOrderByCreatedAtDesc(User user);
    
    /**
     * 查找用户的活跃目标
     */
    List<UserGoal> findByUserAndStatusOrderByPriorityDescCreatedAtDesc(User user, UserGoal.GoalStatus status);
    
    /**
     * 根据分类查找用户目标
     */
    List<UserGoal> findByUserAndCategoryOrderByCreatedAtDesc(User user, String category);
    
    /**
     * 查找即将到期的目标
     */
    @Query("SELECT g FROM UserGoal g WHERE g.user = :user AND g.status = 'ACTIVE' AND g.targetDate BETWEEN :startDate AND :endDate ORDER BY g.targetDate ASC")
    List<UserGoal> findUpcomingGoals(@Param("user") User user, 
                                   @Param("startDate") LocalDate startDate, 
                                   @Param("endDate") LocalDate endDate);
    
    /**
     * 统计用户各状态的目标数量
     */
    @Query("SELECT g.status, COUNT(g) FROM UserGoal g WHERE g.user = :user GROUP BY g.status")
    List<Object[]> countGoalsByStatus(@Param("user") User user);
    
    /**
     * 查找用户的高优先级目标
     */
    @Query("SELECT g FROM UserGoal g WHERE g.user = :user AND g.status = 'ACTIVE' AND g.priority >= :minPriority ORDER BY g.priority DESC, g.createdAt DESC")
    List<UserGoal> findHighPriorityGoals(@Param("user") User user, @Param("minPriority") int minPriority);
} 