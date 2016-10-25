package com.cheng.bookstore.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheng.bookstore.domain.Account;
import com.cheng.bookstore.domain.Book;
import com.cheng.bookstore.domain.ShoppingCart;
import com.cheng.bookstore.domain.ShoppingCartItem;
import com.cheng.bookstore.domain.User;
import com.cheng.bookstore.service.AccountService;
import com.cheng.bookstore.service.BookService;
import com.cheng.bookstore.service.UserService;
import com.cheng.bookstore.web.BookStoreWebUtils;
import com.cheng.bookstore.web.CriteriaBook;
import com.cheng.bookstore.web.Page;
import com.google.gson.Gson;

/**
 * Servlet implementation class BookServlet
 */
@WebServlet("/bookServlet")
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookService bookService=new BookService();
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String methodName = request.getParameter("method");
		try {
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class,
					HttpServletResponse.class);
			method.setAccessible(true);
			method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
	protected void cash(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username=request.getParameter("username");
		String accountId=request.getParameter("accountId");
		StringBuffer erros=volidateFormField(username,accountId);
		//����֤ͨ��
		if(erros.toString().equals("")){
			erros=volidateUser(username, accountId);
			//�û���֤ͨ��
			if(erros.toString().equals("")){
				erros=volidateBookStoreNumber(request);
				//�����֤ͨ��
				if(erros.toString().equals("")){
					erros=volidateBalance(request, accountId);
					//�����֤ͨ��
					
				}
			}
			
		}
		
		if(!erros.toString().equals("")){
			request.setAttribute("erros", erros);
			request.getRequestDispatcher("/WEB-INF/pages/cash.jsp").forward(request, response);
			return;
		}
		//��֤ͨ��
		bookService.cash(BookStoreWebUtils.getShoppingCart(request),username,accountId);
		//֧���ɹ�
		response.sendRedirect(request.getContextPath()+"/success.jsp");
		
		
		
	}
	private AccountService as=new AccountService();
	/**
	 * ��֤����Ƿ����
	 * @param request
	 * @param accountId
	 * @return
	 */
	public StringBuffer volidateBalance(HttpServletRequest request,String accountId){
		StringBuffer erros=new StringBuffer("");
		ShoppingCart sc=BookStoreWebUtils.getShoppingCart(request);
		Account account=as.getAccount(Integer.parseInt(accountId));
		if(account!=null){
			
			if(account.getBalance()<sc.getTotalMoney()){
				erros.append("����");
			}
			
		}
		
		return erros;
		
	}
	/**
	 * ��֤����Ƿ����
	 * @param request
	 * @return
	 */
	public StringBuffer volidateBookStoreNumber(HttpServletRequest request){
		ShoppingCart sc=BookStoreWebUtils.getShoppingCart(request);
		StringBuffer erros=new StringBuffer("");
		for(ShoppingCartItem sci:sc.getItems()){
			int quantity=sci.getQuantity();
			int storeNumber=bookService.getbook(sci.getBook().getId()).getStoreNumber();
			if(quantity>storeNumber){
				erros.append(sci.getBook().getTitle()+"��治��<br>");
			}
		}
		return erros;
		
	}
	private UserService userService=new UserService();
	/**
	 * ��֤�û������ʺ��Ƿ�ƥ��
	 * @param username
	 * @param accountId
	 * @return
	 */
	public StringBuffer volidateUser(String username,String accountId){
		User user=userService.getUser(username);
		boolean flag=false;
		StringBuffer erros=new StringBuffer("");
		if(user!=null){
			int accountId2=user.getAccountId();
			if(accountId.trim().equals(""+accountId2)){
				flag=true;
			}
		}
		if(!flag){
			erros.append("�û������ʺŲ�ƥ��");
		}
		
		return erros;
	}
	
	/**
	 * ��֤�����Ƿ�Ϊ��
	 * @param username
	 * @param accountId
	 * @return
	 */
	public StringBuffer volidateFormField(String username, String accountId) {
		StringBuffer erros=new StringBuffer("");
		if(username==null||username.trim().equals("")){
			erros.append("�û�����Ϊ��,<br>");
		}
		if(accountId==null||accountId.trim().equals("")){
			erros.append("�ʺŲ���Ϊ��");
		}
		return erros;
	}

	/**
	 * �޸���Ʒ����
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void updateItemQuantity(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ShoppingCart shoppingCart=BookStoreWebUtils.getShoppingCart(request);
		String idStr=request.getParameter("id");
		String quantityStr=request.getParameter("quantity");
		int id=-1;
		int quantity=-1;
		try {
			id=Integer.parseInt(idStr);
			quantity=Integer.parseInt(quantityStr);
		} catch (NumberFormatException e) {}
		if(id>0&&quantity>0){
			bookService.updateItemQuantity(shoppingCart,id,quantity);
		}
		Map<String, Object> result=new HashMap<>();
		result.put("bookNumber", shoppingCart.getBookNumber());
		result.put("totalMoney", shoppingCart.getTotalMoney());
		Gson gson=new Gson();
		String jsonStr=gson.toJson(result);
		response.setContentType("text/javascript");
		response.getWriter().print(jsonStr);
		
		
	}
	/**
	 * ��չ��ﳵ
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void clear(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ShoppingCart sc=BookStoreWebUtils.getShoppingCart(request);
		bookService.clearShoppingCart(sc);
		request.getRequestDispatcher("/WEB-INF/pages/emptycart.jsp").forward(request, response);
	}
	/**
	 * ɾ�����ﳵ��ĳ��
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void remove(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idStr=request.getParameter("id");
		int id=-1;
		try {
			id=Integer.parseInt(idStr);
		} catch (Exception e) {}
		
			ShoppingCart sc=BookStoreWebUtils.getShoppingCart(request);
			bookService.removeItemFromShoppingCart(id, sc);
		if(sc.isEmpty()){
			request.getRequestDispatcher("/WEB-INF/pages/emptycart.jsp").forward(request, response);
			return ;
		}
		request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
	}
	protected void forwardPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = request.getParameter("page");
		String pageNO=request.getParameter("pageNo");
		
		request.getRequestDispatcher("/WEB-INF/pages/" + page + ".jsp").forward(request, response);
	}

	/**
	 * ����Ʒ��ӵ����ﳵ
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void addToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr=request.getParameter("id");
		int id=-1;
		boolean flag=false;
		try {
			id=Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
			
		}
		if(id>0){
		ShoppingCart sc=BookStoreWebUtils.getShoppingCart(request);
		flag=bookService.addToCart(id,sc);
		}
		if(flag){
			getBooks(request, response);
			return;
		}
		response.sendRedirect(request.getContextPath()+"/erro.jsp");
		
		
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void getBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String idStr=request.getParameter("id");
		int id=-1;
		Book book=null;
		try {
			id=Integer.parseInt(idStr);
		} catch (NumberFormatException e) {}
		if(id>0){
			 book=bookService.getbook(id);
		}
		if(book==null){
			response.sendRedirect(request.getContextPath()+"/erro.jsp");
		}
		request.setAttribute("book", book);
		request.getRequestDispatcher("/WEB-INF/pages/book.jsp").forward(request, response);
		
		
		
	}
/**
 * 
 * @param request
 * @param response
 * @throws ServletException
 * @throws IOException
 */
	protected void getBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pageNoStr = request.getParameter("pageNo");
		String minPriceStr = request.getParameter("minPrice");
		String maxPriceStr = request.getParameter("maxPrice");
//		System.out.println(pageNoStr+"jkj");
		int pageNo = 1;
		int minPrice = 0;
		int maxPrice = Integer.MAX_VALUE;

		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {
		}
		try {
			minPrice = Integer.parseInt(minPriceStr);
		} catch (NumberFormatException e) {
		}
		try {
			maxPrice = Integer.parseInt(maxPriceStr);
		} catch (NumberFormatException e) {
		}
		
		CriteriaBook cb=new CriteriaBook(minPrice, maxPrice, pageNo);
		Page<Book> page=bookService.getPage(cb);
		request.setAttribute("bookPage", page);
		request.getRequestDispatcher("/WEB-INF/pages/books.jsp").forward(request, response);

	}

}
