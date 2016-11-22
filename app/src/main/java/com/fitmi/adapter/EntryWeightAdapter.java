package com.fitmi.adapter;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.callback.BloodPressureChangeNotification;
import com.callback.Item;
import com.callback.WeightChangeNotification;
import com.db.modules.BpLogModule;
import com.db.modules.WeightLogModule;
import com.fitmi.R;
import com.fitmi.adapter.PressureAdapter.ViewHolder;
import com.fitmi.dao.BloodPressureDAO;
import com.fitmi.dao.BloodPressureSet;
import com.fitmi.dao.EntryItem;
import com.fitmi.dao.EntryItemWeight;
import com.fitmi.dao.SectionItem;
import com.fitmi.dao.WeightSet;
import com.fitmi.fragments.BloodPressureFragment;
import com.fitmi.fragments.WeightFragment;
import com.fitmi.utils.Constants;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class EntryWeightAdapter extends ArrayAdapter<Item> {

	private Context context;
	private ArrayList<Item> items;
	private LayoutInflater vi;
	ListView pressurelv;
	boolean[] weightRows;
	int lastClickPos = -1;

	public EntryWeightAdapter(Context context,ArrayList<Item> items,ListView pressurelv) {
		super(context,0, items);
		this.context = context;
		this.items = items;
		this.pressurelv = pressurelv;		
		vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public void setStatusRows() {
		weightRows = new boolean[items.size()];
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		//ViewHolder holder = null;
		final Item i = items.get(position);
		if (i != null) {
			if(i.isSection()){
				SectionItem si = (SectionItem)i;
				v = vi.inflate(R.layout.blue_header_headertem, null);
				v.setBackgroundColor(Color.parseColor("#31A4C4"));	
				v.setOnClickListener(null);
				v.setOnLongClickListener(null);
				v.setLongClickable(false);

				TextView sectionView = (TextView) v.findViewById(R.id.textSeparator);
				sectionView.setText(si.getTitle());

			}else{
				EntryItemWeight ei = (EntryItemWeight)i;
				v = vi.inflate(R.layout.weight_listitem, null);				

			
				
				
				TextView textViewTime = (TextView) v.findViewById(R.id.textTime);
				TextView varietynumber = (TextView)v.findViewById(R.id.varietynumber);
				TextView variety = (TextView)v.findViewById(R.id.variety);
				
				
				
				TextView plus = (TextView) v.findViewById(R.id.plus);
				TextView bmitext = (TextView) v.findViewById(R.id.bmitext);
				TextView cloudtext = (TextView) v.findViewById(R.id.cloudtext);
				TextView bodytext = (TextView) v.findViewById(R.id.bodytext);
				TextView bonetext = (TextView) v.findViewById(R.id.bonetext);
				TextView watertext = (TextView) v.findViewById(R.id.watertext);
				ImageView dd = (ImageView) v.findViewById(R.id.dd);
				LinearLayout hiddenlayout = (LinearLayout) v.findViewById(R.id.hiddenlayout);
				
				dd.setTag(ei.getId());
				dd.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String id = (String) v.getTag();
						dialog(id);
					}
				});
				
				plus.setTag(position);
				plus.setOnClickListener(new OnClickListener() {
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
				
				if (weightRows[position]) {
					plus.setText("-");
					hiddenlayout.setVisibility(View.VISIBLE);
				} else {
					plus.setText("+");
					hiddenlayout.setVisibility(View.GONE);
				}
				//textView.setText(mData.get(position));
				
				
				
				/*bmitext.setText(bmi.get(position));
				cloudtext.setText(cloud.get(position));
				watertext.setText(water.get(position));
				bodytext.setText(body.get(position));
				bonetext.setText(bone.get(position));*/
				
				textViewTime.setText(ei.getTime());
				
				//avinash for unit
				
				
			//	varietynumber.setText(ei.getWeight());
				
				if(Constants.gunitwt==0){
					varietynumber.setText(ei.getWeight());
					variety.setText("Kg");
					}else{
						double wt,f_wt;
						wt=Double.parseDouble(ei.getWeight());
						f_wt=wt*2.2046;
						
						Log.e(" Getting value in lbs ", String.valueOf(f_wt));
						
						DecimalFormat formatter = new DecimalFormat("##.##");
						String yourFormattedString = formatter.format(f_wt);
					//	dataList.get(arg0).getMealWeight()
						Log.e(" Getting value in lbs ", String.valueOf(yourFormattedString));
						varietynumber.setText(yourFormattedString);
						variety.setText("lbs");
					}
				
				v.setTag(ei.getId());
				
				
				
				v.setTag(ei.getId());
			}
		}
		return v;
	}
	
	
	public static class ViewHolder {
		public TextView textView, hearttext, systext, diatext;
		public ImageView dd;
		public LinearLayout hiddenlayout;

	}
	
	
	/**
	 *   Dialog to edit blood pressure 
	 */
	
	public void dialog(final String id)
	{
		final WeightLogModule weightModule = new WeightLogModule(context);
		
		final Dialog dialog=new Dialog(context/*,R.style.Theme_Dialog*/);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_enter_blood_pressure);
		final EditText sysValue = (EditText)dialog.findViewById(R.id.editSys);	
		final EditText diaValue = (EditText)dialog.findViewById(R.id.editDia);	
		diaValue.setVisibility(View.GONE);
		
		TextView dialogTitle = (TextView)dialog.findViewById(R.id.dialogTitle);
		dialogTitle.setText("Edit your blood presure");
		
		WeightSet editValue = weightModule.getEditValue(id);
		
		sysValue.setText(editValue.getWeight());
		sysValue.setOnFocusChangeListener(new OnFocusChangeListener() {

	        public void onFocusChange(View v, boolean hasFocus) {
	            if (hasFocus == true){
	            	
	                InputMethodManager inputMgr = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
	                inputMgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
	                inputMgr.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);

	            }
	        }
	    });
	sysValue.requestFocus();

		dialog.findViewById(R.id.savebtn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(sysValue.getText().toString().equalsIgnoreCase(""))
				{
					Toast.makeText(context, "Enter your weight value", Toast.LENGTH_LONG).show();
					return;
				}
				
				/*if(diaValue.getText().toString().equalsIgnoreCase(""))
				{
					Toast.makeText(context, "Enter your dia value", Toast.LENGTH_LONG).show();
					return;
				}*/
				
				String weigth = sysValue.getText().toString();
								
				
				weightModule.editWeightInformation(weigth, id);	
				WeightFragment object = new WeightFragment();;
				
				WeightChangeNotification callBack = (WeightChangeNotification) object;
				callBack.changeWeight(weightModule,context,pressurelv);
				dialog.dismiss();
				
			}
		});
		dialog.findViewById(R.id.cancelbtn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		dialog.show();
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
		dialog.getWindow().setLayout((6 * width)/7, LayoutParams.WRAP_CONTENT);

	}
}
