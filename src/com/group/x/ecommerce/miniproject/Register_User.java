package com.group.x.ecommerce.miniproject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Register_User {
   // Project Statement 1-Registration
    public void customer_Registration() throws SQLException, ClassNotFoundException {
        System.out.println("Welcome to Buy the Amazing Products...!");
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

    }
//Project Statement 2 - Only Registered Users can buy products
    public void buyProduct() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Miniproject", "root", "root");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter UserName and Password");
        String userName1 = sc.next();
        String password1 = sc.next();
        if(userName1.equals("Admin")&& password1.equals("Password")){

            System.out.println("Welcome Admin");
            System.out.println("1. Check the quantity of product");
            System.out.println("2. Show entire registered User List");
            System.out.println("3. Check purchase details");
            System.out.println("Which action you want to do");
            int n=sc.nextInt();

            switch(n){
                case 1: checkQuantity();
                        break;
                case 2: getRegisteredUsers();
                        break;

               case 3: System.out.println("Enter Customer-Id ");
                       Scanner sc1=new Scanner(System.in);
                       int cust_id=sc1.nextInt();
                       checkPurchaseDetails(cust_id);

                        break;


            }
        }
        else{
        PreparedStatement ps = con.prepareStatement("select cust_id from Customer_Details where userName=? and password=?");
        ps.setString(1, userName1);
        ps.setString(2, password1);
        ResultSet r = ps.executeQuery();
        if(r.next()){
            int cust_id=r.getInt(1);
            System.out.println(cust_id);
            showProductList(cust_id);
        }
        else{
            System.out.println("UserName or  Password is incorrect ");
        }}
        /*int flag=0;
        while (r.next()) {
            {if (userName1.equals(r.getString(1)) && password1.equals(r.getString(2))) {
                    System.out.println("valid user");
                    flag=1;
                   showProductList();
                    break;
                } else {
                    System.out.println("Invalid User");
                }
            }
        }
        if(flag==1){
            PreparedStatement ps2 = con.prepareStatement("select cust_id from Customer_Details where userName=userName1");
            ResultSet rs=ps2.executeQuery();
            while(rs.next()){
                int user_Id=rs.getInt(1);
                System.out.println("ID :"+rs.getInt(1));
            }
           PreparedStatement ps1 = con.prepareStatement("insert into Order_Details (userName) values(?)");
       ps1.setString(1,userName1);
        ps1.execute();
        }
   }*/
    }
//Project Statement - 4 Product List
    public void showProductList(int cust_id) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Miniproject", "root", "root");
        PreparedStatement ps = con.prepareStatement("select * from Product order by price asc ");
        System.out.println("PRODUCT LIST :");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            System.out.println("Product ID :" + rs.getInt(1) + "\t"
                    + " Product Description :" + rs.getString(2) + "\t"
                    + " Product Price :" + rs.getInt(3) + "\t"
                    + " Product Name :" + rs.getString(4) + "\t"
                    + " Product Quantity :" + rs.getInt(5) + "\n");
        }
        addToCart(cust_id);

    }
//Project Statement 3 and 5 - User can buy Multiple Products , Products selected and added to cart
    public void addToCart(int cust_id) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Miniproject", "root", "root");
        //ArrayList of products which customer want to buy
        ArrayList<Integer> al = new ArrayList<Integer>();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of products you want to buy ?");
        int count_products = sc.nextInt();
        System.out.println("Please enter product id for the products which you want to buy ?");
        for (int i = 1; i <= count_products; i++) {
            int id = sc.nextInt();
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
//        for(int i=0;i<3;i++) {
//            PreparedStatement ps = con.prepareStatement("insert into Order_Details (orderedHistory) values(?)");
//            ps.setInt(1, a[i]);
//            ps.execute();
//        }


        //Total Amount calculated -Project Statement 6
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Miniproject", "root", "root");
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
            while (rs.next()) {
                int currentQuantity = rs.getInt(1);
                int availableQuantity = currentQuantity - 1;
                currentQuantity = availableQuantity;
                PreparedStatement ps1 = con.prepareStatement("update Product set quantity=? where product_id=? ");
                ps1.setInt(1, currentQuantity);
                ps1.setInt(2, id);
                ps1.execute();
                System.out.println("Available Quantity of this product " + id + " is " + currentQuantity);
            }
        }
    }
     // check quantity -Project Statement 7
     public void checkQuantity() throws ClassNotFoundException, SQLException {
         Class.forName("com.mysql.cj.jdbc.Driver");
         Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Miniproject", "root", "root");
         System.out.println("Enter Product ID for Which you want to check quantity ?");
         Scanner sc=new Scanner(System.in);
         int id=sc.nextInt();
         PreparedStatement ps = con.prepareStatement("select product_id, name, quantity from Product where product_id=?");
         ps.setInt(1,id);
         ResultSet rs=ps.executeQuery();
         while(rs.next()){
             System.out.println("Product-ID :"+rs.getInt(1));
             System.out.println("Product-Name :"+rs.getString(2));
             System.out.println("Product-Quantity :"+rs.getInt(3));

         }
    }
        //Project Statement-8 -All registered Users
         public void getRegisteredUsers() throws ClassNotFoundException, SQLException {
             System.out.println("All Registered Users : ");
             Class.forName("com.mysql.cj.jdbc.Driver");
             Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Miniproject", "root", "root");
             PreparedStatement ps=con.prepareStatement("select cust_id,userName from Customer_Details");
             ResultSet rs=ps.executeQuery();
             while(rs.next()){
                 System.out.println("Customer ID :"+rs.getInt(1)+" Customer Name :"+rs.getString(2));
             }
    }

    public void checkPurchaseDetails(int cust_id) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Miniproject", "root", "root");
        PreparedStatement ps=con.prepareStatement("select o.order_id,p.name from Order_Details o,order_Products op, Product p  where o.cust_id=? and o.order_id=op.order_id and op.product_id=p.product_id");
        ps.setInt(1,cust_id);
        ResultSet rs=ps.executeQuery();
        while(rs.next()){
            System.out.println("Order Id :"+rs.getInt(1));
            System.out.println("Product_Name:"+rs.getString(2));
        }



    }
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Register_User ru = new Register_User();
        //ru.customer_Registration();
       // ru.showProductList();
        // ru.addedToCart();
        //ru.totalAmount();
        ru.buyProduct();
       // ru.checkQuantity();
        //ru.getRegisteredUsers();


    }
}
