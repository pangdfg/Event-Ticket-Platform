package com.platform.tickets;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;
@SpringBootApplication
public class TicketsApplication {

	public static void main(String[] args) {
		System.out.print("JVM  Default TimeZone: " + TimeZone.getDefault().getID());
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Bangkok"));	
		SpringApplication.run(TicketsApplication.class, args);
	}

}
