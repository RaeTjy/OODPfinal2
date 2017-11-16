package utility;

import java.io.Serializable;
/**
 * Represents a User Account with a specific username,
 * password, age, mobile and uniqueID
 * @author SS2-Group8
 * @version 1.0
 * @since 15/11/2017;
 */
public class UserAccount implements Serializable {
	/**
	 * The Username of the Account
	 */
	private String username ;
	/**
	 * The Password of the Account
	 */
	private String password;
	/**
	 * The age of the User
	 */
	private String age ;
	/**
	 * The mobile number of the User
	 */
	private String mobile;
	/**
	 * The uniqueID of the User
	 */
	private String uniqueID;
	
	public UserAccount() {}

	/**
	 * Creates a new user with the specific username,
	 * password, age, email and uniqueID
	 * @param u Username
	 * @param p Password
	 * @param a Age
	 * @param m Mobile Number
	 * @param uid Unique ID
	 */
	public UserAccount(String u, String p, String a, String m, String uid)  {
		username = u;
		password = p;
		age = a;
		mobile = m;
		uniqueID = uid;
	}
	/**
	 * Gets the Unique ID of the User
	 * @return UniqueID
	 */
	public String getUniqueID() { return uniqueID ; }
	/**
	 * Gets the Username of the User
	 * @return Username
	 */
	public String getUsername() { return username ; }
	/**
	 * Gets the Password
	 * @return Password
	 */
	public String getPassword() { return password ; }
	/**
	 * Gets the Age of the User
	 * @return Age
	 */
	public String getAge() { return age ; }
	/**
	 * Gets the Mobile Number of the User
	 * @return Mobile
	 */
	public String getMobile() { return mobile ;}

}