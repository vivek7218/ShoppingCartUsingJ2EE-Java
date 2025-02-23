package com.shoppingCart.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.spec.PSource;

import com.mysql.cj.exceptions.RSAException;
import com.shoppingCart.model.Cart;
import com.shoppingCart.model.Order;
import com.shoppingCart.model.Product;

public class ProductDao {

	private Connection connection;
	private String query;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

	public ProductDao(Connection connection) {
		super();
		this.connection = connection;
	}

	
	
	public List<Product> getAllProducts() {
		List<Product> products = new ArrayList<Product>();
		try {
			query = "select * from products";
			preparedStatement = this.connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Product row = new Product();
				row.setId(resultSet.getInt("id"));
				row.setName(resultSet.getString("name"));
				row.setCategory(resultSet.getString("category"));
				row.setPrice(resultSet.getDouble("price"));
				row.setImage(resultSet.getString("image"));

				products.add(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return products;

	}
	
	
	
	
	

	// this method is used to Show All Cart products inside Cart Page
	public List<Cart> getCartProduct(ArrayList<Cart> cartList) {

		List<Cart> products = new ArrayList<Cart>();

		try {
			if (cartList.size() > 0) {
				for (Cart item : cartList) {

					query = "Select * from products where id=?";
					preparedStatement = this.connection.prepareStatement(query);
					preparedStatement.setInt(1, item.getId());
					resultSet = preparedStatement.executeQuery();
					while (resultSet.next()) {
						Cart row = new Cart();
						row.setId(resultSet.getInt("id"));
						row.setName(resultSet.getString("name"));
						row.setCategory(resultSet.getString("category"));
						row.setPrice(resultSet.getDouble("price")*item.getQuantity());
						row.setQuantity(item.getQuantity());

						products.add(row);

					}

				}
			}

		} catch (Exception e) {
			
               e.printStackTrace();
			System.out.println(e.getMessage());

		}

		return products;

	}
	
	
	
	
	
	public Product getSingleProduct(int id)
	{
		Product row  = null;
		try {
			query = "select * from products where id=?";
			preparedStatement= this.connection.prepareStatement(query);
			preparedStatement.setInt(1, id );
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				row = new Product();
				row.setId(resultSet.getInt("id"));
				row.setName(resultSet.getString("name"));
				row.setCategory(resultSet.getString("category"));
				row.setPrice(resultSet.getDouble("price"));
				row.setImage(resultSet.getString("image"));
				
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			
		}
		
		
		return row;
		
	}
	
	
	

	// method is created to calculate total price of cart items
	public double getTotalCartPrice(ArrayList<Cart> cartList) {
		double sum = 0;
		try {
			if (cartList.size() > 0) {
				for (Cart item : cartList) {
					query = "select price from products where id=?";
					preparedStatement = this.connection.prepareStatement(query);
					preparedStatement.setInt(1, item.getId());
					resultSet = preparedStatement.executeQuery();

					while (resultSet.next()) {
						sum = sum + resultSet.getDouble("price")*item.getQuantity();
					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return sum;
	}

}
