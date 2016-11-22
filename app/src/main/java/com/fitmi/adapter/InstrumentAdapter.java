package com.fitmi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fitmi.R;

import butterknife.ButterKnife;

public class InstrumentAdapter extends BaseAdapter {

	Context context;

	public InstrumentAdapter(Context context) {
		// TODO Auto-generated constructor stub

		this.context = context;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 10;
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
			view = inflater
					.inflate(R.layout.item_instrument_user_profile, null);

			holder = new ViewHolder(view);

			view.setTag(holder);

		} else {

			holder = (ViewHolder) view.getTag();

		}

		return view;
	}

}
