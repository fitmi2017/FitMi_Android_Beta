package com.fitmi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fitmi.R;

public class SpinnerAdapter extends ArrayAdapter<String> {

	Context ctx;
	String[] spinnerValues;

	public SpinnerAdapter(Context ctx, int txtViewResourceId, String[] objects) {
		super(ctx, txtViewResourceId, objects);

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
		View mySpinner = inflater.inflate(R.layout.spinner_item, parent, false);
		TextView main_text = (TextView) mySpinner
				.findViewById(R.id.SpinnerText);
		main_text.setText(spinnerValues[position]);

		return mySpinner;
	}
}
