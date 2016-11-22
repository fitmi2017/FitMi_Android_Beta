package com.fitmi.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.text.format.Time;

import com.fitmi.dao.CalenderFirsLastDay;

public class FirstLastDayWeekMonthly {

	/**
	 * Get first and last day of week	
	 * @param selectedDate
	 * @return
	 */
	public CalenderFirsLastDay getWeekFirstDateLastDay(String selectedDate)
	{
		CalenderFirsLastDay firstLastDayObj = new CalenderFirsLastDay();

		// get today and clear time of day
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);

		SimpleDateFormat sdf = new SimpleDateFormat(
				"EEEE, MMM dd, yyyy", Locale.ENGLISH);		
		try {
			cal.setTime(sdf.parse(selectedDate.trim()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		SimpleDateFormat postFormat = new SimpleDateFormat("yyyy-MM-dd");		

		// get start of this week in milliseconds
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		System.out.println("Start of this week:       " + cal.getTime());

		Date firstParsedDate = cal.getTime();	
		String firstDate = postFormat.format(firstParsedDate);		
		firstLastDayObj.setFirstDay(firstDate);


		// get last of this week in milliseconds
		cal.add(Calendar.DAY_OF_WEEK, 6);
		System.out.println("Last of this week:       " + cal.getTime());

		Date lastParsedDate = cal.getTime();	
		String lasttDate = postFormat.format(lastParsedDate);
		firstLastDayObj.setLastDay(lasttDate);

		return firstLastDayObj;			
	}

	/**
	 *  Get first and last day monthly
	 */

	public CalenderFirsLastDay getMonthFirstDateLastDay(String selectedDate)
	{
		CalenderFirsLastDay firstLastDayObj = new CalenderFirsLastDay();

		// get today and clear time of day
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);

		SimpleDateFormat sdf = new SimpleDateFormat(
				"EEEE, MMMM dd, yyyy", Locale.ENGLISH);	
		SimpleDateFormat sdf2 = new SimpleDateFormat(
				"EEEE, MMMM dd, yyyy", Locale.ENGLISH);	
		try {
			cal.setTime(sdf.parse(selectedDate.trim()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				
				cal.setTime(sdf2.parse(selectedDate.trim()));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}	

		SimpleDateFormat postFormat = new SimpleDateFormat("yyyy-MM-dd");		

		// get start of this week in milliseconds
		cal.set(Calendar.DAY_OF_MONTH, 1);
		System.out.println("Start of this month:       " + cal.getTime());

		Date firstParsedDate = cal.getTime();	
		String firstDate = postFormat.format(firstParsedDate);		
		firstLastDayObj.setFirstDay(firstDate);

		int lastDayNo = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// get last of this week in milliseconds
		cal.add(Calendar.DAY_OF_MONTH, (lastDayNo - 1));
		System.out.println("Last of this month:       " + cal.getTime());

		Date lastParsedDate = cal.getTime();	
		String lasttDate = postFormat.format(lastParsedDate);
		firstLastDayObj.setLastDay(lasttDate);
		
		firstLastDayObj.setTotalDay(cal.getActualMaximum(Calendar.DAY_OF_MONTH));

		return firstLastDayObj;	
	}
	
	
	public int [] getTotalWeekInMonth(String selectedDate)
	{
		int [] week = new int[2];

		// get today and clear time of day
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);

		SimpleDateFormat sdf = new SimpleDateFormat(
				"EEEE, MMM dd, yyyy", Locale.ENGLISH);		
		try {
			cal.setTime(sdf.parse(selectedDate.trim()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		int currentWeek = cal.get(Calendar.WEEK_OF_MONTH);		
		int numOfWeeksInMonth = cal.getActualMaximum(Calendar.WEEK_OF_MONTH); 
		
		week[0]=currentWeek;
		week[1]=numOfWeeksInMonth;
		
		return week;		
	}

}
