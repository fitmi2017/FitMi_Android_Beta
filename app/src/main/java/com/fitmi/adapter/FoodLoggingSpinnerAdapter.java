package com.fitmi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.fitmi.R;
import com.fitmi.fragments.FoodLoggingFragment;

public class FoodLoggingSpinnerAdapter extends BaseAdapter {

	Context ctx;
	String[] spinnerValues;
	Integer[] imagevalues;
	int framebg;

	public FoodLoggingSpinnerAdapter(Context ctx, String[] objects,Integer[] intobj,int bg) {

		this.ctx = ctx;
		spinnerValues = objects;
		imagevalues = intobj;
		framebg=bg;
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
		View mySpinner = inflater.inflate(R.layout.foodloggingspinneritem, parent, false);
		TextView flspinnertext = (TextView) mySpinner
				.findViewById(R.id.flspinnertext);
		ImageView flspinnerimage = (ImageView) mySpinner
				.findViewById(R.id.flspinnerimage);
		FrameLayout flframelayout=(FrameLayout) mySpinner
				.findViewById(R.id.flframelayout);
		flspinnertext.setText(spinnerValues[position]);

		if(FoodLoggingFragment.foodSpinnerSelect == position){
			flspinnertext.setAlpha(1.0f);
		}else{
			flspinnertext.setAlpha(0.6f);
		}


		if(imagevalues[position] !=null){
			flspinnerimage.setImageResource(imagevalues[position]);

			if(FoodLoggingFragment.foodSpinnerSelect == position){
				flspinnerimage.setAlpha(1.0f);
			}else{
				flspinnerimage.setAlpha(0.6f);
			}
		}
		flframelayout.setBackgroundResource(framebg);
		return mySpinner;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imagevalues.length;
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
