package com.emorphis.cashmanagement.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtility {

	public static Date getDateWithoutTime(Date date) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    cal.set(Calendar.MILLISECOND, 0);
	    return cal.getTime();
	}
	

	public static Date getEndDateTime(Date date) {
	    return new Date(date.getTime() + (24 * 60 * 60 * 1000));
	}
	
}
