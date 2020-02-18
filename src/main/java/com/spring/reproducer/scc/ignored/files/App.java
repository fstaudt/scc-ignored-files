package com.spring.reproducer.scc.ignored.files;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.spring.reproducer", proxyBeanMethods = false)
class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}