package org.embed;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringMyApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringMyApplication.class, args);
	}

	private final CityMapper cityMapper;
	
	public SpringMyApplication() {
		// TODO Auto-generated constructor stub
	 this.cityMapper=cityMapper;
	 
	 
	
	}
	
	@Override
	@SuppressWarnings("squid:s106")
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(this.cityMapper.findByState("CA"));
	}
	
	
	
}
