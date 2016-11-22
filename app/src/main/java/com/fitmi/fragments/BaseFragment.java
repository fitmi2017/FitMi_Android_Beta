package com.fitmi.fragments;

import java.util.ArrayList;
import java.util.HashMap;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.dts.classes.AsyncTaskListener;
import com.dts.classes.AsyncTaskMain;
import com.dts.classes.CommonFunction;
import com.dts.classes.PostObject;
import com.dts.classes.Pref;
import com.fitmi.utils.Constants;

public class BaseFragment extends Fragment {

	public CommonFunction mCommonFunction;
	public Pref _Pref;
	private ProgressDialog _progressDialog;	
	TextView view;
	String newDate="";

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Constants.fragmentNumber = 0;
		mCommonFunction = new CommonFunction(getActivity());
		_Pref = new Pref(getActivity());
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	public void postTowebservice(AsyncTaskListener _listener,
			HashMap<String, PostObject> map) {
		if (this != null) {
			AsyncTaskMain _asyncTaskMain = new AsyncTaskMain(_listener, map,
					getActivity());
			_asyncTaskMain
					.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
		}

	}

	public void postTowebservice(AsyncTaskListener _listener,
			ArrayList<String> keys, ArrayList<String> values) {
		if (this != null) {
			AsyncTaskMain _asyncTaskMain = new AsyncTaskMain(_listener, keys,
					values, getActivity());
			_asyncTaskMain
					.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
		}

	}

	public PostObject getPostObject(String value, boolean isFile) {
		PostObject _postObject = new PostObject();
		_postObject.setFile(isFile);
		if (isFile) {
			_postObject.setFile_url(value);
		}
		_postObject.setString_value(value);
		return _postObject;
	}

	public void showProgressMessage(String title, String message) {

		if (title.startsWith("Team")) {
			title = "MyTeamConnector";
		}

		_progressDialog = new ProgressDialog(getActivity(),
				ProgressDialog.THEME_HOLO_LIGHT);
		_progressDialog.setTitle(title);
		_progressDialog.setMessage(message);
		_progressDialog.setCancelable(false);
		_progressDialog.show();
	}

	public void hideProgressDialog() {
		_progressDialog.dismiss();
	}

	public boolean isLoggedIn() {
		boolean loggedin = false;
		// String uid = mCommonFunction.getPrefInstance().getSession(
		// SharedPrefConstants.USERID);
		// if (uid.length() > 0) {
		// loggedin = true;
		// }
		return loggedin;
	}

	public String getUserId() {
		// String uid = mCommonFunction.getPrefInstance().getSession(
		// SharedPrefConstants.USERID);
		return "";
	}

	public void removeSession() {

	}

	public void hideKeyboard(EditText myEditText) {
		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
	}

	public String filterJsonValue(JSONObject jobj, String key)
			throws JSONException {
		String returnStr = "";

		if (jobj.isNull(key)) {
			returnStr = "";
		} else {
			returnStr = jobj.getString(key);
		}

		return returnStr;
	}

	public boolean isEditTextEmpty(EditText editText) {

		if (editText.getText().toString().length() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean isSpinnerTextEmpty(Spinner spinner) {
		try {
			if (spinner.getSelectedItem() != null) {

				if (spinner.getSelectedItem().toString().length() == 0) {
					return true;
				} else {
					return false;
				}
			} else {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return true;
		}

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub

		super.onDestroy();

	}

	public static void addImageToGallery(final String filePath,
			final Context context) {

		ContentValues values = new ContentValues();

		values.put(Images.Media.DATE_TAKEN, System.currentTimeMillis());
		values.put(Images.Media.MIME_TYPE, "image/jpeg");
		values.put(MediaStore.MediaColumns.DATA, filePath);

		context.getContentResolver().insert(Images.Media.EXTERNAL_CONTENT_URI,
				values);
	}

	public int startIndex = 1;
	public static final int NO_ROWS_LIMIT = 20;
	public static final String STARTINDEX_KEY = "start_indx";
	public static final String TOPROWS_KEY = "top_rows";
	public boolean loadingMore = false;
	public View loadMoreView;

	public PostObject getStartIndexPOObject() {
		PostObject _postObject = new PostObject();
		_postObject.setString_value(String.valueOf(startIndex));
		return _postObject;

	}

	public void resetStartIndex() {
		this.startIndex = 1;
	}

	public PostObject getRowsLimitPOObject() {
		PostObject _postObject = new PostObject();
		_postObject.setString_value(String.valueOf(NO_ROWS_LIMIT));
		return _postObject;

	}
	
	public void setNullClickListener(View view)
	{
		view.setOnClickListener(null);
	}
	
	public void setChangedDate(TextView view)
	{
		this.view = view;
	}
	
	public void saveNewDate(String newDate)
	{				
		this.newDate = newDate;
		//view.setText("< " + newDate + " >");
	}
	
	
}


