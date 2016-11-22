package com.fitmi.activitys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.EditText;
import android.widget.Spinner;
import butterknife.ButterKnife;

import com.dts.classes.AsyncTaskListener;
import com.dts.classes.AsyncTaskMain;
import com.dts.classes.CommonFunction;
import com.dts.classes.PostObject;
import com.dts.classes.Pref;
import com.dts.utils.Constants;


import com.fitmi.R;

public class BaseActivity extends Activity implements OnScrollListener {
	
	
	public static String __activityId="0";
	
	public static String id="id";
	public static String exercise="exercise";
	public static String description="description";
	public static String cals_per_hour="cals_per_hour";
	public static String livestrong_id="livestrong_id";
	
	public static String user_id="user_id";
	public static String user_profile_id="user_profile_id";
	public static String exercise_id="exercise_id";
	public static String exercise_name="exercise_name";
	public static String calories_burned="calories_burned";
	public static String total_time_minutes="total_time_minutes";
	public static String log_time="log_time";
	public static String date_added="date_added";

	public CommonFunction mCommonFunction;
	public Pref _Pref;
	private ProgressDialog _progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		mCommonFunction = new CommonFunction(BaseActivity.this);
		_Pref = new Pref(BaseActivity.this);
	
		setupBroadCastReceiver();

	}

	public void prepareKnives() {
		ButterKnife.inject(BaseActivity.this);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		ActivityManager mngr = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> taskList = mngr
				.getRunningTasks(20);
		if (taskList.get(0).numActivities == 1) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("You are about to quit the application! Are you sure?");
			builder.setTitle("Quit " + getString(R.string.app_name));
			builder.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							BaseActivity.this.finish();
							overridePendingTransition(
									R.anim.activityback_lefttoright,
									R.anim.activityback_righttoleft);
						}
					});
			builder.setNegativeButton("Not Now", null);
			builder.create();
			builder.show();
		} else {
			this.finish();
			overridePendingTransition(R.anim.activityback_lefttoright,
					R.anim.activityback_righttoleft);
		}

	}

	public void postTowebservice(AsyncTaskListener _listener,
			HashMap<String, PostObject> map) {
		if (this != null) {
			AsyncTaskMain _asyncTaskMain = new AsyncTaskMain(_listener, map,
					this);
			_asyncTaskMain
					.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
		}

	}

	public void postTowebservice(AsyncTaskListener _listener,
			ArrayList<String> keys, ArrayList<String> values) {
		if (this != null) {
			AsyncTaskMain _asyncTaskMain = new AsyncTaskMain(_listener, keys,
					values, this);
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

		_progressDialog = new ProgressDialog(this,
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
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
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

	BroadcastReceiver _logoutReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Log.d("onReceive", "Logout in progress");
			// At this point you should start the login activity and finish
			// this one
			finish();
		}
	};

	private void setupBroadCastReceiver() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Constants.ACTION_LOGOUT);
		registerReceiver(_logoutReceiver, intentFilter);

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(_logoutReceiver);
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

	public void setLoadMoreView() {
		loadMoreView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
				.inflate(R.layout.loadmore, null, false);
	}

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

	public void showHomeScreen() {
		Intent startMain = new Intent(Intent.ACTION_MAIN);
		startMain.addCategory(Intent.CATEGORY_HOME);
		startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(startMain);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}
	
}
