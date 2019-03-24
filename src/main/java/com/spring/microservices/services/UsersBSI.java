package com.spring.microservices.services;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.http.converter.json.MappingJacksonValue;

import com.spring.microservices.vo.DynamicFilteringVO;
import com.spring.microservices.vo.UserVO;

public interface UsersBSI {

	public UserVO getUserById(int id);

	public List<UserVO> getUsers();

	public UserVO saveUser(UserVO user);

	public DynamicFilteringVO getUserByFirstName(@NotNull String firstName);

	public MappingJacksonValue filterProperties(DynamicFilteringVO vo, String fieldName);

}
