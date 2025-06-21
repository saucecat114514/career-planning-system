-- ========================================
-- CareerFlow 职业规划系统 - 完整数据库初始化脚本
-- 版本: 2.0
-- 创建日期: 2024-06-20
-- 说明: 包含所有表结构、索引和初始数据
-- ========================================

-- 1. 创建数据库
DROP DATABASE IF EXISTS career_planning_system;
CREATE DATABASE career_planning_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE career_planning_system;

-- ========================================
-- 2. 创建表结构
-- ========================================

-- 用户表
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

-- 测评记录表
CREATE TABLE assessments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    recommended_career VARCHAR(200) COMMENT '推荐职业',
    personality_type VARCHAR(100) COMMENT '性格类型',
    recommended_path TEXT COMMENT '推荐路径',
    scores TEXT COMMENT '测评分数',
    assessment_type VARCHAR(50) NOT NULL DEFAULT 'CAREER_ASSESSMENT' COMMENT '测评类型',
    assessment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '测评日期',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_assessment (user_id, assessment_type),
    INDEX idx_assessment_date (assessment_date)
) COMMENT '测评记录表';

-- 测评问题表
CREATE TABLE assessment_questions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    question_text TEXT NOT NULL COMMENT '问题内容',
    category VARCHAR(50) NOT NULL COMMENT '问题分类',
    optiona VARCHAR(200) NOT NULL COMMENT '选项A',
    optionb VARCHAR(200) NOT NULL COMMENT '选项B',
    optionc VARCHAR(200) NOT NULL COMMENT '选项C',
    optiond VARCHAR(200) NOT NULL COMMENT '选项D',
    order_index INT DEFAULT 0 COMMENT '问题顺序',
    active BOOLEAN DEFAULT TRUE COMMENT '是否启用',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    
    INDEX idx_category (category),
    INDEX idx_order (order_index),
    INDEX idx_active (active)
) COMMENT '测评问题表';

-- 用户目标表
CREATE TABLE user_goals (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    goal_description TEXT NOT NULL COMMENT '目标描述',
    target_date DATE COMMENT '目标日期',
    status ENUM('ACTIVE', 'COMPLETED', 'PAUSED', 'CANCELLED') DEFAULT 'ACTIVE' COMMENT '目标状态',
    category VARCHAR(50) NOT NULL COMMENT '目标分类',
    priority INT DEFAULT 1 COMMENT '优先级',
    progress INT DEFAULT 0 COMMENT '完成进度',
    notes TEXT COMMENT '备注',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_goal (user_id, status),
    INDEX idx_category (category),
    INDEX idx_priority (priority)
) COMMENT '用户目标表';

-- 课程表
CREATE TABLE courses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL COMMENT '课程标题',
    description TEXT COMMENT '课程描述',
    category VARCHAR(50) NOT NULL COMMENT '课程分类',
    difficulty VARCHAR(20) NOT NULL COMMENT '难度级别',
    image_url VARCHAR(500) COMMENT '课程图片',
    course_url VARCHAR(500) COMMENT '课程链接',
    duration INT DEFAULT 0 COMMENT '课程时长（小时）',
    rating DECIMAL(3,2) DEFAULT 0.0 COMMENT '评分',
    enrollment_count INT DEFAULT 0 COMMENT '报名人数',
    active BOOLEAN DEFAULT TRUE COMMENT '是否启用',
    tags VARCHAR(500) COMMENT '标签',
    instructor VARCHAR(100) COMMENT '讲师',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    
    INDEX idx_category (category),
    INDEX idx_difficulty (difficulty),
    INDEX idx_rating (rating),
    INDEX idx_active (active),
    FULLTEXT idx_search (title, description, tags)
) COMMENT '课程表';

-- 职业规划表
CREATE TABLE career_plans (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    plan_name VARCHAR(200) NOT NULL COMMENT '规划名称',
    career_goal TEXT COMMENT '职业目标',
    current_position VARCHAR(100) COMMENT '当前职位',
    target_position VARCHAR(100) COMMENT '目标职位',
    industry VARCHAR(100) COMMENT '行业',
    timeline_years INT COMMENT '时间线（年）',
    skills_to_develop TEXT COMMENT '待发展技能',
    milestones TEXT COMMENT '里程碑',
    action_plans TEXT COMMENT '行动计划',
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

-- 学习资源表
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
    tags VARCHAR(500) COMMENT '标签',
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
-- 3. 插入初始数据
-- ========================================

-- 插入用户数据（密码已使用BCrypt加密，明文密码都是123456）
INSERT INTO users (username, password, email, real_name, phone, birth_date, gender, education_level, career_field, experience_level, location, bio) VALUES
('admin', '$2a$10$9aKoD8fBHMaF7..XvV8HOeFPqJ6J8gZ2jZqWJ8v.xC3Nj2qY4eKLK', 'admin@careerflow.com', '系统管理员', '13800138000', '1990-01-01', 'MALE', '硕士', 'IT管理', '高级', '北京市', '系统管理员，负责平台维护和用户支持'),
('testuser', '$2a$10$9aKoD8fBHMaF7..XvV8HOeFPqJ6J8gZ2jZqWJ8v.xC3Nj2qY4eKLK', 'test@careerflow.com', '张三', '13900139000', '1995-05-15', 'MALE', '本科', 'IT/软件', '初级', '上海市', '计算机专业应届毕业生，希望在软件开发领域发展'),
('alice', '$2a$10$9aKoD8fBHMaF7..XvV8HOeFPqJ6J8gZ2jZqWJ8v.xC3Nj2qY4eKLK', 'alice@example.com', '李小红', '13700137000', '1993-08-20', 'FEMALE', '硕士', '数据科学', '中级', '深圳市', '数据分析师，专注于机器学习和数据挖掘'),
('bob', '$2a$10$9aKoD8fBHMaF7..XvV8HOeFPqJ6J8gZ2jZqWJ8v.xC3Nj2qY4eKLK', 'bob@example.com', '王强', '13600136000', '1992-12-03', 'MALE', '本科', '产品管理', '中级', '杭州市', '产品经理，负责移动互联网产品设计和运营'),
('carol', '$2a$10$9aKoD8fBHMaF7..XvV8HOeFPqJ6J8gZ2jZqWJ8v.xC3Nj2qY4eKLK', 'carol@example.com', '陈美丽', '13500135000', '1996-03-10', 'FEMALE', '本科', '市场营销', '初级', '广州市', '市场专员，专注于数字营销和品牌推广');

-- 插入测评问题数据（36个适合大学生的问题）
INSERT INTO assessment_questions (question_text, category, optiona, optionb, optionc, optiond, order_index) VALUES
-- 技术类问题 (1-6)
('你对编程和软件开发的兴趣程度如何？', '技术', '非常感兴趣，经常自学编程', '比较感兴趣，愿意学习', '一般，觉得还可以', '不太感兴趣，觉得困难', 1),
('遇到技术问题时，你的第一反应是什么？', '技术', '立即查找资料并动手解决', '先思考可能的原因再查资料', '寻求他人帮助', '感到困扰，不知从何入手', 2),
('你更喜欢哪种学习编程的方式？', '技术', '通过项目实战学习', '系统学习理论知识', '观看视频教程', '参加培训班', 3),
('对于新技术和编程语言，你的态度是？', '技术', '积极学习，紧跟技术趋势', '根据需要选择性学习', '等技术成熟后再学习', '倾向于使用熟悉的技术', 4),
('你认为自己在逻辑思维方面的能力如何？', '技术', '很强，能够清晰分析复杂问题', '较强，大部分问题能理清思路', '一般，需要时间思考', '较弱，容易被复杂问题困扰', 5),
('面对代码调试，你的感受是？', '技术', '享受查找和解决Bug的过程', '虽然麻烦但能坚持解决', '感到有些枯燥但能完成', '觉得很困难，容易放弃', 6),

-- 管理类问题 (7-12)
('在小组作业中，你通常扮演什么角色？', '管理', '组织者，分配任务协调进度', '积极参与者，完成分配任务', '建议提供者，给出意见和想法', '默默贡献者，按要求完成工作', 7),
('当团队出现意见分歧时，你会如何处理？', '管理', '主动协调，寻找各方都能接受的方案', '表达自己观点，争取说服他人', '倾听各方意见，保持中立', '避免冲突，接受大多数人的意见', 8),
('你更擅长哪种类型的计划制定？', '管理', '长期战略规划和目标设定', '中期项目计划和里程碑', '短期任务安排和时间管理', '灵活应变，不太喜欢详细计划', 9),
('面对压力和紧急情况，你的表现如何？', '管理', '保持冷静，能够有效应对', '稍有紧张但能快速调整', '需要时间适应和处理', '容易感到焦虑，需要支持', 10),
('你认为自己的领导潜质如何？', '管理', '很强，天生具有领导气质', '较强，在适合的环境下能发挥', '一般，可以培养和提升', '较弱，更适合执行角色', 11),
('在责任分配方面，你的倾向是？', '管理', '主动承担更多责任', '承担适当的责任', '按分配承担相应责任', '倾向于承担较少责任', 12),

-- 创意类问题 (13-18)
('你对视觉设计和美学的敏感度如何？', '创意', '很敏感，能发现细微的设计问题', '比较敏感，对美丑有清晰判断', '一般，能分辨基本的好坏', '不太敏感，主要看功能性', 13),
('面对创作任务时，你的状态是？', '创意', '充满灵感，享受创作过程', '需要一些启发，但能产出作品', '需要参考和模仿才能开始', '感到困难，缺乏创作灵感', 14),
('你更倾向于哪种表达方式？', '创意', '视觉化的图像和设计', '文字和语言表达', '数据和图表展示', '逻辑和结构化描述', 15),
('对于用户体验设计，你的理解是？', '创意', '深刻理解，关注用户感受', '有一定了解，知道基本原则', '了解概念，但缺乏实践', '不太了解，主要关注功能', 16),
('你的创新思维能力如何？', '创意', '很强，经常有新颖的想法', '较强，能在现有基础上创新', '一般，偶尔有一些新想法', '较弱，更倾向于跟随现有模式', 17),
('面对艺术和设计作品，你的关注点是？', '创意', '创意构思和表现手法', '视觉效果和美感', '实用性和功能性', '技术实现和制作工艺', 18),

-- 分析类问题 (19-24)
('你对数据和统计的兴趣程度如何？', '分析', '很感兴趣，喜欢从数据中发现规律', '比较感兴趣，认为数据很有价值', '一般，知道数据的重要性', '不太感兴趣，觉得数据枯燥', 19),
('遇到复杂问题时，你的分析方法是？', '分析', '系统性分解，逐步深入分析', '收集信息，寻找相关性', '参考经验，快速判断', '依靠直觉，凭感觉决定', 20),
('你更擅长处理哪类信息？', '分析', '量化数据和统计信息', '趋势分析和模式识别', '文字信息和内容分析', '图像和视觉信息', 21),
('对于市场调研和用户研究，你的看法是？', '分析', '非常重要，是决策的基础', '比较重要，能提供有价值的洞察', '有一定作用，但不是唯一依据', '作用有限，更相信经验和直觉', 22),
('你的批判性思维能力如何？', '分析', '很强，善于质疑和深入思考', '较强，能发现问题和不一致', '一般，能进行基本的分析', '较弱，更倾向于接受现有观点', 23),
('面对大量信息时，你的处理方式是？', '分析', '系统整理，建立框架分析', '筛选关键信息，抓住重点', '分类归纳，逐步处理', '感到困扰，不知如何入手', 24),

-- 沟通类问题 (25-30)
('在团队讨论中，你的参与度如何？', '沟通', '积极发言，经常提出观点', '适度参与，在合适时机发言', '较少发言，主要倾听他人', '很少发言，倾向于观察', 25),
('当你的观点与他人不同时，你会如何处理？', '沟通', '据理力争，努力说服他人', '平和表达，寻求理解', '适当妥协，求同存异', '保持沉默，避免冲突', 26),
('你在公开演讲或展示方面的能力如何？', '沟通', '很强，享受在众人面前表达', '较强，能够清晰表达观点', '一般，能完成但会紧张', '较弱，感到紧张和不适', 27),
('面对不同类型的人，你的沟通策略是？', '沟通', '灵活调整，因人而异', '保持一致，真诚沟通', '观察学习，模仿他人方式', '保持距离，减少沟通', 28),
('你对建立人际关系的兴趣程度如何？', '沟通', '很感兴趣，喜欢认识新朋友', '比较感兴趣，愿意建立联系', '一般，维持现有关系', '不太感兴趣，喜欢独处', 29),
('在倾听他人方面，你的能力如何？', '沟通', '很强，能够理解他人的想法和感受', '较强，通常能把握谈话要点', '一般，有时会分心或误解', '较弱，更关注自己想说的内容', 30),

-- 研究类问题 (31-36)
('你对学术研究和深入探索的兴趣程度如何？', '研究', '很感兴趣，享受深入研究的过程', '比较感兴趣，愿意投入时间学习', '一般，根据需要进行研究', '不太感兴趣，倾向于实用导向', 31),
('面对未知领域时，你的态度是？', '研究', '充满好奇，迫切想要了解', '保持开放，愿意学习探索', '谨慎观望，了解基本情况', '保持观望，等待他人验证', 32),
('你更喜欢哪种类型的学习内容？', '研究', '理论深度和学术前沿', '实践应用和案例分析', '技能培训和操作指导', '概览介绍和基础知识', 33),
('对于阅读学术论文和专业文献，你的感受是？', '研究', '享受其中，能获得新见解', '认真对待，虽然有时枯燥', '按需阅读，关注实用部分', '觉得困难，倾向于避免', 34),
('你的持续学习能力如何？', '研究', '很强，对新知识始终保持热情', '较强，能够坚持长期学习', '一般，阶段性地进行学习', '较弱，更愿意使用现有知识', 35),
('面对理论与实践的关系，你的观点是？', '研究', '理论指导实践，两者密不可分', '理论重要，但实践更关键', '实践优先，理论作为补充', '重视实践，理论价值有限', 36);

-- 插入课程数据
INSERT INTO courses (title, description, category, difficulty, image_url, course_url, duration, rating, enrollment_count, tags, instructor) VALUES
-- 编程开发类
('Java从零基础到项目实战', 'Java编程语言的系统性学习，从语法基础到企业级项目开发', '编程开发', '初级', '/images/courses/java-basic.jpg', 'https://example.com/java-basic', 80, 4.8, 1256, 'Java,编程基础,面向对象', '李老师'),
('Spring Boot微服务开发', '基于Spring Boot框架的微服务架构设计与实现', '编程开发', '中级', '/images/courses/springboot.jpg', 'https://example.com/springboot', 60, 4.7, 892, 'Spring Boot,微服务,后端开发', '王工程师'),
('前端Vue.js全栈开发', 'Vue.js框架的深入学习，包括组件开发和项目实战', '编程开发', '中级', '/images/courses/vuejs.jpg', 'https://example.com/vuejs', 50, 4.6, 1024, 'Vue.js,前端开发,JavaScript', '张老师'),
('Python数据分析入门', 'Python语言在数据科学领域的应用，包括NumPy、Pandas等', '数据分析', '初级', '/images/courses/python-data.jpg', 'https://example.com/python-data', 45, 4.9, 1567, 'Python,数据分析,机器学习', '数据科学家'),

-- 设计创意类
('UI设计从入门到精通', '用户界面设计的系统学习，包括设计原理和工具使用', '设计创意', '初级', '/images/courses/ui-design.jpg', 'https://example.com/ui-design', 40, 4.5, 756, 'UI设计,Figma,设计原理', '设计师小李'),
('Figma产品设计实战', '使用Figma进行产品原型设计和交互设计', '设计创意', '中级', '/images/courses/figma.jpg', 'https://example.com/figma', 35, 4.7, 634, 'Figma,产品设计,原型设计', '资深设计师'),
('用户体验设计思维', 'UX设计方法论和用户研究技巧', '设计创意', '中级', '/images/courses/ux-design.jpg', 'https://example.com/ux-design', 30, 4.8, 445, 'UX设计,用户研究,交互设计', 'UX专家'),

-- 产品管理类
('产品经理入门指南', '产品管理的基础知识和核心技能培养', '产品管理', '初级', '/images/courses/pm-intro.jpg', 'https://example.com/pm-intro', 25, 4.6, 789, '产品管理,需求分析,项目管理', '产品总监'),
('数据驱动的产品决策', '如何使用数据分析指导产品策略和决策', '产品管理', '中级', '/images/courses/data-pm.jpg', 'https://example.com/data-pm', 30, 4.7, 567, '数据分析,产品策略,用户行为', '高级产品经理'),

-- 通用技能类
('Git版本控制系统', 'Git的使用方法和团队协作开发流程', '通用技能', '初级', '/images/courses/git.jpg', 'https://example.com/git', 15, 4.8, 2134, 'Git,版本控制,团队协作', '技术专家'),
('SQL数据库基础', 'SQL查询语言和关系型数据库的基础知识', '通用技能', '初级', '/images/courses/sql.jpg', 'https://example.com/sql', 20, 4.5, 1789, 'SQL,数据库,MySQL', '数据库专家'),
('敏捷开发与Scrum', '敏捷开发方法论和Scrum框架的实践应用', '项目管理', '中级', '/images/courses/scrum.jpg', 'https://example.com/scrum', 25, 4.6, 456, 'Scrum,敏捷开发,项目管理', 'Scrum Master'),

-- 专业进阶类
('机器学习算法实战', '机器学习核心算法的理论学习和实践应用', '算法研究', '高级', '/images/courses/ml.jpg', 'https://example.com/ml', 70, 4.9, 234, '机器学习,算法,人工智能', 'AI研究员'),
('深度学习与神经网络', 'TensorFlow和PyTorch框架的深度学习实践', '算法研究', '高级', '/images/courses/dl.jpg', 'https://example.com/dl', 80, 4.8, 156, '深度学习,神经网络,TensorFlow', '机器学习专家'),
('区块链技术与应用', '区块链技术原理和实际应用场景分析', '新兴技术', '高级', '/images/courses/blockchain.jpg', 'https://example.com/blockchain', 45, 4.4, 123, '区块链,智能合约,分布式系统', '区块链专家');

-- 插入示例测评结果
INSERT INTO assessments (user_id, recommended_career, personality_type, recommended_path, scores, assessment_type) VALUES
(2, '前端/后端开发工程师', '技术型 + 分析型', '1. 掌握编程语言基础(Java/Python/JavaScript) → 2. 学习开发框架(Spring Boot/React/Vue) → 3. 完成个人项目作品集 → 4. 参与开源项目或实习实践', '技术:22,管理:15,创意:12,分析:18,沟通:14,研究:16', 'CAREER_ASSESSMENT'),
(3, '数据分析师/商业分析师', '研究型 + 分析型', '1. 学习统计学和数学基础 → 2. 掌握分析工具(Python/R/SQL/Excel) → 3. 学习机器学习基础 → 4. 完成数据分析项目案例', '技术:16,管理:12,创意:10,分析:24,沟通:13,研究:21', 'CAREER_ASSESSMENT'),
(4, '产品经理/市场专员', '管理型 + 沟通型', '1. 培养产品思维和商业敏感度 → 2. 学习用户研究和数据分析方法 → 3. 掌握项目管理和沟通技能 → 4. 参与产品实习或校园创业项目', '技术:12,管理:20,创意:16,分析:15,沟通:22,研究:11', 'CAREER_ASSESSMENT');

-- 插入用户目标数据
INSERT INTO user_goals (user_id, goal_description, target_date, status, category, priority, progress, notes) VALUES
(2, '完成Java基础学习并通过认证考试', '2024-09-30', 'ACTIVE', '学习路径', 5, 35, '已完成语法基础部分，正在学习面向对象编程'),
(2, '开发一个个人网站项目', '2024-12-31', 'ACTIVE', '学习路径', 4, 10, '正在规划项目需求和技术选型'),
(2, '参与一个开源项目贡献代码', '2025-03-31', 'ACTIVE', '学习路径', 3, 0, '寻找合适的开源项目'),
(3, '掌握Python数据分析核心库', '2024-08-31', 'COMPLETED', '学习路径', 5, 100, 'NumPy、Pandas、Matplotlib已熟练掌握'),
(3, '完成一个机器学习项目案例', '2024-10-31', 'ACTIVE', '学习路径', 4, 60, '正在进行房价预测项目'),
(4, '提升数据分析能力', '2024-07-31', 'ACTIVE', '学习路径', 5, 70, '已学习Excel高级功能和SQL基础'),
(4, '负责一个独立产品功能', '2024-12-31', 'ACTIVE', '学习路径', 4, 25, '正在参与公司新功能的需求分析');

-- 插入职业规划数据
INSERT INTO career_plans (user_id, plan_name, career_goal, current_position, target_position, industry, timeline_years, skills_to_develop, milestones, action_plans, progress_status, completion_percentage) VALUES
(2, 'Java全栈开发工程师成长计划', '成为一名资深的Java全栈开发工程师，能够独立负责大型项目的前后端开发', '应届毕业生', 'Java高级开发工程师', 'IT/互联网', 3, 
'Spring Boot,Vue.js,微服务架构,Redis,MySQL优化,Docker,Kubernetes', 
'第1年:掌握Java和前端基础;第2年:学习架构设计;第3年:成为技术骨干', 
'Q1-Q2:Java基础和Spring框架;Q3-Q4:前端Vue.js学习;次年:架构和微服务',
'IN_PROGRESS', 28.50),

(3, '数据科学家职业发展路径', '成为在机器学习和大数据分析领域的专家，为企业提供数据驱动的决策支持', '数据分析师', '高级数据科学家', '数据科学/AI', 4,
'Python高级编程,深度学习,大数据处理,模型部署,业务理解,可视化技能',
'第1年:精通机器学习;第2年:掌握深度学习;第3年:大数据处理;第4年:成为专家',
'当前:深入学习TensorFlow和PyTorch;下半年:参与AI项目;明年:发表论文',
'IN_PROGRESS', 65.75);

-- 插入学习资源数据
INSERT INTO learning_resources (career_plan_id, title, description, resource_type, url, provider, duration, difficulty_level, tags, is_free, price, rating) VALUES
(1, 'Java编程思想', 'Java编程的经典教材，深入理解面向对象编程', 'BOOK', 'https://book.java.com', '机械工业出版社', '4周', 'INTERMEDIATE', 'Java,面向对象,编程思想', false, 89.00, 4.9),
(1, 'Vue.js官方教程', 'Vue.js框架的官方学习文档和教程', 'TUTORIAL', 'https://vuejs.org/tutorial', 'Vue.js官方', '30小时', 'BEGINNER', 'Vue.js,前端,JavaScript', true, 0.00, 4.8),
(2, 'Python机器学习实战', '机器学习算法的Python实现和项目案例', 'COURSE', 'https://ml.python.com', '极客时间', '50小时', 'INTERMEDIATE', 'Python,机器学习,scikit-learn', false, 299.00, 4.7),
(2, 'Kaggle数据竞赛', '通过实际竞赛提升数据科学技能', 'PRACTICE', 'https://kaggle.com', 'Kaggle', '持续', 'ADVANCED', 'Kaggle,数据竞赛,实战', true, 0.00, 4.6);

-- ========================================
-- 4. 创建性能优化索引
-- ========================================

-- 复合索引优化查询性能
CREATE INDEX idx_user_career_active ON career_plans(user_id, is_active, progress_status);
CREATE INDEX idx_resource_search ON learning_resources(resource_type, difficulty_level, is_free);
CREATE INDEX idx_assessment_user_type ON assessments(user_id, assessment_type, assessment_date);
CREATE INDEX idx_goal_user_status ON user_goals(user_id, status, priority);
CREATE INDEX idx_course_category_rating ON courses(category, rating DESC, active);

-- ========================================
-- 5. 创建视图简化查询
-- ========================================

-- 用户统计视图
CREATE VIEW user_statistics AS
SELECT 
    u.id as user_id,
    u.username,
    u.real_name,
    COUNT(DISTINCT a.id) as assessment_count,
    COUNT(DISTINCT g.id) as goal_count,
    COUNT(DISTINCT cp.id) as career_plan_count,
    MAX(a.assessment_date) as latest_assessment_date
FROM users u
LEFT JOIN assessments a ON u.id = a.user_id
LEFT JOIN user_goals g ON u.id = g.user_id
LEFT JOIN career_plans cp ON u.id = cp.user_id
WHERE u.is_active = TRUE
GROUP BY u.id, u.username, u.real_name;

-- ========================================
-- 6. 显示创建结果
-- ========================================

SELECT 'CareerFlow 职业规划系统数据库初始化完成！' as status;
SELECT CONCAT('共创建 ', COUNT(*), ' 个数据表') as table_count FROM information_schema.tables WHERE table_schema = 'career_planning_system';
SELECT CONCAT('共插入 ', COUNT(*), ' 个用户') as user_count FROM users;
SELECT CONCAT('共插入 ', COUNT(*), ' 个测评问题') as question_count FROM assessment_questions;
SELECT CONCAT('共插入 ', COUNT(*), ' 个课程') as course_count FROM courses;
SELECT CONCAT('共插入 ', COUNT(*), ' 个学习目标') as goal_count FROM user_goals; 