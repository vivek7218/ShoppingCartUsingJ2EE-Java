package com.shoppingCart.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.shoppingCart.model.User;

public class UserDao {
	private Connection connection;
	private String query;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	
	public UserDao(Connection connection)
	{
		this.connection= connection;
		
	}
	
	//method for user to login
	public User userLogin(String email,String password)
	{
		User user = null;
		try {
			query = "select * from users where email=? and password=? ";
			preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			resultSet=preparedStatement.executeQuery();
			
			
			if(resultSet.next())
			{
				user = new User();
				user.setId(resultSet.getInt("id"));
				user.setName(resultSet.getString("name"));
				user.setEmail(resultSet.getString("email"));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return user;
	}

}
