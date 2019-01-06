package com.spring.microservices.services;

import java.util.List;

import com.spring.microservices.vo.UserVO;

public interface UsersBSI {

	public UserVO getUser(int id);

	public List<UserVO> getUsers();

	public UserVO saveUser(UserVO user);

}
