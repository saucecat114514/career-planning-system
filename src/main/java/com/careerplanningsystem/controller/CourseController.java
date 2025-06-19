package com.careerplanningsystem.controller;

import com.careerplanningsystem.entity.Course;
import com.careerplanningsystem.entity.User;
import com.careerplanningsystem.service.CourseService;
import com.careerplanningsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/courses")
public class CourseController {
    
    @Autowired
    private CourseService courseService;
    
    @Autowired
    private UserService userService;
    
    /**
     * 显示课程首页
     */
    @GetMapping
    public String showCourses(Model model, 
                            @RequestParam(required = false) String category,
                            @RequestParam(required = false) String difficulty,
                            @RequestParam(required = false) String search) {
        
        List<Course> courses;
        
        if (search != null && !search.trim().isEmpty()) {
            // 搜索课程
            courses = courseService.searchCourses(search.trim());
            model.addAttribute("searchKeyword", search.trim());
        } else if (category != null && !category.trim().isEmpty()) {
            // 按分类筛选
            courses = courseService.getCoursesByCategory(category);
            model.addAttribute("selectedCategory", category);
        } else if (difficulty != null && !difficulty.trim().isEmpty()) {
            // 按难度筛选
            courses = courseService.getCoursesByDifficulty(difficulty);
            model.addAttribute("selectedDifficulty", difficulty);
        } else {
            // 显示推荐课程
            courses = courseService.getRecommendedCourses();
        }
        
        // 获取所有分类用于筛选
        List<String> categories = courseService.getAllCategories();
        
        model.addAttribute("courses", courses);
        model.addAttribute("categories", categories);
        model.addAttribute("difficulties", List.of("初级", "中级", "高级"));
        
        return "courses/index";
    }
    
    /**
     * 显示课程详情
     */
    @GetMapping("/{id}")
    public String showCourseDetail(@PathVariable Long id, Model model) {
        Optional<Course> courseOpt = courseService.getCourseById(id);
        if (courseOpt.isEmpty()) {
            return "redirect:/courses";
        }
        
        Course course = courseOpt.get();
        model.addAttribute("course", course);
        
        // 获取相关课程推荐
        List<Course> relatedCourses = courseService.getCoursesByCategory(course.getCategory());
        relatedCourses.removeIf(c -> c.getId().equals(id)); // 移除当前课程
        if (relatedCourses.size() > 4) {
            relatedCourses = relatedCourses.subList(0, 4); // 只显示4个相关课程
        }
        model.addAttribute("relatedCourses", relatedCourses);
        
        return "courses/detail";
    }
    
    /**
     * 加入学习
     */
    @PostMapping("/{id}/enroll")
    @ResponseBody
    public String enrollCourse(@PathVariable Long id, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "请先登录";
        }
        
        Optional<User> userOpt = userService.findByUsername(authentication.getName());
        if (userOpt.isEmpty()) {
            return "用户不存在";
        }
        
        Optional<Course> courseOpt = courseService.getCourseById(id);
        if (courseOpt.isEmpty()) {
            return "课程不存在";
        }
        
        // 增加报名人数
        courseService.incrementEnrollmentCount(id);
        
        return "加入成功！开始您的学习之旅吧！";
    }
    
    /**
     * 获取热门课程
     */
    @GetMapping("/popular")
    public String showPopularCourses(Model model) {
        List<Course> popularCourses = courseService.getPopularCourses();
        model.addAttribute("courses", popularCourses);
        model.addAttribute("pageTitle", "热门课程");
        return "courses/list";
    }
    
    /**
     * 根据推荐职业显示相关课程
     */
    @GetMapping("/recommended")
    public String showRecommendedCourses(@RequestParam String career, Model model) {
        List<Course> courses = courseService.getCoursesByRecommendedCareer(career);
        model.addAttribute("courses", courses);
        model.addAttribute("pageTitle", "为您推荐 - " + career);
        model.addAttribute("recommendedCareer", career);
        return "courses/recommended";
    }
} 