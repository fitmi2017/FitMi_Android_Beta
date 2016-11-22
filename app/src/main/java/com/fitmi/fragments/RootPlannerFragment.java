package com.fitmi.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitmi.R;

public class RootPlannerFragment extends BaseFragment {

	private static final String TAG = "RootPlannerFragment";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/* Inflate the layout for this fragment */
		View view = inflater.inflate(R.layout.root_planner_fragment, container,
				false);

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		getFragmentManager().popBackStack(null,
				FragmentManager.POP_BACK_STACK_INCLUSIVE);
		transaction.add(R.id.root_planner_frame, new PlannerFragment(),
				"PlannerFragment");
		transaction.commit();

		return view;
		
	}

	public RootPlannerFragment newInstance(Context context) {

		RootPlannerFragment f = new RootPlannerFragment();
		return f;

	}

}
