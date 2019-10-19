package com.spring.microservices.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.spring.microservices.exceptions.UserNotFoundException;
import com.spring.microservices.services.UsersBSI;
import com.spring.microservices.vo.UserVO;

@RestController
@Validated
//To change the scope
//@Scope(value=WebApplicationContext.SCOPE_REQUEST)
public class UserController {

	private Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UsersBSI userBS;

	@GetMapping(path = "/users", produces = "application/json")
	public List<UserVO> getAllUsers() {
		return userBS.getUsers();
	}

	@GetMapping("/users/{userId}")
	public Resource<UserVO> getUserById(@PathVariable int userId) throws UserNotFoundException {
		UserVO userVO = userBS.getUserById(userId);
		if (userVO == null) {
			throw new UserNotFoundException("User Not Found with " + (new Integer(userId).toString()));
		}

		// Adding HATEOAS
		Resource<UserVO> resource = new Resource<UserVO>(userVO);
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllUsers());
		resource.add(linkTo.withRel("all-users"));

		return resource;
	}

	/**
	 * Saving User, also validating the RequestBody.
	 * 
	 * @param user
	 * @return ResponseEntity
	 */
	@PostMapping("/users")
	public ResponseEntity<Object> saveUser(@Valid @RequestBody UserVO user) {
		UserVO userVO = userBS.saveUser(user);
		// Using ResponseEntity to give proper response.
		URI loc = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userVO.getId()).toUri();
		return ResponseEntity.created(loc).build();
	}

	/**
	 * Accepting Query Param from the user.
	 * 
	 * @param userID
	 * @return userId along with name.
	 */
	@GetMapping("/accepting-query-params")
	public String getAnswerForAcceptingParams(@RequestParam(name = "userID", required = false) Integer userID,
			@RequestParam(name = "name", required = false) String name) {
		LOGGER.info("UserID got through Query Parameter: {}", userID);
		LOGGER.info("Name is: {}", name);
		return userID + " " + name;
	}

	/**
	 * First way of Versioning through parameter.
	 * 
	 * @return String
	 */
	@GetMapping(value = "/accepting-params-versioning", params = "version=1")
	public String paramV1() {
		return "Accepted Version 1";
	}

	/**
	 * Second way of Versioning through headers.
	 *
	 * @return String
	 */
	@GetMapping(value = "/accepting-header-versioning", headers = "X-API-Version=1")
	public String headerV1() {
		return "Accepted Header V1";
	}

	/**
	 * Third way of Versioning through produces.
	 * 
	 * The header type can be "Accept" or Content-type both and this will be a value
	 * present in produces.
	 * 
	 * @return String
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
		LOGGER.info("Payload values: {}", map);
		LOGGER.info("Authorization Header Requested Value: {}", userName);
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
	public String saveMapping(@PathVariable(name = "username") String userName,
			@MatrixVariable(name = "user") String path) {
		LOGGER.info("UserName is {}", userName);
		return userName;
	}

	/**
	 * Validating RequestParams using Spring. Used @Validated annotation.
	 * 
	 */
	@GetMapping("/pathvariable/validation/{username}")
	public String getValidation(
			@PathVariable(name = "username") @Length(max = 3, message = "UserName can't be greater than 3 length") String userName) {
		LOGGER.info("UserName is {}", userName);
		return userName;
	}

	/**
	 * Validating PathVariables using Spring. Used @Validated annotation.
	 * 
	 * @return String
	 */
	@GetMapping("/requestparam/validation")
	public String getRequestParamValidation(
			@RequestParam(required = false, name = "firstname") @Min(2) String firstName) {
		LOGGER.info("UserName is {}", firstName);
		return firstName;
	}
}
