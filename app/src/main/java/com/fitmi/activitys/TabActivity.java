package com.fitmi.activitys;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.db.modules.UnitModule;
import com.db.modules.UserInfoModule;
import com.fitmi.R;
import com.fitmi.adapter.ViewPagerAdapter;
import com.fitmi.dao.UnitItemDAO;
import com.fitmi.dao.UserInfoDAO;
import com.fitmi.fragments.HomeFragment;
import com.fitmi.utils.Constants;
import com.fitmi.utils.ExceptionHandler;
import com.fitmi.utils.interFragmentScaleCommunicator;

import java.util.ArrayList;

import butterknife.InjectView;
import butterknife.OnClick;
import cn.net.aicare.pabulumlibrary.bleprofile.BleProfileService;
import cn.net.aicare.pabulumlibrary.entity.FoodData;
import cn.net.aicare.pabulumlibrary.pabulum.PabulumService;
import cn.net.aicare.pabulumlibrary.utils.PabulumBleConfig;

public class TabActivity extends BaseFragmentActivity implements interFragmentScaleCommunicator {

    public PabulumService.PabulumBinder binder;

    public ViewPager _mViewPager;
    public ViewPagerAdapter _madapter;
    public static BluetoothDevice bluetoothDevice;


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
    public int wt = 0;
    int ut = 0;
    private Context context = this;
    AlertDialog alertDialog;
    boolean hasGoneForEnableLocation = false;

    public void setWeightOnDevice(int wt) {
        byte[] b = initSetWeighCMD(wt * 10); // weight_value=236
        binder.sendCMD(b);
    }


    @TargetApi(Build.VERSION_CODES.M)
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

        if (userList.size() > 0) {
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
        } else {
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
    }

    @Override
    protected void onServiceBinded(BleProfileService.LocalBinder localBinder) {
        this.binder = (PabulumService.PabulumBinder) localBinder;
    }


    @Override
    protected void onReadRssi(int i) {

    }

    @Override
    protected void getBleVersion(String s) {

    }

    @Override
    protected void onLeScanCallback(BluetoothDevice bluetoothDevice, int i) {
        connectDevice(bluetoothDevice);

        Constants.addLogToFile(" TabActivity onLeScanCallback-->  connectDevice \n");
        Log.e("AutoConnection", "onLeScanCallback---->connectDevice");

        this.bluetoothDevice = bluetoothDevice;
        //L.e(TAG, bluetoothDevice.getAddress());
    }

    @Override
    protected void onStateChanged(int state) {
        super.onStateChanged(state);

        Constants.addLogToFile(" TabActivity onStateChanged-->  onStateChanged \n");

        switch (state) {
            case BleProfileService.STATE_CONNECTED:
                Log.e("AutoConnection", "onStateChanged STATE_CONNECTED");
                Toast.makeText(TabActivity.this, "Scale Connected!", Toast.LENGTH_SHORT).show();


                Constants.addLogToFile(" TabActivity onStateChanged-->  STATE_CONNECTED\n");


                if (isDeviceConnected()) {
                    Log.e("AutoConnection", " STATE_CONNECTED isDeviceConnected");
                    Constants.isSync = true;
                    Intent new_intent = new Intent();
                    new_intent.setAction(Constants.ACTION_SCALE_SUCCESSFULLY_CONNECTED);
                    sendBroadcast(new_intent);

                    Constants.addLogToFile(" TabActivity onStateChanged-->  STATE_CONNECTED sendBroadcasts\n");


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (getSavedUnits().equals("7")) {
                                binder.setUnit(PabulumBleConfig.UNIT_G);
                                ut = 7;
                            } else {
                                binder.setUnit(PabulumBleConfig.UNIT_OZ);
                                ut = 8;
                            }
                        }
                    }, 800);
                }else{
                    Log.e("AutoConnection", "STATE_CONNECTED not isDeviceConnected");
                }

                break;
            case BleProfileService.STATE_DISCONNECTED:

                Constants.addLogToFile(" TabActivity onStateChanged-->  STATE_DISCONNECTED\n");
                Log.e("AutoConnection", "onStateChanged STATE_DISCONNECTED");
                stopScan();
                Toast.makeText(TabActivity.this, "Scale Disconnected!", Toast.LENGTH_SHORT).show();
                wt = 0;
                Constants.isSync = false;
                sendBroadcast(new Intent("DeviceDisconnected"));

                Constants.addLogToFile(" TabActivity onStateChanged-->  STATE_DISCONNECTED sendBroadcasts\n");

                break;
//            case BleProfileService.STATE_INDICATION_SUCCESS:
//                setUnit(DEFAULT_UNIT);
//                break;
        }
    }


    @Override
    protected void bluetoothStateOn() {
        super.bluetoothStateOn();
        startBLEScan();
    }


    @Override
    protected void getFoodData(FoodData foodData) {
//        int v1 = getMeSomethingIncredible(foodData);

        int v1 = (int) (foodData.getWeight() + 0.5);
        if (wt != v1)
            sendBroadcast(new Intent().setAction("NEW_WEIGHT").putExtra("weight", v1));
        wt = v1;
    }

    private int getMeSomethingIncredible(FoodData foodData) {

//        Log.e("weight=======", foodData.getWeight()+" --");

        switch (foodData.getUnit()) {
            //Unit: 00=g  01:mL 02:lboz  03:floz
            case 00:
                // Grams
                return (int) (foodData.getWeight());

            case 01:
                return getFromMilliLitres(foodData.getWeight());

            case 02:
                return getFromLbOz(String.valueOf(foodData.getWeight()));

            case 03:
                return getFromOz(foodData.getWeight());
        }

        return 0;
    }

    private int getFromLbOz(String weight) {
        // 0:0.00
        weight = weight.trim();

        int index = weight.indexOf(":");
        String lbsStr = weight.substring(0, index);
        String ozStr = weight.substring(index + 1, weight.length());

        int lbs = Integer.parseInt(lbsStr.trim());
        int oz = getFromOz(Double.parseDouble(ozStr.trim()));

        return (int) ((lbs * 453.59237d) + oz);
    }

    private int getFromMilliLitres(double weight) {
        return (int) (weight);
    }

    private int getFromOz(double weight) {
        double oz = (weight);
        return (int) (oz * 28.34952d);
    }

    @Override
    protected void getUnit(byte b) {
        Log.e("new unit is= ", " " + b);
        if (PabulumBleConfig.UNIT_G == b)
            sendBroadcast(new Intent().setAction("NEW_UNIT").putExtra("unit", 7));
        else if (PabulumBleConfig.UNIT_OZ == b)
            sendBroadcast(new Intent().setAction("NEW_UNIT").putExtra("unit", 8));
//        else if (b == -2)
//            sendBroadcast(new Intent().setAction("NEW_UNIT").putExtra("unit", ut));
    }

    @OnClick({R.id.HomeLinear_Tab, R.id.HomeImg_Tab})
    public void homeTab() {

        Constants.sTempDate = "";
        Constants.shareIntent = false;
        resetTabs();
        homeLinear.setBackgroundColor(getResources().getColor(
                R.color.royal_blue));
        _mViewPager.setCurrentItem(0);

    }

    @OnClick({R.id.ProfileLinear_Tab, R.id.ProfileImg_Tab})
    public void profileTab() {
        Constants.sTempDate = "";
        Constants.shareIntent = false;
        resetTabs();
        profileLinear.setBackgroundColor(getResources().getColor(
                R.color.royal_blue));
        if (Constants.fragmentSet)
            _mViewPager.setCurrentItem(5);
        else
            _mViewPager.setCurrentItem(1);

    }

    @OnClick({R.id.ActivityLinear_Tab, R.id.ActivityImg_Tab})
    public void activityTab() {

        resetTabs();
        activityLinear.setBackgroundColor(getResources().getColor(
                R.color.royal_blue));
        _mViewPager.setCurrentItem(2);

    }

    @OnClick({R.id.HelpLinear_Tab, R.id.HelpImg_Tab})
    public void helpTab() {
        Constants.shareIntent = false;
        resetTabs();
        helpLinear.setBackgroundColor(getResources().getColor(
                R.color.royal_blue));
        _mViewPager.setCurrentItem(3);

    }

    @OnClick({R.id.CalendarLinear_Tab, R.id.CalendarImg_Tab})
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

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onResume() {
        super.onResume();

        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                && (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                && hasGoneForEnableLocation) {
            hasGoneForEnableLocation = false;

            if (ensureBLESupported()) {
                try {
                    startBLEScan();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


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

        if (homeFragment != null && homeFragment.isVisible()) {
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

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void connectDevice() {
        Log.e("AutoConnection", "connectDevice");
        Constants.addLogToFile(" TabActivity connectDevice-->  1st time \n");
        try {
            if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    && !(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001);
            } else {
                if (ensureBLESupported()) {
                    Constants.addLogToFile(" TabActivity connectDevice-->  1st time  startBLEScan\n");
                    startBLEScan();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void connectDevice(boolean isFromRepeatingCheck) {

        Log.e("AutoConnection", "connectDevice(true)");
        Constants.addLogToFile(" TabActivity connectDevice--> isFromRepeatingCheck \n");
        try {
            if (((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    && (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED))
                    || (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP)) {
//                Toast.makeText(this, "connectDevice called", Toast.LENGTH_SHORT);

                if (ensureBLESupported() && !isDeviceConnected()) {
                    BluetoothAdapter c = BluetoothAdapter.getDefaultAdapter();
                    if (c.isEnabled()) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
//                                Toast.makeText(TabActivity.this, "connectDevice called scanning...."+bluetoothDevice, Toast.LENGTH_SHORT);
//                                startScan();
//                                if (bluetoothDevice != null) {
//                                    connectDevice(bluetoothDevice);
//                                    Log.e("AutoConnection","connectDevice (true)------>connectDevice");
//                                } else {
                                    startScan();
                                Constants.addLogToFile(" TabActivity connectDevice--> isFromRepeatingCheck startScan \n");
                                    Log.e("AutoConnection","connectDevice (true)------>startScan");
//                                }

                            }
                        }, 500);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startBLEScan() {
        BluetoothAdapter c = BluetoothAdapter.getDefaultAdapter();
        if (c.isEnabled()) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startScan();
                    Constants.addLogToFile(" TabActivity connectDevice-->  1st time  startBLEScan startScan\n");
                }
            }, 500);
        } else {
            startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 102);
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1001) {

            boolean isAllow = true;
            for (int i = 0, len = permissions.length; i < len; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    isAllow = false;
                }
            }

            if (isAllow) {
                if (ensureBLESupported()) {
                    try {
                        Constants.addLogToFile(" TabActivity connectDevice-->  1st time  onRequestPermissionsResult startBLEScan \n");
                        startBLEScan();
                        Log.e("start scan", "icomon if scan started ");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                hasGoneForEnableLocation = true;
                goToSettings("Application needs Location services enabled to communicate with bloothoth. Please Enable Location Services!", "Location");
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("resultCode", "ResultCode= " + resultCode);

        if (requestCode == 102) {
            if (resultCode == RESULT_OK) {
                if (ensureBLESupported()) {
                    try {
                        startBLEScan();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                goToSettings("Application can't communicate with Scale without bluetooth. Please turn on Bluetooth!", "Bluetooth");
            }
        }
    }


    @Override
    public void tare() {
        if (isDeviceConnected() && binder != null) {
            binder.netWeight();
        }
    }

    @Override
    public void changeUnits(String units) {

        Log.e("changeUnits=", " " + units);

        if (isDeviceConnected() && binder != null) {
            if (units != null && units.equalsIgnoreCase("7")) {
                binder.setUnit(PabulumBleConfig.UNIT_G);
                ut = 7;
            } else {
                binder.setUnit(PabulumBleConfig.UNIT_OZ);
                ut = 8;
            }
            // getUnits callback is not getting called when units changes from Application.
            sendBroadcast(new Intent().setAction("NEW_UNIT").putExtra("unit", ut));
        }

    }

    @Override
    public boolean isScaleConnected() {
        return isDeviceConnected();
    }

    @Override
    public String getWeight() {
        return String.valueOf(wt);
    }

    @Override
    public void onDestroy() {
        bluetoothTurningOff();
        super.onDestroy();
    }


    String getSavedUnits() {

        int unitIdFw = 7;
        UnitModule unitModel = new UnitModule(this);
        ArrayList<UnitItemDAO> unitItem = unitModel.selectUnitLogList();
        if (unitItem.size() > 0) {
            for (int i = 0; i < unitItem.size(); i++) {
                UnitItemDAO unitObj = unitItem.get(i);
                if (unitObj.getType().equalsIgnoreCase("food_weight")) {
                    if (unitObj.getUnitId().equalsIgnoreCase("7"))
                        unitIdFw = 7;
                    else unitIdFw = 8;
                }
            }
        } else {
            unitIdFw = 7;
        }
        return String.valueOf(unitIdFw);
    }

    private void goToSettings(String msg, final String type) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TabActivity.this);
        alertDialogBuilder.setMessage(msg);

        if (type.equals("Bluetooth")) {
            alertDialogBuilder.setPositiveButton("Enable",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface d, int arg1) {
                            final BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                            adapter.enable();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (adapter.isEnabled())
                                        startScan();
                                }
                            }, 500);
                            d.dismiss();
                        }
                    });
        } else if (type.equals("Location")) {
            alertDialogBuilder.setPositiveButton("Settings",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface d, int arg1) {
                            final Intent i = new Intent();
                            i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            i.addCategory(Intent.CATEGORY_DEFAULT);
                            i.setData(Uri.parse("package:" + TabActivity.this.getPackageName()));
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                            TabActivity.this.startActivity(i);
                            d.dismiss();
                        }
                    });
        }
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog = alertDialogBuilder.create();
        try {
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    private void blutoothAlert() {
//
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TabActivity.this);
//        alertDialogBuilder.setMessage("Application can't communicate with Scale without bluetooth. Please turn on Bluetooth!");
//
//        alertDialogBuilder.setPositiveButton("OK",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface d, int arg1) {
//                        d.dismiss();
//                    }
//                });
//
//        alertDialog = alertDialogBuilder.create();
//        alertDialog.show();
//    }
}
