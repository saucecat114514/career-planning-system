package com.careerplanningsystem.service;

import com.careerplanningsystem.entity.Assessment;
import com.careerplanningsystem.entity.AssessmentQuestion;
import com.careerplanningsystem.entity.User;
import com.careerplanningsystem.repository.AssessmentQuestionRepository;
import com.careerplanningsystem.repository.AssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class AssessmentService {
    
    @Autowired
    private AssessmentQuestionRepository questionRepository;
    
    @Autowired
    private AssessmentRepository assessmentRepository;
    
    /**
     * 获取所有测评问题
     */
    public List<AssessmentQuestion> getAllQuestions() {
        return questionRepository.findByActiveOrderByOrderIndex(true);
    }
    
    /**
     * 根据分类获取问题
     */
    public List<AssessmentQuestion> getQuestionsByCategory(String category) {
        return questionRepository.findByCategoryAndActiveOrderByOrderIndex(category, true);
    }
    
    /**
     * 获取所有问题分类
     */
    public List<String> getAllCategories() {
        return questionRepository.findDistinctCategories();
    }
    
    /**
     * 处理测评答案并生成结果
     */
    public Assessment processAssessment(User user, Map<Long, String> answers) {
        // 分析答案，生成测评结果
        Map<String, Integer> categoryScores = calculateCategoryScores(answers);
        
        // 确定推荐的职业领域
        String recommendedCareer = determineRecommendedCareer(categoryScores);
        
        // 确定性格类型
        String personalityType = determinePersonalityType(categoryScores);
        
        // 生成推荐路径
        String recommendedPath = generateRecommendedPath(recommendedCareer, personalityType);
        
        // 创建测评记录
        Assessment assessment = new Assessment();
        assessment.setUser(user);
        assessment.setRecommendedCareer(recommendedCareer);
        assessment.setPersonalityType(personalityType);
        assessment.setRecommendedPath(recommendedPath);
        assessment.setScores(formatScores(categoryScores));
        assessment.setAssessmentType("CAREER_ASSESSMENT");
        assessment.setAssessmentDate(LocalDateTime.now());
        
        return assessmentRepository.save(assessment);
    }
    
    /**
     * 计算各分类的得分
     */
    private Map<String, Integer> calculateCategoryScores(Map<Long, String> answers) {
        Map<String, Integer> scores = new HashMap<>();
        
        for (Map.Entry<Long, String> entry : answers.entrySet()) {
            Long questionId = entry.getKey();
            String answer = entry.getValue();
            
            Optional<AssessmentQuestion> questionOpt = questionRepository.findById(questionId);
            if (questionOpt.isPresent()) {
                AssessmentQuestion question = questionOpt.get();
                String category = question.getCategory();
                
                // 根据答案计算分数（A=4, B=3, C=2, D=1）
                int score = getScoreForAnswer(answer);
                scores.put(category, scores.getOrDefault(category, 0) + score);
            }
        }
        
        return scores;
    }
    
    /**
     * 根据答案获取分数
     */
    private int getScoreForAnswer(String answer) {
        switch (answer.toUpperCase()) {
            case "A": return 4;
            case "B": return 3;
            case "C": return 2;
            case "D": return 1;
            default: return 0;
        }
    }
    
    /**
     * 确定推荐的职业领域
     */
    private String determineRecommendedCareer(Map<String, Integer> categoryScores) {
        // 找到得分最高的分类
        String topCategory = categoryScores.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("综合发展");
        
        // 根据分类映射到适合大学生的具体职业
        Map<String, String> careerMapping = Map.of(
            "技术", "前端/后端开发工程师",
            "管理", "产品运营/项目助理",
            "创意", "UI/UX设计师",
            "分析", "数据分析师/商业分析师",
            "沟通", "产品经理/市场专员",
            "研究", "算法工程师/研发工程师"
        );
        
        return careerMapping.getOrDefault(topCategory, "全栈发展专家");
    }
    
    /**
     * 确定性格类型
     */
    private String determinePersonalityType(Map<String, Integer> categoryScores) {
        List<String> topCategories = categoryScores.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(2)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        
        if (topCategories.size() >= 2) {
            return topCategories.get(0) + "型 + " + topCategories.get(1) + "型";
        } else if (topCategories.size() == 1) {
            return topCategories.get(0) + "型";
        } else {
            return "平衡型";
        }
    }
    
    /**
     * 生成推荐路径
     */
    private String generateRecommendedPath(String recommendedCareer, String personalityType) {
        StringBuilder path = new StringBuilder();
        
        // 根据推荐职业生成适合大学生的学习路径
        if (recommendedCareer.contains("开发工程师")) {
            path.append("1. 掌握编程语言基础(Java/Python/JavaScript) → 2. 学习开发框架(Spring Boot/React/Vue) → 3. 完成个人项目作品集 → 4. 参与开源项目或实习实践");
        } else if (recommendedCareer.contains("数据分析师")) {
            path.append("1. 学习统计学和数学基础 → 2. 掌握分析工具(Python/R/SQL/Excel) → 3. 学习机器学习基础 → 4. 完成数据分析项目案例");
        } else if (recommendedCareer.contains("UI/UX设计师")) {
            path.append("1. 学习设计基础理论和美学原理 → 2. 熟练使用设计工具(Figma/Sketch/Adobe) → 3. 建设个人设计作品集 → 4. 关注用户体验趋势和最佳实践");
        } else if (recommendedCareer.contains("产品经理") || recommendedCareer.contains("产品运营")) {
            path.append("1. 培养产品思维和商业敏感度 → 2. 学习用户研究和数据分析方法 → 3. 掌握项目管理和沟通技能 → 4. 参与产品实习或校园创业项目");
        } else if (recommendedCareer.contains("算法工程师") || recommendedCareer.contains("研发工程师")) {
            path.append("1. 深入学习数据结构和算法 → 2. 掌握机器学习和深度学习 → 3. 参与科研项目或竞赛 → 4. 关注前沿技术发展趋势");
        } else if (recommendedCareer.contains("市场专员")) {
            path.append("1. 学习市场营销基础理论 → 2. 掌握数字营销工具和方法 → 3. 参与市场调研和活动策划 → 4. 培养创意思维和沟通能力");
        } else {
            path.append("1. 明确个人兴趣和职业方向 → 2. 系统学习相关专业技能 → 3. 积极参与实习和项目实践 → 4. 建立个人品牌和职业网络");
        }
        
        return path.toString();
    }
    
    /**
     * 格式化分数
     */
    private String formatScores(Map<String, Integer> categoryScores) {
        return categoryScores.entrySet()
                .stream()
                .map(entry -> entry.getKey() + ":" + entry.getValue())
                .collect(Collectors.joining(","));
    }
    
    /**
     * 获取用户的测评历史
     */
    public List<Assessment> getUserAssessments(User user) {
        return assessmentRepository.findByUserOrderByAssessmentDateDesc(user);
    }
    
    /**
     * 获取用户最新的测评结果
     */
    public Optional<Assessment> getLatestAssessment(User user) {
        List<Assessment> assessments = getUserAssessments(user);
        return assessments.isEmpty() ? Optional.empty() : Optional.of(assessments.get(0));
    }
    
    /**
     * 根据ID获取测评结果
     */
    public Optional<Assessment> getAssessmentById(Long id) {
        return assessmentRepository.findById(id);
    }
    
    /**
     * 获取用户的测评次数
     */
    public int getAssessmentCountByUser(User user) {
        return assessmentRepository.findByUserOrderByAssessmentDateDesc(user).size();
    }
} 