package mainUI;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import Movie.DateTime;
import Movie.Movie;
import Movie.MovieRating;
import Movie.MovieTimes;
import Movie.movieTimingAllocation;
import cinema.cinema;
import movieBookings.bookingHistory;
import movieBookings.movieBooking;
import utility.readWrite;

public class UserApp {

	String movieDetails = "movie.txt", movieRating = "movierating.txt", movieAlloc = "movietimingalloc.txt", movieTimes = "movietimes.txt";
	static Scanner sc = new Scanner(System.in);
	Movie m = new Movie();
	cinema c = new cinema();
	MovieTimes mt = new MovieTimes();
	MovieRating mr = new MovieRating();
	readWrite rw = new readWrite();
	bookingHistory boh = new bookingHistory();
	movieBooking mob = new movieBooking();
	String bookingHistory = "bookingHistory.txt";
	DateTime dt = new DateTime();
	int counter = 0;
	
	public void UserAppMenu(int uniqueID){
		int movieSelection = 0;
		int choice = 0;
			
		System.out.println("\nWelcome back!");
		
	 	while(choice!=8){
	 		System.out.println("\nUser Menu:");
            System.out.println("(1) Show current Movie List");
            System.out.println("(2) Show Top 5 Movies");
            System.out.println("(3) View Movie Info");
            System.out.println("(4) Show current Movie Timings");
            System.out.println("(5) Book Movie");
            System.out.println("(6) View Booking History");
            System.out.println("(7) Add Movie Review");
            System.out.println("(8) Exit");
            
            System.out.println("Enter the number of your choice: ");
            choice = sc.nextInt();
            
            switch (choice) {
            case 1:
            		m.showMovieList();
            		break;
            case 2:
            		System.out.println("Select (1) Top 5 Movies by Review OR (2) Top 5 Movies by Movie Sales");
            		System.out.println("Enter any other number to exit");
            		choice = sc.nextInt();
            		switch(choice) {
            		case 1:
            			showTopFiveRatedMovie();
            			break;
            		case 2:
            			showTopFiveMovieSales();
            			break;
            		default:
            			break;
            		}
            		break;
            case 3:           		
            		movieSelection = m.selectMovie();  
            		if (movieSelection > rw.checkArrayLength(movieDetails) || movieSelection == 0) {
            			System.out.println("Invalid Movie Selection, Please try again");
            			break;
    				}
            		m.showMovieInfo(movieSelection);
            		break;
            case 4:
            		mt.showMovieTimes();
            		break;
            case 5:
            		movieSelection = m.selectMovie();
            		if (movieSelection > rw.checkArrayLength(movieDetails) || movieSelection == 0) {
            			System.out.println("Invalid Movie Selection, Please try again");
            			break;
				}
            		sc.nextLine();
            		String cineplex = c.selectCineplex();
            		int day = dt.getDay();
            		int month = dt.getMonth();
            		int year = dt.getYear();   		
            		String date = dt.addShowDate(day, month, year);  
            		String days = Integer.toString(day);
            		String months = Integer.toString(month+1);
            		String years = Integer.toString(year);
            		String dateString = years + months + days;
            		mob.selectMovieTimeAndSeats(movieSelection, cineplex, date, uniqueID, dateString); 			
            		break;
            case 6:
            		boh.viewBookingHistory(uniqueID);
            		break;
            case 7:        
            		movieSelection = m.selectMovie();  
            		if (movieSelection > rw.checkArrayLength(movieDetails) || movieSelection == 0) {
            			System.out.println("Invalid Movie Selection, Please try again");
            			break;
				}
            		addMovieReview(movieSelection);
            		m.deleteMovie(movieSelection);
            		break;
            case 8:
            		break;          
            		}
            }
	 	}
	
	public void addMovieReview(int movieSelection) {
		System.out.println("Select Number of Stars for Movie Review (1-5 Stars):");
		System.out.println("1 Star for lowest rating - 5 Star for highest rating:");
		int stars = sc.nextInt();
		try {
			ArrayList al = m.readMovie(movieDetails);
			ArrayList alm = mr.readMovieRating(movieRating);
			Movie mov = (Movie)al.get(movieSelection-1);
			MovieRating movrat = (MovieRating)alm.get(movieSelection-1);
			
			double reviewRatings;
			String name = mov.getName(); 				 int oneStars = Integer.parseInt(movrat.getOne());
			String rating = mov.getRating();				 int twoStars = Integer.parseInt(movrat.getTwo());
			String type = mov.getType(); 				 int threeStars = Integer.parseInt(movrat.getThree());
			String status = mov.getStatus();				 int fourStars = Integer.parseInt(movrat.getFour());
			String director = mov.getDirector();			 int fiveStars = Integer.parseInt(movrat.getFive());
			String cast = mov.getCast(); 				
			String synopsis = mov.getSynopsis();
			String totalSales = mov.getTotalSales();
			int reviewCounts = Integer.parseInt(mov.getReviewCount());
					
			reviewCounts++;
			if (stars == 1) oneStars++;
			else if (stars == 2) twoStars++;
			else if (stars == 3) threeStars++;
			else if (stars == 4) fourStars++;
			else if (stars == 5) fiveStars++;

			reviewRatings = (oneStars + 2*twoStars + 3*threeStars + 4*fourStars + 5*fiveStars) / (double)(reviewCounts) ;
			
			String reviewRating = String.format("%.1f", reviewRatings);
			
			String oneStar = Integer.toString(oneStars);
			String twoStar = Integer.toString(twoStars);
			String threeStar = Integer.toString(threeStars);
			String fourStar = Integer.toString(fourStars);
			String fiveStar = Integer.toString(fiveStars);
			String reviewCount = Integer.toString(reviewCounts);
					
			Movie p1 = new Movie(name, rating, type, status, director, cast, synopsis, "NA", reviewCount, totalSales);
			Movie p2 = new Movie(name, rating, type, status, director, cast, synopsis, reviewRating, reviewCount, totalSales);
			MovieRating p3 = new MovieRating(name, oneStar, twoStar, threeStar, fourStar, fiveStar);
			
			if (reviewCounts == 0 || reviewCounts == 1)
			{
				al.add(p1); alm.add(p3);
				m.saveMovie(movieDetails, al); mr.saveMovieRating(movieRating, alm);				
			}
			else 
			{
				al.add(p2); alm.add(p3);
				m.saveMovie(movieDetails, al); mr.saveMovieRating(movieRating, alm);
			}
			System.out.println("Movie Review Added!");
		}catch (IOException e) {
			System.out.println("IOException > " + e.getMessage());
			}
	}
	
	public void showTopFiveRatedMovie() {
		
		String topFive[][] =  {{"NA","NA","NA","NA","NA"},
				{"NA","NA","NA","NA","NA"}};

		try{
			ArrayList al = m.readMovie(movieDetails);
			for (int i = 0 ; i < al.size() ; i++) {
				Movie mov = (Movie)al.get(i);
				String rating = mov.getReviewRating();
				if (rating.equals("NA"))
					continue;
				else if (Double.parseDouble(mov.getReviewRating()) > 0) {		
				
					Double ratings = Double.parseDouble(mov.getReviewRating());						
					if ((topFive[0][0].equals("NA")) && topFive[0][1].equals("NA") && (topFive[0][2].equals("NA")) && (topFive[0][3].equals("NA")) && (topFive[0][4].equals("NA"))) {
						topFive[0][0] = mov.getReviewRating();
						topFive[1][0] = mov.getName();
						continue;
					}	
					else if (topFive[0][1].equals("NA") && (topFive[0][2].equals("NA")) && (topFive[0][3].equals("NA")) && (topFive[0][4].equals("NA"))) {
						if (Double.parseDouble(topFive[0][0])> ratings) {
							topFive[0][1] = mov.getReviewRating();
							topFive[1][1] = mov.getName();
							}						
						else if (ratings > Double.parseDouble(topFive[0][0])) {
							topFive[0][1] = topFive[0][0];
							topFive[1][1] = topFive[1][0];
							topFive[0][0] = mov.getReviewRating();
							topFive[1][0] = mov.getName();
							}
						continue;
					}
					else if ((topFive[0][2].equals("NA")) && (topFive[0][3].equals("NA")) && (topFive[0][4].equals("NA"))) {
						if (Double.parseDouble(topFive[0][1]) > ratings) {
							topFive[0][2] = mov.getReviewRating();
							topFive[1][2] = mov.getName();
						}
						else if ((Double.parseDouble(topFive[0][0]) > ratings) && (ratings > Double.parseDouble(topFive[0][1]))) {
							topFive[0][2] = topFive[0][1];
							topFive[1][2] = topFive[1][1];
							topFive[0][1] = mov.getReviewRating();
							topFive[1][1] = mov.getName();
						}
						else if (ratings > Double.parseDouble(topFive[0][0])) {
							topFive[0][2] = topFive[0][1];
							topFive[1][2] = topFive[1][1];
							topFive[0][1] = topFive[0][0];
							topFive[1][1] = topFive[1][0];
							topFive[0][0] = mov.getReviewRating();
							topFive[1][0] = mov.getName();
						}		
						continue;
					}
					else if ((topFive[0][3].equals("NA")) && (topFive[0][4].equals("NA"))) {
						if (Double.parseDouble(topFive[0][2]) > ratings) {
							topFive[0][3] = mov.getReviewRating();
							topFive[1][3] = mov.getName();
						}
						else if ((Double.parseDouble(topFive[0][1]) > ratings) && (ratings > Double.parseDouble(topFive[0][2]))){
							topFive[0][3] = topFive[0][2];
							topFive[1][3] = topFive[1][2];
							topFive[0][2] = mov.getReviewRating();
							topFive[1][2] = mov.getName();
						}
						else if ((ratings > Double.parseDouble(topFive[0][1])) && (Double.parseDouble(topFive[0][0]) > ratings)) {
							topFive[0][3] = topFive[0][2];
							topFive[1][3] = topFive[1][2];
							topFive[0][2] = topFive[0][1];
							topFive[1][2] = topFive[1][1];
							topFive[0][1] = mov.getReviewRating();
							topFive[1][2] = mov.getName();
						}
						else if (ratings > Double.parseDouble(topFive[0][0])) {
							topFive[0][3] = topFive[0][2];
							topFive[1][3] = topFive[1][2];
							topFive[0][2] = topFive[0][1];
							topFive[1][2] = topFive[1][1];
							topFive[0][1] = topFive[0][0];
							topFive[1][1] = topFive[1][0];
							topFive[0][0] = mov.getReviewRating();
							topFive[1][0] = mov.getName();
							}
						continue;
					}
					else if (topFive[0][4].equals("NA")) {
						if (Double.parseDouble(topFive[0][3]) > ratings){
							topFive[0][4] = mov.getReviewRating();
							topFive[1][4] = mov.getName();
						}
						else if ((Double.parseDouble(topFive[0][2]) > ratings) && (ratings > Double.parseDouble(topFive[0][3]))) {
							topFive[0][4] = topFive[0][3];
							topFive[1][4] = topFive[1][3];
							topFive[0][3] = mov.getReviewRating();
							topFive[1][3] = mov.getName();
						}
						else if ((Double.parseDouble(topFive[0][1]) > ratings) && (ratings > Double.parseDouble(topFive[0][2]))){
							topFive[0][4] = topFive[0][3];
							topFive[1][4] = topFive[1][3];
							topFive[0][3] = topFive[0][2];
							topFive[1][3] = topFive[1][2];
							topFive[0][2] = mov.getReviewRating();
							topFive[1][2] = mov.getName();
						}
						else if ((Double.parseDouble(topFive[0][0]) > ratings) && (ratings > Double.parseDouble(topFive[0][1]))){
							topFive[0][4] = topFive[0][3];
							topFive[1][4] = topFive[1][3];
							topFive[0][3] = topFive[0][2];
							topFive[1][3] = topFive[1][2];
							topFive[0][2] = topFive[0][1];
							topFive[1][2] = topFive[1][1];
							topFive[0][1] = mov.getReviewRating();
							topFive[1][1] = mov.getName();
						}
						else if (ratings > Double.parseDouble(topFive[0][0])) {
							topFive[0][4] = topFive[0][3];
							topFive[1][4] = topFive[1][3];
							topFive[0][3] = topFive[0][2];
							topFive[1][3] = topFive[1][2];
							topFive[0][2] = topFive[0][1];
							topFive[1][2] = topFive[1][1];
							topFive[0][1] = topFive[0][0];
							topFive[1][1] = topFive[1][0];
							topFive[0][0] = mov.getReviewRating();
							topFive[1][0] = mov.getName();
						}
						continue;
					}
					else {
						if (ratings > Double.parseDouble(topFive[0][0])) {
							topFive[0][4] = topFive[0][3];
							topFive[1][4] = topFive[1][3];
							topFive[0][3] = topFive[0][2];
							topFive[1][3] = topFive[1][2];
							topFive[0][2] = topFive[0][1];
							topFive[1][2] = topFive[1][1];
							topFive[0][1] = topFive[0][0];
							topFive[1][1] = topFive[1][0];
							topFive[0][0] = mov.getReviewRating();
							topFive[1][0] = mov.getName();
						}
						else if ((Double.parseDouble(topFive[0][0]) > ratings) && (ratings > Double.parseDouble(topFive[0][1]))) {
							topFive[0][4] = topFive[0][3];
							topFive[1][4] = topFive[1][3];
							topFive[0][3] = topFive[0][2];
							topFive[1][3] = topFive[1][2];
							topFive[0][2] = topFive[0][1];
							topFive[1][2] = topFive[1][1];
							topFive[0][1] = mov.getReviewRating();
							topFive[1][1] = mov.getName();
						}
						else if ((Double.parseDouble(topFive[0][1]) > ratings) && (ratings > Double.parseDouble(topFive[0][2]))){
							topFive[0][4] = topFive[0][3];
							topFive[1][4] = topFive[1][3];
							topFive[0][3] = topFive[0][2];
							topFive[1][3] = topFive[1][2];
							topFive[0][2] = mov.getReviewRating();
							topFive[1][2] = mov.getName();
						}
						else if ((Double.parseDouble(topFive[0][2]) > ratings) && (ratings > Double.parseDouble(topFive[0][3]))) {
							topFive[0][4] = topFive[0][3];
							topFive[1][4] = topFive[1][3];
							topFive[0][3] = mov.getReviewRating();
							topFive[1][3] = mov.getName();
						}
						else if (Double.parseDouble(topFive[0][3]) > ratings){
							topFive[0][4] = mov.getReviewRating();
							topFive[1][4] = mov.getName();
							}
						else
							continue;
						continue;
						}
					}
				}
			}catch (IOException e) {
				System.out.println("IOException > " + e.getMessage());	
			}
		System.out.println("Top 5 Movies by Review Rating");
		System.out.println("(1) " + topFive[1][0]);
		System.out.println("(2) " + topFive[1][1]);
		System.out.println("(3) " + topFive[1][2]);
		System.out.println("(4) " + topFive[1][3]);
		System.out.println("(5) " + topFive[1][4]);
		
	}
	
	public void showTopFiveMovieSales() {
		
		String topFive[][] =  {{"NA","NA","NA","NA","NA"},
				{"NA","NA","NA","NA","NA"}};
				
		try{
			ArrayList al = m.readMovie(movieDetails);
			for (int i = 0 ; i < al.size() ; i++) {
				Movie mov = (Movie)al.get(i);
				String sale = mov.getTotalSales();
				if (sale.equals("NA"))
					continue;
				else if (Integer.parseInt(mov.getTotalSales()) > 0) {		
				
					int sales = Integer.parseInt(mov.getTotalSales());						
					if ((topFive[0][0].equals("NA")) && topFive[0][1].equals("NA") && (topFive[0][2].equals("NA")) && (topFive[0][3].equals("NA")) && (topFive[0][4].equals("NA"))) {
						topFive[0][0] = mov.getTotalSales();
						topFive[1][0] = mov.getName();
						continue;
					}	
					else if (topFive[0][1].equals("NA") && (topFive[0][2].equals("NA")) && (topFive[0][3].equals("NA")) && (topFive[0][4].equals("NA"))) {
						if (Integer.parseInt(topFive[0][0])> sales) {
							topFive[0][1] = mov.getTotalSales();
							topFive[1][1] = mov.getName();
							}						
						else if (sales > Integer.parseInt(topFive[0][0])) {
							topFive[0][1] = topFive[0][0];
							topFive[1][1] = topFive[1][0];
							topFive[0][0] = mov.getTotalSales();
							topFive[1][0] = mov.getName();
							}
						continue;
					}
					else if ((topFive[0][2].equals("NA")) && (topFive[0][3].equals("NA")) && (topFive[0][4].equals("NA"))) {
						if (Integer.parseInt(topFive[0][1]) > sales) {
							topFive[0][2] = mov.getTotalSales();
							topFive[1][2] = mov.getName();
						}
						else if ((Integer.parseInt(topFive[0][0]) > sales) && (sales > Integer.parseInt(topFive[0][1]))) {
							topFive[0][2] = topFive[0][1];
							topFive[1][2] = topFive[1][1];
							topFive[0][1] = mov.getTotalSales();
							topFive[1][1] = mov.getName();
						}
						else if (sales > Integer.parseInt(topFive[0][0])) {
							topFive[0][2] = topFive[0][1];
							topFive[1][2] = topFive[1][1];
							topFive[0][1] = topFive[0][0];
							topFive[1][1] = topFive[1][0];
							topFive[0][0] = mov.getTotalSales();
							topFive[1][0] = mov.getName();
						}		
						continue;
					}
					else if ((topFive[0][3].equals("NA")) && (topFive[0][4].equals("NA"))) {
						if (Integer.parseInt(topFive[0][2]) > sales) {
							topFive[0][3] = mov.getTotalSales();
							topFive[1][3] = mov.getName();
						}
						else if ((Integer.parseInt(topFive[0][1]) > sales) && (sales > Integer.parseInt(topFive[0][2]))){
							topFive[0][3] = topFive[0][2];
							topFive[1][3] = topFive[1][2];
							topFive[0][2] = mov.getTotalSales();
							topFive[1][2] = mov.getName();
						}
						else if ((sales > Integer.parseInt(topFive[0][1])) && (Integer.parseInt(topFive[0][0]) > sales)) {
							topFive[0][3] = topFive[0][2];
							topFive[1][3] = topFive[1][2];
							topFive[0][2] = topFive[0][1];
							topFive[1][2] = topFive[1][1];
							topFive[0][1] = mov.getTotalSales();
							topFive[1][2] = mov.getName();
						}
						else if (sales > Integer.parseInt(topFive[0][0])) {
							topFive[0][3] = topFive[0][2];
							topFive[1][3] = topFive[1][2];
							topFive[0][2] = topFive[0][1];
							topFive[1][2] = topFive[1][1];
							topFive[0][1] = topFive[0][0];
							topFive[1][1] = topFive[1][0];
							topFive[0][0] = mov.getTotalSales();
							topFive[1][0] = mov.getName();
							}
						continue;
					}
					else if (topFive[0][4].equals("NA")) {
						if (Integer.parseInt(topFive[0][3]) > sales){
							topFive[0][4] = mov.getTotalSales();
							topFive[1][4] = mov.getName();
						}
						else if ((Integer.parseInt(topFive[0][2]) > sales) && (sales > Integer.parseInt(topFive[0][3]))) {
							topFive[0][4] = topFive[0][3];
							topFive[1][4] = topFive[1][3];
							topFive[0][3] = mov.getTotalSales();
							topFive[1][3] = mov.getName();
						}
						else if ((Integer.parseInt(topFive[0][1]) > sales) && (sales > Integer.parseInt(topFive[0][2]))){
							topFive[0][4] = topFive[0][3];
							topFive[1][4] = topFive[1][3];
							topFive[0][3] = topFive[0][2];
							topFive[1][3] = topFive[1][2];
							topFive[0][2] = mov.getTotalSales();
							topFive[1][2] = mov.getName();
						}
						else if ((Integer.parseInt(topFive[0][0]) > sales) && (sales > Integer.parseInt(topFive[0][1]))){
							topFive[0][4] = topFive[0][3];
							topFive[1][4] = topFive[1][3];
							topFive[0][3] = topFive[0][2];
							topFive[1][3] = topFive[1][2];
							topFive[0][2] = topFive[0][1];
							topFive[1][2] = topFive[1][1];
							topFive[0][1] = mov.getTotalSales();
							topFive[1][1] = mov.getName();
						}
						else if (sales > Integer.parseInt(topFive[0][0])) {
							topFive[0][4] = topFive[0][3];
							topFive[1][4] = topFive[1][3];
							topFive[0][3] = topFive[0][2];
							topFive[1][3] = topFive[1][2];
							topFive[0][2] = topFive[0][1];
							topFive[1][2] = topFive[1][1];
							topFive[0][1] = topFive[0][0];
							topFive[1][1] = topFive[1][0];
							topFive[0][0] = mov.getTotalSales();
							topFive[1][0] = mov.getName();
						}
						continue;
					}
					else {
						if (sales > Integer.parseInt(topFive[0][0])) {
							topFive[0][4] = topFive[0][3];
							topFive[1][4] = topFive[1][3];
							topFive[0][3] = topFive[0][2];
							topFive[1][3] = topFive[1][2];
							topFive[0][2] = topFive[0][1];
							topFive[1][2] = topFive[1][1];
							topFive[0][1] = topFive[0][0];
							topFive[1][1] = topFive[1][0];
							topFive[0][0] = mov.getTotalSales();
							topFive[1][0] = mov.getName();
						}
						else if ((Integer.parseInt(topFive[0][0]) > sales) && (sales > Integer.parseInt(topFive[0][1]))) {
							topFive[0][4] = topFive[0][3];
							topFive[1][4] = topFive[1][3];
							topFive[0][3] = topFive[0][2];
							topFive[1][3] = topFive[1][2];
							topFive[0][2] = topFive[0][1];
							topFive[1][2] = topFive[1][1];
							topFive[0][1] = mov.getTotalSales();
							topFive[1][1] = mov.getName();
						}
						else if ((Integer.parseInt(topFive[0][1]) > sales) && (sales > Integer.parseInt(topFive[0][2]))){
							topFive[0][4] = topFive[0][3];
							topFive[1][4] = topFive[1][3];
							topFive[0][3] = topFive[0][2];
							topFive[1][3] = topFive[1][2];
							topFive[0][2] = mov.getTotalSales();
							topFive[1][2] = mov.getName();
						}
						else if ((Integer.parseInt(topFive[0][2]) > sales) && (sales > Integer.parseInt(topFive[0][3]))) {
							topFive[0][4] = topFive[0][3];
							topFive[1][4] = topFive[1][3];
							topFive[0][3] = mov.getTotalSales();
							topFive[1][3] = mov.getName();
						}
						else if (Integer.parseInt(topFive[0][3]) > sales){
							topFive[0][4] = mov.getTotalSales();
							topFive[1][4] = mov.getName();
							}
						else
							continue;
						continue;
						}
					}
				}
			}catch (IOException e) {
				System.out.println("IOException > " + e.getMessage());	
			}
		System.out.println("Top 5 Movies by Review Sales");
		System.out.println("(1) " + topFive[1][0]);
		System.out.println("(2) " + topFive[1][1]);
		System.out.println("(3) " + topFive[1][2]);
		System.out.println("(4) " + topFive[1][3]);
		System.out.println("(5) " + topFive[1][4]);
		
	}
	
	
}
