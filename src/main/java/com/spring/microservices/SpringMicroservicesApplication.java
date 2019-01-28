package com.spring.microservices;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import com.spring.microservices.configuration.CustomConfigurationVO;

//@SpringBootApplication(exclude = { JmsAutoConfiguration.class, JmxAutoConfiguration.class })
@SpringBootApplication
//@EnableConfigurationProperties(CustomConfigurationVO.class)
public class SpringMicroservicesApplication {

	@Value("${server.port}")
	private int serverPort;

	public static void main(String[] args) {
		SpringApplication.run(SpringMicroservicesApplication.class, args);
	}

	/**
	 * LocaleResolver is from : import
	 * org.springframework.web.servlet.LocaleResolver; import
	 * org.springframework.web.servlet.i18n.SessionLocaleResolver;
	 * 
	 * This is used fro i18n (internalization).
	 * @return
	 */
	@Bean
	public LocaleResolver localeResolver() {
		// SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		//
		// Now I have to use AcceptHeaderLocaleResolver, as we are taking value of
		// header Accept-Language
		// in spring automatically.
		AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
		localeResolver.setDefaultLocale(Locale.US);

		// use of Server Port
		System.out.println("SpringBoot started on server port: " + serverPort);
		return localeResolver;
	}

	/**
	 * When we are using @Bean annotation then the name of this method should become
	 * as a variable name where we are autowiring this resource like:
	 * 
	 * @Autowired private MessageSource messageSource;
	 * 
	 *            Here Spring will inject messageSource by calling the method name.
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
