package com.spring.microservices.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

//Below annotation used for Swagger.
@ApiModel(description = "This webservice filter the records send in response", value = "FilteringVO")
//This is first way to hide or to filter properties.
@JsonIgnoreProperties(value = { "lastName" })
public class StaticFilteringVO {

	// This is second way, we can hide properties in this way.
	// @JsonIgnore
	private String firstName;
	@ApiModelProperty(notes="lastName should have 2 characters.")
	private String lastName;

	public StaticFilteringVO() {
		super();
	}

	public StaticFilteringVO(String firstName, String lastName) {
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
