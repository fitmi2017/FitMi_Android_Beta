package com.fitmi.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.fitmi.fragments.RootShareFragment;
import com.fitmi.fragments.RootHelpFragment;
import com.fitmi.fragments.RootHomeFragment;
import com.fitmi.fragments.RootPlannerFragment;
import com.fitmi.fragments.RootProfileFragment;
import com.fitmi.fragments.UserInfoFragment;
import com.fitmi.fragments.UserProfileFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
	private Context _mcontext;
	private Fragment _selectedFragment;
	private FragmentManager mFragmentManager;

	public ViewPagerAdapter(Context context, FragmentManager fm) {
		super(fm);

		_mcontext = context;
		mFragmentManager = fm;

	}

	Fragment f;

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub

		f = new Fragment();
		switch (position) {
		case 0:

			f = new RootHomeFragment();

			break;

		case 1:

			f = new RootProfileFragment();

			break;

		case 2:

			f = new RootShareFragment();

			break;

		case 3:

			f = new RootHelpFragment();

			break;

		case 4:

			f = new RootPlannerFragment();

			break;	
			
		case 5:		
			
			
			f = new RootProfileFragment();
			
			break;

		}

		_selectedFragment = f;

		return f;

	}

	public Fragment getSelectedFragment() {
		return _selectedFragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 6;
	}

	@Override
	public int getItemPosition(Object object) {
		return PagerAdapter.POSITION_NONE;
	}

}
