package com.fitmi.activitys;

import java.util.ArrayList;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.InjectView;
import butterknife.OnClick;

import com.db.modules.UnitModule;
import com.db.modules.UserInfoModule;
import com.fitmi.R;
import com.fitmi.adapter.ViewPagerAdapter;
import com.fitmi.dao.UnitItemDAO;
import com.fitmi.dao.UserInfoDAO;
import com.fitmi.fragments.HomeFragment;
import com.fitmi.utils.Constants;
import com.fitmi.utils.ExceptionHandler;

public class TabActivity extends BaseFragmentActivity {

	public ViewPager _mViewPager;
	public ViewPagerAdapter _madapter;
	
	

	@InjectView(R.id.HomeLinear_Tab)
	public LinearLayout homeLinear;

	@InjectView(R.id.ProfileLinear_Tab)
	LinearLayout profileLinear;

	@InjectView(R.id.ActivityLinear_Tab)
	LinearLayout activityLinear;

	@InjectView(R.id.HelpLinear_Tab)
	LinearLayout helpLinear;

	@InjectView(R.id.CalendarLinear_Tab)
	LinearLayout calendarLinear;
	
	@InjectView(R.id.HomeImg_Tab)
	ImageView HomeImg_Tab;
	
	@InjectView(R.id.ProfileImg_Tab)
	ImageView ProfileImg_Tab;
	
	@InjectView(R.id.ActivityImg_Tab)
	ImageView ActivityImg_Tab;
	
	@InjectView(R.id.HelpImg_Tab)
	ImageView HelpImg_Tab;
	
	@InjectView(R.id.CalendarImg_Tab)
	ImageView CalendarImg_Tab;
	
	HomeFragment selectedFragment;
	
	UserInfoModule obj;
	ArrayList<UserInfoDAO> userList;
	
	// Storage for camera image URI components
		private final static String CAPTURED_PHOTO_PATH_KEY = "mCurrentPhotoPath";
		private final static String CAPTURED_PHOTO_URI_KEY = "mCapturedImageURI";
		// Required for camera operations in order to save the image file on resume.
		private String mCurrentPhotoPath = null;
		private Uri mCapturedImageURI = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab);
		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(TabActivity.this));
		prepareKnives();
		
		obj = new UserInfoModule(TabActivity.this);
		
		userList = obj.userList();
		Log.e("Started Tabs", "Yes");
		_mViewPager = (ViewPager) findViewById(R.id.ViewPager);
		_madapter = new ViewPagerAdapter(getApplicationContext(),
				getSupportFragmentManager());
		_mViewPager.setAdapter(_madapter);
		//_mViewPager.setCurrentItem(1);
		
		resetTabs();
		
		if(userList.size()>0){
			
			homeLinear.setClickable(true);
			activityLinear.setClickable(true);
			helpLinear.setClickable(true);
			calendarLinear.setClickable(true);
			
			CalendarImg_Tab.setClickable(true);
			HelpImg_Tab.setClickable(true);
			ProfileImg_Tab.setClickable(true);
			HomeImg_Tab.setClickable(true);
			ActivityImg_Tab.setClickable(true);
			
			_mViewPager.setCurrentItem(0);
			homeLinear.setBackgroundColor(getResources().getColor(
					R.color.royal_blue));
		}
		else{	
			
			Constants.homeLinear = homeLinear; 
			Constants.activityLinear = activityLinear; 
			Constants.helpLinear = helpLinear; 
			Constants.calendarLinear = calendarLinear; 
			
			Constants.CalendarImg_Tab = CalendarImg_Tab; 
			Constants.HelpImg_Tab = HelpImg_Tab; 
			Constants.ProfileImg_Tab = ProfileImg_Tab; 
			Constants.HomeImg_Tab = HomeImg_Tab; 
			Constants.ActivityImg_Tab = ActivityImg_Tab; 
			
			homeLinear.setClickable(false);
			activityLinear.setClickable(false);
			helpLinear.setClickable(false);
			calendarLinear.setClickable(false);
			
			CalendarImg_Tab.setClickable(false);
			HelpImg_Tab.setClickable(false);
			ProfileImg_Tab.setClickable(false);
			HomeImg_Tab.setClickable(false);
			ActivityImg_Tab.setClickable(false);
			
			_mViewPager.setCurrentItem(5);
			profileLinear.setBackgroundColor(getResources().getColor(
					R.color.royal_blue));
			
		}

		


		/*profileLinear.setBackgroundColor(getResources().getColor(
				R.color.royal_blue));*/
		/*homeLinear.setBackgroundColor(getResources().getColor(
				R.color.royal_blue));*/
		
	}

	@OnClick({ R.id.HomeLinear_Tab, R.id.HomeImg_Tab })
	public void homeTab() {

		Constants.sTempDate="";
		Constants.shareIntent = false;
		resetTabs();
		homeLinear.setBackgroundColor(getResources().getColor(
				R.color.royal_blue));
		_mViewPager.setCurrentItem(0);

	}

	@OnClick({ R.id.ProfileLinear_Tab, R.id.ProfileImg_Tab })
	public void profileTab() {
		Constants.sTempDate="";
		Constants.shareIntent = false;
		resetTabs();
		profileLinear.setBackgroundColor(getResources().getColor(
				R.color.royal_blue));
		if(Constants.fragmentSet)
		_mViewPager.setCurrentItem(5);
		else
			_mViewPager.setCurrentItem(1);

	}

	@OnClick({ R.id.ActivityLinear_Tab, R.id.ActivityImg_Tab })
	public void activityTab() {

		resetTabs();
		activityLinear.setBackgroundColor(getResources().getColor(
				R.color.royal_blue));
		_mViewPager.setCurrentItem(2);

	}

	@OnClick({ R.id.HelpLinear_Tab, R.id.HelpImg_Tab })
	public void helpTab() {
		Constants.shareIntent = false;
		resetTabs();
		helpLinear.setBackgroundColor(getResources().getColor(
				R.color.royal_blue));
		_mViewPager.setCurrentItem(3);

	}

	@OnClick({ R.id.CalendarLinear_Tab, R.id.CalendarImg_Tab })
	public void calendarTab() {
	//	Constants.sTempDate="";
		Constants.shareIntent = false;
		resetTabs();
		calendarLinear.setBackgroundColor(getResources().getColor(
				R.color.royal_blue));
		_mViewPager.setCurrentItem(4);

	}

	public void resetTabs() {

		homeLinear.setBackgroundColor(getResources().getColor(R.color.bg_blue));
		profileLinear.setBackgroundColor(getResources().getColor(
				R.color.bg_blue));
		activityLinear.setBackgroundColor(getResources().getColor(
				R.color.bg_blue));
		helpLinear.setBackgroundColor(getResources().getColor(R.color.bg_blue));
		calendarLinear.setBackgroundColor(getResources().getColor(
				R.color.bg_blue));
		_madapter.notifyDataSetChanged();

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		// FragmentManager manager = getSupportFragmentManager();
		// int backStackEntryCount = manager.getBackStackEntryCount();
		// Log.e("backStackEntryCount---", backStackEntryCount + "");
		// if (manager != null && backStackEntryCount > 0) {
		// Fragment currFrag = manager.getFragments().get(
		// backStackEntryCount - 1);
		// FragmentManager childFragmentManager = currFrag
		// .getChildFragmentManager();
		// if (childFragmentManager.getBackStackEntryCount() > 0) {
		//
		// currFrag.onResume();
		//
		// }
		//
		// }

		super.onBackPressed();

		HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager()
				.findFragmentByTag("HomeFragment");
		
		if (homeFragment !=null && homeFragment.isVisible()) {
			homeFragment.updateDate();
		}
	}	
	
	
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	if (mCurrentPhotoPath != null) {
	savedInstanceState.putString(CAPTURED_PHOTO_PATH_KEY, mCurrentPhotoPath);
	}
	if (mCapturedImageURI != null) {
	savedInstanceState.putString(CAPTURED_PHOTO_URI_KEY, mCapturedImageURI.toString());
	}
	super.onSaveInstanceState(savedInstanceState);
	}
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
	if (savedInstanceState.containsKey(CAPTURED_PHOTO_PATH_KEY)) {
	mCurrentPhotoPath = savedInstanceState.getString(CAPTURED_PHOTO_PATH_KEY);
	}
	if (savedInstanceState.containsKey(CAPTURED_PHOTO_URI_KEY)) {
	mCapturedImageURI = Uri.parse(savedInstanceState.getString(CAPTURED_PHOTO_URI_KEY));
	}
	super.onRestoreInstanceState(savedInstanceState);
	}
	/**
	* Getters and setters.
	*/
	public String getCurrentPhotoPath() {
	return mCurrentPhotoPath;
	}
	public void setCurrentPhotoPath(String mCurrentPhotoPath) {
	this.mCurrentPhotoPath = mCurrentPhotoPath;
	}
	public Uri getCapturedImageURI() {
	return mCapturedImageURI;
	}
	public void setCapturedImageURI(Uri mCapturedImageURI) {
	this.mCapturedImageURI = mCapturedImageURI;
	}

}
