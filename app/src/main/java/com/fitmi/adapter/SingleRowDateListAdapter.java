package com.fitmi.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.fitmi.R;
import com.fitmi.dao.UserInfoDAO;

public class SingleRowDateListAdapter extends BaseAdapter {

	Context context;
	ArrayList<String> dataList;

	public SingleRowDateListAdapter(Context context, ArrayList<String> dataList) {
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
		
		@InjectView(R.id.imageView1)
		ImageView imageView1;

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
			
			view = inflater.inflate(R.layout.item_setting, null);

			holder = new ViewHolder(view);

			view.setTag(holder);

		} else {

			holder = (ViewHolder) view.getTag();

		}

		holder.userName.setText(dataList.get(arg0));
		
		holder.imageView1.setVisibility(View.GONE);

		return view;
	}

}
