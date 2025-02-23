package com.shoppingCart.model;

public class Cart extends Product {
	
//this model is create to implement add to cart for product functionality
	
	private int quantity;
	
	public Cart() {}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	
	
}
