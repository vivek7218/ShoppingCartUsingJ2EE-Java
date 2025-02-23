package com.shoppingCart.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shoppingCart.Dao.OrderDao;
import com.shoppingCart.connection.dbConnect;
import com.shoppingCart.model.Cart;
import com.shoppingCart.model.Order;
import com.shoppingCart.model.User;

public class CheckOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try(PrintWriter out = response.getWriter())
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			Date date = new Date();
			//retrive all cart product 
			ArrayList<Cart>cart_list = (ArrayList<Cart>)request.getSession().getAttribute("cart-list");
			
			
			User auth = (User)request.getSession().getAttribute("auth");
			
			//check auth and cart-list
			if(cart_list !=null && auth !=null)
			{
				for(Cart c: cart_list)
				{
					Order order = new Order();
					order.setId(c.getId());
					order.setUid(auth.getId());
					order.setDate(formatter.format(date));
					order.setQuantity(c.getQuantity());
					
					OrderDao orderDao = new OrderDao(dbConnect.getConnection());
				boolean result	 = orderDao.insertOrder(order);
				if(!result)
					break;
					
				}
				cart_list.clear();
				response.sendRedirect("orders.jsp");
				
				
			}
			else {
				if(auth==null)
				{
					response.sendRedirect("login.jsp");
				}
				response.sendRedirect("cart.jsp");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
