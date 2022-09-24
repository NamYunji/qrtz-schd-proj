package com.qrtz.schd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QrtzSchdTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(QrtzSchdTestApplication.class, args);
		System.out.println("Quartz Schedule Test Application Started ...");
	}

}
