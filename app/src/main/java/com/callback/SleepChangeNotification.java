package com.callback;

import android.content.Context;
import android.widget.ListView;
import android.widget.TextView;

import com.db.modules.SleepModule;
import com.db.modules.WaterModule;

public interface SleepChangeNotification {
	
	public void changeSleep(SleepModule sleepModule, Context contex, ListView pressurelv, TextView txtTotal);

}
