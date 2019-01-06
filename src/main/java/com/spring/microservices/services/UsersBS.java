package com.spring.microservices.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.microservices.vo.UserVO;

@Service  //Required by spring to understand what class to register.
public class UsersBS implements UsersBSI {

	private static List<UserVO> lstUsers = new ArrayList<>();
	private static int usersCount = 5;

	static {
		lstUsers.add(new UserVO(1, "Atishay", "Jain"));
		lstUsers.add(new UserVO(2, "Gaurav", "Suryawanshi"));
		lstUsers.add(new UserVO(3, "Surya", "Dilwale"));
		lstUsers.add(new UserVO(4, "Ajinkya", "Vaze"));
		lstUsers.add(new UserVO(5, "Rohini", "Sharma"));
	}

	@Override
	public UserVO getUser(int id) {
		return lstUsers.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
	}

	@Override
	public List<UserVO> getUsers() {
		return lstUsers;
	}

	@Override
	public UserVO saveUser(UserVO user) {
		if (user.getId() == null) {
			user.setId(++usersCount);
		}
		if (user != null) {
			lstUsers.add(user);
		}
		return user;
	}

}
