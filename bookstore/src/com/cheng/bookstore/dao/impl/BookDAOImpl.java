
package com.cheng.bookstore.dao.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.cheng.bookstore.dao.BookDAO;
import com.cheng.bookstore.web.CriteriaBook;
import com.cheng.bookstore.web.Page;
import com.cheng.bookstore.domain.Book;
//import com.cheng.bookstore.domain.ShoppingCartItem;
import com.cheng.bookstore.domain.ShoppingCartItem;

public class BookDAOImpl extends BaseDao<Book> implements BookDAO{

	@Override
	public Book getBook(int id) {
		String sql = "SELECT id, author, title, price, publishingDate, " +
				"salesAmount, storeNumber, remark FROM mybooks WHERE id = ?";
		return query(sql, id);
	}

	@Override
	public Page<Book> getPage(CriteriaBook cb) {
		Page<Book> page=new Page<>(cb.getPageNo());
		page.setTotalItemNumber(getTotalBookNumber(cb));
		cb.setPageNo(page.getPageNo());
		page.setList(getPageList(cb, 3));
		return page;
	}

	@Override
	public long getTotalBookNumber(CriteriaBook cb) {
		String sql="select count(id) from mybooks where price<=? and price>=?";
		return getSingleVal(sql, cb.getMaxPrice(),cb.getMinPrice());
	}

	@Override
	public List<Book> getPageList(CriteriaBook cb, int pageSize) {
		String sql = "SELECT id, author, title, price, publishingDate, " +
				"salesAmount, storeNumber, remark FROM mybooks " +
				"WHERE price <= ? AND price >=? " +
				"LIMIT ?, ?";
		
		return queryForList(sql, cb.getMaxPrice(),cb.getMinPrice(),(cb.getPageNo()-1)*pageSize,pageSize);
	}

	@Override
	public int getStoreNumber(Integer id) {
		String sql = "SELECT storeNumber FROM mybooks WHERE id = ?";
		
		return getSingleVal(sql, id);
	}

	

	@Override
	public void batchUpdateStoreNumberAndSalesAmount(Collection<ShoppingCartItem> items) {
		String sql="update mybooks set Salesamount=Salesamount+?,"
				+ "Storenumber=Storenumber-? "
				+ "where id=?";
		Object[][] params=new Object[items.size()][3];
		List<ShoppingCartItem> scis=new ArrayList<>(items);
		for(int i=0;i<items.size();i++){
			params[i][0]=scis.get(i).getQuantity();
			params[i][1]=scis.get(i).getQuantity();
			params[i][2]=scis.get(i).getBook().getId();
		}
		batch(sql, params);
	}

}
