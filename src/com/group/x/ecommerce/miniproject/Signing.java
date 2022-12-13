package com.group.x.ecommerce.miniproject;

import java.sql.SQLException;
import java.util.Scanner;

public class Signing {
	LogIn_Page lp = new LogIn_Page();
	public void signUpSignIN() {
		System.out.println("1. Sign In");
		System.out.println("2. Sign Up");
		System.out.println("\t\t\t\t\t\t");
		Scanner sc = new Scanner(System.in);
		int choice = sc.nextInt();
		switch (choice) {

		case 1:
		
			try {
				lp.buyProduct();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			break;

		case 2:
			Register_User ru = new Register_User();
			try {
				ru.customer_Registration();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

	}

}

