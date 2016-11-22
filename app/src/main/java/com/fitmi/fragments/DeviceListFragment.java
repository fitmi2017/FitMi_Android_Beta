package com.fitmi.fragments;

import java.io.IOException;
import java.util.ArrayList;

import android.bluetooth.BluetoothAdapter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.db.DatabaseHelper;
import com.db.modules.UserInfoModule;
import com.fitmi.R;
import com.fitmi.adapter.TwoRowDataListAdapter;
import com.fitmi.dao.UserInfoDAO;
import com.fitmi.utils.Constants;
import com.fitmi.utils.HandelOutfemoryException;
import com.squareup.picasso.Picasso;

public class DeviceListFragment extends BaseFragment {

	@InjectView(R.id.Heading)
	public TextView heading;

	@InjectView(R.id.Back)
	public ImageView back;
	
	@InjectView(R.id.backLiner)
	LinearLayout backLiner;
	int mRootId = 0;

	@InjectView(R.id.DeviceListView)
	ListView deviceListView;
	
	@InjectView(R.id.UsersName)
	TextView UsersName;
	
	@InjectView(R.id.imgDevListProfile)
	ImageView imgDevListProfile;

	TwoRowDataListAdapter adapter;
	ArrayList<String> deviceNames;
	DatabaseHelper databaseObject;
	UserInfoDAO userInfo;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.fragment_device_list, container,
				false);

		ButterKnife.inject(this, view);
		setNullClickListener(view);
		heading.setText("Devices");

		deviceNames = new ArrayList<String>();
		
		//temp hidden by avinash
	//	deviceNames.add("Weight Scale");
	//	deviceNames.add("Pedometer");
		deviceNames.add("Food Scale");
	//	deviceNames.add("Blood Pressure Monitor");
				
	//	deviceNames.add("Sleep Tracker");
		
		databaseObject = new DatabaseHelper(getActivity());
		try {

			databaseObject.createDatabase();

			databaseObject.openDatabase();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		userInfo = UserInfoModule.getUserInformation(databaseObject);
		UsersName.setText(userInfo.getFirstName()+" "+userInfo.getLastName());
		
		Bundle bundle = this.getArguments();
		if (bundle != null) {
			mRootId = bundle.getInt("root_id", R.id.root_home_frame);
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
		
		String imagePath = userInfo.getPicturePath();
		
		if(imagePath !=null && !imagePath.equalsIgnoreCase("")){
			Bitmap myBitmap = HandelOutfemoryException.convertBitmap(imagePath);
			imgDevListProfile.setImageBitmap(myBitmap);
			Picasso.with(getActivity()).load("file:" + imagePath).noFade().resize(80, 80).centerCrop().into(imgDevListProfile);
		}
		else{
				
				if (userInfo.getGender().equalsIgnoreCase("Male")) {	
					
					imgDevListProfile.setImageResource(R.drawable.user_male);

				} else {
					
					imgDevListProfile.setImageResource(R.drawable.user_female);
				}
			}

		adapter = new TwoRowDataListAdapter(getActivity(), deviceNames);
		deviceListView.setAdapter(adapter);

		deviceListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				DeviceSyncFragment fragment = new DeviceSyncFragment();

				Bundle bundle = new Bundle();
				
				//if(arg2==0){
					
					bundle.putString("userName",
							userInfo.getFirstName() + " " + userInfo.getLastName());
					bundle.putString("deviceName", "FoodScale");
					bundle.putString("imgPath", userInfo.getPicturePath());
					bundle.putInt("scaleType", 1);
					//					}
				//temp hidden by avinash
			/*	if(arg2==0){
		
					bundle.putString("userName",
							userInfo.getFirstName() + " " + userInfo.getLastName());
					bundle.putString("deviceName", "WeightScale");
					bundle.putString("imgPath", userInfo.getPicturePath());
					bundle.putInt("scaleType", 2);
				}
				else if(arg2==1){
					bundle.putString("userName",
							userInfo.getFirstName() + " " + userInfo.getLastName());
					bundle.putString("deviceName", "Pedometer");
					bundle.putString("imgPath", userInfo.getPicturePath());
					bundle.putInt("scaleType", 0);
								}		
				else if(arg2==2){
			
			bundle.putString("userName",
					userInfo.getFirstName() + " " + userInfo.getLastName());
			bundle.putString("deviceName", "KitchenScale");
			bundle.putString("imgPath", userInfo.getPicturePath());
			bundle.putInt("scaleType", 1);
								}*/

				
				fragment.setArguments(bundle);

				FragmentTransaction transaction = getFragmentManager()
						.beginTransaction();
				/*if(Constants.HomeRootId==0){
				transaction.add(R.id.root_home_frame, fragment,
						"DeviceSyncFragment");
				}
				else{
				transaction.add(R.id.root_profile_frame, fragment,
						"DeviceSyncFragment");
				}*/
				transaction.add(mRootId, fragment,
						"DeviceSyncFragment");
				
				transaction
						.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				transaction.addToBackStack(null);
				transaction.commit();

			}
		});

		return view;
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
		transaction.addToBackStack(null);
		transaction.commit();

	}
	
	@OnClick(R.id.Base_Header)
	public void clickHeaderBase() {

	}

}