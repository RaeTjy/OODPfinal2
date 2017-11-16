package cinema;

import java.util.ArrayList;
import java.util.Scanner;

import Movie.Movie;
import Movie.MovieRating;
import cinema.cinema;
import mainUI.CinemaStaff;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * Represents a cinema class involving the methods
 * with regards to the cinema
 * @author SSP2-Group8
 *
 */

public class cinema {
	
	/**
	 *  String to point filename where movie details
	 *  and ratings are stored
	 */
	private static String movieDetails = "movie.txt", movieRating = "movierating.txt";
	
	Scanner sc = new Scanner(System.in);	
	
	/**
	 * Create an instance of the Movie class to
	 * call the methods within
	 */
	
	Movie m = new Movie();
	
	/** 
	 * Create an instance of the MovieRating class
	 * to call the methods within
	 */
	MovieRating mr = new MovieRating();
	
	public cinema() {}
	
	/**
	 * 
	 * Creates a new cinema with the specified Movie Title, 
	 * Cineplex Location, Cinema Number, Date and Time of Movie
	 * 
	 * Upon creation of the cinema, the cinema will be written 
	 * to the txt file in the folder 'movieTimings' with the 
	 * following format
	 * movieName-cineplex-cinemaNo-date-time.txt
	 * 
	 * @param movieName Movie Title
	 * @param cineplex Cineplex Location
	 * @param cinemaNo Cinema Number
	 * @param date Date of Movie
	 * @param time Time of Movie
	 * @throws IOException
	 */

	public void newCinema(String movieName, String cineplex, String cinemaNo, String date, String time) throws IOException  {		
		
		String [][] cinema = {{"-1", "-1", "0", "0", "0", "0", "-1", "0", "0", "0"},
						   {"-1", "-1", "0", "0", "0", "0", "-1", "0", "0", "0"},
						   {"-1", "-1", "0", "0", "0", "0", "-1", "0", "0", "0"},
						   {"-1", "-1", "0", "0", "0", "0", "-1", "0", "0", "0"},
						   {"0", "0", "0", "0", "0", "0", "-1", "0", "0", "0"},
						   {"0", "0", "0", "0", "0", "0", "-1", "0", "0", "0"},
						   {"0", "0", "0", "0", "0", "0", "-1", "0", "0", "0"},
						   {"0", "0", "0", "0", "0", "0", "-1", "0", "0", "0"},
						   {"0", "0", "0", "0", "0", "0", "-1", "0", "0", "0"},
						   {"0", "0", "0", "0", "0", "0", "-1", "-1", "0", "0"}};
					   
		
		StringBuilder build = new StringBuilder();
		for(int k = 0; k < cinema.length; k++) {
			for(int l = 0; l < cinema.length; l++) {
				build.append(cinema[k][l]+"");
				if(l < cinema.length - 1)
			         build.append(" ");
				}
			build.append("\n");
		}		
		
		BufferedWriter writer = new BufferedWriter(new FileWriter("movieTimings/" + movieName + "-" + cineplex + "-" + cinemaNo + "-" + date + "-" + time + ".txt"));
					writer.write(build.toString());
					writer.close();
	}
	
	/**
	 * 
	 * Reads a specified cinema file that was
	 * previously created and returns back
	 * the layout of the cinema
	 * 
	 * @param filename Name of the cinema file
	 * @return cinema Cinema Layout as a 10x10 String Array
	 * @throws IOException
	 */
	

	public String[][] readCinema(String filename) throws IOException {
		String[][] cinema = new String[10][10];
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line = "";
		int row = 0;
		while((line = reader.readLine()) != null){
			String[] cols = line.split(" ");
			int col = 0;
			for(String  c : cols){
				cinema[row][col] = (c);
				col++;
				}
			row++;
			}
		
		reader.close();
		return cinema;

	}
	
	/**
	 * Writes to a specific cinema file that was
	 * previously created and returns back
	 * the layout of the cinema 
	 * 
	 * @param filename Name of the cinema file
	 * @param cinema New Layout of the cinema
	 * @throws IOException
	 */
	
	public void writeCinema(String filename, String [][] cinema) throws IOException {
		
		StringBuilder build = new StringBuilder();
		for(int k = 0; k < cinema.length; k++) {
			for(int l = 0; l < cinema.length; l++) {
				build.append(cinema[k][l]+"");
				if(l < cinema.length - 1)
			         build.append(" ");
				}
			build.append("\n");
			}
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		writer.write(build.toString());
		writer.close();
	}
	
	/**
	 * 
	 * Gets the layout of the cinema and
	 * shows the available seats to 
	 * the user for booking
	 * 
	 * @param cinema
	 */

	public void getLayout(String[][] cinema) {

			int z = 65;
			System.out.println("\nSeating arrangement: ");
			System.out.println("--------------------MOVIE SCREEN--------------------");
		
		// Loop and print empty seats
			for (int i = 0; i < cinema.length; i++) {
				System.out.print((char)z);
				z++;
				System.out.print(" ");
				for (int j = 0; j < cinema.length; j++) {
					if (cinema[i][j].equals("0")) {
						System.out.print("| 0 |");
					}
					else if (cinema[i][j].equals("-1")) {
						System.out.print("     ");
					}
					else
						System.out.print("| X |");
				}
				System.out.println();
			}
			System.out.print("  ");
			for (int y = 0; y < cinema.length; y++) {
				System.out.print("  ");
				System.out.print(y + 1);
				System.out.print("  ");
			}
			System.out.println("\n(0 refers to Seat Available, X refers to Seat Taken)");
		}	
	
	/**
	 * 
	 * This method allows the assigning of
	 * seats to a user for reservation 
	 * prior to payment
	 * 
	 * @param movieSelection The Movie selected by the User based on the choices provided
	 * @param userID The uniqueID of the User
	 * @param cinema The String Array of the layout of the selected cinema
	 * @param date Date of the movie
	 * @param time Time of the movie
	 */

	public void assignSeat(int movieSelection, int userID, String[][] cinema, String date, String time) {

		int seatID, rowId = 0; 
		char rowID;

		System.out.println("Assigning Seat ..");
		// Get Row ID
		System.out.print("Please Enter RowID: ");
		rowID = sc.next().charAt(0);
		rowId = charToNum(rowID);
		// Get Seat ID
		System.out.print("Please Enter SeatID: ");
		seatID = sc.nextInt();
		
		// Check if seat is free        
		for (int i = 0; i < cinema.length; i++) {
			for (int j = 0; j < cinema.length; j++) {
				if (i == rowId-1 && j == seatID-1) {
					if (cinema[i][j].equals("0")) {				
						// Seat is free, can assign to Customer
						try {
								ArrayList<Movie> al = m.readMovie(movieDetails) ;
								ArrayList<MovieRating> alw = mr.readMovieRating(movieRating);								
								Movie mov = (Movie)al.get(movieSelection-1);
								MovieRating movrat = (MovieRating)alw.get(movieSelection-1);
								String name = mov.getName(); 				 String oneStar = movrat.getOne();
								String rating = mov.getRating();				 String twoStar = movrat.getTwo();
								String type = mov.getType(); 				 String threeStar = movrat.getThree();
								String director = mov.getDirector();			 String fourStar = movrat.getFour();
								String cast = mov.getCast(); 				 String fiveStar = movrat.getFive();
								String synopsis = mov.getSynopsis();
								String status = mov.getStatus();
								String reviewRating = mov.getReviewRating();  
								String reviewCount = mov.getReviewCount();
								String totalSales = mov.getTotalSales();
								int totalSale = Integer.parseInt(totalSales);
								totalSale++;
								totalSales = Integer.toString(totalSale);
								MovieRating p2 = new MovieRating(name, oneStar, twoStar, threeStar, fourStar, fiveStar);
								Movie p1 = new Movie(name, rating, type, status, director, cast, synopsis, reviewRating, reviewCount, totalSales);
								al.add(p1); alw.add(p2);
								al.remove(movieSelection-1); alw.remove(movieSelection-1);
								m.saveMovie(movieDetails, al); mr.saveMovieRating(movieRating, alw);
							}catch (IOException e) {
								System.out.println("IOException > " + e.getMessage());
								}		
						
						cinema[i][j] = Integer.toString(userID);
						System.out.println("Seat Booked!");
						getLayout(cinema); 
						}
					else if (cinema[i][j].equals("-1")){
						System.out.println("Invalid Selection, please try again");
					}
					
					else 
						System.out.println("Seat is already taken, please try again");
						return;
				}
			}	
		}
	}
	
	/**
	 * Gives the user the option to
	 * select a Cineplex
	 * 
	 * Currently provided only 3 choices 
	 * but can be expanded if required
	 * 
	 * @return the location of the Cineplex in String
	 */
	
	public String selectCineplex() {
		String cineplex = ""; int choice = 0;
		System.out.println("Select Cineplex (1) Kallang (2) Tampines (3) Jurong");
		choice = sc.nextInt();
		switch(choice) {
		case 1:
			cineplex = "Kallang";
			break;
		case 2:
			cineplex = "Tampines";
			break;
		case 3:
			cineplex = "Jurong";
			break;
		}
		return cineplex;
	}
	
	/**
	 * 
	 * Converts char to int
	 * 
	 * @param Char the input character
	 * @return an int associated with the input character
	 */
	
	public int charToNum(char Char)
	{
		int newInt=-1;
		
		if (Char == 'a' || Char == 'A')
			newInt = 1;
		else if (Char == 'b' || Char == 'B')
			newInt = 2;
		else if (Char == 'c' || Char == 'c')
			newInt = 3;
		else if (Char == 'd' || Char == 'D')
			newInt = 4;
		else if (Char == 'e' || Char == 'E')
			newInt = 5;
		else if (Char == 'f' || Char == 'F')
			newInt = 6;
		else if (Char == 'g' || Char == 'G')
			newInt = 7;
		else if (Char == 'h' || Char == 'H')
			newInt = 8;
		else if (Char == 'i' || Char == 'I')
			newInt = 9;
		else if (Char == 'j' || Char == 'J')
			newInt = 10;
		
		return newInt;
	}
	
	/**
	 * 
	 * Converts int to char
	 * 
	 * @param Num the input number
	 * @return a String character associated with the input number
	 */
	
	public String NumToChar(int Num)
	{
		String newChar = "";
		
		if (Num == 0)
			newChar = "A";
		if (Num == 1)
			newChar = "B";
		if (Num == 2)
			newChar = "C";
		if (Num == 3)
			newChar = "D";
		if (Num == 4)
			newChar = "E";
		if (Num == 5)
			newChar = "F";
		if (Num == 6)
			newChar = "G";
		if (Num == 7)
			newChar = "H";
		if (Num == 8)
			newChar = "I";
		if (Num == 9)
			newChar = "J";
		
		return newChar;
	}
}

