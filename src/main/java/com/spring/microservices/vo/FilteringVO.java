package com.spring.microservices.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//This is first way to hide or to filter properties.
@JsonIgnoreProperties(value = { "lastName" })
public class FilteringVO {

	// This is second way, we can hide properties in this way.
	// @JsonIgnore
	private String firstName;
	private String lastName;

	public FilteringVO() {
		super();
	}

	public FilteringVO(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
