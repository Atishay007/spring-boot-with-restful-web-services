package com.spring.microservices.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	private Logger LOGGER = LoggerFactory.getLogger(HelloWorldController.class);

	// For application health status.
	@GetMapping("")
	public String getHelloWorld() {
		LOGGER.info("Application Started");
		return "Application Started";
	}
}
