package com.cheng.bookstore.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheng.bookstore.web.ConnectionContext;
import com.cheng.boostore.db.JDBCUtils;

/**
 * Servlet Filter implementation class TranctionFilter
 */
@WebFilter("/*")
public class TranctionFilter implements Filter {

    /**
     * Default constructor. 
     */
    public TranctionFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		Connection connection=null;
		
		try {
			 connection=JDBCUtils.getConnection();
			 
			 connection.setAutoCommit(false);
			 
			ConnectionContext.getInstance().bind(connection);
			
			chain.doFilter(request, response);
			
			connection.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			HttpServletRequest httpServletRequest=(HttpServletRequest) request;
			HttpServletResponse httpServletResponse=(HttpServletResponse) response;
			httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/erro.jsp");
			
		}finally{
			ConnectionContext.getInstance().remove();
			JDBCUtils.release(connection);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
