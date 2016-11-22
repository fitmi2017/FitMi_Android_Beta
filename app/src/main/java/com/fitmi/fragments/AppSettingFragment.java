package com.fitmi.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.fitmi.R;
import com.fitmi.activitys.ChangePasswordActivity;
import com.fitmi.utils.Constants;

public class AppSettingFragment extends BaseFragment {

	int mRootId = 0;

	@InjectView(R.id.Heading)
	public TextView heading;

	@InjectView(R.id.Back)
	public ImageView back;
	
	@InjectView(R.id.backLiner)
	LinearLayout backLiner;

	@InjectView(R.id.Settings)
	public ImageView settings;	

	Bundle bundle;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/* Inflate the layout for this fragment */
		View view = inflater.inflate(R.layout.app_setting, container, false);
		

		bundle = this.getArguments();
		if (bundle != null) {
			mRootId = bundle.getInt("root_id", R.id.root_home_frame);
		}

		ButterKnife.inject(this, view);

		heading.setText("Settings");
		settings.setVisibility(View.GONE);

		return view;
	}

	@OnClick(R.id.backLiner)
	public void back() {

		getActivity().onBackPressed();

	}


	@OnClick(R.id.Base_Header)
	public void clickHeaderBase() {

	}

	

	@OnClick(R.id.layout_display)
	public void clickDisplay(){

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		DisplayFragment fragment = new DisplayFragment();
		fragment.setArguments(bundle);
		transaction.add(mRootId, fragment, "DisplayFragment");

		transaction
		.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();
	}

}
