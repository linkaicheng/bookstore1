package com.cheng.bookstore.dao.impl;

import com.cheng.bookstore.dao.UserDAO;
import com.cheng.bookstore.domain.User;

public class UserDAOImpl extends BaseDao<User> implements UserDAO {

	@Override
	public User getUser(String username) {
		String sql="select userId,username,accountId from userinfo where username=?";
		
		return query(sql, username);
	}

}
