package com.fitmi.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
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
import com.callback.WaterChangeNotification;
import com.db.modules.BpLogModule;
import com.db.modules.WaterModule;
import com.fitmi.R;
import com.fitmi.dao.BloodPressureSet;
import com.fitmi.dao.WaterLogDAO;
import com.fitmi.fragments.BloodPressureFragment;
import com.fitmi.fragments.WaterFragment;

import java.util.ArrayList;
import java.util.HashMap;


public class WaterAdapter extends BaseAdapter {
	private Context context;
	private Activity activity;
	private ArrayList<WaterLogDAO> data;

	RelativeLayout rl;
	ImageView imagebg;

	private static LayoutInflater inflater=null;
	ListView pressurelv;
	TextView txtTotal;

	public WaterAdapter(Context context, ArrayList<WaterLogDAO> waterList,ListView pressurelv,TextView txtTotal)
	{

		this.context = context;
		data=waterList;
		this.pressurelv = pressurelv;
		this.txtTotal = txtTotal;
		activity= (Activity)context;

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
			vi = inflater.inflate(R.layout.waterlistrow, null);

		//System.out.println("coupon adapterget viwe called"); 
		final TextView time = (TextView)vi.findViewById(R.id.time); // title
		time.setText(data.get(position).getLogTime());
		final TextView waternum = (TextView)vi.findViewById(R.id.waternum); // title
		waternum.setText(data.get(position).getOzValue());
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
	
	@SuppressWarnings("deprecation")
	public void dialog(final String id)
	{
		final WaterModule waterModule = new WaterModule(context);
		
		/*final Dialog dialog=new Dialog(context,R.style.Theme_Dialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		dialog.setContentView(R.layout.dialog_enter_blood_pressure);
		final EditText sysValue = (EditText)dialog.findViewById(R.id.editSys);	
		final EditText diaValue = (EditText)dialog.findViewById(R.id.editDia);	
		diaValue.setVisibility(View.GONE);
		
		TextView dialogTitle = (TextView)dialog.findViewById(R.id.dialogTitle);
		dialogTitle.setText("Edit your water oz");*/
		
		
		 final AlertDialog alertDialog = new AlertDialog.Builder(context,AlertDialog.THEME_HOLO_DARK).create();
		// alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
			// alertDialog.setTitle("Fitmi");
		//	 alertDialog.setIcon(R.drawable.app_icon);
		 alertDialog.setMessage("Edit your water oz");
		 
		 final EditText sysValue = new EditText(context);
		 
		 sysValue.setInputType(InputType.TYPE_CLASS_NUMBER);
		 sysValue.setBackgroundColor(context.getResources().getColor(R.color.black_dialbg));
		 sysValue.setHint("Enter value");
		 sysValue.setTextColor(context.getResources().getColor(R.color.white));
		
		// newCalory.setTextAppearance(getActivity(), R.style.UserInfo_InputBoxStyleBlack);
	         
	
	         
		
		WaterLogDAO editValue = waterModule.getEditValue(id);		
		sysValue.setText(editValue.getOzValue());
		
		
		
		alertDialog.setView(sysValue);
	

	      alertDialog.setButton("Save", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	            
	            	
				
				if(sysValue.getText().toString().equalsIgnoreCase(""))
				{
					Toast.makeText(context, "Enter your oz value", Toast.LENGTH_LONG).show();
					return;
				}
				
				String sysvalue = sysValue.getText().toString();
				
				
				waterModule.editWaterInformation(sysvalue, id);					
				WaterFragment object = new WaterFragment();;
				
				WaterChangeNotification callBack = (WaterChangeNotification) object;
				callBack.changeWater(waterModule,context,pressurelv,txtTotal);
				
				View view = activity.getCurrentFocus();
				if (view != null) {  
				    InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
				    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
				}
				 alertDialog.dismiss();
				
			}
		});
		     alertDialog.setButton2("Cancel",new DialogInterface.OnClickListener() {
		         @Override
		         public void onClick(DialogInterface dialog, int which) {
		        	 View view = activity.getCurrentFocus();
		     		if (view != null) {  
		     		    InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		     		    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		     		}
		        	 alertDialog.dismiss();
			}
		});
		
		alertDialog.show();
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
		alertDialog.getWindow().setLayout((6 * width)/7, LayoutParams.WRAP_CONTENT);
		
	/*	alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE  | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);*/
		sysValue.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		

	}

}