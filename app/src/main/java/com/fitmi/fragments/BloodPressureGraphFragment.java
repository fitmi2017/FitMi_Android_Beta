package com.fitmi.fragments;

import it.sephiroth.demo.slider.widget.OnSwipeTouchListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


import com.db.modules.BpLogModule;
import com.fitmi.R;
import com.fitmi.dao.BloodPressureDAO;
import com.fitmi.dao.BloodPressureSet;
import com.fitmi.dao.CalenderFirsLastDay;
import com.fitmi.utils.Constants;
import com.fitmi.utils.DateModule;
import com.fitmi.utils.FirstLastDayWeekMonthly;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

public class BloodPressureGraphFragment extends BaseFragment{

	@InjectView(R.id.Heading)
	public TextView heading;

	@InjectView(R.id.Back)
	public ImageView back;
	
	@InjectView(R.id.backLiner)
	LinearLayout backLiner;

	@InjectView(R.id.showListText)
	public ImageView showListText;

	@InjectView(R.id.Date)
	TextView dateTextView;
	
	@InjectView(R.id.viewFirst)
	View viewFirst;
	
	@InjectView(R.id.viewSecond)
	View viewSecond;
	
	@InjectView(R.id.viewThird)
	View viewThird;
	
	@InjectView(R.id.viewFourth)
	View viewFourth;
	
	@InjectView(R.id.viewFifth)
	View viewFifth;

	Button daily, monthly, weekly;
	BarChart chart;

	BpLogModule bpModule;
	DateModule getDate = new DateModule();

	String today = "";

	ArrayList<BloodPressureSet> bpGraphData;
	ArrayList<BloodPressureSet> bpGraphDataDemo = new ArrayList<BloodPressureSet>();;

	FirstLastDayWeekMonthly firstLastDayDate = new FirstLastDayWeekMonthly();

	int selectGrapType = 1;
	
	double avgBPSys=0, avgBPDia=0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_blood_pressure_graph, container,
				false);
		setNullClickListener(v);

		Constants.fragmentNumber = 4;
		getActivity().registerReceiver(dateSetReceiver,
				new IntentFilter("bloodpressuregraph"));


		ButterKnife.inject(this, v);
		heading.setText("Blood Pressure");

		bpModule = new BpLogModule(getActivity());

		dialog();

		chart = (BarChart) v.findViewById(R.id.chart);
		chart.setClickable(false);
		chart.setPinchZoom(false);
		
		
		
		chart.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
		    public void onSwipeTop() {
		      // Toast.makeText(getActivity(), "top", Toast.LENGTH_SHORT).show();
		    }
		    public void onSwipeRight() {
		     // Toast.makeText(getActivity(), "right", Toast.LENGTH_SHORT).show();
		        datePreview();
		    }
		    public void onSwipeLeft() {
		     //  Toast.makeText(getActivity(), "left", Toast.LENGTH_SHORT).show();
		       
		       dateNext();
		    }
		    public void onSwipeBottom() {
		     //  Toast.makeText(getActivity(), "bottom", Toast.LENGTH_SHORT).show();
		    }

		    /*public boolean onTouch(View v, MotionEvent event) {
		        return gestureDetector.onTouchEvent(event);
		    }*/
		});

		//showListText.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

		daily = (Button) v.findViewById(R.id.dailybtn);
		monthly = (Button) v.findViewById(R.id.monthbtn);
		weekly = (Button) v.findViewById(R.id.weekbtn);


		daily.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				daily.setBackgroundColor(Color.parseColor("#B51F6B"));
				monthly.setBackgroundColor(Color.parseColor("#D379A6"));
				weekly.setBackgroundColor(Color.parseColor("#D379A6"));

				selectGrapType = 1;				
				//setGraphDaily();
				setGraphDaily();	
			}
		});
		monthly.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				daily.setBackgroundColor(Color.parseColor("#D379A6"));
				monthly.setBackgroundColor(Color.parseColor("#B51F6B"));
				weekly.setBackgroundColor(Color.parseColor("#D379A6"));

				selectGrapType = 3;
				//setGraphMonthly();
				setGraphMonhly();
			}
		});
		weekly.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				daily.setBackgroundColor(Color.parseColor("#D379A6"));
				monthly.setBackgroundColor(Color.parseColor("#D379A6"));
				weekly.setBackgroundColor(Color.parseColor("#B51F6B"));

				selectGrapType = 2;				
				//setGraphDaily();	
				
				setGraphWeekly();
			}
		});

		showListText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentTransaction transaction = getFragmentManager()
						.beginTransaction();
				transaction.add(R.id.root_home_frame, new BloodPressureFragment(),
						"BloodPressureFragment1");
				transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});
		chart.setPinchZoom(false);


		/*bpGraphData = bpModule.selectDailyBloodPressureGraph();				
		chartDraw(bpGraphData);*/

		return v;

	}

	@OnClick(R.id.backLiner)
	public void back() {

		getActivity().onBackPressed();
	}



	private ArrayList<BarDataSet> getDataSet(ArrayList<BloodPressureSet> bpGraphData) {	 

		ArrayList<BarDataSet> dataSets = null;
		ArrayList<BarEntry> valueSet = new ArrayList<>();
		for(int i=0;i<bpGraphData.size();i++){

			float fSys = Float.parseFloat(bpGraphData.get(i).getSys());
			float fDia = Float.parseFloat(bpGraphData.get(i).getDia());
			BarEntry v1e1 = new BarEntry(new float[] { fDia,(fSys - fDia)}, i); 
			valueSet.add(v1e1);
		}


		BarDataSet barDataSet = new BarDataSet(valueSet, "");
		//barDataSet1.setColor(Color.rgb(0, 155, 0));
		// barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
		barDataSet.setStackLabels(new String[] { "Dia", "Sys"});
		barDataSet.setColors(getColors());
		barDataSet.setDrawValues(false);
		dataSets = new ArrayList<>();
		dataSets.add(barDataSet);

		return dataSets;
	}

	private ArrayList<String> getXAxisValues(ArrayList<BloodPressureSet> bpGraphData) {
		ArrayList<String> xAxis = new ArrayList<>();

		for(int i=0;i<bpGraphData.size();i++){		

			switch (selectGrapType) {
			case 1:
				//xAxis.add(bpGraphData.get(i).getLogTime());
				
				SimpleDateFormat previousFormatter = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date parsedDate = previousFormatter.parse(bpGraphData.get(i).getAddedDate());
					SimpleDateFormat targetFormatter = new SimpleDateFormat(
							"EEE", Locale.ENGLISH);
					String formattedDate = targetFormatter.format(parsedDate);
					xAxis.add(formattedDate);

				}catch(Exception e){
					System.out.println("Exception :"+e); 
				}
				
				break;

			case 2:				
				
				xAxis.add("week "+bpGraphData.get(i).getAddedDate());

				break;
			case 3:
				/*SimpleDateFormat previousFormatterMonth = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date parsedDate = previousFormatterMonth.parse(bpGraphData.get(i).getAddedDate());
					SimpleDateFormat targetFormatter = new SimpleDateFormat(
							"dd", Locale.ENGLISH);
					String formattedDate = targetFormatter.format(parsedDate);
					xAxis.add(formattedDate);

				}catch(Exception e){
					System.out.println("Exception :"+e); 
				}*/
				
				xAxis.add(bpGraphData.get(i).getAddedDate());
				
				break;
			}

		}

		return xAxis;
	}

	private int[] getColors() {

		int stacksize = 2;

		// have as many colors as stack-values per entry
		int[] colors = new int[stacksize];

		for (int i = 0; i < stacksize; i++) {
			colors[i] = ColorTemplate.VORDIPLOM_COLORS[i];			
		}

		return colors;
	}



	public void chartDraw(ArrayList<BloodPressureSet> bpGraphData)
	{
		BarData data = new BarData(getXAxisValues(bpGraphData), getDataSet(bpGraphData));
		chart.setData(data);
		chart.setDescription("");
		chart.animateXY(2000, 2000);        
		chart.invalidate();   

		XAxis xAxis = chart.getXAxis();
		xAxis.setPosition(XAxisPosition.BOTTOM);
		xAxis.setSpaceBetweenLabels(0);
		xAxis.setTextColor(Color.parseColor("#31A4C4"));
		xAxis.setDrawGridLines(false);
		chart.getAxisRight().setEnabled(false);

		YAxis yAxis = chart.getAxisLeft();
		yAxis.setTextColor(Color.parseColor("#31A4C4"));        
		yAxis.setSpaceTop(10f);
	}

	@OnClick(R.id.Date)
	public void changeDate() {

		Constants.fragmentNumber = 4;

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.add(R.id.root_home_frame, new CalendarFragment(),
				"CalendarFragment");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();
	}


	/**
	 *   Dialog to set blood pressure 
	 */

	public void dialog()
	{

		final Dialog dialog=new Dialog(getActivity()/*,R.style.Theme_Dialog*/);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_enter_blood_pressure);
		final EditText sysValue = (EditText)dialog.findViewById(R.id.editSys);	
		final EditText diaValue = (EditText)dialog.findViewById(R.id.editDia);	

		TextView dialogTitle = (TextView)dialog.findViewById(R.id.dialogTitle);
		dialogTitle.setText("");

		sysValue.setOnFocusChangeListener(new OnFocusChangeListener() {

	        public void onFocusChange(View v, boolean hasFocus) {
	            if (hasFocus == true){
	            	
	                InputMethodManager inputMgr = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	                inputMgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
	                inputMgr.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);

	            }
	        }
	    });
	sysValue.requestFocus();
		dialog.findViewById(R.id.savebtn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if(sysValue.getText().toString().equalsIgnoreCase(""))
				{
					Toast.makeText(getActivity(), "Enter your sys value", Toast.LENGTH_LONG).show();
					return;
				}

				if(diaValue.getText().toString().equalsIgnoreCase(""))
				{
					Toast.makeText(getActivity(), "Enter your dia value", Toast.LENGTH_LONG).show();
					return;
				}
				int _dia=0;
				int _sys=0;
				if(!diaValue.getText().toString().equalsIgnoreCase("") && !sysValue.getText().toString().equalsIgnoreCase(""))
				{
					 _dia=Integer.parseInt(diaValue.getText().toString());
					_sys=Integer.parseInt(sysValue.getText().toString());
					
					
				}
				if(_dia>_sys){
					Toast.makeText(getActivity(), "Enter your sys value should be less than dia value", Toast.LENGTH_LONG).show();
					return;
					
				}
				BloodPressureDAO bloodPressureDate = new BloodPressureDAO();

				bloodPressureDate.setSys(sysValue.getText().toString());
				bloodPressureDate.setDia(diaValue.getText().toString());
				bloodPressureDate.setUserId(Constants.USER_ID);
				bloodPressureDate.setProfileId(Constants.PROFILE_ID);
				bloodPressureDate.setLogTime(getDate.getTime());
				//old avinash
				//bloodPressureDate.setAddedtime(getDate.getDateFormat());				
				bloodPressureDate.setAddedtime(getDate.getFormatDateSearchBloodPressure(Constants.sDate+" "+getDate.getLogTime()));
				bpModule.insertBpInformation(bloodPressureDate);
				//setGraphDaily();
				setGraphDaily();

				dialog.dismiss();

			}
		});
		dialog.findViewById(R.id.cancelbtn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();
		DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
		dialog.getWindow().setLayout((6 * width)/7, LayoutParams.WRAP_CONTENT);

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		try {
		/*	Calendar c = Calendar.getInstance();
			SimpleDateFormat targetFormatter = new SimpleDateFormat(
					"EEEE, ,MMM dd, yyyy", Locale.ENGLISH);
			String formattedDate = targetFormatter.format(c.getTime());
			dateTextView.setText(formattedDate);*/
			if (Constants.sTempDate.length() == 0) {
				//Constants.sDate = "Tuesday, February 10, 2015";

				Calendar c = Calendar.getInstance();
				System.out.println("Current time => " + c.getTime());	
				//	SimpleDateFormat df = new SimpleDateFormat("YYYY-MMM-dd hh:mm:ss");
				//SimpleDateFormat df = new SimpleDateFormat("EEEE, ,MMM dd, yyyy");
				SimpleDateFormat df = new SimpleDateFormat("EEEE, MMM dd, yyyy");
				Constants.sDate = df.format(c.getTime());


				SimpleDateFormat postFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");			
				Constants.postDate = postFormat.format(c.getTime());


				SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
				Constants.conditionDate = dformat.format(c.getTime());
				System.out.println("Calender post format :"+ Constants.postDate);
				//Toast.makeText(getActivity(), Constants.postDate, Toast.LENGTH_LONG).show();
				
			}

			dateTextView.setText(Constants.sDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//setGraphDaily();
		
		switch (selectGrapType) {
		case 1:
			
			setGraphDaily();
			
			break;

		case 2:
			
			setGraphWeekly();
			
			break;
			
		case 3:
			
			//setGraphMonthly();
			setGraphMonhly();
			
			break;
		}
	}

	BroadcastReceiver dateSetReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			Bundle bundleBroadcast = intent.getExtras();			
			dateTextView.setText(bundleBroadcast.getString("key"));

			switch (selectGrapType) {
			case 1:
				//setGraphDaily();
				setGraphDaily();
				break;
			case 2:
				//setGraphWeekly();
				setGraphWeekly();
				break;
			case 3:
				//setGraphMonthly();
				setGraphMonhly();
				break;
			}

		}
	};

	/**
	 *   Graph section
	 */
		

	/*public void setGraphDaily()
	{

		String selectedDate = dateTextView.getText().toString();

		selectedDate = selectedDate.replace("<", "");
		selectedDate = selectedDate.replace(">", "");

		SimpleDateFormat baseFormatter = new SimpleDateFormat(
				"EEEE, ,MMM dd, yyyy", Locale.ENGLISH);

		try {
			Date parsedDate = baseFormatter.parse(selectedDate.trim());
			SimpleDateFormat targetFormatter = new SimpleDateFormat("yyyy-MM-dd");
			String formattedDate = targetFormatter.format(parsedDate);

			bpModule = new BpLogModule(getActivity());
			bpGraphData = bpModule.selectDailyBloodPressureGraph(formattedDate);				
			chartDraw(bpGraphData);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}*/

	public void setGraphMonthly()
	{
		String selectedDate = dateTextView.getText().toString();

		selectedDate = selectedDate.replace("<", "");
		selectedDate = selectedDate.replace(">", "");

		CalenderFirsLastDay firstLastDayObj = firstLastDayDate.getMonthFirstDateLastDay(selectedDate);

		bpModule = new BpLogModule(getActivity());
		bpGraphData = bpModule.selectMonthlyBloodPressureGraph(firstLastDayObj);
		
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0); 
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);

		SimpleDateFormat previousFormatter = new SimpleDateFormat("yyyy-MM-dd");		

		try {
			cal.setTime(previousFormatter.parse(firstLastDayObj.getFirstDay()));
			cal.set(Calendar.DAY_OF_MONTH, 1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(bpGraphDataDemo.size()>0)
			bpGraphDataDemo.clear();

		for (int i = 0; i < cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
			Log.i("dateTag", previousFormatter.format(cal.getTime()));

			String demoDate = previousFormatter.format(cal.getTime());

			BloodPressureSet bpObject = new BloodPressureSet();

			bpObject.setSys("0");
			bpObject.setDia("0");
			bpObject.setLogTime("");
			bpObject.setAddedDate(demoDate);

			bpGraphDataDemo.add(bpObject);	        

			cal.add(Calendar.DAY_OF_WEEK, 1);
		}


		for (int i=0; i < bpGraphData.size(); i++){

			for (int j=0; j < bpGraphDataDemo.size(); j++){

				String date = getDate.getFormatDate(bpGraphData.get(i).getAddedDate());

				if(!(bpGraphDataDemo.get(j).getAddedDate().equals(date))){
					//do something for not equals

				}else{
					//do something for equals

					bpGraphDataDemo.set(j, bpGraphData.get(i));
				}
			}
		}	

		chartDraw(bpGraphDataDemo);		
	}

	public void setGraphDaily()
	{
		CalenderFirsLastDay firstLastDayObj = new CalenderFirsLastDay();
		
		String selectedDate = dateTextView.getText().toString();
		selectedDate = selectedDate.replace("<", "");
		selectedDate = selectedDate.replace(">", "");

		CalenderFirsLastDay firstLastDayObj1 = firstLastDayDate.getWeekFirstDateLastDay(selectedDate);

		bpModule = new BpLogModule(getActivity());
		
		bpGraphData = bpModule.avgBpDate(firstLastDayObj1);
		
		if(bpGraphData.get(0).getSys()!=null){
			
			avgBPSys=Double.parseDouble(bpGraphData.get(0).getSys());
			avgBPDia=Double.parseDouble(bpGraphData.get(0).getDia());
		}
		sideGraphShow();

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0); 
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);

		SimpleDateFormat previousFormatter = new SimpleDateFormat("yyyy-MM-dd");		

		try {
			cal.setTime(previousFormatter.parse(firstLastDayObj1.getFirstDay()));
			cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(bpGraphDataDemo.size()>0)
			bpGraphDataDemo.clear();

		for (int i = 0; i < 7; i++) {
			Log.i("dateTag", previousFormatter.format(cal.getTime()));

			String demoDate = previousFormatter.format(cal.getTime());
			
			firstLastDayObj.setFirstDay(demoDate);

			BloodPressureSet bpObject = new BloodPressureSet();
			
			bpGraphData = bpModule.selectDailylyBloodPressureGraph(firstLastDayObj);
			
			if(bpGraphData.get(0).getSys()!=null){
				
				
				bpObject.setSys(bpGraphData.get(0).getSys());
				bpObject.setDia(bpGraphData.get(0).getDia());
				bpObject.setLogTime(bpGraphData.get(0).getLogTime());
				bpObject.setAddedDate(demoDate);
				
			}else{
				
				bpObject.setSys("0");
				bpObject.setDia("0");
				bpObject.setLogTime("");
				bpObject.setAddedDate(demoDate);
				
			}
			
			

			/*bpObject.setSys("0");
			bpObject.setDia("0");
			bpObject.setLogTime("");
			bpObject.setAddedDate(demoDate);*/

			bpGraphDataDemo.add(bpObject);	        

			cal.add(Calendar.DAY_OF_WEEK, 1);
		}


		/*for (int i=0; i < bpGraphData.size(); i++){

			for (int j=0; j < bpGraphDataDemo.size(); j++){

				String date = getDate.getFormatDate(bpGraphData.get(i).getAddedDate());

				if(!(bpGraphDataDemo.get(j).getAddedDate().equals(date))){
					//do something for not equals

				}else{
					//do something for equals

					bpGraphDataDemo.set(j, bpGraphData.get(i));
				}
			}
		}*/	

		chartDraw(bpGraphDataDemo);
	}
	
	
	public void setGraphWeekly()
	{
		String weekFirstDate = "";
		String weekLastDate = "";
		CalenderFirsLastDay firstLastDayObj = new CalenderFirsLastDay();
		bpModule = new BpLogModule(getActivity());
		
		String selectedDate = dateTextView.getText().toString();
		selectedDate = selectedDate.replace("<", "");
		selectedDate = selectedDate.replace(">", "");
		
		int [] week = firstLastDayDate.getTotalWeekInMonth(selectedDate);
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0); 
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);

		SimpleDateFormat previousFormatter = new SimpleDateFormat("yyyy-MM-dd");	
		
		SimpleDateFormat sdf = new SimpleDateFormat(
				"EEEE, MMM dd, yyyy", Locale.ENGLISH);	

		try {
			cal.setTime(sdf.parse(selectedDate));			
			cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
			cal.set(Calendar.WEEK_OF_MONTH, week[0]);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(bpGraphDataDemo.size()>0)
			bpGraphDataDemo.clear();
		
		for(int i=week[0];i<=week[1];i++){
			
			for(int j=0;j<7;j++){
				
				String demoDate = previousFormatter.format(cal.getTime());	
				
				if(j==0){
					
					weekFirstDate = demoDate;
					firstLastDayObj.setFirstDay(demoDate);
					
				}else if(j == 6){
					
					weekLastDate = demoDate;
					firstLastDayObj.setLastDay(demoDate);
				}				
				
				cal.add(Calendar.DAY_OF_WEEK, 1);
			}
			
			bpGraphData = bpModule.selectWeeklyBloodPressureGraph(firstLastDayObj);
			
			BloodPressureSet bpObject = new BloodPressureSet();
			
			if(bpGraphData.get(0).getSys()!=null){
				
				
				bpObject.setSys(bpGraphData.get(0).getSys());
				bpObject.setDia(bpGraphData.get(0).getDia());
				bpObject.setLogTime(bpGraphData.get(0).getLogTime());
				bpObject.setAddedDate(String.valueOf(i));
				
				avgBPSys=Double.parseDouble(bpGraphData.get(0).getSys());
				avgBPDia=Double.parseDouble(bpGraphData.get(0).getDia());				
				
			}else{
				
				bpObject.setSys("0");
				bpObject.setDia("0");
				bpObject.setLogTime("");
				bpObject.setAddedDate(String.valueOf(i));
				
			}

			bpGraphDataDemo.add(bpObject);
			
		}
		
		
		chartDraw(bpGraphDataDemo);
		sideGraphShow();
	}
	
	public void setGraphMonhly()
	{
		CalenderFirsLastDay firstLastDayObj = new CalenderFirsLastDay();
		bpModule = new BpLogModule(getActivity());
		
		String selectedDate = dateTextView.getText().toString();
		selectedDate = selectedDate.replace("<", "");
		selectedDate = selectedDate.replace(">", "");
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		
		//SimpleDateFormat previousFormatter = new SimpleDateFormat("yyyy-MM-dd");			
		SimpleDateFormat sdf = new SimpleDateFormat(
				"EEEE, MMM dd, yyyy", Locale.ENGLISH);	
		try {
			cal.setTime(sdf.parse(selectedDate));	
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	//	int currentMonth = cal.get(Calendar.MONTH) + 1;
		cal.add(Calendar.MONTH, -3);
		int currentMonth = cal.get(Calendar.MONTH) + 1;
	
		
		SimpleDateFormat postFormat = new SimpleDateFormat("yyyy-MM-dd");	
		
		if(bpGraphDataDemo.size()>0)
			bpGraphDataDemo.clear();
		
		for(int i=0;i<4;i++){
			
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
			
			SimpleDateFormat month_date = new SimpleDateFormat("MMM");
			String month_name = month_date.format(cal.getTime());
			
			bpGraphData = bpModule.selectMonthlyBloodPressureGraph(firstLastDayObj);
			
			
			BloodPressureSet bpObject = new BloodPressureSet();
			
			if(bpGraphData.get(0).getSys()!=null){
				
				
				bpObject.setSys(bpGraphData.get(0).getSys());
				bpObject.setDia(bpGraphData.get(0).getDia());
				bpObject.setLogTime(bpGraphData.get(0).getLogTime());
				bpObject.setAddedDate(month_name);
				
				avgBPSys=Double.parseDouble(bpGraphData.get(0).getSys());
				avgBPDia=Double.parseDouble(bpGraphData.get(0).getDia());
				
			}else{
				
				bpObject.setSys("0");
				bpObject.setDia("0");
				bpObject.setLogTime("");
				bpObject.setAddedDate(month_name);
				
			}

			bpGraphDataDemo.add(bpObject);
			
			cal.add(Calendar.MONTH, 1);
		}
		
		chartDraw(bpGraphDataDemo);
		sideGraphShow();
		
	}

	@OnClick(R.id.txtPreview)
	public void datePreview()
	{
		String setDate = dateTextView.getText().toString().trim();
		setDate = getDate.datePreview(setDate);
		dateTextView.setText(setDate);
		Constants.conditionDate = getDate.conditionDateFormat(setDate);
		Constants.postDate =getDate.getFormatDateSearchInsert(setDate);
		Constants.sTempDate= Constants.conditionDate;
		Constants.sDate=getDate.sDateFormat(setDate);
		
		switch (selectGrapType) {
		case 1:
			//setGraphDaily();
			setGraphDaily();
			break;
		case 2:
			//setGraphWeekly();
			setGraphWeekly();
			break;
		case 3:
			//setGraphMonthly();
			setGraphMonhly();
			break;
		}		
	}

	@OnClick(R.id.txtNext)
	public void dateNext()
	{
		String setDate = dateTextView.getText().toString().trim();
		setDate = getDate.dateNext(setDate);
		dateTextView.setText(setDate);
		Constants.conditionDate = getDate.conditionDateFormat(setDate);
		Constants.postDate =getDate.getFormatDateSearchInsert(setDate);
		Constants.sTempDate= Constants.conditionDate;
		Constants.sDate=getDate.sDateFormat(setDate);
		switch (selectGrapType) {
		case 1:
			//setGraphDaily();
			setGraphDaily();
			break;
		case 2:
			//setGraphWeekly();
			setGraphWeekly();
			break;
		case 3:
			//setGraphMonthly();
			setGraphMonhly();
			break;
		}		
	}


	@OnClick(R.id.Settings)
	public void gotoSettings() {

		SettingsFragment fragment = new SettingsFragment();

		Bundle bundle = new Bundle();
		bundle.putInt("root_id", R.id.root_home_frame);
		fragment.setArguments(bundle);

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.add(R.id.root_home_frame, fragment, "SettingsFragment");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();

	}
	
	
	public void sideGraphShow()
	{
		 //Normal
	    if(avgBPSys<120 && avgBPDia<80)
	    {
	        /*_imgVwArrowNormal.hidden=NO;
	        _imgVwArrowPreHyperTension.hidden=YES;
	        _imgVwArrowHyperTension1.hidden=YES;
	        _imgVwArrowHyperTension2.hidden=YES;
	        _imgVwArrowHyperTensive.hidden=YES;*/
	        
	        
	        viewFirst.setVisibility(View.INVISIBLE);
	    	viewSecond.setVisibility(View.INVISIBLE);
	    	viewThird.setVisibility(View.INVISIBLE);
	    	viewFourth.setVisibility(View.INVISIBLE);
	    	viewFifth.setVisibility(View.VISIBLE);
	        
	    }
	    //PreHyperTension
	   else if ((avgBPSys>=120 && avgBPSys<=139) || (avgBPSys>=80 && avgBPSys<=89))
	    {
	        /*_imgVwArrowNormal.hidden=YES;
	        _imgVwArrowPreHyperTension.hidden=NO;
	        _imgVwArrowHyperTension1.hidden=YES;
	        _imgVwArrowHyperTension2.hidden=YES;
	        _imgVwArrowHyperTensive.hidden=YES;*/
		   
		   	viewFirst.setVisibility(View.INVISIBLE);
	    	viewSecond.setVisibility(View.INVISIBLE);
	    	viewThird.setVisibility(View.INVISIBLE);
	    	viewFourth.setVisibility(View.VISIBLE);
	    	viewFifth.setVisibility(View.INVISIBLE);
		   
	    }
	    //HyperTension-1
	   else if ((avgBPSys>=140 && avgBPSys<=159) || (avgBPSys>=90 && avgBPSys<=99))
	   {
	       /*_imgVwArrowNormal.hidden=YES;
	       _imgVwArrowPreHyperTension.hidden=YES;
	       _imgVwArrowHyperTension1.hidden=NO;
	       _imgVwArrowHyperTension2.hidden=YES;
	       _imgVwArrowHyperTensive.hidden=YES;*/
		   
			viewFirst.setVisibility(View.INVISIBLE);
	    	viewSecond.setVisibility(View.INVISIBLE);
	    	viewThird.setVisibility(View.VISIBLE);
	    	viewFourth.setVisibility(View.INVISIBLE);
	    	viewFifth.setVisibility(View.INVISIBLE);
		   
	   }
	   // HyperTension-2
	   else if ((avgBPSys>=160 && avgBPSys<=180) || (avgBPSys>=100 && avgBPSys<=110))
	   {
	       /*_imgVwArrowNormal.hidden=YES;
	       _imgVwArrowPreHyperTension.hidden=YES;
	       _imgVwArrowHyperTension1.hidden=YES;
	       _imgVwArrowHyperTension2.hidden=NO;
	       _imgVwArrowHyperTensive.hidden=YES;*/
		   
		   viewFirst.setVisibility(View.INVISIBLE);
	    	viewSecond.setVisibility(View.INVISIBLE);
	    	viewThird.setVisibility(View.INVISIBLE);
	    	viewFourth.setVisibility(View.VISIBLE);
	    	viewFifth.setVisibility(View.INVISIBLE);
		   
	   }
	    //HyperTensive
	   else if (avgBPSys>180 || avgBPSys>110)
	   {
	      /* _imgVwArrowNormal.hidden=YES;
	       _imgVwArrowPreHyperTension.hidden=YES;
	       _imgVwArrowHyperTension1.hidden=YES;
	       _imgVwArrowHyperTension2.hidden=YES;
	       _imgVwArrowHyperTensive.hidden=NO;*/
		   
		   viewFirst.setVisibility(View.INVISIBLE);
	    	viewSecond.setVisibility(View.INVISIBLE);
	    	viewThird.setVisibility(View.INVISIBLE);
	    	viewFourth.setVisibility(View.INVISIBLE);
	    	viewFifth.setVisibility(View.VISIBLE);
		   
	   }
	}

}
