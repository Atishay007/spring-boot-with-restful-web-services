# Spring Boot Project.
A small project using Spring Boot which provides operations on User.

# It provides following functionalities:
1. Internalization(Supports US and French- Created 2 properties file{messages_fr.properties, messages.properties})
2. Basic Spring Authentication (Dependency commented in POM).
3. Rest API versioning using v1(custom), headers versioning, params versioning.
4. It uses H2 (In-memory database,which uses data.sql file to create new users), to open H2 UI console
http://localhost:9001/h2-console  : Use this URL
Make sure: jdbc:url in UI is referring to : jdbc:h2:mem:testdb
5. It uses HATEOAS to create links of other URI.
6. It uses Spring ResponseEntityExceptionHandler class to provide custom Exception Handling.
7. It uses javax.validation.Valid annotations to validate the RequestBody coming in POST method.
8. It uses Swagger to document Rest API (http://localhost:9000/swagger-ui.html) Port can be change, please check application.properties: Change the port where your tomcat is listening.
9. It Provides response in Json/xml format and accepts the requestBody in both format, to accept/showing responses in XML we have used
com.fasterxml.jackson.dataformat dependency.
10. It uses filtering feature using jackson annotations(It means which fields does user wants to see).
11. It creates executable jar with the help of dependency in pom(Project Object Model) spring-boot-maven-plugin.
12. It uses externalizable configuration by using "custom_configuration.properties". Currently supports ".properties file" but the same functinality can be done for yaml or yml(yet another markup language) files.
13. This app is using Spring Rest WebServices.
14. Using DevTools to reload the changes without manually restarting the application.
15. Spring Security Basic In-Memory authentication.
16. Included dependency for HAL Browser(JSON Hypertext Application Language, or HAL)
URL:
To open HAL browser with actuator:
http://localhost:9008/browser/index.html#/actuator

To open only HAL Browser
http://localhost:9008/browser/index.html#

To open any rest url with HAL Browser
http://localhost:9008/browser/index.html#/users

# Important
1. Added: How to do external configuration for yml/yaml file (not application.yml) and injecting values using Spring Environment and @Value annotation.
2. Removed: External configuration for other properties file as it was very easy and there example are available on internet.

# Execution:
1. To run the above app, use below command from cmd prompt.
mvn run spring-boot:run
2. To run the executable jar, open the directory where the jar is created.
java -jar spring-microservices-0.0.1-SNAPSHOT.jar

# Problems & Solutions:
1. The Tomcat connector configured to listen on port 9000 failed to start. The port may already be in use or the connector may be misconfigured.
Solution: If application fail to start, either change the server port in application.properties
or
java -jar spring-microservices-0.0.1-SNAPSHOT.jar --server.port=8083
or
java -jar -Dserver.port=8083 spring-microservices-0.0.1-SNAPSHOT.jar

2. If Spring Security is uncommented in POM.
User username: user and password: It will be displayed in Console.
Use these username and password and set these into Authorization column by selecting Authorization as "Basic"

3. Implemented Spring Security In-memory basic authentication, please see: BasicAuthenticationHandler.java
If sending request from PostMan, use Basic-Auth under Authorization tab and then set the user and password as given in above file
