package com.fitmi.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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

public class MyNotificationFragment extends BaseFragment {

	int mRootId = 0;

	@InjectView(R.id.Heading)
	public TextView heading;

	@InjectView(R.id.Back)
	public ImageView back;
	
	@InjectView(R.id.btn_back_pink)
	public Button btn_back_pink;
	
	@InjectView(R.id.backLiner)
	LinearLayout backLiner;

	@InjectView(R.id.Settings)
	public ImageView settings;

	@InjectView(R.id.ListView_Mynotification)
	ListView ListView_Mynotification;
	
	

	SingleRowDateListAdapter adapter;
	ArrayList<String> notificationSettingsMenu;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/* Inflate the layout for this fragment */
		View view = inflater.inflate(R.layout.fragment_mynotification, container, false);

		Bundle bundle = this.getArguments();
		if (bundle != null) {
			mRootId = bundle.getInt("root_id", R.id.root_home_frame);
		}

		ButterKnife.inject(this, view);
		setNullClickListener(view);
		heading.setText("Notifications");
		settings.setVisibility(View.GONE);

		notificationSettingsMenu = new ArrayList<String>();
		notificationSettingsMenu.add("Meals ");
		notificationSettingsMenu.add("Workouts ");
		notificationSettingsMenu.add("Weigh-Ins ");
		notificationSettingsMenu.add("Blood Pressure");
		notificationSettingsMenu.add("Sleep");
		notificationSettingsMenu.add("Water");
		

		adapter = new SingleRowDateListAdapter(getActivity(), notificationSettingsMenu);
		ListView_Mynotification.setAdapter(adapter);

		ListView_Mynotification.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				Bundle bundle = new Bundle();
				bundle.putInt("root_id", mRootId);

				FragmentTransaction transaction = getFragmentManager()
						.beginTransaction();

				if (arg2 == 0) {

					MealNotificationFragments fragment = new MealNotificationFragments();
					fragment.setArguments(bundle);
					//Constants.fromFragment=3;
					transaction.add(mRootId, fragment,
							"MealNotificationFragments");

				}else if (arg2 == 1) {


				}
				
				else if (arg2 == 2) {

			

				} else if (arg2 == 3) {

					

				}else if (arg2 == 4) {

					
				}
				else if (arg2 == 5) {

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
	
	@OnClick(R.id.btn_back_pink)
	public void back_pnk() {

		getActivity().onBackPressed();

	}

	@OnClick(R.id.Base_Header)
	public void clickHeaderBase() {

	}

}
