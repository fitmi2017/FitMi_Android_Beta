package com.fitmi.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dialog.Alert;
import com.fitmi.R;
import com.fitmi.dao.PlannerWeeklyDAO;
import com.fitmi.fragments.FoodLoggingFragment;
import com.fitmi.fragments.MyActivityFragment;
import com.fitmi.fragments.PlannerFragment;
import com.fitmi.utils.Constants;
import com.fitmi.utils.DateModule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class PlannerAdapter extends BaseAdapter {
	private Context context;
	private Activity activity;
	private ArrayList<PlannerWeeklyDAO> data;
	private ArrayList<String> data1;
	RelativeLayout rl;
	ImageView imagebg;
	String today = "";
	DateModule getDate = new DateModule();
	
	ArrayList<ImageView> breakfast = new ArrayList<ImageView>();
	ArrayList<ImageView> lunch = new ArrayList<ImageView>();
	ArrayList<ImageView> dinner = new ArrayList<ImageView>();
	ArrayList<ImageView> snack = new ArrayList<ImageView>();
	ArrayList<ImageView> activityList = new ArrayList<ImageView>();
	
	public static HashMap<String, Integer> brekMap = new HashMap<String, Integer>();
	public static HashMap<String, Integer> lunchMap = new HashMap<String, Integer>();
	public static HashMap<String, Integer> snackMap = new HashMap<String, Integer>();
	public static HashMap<String, Integer> dinnerMap = new HashMap<String, Integer>();
	public static HashMap<String, Integer> activityMap = new HashMap<String, Integer>();

	//boolean breakfastActive = false,lunchActive = false , dinnerActive = false , snackActive = false , activityActive =false;
	
	int clickItem = -1;
	
	int breakfatId = 0 , lunchId = 0, snackId = 0, dinnerId = 0;

	private static LayoutInflater inflater=null;
	
	FragmentTransaction transaction;
	
	Fragment fragment;

	public PlannerAdapter(Activity a, ArrayList<PlannerWeeklyDAO> d,FragmentTransaction transaction)
	{
		activity = a;
		data=d;
		this.transaction = transaction;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);	
	}

	@Override
	public int getCount() {
		return data.size();
	}
	@Override
	public Object getItem(int position) {
		return position;
	}
	@Override
	public long getItemId(int position) {
		return position;
	}


	public View getView(int position, View convertView, ViewGroup parent) {

		View vi=convertView;

		final ViewHolder holder;

		if(convertView==null){

			holder = new ViewHolder();
			vi = inflater.inflate(R.layout.plannerlistrow, null);

			holder.date = (TextView)vi.findViewById(R.id.plannerdate); // title
			holder.imgBreakfast = (ImageView)vi.findViewById(R.id.imgBreakfast);
			holder.imgLunch = (ImageView)vi.findViewById(R.id.imgLunch);
			holder.imgDinner = (ImageView)vi.findViewById(R.id.imgDinner);
			holder.imgSnack = (ImageView)vi.findViewById(R.id.imgSnack);
			holder.imgActivity = (ImageView)vi.findViewById(R.id.imgActivity);	

			vi.setTag(holder);
		}
		else{

			holder = (ViewHolder) vi.getTag();
		}	

		if(PlannerFragment.listItemCliclEnable){


			if(PlannerFragment.activityClickEnable){
				
				vi.setEnabled(true);
				holder.date.setEnabled(true);
				holder.imgBreakfast.setEnabled(false);
				holder.imgLunch.setEnabled(false);
				holder.imgDinner.setEnabled(false);
				holder.imgSnack.setEnabled(false);
				holder.imgActivity.setEnabled(true);

				vi.setClickable(true);
				holder.date.setClickable(true);
				holder.imgBreakfast.setClickable(false);
				holder.imgLunch.setClickable(false);
				holder.imgDinner.setClickable(false);
				holder.imgSnack.setClickable(false);
				holder.imgActivity.setClickable(true);
				
			}else{
				
				
				vi.setEnabled(true);
				holder.date.setEnabled(true);
				holder.imgBreakfast.setEnabled(true);
				holder.imgLunch.setEnabled(true);
				holder.imgDinner.setEnabled(true);
				holder.imgSnack.setEnabled(true);
				holder.imgActivity.setEnabled(false);

				vi.setClickable(true);
				holder.date.setClickable(true);
				holder.imgBreakfast.setClickable(true);
				holder.imgLunch.setClickable(true);
				holder.imgDinner.setClickable(true);
				holder.imgSnack.setClickable(true);
				holder.imgActivity.setClickable(false);
			}


		}else{

			vi.setClickable(false);
			holder.date.setClickable(false);
			holder.imgBreakfast.setClickable(false);
			holder.imgLunch.setClickable(false);
			holder.imgDinner.setClickable(false);
			holder.imgSnack.setClickable(false);
			holder.imgActivity.setClickable(false);

			vi.setEnabled(false);
			holder.date.setEnabled(false);
			holder.imgBreakfast.setEnabled(false);
			holder.imgLunch.setEnabled(false);
			holder.imgDinner.setEnabled(false);
			holder.imgSnack.setEnabled(false);
			holder.imgActivity.setEnabled(false);		

		}

		PlannerWeeklyDAO object = data.get(position);
		
		object.setBreakfast(holder.imgBreakfast);
		object.setLunch(holder.imgLunch);
		object.setDinner(holder.imgDinner);
		object.setSnack(holder.imgSnack);
		object.setActivityList(holder.imgActivity);
		
		Tag tag = new Tag();
		tag.position = position;
		tag.date = data.get(position).getDateSet();
		tag.object = object;
		
		
		holder.date.setText(data.get(position).getDateSet());
		
		if(object.isClickBreackfast()){			
			
			object.getBreakfast().setBackgroundResource(R.drawable.breakfast_color);
			object.getBreakfast().setAlpha(1.0f);
			
		}else{
			
			if(data.get(position).getBreakfastActive() == 0){

				holder.imgBreakfast.setBackgroundResource(R.drawable.breakfast2);
				holder.imgBreakfast.setAlpha(0.5f);
				//breakfastActive = false;	
				
			//making true avinash	//object.setBreakfastActivate(false);
	
				object.setBreakfastActivate(true);
				if(PlannerFragment.listItemCliclEnable){
					holder.imgBreakfast.setEnabled(true);
					holder.imgBreakfast.setClickable(true);
				}else{
					holder.imgBreakfast.setEnabled(false);
					holder.imgBreakfast.setClickable(false);
				}
				
				
			}else{
				holder.imgBreakfast.setBackgroundResource(R.drawable.breakfast2);
				holder.imgBreakfast.setAlpha(1.0f);
				//breakfastActive = true;
				object.setBreakfastActivate(true);
				holder.imgBreakfast.setEnabled(true);
				holder.imgBreakfast.setClickable(true);
			}
		}
		
		//holder.imgBreakfast.setTag(breakfastActive);
		holder.imgBreakfast.setTag(tag);

		if(object.isClickLunch()){
			
			object.getLunch().setBackgroundResource(R.drawable.lunch_color);
			object.getLunch().setAlpha(1.0f);
			
		}else{
			
			if(data.get(position).getLunchActive() == 0){

				holder.imgLunch.setBackgroundResource(R.drawable.lunch2);
				holder.imgLunch.setAlpha(0.5f);
				//lunchActive = false;
				if(PlannerFragment.listItemCliclEnable){
					holder.imgLunch.setEnabled(true);
					holder.imgLunch.setClickable(true);
				}else{
					holder.imgLunch.setEnabled(false);
					holder.imgLunch.setClickable(false);
				}
				//making true avinash	//object.setLunchActivate(false);
				object.setLunchActivate(true);
				
			}else{
				holder.imgLunch.setBackgroundResource(R.drawable.lunch2);
				holder.imgLunch.setAlpha(1.0f);
				//lunchActive = true;
				holder.imgLunch.setEnabled(true);
				holder.imgLunch.setClickable(true);
				object.setLunchActivate(true);
			}
		}
		
		//holder.imgLunch.setTag(lunchActive);
		holder.imgLunch.setTag(tag);

		if(object.isClickDinner()){
			
			object.getDinner().setBackgroundResource(R.drawable.dinner_color);
			object.getDinner().setAlpha(1.0f);
			
		}else{
			
			if(data.get(position).getDinnerActive() == 0){

				holder.imgDinner.setBackgroundResource(R.drawable.dinner1);
				holder.imgDinner.setAlpha(0.5f);
				//dinnerActive = false;
				if(PlannerFragment.listItemCliclEnable){
					holder.imgDinner.setEnabled(true);
					holder.imgDinner.setClickable(true);
				}else{
					holder.imgDinner.setEnabled(false);
					holder.imgDinner.setClickable(false);
				}
				//making true avinash	//object.setDinnerActivate(false);
				object.setDinnerActivate(true);
				
			}else{
				holder.imgDinner.setBackgroundResource(R.drawable.dinner1);
				holder.imgDinner.setAlpha(1.0f);
				//dinnerActive = true;
				holder.imgDinner.setEnabled(true);
				holder.imgDinner.setClickable(true);
				object.setDinnerActivate(true);
			}
		}
		
		//holder.imgDinner.setTag(dinnerActive);
		holder.imgDinner.setTag(tag);

		if(object.isClickSnacks()){
			
			object.getSnack().setBackgroundResource(R.drawable.snack_color);
			object.getSnack().setAlpha(1.0f);
			
		}else{
			
			if(data.get(position).getSnackActive() == 0){

				holder.imgSnack.setBackgroundResource(R.drawable.snack2);
				holder.imgSnack.setAlpha(0.5f);
				//snackActive = false;
				if(PlannerFragment.listItemCliclEnable){
					holder.imgSnack.setEnabled(true);
					holder.imgSnack.setClickable(true);
				}else{
					holder.imgSnack.setEnabled(false);
					holder.imgSnack.setClickable(false);
				}
				//making true avinash	//object.setSnackActivate(false);
				object.setSnackActivate(true);
			
				
			}else{
				
				holder.imgSnack.setBackgroundResource(R.drawable.snack2);
				holder.imgSnack.setAlpha(1.0f);
				//snackActive = true;
				holder.imgSnack.setEnabled(true);
				holder.imgSnack.setClickable(true);
				object.setSnackActivate(true);
			}
		}

		//holder.imgSnack.setTag(snackActive);
		holder.imgSnack.setTag(tag);

		if(object.isClickActivity()){
			
			object.getActivityList().setBackgroundResource(R.drawable.activity_color);
			object.getActivityList().setAlpha(1.0f);
			
		}else{
			if(data.get(position).getActivityActive() == 0){

				holder.imgActivity.setBackgroundResource(R.drawable.activity);
				holder.imgActivity.setAlpha(0.5f);
				//activityActive = false;
				if(PlannerFragment.listItemCliclEnable){
					
					holder.imgActivity.setEnabled(true);
					holder.imgActivity.setClickable(true);
				}else{
					
					holder.imgActivity.setEnabled(false);
					holder.imgActivity.setClickable(false);
				}
				//making true avinash	//object.setActivityActivate(false);
				object.setActivityActivate(true);
			
				
			}else{
				holder.imgActivity.setBackgroundResource(R.drawable.activity);
				holder.imgActivity.setAlpha(1.0f);
				//activityActive = true;
				holder.imgActivity.setEnabled(true);
				holder.imgActivity.setClickable(true);
				object.setActivityActivate(true);
			}
		}
		
		if(PlannerFragment.donelay==true&&PlannerFragment.activityclickweek==false)
		{
			holder.imgActivity.setEnabled(false);
			holder.imgActivity.setClickable(false);
		}
		
		if(PlannerFragment.donelay==true&&PlannerFragment.mealclickkweek==false)
		{
			holder.imgSnack.setEnabled(false);
			holder.imgSnack.setClickable(false);
			
			holder.imgBreakfast.setEnabled(false);
			holder.imgBreakfast.setClickable(false);
			
			holder.imgDinner.setEnabled(false);
			holder.imgDinner.setClickable(false);
			
			holder.imgLunch.setEnabled(false);
			holder.imgLunch.setClickable(false);
			
		}
		
		holder.imgActivity.setTag(tag);

		holder.imgBreakfast.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub				

				Tag tag = (Tag) v.getTag();
				
				if(tag.object.isBreakfastActivate() && PlannerFragment.donelay==false){
					
					//transaction = getFragmentManager().beginTransaction();			
					
					Constants.conditionDate = convertDateFormat(tag.date);	
					
					if(fragment !=null)					
					  transaction.remove(fragment);
					
					FoodLoggingFragment foodLogin = new FoodLoggingFragment();
					fragment = foodLogin;
					Bundle bundle = new Bundle();
					bundle.putInt("mealid", 1);
					bundle.putString("foodtype", "Breakfast");	
					bundle.putBoolean("replace", false);
					bundle.putBoolean("log", false);
					
	/*				String setDate =tag.date;
				//	setDate = getDate.dateToday(setDate);
					today = getDate.conditionDateFormat(setDate);
					//Constants.conditionDate = today;
					//avinash next and prv date changes
					com.fitmi.utils.Constants.sTempDate=today;
					Constants.sDate= getDate.sDateFormat(setDate);
					Constants.postDate=getDate.getPostDateFormat(setDate);*/
					//String setDate =tag.date;
					String setDate=holder.date.getText().toString().trim();	
					setDate = getDate.dateToday(setDate);
					Constants.conditionDate = getDate.conditionDateFormat(setDate);
					Constants.postDate =getDate.getFormatDateSearchInsert(setDate);
					Constants.sTempDate= Constants.conditionDate;
					Constants.sDate=getDate.sDateFormat(setDate);
					/*transaction.add(R.id.root_planner_frame, foodLogin,
							"FoodLoggingFragment");*/	
					transaction.replace(R.id.root_planner_frame, foodLogin,
							"FoodLoggingFragment");
					foodLogin.setArguments(bundle);
					transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
					transaction.addToBackStack(null);
					transaction.commit();
					
				}else{
					
					if(tag.object.getBreakfastClick() == 0){

						tag.object.getBreakfast().setBackgroundResource(R.drawable.breakfast_color);
						tag.object.getBreakfast().setAlpha(1.0f);
						tag.object.setBreakfastClick(1);				
						breakfatId = 1;					
						brekMap.put(tag.date, 1);
						tag.object.setClickBreackfast(true);
						
					}else{
						tag.object.getBreakfast().setBackgroundResource(R.drawable.breakfast2);
						tag.object.setBreakfastClick(0);		
						breakfatId = -1;
						brekMap.remove(tag.date);
						tag.object.setClickBreackfast(false);
					}
				}

			}
		});


		holder.imgLunch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Tag tag = (Tag) v.getTag();
				
				if(tag.object.isLunchActivate()&&PlannerFragment.donelay==false){	

					Constants.conditionDate = convertDateFormat(tag.date);	
					
					if(fragment !=null)					
						 transaction.remove(fragment);
					
					FoodLoggingFragment foodLogin = new FoodLoggingFragment();
					fragment = foodLogin;
					Bundle bundle = new Bundle();
					bundle.putInt("mealid", 2);
					bundle.putString("foodtype", "Lunch");
					bundle.putBoolean("replace", false);
					bundle.putBoolean("log", false);

					String setDate=holder.date.getText().toString().trim();	
					setDate = getDate.dateToday(setDate);
					Constants.conditionDate = getDate.conditionDateFormat(setDate);
					Constants.postDate =getDate.getFormatDateSearchInsert(setDate);
					Constants.sTempDate= Constants.conditionDate;
					Constants.sDate=getDate.sDateFormat(setDate);
					/*transaction.add(R.id.root_planner_frame, foodLogin,
							"FoodLoggingFragment");*/
					transaction.replace(R.id.root_planner_frame, foodLogin,
							"FoodLoggingFragment");
					foodLogin.setArguments(bundle);
					transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
					transaction.addToBackStack(null);
					transaction.commit();
					
				}else{
					
					if(tag.object.getLunchClick() == 0){
						tag.object.getLunch().setBackgroundResource(R.drawable.lunch_color);
						tag.object.getLunch().setAlpha(1.0f);
						tag.object.setLunchClick(1);				
						lunchId = 2;
						lunchMap.put(tag.date, 2);
						tag.object.setClickLunch(true);
					}else{
						tag.object.getLunch().setBackgroundResource(R.drawable.lunch2);
						tag.object.setLunchClick(0);		
						lunchId = -1;
						lunchMap.remove(tag.date);
						tag.object.setClickLunch(false);
					}
				}

				
			}
		});
		holder.imgDinner.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Tag tag = (Tag) v.getTag();
				
				if(tag.object.isDinnerActivate()&&PlannerFragment.donelay==false){
					
					Constants.conditionDate = convertDateFormat(tag.date);	
					
					if(fragment !=null)					
						 transaction.remove(fragment);
					
					FoodLoggingFragment foodLogin = new FoodLoggingFragment();
					fragment = foodLogin;
					Bundle bundle = new Bundle();
					bundle.putInt("mealid", 3);
					bundle.putString("foodtype", "Dinner");
					bundle.putBoolean("replace", false);
					bundle.putBoolean("log", false);

					String setDate=holder.date.getText().toString().trim();	
					setDate = getDate.dateToday(setDate);
					Constants.conditionDate = getDate.conditionDateFormat(setDate);
					Constants.postDate =getDate.getFormatDateSearchInsert(setDate);
					Constants.sTempDate= Constants.conditionDate;
					Constants.sDate=getDate.sDateFormat(setDate);
					/*transaction.add(R.id.root_planner_frame, foodLogin,
							"FoodLoggingFragment");*/
					transaction.replace(R.id.root_planner_frame, foodLogin,
							"FoodLoggingFragment");
					foodLogin.setArguments(bundle);
					transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
					transaction.addToBackStack(null);
					transaction.commit();
					
				}else{
					
					if(tag.object.getDinnerClick() == 0){
						tag.object.getDinner().setBackgroundResource(R.drawable.dinner_color);
						tag.object.getDinner().setAlpha(1.0f);
						//dinner.get(tag.position).setBackgroundResource(R.drawable.dinner_color);
						//dinner.get(tag.position).setAlpha(1.0f);
						tag.object.setDinnerClick(1);					
						dinnerId = 3;
						dinnerMap.put(tag.date, 3);
						tag.object.setClickDinner(true);
					}else{
						tag.object.getDinner().setBackgroundResource(R.drawable.dinner1);
						tag.object.setDinnerClick(0);
						dinnerId = -1;
						dinnerMap.remove(tag.date);
						tag.object.setClickDinner(false);
					}
				}

			}
		});
		holder.imgSnack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Tag tag = (Tag) v.getTag();
				
				if(tag.object.isSnackActivate()&&PlannerFragment.donelay==false){
					Constants.conditionDate = convertDateFormat(tag.date);	
					
					if(fragment !=null)					
						 transaction.remove(fragment);
					
					FoodLoggingFragment foodLogin = new FoodLoggingFragment();
					fragment = foodLogin;
					Bundle bundle = new Bundle();
					bundle.putInt("mealid", 4);
					bundle.putString("foodtype", "Snack");
					bundle.putBoolean("replace", false);
					bundle.putBoolean("log", false);
					
					String setDate=holder.date.getText().toString().trim();	
					setDate = getDate.dateToday(setDate);
					Constants.conditionDate = getDate.conditionDateFormat(setDate);
					Constants.postDate =getDate.getFormatDateSearchInsert(setDate);
					Constants.sTempDate= Constants.conditionDate;
					Constants.sDate=getDate.sDateFormat(setDate);
					/*transaction.add(R.id.root_planner_frame, foodLogin,
							"FoodLoggingFragment");*/
					
					transaction.replace(R.id.root_planner_frame, foodLogin,
							"FoodLoggingFragment");
					
					foodLogin.setArguments(bundle);
					transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
					transaction.addToBackStack(null);
					transaction.commit();
					
				}else{
					
					if(tag.object.getSnackclick() == 0){
						tag.object.getSnack().setBackgroundResource(R.drawable.snack_color);
						tag.object.getSnack().setAlpha(1.0f);
						//snack.get(tag.position).setBackgroundResource(R.drawable.snack_color);
						//snack.get(tag.position).setAlpha(1.0f);
						tag.object.setSnackclick(1);					
						snackId = 4;
						snackMap.put(tag.date, 4);
						tag.object.setClickSnacks(true);
					}else{
						tag.object.getSnack().setBackgroundResource(R.drawable.snack2);
						tag.object.setSnackclick(0);
						snackId = -1;
						snackMap.remove(tag.date);
						tag.object.setClickSnacks(false);
					}
				}

				

			}
		});
		holder.imgActivity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Tag tag = (Tag) v.getTag();
				
				if(tag.object.isActivityActivate()&&PlannerFragment.donelay==false){	
					
					Constants.conditionDate = convertDateFormat(tag.date);	
					
					if(fragment !=null)					
						 transaction.remove(fragment);
					
					MyActivityFragment activity = new MyActivityFragment();					
					fragment = activity;		
					String setDate=holder.date.getText().toString().trim();	
					setDate = getDate.dateToday(setDate);
					Constants.conditionDate = getDate.conditionDateFormat(setDate);
					Constants.postDate =getDate.getFormatDateSearchInsert(setDate);
					Constants.sTempDate= Constants.conditionDate;
					Constants.sDate=getDate.sDateFormat(setDate);
					/*transaction.add(R.id.root_planner_frame, activity,
							"FoodLoggingFragment");*/
					transaction.replace(R.id.root_planner_frame, activity,
							"FoodLoggingFragment");
					transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
					transaction.addToBackStack(null);
					transaction.commit();
					
				}else{
					
					if(tag.object.getActivityClick()==0){
						tag.object.getActivityList().setBackgroundResource(R.drawable.activity_color);
						tag.object.getActivityList().setAlpha(1.0f);
						//activityList.get(tag.position).setBackgroundResource(R.drawable.activity_color);
						//activityList.get(tag.position).setAlpha(1.0f);
						tag.object.setActivityClick(1);					
						activityMap.put(tag.date, 1);
						tag.object.setClickActivity(true);
					}else{

						tag.object.getActivityList().setBackgroundResource(R.drawable.activity);
						tag.object.setActivityClick(0);		
						snackMap.remove(tag.date);
						tag.object.setClickActivity(false);
					}
				}

				
			}
		});


		return vi;
	}

	class ViewHolder{

		TextView date;
		ImageView imgBreakfast;
		ImageView imgLunch;
		ImageView imgDinner;
		ImageView imgSnack;
		ImageView imgActivity;
	}

	
	class Tag
	{
		int position=0;
		String date = "";
		PlannerWeeklyDAO object;		
	
	}
	
	public String convertDateFormat(String stringDate)
	{
		SimpleDateFormat df = new SimpleDateFormat("EEEE, MMM dd, yyyy");
		SimpleDateFormat targetFormatter = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");	

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);


		try {
			cal.setTime(df.parse(stringDate));		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String formattedDate = targetFormatter.format(cal.getTime());
		return formattedDate;
	}

}