package com.xyz.changefeed.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class ChangeFeedApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(ChangeFeedApplication.class, args);
		}catch (Exception ex){
			System.out.println(ex.getMessage());
		}
	}


}
