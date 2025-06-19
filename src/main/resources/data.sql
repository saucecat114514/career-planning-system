-- 插入一些示例学习资源数据
INSERT IGNORE INTO learning_resources (title, description, resource_type, url, provider, duration, difficulty_level, tags, is_free, price, rating, created_at, updated_at) VALUES
('Python 数据分析实战', '从零开始学习Python数据分析，包含numpy、pandas、matplotlib等核心库的使用', 'COURSE', 'https://example.com/python-data-analysis', '慕课网', '40小时', 'BEGINNER', 'Python,数据分析,pandas', true, 0.0, 4.8, NOW(), NOW()),
('Python 数据分析实战', '深入学习Python在数据科学领域的应用，项目驱动式学习', 'COURSE', 'https://example.com/python-advanced', '实验楼', '60小时', 'INTERMEDIATE', 'Python,机器学习,数据挖掘', false, 299.0, 4.7, NOW(), NOW()),
('Python 数据分析实战', 'Python数据分析入门到精通，适合初学者和进阶者', 'COURSE', 'https://example.com/python-complete', '网易云课堂', '50小时', 'BEGINNER', 'Python,数据可视化,统计分析', true, 0.0, 4.9, NOW(), NOW()),
('Python 数据分析实战', 'Python金融数据分析专业课程', 'COURSE', 'https://example.com/python-finance', '清华大学', '30小时', 'ADVANCED', 'Python,金融,量化分析', false, 599.0, 4.6, NOW(), NOW()),
('Python 数据分析实战', 'Python Web开发实战教程', 'COURSE', 'https://example.com/python-web', 'Django官方', '45小时', 'INTERMEDIATE', 'Python,Web开发,Django', true, 0.0, 4.5, NOW(), NOW()),
('Python 数据分析实战', 'Python人工智能应用开发', 'COURSE', 'https://example.com/python-ai', 'AI学院', '80小时', 'EXPERT', 'Python,人工智能,深度学习', false, 999.0, 4.8, NOW(), NOW()); 