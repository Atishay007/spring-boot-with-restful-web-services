package com.spring.microservices.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.microservices.vo.StaticFilteringVO;

import io.swagger.annotations.ApiResponse;

@RestController
public class StaticFilteringController {

	@ApiResponse(message = "Atishay", code = 200)
	@GetMapping("/filtering")
	public List<StaticFilteringVO> getFilteringVO() {
		return Arrays.asList(new StaticFilteringVO("Atishay", "Jain"), new StaticFilteringVO("Joshua", "Bloch"),
				new StaticFilteringVO("Gaurav", "Saini"), new StaticFilteringVO("Ajinkya", "Vaze"));

	}
}
