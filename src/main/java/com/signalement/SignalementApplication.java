package com.signalement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
public class SignalementApplication {

    public static void main(String[] args) {
        SpringApplication.run(SignalementApplication.class, args);
    }

}
