package com.group.x.ecommerce.miniproject;

import java.sql.*;
import java.util.Scanner;

public class Register_User {
    // Project Statement 1-Registration
    public void customer_Registration() throws SQLException, ClassNotFoundException {
        System.out.println("Welcome to E-Commerce Site");
        System.out.println("First Register Yourself :");
        Scanner sc = new Scanner(System.in);
        System.out.println("Please give below Registration details :");
        System.out.println("Enter userName : ");
        String userName = sc.nextLine();
        System.out.println("Enter Password");
        String password = sc.nextLine();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Miniproject", "root", "root");
        PreparedStatement ps = con.prepareStatement("insert into Customer_Details(username,password) values (?,?)");
        ps.setString(1, userName);
        ps.setString(2, password);
        ps.execute();
        System.out.println("Successfully Registered");
        Signing mm=new Signing();
        mm.signUpSignIN();


    }


    //Project Statement-8 -All registered Users
    public void getRegisteredUsers() throws ClassNotFoundException, SQLException {
        System.out.println("All Registered Users : ");
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Miniproject", "root", "root");
        PreparedStatement ps = con.prepareStatement("select cust_id,userName from Customer_Details");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            System.out.println("Customer ID :" + rs.getInt(1) + " Customer Name :" + rs.getString(2));
        }
    }
}




