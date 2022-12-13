package com.group.x.ecommerce.miniproject;

import java.sql.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Formatter;
public class ProductServiceImpl extends RuntimeException implements ProductService{
	public ProductServiceImpl() {
		
	}
	public ProductServiceImpl(String s1) {
		super(s1);
	}
	
	
    // check quantity -Project Statement 7
    public void checkQuantity() throws ClassNotFoundException, SQLException {
    	
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Miniproject", "root", "MysqlRoot@12");
        System.out.println("Enter Product ID for Which you want to check quantity ?");
        Scanner sc=new Scanner(System.in);
        int id=sc.nextInt();
        
        PreparedStatement ps = con.prepareStatement("select product_id, name, quantity from Product where product_id=?");
        ps.setInt(1,id);
        ResultSet rs=ps.executeQuery();
        if(id<1 || id>10) {
        	throw new ProductServiceImpl("Invalid Product id.Enter Valid Product id To Check Quantity.");
        }
        while(rs.next()){
            System.out.println("Product-ID :"+rs.getInt(1));
            System.out.println("Product-Name :"+rs.getString(2));
            System.out.println("Product-Quantity :"+rs.getInt(3));

        }
    }


	public void checkPurchaseDetails(int cust_id) throws ClassNotFoundException, SQLException {
    	
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Miniproject", "root", "MysqlRoot@12");
        PreparedStatement ps=con.prepareStatement("select o.order_id,p.name from Order_Details o,order_Products op, Product p  where o.cust_id=? and o.order_id=op.order_id and op.product_id=p.product_id");
        ps.setInt(1,cust_id);
        ResultSet rs=ps.executeQuery();
        while(rs.next()){
            System.out.println("Order Id :"+rs.getInt(1));
            System.out.println("Product_Name:"+rs.getString(2));
        }



    }
    //Project Statement - 4 Product List
    
    public void showProductList(int cust_id) throws ClassNotFoundException, SQLException {
    	
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Miniproject", "root", "MysqlRoot@12");
        PreparedStatement ps = con.prepareStatement("select * from Product order by price asc ");
        System.out.println("\t\t\t\t\t\t\t# PRODUCT LIST #");
        System.out.println("            ");
        System.out.println("******************************************************************************************************************************************");
        System.out.println("                ");
        ResultSet rs = ps.executeQuery();
        System.out.format("%15s%24s%21s%22s%43s","[Product ID]","[Product Name]","[Product Price]","[Product Quantity]","[Product Description]");
        System.out.println("   ");
        System.out.println("  ");
        
        while (rs.next()) {
            
        	System.out.format("->%7d%24s%22d%20d%50s", rs.getInt(1),rs.getString(4),rs.getInt(3),rs.getInt(5),rs.getString(2));
        	System.out.println("                                 ");
        	System.out.println();
        }        addToCart(cust_id);

    }


    //Project Statement 3 and 5 - User can buy Multiple Products , Products selected and added to cart
    
    
    public void addToCart(int cust_id) throws ClassNotFoundException, SQLException {
    	
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Miniproject", "root", "MysqlRoot@12");
        //ArrayList of products which customer want to buy
        
        ArrayList<Integer> al = new ArrayList<Integer>();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of products you want to buy ?");
        int count_products = sc.nextInt();
        System.out.println("Please enter product id for the products which you want to buy ?");
        for (int i = 1; i <= count_products; i++) {
            int id = sc.nextInt();
            if(id<1 || id>10) {
            	throw new ProductServiceImpl("Invalid Product id.Please Enter Valid Product id. ");
            }
            al.add(id);
        }


        PreparedStatement ps = con.prepareStatement("insert into Order_Details (cust_id) values(?)");
        ps.setInt(1,cust_id);
        ps.execute();
        PreparedStatement ps4 = con.prepareStatement("select order_id from Order_Details where cust_id=? order by when_created desc limit 1");
        ps4.setInt(1,cust_id);
        ResultSet r= ps4.executeQuery();
        while(r.next()) {
            int order_id = r.getInt(1);
            for (int i = 0; i < al.size(); i++) {
                PreparedStatement ps5 = con.prepareStatement("insert into order_Products (order_id,product_id)values(?,?)");
                ps5.setInt(1, order_id);
                ps5.setInt(2, al.get(i));
                ps5.execute();

            }
        }


        //Total Amount calculated -Project Statement 6  >>>>>>>>>>>>>>>>>>>
        
        int totalAmount = 0;
        for (int id : al) {
            PreparedStatement ps1 = con.prepareStatement("select price from Product where product_id=? ");
            ps1.setInt(1, id);
            ResultSet rs = ps1.executeQuery();
            while (rs.next()) {
                int price = rs.getInt(1);
                totalAmount = price + totalAmount;
            }
        }
        System.out.println("Total Amount of all products :" + totalAmount);


        //Available Quantity

        for (int id : al) {

            PreparedStatement ps2 = con.prepareStatement("select quantity from Product where product_id=? ");
            ps2.setInt(1, id);
            ResultSet rs = ps2.executeQuery();
            
            while (rs.next()) 
            {
                int currentQuantity = rs.getInt(1);
                int availableQuantity = currentQuantity - 1;
                currentQuantity = availableQuantity;
                PreparedStatement ps1 = con.prepareStatement("update Product set quantity=? where product_id=? ");
                ps1.setInt(1, currentQuantity);
                ps1.setInt(2, id);
                ps1.execute();
                System.out.println(" ");
                System.out.println("Available Quantity of this product " + id + " is " + currentQuantity);
            }
        }
        
        System.out.println("  ");
        System.out.println("1. To Continue Shopping");
        System.out.println("2. Exit");
        Scanner sc1=new Scanner(System.in);
        int n=sc1.nextInt();
        switch(n)
        {
            case 1: showProductList(cust_id);
                    break;
            case 2: System.exit(0);
                    break;
            
            default :
            	throw new ProductServiceImpl("Invalid Input");
        }
    }
}
