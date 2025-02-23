package com.shoppingCart.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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

//this servlet is implemented for placing the order via buy button
public class OrderNowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			
			// dateformatter is used cause i want to know the date on which user buy the
			// things

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			Date date = new Date();

			// first of all we need a little bit autherisation cause we want to know which
			// is logged in

			User auth = (User) request.getSession().getAttribute("auth");
			if (auth != null) // means user is logged in
			{
				String productId = request.getParameter("id"); // this id will come from product model
				int productQuantity = Integer.parseInt(request.getParameter("quantity"));
				if (productQuantity <= 0) // you can only buy the order only if quantity is grater than 0
				{
					productQuantity = 1; // if it is we will set it 1

				}

				// while buying order you need product id,user id,quantity and date
				Order orderModel = new Order();
				orderModel.setId(Integer.parseInt(productId)); // product id is comming from product model
				orderModel.setUid(auth.getId());
				orderModel.setQuantity(productQuantity);
				orderModel.setDate(formatter.format(date));

				OrderDao orderDao = new OrderDao(dbConnect.getConnection());
				boolean result=orderDao.insertOrder(orderModel);
				
				if(result)
				{
					ArrayList<Cart>cart_list = (ArrayList<Cart>)request.getSession().getAttribute("cart-list");
					if(cart_list !=null)
					{
						for(Cart c:cart_list)
						{
							if(c.getId()== Integer.parseInt(productId))
							{
								cart_list.remove(cart_list.indexOf(c));
								break;
								
							}
						}
						
					}
					response.sendRedirect("orders.jsp");
				}
				else {
					out.println("Order failed");
				}

			}

			else {
				response.sendRedirect("login.jsp");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
