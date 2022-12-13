package com.group.x.ecommerce.miniproject;

import java.sql.*;
import java.util.Scanner;

public class Register_User {
    // Project Statement 1-Registration
    public void customer_Registration() throws SQLException, ClassNotFoundException {
        System.out.println("Welcome to E-Commerce Site");
        System.out.println(" ");
        System.out.println("First Register Yourself :");
        System.out.println(" ");
        Scanner sc = new Scanner(System.in);
        System.out.println("Please give below Registration details :");
        System.out.println("Enter userName : ");
        String userName = sc.nextLine();
        System.out.println("Enter Password");
        String password = sc.nextLine();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Miniproject", "root", "MysqlRoot@12");
        PreparedStatement ps = con.prepareStatement("select cust_id from Customer_Details where userName=? and password=?");
        ps.setString(1, userName);
        ps.setString(2, password);
        ResultSet r = ps.executeQuery();
        
        if (r.next()) {
            int cust_id = r.getInt(1);
            System.out.println("\t\t\t\t\t\t");
            System.out.println("Already Existing user");
            System.out.println("Please login to proceed further");
            System.out.println();
           LogIn_Page p = new LogIn_Page();
            p.buyProduct();
        }
            
            else {
            	
            	
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/Miniproject", "root", "MysqlRoot@12");
        PreparedStatement ps1 = con.prepareStatement("insert into Customer_Details(username,password) values (?,?)");
        ps1.setString(1, userName);
        ps1.setString(2, password);
        ps1.execute();
        System.out.println("Successfully Registered");
        
        Signing mm=new Signing();
        mm.signUpSignIN();
            }

    }


    //Project Statement-8 -All registered Users
    public void getRegisteredUsers() throws ClassNotFoundException, SQLException {
        System.out.println("All Registered Users : ");
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Miniproject", "root", "MysqlRoot@12");
        PreparedStatement ps = con.prepareStatement("select cust_id,userName from Customer_Details");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            System.out.println("Customer ID :" + rs.getInt(1) + " Customer Name :" + rs.getString(2));
        }
    }
}




