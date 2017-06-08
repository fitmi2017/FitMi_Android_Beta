package com.fitmi;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.WindowManager;

import com.fitmi.activitys.BaseActivity;
import com.fitmi.activitys.ResetPasswordActivity;
import com.fitmi.activitys.SignInActivity;
import com.fitmi.activitys.TabActivity;
import com.fitmi.dao.RememberMeData;
import com.fitmi.utils.Constants;
import com.fitmi.utils.SaveSharedPreferences;

public class SplashActivity extends BaseActivity {

    private long SPLASHTIME_INMILLIS = 2000;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                && !(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1001);
        } else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    try {
                        if (getIntent() != null && getIntent().getData() != null) {
                            //E/intnt:  = fitmi://app.fitmi.com/email_address=baljinder.impinge@gmail.com
                            Log.e("data", "data===" + getIntent().getData());
                            String data = getIntent().getData().toString();
                            String email = data.substring(data.indexOf("=") + 1);
                            Intent intent = new Intent(SplashActivity.this, ResetPasswordActivity.class);
                            intent.putExtra("email_address", email);
                            startActivity(intent);
                        } else {

                            RememberMeData rememberMe = SaveSharedPreferences.getLoginDetail(SplashActivity.this);
                            if (!rememberMe.isRemember())
                                mCommonFunction.showIntent(SignInActivity.class);
                            else {
                                Constants.USER_ID = rememberMe.getUserId();
                                Constants.LOGIN_MAIL_ID = rememberMe.getUserName();
                                Constants.Access_key = rememberMe.getAccess_key();
                                mCommonFunction.showIntent(TabActivity.class);
                            }
                        }

                        finish();
                    } catch (Exception e) {
                    }

                }
            }, SPLASHTIME_INMILLIS);
        }


    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1001) {

            boolean isAllow = true;
            for (int i = 0, len = permissions.length; i < len; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    isAllow = false;
                }
            }

            if (isAllow) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            if (getIntent() != null && getIntent().getData() != null) {
                                //E/intnt:  = fitmi://app.fitmi.com/email_address=baljinder.impinge@gmail.com
                                Log.e("data", "data===" + getIntent().getData());
                                String data = getIntent().getData().toString();
                                String email = data.substring(data.indexOf("=") + 1);
                                Intent intent = new Intent(SplashActivity.this, ResetPasswordActivity.class);
                                intent.putExtra("email_address", email);
                                startActivity(intent);
                            } else {

                                RememberMeData rememberMe = SaveSharedPreferences.getLoginDetail(SplashActivity.this);
                                if (!rememberMe.isRemember())
                                    mCommonFunction.showIntent(SignInActivity.class);
                                else {
                                    Constants.USER_ID = rememberMe.getUserId();
                                    Constants.LOGIN_MAIL_ID = rememberMe.getUserName();
                                    Constants.Access_key = rememberMe.getAccess_key();
                                    mCommonFunction.showIntent(TabActivity.class);
                                }
                            }

                            finish();
                        } catch (Exception e) {
                        }

                    }
                }, SPLASHTIME_INMILLIS);
            }
        }
    }

}



