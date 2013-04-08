package com.uestc.se.tool;

import java.util.Calendar;
import java.util.Date;

public class DateTool {
	
	private static final Calendar calendar = Calendar.getInstance();

	public static Date forward(Date from, int minuteSteps){
		long fromMillis = from.getTime();
		long toMillis = fromMillis + (long)(minuteSteps*60000);
		return new Date(toMillis);
	}
	
	public static Date forwardHours(Date from, int hourSteps){
		long fromMillis = from.getTime();
		long toMillis = fromMillis + (long)(hourSteps*3600000);
		return new Date(toMillis);
	}
	
    public static Date date(final int day, final int month, final int year, final int hour, final int minute, final int second, final int milli) {

//        final Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.set(year, month, day, hour, minute, second);
        long millis = calendar.getTimeInMillis() + milli;
        calendar.setTimeInMillis(millis);
        date = calendar.getTime();
        return date;
    }
    
    /**
     * @eg 1999-03-04 00:00:00.000
     */
    public static long getTimeInMillis(String date){

    	calendar.set(
    			Integer.parseInt(date.split(" ")[0].split("-")[0]), 
    			Integer.parseInt(date.split(" ")[0].split("-")[1]), 
    			Integer.parseInt(date.split(" ")[0].split("-")[2]), 
    			Integer.parseInt(date.split(" ")[1].split(":")[0]), 
    			Integer.parseInt(date.split(" ")[1].split(":")[1]), 
    			Integer.parseInt(date.split(" ")[1].split(":")[2].substring(0, 1))
    			);
    	return calendar.getTimeInMillis() + Integer.parseInt(date.split(" ")[1].split(":")[2].substring(3));
	}
    
    public static Date date(final int day, final int month, final int year, final int hour, final int minute, final int second) {
    	final Calendar calendar = Calendar.getInstance();
    	Date date = new Date();
    	calendar.clear();
    	calendar.setTime(date);
        calendar.set(year, month, day, hour, minute, second);
        date = calendar.getTime();
        return date;
    }
    
    public static String getDateStr(Date date){
    	final Calendar calendar = Calendar.getInstance();
    	calendar.clear();
    	calendar.setTime(date);
    	String dateStr = Integer.toString(calendar.get(Calendar.YEAR)) + "-"
    			+ Integer.toString(calendar.get(Calendar.MONTH)+1) + "-"
    			+ Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + " "
    			+ calendar.getTime().toString().split(" ")[3] + "(UTC)";
    	// eg. calendar.getTime().toString() = Thu Sep 25 14:00:00 CST 2008
    	return dateStr;
    }
}
