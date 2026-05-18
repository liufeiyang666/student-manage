package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("学生管理系统接口文档")
                        .version("1.0.0")
                        .description("学生管理系统RESTful API文档")
                        .contact(new Contact()
                                .name("系统管理员")
                                .email("admin@example.com")))
                .servers(Collections.singletonList(
                        new Server()
                                .url("http://localhost:8080")
                                .description("开发环境服务器")
                ));
    }
}
