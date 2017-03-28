package com.fitmi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.db.modules.RememberMeData;
import com.dts.classes.CommonFunction;

import com.fitmi.activitys.BaseActivity;
import com.fitmi.activitys.DeviceSyncService;
import com.fitmi.activitys.SignInActivity;
import com.fitmi.activitys.TabActivity;
import com.fitmi.utils.Constants;
import com.fitmi.utils.SaveSharedPreferences;

public class SplashActivity extends BaseActivity {

	private long SPLASHTIME_INMILLIS = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		

//		Intent service = new Intent(this, DeviceSyncService.class);
//		startService(service);
		
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				RememberMeData rememberMe = SaveSharedPreferences.getLoginDetail(SplashActivity.this);

				if(!rememberMe.isRemember())
					mCommonFunction.showIntent(SignInActivity.class);
				else{	
					Constants.USER_ID = rememberMe.getUserId();
					Constants.LOGIN_MAIL_ID = rememberMe.getUserName();
					Constants.Access_key=rememberMe.getAccess_key();
					mCommonFunction.showIntent(TabActivity.class);
				}				
				finish();

			}
		}, SPLASHTIME_INMILLIS);

	}

}
