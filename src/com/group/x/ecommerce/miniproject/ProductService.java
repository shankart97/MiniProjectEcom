package com.group.x.ecommerce.miniproject;

import java.sql.SQLException;

public interface ProductService {
	public void checkQuantity()throws ClassNotFoundException, SQLException;
	public void checkPurchaseDetails(int cust_id)throws ClassNotFoundException, SQLException ;
	public void showProductList(int cust_id)throws ClassNotFoundException, SQLException ;
	public void addToCart(int cust_id)throws ClassNotFoundException, SQLException ;
}
