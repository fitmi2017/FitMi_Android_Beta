package com.fitmi.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.callback.NotificationCalorieIntake;
import com.fitmi.dao.RememberMeData;
import com.db.modules.UserInfoModule;
import com.fitmi.R;
import com.fitmi.utils.Constants;
import com.fitmi.utils.SaveSharedPreferences;

public class DailyCalorieIntakeFragment extends BaseFragment {

	int mRootId = 0;

	@InjectView(R.id.Heading)
	public TextView heading;

	@InjectView(R.id.Back)
	public ImageView back;

	@InjectView(R.id.backLiner)
	LinearLayout backLiner;

	@InjectView(R.id.Settings)
	public ImageView settings;

	@InjectView(R.id.SwitchText_CalorieIntake)
	public TextView switchOn;

	@InjectView(R.id.DailyCalorieIntakeLinear_CalorieIntake)
	public LinearLayout dailyCalorieIntakeLinear;

	@InjectView(R.id.CalorieDetails_CalorieIntake)
	public TextView calorieDetailsText;

	UserInfoModule userDb;

	String caloryIntake = "";
	RememberMeData calValueShared;

	@InjectView(R.id.editTextCaloryUpdate)
	EditText editTextCaloryUpdate;

	@InjectView(R.id.txtCaloryTakeset)
	TextView txtCaloryTakeset;

	@InjectView(R.id.frameCalorySet)
	FrameLayout frameCalorySet;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/* Inflate the layout for this fragment */
		View view = inflater.inflate(R.layout.fragment_calorie_intake,
				container, false);

		Bundle bundle = this.getArguments();
		if (bundle != null) {
			mRootId = bundle.getInt("root_id", R.id.root_home_frame);
		}

		ButterKnife.inject(this, view);
		setNullClickListener(view);

		userDb = new UserInfoModule(getActivity());

		caloryIntake = userDb.getCaloryTake();

		txtCaloryTakeset.setText(caloryIntake);

	//	heading.setText("Settings");
		
		if(Constants.fromFragment==0)
		{
			heading.setText("Home");
		}else if(Constants.fromFragment==1)
		{
			heading.setText("My Meal");
		}else if(Constants.fromFragment==2)
		{
			heading.setText("Activity");
		}else{
			heading.setText("Settings");
		}
		
		settings.setVisibility(View.GONE);

		switchOn.setText("ON");
		dailyCalorieIntakeLinear.setVisibility(View.GONE);
		calorieDetailsText.setText(getResources().getString(
				R.string.daily_calorie_intake_on));

		
		
		try{
			calValueShared=SaveSharedPreferences.getCalorieDetail(getActivity());
			Log.e("calValueShared ",calValueShared.get_calorieOn());
			
			if(calValueShared.get_calorieOn().equalsIgnoreCase("0"))
			{
				switchOn.setText("OFF");
				dailyCalorieIntakeLinear.setVisibility(View.VISIBLE);
				frameCalorySet.setVisibility(View.GONE);
				calorieDetailsText.setText(getResources().getString(
						R.string.daily_calorie_intake_off));
				editTextCaloryUpdate.setText(caloryIntake);
				
			
			}else{
				switchOn.setText("ON");
				dailyCalorieIntakeLinear.setVisibility(View.GONE);
				frameCalorySet.setVisibility(View.VISIBLE);
				calorieDetailsText.setText(getResources().getString(
						R.string.daily_calorie_intake_on));
				
			}
			} catch (Exception a)
			{
				a.printStackTrace();
			}
		switchOn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (switchOn.getText().toString().equalsIgnoreCase("ON")) {

					switchOn.setText("OFF");
					dailyCalorieIntakeLinear.setVisibility(View.VISIBLE);
					frameCalorySet.setVisibility(View.GONE);
					calorieDetailsText.setText(getResources().getString(
							R.string.daily_calorie_intake_off));
					editTextCaloryUpdate.setText(caloryIntake);
					SaveSharedPreferences.saveCalorieDetail(getActivity(),Constants.USER_ID,"0");
				} else {

					switchOn.setText("ON");
					dailyCalorieIntakeLinear.setVisibility(View.GONE);
					frameCalorySet.setVisibility(View.VISIBLE);
					calorieDetailsText.setText(getResources().getString(
							R.string.daily_calorie_intake_on));
					SaveSharedPreferences.saveCalorieDetail(getActivity(),Constants.USER_ID,"1");
				}

			}
		});

		return view;

	}

	@OnClick(R.id.backLiner)
	public void back() {

		switch (Constants.fromFragment) {
		case 0:
			HomeFragment homeCalory = new HomeFragment();
			NotificationCalorieIntake homeCallback = (NotificationCalorieIntake) homeCalory;
			homeCallback.setNewCaloryIntake(caloryIntake);
			break;

		case 1:

			FoodLoggingFragment foodLogCalory = new FoodLoggingFragment();
			NotificationCalorieIntake foodLogCallback = (NotificationCalorieIntake) foodLogCalory;
			foodLogCallback.setNewCaloryIntake(caloryIntake);

			break;
		case 2:

			MyActivityFragment myActivityCalory = new MyActivityFragment();
			NotificationCalorieIntake myActivityCallback = (NotificationCalorieIntake) myActivityCalory;
			myActivityCallback.setNewCaloryIntake(caloryIntake);

			break;
		}
		View view = getActivity().getCurrentFocus();
		if (view != null) {  
		    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
		getActivity().onBackPressed();

	}

	@OnClick(R.id.Cancel_CalorieIntake)
	public void cancel() {

		getActivity().onBackPressed();

	}

	@OnClick(R.id.Base_Header)
	public void clickHeaderBase() {

	}

	@OnClick(R.id.Save_CalorieIntake)
	public void saveNewCalory() {
		// avinash
		try {

		
			if (switchOn.getText().toString().equalsIgnoreCase("ON")) {
				caloryIntake = userDb.getCaloryTake();

			} else {

			
				caloryIntake = editTextCaloryUpdate.getText().toString();
			}
				userDb.updateCaloryTake(caloryIntake);
			/*	Toast.makeText(getActivity(), "Calory updated",
						Toast.LENGTH_LONG).show();*/
				getActivity().onBackPressed();
			
		} catch (Exception a) {
			a.printStackTrace();
			
		}
	}

}