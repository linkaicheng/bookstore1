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
	 * �Ƴ�ָ��������
	 * 
	 * @param id
	 */
	public void removeItem(int id) {
		books.remove(id);
	}

	/**
	 * ��չ��ﳵ
	 */
	public void clear() {
		books.clear();
	}

	/**
	 * ���ع��ﳵ�Ƿ�Ϊ��
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return books.isEmpty();
	}

	/**
	 * ��ȡ������Ʒ����Ǯ��
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
	 * ���ع��ﳵ����Ʒ��������
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
	 * ��������ShoppingCartItem��ɵļ���
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
	 * �жϹ��ﳵ���Ƿ���ָ��Id����Ʒ
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
