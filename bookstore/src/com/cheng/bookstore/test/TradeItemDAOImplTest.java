package com.cheng.bookstore.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.junit.Test;

import com.cheng.bookstore.dao.TradeItemDAO;
import com.cheng.bookstore.dao.impl.TradeItemDAOImpl;
import com.cheng.bookstore.domain.TradeItem;

public class TradeItemDAOImplTest {
TradeItemDAO tradeItemDao=new TradeItemDAOImpl();
	//@Test
	public void testBatchSave() {
		Collection<TradeItem> items=new ArrayList<>();
		
		items.add(new TradeItem(null, 1, 2, 17));
		items.add(new TradeItem(null, 2, 4, 18));
		items.add(new TradeItem(null, 3, 12, 19));
		items.add(new TradeItem(null, 4, 20, 17));
		tradeItemDao.batchSave(items);
		
	}

	@Test
	public void testGetTradeItemsWithTradeId() {
		
		Set<TradeItem> tradeItems=tradeItemDao.getTradeItemsWithTradeId(13);
		System.out.println(tradeItems);
	}

}
