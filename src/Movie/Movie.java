package Movie;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import utility.readWrite;

import java.util.Scanner;

public class Movie implements Serializable {
	private String name ;
	private String rating;
	private String type ;
	private String status;
	private String director;
	private String cast;
	private String synopsis;
	private String reviewRating;
	private String reviewCount;
	private String totalSales;
	
	String movieTimes = "movietimes.txt";
	String movieDetails = "movie.txt"; 
	String movieRating = "movierating.txt";
	String movieAlloc = "movietimingalloc.txt";
	String bookingHistory = "bookingHistory.txt";
	
	
	public Movie(String n, String e, String c, String s, String d, String cas, String syn, String rr, String rc, String ts)  {
		name = n;
		rating = e;
		type = c;
		status = s;
		director = d;
		cast = cas;
		synopsis = syn;
		reviewRating = rr;
		reviewCount = rc;
		totalSales = ts;
	}
	
	public Movie() {	}

	readWrite rw = new readWrite();
	MovieRating mr = new MovieRating();
	Scanner sc = new Scanner(System.in);
	
	public String getName() { return name ; }
	public String getRating() { return rating ; }
	public String getType() { return type ; }
	public String getStatus() { return status ;}
	public String getDirector() { return director ;}
	public String getCast() { return cast ;}
	public String getSynopsis() { return synopsis; }
	public String getReviewRating() { return reviewRating; }
	public String getReviewCount() { return reviewCount; }
	public String getTotalSales() { return totalSales; }
	
	public ArrayList<Movie> readMovie(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList)rw.read(filename);
		ArrayList alr = new ArrayList() ;

        for (int i = 0 ; i < stringArray.size() ; i++) {
				String st = (String)stringArray.get(i);
				
				StringTokenizer star = new StringTokenizer(st , "|");	
				String  name = star.nextToken();	
				String  rating = star.nextToken().trim();
				String  type = star.nextToken().trim();
				String  status = star.nextToken().trim(); 
				String 	director = star.nextToken().trim();
				String	cast = star.nextToken().trim();
				String 	synopsis = star.nextToken().trim();
				String 	reviewRating = star.nextToken().trim();
				String 	reviewCount = star.nextToken().trim();
				String 	totalSales = star.nextToken().trim();
				Movie mov = new Movie(name, rating, type, status, director, cast, synopsis, reviewRating, reviewCount, totalSales);
				alr.add(mov) ;
			}
			return alr ;
	}

	public void saveMovie(String filename, List al) throws IOException {
		List alw = new ArrayList() ;// to store MovieList data

        for (int i = 0 ; i < al.size() ; i++) {
				Movie mov = (Movie)al.get(i);
				StringBuilder st =  new StringBuilder() ;
				st.append(mov.getName().trim());
				st.append("|");
				st.append(mov.getRating().trim());
				st.append("|");
				st.append(mov.getType().trim());
				st.append("|");
				st.append(mov.getStatus().trim());
				st.append("|");
				st.append(mov.getDirector().trim());
				st.append("|");
				st.append(mov.getCast().trim());
				st.append("|");
				st.append(mov.getSynopsis());
				st.append("|");
				st.append(mov.getReviewRating());
				st.append("|");
				st.append(mov.getReviewCount());
				st.append("|");
				st.append(mov.getTotalSales());
				alw.add(st.toString()) ;
				
			}
			rw.write(filename,alw);
	}
	
	public void newMovie(){
		String movieType = "", movieStatus = "", rating = "";	
		
		System.out.println("Enter Movie Name:");
		String name = sc.nextLine();
		System.out.println("Select Movie Rating:");
		System.out.println("Movie Rating: (1) G OR (2) PG OR (3) NC16 OR (4) M18 OR (5) R21");
		int choice = sc.nextInt();
		switch(choice) {
		case 1:
			rating = "G";
    			break;
		case 2:
			rating = "PG";
			break;
		case 3:
    			rating = "NC16";
    			break;
		case 4:
    			rating = "M18";
    			break;
		case 5:
			rating = "R21";
			break;
			}
		sc.nextLine();
		System.out.println("Select Type of Movie:");
		System.out.println("Movie Type: (1) Normal OR (2) 3D OR (3) Blockbuster");
		choice = sc.nextInt();
		switch(choice) {
    		case 1:
    			movieType = "Normal";
    			break;
    		case 2:
    			movieType = "3D";
    			break;
    		case 3:
    			movieType = "Blockbuster";
    			break;		          
    			}	            
		sc.nextLine();
		System.out.println("Select Status of Movie:");
		System.out.println("Movie Status: (1) Preview OR (2) Now Showing OR (3) End Of Screening");
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
    			break;
    			}
		sc.nextLine();
		System.out.println("Enter Director of Movie:");
		String director = sc.nextLine();
		System.out.println("Enter Cast of Movie:");
		String cast = sc.nextLine();
		System.out.println("Enter Synopsis of Movie:");
		String synopsis = sc.nextLine();
		String reviewRating = "NA";
		String reviewCount = "0";
		try {
			ArrayList<Movie> al = readMovie(movieDetails) ;
			ArrayList<MovieRating> alw = mr.readMovieRating(movieRating);
			Movie p1 = new Movie(name, rating, movieType, movieStatus, director, cast, synopsis, reviewRating, reviewCount, "0");
			String oneStar = "0"; String twoStar = "0"; String threeStar = "0"; String fourStar = "0"; String fiveStar = "0";
			MovieRating p2 = new MovieRating(name, oneStar, twoStar, threeStar, fourStar, fiveStar);
			alw.add(p2);
			al.add(p1);
			saveMovie(movieDetails, al);
			mr.saveMovieRating(movieRating, alw);
			}catch (IOException e) {
				System.out.println("IOException > " + e.getMessage());
				}
    }
	
	public void deleteMovie(int selection) {
		
		try {
			ArrayList<Movie> al = readMovie(movieDetails) ;
			ArrayList<MovieRating> alw = mr.readMovieRating(movieRating);
			alw.remove(selection-1);
			al.remove(selection-1);
			saveMovie(movieDetails, al);
			mr.saveMovieRating(movieRating, alw);
			}catch (IOException e) {
				System.out.println("IOException > " + e.getMessage());
				}
	}
	
	public void modifyMovieStatus(int selection, String status) {
		
		try {
			ArrayList<Movie> al = readMovie(movieDetails) ;
			ArrayList<MovieRating> alw = mr.readMovieRating(movieRating);
			Movie mov = (Movie)al.get(selection-1);
			MovieRating movrat = (MovieRating)alw.get(selection-1);
			String name = mov.getName(); 				 String oneStar = movrat.getOne();
			String rating = mov.getRating();				 String twoStar = movrat.getTwo();
			String type = mov.getType(); 				 String threeStar = movrat.getThree();
			String director = mov.getDirector();			 String fourStar = movrat.getFour();
			String cast = mov.getCast(); 				 String fiveStar = movrat.getFive();
			String synopsis = mov.getSynopsis();
			String reviewRating = mov.getReviewRating();  
			String reviewCount = mov.getReviewCount();
			String totalSales = mov.getTotalSales();
			MovieRating p2 = new MovieRating(name, oneStar, twoStar, threeStar, fourStar, fiveStar);
			Movie p1 = new Movie(name, rating, type, status, director, cast, synopsis, reviewRating, reviewCount, totalSales);
			al.add(p1); alw.add(p2);
			saveMovie(movieDetails, al); mr.saveMovieRating(movieRating, alw);
			}catch (IOException e) {
				System.out.println("IOException > " + e.getMessage());
				}
		
	}
	
	public int selectMovie() {
		showMovieList();
		System.out.println("Select Movie: ");
		int movieSelection = sc.nextInt();
		return movieSelection;
	}
	
	public void showMovieList() {
		try {
			ArrayList<Movie> al = readMovie(movieDetails);
			for (int i = 0 ; i < al.size() ; i++) {
				Movie mov = (Movie)al.get(i);
				System.out.println((i+1) + ". Name: " + mov.getName() + " Rating: " + mov.getRating() + " Type: " + mov.getType() + 
						" Status: " + mov.getStatus());
			}
		} catch (IOException e) {
			System.out.println("IOException > " + e.getMessage());
			}
		}
	
	public void showMovieInfo(int movieSelection) {
		try {
			ArrayList al = readMovie(movieDetails);				
			Movie mov = (Movie)al.get(movieSelection-1);
			System.out.println("Name: " + mov.getName() + " \nDirector: " + mov.getDirector() + " \nCast: " + mov.getCast() + 
						" \nSynopsis: " + mov.getSynopsis() + "\nOverall Reviewer Rating: " + mov.getReviewRating()
						+ "\nNumber of Reviews: " + mov.getReviewCount());
			}	catch (IOException e) {
			System.out.println("IOException > " + e.getMessage());
			}
		}
}