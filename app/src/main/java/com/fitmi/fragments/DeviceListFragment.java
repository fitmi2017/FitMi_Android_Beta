package com.fitmi.fragments;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.db.DatabaseHelper;
import com.db.modules.UserInfoModule;
import com.fitmi.R;
import com.fitmi.adapter.TwoRowDataListAdapter;
import com.fitmi.dao.DeviceListDAO;
import com.fitmi.dao.UserInfoDAO;
import com.fitmi.utils.Constants;
import com.fitmi.utils.HandelOutfemoryException;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

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
    ArrayList<DeviceListDAO> deviceNames;
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

        Log.e("oncreateview", "testing on resume");
        deviceNames = new ArrayList<DeviceListDAO>();

        //temp hidden by avinash
        //	deviceNames.add("Weight Scale");
        //	deviceNames.add("Pedometer");
        DeviceListDAO dao = new DeviceListDAO();
        dao.setDeviceName("Food Scale");
        if (Constants.isSync) {
            dao.setSyncType("Synced");
        } else {
            dao.setSyncType("Not Synced");
        }
        deviceNames.add(dao);
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
        UsersName.setText(userInfo.getFirstName() + " " + userInfo.getLastName());

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
                Constants.isBluetoothOnLocal = 0;
                Constants.connectedTodevice = 0;
            } else {
                Constants.isBluetoothOnLocal = 1;
            }
        }

        String imagePath = userInfo.getPicturePath();

        if (imagePath != null && !imagePath.equalsIgnoreCase("")) {
            Bitmap myBitmap = HandelOutfemoryException.convertBitmap(imagePath);
            imgDevListProfile.setImageBitmap(myBitmap);
            Picasso.with(getActivity()).load("file:" + imagePath).noFade().resize(80, 80).centerCrop().into(imgDevListProfile);
        } else {

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

                getActivity().getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        deviceNames.clear();
                        DeviceListDAO dao = new DeviceListDAO();
                        dao.setDeviceName("Food Scale");
                        if (Constants.isSync) {
                            dao.setSyncType("Synced");
                        } else {
                            dao.setSyncType("Not Synced");
                        }
                        deviceNames.add(dao);
                        adapter.notifyDataSetChanged();
                    }
                });
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

        if (scaleConnected != null) {
            IntentFilter intentFilter = new IntentFilter(Constants.ACTION_SCALE_SUCCESSFULLY_CONNECTED);
            getActivity().registerReceiver(scaleConnected, intentFilter);
        }

        if (scaleDisConnected != null) {
            IntentFilter intentFilter = new IntentFilter("DeviceDisconnected");
            getActivity().registerReceiver(scaleDisConnected, intentFilter);
        }

        return view;


    }

    private BroadcastReceiver scaleConnected = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            deviceNames.clear();
            DeviceListDAO dao = new DeviceListDAO();
            dao.setDeviceName("Food Scale");

            dao.setSyncType("Synced");

            deviceNames.add(dao);
            adapter.notifyDataSetChanged();
//            Toast.makeText(getActivity(), "Connected -->receiver to change the text", Toast.LENGTH_SHORT).show();
        }
    };

    private BroadcastReceiver scaleDisConnected = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

//            Toast.makeText(getActivity(), "deisconnected receiver to change the text", Toast.LENGTH_SHORT).show();

            deviceNames.clear();
            DeviceListDAO dao = new DeviceListDAO();
            dao.setDeviceName("Food Scale");

            dao.setSyncType("Not Synced");

            deviceNames.add(dao);
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        try {
            getActivity().unregisterReceiver(scaleConnected);
            getActivity().unregisterReceiver(scaleDisConnected);

        } catch (Exception a) {
            a.printStackTrace();
        }
        super.onDestroy();
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