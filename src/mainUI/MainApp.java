package mainUI;

import java.util.Scanner;

import utility.AccountInfo;
import utility.Login;


public class MainApp {
	
	Scanner sc1 = new Scanner(System.in);
	static AccountInfo ai = new AccountInfo();
	String userDetails = "userdetails.txt";
	boolean registerAccount = true;
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		int choice = 0;
		Login li = new Login();
		
		do {
			System.out.println("-----------------------------------------------------------------------");
			System.out.println("Welcome to MOvie Booking and LIsting Management Application (MOBLIMA)");
			System.out.println("-----------------------------------------------------------------------");
			System.out.println("");
         		System.out.println("(1) Log in");
			System.out.println("(2) Create account");
			System.out.println("(3) Exit");
			System.out.println("");
			System.out.println("Enter choice: ");
			
			try 
			{
				choice = sc.nextInt();
				if (choice != 1 && choice != 2 && choice != 3)
				{
					System.out.println("Invalid choice!");
				}
			}
			catch (InputMismatchException e)
			{
				System.out.println("Please enter a choice from 1 - 3");
				sc.nextLine();
			}
			
			switch(choice) {
            		case 1:
            			li.logIn();
            			break;
            		case 2:
            			ai.createAccount();
            			break;
            		case 3:
            			System.out.println("Goodbye!");
            			break;
			}
		}
	}
}

	
