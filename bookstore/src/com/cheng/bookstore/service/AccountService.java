package com.cheng.bookstore.service;

import com.cheng.bookstore.dao.AccountDAO;
import com.cheng.bookstore.dao.impl.AccountDAOImpl;
import com.cheng.bookstore.domain.Account;

public class AccountService {
	AccountDAO ad=new AccountDAOImpl();
public Account getAccount(int  accountId){
return ad.get(accountId);
}
}
