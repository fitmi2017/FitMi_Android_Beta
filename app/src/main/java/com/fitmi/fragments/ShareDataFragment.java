package com.fitmi.fragments;


import android.os.Bundle;
import android.app.Dialog;
import android.app.Fragment;
import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.db.DatabaseHelper;
import com.db.modules.ActivityModule;
import com.db.modules.BpLogModule;
import com.db.modules.FoodLoginModule;
import com.db.modules.SleepModule;
import com.db.modules.UserInfoModule;
import com.db.modules.WaterModule;
import com.db.modules.WeightLogModule;
import com.fitmi.R;
import com.fitmi.adapter.ShareDataAdapter;
import com.fitmi.dao.CalenderFirsLastDay;
import com.fitmi.utils.Constants;
import com.fitmi.utils.FirstLastDayWeekMonthly;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShareDataFragment extends BaseFragment {  


	Button daily, monthly, weekly;
	ListView sharedatalv;

	WaterModule waterDb;
	WeightLogModule weightModule;
	FoodLoginModule foodLoginModule;
	ActivityModule activityModule;
	SleepModule sleepModule;
	BpLogModule bpModule;

	FirstLastDayWeekMonthly firstLastDayDate = new FirstLastDayWeekMonthly();

	@InjectView(R.id.txtTotalFood) 
	TextView txtTotalFood;

	@InjectView(R.id.txtTotalActivity)
	TextView txtTotalActivity;

	@InjectView(R.id.txtTotalWeight)
	TextView txtTotalWeight;
	
	@InjectView(R.id.variety2)
	TextView variety2;
	

	@InjectView(R.id.txtTotalWater)
	TextView txtTotalWater;

	@InjectView(R.id.systextt)
	TextView systextt;

	@InjectView(R.id.diatextt)
	TextView diatextt;

	@InjectView(R.id.txtTotalsleep)
	TextView txtTotalsleep;

	@InjectView(R.id.backLiner)
	LinearLayout backLiner;

	@InjectView(R.id.Heading)
	public TextView heading;

	@InjectView(R.id.Settings)
	ImageView Settings;
	
	@InjectView(R.id.Back)
	public ImageView back;

	//plus sign 
	@InjectView(R.id.linlay_one)
	LinearLayout linlay_one;
	
	@InjectView(R.id.txt_expandplus)
	TextView txt_expandplus;
	
	@InjectView(R.id.linlay_two)
	LinearLayout linlay_two;
	
	@InjectView(R.id.txt_expandplus1)
	TextView txt_expandplus1;
	
	@InjectView(R.id.linlay_three)
	LinearLayout linlay_three;
	
	@InjectView(R.id.txt_expandplus2)
	TextView txt_expandplus2;
	
	@InjectView(R.id.linlay_four)
	LinearLayout linlay_four;
	
	@InjectView(R.id.txt_expandplus3)
	TextView txt_expandplus3;
	
	@InjectView(R.id.linlay_five)
	LinearLayout linlay_five;
	
	@InjectView(R.id.txt_expandplus4)
	TextView txt_expandplus4;
	
	@InjectView(R.id.linlay_six)
	LinearLayout linlay_six;
	
	@InjectView(R.id.txt_expandplus5)
	TextView txt_expandplus5;
	
	String waterTotal = "";
	String weightTotal = "";		
	String totalCaloty = "";
	String totalCalotyBurn = "";
	String sleepTotal = "";
	String note = "";
	ArrayList<String> sumArray;

	DatabaseHelper databaseObject;


	public ShareDataFragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_share_data, container, false);

		databaseObject = new DatabaseHelper(getActivity());
		try {

			databaseObject.createDatabase();

			databaseObject.openDatabase();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Constants.fragmentSet = false;
		waterDb = new WaterModule(getActivity());
		weightModule = new WeightLogModule(getActivity());
		foodLoginModule = new FoodLoginModule(getActivity());
		activityModule = new ActivityModule();
		bpModule = new BpLogModule(getActivity());
		sleepModule = new SleepModule(getActivity());


		ButterKnife.inject(this, v);
		setNullClickListener(v);

		heading.setText("Share");
		back.setVisibility(View.GONE);	
		Settings.setVisibility(View.GONE);
		plusHideAll();
		//sharedatalv=(ListView)v.findViewById(R.id.sharedatalv);

		daily = (Button) v.findViewById(R.id.dailybtn);
		monthly = (Button) v.findViewById(R.id.monthbtn);
		weekly = (Button) v.findViewById(R.id.weekbtn);

		if(Constants.shareIntent){

			setDataMonthly();
			daily.setBackgroundColor(Color.parseColor("#D379A6"));
			monthly.setBackgroundColor(Color.parseColor("#B51F6B"));
			weekly.setBackgroundColor(Color.parseColor("#D379A6"));

		}else{

			setDataDaily();
			daily.setBackgroundColor(Color.parseColor("#B51F6B"));
			monthly.setBackgroundColor(Color.parseColor("#D379A6"));
			weekly.setBackgroundColor(Color.parseColor("#D379A6"));
		}

		daily.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				daily.setBackgroundColor(Color.parseColor("#B51F6B"));
				monthly.setBackgroundColor(Color.parseColor("#D379A6"));
				weekly.setBackgroundColor(Color.parseColor("#D379A6"));

				setDataDaily();
			}
		});
		monthly.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				daily.setBackgroundColor(Color.parseColor("#D379A6"));
				monthly.setBackgroundColor(Color.parseColor("#B51F6B"));
				weekly.setBackgroundColor(Color.parseColor("#D379A6"));

				setDataMonthly();
			}
		});
		weekly.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				daily.setBackgroundColor(Color.parseColor("#D379A6"));
				monthly.setBackgroundColor(Color.parseColor("#D379A6"));
				weekly.setBackgroundColor(Color.parseColor("#B51F6B"));

				setDataWeekly();
			}
		});


		return v;

	}

	@OnClick(R.id.waterNote)
	public void clickWaterNote()
	{
		noteDialog();
	}

	@OnClick(R.id.sleepNote)
	public void clickSleepNote(){
		noteDialog();
	}

	@OnClick(R.id.bpNote)
	public void clickBpNote(){
		noteDialog();
	}

	@OnClick(R.id.weightNote)
	public void clickWeightNote(){
		noteDialog();
	}

	@OnClick(R.id.activityNote)
	public void clickActivityNote()
	{
		noteDialog();
	}

	@OnClick(R.id.foodNote)
	public void clickFoodNote(){
		noteDialog();
	}

	@OnClick(R.id.sharebtn)
	public void shareData()
	{

		ShareFragment fragment = new ShareFragment();

		Bundle bundle = new Bundle();
		bundle.putString("waterTotal", waterTotal);
		bundle.putString("weightTotal", weightTotal);
		bundle.putString("totalCaloty", totalCaloty);//
		bundle.putString("totalCalotyBurn", totalCalotyBurn);
		bundle.putString("note", note);
		bundle.putStringArrayList("sumArray", sumArray);
		fragment.setArguments(bundle);

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.add(R.id.root_share_frame, fragment, "ShareFragment");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();
	}

/*	@OnClick(R.id.Settings)
	public void gotoSettings() {

		SettingsFragment fragment = new SettingsFragment();

		Bundle bundle = new Bundle();
		bundle.putInt("root_id", R.id.root_share_frame);
		fragment.setArguments(bundle);

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.add(R.id.root_share_frame, fragment, "SettingsFragment");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();

	}
*/

	public void setDataDaily()
	{

		String todayDate = getTodayDate();

		SimpleDateFormat baseFormatter = new SimpleDateFormat(
				"EEEE, MMM dd, yyyy", Locale.ENGLISH);

		try {
			Date parsedDate = baseFormatter.parse(todayDate.trim());
			SimpleDateFormat targetFormatter = new SimpleDateFormat("yyyy-MM-dd");
			String formattedDate = targetFormatter.format(parsedDate);

			waterTotal = waterDb.sumWaterOzDaily(formattedDate);
			weightTotal = weightModule.sumWaight(formattedDate);
			totalCaloty = foodLoginModule.totalCaloryDaily(formattedDate,databaseObject);
			totalCalotyBurn = activityModule.totalCaloryBurnDaily(formattedDate,databaseObject);
			sumArray = bpModule.sumBpDaily(formattedDate);
			sleepTotal = sleepModule.sumSleepHourDaily(formattedDate);

			setTotalValue(waterTotal,weightTotal,totalCaloty,totalCalotyBurn,sumArray,sleepTotal);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public void setDataMonthly()
	{
		String todayDate = getTodayDate();

		CalenderFirsLastDay firstLastDayObj = firstLastDayDate.getMonthFirstDateLastDay(todayDate);

		waterTotal = waterDb.sumWaterOzMonthly(firstLastDayObj);
		weightTotal = weightModule.sumWaightMonthly(firstLastDayObj);		
		totalCaloty = foodLoginModule.totalCaloryMonthly(firstLastDayObj,databaseObject);
		totalCalotyBurn = activityModule.totalCaloryBurnMonthly(firstLastDayObj,databaseObject);
		sumArray = bpModule.sumBpMonthly(firstLastDayObj);
		sleepTotal = sleepModule.sumSleepHourMonthly(firstLastDayObj);

		setTotalValue(waterTotal,weightTotal,totalCaloty,totalCalotyBurn,sumArray,sleepTotal);	

	}

	public void setDataWeekly()
	{
		String todayDate = getTodayDate();

		CalenderFirsLastDay firstLastDayObj = firstLastDayDate.getWeekFirstDateLastDay(todayDate);
		waterTotal = waterDb.sumWaterOzWeekly(firstLastDayObj);
		weightTotal = weightModule.sumWaightWeekly(firstLastDayObj);		
		totalCaloty = foodLoginModule.totalCaloryWeekly(firstLastDayObj,databaseObject);
		totalCalotyBurn = activityModule.totalCaloryBurnWeekly(firstLastDayObj,databaseObject);
		sumArray = bpModule.sumBpWeekly(firstLastDayObj);
		sleepTotal = sleepModule.sumSleepHourWeekly(firstLastDayObj);

		setTotalValue(waterTotal,weightTotal,totalCaloty,totalCalotyBurn,sumArray,sleepTotal);

	}

	public String getTodayDate()
	{
		Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());

		SimpleDateFormat df = new SimpleDateFormat("EEEE, MMM dd, yyyy");
		String formattedDate = df.format(c.getTime());

		return formattedDate;
	}

	public void setTotalValue(String waterTotal,String weightTotal,String totalCaloty,String totalCalotyBurn,ArrayList<String> sumArray,String sleepTotal)
	{
		if(waterTotal==null){		

			txtTotalWater.setText("0");
		}else{
			txtTotalWater.setText(waterTotal);
		}

		if(weightTotal==null){		

			//avinash unit
			txtTotalWeight.setText("0");
			if(Constants.gunitwt==0){
				variety2.setText("Kg");
				}else{
				
					variety2.setText("lbs");
				}
			variety2.setText("");
		}else{
			
			if(Constants.gunitwt==0){
				txtTotalWeight.setText(weightTotal);
				variety2.setText("Kg");
				}else{
					double wt,f_wt;
					wt=Double.parseDouble(weightTotal);
					f_wt=wt*2.2046;
					
					Log.e(" Getting value in wt ", String.valueOf(f_wt));
					
					DecimalFormat formatter = new DecimalFormat("##.##");
					String yourFormattedString = formatter.format(f_wt);
				//	dataList.get(arg0).getMealWeight()
					Log.e(" Getting value in wt ", String.valueOf(yourFormattedString));
					txtTotalWeight.setText(yourFormattedString);
					variety2.setText("lbs");
				}
		//	txtTotalWeight.setText(weightTotal);
		}

		if(totalCaloty==null){		

			txtTotalFood.setText("0");
		}else{
			float totalcal=Float.parseFloat(totalCaloty);
			DecimalFormat format = new DecimalFormat("0.00");
			totalCaloty=format.format(totalcal);
			System.out.println(format.format(totalcal));
			txtTotalFood.setText(totalCaloty);
		}

		if(totalCalotyBurn==null){		

			txtTotalActivity.setText("0");
		}else{
			txtTotalActivity.setText(totalCalotyBurn);
		}

		if(sleepTotal ==null){

			txtTotalsleep.setText("0");
		}else{
			txtTotalsleep.setText(sleepTotal);
		}



		if(sumArray.size()>0){

			if(sumArray.get(0) == null)
				systextt.setText("0");
			else

				systextt.setText(sumArray.get(0));

			if(sumArray.get(1) == null)
				diatextt.setText("0");
			else
				diatextt.setText(sumArray.get(1));


		}
		else{
			systextt.setText("0");				
			diatextt.setText("0");			
		}

	}

	public void noteDialog()
	{
		// custom dialog
		final Dialog dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.note_dialog);
		
		final EditText edTextNote = (EditText)dialog.findViewById(R.id.edTextNote);


		Button saveButton = (Button) dialog.findViewById(R.id.saveBtn);
		// if button is clicked, close the custom dialog
		saveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(!note.equalsIgnoreCase(""))
				  note +=" "+edTextNote.getText().toString();
				else
					note =edTextNote.getText().toString();
				dialog.dismiss();
			}
		});

		Button cancelButton = (Button) dialog.findViewById(R.id.cancelBtn);
		// if button is clicked, close the custom dialog
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();
	}

	
	@OnClick(R.id.txt_expandplus)
	public void linlay_oneClick(){
		plusShow(1);
		
	}
	
	@OnClick(R.id.txt_expandplus1)
	public void linlay_twoClick(){

		plusShow(2);
	}
	
	@OnClick(R.id.txt_expandplus2)
	public void linlay_threeClick(){
		plusShow(3);
	}
	
	@OnClick(R.id.txt_expandplus3)
	public void linlay_fourClick(){
		plusShow(4);
	}
	
	@OnClick(R.id.txt_expandplus4)
	public void linlay_fiveClick(){
		plusShow(5);
	}
	
	@OnClick(R.id.txt_expandplus5)
	public void linlay_sixClick(){
		plusShow(6);
	}
	

	public void plusShow(int pos)
	{
		switch(pos)
		{
		
		case 1:
			if (linlay_one.getVisibility() == View.VISIBLE) {
				
				plusHideAll();
			} else {
				plusHideAll();
				txt_expandplus.setText("-");
				linlay_one.setVisibility(View.VISIBLE);
				

			}
			
		break;
		
		case 2:
			
			if (linlay_two.getVisibility() == View.VISIBLE) {
			    
			
				plusHideAll();
			} else {
				plusHideAll();
				txt_expandplus1.setText("-");
				linlay_two.setVisibility(View.VISIBLE);
				
			}
		break;
		
		case 3:
			if (linlay_three.getVisibility() == View.VISIBLE) {
			    
			
				plusHideAll();
			} else {
				plusHideAll();
				txt_expandplus2.setText("-");
				linlay_three.setVisibility(View.VISIBLE);
		
			}
		break;
		
		case 4:
			if (linlay_four.getVisibility() == View.VISIBLE) {
			    
			
				plusHideAll();
			} else {
				plusHideAll();
				txt_expandplus3.setText("-");
				linlay_four.setVisibility(View.VISIBLE);
			
			}
		break;
		
		case 5:
			if (linlay_five.getVisibility() == View.VISIBLE) {
			  
				plusHideAll();
			} else {
				plusHideAll();
				txt_expandplus4.setText("-");
				linlay_five.setVisibility(View.VISIBLE);
				
			}
		break;
		
		case 6:
			if (linlay_six.getVisibility() == View.VISIBLE) {
			    
				
				plusHideAll();
			} else {
				plusHideAll();
				txt_expandplus5.setText("-");
				linlay_six.setVisibility(View.VISIBLE);
			}
		break;
		}
	}
	
	public void plusHideAll(){
	    
		
		linlay_one.setVisibility(View.GONE);
		linlay_two.setVisibility(View.GONE);
		linlay_three.setVisibility(View.GONE);
		linlay_four.setVisibility(View.GONE);
		linlay_five.setVisibility(View.GONE);
		linlay_six.setVisibility(View.GONE);
		txt_expandplus.setText("+");
		txt_expandplus1.setText("+");
		txt_expandplus2.setText("+");
		txt_expandplus3.setText("+");
		  
		txt_expandplus4.setText("+");
		txt_expandplus5.setText("+");
	}
	
}
