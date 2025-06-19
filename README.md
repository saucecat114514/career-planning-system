# CareerFlow - 职业规划系统

连接校园与职场的智能成长平台，帮助学生和职场新人进行职业规划、技能提升和行业洞察。

## 功能特色

- 🎯 **职业规划助手** - 从自我测评到具体路线规划，智能生成成长路线图
- 📈 **技能提升** - 精准课程推荐，实训项目推荐
- 📊 **行业洞察** - 用数据洞察行业热度和新兴趋势  
- 🎨 **现代化UI** - 使用液体玻璃(Liquid Glass)效果的现代化界面设计

## 技术栈

- **后端**: Spring Boot 3.4.6, Spring Security, Spring Data JPA
- **数据库**: MySQL 8.0+
- **前端**: Thymeleaf, HTML5, CSS3, JavaScript
- **UI设计**: Liquid Glass效果，响应式设计
- **构建工具**: Maven

## 环境要求

- Java 17+
- MySQL 8.0+
- Maven 3.6+

## 快速开始

### 1. 克隆项目
```bash
git clone <your-repo-url>
cd Career-Planning-System
```

### 2. 数据库配置

在MySQL中创建数据库：
```sql
CREATE DATABASE IF NOT EXISTS career_planning_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. 配置数据库连接

编辑 `src/main/resources/application.properties` 文件：
```properties
# 修改数据库连接信息
spring.datasource.url=jdbc:mysql://localhost:3306/career_planning_system?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 4. 运行项目

```bash
# 使用Maven运行
mvn spring-boot:run

# 或者使用包装器
./mvnw spring-boot:run  # Linux/Mac
mvnw.cmd spring-boot:run  # Windows
```

### 5. 访问应用

打开浏览器访问：http://localhost:8080

## 默认账户

系统会自动创建以下测试账户：

- **管理员**: `admin` / `123456`
- **测试用户**: `testuser` / `123456`

## 项目结构

```
src/
├── main/
│   ├── java/
│   │   └── com/careerplanningsystem/
│   │       ├── config/          # 配置类
│   │       ├── controller/      # 控制器
│   │       ├── entity/          # 实体类
│   │       ├── repository/      # 数据访问层
│   │       └── service/         # 服务层
│   └── resources/
│       ├── templates/           # Thymeleaf模板
│       ├── static/             # 静态资源
│       └── application.properties
```

## 页面展示

### 主页
- 使用液体玻璃效果的现代化设计
- 响应式布局，支持移动端
- 动态背景装饰和交互效果

### 职业规划
- 三步骤指引：测评 → 路径 → 目标
- 交互式界面设计
- 智能分析推荐

### 用户系统
- 安全的用户认证
- 个人信息管理
- 学习进度跟踪

## 开发说明

### 添加新功能

1. 在相应的包中创建实体类、仓库、服务和控制器
2. 创建对应的Thymeleaf模板
3. 更新导航菜单和路由配置

### 样式定制

项目使用内联CSS和液体玻璃效果，主要特色：
- `backdrop-filter: blur()` 实现毛玻璃效果
- 渐变背景和动态装饰
- 悬停动画和交互反馈
- 响应式设计

### 数据库扩展

可以通过JPA注解轻松扩展数据模型：
- `@Entity` 实体类
- `@Repository` 数据访问
- `@Service` 业务逻辑

## 贡献指南

1. Fork 项目
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

## 许可证

此项目使用 MIT 许可证。详情请见 [LICENSE](LICENSE) 文件。

## 联系我们

如有问题或建议，请通过以下方式联系：
- 邮箱: support@careerflow.com
- 项目Issues: [GitHub Issues](your-repo-issues-url)

---

**CareerFlow** - 让每个人都能找到属于自己的职业发展道路 🚀 