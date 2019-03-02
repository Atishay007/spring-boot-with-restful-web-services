package com.spring.microservices.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.spring.microservices.configuration.CustomConfigurationVO;
import com.spring.microservices.exception.controllers.Checker;

@RestController
public class HelloWorldController {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private CustomConfigurationVO config;
	
	// For application health status.
	@GetMapping("")
	public String getHelloWorld() {
		//System.out.println("First name: "+config.getFirstName());
		
		//This is a custom class added in Library folder.
		//This is just to test how we can add custom class.
		
		//For more info:
		//https://www.wikihow.com/Add-JARs-to-Project-Build-Paths-in-Eclipse-%28Java%29
		Checker checker = new Checker("Atishay", "Jain");
		//System.out.println(checker.getFirstName());
		
		System.out.println("Spring ENV: "+env.getProperty("fullname.firstname"));
		System.out.println("Using External Configuration :"+config.getFirstname());
		return "Application Started";
	}

	@GetMapping("/hello/{name}")
	public String getHelloWorld(@PathVariable String name) {
		return name;
	}
}
