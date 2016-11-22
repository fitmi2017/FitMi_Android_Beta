package com.fitmi.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.fitmi.R;
import com.fitmi.fragments.UserInfoFragment;

public class UserInfoSpinnerAdapter extends BaseAdapter {

	Context ctx;
	String[] spinnerValues;
	
	public static ArrayList<RadioButton> radioList = new ArrayList<RadioButton>();
	
	public UserInfoSpinnerAdapter(Context ctx, String[] objects) {
	
		this.ctx = ctx;
		spinnerValues = objects;		
	}

	@Override
	public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
		return getCustomView(position, cnvtView, prnt);
	}

	@Override
	public View getView(int pos, View cnvtView, ViewGroup prnt) {
		return getCustomView(pos, cnvtView, prnt);
	}

	public View getCustomView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View mySpinner = inflater.inflate(R.layout.user_info_adapter, parent, false);
		TextView spinnertext = (TextView) mySpinner
				.findViewById(R.id.userInfoSpinnertext);
		RadioButton rButtonSelect = (RadioButton) mySpinner.findViewById(R.id.userInfoSpinnerRadio);
		
		//radioList.add(rButtonSelect);
		
		spinnertext.setText(spinnerValues[position]);
		
		if(UserInfoFragment.UNIT_SELECT_POSITION == position){			
			rButtonSelect.setChecked(true);
		}else{			
			rButtonSelect.setChecked(false);
		}
		
		
		return mySpinner;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return spinnerValues.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
}
