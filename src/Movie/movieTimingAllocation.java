package Movie;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import utility.readWrite;

/**
 Represents Movie Timing Allocation for an individual movie time 
 for a specific cineplex, specific cinema, specific date and time
 * @author SS2-Group8
 * @version 1.0
 * @since 15/11/2017;
 */

public class movieTimingAllocation implements Serializable {
	/**
	 * The title of the Movie
	 */
	private String name;
	/**
	 * The location of the Cineplex
	 */
	private String cineplex;
	/**
	 * The cinema number. Different cinemas have different features
	 * Cinema 1 is for 3D movies, Cinema 2/3 is for all other movies
	 */
	private String cinema;
	/**
	 *  Date of the Movie Screening
	 */
	private String date;
	/**
	 * Time of the Movie Screening
	 */
	private String time;
	/**
	 *  Type of Day referring to whether it is a public holiday/weekend/Friday or Monday-Thursday
	 */
	private String typeOfDay;
	
	readWrite rw = new readWrite();
	
	public movieTimingAllocation() {}
	
	/**
	 * Creates a new Movie Timing Allocation with the given name,
	 * cineplex, cinema, date ,time and typeOfDay
	 * @param n Movie Title 
	 * @param cp Cineplex
	 * @param cn Cinema
	 * @param d Date
	 * @param t Time
	 * @param tod typeOfDay
	 */
	public movieTimingAllocation(String n, String cp, String cn, String d, String t, String tod)  {
		name = n;
		cineplex = cp;
		cinema = cn ;
		date = d;
		time = t ;
		typeOfDay = tod;
	}
	
	/**
	 * Gets the title of the Movie
	 * @return Movie Title
	 */
	public String getName() { return name ; }
	/**
	 * Gets the location of the Cinema
	 * @return Name of the Cinema
	 */
	public String getCineplex() {return cineplex ; }	
	/**
	 * Gets the Cinema Number
	 * @return Cinema Number
	 */
	public String getCinema() { return cinema ; }
	/**
	 * Gets the date of the Movie
	 * @return Movie Date
	 */
	public String getDate() { return date ; }
	/**
	 * Gets the Time of the Movie
	 * @return Movie Time
	 */
	public String getTime() { return time ; }
	/**
	 *  Gets the type Of Day of the Movie
	 * @return Type Of Day of the Movie
	 */
	public String getTypeOfDay() { return typeOfDay ; }
	
	public ArrayList<movieTimingAllocation> readMovieTimingAllocation(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList)rw.read(filename);
		ArrayList alr = new ArrayList() ;

      for (int i = 0 ; i < stringArray.size() ; i++) {
				String st = (String)stringArray.get(i);
				
				StringTokenizer star = new StringTokenizer(st , "|");	
				String name = star.nextToken().trim();
				String cineplex = star.nextToken().trim();
				String  cinema = star.nextToken().trim();	
				String date = star.nextToken().trim();
				String  time = star.nextToken().trim();	
				String typeOfDay = star.nextToken().trim();
				movieTimingAllocation movtimalloc = new movieTimingAllocation(name, cineplex, cinema, date, time, typeOfDay);
				alr.add(movtimalloc) ;
			}
			return alr ;
	}

	public void saveMovieTimingAllocation(String filename, List al) throws IOException {
		List alw = new ArrayList() ;// 

      for (int i = 0 ; i < al.size() ; i++) {
				movieTimingAllocation movtimalloc = (movieTimingAllocation)al.get(i);
				StringBuilder st =  new StringBuilder() ;
				st.append(movtimalloc.getName().trim());
				st.append("|");
				st.append(movtimalloc.getCineplex().trim());
				st.append("|");
				st.append(movtimalloc.getCinema().trim());
				st.append("|");
				st.append(movtimalloc.getDate().trim());
				st.append("|");
				st.append(movtimalloc.getTime().trim());
				st.append("|");
				st.append(movtimalloc.getTypeOfDay().trim());
				alw.add(st.toString()) ;
			}
			rw.write(filename,alw);
	}

}