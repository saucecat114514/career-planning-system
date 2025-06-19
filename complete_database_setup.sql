-- ========================================
-- CareerFlow 职业规划系统 - 完整数据库初始化脚本
-- ========================================

-- 1. 创建数据库
DROP DATABASE IF EXISTS career_planning_system;
CREATE DATABASE career_planning_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE career_planning_system;

-- 2. 创建用户表
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码（加密后）',
    email VARCHAR(100) NOT NULL UNIQUE COMMENT '邮箱',
    real_name VARCHAR(100) COMMENT '真实姓名',
    phone VARCHAR(20) COMMENT '手机号码',
    birth_date DATE COMMENT '出生日期',
    gender ENUM('MALE', 'FEMALE', 'OTHER') COMMENT '性别',
    education_level VARCHAR(50) COMMENT '教育水平',
    career_field VARCHAR(100) COMMENT '职业领域',
    experience_level VARCHAR(50) COMMENT '经验水平',
    location VARCHAR(100) COMMENT '所在地',
    bio TEXT COMMENT '个人简介',
    avatar_url VARCHAR(255) COMMENT '头像链接',
    is_active BOOLEAN DEFAULT TRUE COMMENT '是否激活',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    
    INDEX idx_username (username),
    INDEX idx_email (email),
    INDEX idx_career_field (career_field)
) COMMENT '用户表';

-- 3. 创建测评表
CREATE TABLE assessments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    assessment_type VARCHAR(50) NOT NULL COMMENT '测评类型',
    score TEXT COMMENT '测评分数（JSON格式）',
    result TEXT COMMENT '测评结果（JSON格式）',
    recommendations TEXT COMMENT '推荐建议（JSON格式）',
    completed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '完成时间',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_assessment (user_id, assessment_type),
    INDEX idx_assessment_type (assessment_type)
) COMMENT '测评表';

-- 4. 创建测评答案表
CREATE TABLE assessment_answers (
    assessment_id BIGINT NOT NULL COMMENT '测评ID',
    question_key VARCHAR(100) NOT NULL COMMENT '问题键',
    answer_value TEXT COMMENT '答案值',
    
    PRIMARY KEY (assessment_id, question_key),
    FOREIGN KEY (assessment_id) REFERENCES assessments(id) ON DELETE CASCADE
) COMMENT '测评答案表';

-- 5. 创建职业规划表
CREATE TABLE career_plans (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    plan_name VARCHAR(200) NOT NULL COMMENT '规划名称',
    career_goal TEXT COMMENT '职业目标',
    current_position VARCHAR(100) COMMENT '当前职位',
    target_position VARCHAR(100) COMMENT '目标职位',
    industry VARCHAR(100) COMMENT '行业',
    timeline_years INT COMMENT '时间线（年）',
    skills_to_develop TEXT COMMENT '待发展技能（JSON格式）',
    milestones TEXT COMMENT '里程碑（JSON格式）',
    action_plans TEXT COMMENT '行动计划（JSON格式）',
    progress_status ENUM('PLANNING', 'IN_PROGRESS', 'COMPLETED', 'PAUSED') DEFAULT 'PLANNING' COMMENT '进度状态',
    completion_percentage DECIMAL(5,2) DEFAULT 0.00 COMMENT '完成百分比',
    is_active BOOLEAN DEFAULT TRUE COMMENT '是否激活',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_plan (user_id),
    INDEX idx_industry (industry),
    INDEX idx_status (progress_status)
) COMMENT '职业规划表';

-- 6. 创建学习资源表
CREATE TABLE learning_resources (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    career_plan_id BIGINT COMMENT '关联的职业规划ID',
    title VARCHAR(200) NOT NULL COMMENT '资源标题',
    description TEXT COMMENT '资源描述',
    resource_type ENUM('COURSE', 'TUTORIAL', 'BOOK', 'VIDEO', 'ARTICLE', 'PRACTICE', 'CERTIFICATION', 'BOOTCAMP') NOT NULL COMMENT '资源类型',
    url VARCHAR(500) COMMENT '资源链接',
    provider VARCHAR(100) COMMENT '提供者',
    duration VARCHAR(50) COMMENT '学习时长',
    difficulty_level ENUM('BEGINNER', 'INTERMEDIATE', 'ADVANCED', 'EXPERT') COMMENT '难度级别',
    tags VARCHAR(500) COMMENT '标签，逗号分隔',
    is_free BOOLEAN DEFAULT TRUE COMMENT '是否免费',
    price DECIMAL(10,2) COMMENT '价格',
    rating DECIMAL(3,2) COMMENT '评分',
    language VARCHAR(20) DEFAULT 'zh-CN' COMMENT '语言',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    
    FOREIGN KEY (career_plan_id) REFERENCES career_plans(id) ON DELETE SET NULL,
    INDEX idx_resource_type (resource_type),
    INDEX idx_difficulty (difficulty_level),
    INDEX idx_price (is_free, price),
    INDEX idx_rating (rating),
    FULLTEXT idx_search (title, description, tags)
) COMMENT '学习资源表';

-- ========================================
-- 插入示例数据
-- ========================================

-- 插入用户数据（密码已使用BCrypt加密，明文密码都是123456）
INSERT INTO users (username, password, email, real_name, phone, birth_date, gender, education_level, career_field, experience_level, location, bio) VALUES
('admin', '$2a$10$9aKoD8fBHMaF7..XvV8HOeFPqJ6J8gZ2jZqWJ8v.xC3Nj2qY4eKLK', 'admin@careerflow.com', '系统管理员', '13800138000', '1990-01-01', 'MALE', '硕士', 'IT管理', '高级', '北京市', '系统管理员，负责平台维护和用户支持'),
('testuser', '$2a$10$9aKoD8fBHMaF7..XvV8HOeFPqJ6J8gZ2jZqWJ8v.xC3Nj2qY4eKLK', 'test@careerflow.com', '张三', '13900139000', '1995-05-15', 'MALE', '本科', 'IT/软件', '初级', '上海市', '计算机专业应届毕业生，希望在软件开发领域发展'),
('alice', '$2a$10$9aKoD8fBHMaF7..XvV8HOeFPqJ6J8gZ2jZqWJ8v.xC3Nj2qY4eKLK', 'alice@example.com', '李小红', '13700137000', '1993-08-20', 'FEMALE', '硕士', '数据科学', '中级', '深圳市', '数据分析师，专注于机器学习和数据挖掘'),
('bob', '$2a$10$9aKoD8fBHMaF7..XvV8HOeFPqJ6J8gZ2jZqWJ8v.xC3Nj2qY4eKLK', 'bob@example.com', '王强', '13600136000', '1992-12-03', 'MALE', '本科', '产品管理', '中级', '杭州市', '产品经理，负责移动互联网产品设计和运营'),
('carol', '$2a$10$9aKoD8fBHMaF7..XvV8HOeFPqJ6J8gZ2jZqWJ8v.xC3Nj2qY4eKLK', 'carol@example.com', '陈美丽', '13500135000', '1996-03-10', 'FEMALE', '本科', '市场营销', '初级', '广州市', '市场专员，专注于数字营销和品牌推广');

-- 插入测评数据
INSERT INTO assessments (user_id, assessment_type, score, result, recommendations) VALUES
(2, '兴趣测评', '{"技术兴趣": 85, "创新能力": 78, "团队协作": 72, "领导能力": 65}', '{"主导兴趣": "技术研发", "次要兴趣": "产品创新", "职业倾向": "软件工程师"}', '{"建议1": "加强编程技能学习", "建议2": "参与开源项目", "建议3": "关注新技术趋势"}'),
(3, '性格测评', '{"外向性": 45, "开放性": 88, "严谨性": 92, "宜人性": 75, "神经质": 25}', '{"性格类型": "INTJ", "特点": "理性分析型", "适合职业": "数据科学家"}', '{"建议1": "发挥分析优势", "建议2": "提升沟通技巧", "建议3": "培养团队合作"}'),
(4, '技能测评', '{"产品设计": 88, "用户研究": 75, "项目管理": 82, "数据分析": 70}', '{"强项技能": "产品设计", "待提升": "数据分析", "职业匹配": "高级产品经理"}', '{"建议1": "学习高级数据分析", "建议2": "提升用户体验设计", "建议3": "加强商业思维"}');

-- 插入测评答案数据
INSERT INTO assessment_answers (assessment_id, question_key, answer_value) VALUES
(1, 'Q1_喜欢的工作类型', '技术开发'),
(1, 'Q2_工作环境偏好', '团队协作'),
(1, 'Q3_职业发展方向', '技术专家'),
(2, 'Q1_性格倾向', '内向'),
(2, 'Q2_决策方式', '逻辑分析'),
(2, 'Q3_工作风格', '独立思考'),
(3, 'Q1_技能水平_产品设计', '熟练'),
(3, 'Q2_技能水平_项目管理', '良好'),
(3, 'Q3_技能水平_数据分析', '一般');

-- 插入职业规划数据
INSERT INTO career_plans (user_id, plan_name, career_goal, current_position, target_position, industry, timeline_years, skills_to_develop, milestones, action_plans, progress_status, completion_percentage) VALUES
(2, 'Java后端开发工程师成长计划', '成为一名资深的Java后端开发工程师，能够独立负责大型项目的架构设计', '应届毕业生', 'Java高级开发工程师', 'IT/互联网', 3, 
'["Spring Boot", "微服务架构", "Redis", "MySQL优化", "分布式系统", "Docker", "Kubernetes"]',
'[{"年份": 1, "目标": "掌握Spring Boot和基础开发技能"}, {"年份": 2, "目标": "学习微服务和分布式架构"}, {"年份": 3, "目标": "成为团队技术骨干"}]',
'[{"阶段": "第1-6个月", "计划": "学习Java基础和Spring框架"}, {"阶段": "第7-12个月", "计划": "参与实际项目开发"}, {"阶段": "第2年", "计划": "深入学习架构设计"}]',
'IN_PROGRESS', 25.50),

(3, '数据科学家职业发展路径', '成为在机器学习和大数据分析领域的专家，能够为企业提供数据驱动的决策支持', '数据分析师', '高级数据科学家', '数据科学/AI', 4,
'["Python高级编程", "深度学习", "大数据处理", "模型部署", "业务理解", "可视化技能"]',
'[{"年份": 1, "目标": "精通机器学习算法"}, {"年份": 2, "目标": "掌握深度学习框架"}, {"年份": 3, "目标": "具备大数据处理能力"}, {"年份": 4, "目标": "成为领域专家"}]',
'[{"阶段": "当前", "计划": "深入学习TensorFlow和PyTorch"}, {"阶段": "下半年", "计划": "参与AI项目实践"}, {"阶段": "明年", "计划": "发表技术论文"}]',
'IN_PROGRESS', 60.75),

(4, '产品经理晋升计划', '从产品专员晋升为资深产品经理，能够负责产品全生命周期管理', '产品专员', '资深产品经理', '互联网产品', 2,
'["商业分析", "高级数据分析", "用户体验设计", "团队管理", "战略规划", "市场洞察"]',
'[{"年份": 1, "目标": "晋升为产品经理"}, {"年份": 2, "目标": "成为资深产品经理"}]',
'[{"阶段": "Q1-Q2", "计划": "提升数据分析能力"}, {"阶段": "Q3-Q4", "计划": "负责独立产品线"}, {"阶段": "次年", "计划": "带领产品团队"}]',
'PLANNING', 15.00);

-- 插入学习资源数据
INSERT INTO learning_resources (career_plan_id, title, description, resource_type, url, provider, duration, difficulty_level, tags, is_free, price, rating) VALUES
-- Java开发相关资源
(1, 'Spring Boot 从入门到精通', '全面系统地学习Spring Boot框架，包含实战项目案例', 'COURSE', 'https://www.imooc.com/spring-boot', '慕课网', '50小时', 'BEGINNER', 'Spring Boot,Java,后端开发', false, 299.00, 4.8),
(1, 'Java并发编程实战', '深入理解Java并发编程的核心概念和最佳实践', 'BOOK', 'https://book.douban.com/java-concurrency', '机械工业出版社', '2周', 'INTERMEDIATE', 'Java,并发编程,多线程', false, 89.00, 4.9),
(1, 'Docker容器化技术实战', '学习Docker容器技术，为微服务部署打下基础', 'TUTORIAL', 'https://docker.tutorial.com', 'Docker官方', '30小时', 'INTERMEDIATE', 'Docker,容器化,DevOps', true, 0.00, 4.7),
(1, 'Redis深度解析与实战', 'Redis数据库的深入学习，包含缓存策略和性能优化', 'COURSE', 'https://redis.course.cn', '极客时间', '25小时', 'ADVANCED', 'Redis,缓存,数据库', false, 199.00, 4.6),

-- 数据科学相关资源
(2, 'Python数据科学手册', '使用Python进行数据分析的权威指南', 'BOOK', 'https://book.python-datascience.com', 'O\'Reilly', '3周', 'INTERMEDIATE', 'Python,数据科学,NumPy,Pandas', false, 128.00, 4.9),
(2, 'TensorFlow 2.0深度学习实战', '基于TensorFlow 2.0的深度学习项目实战', 'COURSE', 'https://tensorflow.deeplearning.ai', 'DeepLearning.AI', '40小时', 'ADVANCED', 'TensorFlow,深度学习,神经网络', false, 399.00, 4.8),
(2, '机器学习算法与应用', '机器学习核心算法的理论与实践', 'COURSE', 'https://ml.coursera.org', 'Coursera', '60小时', 'INTERMEDIATE', '机器学习,算法,统计学', false, 299.00, 4.7),
(2, 'Kaggle竞赛实战指南', '通过Kaggle竞赛提升数据科学实战能力', 'PRACTICE', 'https://kaggle.com/competitions', 'Kaggle', '持续', 'ADVANCED', 'Kaggle,数据竞赛,实战项目', true, 0.00, 4.5),

-- 产品管理相关资源
(3, '人人都是产品经理', '产品经理入门经典教程，从0到1学习产品思维', 'BOOK', 'https://book.product.cn', '电子工业出版社', '2周', 'BEGINNER', '产品管理,产品思维,用户需求', false, 68.00, 4.6),
(3, '数据驱动的产品决策', '学习如何使用数据分析指导产品决策', 'COURSE', 'https://data-product.edu', '网易云课堂', '35小时', 'INTERMEDIATE', '数据分析,产品决策,用户行为', false, 199.00, 4.7),
(3, 'Axure RP产品原型设计', '掌握专业的产品原型设计工具和方法', 'TUTORIAL', 'https://axure.tutorial.com', 'Axure官方', '20小时', 'BEGINNER', 'Axure,原型设计,产品设计', false, 99.00, 4.4),
(3, '产品经理必备技能树', '产品经理核心技能的系统化学习路径', 'BOOTCAMP', 'https://pm.bootcamp.cn', '产品壹佰', '3个月', 'INTERMEDIATE', '产品管理,技能提升,职业发展', false, 2999.00, 4.8),

-- 通用技能资源
(null, 'Git版本控制系统入门', '学习Git的基本操作和团队协作流程', 'TUTORIAL', 'https://git-scm.com/tutorial', 'Git官方', '10小时', 'BEGINNER', 'Git,版本控制,团队协作', true, 0.00, 4.8),
(null, 'SQL数据库基础教程', '掌握SQL查询语言和数据库设计基础', 'COURSE', 'https://sql.w3schools.com', 'W3Schools', '25小时', 'BEGINNER', 'SQL,数据库,MySQL', true, 0.00, 4.6),
(null, '敏捷开发与Scrum实践', '学习敏捷开发方法论和Scrum框架', 'CERTIFICATION', 'https://scrum.org/certification', 'Scrum.org', '2周', 'INTERMEDIATE', 'Scrum,敏捷开发,项目管理', false, 800.00, 4.7),
(null, '英语技术写作提升', '提升技术文档写作和英语沟通能力', 'COURSE', 'https://english.tech-writing.com', '技术写作学院', '30小时', 'INTERMEDIATE', '英语,技术写作,沟通技巧', false, 299.00, 4.5);

-- ========================================
-- 创建索引优化查询性能
-- ========================================

-- 为常用查询创建复合索引
CREATE INDEX idx_user_career_active ON career_plans(user_id, is_active, progress_status);
CREATE INDEX idx_resource_search ON learning_resources(resource_type, difficulty_level, is_free);
CREATE INDEX idx_assessment_user_type ON assessments(user_id, assessment_type, completed_at);

-- ========================================
-- 显示创建结果
-- ========================================

SELECT '✅ 数据库创建完成！' AS status;
SELECT 'career_planning_system' AS database_name;

-- 显示表格统计
SELECT 
    'users' AS table_name, 
    COUNT(*) AS record_count 
FROM users
UNION ALL
SELECT 
    'assessments' AS table_name, 
    COUNT(*) AS record_count 
FROM assessments
UNION ALL
SELECT 
    'career_plans' AS table_name, 
    COUNT(*) AS record_count 
FROM career_plans
UNION ALL
SELECT 
    'learning_resources' AS table_name, 
    COUNT(*) AS record_count 
FROM learning_resources;

SELECT '🎉 数据库初始化完成，包含示例数据！' AS message; 