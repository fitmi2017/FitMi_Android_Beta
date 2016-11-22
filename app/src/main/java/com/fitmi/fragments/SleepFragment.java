package com.fitmi.fragments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
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
import com.callback.SleepChangeNotification;
import com.db.modules.SleepModule;
import com.db.modules.UserInfoModule;
import com.db.modules.WaterModule;
import com.fitmi.R;
import com.fitmi.adapter.SleepAdapter;
import com.fitmi.adapter.WaterAdapter;
import com.fitmi.dao.SleepLogADO;
import com.fitmi.dao.WaterLogDAO;
import com.fitmi.utils.Constants;
import com.fitmi.utils.DateModule;

public class SleepFragment extends BaseFragment implements SleepChangeNotification{

	int mRootId = 0;

	@InjectView(R.id.Heading)
	public TextView heading;

	@InjectView(R.id.Back)
	public ImageView back;
	
	@InjectView(R.id.backLiner)
	LinearLayout backLiner;

	@InjectView(R.id.Settings)
	public ImageView settings;

	@InjectView(R.id.edit_sleep)
	EditText edit_sleep;

	@InjectView(R.id.txtTotal)
	TextView txtTotal;
	
	@InjectView(R.id.Date)
	TextView dateTextView;	
	

	ListView sleeplv;

	UserInfoModule userDb;

	String sleep = "";

	SleepModule sleepModule;
	SleepLogADO sleepData;
	ArrayList<SleepLogADO> sleepList;
	DateModule dateModule = new DateModule();
	SleepAdapter sad;

	String sleepValue = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/* Inflate the layout for this fragment */
		View view = inflater.inflate(R.layout.fragment_sleep, container, false);

		sleepData = new SleepLogADO();

		Bundle bundle = this.getArguments();
		if (bundle != null) {
			mRootId = bundle.getInt("root_id", R.id.root_home_frame);
		}

		getActivity().registerReceiver(dateSetReceiver,
				new IntentFilter("sleeplogupdate"));

		Constants.fragmentNumber = 16;
		
		sleepModule = new SleepModule(getActivity());

		ButterKnife.inject(this, view);
		setNullClickListener(view);
		Constants.fragmentSet = false;

		userDb = new UserInfoModule(getActivity());

		sleeplv = (ListView) view.findViewById(R.id.sleeplv);

		//sleep = userDb.getSleep();

		edit_sleep.setText(sleep);

		heading.setText("Sleep");
		//settings.setVisibility(View.GONE);

		getLogList();

		return view;
	}

	@OnClick(R.id.backLiner)
	public void back() {
		InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		in.hideSoftInputFromWindow(edit_sleep.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
		getActivity().onBackPressed();

	}

	@OnClick(R.id.Cancel_Sleep)
	public void cancel() {

		getActivity().onBackPressed();

	}

	@OnClick(R.id.Base_Header)
	public void clickHeaderBase() {

	}

	@OnClick(R.id.Save_UserInfo)
	public void saveSleep()
	{
		sleep = edit_sleep.getText().toString();		
		userDb.updateSleep(sleep);

		Toast.makeText(getActivity(), "Weight updated", Toast.LENGTH_LONG).show();
	}

	public void getLogList()
	{

		totalSleep(sleepModule,txtTotal);		

		setAdapter(getActivity(),sleeplv,txtTotal);
	}

	public void setAdapter(Context context,ListView sleeplv, TextView txtTotal)
	{
		sad = new SleepAdapter(context,sleepList,sleeplv,txtTotal);

		sleeplv.setAdapter(sad);
	}

	public void totalSleep(SleepModule sleepModule,TextView txtTotal)
	{
		sleepList = sleepModule.selectSleepLogList();

		String sum = sleepModule.sumSleepHour();
		
		
		HomeFragment tosetSleep = new HomeFragment();
		
		NotificationSleep sleepCallback = (NotificationSleep) tosetSleep;

		if(sum == null){

			txtTotal.setText("0 hours");
			sleepCallback.sleepHourChange("0");

		}else{
			txtTotal.setText(sum+" hours");
			sleepCallback.sleepHourChange(sum);
		}
	}


	@OnClick(R.id.btnSleepLog)
	public void saveOzValue()
	{
		sleepValue = edit_sleep.getText().toString();

		if(sleepValue.equalsIgnoreCase("")){

			Toast.makeText(getActivity(), "Select sleep hours", Toast.LENGTH_LONG).show();
			return;
		}

		DateModule dateObj = new DateModule();
		String time = dateObj.getTime();

		edit_sleep.setText("");

		sleepData.setHours(sleepValue);
		sleepData.setLogTime(time);
		sleepData.setLogDate(Constants.postDate);
		sleepData.setUserId(Constants.USER_ID);
		sleepData.setProfileId(Constants.PROFILE_ID);

		sleepModule.setSleepLog(sleepData);

		getLogList();

	}

	@Override
	public void changeSleep(SleepModule sleepModule, Context contex,
			ListView pressurelv, TextView txtTotal) {
		// TODO Auto-generated method stub

		totalSleep(sleepModule,txtTotal);
		setAdapter(contex,pressurelv,txtTotal);
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
	
	@OnClick(R.id.txtPreview)
	public void datePreview()
	{
		String setDate = dateTextView.getText().toString().trim();
		setDate = dateModule.datePreview(setDate);
		dateTextView.setText(setDate);
		Constants.conditionDate = dateModule.conditionDateFormat(setDate);
		Constants.postDate = dateModule.getPostDateFormat(setDate);
		Constants.sTempDate= Constants.conditionDate;
		Constants.sDate=dateModule.sDateFormat(setDate);
		getLogList();
		sad.notifyDataSetChanged();
		//Toast.makeText(getActivity(), setDate, Toast.LENGTH_LONG).show();
	}

	@OnClick(R.id.txtNext)
	public void dateNext()
	{
		String setDate = dateTextView.getText().toString().trim();
		setDate = dateModule.dateNext(setDate);
		dateTextView.setText(setDate);
		Constants.conditionDate = dateModule.conditionDateFormat(setDate);
		Constants.postDate = dateModule.getPostDateFormat(setDate);
		Constants.sTempDate= Constants.conditionDate;
		Constants.sDate=dateModule.sDateFormat(setDate);
		getLogList();
		sad.notifyDataSetChanged();
		//Toast.makeText(getActivity(), setDate, Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		/*if (Constants.sDate.length() == 0) {
			//Constants.sDate = "Tuesday, February 10, 2015";

			Calendar c = Calendar.getInstance();
			System.out.println("Current time => " + c.getTime());	
			//	SimpleDateFormat df = new SimpleDateFormat("YYYY-MMM-dd hh:mm:ss");
			SimpleDateFormat df = new SimpleDateFormat("EEEE, MMM dd, yyyy");
			Constants.sDate = df.format(c.getTime());	


			SimpleDateFormat postFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");			
			Constants.postDate = postFormat.format(c.getTime());			

		}
		
		dateTextView.setText(Constants.sDate);
		*/
		
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

	BroadcastReceiver dateSetReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			Bundle bundleBroadcast = intent.getExtras();
			dateTextView.setText(bundleBroadcast.getString("key"));
			getLogList();

			sad.notifyDataSetChanged();
		}
	};

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
