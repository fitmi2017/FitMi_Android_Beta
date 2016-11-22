package com.fitmi.adapter;

import java.util.ArrayList;

import com.callback.BloodPressureChangeNotification;
import com.callback.Item;
import com.db.modules.BpLogModule;
import com.fitmi.R;
import com.fitmi.adapter.PressureAdapter.ViewHolder;
import com.fitmi.dao.BloodPressureDAO;
import com.fitmi.dao.BloodPressureSet;
import com.fitmi.dao.EntryItem;
import com.fitmi.dao.SectionItem;
import com.fitmi.fragments.BloodPressureFragment;
import com.fitmi.utils.Constants;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.DisplayMetrics;
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

public class EntryAdapter extends ArrayAdapter<Item> {

	private Context context;
	private ArrayList<Item> items;
	private LayoutInflater vi;
	ListView pressurelv;

	public EntryAdapter(Context context,ArrayList<Item> items,ListView pressurelv) {
		super(context,0, items);
		this.context = context;
		this.items = items;
		this.pressurelv = pressurelv;
		vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
				EntryItem ei = (EntryItem)i;
				v = vi.inflate(R.layout.pressurelistrow, null);				

				TextView texttime = (TextView) v
						.findViewById(R.id.texttime);
				TextView diatext = (TextView) v
						.findViewById(R.id.diatext);
				TextView systext = (TextView) v
						.findViewById(R.id.systext);
				TextView hearttext = (TextView) v
						.findViewById(R.id.hearttext);
				ImageView dd = (ImageView) v.findViewById(R.id.dd);
				
				dd.setTag(ei.getId());
				dd.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View view) {
						// TODO Auto-generated method stub
						
						String id = (String) view.getTag();
						dialog(id);
					}
				});
				
				texttime.setText(ei.getTime());
				systext.setText(ei.getSys());
				diatext.setText(ei.getDia());
				hearttext.setText(ei.getHeartBeat());
				
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
		final BpLogModule bpModule = new BpLogModule(context);
		
		final Dialog dialog=new Dialog(context/*,R.style.Theme_Dialog*/);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_enter_blood_pressure);
		final EditText sysValue = (EditText)dialog.findViewById(R.id.editSys);	
		final EditText diaValue = (EditText)dialog.findViewById(R.id.editDia);	
		
		TextView dialogTitle = (TextView)dialog.findViewById(R.id.dialogTitle);
		dialogTitle.setText("Edit your blood presure");
		
		BloodPressureSet editValue = bpModule.getEditValue(id);
		
		sysValue.setText(editValue.getSys());
		diaValue.setText(editValue.getDia());
		
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
					Toast.makeText(context, "Enter your sys value", Toast.LENGTH_LONG).show();
					return;
				}
				
				if(diaValue.getText().toString().equalsIgnoreCase(""))
				{
					Toast.makeText(context, "Enter your dia value", Toast.LENGTH_LONG).show();
					return;
				}
				
				int _dia=0;
				int _sys=0;
				if(!diaValue.getText().toString().equalsIgnoreCase("") && !sysValue.getText().toString().equalsIgnoreCase(""))
				{
					 _dia=Integer.parseInt(diaValue.getText().toString());
					_sys=Integer.parseInt(sysValue.getText().toString());
					
					
				}
				if(_dia>_sys){
					Toast.makeText(context, "Enter your sys value should be less than dia value", Toast.LENGTH_LONG).show();
					return;
					
				}
				
				String sysvalue = sysValue.getText().toString();
				String diavalue = diaValue.getText().toString();				
				
				bpModule.editBpInformation(sysvalue, diavalue,id);					
				BloodPressureFragment object = new BloodPressureFragment();;
				
				BloodPressureChangeNotification callBack = (BloodPressureChangeNotification) object;
				callBack.changeBloodPressure(bpModule,context,pressurelv);
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
