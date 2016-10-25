package com.cheng.bookstore.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cheng.bookstore.dao.UserDAO;
import com.cheng.bookstore.dao.impl.UserDAOImpl;
import com.cheng.bookstore.domain.User;

public class UserDAOImplTest {
	UserDAO userDao=new UserDAOImpl();
	@Test
	public void testGetUser() {
		User user=new User();
		user=userDao.getUser("Tom");
		System.out.println(user);
	
	}

}
