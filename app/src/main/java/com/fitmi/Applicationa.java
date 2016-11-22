package com.fitmi;

import android.util.Log;

public class Applicationa extends android.app.Application
{
	@Override
	public void onCreate()
	{
		super.onCreate();
		Log.e("Started ","Application Application");
		ReportHandler.install(this, "avinash.pandey@dreamztech.com");
	}
}

