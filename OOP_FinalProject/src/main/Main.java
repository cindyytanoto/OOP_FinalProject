package main;

import utility.Helper;
import view.LoginPage;
import view.RegisterPage;

public class Main {
	
	Helper helper = new Helper();
	
	public Main() {
		int choice;
		
		do {
			System.out.println("Laper Ah");
			System.out.println("1. Login");
			System.out.println("2. Register");
			choice = helper.getInteger(">> ");
			
			if(choice == 1) {
				new LoginPage();
			} else if (choice == 2) {
				new RegisterPage();
			}
			
		} while (choice != 3);
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
