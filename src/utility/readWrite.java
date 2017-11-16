package utility;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Provides the options to read and 
 * write files as well as checking
 * Array Length
 * 
 * @author SSP2-Group8
 *
 */
public class readWrite {
	
	public readWrite() {}

	/**
	 * Writes to a file a List of data
	 * 
	 * @param fileName file to be written
	 * @param data data to be written
	 * @throws IOException
	 */
	
	public static void write(String fileName, List data) throws IOException  {
	    PrintWriter out = new PrintWriter(new FileWriter(fileName));

	    try {
			for (int i =0; i < data.size() ; i++) {
	      		out.println((String)data.get(i));
			}
	    }
	    finally {
	      out.close();
	    }
	  }
	
	/**
	 * reads a file and returns the data as a list
	 * @param fileName file to be read
	 * @return List of data that has been read
	 * @throws IOException
	 */

	public static List read(String fileName) throws IOException {
		List data = new ArrayList() ;
	    Scanner scanner = new Scanner(new FileInputStream(fileName));
	    try {
	      while (scanner.hasNextLine()){
	        data.add(scanner.nextLine());
	      }
	    }
	    finally{
	      scanner.close();
	    }
	    return data;
	  }
	
	/**
	 * Gets the Array Length based
	 * on the input file
	 * @param filename file of stored database
	 * @return length of array in int
	 */
	
	public int checkArrayLength(String filename){
		
		try {
			ArrayList al = (ArrayList)read(filename);
			return al.size();
		} catch (IOException e) {
			System.out.println("IOException > " + e.getMessage());
			return -1;
			}	
	}

}
