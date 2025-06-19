-- CareerFlow 职业规划系统完整数据库初始化脚本
-- 包含所有功能模块的测试数据

-- 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS career_planning_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE career_planning_system;

-- 清理现有数据（谨慎使用）
-- DELETE FROM assessment_questions WHERE id > 0;
-- DELETE FROM assessments WHERE id > 0;
-- DELETE FROM courses WHERE id > 0;
-- DELETE FROM user_goals WHERE id > 0;

-- 插入测评问题数据
INSERT IGNORE INTO assessment_questions (question_text, category, option_a, option_b, option_c, option_d, order_index, active) VALUES
-- 技术类问题
('您更喜欢哪种工作方式？', '技术', '编写代码解决问题', '分析数据找规律', '设计用户界面', '管理项目进度', 1, true),
('面对复杂问题时，您通常会？', '技术', '拆解成小问题逐步解决', '收集数据进行分析', '画图或原型来理解', '制定计划分配任务', 2, true),
('您最感兴趣的技术领域是？', '技术', '后端开发和系统架构', '数据科学和机器学习', '前端开发和用户体验', 'DevOps和运维管理', 3, true),
('在团队中，您更愿意承担什么角色？', '技术', '核心开发者', '数据分析师', '界面设计师', '技术负责人', 4, true),
('您对新技术的态度是？', '技术', '深入研究底层原理', '关注数据处理能力', '重视用户体验效果', '考虑团队采用成本', 5, true),

-- 创意类问题
('您在创作时更注重什么？', '创意', '功能实现的完整性', '数据支撑的准确性', '视觉效果的美观性', '整体协调的统一性', 6, true),
('看到一个APP时，您首先关注？', '创意', '功能是否强大', '数据是否准确', '界面是否美观', '流程是否合理', 7, true),
('您更喜欢哪种设计风格？', '创意', '简洁实用', '数据可视化', '美观时尚', '系统规范', 8, true),
('在设计项目中，您最关心？', '创意', '技术可行性', '数据合理性', '用户体验', '项目管理', 9, true),
('您认为好的设计应该？', '创意', '功能强大', '逻辑清晰', '美观易用', '规范统一', 10, true),

-- 分析类问题
('面对大量信息时，您会？', '分析', '编程自动处理', '建立分析模型', '制作可视化图表', '制定处理流程', 11, true),
('您更擅长发现什么类型的问题？', '分析', '系统架构问题', '数据异常问题', '用户体验问题', '流程效率问题', 12, true),
('在做决策时，您依赖？', '分析', '技术可行性分析', '数据统计分析', '用户反馈分析', '综合评估分析', 13, true),
('您对数字的敏感度如何？', '分析', '关注性能指标', '敏感数据变化', '关注用户数据', '关注项目数据', 14, true),
('解决问题时，您更倾向于？', '分析', '编写算法解决', '用数据说话', '用户调研验证', '团队讨论决定', 15, true),

-- 沟通类问题
('在会议中，您通常？', '沟通', '提供技术方案', '展示数据分析', '演示设计效果', '协调各方意见', 16, true),
('与客户沟通时，您更关注？', '沟通', '技术实现细节', '数据支撑证据', '用户体验感受', '项目整体进展', 17, true),
('您更喜欢哪种沟通方式？', '沟通', '技术文档交流', '数据报表展示', '原型演示说明', '会议讨论决策', 18, true),
('面对冲突时，您会？', '沟通', '用技术标准判断', '用数据事实说话', '考虑用户感受', '寻求平衡方案', 19, true),
('您认为有效沟通的关键是？', '沟通', '技术准确性', '数据客观性', '表达清晰性', '协调一致性', 20, true),

-- 管理类问题
('在项目管理中，您最重视？', '管理', '技术架构规划', '数据质量控制', '用户需求满足', '进度风险控制', 21, true),
('您更适合管理什么类型的团队？', '管理', '技术开发团队', '数据分析团队', '设计创意团队', '跨职能团队', 22, true),
('制定计划时，您优先考虑？', '管理', '技术难点攻克', '数据收集分析', '用户体验优化', '资源合理分配', 23, true),
('评估项目成功时，您看重？', '管理', '技术指标达成', '数据目标实现', '用户满意度', '整体项目效益', 24, true),
('您的领导风格倾向于？', '管理', '技术专家型', '数据驱动型', '用户导向型', '协调平衡型', 25, true);

-- 插入更多课程数据（补充现有的课程）
INSERT IGNORE INTO courses (title, description, category, difficulty, course_url, duration, rating, enrollment_count, tags, instructor, active) VALUES
-- 编程开发进阶课程
('微服务架构设计', '学习微服务架构设计原理和最佳实践', '编程开发', '高级', 'https://example.com/microservices', 60, 4.8, 450, '微服务,架构设计,Spring Cloud', '张架构师', true),
('Vue.js 全栈开发', '使用Vue.js构建现代化Web应用', '编程开发', '中级', 'https://example.com/vue', 38, 4.6, 820, 'Vue.js,前端,全栈开发', '李前端', true),
('Node.js 后端开发', '使用Node.js构建高性能后端服务', '编程开发', '中级', 'https://example.com/nodejs', 42, 4.7, 680, 'Node.js,JavaScript,后端', '王后端', true),

-- 数据分析进阶课程
('深度学习实战', 'TensorFlow和PyTorch深度学习项目实战', '数据分析', '高级', 'https://example.com/deeplearning', 80, 4.9, 280, '深度学习,TensorFlow,PyTorch', '陈AI专家', true),
('大数据处理技术', 'Spark、Hadoop大数据处理技术栈', '数据分析', '高级', 'https://example.com/bigdata', 70, 4.7, 350, '大数据,Spark,Hadoop', '刘大数据', true),
('商业数据分析', '业务场景下的数据分析方法和工具', '数据分析', '中级', 'https://example.com/business-analysis', 35, 4.5, 560, '商业分析,数据挖掘,BI', '赵分析师', true),

-- 设计创意进阶课程
('品牌视觉设计', '企业品牌视觉识别系统设计', '设计创意', '中级', 'https://example.com/brand-design', 30, 4.6, 380, '品牌设计,VI设计,视觉传达', '孙设计师', true),
('交互设计原理', '用户交互设计原理和实践方法', '设计创意', '中级', 'https://example.com/interaction-design', 32, 4.7, 420, '交互设计,用户体验,原型设计', '周UX师', true),
('移动端UI设计', '移动应用界面设计规范和实践', '设计创意', '初级', 'https://example.com/mobile-ui', 25, 4.4, 690, '移动UI,APP设计,响应式', '吴UI师', true),

-- 产品管理进阶课程
('产品数据分析', '产品经理必备的数据分析技能', '产品管理', '中级', 'https://example.com/product-analysis', 28, 4.5, 480, '产品分析,数据驱动,用户增长', '徐产品', true),
('B端产品设计', '企业级产品设计方法和实践', '产品管理', '中级', 'https://example.com/b2b-product', 35, 4.6, 320, 'B端产品,企业软件,SaaS', '郑产品经理', true),

-- 项目管理课程
('PMP项目管理', 'PMP认证考试培训和项目管理实践', '项目管理', '中级', 'https://example.com/pmp', 50, 4.7, 650, 'PMP,项目管理,认证考试', '何项目经理', true),
('团队协作工具', '现代团队协作工具使用和管理', '项目管理', '初级', 'https://example.com/collaboration', 20, 4.3, 750, '团队协作,工具使用,远程办公', '马协作专家', true),

-- 综合技能课程
('演讲与表达', '提升公众演讲和表达能力', '综合技能', '初级', 'https://example.com/presentation', 18, 4.4, 520, '演讲技巧,表达能力,公众演讲', '冯演讲师', true),
('时间管理', '高效时间管理方法和工具', '综合技能', '初级', 'https://example.com/time-management', 15, 4.2, 680, '时间管理,效率提升,工作方法', '袁效率专家', true),
('创新思维', '培养创新思维和解决问题的能力', '综合技能', '中级', 'https://example.com/innovation', 25, 4.5, 390, '创新思维,问题解决,思维方法', '邓创新导师', true);

-- 插入示例测评结果（为测试用户）
INSERT IGNORE INTO assessments (user_id, recommended_career, personality_type, recommended_path, scores, assessment_date, created_at, updated_at) 
SELECT u.id, '软件开发工程师', '技术型 + 分析型', 
       '1. 学习编程基础 → 2. 掌握开发框架 → 3. 参与项目实践 → 4. 提升系统设计能力',
       '技术:18,创意:12,分析:16,沟通:10,管理:8',
       NOW(), NOW(), NOW()
FROM users u WHERE u.username = 'testuser' AND NOT EXISTS (SELECT 1 FROM assessments a WHERE a.user_id = u.id);

-- 插入示例用户目标（为测试用户）
INSERT IGNORE INTO user_goals (user_id, goal_description, target_date, status, category, priority, progress, notes, created_at, updated_at)
SELECT u.id, '学习Java Spring Boot框架', DATE_ADD(CURDATE(), INTERVAL 2 MONTH), 'ACTIVE', '学习路径', 5, 30, '已完成基础语法学习', NOW(), NOW()
FROM users u WHERE u.username = 'testuser'
AND NOT EXISTS (SELECT 1 FROM user_goals g WHERE g.user_id = u.id AND g.goal_description = '学习Java Spring Boot框架');

INSERT IGNORE INTO user_goals (user_id, goal_description, target_date, status, category, priority, progress, notes, created_at, updated_at)
SELECT u.id, '完成一个完整的Web项目', DATE_ADD(CURDATE(), INTERVAL 4 MONTH), 'ACTIVE', '项目实践', 4, 10, '正在规划项目需求', NOW(), NOW()
FROM users u WHERE u.username = 'testuser'
AND NOT EXISTS (SELECT 1 FROM user_goals g WHERE g.user_id = u.id AND g.goal_description = '完成一个完整的Web项目');

INSERT IGNORE INTO user_goals (user_id, goal_description, target_date, status, category, priority, progress, notes, created_at, updated_at)
SELECT u.id, '获得Java开发认证', DATE_ADD(CURDATE(), INTERVAL 6 MONTH), 'ACTIVE', '证书考试', 3, 0, '准备开始复习', NOW(), NOW()
FROM users u WHERE u.username = 'testuser'
AND NOT EXISTS (SELECT 1 FROM user_goals g WHERE g.user_id = u.id AND g.goal_description = '获得Java开发认证');

-- 验证数据插入
SELECT '=== 数据统计 ===' as info;
SELECT '测评问题数量:' as type, COUNT(*) as count FROM assessment_questions WHERE active = true;
SELECT '课程数量:' as type, COUNT(*) as count FROM courses WHERE active = true;
SELECT '用户数量:' as type, COUNT(*) as count FROM users;
SELECT '测评结果数量:' as type, COUNT(*) as count FROM assessments;
SELECT '用户目标数量:' as type, COUNT(*) as count FROM user_goals;

SELECT '=== 课程分类统计 ===' as info;
SELECT category, COUNT(*) as count FROM courses WHERE active = true GROUP BY category;

SELECT '=== 系统初始化完成 ===' as info; 