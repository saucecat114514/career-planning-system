package com.careerplanningsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/industry")
public class IndustryController {
    
    /**
     * 显示行业洞察首页
     */
    @GetMapping
    public String showIndustryInsights(Model model) {
        // 模拟行业热度数据
        Map<String, Object> industryTrends = createIndustryTrendData();
        model.addAttribute("industryTrends", industryTrends);
        
        // 模拟技能需求词云数据
        List<Map<String, Object>> skillsData = createSkillsWordCloudData();
        model.addAttribute("skillsData", skillsData);
        
        // 热门行业统计
        List<Map<String, Object>> popularIndustries = createPopularIndustriesData();
        model.addAttribute("popularIndustries", popularIndustries);
        
        // 薪资趋势数据
        Map<String, Object> salaryTrends = createSalaryTrendData();
        model.addAttribute("salaryTrends", salaryTrends);
        
        return "industry/insights";
    }
    
    /**
     * 创建行业趋势数据
     */
    private Map<String, Object> createIndustryTrendData() {
        Map<String, Object> data = new HashMap<>();
        
        // 时间轴
        data.put("months", Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"));
        
        // 各行业数据
        Map<String, List<Integer>> industries = new HashMap<>();
        industries.put("互联网", Arrays.asList(85, 88, 92, 95, 98, 100, 102, 105, 108, 110, 112, 115));
        industries.put("人工智能", Arrays.asList(75, 80, 85, 90, 95, 100, 105, 110, 115, 120, 125, 130));
        industries.put("数据分析", Arrays.asList(70, 75, 80, 85, 88, 92, 95, 98, 100, 102, 105, 108));
        industries.put("UI设计", Arrays.asList(60, 65, 70, 72, 75, 78, 80, 82, 85, 87, 90, 92));
        industries.put("产品管理", Arrays.asList(65, 68, 72, 75, 78, 80, 82, 85, 88, 90, 92, 95));
        
        data.put("industries", industries);
        
        return data;
    }
    
    /**
     * 创建技能词云数据
     */
    private List<Map<String, Object>> createSkillsWordCloudData() {
        return Arrays.asList(
            createSkillItem("Python", 100),
            createSkillItem("Java", 95),
            createSkillItem("JavaScript", 90),
            createSkillItem("React", 85),
            createSkillItem("Vue", 80),
            createSkillItem("Spring Boot", 75),
            createSkillItem("MySQL", 70),
            createSkillItem("Docker", 65),
            createSkillItem("Git", 60),
            createSkillItem("Linux", 55),
            createSkillItem("Figma", 50),
            createSkillItem("Photoshop", 45),
            createSkillItem("数据分析", 85),
            createSkillItem("机器学习", 80),
            createSkillItem("产品设计", 75),
            createSkillItem("用户体验", 70),
            createSkillItem("项目管理", 65),
            createSkillItem("敏捷开发", 60),
            createSkillItem("云计算", 55),
            createSkillItem("微服务", 50)
        );
    }
    
    /**
     * 创建技能项
     */
    private Map<String, Object> createSkillItem(String name, int value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
    
    /**
     * 创建热门行业数据
     */
    private List<Map<String, Object>> createPopularIndustriesData() {
        return Arrays.asList(
            createIndustryItem("互联网/电商", 32.5, "持续增长", "up"),
            createIndustryItem("人工智能", 28.3, "快速发展", "up"),
            createIndustryItem("金融科技", 18.7, "稳定增长", "up"),
            createIndustryItem("教育培训", 15.2, "转型升级", "stable"),
            createIndustryItem("医疗健康", 12.8, "新兴领域", "up"),
            createIndustryItem("游戏娱乐", 10.5, "竞争激烈", "stable")
        );
    }
    
    /**
     * 创建行业项
     */
    private Map<String, Object> createIndustryItem(String name, double percentage, String trend, String direction) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("percentage", percentage);
        item.put("trend", trend);
        item.put("direction", direction);
        return item;
    }
    
    /**
     * 创建薪资趋势数据
     */
    private Map<String, Object> createSalaryTrendData() {
        Map<String, Object> data = new HashMap<>();
        
        // 经验等级
        data.put("levels", Arrays.asList("应届生", "1-3年", "3-5年", "5-10年", "10年以上"));
        
        // 各职位薪资数据（单位：千元）
        Map<String, List<Integer>> positions = new HashMap<>();
        positions.put("软件工程师", Arrays.asList(8, 15, 25, 40, 60));
        positions.put("数据分析师", Arrays.asList(10, 18, 28, 45, 65));
        positions.put("UI设计师", Arrays.asList(7, 12, 20, 30, 45));
        positions.put("产品经理", Arrays.asList(12, 20, 35, 50, 80));
        positions.put("算法工程师", Arrays.asList(15, 25, 40, 60, 100));
        
        data.put("positions", positions);
        
        return data;
    }
} 