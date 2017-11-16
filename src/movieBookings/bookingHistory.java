package movieBookings;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import mainUI.CinemaStaff;
import utility.readWrite;

/**
 * 
 * Read and Write Booking History
 * View Booking History
 * 
 * @author SSP2-Group8
 *
 */

public class bookingHistory implements Serializable {
	
	/**
	 * Unique ID of the User
	 */
	private String uID;
	
	/**
	 * Transaction ID of the booking
	 */
	private String tID;
	
	/**
	 * Movie Title
	 */
	private String movieName ;
	
	/**
	 * Cineplex Location
	 */
	private String cineplex;
	
	/**
	 * Cinema Number
	 */
	private String cinemaNo;
	
	/**
	 * Date of Movie
	 */
	private String date; 
	
	
	/**
	 * Time of Movie
	 */
	private String time;
	
	/**
	 * Cost of Movie Booking
	 */
	private String cost;
	
	/**
	 * Booked Seats
	 */
	private String seats;
	
	/**
	 * String to point filename where movie times are stored
	 */
	String movieTimes = "movietimes.txt";
	
	/**
	 * String to point filename where movie details are stored
	 */
	String movieDetails = "movie.txt"; 
	
	/**
	 * String to point filename where movie ratings are stored
	 */
	String movieRating = "movierating.txt";
	
	/**
	 * String to point filename where movie timing allocations are stored
	 */
	String movieAlloc = "movietimingalloc.txt";
	
	/**
	 * String to point filename where movie timing allocations are stored
	 */
	String bookingHistory = "bookingHistory.txt";
	
	/**
	 * Creates an instance of the readWrite class to call its methods
	 */
	readWrite rw = new readWrite();
	
	public bookingHistory() {}
	
	/**
	 * 
	 * Creates a new Booking History Entry with the following parameters
	 * @param u uniqueID of the User
	 * @param t Transaction ID of the booking
	 * @param mn Movie Title
	 * @param cn Location of the Cineplex
	 * @param cnum Cinema Number
	 * @param d Date of the Movie
	 * @param tim Time of the Movie
	 * @param s Booked Seats
	 * @param cos Cost of Booking
	 */

	public bookingHistory(String u, String t, String mn, String cn, String cnum, String d, String tim, String s, String cos )  {
		uID = u;
		tID = t;
		movieName = mn;
		cineplex = cn;
		cinemaNo = cnum;
		date = d;
		time = tim;
		seats = s;
		cost = cos;
	}
	public String getUID() { return uID ; }
	public String getTID() { return tID ; }
	public String getMovieName() { return movieName ; }
	public String getCineplex() {return cineplex ; }	
	public String getCinemaNo() { return cinemaNo; }
	public String getDate() { return date ; }
	public String getTime() { return time ; }
	public String getSeats() { return seats ; }
	public String getCost() { return cost ; }

	public void viewBookingHistory(int userID) {
		int count = 0;
		try {
			ArrayList al = readBookingHistory(bookingHistory);
			for (int i = 0 ; i < al.size() ; i++) {
				bookingHistory boh = (bookingHistory)al.get(i);
				String checkUID = boh.getUID();
				if (checkUID.equals(Integer.toString(userID))) {
					System.out.println("\nMovie Name : " + boh.getMovieName() + "\nCineplex : " + boh.getCineplex() + " Cinema No: "
							+ boh.getCinemaNo() + "\nDate : " + boh.getDate() + " Time : " + boh.getTime() + "\nSeats : " +
							boh.getSeats() + "\nTransaction ID : " + boh.getTID() );
					count++;
				}
			}
			if (count == 0)
				System.out.println("No Booking History Found");
		} catch (IOException e) {
			System.out.println("IOException > " + e.getMessage());	
		}
	}
	
	public ArrayList<bookingHistory> readBookingHistory(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList)rw.read(filename);
		ArrayList alr = new ArrayList() ;

      for (int i = 0 ; i < stringArray.size() ; i++) {
				String st = (String)stringArray.get(i);
				
				StringTokenizer star = new StringTokenizer(st , "|");	
				String UID = star.nextToken().trim();
				String TID = star.nextToken().trim();
				String movieName = star.nextToken().trim();	
				String cineplex = star.nextToken().trim();
				String cinemaNo = star.nextToken().trim();	
				String date = star.nextToken().trim();	
				String time = star.nextToken().trim();
				String seats = star.nextToken().trim();
				String cost = star.nextToken().trim();				
				bookingHistory boh = new bookingHistory(UID, TID, movieName, cineplex, cinemaNo, date, time, seats, cost);
				alr.add(boh) ;
			}
			return alr ;
	}

	public void saveBookingHistory(String filename, List al) throws IOException {

		List alw = new ArrayList() ;// 

      for (int i = 0 ; i < al.size() ; i++) {
				bookingHistory boh = (bookingHistory)al.get(i);
				StringBuilder st =  new StringBuilder() ;
				st.append(boh.getUID().trim());
				st.append("|");
				st.append(boh.getTID().trim());
				st.append("|");
				st.append(boh.getMovieName().trim());
				st.append("|");		
				st.append(boh.getCineplex().trim());
				st.append("|");
				st.append(boh.getCinemaNo().trim());
				st.append("|");
				st.append(boh.getDate().trim());
				st.append("|");
				st.append(boh.getTime().trim());
				st.append("|");
				st.append(boh.getSeats().trim().trim());
				st.append("|");
				st.append(boh.getCost().trim());
				alw.add(st.toString()) ;			
			}
			rw.write(filename,alw);
	}
}