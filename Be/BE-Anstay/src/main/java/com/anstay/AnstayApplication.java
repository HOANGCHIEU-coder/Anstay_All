package com.anstay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AnstayApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnstayApplication.class, args);
		System.out.println("Đã chạy xong ....");
	}
}