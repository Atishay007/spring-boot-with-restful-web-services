package com.spring.microservices;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@SpringBootApplication
public class SpringMicroservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMicroservicesApplication.class, args);
	}

	/**
	 * LocaleResolver is from :
	 * import org.springframework.web.servlet.LocaleResolver;
	 * import org.springframework.web.servlet.i18n.SessionLocaleResolver;
	 * 
	 * @return
	 */
	@Bean
	public LocaleResolver localeResolver() {
		//SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		// Now I have to use AcceptHeaderLocaleResolver, as we are taking value of header Accept-Language
		//in spring automatically.
		AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
		localeResolver.setDefaultLocale(Locale.US);
		return localeResolver;
	}
	
	/**
	 * When we are using @bean annotation then the name of this method shoudl become
	 * as a variable name where we are autowiring this resource like:
	 * @Autowired
	 * private MessageSource messageSource;
	 * 
	 * Here Spring will inject messageSource by calling the method name.
	 * @return
	 */
	/*
	 * Very important:::
	 * 
	 * This is now configured in application.properties with name of
	 * spring.messages.basename=messages
	 * 
	 * @Bean public ResourceBundleMessageSource messageSource() {
	 * ResourceBundleMessageSource msgSource = new ResourceBundleMessageSource();
	 * msgSource.setBasename("messages"); return msgSource; }
	 */
}
