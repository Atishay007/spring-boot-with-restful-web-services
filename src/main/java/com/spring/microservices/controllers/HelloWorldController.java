package com.spring.microservices.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.spring.microservices.configuration.CustomConfigurationVO;

@RestController
public class HelloWorldController {
	
	@Autowired
	private CustomConfigurationVO config;
	
	// For application health status.
	@GetMapping("")
	public String getHelloWorld() {
		//System.out.println("First name: "+config.getFirstName());
		return "Application Started";
	}

	@GetMapping("/hello/{name}")
	public String getHelloWorld(@PathVariable String name) {
		return name;
	}
}
