package com.shoppingCart.Dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.shoppingCart.model.*;

public class OrderDao {
	private Connection connection;
	private String query;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	
	
	public OrderDao(Connection connection) {
		super();
		this.connection = connection;
	}
	
	public boolean insertOrder(Order model)
	{
		boolean result  = false;
		try {
			
			query = "insert into orders(p_id,u_id,o_quantity,o_date)values(?,?,?,?)";
			preparedStatement = this.connection.prepareStatement(query);
			
			preparedStatement.setInt(1, model.getId());
			preparedStatement.setInt(2, model.getUid() );
			preparedStatement.setInt(3, model.getQuantity());
			preparedStatement.setString(4, model.getDate());
			preparedStatement.executeUpdate();
			result= true;
			
			//System.out.println("order inserted in db");
			} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}
	
	public List<Order>userOrder(int id)
	{
		List<Order>list = new ArrayList<>();
		try {
			query = "select * from orders where u_id=? order by orders.o_id desc";
			preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
			
				Order order = new Order();
				ProductDao productDao = new ProductDao(this.connection);
				int pId = resultSet.getInt("p_id");
				Product product=productDao.getSingleProduct(pId);
				order.setOrderId(resultSet.getInt("o_id"));
				order.setId(pId);
				order.setName(product.getName());
				order.setCategory(product.getCategory());
				order.setPrice(product.getPrice()*resultSet.getInt("o_quantity"));
				order.setDate(resultSet.getString("o_id"));
				list.add(order);
						
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return list;
	}
	
	
	
	public void cancelOrder(int id)
	{
		
		try {
			query = "delete from orders where o_id =?";
			preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			preparedStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		
	}
	
	
	
}
