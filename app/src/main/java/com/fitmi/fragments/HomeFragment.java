package com.fitmi.fragments;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.callback.NotificationCalorieIntake;
import com.callback.NotificationCaloryBurn;
import com.callback.NotificationSleep;
import com.callback.NotificationWater;
import com.db.DatabaseHelper;
import com.db.modules.ActivityModule;
import com.db.modules.BpLogModule;
import com.db.modules.FoodLoginModule;
import com.db.modules.SleepModule;
import com.db.modules.UnitModule;
import com.db.modules.UserInfoModule;
import com.db.modules.WaterModule;
import com.db.modules.WeightLogModule;
import com.dialog.Alert;
import com.fitmi.R;
import com.fitmi.activitys.TabActivity;
import com.fitmi.customviews.CustomScrollView;
import com.fitmi.dao.BloodPressureDAO;
import com.fitmi.dao.CaloryBaselineDAO;
import com.fitmi.dao.UnitItemDAO;
import com.fitmi.utils.Constants;
import com.fitmi.utils.DateModule;
import com.fitmi.utils.NotificationTotalCaloryChange;
import com.fitmi.utils.SaveSharedPreferences;
import com.lifesense.ble.LsBleManager;
import com.lifesense.ble.PairCallback;
import com.lifesense.ble.ReceiveDataCallback;
import com.lifesense.ble.SearchCallback;
import com.lifesense.ble.bean.BloodPressureData;
import com.lifesense.ble.bean.KitchenScaleData;
import com.lifesense.ble.bean.LsDeviceInfo;
import com.lifesense.ble.bean.PedometerData;
import com.lifesense.ble.bean.WeightData_A2;
import com.lifesense.ble.bean.WeightData_A3;
import com.lifesense.ble.bean.WeightUserInfo;
import com.lifesense.ble.commom.DeviceType;
import com.szugyi.circlemenu.view.CircleImageView;
import com.szugyi.circlemenu.view.CircleLayout;
import com.szugyi.circlemenu.view.CircleLayout.OnItemClickListener;
import com.szugyi.circlemenu.view.CircleLayout.OnItemSelectedListener;

public class HomeFragment extends BaseFragment implements NotificationTotalCaloryChange ,NotificationCaloryBurn , NotificationSleep , NotificationWater,NotificationCalorieIntake{//implements NotificationTotalCaloryChange


	@InjectView(R.id.ScrollView_HomeFragment)
	CustomScrollView scrollViewHomeFragment;
	
	@InjectView(R.id.view_click)
	View view_click;
	
	@InjectView(R.id.Heading)
	public TextView heading;

	@InjectView(R.id.Back)
	public ImageView back;
	
	@InjectView(R.id.backLiner)
	LinearLayout backLiner;
	
	@InjectView(R.id.homeCupValue)
	TextView homeCupValue;

	@InjectView(R.id.Date)
	TextView dateTextView;
	
	@InjectView(R.id.txtHomeBpGraphDia)
	TextView txtHomeBpGraphDia;

	@InjectView(R.id.AddFoodLogging)
	TextView addFoodLogging;

	@InjectView(R.id.AddActivity)
	TextView addActivity;

	@InjectView(R.id.AddWeight)
	TextView addWeight;

	@InjectView(R.id.AddBloodPressure)
	TextView addBloodPressure;

	@InjectView(R.id.AddSleep)
	TextView addSleep;

	@InjectView(R.id.AddWater)
	TextView addWater;

	@InjectView(R.id.CircularMenuHolder)
	RelativeLayout circularMenuHolder;

	@InjectView(R.id.CircularMenuView)
	View circularMenuView;

	@InjectView(R.id.FrameParent_HomeFragment)
	FrameLayout parentFrame;

	@InjectView(R.id.homeOzValue)
	TextView homeOzValue;

	// ********** For CircleMenu **************

	@InjectView(R.id.main_calendar_image)
	CircleImageView Menu1;

	@InjectView(R.id.main_cloud_image)
	CircleImageView Menu2;

	@InjectView(R.id.main_facebook_image)
	CircleImageView Menu3;

	@InjectView(R.id.main_key_image)
	CircleImageView Menu4;

	@InjectView(R.id.main_profile_image)
	CircleImageView Menu5;

	@InjectView(R.id.main_tap_image)
	CircleImageView Menu6;

	
	//sending to calorie page
	int mRootId = 0;
	@InjectView(R.id.frameHomeFoodGraph)
	FrameLayout frameHomeFoodGraph;
	/*
	 * @InjectView(R.id.main_business_card_image) CircleImageView Menu5;
	 */

	@InjectView(R.id.main_circle_layout)
	CircleLayout main_circle_layout;

	@InjectView(R.id.AddDummyText)
	TextView addDummyText;
	
	@InjectView(R.id.waterFrameLayout)
	FrameLayout waterFrameLayout;
	
	//@InjectView(R.id.circular_relativeView)
	//RelativeLayout circular_relativeView;
	

	// *******************************************

	/**
	 * 
	 * For Deselect Menus
	 * 
	 */

	@InjectView(R.id.FoodLogging_AlphaLinear)
	LinearLayout foodLoggingAlphaLinear;

	@InjectView(R.id.Activity_AlphaLinear)
	LinearLayout activityAlphaLinear;

	@InjectView(R.id.Weight_AlphaLinear)
	LinearLayout weightAlphaLinear;

	@InjectView(R.id.BloodPressure_AlphaLinear)
	LinearLayout bpAlphaLinear;

	@InjectView(R.id.Sleep_AlphaLinear)
	LinearLayout sleepAlphaLinear;

	@InjectView(R.id.Water_AlphaLinear)
	LinearLayout waterAlphaLinear;



	@InjectView(R.id.txtViewWater)
	TextView txtViewWater;

	@InjectView(R.id.txtViewSleep)
	TextView txtViewSleep;

	@InjectView(R.id.txtViewBlodPreser)
	TextView txtViewBlodPreser;

	@InjectView(R.id.txtViewTotalWeight)
	TextView txtViewTotalWeight;

	@InjectView(R.id.txtViewTotalburn)
	TextView txtViewTotalburn;

	@InjectView(R.id.txtViewtotalIntake)
	TextView txtViewtotalIntake;

	@InjectView(R.id.txtViewSys)
	TextView txtViewSys;

	@InjectView(R.id.txtViewDia)
	TextView txtViewDia;

	@InjectView(R.id.txtViewtotalCalory)
	TextView txtViewtotalCalory;

	@InjectView(R.id.txtvWeight)
	TextView txtvWeight;
	
	@InjectView(R.id.txtvWeightunit)
	TextView txtvWeightunit;
	
	/*
	 *   Graph layout	
	 */


	@InjectView(R.id.txtHomeWaterGraph)
	TextView txtHomeWaterGraph;

	@InjectView(R.id.txtHomeSleepGraph)
	TextView txtHomeSleepGraph;

	@InjectView(R.id.txtHomeBpGraphSys)
	TextView txtHomeBpGraphSys;

	@InjectView(R.id.txtHomeWeightGraph)
	TextView txtHomeWeightGraph;

	@InjectView(R.id.txtHomeActivityGraph)
	TextView txtHomeActivityGraph;

	@InjectView(R.id.txtviewHomeFoodGraph)
	TextView txtviewHomeFoodGraph;


	double caloryIntake = 0;
	double caloryBurn = 0;
	double weight = 0;
	int bpSys = 0;
	int bpDia = 0;
	double sleep = 0;
	double water = 0;

	/*
	 *   Graph layout end	
	 */	


	private static long lastYPosition = 0;
	private static long lastXPosition = 0;
	private static int currentClickedPos = 0;
	int itemShow = 0;

	int[] srcImage;

	String currrentDate = "";

	DatabaseHelper databaseObject;

	CaloryBaselineDAO detailCaloryBaseline;

	@InjectView(R.id.totalCaloryBurn)
	TextView totalCaloryBurn;

	@InjectView(R.id.totalSleepHome)
	TextView totalSleepHome;

	String totalCaloty = "",totalCalotyBurn="" , totalOz = "";

	String today = "";

	DateModule dateModule = new DateModule();
	DateModule getDate = new DateModule();
	WaterModule waterDb;
	WeightLogModule weightModule;
	SleepModule sleepModule;
	BpLogModule bpModule;
	ArrayList<BloodPressureDAO> bpArray;

/*	//sync
	LsBleManager lsBleManager;
	Button buttonsearch;
	SearchCallback searchCallback;
	boolean isDone = false;
	ReceiveDataCallback receiveDataCallback;
	PairCallback pairCallback;

	List<DeviceType> list = new ArrayList<DeviceType>();
	DeviceType deviceType;

	LsDeviceInfo deviceInfo;*/
	
	//unit selection Type
			UnitModule unitModel;
			ArrayList<UnitItemDAO> unitItem;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.fragment_home, container, false);

		ButterKnife.inject(this, view);
		setNullClickListener(view);
	
		
		heading.setText(getResources().getString(R.string.fitmi));
		back.setVisibility(View.GONE);
		circularMenuHolder.setVisibility(View.GONE);
		waterDb = new WaterModule(getActivity());
		weightModule = new WeightLogModule(getActivity());
		sleepModule = new SleepModule(getActivity());
		bpModule = new BpLogModule(getActivity());

		lastYPosition = 0;
		lastXPosition = 0;
		currentClickedPos = 0;		
		Constants.txtViewtotalCalory = txtViewtotalCalory;
		Constants.txtViewtotalCaloryBurn = totalCaloryBurn;
		Constants.txtviewHomeFoodGraph = txtviewHomeFoodGraph;
		Constants.txtHomeActivityGraph = txtHomeActivityGraph;
		Constants.txtHomeSleep = totalSleepHome;
		Constants.homeOzValue = homeOzValue;
		Constants.homeCupValue = homeCupValue;
		Constants.homeCaloryIntake = txtViewtotalIntake;
		Constants.calendreSet = 0;
		Constants.pageLog = 0;
		Constants.activityLog = false;
		Constants.fromFragment = 0;
		Constants.fragmentSet = false;		

		Constants.PROFILE_ID = SaveSharedPreferences.getProfileId(getActivity());

		databaseObject = new DatabaseHelper(getActivity());
		try {

			databaseObject.createDatabase();

			databaseObject.openDatabase();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addFoodLogging.setOnClickListener(new PlusIconClick(
				Constants.FOOD_LOGGING_PLUS));
		addActivity.setOnClickListener(new PlusIconClick(
				Constants.ACTIVITY_LOGGING_PLUS));
		addWeight.setOnClickListener(new PlusIconClick(
				Constants.WEIGHT_LOGGING_PLUS));

		removeDeselectedMenu();
		addOnItemclickListener();

		
		
		
		unitModel = new UnitModule(getActivity());

		unitItem = unitModel.selectUnitLogList();
		

		if(unitItem.size()>0){

			for(int i =0;i<unitItem.size();i++)
			{
				UnitItemDAO unitObj = unitItem.get(i);

				if(unitObj.getType().equalsIgnoreCase("height")){

					if(unitObj.getUnitId().equalsIgnoreCase("1"))
					{
						
						Constants.gunitht = 0;
				
						
					}else{

						Constants.gunitht = 1;
					}
				}
				else if(unitObj.getType().equalsIgnoreCase("weight")){

					if(unitObj.getUnitId().equalsIgnoreCase("3"))
					{
						Constants.gunitwt = 1;
						

					}else{
						Constants.gunitwt = 0;
						
					}
				}
				else if(unitObj.getType().equalsIgnoreCase("blood_pressure")){

					if(unitObj.getUnitId().equalsIgnoreCase("5"))
					{
						
						Constants.gunitbp = 0;
						
						
					}else{
						Constants.gunitbp = 1;
					
					}
				}
				else if(unitObj.getType().equalsIgnoreCase("food_weight")){

					if(unitObj.getUnitId().equalsIgnoreCase("7"))
					{

						Constants.gunitfw = 0;
					}else{

						Constants.gunitfw = 1;
					}
				}
			}
			
			

		}else{
			
			Constants.gunitwt = 0;
			Constants.gunitht = 0;
			Constants.gunitbp = 0;
			Constants.gunitfw = 0;
		}

		//exportDatabse("Fitmi.sqlite");

//need to see work here avinash

	/*	Calendar c = Calendar.getInstance();	
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");			
		today = df.format(c.getTime());
*/
		if (Constants.sTempDate.length() == 0) {
			//Constants.sDate = "Tuesday, February 10, 2015";
			
			Calendar c1 = Calendar.getInstance();	
			SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");			
			today = df1.format(c1.getTime());
			Constants.sTempDate=today;
			
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
			System.out.println("Calender post format :"+Constants.postDate); 
			//Toast.makeText(getActivity(), Constants.postDate, Toast.LENGTH_LONG).show();
			
		}
		else{
			today=Constants.sTempDate;
		}
		if(Constants.USER_ID !="" && Constants.PROFILE_ID !=""){
			totalCaloty = FoodLoginModule.todayTotalCalory(today,databaseObject);
			detailCaloryBaseline = UserInfoModule.getCaloryBaseline(databaseObject);
			totalCalotyBurn = ActivityModule.todayTotalCaloryBurn(today, databaseObject,getActivity());
			Constants.conditionDate = today;

			setAlldata();

			foodlogHomeGraph();

			activityHomeGraph();

			setTotalWater();

			setTotalWeight(today);

			setTotalSleep();
			
			setBpSysDia();
		}

		databaseObject.closeDataBase();

	
		
		
		return view;

	}

	@OnClick(R.id.ParentLinear1_HomeFragment)
	public void gotoFoodLogging() {

		if(Constants.PROFILE_ID !=null && Constants.PROFILE_ID.equalsIgnoreCase("")){

			Alert.showAlert(getActivity(), "Kindly create at least one user to access the App");
			return;
		}

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.add(R.id.root_home_frame, new FoodLoggingFragment(),
				"FoodLoggingFragment");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();

	}

	@OnClick(R.id.ParentLinear2_HomeFragment)
	public void gotoActivity() {

		if(Constants.PROFILE_ID !=null && Constants.PROFILE_ID.equalsIgnoreCase("")){

			Alert.showAlert(getActivity(), "Kindly create at least one user to access the App");
			return;
		}

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.add(R.id.root_home_frame, new MyActivityFragment(),
				"MyActivityFragment");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();

	}

	@OnClick(R.id.ParentLinear3_HomeFragment)
	public void gotoWeight() {


		if(Constants.PROFILE_ID !=null && Constants.PROFILE_ID.equalsIgnoreCase("")){

			Alert.showAlert(getActivity(), "Kindly create at least one user to access the App");
			return;
		}

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		/*transaction.add(R.id.root_home_frame, new WeightFragment(),
				"WeightFragment");*/

		transaction.add(R.id.root_home_frame, new WeightGraphFragment(),
				"WeightFragment");

		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();

	}

	@OnClick(R.id.ParentLinear4_HomeFragment)
	public void gotoBloodPressure() {

		if(Constants.PROFILE_ID !=null && Constants.PROFILE_ID.equalsIgnoreCase("")){

			Alert.showAlert(getActivity(), "Kindly create at least one user to access the App");
			return;
		}

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		/*transaction.add(R.id.root_home_frame, new BloodPressureFragment(),
				"BloodPressureFragment");*/

		transaction.add(R.id.root_home_frame, new
				BloodPressureGraphFragment(), "BloodPressureFragment");

		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();

	}

	@OnClick(R.id.ParentLinear5_HomeFragment)
	public void gotoSleep() {

		if(Constants.PROFILE_ID !=null && Constants.PROFILE_ID.equalsIgnoreCase("")){

			Alert.showAlert(getActivity(), "Kindly create at least one user to access the App");
			return;
		}

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.add(R.id.root_home_frame, new SleepFragment(),
				"WaterFragment");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();

	}

	@OnClick(R.id.ParentLinear6_HomeFragment)
	public void gotoWater() {

		if(Constants.PROFILE_ID !=null && Constants.PROFILE_ID.equalsIgnoreCase("")){

			Alert.showAlert(getActivity(), "Kindly create at least one user to access the App");
			return;
		}

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.add(R.id.root_home_frame, new WaterFragment(),
				"WaterFragment");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();

	}

	@OnClick(R.id.backLiner)
	public void back() {

		getActivity().onBackPressed();

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

	@OnClick(R.id.Date)
	public void changeDate() {

		
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.add(R.id.root_home_frame, new CalendarFragment(),
				"CalendarFragment");
		getFragmentManager().popBackStack(null,
				FragmentManager.POP_BACK_STACK_INCLUSIVE);
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();

	}


	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (Constants.sDate.length() == 0) {
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

			System.out.println("Calender post format :"+Constants.postDate); 
			//Toast.makeText(getActivity(), Constants.postDate, Toast.LENGTH_LONG).show();
		}
		//Toast.makeText(getActivity(), Constants.postDate, Toast.LENGTH_LONG).show();
		dateTextView.setText(Constants.sDate);
		lkpParams();
	}

	public void updateDate() {

		dateTextView.setText(Constants.sDate); 
		
		String setDate = dateTextView.getText().toString().trim();	
		updateDataOnDate(setDate);
	}

	//wheel icon listing 
	private void setColorToMenus(int drawable, int[] srcImg, int itemShow) {

		Menu1.setBackgroundResource(drawable);
		Menu2.setBackgroundResource(drawable);
		Menu3.setBackgroundResource(drawable);
		Menu4.setBackgroundResource(drawable);
		Menu5.setBackgroundResource(drawable);
		Menu6.setBackgroundResource(drawable);

		switch (itemShow) {
		case 4:

			Menu1.setVisibility(View.VISIBLE);
			Menu2.setVisibility(View.VISIBLE);
			Menu3.setVisibility(View.VISIBLE);
			Menu4.setVisibility(View.VISIBLE);
			Menu5.setVisibility(View.VISIBLE);
			Menu6.setVisibility(View.VISIBLE);

			Menu1.setImageResource(srcImg[0]);
			Menu2.setImageResource(srcImg[1]);
			Menu3.setImageResource(srcImg[2]);
			Menu4.setImageResource(srcImg[3]);
			Menu5.setImageResource(srcImg[1]);
			Menu6.setImageResource(srcImg[2]);

			break;

		case 5:

			Menu1.setVisibility(View.VISIBLE);
			Menu2.setVisibility(View.VISIBLE);
			Menu3.setVisibility(View.VISIBLE);
			Menu4.setVisibility(View.VISIBLE);
			Menu5.setVisibility(View.VISIBLE);
			Menu6.setVisibility(View.GONE);

			Menu1.setImageResource(srcImg[0]);
			Menu2.setImageResource(srcImg[1]);
			Menu3.setImageResource(srcImg[2]);
			Menu4.setImageResource(srcImg[3]);
			Menu5.setImageResource(srcImg[4]);

			break;

		case 6:

			Menu1.setVisibility(View.VISIBLE);
			Menu2.setVisibility(View.VISIBLE);
			Menu3.setVisibility(View.VISIBLE);
			Menu4.setVisibility(View.VISIBLE);
			Menu5.setVisibility(View.VISIBLE);
			Menu6.setVisibility(View.VISIBLE);

			Menu1.setImageResource(srcImg[0]);
			Menu2.setImageResource(srcImg[1]);
			Menu3.setImageResource(srcImg[2]);
			Menu4.setImageResource(srcImg[3]);
			Menu5.setImageResource(srcImg[4]);
			Menu6.setImageResource(srcImg[5]);

			break;

		}

	}

	@OnClick(R.id.Base_Header)
	public void clickHeaderBase() {

	}

	private void showDeselectedMenu() {

		foodLoggingAlphaLinear.setVisibility(View.VISIBLE);
		activityAlphaLinear.setVisibility(View.VISIBLE);
		weightAlphaLinear.setVisibility(View.VISIBLE);
		bpAlphaLinear.setVisibility(View.VISIBLE);
		sleepAlphaLinear.setVisibility(View.VISIBLE);
		waterAlphaLinear.setVisibility(View.VISIBLE);

		scrollViewHomeFragment.setEnableScrolling(false);

	}

	private void removeDeselectedMenu() {

		foodLoggingAlphaLinear.setVisibility(View.GONE);
		activityAlphaLinear.setVisibility(View.GONE);
		weightAlphaLinear.setVisibility(View.GONE);
		bpAlphaLinear.setVisibility(View.GONE);
		sleepAlphaLinear.setVisibility(View.GONE);
		waterAlphaLinear.setVisibility(View.GONE);

		scrollViewHomeFragment.setEnableScrolling(true);

	}

	@OnClick(R.id.FoodLogging_AlphaLinear)
	public void onClickFoodLoggingAlphaLinear() {

	}

	@OnClick(R.id.Activity_AlphaLinear)
	public void onClickActivityAlphaLinear() {
		if (circularMenuHolder.getVisibility() == View.VISIBLE) {
			circularMenuHolder.setVisibility(View.GONE);
			removeDeselectedMenu();
			addFoodLogging.setTextColor(Color.BLACK);
			addActivity.setTextColor(Color.BLACK);
			addWeight.setTextColor(Color.BLACK);
		}
	}

	@OnClick(R.id.Weight_AlphaLinear)
	public void onClickWeightAlphaLinear() {
		if (circularMenuHolder.getVisibility() == View.VISIBLE) {
			circularMenuHolder.setVisibility(View.GONE);
			removeDeselectedMenu();
			addFoodLogging.setTextColor(Color.BLACK);
			addActivity.setTextColor(Color.BLACK);
			addWeight.setTextColor(Color.BLACK);
			
		}
	}

	@OnClick(R.id.BloodPressure_AlphaLinear)
	public void onClickBpAlphaLinear() {
		if (circularMenuHolder.getVisibility() == View.VISIBLE) {
			circularMenuHolder.setVisibility(View.GONE);
			removeDeselectedMenu();
			addFoodLogging.setTextColor(Color.BLACK);
			addActivity.setTextColor(Color.BLACK);
			addWeight.setTextColor(Color.BLACK);
		}
	}

	
	@OnClick(R.id.Sleep_AlphaLinear)
	public void onClickSleepAlphaLinear() {
		if (circularMenuHolder.getVisibility() == View.VISIBLE) {
			circularMenuHolder.setVisibility(View.GONE);
			removeDeselectedMenu();
			addFoodLogging.setTextColor(Color.BLACK);
			addActivity.setTextColor(Color.BLACK);
			addWeight.setTextColor(Color.BLACK);
		}
	}

	@OnClick(R.id.Water_AlphaLinear)
	public void onClickWaterAlphaLinear() {
		if (circularMenuHolder.getVisibility() == View.VISIBLE) {
			circularMenuHolder.setVisibility(View.GONE);
			removeDeselectedMenu();
			addFoodLogging.setTextColor(Color.BLACK);
			addActivity.setTextColor(Color.BLACK);
			addWeight.setTextColor(Color.BLACK);
		}
	}
	
	@OnClick(R.id.view_click)
	public void onClickWaterAlphaLinearview_click() {
		Log.e("view_click", "view_click");
		if (circularMenuHolder.getVisibility() == View.VISIBLE) {
			circularMenuHolder.setVisibility(View.GONE);
			removeDeselectedMenu();
			addFoodLogging.setTextColor(Color.BLACK);
			addActivity.setTextColor(Color.BLACK);
			addWeight.setTextColor(Color.BLACK);
		}
	}
	

	@OnClick(R.id.AddDummyText)
	public void onClickAddDummyText() {

		circularMenuHolder.setVisibility(View.GONE);
		removeDeselectedMenu();
		addFoodLogging.setTextColor(Color.BLACK);
		addActivity.setTextColor(Color.BLACK);
		addWeight.setTextColor(Color.BLACK);

	}

	class PlusIconClick implements OnClickListener {

		int itemClicked = -1;

		public PlusIconClick(int item) {
			this.itemClicked = item;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int[] locations = new int[2];
			v.getLocationOnScreen(locations);
			int x = locations[0];
			int y = locations[1];
			if (circularMenuHolder.getVisibility() == View.VISIBLE
					&& (x >= lastYPosition + 100)) {
				circularMenuHolder.setVisibility(View.GONE);
				removeDeselectedMenu();
			} else {
				lastYPosition = y;
				lastXPosition = x;
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

				params.topMargin = y - 400;

				circularMenuHolder.setVisibility(View.VISIBLE);
				circularMenuView.setLayoutParams(params);

				showDeselectedMenu();

				switch (itemClicked) {
				case Constants.FOOD_LOGGING_PLUS:
					currentClickedPos = 0;
					itemShow = 4;
					//avinash circle value changes 
				/*	srcImage = new int[] { R.drawable.breakfast,
							R.drawable.lunch, R.drawable.dinner,
							R.drawable.snack };*/
					
					srcImage = new int[] {R.drawable.snack , R.drawable.dinner, R.drawable.lunch,R.drawable.breakfast};
					
					setColorToMenus(R.drawable.circle_green, srcImage, itemShow);
					foodLoggingAlphaLinear
					.setBackgroundResource(R.color.transparent);
					addFoodLogging.setTextColor(getResources().getColor(R.color.home_green_select_dark));
					break;

				case Constants.ACTIVITY_LOGGING_PLUS:

					currentClickedPos = 1;

					itemShow = 6;

					//srcImage = new int[]{R.drawable.chin_ups,R.drawable.treadmill,R.drawable.swimming,R.drawable.jumprope,R.drawable.boxing,R.drawable.lifting_weight};
					srcImage = new int[]{R.drawable.lifting_weight,R.drawable.boxing,R.drawable.jumprope,R.drawable.swimming,R.drawable.treadmill,R.drawable.chin_ups};

					setColorToMenus(R.drawable.circle_pink,srcImage,itemShow);
					activityAlphaLinear
					.setBackgroundResource(R.color.transparent);
					addActivity.setTextColor(getResources().getColor(R.color.home_pink_select_light));

					break;

				case Constants.WEIGHT_LOGGING_PLUS:

					currentClickedPos = 2;

					itemShow = 5;

					srcImage = new int[]{R.drawable.bone_mass,R.drawable.bmi,R.drawable.body_fat,R.drawable.body_water,R.drawable.muscle_mass};

					setColorToMenus(R.drawable.circle_navy_blue,srcImage,itemShow);
					weightAlphaLinear
					.setBackgroundResource(R.color.transparent);
					addWeight.setTextColor(getResources().getColor(R.color.home_navyblue_select_dark));

					break;
				}
			}
		}

	}

	public void addOnItemclickListener() {

		main_circle_layout.setOnItemClickListener(new OnItemClickListener() {	

			@Override
			public void onItemClick(View view, String name) {
				// TODO Auto-generated method stub
				Log.e("Name", name+" OK");

				//{R.drawable.lifting_weight,R.drawable.boxing,R.drawable.jumprope,R.drawable.swimming,R.drawable.treadmill,R.drawable.chin_ups};

				addFoodLogging.setTextColor(Color.BLACK);
				addActivity.setTextColor(Color.BLACK);
				addWeight.setTextColor(Color.BLACK);

				FragmentTransaction transaction = getFragmentManager()
						.beginTransaction();

				MyActivityFragment activityfragment = new MyActivityFragment();
				Bundle bundle = new Bundle();

				switch (view.getId()) {
				case R.id.main_calendar_image:					

					switch (itemShow) {
					case 4:
						//Bundle bundle = new Bundle();

						if(Constants.PROFILE_ID !=null && Constants.PROFILE_ID.equalsIgnoreCase("")){

							Alert.showAlert(getActivity(), "Kindly create at least one user to access the App");
							return;
						}

						FoodLoggingFragment foodLogin = new FoodLoggingFragment();
						bundle.putInt("mealid", 4);
						bundle.putString("foodtype", "Snack");
						bundle.putBoolean("replace", false);
						bundle.putBoolean("log", false);

						transaction.add(R.id.root_home_frame, foodLogin,
								"FoodLoggingFragment");
						foodLogin.setArguments(bundle);
						transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
						transaction.addToBackStack(null);
						transaction.commit();
						circularMenuHolder.setVisibility(View.GONE);
						removeDeselectedMenu();

						break;

					case 5:

						if(Constants.PROFILE_ID !=null && Constants.PROFILE_ID.equalsIgnoreCase("")){

							Alert.showAlert(getActivity(), "Kindly create at least one user to access the App");
							return;
						}
						/*transaction.add(R.id.root_home_frame, new WeightFragment(),
								"WeightFragment");*/
						transaction.add(R.id.root_home_frame, new WeightGraphFragment(),
								"WeightFragment");
						transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
						transaction.addToBackStack(null);
						transaction.commit();

						circularMenuHolder.setVisibility(View.GONE);
						removeDeselectedMenu();

						break;
					case 6:
						if(Constants.PROFILE_ID !=null && Constants.PROFILE_ID.equalsIgnoreCase("")){

							Alert.showAlert(getActivity(), "Kindly create at least one user to access the App");
							return;
						}

						//bundle.putInt("livestrong_id", 2952);
						bundle.putInt("livestrong_id", 5083);
						bundle.putBoolean("replace", false);
						bundle.putBoolean("log", false);
						bundle.putBoolean("activity", true);
						activityfragment.setArguments(bundle);


						transaction.add(R.id.root_home_frame, activityfragment,
								"MyActivityFragment");
						transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
						transaction.addToBackStack(null);
						transaction.commit();

						circularMenuHolder.setVisibility(View.GONE);
						removeDeselectedMenu();

						break;
					}


					break;

				case R.id.main_cloud_image:

					switch (itemShow) {
					case 4:

						if(Constants.PROFILE_ID !=null && Constants.PROFILE_ID.equalsIgnoreCase("")){

							Alert.showAlert(getActivity(), "Kindly create at least one user to access the App");
							return;
						}
						//Bundle bundle = new Bundle();
						FoodLoggingFragment foodLogin = new FoodLoggingFragment();
						bundle.putInt("mealid", 3);
						bundle.putString("foodtype", "Dinner");
						bundle.putBoolean("replace", false);
						bundle.putBoolean("log", false);

						transaction.add(R.id.root_home_frame, foodLogin,
								"FoodLoggingFragment");
						foodLogin.setArguments(bundle);
						transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
						transaction.addToBackStack(null);
						transaction.commit();
						circularMenuHolder.setVisibility(View.GONE);
						removeDeselectedMenu();
						break;

					case 5:

						if(Constants.PROFILE_ID !=null && Constants.PROFILE_ID.equalsIgnoreCase("")){

							Alert.showAlert(getActivity(), "Kindly create at least one user to access the App");
							return;
						}

						/*transaction.add(R.id.root_home_frame, new WeightFragment(),
								"WeightFragment");*/
						transaction.add(R.id.root_home_frame, new WeightGraphFragment(),
								"WeightFragment");
						transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
						transaction.addToBackStack(null);
						transaction.commit();
						circularMenuHolder.setVisibility(View.GONE);
						removeDeselectedMenu();

						break;
					case 6:

						if(Constants.PROFILE_ID !=null && Constants.PROFILE_ID.equalsIgnoreCase("")){

							Alert.showAlert(getActivity(), "Kindly create at least one user to access the App");
							return;
						}

						//bundle.putInt("livestrong_id", 3547);
						bundle.putInt("livestrong_id", 2606);
						bundle.putBoolean("replace", false);
						bundle.putBoolean("log", false);
						bundle.putBoolean("activity", true);
						activityfragment.setArguments(bundle);

						transaction.add(R.id.root_home_frame, activityfragment,
								"MyActivityFragment");
						transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
						transaction.addToBackStack(null);
						transaction.commit();
						circularMenuHolder.setVisibility(View.GONE);
						removeDeselectedMenu();

						break;
					}

					break;

				case R.id.main_facebook_image:

					switch (itemShow) {
					case 4:


						if(Constants.PROFILE_ID !=null && Constants.PROFILE_ID.equalsIgnoreCase("")){

							Alert.showAlert(getActivity(), "Kindly create at least one user to access the App");
							return;
						}
						//Bundle bundle = new Bundle();
						FoodLoggingFragment foodLogin = new FoodLoggingFragment();
						bundle.putInt("mealid", 2);
						bundle.putString("foodtype", "Lunch");
						bundle.putBoolean("replace", false);
						bundle.putBoolean("log", false);

						transaction.add(R.id.root_home_frame, foodLogin,
								"FoodLoggingFragment");
						foodLogin.setArguments(bundle);
						transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
						transaction.addToBackStack(null);
						transaction.commit();
						circularMenuHolder.setVisibility(View.GONE);
						removeDeselectedMenu();
						break;

					case 5:

						if(Constants.PROFILE_ID !=null && Constants.PROFILE_ID.equalsIgnoreCase("")){

							Alert.showAlert(getActivity(), "Kindly create at least one user to access the App");
							return;
						}
						/*transaction.add(R.id.root_home_frame, new WeightFragment(),
								"WeightFragment");*/
						transaction.add(R.id.root_home_frame, new WeightGraphFragment(),
								"WeightFragment");
						transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
						transaction.addToBackStack(null);
						transaction.commit();
						circularMenuHolder.setVisibility(View.GONE);
						removeDeselectedMenu();

						break;
					case 6:
						if(Constants.PROFILE_ID !=null && Constants.PROFILE_ID.equalsIgnoreCase("")){

							Alert.showAlert(getActivity(), "Kindly create at least one user to access the App");
							return;
						}
						//bundle.putInt("livestrong_id", 5062);
						bundle.putInt("livestrong_id", 5027);
						bundle.putBoolean("replace", false);
						bundle.putBoolean("log", false);
						bundle.putBoolean("activity", true);
						activityfragment.setArguments(bundle);

						transaction.add(R.id.root_home_frame, activityfragment,
								"MyActivityFragment");
						transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
						transaction.addToBackStack(null);
						transaction.commit();
						circularMenuHolder.setVisibility(View.GONE);
						removeDeselectedMenu();
						break;
					}

					break;

				case R.id.main_key_image:

					switch (itemShow) {
					case 4:

						if(Constants.PROFILE_ID !=null && Constants.PROFILE_ID.equalsIgnoreCase("")){

							Alert.showAlert(getActivity(), "Kindly create at least one user to access the App");
							return;
						}
						//Bundle bundle = new Bundle();
						FoodLoggingFragment foodLogin = new FoodLoggingFragment();
						bundle.putInt("mealid", 1);
						bundle.putString("foodtype", "Breakfast");	
						bundle.putBoolean("replace", false);
						bundle.putBoolean("log", false);

						transaction.add(R.id.root_home_frame, foodLogin,
								"FoodLoggingFragment");						
						foodLogin.setArguments(bundle);
						transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
						transaction.addToBackStack(null);
						transaction.commit();
						circularMenuHolder.setVisibility(View.GONE);
						removeDeselectedMenu();
						
						break;

					case 5:

						if(Constants.PROFILE_ID !=null && Constants.PROFILE_ID.equalsIgnoreCase("")){

							Alert.showAlert(getActivity(), "Kindly create at least one user to access the App");
							return;
						}
						/*transaction.add(R.id.root_home_frame, new WeightFragment(),
								"WeightFragment");*/
						transaction.add(R.id.root_home_frame, new WeightGraphFragment(),
								"WeightFragment");
						transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
						transaction.addToBackStack(null);
						transaction.commit();
						circularMenuHolder.setVisibility(View.GONE);
						removeDeselectedMenu();
						break;
					case 6:

						if(Constants.PROFILE_ID !=null && Constants.PROFILE_ID.equalsIgnoreCase("")){

							Alert.showAlert(getActivity(), "Kindly create at least one user to access the App");
							return;
						}
						//bundle.putInt("livestrong_id", 5027);
						bundle.putInt("livestrong_id", 5062);
						bundle.putBoolean("replace", false);
						bundle.putBoolean("log", false);
						bundle.putBoolean("activity", true);
						activityfragment.setArguments(bundle);

						transaction.add(R.id.root_home_frame, activityfragment,
								"MyActivityFragment");
						transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
						transaction.addToBackStack(null);
						transaction.commit();
						circularMenuHolder.setVisibility(View.GONE);
						removeDeselectedMenu();
						break;
					}

					break;

				case R.id.main_profile_image:

					switch (itemShow) {					

					case 4:

						if(Constants.PROFILE_ID !=null && Constants.PROFILE_ID.equalsIgnoreCase("")){

							Alert.showAlert(getActivity(), "Kindly create at least one user to access the App");
							return;
						}
						//Bundle bundle = new Bundle();
						FoodLoggingFragment foodLogin = new FoodLoggingFragment();
						bundle.putInt("mealid", 3);
						bundle.putString("foodtype", "Dinner");
						bundle.putBoolean("replace", false);
						bundle.putBoolean("log", false);

						transaction.add(R.id.root_home_frame, foodLogin,
								"FoodLoggingFragment");
						foodLogin.setArguments(bundle);
						transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
						transaction.addToBackStack(null);
						transaction.commit();
						circularMenuHolder.setVisibility(View.GONE);
						removeDeselectedMenu();
						break;
					case 5:
						if(Constants.PROFILE_ID !=null && Constants.PROFILE_ID.equalsIgnoreCase("")){

							Alert.showAlert(getActivity(), "Kindly create at least one user to access the App");
							return;
						}
						/*transaction.add(R.id.root_home_frame, new WeightFragment(),
								"WeightFragment");*/
						transaction.add(R.id.root_home_frame, new WeightGraphFragment(),
								"WeightFragment");
						transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
						transaction.addToBackStack(null);
						transaction.commit();
						circularMenuHolder.setVisibility(View.GONE);
						removeDeselectedMenu();
						break;
					case 6:
						if(Constants.PROFILE_ID !=null && Constants.PROFILE_ID.equalsIgnoreCase("")){

							Alert.showAlert(getActivity(), "Kindly create at least one user to access the App");
							return;
						}
						
									
						/*transaction.add(R.id.root_home_frame, new MyActivityFragment(),
								"MyActivityFragment");
						transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
						transaction.addToBackStack(null);
						transaction.commit();
						circularMenuHolder.setVisibility(View.GONE);
						removeDeselectedMenu();*/
						bundle.putInt("livestrong_id", 3547);
						bundle.putBoolean("replace", false);
						bundle.putBoolean("log", false);
						bundle.putBoolean("activity", true);
						activityfragment.setArguments(bundle);

						transaction.add(R.id.root_home_frame, activityfragment,
								"MyActivityFragment");
						transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
						transaction.addToBackStack(null);
						transaction.commit();
						circularMenuHolder.setVisibility(View.GONE);
						removeDeselectedMenu();
						break;
					}

					break;

				case R.id.main_tap_image:

					switch (itemShow) {

					case 4:


						if(Constants.PROFILE_ID !=null && Constants.PROFILE_ID.equalsIgnoreCase("")){

							Alert.showAlert(getActivity(), "Kindly create at least one user to access the App");
							return;
						}
						//Bundle bundle = new Bundle();
						FoodLoggingFragment foodLogin = new FoodLoggingFragment();
						bundle.putInt("mealid", 2);
						bundle.putString("foodtype", "Lunch");
						bundle.putBoolean("replace", false);
						bundle.putBoolean("log", false);

						transaction.add(R.id.root_home_frame, foodLogin,
								"FoodLoggingFragment");
						foodLogin.setArguments(bundle);
						transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
						transaction.addToBackStack(null);
						transaction.commit();
						circularMenuHolder.setVisibility(View.GONE);
						removeDeselectedMenu();
						break;
					case 5:
						if(Constants.PROFILE_ID !=null && Constants.PROFILE_ID.equalsIgnoreCase("")){

							Alert.showAlert(getActivity(), "Kindly create at least one user to access the App");
							return;
						}
						/*transaction.add(R.id.root_home_frame, new WeightFragment(),
								"WeightFragment");*/
						transaction.add(R.id.root_home_frame, new WeightGraphFragment(),
								"WeightFragment");
						transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
						transaction.addToBackStack(null);
						transaction.commit();
						circularMenuHolder.setVisibility(View.GONE);
						removeDeselectedMenu();
						break;
					case 6:
						if(Constants.PROFILE_ID !=null && Constants.PROFILE_ID.equalsIgnoreCase("")){

							Alert.showAlert(getActivity(), "Kindly create at least one user to access the App");
							return;
						}
						//	bundle.putInt("livestrong_id", 2606);
						bundle.putInt("livestrong_id", 2952);
						bundle.putBoolean("replace", false);
						bundle.putBoolean("log", false);
						bundle.putBoolean("activity", true);
						activityfragment.setArguments(bundle);

						transaction.add(R.id.root_home_frame, activityfragment,
								"MyActivityFragment");
						transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
						transaction.addToBackStack(null);
						transaction.commit();
						circularMenuHolder.setVisibility(View.GONE);
						removeDeselectedMenu();
						break;
					}

					break;

				}
			}
		});
	}

	public void setAlldata()
	{
		if(detailCaloryBaseline !=null){
			//String.format("%.0f",detailCaloryBaseline.getTotalIntake());
			txtViewtotalIntake.setText(detailCaloryBaseline.getTotalIntake()+" cal");
			txtViewTotalburn.setText(detailCaloryBaseline.getTotalBurned()+" cal");

			String[] separated = detailCaloryBaseline.getWeight().split("\\.");			
//avinash
		///	txtViewTotalWeight.setText(separated[0]+" lb");
			
			if(Constants.gunitwt==0){
				txtViewTotalWeight.setText(separated[0]+" Kg");
				}else{
					double wt,f_wt;
					wt=Double.parseDouble(separated[0]);
					f_wt=wt*2.2046;
					
					Log.e(" Getting value in oz ", String.valueOf(f_wt));
					
					DecimalFormat formatter = new DecimalFormat("##.##");
					String yourFormattedString = formatter.format(f_wt);
				//	dataList.get(arg0).getMealWeight()
					Log.e(" Getting value in oz ", String.valueOf(yourFormattedString));
					txtViewTotalWeight.setText(yourFormattedString+" lbs");
				}
			//txtViewBlodPreser.setText();
			txtViewSleep.setText(detailCaloryBaseline.getSleep()+" hours");
			txtViewWater.setText(detailCaloryBaseline.getWater()+" cups");

			txtViewSys.setText(detailCaloryBaseline.getBpSys());		
			txtViewDia.setText(detailCaloryBaseline.getBpDia());	

			Constants.TOTAL_CALORY_INTAKE = detailCaloryBaseline.getTotalIntake();
			Constants.TOTAL_CALORY_BURN = detailCaloryBaseline.getTotalBurned();

			// caloryIntake = Integer.parseInt(detailCaloryBaseline.getTotalIntake());

			try{
				Log.e(" Home calorie ", detailCaloryBaseline.getTotalIntake());
			caloryIntake = Double.parseDouble(detailCaloryBaseline.getTotalIntake());

			caloryBurn = Double.parseDouble(detailCaloryBaseline.getTotalBurned());
			weight = Double.parseDouble(separated[0]);
			// bp = Integer.parseInt(detailCaloryBaseline.getTotalIntake());			

			String[] separatedSleep = detailCaloryBaseline.getSleep().split(" ");
			sleep = Double.parseDouble(separatedSleep[0]);

			String[] separatedWater = detailCaloryBaseline.getWater().split(" ");	
			water = Double.parseDouble(separatedWater[0]);

			bpSys = Integer.parseInt(detailCaloryBaseline.getBpSys());
			bpDia = Integer.parseInt(detailCaloryBaseline.getBpDia());
			}catch(Exception a){
				a.printStackTrace();
			}
		}
	}


	public void exportDatabse(String databaseName) {
		try {
			File sd = Environment.getExternalStorageDirectory();
			File data = Environment.getDataDirectory();

			Date today = new Date();

			if (sd.canWrite()) {
				String currentDBPath = "/data/" + getActivity().getPackageName()
						+ "/databases/" + databaseName + "";
				String backupDBPath = "backupname" + today.toString().trim()
						+ ".sqlite";
				File currentDB = new File(data, currentDBPath);
				File backupDB = new File(sd, backupDBPath);

				if (currentDB.exists()) {
					FileChannel src = new FileInputStream(currentDB)
					.getChannel();
					FileChannel dst = new FileOutputStream(backupDB)
					.getChannel();
					dst.transferFrom(src, 0, src.size());
					src.close();
					dst.close();
				}
			}
		} catch (Exception e) {

		}
	}

	@Override
	public void setTotalCalory(float caloryCalculate) {
		// TODO Auto-generated method stub

		Constants.txtViewtotalCalory.setText((int)caloryCalculate+"");

		double totalCalory = caloryCalculate;

		caloryIntake = Double.parseDouble(Constants.TOTAL_CALORY_INTAKE);

		if(totalCalory>caloryIntake){

			FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
			lp.gravity = Gravity.BOTTOM;
			Constants.txtviewHomeFoodGraph.setLayoutParams(lp);
		}
		else{
			double  graphHeight = (totalCalory/caloryIntake);				
			int percent = (int) (graphHeight * 100);
			int heightset = percent*130/100;

			FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, heightset);
			lp.gravity = Gravity.BOTTOM;
			Constants.txtviewHomeFoodGraph.setLayoutParams(lp);
		}


	}

	@Override
	public void setTotalCaloryBurn(int caloryCalculate) {
		// TODO Auto-generated method stub
		Constants.txtViewtotalCaloryBurn.setText(caloryCalculate+""); 

		double totalCalory = caloryCalculate;

		if(!Constants.TOTAL_CALORY_BURN.equalsIgnoreCase(""))
			caloryBurn = Double.parseDouble(Constants.TOTAL_CALORY_BURN);

		if(totalCalory>caloryBurn){

			FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
			lp.gravity = Gravity.BOTTOM;


			Constants.txtHomeActivityGraph.setLayoutParams(lp);
		}
		else{
			double  graphHeight = (totalCalory/caloryBurn);				
			int percent = (int) (graphHeight * 100);
			int heightset = percent*130/100;

			FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, heightset);
			lp.gravity = Gravity.BOTTOM;

			Constants.txtHomeActivityGraph.setLayoutParams(lp);			
		}

	}

	@OnClick(R.id.txtPreview)
	public void datePreview()
	{
		String setDate = dateTextView.getText().toString().trim();
		setDate = dateModule.datePreview(setDate);
		dateTextView.setText(setDate);	
		
		updateDataOnDate(setDate);
	}

	@OnClick(R.id.txtNext)
	public void dateNext()
	{
		String setDate = dateTextView.getText().toString().trim();
		setDate = dateModule.dateNext(setDate);
		dateTextView.setText(setDate);	
		
		updateDataOnDate(setDate);
	}


	//showing weight on white portion
	public void setTotalWeight(String date)
	{
	//	String weight = weightModule.sumWaight(date);

		String weight = weightModule.avgWaight(date);
		if(weight==null){		

			if(Constants.gunitwt==0){
			
			
				txtvWeight.setText("0");
				txtvWeightunit.setText("Kg");
			}else{
				txtvWeight.setText("0");
				txtvWeightunit.setText("lbs");
			}
			weightHomeGraph("0");
			
		}else{

			

			if(Constants.gunitwt==0){
				double wt_te;
				wt_te=Double.parseDouble(weight);
				DecimalFormat formatter = new DecimalFormat("##.##");
				String wt_te_s = formatter.format(wt_te);
				txtvWeight.setText(wt_te_s);
				txtvWeightunit.setText("Kg");
					}else{
						double wt,f_wt;
						wt=Double.parseDouble(weight);
						f_wt=wt*2.2046;
						
						Log.e(" Getting value in oz ", String.valueOf(f_wt));
						
						DecimalFormat formatter = new DecimalFormat("##.##");
						String yourFormattedString = formatter.format(f_wt);
					//	dataList.get(arg0).getMealWeight()
						Log.e(" Getting value in oz ", String.valueOf(yourFormattedString));
						txtvWeight.setText(yourFormattedString);
						txtvWeightunit.setText("lbs");
					}
			
		//	txtvWeight.setText(weight);
			weightHomeGraph(weight);
		}
	}

	public void setTotalWater()
	{
		String sum = waterDb.sumWaterOz();

		if(sum == null){

			homeOzValue.setText("0.0");
			homeCupValue.setText("0");
			waterHomeGraph("0");

		}else{
			
			double ozInt = Double.parseDouble(sum);
			double cup = ozInt/8;
			int cupInt = (int) cup;
			homeCupValue.setText(cupInt+"");
			
			double oz = ozInt%8;
			//double ozVzlue = cup;
			
			homeOzValue.setText(String.format("%.1f", oz)+"");
			waterHomeGraph(sum);
		}
	}


	public void setTotalSleep()
	{
		String sum = sleepModule.sumSleepHour();

		if(sum == null){

			totalSleepHome.setText("0");
			sleepHomeGraph("0");

		}else{
			totalSleepHome.setText(sum);
			sleepHomeGraph(sum);
		}
	}

	public void setBpSysDia()
	{
		bpArray = bpModule.getAverage();

		if(bpArray.size()>0){
			
			if(bpArray.get(0).getSys() !=null){
				
				double bpSys = Double.parseDouble(bpArray.get(0).getSys())/bpArray.size();
				double bpDia = Double.parseDouble(bpArray.get(0).getDia())/bpArray.size();
				
				int bpSysInt = (int)bpSys;
				int bpDiaInt = (int)bpDia;

				txtViewSys.setText(bpSysInt+"");		
				txtViewDia.setText(bpDiaInt+"");	
				bpHomeGraph(bpArray);
			}else{
				
				txtViewSys.setText("120");		
				txtViewDia.setText("80");
				bpHomeGraph(bpArray);
			}
		}

	}

	/**
	 *  Graph functionality implementation section
	 */

	public void foodlogHomeGraph()
	{
		if(totalCaloty !=null && !totalCaloty.equalsIgnoreCase("")){
			

			double totalCalory = Double.parseDouble(totalCaloty);
			
			txtViewtotalCalory.setText((int)totalCalory+"");

			if(totalCalory>caloryIntake){

				FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
				lp.gravity = Gravity.BOTTOM;
				txtviewHomeFoodGraph.setLayoutParams(lp);
			}
			else{
				double  graphHeight = (totalCalory/caloryIntake);				
				int percent = (int) (graphHeight * 100);
				int heightset = percent*260/100;

				FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, heightset);
				lp.gravity = Gravity.BOTTOM;
				txtviewHomeFoodGraph.setLayoutParams(lp);
			}
		}else{
			txtViewtotalCalory.setText("0");

			FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 0);
			lp.gravity = Gravity.BOTTOM;
			txtviewHomeFoodGraph.setLayoutParams(lp);


		}
	}

	public void activityHomeGraph()
	{
		if(totalCalotyBurn !=null && !totalCalotyBurn.equalsIgnoreCase("")){
			totalCaloryBurn.setText(totalCalotyBurn);

			double totalCalory = Double.parseDouble(totalCalotyBurn);

			if(totalCalory>caloryBurn){

				FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
				lp.gravity = Gravity.BOTTOM;
				txtHomeActivityGraph.setLayoutParams(lp);
			}
			else{
				double  graphHeight = (totalCalory/caloryBurn);				
				int percent = (int) (graphHeight * 100);
				int heightset = percent*260/100;

				FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, heightset);
				lp.gravity = Gravity.BOTTOM;
				txtHomeActivityGraph.setLayoutParams(lp);
			}
		}else{
			totalCaloryBurn.setText("0");

			FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 0);
			lp.gravity = Gravity.BOTTOM;
			txtHomeActivityGraph.setLayoutParams(lp);

		}		

	}


	public void weightHomeGraph(String weightValue)
	{
		double totalWeight = Double.parseDouble(weightValue);

		if(totalWeight>weight){

			FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
			lp.gravity = Gravity.BOTTOM;
			txtHomeWeightGraph.setLayoutParams(lp);
		}
		else{
			double  graphHeight = (totalWeight/weight);				
			int percent = (int) (graphHeight * 100);
			int heightset = percent*260/100;

			FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, heightset);
			lp.gravity = Gravity.BOTTOM;
			txtHomeWeightGraph.setLayoutParams(lp);
		}
	}



	public void sleepHomeGraph(String sleepValue)
	{
		double totalSleep = Double.parseDouble(sleepValue);

		if(totalSleep>sleep){

			FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
			lp.gravity = Gravity.BOTTOM;
			txtHomeSleepGraph.setLayoutParams(lp);
		}
		else{
			double  graphHeight = (totalSleep/sleep);				
			int percent = (int) (graphHeight * 100);
			int heightset = percent*260/100;

			FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, heightset);
			lp.gravity = Gravity.BOTTOM;
			txtHomeSleepGraph.setLayoutParams(lp);
		}
	}


	public void waterHomeGraph(String waterValue)
	{
		double totalWater = Double.parseDouble(waterValue);

		if(totalWater>64){

			FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
			lp.gravity = Gravity.BOTTOM;
			txtHomeWaterGraph.setLayoutParams(lp);
		}
		else{
			double  graphHeight = (totalWater/64);				
			int percent = (int) (graphHeight * 100);
			int heightset = percent*260/100;

			FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, heightset);
			lp.gravity = Gravity.BOTTOM;
			txtHomeWaterGraph.setLayoutParams(lp);
		}
	}

	public void bpHomeGraph(ArrayList<BloodPressureDAO> bpArray)
	{
		double bpSysAvg = 0;
		double bpDiaAvg = 0;
	
		if(bpArray.get(0).getSys() !=null){
			
			bpSysAvg = Double.parseDouble(bpArray.get(0).getSys())/bpArray.size();
			bpDiaAvg = Double.parseDouble(bpArray.get(0).getDia())/bpArray.size();
		}
		

		if(bpSysAvg>bpSys){

			FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
			lp.gravity = Gravity.BOTTOM;
			txtHomeBpGraphSys.setLayoutParams(lp);
		}else{

			double  graphHeight = (bpSysAvg/bpSys);				
			int percent = (int) (graphHeight * 100);
			int heightset = percent*130/100;

			FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, heightset);
			lp.gravity = Gravity.BOTTOM;
			txtHomeBpGraphSys.setLayoutParams(lp);
		}
		
		if(bpDiaAvg>bpDia){

			FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
			lp.gravity = Gravity.BOTTOM;
			txtHomeBpGraphDia.setLayoutParams(lp);
		}else{

			double  graphHeight = (bpDiaAvg/bpDia);				
			int percent = (int) (graphHeight * 100);
			int heightset = percent*130/100;

			FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, heightset);
			lp.gravity = Gravity.BOTTOM;
			txtHomeBpGraphDia.setLayoutParams(lp);
		}


	}

	@Override
	public void sleepHourChange(String totalHour) {
		// TODO Auto-generated method stub
		
		Constants.txtHomeSleep.setText(totalHour);
		
		//sleepHomeGraph(totalHour);
	}

	@Override
	public void waterOzChange(String totalHour) {
		// TODO Auto-generated method stub
		
		double ozInt = Double.parseDouble(totalHour);
		double cup = ozInt/8;
		int cupInt = (int) cup;
		Constants.homeCupValue.setText(cupInt+"");
		
		double oz = ozInt%8;
		//double ozVzlue = cup;
		
		Constants.homeOzValue.setText(String.format("%.1f", oz)+"");
		//waterHomeGraph(totalHour);
		
	}

	@Override
	public void setNewCaloryIntake(String calorie) {
		// TODO Auto-generated method stub
		Constants.homeCaloryIntake.setText(calorie);
	}	
	
	
	private void updateDataOnDate(String setDate)
	{
		today = getDate.conditionDateFormat(setDate);
		Constants.conditionDate = today;
		//avinash next and prv date changes
		Constants.sTempDate=today;
		Constants.sDate= getDate.sDateFormat(setDate);
		Constants.postDate=getDate.getPostDateFormat(setDate);
		if(Constants.USER_ID !="" && Constants.PROFILE_ID !=""){
			totalCaloty = FoodLoginModule.todayTotalCalory(today,databaseObject);		

			totalCalotyBurn = ActivityModule.todayTotalCaloryBurn(today, databaseObject,getActivity());			

			setTotalWater();

			setTotalWeight(today);

			setTotalSleep();
			
			setBpSysDia();
		}
		

		foodlogHomeGraph();

		activityHomeGraph();
	}
	
	@OnClick(R.id.frameHomeFoodGraph)
	public void clickDailyCalory() {
		
		Constants.fromFragment = 0;
		
		mRootId = R.id.root_home_frame;

		Bundle bundle = new Bundle();
		bundle.putInt("root_id", mRootId);
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();

		DailyCalorieIntakeFragment fragment = new DailyCalorieIntakeFragment();
		fragment.setArguments(bundle);
		transaction.add(mRootId, fragment, "DailyCalorieIntakeFragment");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();
	}
	
	private void lkpParams(){
		
	//	addFoodLogging.getP
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.e("HEIGHT OF CIRCULAR MENU", circularMenuHolder.getHeight()+"");
				Log.e("Framelayout height", waterFrameLayout.getHeight()+"");
				
				FrameLayout.LayoutParams relativeParams = new FrameLayout.LayoutParams(
				        new LayoutParams(
				                LayoutParams.MATCH_PARENT,
				                LayoutParams.WRAP_CONTENT));
				
				int top = waterFrameLayout.getHeight()/ 3;
				//circularMenuHolder
				
	
				if (waterFrameLayout.getHeight() > 300){
					top = top+42;	
					
				relativeParams.setMargins(0, -top, 0, 0);
				circularMenuHolder.setLayoutParams(relativeParams);
				circularMenuHolder.requestLayout();
				
				
				Log.e("Pixel toDP", pxToDp(top)+"");
				}
				//main_circle_layout
				
				
				
				
				
			}
		}, 500);
		
		
		
	}
	
	 public int dpToPx(int dp) {
			/*DisplayMetrics displayMetrics = mctx.getResources().getDisplayMetrics();
			int px = Math.round(dp
					* (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));*/

			final float scale = getActivity().getResources().getDisplayMetrics().density;
			int pixels = (int) (dp * scale + 0.5f);

			return pixels;
		}

		public int pxToDp(int px) {
			/*DisplayMetrics displayMetrics = mctx.getResources().getDisplayMetrics();
			int dp = Math.round(px
					/ (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));*/

			DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
			return (int) ((px/displayMetrics.density)+0.5);
			//return dp;
		}
	
}
