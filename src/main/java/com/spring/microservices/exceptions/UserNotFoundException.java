package com.spring.microservices.exceptions;

public class UserNotFoundException extends Exception{

	public UserNotFoundException(String errorMsg) {
		super(errorMsg);
	}
}
