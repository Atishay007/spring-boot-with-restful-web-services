package com.spring.microservices.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "This webservice filter the records send in response", value = "FilteringVO")
@JsonIgnoreProperties(value = { "lastName" })
public class StaticFilteringVO {

	private String firstName;
	@ApiModelProperty(notes = "lastName should have 2 characters.")
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
