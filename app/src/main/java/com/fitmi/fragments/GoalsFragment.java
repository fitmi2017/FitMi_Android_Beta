package com.fitmi.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.fitmi.R;
import com.fitmi.adapter.SingleRowDateListAdapter;
import com.fitmi.utils.Constants;

public class GoalsFragment extends BaseFragment {

	int mRootId = 0;

	@InjectView(R.id.Heading)
	public TextView heading;

	@InjectView(R.id.Back)
	public ImageView back;
	
	@InjectView(R.id.backLiner)
	LinearLayout backLiner;

	@InjectView(R.id.Settings)
	public ImageView settings;

	@InjectView(R.id.ListView_Goals)
	ListView goalsListView;

	SingleRowDateListAdapter adapter;
	ArrayList<String> goalSettingsMenu;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/* Inflate the layout for this fragment */
		View view = inflater.inflate(R.layout.fragment_goals, container, false);

		Bundle bundle = this.getArguments();
		if (bundle != null) {
			mRootId = bundle.getInt("root_id", R.id.root_home_frame);
		}

		ButterKnife.inject(this, view);
		setNullClickListener(view);
		heading.setText("Settings");
		settings.setVisibility(View.GONE);

		goalSettingsMenu = new ArrayList<String>();
		goalSettingsMenu.add("Daily Calorie Intake");
		//goalSettingsMenu.add("Calorie to Burn");
		//goalSettingsMenu.add("Weight");
	//	goalSettingsMenu.add("Sleep");
		goalSettingsMenu.add("Water");
		//goalSettingsMenu.add("Blood Pressure");

		adapter = new SingleRowDateListAdapter(getActivity(), goalSettingsMenu);
		goalsListView.setAdapter(adapter);

		goalsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				Bundle bundle = new Bundle();
				bundle.putInt("root_id", mRootId);

				FragmentTransaction transaction = getFragmentManager()
						.beginTransaction();

				if (arg2 == 0) {

					DailyCalorieIntakeFragment fragment = new DailyCalorieIntakeFragment();
					fragment.setArguments(bundle);
					Constants.fromFragment=3;
					transaction.add(mRootId, fragment,
							"DailyCalorieIntakeFragment");

				}else if (arg2 == 1) {

					WaterEditFragment fragment = new WaterEditFragment();
					fragment.setArguments(bundle);
					transaction.add(mRootId, fragment, "WaterFragment");
				}
				
				//temp hidden by avinash
				/*else if (arg2 == 1) {

					CaloriesEditFragment fragment = new CaloriesEditFragment();
					fragment.setArguments(bundle);
					transaction.add(mRootId, fragment, "CaloriesBurnFragment");

				}
				
				else if (arg2 == 2) {

					WeightSettingsFragment fragment = new WeightSettingsFragment();
					fragment.setArguments(bundle);
					transaction.add(mRootId, fragment, "WeightFragment");

				} else if (arg2 == 3) {

					SleepEditFragment fragment = new SleepEditFragment();
					fragment.setArguments(bundle);
					transaction.add(mRootId, fragment, "SleepFragment");

				}else if (arg2 == 4) {

					WaterEditFragment fragment = new WaterEditFragment();
					fragment.setArguments(bundle);
					transaction.add(mRootId, fragment, "WaterFragment");
				}
				else if (arg2 == 5) {

					BPEditFragment fragment = new BPEditFragment();
					fragment.setArguments(bundle);
					transaction.add(mRootId, fragment, "BpFragment");
				}*/

				transaction
						.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				transaction.addToBackStack(null);
				transaction.commit();

			}
		});

		return view;
	}

	@OnClick(R.id.backLiner)
	public void back() {

		getActivity().onBackPressed();

	}

	@OnClick(R.id.Base_Header)
	public void clickHeaderBase() {

	}

}
