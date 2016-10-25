package com.cheng.bookstore.service;

import java.util.Set;

import com.cheng.bookstore.dao.BookDAO;
import com.cheng.bookstore.dao.TradeDAO;
import com.cheng.bookstore.dao.TradeItemDAO;
import com.cheng.bookstore.dao.impl.BookDAOImpl;
import com.cheng.bookstore.dao.impl.TradeDAOImpl;
import com.cheng.bookstore.dao.impl.TradeItemDAOImpl;
import com.cheng.bookstore.dao.impl.UserDAOImpl;
import com.cheng.bookstore.domain.Book;
import com.cheng.bookstore.domain.Trade;
import com.cheng.bookstore.domain.TradeItem;
import com.cheng.bookstore.domain.User;

public class UserService {
	UserDAOImpl userDAOImpl=new UserDAOImpl();
public User getUser(String username){
	return userDAOImpl.getUser(username);
}
private TradeDAO tradeDAO=new TradeDAOImpl();
private TradeItemDAO tradeItemDAO=new TradeItemDAOImpl();
private BookDAO bookDAO=new BookDAOImpl();
public User getUserWithTrades(String username) {
	User user=userDAOImpl.getUser(username);
	if(user==null){
		return null;
	}
	Set<Trade> trades=tradeDAO.getTradesWithUserId(user.getUserId());
	if(trades!=null){
		for(Trade trade:trades){
			Set<TradeItem> tradeItems=tradeItemDAO.getTradeItemsWithTradeId(trade.getTradeId());
			if(tradeItems!=null){
				for(TradeItem tradeItem:tradeItems){
					Book book=bookDAO.getBook(tradeItem.getBookId());
					tradeItem.setBook(book);
				}
				trade.setItems(tradeItems);
			}
		}
		
		user.setTrades(trades);
	}
	
	return user;
}
}
