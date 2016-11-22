package com.fitmi.adapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.callback.ActivityTimeUpdate;
import com.db.DatabaseHelper;
import com.db.modules.ActivityModule;
import com.db.modules.FoodLoginModule;
import com.fitmi.R;
import com.fitmi.activitys.BaseActivity;
import com.fitmi.adapter.FoodLoggingAdapter.LocalClass;
import com.fitmi.dao.FitmiFoodLogDAO;
import com.fitmi.fragments.FoodLoggingFragment;
import com.fitmi.fragments.MyActivityFragment;
import com.fitmi.utils.NotifyCalorieChange;

public class ActivitiesAdapter extends BaseAdapter {

	Context context;
	ArrayList<HashMap<String, String>> dataList;

	String time = "";

	DatabaseHelper databaseObject;
	TextView totalmintxt;

	public ActivitiesAdapter(Context context, ArrayList<HashMap<String, String>> dataList,TextView totalmintxt) {
		// TODO Auto-generated constructor stub

		this.context = context;
		this.dataList = dataList;
		
		this.totalmintxt = totalmintxt;

		databaseObject = new DatabaseHelper(context);
		try {

			databaseObject.createDatabase();

			databaseObject.openDatabase();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

		@InjectView(R.id.ActivityName_FoodLoggingListItem)
		TextView activityName;
		@InjectView(R.id.exercisetime)
		TextView exercisetime;
		@InjectView(R.id.cal_burned)
		TextView cal_burned;
		@InjectView(R.id.description)
		TextView description;

		@InjectView(R.id.Activity_ListItemParentLinear)
		LinearLayout activity_ListItemParentLinear;

		@InjectView(R.id.imageViewEdit)
		ImageView imageViewEdit;

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
			view = inflater.inflate(R.layout.activity_listitem, null);

			holder = new ViewHolder(view);

			view.setTag(holder);

		} else {

			holder = (ViewHolder) view.getTag();

		}
		//System.out.println(dataList.get(arg0).get(BaseActivity.exercise_name));
		holder.activityName.setText(dataList.get(arg0).get(BaseActivity.exercise_name));
		holder.cal_burned.setText(dataList.get(arg0).get(BaseActivity.calories_burned)+" cal");
		holder.exercisetime.setText(dataList.get(arg0).get(BaseActivity.total_time_minutes)+" min");
		holder.description.setText(dataList.get(arg0).get(BaseActivity.description));


		LocalClass local = new LocalClass();

		local.tagObject = dataList.get(arg0);
		local.position = arg0;
		local.timeTxt = holder.exercisetime;

		if (MyActivityFragment.sACTIVITY_POS == arg0) {

			holder.activity_ListItemParentLinear
			.setBackgroundResource(R.color.home_pink_select_light);

		} else {

			holder.activity_ListItemParentLinear
			.setBackgroundResource(R.color.white);

		}

		holder.imageViewEdit.setTag(local);
		holder.imageViewEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub

				LocalClass local = (LocalClass)view.getTag(); 

				//FitmiFoodLogDAO tagObject = local.tagObject; 

				dialog(local);

			}
		});


		return view;
	}

	class LocalClass
	{
		HashMap<String, String> tagObject;
		int position;
		TextView timeTxt;
	}


	public void dialog(LocalClass local)
	{
		//final FitmiFoodLogDAO tagLocalObject = local.tagObject; 

		final int position = local.position;

		final Dialog dialog=new Dialog(context/*,R.style.Theme_Dialog*/);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_edit_calory);
		final EditText newCalory = (EditText)dialog.findViewById(R.id.newCalory);	
		
		String minute = local.timeTxt.getText().toString();
		
		String[] parts = minute.split("m");
		minute =parts[0];
		
		newCalory.setText(minute.trim());

		TextView dialogTitle = (TextView)dialog.findViewById(R.id.dialogTitle);
		dialogTitle.setText("Edit time (minute)");

		dialog.findViewById(R.id.savebtn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if(newCalory.getText().toString().equalsIgnoreCase(""))
				{
					Toast.makeText(context, "Enter time", Toast.LENGTH_LONG).show();
					return;
				}

				time = newCalory.getText().toString();

				ActivityModule.editActivityTime(time,databaseObject,(position+1));
				//editCalory(tagLocalObject, databaseObject, calory);			

				dataList.get(position).put(BaseActivity.total_time_minutes, time);

				notifyDataSetChanged();

				MyActivityFragment myActivity = new MyActivityFragment();
				
				ActivityTimeUpdate callBack = (ActivityTimeUpdate) myActivity;
				
				callBack.timeUpdate(totalmintxt, dataList);

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