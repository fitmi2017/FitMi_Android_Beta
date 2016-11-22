package com.fitmi.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateModule {

	public String getDateFormat() {
		Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		return df.format(c.getTime());
	}

	public String getLogTime() {
		Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());
		SimpleDateFormat df = new SimpleDateFormat("kk:mm:ss");
		return df.format(c.getTime());
	}

	public String getDefaultFoodNameDate() {
		Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());
		SimpleDateFormat df = new SimpleDateFormat("MMM-dd-yyyy");
		return df.format(c.getTime());
	}

	public String dateConvert(String dateString) {
		String formattedDate = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd kk:mm:ss");
		Date convertedDate = new Date();
		try {
			convertedDate = dateFormat.parse(dateString);

			SimpleDateFormat targetFormatter = new SimpleDateFormat(
					"EEEE, MMMM dd, yyyy", Locale.ENGLISH);
			formattedDate = targetFormatter.format(convertedDate);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(convertedDate);
		return formattedDate;
	}

	public String getPostDateFormat(String dateString) {

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat preFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
		try {
			cal.setTime(preFormat.parse(dateString));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();

		cal.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, c.get(Calendar.MINUTE));
		cal.set(Calendar.SECOND, c.get(Calendar.SECOND));

		SimpleDateFormat postFormat = new SimpleDateFormat(
				"yyyy-MM-dd kk:mm:ss");
		return postFormat.format(cal.getTime());
	}

	public String conditionDateFormat(String dateString) {
		String formattedDate = "";

		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"EEEE, MMMM dd, yyyy", Locale.ENGLISH);
		SimpleDateFormat targetFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Date convertedDate = new Date();
		try {
			convertedDate = dateFormat.parse(dateString);

			formattedDate = targetFormatter.format(convertedDate);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(convertedDate);
		return formattedDate;
	}
	
	public String sDateFormat(String dateString) {
		String formattedDate = "";

		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"EEEE, MMMM dd, yyyy", Locale.ENGLISH);
		SimpleDateFormat targetFormatter = new SimpleDateFormat("EEEE, MMM dd, yyyy");
		Date convertedDate = new Date();
		try {
			convertedDate = dateFormat.parse(dateString);

			formattedDate = targetFormatter.format(convertedDate);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(convertedDate);
		return formattedDate;
	}

	public String getTime() {
		Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());
		SimpleDateFormat df = new SimpleDateFormat("KK:mm a");
		return df.format(c.getTime());
	}

	public String datePreview(String dateString) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);

		// SimpleDateFormat preFormat = new
		// SimpleDateFormat("EEEE, MMM dd, yyyy");
		SimpleDateFormat preFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
		try {
			cal.setTime(preFormat.parse(dateString));
			cal.add(Calendar.DATE, -1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date lastParsedDate = cal.getTime();
		String formattedDateFirst = preFormat.format(lastParsedDate);

		return formattedDateFirst;

	}
	public String dateToday(String dateString) {
		SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
		SimpleDateFormat formatter2 = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
		String dateInString =dateString;		
		Date date = null ;
		try {

			date = formatter.parse(dateInString);
			System.out.println(date);
			System.out.println(formatter.format(date));

		} catch (ParseException e) {
			e.printStackTrace();
		}
		String formattedDateFirst = formatter2.format(date);

		return formattedDateFirst;

	}

	public String dateNext(String dateString) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);

		// SimpleDateFormat preFormat = new
		// SimpleDateFormat("EEEE, MMM dd, yyyy");
		SimpleDateFormat preFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
		try {
			cal.setTime(preFormat.parse(dateString));
			cal.add(Calendar.DATE, 1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date lastParsedDate = cal.getTime();
		String formattedDateFirst = preFormat.format(lastParsedDate);

		return formattedDateFirst;
	}

	public String getDateFrag(String dateString) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);

		// SimpleDateFormat preFormat = new
		// SimpleDateFormat("EEEE, MMM dd, yyyy");
		SimpleDateFormat preFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
		try {
			cal.setTime(preFormat.parse(dateString));
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date lastParsedDate = cal.getTime();
		String formattedDateFirst = preFormat.format(lastParsedDate);

		return formattedDateFirst;
	}
	public String previewWeek(String dateString) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);

		SimpleDateFormat preFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
		try {
			cal.setTime(preFormat.parse(dateString));
			cal.add(Calendar.DATE, -7);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date lastParsedDate = cal.getTime();
		String formattedDateFirst = preFormat.format(lastParsedDate);

		return formattedDateFirst;

	}

	public String nextWeek(String dateString) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);

		SimpleDateFormat preFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
		SimpleDateFormat posFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
		try {
			cal.setTime(preFormat.parse(dateString));
			cal.add(Calendar.DATE, 7);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date lastParsedDate = cal.getTime();
		String formattedDateFirst = posFormat.format(lastParsedDate);

		return formattedDateFirst;
	}

	public String dobDateFormat(String dateString) {
		String formattedDate = "";

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMMM/yyyy",
				Locale.ENGLISH);
		SimpleDateFormat targetFormatter = new SimpleDateFormat("MMMM dd, yyyy");
		Date convertedDate = new Date();
		try {
			convertedDate = dateFormat.parse(dateString);

			formattedDate = targetFormatter.format(convertedDate);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(convertedDate);
		return formattedDate;
	}

	public String getFormatDate(String dateString) {
		String formattedDate = "";

		SimpleDateFormat postFormatter = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat preFormatter = new SimpleDateFormat(
				"yyyy-MM-dd kk:mm:ss");
		Date convertedDate = new Date();
		try {
			convertedDate = preFormatter.parse(dateString);

			formattedDate = postFormatter.format(convertedDate);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formattedDate;

	}

	public String getFormatDateSearchInsert(String dateString) {
		String formattedDate = "";

		SimpleDateFormat preFormatter = new SimpleDateFormat(
				"EEEE, MMM dd, yyyy");

		SimpleDateFormat postFormatter = new SimpleDateFormat(
				"yyyy-MM-dd kk:mm:ss");
		Date convertedDate = new Date();
		try {
			convertedDate = preFormatter.parse(dateString);

			formattedDate = postFormatter.format(convertedDate);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formattedDate;

	}

	public String getFormatDateSearchTwo(String dateString,String time) {
		String formattedDate = "";

		SimpleDateFormat preFormatter = new SimpleDateFormat(
				"EEEE, MMM dd, yyyy");

		SimpleDateFormat postFormatter = new SimpleDateFormat(
				"yyyy-MM-dd");
		Date convertedDate = new Date();
		try {
			convertedDate = preFormatter.parse(dateString);

			formattedDate = postFormatter.format(convertedDate);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formattedDate+" "+time;

	}
	
	public String getFormatDateSearchBloodPressure(String dateString) {
		String formattedDate = "";

		SimpleDateFormat preFormatter = new SimpleDateFormat(
				"EEEE, MMM dd, yyyy kk:mm:ss");

		SimpleDateFormat postFormatter = new SimpleDateFormat(
				"yyyy-MM-dd kk:mm:ss");
		Date convertedDate = new Date();
		try {
			convertedDate = preFormatter.parse(dateString);

			formattedDate = postFormatter.format(convertedDate);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formattedDate;

	}
	
}
