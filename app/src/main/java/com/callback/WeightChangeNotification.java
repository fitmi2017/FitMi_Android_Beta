package com.callback;

import android.content.Context;
import android.widget.ListView;

import com.db.modules.BpLogModule;
import com.db.modules.WeightLogModule;

public interface WeightChangeNotification {
	
	public void changeWeight(WeightLogModule weightModule, Context contex, ListView pressurelv);

}
