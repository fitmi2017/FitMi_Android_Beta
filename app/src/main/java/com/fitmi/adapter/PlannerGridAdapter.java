package com.fitmi.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import com.db.modules.FoodLoginModule;
import com.dialog.Alert;
import com.fitmi.R;
import com.fitmi.adapter.PlannerAdapter.Tag;
import com.fitmi.adapter.PlannerAdapter.ViewHolder;
import com.fitmi.dao.FitmiFoodLogDAO;
import com.fitmi.dao.FoodActivityActive;
import com.fitmi.dao.MealTypeDAO;
import com.fitmi.dao.PlannerWeeklyDAO;
import com.fitmi.fragments.FoodLoggingFragment;
import com.fitmi.fragments.MyActivityFragment;
import com.fitmi.fragments.PlannerFragment;
import com.fitmi.utils.Constants;
import com.fitmi.utils.DateModule;

import java.util.ArrayList;
import java.util.HashMap;


public class PlannerGridAdapter extends BaseAdapter {
	private Activity activity;
	private ArrayList<FoodActivityActive> data;
	RelativeLayout rl;
	ImageView imagebg;

	FragmentTransaction transaction;

	private static LayoutInflater inflater=null;

	boolean activityActive =false,lunch = false;
	public static HashMap<String, Integer> brekMapMonthly = new HashMap<String, Integer>();
	
	public static HashMap<String, Integer> activityMapMonthly = new HashMap<String, Integer>();

	public PlannerGridAdapter(Activity a, ArrayList<FoodActivityActive> d)
	{

		activity = a;
		data=d;

		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

		ViewHolder holder;
		View vi=convertView;
		if(convertView==null)
		{
			holder = new ViewHolder();

			Context context = parent.getContext();		

			vi = inflater.inflate(R.layout.plannergridrow, null);
			
			/*holder.centerid=(LinearLayout)vi.findViewById(R.id.centerid);*/
			holder.centerid=(LinearLayout)vi.findViewById(R.id.linlay_id);
			holder.plannergridrowid=(LinearLayout)vi.findViewById(R.id.plannergridrowid);

			holder.date = (TextView)vi.findViewById(R.id.textnum); // title
		//	holder.foodLogTake = (ImageView)vi.findViewById(R.id.foodLogTake);
		//	holder.activityLogTake = (ImageView)vi.findViewById(R.id.activityLogTake);			


			vi.setTag(holder);
		}else
		{
			holder = (ViewHolder) vi.getTag();
		}

		//System.out.println("coupon adapterget viwe called");
		System.out.println("coupon adapterget gridItemCliclEnable "+PlannerFragment.gridItemCliclEnable +" PlannerFragment.activityClickEnablegrid  "+PlannerFragment.activityClickEnablegrid);
		
		if(PlannerFragment.gridItemCliclEnable){

			if(PlannerFragment.activityClickEnablegrid){

				vi.setEnabled(false);
				holder.date.setEnabled(false);
				holder.centerid.setEnabled(false);
				holder.plannergridrowid.setEnabled(false);
	//			holder.foodLogTake.setEnabled(false);
		//		holder.activityLogTake.setEnabled(true);

				vi.setClickable(false);
				holder.centerid.setClickable(false);
				holder.plannergridrowid.setClickable(false);
				holder.date.setClickable(false);
			//	holder.foodLogTake.setClickable(false);
		//		holder.activityLogTake.setClickable(true);

			}else{

				vi.setEnabled(true);
				holder.date.setEnabled(true);
				holder.centerid.setEnabled(true);
				holder.plannergridrowid.setEnabled(true);
		//		holder.foodLogTake.setEnabled(true);
			//	holder.activityLogTake.setEnabled(false);

				vi.setClickable(true);
				holder.centerid.setClickable(true);
				holder.plannergridrowid.setClickable(true);
				holder.date.setClickable(true);
			//	holder.foodLogTake.setClickable(true);
			//	holder.activityLogTake.setClickable(false);
			}

		}else{

			vi.setEnabled(false);
			holder.date.setEnabled(false);
			holder.centerid.setEnabled(false);
			holder.plannergridrowid.setEnabled(false);
		//	holder.foodLogTake.setEnabled(false);
		//	holder.activityLogTake.setEnabled(false);

			vi.setClickable(false);
			holder.centerid.setClickable(false);
			holder.plannergridrowid.setClickable(false);
			holder.date.setClickable(false);
		//	holder.foodLogTake.setClickable(false);
		//	holder.activityLogTake.setClickable(false);					
		}


		


		/*holder.foodLogTake.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("Click foodLogTake ", " foodLogTake Working");
				
				Tag tag = (Tag) v.getTag();

				if(tag.object.getBreakfastClick() == 0){

					tag.object.getBreakfast().setBackgroundResource(R.drawable.food2);
					tag.object.getBreakfast().setAlpha(1.0f);
					tag.object.setBreakfastClick(1);				
					//breakfatId = 1;					
					brekMapMonthly.put(tag.object.getDate(), 1);
					tag.object.setClickBreackfast(true);
					
				}else{
					tag.object.getBreakfast().setBackgroundResource(R.drawable.food1);
					tag.object.setBreakfastClick(0);		
					//breakfatId = -1;
					brekMapMonthly.remove(tag.object.getDate());
					tag.object.setClickBreackfast(false);
				}


			}
		});
*/
		/*holder.activityLogTake.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub				

				Tag tag = (Tag) v.getTag();

				if(tag.object.getActivityClick() == 0){

					tag.object.getActivityList().setBackgroundResource(R.drawable.activity_color);
					tag.object.getActivityList().setAlpha(1.0f);
					tag.object.setActivityClick(1);				
					//breakfatId = 1;					
					brekMapMonthly.put(tag.object.getDate(), 1);
					tag.object.setClickActivity(true);
					
				}else{
					tag.object.getActivityList().setBackgroundResource(R.drawable.activity);
					tag.object.setActivityClick(0);		
					//breakfatId = -1;
					brekMapMonthly.remove(tag.object.getDate());
					tag.object.setClickActivity(false);
				}
			}
		});*/


		if(position==0)
		{
			holder.plannergridrowid.setBackgroundColor(Color.parseColor("#47A4C4"));
			holder.centerid.setVisibility(View.INVISIBLE);
			holder.date.setText("");
		}
		else{
			
			FoodActivityActive object = data.get(position);

		//	object.setBreakfast(holder.foodLogTake);		
		//	object.setActivityList(holder.activityLogTake);

			Tag tag = new Tag();
			tag.position = position;
			//tag.date = data.get(position).getDateSet();
			tag.object = object;
			
			holder.plannergridrowid.setBackgroundColor(Color.parseColor("#ffffff"));
			holder.centerid.setVisibility(View.VISIBLE);
			holder.date.setText(position+"");

			if(object.isClickBreackfast()){
				
				object.getBreakfast().setBackgroundResource(R.drawable.food2);
				object.getBreakfast().setAlpha(1.0f);
				
			}else{
				
				if(data.get(position).getFoodActive() == 0){

			//		holder.foodLogTake.setBackgroundResource(R.drawable.food1);
			//		holder.foodLogTake.setAlpha(0.5f);	
					lunch = false;

				} else{
				//	holder.foodLogTake.setBackgroundResource(R.drawable.food1);
				//	holder.foodLogTake.setAlpha(1.0f);	
					lunch = true;
				}
			}
			
			

		//	holder.foodLogTake.setTag(tag);
			
			if(object.isClickActivity()){
				
				object.getActivityList().setBackgroundResource(R.drawable.activity_color);
				object.getBreakfast().setAlpha(1.0f);
				
			}else{
				
				if(data.get(position).getActivityActive() == 0){

			//		holder.activityLogTake.setBackgroundResource(R.drawable.activity);
			//		holder.activityLogTake.setAlpha(0.5f);	
					activityActive = false;

				} else{
				//	holder.activityLogTake.setBackgroundResource(R.drawable.activity);	
			//		holder.activityLogTake.setAlpha(1.0f);	
					activityActive = true;
				}

			}

			
			//holder.activityLogTake.setTag(tag);
		}


		return vi;
	}

	class ViewHolder
	{
		TextView date;
		//ImageView foodLogTake;
		//ImageView activityLogTake;
		LinearLayout centerid;
		LinearLayout plannergridrowid;
		
		LinearLayout linlay_one;
		LinearLayout linlay_two;
	}

	/*public void plannerFoodAdd(FragmentTransaction transaction)
	{
		if(Constants.PROFILE_ID !=null && Constants.PROFILE_ID.equalsIgnoreCase("")){

			Alert.showAlert(activity, "Kindly create at least one user to access the App");
			return;
		}


		transaction.add(R.id.root_planner_frame, new FoodLoggingFragment(),
				"FoodLoggingFragment");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();		
	}

	public void plannerActivityAdd(FragmentTransaction transaction)
	{
		if(Constants.PROFILE_ID !=null && Constants.PROFILE_ID.equalsIgnoreCase("")){

			Alert.showAlert(activity, "Kindly create at least one user to access the App");
			return;
		}


		transaction.add(R.id.root_planner_frame, new MyActivityFragment(),
				"MyActivityFragment");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();

	}*/

	class Tag
	{
		int position=0;
		String date = "";
		FoodActivityActive object;		

	}
	
	
	

}