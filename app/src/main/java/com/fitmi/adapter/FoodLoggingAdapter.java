package com.fitmi.adapter;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.db.DatabaseHelper;
import com.db.modules.FoodLoginModule;
import com.fitmi.R;
import com.fitmi.dao.FitmiFoodLogDAO;
import com.fitmi.dao.MealTypeDAO;
import com.fitmi.fragments.FoodLoggingFragment;
import com.fitmi.utils.DateModule;
import com.fitmi.utils.NotifyCalorieChange;

public class FoodLoggingAdapter extends BaseAdapter {

	Context context;
	ArrayList<FitmiFoodLogDAO> dataList;
	DatabaseHelper databaseObject;
	
	
	ImageView snacksImg;
	ImageView breakFastImg;
	ImageView lunchImg;
	ImageView dinnerImg;
	String mealIdfromTable = "";
	String calory = "";
	TextView totalCalory;
	Bundle bundle;

	public FoodLoggingAdapter(Context context, ArrayList<FitmiFoodLogDAO>  dataList,TextView totalCalory,Bundle bundle) {
		// TODO Auto-generated constructor stub

		this.context = context;
		this.dataList = dataList;
		
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

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public class ViewHolder {

		@InjectView(R.id.FoodName_FoodLoggingListItem)
		TextView foodName;

		@InjectView(R.id.FL_ListItemParentLinear)
		LinearLayout fl_ListItemParentLinear;
		
//		@InjectView(R.id.foodDescription)
//		TextView foodDescription;
		
		@InjectView(R.id.foodListcalory)
		TextView foodListcalory;
		
		@InjectView(R.id.imageViewEdit)
		ImageView imageViewEdit;
		
		@InjectView(R.id.foodListGram)
		TextView foodListGram;

		public ViewHolder(View view) {
			ButterKnife.inject(this, view);
		}

	}

	ViewHolder holder;

	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub

		View view = arg1;

		if (view == null) {

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.food_logging_listitem, null);

			holder = new ViewHolder(view);

			view.setTag(holder);

		} else {

			holder = (ViewHolder) view.getTag();

		}
		
	/*	if(!dataList.get(position).getDescription().equalsIgnoreCase("null"))
			holder.foodDescription.setText(dataList.get(position).getDescription());*/
		
		if(!dataList.get(position).getMealWeight().equalsIgnoreCase("null"))
			
			
			holder.foodListGram.setText(dataList.get(position).getMealWeight()+" g");
		
		else
			holder.foodListGram.setText("0 g");

		holder.foodName.setText(dataList.get(position).getFoodName());	
		
		String calory = dataList.get(position).getCalory();
		double cal = 0;
		if(!calory.equalsIgnoreCase(""))
			cal = Double.parseDouble(calory);
		
		holder.foodListcalory.setText((int)cal+" cal");
		
		LocalClass local = new LocalClass();
		
		local.tagObject = dataList.get(position);
		local.position = position;
		local.calTxt = holder.foodListcalory;
		
		holder.imageViewEdit.setTag(local);
		holder.imageViewEdit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				
				LocalClass local = (LocalClass)view.getTag(); 
				
				//FitmiFoodLogDAO tagObject = local.tagObject; 
				
				dialog(local);
				
			}
		});
		

		/*if (FoodLoggingFragment.sFOODLOGGING_POS == position) {

			holder.fl_ListItemParentLinear
					.setBackgroundResource(R.color.home_green_select_light);

		} else {

			holder.fl_ListItemParentLinear.setBackgroundResource(R.color.white);

		}*/

		return view;
	}
	
	
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
				
				dataList.get(position).setCalory(calory);
				
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