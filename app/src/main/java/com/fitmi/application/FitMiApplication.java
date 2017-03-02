package com.fitmi.application;

import android.app.Application;
import android.support.multidex.MultiDex;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;
//http://www.playlottoworld.com/dev/acra_timir.php  timir da

/*@ReportsCrashes(formUri = "http://98.191.125.87/acra_fitmiandroid.php",mailTo="avinash.pandey@dreamztech.com")*/

@ReportsCrashes(formKey = "1h9ZDqZwrJLAhSDvbySOmSTX4LlGu6SimBQa45KwyuO4",formUri = "http://www.oddapp.se/GB2014/Gb12_servicesNative/acra_fitmiandroid.php")

public class FitMiApplication extends Application {
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		MultiDex.install(this);

//		ACRA.init(this);
//		ACRAReportSender reportSender = new ACRAReportSender("joe08mark@gmail.com", "Host123456");
//
//		  // register it with ACRA.
//		  ACRA.getErrorReporter().setReportSender(reportSender);
	}

}
