package com.callback;

import android.content.Context;
import android.widget.ListView;

import com.db.modules.BpLogModule;

public interface BloodPressureChangeNotification {
	
	public void changeBloodPressure(BpLogModule bpModule, Context contex, ListView pressurelv);

}
