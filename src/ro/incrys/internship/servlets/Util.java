/**
 * 
 */
package ro.incrys.internship.servlets;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import services.WordContainsException;

/**
 * @author Alina
 * 
 */
public class Util {

	public static String getCurrentTime() {

		Calendar cal = Calendar.getInstance();
		cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(cal.getTime());

	}

	public static Time getSQLTime(String time) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		long ms = sdf.parse(time).getTime();
		Time t = new Time(ms);

		return t;
	}
/**
 * @name veryfyParameter verifies whether a word is different from null or not empty
 * @param param text word to verify 
 * @throws WordContainsException
 */
	public static void verifyParameter(String param) throws WordContainsException {
		if (param == null || param.equals(" ")) {
		    throw new WordContainsException("This field must contain text");    
		}
	      
	}

}
