package com.tusaryan.TestingApp;

import com.tusaryan.TestingApp.services.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//to initialise the dataService bean, alternatively we can use Constructor injection
@RequiredArgsConstructor
@SpringBootApplication
public class TestingAppApplication implements CommandLineRunner {

//	private final DataService dataService;

	@Value("${my.variable}")
	private String myVariable;
	public static void main(String[] args) {
		SpringApplication.run(TestingAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println("my variable: " + myVariable);
//		System.out.println("The data is: "+dataService.getData());
	}
}
