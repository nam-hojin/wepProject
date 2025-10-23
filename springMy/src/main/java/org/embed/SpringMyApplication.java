package org.embed;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringMyApplication  {

	public static void main(String[] args) {
		SpringApplication.run(SpringMyApplication.class, args);
	
		System.out.print("수정하기");
	
	}

	
}
