你现在是一名资深全栈架构师兼代码生成助手，请为我生成一个完整可实现的前后端分离学生管理系统项目，要求严格按照下面的技术栈、功能、架构、目录结构、接口规范、数据库设计、代码风格和开发步骤来输出。

一、项目目标
开发一个 学生管理系统，用于学校或培训机构对学生信息进行统一管理。系统分为：

管理员端
- 登录/退出
- 学生信息管理
- 班级管理
- 课程管理
- 成绩管理
- 用户管理
- 数据统计看板

普通用户端（教师/学生）
- 登录
- 查看个人信息
- 查看课程信息
- 查看成绩信息

要求系统具备基础权限控制、JWT 鉴权、规范的 RESTful API、可维护的前后端工程结构，以及清晰的数据库设计。

二、技术栈要求
后端
Java 21+
Spring Boot 4.x
Spring Web
Spring Validation
Spring Security
JWT
MyBatis
MySQL 8.0
Lombok
Maven
Knife4j / SpringDoc OpenAPI（用于接口文档）
全局异常处理
统一返回结果封装
分层架构：controller / service / mapper / entity / dto / vo / config / security / exception
前端
Vue 3
Vite
Vue Router
Pinia
Axios
Element Plus
ECharts（数据统计图表）
SCSS
前端路由守卫
登录态持久化
动态菜单/权限控制（可采用角色控制的简化版本）
三、系统功能需求
请完整实现以下功能模块。

1. 登录与认证模块
用户名密码登录
登录成功返回 JWT token
前端保存 token
后续请求通过请求头携带 token
后端基于 Spring Security + JWT 对接口进行认证
实现退出登录
token 过期后前端自动跳转登录页
2. 用户与角色模块
用户分为：

ADMIN：管理员
TEACHER：教师
STUDENT：学生
功能：

用户列表查询
新增用户
编辑用户
删除用户
重置密码
启用/禁用用户
按角色筛选
3. 学生管理模块
字段建议包含：

学号
姓名
性别
年龄
手机号
邮箱
班级
入学日期
家庭住址
状态（在读/休学/毕业）
功能：

学生分页查询
按姓名/学号/班级搜索
新增学生
编辑学生
删除学生
批量删除
查看学生详情
4. 班级管理模块
字段建议：

班级名称
班级编号
班主任
班级描述
创建时间
功能：

班级列表
新增班级
编辑班级
删除班级
查询班级下学生数量
5. 课程管理模块
字段建议：

课程名称
课程编号
学分
任课教师
课程描述
功能：

课程分页查询
新增课程
编辑课程
删除课程
根据教师查询课程
6. 成绩管理模块
字段建议：

学生ID
课程ID
平时成绩
期末成绩
总成绩
学期
功能：

成绩录入
成绩修改
成绩删除
按学生查询成绩
按课程查询成绩
自动计算总成绩
支持成绩统计（平均分、最高分、最低分、及格率）
7. 首页数据统计模块
管理员首页展示：

学生总数
教师总数
班级总数
课程总数
最近新增学生
成绩分布图
各班级人数统计图
四、权限设计要求
请实现基于角色的权限控制（RBAC 简化版）：

角色访问规则
ADMIN：
- 拥有所有模块管理权限

TEACHER：
- 可查看学生列表
- 可查看和录入自己课程的成绩
- 可查看课程信息

STUDENT：
- 仅可查看自己的个人信息、课程和成绩

后端权限要求
使用 Spring Security
JWT 认证过滤器
方法级权限控制（如 @PreAuthorize）
公共接口仅开放登录接口与必要静态资源
前端权限要求
路由守卫
根据角色动态显示菜单
无权限页面跳转 403
五、数据库设计要求
请设计完整 MySQL 8.0 数据库表结构，并输出：

建库 SQL
建表 SQL
初始化测试数据 SQL
至少包含以下表：

sys_user 用户表
sys_role 角色表
sys_user_role 用户角色关联表
class_info 班级表
student 学生表
teacher 教师表
course 课程表
score 成绩表
要求：

主键自增或雪花ID均可，但要统一
字段命名规范
必要索引
外键关系可逻辑体现，不强制数据库外键
包含创建时间、更新时间、逻辑删除字段（如适用）
六、后端工程要求
请生成一个完整规范的 Spring Boot 项目，目录结构示例如下：

student-management-server
├── src/main/java/com/example/student
│   ├── StudentManagementApplication.java
│   ├── config
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── enums
│   ├── exception
│   ├── mapper
│   ├── security
│   ├── service
│   ├── service/impl
│   ├── utils
│   └── vo
├── src/main/resources
│   ├── mapper
│   ├── application.yml
│   └── sql
└── pom.xml
后端必须生成以下内容：

1. pom.xml
包含所有必要依赖：

spring-boot-starter-web
spring-boot-starter-security
spring-boot-starter-validation
mybatis-spring-boot-starter
mysql-connector-j
jjwt 或等价 JWT 库
lombok
knife4j/openapi
其他必要依赖
2. application.yml
包含：

端口配置
MySQL 数据源配置
MyBatis 配置
JWT 配置（secret、expiration）
日志配置
3. 统一响应结构
例如：

{
  "code": 200,
  "message": "success",
  "data": {}
}
4. 全局异常处理
参数校验异常
业务异常
权限异常
未登录异常
系统异常
5. 安全模块
必须生成：

SecurityConfig
JwtAuthenticationFilter
JwtTokenProvider / JwtUtils
UserDetailsService 实现
密码加密器（BCrypt）
自定义认证失败/权限不足处理器
6. 实体类、DTO、VO、Mapper、Service、Controller
请为每个模块都生成完整代码，不要只给伪代码。

7. MyBatis 要求
使用 Mapper 接口 + XML
XML 中实现常用 CRUD、分页查询、条件查询
SQL 要规范清晰
对成绩统计、学生筛选等写出实际 SQL
8. 分页要求
后端统一支持分页参数：

pageNum
pageSize
返回结构示例：

{
  "list": [],
  "total": 100,
  "pageNum": 1,
  "pageSize": 10
}
七、前端工程要求
请生成一个完整规范的 Vue3 项目，目录结构示例如下：

student-management-web
├── src
│   ├── api
│   ├── assets
│   ├── components
│   ├── layout
│   ├── router
│   ├── store
│   ├── utils
│   ├── views
│   │   ├── login
│   │   ├── dashboard
│   │   ├── student
│   │   ├── class
│   │   ├── course
│   │   ├── score
│   │   ├── user
│   │   └── profile
│   ├── App.vue
│   └── main.js
├── package.json
├── vite.config.js
└── .env.development
前端必须生成以下内容：

1. 登录页
用户名密码表单
表单校验
登录成功后跳转首页
登录失败提示
2. 布局页
左侧菜单
顶部导航栏
面包屑
用户下拉菜单
退出登录
3. 学生管理页
查询表单
表格展示
分页
新增/编辑弹窗
删除确认
批量删除
4. 班级管理页
表格展示
新增/编辑/删除
班级人数统计字段
5. 课程管理页
表格展示
新增/编辑/删除
教师选择
6. 成绩管理页
查询筛选
成绩录入弹窗
成绩编辑
统计信息展示
7. 用户管理页
用户分页
用户新增/编辑/删除
角色分配
启用禁用
8. 个人中心页
查看个人信息
修改密码
9. Dashboard
统计卡片
ECharts 图表
最近新增学生列表
10. 前端基础能力
必须生成：

Axios 请求封装
请求拦截器（自动加 token）
响应拦截器（token 失效统一处理）
Pinia 用户状态管理
路由守卫
API 模块化管理
八、接口设计要求
请为系统设计完整 RESTful API，并输出：

接口路径
请求方式
请求参数
返回示例
权限要求
至少包含以下接口：

认证接口
POST /api/auth/login
POST /api/auth/logout
GET /api/auth/me
用户管理
GET /api/users
POST /api/users
PUT /api/users/{id}
DELETE /api/users/{id}
PUT /api/users/{id}/status
PUT /api/users/{id}/reset-password
学生管理
GET /api/students
GET /api/students/{id}
POST /api/students
PUT /api/students/{id}
DELETE /api/students/{id}
DELETE /api/students/batch
班级管理
GET /api/classes
POST /api/classes
PUT /api/classes/{id}
DELETE /api/classes/{id}
课程管理
GET /api/courses
POST /api/courses
PUT /api/courses/{id}
DELETE /api/courses/{id}
成绩管理
GET /api/scores
POST /api/scores
PUT /api/scores/{id}
DELETE /api/scores/{id}
GET /api/scores/statistics
Dashboard
GET /api/dashboard/overview
九、代码生成要求
请严格按以下要求输出代码：

1. 输出顺序
按以下顺序逐步输出：

项目总体说明
数据库设计与 SQL
后端项目结构
后端完整代码
前端项目结构
前端完整代码
接口联调说明
本地运行说明
后续优化建议
2. 代码要求
必须尽量输出完整可运行代码
不要只给思路
不要省略关键文件
每个文件都要标注文件路径
代码块必须清晰
配置文件要完整
SQL 要可直接执行
前端页面至少实现可用的 CRUD 界面
后端接口至少实现完整 CRUD 和登录鉴权
3. 质量要求
代码命名规范
注释适量清晰
结构合理
避免明显 bug
保持前后端字段一致
可直接用于二次开发
十、额外要求
1. 默认账号
请初始化以下测试账号：

管理员：admin / 123456
教师：teacher1 / 123456
学生：student1 / 123456
密码请使用 BCrypt 加密后插入数据库，或者在项目启动时自动初始化。

2. 样式要求
界面简洁现代
Element Plus 风格统一
表单和表格布局规范
Dashboard 有基本可视化效果
3. 可扩展性要求
请在设计中考虑后续扩展：

导入导出 Excel
文件上传头像
细粒度权限
多角色用户
操作日志
Redis 缓存
Docker 部署
十一、如果内容过长，请按阶段输出
如果一次无法输出完整内容，请按以下阶段分批输出，并保证每一阶段都完整：

阶段 1
项目说明
数据库设计
SQL
后端项目结构
后端基础配置代码
阶段 2
后端业务模块完整代码（认证、用户、学生、班级、课程、成绩、dashboard）
阶段 3
前端项目结构
前端基础配置
登录、布局、路由、store、axios 封装
阶段 4
前端各业务页面完整代码
接口联调
运行说明
优化建议
每个阶段结束时，请提示我输入“继续”再输出下一阶段。

十二、特别注意事项
后端使用 MyBatis XML，不要改成 JPA
认证必须使用 JWT
前端必须使用 Vue3 + Pinia + Element Plus
数据库必须是 MySQL 8.0
接口必须符合 RESTful 风格
所有模块必须可落地实现
尽量避免使用过于简化的伪代码
输出时优先保证“能运行”和“结构完整”
现在请开始输出 阶段 1，先给我：

项目总体说明
数据库表设计
完整 SQL
后端项目目录结构
后端基础配置代码（pom.xml、application.yml、统一返回体、异常处理、security 基础骨架）
