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

public class UserSettingFragment extends BaseFragment {

	int mRootId = 0;

	@InjectView(R.id.Heading)
	public TextView heading;

	@InjectView(R.id.Back)
	public ImageView back;
	
	@InjectView(R.id.backLiner)
	LinearLayout backLiner;

	@InjectView(R.id.Settings)
	public ImageView settings;

	@InjectView(R.id.layout_changepassword)
	RelativeLayout layout_changepassword;

	@InjectView(R.id.layout_editprofile)
	RelativeLayout layout_editprofile;

	Bundle bundle;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/* Inflate the layout for this fragment */
		View view = inflater.inflate(R.layout.user_setting, container, false);

		bundle = this.getArguments();
		if (bundle != null) {
			mRootId = bundle.getInt("root_id", R.id.root_home_frame);
		}

		ButterKnife.inject(this, view);
		setNullClickListener(view);

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

	@OnClick(R.id.layout_changepassword)
	public void clickChangePassword(){

		Intent intent = new Intent(getActivity(),
				ChangePasswordActivity.class);
		startActivity(intent);
	}

	@OnClick(R.id.layout_editprofile)
	public void clickEditprofile(){

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		UserInfoFragment fragment = new UserInfoFragment();
		fragment.setArguments(bundle);
		transaction.add(mRootId, fragment, "UserInfoFragment");

		transaction
		.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();
	}

}
