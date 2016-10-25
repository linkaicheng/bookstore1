package com.cheng.bookstore.test;

import org.junit.Test;

import com.cheng.bookstore.dao.AccountDAO;
import com.cheng.bookstore.dao.impl.AccountDAOImpl;
import com.cheng.bookstore.domain.Account;

public class AccountDAOImplTest {
	AccountDAO ado = new AccountDAOImpl();

	// @Test
	public void testGet() {
		Account account = ado.get(1);
		System.out.println(account.getBalance());
	}

	@Test
	public void testUpdateBalance() {
		ado.updateBalance(1, 540);
	}

}
