package com.fitmi.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.db.DatabaseHelper;
import com.db.modules.ActivityModule;
import com.db.modules.UserInfoModule;
import com.db.modules.WeightLogModule;

import com.fitmi.R;
import com.fitmi.activitys.BaseActivity;
import com.fitmi.activitys.DeviceSyncService;
import com.fitmi.activitys.TabActivity;
import com.fitmi.dao.UserInfoDAO;
import com.fitmi.dao.WeightLogDAO;
import com.fitmi.utils.Constants;
import com.fitmi.utils.DateModule;
import com.fitmi.utils.HandelOutfemoryException;
import com.fitmi.utils.interFragmentScaleCommunicator;
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
import com.lifesense.ble.commom.BroadcastType;
import com.lifesense.ble.commom.DeviceType;
import com.squareup.picasso.Picasso;

public class DeviceSyncFragment extends BaseFragment {

    @InjectView(R.id.Heading)
    public TextView heading;

    @InjectView(R.id.Back)
    public ImageView back;

    @InjectView(R.id.backLiner)
    LinearLayout backLiner;

    @InjectView(R.id.imgSynkProfile)
    ImageView imgSynkProfile;

    @InjectView(R.id.CongratulationsText_DeviceSyncFragment)
    TextView congratulationsText;

    @InjectView(R.id.DeviceName_DeviceSyncFragment)
    TextView deviceName;

    @InjectView(R.id.DeviceSyncStatus_DeviceSyncFragment)
    TextView deviceSyncStatus;

    @InjectView(R.id.DeviceSyncImg_DeviceSyncFragment)
    ImageView deviceSyncImg;

    @InjectView(R.id.profileImage)
    ImageView profileImage;

    @InjectView(R.id.GetStartedText)
    TextView GetStartedText;

    @InjectView(R.id.UsersName)
    TextView UsersName;


    // Handler handler;
    int _scaleType = 0;

    DatabaseHelper databaseObject;
    UserInfoDAO userInfo;

    // Lifesense

    Button buttonsearch;

    boolean isDone = false;
    ReceiveDataCallback receiveDataCallback;
    PairCallback pairCallback;
    interFragmentScaleCommunicator scaleCommunicator;


    String _weight, _walkingsteps, _calorieburn;

    DateModule getDate = new DateModule();
    WeightLogModule weightModule;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.fragment_device_sync, container,
                false);
        scaleCommunicator = (interFragmentScaleCommunicator) getActivity();
        ButterKnife.inject(this, view);
        setNullClickListener(view);
        heading.setText("Devices");

        databaseObject = new DatabaseHelper(getActivity());
        try {

            databaseObject.createDatabase();

            databaseObject.openDatabase();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        weightModule = new WeightLogModule(getActivity());

        userInfo = UserInfoModule.getUserInformation(databaseObject);
        String mDeviceName = "";
        String userName = "";
        String imgPath = "";
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mDeviceName = bundle.getString("deviceName", "");
            userName = bundle.getString("userName", "");
            imgPath = bundle.getString("imgPath", "");
            _scaleType = bundle.getInt("scaleType", 0);
        }
        UsersName.setText(userName);

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth

        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                // Bluetooth is not enable :)
                Constants.isBluetoothOnLocal = 0;
                Constants.connectedTodevice = 0;

            } else {
                Constants.isBluetoothOnLocal = 1;

            }
        }

        if (Constants.isBluetoothOnLocal == 1 && Constants.connectedTodevice == 1) {
            deviceSyncStatus.setText("Synced");

        } else {
            deviceSyncStatus.setText("Not Synced");
        }
        /*
		 * if(imgPath !=null && !imgPath.equalsIgnoreCase("")){ Bitmap myBitmap
		 * = HandelOutfemoryException.convertBitmap(imgPath);
		 * imgSynkProfile.setImageBitmap(myBitmap); }
		 */
        if (imgPath != null && !imgPath.equalsIgnoreCase("")) {
            Bitmap myBitmap = HandelOutfemoryException.convertBitmap(imgPath);
            imgSynkProfile.setImageBitmap(myBitmap);
            Picasso.with(getActivity()).load("file:" + imgPath).noFade().resize(80, 80).centerCrop().into(imgSynkProfile);

        } else {

            if (userInfo.getGender().equalsIgnoreCase("Male")) {

                imgSynkProfile.setImageResource(R.drawable.user_male);

            } else {

                imgSynkProfile.setImageResource(R.drawable.user_female);
            }
        }

        if (mDeviceName.equalsIgnoreCase("FoodScale")) {
            profileImage.setImageResource(R.drawable.food1);
        } else if (mDeviceName.equalsIgnoreCase("WeightScale")) {
            profileImage.setImageResource(R.drawable.fat_scale);
        } else {
            profileImage.setImageResource(R.drawable.pedometer);
        }
        congratulationsText.setVisibility(View.GONE);
        deviceName.setText(mDeviceName);

        deviceSyncImg.setVisibility(View.VISIBLE);

        deviceSyncImg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//				OLD
//				// TODO Auto-generated method stub
//				deviceSyncStatus.setText("Syncing...");
//				sendBroadcast();
//				NEW
                scaleCommunicator.connectDevice();
            }
        });
		
			       	
	/*	        	WeightLogDAO weightDate = new WeightLogDAO();

					weightDate.setWeight(_weight);
					weightDate.setUserId(Constants.USER_ID);
					weightDate.setProfileId(Constants.PROFILE_ID);
					weightDate.setLogTime(getDate.getTime());
					weightDate.setAddedtime(getDate.getDateFormat());				

					weightModule.insertWeightInformation(weightDate);	
		        	*/


        deviceSyncImg.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //Button Pressed
                    deviceSyncImg.setAlpha(0.5f);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //finger was lifted
                    deviceSyncImg.setAlpha(1.0f);
                }
                return false;
            }
        });

        if(Constants.isSync)
            setText();

        return view;
    }

    @OnClick(R.id.GetStartedText)
    public void getStarted() {

        // handler.removeCallbacksAndMessages(null);
        if (Constants.isSync) {
            ((TabActivity) getActivity()).resetTabs();
            ((TabActivity) getActivity()).homeLinear
                    .setBackgroundColor(getResources().getColor(
                            R.color.royal_blue));
            ((TabActivity) getActivity())._mViewPager.setCurrentItem(0);

        } else {
            getActivity().onBackPressed();
        }

    }

    @OnClick(R.id.backLiner)
    public void back() {
        // handler.removeCallbacksAndMessages(null);
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

    @OnClick(R.id.ParentLinear_DeviceSyncFragment)
    public void parentLinear() {

    }



    @OnClick(R.id.Base_Header)
    public void clickHeaderBase() {

    }


    private void setText() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Constants.isSync = true;
                String sourceString = "<b> Congratulations!</b> You have successfully synced your device.";
                congratulationsText.setText(Html.fromHtml(sourceString));
                congratulationsText.setVisibility(View.VISIBLE);
                deviceSyncStatus.setText("Synced");
                deviceSyncImg.setVisibility(View.GONE);
                GetStartedText.setText("Get Started");
                GetStartedText.setBackgroundColor(getResources().getColor(R.color.royal_blue));
                System.out.println("setText setText");
	/*        	Toast.makeText(getActivity(), "setText Working",Toast.LENGTH_LONG ).show();
	        	((TabActivity) getActivity()).resetTabs();
				((TabActivity) getActivity()).homeLinear
						.setBackgroundColor(getResources().getColor(
								R.color.royal_blue));
				((TabActivity) getActivity())._mViewPager.setCurrentItem(0);*/
            }
        });
    }

    private BroadcastReceiver activityReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            String _status = intent.getStringExtra("success");
            if (_status.equalsIgnoreCase("0")) {
                Toast.makeText(getActivity(),
                        "Device not sync !", Toast.LENGTH_SHORT)
                        .show();
            } else {
                setText();
            }
		/*	Toast.makeText(getActivity(),
					"received message in activity..!", Toast.LENGTH_SHORT)
					.show();*/
        }
    };


    private BroadcastReceiver scaleConnected = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setText();
        }
    };


    private void sendBroadcast() {
        Intent new_intent = new Intent();
        new_intent.setAction(DeviceSyncService.ACTION_FROM_ACTIVITY);
        new_intent.putExtra("scaleType", _scaleType);
        getActivity().sendBroadcast(new_intent);
    }

    @Override
    public void onResume() {

        if (scaleConnected != null) {
            IntentFilter intentFilter = new IntentFilter(Constants.ACTION_SCALE_SUCCESSFULLY_CONNECTED);
            getActivity().registerReceiver(scaleConnected, intentFilter);
        }

        super.onResume();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        try {
            getActivity().unregisterReceiver(scaleConnected);
        } catch (Exception a) {
            a.printStackTrace();
        }
        super.onDestroy();
    }

}