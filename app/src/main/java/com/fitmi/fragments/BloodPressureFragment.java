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

import com.callback.BloodPressureChangeNotification;
import com.callback.Item;
import com.db.modules.BpLogModule;
import com.fitmi.R;
import com.fitmi.adapter.EntryAdapter;
import com.fitmi.adapter.PressureAdapter;
import com.fitmi.dao.BloodPressureDAO;
import com.fitmi.dao.CalenderFirsLastDay;
import com.fitmi.dao.EntryItem;
import com.fitmi.dao.PresureDAO;
import com.fitmi.dao.SectionItem;
import com.fitmi.utils.Constants;
import com.fitmi.utils.DateModule;
import com.fitmi.utils.FirstLastDayWeekMonthly;

/**
 * A simple {@link Fragment} subclass.
 *  Section header link
 *  
 *  http://sunil-android.blogspot.in/2013/08/section-header-listview-in-android.html
 */
public class BloodPressureFragment extends BaseFragment implements BloodPressureChangeNotification{//implements BloodPressureChangeNotification

	@InjectView(R.id.Heading)
	public TextView heading;

	@InjectView(R.id.Back)
	public ImageView back;
	
	@InjectView(R.id.backLiner)
	LinearLayout backLiner;

	@InjectView(R.id.Date)
	TextView dateTextView;

	@InjectView(R.id.textSeparator)
	TextView textSeparator;



	Button daily, monthly, weekly;

	ListView pressurelv;
	ArrayList<String> date = new ArrayList<>();

	ArrayList<BloodPressureDAO> bpInfo;

	BpLogModule bpModule;

	String dateString = "",dateStringTemp = "";

	DateModule dateConverter = new DateModule();
	DateModule getDate = new DateModule();
	FirstLastDayWeekMonthly firstLastDayDate = new FirstLastDayWeekMonthly();

	ArrayList<Item> items = new ArrayList<Item>();
	EntryAdapter adapter;
	String today = "";
	int selectGrapType = 1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_blood_pressure, container,
				false);

		ButterKnife.inject(this, v);
		setNullClickListener(v);
		heading.setText("Blood Pressure");
		
		/*Calendar c = Calendar.getInstance();	
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");			
		today = df.format(c.getTime());
		
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
		else{
			today=	Constants.sTempDate;
		}
		Constants.conditionDate = today;



		bpModule = new BpLogModule(getActivity());

		//bpInfo = bpModule.getBpInformation();

		daily = (Button) v.findViewById(R.id.dailybtn);
		monthly = (Button) v.findViewById(R.id.monthbtn);
		weekly = (Button) v.findViewById(R.id.weekbtn);
		pressurelv = (ListView) v.findViewById(R.id.pressurelv);


		daily.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				daily.setBackgroundColor(Color.parseColor("#B51F6B"));
				monthly.setBackgroundColor(Color.parseColor("#D379A6"));
				weekly.setBackgroundColor(Color.parseColor("#D379A6"));

				/*Calendar c = Calendar.getInstance();	
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");			
				today = df.format(c.getTime());*/
				selectGrapType = 1;				
				setBloodPressureListDaily();

			}
		});
		monthly.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				daily.setBackgroundColor(Color.parseColor("#D379A6"));
				monthly.setBackgroundColor(Color.parseColor("#B51F6B"));
				weekly.setBackgroundColor(Color.parseColor("#D379A6"));

				selectGrapType = 3;
				setBloodPressureListMonthlyly();
			}
		});
		weekly.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				daily.setBackgroundColor(Color.parseColor("#D379A6"));
				monthly.setBackgroundColor(Color.parseColor("#D379A6"));
				weekly.setBackgroundColor(Color.parseColor("#B51F6B"));

				selectGrapType = 2;			
				setBloodPressureListWeekly();
			}
		});

		//setSectionAdapterValue(bpInfo);
		setAdapter(getActivity(),pressurelv);
		setBloodPressureListDaily();

		pressurelv.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int pos, long id) {

				dialogDeleteItem(pos,(String)arg1.getTag());
				return true;
			}
		}); 

		return v;

	}

	@OnClick(R.id.backLiner)
	public void back() {
		getActivity().onBackPressed();
	}

	@OnClick(R.id.Date)
	public void changeDate() {

		Constants.fragmentNumber = 44;

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
			else{
				today=	Constants.sTempDate;
			}
			dateTextView.setText(Constants.sDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Constants.fragmentNumber = 44;
		getActivity().registerReceiver(dateSetReceiver,
				new IntentFilter("bloodpressure"));
	}

	BroadcastReceiver dateSetReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			Bundle bundleBroadcast = intent.getExtras();			
			dateTextView.setText(bundleBroadcast.getString("key"));

			switch (selectGrapType) {
			case 1:
				setBloodPressureListDaily();
				break;
			case 2:
				setBloodPressureListWeekly();
				break;
			case 3:
				setBloodPressureListMonthlyly();
				break;
			}

		}
	};

	@Override
	public void changeBloodPressure(BpLogModule bpModule,Context contex,ListView pressurelv) {
		// TODO Auto-generated method stub

		bpInfo = bpModule.getBpInformation();
		setSectionAdapterValue(bpInfo);

		setAdapter(contex,pressurelv);
	}

	public void setAdapter(Context context,ListView pressurelv)
	{		
		adapter = new EntryAdapter(context, items,pressurelv);
		pressurelv.setAdapter(adapter);
		adapter.notifyDataSetChanged();
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
				bpModule.deleteItem(id);

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

	public void setSectionAdapterValue(ArrayList<BloodPressureDAO> bpInfo)
	{

		if(items.size()>0){
			items.clear();
			date.clear();
		}

		for(int j=0;j<bpInfo.size();j++)
		{
			dateString = bpInfo.get(j).getAddedtime();
			dateString = dateConverter.dateConvert(dateString);		
			SectionItem header = new SectionItem(dateString);

			if(!date.contains(dateString))	
			{
				date.add(dateString);			
				items.add(header);
			}
			dateStringTemp = bpInfo.get(j).getAddedtime();
			dateStringTemp = dateConverter.dateConvert(dateStringTemp);		
			if(dateString.equalsIgnoreCase(dateStringTemp))		      
				items.add(new EntryItem(bpInfo.get(j).getLogTime(), bpInfo.get(j).getSys(),bpInfo.get(j).getDia(),"72",bpInfo.get(j).getId()));
		}
	}

	public void setBloodPressureListDaily()
	{
		if(bpInfo !=null && bpInfo.size()>0)
			bpInfo.clear();

		bpInfo = bpModule.getBpInformation();		
		setSectionAdapterValue(bpInfo);
		adapter.notifyDataSetChanged();
		headerDateSet();
	}

	public void setBloodPressureListWeekly()
	{		
		String selectedDate = dateTextView.getText().toString();
		selectedDate = selectedDate.replace("<", "");
		selectedDate = selectedDate.replace(">", "");


		CalenderFirsLastDay firstLastDayObj = firstLastDayDate.getWeekFirstDateLastDay(selectedDate);
		bpInfo = bpModule.getBpInformationWeekly(firstLastDayObj);
		setSectionAdapterValue(bpInfo);
		adapter.notifyDataSetChanged();
		headerDateSet();
	}

	public void setBloodPressureListMonthlyly()
	{		
		String selectedDate = dateTextView.getText().toString();
		selectedDate = selectedDate.replace("<", "");
		selectedDate = selectedDate.replace(">", "");

		CalenderFirsLastDay firstLastDayObj = firstLastDayDate.getMonthFirstDateLastDay(selectedDate);
		bpInfo = bpModule.getBpInformationMonthly(firstLastDayObj);
		setSectionAdapterValue(bpInfo);
		adapter.notifyDataSetChanged();
		headerDateSet();
	}

	@OnClick(R.id.txtPreview)
	public void datePreview()
	{
		String setDate = dateTextView.getText().toString().trim();
		setDate = dateConverter.datePreview(setDate);
		dateTextView.setText(setDate);
		Constants.conditionDate = dateConverter.conditionDateFormat(setDate);
		Constants.sTempDate= Constants.conditionDate;
		Constants.sDate=getDate.sDateFormat(setDate);
		
		switch (selectGrapType) {
		case 1:
			setBloodPressureListDaily();
			break;
		case 2:
			setBloodPressureListWeekly();
			break;
		case 3:
			setBloodPressureListMonthlyly();
			break;
		}
		//Toast.makeText(getActivity(), setDate, Toast.LENGTH_LONG).show();
	}

	@OnClick(R.id.txtNext)
	public void dateNext()
	{
		String setDate = dateTextView.getText().toString().trim();
		setDate = dateConverter.dateNext(setDate);
		dateTextView.setText(setDate);
		Constants.conditionDate = dateConverter.conditionDateFormat(setDate);
		Constants.sTempDate= Constants.conditionDate;
		Constants.sDate=getDate.sDateFormat(setDate);
		switch (selectGrapType) {
		case 1:
			setBloodPressureListDaily();
			break;
		case 2:
			setBloodPressureListWeekly();
			break;
		case 3:
			setBloodPressureListMonthlyly();
			break;
		}
		//Toast.makeText(getActivity(), setDate, Toast.LENGTH_LONG).show();
	}

	public void headerDateSet()
	{
		if(items.size()>0){
			textSeparator.setVisibility(View.GONE);
			pressurelv.setVisibility(View.VISIBLE);
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
			pressurelv.setVisibility(View.INVISIBLE);
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
}
