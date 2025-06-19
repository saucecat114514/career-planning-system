package com.careerplanningsystem.config;

import com.careerplanningsystem.entity.*;
import com.careerplanningsystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final CourseService courseService;

    @Autowired
    public DataInitializer(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @Override
    public void run(String... args) throws Exception {
        // 检查是否已经存在管理员用户
        if (!userService.existsByUsername("admin")) {
            // 创建默认管理员用户
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("123456");
            admin.setEmail("admin@careerflow.com");
            admin.setRealName("管理员");
            
            userService.registerUser(admin);
            System.out.println("已创建默认管理员用户: admin/123456");
        }

        // 创建一些测试用户
        if (!userService.existsByUsername("testuser")) {
            User testUser = new User();
            testUser.setUsername("testuser");
            testUser.setPassword("123456");
            testUser.setEmail("test@careerflow.com");
            testUser.setRealName("测试用户");
            testUser.setCareerField("IT/软件");
            testUser.setExperienceLevel("初级");
            testUser.setEducationLevel("本科");
            
            userService.registerUser(testUser);
            System.out.println("已创建测试用户: testuser/123456");
        }
        
        // 初始化课程数据
        initializeCourses();
    }
    
    private void initializeCourses() {
        // 检查是否已有课程数据
        if (courseService.getAllActiveCourses().isEmpty()) {
            System.out.println("正在初始化课程数据...");
            
            // 编程开发类课程
            createCourse("Java Spring Boot 实战", "全面学习Spring Boot框架，掌握企业级Java开发", 
                        "编程开发", "中级", "https://example.com/java-course", 40, 4.8, 1250, "Java,Spring Boot,微服务", "张老师");
            
            createCourse("Python 数据分析入门", "从零开始学习Python数据分析，掌握pandas、numpy等工具", 
                        "数据分析", "初级", "https://example.com/python-course", 30, 4.6, 890, "Python,数据分析,pandas", "李老师");
            
            createCourse("React 前端开发", "现代前端框架React从入门到精通", 
                        "编程开发", "中级", "https://example.com/react-course", 35, 4.7, 1100, "React,JavaScript,前端", "王老师");
            
            // 设计创意类课程
            createCourse("UI/UX 设计基础", "用户界面和用户体验设计完整教程", 
                        "设计创意", "初级", "https://example.com/ui-course", 25, 4.5, 650, "UI设计,UX设计,Figma", "赵老师");
            
            createCourse("Figma 设计工具精通", "专业设计工具Figma的全面使用指南", 
                        "设计创意", "中级", "https://example.com/figma-course", 20, 4.4, 420, "Figma,原型设计,协作", "陈老师");
            
            // 产品管理类课程
            createCourse("产品经理入门", "产品思维培养和产品管理方法论", 
                        "产品管理", "初级", "https://example.com/pm-course", 28, 4.6, 780, "产品管理,需求分析,用户研究", "刘老师");
            
            createCourse("敏捷项目管理", "Scrum和敏捷开发项目管理实践", 
                        "项目管理", "中级", "https://example.com/agile-course", 22, 4.5, 560, "敏捷开发,Scrum,项目管理", "周老师");
            
            // 算法研究类课程
            createCourse("机器学习算法实战", "深入理解机器学习算法原理和应用", 
                        "算法研究", "高级", "https://example.com/ml-course", 50, 4.9, 320, "机器学习,算法,Python", "吴老师");
            
            createCourse("数据结构与算法", "计算机基础：数据结构和算法设计", 
                        "算法研究", "中级", "https://example.com/algorithm-course", 45, 4.7, 980, "数据结构,算法,编程基础", "徐老师");
            
            // 综合技能类课程
            createCourse("职场沟通技巧", "提升职场沟通和协作能力", 
                        "综合技能", "初级", "https://example.com/communication-course", 15, 4.3, 430, "沟通技巧,职场技能,团队协作", "孙老师");
            
            System.out.println("课程数据初始化完成");
        }
    }
    
    private void createCourse(String title, String description, String category, String difficulty, 
                            String courseUrl, int duration, double rating, int enrollmentCount, 
                            String tags, String instructor) {
        Course course = new Course(title, description, category, difficulty);
        course.setCourseUrl(courseUrl);
        course.setDuration(duration);
        course.setRating(rating);
        course.setEnrollmentCount(enrollmentCount);
        course.setTags(tags);
        course.setInstructor(instructor);
        courseService.saveCourse(course);
    }
} 