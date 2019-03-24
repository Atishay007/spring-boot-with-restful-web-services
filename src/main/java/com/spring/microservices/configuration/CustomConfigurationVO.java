package com.spring.microservices.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Reading properties from yaml file.
 * 
 */
@Configuration
@ConfigurationProperties(prefix = "fullname")
public class CustomConfigurationVO {

	private String firstname;
	private String lastname;

	public CustomConfigurationVO() {
		super();
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
}