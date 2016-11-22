package com.fitmi.activitys;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.db.DatabaseHelper;
import com.db.modules.LoginModule;
import com.db.modules.SignUpModule;
import com.dialog.Alert;
import com.dts.classes.AsyncTaskListener;
import com.dts.classes.PostObject;
import com.fitmi.R;
import com.fitmi.dao.NutritionTypeSetget;
import com.fitmi.dao.SignUpDAO;
import com.fitmi.utils.Constants;
import com.fitmi.utils.SaveSharedPreferences;
import com.ssl.MySSLSocketFactory;

import org.apache.http.HttpVersion;
import org.apache.http.client.ResponseHandler;
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
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;

public class SignUpActivity extends BaseActivity {

	private String TAG = "SignUpActivity";
	String access_key;

	@InjectView(R.id.Email)
	EditText email;

	@InjectView(R.id.Password)
	EditText password;

	@InjectView(R.id.ConfirmPassword)
	EditText confirmPassword;
	
	@InjectView(R.id.firstName)
	EditText firstName;
	
	@InjectView(R.id.lastName)
	EditText lastName;
	
	DatabaseHelper databaseObject;
	ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);

		prepareKnives();
		
		databaseObject = new DatabaseHelper(SignUpActivity.this);
		  try {
			  
			  databaseObject.createDatabase();
			  
			  databaseObject.openDatabase();
			  
       } catch (IOException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
       }
			pDialog = new ProgressDialog(this);

		confirmPassword.setOnEditorActionListener(new OnEditorActionListener() {
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
						|| (actionId == EditorInfo.IME_ACTION_DONE)) {
					Log.i(TAG, "Enter pressed");
					signUp();
				}
				return false;
			}
		});

	}

	@OnClick(R.id.SignUp_SignUpActivity)
	public void signUp() {

		TextView[] views = {/* firstName, lastName,*/ email, password, confirmPassword };
		String[] msg = {/*"Firstname cannot be blank!","Last cannot be blank!", */"Email cannot be blank!", "Password cannot be blank!",
				"Confirm Password cannot be blank!" };

		if (!mCommonFunction.validateAllFields(views, msg)) {
			return;
		}

		if (!mCommonFunction.isEmailValid(email)) {
			mCommonFunction.showToastMessageShort("Invalid E-mail id!");
			return;
		}

		if (!password.getText().toString()
				.equals(confirmPassword.getText().toString())) {
			mCommonFunction
					.showToastMessageShort("Password and Confirm Password does not match!");
			return;
		}

		mCommonFunction.dismissKeyboard(password);	

		 register();

	}
	
	@OnClick(R.id.SignUp_cancel)
	public void clickCancel()
	{
		finish();
	}

	public void register() {

		HashMap<String, PostObject> map = new HashMap<String, PostObject>();
		PostObject _resource = getPostObject("signup?", false);
		
	/*	PostObject _firstName = getPostObject(firstName.getText().toString(), false);
		PostObject _lastName = getPostObject(lastName.getText().toString(), false);	*/		
	//	PostObject _firstName = getPostObject("", false);
		PostObject _access_token = getPostObject(Constants.Access_token, false);
	//	PostObject _lastName = getPostObject("", false);		
		PostObject _email = getPostObject(email.getText().toString(), false);
		PostObject _passwd = getPostObject(password.getText().toString(), false);
		PostObject _conPass = getPostObject(confirmPassword.getText().toString(), false);
		
		//first_name=Raju&last_name=Sen&email_address=raju.sen@dreamztech.com&confirm_email_address=raju.sen@dreamztech.com&password=1234567&confirm_password=1234567",

		map.put("resource", _resource);
		map.put("access_token", _access_token);
	//	map.put("first_name", _firstName);
	//	map.put("last_name", _lastName);
		map.put("email_address", _email);
		map.put("confirm_email_address", _email);
		map.put("password", _passwd);
		map.put("confirm_password", _conPass);

		postTowebservice(_listener, map);

	}

	AsyncTaskListener _listener = new AsyncTaskListener() {

		@Override
		public void onTaskPreExecute() {
			// TODO Auto-generated method stub
			showProgressMessage(getResources().getString(R.string.app_name),
					"Please wait... Signing Up...");
		}

		@Override
		public void onTaskCompleted(String result) {
			// TODO Auto-generated method stub

			hideProgressDialog();
			
			SignUpDAO signData = new SignUpDAO();

			try {

				Log.e(TAG, result);
				JSONObject jsonObject = new JSONObject(result);
				
				String status = jsonObject.optString("status");
	
				if(status.contains("true")){
					 access_key=jsonObject.optString("access_key");
					 Constants.Access_key=access_key;
				}
				if(status.equalsIgnoreCase("true"))
				{
					Constants.USER_ID=jsonObject.optString("users_id");
					
				
					JSONObject systemInfo = jsonObject.optJSONObject("system_info");
					JSONObject postData = systemInfo.optJSONObject("post_data");
					
					String username = email.getText().toString().trim();
					String pass = password.getText().toString().trim();
		/*			if(postData.has("first_name"))
					{
					signData.setFirstName(postData.optString("first_name"));
					}
					
					if(postData.has("first_name"))
					{
						signData.setLastName(postData.optString("last_name"));
					}
					
					if(postData.has("first_name"))
					{
					signData.setFirstName(postData.optString("first_name"));
					}
					
					signData.setEmailAddress(postData.optString("email_address"));
					signData.setPassword(postData.optString("password"));*/
					
					
					signData.setUserid(Constants.USER_ID);
					signData.setEmailAddress(username);
					signData.setPassword(pass);
					
					System.out.println(" Server Response :"+jsonObject.toString()); 
					
					SignUpModule.insertSignupTable(signData,databaseObject);
					
					int loginStatus = LoginModule.getLogin(signData,databaseObject);
					
					
					if(loginStatus == 1){
					
						
						Constants.LOGIN_MAIL_ID = username;
						
						SaveSharedPreferences.saveLoginDetail(SignUpActivity.this,username,pass,Constants.USER_ID,access_key);
						
					//	Alert.showAlertSignIn(SignUpActivity.this, "You have successfully signed up.",mCommonFunction);
						mCommonFunction.showIntent(TabActivity.class);
						finish();
					}
					
					databaseObject.closeDataBase();
					
					//mCommonFunction.showIntent(TabActivity.class);
				}
				else
				{
					//JSONObject task = jsonObject.optJSONObject("tasks");
					//JSONArray messageArr = task.optJSONArray("password");
				//	String message = messageArr.optString(0);
										
					Alert.showAlert(SignUpActivity.this, "This email address already exist! You should try another one");
				}
				

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}
	};
		

}
