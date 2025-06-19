package com.careerplanningsystem.controller;

import com.careerplanningsystem.entity.Assessment;
import com.careerplanningsystem.entity.Course;
import com.careerplanningsystem.entity.User;
import com.careerplanningsystem.entity.UserGoal;
import com.careerplanningsystem.service.AssessmentService;
import com.careerplanningsystem.service.CourseService;
import com.careerplanningsystem.service.UserGoalService;
import com.careerplanningsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private AssessmentService assessmentService;
    
    @Autowired
    private CourseService courseService;
    
    @Autowired
    private UserGoalService userGoalService;

    @GetMapping("/")
    public String home(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated() && 
            !authentication.getName().equals("anonymousUser")) {
            return "redirect:/dashboard";
        }
        return "index";
    }

    @GetMapping("/home")
    public String homeAlias(Authentication authentication) {
        return home(authentication, null);
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            Optional<User> userOpt = userService.findByUsername(authentication.getName());
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                model.addAttribute("user", user);
                
                // 获取用户最新的测评结果
                Optional<Assessment> latestAssessment = assessmentService.getLatestAssessment(user);
                model.addAttribute("latestAssessment", latestAssessment.orElse(null));
                
                // 获取推荐课程
                List<Course> recommendedCourses = courseService.getRecommendedCourses();
                if (recommendedCourses.size() > 6) {
                    recommendedCourses = recommendedCourses.subList(0, 6);
                }
                model.addAttribute("recommendedCourses", recommendedCourses);
                
                // 获取用户的活跃目标
                List<UserGoal> activeGoals = userGoalService.getActiveGoals(user);
                if (activeGoals.size() > 5) {
                    activeGoals = activeGoals.subList(0, 5);
                }
                model.addAttribute("activeGoals", activeGoals);
                
                // 获取目标统计信息
                UserGoalService.GoalStatistics statistics = userGoalService.getGoalStatistics(user);
                model.addAttribute("goalStatistics", statistics);
            }
            
            model.addAttribute("username", authentication.getName());
            return "dashboard";
        }
        return "redirect:/login";
    }

    @GetMapping("/career-planning")
    public String careerPlanning() {
        return "career-planning";
    }

    @GetMapping("/skills-development")
    public String skillsDevelopment() {
        return "redirect:/courses";
    }

    @GetMapping("/industry-insights")
    public String industryInsights() {
        return "redirect:/industry";
    }
} 