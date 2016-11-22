package com.dts.classes;

public interface AsyncTaskListener {
	
	public void onTaskCompleted(String result);
	public void onTaskPreExecute();
}
