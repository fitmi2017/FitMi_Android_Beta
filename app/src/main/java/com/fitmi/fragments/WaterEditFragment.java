package com.fitmi.fragments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.callback.SleepChangeNotification;
import com.db.modules.SleepModule;
import com.db.modules.UserInfoModule;
import com.db.modules.WaterModule;
import com.fitmi.R;
import com.fitmi.adapter.SleepAdapter;
import com.fitmi.adapter.WaterAdapter;
import com.fitmi.dao.SleepLogADO;
import com.fitmi.dao.WaterLogDAO;
import com.fitmi.utils.Constants;
import com.fitmi.utils.DateModule;

public class WaterEditFragment extends BaseFragment{

	int mRootId = 0;

	@InjectView(R.id.Heading)
	public TextView heading;

	@InjectView(R.id.Back)
	public ImageView back; 
	
	@InjectView(R.id.backLiner)
	LinearLayout backLiner;

	@InjectView(R.id.Settings)
	public ImageView settings;

	@InjectView(R.id.edit_water)
	EditText edit_water;

	UserInfoModule userDb;

	String water = "";



	String sleepValue = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/* Inflate the layout for this fragment */
		View view = inflater.inflate(R.layout.fragment_water_edit, container, false);

	
		Bundle bundle = this.getArguments();
		if (bundle != null) {
			mRootId = bundle.getInt("root_id", R.id.root_home_frame);
		}		

		ButterKnife.inject(this, view);
		setNullClickListener(view);
		userDb = new UserInfoModule(getActivity());

	

		water = userDb.getWater();

		edit_water.setText(water);

		heading.setText("Settings");
		settings.setVisibility(View.GONE);
		
		return view;
	}

	@OnClick(R.id.backLiner)
	public void back() {

		InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		in.hideSoftInputFromWindow(edit_water.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
		getActivity().onBackPressed();

	}

	@OnClick(R.id.Cancel_Sleep)
	public void cancel() {

		getActivity().onBackPressed();

	}

	@OnClick(R.id.Base_Header)
	public void clickHeaderBase() {

	}

	@OnClick(R.id.Save_UserInfo)
	public void saveSleep()
	{
		water = edit_water.getText().toString();		
		userDb.updateWater(water);

		getActivity().onBackPressed();
		//Toast.makeText(getActivity(), "Water updated", Toast.LENGTH_LONG).show();
	}



}
