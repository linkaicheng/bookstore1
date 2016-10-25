<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<center>
		<table>
		<c:forEach items="${user.trades}" var="trade">
			
						<tr>
							<td colspan="3">User:${user.username }</td>
						</tr>
					<table border="1" cellspacing="0" cellpadding="10">
							<tr>
								<td colspan="3">TradeTime:${trade.tradeTime }</td>
							</tr>
							<tr>
								<td>书名</td>
								<td>单价</td>
								<td>数量</td>
							</tr>
							<c:forEach items="${trade.items }" var="item">
								<tr>
									<td>${item.book.title }</td>
									<td>${item.book.price }</td>
									<td>${item.quantity }</td>
								</tr>
							</c:forEach>
							<tr><td colspan="3">&nbsp;</td></tr>
					</table>
					<br>
				
						</c:forEach>
		</table>
	</center>
</body>
</html>