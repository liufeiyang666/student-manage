# 学生管理系统 - 后端运行说明

## 环境要求
- JDK 17+ (系统已安装 JDK 18)
- MySQL 8.0+ (已配置，密码：123456)
- Maven 3.6+

## 数据库配置
- 数据库名：student_management
- 用户名：root
- 密码：123456
- 端口：3306

数据库已创建并包含测试数据：
- 管理员：admin / 123456
- 教师：teacher1 / 123456
- 学生：student1 / 123456

## 运行步骤

### 方法1：使用IntelliJ IDEA运行
1. 打开IntelliJ IDEA
2. File -> Open -> 选择 `c:\Users\严益堃\Desktop\测试springboot项目\demo1`
3. 等待Maven依赖下载完成
4. 右键点击 `Demo1Application.java`
5. 选择 "Run 'Demo1Application'"

### 方法2：使用命令行运行
```bash
cd c:\Users\严益堃\Desktop\测试springboot项目\demo1
mvn clean install
mvn spring-boot:run
```

### 方法3：运行打包后的JAR文件
```bash
cd c:\Users\严益堃\Desktop\测试springboot项目\demo1\target
java -jar demo1-0.0.1-SNAPSHOT.jar
```

## 访问地址
- 应用地址：http://localhost:8080
- Swagger文档：http://localhost:8080/doc.html
- Knife4j文档：http://localhost:8080/doc.html

## 测试接口

### 登录接口
```bash
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "123456"
}
```

### 获取当前用户信息（需要token）
```bash
GET http://localhost:8080/api/auth/me
Authorization: Bearer <token>
```

## API文档
启动应用后访问：http://localhost:8080/doc.html
