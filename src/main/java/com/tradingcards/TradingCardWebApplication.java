package com.tradingcards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TradingCardWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(TradingCardWebApplication.class, args);
        System.out.println("✅ Trading Card Web App Started!");
        System.out.println("🌐 Open: http://localhost:8080");
    }
}