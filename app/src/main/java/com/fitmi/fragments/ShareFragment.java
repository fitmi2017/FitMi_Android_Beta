package com.fitmi.fragments;

import java.util.ArrayList;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.fitmi.R;

/**
 * A simple {@link Fragment} subclass.
 */

/*
 *   share instagarm
 *   http://stackoverflow.com/questions/16864138/how-to-send-a-photo-to-instagram-using-my-android-app
 */

public class ShareFragment extends BaseFragment {
	
	

	@InjectView(R.id.Heading)
	public TextView heading;

	@InjectView(R.id.Back)
	public ImageView back;
	
	
	@InjectView(R.id.backLiner)
	LinearLayout backLiner;
	
	@InjectView(R.id.imgMailShare)
	ImageView imgMailShare;
	
	@InjectView(R.id.imgFbShare)
	ImageView imgFbShare;
	
	@InjectView(R.id.imgTwitterShare)
	ImageView imgTwitterShare;
	
	@InjectView(R.id.imgInstagramShare)
	ImageView imgInstagramShare;
	
	
	String waterTotal = "";
	String weightTotal = "";		
	String totalCaloty = "";
	String totalCalotyBurn = "";
	String note = "";
	ArrayList<String> sumArray;
	
	String shareText = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_share, container, false);
		
		
		Bundle bundle = this.getArguments();
		
		if(bundle !=null){
			
			waterTotal = bundle.getString("waterTotal");
			weightTotal = bundle.getString("weightTotal");
			totalCaloty = bundle.getString("totalCaloty");
			totalCalotyBurn = bundle.getString("totalCalotyBurn");
			note = bundle.getString("note");
			sumArray = bundle.getStringArrayList("sumArray");
			
			if(waterTotal !=null)
				shareText = "Water :"+waterTotal;
			if(weightTotal !=null)
				shareText +="\nWeight :"+weightTotal;
			if(totalCaloty !=null)
				shareText +="\nTotal calorie :"+totalCaloty;
			if(totalCalotyBurn !=null)
				shareText +="\nTotal calorie burned :"+totalCalotyBurn;
			if(sumArray.get(0) !=null)
				shareText +="\n Sys :"+sumArray.get(0)+" Dia :"+sumArray.get(1)+" Pulse Rate :74";
			if(!note.equalsIgnoreCase("")&&note!=null)
				shareText +="\n Note :"+note;			
		}

		ButterKnife.inject(this, v);
		setNullClickListener(v);
		heading.setText("Share");
		back.setVisibility(View.GONE);

		return v;
	}

	@OnClick(R.id.Settings)
	public void gotoSettings() {

		SettingsFragment fragment = new SettingsFragment();

		Bundle bundle = new Bundle();
		bundle.putInt("root_id", R.id.root_share_frame);
		fragment.setArguments(bundle);

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.add(R.id.root_share_frame, fragment, "SettingsFragment");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();

	}
	
	@OnClick(R.id.imgMailShare)
	protected void sendEmail() {
		
		
	      
	      String[] TO = {""};
	      String[] CC = {""};
	      Intent emailIntent = new Intent(Intent.ACTION_SEND);  	      
	      emailIntent.setData(Uri.parse("mailto:"));
	      emailIntent.setType("text/plain");
	     // emailIntent.setType("message/rfc822");	 
	      emailIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
	      emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
	      emailIntent.putExtra(Intent.EXTRA_CC, CC);
	      emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Share my information");
	      emailIntent.putExtra(Intent.EXTRA_TEXT, shareText);	   
	      
	      try {
	         startActivity(Intent.createChooser(emailIntent, "Send mail..."));
	         
	      }
	      catch (android.content.ActivityNotFoundException ex) {
	         Toast.makeText(getActivity(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
	      }
	   }
	
	
	@OnClick(R.id.imgInstagramShare)
	public void clickInstagram()
	{
		if(verificaInstagram()){
			
			Intent shareIntent = new Intent(Intent.ACTION_SEND);
			shareIntent.setType("image/*");			
			shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);			
			shareIntent.putExtra(Intent.EXTRA_TEXT,shareText);
			shareIntent.setPackage("com.instagram.android");
			startActivity(shareIntent);
			
		}else{
			
			Toast.makeText(getActivity(), "Instagram is not installed.", Toast.LENGTH_LONG).show();
		}
	}
	
	@OnClick(R.id.imgFbShare)
	public void clickFacebookShare()
	{		
		// http://stackoverflow.com/questions/22533773/android-how-to-share-image-with-text-on-facebook-via-intent
		
		
		/*String urlToShare = "www.google.com";
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");		
		//intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);	
		intent.putExtra(Intent.EXTRA_TITLE, shareText);		
		intent.putExtra(Intent.EXTRA_SUBJECT, shareText);
		intent.putExtra(Intent.EXTRA_TEXT, shareText);		

		// See if official Facebook app is found
		boolean facebookAppFound = false;
		List<ResolveInfo> matches = getActivity().getPackageManager().queryIntentActivities(intent, 0);
		for (ResolveInfo info : matches) {
		    if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana")) {
		        intent.setPackage(info.activityInfo.packageName);
		        facebookAppFound = true;
		        break;
		    }
		}

		// As fallback, launch sharer.php in a browser
		if (!facebookAppFound) {
		    String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u="+ urlToShare;// 
		    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
		}

		startActivity(intent);*/
		
		
		
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");		
		shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
		PackageManager pm = getActivity().getPackageManager();
		List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
		for (final ResolveInfo app : activityList) {
		    if ((app.activityInfo.name).contains("facebook")) {
		        final ActivityInfo activity = app.activityInfo;
		        final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
		        shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		        shareIntent.setComponent(name);
		        getActivity().startActivity(shareIntent);
		        break;
		   }
		}
		
		
		 // get available share intents
	 /*   List<Intent> targets = new ArrayList<Intent>();
	    Intent template = new Intent(Intent.ACTION_SEND);
	    template.setType("text/plain");
	    List<ResolveInfo> candidates = getActivity().getPackageManager().
		  queryIntentActivities(template, 0);

	    // remove facebook which has a broken share intent
	    for (ResolveInfo candidate : candidates) {
	        String packageName = candidate.activityInfo.packageName;
	        if (!packageName.equals("com.facebook.katana")) {
		  Intent target = new Intent(android.content.Intent.ACTION_SEND);
		  target.setType("text/plain");
		  target.putExtra(Intent.EXTRA_TEXT, "Text to share");
		  target.setPackage(packageName);
		  targets.add(target);
	        }
	    }
	    Intent chooser = Intent.createChooser(targets.remove(0), "Share Via");
	    chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, targets.toArray(new Parcelable[]{}));
	    startActivity(chooser);*/
	}
	
	@OnClick(R.id.imgTwitterShare)
	public void clickTwitterShare()
	{
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
		PackageManager pm = getActivity().getPackageManager();
		List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
		for (final ResolveInfo app : activityList) {
		    if ("com.twitter.android.composer.ComposerActivity".equals(app.activityInfo.name)) {//.PostActivity
		        final ActivityInfo activity = app.activityInfo;
		        final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
		        shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		        shareIntent.setComponent(name);
		        startActivity(shareIntent);
		        break;
		   }
		}
	}
	
	@OnClick(R.id.cancelbtn)
	public void clickOnCancel()
	{
		getActivity().onBackPressed();
	}
	
/**
 *  Check instagram installed	
 * @return
 */
	private boolean verificaInstagram(){
	    boolean installed = false;

	    try {
	        ApplicationInfo info = getActivity().getPackageManager().getApplicationInfo("com.instagram.android", 0);
	        installed = true;
	    } catch (NameNotFoundException e) {
	        installed = false;
	    }
	        return installed;
	    }

}
