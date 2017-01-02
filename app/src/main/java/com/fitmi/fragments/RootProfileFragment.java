package com.fitmi.fragments;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.db.modules.UserInfoModule;
import com.fitmi.R;
import com.fitmi.activitys.TabActivity;
import com.fitmi.dao.UserInfoDAO;

public class RootProfileFragment extends BaseFragment {

	private static final String TAG = "RootProfileFragment";
	
	UserInfoModule obj;
	ArrayList<UserInfoDAO> userList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/* Inflate the layout for this fragment */
		View view = inflater.inflate(R.layout.root_profile_fragment, container,
				false);

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		getFragmentManager().popBackStack(null,
				FragmentManager.POP_BACK_STACK_INCLUSIVE);
		/*transaction.add(R.id.root_profile_frame, new UserListFragment(),
				"UserProfileFragment");*/
		
		obj = new UserInfoModule(getActivity());
		userList = obj.userList();
		
		if(userList.size()>0){
			transaction.add(R.id.root_profile_frame, new UserProfileFragment(),
					"UserProfileFragment");
		}else{
			Bundle bundle = new Bundle();
			bundle.putInt("root_id", 1);
			
			UserInfoFragment f = new UserInfoFragment();			
			f = new UserInfoFragment();			
			f.setArguments(bundle);			
			transaction.add(R.id.root_profile_frame, f,	"UserProfileFragment");
		}

		transaction.commit();
		return view;
	}

	public RootProfileFragment newInstance(Context context) {

		RootProfileFragment f = new RootProfileFragment();
		return f;

	}

}
