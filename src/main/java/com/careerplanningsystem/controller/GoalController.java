package com.careerplanningsystem.controller;

import com.careerplanningsystem.entity.User;
import com.careerplanningsystem.entity.UserGoal;
import com.careerplanningsystem.service.UserGoalService;
import com.careerplanningsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/goals")
public class GoalController {
    
    @Autowired
    private UserGoalService userGoalService;
    
    @Autowired
    private UserService userService;
    
    /**
     * 显示目标管理首页
     */
    @GetMapping
    public String showGoals(Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }
        
        Optional<User> userOpt = userService.findByUsername(authentication.getName());
        if (userOpt.isEmpty()) {
            return "redirect:/login";
        }
        User user = userOpt.get();
        
        // 获取用户的活跃目标
        List<UserGoal> activeGoals = userGoalService.getActiveGoals(user);
        
        // 获取即将到期的目标（7天内）
        List<UserGoal> upcomingGoals = userGoalService.getUpcomingGoals(user, 7);
        
        // 获取高优先级目标
        List<UserGoal> highPriorityGoals = userGoalService.getHighPriorityGoals(user);
        
        // 获取目标统计信息
        UserGoalService.GoalStatistics statistics = userGoalService.getGoalStatistics(user);
        
        model.addAttribute("activeGoals", activeGoals);
        model.addAttribute("upcomingGoals", upcomingGoals);
        model.addAttribute("highPriorityGoals", highPriorityGoals);
        model.addAttribute("statistics", statistics);
        model.addAttribute("user", user);
        
        return "goals/index";
    }
    
    /**
     * 创建新目标
     */
    @PostMapping("/create")
    public String createGoal(@RequestParam String goalDescription,
                           @RequestParam String category,
                           @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate targetDate,
                           @RequestParam(defaultValue = "3") int priority,
                           Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }
        
        Optional<User> userOpt = userService.findByUsername(authentication.getName());
        if (userOpt.isEmpty()) {
            return "redirect:/login";
        }
        User user = userOpt.get();
        
        userGoalService.createGoal(user, goalDescription, category, targetDate, priority);
        
        return "redirect:/goals";
    }
    
    /**
     * 更新目标进度
     */
    @PostMapping("/{id}/progress")
    @ResponseBody
    public String updateProgress(@PathVariable Long id, @RequestParam int progress) {
        UserGoal goal = userGoalService.updateGoalProgress(id, progress);
        if (goal != null) {
            return "进度更新成功";
        } else {
            return "更新失败";
        }
    }
} 