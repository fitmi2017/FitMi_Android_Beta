package com.fitmi.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.fitmi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends BaseFragment {

	@InjectView(R.id.Heading)
	public TextView heading;

	@InjectView(R.id.Back)
	public ImageView back;
	
	@InjectView(R.id.Settings)
	ImageView Settings;
	
	@InjectView(R.id.rel_laymail)
	RelativeLayout  rel_laymail;
	
	@InjectView(R.id.rel_laychat)
	RelativeLayout  rel_laychat;
	
	@InjectView(R.id.backLiner)
	LinearLayout backLiner;
	
	@InjectView(R.id.imgMail)
	ImageView imgMail;
	
	@InjectView(R.id.imgChat)
	ImageView imgChat;

	@InjectView(R.id.imgFaq)
	ImageView imgFaq;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_help, container, false);

		ButterKnife.inject(this, v);
		setNullClickListener(v);
		heading.setText("Help");
		back.setVisibility(View.GONE);
		Settings.setVisibility(View.GONE);
		com.fitmi.utils.Constants.fragmentSet = false;

		return v;
	}

/*	@OnClick(R.id.Settings)
	public void gotoSettings() {

		SettingsFragment fragment = new SettingsFragment();

		Bundle bundle = new Bundle();
		bundle.putInt("root_id", R.id.root_help_frame);
		fragment.setArguments(bundle);

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.add(R.id.root_help_frame, fragment, "SettingsFragment");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();

	}*/
	
	@OnClick(R.id.imgMail)
	public void sendMail()
	{
		sendEmail();
	}
	
	@OnClick(R.id.rel_laymail)
	public void onclickRelMail()
	{
		sendEmail();
	}
	@OnClick(R.id.imgChat)
	public void onclickChat()
	{
		//avinash added ChatFragment 
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.add(R.id.root_help_frame, new ChatFragment(),
				"ChatFragment");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();
	
	}
	
	@OnClick(R.id.rel_laychat)
	public void onclickRelChat()
	{
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.add(R.id.root_help_frame, new ChatFragment(),
				"ChatFragment");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();
	}
	
	@OnClick(R.id.imgFaq)
	public void onclickiFaq()
	{
		//avinash added FaqFragment click and FaqFragment webview
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.add(R.id.root_help_frame, new FaqFragment(),
				"FaqFragment");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();
		
		
	}
	
	protected void sendEmail() {
	      
		 String[] TO = {"info@fitmi.com"};
	      String[] CC = {""};
	      Intent emailIntent = new Intent(Intent.ACTION_SEND);  	      
	      emailIntent.setData(Uri.parse("mailto:"));
	      emailIntent.setType("text/plain");
	     // emailIntent.setType("message/rfc822");	 
	      emailIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
	      emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
	      emailIntent.putExtra(Intent.EXTRA_CC, CC);
	      emailIntent.putExtra(Intent.EXTRA_SUBJECT, "FitMi App Support");
	      emailIntent.putExtra(Intent.EXTRA_TEXT, "");	   
	      
	      try {
	         startActivity(Intent.createChooser(emailIntent, "Send mail..."));
	         
	      }
	      catch (android.content.ActivityNotFoundException ex) {
	         Toast.makeText(getActivity(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
	      }
	   }
}
