package com.spring.microservices.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.spring.microservices.exceptions.UserNotFoundException;
import com.spring.microservices.services.UsersBSI;
import com.spring.microservices.vo.UserVO;

@RestController
/**
 * we have to enable validation for both request parameters and path variables
 */
@Validated
public class UserController {

	@Autowired
	private UsersBSI userBS;

	// For Internalization.
	@Autowired
	private MessageSource messageSource;

	// This is old way.
	// @RequestMapping(path="/users",produces="application/json",
	// method=RequestMethod.GET)
	// This is new way.
	@GetMapping(path = "/users", produces = "application/json")
	public List<UserVO> getAllUsers() {
		return userBS.getUsers();
	}

	// The Path variable name should be common in parameter and in request also.
	// If you want to change name of path variable then we can do this using
	// @PathVariable(name or required or value(default))
	@GetMapping("/users/{userId}")
	public Resource<UserVO> getUserById(@PathVariable int userId) throws UserNotFoundException {
		UserVO userVO = userBS.getUser(userId);
		if (userVO == null) {
			throw new UserNotFoundException("User Not Found with " + (new Integer(userId).toString()));
		}

		// Adding HATEOAS
		Resource<UserVO> resource = new Resource<UserVO>(userVO);
		// We have imported all static methods of ControllerLinkBuilder and linkTo is
		// also part of it.
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllUsers());
		resource.add(linkTo.withRel("all-users"));

		/*
		 * Output for the above HATEOAS. { "id": 1, "firstName": "Atishay", "lastName":
		 * "Jain", "_links": { "all-users": { "href": "http://localhost:8081/users" } }
		 * }
		 */

		return resource;
	}

	/**
	 * Accepting Post Request along with Body.
	 * 
	 * @Valid is necessary to make validations on UserVO.
	 * 
	 *        Exception to be thrown when validation on an argument annotated
	 *        with @Valid fails
	 *        com.spring.microservices.exception.controllers.CustomizedResponsedEntityExceptionHandler.handleMethodArgumentNotValid(MethodArgumentNotValidException,
	 *        HttpHeaders, HttpStatus, WebRequest)
	 * 
	 * @param UserVO
	 * @return ResponseEntity<Object>
	 */
	@PostMapping("/users")
	public ResponseEntity<Object> saveUser(@Valid @RequestBody UserVO user) {
		UserVO userVO = userBS.saveUser(user);
		// Using ResponseEntity to give proper response.
		URI loc = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userVO.getId()).toUri();
		return ResponseEntity.created(loc).build();
	}

	/**
	 * Here we are using Request header and Internationalization or i18n.
	 * 
	 * If I have to add the request header in every method that is internationalized
	 * then it is headache, so spring gives this feature, instead of writing this
	 * 
	 * @RequestHeader(name = "Accept-Language", required = false) Locale locale in
	 *                     method parameter, we can directly use the below way.
	 * 
	 * @return String
	 */
	@GetMapping("/hello-world-internalizalized")
	public String helloWorld() {
		// LocaleContextHolder this will automatically take the header which we are
		// sending from Request.
		return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
	}

	/**
	 * Accepting UserID as Query Parameter.
	 * http://localhost:8081/accepting-params/userID?userID=2
	 * 
	 * OR
	 * 
	 * http://localhost:8081/accepting-query-params?userID=2&name=Atishay
	 * 
	 * By default @RequestParam "required = true " which means it is mandatory. To
	 * make it not mandatory use "required" =false.
	 * 
	 * In Postman we will set key and value in Params section.
	 * 
	 * @param userID
	 * @return String
	 */
	@GetMapping("/accepting-query-params")
	public String getAnswerForAcceptingParams(@RequestParam(name = "userID", required = false) Integer userID,
			@RequestParam(name = "name", required = false) String name) {
		System.out.println("UserID got through Query Parameter: " + userID);
		System.out.println("Name is: " + name);
		return userID + " " + name;
	}

	/**
	 * First way of Versioning through "params" Versioning through "params"
	 * Accepting UserID as Query Parameter.
	 *
	 * Example:URI: http://localhost:8081/accepting-params-versioning In Params tab:
	 * key=version value=1
	 * 
	 * @param userID
	 * @return String
	 */
	@GetMapping(value = "/accepting-params-versioning", params = "version=1")
	public String paramV1() {
		return "Accepted Version 1";
	}

	/**
	 * Second way of Versioning through headers.
	 * 
	 * Example: http://localhost:8081/accepting-header In header section
	 * Key=X-API-Version Value=1
	 * 
	 * @param userID
	 * @return
	 */
	@GetMapping(value = "/accepting-header-versioning", headers = "X-API-Version=1")
	public String headerV1() {
		return "Accepted Header V1";
	}

	/**
	 * Third way of Versioning through produces
	 * 
	 * The header type can be "Accept" or Content-type both and this will be a value
	 * present in produces.
	 * 
	 * @param userID
	 * @return
	 */
	@GetMapping(value = "/produces", produces = "application/vnd.com.company.tass+json;level=1")
	public String producesV1() {
		return "Produces Versioning V1";
	}

	/**
	 * This is just an example of how to use @RequestHeader and how to take input in
	 * format of key-value using Map instead of customVo like UserVO.
	 * 
	 * @param map
	 * @param userName
	 */
	@PostMapping("/lists")
	public void displayHeaderAndRequestBody(@RequestBody Map<String, String> map,
			@RequestHeader("Authorization") String userName) {
		System.out.println(map);
		System.out.println("Authorization Header Requested Value: " + userName);
	}

	/**
	 * Use of Matrix Variable. We have to enable them manually, for this reason we
	 * have create WebConfig.java class becoz by default they are disable. Request:
	 * http://localhost:9001/param-annotation/atishay;user=jain
	 * 
	 * To understand more go through:
	 * https://www.baeldung.com/spring-mvc-matrix-variables
	 * 
	 * @param username
	 * @param path
	 */
	@GetMapping("/param-annotation/{username}")
	public void saveMapping(@PathVariable String username, @MatrixVariable(name = "user") String path) {
		System.out.println(username);
	}

	// Validating RequestParams and PathVariables in Spring.
	// We have already see hoe to Validate @Valid @RequestBody requestPayLoad.
	@GetMapping("/pathvariable/validation/{username}")
	public void getValidation(
			@PathVariable @Length(max = 3, message = "UserName can't be greater than 3 length") String username) {
		System.out.println(username);
	}

	@GetMapping("/requestparam/validation")
	public String getRequestParamValidation(
			@RequestParam(required = false, name = "firstname") @Min(2) String firstName) {
		System.out.println(firstName);
		return firstName;
	}
}
