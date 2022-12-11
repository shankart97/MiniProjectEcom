package com.group.x.ecommerce.miniproject;

import java.sql.*;
import java.util.Scanner;

public class LogIn_Page extends Product {
    // public void login(){
    //Project Statement 2 - Only Registered Users can buy products
    public void buyProduct() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Miniproject", "root", "root");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter UserName and Password");
        String userName1 = sc.next();
        String password1 = sc.next();
        if (userName1.equals("Admin") && password1.equals("Password")) {

            System.out.println("Welcome Admin");
            System.out.println("1. Check the quantity of product");
            System.out.println("2. Show entire registered User List");
            System.out.println("3. Check purchase details");
//            System.out.println("Which action you want to do ?");
            int n = sc.nextInt();

            switch (n) {
                case 1:
                    checkQuantity();
                    break;
                case 2:
                    Register_User ru = new Register_User();
                    ru.getRegisteredUsers();
                    break;

                case 3:
                    System.out.println("Enter Customer-Id ");
                    Scanner sc1 = new Scanner(System.in);
                    int cust_id = sc1.nextInt();
                    checkPurchaseDetails(cust_id);
                    break;
            }
        }
        else {
            PreparedStatement ps = con.prepareStatement("select cust_id from Customer_Details where userName=? and password=?");
            ps.setString(1, userName1);
            ps.setString(2, password1);
            ResultSet r = ps.executeQuery();
            if (r.next()) {
                int cust_id = r.getInt(1);
                System.out.println(cust_id);
                showProductList(cust_id);
            } else {
                System.out.println("UserName or  Password is incorrect ");
            }
        }

    }

}


