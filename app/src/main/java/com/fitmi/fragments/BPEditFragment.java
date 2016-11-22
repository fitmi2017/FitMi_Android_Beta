package com.fitmi.fragments;


import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.db.modules.UserInfoModule;
import com.fitmi.R;

public class BPEditFragment extends BaseFragment{

	int mRootId = 0;

	@InjectView(R.id.Heading)
	public TextView heading;

	@InjectView(R.id.Back)
	public ImageView back; 
	
	@InjectView(R.id.backLiner)
	LinearLayout backLiner;

	@InjectView(R.id.Settings)
	public ImageView settings;

	@InjectView(R.id.edit_sys)
	EditText edit_sys;
	
	@InjectView(R.id.edit_dia)
	EditText edit_dia;

	UserInfoModule userDb;

	ArrayList<String> bpData;



	String syaValue = "";
	String diaValue = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/* Inflate the layout for this fragment */
		View view = inflater.inflate(R.layout.fragment_bp_edit, container, false);

	
		Bundle bundle = this.getArguments();
		if (bundle != null) {
			mRootId = bundle.getInt("root_id", R.id.root_home_frame);
		}		

		ButterKnife.inject(this, view);
		setNullClickListener(view);
		userDb = new UserInfoModule(getActivity());

	

		bpData = userDb.getBloodPressure();

		edit_sys.setText(bpData.get(0));
		edit_dia.setText(bpData.get(1));

		heading.setText("Settings");
		settings.setVisibility(View.GONE);
		
		return view;
	}

	@OnClick(R.id.backLiner)
	public void back() {

		InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		in.hideSoftInputFromWindow(edit_sys.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
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
	public void saveBP()
	{
		
		int _dia=0;
		int _sys=0;
		if(!edit_dia.getText().toString().equalsIgnoreCase("") && !edit_sys.getText().toString().equalsIgnoreCase(""))
		{
			 _dia=Integer.parseInt(edit_dia.getText().toString());
			_sys=Integer.parseInt(edit_sys.getText().toString());
			
			
		}
		if(_dia>_sys){
			Toast.makeText(getActivity(), "Enter your sys value should be less than dia value", Toast.LENGTH_LONG).show();
			return;
			
		}
		syaValue = edit_sys.getText().toString();		
		diaValue = edit_dia.getText().toString();		
		userDb.updateBloodPressure(syaValue, diaValue); 

		Toast.makeText(getActivity(), "Bp updated", Toast.LENGTH_LONG).show();
	}



}
