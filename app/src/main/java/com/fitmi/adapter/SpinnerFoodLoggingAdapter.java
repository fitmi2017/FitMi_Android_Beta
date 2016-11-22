package com.fitmi.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.fitmi.R;
import com.fitmi.fragments.FoodLoggingFragment;

public class SpinnerFoodLoggingAdapter extends BaseAdapter {

	Context context;
	ArrayList<String> dataList;

	public SpinnerFoodLoggingAdapter(Context context, ArrayList<String> dataList) {
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

		@InjectView(R.id.FoodName_FoodLoggingListItem)
		TextView foodName;

		@InjectView(R.id.FL_ListItemParentLinear)
		LinearLayout fl_ListItemParentLinear;

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
			view = inflater.inflate(R.layout.food_logging_listitem, null);

			holder = new ViewHolder(view);

			view.setTag(holder);

		} else {

			holder = (ViewHolder) view.getTag();

		}

		holder.foodName.setText(dataList.get(arg0));

		if (FoodLoggingFragment.sFOODLOGGING_POS == arg0) {

			holder.fl_ListItemParentLinear
					.setBackgroundResource(R.color.home_green_select_light);

		} else {

			holder.fl_ListItemParentLinear.setBackgroundResource(R.color.white);

		}

		return view;
	}

}