package com.careerplanningsystem.service;

import com.careerplanningsystem.entity.Assessment;
import com.careerplanningsystem.entity.AssessmentQuestion;
import com.careerplanningsystem.entity.User;
import com.careerplanningsystem.repository.AssessmentQuestionRepository;
import com.careerplanningsystem.repository.AssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        
        // 根据分类映射到具体职业
        Map<String, String> careerMapping = Map.of(
            "技术", "软件开发工程师",
            "管理", "项目管理师",
            "创意", "UI/UX设计师",
            "分析", "数据分析师",
            "沟通", "产品经理",
            "研究", "算法工程师"
        );
        
        return careerMapping.getOrDefault(topCategory, "综合发展专家");
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
        
        // 根据推荐职业生成学习路径
        switch (recommendedCareer) {
            case "软件开发工程师":
                path.append("1. 学习编程基础 → 2. 掌握开发框架 → 3. 参与项目实践 → 4. 提升系统设计能力");
                break;
            case "数据分析师":
                path.append("1. 学习统计基础 → 2. 掌握分析工具 → 3. 数据挖掘实践 → 4. 业务洞察能力");
                break;
            case "UI/UX设计师":
                path.append("1. 设计理论学习 → 2. 工具技能掌握 → 3. 作品集建设 → 4. 用户体验优化");
                break;
            case "产品经理":
                path.append("1. 产品思维培养 → 2. 市场分析能力 → 3. 项目管理技能 → 4. 沟通协调能力");
                break;
            default:
                path.append("1. 基础技能学习 → 2. 专业能力提升 → 3. 实践经验积累 → 4. 综合素质发展");
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
} 