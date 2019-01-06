package com.spring.microservices;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@GetMapping("/hello/{name}")
	public String getHelloWorld(@PathVariable String name) {
		return name;
	}
}
