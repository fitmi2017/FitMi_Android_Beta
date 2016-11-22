package com.fitmi.adapter;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.InjectView;

import com.callback.BloodPressureChangeNotification;
import com.callback.Item;
import com.db.DatabaseHelper;
import com.db.modules.BpLogModule;
import com.db.modules.FoodLoginModule;
import com.fitmi.R;
import com.fitmi.adapter.FoodLoggingAdapter.LocalClass;
import com.fitmi.adapter.PressureAdapter.ViewHolder;
import com.fitmi.dao.BloodPressureDAO;
import com.fitmi.dao.BloodPressureSet;
import com.fitmi.dao.EntryItem;
import com.fitmi.dao.FitmiFoodLogDAO;
import com.fitmi.dao.SectionItem;
import com.fitmi.dao.SectionItemFoodlogin;
import com.fitmi.fragments.BloodPressureFragment;
import com.fitmi.fragments.FoodLoggingFragment;
import com.fitmi.utils.Constants;
import com.fitmi.utils.NotifyCalorieChange;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class EntryFoodLoginAdapter extends ArrayAdapter<Item> {

	private Context context;
	private ArrayList<Item> items;
	private LayoutInflater vi;
	
	
	DatabaseHelper databaseObject;	
	
	ImageView snacksImg;
	ImageView breakFastImg;
	ImageView lunchImg;
	ImageView dinnerImg;
	String mealIdfromTable = "";
	String calory = "";
	TextView totalCalory;
	Bundle bundle;
	FitmiFoodLogDAO ei;

	public EntryFoodLoginAdapter(Context context,ArrayList<Item> items,TextView totalCalory,Bundle bundle) {
		super(context,0, items);
		this.context = context;
		this.items = items;		
		
		this.totalCalory = totalCalory;
		this.bundle = bundle;
		
		databaseObject = new DatabaseHelper(context);
		try {

			databaseObject.createDatabase();

			databaseObject.openDatabase();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		//ViewHolder holder = null;
		final Item i = items.get(position);
		if (i != null) {
			if(i.isSection()){
				SectionItemFoodlogin si = (SectionItemFoodlogin)i;
				v = vi.inflate(R.layout.blue_header_listitem, null);
				
				//v.setBackgroundColor(Color.parseColor("#008F4D"));				
			

				//v.setOnClickListener(null);
				v.setOnLongClickListener(null);
				v.setLongClickable(false);

				TextView sectionView = (TextView) v.findViewById(R.id.textSeparator);
				sectionView.setText(si.getTitle());
				
				TextView headerCalTotal = (TextView)v.findViewById(R.id.headerCalTotal);
				
				double total = Double.parseDouble(si.getTotal());
				
				headerCalTotal.setText((int)total+"");

			}else{
				ei = (FitmiFoodLogDAO)i;
				v = vi.inflate(R.layout.food_logging_listitem, null);	
				
				v.setOnClickListener(null);
				v.setOnLongClickListener(null);
				v.setLongClickable(false);
				
				
				TextView foodName = (TextView)v.findViewById(R.id.FoodName_FoodLoggingListItem);
				
				LinearLayout fl_ListItemParentLinear = (LinearLayout)v.findViewById(R.id.FL_ListItemParentLinear);				
				
				TextView foodDescription = (TextView)v.findViewById(R.id.foodDescription);				
				
				TextView foodListcalory = (TextView)v.findViewById(R.id.foodListcalory);				
				
				ImageView imageViewEdit = (ImageView)v.findViewById(R.id.imageViewEdit);
				
				TextView foodListGram = (TextView)v.findViewById(R.id.foodListGram);
				
				
				foodName.setText(ei.getFoodName());
			/*	
				if(!ei.getDescription().equalsIgnoreCase("null"))				
				   foodDescription.setText(ei.getDescription());*/
				
				if(!ei.getMealWeight().equalsIgnoreCase("null"))
				
					if(Constants.gunitfw==0){
						foodListGram.setText(ei.getMealWeight()+" g");
						}else{
							double wt,f_wt;
							wt=Double.parseDouble(ei.getMealWeight());
							f_wt=wt/28.34952;
							
							Log.e(" Getting value in oz ", String.valueOf(f_wt));
							
							DecimalFormat formatter = new DecimalFormat("##.##");
							String yourFormattedString = formatter.format(f_wt);
							Log.e(" Getting value in oz ", String.valueOf(yourFormattedString));
							foodListGram.setText(yourFormattedString+" oz");;
						}
					//foodListGram.setText(ei.getMealWeight()+" g");
				else{
				//	foodListGram.setText("0 g");
					
					if(Constants.gunitfw==0){
						foodListGram.setText("0 g");
					}{
						foodListGram.setText("0 oz");
					}
				}
				double cal = 0;
				if(!ei.getCalory().equalsIgnoreCase(""))
				  cal = Double.parseDouble(ei.getCalory());
				
				foodListcalory.setText((int)cal+" cal");
				
				LocalClass local = new LocalClass();
				
				local.tagObject = ei;
				local.position = position;
				local.calTxt = foodListcalory;
				
				imageViewEdit.setTag(local);
				imageViewEdit.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View view) {
						// TODO Auto-generated method stub
						
						LocalClass local = (LocalClass)view.getTag(); 
						
						//FitmiFoodLogDAO tagObject = local.tagObject; 
						
						dialog(local);
						
					}
				});
				
				
				v.setTag(ei.getMealId());
			}
		}
		return v;
	}
	
	
	public static class ViewHolder {
		public TextView textView, hearttext, systext, diatext;
		public ImageView dd;
		public LinearLayout hiddenlayout;

	}
	
	
	/**
	 *   Dialog to edit blood pressure 
	 */
	
	//avinash enter search meal weight //
	
	public void dialog(LocalClass local)
	{
		final FitmiFoodLogDAO tagLocalObject = local.tagObject; 
		
		final int position = local.position;
		
	    String minute = local.calTxt.getText().toString();
		
		String[] parts = minute.split("c");
		minute =parts[0];
		
		final Dialog dialog=new Dialog(context/*,R.style.Theme_Dialog*/);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_edit_calory);
		final EditText newCalory = (EditText)dialog.findViewById(R.id.newCalory);	
		
		TextView dialogTitle = (TextView)dialog.findViewById(R.id.dialogTitle);
		dialogTitle.setText("Edit calorie");
		
		newCalory.setText(minute.trim());

		dialog.findViewById(R.id.savebtn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(newCalory.getText().toString().equalsIgnoreCase(""))
				{
					Toast.makeText(context, "Enter calory", Toast.LENGTH_LONG).show();
					return;
				}
				
				calory = newCalory.getText().toString();
				
				FoodLoginModule.editCalory(tagLocalObject, databaseObject, calory);			
				
				ei.setCalory(calory);
				
				notifyDataSetChanged();
				
				FoodLoggingFragment foodLogin = new FoodLoggingFragment();
				
				NotifyCalorieChange callBack = (NotifyCalorieChange) foodLogin;
				
				callBack.calorieUpdate(totalCalory,bundle,databaseObject);
				
				dialog.dismiss();
				
			}
		});
		dialog.findViewById(R.id.cancelbtn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		dialog.show();
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
		dialog.getWindow().setLayout((6 * width)/7, LayoutParams.WRAP_CONTENT);

	}

	
	class LocalClass
	{
		FitmiFoodLogDAO tagObject;
		int position;
		TextView calTxt;
	}
}
