package com.dts.classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dts.sglibrary.R;

@SuppressLint("SimpleDateFormat")
public class CommonFunction {
	private Context mctx;
	private Activity _activity;
	public int TOAST_LENGTH_LONG = Toast.LENGTH_LONG;
	public int TOAST_LENGTH_SHORT = Toast.LENGTH_SHORT;
	private Pref _spref;

	public CommonFunction(Context ctx) {
		// TODO Auto-generated constructor stub
		this.mctx = ctx;
		this._activity = (Activity) mctx;
		this._spref = new Pref(_activity);

	}

	public void showActionBar() {
		/*
		 * ActionBar actionBar = _activity.getActionBar();
		 * actionBar.setCustomView(R.layout.custom_bar);
		 * actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		 */
	}

	public static Bitmap decodeUri(Context c, Uri uri, final int requiredSize)
			throws FileNotFoundException {
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri),
				null, o);

		int width_tmp = o.outWidth, height_tmp = o.outHeight;
		int scale = 1;

		while (true) {
			if (width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize)
				break;
			width_tmp /= 2;
			height_tmp /= 2;
			scale *= 2;
		}

		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize = scale;
		return BitmapFactory.decodeStream(c.getContentResolver()
				.openInputStream(uri), null, o2);
	}

	
	
	public void exportDatabse(String databaseName,Context cont) {
		try {
			File sd = Environment.getExternalStorageDirectory();
			File data = Environment.getDataDirectory();

			Date today = new Date();

			if (sd.canWrite()) {
				String currentDBPath = "//data//" + cont.getPackageName()
						+ "//databases//" + databaseName + "";
				String backupDBPath = "backupname1" + today.toString().trim()
						+ ".db";
				File currentDB = new File(data, currentDBPath);
				File backupDB = new File(sd, backupDBPath);

				if (currentDB.exists()) {
					FileChannel src = new FileInputStream(currentDB)
							.getChannel();
					FileChannel dst = new FileOutputStream(backupDB)
							.getChannel();
					dst.transferFrom(src, 0, src.size());
					src.close();
					dst.close();
				}
			}
		} catch (Exception e) {

		}
	}
	
	public Pref getPrefInstance() {
		return _spref;
	}

	public void hideNoItemsText(View v) {
		try {
			v.setVisibility(View.GONE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showNoItemsText(View v) {
		try {
			v.setVisibility(View.VISIBLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showProgressBar(View v) {
		try {
			v.setVisibility(View.VISIBLE);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void hideProgressBar(View v) {

		try {
			v.setVisibility(View.GONE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showToastMessage(String message, int toastlength) {
		Toast.makeText(_activity, message, toastlength).show();
	}

	public void showToastMessageLong(String message) {
		Toast.makeText(_activity, message, Toast.LENGTH_LONG).show();
	}

	public void showToastMessageShort(String message) {
		Toast.makeText(_activity, message, Toast.LENGTH_SHORT).show();
	}

	public void overrideActivityTransition() {

		_activity.overridePendingTransition(R.anim.activity_righttoleft,
				R.anim.activity_lefttoright);

		// _activity.overridePendingTransition(android.R.anim,android.R.anim.slide_out_right);
		// _activity.overridePendingTransition(enterAnim, exitAnim)
	}

	public void showIntent(Class<?> c) {
		Intent i = new Intent(mctx, c);
		_activity.startActivity(i);
		boolean transition = true;
		/*
		 * if (c.isInstance(HomeActivity.class)) { if
		 * (!getPrefInstance().getSession("HomeActivity").equalsIgnoreCase(
		 * "LOADED")) transition = false; }
		 */
		if (transition)
			overrideActivityTransition();
	}

	public void showIntent(Class<?> c, int flag) {
		Intent i = new Intent(mctx, c);
		i.addFlags(flag);
		_activity.startActivity(i);
		boolean transition = true;
		if (transition)
			overrideActivityTransition();
	}

	public static String getYoutubeVideoId(String youtubeUrl) {
		String video_id = "";
		if (youtubeUrl != null && youtubeUrl.trim().length() > 0
				&& youtubeUrl.startsWith("http")) {

			String expression = "^.*((youtu.be"
					+ "\\/)"
					+ "|(v\\/)|(\\/u\\/w\\/)|(embed\\/)|(watch\\?))\\??v?=?([^#\\&\\?]*).*";
			CharSequence input = youtubeUrl;
			Pattern pattern = Pattern.compile(expression,
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(input);
			if (matcher.matches()) {
				String groupIndex1 = matcher.group(7);
				if (groupIndex1 != null && groupIndex1.length() == 11)
					video_id = groupIndex1;
			}
		}
		return video_id;
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}

	public void showIntent(Class<?> c, ArrayList<BasicNameValuePair> np,
			boolean OK) {
		Intent i = new Intent(mctx, c);
		if (np != null) {
			for (int j = 0; j < np.size(); j++) {
				String extraValue = np.get(j).getValue();
				if (isInteger(extraValue)) {
					int intVal = Integer.parseInt(extraValue);
					i.putExtra(np.get(j).getName(), intVal);
				} else {
					i.putExtra(np.get(j).getName(), np.get(j).getValue());
				}

			}
		}
		_activity.startActivity(i);
		overrideActivityTransition();
	}

	public void showIntent(Class<?> c, ArrayList<KeyValueClass> params) {
		Intent i = new Intent(mctx, c);
		if (params != null) {
			for (int j = 0; j < params.size(); j++) {
				i.putExtra(params.get(j).getKey(), params.get(j).getObject());
			}
		}
		_activity.startActivity(i);
		overrideActivityTransition();
	}

	public void showIntent(Class<?> c, String key, byte[] bytes) {
		Intent i = new Intent(mctx, c);
		i.putExtra(key, bytes);
		_activity.startActivity(i);
		overrideActivityTransition();
	}

	@SuppressLint("SimpleDateFormat")
	public static String getDateFromtimestamp(long timestamp) {
		// Mon 4/1/13 3:52 PM
		/*
		 * Date d = new Date(timestamp); DateFormat defaultDf = new
		 * SimpleDateFormat("EEE MM/d/yy h:mm a"); String formattedDate =
		 * defaultDf.format(d);
		 */
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(timestamp));
		// DateFormat defaultDf = new
		// SimpleDateFormat("EEE MM/dd/yyyy hh:mm a");
		DateFormat defaultDf = new SimpleDateFormat("EEE M/d/yy hh:mma");
		String formattedDate = defaultDf.format(c.getTime());
		return formattedDate;
	}

	public static String getBoldText(String theText) {
		return "<b>" + theText + "</b>";

	}

	public static String getFilteredFolderName(String display_name) {
		display_name = display_name
				.substring(display_name.lastIndexOf("/") + 1);
		display_name = display_name
				.substring(display_name.lastIndexOf(".") + 1);
		return display_name;
	}

	public int getDPI() {
		DisplayMetrics metrics = new DisplayMetrics();
		_activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.densityDpi;
	}

	@SuppressLint("SimpleDateFormat")
	public String getDateFormatted(String serverDate) {
		String[] dateArray = serverDate.split("/");
		// Log.e("MONTH", ""+Integer.parseInt(dateArray[1]));

		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, Integer.parseInt(dateArray[0]));
		c.set(Calendar.MONTH, Integer.parseInt(dateArray[1]) - 1);
		c.set(Calendar.DATE, Integer.parseInt(dateArray[2]));

		if (serverDate.length() > 0) {
			SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM d");
			String date = DATE_FORMAT.format(c.getTime());

			return date;

		} else {
			return "";
		}

	}

	public String getDateFormattedReversed(String serverDate) {
		String[] dateArray = serverDate.split("/");
		// Log.e("MONTH", ""+Integer.parseInt(dateArray[1]));

		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, Integer.parseInt(dateArray[2]));
		c.set(Calendar.MONTH, Integer.parseInt(dateArray[0]) - 1);
		c.set(Calendar.DATE, Integer.parseInt(dateArray[1]));

		if (serverDate.length() > 0) {
			SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM d");
			String date = DATE_FORMAT.format(c.getTime());

			return date;

		} else {
			return "";
		}
	}

	public String getDateFormattedwithYear(String serverDate) {
		String[] dateArray = serverDate.split("/");

		Log.e("DATE", "" + Integer.parseInt(dateArray[1]));
		Log.e("MONTH", "" + Integer.parseInt(dateArray[0]));
		Log.e("YEAR", "" + Integer.parseInt(dateArray[2]));

		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, Integer.parseInt(dateArray[2]));
		c.set(Calendar.MONTH, Integer.parseInt(dateArray[0]) - 1);
		c.set(Calendar.DATE, Integer.parseInt(dateArray[1]));

		if (serverDate.length() > 0) {
			SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMMMM d, yyyy");
			String date = DATE_FORMAT.format(c.getTime());

			return date;

		} else {
			return "";
		}

	}

	// 22nd June 2014
	public String getSpecialDateFormat(String serverDate) {

		String[] dateArray = serverDate.split("/");

		/*
		 * Log.e("DATE", "" + Integer.parseInt(dateArray[1])); Log.e("MONTH", ""
		 * + Integer.parseInt(dateArray[0])); Log.e("YEAR", "" +
		 * Integer.parseInt(dateArray[2]));
		 */
		try {
			Calendar c = Calendar.getInstance();
			c.set(Calendar.YEAR, Integer.parseInt(dateArray[2]));
			c.set(Calendar.MONTH, Integer.parseInt(dateArray[0]) - 1);
			c.set(Calendar.DATE, Integer.parseInt(dateArray[1]));

			if (serverDate.length() > 0) {
				String suffix = getDayOfMonthSuffix(Integer
						.parseInt(dateArray[1]));
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
						"MMMMM, yyyy");
				String date = Integer.parseInt(dateArray[1]) + suffix + " "
						+ DATE_FORMAT.format(c.getTime());

				return date;

			} else {
				return "";
			}

		} catch (Exception e) {
			return "";
		}

	}

	public String getDayOfMonthSuffix(final int n) {

		if (n >= 11 && n <= 13) {
			return "th";
		}
		switch (n % 10) {
		case 1:
			return "st";
		case 2:
			return "nd";
		case 3:
			return "rd";
		default:
			return "th";
		}
	}

	// 11:52
	public String getTimeFormatted(String twelveHour) {
		String return_string = "";
		String[] splitStrTime = twelveHour.split(":");
		int hours = 0;
		String minutes = "";
		if (splitStrTime.length > 0) {
			hours = Integer.parseInt(splitStrTime[0]);
			minutes = splitStrTime[1];

			if (minutes.length() == 1) {
				minutes = "0" + minutes;
			}

			if (hours > 12) {
				hours = hours - 12;
				return_string = hours + ":" + minutes + " PM";
			} else if (hours == 12) {
				return_string = hours + ":" + minutes + " PM";
			} else {
				return_string = hours + ":" + minutes + " AM";
			}
		}

		return return_string;

	}

	public boolean isTaskRealPending(int taskstatus, long taskdateinMilliseconds) {
		if (taskstatus == 3) {
			return false;
		} else if (taskstatus == 1) {
			Calendar currentTime = Calendar.getInstance();
			long currentDateinMilliseconds = currentTime.getTimeInMillis();
			// Log.e("CURRENTTIME", currentDateinMilliseconds+"");
			// Log.e("TASKTIME", taskdateinMilliseconds+"");
			if (taskdateinMilliseconds > currentDateinMilliseconds) {
				return false;
			} else {
				return true;
			}

		}
		return false;
	}

	public boolean isEmailValid(EditText editText) {

		String email = editText.getText().toString();

		return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();

	}

	public void sendEmail(String subject, String body, String to) {

		Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
				"mailto", to, null));
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
		emailIntent.putExtra(Intent.EXTRA_TEXT, body);
		mctx.startActivity(Intent.createChooser(emailIntent, "Send email..."));

	}

	public boolean validateAllFields(TextView[] views, String msg[]) {
		if (views.length != msg.length) {
			showToastMessageShort("Error Handler Problem !");
			return false;
		}
		int i = 0;
		for (TextView field : views) {
			if (checkifBlank(field, msg[i])) {
				return false;
			}
			i++;
		}
		return true;

	}

	public boolean checkifBlank(TextView view, String msg) {
		if (view == null) {
			return true;
		}
		if (view.getText().toString().trim().length() == 0) {
			showToastMessageShort(msg);
			return true;
		}
		return false;
	}

	public void dismissKeyboard(EditText editText) {
		InputMethodManager imm = (InputMethodManager) mctx
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

	}

}
