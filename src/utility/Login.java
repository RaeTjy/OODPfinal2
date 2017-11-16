package utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import mainUI.CinemaStaff;
import mainUI.UserApp;

/**
 * 
 * Login class
 * 
 * @author SSP2-Group8
 *
 */
public class Login {
	
	/**
	 * String pointing to the txt file where
	 * user details are contained in
	 */
	String userDetails = "userdetails.txt";
	/**
	 * Creates an instance of the UserAccount class
	 * to call its methods
	 */
	UserAccount uac = new UserAccount();
	/**
	 * Creates an instance of the AccountInfo class
	 * to call its methods
	 */
	AccountInfo ai = new AccountInfo();
	/**
	 * Creates an instance of the UserApp class
	 * to call its methods
	 */
	UserApp ua = new UserApp();
	/** 
	 * Creates an instance of the CinemaStaff class
	 * to call its methods
	 */
	CinemaStaff cs = new CinemaStaff();
	/**
	 * new Scanner
	 */
	Scanner sc1 = new Scanner(System.in);
	
	public Login() {}
	
	/**
	 * Login for user or administrator
	 * Checks with the user account database
	 * to ensure such a user exists
	 */
	public void logIn() {
		boolean checkUserPass = false;
		int uniqueID = 0;
		
		System.out.println("Enter Username : ");
		String username = sc1.next();	
		System.out.println("Enter Password : ");
		String password = sc1.next();
		
		if (username.equals("admin") && password.equals("admin123"))
				{
			cs.adminMenu();
				}
		else {
			try {
				ArrayList<UserAccount> al = ai.readUserAccount(userDetails);		
				for (int i = 0 ; i < al.size(); i++) {
					UserAccount uac = (UserAccount)al.get(i);
					String checkUser = uac.getUsername();
					String checkPass = uac.getPassword();
					if (checkUser.equals(username) && checkPass.equals(password)) { 
						checkUserPass = true;
						uniqueID = Integer.parseInt(uac.getUniqueID());
						}				
					else { checkUser = ""; checkPass = "";}
				}
				if (checkUserPass == true)
					ua.UserAppMenu(uniqueID);
				else {
					System.out.println("Invalid Username and Password! Please try again");
					System.out.println("");
				}
				
			}catch (IOException e) { System.out.println("IOException > " + e.getMessage());}
		}
				
	}

}
