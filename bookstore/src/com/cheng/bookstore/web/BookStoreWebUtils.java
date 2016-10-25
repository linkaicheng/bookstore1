package com.cheng.bookstore.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cheng.bookstore.domain.ShoppingCart;

public class BookStoreWebUtils {
public static ShoppingCart getShoppingCart(HttpServletRequest request){
	HttpSession session=request.getSession();
	ShoppingCart sCart=(ShoppingCart) session.getAttribute("shoppingCart");
	if(sCart==null){
		sCart=new ShoppingCart();
		session.setAttribute("shoppingCart", sCart);
	}
	return sCart;
}
}
