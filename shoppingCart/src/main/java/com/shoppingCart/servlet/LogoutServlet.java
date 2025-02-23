package com.shoppingCart.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/log-out")
//web servlet is mapped by url mapping(web.xml) not annotation based mapping so no  need to use annotation 
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    
/*for logout servlet we are just going to  remove the session*/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try(PrintWriter out = response.getWriter())
		{
			//for logout servlet we are trying to get auth object which we have set in login Servlet
			if(request.getSession().getAttribute("auth")!=null) {   //to check this session is exist or not 
				                                                   //if it is not null means our user is logged in which we are going to remove now
				request.getSession().removeAttribute("auth");        //removing attribute
				response.sendRedirect("login.jsp");
				
			}
			else {
				response.sendRedirect("index.jsp");
			}
		}
		

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}


	

}
