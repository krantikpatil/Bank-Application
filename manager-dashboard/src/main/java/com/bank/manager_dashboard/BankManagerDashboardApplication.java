package com.bank.manager_dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BankManagerDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankManagerDashboardApplication.class, args);
	}
}
