package com.callback;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.widget.TextView;

import com.db.DatabaseHelper;

public interface ActivityTimeUpdate {
	
	public void timeUpdate(TextView totalCalory, ArrayList<HashMap<String, String>> List);

}
