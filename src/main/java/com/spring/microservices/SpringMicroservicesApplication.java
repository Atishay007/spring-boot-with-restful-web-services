package com.spring.microservices;

import java.io.IOException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

//How to exclude classes
//@SpringBootApplication(exclude = { JmsAutoConfiguration.class, JmxAutoConfiguration.class })
@SpringBootApplication
@EnableConfigurationProperties

public class SpringMicroservicesApplication implements CommandLineRunner {

	@Autowired
	private Environment env;

	// This property is present in
	// application.properties
	@Value("${server.port}")
	private int serverPort;

	// This is present in custom2.yml
	@Value("${fullname.firstname}")
	private String firstName;

	public static void main(String[] args) throws IOException {
		// The Spring will starts its magic of autowiring and other methods during the
		// below method execution.
		SpringApplication.run(SpringMicroservicesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("FirstName using environment :" + env.getProperty("fullname.firstname"));
		System.out.println("FirstName using @Value tag: " + firstName);
		System.out.println("Server Port:" + serverPort);
	}

	/**
	 * This method is used when we want to use yml properties in Spring Environment
	 * using environment.
	 * 
	 * If we use this method then we can't use {@value} tag, becoz we will get error
	 * like Injection of autowired dependencies failed; nested exception is
	 * java.lang.IllegalArgumentException: Could not resolve placeholder
	 * 'fullname.firstname' in value "${fullname.firstname}"
	 * 
	 * So we have manually configured it.
	 * 
	 * This is used when we want: Exposing YAML as Properties in the Spring
	 * Environment
	 * 
	 * ConfigurableApplicationContext will be autowired by the Spring, if there are
	 * 2 parameters then we have to use @Autowired.
	 * 
	 * @return PropertySource<?>
	 */

	@Bean
	public static PropertySource<?> loadYamlPropertiesInSpringEnv(ConfigurableApplicationContext apContext)
			throws IOException {
		YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
		PropertySource<?> applicationYamlPropertySource = loader
				.load("custom2.yml", new ClassPathResource("custom2.yml")).get(0);
		// It is necessary to add this in application context.
		apContext.getEnvironment().getPropertySources().addLast(applicationYamlPropertySource);
		return applicationYamlPropertySource;
	}

	/**
	 * This will give us freedom to use external configuration of yml/yaml file we
	 * are not talking about application.yml, we are talking about custom.yml file
	 * and we have to inject that value using @Value tag.
	 * 
	 * There should be return type of every @Bean methods, we can't make those
	 * methods as void., otherwise runtime exception will be there.
	 * 
	 * If we don't use this method then we can't use {@value} tag, becoz we will get
	 * error like Injection of autowired dependencies failed; nested exception is
	 * java.lang.IllegalArgumentException: Could not resolve placeholder
	 * 'fullname.firstname' in value "${fullname.firstname}"
	 * 
	 * @return PropertySourcesPlaceholderConfigurer
	 * 
	 *         Use of Below Method:: Spring Framework provides two convenient
	 *         classes that can be used to load YAML documents. The
	 *         YamlPropertiesFactoryBean loads YAML as Properties and the
	 *         YamlMapFactoryBean loads YAML as a Map.
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
		PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
		// This is the main part which is used to load yaml files.
		YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
		yaml.setResources(new ClassPathResource("custom2.yml"));
		propertySourcesPlaceholderConfigurer.setProperties(yaml.getObject());
		return propertySourcesPlaceholderConfigurer;
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

	/**
	 * LocaleResolver is from : import
	 * org.springframework.web.servlet.LocaleResolver; import
	 * org.springframework.web.servlet.i18n.SessionLocaleResolver;
	 * 
	 * This is used for i18n (internalization).
	 * 
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
}
