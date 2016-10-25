package com.cheng.bookstore.dao.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import com.cheng.bookstore.dao.TradeDAO;
import com.cheng.bookstore.domain.Trade;

public class TradeDAOImpl extends BaseDao<Trade> implements TradeDAO{
	
	@Override
	public void insert(Trade trade) {
		String sql="INSERT INTO trade(userId,tradeTime) VALUES (?,?)";
	long tradeId=insert(sql, trade.getUserId(),trade.getTradeTime());
	trade.setTradeId((int)tradeId);
		
	}

	@Override
	public Set<Trade> getTradesWithUserId(Integer userId) {
		String sql="SELECT tradeId,userId,tradeTime from trade where userId=? order by tradetime desc";
	return new LinkedHashSet<>(queryForList(sql, userId));
	}

}
