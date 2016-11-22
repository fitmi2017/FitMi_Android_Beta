package com.fitmi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;

public class CustomExceptionHandler implements UncaughtExceptionHandler {

	// private UncaughtExceptionHandler defaultUEH;

	private String localPath, finalPath = "";
	static private final String EOL = "\n\r";
	private String url;
	Context mContext;
	UncaughtExceptionHandler defaultUEH;
	EmailSendClass email = new EmailSendClass();

	/*
	 * if any of the parameters is null, the respective functionality will not
	 * be used
	 */
	public CustomExceptionHandler(Context context) {

		mContext = context;
		this.localPath = getDefaultFilePath();
		this.url = null;//"http://www.oddapp.se/GB2014/Gb12_servicesNative/acra_fitmiandroid.php";
		this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);

	}

	String stacktrace = "";

	@Override
	public void uncaughtException(Thread t, Throwable e) {

		Date timestamp = new Date(System.currentTimeMillis());
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		e.printStackTrace(printWriter);
		stacktrace = result.toString();
		printWriter.close();
		String filename = System.currentTimeMillis() + ".txt";

		if (localPath != null) {
			writeToFile(stacktrace, filename);
			email.sendMail(mContext, "avinash.pandey@dreamztech.com", finalPath);

		}
		if (url != null) {
			sendToServer(stacktrace, filename);
		}

		/*
		 * Intent intent = new Intent(mContext, CrashActivity.class);
		 * intent.putExtra("error", stacktrace+getPhoneInfo().toString());
		 * mContext.startActivity(intent);
		 */

		// android.os.Process.killProcess();
		System.exit(10);

		defaultUEH.uncaughtException(t, e);
	}

	private void writeToFile(String stacktrace, String filename) {
		try {
			finalPath = localPath + "/" + filename;
			File file = new File(localPath + "/" + filename);
			BufferedWriter bos = new BufferedWriter(new FileWriter(file, true));
			bos.write(getPhoneInfo().toString());
			bos.write("\n\r");
			bos.write("-------------------------------------------");
			bos.write("\n\r");
			bos.write(stacktrace);
			bos.write("-------------------------------------------");
			bos.write("\n\r");
			bos.write("-------------------------------------------");
			bos.write("\n\r");
			bos.flush();
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	private void sendToServer(String stacktrace, String filename) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("APP_VERSION_NAME", getPhoneInfo().toString()));
		nvps.add(new BasicNameValuePair("PHONE_MODEL", "blank"));
		System.out.println("working sendToServer");
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			httpClient.execute(httpPost);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public StringBuffer getPhoneInfo() {

		StringBuffer returnVal = new StringBuffer();
		// SharedPreferences shared =
		// mContext.getSharedPreferences(Constants.PREF_NAME,
		// mContext.MODE_PRIVATE);
		// String emailTxt = (shared.getString(Constants.PREF_REM_ME, ""));
		//
		// returnVal.append("User Email id : ").append(emailTxt).append(EOL);
		returnVal.append("Android Version : ").append(VERSION.CODENAME)
				.append(EOL);
		returnVal.append("Release : ").append(VERSION.RELEASE).append(EOL);
		returnVal.append("SDK int : ").append(VERSION.SDK_INT).append(EOL);
		returnVal.append("Phone Model : ").append(Build.MODEL).append(EOL);
		returnVal.append("Board : ").append(Build.BOARD).append(EOL);
		returnVal.append("Brand : ").append(Build.BRAND).append(EOL);
		returnVal.append("Device : ").append(Build.DEVICE).append(EOL);
		returnVal.append("Display : ").append(Build.DISPLAY).append(EOL);
		returnVal.append("Finger Print : ").append(Build.FINGERPRINT)
				.append(EOL);
		returnVal.append("Host : ").append(Build.HOST).append(EOL);
		returnVal.append("ID : ").append(Build.ID).append(EOL);
		returnVal.append("Product : ").append(Build.PRODUCT).append(EOL);
		returnVal.append("Tags : ").append(Build.TAGS).append(EOL);
		returnVal.append("User : ").append(Build.USER).append(EOL);

		StatFs stats = new StatFs(Environment.getDataDirectory().getPath());
		double availableMem = (double) stats.getAvailableBlocks()
				* (double) stats.getBlockSize();

		returnVal.append("Available Internal memory : ").append(availableMem)
				.append(EOL);
		returnVal.append("Date and Time : ").append(System.currentTimeMillis())
				.append(EOL);

		return returnVal;
	}

	private String getDefaultFilePath() {
		String CRASH_DIRECTORY = Environment.getExternalStorageDirectory()
				+ "/MetroNational_crash_directory";
		File file = new File(CRASH_DIRECTORY);
		if (!file.exists())
			file.mkdirs();
		return file.getPath();
	}
}
