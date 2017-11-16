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
		
		while(choice!=3){
			System.out.println("-----------------------------------------------------------------------");
			System.out.println("Welcome to MOvie Booking and LIsting Management Application (MOBLIMA)");
			System.out.println("-----------------------------------------------------------------------");
			System.out.println("");
            System.out.println("(1) Log in");
            System.out.println("(2) Create account");
            System.out.println("(3) Exit");
            System.out.println("");
            System.out.println("Enter choice: ");
            choice = sc.nextInt();
            
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

	
