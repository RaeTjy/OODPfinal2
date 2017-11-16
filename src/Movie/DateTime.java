package Movie;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class DateTime {
	
	Scanner sc= new Scanner (System.in);
	
	public String addShowDate(int day, int month, int year) {
		Date showDate = new Date();
	
	    SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE, MMMM d, yyyy");

	    Calendar aDate;
	    aDate = new GregorianCalendar(year, month, day);
	    showDate=aDate.getTime();
	    return dateFormatter.format(showDate);      
		}
	
	public String addShowTime() {
		
		Date showTime = new Date();
	    
		SimpleDateFormat timeFormatter = new SimpleDateFormat("HHmm");
	    int hour,minute ;
	    int year = 0, month = 0, day = 0;

	    System.out.println("Enter Hour:");
	   	hour=sc.nextInt();
	    System.out.println("Enter Minute:");
	    minute=sc.nextInt();

	    Calendar aTime;
	    
	    aTime = new GregorianCalendar(year,month,day,hour,minute);
	    showTime=aTime.getTime();
	   
	   return timeFormatter.format(showTime);
			
		}
	
	public int getDay() {
		    System.out.println("Enter Day (DD):");
		    int day=sc.nextInt();
		    return day;
	}
	public int getMonth() {
		System.out.println("Enter Month(MM):");
	    int month=sc.nextInt()-1;
		return month;
	}
	
	public int getYear() {
		 System.out.println("Enter Year(YYYY):");
		 int year = sc.nextInt();
		 return year;
	}
	
	
	}


