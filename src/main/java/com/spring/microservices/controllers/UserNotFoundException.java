package com.spring.microservices.controllers;

public class UserNotFoundException extends Exception{

	public UserNotFoundException(String errorMsg) {
		super(errorMsg);
	}
}
