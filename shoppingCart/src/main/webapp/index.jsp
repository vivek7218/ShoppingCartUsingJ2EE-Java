
<%@page import="com.shoppingCart.model.Cart"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.shoppingCart.model.Product"%>
<%@page import="java.util.List"%>
<%@page import="com.shoppingCart.Dao.ProductDao"%>
<%@page import="com.shoppingCart.model.User"%>
<%@page import="com.shoppingCart.connection.dbConnect"%>
<%@  page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
User auth = (User) request.getSession().getAttribute("auth");
if (auth != null) {
	request.setAttribute("auth", auth);
}

ProductDao productDao = new ProductDao(dbConnect.getConnection());
List<Product> products = productDao.getAllProducts();



ArrayList<Cart>cart_list = (ArrayList<Cart>)session.getAttribute("cart-list");

if(cart_list !=null)
{
	
	request.setAttribute("cart_list", cart_list);
}


%>



<!DOCTYPE html>
<html>
<head>

<%@ include file="/includes/head.jsp"%>
<title>Welcome to E-Shopping Cart</title>
</head>
<body>

	<%@ include file="/includes/navbar.jsp"%>

	
	<div class="container">
		<div class="card-header my-3">All Product</div>
		<div class="row">
		
		<%
		     if(!products.isEmpty())
		     {
		    	 for(Product p:products)
		    	 {%>
		    		 <div class="col-md-3 my-3">
						<div class="card w-100" style="width: 18rem;">
							<img class="card-img-top" src="product-image/<%= p.getImage() %>"  alt="Card image cap">
							<div class="card-body">
							
								<h5 class="card-title"><%= p.getName() %></h5>
								<h6 class="price">Price: $<%= p.getPrice() %></h6>
								<h6 class="category">Category:<%=p.getCategory() %></h6>
								<div class="mt-3 d-flex justify-content-between">

									<a  class="btn btn-dark"  href="add-to-cart?id=<%=p.getId()%>">Add to Cart</a>
									 <a href="order-now/quantity=1?id=<%=p.getId() %>" class="btn btn-primary">Buy Now</a>


								</div>
							</div>
						</div>


					</div>
		    		 
		    	<% }
		    	 
		     }
		     else{
		    	 out.println("there are no products");
		     }
		
		%>
			
		</div>
	</div>
	<%@ include file="includes/footer.jsp"%>
</body>
</html>