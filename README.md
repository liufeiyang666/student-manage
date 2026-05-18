# 学生管理系统

## 项目简介
这是一个基于前后端分离技术架构的学生管理系统。

## 技术栈

### 后端
- Java 17
- Spring Boot 3.x
- Spring Security + JWT
- MyBatis Plus
- MySQL 8.0
- Knife4j (Swagger增强版)

### 前端
- Vue 3
- Vite
- Vue Router
- Pinia
- Element Plus
- Axios
- ECharts

## 项目结构
```
├── demo1/                    # 后端项目
│   ├── src/main/java/
│   ├── src/main/resources/
│   └── pom.xml
└── student-management-web/  # 前端项目
    ├── src/
    ├── index.html
    └── package.json
```

## 运行说明

### 1. 数据库配置
确保MySQL已安装并启动，创建数据库并执行初始化SQL脚本。

```sql
-- 创建数据库
CREATE DATABASE student_management DEFAULT CHARACTER SET utf8mb4;

-- 执行初始化脚本
-- 使用数据库管理工具执行 demo1/src/main/resources/sql/init.sql
```

### 2. 后端运行
```bash
cd demo1

# 使用Maven编译运行
mvn spring-boot:run

# 或在IDE中直接运行 Demo1Application.java
```

后端服务地址：http://localhost:8080

API文档地址：http://localhost:8080/doc.html 或 http://localhost:8080/swagger-ui.html

### 3. 前端运行
```bash
cd student-management-web

# 安装依赖
npm install

# 开发模式运行
npm run dev

# 打包生产环境
npm run build
```

前端服务地址：http://localhost:3000

## 测试账号

| 角色 | 用户名 | 密码 |
|------|--------|------|
| 管理员 | admin | 123456 |
| 教师 | teacher1 | 123456 |
| 学生 | student1 | 123456 |

## 功能模块

### 管理员功能
- 用户管理（新增、编辑、删除、重置密码、启用/禁用）
- 学生管理（CRUD、批量删除）
- 班级管理（CRUD）
- 课程管理（CRUD）
- 成绩管理（录入、编辑、删除）
- 数据统计看板

### 教师功能
- 查看学生列表
- 查看和录入所授课程的成绩
- 查看课程信息

### 学生功能
- 查看个人信息
- 查看课程信息
- 查看成绩信息

## 开发说明

### 后端配置文件
`demo1/src/main/resources/application.yml`

包含：
- 数据库配置
- JWT配置
- MyBatis Plus配置
- 日志配置

### 前端配置文件
`student-management-web/vite.config.js`

配置代理请求到后端API

## 注意事项
1. 确保MySQL服务已启动
2. 确保数据库连接配置正确
3. 前后端端口分别为8080和3000，确保端口未被占用
