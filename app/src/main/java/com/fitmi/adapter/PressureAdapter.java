package com.fitmi.adapter;

/**
 * Created by dts_user on 25/8/15.
 */

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

import java.util.ArrayList;
import java.util.TreeSet;

public class PressureAdapter extends BaseAdapter {

	private static final int TYPE_ITEM = 0;
	private static final int TYPE_SEPARATOR = 1;
	protected ListView mListView;
	private static int pos;
	int counter = 0;
	private ArrayList<String> mData = new ArrayList<String>();
	private ArrayList<String> sys = new ArrayList<String>();
	private ArrayList<String> dia = new ArrayList<String>();
	private ArrayList<String> heart = new ArrayList<String>();

	private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();

	boolean[] weightRows = null;
	int lastClickPos = -1;

	private LayoutInflater mInflater;

	public PressureAdapter(Context context) {
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	public void setStatusRows() {
		weightRows = new boolean[mData.size()];
	}

	public void addItem(final String item, String item1, String item2,
			String item3) {
		mData.add(item);
		sys.add(item1);
		dia.add(item2);
		heart.add(item3);

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
				convertView = mInflater.inflate(R.layout.pressurelistrow, null);
				holder.textView = (TextView) convertView
						.findViewById(R.id.texttime);
				holder.diatext = (TextView) convertView
						.findViewById(R.id.diatext);
				holder.systext = (TextView) convertView
						.findViewById(R.id.systext);
				holder.hearttext = (TextView) convertView
						.findViewById(R.id.hearttext);
				holder.dd = (ImageView) convertView.findViewById(R.id.dd);
				holder.systext.setText(sys.get(position));
				holder.diatext.setText(dia.get(position));
				holder.hearttext.setText(heart.get(position));

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

		holder.textView.setText(mData.get(position));

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
		public TextView textView, hearttext, systext, diatext;
		public ImageView dd;
		public LinearLayout hiddenlayout;

	}

}