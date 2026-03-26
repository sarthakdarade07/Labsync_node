package com.labsync.lms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * LabSync Laboratory Management System
 * Main entry point for the Spring Boot application.
 */
@SpringBootApplication
public class LmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(LmsApplication.class, args);
        System.out.println("lms started");
    }
}
