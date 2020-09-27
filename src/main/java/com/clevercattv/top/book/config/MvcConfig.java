package com.clevercattv.top.book.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan({"com.clevercattv.top.book.controller", "com.clevercattv.top.book.service",
        "com.clevercattv.top.book.client", "com.clevercattv.top.book.exception"})
public class MvcConfig implements WebMvcConfigurer {



}
