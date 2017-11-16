package movieBookings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import Movie.DateTime;
import Movie.Movie;
import Movie.movieTimingAllocation;
import cinema.cinema;

/**
 * 
 * Handles the booking of the movie and payment
 * @author SSP2-Group8
 *
 */
public class movieBooking {
	
	/**
	 *  String to point filename where movie times are stored
	 */
	String movieTimes = "movietimes.txt";
	/**
	 *  String to point filename where movie details are stored
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
	 * String to point filename where booking history is stored
	 */
	String bookingHistory = "bookingHistory.txt";
	
	/**
	 * Creates an instance of the Movie class to call its methods
	 */
	Movie m = new Movie();
	
	/**
	 * Creates an instance of the cinema class to call its methods
	 */
	cinema c = new cinema();
	
	/**
	 * Creates an instance of the bookingHistory class to call its methods
	 */
	bookingHistory boh = new bookingHistory();
	
	/**
	 * Creates an instance of the movieTimingAllocation class to call its methods
	 */
	movieTimingAllocation mta = new movieTimingAllocation();
	
	/**
	 * Creates an instance of the Date Time class to call its methods 
	 */
	DateTime dt = new DateTime();
	
	/**
	 * new Scanner
	 */
	Scanner sc = new Scanner(System.in);
	
	public movieBooking() {}
	
	/**
	 * 
	 * Select movie time and seats based on the selected movie, cineplex and date
	 * The input movie name is derived from the movie selection by the user
	 * 
	 * The user can reserve the seats as well before making payment
	 * 
	 * @param movieSelection Movie Selection Number
	 * @param cineplex Location of Cineplex
	 * @param date Date of Movie
	 * @param uniqueID uniqueID of user
	 * @param dateString date of movie in String "YYYYMMDD"
	 */
	
	public void selectMovieTimeAndSeats(int movieSelection, String cineplex, String date, int uniqueID, String dateString) {
		String movieName = ""; String time = ""; String cinemaNo = ""; String movieType = ""; String movieStatus = "";
		int choice = 0, counter = 0;

		try {			
			ArrayList alm = m.readMovie(movieDetails);
			ArrayList alw = mta.readMovieTimingAllocation(movieAlloc) ;
			Movie mov = (Movie)alm.get(movieSelection-1);
			String typeOfDay = "";
			movieName = mov.getName();
			movieType = mov.getType();
			movieStatus = mov.getStatus();
			System.out.println("Cineplex: " + cineplex + " Movie: " + movieName + " Date: " + date);
			for (int i = 0; i < alw.size() ; i++) {

				movieTimingAllocation movtimalloc = (movieTimingAllocation)alw.get(i);	
				typeOfDay = movtimalloc.getTypeOfDay();
				String checkName = movtimalloc.getName();
				String checkDate = movtimalloc.getDate();
				String checkCineplex = movtimalloc.getCineplex();
				if ((movieName.equals(checkName)) && (cineplex.equals(checkCineplex)) && (date.equals(checkDate)) && (!(movieStatus.equals("End Of Screening")))){
					time = time + " " + movtimalloc.getTime();
					counter++;
				}
			}
			
			if (counter != 0) {
				System.out.println("Show Time :" + time);
				System.out.println("Select Show Time");
        			time = dt.addShowTime();      			
        			for (int i = 0; i < alw.size() ; i++) {
        				movieTimingAllocation movtimalloc = (movieTimingAllocation)alw.get(i);
        				if ((movieName.equals(movtimalloc.getName())) && (cineplex.equals(movtimalloc.getCineplex())) && (date.equals(movtimalloc.getDate()))
        						&& (time.equals(movtimalloc.getTime()))){       					
        					cinemaNo = movtimalloc.getCinema();      					
        					}
        				}
        			String [][] cinemaLayout = c.readCinema("movieTimings/" + movieName + "-" + cineplex + "-" + cinemaNo + "-" + date + "-" + time + ".txt");
        			c.getLayout(cinemaLayout);
        			System.out.println("\n(1) Select Seats to Book:");
        			System.out.println("(2) Select another time to check seats:");
        			System.out.println("(3) Exit to Menu:");
        			choice = sc.nextInt();
        			switch(choice) {
        			case 1:
        				System.out.println("Enter number of seats to book");
        				int count = sc.nextInt(); 
        				String seats = "";
        				for (int i = 0; i < count ; i++) {
                			c.assignSeat(movieSelection, uniqueID, cinemaLayout, date, time);
            				c.writeCinema("movieTimings/" + movieName + "-" + cineplex + "-" + cinemaNo + "-" + date + "-" + time + ".txt", cinemaLayout);
        				}
        				for (int i = 0; i < cinemaLayout.length; i++) {
        					for (int j = 0; j < cinemaLayout.length; j++) {
        						if (cinemaLayout[i][j].equals(Integer.toString(uniqueID))) {
        							String row = c.NumToChar(i);
        							String col = Integer.toString(j+1);
        							String seat = row+col;
        							seats = seats + " " + seat;	
        							}
        						}
        					}
        				makePayment(cinemaLayout, uniqueID, typeOfDay, movieType, dateString, cineplex, cinemaNo, time, movieName, date, seats);
        				counter = 0;
        				break;
        			case 2:
        				selectMovieTimeAndSeats(movieSelection, cineplex, date, uniqueID, dateString);
            			break;
            		case 3:
            			break; 
            			}
        			}			
			else if (counter == 0) {
				System.out.println("No timings found for this movie at this cineplex on this date, please try again");
			}
		}catch (IOException e) {
			System.out.println("IOException > " + e.getMessage());	
			}
	}
	
	/**
	 * 
	 * Payment method for user to settle payment after reservation of seats has been done
	 * Will check type of movie and type of day to determine ticket prices
	 * 
	 * @param cinemaLayout Layout of the selected cinema of the selected time
	 * @param userID uniqueID of the user
	 * @param typeOfDay Weekend/PublicHoliday/Friday or Monday-Thurs
	 * @param type type of Movie
	 * @param dateString date of movie in String "YYYYMMDD"
	 * @param cineplex Cineplex Location
	 * @param cinemaNo Cinema Number
	 * @param time Time of Movie
	 * @param movieName Movie Title
	 * @param date Date of Movie
	 * @param seats Seat number
	 */
		
	private void makePayment(String[][] cinemaLayout, int userID, String typeOfDay, String type, String dateString, String cineplex, String cinemaNo, String time, String movieName, String date, String seats){
		
		int count = 0, price = 7;
		
		for (int i = 0; i < cinemaLayout.length; i++) {
			for (int j = 0; j < cinemaLayout.length; j++) {
				if (cinemaLayout[i][j].equals(Integer.toString(userID)))
					count++;
				}
			}
		
		System.out.println("Total Tickets : " + count);
		System.out.println("Type of Movie : " + type);

		if (type.equals("3D") || type.equals("Blockbuster"))
			price = 8;
		if (typeOfDay.equals("PHWKND"))
			price += 2;
		System.out.println("Total Cost: " + count*price);
		System.out.println("Confirm with booking and make payment?");
		System.out.println("Enter y to proceed, any other key to cancel");
		char selection = 0;	
		selection = sc.next().charAt(0);
		switch(selection) {
		case 'y':
			String cinemaCode = "";
			String uniqueID = Integer.toString(userID);
			if (cineplex.equals("Tampines")) { cinemaCode = "002"; }
			else if (cineplex.equals("Kallang")) { cinemaCode = "001";}
			else if (cineplex.equals("Jurong")) { cinemaCode = "003";}
			for (int i = 0; i < cinemaLayout.length; i++) {
				for (int j = 0; j < cinemaLayout.length; j++) {
					if (cinemaLayout[i][j].equals(Integer.toString(userID))) 
						cinemaLayout[i][j] = cinemaCode + cinemaNo + dateString + time + uniqueID;						
					}
				}
			try {
				c.writeCinema("movieTimings/" + movieName + "-" + cineplex + "-" + cinemaNo + "-" + date + "-" + time + ".txt", cinemaLayout);
				ArrayList<bookingHistory> al = boh.readBookingHistory(bookingHistory) ;
				String cost = Integer.toString(count*price);
				bookingHistory p1 = new bookingHistory(Integer.toString(userID), cinemaCode + cinemaNo + dateString + time + uniqueID, movieName, cineplex, cinemaNo, date, time, seats, cost);
				al.add(p1);
				boh.saveBookingHistory(bookingHistory, al);
			} catch (IOException e) {
				System.out.println("IOException > " + e.getMessage());	
			}
			System.out.println("Payment is successful, thank you for your booking!");
			break;
		default:
			for (int i = 0; i < cinemaLayout.length; i++) {
				for (int j = 0; j < cinemaLayout.length; j++) {
					if (cinemaLayout[i][j].equals(Integer.toString(userID)))
						cinemaLayout[i][j] = "0";
				}
			}
			try {
				c.writeCinema("movieTimings/" + movieName + "-" + cineplex + "-" + cinemaNo + "-" + date + "-" + time + ".txt", cinemaLayout);
			} catch (IOException e) {
				System.out.println("IOException > " + e.getMessage());	
			}
			System.out.println("Booking is cancelled!");
			break;		
		}
	}

}
