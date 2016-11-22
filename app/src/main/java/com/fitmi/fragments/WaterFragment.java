package com.fitmi.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.callback.NotificationSleep;
import com.callback.NotificationWater;
import com.callback.WaterChangeNotification;
import com.callback.WeightChangeNotification;
import com.db.DatabaseHelper;
import com.db.modules.WaterModule;
import com.db.modules.WeightLogModule;
import com.fitmi.R;
import com.fitmi.adapter.WaterAdapter;
import com.fitmi.dao.WaterLogDAO;
import com.fitmi.utils.Constants;
import com.fitmi.utils.DateModule;
import com.fitmi.utils.NotificationTotalCaloryChange;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class WaterFragment extends BaseFragment implements
		WaterChangeNotification {

	@InjectView(R.id.Heading)
	public TextView heading;

	@InjectView(R.id.Back)
	public ImageView back;

	@InjectView(R.id.backLiner)
	LinearLayout backLiner;

	@InjectView(R.id.waterOzEnter)
	EditText waterOzEnter;

	@InjectView(R.id.txtTotal)
	TextView txtTotal;

	@InjectView(R.id.Date)
	TextView dateTextView;

	@InjectView(R.id.type)
	TextView water_type_change1;

	ImageView water_type_change2;
	ListView waterlv;

	String waterValue = "";

	WaterModule waterDb;
	WaterLogDAO waterData;

	int waterType = 0;
	ArrayList<WaterLogDAO> waterList;

	WaterAdapter wad;

	DatabaseHelper databaseObject;
	DateModule dateModule = new DateModule();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_water, container, false);
		setNullClickListener(v);

		waterData = new WaterLogDAO();

		water_type_change2 = (ImageView) v.findViewById(R.id.close);
		databaseObject = new DatabaseHelper(getActivity());
		try {

			databaseObject.createDatabase();

			databaseObject.openDatabase();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		getActivity().registerReceiver(dateSetReceiver,
				new IntentFilter("waterlogupdate"));

		Constants.fragmentNumber = 6;

		ButterKnife.inject(this, v);
		setNullClickListener(v);
		heading.setText("Water");

		waterDb = new WaterModule(getActivity());

		waterlv = (ListView) v.findViewById(R.id.waterlv);

		getLogList();

		water_type_change2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (waterType == 0) {
					waterType = 1;
					water_type_change1.setText("ml");
				} else {
					waterType = 0;
					water_type_change1.setText("OZ");
				}
			}
		});
		return v;

	}

	@OnClick(R.id.backLiner)
	public void back() {
		InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		in.hideSoftInputFromWindow(waterOzEnter.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
		getActivity().onBackPressed();

	}

	@OnClick(R.id.Date)
	public void changeDate() {

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.add(R.id.root_home_frame, new CalendarFragment(),
				"CalendarFragment");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	@OnClick(R.id.type)
	public void changeWaterUnit() {

		if (waterType == 0) {
			waterType = 1;
			water_type_change1.setText("ml");
		} else {
			waterType = 0;
			water_type_change1.setText("OZ");
		}
	}

	@OnClick(R.id.imgV_water_8oz)
	public void set8ozValue() {
		waterOzEnter.setText("8");
	}

	@OnClick(R.id.imgV_water_16oz)
	public void set16ozValue() {
		waterOzEnter.setText("16.9");
	}

	@OnClick(R.id.imgV_water_24oz)
	public void set24ozValue() {
		waterOzEnter.setText("24.7");
	}

	@OnClick(R.id.imgV_water_34oz)
	public void set34ozValue() {
		waterOzEnter.setText("34.3");
	}

	@OnClick(R.id.btnWaterLog)
	public void saveOzValue() {
		waterValue = waterOzEnter.getText().toString();

		if (waterValue.equalsIgnoreCase("")) {

			Toast.makeText(getActivity(), "Select water oz", Toast.LENGTH_LONG)
					.show();
			return;
		}
		if (waterType == 0) {
			waterValue = waterOzEnter.getText().toString();
		} else {
			double originalWatervalue = Double.parseDouble(waterValue);
			originalWatervalue = originalWatervalue * 0.03381402207;
			NumberFormat numberFormat = NumberFormat.getInstance();
			numberFormat.setMaximumFractionDigits(2);
			numberFormat.setMinimumFractionDigits(2);
			waterValue = numberFormat.format(originalWatervalue);
		}
		DateModule dateObj = new DateModule();
		String time = dateObj.getTime();

		waterOzEnter.setText("");

		waterData.setOzValue(waterValue);
		waterData.setLogTime(time);
		waterData.setLogDate(Constants.postDate);
		waterData.setUserId(Constants.USER_ID);
		waterData.setProfileId(Constants.PROFILE_ID);

		waterDb.setWaterLog(waterData, databaseObject);

		getLogList();

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
/*
		if (Constants.sDate.length() == 0) {
			// Constants.sDate = "Tuesday, February 10, 2015";

			Calendar c = Calendar.getInstance();
			System.out.println("Current time => " + c.getTime());
			// SimpleDateFormat df = new
			// SimpleDateFormat("YYYY-MMM-dd hh:mm:ss");
			SimpleDateFormat df = new SimpleDateFormat("EEEE, MMM dd, yyyy");
			Constants.sDate = df.format(c.getTime());

			SimpleDateFormat postFormat = new SimpleDateFormat(
					"yyyy-MM-dd kk:mm:ss");
			Constants.postDate = postFormat.format(c.getTime());

		}

		dateTextView.setText(Constants.sDate);*/
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
			System.out.println("Calender post format :"+ Constants.postDate);
			//Toast.makeText(getActivity(), Constants.postDate, Toast.LENGTH_LONG).show();
			
		}

		dateTextView.setText(Constants.sDate);
	}

	BroadcastReceiver dateSetReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			Bundle bundleBroadcast = intent.getExtras();
			dateTextView.setText(bundleBroadcast.getString("key"));
			getLogList();

			wad.notifyDataSetChanged();
		}
	};

	public void getLogList() {

		totalWater(waterDb, txtTotal);

		setAdapter(getActivity(), waterlv, txtTotal);
	}

	@OnClick(R.id.txtPreview)
	public void datePreview() {
		String setDate = dateTextView.getText().toString().trim();
		setDate = dateModule.datePreview(setDate);
		dateTextView.setText(setDate);
		Constants.conditionDate = dateModule.conditionDateFormat(setDate);
		Constants.postDate = dateModule.getPostDateFormat(setDate);
		Constants.sTempDate= Constants.conditionDate;
		Constants.sDate=dateModule.sDateFormat(setDate);
		getLogList();
		wad.notifyDataSetChanged();
		// Toast.makeText(getActivity(), setDate, Toast.LENGTH_LONG).show();
	}

	@OnClick(R.id.txtNext)
	public void dateNext() {
		String setDate = dateTextView.getText().toString().trim();
		setDate = dateModule.dateNext(setDate);
		dateTextView.setText(setDate);
		Constants.conditionDate = dateModule.conditionDateFormat(setDate);
		Constants.postDate = dateModule.getPostDateFormat(setDate);
		Constants.sTempDate= Constants.conditionDate;
		Constants.sDate=dateModule.sDateFormat(setDate);
		getLogList();
		wad.notifyDataSetChanged();
		// Toast.makeText(getActivity(), setDate, Toast.LENGTH_LONG).show();
	}

	@Override
	public void changeWater(WaterModule waterModule, Context contex,
			ListView pressurelv, TextView txtTotal) {
		// TODO Auto-generated method stub

		waterList = waterModule.selectWaterLogList();
		totalWater(waterModule, txtTotal);

		setAdapter(contex, pressurelv, txtTotal);
	}

	public void setAdapter(Context context, ListView waterlv, TextView txtTotal) {
		wad = new WaterAdapter(context, waterList, waterlv, txtTotal);

		waterlv.setAdapter(wad);
	}

	public void totalWater(WaterModule waterDb, TextView txtTotal) {
		waterList = waterDb.selectWaterLogList();

		String sum = waterDb.sumWaterOz();

		HomeFragment tosetSleep = new HomeFragment();

		NotificationWater sleepCallback = (NotificationWater) tosetSleep;

		if (sum == null) {

			txtTotal.setText("0.00 ounces");
			sleepCallback.waterOzChange("0.00");

		} else {
			txtTotal.setText(sum + " ounces");
			sleepCallback.waterOzChange(sum);
		}
	}

	@OnClick(R.id.Settings)
	public void gotoSettings() {

		View view = getActivity().getCurrentFocus();
		if (view != null) {  
		    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
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
	
	   @Override
	public void onPause() {
		// TODO Auto-generated method stub
try{
		   if(dateSetReceiver!=null)
		   getActivity().unregisterReceiver(dateSetReceiver);
}catch (Exception a){
	a.printStackTrace();
}
		super.onPause();
	}
}
