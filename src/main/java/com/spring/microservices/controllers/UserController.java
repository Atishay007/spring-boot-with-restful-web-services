package com.spring.microservices.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.spring.microservices.services.UsersBSI;
import com.spring.microservices.vo.UserVO;

@RestController
public class UserController {

	@Autowired
	private UsersBSI userBS;

	@GetMapping("/users")
	public List<UserVO> getAllUsers() {
		return userBS.getUsers();
	}

	// The Path variable name should be common in parameter and in request also.
	@GetMapping("/users/{userId}")
	public UserVO getUserById(@PathVariable int userId) throws Exception {
		UserVO userVO = userBS.getUser(userId);
		if (userVO == null) {
			throw new Exception(new Integer(userId).toString());
		}
		return userVO;
	}

	@PostMapping("/users")
	public ResponseEntity<Object> saveUser(@RequestBody UserVO user) {
		UserVO userVO = userBS.saveUser(user);
		URI loc = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userVO.getId()).toUri();
		return ResponseEntity.created(loc).build();
	}
}
