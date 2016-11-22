package com.callback;

import android.content.Context;
import android.widget.ListView;
import android.widget.TextView;

import com.db.modules.WaterModule;

public interface WaterChangeNotification {
	
	public void changeWater(WaterModule waterModule, Context contex, ListView pressurelv, TextView txtTotal);

}
