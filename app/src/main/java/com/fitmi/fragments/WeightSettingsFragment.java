package com.fitmi.fragments;

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

public class WeightSettingsFragment extends BaseFragment {

	int mRootId = 0;

	@InjectView(R.id.Heading)
	public TextView heading;

	@InjectView(R.id.Back)
	public ImageView back;
	
	@InjectView(R.id.backLiner)
	LinearLayout backLiner;

	@InjectView(R.id.Settings)
	public ImageView settings;
	
	@InjectView(R.id.txtViweightUpdate)
	EditText txtViweightUpdate;
	
	UserInfoModule userDb;
	
	String weight = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/* Inflate the layout for this fragment */
		View view = inflater
				.inflate(R.layout.fragment_weight_settings, container, false);

		Bundle bundle = this.getArguments();
		if (bundle != null) {
			mRootId = bundle.getInt("root_id", R.id.root_home_frame);
		}

		ButterKnife.inject(this, view);
		setNullClickListener(view);
		
		userDb = new UserInfoModule(getActivity());
		
		weight = userDb.getWeight();
		
		txtViweightUpdate.setText(weight);

		heading.setText("Settings");
		settings.setVisibility(View.GONE);

		return view;
	}

	@OnClick(R.id.backLiner)
	public void back() {

		InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		in.hideSoftInputFromWindow(txtViweightUpdate.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
		getActivity().onBackPressed();

	}

	@OnClick(R.id.Cancel_Weight)
	public void cancel() {

		getActivity().onBackPressed();

	}

	@OnClick(R.id.Base_Header)
	public void clickHeaderBase() {

	}
	
	@OnClick(R.id.Save_UserInfo)
	
	public void saveNewWeight()
	{
		weight = txtViweightUpdate.getText().toString();		
		userDb.updateWeight(weight);
		
		Toast.makeText(getActivity(), "Weight updated", Toast.LENGTH_LONG).show();
	}

}
