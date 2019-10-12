package com.spring.microservices.services;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.spring.microservices.vo.DynamicFilteringVO;
import com.spring.microservices.vo.UserVO;

@Service
public class UsersBS implements UsersBSI {

	private static List<UserVO> users = new ArrayList<>();
	private static int usersCount = 5;
	private List<DynamicFilteringVO> dynamicVOS = new ArrayList<>();

	{
		dynamicVOS.add(new DynamicFilteringVO("1", "Bawa", "Check"));
		dynamicVOS.add(new DynamicFilteringVO("2", "Joshua", "Bloch"));
		dynamicVOS.add(new DynamicFilteringVO("3", "Simran", "Sharma"));
		dynamicVOS.add(new DynamicFilteringVO("4", "Bhuwan", "Bheem"));
	}

	static {
		users.add(new UserVO(1, "Atishay", "Jain"));
		users.add(new UserVO(2, "Gaurav", "Suryawanshi"));
		users.add(new UserVO(3, "Surya", "Costco"));
		users.add(new UserVO(4, "Ajinkya", "Vaze"));
		users.add(new UserVO(5, "Rohini", "Sharma"));
	}

	@Override
	public UserVO getUserById(int id) {
		return users.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
	}

	@Override
	public DynamicFilteringVO getUserByFirstName(@NotNull String firstName) {
		return dynamicVOS.stream().filter(u -> u.getFirstName().equals(firstName)).findFirst().orElse(null);
	}

	@Override
	public List<UserVO> getUsers() {
		return users;
	}

	@Override
	public UserVO saveUser(UserVO user) {
		if (user.getId() == null) {
			user.setId(++usersCount);
		}
		if (user != null) {
			users.add(user);
		}
		return user;
	}

	/**
	 * Filtering VO on the basis of field name.
	 */
	@Override
	public MappingJacksonValue filterProperties(DynamicFilteringVO vo, String fieldName) {

		// creating filter.
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", fieldName);

		FilterProvider filters = new SimpleFilterProvider().addFilter("BeanFilter", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(vo);

		mapping.setFilters(filters);

		return mapping;
	}
}
