package com.cheng.bookstore.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
	private Map<Integer, ShoppingCartItem> books = new HashMap<>();

	public void updateItemQuantity(int id, int quantity) {
		ShoppingCartItem sc = books.get(id);
		if (sc != null) {
			sc.setQuantity(quantity);
		}
	}

	/**
	 * 移除指定购物项
	 * 
	 * @param id
	 */
	public void removeItem(int id) {
		books.remove(id);
	}

	/**
	 * 清空购物车
	 */
	public void clear() {
		books.clear();
	}

	/**
	 * 返回购物车是否为空
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return books.isEmpty();
	}

	/**
	 * 获取所有商品的总钱数
	 * 
	 * @return
	 */
	public float getTotalMoney() {
		float total = 0;
		for (ShoppingCartItem item : books.values()) {
			total += item.getItemMoney();
		}
		return total;
	}

	/**
	 * 返回购物车中商品的总数量
	 * 
	 * @return
	 */
	public int getBookNumber() {
		int total = 0;
		for (ShoppingCartItem item : books.values()) {
			total += item.getQuantity();
		}
		return total;
	}

	/**
	 * 返回所有ShoppingCartItem组成的集合
	 * 
	 * @return
	 */
	public Collection<ShoppingCartItem> getItems() {
		return books.values();
	}

	public Map<Integer, ShoppingCartItem> getBooks() {
		return books;
	}

	/**
	 * 判断购物车中是否有指定Id的商品
	 * 
	 * @param id
	 * @return
	 */
	public boolean hasBook(Integer id) {
		return books.containsKey(id);
	}

	/**
	 * 
	 */
	public void add(Book book) {
		ShoppingCartItem sc = books.get(book.getId());
		if (sc == null) {
			sc = new ShoppingCartItem(book);
			books.put(book.getId(), sc);

		} else {
			sc.increment();
		}

	}
}
