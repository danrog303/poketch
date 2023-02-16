package com.github.danrog303.poketch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class PoketchApplication {
    public static void main(String[] args) {
        SpringApplication.run(PoketchApplication.class, args);
    }
}
