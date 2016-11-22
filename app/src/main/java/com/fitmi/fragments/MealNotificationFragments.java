package com.fitmi.fragments;

import java.util.ArrayList;
import java.util.Calendar;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


import com.fitmi.R;
import com.fitmi.adapter.MealsNotificationAdapter;
import com.fitmi.adapter.SingleRowDateListAdapter;
import com.fitmi.utils.Constants;

public class MealNotificationFragments extends BaseFragment {

	int mRootId = 0;

	@InjectView(R.id.Heading)
	public TextView heading;
	
	@InjectView(R.id.SubHeading_UnitSettings)
	public TextView SubHeading_UnitSettings;
	

	@InjectView(R.id.Back)
	public ImageView back;
	
	/*@InjectView(R.id.btn_back_pink)
	public Button btn_back_pink;*/
	
	
	@InjectView(R.id.UserName_ItemUser)
	TextView userName;
	@InjectView(R.id.textalarmtime)
	TextView textalarmtime;
	
	@InjectView(R.id.toggle_btn_on)
	ToggleButton toggle_btn_on;
	
	
	@InjectView(R.id.UserName_ItemUser2)
	TextView userName2;
	@InjectView(R.id.textalarmtime2)
	TextView textalarmtime2;
	
	@InjectView(R.id.toggle_btn_on2)
	ToggleButton toggle_btn_on2;
	
	
	@InjectView(R.id.UserName_ItemUser3)
	TextView userName3;
	@InjectView(R.id.textalarmtime3)
	TextView textalarmtime3;
	
	@InjectView(R.id.toggle_btn_on3)
	ToggleButton toggle_btn_on3;
	
	
	
	
	@InjectView(R.id.backLiner)
	LinearLayout backLiner;

	@InjectView(R.id.Settings)
	public ImageView settings;
/*
	@InjectView(R.id.ListView_Goals)
	ListView ListView_Mynotification;*/
	
	int click_=0;

	MealsNotificationAdapter adapter;
	ArrayList<String> notificationSettingsMenu;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/* Inflate the layout for this fragment */
		View view = inflater.inflate(R.layout.fragment_mealnotification, container, false);

		Bundle bundle = this.getArguments();
		if (bundle != null) {
			mRootId = bundle.getInt("root_id", R.id.root_home_frame);
		}

		ButterKnife.inject(this, view);
		setNullClickListener(view);

		Thread.setDefaultUncaughtExceptionHandler(new
				com.fitmi.CustomExceptionHandler(getActivity().getApplicationContext()));
		heading.setText("Notifications");
		SubHeading_UnitSettings.setText("Food Notifications");
		settings.setVisibility(View.GONE);

		notificationSettingsMenu = new ArrayList<String>();
		notificationSettingsMenu.add("Breakfast ");
		notificationSettingsMenu.add("Lunch ");
		notificationSettingsMenu.add("Dinner ");

		SubHeading_UnitSettings.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				throw new IllegalStateException("THIS IS A CRASH!");
			}
		});
	/*	int a=2014;
		a=a/0;
		
		*/
		
		toggle_btn_on.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			 
	        @Override
	        public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
	          //  text.setText("Status: " + isChecked);
	        	
	        	//Toast.makeText(context, "Status: " + isChecked, Toast.LENGTH_LONG).show();
	        	if(isChecked){
	        		click_=1;
	        		textalarmtime.setVisibility(View.VISIBLE);
	        	CustomTimePickerDialog timePickerDialog = new CustomTimePickerDialog(getActivity(), timeSetListener, 
	                    Calendar.getInstance().get(Calendar.HOUR), 
	                    CustomTimePickerDialog.getRoundedMinute(Calendar.getInstance().get(Calendar.MINUTE) + CustomTimePickerDialog.TIME_PICKER_INTERVAL), true);
	        timePickerDialog.setTitle("Set the time for your breakfast alert");
	        timePickerDialog.show();
	        	}else{
	        	textalarmtime.setVisibility(View.GONE);
	        	textalarmtime.setText("");
	        	}
	        
	    				
	    		
	        }
	    });
		
		
		toggle_btn_on2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			 
	        @Override
	        public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
	          //  text.setText("Status: " + isChecked);
	        	
	        	//Toast.makeText(context, "Status: " + isChecked, Toast.LENGTH_LONG).show();
	        	if(isChecked){
	        		click_=2;
	        		textalarmtime.setVisibility(View.VISIBLE);
	        	CustomTimePickerDialog timePickerDialog = new CustomTimePickerDialog(getActivity(), timeSetListener, 
	                    Calendar.getInstance().get(Calendar.HOUR), 
	                    CustomTimePickerDialog.getRoundedMinute(Calendar.getInstance().get(Calendar.MINUTE) + CustomTimePickerDialog.TIME_PICKER_INTERVAL), true);
	        	timePickerDialog.setTitle("Set the time for your lunch alert");
	        timePickerDialog.show();
	        	}else{
	        	textalarmtime2.setVisibility(View.GONE);
	        	textalarmtime2.setText("");
	        	}
	        	
	        }
	    });
		toggle_btn_on3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			 
	        @Override
	        public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
	          //  text.setText("Status: " + isChecked);
	        	
	        	//Toast.makeText(context, "Status: " + isChecked, Toast.LENGTH_LONG).show();
	        	if(isChecked){
	        		click_=3;
	        		textalarmtime.setVisibility(View.VISIBLE);
	        	CustomTimePickerDialog timePickerDialog = new CustomTimePickerDialog(getActivity(), timeSetListener, 
	                    Calendar.getInstance().get(Calendar.HOUR), 
	                    CustomTimePickerDialog.getRoundedMinute(Calendar.getInstance().get(Calendar.MINUTE) + CustomTimePickerDialog.TIME_PICKER_INTERVAL), true);
	        	timePickerDialog.setTitle("Set the time for your dinner alert");
	        timePickerDialog.show();
	        	}else{
	        	textalarmtime3.setVisibility(View.GONE);
	        	textalarmtime3.setText("");
	        	}
	        	
	        }
	    });
		return view;
	}

	@OnClick(R.id.backLiner)
	public void back() {

		getActivity().onBackPressed();

	}
	

	@OnClick(R.id.Base_Header)
	public void clickHeaderBase() {

	}
    
    public static class CustomTimePickerDialog extends TimePickerDialog{

           public static final int TIME_PICKER_INTERVAL=1;
           private boolean mIgnoreEvent=false;

           public CustomTimePickerDialog(Context context, OnTimeSetListener callBack, int hourOfDay, int minute, boolean is24HourView) {
           super(context, callBack, hourOfDay, minute, is24HourView);
           }
    
           @Override
           public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute) {
               super.onTimeChanged(timePicker, hourOfDay, minute);
               if (!mIgnoreEvent){
                   minute = getRoundedMinute(minute);
                   mIgnoreEvent=true;
                   timePicker.setCurrentMinute(minute);
                   mIgnoreEvent=false;
               }
           }

           public static  int getRoundedMinute(int minute){
                if(minute % TIME_PICKER_INTERVAL != 0){
                   int minuteFloor = minute - (minute % TIME_PICKER_INTERVAL);
                   minute = minuteFloor + (minute == minuteFloor + 1 ? TIME_PICKER_INTERVAL : 0);
                   if (minute == 60)  minute=0;
                }

               return minute;
           }
       }
     
    private OnTimeSetListener timeSetListener = new OnTimeSetListener() {
           @Override
           public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        	 if(click_==1){
        		 textalarmtime.setText(String.format("%02d", hourOfDay) + ":" +String.format("%02d", minute));
        	 }
        	 
        	 else if(click_==2){
        		 textalarmtime2.setText(String.format("%02d", hourOfDay) + ":" +String.format("%02d", minute));
        	 }
        	 
        	 else if(click_==3){
        		 textalarmtime3.setText(String.format("%02d", hourOfDay) + ":" +String.format("%02d", minute));
        	 }
        	 }
     };
     
     @Override
     public void onDestroy() {
    	 
     };
     

}
