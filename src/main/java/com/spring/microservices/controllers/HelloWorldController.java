package com.spring.microservices.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	// For application health status.
	@GetMapping("")
	public String getHelloWorld() {
		return "Application Started";
	}

	@GetMapping("/hello/{name}")
	public String getHelloWorld(@PathVariable String name) {
		return name;
	}
}
