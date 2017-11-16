package Movie;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import utility.readWrite;

import java.util.Scanner;

public class MovieRating implements Serializable {
	private String name;
	private String oneStar;
	private String twoStar ;
	private String threeStar;
	private String fourStar;
	private String fiveStar;
	
	String movieTimes = "movietimes.txt";
	String movieDetails = "movie.txt"; 
	String movieRating = "movierating.txt";
	String movieAlloc = "movietimingalloc.txt";
	String bookingHistory = "bookingHistory.txt";
	


	public MovieRating(String n, String one, String two, String three, String four, String five)  {
		name = n;
		oneStar = one;
		twoStar = two;
		threeStar = three;
		fourStar = four;
		fiveStar = five;		
	}
	
	public MovieRating() {}

	readWrite rw = new readWrite();
	Scanner sc = new Scanner(System.in);
	
	public String getName() { return name; }
	public String getOne() { return oneStar; }
	public String getTwo() { return twoStar; }
	public String getThree() { return threeStar; }
	public String getFour() { return fourStar; }
	public String getFive() { return fiveStar; }
	
	public ArrayList<MovieRating> readMovieRating(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList)rw.read(filename);
		ArrayList alr = new ArrayList() ;

      for (int i = 0 ; i < stringArray.size() ; i++) {
				String st = (String)stringArray.get(i);
				
				StringTokenizer star = new StringTokenizer(st , "|");	
				String name = star.nextToken().trim();
				String oneStar = star.nextToken().trim();
				String twoStar = star.nextToken().trim();
				String threeStar = star.nextToken().trim();
				String fourStar = star.nextToken().trim();
				String fiveStar = star.nextToken().trim();
				MovieRating movrat = new MovieRating(name, oneStar, twoStar, threeStar, fourStar, fiveStar);
				alr.add(movrat) ;
			}
			return alr ;
	}

	public void saveMovieRating(String filename, List al) throws IOException {
		List alw = new ArrayList() ;

      for (int i = 0 ; i < al.size() ; i++) {
				MovieRating movrat = (MovieRating)al.get(i);
				StringBuilder st =  new StringBuilder() ;
				st.append(movrat.getName().trim());
				st.append("|");
				st.append(movrat.getOne().trim());
				st.append("|");
				st.append(movrat.getTwo().trim());
				st.append("|");
				st.append(movrat.getThree().trim());
				st.append("|");
				st.append(movrat.getFour().trim());
				st.append("|");
				st.append(movrat.getFive().trim());
				alw.add(st.toString()) ;
			}
			rw.write(filename,alw);
	}
	

}