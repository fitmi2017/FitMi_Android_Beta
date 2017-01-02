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
import com.fitmi.dao.DeviceListDAO;
import com.fitmi.utils.Constants;

public class TwoRowDataListAdapter extends BaseAdapter {

    ArrayList<DeviceListDAO> dataList;
    Context context;

    public TwoRowDataListAdapter(Context context, ArrayList<DeviceListDAO> dataList) {
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

        @InjectView(R.id.DataName)
        TextView dataName;
        @InjectView(R.id.device_image)
        ImageView device_image;

        @InjectView(R.id.sync_status)
        TextView sync_status;

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
            view = inflater.inflate(R.layout.device_list_item_data, null);

            holder = new ViewHolder(view);

            view.setTag(holder);

        } else {

            holder = (ViewHolder) view.getTag();

        }

        holder.dataName.setText(dataList.get(arg0).getDeviceName());

        if (dataList.get(arg0).getDeviceName().equalsIgnoreCase("Food Scale")) {
            holder.device_image.setImageResource(R.drawable.food1);
        } else if (dataList.get(arg0).getDeviceName().equalsIgnoreCase("Weight Scale")) {
            holder.device_image.setImageResource(R.drawable.fat_scale);
        } else {
            holder.device_image.setImageResource(R.drawable.pedometer);
        }

        holder.sync_status.setText(dataList.get(arg0).getSyncType());

        return view;
    }

}
