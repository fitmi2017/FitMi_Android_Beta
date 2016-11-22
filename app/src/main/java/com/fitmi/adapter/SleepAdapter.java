package com.fitmi.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;


import com.callback.BloodPressureChangeNotification;
import com.callback.SleepChangeNotification;
import com.callback.WaterChangeNotification;
import com.db.modules.BpLogModule;
import com.db.modules.SleepModule;
import com.db.modules.WaterModule;
import com.fitmi.R;
import com.fitmi.dao.BloodPressureSet;
import com.fitmi.dao.SleepLogADO;
import com.fitmi.dao.WaterLogDAO;
import com.fitmi.fragments.BloodPressureFragment;
import com.fitmi.fragments.SleepFragment;
import com.fitmi.fragments.WaterFragment;

import java.util.ArrayList;
import java.util.HashMap;


public class SleepAdapter extends BaseAdapter {
	private Context context;
	private Activity activity;
	private ArrayList<SleepLogADO> data;

	RelativeLayout rl;
	ImageView imagebg;

	private static LayoutInflater inflater=null;
	ListView pressurelv;
	TextView txtTotal;

	public SleepAdapter(Context context, ArrayList<SleepLogADO> waterList,ListView pressurelv,TextView txtTotal)
	{

		this.context = context;
		data=waterList;
		this.pressurelv = pressurelv;
		this.txtTotal = txtTotal;

		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//System.out.println("coupon adapter constructor called"); 
	}

	@Override
	public int getCount() {
		return data.size();
	}
	@Override
	public Object getItem(int position) {
		return position;
	}
	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		View vi=convertView;
		if(convertView==null)
			vi = inflater.inflate(R.layout.sleeplistrow, null);

		//System.out.println("coupon adapterget viwe called"); 
		final TextView time = (TextView)vi.findViewById(R.id.time); // title
		time.setText(data.get(position).getLogTime());
		final TextView waternum = (TextView)vi.findViewById(R.id.waternum); // title
		waternum.setText(data.get(position).getHours());
		final Button btnWaterEdit = (Button)vi.findViewById(R.id.btnWaterEdit);
		btnWaterEdit.setTag(data.get(position).getId());
		
		btnWaterEdit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String id = (String) v.getTag();
				dialog(id);
			}
		});

		return vi;
	}
	
	/**
	 *   Dialog to edit water pressure 
	 */
	
	public void dialog(final String id)
	{
		final SleepModule sleepModule = new SleepModule(context);
		
		final Dialog dialog=new Dialog(context/*,R.style.Theme_Dialog*/);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		dialog.setContentView(R.layout.dialog_enter_blood_pressure);
		final EditText sysValue = (EditText)dialog.findViewById(R.id.editSys);	
		final EditText diaValue = (EditText)dialog.findViewById(R.id.editDia);	
		diaValue.setVisibility(View.GONE);
		
		TextView dialogTitle = (TextView)dialog.findViewById(R.id.dialogTitle);
		dialogTitle.setText("Edit your sleep hour");
		
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(sysValue, InputMethodManager.SHOW_IMPLICIT);
		
		SleepLogADO editValue = sleepModule.getEditValue(id);		
		sysValue.setText(editValue.getHours());

		dialog.findViewById(R.id.savebtn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(sysValue.getText().toString().equalsIgnoreCase(""))
				{
					Toast.makeText(context, "Enter your sleep value", Toast.LENGTH_LONG).show();
					return;
				}
				
				String sysvalue = sysValue.getText().toString();
				
				
				sleepModule.editSleepInformation(sysvalue, id);					
				SleepFragment object = new SleepFragment();;
				
				SleepChangeNotification callBack = (SleepChangeNotification) object;
				callBack.changeSleep(sleepModule,context,pressurelv,txtTotal);
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