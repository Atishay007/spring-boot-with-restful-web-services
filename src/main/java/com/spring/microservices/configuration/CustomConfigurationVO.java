package com.spring.microservices.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * This class is using custom yml/yaml file.
 * Using properties file in external configuration is very easy.
 * 
 * But this custom yaml file functionality has taken more than 4 hrs.
 * We have achieved 2 things.
 * 
 * 1. External configuration for custom yml/yaml file not application.yaml/yml.
 * 2. We can inject those value using @Value annotation.
 * 
 * @author Champ
 *
 */
@Configuration
//@PropertySource can't be used when there is yaml file.
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