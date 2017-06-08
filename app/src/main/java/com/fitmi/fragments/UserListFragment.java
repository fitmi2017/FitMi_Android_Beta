package com.fitmi.fragments;

import java.util.ArrayList;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.db.modules.UserInfoModule;
import com.fitmi.R;
import com.fitmi.adapter.UserListAdapter;
import com.fitmi.dao.UserInfoDAO;
import com.fitmi.utils.Constants;
import com.fitmi.utils.SaveSharedPreferences;

public class UserListFragment extends BaseFragment {//implements UserListCallback

	@InjectView(R.id.Heading)
	public TextView heading;

	@InjectView(R.id.Back)
	public ImageView back;
	
	@InjectView(R.id.backLiner)
	LinearLayout backLiner;

	@InjectView(R.id.UserListView)
	ListView userListView;

	UserListAdapter adapter;
	
	UserInfoModule obj;
	
	ArrayList<UserInfoDAO> userList;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.fragment_user_list, container,
				false);



		ButterKnife.inject(this, view);
		setNullClickListener(view);
		heading.setText("User");
		back.setVisibility(View.GONE);

		/*ArrayList<UserInfoDAO> userList = UserInfoModule.userList(databaseObject); 	
		adapter = new UserListAdapter(getActivity(), userList);
		userListView.setAdapter(adapter);*/
		
		obj = new UserInfoModule(getActivity());

		setAdapter();

		userListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub

				Constants.PROFILE_ID = userList.get(position).getProfileId();
				
				adapter.notifyDataSetChanged();
			
				SaveSharedPreferences.saveProfileID(getActivity(), Constants.PROFILE_ID);
				
//				DeviceListFragment fragment = new DeviceListFragment();
//
//				Bundle bundle = new Bundle();
//				bundle.putInt("root_id", R.id.root_profile_frame);
//				fragment.setArguments(bundle);
//
//				FragmentTransaction transaction = getFragmentManager()
//						.beginTransaction();
//				transaction.add(R.id.root_profile_frame, fragment, "DeviceListFragment");
//				transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//				transaction.addToBackStack("DeviceListFragment");
//				transaction.commit();
				FragmentTransaction transaction = getFragmentManager()
						.beginTransaction();
				getFragmentManager().popBackStack(null,
						FragmentManager.POP_BACK_STACK_INCLUSIVE);
				transaction.add(R.id.root_profile_frame, new UserProfileFragment(),
						"UserProfileFragment");
				transaction.commit();
				
				
		/*		FragmentTransaction transaction = getFragmentManager()
						.beginTransaction();
				transaction.add(R.id.root_profile_frame,
						new DeviceListFragment(), "DeviceListFragment");
				transaction
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				transaction.addToBackStack("DeviceListFragment");
				transaction.commit();*/

			}
		});
		
		
		
		userListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int pos, long id) {
				// TODO Auto-generated method stub


				dialogDeleteItem(pos);


				return true;
			}
		}); 

		return view;
	}

	@OnClick(R.id.AddNewUserText)
	public void addNewUser() {
		
		Bundle bundle = new Bundle();
		bundle.putInt("root_id", 1);
		
		UserInfoFragment userInfo = new UserInfoFragment();
		userInfo.setArguments(bundle);

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.add(R.id.root_profile_frame, userInfo,
				"UserInfoFragment");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack("UserInfoFragment");
		transaction.commit();
		
		//getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();

	}

	@OnClick(R.id.Settings)
	public void gotoSettings() {

		SettingsFragment fragment = new SettingsFragment();

		Bundle bundle = new Bundle();
		bundle.putInt("root_id", R.id.root_profile_frame);
		fragment.setArguments(bundle);

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.add(R.id.root_profile_frame, fragment, "SettingsFragment");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack("SettingsFragment");
		transaction.commit();

	}

	@OnClick(R.id.Base_Header)
	public void clickHeaderBase() {

	}

	public void setAdapter()
	{			
		userList = obj.userList(); 			
		adapter = new UserListAdapter(getActivity(), userList);
		userListView.setAdapter(adapter);
	}

	
	/** 
	 *  Dialog delete item
	 */

	public void dialogDeleteItem(final int position)
	{

		final Dialog dialog=new Dialog(getActivity()/*,R.style.Theme_Dialog*/);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		dialog.setContentView(R.layout.dialog_delete_item);

		//
		TextView dialogTitle = (TextView)dialog.findViewById(R.id.dialogTitle);
		dialogTitle.setText("Do you want to delete the user?");
		
		dialog.findViewById(R.id.savebtn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String profileId = userList.get(position).getProfileId();
				userList.remove(position);
				adapter.notifyDataSetChanged();
				obj.deleteUser(profileId);
				Constants.PROFILE_ID = obj.userCount();
				SaveSharedPreferences.saveProfileID(getActivity(), Constants.PROFILE_ID);				
				dialog.dismiss();
			}
		});
		dialog.findViewById(R.id.cancelbtn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
		dialog.getWindow().setLayout((6 * width)/7, LayoutParams.WRAP_CONTENT);

	}
}