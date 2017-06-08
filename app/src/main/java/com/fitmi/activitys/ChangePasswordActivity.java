package com.fitmi.activitys;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.InjectView;
import butterknife.OnClick;

import com.db.DatabaseHelper;
import com.db.modules.SignUpModule;
import com.dts.classes.JSONParser;
import com.fitmi.R;
import com.fitmi.activitys.BaseActivity;
import com.fitmi.utils.Constants;
import com.ssl.MySSLSocketFactory;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

public class ChangePasswordActivity extends BaseActivity {


	DatabaseHelper databaseObject;

	String validPass = "";

	@InjectView(R.id.OldPassword)
	EditText OldPassword;

	@InjectView(R.id.NewPassword)
	EditText NewPassword;

	@InjectView(R.id.ConfirmPassword)
	EditText ConfirmPassword;

	@InjectView(R.id.Save_ChangePasswordActivity)
	Button Save_ChangePasswordActivity;

	String oldPass="" , newPass="", conPass="";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);

		databaseObject = new DatabaseHelper(ChangePasswordActivity.this);
		try {

			databaseObject.createDatabase();
			databaseObject.openDatabase();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		prepareKnives();

		validPass = SignUpModule.getPassword(databaseObject);
		//hidden by avinash
	//	OldPassword.setText(validPass);

		Save_ChangePasswordActivity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				TextView[] views = { OldPassword, NewPassword, ConfirmPassword };
				String[] msg = { "Old password cannot be blank!", "New password cannot be blank!", "Confirm password cannot be blank!" };

				if (!mCommonFunction.validateAllFields(views, msg)) {
					return;
				}

				oldPass = OldPassword.getText().toString();
				newPass = NewPassword.getText().toString();
				conPass = ConfirmPassword.getText().toString();

				if(!oldPass.equalsIgnoreCase(validPass))
				{
					Toast.makeText(ChangePasswordActivity.this, "Old password is wrong", Toast.LENGTH_LONG).show();
					return;
				}
				else if(!newPass.equalsIgnoreCase(conPass))
				{
					Toast.makeText(ChangePasswordActivity.this, "Confirm password is not match with new password", Toast.LENGTH_LONG).show();
					return;
				}

				AsyncChangetpassword asyncForgetpassword = new AsyncChangetpassword(JSONParser.webserviceResetPass);
				asyncForgetpassword.execute("exe");

//				SignUpModule.changePassword(newPass,databaseObject);
//
//				validPass = SignUpModule.getPassword(databaseObject);
//
//				if(newPass.equalsIgnoreCase(validPass))
//				{
//					Toast.makeText(ChangePasswordActivity.this, "Password change success", Toast.LENGTH_LONG).show();
//
//				}
//
//				finish();
			}
		});


	}
	
	@OnClick(R.id.Save_ChangePasswordCancel)
	public void cliclCancel()
	{
		finish();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}



	private class AsyncChangetpassword extends AsyncTask<String, Void, String> {

		String JSON_URL;
		JSONObject holder = new JSONObject();
		ProgressDialog pDialog;

		public AsyncChangetpassword(String url) {
			// TODO Auto-generated constructor stub
			JSON_URL = url;

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			pDialog = new ProgressDialog(ChangePasswordActivity.this);
			pDialog.setMessage("Requesting...");
			pDialog.show();

			super.onPreExecute();
		}

		@SuppressWarnings({"deprecation", "unchecked"})
		@Override
		protected String doInBackground(String... urls) {
			// placesAutoCompleteAdapter.notifyDataSetInvalidated();

			String SetServerString = "";
			HttpURLConnection conn = null;


			DefaultHttpClient Client = getNewHttpClient();
			HttpPost httpost = new HttpPost(JSON_URL);
			httpost.setHeader(HttpHeaders.AUTHORIZATION, "dts_android_request");
			StringEntity se = null;
			try {
				se = new StringEntity(holder.toString());
			} catch (UnsupportedEncodingException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("email_address",
					Constants.LOGIN_MAIL_ID));
			nameValuePairs.add(new BasicNameValuePair("password",
					newPass));
			try {
				httpost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			@SuppressWarnings("rawtypes")
			ResponseHandler responseHandler = new BasicResponseHandler();
			try {
				SetServerString = (String) Client.execute(httpost, responseHandler);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


			return SetServerString;
		}

		@Override
		protected void onPostExecute(String result) {
			pDialog.dismiss();

			try {
				JSONObject jsonObject = new JSONObject(String.valueOf(Html.fromHtml(result)));

				String status = jsonObject.optString("status");
				if (status.equalsIgnoreCase("true")) {
					SignUpModule.changePassword(newPass, databaseObject);
					Toast.makeText(ChangePasswordActivity.this, "Your password has been changed successfully.", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(ChangePasswordActivity.this, "Error Occured. Please Try again!", Toast.LENGTH_SHORT).show();
				}
				finish();

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public DefaultHttpClient getNewHttpClient() {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore
					.getDefaultType());
			trustStore.load(null, null);

			MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
			params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
					com.dts.utils.Constants.CONNECTION_TIMED_OUT);

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));

			ClientConnectionManager ccm = new ThreadSafeClientConnManager(
					params, registry);

			return new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
			return new DefaultHttpClient();
		}
	}

}
