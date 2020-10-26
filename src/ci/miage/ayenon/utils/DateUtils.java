package ci.miage.ayenon.utils;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtils {

	public static String toISODate(LocalDate date)
	{
		String dt = "";
		int year = date.getYear();
		int month = date.getMonthValue();
		int day = date.getDayOfMonth();
		dt += year;
		dt += "-"+((month < 10) ? "0"+month : month);
		dt += "-"+((day < 10) ? "0"+day : day);
		
		return dt;
	}
	
	public static LocalDate fromISODate(String date) {
		
		Pattern r = Pattern.compile("(\\d+)-(\\d+)-(\\d+)");
		Matcher m = r.matcher(date);
		if(m.find()) {
			int year = Integer.valueOf(m.group(1));
			int month = Integer.valueOf(m.group(2));
			int day = Integer.valueOf(m.group(3));
			return LocalDate.of(year, month, day);
		}
		return null; 
		
	}
	
	
}
