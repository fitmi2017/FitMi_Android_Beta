package com.fitmi.utils;

import com.db.DatabaseHelper;

import android.os.Bundle;
import android.widget.TextView;

public interface NotifyCalorieChange {
	
	public void calorieUpdate(TextView totalCalory, Bundle bundle, DatabaseHelper databaseObject);

}
