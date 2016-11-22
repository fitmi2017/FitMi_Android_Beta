package com.fitmi.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitmi.R;

public class RootHomeFragment extends BaseFragment {

	private static final String TAG = "RootHomeFragment";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/* Inflate the layout for this fragment */
		View view = inflater.inflate(R.layout.root_home_fragment, container,
				false);

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		getFragmentManager().popBackStack(null,
				FragmentManager.POP_BACK_STACK_INCLUSIVE);
		transaction.add(R.id.root_home_frame, new HomeFragment(),
				"HomeFragment");
		transaction.commit();

		return view;
	}

	public RootHomeFragment newInstance(Context context) {

		RootHomeFragment f = new RootHomeFragment();
		return f;

	}

}