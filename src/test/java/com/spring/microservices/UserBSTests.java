package com.spring.microservices;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.spring.microservices.services.UsersBSI;
import com.spring.microservices.vo.DynamicFilteringVO;
import com.spring.microservices.vo.UserVO;

@RunWith(MockitoJUnitRunner.class)
public class UserBSTests {

	@Mock
	private UsersBSI usersBS;

	@Test
	public void testGetUserById() {
		UserVO userVO = new UserVO(1, "Josh", "Jenny");
		when(usersBS.getUserById(10)).thenReturn(userVO);
		assertTrue(userVO.getId() == usersBS.getUserById(10).getId());
	}
	
	@Test
	public void testGetUserByFirstName() {
		DynamicFilteringVO vo = new DynamicFilteringVO("1", "Josh", "Jenny");
		when(usersBS.getUserByFirstName("At")).thenReturn(vo);
		assertTrue(vo.getId() == usersBS.getUserByFirstName("At").getId());
	}

}
