package com.cheng.bookstore.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cheng.bookstore.dao.TradeItemDAO;
import com.cheng.bookstore.domain.TradeItem;

public class TradeItemDAOImpl extends BaseDao<TradeItem> implements TradeItemDAO {

	@Override
	public void batchSave(Collection<TradeItem> items) {
		String sql="INSERT INTO tradeitem(bookid,quantity,tradeid) values(?,?,?)";
		Object[][] params=new Object[items.size()][3];
		List<TradeItem> tradeItems=new ArrayList<>(items);
		for(int i=0;i<items.size();i++){
			params[i][0]=tradeItems.get(i).getBookId();
			params[i][1]=tradeItems.get(i).getQuantity();
			params[i][2]=tradeItems.get(i).getTradeId();
		}
		batch(sql, params);
	}

	@Override
	public Set<TradeItem> getTradeItemsWithTradeId(Integer tradeId) {
		String sql="SELECT itemid tradeItemId,bookId,quantity,tradeId from tradeitem where tradeid=?";
		
		return new HashSet<>(queryForList(sql, tradeId));
	}

}
