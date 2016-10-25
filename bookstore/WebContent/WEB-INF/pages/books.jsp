<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="script/jquery-1.7.2.min.js"></script>

<script type="text/javascript">
	
$(function(){
		$("#pageNo").change(function() {
		var val = $(this).val();
		val = $.trim(val);
		var flag = false;
		var reg = /^\d+$/g;
		var pageNo = 0;
		if (reg.test(val)) {
			pageNo = parseInt(val);
			if (pageNo >= 1&& pageNo <= parseInt("${bookPage.totalPageNumber }")) {
				flag = true;
			}
		}
		if (!flag) {

			alert("输入的不是合法的页码.");
			$(this).val("");
			return;
		}
		var href = "bookServlet?method=getBooks&pageNo="+ pageNo + "&" + $(":hidden").serialize();
		window.location.href = href;
		});
		
	})
</script>
<%@include file="/commons/queryCondition.jsp"%>
</head>
<body>
	
	<center>
	<c:if test="${param.title!=null }">
	您已经将${param.title }添加到购物车
	<br><br>
	</c:if>
	<c:if test="${!empty sessionScope.shoppingCart.books }">
	您的购物车有${sessionScope.shoppingCart.bookNumber }件商品<a href="bookServlet?method=forwardPage&page=cart&pageNo=${bookPage.pageNo }">查看购物车</a>
	<br><br>
	</c:if>
		<form action="bookServlet?method=getBooks" method="post">
			Price: <input type="text" size="1" name="minPrice">- <input
				type="text" size="1" name="maxPrice"> <input type="submit"
				value="submit">
		</form>

		<br> <br>

		<table>
			<c:forEach items="${bookPage.list }" var="book">
				<tr>
					<td><a href="bookServlet?method=getBook&pageNo=${bookPage.pageNo }&id=${book.id}">${book.title }</a> <br>${book.author }</td>
					<td>${book.price }</td>
					<td><a href="bookServlet?method=addToCart&pageNo=${bookPage.pageNo }&id=${book.id}&title=${book.title}">加入购物车</a></td>
				</tr>

			</c:forEach>
		</table>
		<br> <br> 共${bookPage.totalPageNumber }页 &nbsp;&nbsp;
		当前第${bookPage.pageNo }页 &nbsp;&nbsp;
		<c:if test="${bookPage.hasPrev }">
			<a href="bookServlet?method=getBooks&pageNo=1">首页</a>
			&nbsp;&nbsp;
			<a href="bookServlet?method=getBooks&pageNo=${bookPage.prevPage }">上一页</a>
		</c:if>
		&nbsp;&nbsp;
		<c:if test="${bookPage.hasNext }">
			<a href="bookServlet?method=getBooks&pageNo=${bookPage.nextPage }">下一页</a>
			&nbsp;&nbsp;
			<a
				href="bookServlet?method=getBooks&pageNo=${bookPage.totalPageNumber }">末页</a>
		</c:if>
		&nbsp;&nbsp; 转到 <input type="text" size="1" id="pageNo">页
	</center>
</body>
</html>