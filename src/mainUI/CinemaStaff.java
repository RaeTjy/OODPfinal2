package mainUI;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

import Movie.Movie;
import Movie.MovieTimes;
import Movie.DateTime;
import Movie.MovieRating;
import Movie.movieTimingAllocation;
import cinema.cinema;
import utility.readWrite;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class CinemaStaff {
	
	boolean daySelection;
	String movieTimes = "movietimes.txt";
	String movieDetails = "movie.txt"; 
	String movieRating = "movierating.txt";
	String movieAlloc = "movietimingalloc.txt";
	String bookingHistory = "bookingHistory.txt";
	
	Scanner sc = new Scanner(System.in);
	DateTime dt= new DateTime();
	Random rand = new Random();
	Movie m = new Movie();
	MovieTimes mt = new MovieTimes();
	movieTimingAllocation mta = new movieTimingAllocation();
	readWrite rw = new readWrite();
	cinema c = new cinema();

	public void adminMenu()  {
		int choice = 0, cinemaNo = 0;
		char choice2 = 'z';
		int movieSelection;
		String movieStatus = "", movieType = "", movieName = "", listOfTime = "";
	    
	    	while(choice!=7){
	    			System.out.println("\nAdmin Menu");
	            System.out.println("(1) Show current Movie List");
	            System.out.println("(2) Show current Movie Timings");
	            System.out.println("(3) Create new Movie Listing");
	            System.out.println("(4) Delete Movie Listing");
	            System.out.println("(5) Update Movie Status");
	            System.out.println("(6) Settings");
	            System.out.println("(7) Exit");
	            
	            System.out.println("Enter the number of your choice: ");
	            choice = sc.nextInt();
	            
				switch(choice){
	            case 1:
	            		m.showMovieList();
	            		break;
	            case 2:         
	            		mt.showMovieTimes();	      
	            		break;
	            case 3:	         
	            		m.newMovie();
	            		break;   
	            case 4:
	            		movieSelection = m.selectMovie();  
	            		if (movieSelection > rw.checkArrayLength(movieDetails) || movieSelection == 0) {
	            			System.out.println("Invalid Movie Selection, Please try again");
	            			break;
	            			}
	            		mt.deleteMovieTimes(movieSelection);
	            		m.deleteMovie(movieSelection);
	            		System.out.println("Movie Deleted");
	            		break;
	            case 5:
	            		movieSelection = m.selectMovie();  
	            		if (movieSelection > rw.checkArrayLength(movieDetails) || movieSelection == 0) {
	            			System.out.println("Invalid Movie Selection, Please try again");
	            			break;
            				}
	            		System.out.println("New Movie Status: (1) Preview OR (2) Now Showing OR (3) End Of Screening");
	            		choice = sc.nextInt();
	            		switch(choice) {
		            	case 1:
		            		movieStatus = "Preview";
		            		break;
		            	case 2:
		            		movieStatus = "Now Showing";
		            		break;
		            	case 3:
		            		movieStatus = "End Of Screening";
		            		mt.deleteMovieTimes(movieSelection);
		            		break;
		            		}
	            		m.modifyMovieStatus(movieSelection, movieStatus);
	            		m.deleteMovie(movieSelection);
	            		m.showMovieList();
	            		break;
	            case 6:	   
	            		while(choice2!='c'){
	            			System.out.println("(a) Adjust day (Weekend/PH/Fri OR Mon to Thurs)");
	            			System.out.println("(b) Add Showtimes");	
	            			System.out.println("(c) Back to Main Menu");	
	        				
	            			System.out.println("Enter the number of your choice: ");
	            			choice2 = sc.next().charAt(0);
	                    
	                    switch(choice2) {
	                    case 'a':
	                			currentDaySettings();
	                			adjustDay();
	                			System.out.println("");
	                			currentDaySettings();
	                			break;	                
	                    case 'b':	                    	
	                    		mt.newMovieTimes();
	                    		listOfTime = "";
	                			break;
	                    case 'c':	                 
	                			break;
	                			}
	            		}	
	            		choice2 = 'z';
	            		break;
	            case 7:
	      	     	System.out.println("Logging off");
	            		break;
	            }
	    	}
	}
	
            	
	public boolean currentDaySettings() {
		if (daySelection == true)
			System.out.println("Current Day Selection: Mon-Thurs\n");
		else
			System.out.println("Current Day Selection: Weekend/PH/Fri\n");
		return daySelection;
    }
	
	private void adjustDay() {
		if (daySelection == true) {
			System.out.println("Adjusting Day Selection to Weekend/PH/Fri");
			daySelection = false;
			}
		else {
			System.out.println("Adjusting Day Selection to Mon-Thurs");
			daySelection = true;
			}
		System.out.println("Adjustment done");
    }
	
	
	
	
	
	
		
}
