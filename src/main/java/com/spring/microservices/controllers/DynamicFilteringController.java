package com.spring.microservices.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.microservices.services.UsersBSI;
import com.spring.microservices.vo.DynamicFilteringVO;

@RestController
public class DynamicFilteringController {

	@Autowired
	private UsersBSI usersBS;

	/**
	 * This will do dynamic filtering based on user request fields.
	 * 
	 * @param firstName
	 * 
	 * @return MappingJacksonValue
	 */
	@GetMapping("/users/filter")
	public MappingJacksonValue getUserByFirstName(
			@RequestParam(name = "firstname", required = false) String firstName) {

		// finding vo by firstName.
		DynamicFilteringVO vo = usersBS.getUserByFirstName(firstName);
		return usersBS.filterProperties(vo, "firstName");
	}
}
