package com.careerplanningsystem.controller;

import com.careerplanningsystem.entity.Assessment;
import com.careerplanningsystem.entity.AssessmentQuestion;
import com.careerplanningsystem.entity.User;
import com.careerplanningsystem.service.AssessmentService;
import com.careerplanningsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/assessment")
public class AssessmentController {
    
    @Autowired
    private AssessmentService assessmentService;
    
    @Autowired
    private UserService userService;
    
    /**
     * 显示测评首页
     */
    @GetMapping
    public String showAssessment(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Optional<User> userOpt = userService.findByUsername(authentication.getName());
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                // 获取用户最新的测评结果
                Optional<Assessment> latestAssessment = assessmentService.getLatestAssessment(user);
                model.addAttribute("latestAssessment", latestAssessment.orElse(null));
                model.addAttribute("hasAssessment", latestAssessment.isPresent());
            }
        }
        
        // 获取测评问题分类
        List<String> categories = assessmentService.getAllCategories();
        model.addAttribute("categories", categories);
        
        return "assessment/index";
    }
    
    /**
     * 开始测评
     */
    @GetMapping("/start")
    public String startAssessment(Model model) {
        List<AssessmentQuestion> questions = assessmentService.getAllQuestions();
        model.addAttribute("questions", questions);
        return "assessment/questions";
    }
    
    /**
     * 提交测评答案
     */
    @PostMapping("/submit")
    public String submitAssessment(@RequestParam Map<String, String> answers, 
                                 Authentication authentication,
                                 Model model) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }
        
        Optional<User> userOpt = userService.findByUsername(authentication.getName());
        if (userOpt.isEmpty()) {
            return "redirect:/login";
        }
        User user = userOpt.get();
        
        // 转换答案格式
        Map<Long, String> processedAnswers = new HashMap<>();
        for (Map.Entry<String, String> entry : answers.entrySet()) {
            if (entry.getKey().startsWith("question_")) {
                Long questionId = Long.parseLong(entry.getKey().substring(9));
                processedAnswers.put(questionId, entry.getValue());
            }
        }
        
        // 处理测评结果
        Assessment assessment = assessmentService.processAssessment(user, processedAnswers);
        
        return "redirect:/assessment/result/" + assessment.getId();
    }
    
    /**
     * 显示测评结果
     */
    @GetMapping("/result/{id}")
    public String showResult(@PathVariable Long id, Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }
        
        Optional<User> userOpt = userService.findByUsername(authentication.getName());
        if (userOpt.isEmpty()) {
            return "redirect:/login";
        }
        User user = userOpt.get();
        
        // 获取测评结果
        Optional<Assessment> assessmentOpt = assessmentService.getAssessmentById(id);
        if (assessmentOpt.isEmpty()) {
            return "redirect:/assessment";
        }
        
        Assessment assessment = assessmentOpt.get();
        
        // 验证是否是用户自己的测评结果
        if (!assessment.getUser().getId().equals(user.getId())) {
            return "redirect:/assessment";
        }
        
        model.addAttribute("assessment", assessment);
        model.addAttribute("user", user);
        
        return "assessment/result";
    }
    
    /**
     * 显示测评历史
     */
    @GetMapping("/history")
    public String showHistory(Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }
        
        Optional<User> userOpt = userService.findByUsername(authentication.getName());
        if (userOpt.isEmpty()) {
            return "redirect:/login";
        }
        User user = userOpt.get();
        
        List<Assessment> assessments = assessmentService.getUserAssessments(user);
        model.addAttribute("assessments", assessments);
        model.addAttribute("user", user);
        
        return "assessment/history";
    }
    
    /**
     * 重新开始测评
     */
    @GetMapping("/restart")
    public String restartAssessment() {
        return "redirect:/assessment/start";
    }
} 