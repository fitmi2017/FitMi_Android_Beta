package com.fitmi.adapter;

/**
 * Created by dts_user on 25/8/15.
 */

import java.util.ArrayList;
import java.util.TreeSet;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fitmi.R;

public class WeightAdapter extends BaseAdapter {

	private static final int TYPE_ITEM = 0;
	private static final int TYPE_SEPARATOR = 1;
	protected ListView mListView;
	private static int pos;
	int counter = 0;
	private ArrayList<String> mData = new ArrayList<String>();
	private ArrayList<String> bmi = new ArrayList<String>();
	private ArrayList<String> cloud = new ArrayList<String>();
	private ArrayList<String> water = new ArrayList<String>();
	private ArrayList<String> body = new ArrayList<String>();
	private ArrayList<String> bone = new ArrayList<String>();
	private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();

	boolean[] weightRows = null;
	int lastClickPos = -1;

	private LayoutInflater mInflater;

	public WeightAdapter(Context context) {
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	public void setStatusRows() {
		weightRows = new boolean[mData.size()];
	}

	public void addItem(final String item, String item1, String item2,
			String item3, String item4, String item5) {
		mData.add(item);
		bmi.add(item1);
		cloud.add(item2);
		water.add(item3);
		body.add(item4);
		bone.add(item5);
		notifyDataSetChanged();
	}

	public void addSectionHeaderItem(final String item) {
		mData.add(item);
		sectionHeader.add(mData.size() - 1);
		notifyDataSetChanged();
	}

	@Override
	public int getItemViewType(int position) {
		return sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public String getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		int rowType = getItemViewType(position);
		System.out.println("rowType=" + rowType);
		if (convertView == null) {
			holder = new ViewHolder();
			switch (rowType) {
			case TYPE_ITEM:
				convertView = mInflater.inflate(R.layout.weight_listitem, null);
				holder.textView = (TextView) convertView
						.findViewById(R.id.textTime);
				holder.plus = (TextView) convertView.findViewById(R.id.plus);
				holder.bmitext = (TextView) convertView
						.findViewById(R.id.bmitext);
				holder.cloudtext = (TextView) convertView
						.findViewById(R.id.cloudtext);
				holder.bodytext = (TextView) convertView
						.findViewById(R.id.bodytext);
				holder.bonetext = (TextView) convertView
						.findViewById(R.id.bonetext);
				holder.watertext = (TextView) convertView
						.findViewById(R.id.watertext);
				holder.dd = (ImageView) convertView.findViewById(R.id.dd);
				holder.hiddenlayout = (LinearLayout) convertView
						.findViewById(R.id.hiddenlayout);
				holder.bmitext.setText(bmi.get(position));
				holder.cloudtext.setText(cloud.get(position));
				holder.watertext.setText(water.get(position));
				holder.bodytext.setText(body.get(position));
				holder.bonetext.setText(bone.get(position));
				break;
			case TYPE_SEPARATOR:
				convertView = mInflater.inflate(R.layout.blue_header_listitem,
						null);
				holder.textView = (TextView) convertView
						.findViewById(R.id.textSeparator);
				break;
			}
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		switch (rowType) {
		case TYPE_ITEM:
			holder.dd.setTag(position);
			holder.dd.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					int pos = (Integer) v.getTag();
					if (lastClickPos > -1 && lastClickPos != pos) {
						weightRows[lastClickPos] = false;
					}
					weightRows[pos] = !weightRows[pos];
					Log.e("WEIGHTROWS", weightRows[pos] + "" + pos);
					lastClickPos = pos;
					notifyDataSetChanged();
					// finalHolder1.dd.getTag();
					// finalHolder1.hiddenlayout.setVisibility(View.VISIBLE);
				}
			});
			Log.e("WEIGHTROWS_OUT", weightRows[pos] + "" + pos);
			if (weightRows[position]) {
				holder.plus.setText("-");
				holder.hiddenlayout.setVisibility(View.VISIBLE);
			} else {
				holder.plus.setText("+");
				holder.hiddenlayout.setVisibility(View.GONE);
			}
			holder.textView.setText(mData.get(position));
			break;

		case TYPE_SEPARATOR:
			holder.textView.setText(mData.get(position));
			break;
		}

		return convertView;
	}

	/*
	 * private View.OnClickListener mOnTitleClickListener = new
	 * View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { final int position =
	 * mListView.getPositionForView((View) v.getParent());
	 * System.out.println(position);
	 * 
	 * } };
	 * 
	 * private View.OnClickListener mOnTextClickListener = new
	 * View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { final int position =
	 * mListView.getPositionForView((View) v.getParent());
	 * System.out.println(position); } };
	 */

	public static class ViewHolder {
		public TextView textView, bmitext, bonetext, bodytext, watertext,
				cloudtext, plus;
		public ImageView dd;
		public LinearLayout hiddenlayout;

	}

	class WeightEditClick implements View.OnClickListener {
		LinearLayout hiddenLayout;
		int sposition = -1;

		public WeightEditClick(LinearLayout hiddenLayout, int pos) {
			this.hiddenLayout = hiddenLayout;
			this.sposition = pos;
		}

		@Override
		public void onClick(View v) {
			// if (weightRows[])
		}
	}

}