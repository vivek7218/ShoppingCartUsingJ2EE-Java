package com.shoppingCart.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnect {
	private static Connection connection = null;
	
	public static Connection getConnection() throws SQLException, ClassNotFoundException
	{
		if(connection==null)
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/e_commerce_cart", "root", "12345");
			System.out.println("connected");
		}
		
		return connection;
	}

}
