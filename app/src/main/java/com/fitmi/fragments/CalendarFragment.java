package com.fitmi.fragments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.fitmi.R;
import com.fitmi.activitys.TabActivity;
import com.fitmi.utils.Constants;

public class CalendarFragment extends BaseFragment implements OnClickListener {

	private static final String tag = "CalendarFragment";
	private TextView currentMonth;
	private ImageView prevMonth;
	private ImageView nextMonth;
	private GridView calendarView;
	private GridCellAdapter adapter;
	private Calendar _calendar;
	@SuppressLint("NewApi")
	private int month, year;
	@SuppressWarnings("unused")
	@SuppressLint({ "NewApi", "NewApi", "NewApi", "NewApi" })
	private final DateFormat dateFormatter = new DateFormat();
	private static final String dateTemplate = "MMMM yyyy";
	
	private GestureDetector mGestureDetector;
	View v;
	View previousView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.fragment_calendar, container,
				false);

		ButterKnife.inject(this, view);
		setNullClickListener(view);
		/*view.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				CalendarFragment.this.re
			}
		});*/
		
		mGestureDetector = new GestureDetector(getActivity(), new GestureListener());

		_calendar = Calendar.getInstance(Locale.getDefault());
		month = _calendar.get(Calendar.MONTH) + 1;
		year = _calendar.get(Calendar.YEAR);
		Log.d(tag, "Calendar Instance:= " + "Month: " + month + " " + "Year: "
				+ year);

		prevMonth = (ImageView) view.findViewById(R.id.prevMonth);
		//prevMonth.setOnClickListener(this);
		prevMonth.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				// TODO Auto-generated method stub
				v = view;
				return mGestureDetector.onTouchEvent(event);
			}
		});

		currentMonth = (TextView) view.findViewById(R.id.currentMonth);
		currentMonth.setText(DateFormat.format(dateTemplate,
				_calendar.getTime()));

		nextMonth = (ImageView) view.findViewById(R.id.nextMonth);
		//nextMonth.setOnClickListener(this);
		nextMonth.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				// TODO Auto-generated method stub
				v = view;
				return mGestureDetector.onTouchEvent(event);
			}
		});

		calendarView = (GridView) view.findViewById(R.id.calendar);

		// Initialised
		adapter = new GridCellAdapter(getActivity(),
				R.id.calendar_day_gridcell, month, year);
		adapter.notifyDataSetChanged();
		calendarView.setAdapter(adapter);

		currentMonth.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

			}
		});

		return view;

	}

	/**
	 * 
	 * @param month
	 * @param year
	 */
	private void setGridCellAdapterToDate(int month, int year) {
		adapter = new GridCellAdapter(getActivity(),
				R.id.calendar_day_gridcell, month, year);
		_calendar.set(year, month - 1, _calendar.get(Calendar.DAY_OF_MONTH));
		currentMonth.setText(DateFormat.format(dateTemplate,
				_calendar.getTime()));
		adapter.notifyDataSetChanged();
		calendarView.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		if (v == prevMonth) {
			if (month <= 1) {
				month = 12;
				year--;
			} else {
				month--;
			}
			Log.d(tag, "Setting Prev Month in GridCellAdapter: " + "Month: "
					+ month + " Year: " + year);
			setGridCellAdapterToDate(month, year);
		}
		if (v == nextMonth) {
			if (month > 11) {
				month = 1;
				year++;
			} else {
				month++;
			}
			Log.d(tag, "Setting Next Month in GridCellAdapter: " + "Month: "
					+ month + " Year: " + year);
			setGridCellAdapterToDate(month, year);
		}

	}

	// Inner Class
	public class GridCellAdapter extends BaseAdapter implements OnClickListener {
		private static final String tag = "GridCellAdapter";
		private final Context _context;

		private final List<String> list;
		private static final int DAY_OFFSET = 1;
		private final String[] weekdays = new String[] { "Sun", "Mon", "Tue",
				"Wed", "Thu", "Fri", "Sat" };
		private final String[] months = { "January", "February", "March",
				"April", "May", "June", "July", "August", "September",
				"October", "November", "December" };
		private final int[] daysOfMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30,
				31, 30, 31 };
		private int daysInMonth;
		private int currentDayOfMonth;
		private int currentWeekDay;
		private Button gridcell;
		private TextView num_events_per_day;
		private final HashMap<String, Integer> eventsPerMonthMap;
		private final SimpleDateFormat dateFormatter = new SimpleDateFormat(
				"dd-MMM-yyyy");

		// Days in Current Month
		public GridCellAdapter(Context context, int textViewResourceId,
				int month, int year) {
			super();
			this._context = context;
			this.list = new ArrayList<String>();
			Log.d(tag, "==> Passed in Date FOR Month: " + month + " "
					+ "Year: " + year);
			Calendar calendar = Calendar.getInstance();
			setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
			setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
			Log.d(tag, "New Calendar:= " + calendar.getTime().toString());
			Log.d(tag, "CurrentDayOfWeek :" + getCurrentWeekDay());
			Log.d(tag, "CurrentDayOfMonth :" + getCurrentDayOfMonth());

			// Print Month
			printMonth(month, year);

			// Find Number of Events
			eventsPerMonthMap = findNumberOfEventsPerMonth(year, month);
		}

		private String getMonthAsString(int i) {
			return months[i];
		}

		private String getWeekDayAsString(int i) {
			return weekdays[i];
		}

		private int getNumberOfDaysOfMonth(int i) {
			return daysOfMonth[i];
		}

		public String getItem(int position) {
			return list.get(position);
		}

		@Override
		public int getCount() {
			return list.size();
		}

		/**
		 * Prints Month
		 * 
		 * @param mm
		 * @param yy
		 */
		private void printMonth(int mm, int yy) {
			Log.d(tag, "==> printMonth: mm: " + mm + " " + "yy: " + yy);
			int trailingSpaces = 0;
			int daysInPrevMonth = 0;
			int prevMonth = 0;
			int prevYear = 0;
			int nextMonth = 0;
			int nextYear = 0;

			int currentMonth = mm - 1;
			String currentMonthName = getMonthAsString(currentMonth);
			daysInMonth = getNumberOfDaysOfMonth(currentMonth);

			Log.d(tag, "Current Month: " + " " + currentMonthName + " having "
					+ daysInMonth + " days.");

			GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);
			Log.d(tag, "Gregorian Calendar:= " + cal.getTime().toString());

			if (currentMonth == 11) {
				prevMonth = currentMonth - 1;
				daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
				nextMonth = 0;
				prevYear = yy;
				nextYear = yy + 1;
				Log.d(tag, "*->PrevYear: " + prevYear + " PrevMonth:"
						+ prevMonth + " NextMonth: " + nextMonth
						+ " NextYear: " + nextYear);
			} else if (currentMonth == 0) {
				prevMonth = 11;
				prevYear = yy - 1;
				nextYear = yy;
				daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
				nextMonth = 1;
				Log.d(tag, "**--> PrevYear: " + prevYear + " PrevMonth:"
						+ prevMonth + " NextMonth: " + nextMonth
						+ " NextYear: " + nextYear);
			} else {
				prevMonth = currentMonth - 1;
				nextMonth = currentMonth + 1;
				nextYear = yy;
				prevYear = yy;
				daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
				Log.d(tag, "***---> PrevYear: " + prevYear + " PrevMonth:"
						+ prevMonth + " NextMonth: " + nextMonth
						+ " NextYear: " + nextYear);
			}

			int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
			trailingSpaces = currentWeekDay;

			Log.d(tag, "Week Day:" + currentWeekDay + " is "
					+ getWeekDayAsString(currentWeekDay));
			Log.d(tag, "No. Trailing space to Add: " + trailingSpaces);
			Log.d(tag, "No. of Days in Previous Month: " + daysInPrevMonth);

			if (cal.isLeapYear(cal.get(Calendar.YEAR)))
				if (mm == 2)
					++daysInMonth;
				else if (mm == 3)
					++daysInPrevMonth;

			// Trailing Month days
			for (int i = 0; i < trailingSpaces; i++) {
				Log.d(tag,
						"PREV MONTH:= "
								+ prevMonth
								+ " => "
								+ getMonthAsString(prevMonth)
								+ " "
								+ String.valueOf((daysInPrevMonth
										- trailingSpaces + DAY_OFFSET)
										+ i));
				list.add(String
						.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET)
								+ i)
								+ "-GREY"
								+ "-"
								+ getMonthAsString(prevMonth)
								+ "-"
								+ prevYear);
			}

			// Current Month Days
			for (int i = 1; i <= daysInMonth; i++) {
				Log.d(currentMonthName, String.valueOf(i) + " "
						+ getMonthAsString(currentMonth) + " " + yy);
				if (i == getCurrentDayOfMonth()) {
					list.add(String.valueOf(i) + "-BLUE" + "-"
							+ getMonthAsString(currentMonth) + "-" + yy);
				} else {
					list.add(String.valueOf(i) + "-WHITE" + "-"
							+ getMonthAsString(currentMonth) + "-" + yy);
				}
			}

			// Leading Month days
			for (int i = 0; i < list.size() % 7; i++) {
				Log.d(tag, "NEXT MONTH:= " + getMonthAsString(nextMonth));
				list.add(String.valueOf(i + 1) + "-GREY" + "-"
						+ getMonthAsString(nextMonth) + "-" + nextYear);
			}
		}

		/**
		 * NOTE: YOU NEED TO IMPLEMENT THIS PART Given the YEAR, MONTH, retrieve
		 * ALL entries from a SQLite database for that month. Iterate over the
		 * List of All entries, and get the dateCreated, which is converted into
		 * day.
		 * 
		 * @param year
		 * @param month
		 * @return
		 */
		private HashMap<String, Integer> findNumberOfEventsPerMonth(int year,
				int month) {
			HashMap<String, Integer> map = new HashMap<String, Integer>();

			return map;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			if (row == null) {
				LayoutInflater inflater = (LayoutInflater) _context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.screen_gridcell, parent, false);
			}

			// Get a reference to the Day gridcell
			gridcell = (Button) row.findViewById(R.id.calendar_day_gridcell);
			gridcell.setOnClickListener(this);

			// ACCOUNT FOR SPACING

			Log.d(tag, "Current Day: " + getCurrentDayOfMonth());
			String[] day_color = list.get(position).split("-");
			String theday = day_color[0];
			String themonth = day_color[2];
			String theyear = day_color[3];
			if ((!eventsPerMonthMap.isEmpty()) && (eventsPerMonthMap != null)) {
				if (eventsPerMonthMap.containsKey(theday)) {
					num_events_per_day = (TextView) row
							.findViewById(R.id.num_events_per_day);
					Integer numEvents = (Integer) eventsPerMonthMap.get(theday);
					num_events_per_day.setText(numEvents.toString());
				}
			}

			// Set the Day GridCell
			gridcell.setText(theday);
			gridcell.setTag(theday + "-" + themonth + "-" + theyear);
			Log.d(tag, "Setting GridCell " + theday + "-" + themonth + "-"
					+ theyear);

			if (day_color[1].equals("GREY")) {
				gridcell.setTextColor(getResources()
						.getColor(R.color.lightgray));
			}
			if (day_color[1].equals("WHITE")) {
				gridcell.setTextColor(getResources().getColor(
						R.color.lightgray02));
			}
			if (day_color[1].equals("BLUE")) {
				gridcell.setTextColor(getResources().getColor(R.color.orrange));
				previousView=gridcell;
			}
			return row;
		}

		@Override
		public void onClick(View view) {
			String date_month_year = (String) view.getTag();
			
			((TextView) view).setTextColor(getResources().getColor(R.color.orrange));
			((TextView) previousView).setTextColor(getResources().getColor(R.color.lightgray02)); 
			previousView= view;

			//adapter.notifyDataSetChanged();
			Log.e("Selected date", date_month_year);
			try {
				Date parsedDate = dateFormatter.parse(date_month_year);
				SimpleDateFormat targetFormatter = new SimpleDateFormat(
						"EEEE, MMM dd, yyyy", Locale.ENGLISH);
				String formattedDate = targetFormatter.format(parsedDate);
				Constants.sDate = formattedDate;
				Log.d(tag, "formattedDate: " + formattedDate);

				//saveNewDate(formattedDate);

				SimpleDateFormat postFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");			
				Constants.postDate = postFormat.format(parsedDate);

				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Constants.conditionDate = df.format(parsedDate);
				
				Constants.sTempDate= Constants.conditionDate;
				dateSetCallback(formattedDate);


			} catch (ParseException e) {
				e.printStackTrace();
			}

			getActivity().onBackPressed();

		}

		public int getCurrentDayOfMonth() {
			return currentDayOfMonth;
		}

		private void setCurrentDayOfMonth(int currentDayOfMonth) {
			this.currentDayOfMonth = currentDayOfMonth;
		}

		public void setCurrentWeekDay(int currentWeekDay) {
			this.currentWeekDay = currentWeekDay;
		}

		public int getCurrentWeekDay() {
			return currentWeekDay;
		}
	}

	@OnClick(R.id.CalendarLinear)
	public void clickCalendarLinear() {

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	/*@OnClick(R.id.close)
	public void cliclCloseCalender()
	{
		getActivity().onBackPressed();
	}*/
	
	@OnClick(R.id.linerCalenderclose)	
	public void cliclCloseCalender()
	{
		getActivity().onBackPressed();
	}
	
	@OnClick(R.id.todaybtn)
	public void clickToday()
	{
		Calendar c = Calendar.getInstance();	
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");			
		String today = df.format(c.getTime());
		
		
		SimpleDateFormat targetFormatter = new SimpleDateFormat(
				"EEEE, MMM dd, yyyy", Locale.ENGLISH);
		String formattedDate = targetFormatter.format(c.getTime());
		Constants.sDate = formattedDate;
		Log.d(tag, "formattedDate: " + formattedDate);
		

		SimpleDateFormat postFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");			
		Constants.postDate = postFormat.format(c.getTime());		
		
		Constants.conditionDate = today;
		Constants.sTempDate= Constants.conditionDate;
		dateSetCallback(formattedDate);
		getActivity().onBackPressed();
	}

	/**
	 *  Gester listener
	 */

	class GestureListener extends GestureDetector.SimpleOnGestureListener
	{

		String currentGestureDetected;

		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			// TODO Auto-generated method stub
			
			//Toast.makeText(getActivity(), "Single click", Toast.LENGTH_LONG).show();
			
			if (v == prevMonth) {
				if (month <= 1) {
					month = 12;
					year--;
				} else {
					month--;
				}
				Log.d(tag, "Setting Prev Month in GridCellAdapter: " + "Month: "
						+ month + " Year: " + year);
				setGridCellAdapterToDate(month, year);
			}
			if (v == nextMonth) {
				if (month > 11) {
					month = 1;
					year++;
				} else {
					month++;
				}
				Log.d(tag, "Setting Next Month in GridCellAdapter: " + "Month: "
						+ month + " Year: " + year);
				setGridCellAdapterToDate(month, year);
			}
			
			return true;
		}
		
		// event when double tap occurs
		@Override
		public boolean onDoubleTap(MotionEvent e) {
			
			//Toast.makeText(getActivity(), "Double click", Toast.LENGTH_LONG).show();
			getActivity().onBackPressed();
			return true;
		}

	}

	
	private void dateSetCallback(String formattedDate)
	{
		switch (Constants.fragmentNumber) {
		case 1:

			Intent dataFoodlog = new Intent("foodLogUpdate");							
			dataFoodlog.putExtra("key", formattedDate);				
			getActivity().sendBroadcast(dataFoodlog);

			break;

		case 2:

			Intent dataActive = new Intent("activityUpdate");						
			dataActive.putExtra("key", formattedDate);				
			getActivity().sendBroadcast(dataActive);

			break;

		case 6://bloodpressure

			Intent dataWater = new Intent("waterlogupdate");					
			dataWater.putExtra("key", formattedDate);				
			getActivity().sendBroadcast(dataWater);

			break;

		case 4:

			Intent dataBlodpressureGraph = new Intent("bloodpressuregraph");					
			dataBlodpressureGraph.putExtra("key", formattedDate);				
			getActivity().sendBroadcast(dataBlodpressureGraph);

			break;

		case 44:

			Intent dataBlodpressure = new Intent("bloodpressure");				
			dataBlodpressure.putExtra("key", formattedDate);				
			getActivity().sendBroadcast(dataBlodpressure);

			break;

		case 3:

			Intent dataWeightGraph = new Intent("weightgraph");						
			dataWeightGraph.putExtra("key", formattedDate);				
			getActivity().sendBroadcast(dataWeightGraph);

			break;
		case 33:

			Intent dataWeight = new Intent("weight");				
			dataWeight.putExtra("key", formattedDate);				
			getActivity().sendBroadcast(dataWeight);

			break;

		case 8:

			Intent dataPlannerHome = new Intent("plannerhome");					
			dataPlannerHome.putExtra("key", formattedDate);				
			getActivity().sendBroadcast(dataPlannerHome);

			break;
		case 10:


			Intent dataPlannermonth = new Intent("plannermonth");					
			dataPlannermonth.putExtra("key", formattedDate);				
			getActivity().sendBroadcast(dataPlannermonth);

			break;

		case 9:

			Intent dataPlannerWeek = new Intent("plannerlist");						
			dataPlannerWeek.putExtra("key", formattedDate);				
			getActivity().sendBroadcast(dataPlannerWeek);

			break;

		case 16:

			Intent dataSleepLog = new Intent("sleeplogupdate");						
			dataSleepLog.putExtra("key", formattedDate);				
			getActivity().sendBroadcast(dataSleepLog);

			break;
		}
	}
	
}
