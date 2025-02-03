package com.ccd.chess;

import com.ccd.chess.util.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringApplication {
    private static final String TAG = SpringApplication.class.getSimpleName();

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
        Logger.d(TAG, "http://localhost:8090");
    }
}