package utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Scanner;

import utility.readWrite;

/**
 * 
 * Account Information of a Movie-Goer
 * @author SSP2-Group8
 *
 */
public class AccountInfo {
	
	/**
	 * String pointed to the txt file where
	 * user details are contained
	 */
	String userDetails = "userdetails.txt";

	/**
	 * New Scanner
	 */
	Scanner sc = new Scanner(System.in);
	
	/**
	 * Creates an instance of the 
	 * readWrite class to call its methods
	 */
	readWrite rw = new readWrite();
	
	public AccountInfo() {}	
	
	/**
	 *  Creates an user account and checks 
	 *  for duplicates before writing to 
	 *  the user account database
	 */
	public void createAccount(){
		
		boolean registerAccount = false;
		
		System.out.println("Enter Username :");
		String username = sc.next();
		System.out.println("Enter Password :");
		String password = sc.next();
		System.out.println("Enter Age:");
		String age = sc.next();
		System.out.println("Enter Mobile Number:");
		String mobile = sc.next();
			
		try {
			ArrayList<UserAccount> alw = readUserAccount(userDetails) ;
			ArrayList al = (ArrayList)rw.read(userDetails);
			for (int i = 0 ; i < al.size() ; i++) {
				UserAccount uac = (UserAccount)alw.get(i);
				String checkUser = "";	
				checkUser = uac.getUsername();
				if (checkUser.equals(username) || username.equals("admin")) {
					System.out.println("Username is already in use, please try another username");
					registerAccount = false;
					break;
					}
				else { registerAccount = true; }
				}
			if (registerAccount == true) {
				UserAccount p1 = new UserAccount(username, password, age, mobile, Integer.toString(al.size()+1));
				alw.add(p1);
				saveUserAccount(userDetails, alw);
				System.out.println("Successfully Registered ");	
				}
			}catch (IOException e){
				System.out.println("IOException > " + e.getMessage());
				}
		
	    }
	
	/**
	 * Reads the user account database and returns 
	 * the information as an ArrayList with
	 * the following information
	 * Username, password, age, mobile and UniqueID
	 * 
	 * @param filename file where user account information is stored 
	 * @return StringArray of the User Details
	 * @throws IOException
	 */
	
	public ArrayList<UserAccount> readUserAccount(String filename) throws IOException {
		
		ArrayList stringArray = (ArrayList)rw.read(filename);
		ArrayList alr = new ArrayList() ;

        for (int i = 0 ; i < stringArray.size() ; i++) {
				String st = (String)stringArray.get(i);
				
				StringTokenizer star = new StringTokenizer(st , "|");	
				String  username = star.nextToken();	
				String  password = star.nextToken().trim();
				String  age = star.nextToken().trim();
				String  mobile = star.nextToken().trim(); 
				String  uniqueID = star.nextToken().trim();
				UserAccount uac = new UserAccount(username, password, age, mobile, uniqueID);
				alr.add(uac) ;
			}
			return alr ;
	}
	
	/**
	 * 
	 * Saves the user account database to file by 
	 * reading a List with the following information
	 * Username, password, age, mobile and UniqueID
	 * 
	 * @param filename file where user account information is stored
	 * @param al List containing user information
	 * @throws IOException
	 */

	public void saveUserAccount(String filename, List al) throws IOException {
		List alw = new ArrayList() ;

        for (int i = 0 ; i < al.size() ; i++) {
				UserAccount uac = (UserAccount)al.get(i);
				StringBuilder st =  new StringBuilder() ;
				st.append(uac.getUsername().trim());
				st.append("|");
				st.append(uac.getPassword().trim());
				st.append("|");
				st.append(uac.getAge().trim());
				st.append("|");
				st.append(uac.getMobile().trim());
				st.append("|");
				st.append(uac.getUniqueID().trim());
				alw.add(st.toString()) ;
			}
			rw.write(filename,alw);
		}

}
