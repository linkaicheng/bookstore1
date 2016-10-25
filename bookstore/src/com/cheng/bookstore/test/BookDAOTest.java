package com.cheng.bookstore.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import com.cheng.bookstore.dao.BookDAO;
import com.cheng.bookstore.dao.impl.BookDAOImpl;
import com.cheng.bookstore.domain.Book;
import com.cheng.bookstore.domain.ShoppingCart;
import com.cheng.bookstore.domain.ShoppingCartItem;
import com.cheng.bookstore.web.CriteriaBook;
import com.cheng.bookstore.web.Page;

public class BookDAOTest {
private BookDAO bookDao=new BookDAOImpl();

	//@Test
	public void testGetBook() {
		Book book=bookDao.getBook(5);
		System.out.println(book);
	}

	//@Test
	public void testGetPage() {
		CriteriaBook cb=new CriteriaBook(0, Integer.MAX_VALUE, 3);
		Page<Book> page=bookDao.getPage(cb);
		System.out.println("nextPage:"+page.getNextPage());
		System.out.println("prevpage:"+page.getPrevPage());
		System.out.println("pagetNo:"+page.getPageNo());
		System.out.println("pagesize:"+page.getPageSize());
		System.out.println("TotalItemNumber:"+page.getTotalItemNumber());
		System.out.println("TotalPageNumber:"+page.getTotalPageNumber());
		System.out.println("list"+page.getList());
	}

	//@Test
	public void testGetTotalBookNumber() {
		CriteriaBook cb=new CriteriaBook(50, 57, 2);
		long totalBookNumber=bookDao.getTotalBookNumber(cb);
		System.out.println(totalBookNumber);
	}

	//@Test
	public void testGetPageList() {
		CriteriaBook cb=new CriteriaBook(50, 57, 2);
		List<Book> books=bookDao.getPageList(cb, 3);
		System.out.println(books);
	}

	//@Test
	public void testGetStoreNumber() {
		int storeNumber=bookDao.getStoreNumber(5);
		System.out.println(storeNumber);
	}

	@Test
	public void testBatchUpdateStoreNumberAndSalesAmount() {
		Collection<ShoppingCartItem> items=new ArrayList<>();
		
		Book book=bookDao.getBook(1);
		ShoppingCartItem sci=new ShoppingCartItem(book);
		sci.setQuantity(10);
		items.add(sci);
		
		 book=bookDao.getBook(2);
		sci=new ShoppingCartItem(book);
		sci.setQuantity(20);
		items.add(sci);
		
		book=bookDao.getBook(3);
		 sci=new ShoppingCartItem(book);
		sci.setQuantity(30);
		items.add(sci);
		
		bookDao.batchUpdateStoreNumberAndSalesAmount(items);
		
	}

}
