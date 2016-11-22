package com.dts.classes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

@SuppressLint("CommitPrefEdits")
public class Pref {

	private SharedPreferences spref;
	private Activity _activity;
	public static final String PREF_FILE = "SETTINGS";
	private Editor _editorSpref;

	public Pref(Activity activity) {
		// TODO Auto-generated constructor stub
		this._activity = activity;
		try {
			spref = _activity.getSharedPreferences(PREF_FILE,
					Context.MODE_PRIVATE);
			_editorSpref = spref.edit();
		} catch (Exception e) {

		}
	}

	public String getSession(String key) {
		String value = "";
		if (spref != null)
			value = spref.getString(key, "");
		return value;
	}

	public void setSession(String key, String value) {
		if (key != null && value != null) {
			_editorSpref.putString(key, value);
			_editorSpref.commit();
		}
	}
}
