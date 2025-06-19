-- MySQL数据库初始化脚本
-- 请在MySQL中执行此脚本

-- 创建数据库
CREATE DATABASE IF NOT EXISTS career_planning_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE career_planning_system;

-- 显示确认信息
SELECT 'Career Planning System数据库创建成功！' AS message;

-- 显示数据库信息
SHOW DATABASES LIKE 'career_planning_system'; 