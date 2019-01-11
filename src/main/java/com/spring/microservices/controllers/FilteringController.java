package com.spring.microservices.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.microservices.vo.FilteringVO;

import io.swagger.annotations.ApiResponse;

@RestController
public class FilteringController {

	@ApiResponse(message = "Atishay", code = 200)
	@GetMapping("/filteringVO")
	public List<FilteringVO> getFilteringVO() {
		return Arrays.asList(new FilteringVO("Atishay", "Jain"), new FilteringVO("Barbie", "Jain"),
				new FilteringVO("Gaurav", "Soni"), new FilteringVO("Ajinkya", "Vaze"));

	}
}
