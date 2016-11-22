package com.fitmi.adapter;

/**
 * Created by dts_user on 25/8/15.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.TreeSet;

import com.fitmi.R;

public class ShareDataAdapter extends BaseAdapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;
    protected ListView mListView;
    private static int pos;
     int counter=0;
    private ArrayList<String> mData = new ArrayList<String>();
    int varpos=0,syspos=0;
    private ArrayList<String> sys = new ArrayList<String>();
    private ArrayList<String> dia = new ArrayList<String>();
    private ArrayList<String> heart = new ArrayList<String>();
    private ArrayList<String> variety = new ArrayList<String>();
    private ArrayList<String> varietynum = new ArrayList<String>();
    private static ArrayList<Integer> type = new ArrayList<Integer>();
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();

    boolean[] weightRows = null;
    int lastClickPos = -1;

    private LayoutInflater mInflater;

    public ShareDataAdapter(Context context,ArrayList<String> data1,ArrayList<String> data2,ArrayList<String> data3,ArrayList<Integer> data4,ArrayList<String> data5,ArrayList<String> data6) {
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sys=data1;
        dia=data2;
        heart=data3;
        type=data4;
        variety=data5;
        varietynum=data6;

    }

    public void setStatusRows(){
        weightRows =  new boolean[mData.size()];
    }


    @Override
    public int getItemViewType(int position) {
        return  type.get(position)==1 ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return type.size();
    }

    @Override
    public Integer getItem(int position) {
        return type.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        int rowType = getItemViewType(position);
        System.out.println("rowType="+rowType);
        if (convertView == null) {
            holder = new ViewHolder();
            switch (rowType) {
                case TYPE_SEPARATOR:
                    convertView = mInflater.inflate(R.layout.pressurelistrow, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.textTime);
                    holder.diatext = (TextView) convertView.findViewById(R.id.diatext);
                    holder.systext = (TextView) convertView.findViewById(R.id.systext);
                    holder.hearttext = (TextView) convertView.findViewById(R.id.hearttext);
                    holder.dd = (ImageView) convertView.findViewById(R.id.dd);
                    holder.systext.setText(sys.get(syspos));
                    holder.diatext.setText(dia.get(syspos));
                    holder.hearttext.setText(heart.get(syspos));
                    syspos++;

                    break;
                case TYPE_ITEM:
                    convertView = mInflater.inflate(R.layout.weight_listitem, null);
                    holder.varietytext = (TextView) convertView.findViewById(R.id.variety);
                    holder.varietynumtext = (TextView) convertView.findViewById(R.id.varietynumber);
                    holder.varietytext.setText(variety.get(varpos));
                    holder.varietynumtext.setText(varietynum.get(varpos));
                    varpos++;
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //holder.textView.setText(mData.get(position));

        return convertView;
    }

    /*private View.OnClickListener mOnTitleClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final int position = mListView.getPositionForView((View) v.getParent());
            System.out.println(position);

        }
    };

    private View.OnClickListener mOnTextClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final int position = mListView.getPositionForView((View) v.getParent());
            System.out.println(position);
        }
    };*/

    public static class ViewHolder {
        public TextView textView,hearttext,systext,diatext,varietynumtext,varietytext;
        public ImageView dd;
        public LinearLayout hiddenlayout;

    }



}