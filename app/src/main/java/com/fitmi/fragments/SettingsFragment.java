package com.fitmi.fragments;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.fitmi.R;
import com.fitmi.activitys.ChangePasswordActivity;
import com.fitmi.adapter.SingleRowDateListAdapter;
import com.fitmi.utils.Constants;
import com.fitmi.utils.SaveSharedPreferences;

public class SettingsFragment extends BaseFragment {

	int mRootId = 0;

	@InjectView(R.id.Heading)
	public TextView heading;

	@InjectView(R.id.Back)
	public ImageView back;
	
	@InjectView(R.id.backLiner)
	LinearLayout backLiner;

	@InjectView(R.id.Settings)
	public ImageView settings;

	@InjectView(R.id.ListView_Settings)
	ListView settingsListView;

	SingleRowDateListAdapter adapter;
	ArrayList<String> settingsMenu;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/* Inflate the layout for this fragment */
		View view = inflater.inflate(R.layout.fragment_settings, container,
				false);		
		

		Bundle bundle = this.getArguments();
		if (bundle != null) {
			mRootId = bundle.getInt("root_id", R.id.root_home_frame);
		}

		ButterKnife.inject(this, view);
		setNullClickListener(view);
		
		Constants.fragmentSet = false;

		heading.setText("Settings");
		settings.setVisibility(View.GONE);

		settingsMenu = new ArrayList<String>();
		settingsMenu.add("Change Password");
		settingsMenu.add("Edit Profile");
		//settingsMenu.add("User Settings");
		settingsMenu.add("Device Management");
		settingsMenu.add("Units");
		//settingsMenu.add("App Settings");
		settingsMenu.add("Goals");
		//settingsMenu.add("My Notification");

		adapter = new SingleRowDateListAdapter(getActivity(), settingsMenu);
		settingsListView.setAdapter(adapter);

		settingsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				Bundle bundle = new Bundle();
				bundle.putInt("root_id", mRootId);

				FragmentTransaction transaction = getFragmentManager()
						.beginTransaction();

				
				if (arg2 == 0) {
					
					Intent intent = new Intent(getActivity(),
							ChangePasswordActivity.class);
					startActivity(intent);

				} else if (arg2 == 1) {
					
					/*UserSettingFragment fragment = new UserSettingFragment();
					fragment.setArguments(bundle);
					transaction.add(mRootId, fragment, "UserSettingsFragment");*/
					
					
					UserInfoFragment fragment = new UserInfoFragment();
					fragment.setArguments(bundle);
					transaction.add(mRootId, fragment, "UserInfoFragment");

					/*transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
					transaction.addToBackStack(null);
					transaction.commit();*/

				} else if (arg2 == 2) {
					
					DeviceListFragment fragment = new DeviceListFragment();
					fragment.setArguments(bundle);
					transaction.add(mRootId, fragment, "DeviceListFragment");								
				

				} else if (arg2 == 3) {

					UnitsFragment fragment = new UnitsFragment();
					fragment.setArguments(bundle);
					transaction.add(mRootId, fragment, "UnitsFragment");

				} /*else if (arg2 == 4) {
					
					AppSettingFragment fragment = new AppSettingFragment();
					fragment.setArguments(bundle);
					transaction.add(mRootId, fragment, "DisplayFragment");

				}*/ else if (arg2 == 4) {
					
					GoalsFragment fragment = new GoalsFragment();
					fragment.setArguments(bundle);
					transaction.add(mRootId, fragment, "GoalsFragment");

				} else if (arg2 == 5) {

					MyNotificationFragment fragment = new MyNotificationFragment();
					fragment.setArguments(bundle);
					transaction.add(mRootId, fragment, "MyNotificationFragment");
					/*DisplayFragment fragment = new DisplayFragment();
					fragment.setArguments(bundle);
					transaction.add(mRootId, fragment, "DisplayFragment");*/

				} else if (arg2 == 7) {

					/*Intent intent = new Intent(getActivity(),
							ChangePasswordActivity.class);
					startActivity(intent);*/

				} else if (arg2 == 8) {

					/*UserInfoFragment fragment = new UserInfoFragment();
					fragment.setArguments(bundle);
					transaction.add(mRootId, fragment, "UserInfoFragment");*/

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
