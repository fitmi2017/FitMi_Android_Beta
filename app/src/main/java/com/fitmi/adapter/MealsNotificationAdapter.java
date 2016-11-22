package com.fitmi.adapter;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;
import butterknife.ButterKnife;
import butterknife.InjectView;


import com.fitmi.R;
import com.fitmi.dao.UserInfoDAO;
import com.fitmi.utils.Constants;

public class MealsNotificationAdapter extends BaseAdapter {

	Context context;
	ArrayList<String> dataList;

	public MealsNotificationAdapter(Context context, ArrayList<String> dataList) {
		// TODO Auto-generated constructor stub

		this.context = context;
		this.dataList = dataList;

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

		@InjectView(R.id.UserName_ItemUser)
		TextView userName;
		@InjectView(R.id.textalarmtime)
		TextView textalarmtime;
		
		@InjectView(R.id.toggle_btn_on)
		ToggleButton toggle_btn_on;
		
		
		

		public ViewHolder(View view) {
			ButterKnife.inject(this, view);
		}

	}

	ViewHolder holder;

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub

		View view = arg1;

		if (view == null) {

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			//view = inflater.inflate(R.layout.item_user, null);
			
			view = inflater.inflate(R.layout.item_notification, null);

			holder = new ViewHolder(view);
			
			

			view.setTag(holder);

		} else {

			holder = (ViewHolder) view.getTag();

		}

		holder.userName.setText(dataList.get(arg0));
		
	

		holder.toggle_btn_on.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			 
	        @Override
	        public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
	          //  text.setText("Status: " + isChecked);
	        	
	        	//Toast.makeText(context, "Status: " + isChecked, Toast.LENGTH_LONG).show();
	   /*     	if(isChecked){
	        		 holder.textalarmtime.setVisibility(View.VISIBLE);
	        	CustomTimePickerDialog timePickerDialog = new CustomTimePickerDialog(context, timeSetListener, 
	                    Calendar.getInstance().get(Calendar.HOUR), 
	                    CustomTimePickerDialog.getRoundedMinute(Calendar.getInstance().get(Calendar.MINUTE) + CustomTimePickerDialog.TIME_PICKER_INTERVAL), true);
	        timePickerDialog.setTitle("Set hours and minutes");
	        timePickerDialog.show();
	        	}else{
	        		 holder.textalarmtime.setVisibility(View.GONE);
	        	}*/
	        	
	        }
	    });
		
	
		
		return view;
	}
	
	
/*	toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		 
        @Override
        public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
            text.setText("Status: " + isChecked);
        }
    });*/




}
