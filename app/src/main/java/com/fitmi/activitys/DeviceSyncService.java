package com.fitmi.activitys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.db.modules.WeightLogModule;
import com.fitmi.dao.WeightLogDAO;
import com.fitmi.utils.Constants;
import com.fitmi.utils.DateModule;
import com.lifesense.ble.DeviceConnectState;
import com.lifesense.ble.LsBleManager;
import com.lifesense.ble.OnConnectListener;
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

public class DeviceSyncService extends Service {

	LsBleManager lsBleManager;
	List<DeviceType> list = null;
	ArrayList<DeviceType> deviceType = new ArrayList<>();
	String _scaleType = "0";
	
	int prev_val_kscale=-1;
	DateModule getDate = new DateModule();
	WeightLogModule weightModule;

	public static final String ACTION_FROM_SERVICE = "FROM_SERVICE";
	public static final String ACTION_FROM_ACTIVITY = "FROM_ACTIVITY";

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		lsBleManager = LsBleManager.newInstance();
		lsBleManager.initialize(this.getApplicationContext());
		list = new ArrayList<>();
		list.add(0, DeviceType.WEIGHT_SCALE);
		list.add(1, DeviceType.PEDOMETER);
		list.add(2, DeviceType.SPHYGMOMANOMETER);
		list.add(3, DeviceType.KITCHEN_SCALE);

		weightModule = new WeightLogModule(this);
		
	
		if (serviceReceiver != null) {
			// Create an intent filter to listen to the broadcast sent with the
			// action "ACTION_STRING_SERVICE"
			IntentFilter intentFilter = new IntentFilter(ACTION_FROM_ACTIVITY);
			// Map the intent filter to the receiver
			registerReceiver(serviceReceiver, intentFilter);
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		logConsole("Started");
		// Start Searching Device
		startSearching();

		return START_REDELIVER_INTENT;
	}

	public void startSearching() {
		if (lsBleManager.isOpenBluetooth() && lsBleManager.isSupportLowEnergy()) {
			Constants.isBluetoothOn=1;
			lsBleManager
					.searchLsDevice(searchCallback, list, BroadcastType.ALL);
			
		}else{
		//	Toast.makeText(getApplication(), "please check bluetooth is off !", Toast.LENGTH_LONG).show();
			Constants.isBluetoothOn=0;
		}

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub

		return null;
	}

	
	//connection listner
	OnConnectListener connectListener =new OnConnectListener() {
		
		@Override
		public void onWaitingForStartMeasuring(String arg0) {
			// TODO Auto-generated method stub
			Log.e("onWaitingForStartMeasuring Service", "onWaitingForStartMeasuring");
		}
		
		@Override
		public void onReceiveBloodPressureData(BloodPressureData arg0) {
			// TODO Auto-generated method stub
			Log.e("onReceiveBloodPressureData Service", "onReceiveBloodPressureData");
		}
		
		@SuppressWarnings("static-access")
		@Override
		public void onConnectStateChange(DeviceConnectState arg0) {
			// TODO Auto-generated method stub
			Log.e("onConnectStateChange Service", "onConnectStateChange");
			if(arg0.CONNECTED_SUCCESS != null)
			{
				Log.e("CONNECTED_SUCCESS Service", "Connected");
			}
		}
	};
	
	// SEARCH CALLBACKS

	SearchCallback searchCallback = new SearchCallback() {

		@Override
		public void onSearchResults(LsDeviceInfo deviceInfo) {
			// TODO Auto-generated method stub
			boolean isPaired = false;
			boolean isConnect = false;

				if (deviceInfo.toString().length() > 0) {
				
				logConsole("Search Result "+ deviceInfo.getDeviceType());
				
				
				if (deviceInfo.getDeviceType().equalsIgnoreCase("09")) {

					lsBleManager.stopSearch();
					isPaired = lsBleManager.addMeasureDevice(deviceInfo);
				//	isConnect=lsBleManager.connectDevice(deviceInfo, connectListener);
				//	Log.e("isConnect ", String.valueOf(isConnect));
					lsBleManager.startDataReceiveService(receiveDataCallback);
					
				} else {
					lsBleManager.stopSearch();
					isPaired = lsBleManager.startPairing(deviceInfo,
							pairCallback);

					//lsBleManager.stopDataReceiveService();
					//deviceType.add(DeviceType.WEIGHT_SCALE);
				}

				logConsole(isPaired + "");
				

				// logConsole("Paired Device Result " +
				// deviceInfo.getDeviceName()
				// + " -- " + isPaired);
			} else {
				lsBleManager.stopSearch();
				logConsole("Search Result Device Info is Blank");
			}

		}

	};

	ReceiveDataCallback receiveDataCallback = new ReceiveDataCallback() {

		@Override
		public void onReceivePedometerData(PedometerData pedometerData) {
			// TODO Auto-generated method stub
		/*	logConsole(" Received Pedometer Data");
			if (pedometerData.toString().length() > 0) {
				logConsole("getRunSteps " + pedometerData.getRunSteps());
				logConsole("getWalkSteps " + pedometerData.getWalkSteps());
				logConsole("Calory " + pedometerData.getCalories());

				double myDb = pedometerData.getCalories();
				int myInt = (int) myDb;
				String _calorieburn = String.valueOf(myInt);
				HashMap<String, String> selectItem = new HashMap<String, String>();

				// FOR INSERT TO DB
				
				 * selectItem.put(BaseActivity.exercise_name,
				 * "Walking "+pedometerData.getWalkSteps()+" steps");
				 * selectItem.put(BaseActivity.description,
				 * "Walking "+pedometerData.getWalkSteps()+" steps");
				 * selectItem.put(BaseActivity.calories_burned,_calorieburn);
				 * long rowid=0;
				 * ActivityModule.execiseloginsertion(databaseObject,
				 * selectItem, rowid,"0");
				 
				sendBroadcast("1");
				}
				*/

			
			super.onReceivePedometerData(pedometerData);
		}

		@Override
		public void onReceiveKitchenScaleData(KitchenScaleData kitchenScaleData) {
			// TODO Auto-generated method stub

			logConsole(" Working onReceiveKitchenScaleData  ");
			if (kitchenScaleData.toString().length() > 0) {

				logConsole(" getBattery " + kitchenScaleData.getBattery());
				logConsole(" getWeight " + kitchenScaleData.getWeight());
				logConsole(" getUnit " + kitchenScaleData.getUnit());
				if(kitchenScaleData.getUnit().equalsIgnoreCase("g"))
				{
				int temp_to=0;
				if(Constants.connectedTodevice==0){
				Constants.connectedTodevice=1;
				}
				temp_to=(int) kitchenScaleData.getWeight();
			
				Log.e("Device connect",String.valueOf(Constants.connectedTodevice));
				if((temp_to-prev_val_kscale)!=0)
				{
					prev_val_kscale=temp_to;
				sendBroadcastKitchenScale("1",String.valueOf(temp_to));
				}
				
				}
				else{
					double ozValue,ozValueLb=0.0;
					int _temp_to=0;
					if(Constants.connectedTodevice==0){
						Constants.connectedTodevice=1;
						}
					Log.e("Lb value",String.valueOf(kitchenScaleData.getSectionWeight()));
					ozValue=kitchenScaleData.getWeight();
					ozValue=ozValue/0.035274;
					if(kitchenScaleData.getSectionWeight()>0)
					{
						ozValueLb=kitchenScaleData.getSectionWeight();
						ozValueLb=ozValueLb*453.592;
						Log.e("Lb value to gram",String.valueOf(ozValueLb));
						ozValue=ozValue+ozValueLb;
					}else{
						ozValueLb=0;
						ozValue=ozValue+ozValueLb;
					}
					Log.e("Oz to gram",String.valueOf(ozValue));
					ozValue=Math.round(ozValue);
					_temp_to=(int) ozValue;
					Log.e("Oz to gram after round",String.valueOf(ozValue));
					//453.592
					
					if((_temp_to-prev_val_kscale)!=0)
					{
						prev_val_kscale=_temp_to;
					sendBroadcastKitchenScale("1",String.valueOf(_temp_to));
					}
				}
			}

			super.onReceiveKitchenScaleData(kitchenScaleData);
		}

		@Override
		public void onReceiveWeightData_A3(WeightData_A3 weightDataA3) {
			// TODO Auto-generated method stub

			logConsole(" Working onReceiveWeightData_A3  ");
			if (weightDataA3.toString().length() > 0) {
				logConsole(" getBattery " + weightDataA3.getBattery());
				logConsole(" getWeight " + weightDataA3.getWeight());
				logConsole(" getUnit " + weightDataA3.getDeviceSelectedUnit());
			}
			super.onReceiveWeightData_A3(weightDataA3);
		}

		@Override
		public void onReceiveWeightDta_A2(WeightData_A2 weightDataA2) {
			// TODO Auto-generated method stub
			logConsole(" Working onReceiveWeightDta_A2  ");
			if (weightDataA2.toString().length() > 0) {
				logConsole(" getBattery " + weightDataA2.getBattery());
				logConsole(" getWeight " + weightDataA2.getWeight());
				logConsole(" getUnit " + weightDataA2.getDeviceSelectedUnit());
				
				String _weight;
				double myDb = weightDataA2.getWeight();
				int myInt = (int) myDb;
				_weight=String.valueOf(myInt);
				System.out.println("   After Parsing weight " +_weight);
		       	
	        	WeightLogDAO weightDate = new WeightLogDAO();

				weightDate.setWeight(_weight);
				weightDate.setUserId(Constants.USER_ID);
				weightDate.setProfileId(Constants.PROFILE_ID);
				weightDate.setLogTime(getDate.getTime());
				weightDate.setAddedtime(getDate.getDateFormat());				

				weightModule.insertWeightInformation(weightDate);
				
				if(Constants.connectedTodevice==0){
					Constants.connectedTodevice=1;
					Constants.isBluetoothOn=1;
					}
				sendBroadcast("1");
			}
			//sendBroadcast()
			super.onReceiveWeightDta_A2(weightDataA2);
		}

		@Override
		public void onReceiveUserInfo(WeightUserInfo weightUserInfo) {
			// TODO Auto-generated method stub
			logConsole(" Working onReceiveUserInfo  ");
			if (weightUserInfo.toString().length() > 0) {
				logConsole(" getBattery " + weightUserInfo.toString());

			}

			super.onReceiveUserInfo(weightUserInfo);
		}

		@Override
		public void onReceiveBloodPressureData(BloodPressureData arg0) {
			// TODO Auto-generated method stub
			super.onReceiveBloodPressureData(arg0);
		}

	};

	// PAIRED CALLBACK

	PairCallback pairCallback = new PairCallback() {

		@Override
		public void onDiscoverUserInfo(List arg0) {
			// TODO Auto-generated method stub

			System.out.println(" Working PairCallback  ");

			super.onDiscoverUserInfo(arg0);
		}

		@Override
		public void onPairResults(LsDeviceInfo deviceInfo, int status) {

			logConsole("pairCallback onPairResults");
			if (status == 0) { // if paired
				
				logConsole("getDeviceId " + deviceInfo.getDeviceId());
				logConsole("getDeviceName " + deviceInfo.getDeviceName());
				logConsole("getDeviceUserNumber "
						+ deviceInfo.getDeviceUserNumber());
				logConsole("getPairStatus " + deviceInfo.getPairStatus());
				logConsole("getMacAddress " + deviceInfo.getMacAddress());

				lsBleManager.addMeasureDevice(deviceInfo);
			//	lsBleManager.connectDevice(deviceInfo, connectListener);
				lsBleManager.startDataReceiveService(receiveDataCallback);

			} else { // if not paired

			}

		}

	};

	private BroadcastReceiver serviceReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
		/*	Toast.makeText(getApplicationContext(),
					"received message in service..!", Toast.LENGTH_SHORT)
					.show();*/
			Log.d("Service", "Sending broadcast to activity");
			// sendBroadcast();
			 _scaleType=intent.getStringExtra("scaleType");
			lsBleManager.stopDataReceiveService();
			startSearching();
		}
	};

	private void sendBroadcast(String status) {
		Intent new_intent = new Intent();
		new_intent.setAction(ACTION_FROM_SERVICE);
		new_intent.putExtra("success", status);
		sendBroadcast(new_intent);
	}
	
	private void sendBroadcastKitchenScale(String status,String weight) {
		Intent new_intent = new Intent();
		new_intent.setAction(ACTION_FROM_SERVICE);
		new_intent.putExtra("success", status);
		new_intent.putExtra("kweight", weight);
		sendBroadcast(new_intent);
	}


	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(serviceReceiver);
		super.onDestroy();
	}

	private void logConsole(String message) {
		Log.e("MSG", message);
	}
	
	
	public void lj(){
		DeviceConnectState s;
 
	}
	

}
