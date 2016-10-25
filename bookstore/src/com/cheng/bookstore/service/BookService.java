package com.cheng.bookstore.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import com.cheng.bookstore.dao.AccountDAO;
import com.cheng.bookstore.dao.BookDAO;
import com.cheng.bookstore.dao.TradeDAO;
import com.cheng.bookstore.dao.TradeItemDAO;
import com.cheng.bookstore.dao.UserDAO;
import com.cheng.bookstore.dao.impl.AccountDAOImpl;
import com.cheng.bookstore.dao.impl.BookDAOImpl;
import com.cheng.bookstore.dao.impl.TradeDAOImpl;
import com.cheng.bookstore.dao.impl.TradeItemDAOImpl;
import com.cheng.bookstore.dao.impl.UserDAOImpl;
import com.cheng.bookstore.domain.Book;
import com.cheng.bookstore.domain.ShoppingCart;
import com.cheng.bookstore.domain.ShoppingCartItem;
import com.cheng.bookstore.domain.Trade;
import com.cheng.bookstore.domain.TradeItem;
import com.cheng.bookstore.web.CriteriaBook;
import com.cheng.bookstore.web.Page;

public class BookService {
BookDAO bookDAO=new BookDAOImpl();
public Page<Book> getPage( CriteriaBook cb ){
return bookDAO.getPage(cb);	
}
public Book getbook(int id) {
	
	return bookDAO.getBook(id);
}

public boolean addToCart(int id, ShoppingCart sc) {
	Book book=bookDAO.getBook(id);
	if(book!=null){
		sc.add(book);
		return true;
	}
	
	return false;
}
public void removeItemFromShoppingCart(int id, ShoppingCart sc) {
sc.removeItem(id);	
}
public void clearShoppingCart(ShoppingCart sc) {
sc.clear();	
}
public void updateItemQuantity(ShoppingCart shoppingCart, int id, int quantity) {
	// TODO Auto-generated method stub
	shoppingCart.updateItemQuantity(id, quantity);
	
}

private AccountDAO accountDAO=new AccountDAOImpl();
private TradeDAO tradeDAO=new TradeDAOImpl();
private UserDAO userDAO=new UserDAOImpl();
private TradeItemDAO tradeItemDAO=new TradeItemDAOImpl();
public void cash(ShoppingCart shoppingCart, String username, String accountId) {

	//1更新mybooks数据表相关数据项salesamount 和 storenumber
	
	bookDAO.batchUpdateStoreNumberAndSalesAmount(shoppingCart.getItems());
	
	
	
	//2更新 account 数据表的 balance
	accountDAO.updateBalance(Integer.parseInt(accountId), shoppingCart.getTotalMoney());
	//3. 向 trade 数据表插入一条记录
	Trade trade=new Trade();
	
	trade.setTradeTime(new Date(new java.util.Date().getTime()));
	trade.setUserId(userDAO.getUser(username).getUserId());
	tradeDAO.insert(trade);
	//4. 向 tradeitem 数据表插入 n 条记录
	Collection <TradeItem> items=new ArrayList<>();
	for(ShoppingCartItem item:shoppingCart.getItems()){
		TradeItem tradeItem=new TradeItem();
		tradeItem.setBookId(item.getBook().getId());
		tradeItem.setQuantity(item.getQuantity());
		tradeItem.setTradeId(trade.getTradeId());
		items.add(tradeItem);
	}
	tradeItemDAO.batchSave(items);
	//5. 清空购物车
	shoppingCart.clear();
	
}

}
