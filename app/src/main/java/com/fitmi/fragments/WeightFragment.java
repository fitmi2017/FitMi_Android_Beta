package com.fitmi.fragments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Dialog;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout.LayoutParams;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.callback.Item;
import com.callback.WeightChangeNotification;
import com.db.modules.WeightLogModule;
import com.fitmi.R;
import com.fitmi.adapter.EntryAdapter;
import com.fitmi.adapter.EntryWeightAdapter;
import com.fitmi.adapter.WeightAdapter;
import com.fitmi.dao.BloodPressureDAO;
import com.fitmi.dao.CalenderFirsLastDay;
import com.fitmi.dao.EntryItem;
import com.fitmi.dao.EntryItemWeight;
import com.fitmi.dao.SectionItem;
import com.fitmi.dao.WeightDAO;
import com.fitmi.dao.WeightLogDAO;
import com.fitmi.utils.Constants;
import com.fitmi.utils.DateModule;
import com.fitmi.utils.FirstLastDayWeekMonthly;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeightFragment extends BaseFragment implements WeightChangeNotification{

	@InjectView(R.id.Heading)
	public TextView heading;

	@InjectView(R.id.Back)
	public ImageView back;
	
	@InjectView(R.id.backLiner)
	LinearLayout backLiner;
	
	Button daily, monthly, weekly;
	private ListView weightlv;

	ArrayList<Item> items = new ArrayList<Item>();
	EntryWeightAdapter adapter;
	WeightLogModule weightModule;
	ArrayList<WeightLogDAO> weightInfo;
	String dateString = "",dateStringTemp = "";
	DateModule dateConverter = new DateModule();
	ArrayList<String> date = new ArrayList<>();

	DateModule getDate = new DateModule();
	int selectGrapType = 1;

	FirstLastDayWeekMonthly firstLastDayDate = new FirstLastDayWeekMonthly();
	DateModule dateModule = new DateModule();

	@InjectView(R.id.Date)
	TextView dateTextView;

	@InjectView(R.id.textSeparator)
	TextView textSeparator;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_weight, container, false);

		ButterKnife.inject(this, v);
		setNullClickListener(v);
		heading.setText("Weight");	
		
		Calendar c = Calendar.getInstance();	
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Constants.conditionDate = df.format(c.getTime());

		Constants.fragmentNumber = 33;
		getActivity().registerReceiver(dateSetReceiver,
				new IntentFilter("weight"));

		weightModule = new WeightLogModule(getActivity());

		daily = (Button) v.findViewById(R.id.dailybtn);
		monthly = (Button) v.findViewById(R.id.monthbtn);
		weekly = (Button) v.findViewById(R.id.weekbtn);

		daily.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				daily.setBackgroundColor(Color.parseColor("#B51F6B"));
				monthly.setBackgroundColor(Color.parseColor("#D379A6"));
				weekly.setBackgroundColor(Color.parseColor("#D379A6"));

				selectGrapType = 1;				
				setWeightListDaily();
			}
		});
		monthly.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				daily.setBackgroundColor(Color.parseColor("#D379A6"));
				monthly.setBackgroundColor(Color.parseColor("#B51F6B"));
				weekly.setBackgroundColor(Color.parseColor("#D379A6"));

				selectGrapType = 3;
				setWeightListMonthlyly();
			}
		});
		weekly.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				daily.setBackgroundColor(Color.parseColor("#D379A6"));
				monthly.setBackgroundColor(Color.parseColor("#D379A6"));
				weekly.setBackgroundColor(Color.parseColor("#B51F6B"));

				selectGrapType = 2;			
				setWeightListWeekly();
			}
		});

		weightlv = (ListView) v.findViewById(R.id.weightlv);

		setAdapter(getActivity(),weightlv);
		setWeightListDaily();
		
		
		
		weightlv.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int pos, long id) {

				dialogDeleteItem(pos,(String)arg1.getTag());
				return true;
			}
		});

		return v;
	}

	@OnClick(R.id.Date)
	public void changeDate() {

		Constants.fragmentNumber = 33;

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.add(R.id.root_home_frame, new CalendarFragment(),
				"CalendarFragment");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		try {

			/*Calendar c = Calendar.getInstance();
			SimpleDateFormat targetFormatter = new SimpleDateFormat(
					"EEEE, MMM dd, yyyy", Locale.ENGLISH);
			String formattedDate = targetFormatter.format(c.getTime());
			dateTextView.setText(formattedDate);*/
			
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@OnClick(R.id.backLiner)
	public void back() {

		getActivity().onBackPressed();

	}

	public void setAdapter(Context context,ListView pressurelv)
	{
		adapter = new EntryWeightAdapter(context, items,pressurelv);
		adapter.setStatusRows();
		pressurelv.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	public void setWeightListDaily()
	{
		if(weightInfo !=null && weightInfo.size()>0)
			weightInfo.clear();

		weightInfo = weightModule.getWeightInformation();		
		setSectionAdapterValue(weightInfo);
		//adapter.notifyDataSetChanged();
		setAdapter(getActivity(),weightlv);
		headerDateSet();
	}

	public void setWeightListWeekly()
	{		
		String selectedDate = dateTextView.getText().toString();
		selectedDate = selectedDate.replace("<", "");
		selectedDate = selectedDate.replace(">", "");

		CalenderFirsLastDay firstLastDayObj = firstLastDayDate.getWeekFirstDateLastDay(selectedDate);
		weightInfo = weightModule.getWeightInformationWeekly(firstLastDayObj);
		setSectionAdapterValue(weightInfo);
		//adapter.notifyDataSetChanged();
		setAdapter(getActivity(),weightlv);
		headerDateSet();
	}

	public void setWeightListMonthlyly()
	{		
		String selectedDate = dateTextView.getText().toString();
		selectedDate = selectedDate.replace("<", "");
		selectedDate = selectedDate.replace(">", "");

		CalenderFirsLastDay firstLastDayObj = firstLastDayDate.getMonthFirstDateLastDay(selectedDate);
		weightInfo = weightModule.getWeightInformationMonthly(firstLastDayObj);
		setSectionAdapterValue(weightInfo);
		//adapter.notifyDataSetChanged();
		setAdapter(getActivity(),weightlv);
		headerDateSet();
	}

	public void setSectionAdapterValue(ArrayList<WeightLogDAO> weightInfo)
	{

		if(items.size()>0){
			items.clear();
			date.clear();
		}

		for(int j=0;j<weightInfo.size();j++)
		{
			dateString = weightInfo.get(j).getAddedtime();
			dateString = dateConverter.dateConvert(dateString);		
			SectionItem header = new SectionItem(dateString);

			if(!date.contains(dateString))	
			{
				date.add(dateString);			
				items.add(header);
			}
			dateStringTemp = weightInfo.get(j).getAddedtime();
			dateStringTemp = dateConverter.dateConvert(dateStringTemp);		
			if(dateString.equalsIgnoreCase(dateStringTemp))		      
				items.add(new EntryItemWeight(weightInfo.get(j).getLogTime(), weightInfo.get(j).getWeight(),weightInfo.get(j).getId()));
		}
	}

	/*@Override
	public void onPause() {
		// TODO Auto-generated method stub

		getActivity().unregisterReceiver(dateSetReceiver);

		super.onPause();
	}*/

	BroadcastReceiver dateSetReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			Bundle bundleBroadcast = intent.getExtras();			
			dateTextView.setText(bundleBroadcast.getString("key"));

			switch (selectGrapType) {
			case 1:
				setWeightListDaily();
				break;
			case 2:
				setWeightListWeekly();
				break;
			case 3:
				setWeightListMonthlyly();
				break;
			}

		}
	};

	@OnClick(R.id.txtPreview)
	public void datePreview()
	{
		String setDate = dateTextView.getText().toString().trim();
		setDate = dateModule.datePreview(setDate);
		dateTextView.setText(setDate);
		Constants.conditionDate = dateModule.conditionDateFormat(setDate);
		Constants.postDate =getDate.getFormatDateSearchInsert(setDate);
		Constants.sTempDate= Constants.conditionDate;
		Constants.sDate=getDate.sDateFormat(setDate);

		switch (selectGrapType) {
		case 1:
			setWeightListDaily();
			break;
		case 2:
			setWeightListWeekly();
			break;
		case 3:
			setWeightListMonthlyly();
			break;
		}
	}

	@OnClick(R.id.txtNext)
	public void dateNext()
	{
		String setDate = dateTextView.getText().toString().trim();
		setDate = dateModule.dateNext(setDate);
		dateTextView.setText(setDate);

		Constants.conditionDate = dateModule.conditionDateFormat(setDate);
		Constants.postDate =getDate.getFormatDateSearchInsert(setDate);
		Constants.sTempDate= Constants.conditionDate;
		Constants.sDate=getDate.sDateFormat(setDate);

		switch (selectGrapType) {
		case 1:
			setWeightListDaily();
			break;
		case 2:
			setWeightListWeekly();
			break;
		case 3:
			setWeightListMonthlyly();
			break;
		}
	}

	@Override
	public void changeWeight(WeightLogModule weightModule, Context contex,
			ListView pressurelv) {
		// TODO Auto-generated method stub

		weightInfo = weightModule.getWeightInformation();
		setSectionAdapterValue(weightInfo);

		setAdapter(contex,pressurelv);
	}

	public void headerDateSet()
	{
		if(items.size()>0){
			textSeparator.setVisibility(View.GONE);
			weightlv.setVisibility(View.VISIBLE);
		}else{

			String formattedDate = "";
			formattedDate = dateTextView.getText().toString().trim();
			SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
			Date convertedDate = new Date();
			try {
				convertedDate = dateFormat.parse(formattedDate);

				SimpleDateFormat targetFormatter = new SimpleDateFormat(
						"EEEE, MMMM dd, yyyy", Locale.ENGLISH);
				formattedDate = targetFormatter.format(convertedDate);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			textSeparator.setText(formattedDate);
			textSeparator.setVisibility(View.VISIBLE);
			weightlv.setVisibility(View.INVISIBLE);
		}
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

	
	/** 
	 *  Dialog delete item
	 */

	public void dialogDeleteItem(final int position,final String id)
	{

		final Dialog dialog=new Dialog(getActivity()/*,R.style.Theme_Dialog*/);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		dialog.setContentView(R.layout.dialog_delete_item);


		dialog.findViewById(R.id.savebtn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				items.remove(position);
				adapter.notifyDataSetChanged();
				weightModule.deleteItem(id);

				dialog.dismiss();
			}
		});
		dialog.findViewById(R.id.cancelbtn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
		dialog.getWindow().setLayout((6 * width)/7, LayoutParams.WRAP_CONTENT);

	}
	
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
