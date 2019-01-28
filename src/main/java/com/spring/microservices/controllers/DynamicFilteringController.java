package com.spring.microservices.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.spring.microservices.vo.DynamicFilteringVO;

@RestController
public class DynamicFilteringController {

	private List<DynamicFilteringVO> lst = new ArrayList<>();

	{
		lst.add(new DynamicFilteringVO("1", "Bawa", "Check"));
		lst.add(new DynamicFilteringVO("2", "Joshua", "Bloch"));
		lst.add(new DynamicFilteringVO("3", "Simran", "Sharma"));
		lst.add(new DynamicFilteringVO("4", "Bhuwan", "Bheem"));
	}

	/**
	 * This will do dynamic filtering based on user request fields.
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/users")
	public MappingJacksonValue getAllUsers(@RequestParam("firstname") String firstName) {

		// finding vo by id.
		DynamicFilteringVO vo = lst.stream().filter(e -> e.getFirstName().equalsIgnoreCase(firstName)).findFirst().get();

		// creating filter, according to user requirement
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","firstName");

		// adding filter
		// This BeanFilter is also used @JsonFilter("BeanFilter") on VO, then only this
		// will work.
		FilterProvider filters = new SimpleFilterProvider().addFilter("BeanFilter", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(vo);
		// applying filter on matched Vo.
		mapping.setFilters(filters);

		return mapping;
	}
}
