package Movie;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Random;

import cinema.cinema;
import utility.readWrite;

/**
 * 
 * Represents Movie Times for an individual Movie Name,
 * Cineplex, Date and Time
 * @author SS2-Group8
 * 
 */
public class MovieTimes implements Serializable {
	/**
	 * The name of the Movie 
	 */
	private String name;
	/**
	 * The location of the Cineplex
	 */
	private String cineplex;
	/**
	 * The Date of the Movie
	 */
	private String date ;
	/**
	 * The Time of the Movie
	 */
	private String time;
	
	String movieTimes = "movietimes.txt";
	String movieDetails = "movie.txt"; 
	String movieRating = "movierating.txt";
	String movieAlloc = "movietimingalloc.txt";
	
	Movie m = new Movie();
	movieTimingAllocation mta = new movieTimingAllocation();
	DateTime dt = new DateTime();
	readWrite rw = new readWrite();
	cinema c = new cinema();
	Random rand = new Random();
	Scanner sc = new Scanner(System.in);
	

	public MovieTimes() {}
	/**
	 * Creates a new Movie Times with the given name, 
	 * cineplex, date and time 
	 * @param n Movie Title
	 * @param c Cineplex
	 * @param d Date
	 * @param t Time
	 */
	public MovieTimes(String n, String c, String d, String t)  {
		name = n;
		cineplex = c;
		date = d ;
		time = t ;
	}
	
	/**
	 * Gets the name of the Movie
	 * @return Movie Title
	 */
	public String getName() { return name ; }
	/**
	 * Gets the location of the Cineplex
	 * @return Cineplex
	 */
	public String getCineplex() {return cineplex ; }
	/**
	 * Gets the Date of the Movie
	 * @return Movie Time
	 */
	public String getDate() { return date ; }
	/**
	 * Gets the Time of the Movie
	 * @return Time
	 */
	public String getTime() { return time ; }
	
	public ArrayList<MovieTimes> readMovieTimes(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList)rw.read(filename);
		ArrayList alr = new ArrayList() ;

      for (int i = 0 ; i < stringArray.size() ; i++) {
				String st = (String)stringArray.get(i);
				
				StringTokenizer star = new StringTokenizer(st , "|");	
				String name = star.nextToken().trim();
				String cineplex = star.nextToken().trim();
				String  date = star.nextToken().trim();				
				String  time = star.nextToken().trim();	
				MovieTimes movtim = new MovieTimes(name, cineplex, date, time);
				alr.add(movtim) ;
			}
			return alr ;
	}

	public void saveMovieTimes(String filename, List al) throws IOException {
		List alw = new ArrayList() ;// to store MovieTimes data

      for (int i = 0 ; i < al.size() ; i++) {
				MovieTimes movtim = (MovieTimes)al.get(i);
				StringBuilder st =  new StringBuilder() ;
				st.append(movtim.getName().trim());
				st.append("|");
				st.append(movtim.getCineplex().trim());
				st.append("|");
				st.append(movtim.getDate().trim());
				st.append("|");
				st.append(movtim.getTime().trim());
				alw.add(st.toString()) ;
			}
			rw.write(filename,alw);
	}
	
	public void newMovieTimes(){
		
		int cinemaNo;
		String movieName = "", movieType = "", movieStatus = "", listOfTime = "";

		sc.nextLine();
		String cineplex = c.selectCineplex();            
		int movieSelection = m.selectMovie();  
			if (movieSelection > rw.checkArrayLength(movieDetails) || movieSelection == 0) {
				System.out.println("Invalid Movie Selection, Please try again");
				return;
				} 
		try { 
			ArrayList<Movie> alm = m.readMovie(movieDetails) ;
			Movie mov = (Movie)alm.get(movieSelection-1);
			movieName = mov.getName();
			movieType = mov.getType();
			movieStatus = mov.getStatus();
		}catch (IOException e) {
			System.out.println("IOException > " + e.getMessage());
		}
		if (movieStatus.equals("End Of Screening")) {
			System.out.println("Error! Movie is already End of Screening!");
			return;
		}             			
		if (movieType.equals("3D"))	                    			
			cinemaNo = 1;	             
		else
			cinemaNo = rand.nextInt(1)+2;
		int day = dt.getDay();
		int month = dt.getMonth();
		int year = dt.getYear();
		String date = dt.addShowDate(day, month, year);
		sc.nextLine();
		int counter = 0;
		System.out.println("Enter no. of timings:");
		counter= sc.nextInt();
		System.out.println("Enter Timings,");
		for (int i=0; i < counter ; i++) {
			boolean clash = false;	                    			
			String time = dt.addShowTime();
			int times = Integer.parseInt(time);
			try {
    			ArrayList<movieTimingAllocation> alm = mta.readMovieTimingAllocation(movieAlloc);	 
    			for (int j = 0; j < alm.size(); j++) {
    				movieTimingAllocation movtimalloc = (movieTimingAllocation)alm.get(j);
    				String checkDate = movtimalloc.getDate();
    				String checkCineplex = movtimalloc.getCineplex();
    				int checkCinema = Integer.parseInt(movtimalloc.getCinema());
   				int checkTime = Integer.parseInt(movtimalloc.getTime());
   					
   				if ((cinemaNo == 1) && (checkDate.equals(date)) && (checkCineplex.equals(cineplex)) && (checkCinema == 1)
       					&& ((times >= checkTime) && (checkTime+229 >= times))) {
   					clash = true;
       				break;
       				}	   
   				
					else if ((cinemaNo == 2) && (checkDate.equals(date)) && (checkCineplex.equals(cineplex)) && (checkCinema == cinemaNo)
							&& ((times >= checkTime) && (checkTime+229 >= times))){
						cinemaNo++;
   					if ((cinemaNo == 3) && (checkDate.equals(date)) && (checkCineplex.equals(cineplex)) && (checkCinema == cinemaNo)
       						&& ((times >= checkTime) && (checkTime+229 >= times))) {		                   							
   						clash = true;
   						break;
   						}	                   					
   					}
					else if ((cinemaNo == 3) && (checkDate.equals(date)) && (checkCineplex.equals(cineplex)) && (checkCinema == cinemaNo)
   						&& ((times >= checkTime) && (checkTime+229 >= times))) {
						clash = true;
						break;
						}
   				}		                   							              		                    			
    			if (clash == false) {
    				String typeOfDay = "";
    				System.out.println("Is " + date + " a Public Holiday or Friday/Weekend? \nEnter Y to confirm, any other letters if day is not Public Holiday or Friday/Weekend");
    				char choice1 = sc.next().charAt(0);
    				if (choice1 == 'y' || choice1 == 'Y')
    					typeOfDay = "PHWKND";
    				else
    					typeOfDay = "NOPHWKND";
    				listOfTime = listOfTime + " " + time;
    				String cinemaNum = Integer.toString(cinemaNo);
					movieTimingAllocation p1 = new movieTimingAllocation(movieName, cineplex, cinemaNum, date, time, typeOfDay);
					alm.add(p1);
					mta.saveMovieTimingAllocation(movieAlloc, alm);
					System.out.println("Movie timing " + time + " has been added for " + cineplex + " on "
								+ date);
					c.newCinema(movieName, cineplex, cinemaNum, date, time);	                   						                   					
					
    			}	
    			else if (clash == true) {
   					System.out.println("The timing " + time + " clashes with another movie");    
   					clash = false;
   				}
    			
    			}catch (IOException e) {
			System.out.println("IOException > " + e.getMessage());
			}
		}
		
		try {
			ArrayList<MovieTimes> al = readMovieTimes(movieTimes) ;
			MovieTimes p1 = new MovieTimes(name, cineplex, date, time);
			al.add(p1);
			saveMovieTimes(movieTimes, al);
			}catch (IOException e) {
				System.out.println("IOException > " + e.getMessage());
				}
    }
	
	public void showMovieTimes() {
		int choice2 = 0;
		Scanner sc = new Scanner(System.in);
		int movieCounter = 0;

		String cineplex = c.selectCineplex();
		int day = dt.getDay();
		int month = dt.getMonth();
		int year = dt.getYear();
		String date = dt.addShowDate(day, month, year);
		System.out.println("View Movie Times by (1) By Location and Date OR (2) By Location and Date And Movie");
		choice2 = sc.nextInt();
		switch(choice2) {
		case 1:
			System.out.println("");
			try {
				ArrayList<MovieTimes> alw = readMovieTimes(movieTimes) ;
				ArrayList al = (ArrayList)rw.read(movieTimes);
				System.out.println("Cineplex: " + cineplex + " Date: " + date);
				for (int i = 0 ; i < al.size() ; i++) {
					MovieTimes movtim = (MovieTimes)alw.get(i);
					String checkCineplex = "";
					String checkDate = "";
					checkDate = movtim.getDate();				
					checkCineplex = movtim.getCineplex();				
					if (checkCineplex.equals(cineplex) && checkDate.equals(date)) {
						System.out.println("Name: " + movtim.getName() + " Time: " + movtim.getTime());
						movieCounter++;
						}
					}
				if (movieCounter == 0)
					System.out.println("No movie timings found at this Cineplex. Please try another location");
				} catch (IOException e) {
					System.out.println("IOException > " + e.getMessage());
				}
			break;
		case 2:
			System.out.println("Select Movie");
			m.showMovieList();
			int movieSelection = sc.nextInt();
			System.out.println("");
			try {
				ArrayList<MovieTimes> alw = readMovieTimes(movieTimes) ;
				ArrayList al = (ArrayList)rw.read(movieTimes);
				ArrayList<Movie> alm = m.readMovie(movieDetails) ;
				Movie mov = (Movie)alm.get(movieSelection-1);
				String name = mov.getName();
				System.out.println("Cineplex: " + cineplex + " Movie: " + name + " Date: " + date);
				for (int i = 0 ; i < al.size() ; i++) {
					MovieTimes movtim = (MovieTimes)alw.get(i);
					String checkCineplex = "";
					String checkName = "";
					String checkDate = "";
					checkName = movtim.getName();
					checkCineplex = movtim.getCineplex();
					checkDate = movtim.getDate();
					if (checkCineplex.equals(cineplex) && checkName.equals(name) && checkDate.equals(date)) {
						System.out.println("Time: " + movtim.getTime());
						movieCounter++;
						}
					}
				if (movieCounter == 0)
					System.out.println("No movie timings found at this Cineplex. Please try another location");
				} catch (IOException e) {
					System.out.println("IOException > " + e.getMessage());
					}
			break;
    			}
		}
	
	public void deleteMovieTimes(int movieSelection) {
			
		String movieName = "";
			try {			
				ArrayList<Movie> alm = m.readMovie(movieDetails) ;
				Movie mov = (Movie)alm.get(movieSelection-1);
				movieName = mov.getName();
				ArrayList al = (ArrayList)rw.read(movieTimes);
				ArrayList<MovieTimes> alw = readMovieTimes(movieTimes);
				ArrayList ala = (ArrayList)rw.read(movieAlloc);
				ArrayList<movieTimingAllocation> alb = mta.readMovieTimingAllocation(movieAlloc);
				for (int i = al.size()-1 ; i > 0 ; i--) {
					String st1 = (String)al.get(i);
					StringTokenizer star1 = new StringTokenizer(st1 , "|");		
					if (star1.nextToken().trim().equals(movieName)) {
						alw.remove(i);
						saveMovieTimes(movieTimes, alw);
						}
					}
				for (int i = ala.size()-1 ; i > 0 ; i--) {
					String st1 = (String)ala.get(i);
					StringTokenizer star1 = new StringTokenizer(st1, "|");
					if (star1.nextToken().trim().equals(movieName)) {
						alb.remove(i);
						mta.saveMovieTimingAllocation(movieAlloc, alb);
						}
					}
				}catch (IOException e) {
					System.out.println("IOException > " + e.getMessage());
					}	
			}
	

}