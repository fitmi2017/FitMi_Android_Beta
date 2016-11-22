package com.fitmi.fragments;

import java.io.IOException;
import java.text.DecimalFormat;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.db.DatabaseHelper;
import com.db.modules.UserInfoModule;
import com.fitmi.R;
import com.fitmi.activitys.SignInActivity;
import com.fitmi.adapter.InstrumentAdapter;
import com.fitmi.dao.UserInfoDAO;
import com.fitmi.utils.Constants;
import com.fitmi.utils.DateModule;
import com.fitmi.utils.HandelOutfemoryException;
import com.squareup.picasso.Picasso;

public class UserProfileFragment extends BaseFragment {

	@InjectView(R.id.Heading)
	public TextView heading;

	@InjectView(R.id.Back)
	public ImageView back;

	@InjectView(R.id.backLiner)
	LinearLayout backLiner;

	@InjectView(R.id.imageViewProfile)
	ImageView imageViewProfile;

	/* Device Sync Var by avinash */

	@InjectView(R.id.img_pedometer)
	ImageView img_pedometer;

	@InjectView(R.id.img_kitchenscale)
	ImageView img_kitchenscale;

	@InjectView(R.id.img_weightscale)
	ImageView img_weightscale;

	@InjectView(R.id.lay_pedometer)
	LinearLayout lay_pedometer;

	@InjectView(R.id.lay_kitchenscale)
	LinearLayout lay_kitchenscale;

	@InjectView(R.id.lay_weightscale)
	LinearLayout lay_weightscale;

	/*
	 * @InjectView(R.id.InstrumentsListView) ListView instrumentListView;
	 */

	@InjectView(R.id.txtDob)
	TextView txtDob;

	@InjectView(R.id.txtHeight)
	TextView txtHeight;

	@InjectView(R.id.txtActivityLevel)
	TextView txtActivityLevel;

	@InjectView(R.id.txtDailyCalorytake)
	TextView txtDailyCalorytake;

	@InjectView(R.id.txtWeight)
	TextView txtWeight;

	@InjectView(R.id.txtgender)
	TextView txtgender;

	@InjectView(R.id.UsersName)
	TextView UsersName;

	@InjectView(R.id.txt_sync_status)
	TextView txt_sync_status;
	
	InstrumentAdapter adapter;
	DatabaseHelper databaseObject;
	String userProfileId = "";
	UserInfoDAO userInfo;
	DateModule dateModule = new DateModule();
	String dob = "";
	int mRootId = 0;

	
	@InjectView(R.id.textgender)
	TextView textgender;
	
	@InjectView(R.id.textactivity)
	TextView textactivity;
	
	@InjectView(R.id.textbirthday)
	TextView textbirthday;
	
	@InjectView(R.id.textheight)
	TextView textheight;
	
	@InjectView(R.id.textweight)
	TextView textweight;
	
	@InjectView(R.id.textcalorie)
	TextView textcalorie;
	// avinash
	UserInfoModule userDb;
	
	

	String caloryIntake = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.fragment_user_profile, container,
				false);

		databaseObject = new DatabaseHelper(getActivity());
		try {

			databaseObject.createDatabase();

			databaseObject.openDatabase();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		// avinash
		userDb = new UserInfoModule(getActivity());

		caloryIntake = userDb.getCaloryTake();//

		if (Constants.homeLinear != null) {

			Constants.homeLinear.setClickable(true);
			Constants.activityLinear.setClickable(true);
			Constants.helpLinear.setClickable(true);
			Constants.calendarLinear.setClickable(true);

			Constants.CalendarImg_Tab.setClickable(true);
			Constants.HelpImg_Tab.setClickable(true);
			Constants.ProfileImg_Tab.setClickable(true);
			Constants.HomeImg_Tab.setClickable(true);
			Constants.ActivityImg_Tab.setClickable(true);
		}

		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
		    // Device does not support Bluetooth
			
		} else {
		    if (!mBluetoothAdapter.isEnabled()) {
		        // Bluetooth is not enable :)
		    	Constants.isBluetoothOnLocal=0;
		    	Constants.connectedTodevice=0;
		    	
		    }else{
		    	Constants.isBluetoothOnLocal=1;
		    
		    }
		}
		
		ButterKnife.inject(this, view);
		setNullClickListener(view);
		heading.setText("User Profile");
		
		try{
		backLiner.setVisibility(View.GONE);
		}catch(Exception a)
		{
			
		}
		//changes by avinash to select particular user it replace the new one every time
	//	Constants.PROFILE_ID = UserInfoModule.getProfileId(databaseObject);
		userInfo = UserInfoModule.getUserInformation(databaseObject);

		dob = dateModule.dobDateFormat(userInfo.getDateOfBirth());

		UsersName.setText(userInfo.getFirstName() + " "
				+ userInfo.getLastName());
		txtDob.setText(dob);
		if(Constants.gunitht==0)
		{
		txtHeight.setText(userInfo.getHeightFt() + "'"
				+ userInfo.getHeightInc() + "''");
		}else{
			int inft;
			double _total,_final=0.0,ininch=0.0;
			inft=Integer.parseInt(userInfo.getHeightFt());
			ininch=Float.parseFloat(userInfo.getHeightInc());
			inft=inft*12;
			ininch=ininch/10;
			ininch=ininch*12;
			_final=inft+ininch;
			
			_total=_final*2.54;
			DecimalFormat formatter = new DecimalFormat("##.##");
			String finaltotal = formatter.format(_total);
			txtHeight.setText(finaltotal+" cm");
		}
		txtActivityLevel.setText(userInfo.getActivityLevel());
		/* txtDailyCalorytake.setText(userInfo.getDailyCaloryIntake()); */
		// avinash
		txtDailyCalorytake.setText(caloryIntake); //
		
		
	//	txtWeight.setText(userInfo.getWeight() + " kg");
		
		if(Constants.gunitwt==0){
			
		//	txtWeight.setText(userInfo.getWeight() + " kg");
			int wei=0;
			double db=0.0;
			
			db=Double.parseDouble(userInfo.getWeight());
			wei=(int) db;
			txtWeight.setText(wei + " kg");
			}else{
				double wt,f_wt;
				wt=Double.parseDouble(userInfo.getWeight());
				f_wt=wt*2.2046;
				
				Log.e(" Getting value in wt ", String.valueOf(f_wt));
				
				DecimalFormat formatter = new DecimalFormat("##.##");
				String yourFormattedString = formatter.format(f_wt);
			//	dataList.get(arg0).getMealWeight()
				Log.e(" Getting value in wt ", String.valueOf(yourFormattedString));
				int wt1=0;
				double db1=0.0;
				
				db1=Double.parseDouble(yourFormattedString);
				wt1=(int) db1;
				txtWeight.setText(wt1 + " lbs");
				//txtWeight.setText(yourFormattedString+" lbs");
			}
		txtgender.setText(userInfo.getGender());

		String imagePath = userInfo.getPicturePath();
		if (imagePath != null && !imagePath.equalsIgnoreCase("")) {
			Bitmap myBitmap = HandelOutfemoryException.convertBitmap(imagePath);
			imageViewProfile.setImageBitmap(myBitmap);
			Picasso.with(getActivity()).load("file:" + imagePath).noFade().resize(80, 80).centerCrop().into(imageViewProfile);
		} else {

			if (userInfo.getGender().equalsIgnoreCase("Male")) {

				imageViewProfile.setImageResource(R.drawable.user_male);

			} else {

				imageViewProfile.setImageResource(R.drawable.user_female);
			}
		}

		
		if(Constants.isBluetoothOnLocal==1&&Constants.connectedTodevice==1){
			txt_sync_status.setText("Synced");
			
		}else{
			txt_sync_status.setText("Not Synced");
		}
		/*
		 * adapter = new InstrumentAdapter(getActivity());
		 * instrumentListView.setAdapter(adapter);
		 * 
		 * 
		 * 
		 * instrumentListView.setOnTouchListener(new OnTouchListener() { //
		 * Setting on Touch Listener for handling the touch inside ScrollView
		 * 
		 * @Override public boolean onTouch(View v, MotionEvent event) { //
		 * Disallow the touch request for parent scroll on touch of child view
		 * v.getParent().requestDisallowInterceptTouchEvent(true); return false;
		 * }
		 * 
		 * 
		 * });
		 */
		return view;
	}

	@OnClick(R.id.Logout_UserProfile)
	public void logout() {

		/*
		 * Intent intent = new Intent(getActivity(), SignInActivity.class);
		 * intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
		 * Intent.FLAG_ACTIVITY_CLEAR_TASK); startActivity(intent);
		 * getActivity().finish();
		 */

		// Bundle bundle = new Bundle();
		// bundle.putInt("root_id", R.id.root_profile_frame);
		// UserListFragment fragment = new UserListFragment();
		// fragment.setArguments(bundle);

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.add(R.id.root_profile_frame, new UserListFragment(),
				"UserListFragment");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack("UserListFragment");
		transaction.commit();

	}

	@OnClick(R.id.EditProfile_UserProfile)
	public void editProfile() {

		Bundle bundle = new Bundle();
		bundle.putInt("root_id", R.id.root_profile_frame);
		UserInfoFragment fragment = new UserInfoFragment();
		fragment.setArguments(bundle);

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.add(R.id.root_profile_frame, fragment, "UserInfoFragment");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack("UserInfoFragment");
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
		bundle.putInt("root_id", R.id.root_profile_frame);
		fragment.setArguments(bundle);

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.add(R.id.root_profile_frame, fragment, "SettingsFragment");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack("SettingsFragment");
		transaction.commit();

	}

	@OnClick(R.id.Base_Header)
	public void clickHeaderBase() {

	}

	@OnClick(R.id.lay_kitchenscale)
	public void clickKitchenScale() {

		DeviceSyncFragment fragment = new DeviceSyncFragment();

		Bundle bundle = new Bundle();
		bundle.putInt("root_id", R.id.root_profile_frame);

		bundle.putString("userName",
				userInfo.getFirstName() + " " + userInfo.getLastName());
		bundle.putString("deviceName", "FoodScale");
		bundle.putString("imgPath", userInfo.getPicturePath());
		bundle.putInt("scaleType", 1);

		fragment.setArguments(bundle);

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.add(R.id.root_profile_frame, fragment, "DeviceSyncFragment");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack("DeviceSyncFragment");
		transaction.commit();
	}
	
	@OnClick(R.id.lay_weightscale)
	public void clickWeightScale() {

		DeviceSyncFragment fragment = new DeviceSyncFragment();

		Bundle bundle = new Bundle();
		bundle.putInt("root_id", R.id.root_profile_frame);

		bundle.putString("userName",
				userInfo.getFirstName() + " " + userInfo.getLastName());
		bundle.putString("deviceName", "WeightScale");
		bundle.putString("imgPath", userInfo.getPicturePath());
		bundle.putInt("scaleType", 2);

		fragment.setArguments(bundle);

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.add(R.id.root_profile_frame, fragment, "DeviceSyncFragment");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack("DeviceSyncFragment");
		transaction.commit();
	}
	
	@OnClick(R.id.lay_pedometer)
	public void clickPedometer() {

		DeviceSyncFragment fragment = new DeviceSyncFragment();

		Bundle bundle = new Bundle();
		bundle.putInt("root_id", R.id.root_profile_frame);

		bundle.putString("userName",
				userInfo.getFirstName() + " " + userInfo.getLastName());
		bundle.putString("deviceName", "Pedometer");
		bundle.putString("imgPath", userInfo.getPicturePath());
		bundle.putInt("scaleType", 0);

		fragment.setArguments(bundle);

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction
				.replace(R.id.root_profile_frame, fragment, "DeviceSyncFragment");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack("DeviceSyncFragment");
		transaction.commit();
	}
	

}
