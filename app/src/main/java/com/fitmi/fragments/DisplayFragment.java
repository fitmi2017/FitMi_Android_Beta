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

public class DisplayFragment extends BaseFragment {

	int mRootId = 0;

	@InjectView(R.id.Heading)
	public TextView heading;

	@InjectView(R.id.Back)
	public ImageView back;
	
	@InjectView(R.id.backLiner)
	LinearLayout backLiner;

	@InjectView(R.id.Settings)
	public ImageView settings;

	@InjectView(R.id.ListView_Display)
	ListView displayListView;

	SingleRowDateListAdapter adapter;
	ArrayList<String> displaySettingsMenu;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/* Inflate the layout for this fragment */
		View view = inflater.inflate(R.layout.fragment_display, container,
				false);

		Bundle bundle = this.getArguments();
		if (bundle != null) {
			mRootId = bundle.getInt("root_id", R.id.root_home_frame);
		}

		ButterKnife.inject(this, view);
		setNullClickListener(view);
		
		heading.setText("Settings");
		settings.setVisibility(View.GONE);

		displaySettingsMenu = new ArrayList<String>();
		displaySettingsMenu.add("Home Page");
		displaySettingsMenu.add("Nutrition");
		displaySettingsMenu.add("Activity");
		displaySettingsMenu.add("Weight");
		displaySettingsMenu.add("Report");

		adapter = new SingleRowDateListAdapter(getActivity(),
				displaySettingsMenu);
		displayListView.setAdapter(adapter);

		displayListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				Bundle bundle = new Bundle();
				bundle.putInt("root_id", mRootId);

				FragmentTransaction transaction = getFragmentManager()
						.beginTransaction();

				if (arg2 == 0) {

				} else if (arg2 == 1) {

				} else if (arg2 == 2) {

				} else if (arg2 == 3) {

					WeightSettingsFragment fragment = new WeightSettingsFragment();
					fragment.setArguments(bundle);
					transaction.add(mRootId, fragment, "WeightFragment");

				} else if (arg2 == 4) {

				}

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
