package com.fitmi.fragments;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import android.graphics.Color;
import android.media.Image.Plane;
import android.os.Bundle;
import android.app.Dialog;
import android.app.LauncherActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.db.DatabaseHelper;
import com.db.modules.ActivityModule;
import com.db.modules.FoodLoginModule;
import com.dialog.Alert;
import com.fitmi.R;
import com.fitmi.adapter.PlannerAdapter;
import com.fitmi.adapter.PlannerGridAdapter;
import com.fitmi.dao.CalenderFirsLastDay;
import com.fitmi.dao.FitmiFoodLogDAO;
import com.fitmi.dao.FoodActivityActive;
import com.fitmi.dao.PlannerMealType;
import com.fitmi.dao.PlannerWeeklyDAO;
import com.fitmi.utils.Constants;
import com.fitmi.utils.DateModule;
import com.fitmi.utils.FirstLastDayWeekMonthly;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlannerFragment extends BaseFragment {

	@InjectView(R.id.plannerlist)
	ListView plannerlistview;

	@InjectView(R.id.gridview)
	GridView gv;

	@InjectView(R.id.Heading)
	public TextView heading;

	@InjectView(R.id.Back)
	public ImageView back;

	@InjectView(R.id.backLiner)
	LinearLayout backLiner;

	@InjectView(R.id.pluslayout)
	public RelativeLayout pluslayout;

	@InjectView(R.id.donelayout)
	public RelativeLayout donelayout;

	@InjectView(R.id.dailybtn)
	Button daily;

	@InjectView(R.id.monthbtn)
	Button monthly;

	@InjectView(R.id.weekbtn)
	Button weekly;

	@InjectView(R.id.twocircle)
	LinearLayout twocircle;

	@InjectView(R.id.fivelayout)
	LinearLayout fivelayout;

	@InjectView(R.id.Date)
	TextView dateTextView;

	@InjectView(R.id.plannerActivity)
	ImageView plannerActivity;

	@InjectView(R.id.plannersnack)
	ImageView plannersnack;

	@InjectView(R.id.plannerDinner)
	ImageView plannerDinner;

	@InjectView(R.id.plannerLunch)
	ImageView plannerLunch;

	@InjectView(R.id.plannerBreakfast)
	ImageView plannerBreakfast;

	@InjectView(R.id.BreakFastLinear)
	LinearLayout breakFast;

	@InjectView(R.id.LunchLinear)
	LinearLayout LunchLinear;

	@InjectView(R.id.DinnerLinear)
	LinearLayout DinnerLinear;

	@InjectView(R.id.SnackLinear)
	LinearLayout SnackLinear;

	@InjectView(R.id.ActivitiesLinear)
	LinearLayout ActivitiesLinear;

	@InjectView(R.id.textTitle)
	TextView textTitle;

	/*@InjectView(R.id.txtMnthDetail)
	TextView txtMnthDetail;*/

	@InjectView(R.id.layoutMonth)
	LinearLayout layoutMonth;

	DatabaseHelper databaseObject;

	int clickItem = -1;
	PlannerAdapter padpt;

	public static boolean listItemCliclEnable = false;
	public static boolean activityClickEnable = false;
	public static boolean activityClickEnablegrid = false;
	public static boolean gridItemCliclEnable = false;
	public static boolean donelay = false;
	
	public static boolean mealclickkweek = true;
	public static boolean activityclickweek = true;

	FoodLoginModule foodLogModule;
	ActivityModule activityModule = new ActivityModule();

	PlannerMealType plannerMeal = new PlannerMealType();
	DateModule dateModule = new DateModule();
	ArrayList<PlannerWeeklyDAO> foodSet = new ArrayList<PlannerWeeklyDAO>();

	boolean breakfastActive = false,lunchActive = false , dinnerActive = false , snackActive = false , activityActive =false;

	boolean is_breakfastConsumed=false;
	boolean is_lunchConsumed=false;
	boolean is_dinnerConsumed=false;
	boolean is_snackConsumed=false;
	///////////////// activity////////////////////////////////////////

	boolean is_weightLifting=false;
	boolean is_Boxing=false;
	boolean is_Jumping=false;
	boolean is_Swimming=false;
	boolean is_walking=false;
	boolean is_Gymnastic=false;

	int breakfastClick = 0,lunchClick = 0, snackclick = 0, dinnerClick =0, activityClick = 0;
	int breakfatId = 0 , lunchId = 0, snackId = 0, dinnerId = 0;
	int clickButton = 1;

	String stringDate = "";
	String today = "";

	int btnDateClick = 1;
	PlannerGridAdapter pga;
	ArrayList<FoodActivityActive> foodSetGrid = new ArrayList<FoodActivityActive>();
	FirstLastDayWeekMonthly firstLastDayDate = new FirstLastDayWeekMonthly();
	
	FragmentTransaction transaction;// = getFragmentManager().beginTransaction();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_planner, container, false);

		ButterKnife.inject(this, v);
		setNullClickListener(v);
		heading.setText("Planner");
		back.setVisibility(View.GONE);
		
		System.out.println("com.fitmi.utils.Constants.sTempDate => " + Constants.sTempDate);
		if (Constants.sTempDate.length() == 0) {
			//Constants.sDate = "Tuesday, February 10, 2015";

			Calendar c = Calendar.getInstance();
			System.out.println("Current time => " + c.getTime());	
			//	SimpleDateFormat df = new SimpleDateFormat("YYYY-MMM-dd hh:mm:ss");
			//SimpleDateFormat df = new SimpleDateFormat("EEEE, MMM dd, yyyy");
			SimpleDateFormat df = new SimpleDateFormat("EEEE, MMM dd, yyyy");
			Constants.sDate = df.format(c.getTime());


			SimpleDateFormat postFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");			
			Constants.postDate = postFormat.format(c.getTime());


			SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
			Constants.conditionDate = dformat.format(c.getTime());
			today=	Constants.sTempDate;
			Constants.sTempDate= Constants.conditionDate;
			System.out.println("Calender post format :"+ Constants.postDate);
			//Toast.makeText(getActivity(), Constants.postDate, Toast.LENGTH_LONG).show();
			
		}
		else{
			today=	Constants.sTempDate;
		}
		Constants.conditionDate = today;
		Constants.calendreSet = 1;
		Constants.pageLog = 1;
		Constants.fragmentNumber = 8;
		Constants.fragmentSet = true;
		foodLogModule = new FoodLoginModule(getActivity());
//avinash changes 
		/*plannerBreakfast.setClickable(false);
		plannerLunch.setClickable(false);
		plannerDinner.setClickable(false);
		plannersnack.setClickable(false);
		plannerActivity.setClickable(false);

		plannerBreakfast.setEnabled(false);
		plannerLunch.setEnabled(false);
		plannerDinner.setEnabled(false);
		plannersnack.setEnabled(false);
		plannerActivity.setEnabled(false);*/
		
		transaction = getFragmentManager().beginTransaction();

		getActivity().registerReceiver(dateSetReceiver,
				new IntentFilter("plannerhome"));

		getActivity().registerReceiver(mBroadcastReceiver,
				new IntentFilter("plannerGrid"));

		getActivity().registerReceiver(mBroadcastReceiverlist,
				new IntentFilter("plannerList"));

		databaseObject = new DatabaseHelper(getActivity());
		try {

			databaseObject.createDatabase();

			databaseObject.openDatabase();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		plannerSet();


		padpt=new PlannerAdapter(getActivity(),foodSet,transaction);
		plannerlistview.setAdapter(padpt);

		pga = new PlannerGridAdapter(getActivity(), foodSetGrid);
		gv.setAdapter(pga);

		daily.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				daily.setBackgroundColor(Color.parseColor("#B51F6B"));
				monthly.setBackgroundColor(Color.parseColor("#D379A6"));
				weekly.setBackgroundColor(Color.parseColor("#D379A6"));

				if(twocircle.getVisibility() == View.VISIBLE)
					twocircle.setVisibility(View.GONE);
				
				if (Constants.sTempDate.length() == 0) {
					//Constants.sDate = "Tuesday, February 10, 2015";

					Calendar c = Calendar.getInstance();
					System.out.println("Current time => " + c.getTime());	
					//	SimpleDateFormat df = new SimpleDateFormat("YYYY-MMM-dd hh:mm:ss");
					//SimpleDateFormat df = new SimpleDateFormat("EEEE, MMM dd, yyyy");
					SimpleDateFormat df = new SimpleDateFormat("EEEE, MMM dd, yyyy");
					Constants.sDate = df.format(c.getTime());


					SimpleDateFormat postFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");			
					Constants.postDate = postFormat.format(c.getTime());


					SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
					Constants.conditionDate = dformat.format(c.getTime());
					Constants.sTempDate= Constants.conditionDate;
					System.out.println("Calender post format :"+ Constants.postDate);
					//Toast.makeText(getActivity(), Constants.postDate, Toast.LENGTH_LONG).show();
					
				}
				else{
					today=	Constants.sTempDate;
				}

				btnDateClick = 1;


				plannerlistview.setAlpha(1.0f);	
				gv.setAlpha(1.0f);
				fivelayout.setAlpha(1.0f);

				textTitle.setText("My Daily Plan");
				//txtMnthDetail.setVisibility(View.GONE);
				layoutMonth.setVisibility(View.GONE);

				plannerlistview.setVisibility(View.GONE);
				fivelayout.setVisibility(View.VISIBLE);
				//gv.setVisibility(View.GONE);
				plannerSet();
				dateTextView.setText(Constants.sDate);

			}
		});
		monthly.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				daily.setBackgroundColor(Color.parseColor("#D379A6"));
				monthly.setBackgroundColor(Color.parseColor("#B51F6B"));
				weekly.setBackgroundColor(Color.parseColor("#D379A6"));		

				if(twocircle.getVisibility() == View.VISIBLE)
					twocircle.setVisibility(View.GONE);

				//txtMnthDetail.setVisibility(View.VISIBLE);
				layoutMonth.setVisibility(View.VISIBLE);
				textTitle.setText("My Monthly Plan");
				plannerlistview.setVisibility(View.GONE);
				fivelayout.setVisibility(View.GONE);
				//gv.setVisibility(View.VISIBLE);


				plannerlistview.setAlpha(1.0f);	
				gv.setAlpha(1.0f);
				fivelayout.setAlpha(1.0f);
				gridItemCliclEnable = true;
				listItemCliclEnable = true;
				btnDateClick = 3;
				dateTextView.setText(stringDate);
				//needed
			//	setPlannerMonthly(stringDate);
				if(stringDate.length()>0)
				{
					setPlannerMonthly(stringDate);
				}else{
					setPlannerMonthly(Constants.sDate);
				}
				
			}
		});
		
		//current work need to complete
		weekly.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				daily.setBackgroundColor(Color.parseColor("#D379A6"));
				monthly.setBackgroundColor(Color.parseColor("#D379A6"));
				weekly.setBackgroundColor(Color.parseColor("#B51F6B"));
				//gotoPlannerList();

				if(twocircle.getVisibility() == View.VISIBLE)
					twocircle.setVisibility(View.GONE);

				plannerlistview.setAlpha(1.0f);	
				gv.setAlpha(1.0f);
				fivelayout.setAlpha(1.0f);

				layoutMonth.setVisibility(View.GONE);
				//txtMnthDetail.setVisibility(View.GONE);
				plannerlistview.setVisibility(View.VISIBLE);
				fivelayout.setVisibility(View.GONE);
				textTitle.setText("My Weekly Plan");
				btnDateClick = 2;
			
				//gv.setVisibility(View.GONE);
				setPlannerListWeekly(stringDate);		

			}
		});

		donelayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pluslayout.setVisibility(View.VISIBLE);
				donelayout.setVisibility(View.GONE);
				twocircle.setVisibility(View.GONE);
				fivelayout.setAlpha(1.0f);		
				PlannerFragment.donelay=false;
				PlannerFragment.mealclickkweek=true;
				PlannerFragment.activityclickweek=true;
				
				switch (btnDateClick) {
				case 1:
					Log.e("case 1: ","case 1: working");
					if(breakfatId>0){
						for(int i=0;i<Constants.foodLogData.size();i++){

							FitmiFoodLogDAO fitmiFoodLogData = Constants.foodLogData.get(i);
							fitmiFoodLogData.setMealId(String.valueOf(breakfatId));
							FoodLoginModule.insertFitmifoodLogTable(fitmiFoodLogData, databaseObject);
						}	

						plannerBreakfast.setBackgroundResource(R.drawable.breakfast2);
						breakfastClick = 0;
						breakfastActive=true;
						//avin
					}else{
						plannerBreakfast.setClickable(false);
						plannerBreakfast.setEnabled(false);
						breakfastActive=false;
					
					}

					if(lunchId>0){
						for(int i=0;i<Constants.foodLogData.size();i++){

							FitmiFoodLogDAO fitmiFoodLogData = Constants.foodLogData.get(i);
							fitmiFoodLogData.setMealId(String.valueOf(lunchId));
							FoodLoginModule.insertFitmifoodLogTable(fitmiFoodLogData, databaseObject);
						}	

						plannerLunch.setBackgroundResource(R.drawable.lunch2);
						lunchClick = 0;
						lunchActive=true;
					}
					else{
						plannerLunch.setClickable(false);
						plannerLunch.setEnabled(false);
						lunchActive=false;
					
					}
					if(snackId>0){
						for(int i=0;i<Constants.foodLogData.size();i++){

							FitmiFoodLogDAO fitmiFoodLogData = Constants.foodLogData.get(i);
							fitmiFoodLogData.setMealId(String.valueOf(snackId));
							FoodLoginModule.insertFitmifoodLogTable(fitmiFoodLogData, databaseObject);
						}

						plannersnack.setBackgroundResource(R.drawable.snack2);
						snackclick = 0;
						snackActive=true;
					}
					else{
						plannersnack.setClickable(false);
						plannersnack.setEnabled(false);
						snackActive=false;
					
					}
					if(dinnerId>0){
						for(int i=0;i<Constants.foodLogData.size();i++){

							FitmiFoodLogDAO fitmiFoodLogData = Constants.foodLogData.get(i);
							fitmiFoodLogData.setMealId(String.valueOf(dinnerId));
							FoodLoginModule.insertFitmifoodLogTable(fitmiFoodLogData, databaseObject);
						}	

						plannerDinner.setBackgroundResource(R.drawable.dinner1);
						dinnerClick = 0;
						dinnerActive=true;
					}else{
						plannerDinner.setClickable(false);
						plannerDinner.setEnabled(false);
						dinnerActive=false;
					
					}

					if(activityClick>0){

						for(int i=0;i<Constants.activityLogData.size();i++){	

							ActivityModule.execiseloginsertion(databaseObject,Constants.activityLogData.get(i));							
						}	

						plannerActivity.setBackgroundResource(R.drawable.activity);
						plannerActivity.setAlpha(1.0f);
						activityActive=true;
					}
					else{
						plannerActivity.setClickable(false);
						plannerActivity.setEnabled(false);
						activityActive=false;
					
					}

					if(Constants.foodLogData.size()>0)
						Constants.foodLogData.clear();

/*					plannerBreakfast.setClickable(false);
					plannerLunch.setClickable(false);
					plannerDinner.setClickable(false);
					plannersnack.setClickable(false);
					plannerActivity.setClickable(false);

					plannerBreakfast.setEnabled(false);
					plannerLunch.setEnabled(false);
					plannerDinner.setEnabled(false);
					plannersnack.setEnabled(false);
					plannerActivity.setEnabled(false);*/

					break;

				case 2:

					Log.e("case 2: ","case 2: working");
					
					if(PlannerAdapter.brekMap.size()>0){


						for(int i=0;i<Constants.foodLogData.size();i++){	

							for ( String key : PlannerAdapter.brekMap.keySet() ) {

								String date = convertDateFormat(key);
								FitmiFoodLogDAO fitmiFoodLogData = Constants.foodLogData.get(i);
								fitmiFoodLogData.setMealId(String.valueOf(1));
								fitmiFoodLogData.setLogTime(date);
								fitmiFoodLogData.setDateAdded(date);
								FoodLoginModule.insertFitmifoodLogTable(fitmiFoodLogData, databaseObject);
							}


						}	

						plannerBreakfast.setBackgroundResource(R.drawable.breakfast2);
						breakfastClick = 0;
					}

					if(PlannerAdapter.lunchMap.size()>0){					


						for(int i=0;i<Constants.foodLogData.size();i++){

							for ( String key : PlannerAdapter.lunchMap.keySet() ){

								String date = convertDateFormat(key);
								FitmiFoodLogDAO fitmiFoodLogData = Constants.foodLogData.get(i);
								fitmiFoodLogData.setMealId(String.valueOf(2));
								fitmiFoodLogData.setLogTime(date);
								fitmiFoodLogData.setDateAdded(date);
								FoodLoginModule.insertFitmifoodLogTable(fitmiFoodLogData, databaseObject);
							}


						}	

						plannerLunch.setBackgroundResource(R.drawable.lunch2);
						lunchClick = 0;
					}
					if(PlannerAdapter.snackMap.size()>0){
						for(int i=0;i<Constants.foodLogData.size();i++){

							for ( String key : PlannerAdapter.snackMap.keySet() ){

								String date = convertDateFormat(key);
								FitmiFoodLogDAO fitmiFoodLogData = Constants.foodLogData.get(i);
								fitmiFoodLogData.setMealId(String.valueOf(4));
								fitmiFoodLogData.setLogTime(date);
								fitmiFoodLogData.setDateAdded(date);
								FoodLoginModule.insertFitmifoodLogTable(fitmiFoodLogData, databaseObject);
							}


						}

						plannersnack.setBackgroundResource(R.drawable.snack2);
						snackclick = 0;
					}
					if(PlannerAdapter.dinnerMap.size()>0){
						for(int i=0;i<Constants.foodLogData.size();i++){

							for ( String key : PlannerAdapter.dinnerMap.keySet() ){

								String date = convertDateFormat(key);
								FitmiFoodLogDAO fitmiFoodLogData = Constants.foodLogData.get(i);
								fitmiFoodLogData.setMealId(String.valueOf(3));
								fitmiFoodLogData.setLogTime(date);
								fitmiFoodLogData.setDateAdded(date);
								FoodLoginModule.insertFitmifoodLogTable(fitmiFoodLogData, databaseObject);
							}


						}	

						plannerDinner.setBackgroundResource(R.drawable.dinner1);
						dinnerClick = 0;
					}


					if(PlannerAdapter.activityMap.size()>0){
						for(int i=0;i<Constants.activityLogData.size();i++){

							for ( String key : PlannerAdapter.activityMap.keySet() ){								

								String date = convertDateFormat(key);								
								ActivityModule.execiselogInsertionList(databaseObject,Constants.activityLogData.get(i),date);	
							}							
						}	

					}


					if(Constants.activityLogData.size()>0)
						Constants.activityLogData.clear();

					if(PlannerAdapter.activityMap.size()>0)
						PlannerAdapter.activityMap.clear();

					if(Constants.foodLogData.size()>0)
						Constants.foodLogData.clear();

					if(PlannerAdapter.brekMap.size()>0)
						PlannerAdapter.brekMap.clear();

					if(PlannerAdapter.lunchMap.size()>0)
						PlannerAdapter.lunchMap.clear();

					if(PlannerAdapter.snackMap.size()>0)
						PlannerAdapter.snackMap.clear();

					if(PlannerAdapter.dinnerMap.size()>0)
						PlannerAdapter.dinnerMap.clear();


					setPlannerListWeekly(stringDate);

					break;
				case 3:
					Log.e("case 3: ","case 3: working");
					if(Constants.activityLogData.size()>0){

						if(PlannerGridAdapter.brekMapMonthly.size()>0){
							for(int i=0;i<Constants.activityLogData.size();i++){

								for ( String key : PlannerGridAdapter.brekMapMonthly.keySet() ){								

									String date = convertDateFormatMonthly(key);								
									ActivityModule.execiselogInsertionList(databaseObject,Constants.activityLogData.get(i),date);	
								}							
							}	

						}

						PlannerFragment.gridItemCliclEnable = false;
						PlannerFragment.activityClickEnablegrid = false;

						if(Constants.activityLogData.size()>0)
							Constants.activityLogData.clear();

						if(PlannerGridAdapter.brekMapMonthly.size()>0)
							PlannerGridAdapter.brekMapMonthly.clear();

						setPlannerMonthly(stringDate);

						pga.notifyDataSetChanged();

					}else{

						dialogMonthlyFoodLog();
					}					

					break;
				}			


				//dialog();
				//setPlannerListWeekly(stringDate);
			}
		});
		pluslayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				twocircle.setVisibility(View.VISIBLE);				

				switch (btnDateClick) {
				case 1:					

					fivelayout.setAlpha(.10f);
					//twocircle.setAlpha(.10f);
					breakFast.setClickable(false); 
					LunchLinear.setClickable(false);                   
					DinnerLinear.setClickable(false); 
					SnackLinear.setClickable(false); 
					ActivitiesLinear.setClickable(false);	
//avinash changes making true
				/*	gridItemCliclEnable = false;
					listItemCliclEnable = false;*/

					gridItemCliclEnable = true;
					listItemCliclEnable = true;
					break;

				case 2:

					plannerlistview.setAlpha(.10f);	
					//twocircle.setAlpha(.10f);
					plannerlistview.setClickable(false); 
					gv.setClickable(false);

					breakFast.setClickable(false); 
					LunchLinear.setClickable(false);                   
					DinnerLinear.setClickable(false); 
					SnackLinear.setClickable(false); 
					ActivitiesLinear.setClickable(false);
					//avinash changes making true
					/*listItemCliclEnable = true;
					gridItemCliclEnable = false;*/
					listItemCliclEnable = true;
					gridItemCliclEnable = true;
					break;

				case 3:

					gv.setAlpha(.10f);
					//twocircle.setAlpha(.10f);
					gv.setClickable(false); 
					plannerlistview.setClickable(false); 
					breakFast.setClickable(false); 
					LunchLinear.setClickable(false);                   
					DinnerLinear.setClickable(false); 
					SnackLinear.setClickable(false); 
					ActivitiesLinear.setClickable(false);

					//avinash changes making true
					/*gridItemCliclEnable = true;
					listItemCliclEnable = false;*/
					gridItemCliclEnable = true;
					listItemCliclEnable = true;
					pga.notifyDataSetChanged();

					break;
				}

			}
		});


		return v;
	}

	public void dialog()
	{
		final Dialog dialog=new Dialog(getActivity()/*,R.style.Theme_Dialog*/);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_replace_add_cancel);
		dialog.findViewById(R.id.replacebtn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();

				switch (clickItem) {
				case 0:

					breakfastShow(true);

					break;
				case 1:

					lunchShow(true);

					break;
				case 2:
					dinnerShow(true);
					break;
				case 3:
					snackShow(true);
					break;
				case 4:
					Constants.activityLog = true;
					activityShow();
					break;
				}

			}
		});
		dialog.findViewById(R.id.addbtn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();

				switch (clickItem) {
				case 0:

					breakfastShow(false);

					break;
				case 1:

					lunchShow(false);

					break;
				case 2:
					dinnerShow(false);
					break;
				case 3:
					snackShow(false);
					break;
				case 4:
					Constants.activityLog = true;
					activityShow();
					break;
				}
			}
		});
		dialog.findViewById(R.id.cancelbtn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();

	}

	/*	public void gotoPlannerList() {
		PlannerListFragment fragment = new PlannerListFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("weekly", 1);
		bundle.putInt("root_id", R.id.root_home_frame);
		fragment.setArguments(bundle);

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.add(R.id.root_planner_frame, fragment,
				"PlannerListFragment");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();

	}*/

	/*	public void gotoPlannerGrid() {
		PlannerGridViewFragment fragment = new PlannerGridViewFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("monthly", 1);
		fragment.setArguments(bundle);

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.add(R.id.root_planner_frame, fragment,
				"PlannerGridFragment");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();

	}*/


	@OnClick(R.id.Settings)
	public void gotoSettings() {

		SettingsFragment fragment = new SettingsFragment();

		Bundle bundle = new Bundle();
		bundle.putInt("root_id", R.id.root_planner_frame);
		fragment.setArguments(bundle);

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.add(R.id.root_planner_frame, fragment, "SettingsFragment");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();

	}

	//@OnClick(R.id.BreakFastLinear)
	public void clickBreakfast() {

		if(Constants.PROFILE_ID !=null && Constants.PROFILE_ID.equalsIgnoreCase("")){

			Alert.showAlert(getActivity(), "Kindly create at least one user to access the App");
			return;
		}

		if(breakfastActive){

			clickItem = 0;
			dialog();

		}else{

			breakfastShow(false);
		}

	}

	//@OnClick( R.id.LunchLinear)
	public void clickLunch()
	{
		if(Constants.PROFILE_ID !=null && Constants.PROFILE_ID.equalsIgnoreCase("")){

			Alert.showAlert(getActivity(), "Kindly create at least one user to access the App");
			return;
		}

		if(lunchActive){
			clickItem = 1;
			dialog();
		}else{

			lunchShow(false);
		}

	}

	//@OnClick(R.id.DinnerLinear)
	public void clickDinner()
	{
		if(Constants.PROFILE_ID !=null && Constants.PROFILE_ID.equalsIgnoreCase("")){

			Alert.showAlert(getActivity(), "Kindly create at least one user to access the App");
			return;
		}

		if(dinnerActive){
			clickItem = 2;
			dialog();
		}else{

			dinnerShow(false);
		}

	}

	//@OnClick(R.id.SnackLinear)
	public void clickSnack()
	{
		if(Constants.PROFILE_ID !=null && Constants.PROFILE_ID.equalsIgnoreCase("")){

			Alert.showAlert(getActivity(), "Kindly create at least one user to access the App");
			return;
		}

		if(snackActive){
			clickItem = 3;
			dialog();
		}else{

			snackShow(false);
		}

	}

	//@OnClick(R.id.ActivitiesLinear )
	public void clickActivity()
	{
		if(Constants.PROFILE_ID !=null && Constants.PROFILE_ID.equalsIgnoreCase("")){

			Alert.showAlert(getActivity(), "Kindly create at least one user to access the App");
			return;
		}

		if(activityActive){

			clickItem = 4;
			dialog();
		}else{
			Constants.activityLog = false;
			activityShow();
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		/*try {	

			Calendar c = Calendar.getInstance();
			SimpleDateFormat targetFormatter = new SimpleDateFormat(
					"EEEE, MMM dd, yyyy", Locale.ENGLISH);
			String formattedDate = targetFormatter.format(c.getTime());
			dateTextView.setText(formattedDate);
			stringDate = formattedDate;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		System.out.println("onResume is calling in planner fragment");
		
		if (Constants.sTempDate.length() == 0) {
			//Constants.sDate = "Tuesday, February 10, 2015";

			Calendar c = Calendar.getInstance();
			System.out.println("Current time => " + c.getTime());	
			//	SimpleDateFormat df = new SimpleDateFormat("YYYY-MMM-dd hh:mm:ss");
			//SimpleDateFormat df = new SimpleDateFormat("EEEE, MMM dd, yyyy");
			SimpleDateFormat df = new SimpleDateFormat("EEEE, MMM dd, yyyy");
			Constants.sDate = df.format(c.getTime());
			stringDate = Constants.sDate;

			SimpleDateFormat postFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");			
			Constants.postDate = postFormat.format(c.getTime());


			SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
			Constants.conditionDate = dformat.format(c.getTime());
			System.out.println("Calender post format :"+ Constants.postDate);
			//Toast.makeText(getActivity(), Constants.postDate, Toast.LENGTH_LONG).show();
			
		}
		else{
			today=	Constants.sTempDate;
		}
		dateTextView.setText(Constants.sDate);
	//avinash changes
	
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		getActivity().unregisterReceiver(mBroadcastReceiver);
		getActivity().unregisterReceiver(mBroadcastReceiverlist);
	}
	/*	@Override
	public void onDestroyView() {
		
		System.out.println("onDestriyView is calling in planner fragment");
		
	}
	@Override
	public void onDetach() {
		System.out.println("onDetach is calling in planner fragment");
	}
	@Override
	public void onPause() {
		System.out.println("onPause is calling in planner fragment");
	}
	@Override
	public void onStop() {
		System.out.println("onStop is calling in planner fragment");
	}*/

	BroadcastReceiver dateSetReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			Bundle bundleBroadcast = intent.getExtras();			
			dateTextView.setText(bundleBroadcast.getString("key"));
			stringDate = dateTextView.getText().toString().trim();			
			String setDate = dateTextView.getText().toString().trim();			
			Constants.conditionDate = dateModule.conditionDateFormat(setDate);
			plannerSet();



			/*switch (btnDateClick) {
			case 1:
				String setDate = dateTextView.getText().toString().trim();
				setDate = dateModule.datePreview(setDate);
				dateTextView.setText(setDate);
				Constants.conditionDate = dateModule.conditionDateFormat(setDate);
				plannerSet();
				break;

			case 2:
				SimpleDateFormat targetFormatter = new SimpleDateFormat(
						"EEEE, MMM dd, yyyy", Locale.ENGLISH);		
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.clear(Calendar.MINUTE);
				cal.clear(Calendar.SECOND);
				cal.clear(Calendar.MILLISECOND);


				try {
					cal.setTime(targetFormatter.parse(stringDate));
					cal.add(Calendar.DATE, -7);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Date lastParsedDate = cal.getTime();
				String formattedDateFirst = targetFormatter.format(lastParsedDate);		

				stringDate = formattedDateFirst;
				setPlannerListWeekly(stringDate);
				break;
			case 3:
				SimpleDateFormat targetFormatterGrid = new SimpleDateFormat(
						"EEEE, MMMM dd, yyyy", Locale.ENGLISH);		
				Calendar calGrid = Calendar.getInstance();
				calGrid.set(Calendar.HOUR_OF_DAY, 0);
				calGrid.clear(Calendar.MINUTE);
				calGrid.clear(Calendar.SECOND);
				calGrid.clear(Calendar.MILLISECOND);


				try {
					calGrid.setTime(targetFormatterGrid.parse(stringDate));
					calGrid.add(Calendar.MONTH, -1);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Date lastParsedDateGrid = calGrid.getTime();
				String formattedDateFirstGrid = targetFormatterGrid.format(lastParsedDateGrid);		

				stringDate = formattedDateFirstGrid;
				setPlannerMonthly(stringDate);
				break;
			}*/

		}
	};

	//avinash changing **not to open full calander 
	
/*	@OnClick(R.id.Date)
	public void changeDate() {
		Constants.fragmentNumber = 8;

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.add(R.id.root_planner_frame, new CalendarFragment(),
				"CalendarFragment");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();
	}*/

	public void breakfastShow(boolean replace)
	{
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();

		FoodLoggingFragment foodLogin = new FoodLoggingFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("mealid", 1);
		bundle.putString("foodtype", "Breakfast");
		bundle.putBoolean("replace", replace);

		transaction.add(R.id.root_planner_frame, foodLogin,
				"FoodLoggingFragment");						
		foodLogin.setArguments(bundle);
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	public void lunchShow(boolean replace)
	{
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		Bundle bundle = new Bundle();
		FoodLoggingFragment foodLogin = new FoodLoggingFragment();
		bundle.putInt("mealid", 2);
		bundle.putString("foodtype", "Lunch");
		bundle.putBoolean("replace", replace);

		transaction.add(R.id.root_planner_frame, foodLogin,
				"FoodLoggingFragment");
		foodLogin.setArguments(bundle);
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	public void dinnerShow(boolean replace){
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		Bundle bundle = new Bundle();
		FoodLoggingFragment foodLogin = new FoodLoggingFragment();
		bundle.putInt("mealid", 3);
		bundle.putString("foodtype", "Dinner");
		bundle.putBoolean("replace", replace);

		transaction.add(R.id.root_planner_frame, foodLogin,
				"FoodLoggingFragment");
		foodLogin.setArguments(bundle);
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	public void snackShow(boolean replace){
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		Bundle bundle = new Bundle();
		FoodLoggingFragment foodLogin = new FoodLoggingFragment();
		bundle.putInt("mealid", 4);
		bundle.putString("foodtype", "Snack");
		bundle.putBoolean("replace", replace);

		transaction.add(R.id.root_planner_frame, foodLogin,
				"FoodLoggingFragment");
		foodLogin.setArguments(bundle);
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	public void activityShow()
	{

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.add(R.id.root_planner_frame, new MyActivityFragment(),
				"MyActivityFragment");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	@OnClick(R.id.txtPreview)
	public void datePreview()
	{
		switch (btnDateClick) {
		case 1:
			String setDate = dateTextView.getText().toString().trim();
			setDate = dateModule.datePreview(setDate);
			dateTextView.setText(setDate);
			Constants.conditionDate = dateModule.conditionDateFormat(setDate);
			Constants.postDate =dateModule.getFormatDateSearchInsert(setDate);
			Constants.sTempDate= Constants.conditionDate;
			Constants.sDate=dateModule.sDateFormat(setDate);
		//	Constants.conditionDate = dateModule.conditionDateFormat(setDate);
			plannerSet();
			break;

		case 2:
			SimpleDateFormat targetFormatter = new SimpleDateFormat(
					"EEEE, MMM dd, yyyy", Locale.ENGLISH);		
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.clear(Calendar.MINUTE);
			cal.clear(Calendar.SECOND);
			cal.clear(Calendar.MILLISECOND);


			try {
			//previously in nextbtn press also	cal.setTime(targetFormatter.parse(stringDate));
				
			//	cal.setTime(targetFormatter.parse(stringDate));
				if(stringDate.length()>0)
				{
					cal.setTime(targetFormatter.parse(stringDate));	
				}else{
				cal.setTime(targetFormatter.parse(Constants.sDate));
				}
				cal.add(Calendar.DATE, -7);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Date lastParsedDate = cal.getTime();
			String formattedDateFirst = targetFormatter.format(lastParsedDate);		

			stringDate = formattedDateFirst;
			setPlannerListWeekly(stringDate);
			break;
		case 3:
			SimpleDateFormat targetFormatterGrid = new SimpleDateFormat(
					"EEEE, MMMM dd, yyyy", Locale.ENGLISH);		
			Calendar calGrid = Calendar.getInstance();
			calGrid.set(Calendar.HOUR_OF_DAY, 0);
			calGrid.clear(Calendar.MINUTE);
			calGrid.clear(Calendar.SECOND);
			calGrid.clear(Calendar.MILLISECOND);


			try {
				
				if(stringDate.length()>0)
				{
					calGrid.setTime(targetFormatterGrid.parse(stringDate));
				}else{
					calGrid.setTime(targetFormatterGrid.parse(Constants.sDate));
				}
				calGrid.add(Calendar.MONTH, -1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Date lastParsedDateGrid = calGrid.getTime();
			String formattedDateFirstGrid = targetFormatterGrid.format(lastParsedDateGrid);		

			stringDate = formattedDateFirstGrid;
			setPlannerMonthly(stringDate);
			break;
		}



	}

	@OnClick(R.id.txtNext)
	public void dateNext()
	{

		switch (btnDateClick) {
		case 1:
			String setDate = dateTextView.getText().toString().trim();
			setDate = dateModule.dateNext(setDate);
			dateTextView.setText(setDate);
			Constants.conditionDate = dateModule.conditionDateFormat(setDate);
			Constants.postDate =dateModule.getFormatDateSearchInsert(setDate);
			Constants.sTempDate= Constants.conditionDate;
			Constants.sDate=dateModule.sDateFormat(setDate);
		//	Constants.conditionDate = dateModule.conditionDateFormat(setDate);
			plannerSet();
			break;

		case 2:
			SimpleDateFormat targetFormatter = new SimpleDateFormat(
					"EEEE, MMM dd, yyyy", Locale.ENGLISH);		
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.clear(Calendar.MINUTE);
			cal.clear(Calendar.SECOND);
			cal.clear(Calendar.MILLISECOND);


			try {
				if(stringDate.length()>0)
				{
					cal.setTime(targetFormatter.parse(stringDate));	
				}else{
				cal.setTime(targetFormatter.parse(Constants.sDate));
				}
				cal.add(Calendar.DATE, 7);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Date lastParsedDate = cal.getTime();
			String formattedDateFirst = targetFormatter.format(lastParsedDate);


			stringDate = formattedDateFirst;
			setPlannerListWeekly(stringDate);
			break;
		case 3:
			SimpleDateFormat targetFormatterGrid = new SimpleDateFormat(
					"EEEE, MMM dd, yyyy", Locale.ENGLISH);		
			Calendar calGrid = Calendar.getInstance();
			calGrid.set(Calendar.HOUR_OF_DAY, 0);
			calGrid.clear(Calendar.MINUTE);
			calGrid.clear(Calendar.SECOND);
			calGrid.clear(Calendar.MILLISECOND);


			try {
			//	calGrid.setTime(targetFormatterGrid.parse(stringDate));
				if(stringDate.length()>0)
				{
					calGrid.setTime(targetFormatterGrid.parse(stringDate));
				}else{
					calGrid.setTime(targetFormatterGrid.parse(Constants.sDate));
				}
				calGrid.add(Calendar.MONTH, 1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Date lastParsedDateGrid = calGrid.getTime();
			String formattedDateFirstGrid = targetFormatterGrid.format(lastParsedDateGrid);		

			stringDate = formattedDateFirstGrid;
			setPlannerMonthly(stringDate);
			break;
		}


	}

	public void plannerSet()
	{
		for(int i=1;i<=4;i++){

			ArrayList<PlannerMealType> plannerDate = foodLogModule.plannerMealCheck(databaseObject,String.valueOf(i));

			switch (i) {
			case 1:				

				if(plannerDate.size()>0){
					plannerBreakfast.setBackgroundResource(R.drawable.breakfast2);
					plannerBreakfast.setAlpha(1.0f);
					breakfastActive = true;
					plannerBreakfast.setClickable(true);
					plannerBreakfast.setEnabled(true);
				}
				else{
					plannerBreakfast.setBackgroundResource(R.drawable.breakfast2);
					plannerBreakfast.setAlpha(0.5f);
				//prev no if else only breakfastActive = false;
					if(donelayout.getVisibility()==0)
					{
						breakfastActive = false;
					}else{
						breakfastActive = true;
					}
				//	plannerBreakfast.setClickable(false);
			//		plannerBreakfast.setEnabled(false);
				}

				break;

			case 2:

			if(plannerDate.size()>0){
				plannerLunch.setBackgroundResource(R.drawable.lunch2);
				plannerLunch.setAlpha(1.0f);
				lunchActive = true;
				plannerLunch.setClickable(true);
				plannerLunch.setEnabled(true);
			}
			else{
				plannerLunch.setBackgroundResource(R.drawable.lunch2); 
				plannerLunch.setAlpha(0.5f);
				
				if(donelayout.getVisibility()==0)
				{
					lunchActive = false;
				}else{
					lunchActive = true;
				}
				/*lunchActive = false;
				plannerLunch.setClickable(false);
				plannerLunch.setEnabled(false);*/
			}

			break;
			case 3:

				if(plannerDate.size()>0){
					plannerDinner.setBackgroundResource(R.drawable.dinner1);
					plannerDinner.setAlpha(1.0f);
					dinnerActive = true;
					plannerDinner.setClickable(true);
					plannerDinner.setEnabled(true);
				}
				else{
					plannerDinner.setBackgroundResource(R.drawable.dinner1); 
					plannerDinner.setAlpha(0.5f);
					
					if(donelayout.getVisibility()==0)
					{
						dinnerActive = false;
					}else{
						dinnerActive = true;
					}
				/*	dinnerActive = false;
					plannerDinner.setClickable(false);
					plannerDinner.setEnabled(false);*/
				}

				break;
			case 4:
				if(plannerDate.size()>0){
					plannersnack.setBackgroundResource(R.drawable.snack2);
					plannersnack.setAlpha(1.0f);
					snackActive = true;
					plannersnack.setClickable(true);
					plannersnack.setEnabled(true);
				}
				else{
					plannersnack.setBackgroundResource(R.drawable.snack2);
					plannersnack.setAlpha(0.5f);
					if(donelayout.getVisibility()==0)
					{
						snackActive = false;
					}else{
						snackActive = true;
					}
			/*		snackActive = false;
					plannersnack.setClickable(false);
					plannersnack.setEnabled(false);*/
				}
				break;
			}
		}

		ArrayList<PlannerMealType> plannerData = activityModule.plannerActivityCheck(databaseObject);
		if(plannerData.size()>0){
			plannerActivity.setBackgroundResource(R.drawable.activity);
			plannerActivity.setAlpha(1.0f);
			activityActive = true;
			plannerActivity.setClickable(true);
			plannerActivity.setEnabled(true);
		}
		else{
			plannerActivity.setBackgroundResource(R.drawable.activity);
			plannerActivity.setAlpha(0.5f);
			if(donelayout.getVisibility()==0)
			{
				activityActive = false;
			}else{
				activityActive = true;
			}
			/*activityActive = false;
			plannerActivity.setClickable(false);
			plannerActivity.setEnabled(false);*/
		}
	}

	@OnClick(R.id.foodtwiceframelayout)
	public void addAnyFood()
	{		

		plannerFoodAdd(false);			
		fivelayout.setAlpha(255);
		activityClickEnable = false;
		activityClickEnablegrid = false;
		PlannerFragment.activityclickweek=false;
		PlannerFragment.mealclickkweek=true;
		breakFast.setClickable(true); 
		LunchLinear.setClickable(true);                   
		DinnerLinear.setClickable(true); 
		SnackLinear.setClickable(true); 
		ActivitiesLinear.setClickable(true);

		plannerlistview.setAlpha(255);
		plannerlistview.setClickable(true);		
		//plannerlistview.setScrollContainer(true);

		gv.setAlpha(255);
		gv.setClickable(true); 		
		//gv.setScrollContainer(true);

		padpt.notifyDataSetChanged();
		twocircle.setVisibility(View.GONE);
		pluslayout.setVisibility(View.GONE);
		donelayout.setVisibility(View.VISIBLE);
		if(donelayout.getVisibility()==0)
		{
			breakfastActive=false;
			lunchActive=false;
			dinnerActive=false;
			snackActive=false;
			
		}
		plannerBreakfast.setClickable(true);
		plannerLunch.setClickable(true);
		plannerDinner.setClickable(true);
		plannersnack.setClickable(true);
		plannerActivity.setClickable(false);

		plannerBreakfast.setEnabled(true);
		plannerLunch.setEnabled(true);
		plannerDinner.setEnabled(true);
		plannersnack.setEnabled(true);
		plannerActivity.setEnabled(false);

	}

	@OnClick(R.id.activitytwiceframelayout)
	public void addAnyActivity()
	{		

		plannerActivityAdd(false);			
		fivelayout.setAlpha(255);
		PlannerFragment.activityClickEnable = true;
		PlannerFragment.activityclickweek=true;
		PlannerFragment.mealclickkweek=false;
		activityClickEnablegrid = true;
		breakFast.setClickable(true); 
		LunchLinear.setClickable(true);                   
		DinnerLinear.setClickable(true); 
		SnackLinear.setClickable(true); 
		ActivitiesLinear.setClickable(true); 

		plannerlistview.setAlpha(255);
		plannerlistview.setClickable(true); 
		//plannerlistview.setScrollContainer(true);

		gv.setAlpha(255);
		gv.setClickable(true); 
		//gv.setScrollContainer(true);

		padpt.notifyDataSetChanged();
		twocircle.setVisibility(View.GONE);
		pluslayout.setVisibility(View.GONE);
		donelayout.setVisibility(View.VISIBLE);
		plannerBreakfast.setClickable(false);
		plannerLunch.setClickable(false);
		plannerDinner.setClickable(false);
		plannersnack.setClickable(false);		
		plannerActivity.setClickable(true);

		plannerBreakfast.setEnabled(false);
		plannerLunch.setEnabled(false);
		plannerDinner.setEnabled(false);
		plannersnack.setEnabled(false);
		plannerActivity.setEnabled(true);

	}


	@OnClick(R.id.plannerBreakfast)
	public void clickBreakFastImg()
	{
		System.out.print("plannerBreakfast "+donelayout.getVisibility());
		if(!breakfastActive){

			if(breakfastClick == 0 ){
				plannerBreakfast.setBackgroundResource(R.drawable.breakfast_color);
				plannerBreakfast.setAlpha(1.0f);
				breakfastClick = 1;
				breakfatId = 1;
				
			}else{
				plannerBreakfast.setBackgroundResource(R.drawable.breakfast2);
				plannerBreakfast.setAlpha(1.0f);
				breakfastClick = 0;
				breakfatId = -1;
			}

		}else{

			//Toast.makeText(getActivity(), "Breakfast click", Toast.LENGTH_LONG).show();
			transaction = getFragmentManager().beginTransaction();
			FoodLoggingFragment foodLogin = new FoodLoggingFragment();
			Bundle bundle = new Bundle();
			bundle.putInt("mealid", 1);
			bundle.putString("foodtype", "Breakfast");	
			bundle.putBoolean("replace", false);
			bundle.putBoolean("log", false);

			transaction.add(R.id.root_planner_frame, foodLogin,
					"FoodLoggingFragment");						
			foodLogin.setArguments(bundle);
			transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.addToBackStack(null);
			transaction.commit();
		}		

	}

	@OnClick(R.id.plannerLunch)
	public void clickLunchImg()
	{
		if(!lunchActive){
			
			if(lunchClick == 0){
				plannerLunch.setBackgroundResource(R.drawable.lunch_color);
				plannerLunch.setAlpha(1.0f);
				lunchClick = 1;
				lunchId = 2;
			}else{
				plannerLunch.setBackgroundResource(R.drawable.lunch2);
				lunchClick = 0;
				lunchId = -1;
			}

		}else{

			//Toast.makeText(getActivity(), "Lunch click", Toast.LENGTH_LONG).show();
			transaction = getFragmentManager().beginTransaction();
			FoodLoggingFragment foodLogin = new FoodLoggingFragment();
			Bundle bundle = new Bundle();
			bundle.putInt("mealid", 2);
			bundle.putString("foodtype", "Lunch");
			bundle.putBoolean("replace", false);
			bundle.putBoolean("log", false);

			transaction.add(R.id.root_planner_frame, foodLogin,
					"FoodLoggingFragment");
			foodLogin.setArguments(bundle);
			transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.addToBackStack(null);
			transaction.commit();
		}
		
	}

	@OnClick(R.id.plannerDinner)
	public void clickDinnerImg()
	{
		if(!dinnerActive){
			
			if(dinnerClick == 0){
				plannerDinner.setBackgroundResource(R.drawable.dinner_color);
				plannerDinner.setAlpha(1.0f);
				dinnerClick = 1;
				dinnerId = 3;
			}else{
				plannerDinner.setBackgroundResource(R.drawable.dinner1);
				dinnerClick = 0;
				dinnerId = -1;
			}
			
		}else{
			
			//Toast.makeText(getActivity(), "Dinner click", Toast.LENGTH_LONG).show();
			transaction = getFragmentManager().beginTransaction();
			FoodLoggingFragment foodLogin = new FoodLoggingFragment();
			Bundle bundle = new Bundle();
			bundle.putInt("mealid", 3);
			bundle.putString("foodtype", "Dinner");
			bundle.putBoolean("replace", false);
			bundle.putBoolean("log", false);

			transaction.add(R.id.root_planner_frame, foodLogin,
					"FoodLoggingFragment");
			foodLogin.setArguments(bundle);
			transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.addToBackStack(null);
			transaction.commit();
		}
		
	}

	@OnClick(R.id.plannersnack)
	public void clickSnackImg()
	{
		if(!snackActive){
			if(snackclick == 0){
				plannersnack.setBackgroundResource(R.drawable.snack_color);
				plannersnack.setAlpha(1.0f);
				snackclick = 1;
				snackId = 4;
			}else{
				plannersnack.setBackgroundResource(R.drawable.snack2);
				snackclick = 0;
				snackId = -1;
			}
			
		}else{
			//Toast.makeText(getActivity(), "Snack click", Toast.LENGTH_LONG).show();
			transaction = getFragmentManager().beginTransaction();
			FoodLoggingFragment foodLogin = new FoodLoggingFragment();
			Bundle bundle = new Bundle();
			bundle.putInt("mealid", 4);
			bundle.putString("foodtype", "Snack");
			bundle.putBoolean("replace", false);
			bundle.putBoolean("log", false);

			transaction.add(R.id.root_planner_frame, foodLogin,
					"FoodLoggingFragment");
			foodLogin.setArguments(bundle);
			transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.addToBackStack(null);
			transaction.commit();
		}
		
	}

	@OnClick(R.id.plannerActivity)
	public void clickActivityImg()
	{
		if(!activityActive){
			
			if(activityClick==0){
				plannerActivity.setBackgroundResource(R.drawable.activity_color);
				plannerActivity.setAlpha(1.0f);
				activityClick = 1;
			}else{

				plannerActivity.setBackgroundResource(R.drawable.activity);
				activityClick = 0;
			}
			
		}else{
			
			//Toast.makeText(getActivity(), "Activity click", Toast.LENGTH_LONG).show();
			FragmentTransaction transaction = getFragmentManager()
					.beginTransaction();
			transaction.add(R.id.root_planner_frame, new MyActivityFragment(),
					"FoodLoggingFragment");
			transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.addToBackStack(null);
			transaction.commit();
		}
		
	}

	public void plannerFoodAdd(boolean replace)
	{
		if(Constants.PROFILE_ID !=null && Constants.PROFILE_ID.equalsIgnoreCase("")){

			Alert.showAlert(getActivity(), "Kindly create at least one user to access the App");
			return;
		}

		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		Bundle bundle = new Bundle();
		FoodLoggingFragment foodLogin = new FoodLoggingFragment();
		bundle.putInt("mealid", 0);
		bundle.putString("foodtype", "");
		bundle.putBoolean("replace", replace);
		bundle.putBoolean("log", true);

		transaction.add(R.id.root_planner_frame, foodLogin,
				"FoodLoggingFragment");
		foodLogin.setArguments(bundle);
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();
		//getActivity().unregisterReceiver(mBroadcastReceiver);
	}

	public void plannerActivityAdd(boolean replace)
	{
		if(Constants.PROFILE_ID !=null && Constants.PROFILE_ID.equalsIgnoreCase("")){

			Alert.showAlert(getActivity(), "Kindly create at least one user to access the App");
			return;
		}

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();

		Bundle bundle = new Bundle();
		MyActivityFragment activityLogin = new MyActivityFragment();
		bundle.putInt("livestrong_id", 1);
		bundle.putBoolean("replace", replace);
		bundle.putBoolean("log", true);
		activityLogin.setArguments(bundle);
		transaction.add(R.id.root_planner_frame, activityLogin,"MyActivityFragment");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();

	}


	/**
	 *  Planner weekly
	 */

	public void setPlannerListWeekly(String formattedDate)
	{	

		CalenderFirsLastDay firstLastDayObj = firstLastDayDate.getWeekFirstDateLastDay(formattedDate);
		convertDateFormat(firstLastDayObj);		
	}

	public void convertDateFormat(CalenderFirsLastDay firstLastDayObj)
	{

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);

		SimpleDateFormat preFormat = new SimpleDateFormat("yyyy-MM-dd");	
		try {
			cal.setTime(preFormat.parse(firstLastDayObj.getFirstDay().trim()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SimpleDateFormat poatFormat = new SimpleDateFormat("MMM dd, yyyy");


		try {
			Date firstDate = preFormat.parse(firstLastDayObj.getFirstDay().trim());
			Date lastDate = preFormat.parse(firstLastDayObj.getLastDay().trim());

			String formattedDateFirst = poatFormat.format(firstDate);
			String formattedDateLast = poatFormat.format(lastDate);

			dateTextView.setText(formattedDateFirst+" - "+formattedDateLast);

			if(foodSet.size()>0)
				foodSet.clear();

			for(int i=1;i<=7;i++)
			{
				Date firstParsedDate = cal.getTime();	
				String calenderDate = preFormat.format(firstParsedDate);	

				PlannerWeeklyDAO setActivityFood = new PlannerWeeklyDAO();	

				is_breakfastConsumed = foodLogModule.selectPlannerWeekList(calenderDate,databaseObject,"1");
				is_lunchConsumed = foodLogModule.selectPlannerWeekList(calenderDate,databaseObject,"2");
				is_dinnerConsumed = foodLogModule.selectPlannerWeekList(calenderDate,databaseObject,"3");
				is_snackConsumed = foodLogModule.selectPlannerWeekList(calenderDate,databaseObject,"4");
				///////////////// activity////////////////////////////////////////

				is_weightLifting = activityModule.selectActivityMonthList(calenderDate,databaseObject,"1");
				is_Boxing = activityModule.selectActivityMonthList(calenderDate,databaseObject,"2");
				is_Jumping = activityModule.selectActivityMonthList(calenderDate,databaseObject,"3");
				is_Swimming = activityModule.selectActivityMonthList(calenderDate,databaseObject,"4");
				is_walking = activityModule.selectActivityMonthList(calenderDate,databaseObject,"5");
				is_Gymnastic = activityModule.selectActivityMonthList(calenderDate,databaseObject,"6");


				if(is_breakfastConsumed){
					setActivityFood.setBreakfastActive(1);
				}
				else{
					setActivityFood.setBreakfastActive(0);				

				}

				if(is_lunchConsumed){
					setActivityFood.setLunchActive(1);
				}
				else{
					setActivityFood.setLunchActive(0);				

				}
				if(is_dinnerConsumed){
					setActivityFood.setDinnerActive(1);
				}
				else{
					setActivityFood.setDinnerActive(0);				

				}
				if(is_snackConsumed ){
					setActivityFood.setSnackActive(1);
				}
				else{
					setActivityFood.setSnackActive(0);				

				}

				if(is_weightLifting || is_Boxing || is_Jumping || is_Swimming || is_walking ||  is_Gymnastic){

					setActivityFood.setActivityActive(1);					

				}
				else{

					setActivityFood.setActivityActive(0);	
				}

				SimpleDateFormat targetFormatter = new SimpleDateFormat(
						"EEEE, MMMM dd, yyyy", Locale.ENGLISH);
				String formattedDate = targetFormatter.format(cal.getTime());

				setActivityFood.setDateSet(formattedDate);

				foodSet.add(setActivityFood);

				cal.add(Calendar.DATE, 1);
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//making true
		if (donelayout.getVisibility() == View.VISIBLE) {
		    // Its visible
			PlannerFragment.donelay = true;
		} else {
		    // Either gone or invisible
			PlannerFragment.donelay = false;
		}
		PlannerFragment.listItemCliclEnable = true;
		PlannerFragment.activityClickEnable = true;
		padpt.notifyDataSetChanged();
	}


	public void setPlannerMonthly(String formattedDate)
	{
		CalenderFirsLastDay firstLastDayObj = firstLastDayDate.getMonthFirstDateLastDay(formattedDate);
		//dateTextView.setText(firstLastDayObj.getFirstDay()+" to "+firstLastDayObj.getLastDay());
		convertDateFormatGrid(firstLastDayObj);
	}

	public void convertDateFormatGrid(CalenderFirsLastDay firstLastDayObj)
	{

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);

		SimpleDateFormat preFormat = new SimpleDateFormat("yyyy-MM-dd");	
		try {
			cal.setTime(preFormat.parse(firstLastDayObj.getFirstDay().trim()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SimpleDateFormat poatFormat = new SimpleDateFormat("MMM dd, yyyy");


		try {
			Date firstDate = preFormat.parse(firstLastDayObj.getFirstDay().trim());
			Date lastDate = preFormat.parse(firstLastDayObj.getLastDay().trim());

			String formattedDateFirst = poatFormat.format(firstDate);
			String formattedDateLast = poatFormat.format(lastDate);

			dateTextView.setText(formattedDateFirst+" - "+formattedDateLast);

			if(foodSetGrid.size()>0)
				foodSetGrid.clear();

			FoodActivityActive demoObj = new FoodActivityActive();	
			foodSetGrid.add(demoObj);

			for(int i=1;i<=firstLastDayObj.getTotalDay();i++)
			{
				Date firstParsedDate = cal.getTime();	
				String calenderDate = preFormat.format(firstParsedDate);	

				FoodActivityActive setActivityFood = new FoodActivityActive();	

				is_breakfastConsumed = foodLogModule.selectPlannerMonthList(calenderDate,databaseObject,"1");
				is_lunchConsumed = foodLogModule.selectPlannerMonthList(calenderDate,databaseObject,"2");
				is_dinnerConsumed = foodLogModule.selectPlannerMonthList(calenderDate,databaseObject,"3");
				is_snackConsumed = foodLogModule.selectPlannerMonthList(calenderDate,databaseObject,"4");
				///////////////// activity////////////////////////////////////////

				is_weightLifting = activityModule.selectActivityMonthList(calenderDate,databaseObject,"1");
				is_Boxing = activityModule.selectActivityMonthList(calenderDate,databaseObject,"2");
				is_Jumping = activityModule.selectActivityMonthList(calenderDate,databaseObject,"3");
				is_Swimming = activityModule.selectActivityMonthList(calenderDate,databaseObject,"4");
				is_walking = activityModule.selectActivityMonthList(calenderDate,databaseObject,"5");
				is_Gymnastic = activityModule.selectActivityMonthList(calenderDate,databaseObject,"6");


				if(is_breakfastConsumed || is_lunchConsumed || is_dinnerConsumed || is_snackConsumed ){
					setActivityFood.setFoodActive(1);
					setActivityFood.setDate(calenderDate);
				}
				else{
					setActivityFood.setFoodActive(0);				
					setActivityFood.setDate(calenderDate);
				}

				if(is_weightLifting || is_Boxing || is_Jumping || is_Swimming || is_walking ||  is_Gymnastic){

					setActivityFood.setActivityActive(1);					
					setActivityFood.setDate(calenderDate);
				}
				else{

					setActivityFood.setActivityActive(0);	
					setActivityFood.setDate(calenderDate);
				}

				foodSetGrid.add(setActivityFood);

				cal.add(Calendar.DATE, 1);
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		pga.notifyDataSetChanged();
	}

	BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			//This piece of code will be executed when you click on your item
			// Call your fragment...

			Bundle bundleBroadcast = intent.getExtras();

			int keyItemNo = bundleBroadcast.getInt("keyItemNo");
			boolean keyItem = bundleBroadcast.getBoolean("keyItem");  

			System.out.println("onReceive working  BroadcastReceiver " + Constants.sTempDate);
			
			if(Constants.PROFILE_ID !=null && Constants.PROFILE_ID.equalsIgnoreCase("")){

				Alert.showAlert(getActivity(), "Kindly create at least one user to access the App");
				return;
			}

			switch (keyItemNo) {
			case 1:
				if(keyItem){

					clickItem = 0;
					dialogAddActivity();

				}else{

					plannerFoodAdd(false);
				}


				break;
			case 2:
				if(keyItem){

					clickItem = 1;
					dialogAddActivity();

				}else{

					plannerActivityAdd(false);
				}

				break;
			}
		}
	};

	public void dialogAddActivity()
	{
		final Dialog dialog=new Dialog(getActivity()/*,R.style.Theme_Dialog*/);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		dialog.setContentView(R.layout.dialog_replace_add_cancel);
		dialog.findViewById(R.id.replacebtn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();

				switch (clickItem) {
				case 0:

					plannerFoodAdd(true);
					break;
				case 1:

					plannerActivityAdd(true);
					break;

				}

			}
		});
		dialog.findViewById(R.id.addbtn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();

				switch (clickItem) {
				case 0:
					plannerFoodAdd(false);
					break;
				case 1:
					plannerActivityAdd(false);
					break;				
				}
			}
		});
		dialog.findViewById(R.id.cancelbtn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();

	}


	BroadcastReceiver mBroadcastReceiverlist = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			//This piece of code will be executed when you click on your item
			// Call your fragment...

			Bundle bundleBroadcast = intent.getExtras();

			boolean keyItem = bundleBroadcast.getBoolean("keyItem");   
			int keyItemNo = bundleBroadcast.getInt("keyItemNo");


			if(Constants.PROFILE_ID !=null && Constants.PROFILE_ID.equalsIgnoreCase("")){

				Alert.showAlert(getActivity(), "Kindly create at least one user to access the App");
				return;
			}

			switch (keyItemNo) {
			case 0:
				if(keyItem){

					clickItem = 0;
					dialog();

				}else{

					breakfastShow(false);
				}

				break;

			case 1:
				if(keyItem){
					clickItem = 1;
					dialog();
				}else{

					lunchShow(false);
				}
				break;
			case 2:
				if(keyItem){
					clickItem = 2;
					dialog();
				}else{

					dinnerShow(false);
				}
				break;
			case 3:
				if(keyItem){
					clickItem = 3;
					dialog();
				}else{

					snackShow(false);
				}
				break;
			case 4:
				if(keyItem){

					clickItem = 4;
					dialog();
				}else{
					Constants.activityLog = false;
					activityShow();
				}
				break;
			}


		}
	};


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
	//2016-01-01
	public String convertDateFormatMonthly(String stringDate)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
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


	public void dialogMonthlyFoodLog()
	{
		ImageView snacksImg;
		ImageView breakFastImg;
		ImageView lunchImg;
		ImageView dinnerImg;	

		final Dialog dialog=new Dialog(getActivity()/*,R.style.Theme_Dialog*/);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		dialog.setContentView(R.layout.dialog_add_meal_month);		
		dialog.setCancelable(false);

		breakFastImg = (ImageView)dialog.findViewById(R.id.imgViewBreakfast);
		breakFastImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				addMealMonthly(1);
				dialog.dismiss();
			}
		});

		lunchImg = (ImageView)dialog.findViewById(R.id.imgViewLunch);
		lunchImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				addMealMonthly(2);
				dialog.dismiss();
			}
		});

		dinnerImg = (ImageView)dialog.findViewById(R.id.imgViewDinner);
		dinnerImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				addMealMonthly(3);
				dialog.dismiss();
			}
		});

		snacksImg = (ImageView)dialog.findViewById(R.id.imgViewSnack);
		snacksImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				addMealMonthly(4);
				dialog.dismiss();
			}
		});
		dialog.show();
		/*DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
		dialog.getWindow().setLayout((6 * width)/7, LayoutParams.WRAP_CONTENT);*/

	}

	public void addMealMonthly(int mealId)
	{
		if(PlannerGridAdapter.brekMapMonthly.size()>0){

			for(int i=0;i<Constants.foodLogData.size();i++){	

				for ( String key : PlannerGridAdapter.brekMapMonthly.keySet() ) {

					String date = convertDateFormatMonthly(key);
					FitmiFoodLogDAO fitmiFoodLogData = Constants.foodLogData.get(i);
					fitmiFoodLogData.setMealId(String.valueOf(mealId));
					fitmiFoodLogData.setLogTime(date);
					fitmiFoodLogData.setDateAdded(date);
					FoodLoginModule.insertFitmifoodLogTable(fitmiFoodLogData, databaseObject);
				}

			}	

		}
		//need previous commented 
	//	setPlannerMonthly(stringDate);
		setPlannerMonthly(Constants.sDate);
		
/*		PlannerFragment.gridItemCliclEnable = false;
		PlannerFragment.activityClickEnablegrid = false;*/
		PlannerFragment.gridItemCliclEnable = true;
		PlannerFragment.activityClickEnablegrid = true;
		pga.notifyDataSetChanged();

		if(PlannerGridAdapter.brekMapMonthly.size()>0)
			PlannerGridAdapter.brekMapMonthly.clear();
		if(Constants.foodLogData.size()>0)
			Constants.foodLogData.clear();
	}
	
	   @Override
	public void onPause() {
		// TODO Auto-generated method stub
		   try{
		   getActivity().unregisterReceiver(dateSetReceiver);
		   }catch (Exception a)
		   {
			   a.printStackTrace();
		   }
		super.onPause();
	}
}
