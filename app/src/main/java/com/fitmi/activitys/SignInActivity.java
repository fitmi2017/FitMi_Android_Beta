package com.fitmi.activitys;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.db.DatabaseHelper;
import com.db.modules.LoginModule;
import com.db.modules.SignUpModule;
import com.db.modules.UnitModule;
import com.db.modules.UserInfoModule;
import com.dialog.Alert;
import com.dts.classes.AsyncTaskListener;
import com.dts.classes.JSONParser;
import com.dts.classes.PostObject;
import com.fitmi.R;
import com.fitmi.dao.CaloryBaselineDAO;
import com.fitmi.dao.NutritionTypeSetget;
import com.fitmi.dao.SignUpDAO;
import com.fitmi.dao.UnitItemDAO;
import com.fitmi.dao.UserInfoDAO;
import com.fitmi.utils.Constants;
import com.fitmi.utils.SaveSharedPreferences;
import com.ssl.MySSLSocketFactory;

import org.apache.http.NameValuePair;
import org.apache.http.HttpVersion;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.security.KeyStore;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

public class SignInActivity extends BaseActivity {

	private String TAG = "SignInActivity";

	@InjectView(R.id.Email)
	EditText email;

	String access_key = null;
	@InjectView(R.id.Password)
	EditText password;
	
	@InjectView(R.id.imageview_profiletest)
	ImageView imageview_profiletest;
	
	CheckBox checkBoxSave;
	UnitItemDAO unitDataWeight;
	UnitModule unitModel;
	
	UnitItemDAO unitDataHeight;
	UnitItemDAO unitDataBp;
	UnitItemDAO unitDataFood_Weight;
	
	String weight_unit;
	ArrayList<UnitItemDAO> unitItem;
	@Override
	public void onResume() {
		super.onResume();

	};
	DatabaseHelper databaseObject;
	
	int loginStatus = 0;
	
	String username = "";
	String pass = "";
	boolean checked = false;

	Calendar dobDate;
	private static int mYear;
	private static int mMonth;
	private static int mDay;
	
	float inchVal=0,ftVal,cmVal=0;
	float LbsVal=0,KgVal=0;
	float BMRVal=0;
	int _ftintVal=0;
	private int years;
	private double IntakeVal;

	private double BurnedVal; 
	ProgressDialog pDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signin);

		prepareKnives();
		
		//email = (EditText)findViewById(R.id.Email);
		//password = (EditText)findViewById(R.id.Password);
		
		//email.setText("test1@gmail.com");
		//password.setText("1234567");


		databaseObject = new DatabaseHelper(SignInActivity.this);
		try {

			databaseObject.createDatabase();

			databaseObject.openDatabase();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		unitModel = new UnitModule(this);
		
		

	
		
		password.setOnEditorActionListener(new OnEditorActionListener() {
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
						|| (actionId == EditorInfo.IME_ACTION_DONE)) {
					Log.i(TAG, "Enter pressed");
					signIn();
				}
				return false;
			}
		});
		
		pDialog = new ProgressDialog(this);
		
		checkBoxSave = (CheckBox)findViewById(R.id.checkBoxSave);
		checkBoxSave.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton btn, boolean arg1) {
				// TODO Auto-generated method stub	
				
				if(arg1){
					
					username = email.getText().toString().trim();
					pass = password.getText().toString().trim();
					
					checked = true;
					
					btn.setButtonDrawable(R.drawable.checkiconselected);
					SaveSharedPreferences.saveLoginDetail(SignInActivity.this,username,pass,Constants.USER_ID,access_key);
				}
				else{
					btn.setButtonDrawable(R.drawable.checkcon);
					checked = false;
					SaveSharedPreferences.saveLoginDetail(SignInActivity.this,"","","","");
				}					
			}
		});

	}

	@OnClick(R.id.SignUp_SignInActivity)
	public void signUp() {

		mCommonFunction.showIntent(SignUpActivity.class);

	}

	@OnClick(R.id.ForgotPassword_SignInActivity)
	public void forgotPassword() {

		final Dialog dialog = new Dialog(SignInActivity.this,
				android.R.style.Theme_Light_NoTitleBar);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_forgot_password);
		 final EditText editText2=(EditText)dialog.findViewById(R.id.editText2);
		
		((Button) dialog.findViewById(R.id.Cancel))
		.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				dialog.dismiss();

			}
		});

		((Button) dialog.findViewById(R.id.Send))
		.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String valemail=editText2.getText().toString();
				
				AsyncForgetpassword asyncForgetpassword=new AsyncForgetpassword(JSONParser.HOST_URL_DYNAMIC+"fitmiwebservice/index.php/authentication/forgot_password?",valemail);
				dialog.dismiss();
				asyncForgetpassword.execute("exe");
			}
		});

		dialog.show();

	}

	@OnClick(R.id.SignIn_SignInActivity)
	public void signIn() {

		TextView[] views = { email, password };
		String[] msg = { "Email cannot be blank!", "Password cannot be blank!" };

		if (!mCommonFunction.validateAllFields(views, msg)) {
			return;
		}

		if (!mCommonFunction.isEmailValid(email)) {
			mCommonFunction.showToastMessageShort("Invalid email address!");
			return;
		}

		mCommonFunction.dismissKeyboard(password);
		
		Constants.LOGIN_MAIL_ID = email.getText().toString();
		
		SignUpDAO signData = new SignUpDAO();
		signData.setEmailAddress(email.getText().toString());
		signData.setPassword(password.getText().toString());
		
		
		loginStatus = LoginModule.getLogin(signData,databaseObject);
		
		if(checked){
			SaveSharedPreferences.saveLoginDetail(SignInActivity.this,username,pass,Constants.USER_ID,access_key);
		}else{
			SaveSharedPreferences.saveLoginDetail(SignInActivity.this,"","","","");
		}
		

		
		if(loginStatus == 1){
			
			username = email.getText().toString().trim();
			pass = password.getText().toString().trim();
			
			SaveSharedPreferences.saveLoginDetail(SignInActivity.this,username,pass,Constants.USER_ID,access_key);
			mCommonFunction.showIntent(TabActivity.class);
			finish();
		}
		else
			login();
			
			//Alert.showAlert(SignInActivity.this, "Username/password does not match.");
		
		//mCommonFunction.showIntent(TabActivity.class);
		
		databaseObject.closeDataBase();

		

	}

	public void login() {

		HashMap<String, PostObject> map = new HashMap<String, PostObject>();
		PostObject _resource = getPostObject("login?", false);
		PostObject _email = getPostObject(email.getText().toString(), false);
		PostObject _password = getPostObject(password.getText().toString(),
				false);

		map.put("resource", _resource);
		map.put("email_address", _email);
		map.put("password", _password);

		postTowebservice(_listener, map);

	}

	AsyncTaskListener _listener = new AsyncTaskListener() {

		@Override
		public void onTaskPreExecute() {
			// TODO Auto-generated method stub
			showProgressMessage(getResources().getString(R.string.app_name),
					"Please wait... Signing In...");
		}

		@Override
		public void onTaskCompleted(String result) {
			// TODO Auto-generated method stub

			//hideProgressDialog();

			try {
				
				SignUpDAO signData = new SignUpDAO();

				Log.e(TAG, result);
				JSONObject jsonObject = new JSONObject(result);
				
				String status = jsonObject.optString("status");
				if(status.contains("true")){
					 access_key=jsonObject.optString("access_key");
					 Constants.Access_key=access_key;
				}
				
				if(status.equalsIgnoreCase("true"))
				{
					JSONObject systemInfo = jsonObject.optJSONObject("system_info");
					
					
					Constants.USER_ID=jsonObject.optString("users_id");
					
					JSONObject postData = systemInfo.optJSONObject("post_data");
					
				/*	signData.setFirstName(postData.optString("first_name"));
					signData.setLastName(postData.optString("last_name"));
					signData.setEmailAddress(postData.optString("email_address"));
					signData.setPassword(postData.optString("password"));*/
					
					String emailStr = email.getText().toString();
					String passStr = password.getText().toString();
					
					signData.setEmailAddress(emailStr);
					signData.setPassword(passStr);
					
					signData.setUserid(Constants.USER_ID);
					System.out.println(" Server Response :"+jsonObject.toString()); 
					
					SignUpModule.insertSignupTable(signData,databaseObject);
					
					int loginStatus = LoginModule.getLogin(signData,databaseObject);
					
					if(loginStatus == 1){
						
					//	AsyncGetUserProfile asyncGetUserProfile =new AsyncGetUserProfile("http://59.162.181.91/portfolio/fitmiwebservice/index.php/get/user/profile?", Constants.Access_token, access_key);
						
					//	asyncGetUserProfile.execute("execute");
						
					/*	String username = postData.optString("email_address");
						String pass = postData.optString("password");*/
						
						String username = emailStr;
						String pass = passStr;
						
						Constants.LOGIN_MAIL_ID = username;
						
						SaveSharedPreferences.saveLoginDetail(SignInActivity.this,username,pass,Constants.USER_ID,access_key);
						getUserDetails(access_key, Constants.Access_token, emailStr, JSONParser.HOST_URL_DYNAMIC+"fitmiwebservice/index.php/get/user/profile");
					//	mCommonFunction.showIntent(TabActivity.class);
					//	finish();
					}
					
					System.out.println("Login detail :"+jsonObject.toString()); 
					//mCommonFunction.showIntent(TabActivity.class);
				}else{
					hideProgressDialog();
					Toast.makeText(SignInActivity.this, "User name/password incorrect", Toast.LENGTH_LONG).show();
				}
				
			//	Constants.LOGIN_MAIL_ID = username;
				//mCommonFunction.showIntent(TabActivity.class);
				

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} 

		}
	};


	  
	   

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

		
		  private class AsyncForgetpassword extends AsyncTask<String, Void, String> {
			   
			   String JSON_URL;
			   JSONObject holder = new JSONObject();
			   String email;
			  
			   public AsyncForgetpassword(String url ,String email) {
				// TODO Auto-generated constructor stub
				   JSON_URL=url;
				 
				   this.email=email;
			}
			   @Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				   pDialog.setMessage("Requesting..."); pDialog.show();
				   
				   super.onPreExecute();
			}
			    @SuppressWarnings({ "deprecation", "unchecked" })
				@Override
			    protected String doInBackground(String... urls) {
			    	// placesAutoCompleteAdapter.notifyDataSetInvalidated();
					
					String SetServerString = "";
					HttpURLConnection conn = null;


						DefaultHttpClient Client = getNewHttpClient();
						HttpPost httpost = new HttpPost(JSON_URL);
					StringEntity se = null;
					try {
						se = new StringEntity(holder.toString());
					} catch (UnsupportedEncodingException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
	
						// sets the post request as the resulting string
				//	BasicNameValuePair basicNameValuePair = new BasicNameValuePair("", value);
					// NameValuePair _access_token = new NameValuePair("access_token", access_token);
				 
				
					
					 List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					    nameValuePairs.add(new BasicNameValuePair("email_address",
					    		email));
				
					
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
			   Log.e("Response  AsyncForgetpassword ", result);
					
					
			   try {
				JSONObject jsonObject = new JSONObject(result);
				
				String status = jsonObject.optString("status");
				if(status.equalsIgnoreCase("true"))
				{
					Alert.showAlertForgotPass(SignInActivity.this, jsonObject.optString("message"),mCommonFunction);
				}
				
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			    }
			  }


		  
		  private void getUserDetails(final String acc_key,final String acc_token, final String username, String Json_Url){
		   
			  //	Json_Url = "http://192.168.1.34/WS/check.php/WS/check.php";
			  Log.e("JSON URZL", Json_Url);
		 
		        StringRequest stringRequest = new StringRequest(Request.Method.POST, Json_Url,
		                new Response.Listener<String>() {
		                    @Override
		                    public void onResponse(String response) {
		                     //   Toast.makeText(SignInActivity.this,response,Toast.LENGTH_LONG).show();
		                    	
		                    /*	showProgressMessage(getResources().getString(R.string.app_name),
		        						"Please wait......");*/
		                    	Log.e("response using volley", response.toString());
		                    	
		                    	String _dob;
		                    	UserInfoDAO userInfoData = new UserInfoDAO();
		                    	String userProfileId = "";
		                    	  try {
		              				JSONObject jsonObject = new JSONObject(response);
		              				
		              				String status = jsonObject.optString("status");
		              				if(status.equalsIgnoreCase("true"))
		              				{
		              					JSONObject jsonResult =jsonObject.getJSONObject("result");
		              					
		              					userInfoData.setProfileId(jsonResult.getString("id"));
		              					Constants.PROFILE_ID=jsonResult.getString("id");
		              					userInfoData.setUserId(Constants.USER_ID);
		              					userInfoData.setFirstName(jsonResult.getString("first_name"));
		              					userInfoData.setLastName(jsonResult.getString("last_name"));
		              					userInfoData.setGender(jsonResult.getString("gender"));
		              					userInfoData.setHeightFt(jsonResult.getString("height_ft"));
		              					
		              					userInfoData.setHeightInc(jsonResult.getString("height_in"));
		              					userInfoData.setWeight(jsonResult.getString("weight"));
		              					weight_unit=(jsonResult.getString("weight_units"));
		              					_dob=(jsonResult.getString("date_of_birth"));
		              					Log.e("data of birth timestamp ", _dob);
		              					
		              					long dob_=Long.parseLong(_dob);
		              					_dob=getDate(dob_);
		              					
		              					
		              					Log.e("data of birth timestamp to string ", _dob);
		              					String dateString = _dob;		
		              					
		              					SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyy");  
		              					try {  
		              						Date date = format.parse(dateString);


		              						String stringMonth = (String) android.text.format.DateFormat.format("MMM", date); //Jun			   
		              						String stringyear = (String) android.text.format.DateFormat.format("yyyy", date); //2013
		              						String stringday = (String) android.text.format.DateFormat.format("dd", date); //20

		              						_dob=stringday+"/"+stringMonth+"/"+stringyear;
		              						
		              					/*	Calendar cal = Calendar.getInstance();
		              						cal.setTime(date);


		              						mYear = cal.get(Calendar.YEAR);
		              						mMonth = cal.get(Calendar.MONTH);
		              						mDay = cal.get(Calendar.DAY_OF_MONTH);

		              						dobDate = Calendar.getInstance();				
		              						dobDate.set(mYear, mMonth, mDay);*/
		              						System.out.println(date);  
		              					} catch (Exception e) {  
		              						// TODO Auto-generated catch block  
		              						e.printStackTrace();  
		              					}
		              					Log.e("data of birth timestamp to string android ", _dob);
		              					
		              					userInfoData.setDateOfBirth(_dob);
		              					userInfoData.setActivityLevel(jsonResult.getString("activity_level"));
		              					userInfoData.setDailyCaloryIntake(jsonResult.getString("daily_calorie_intake"));
		              					//userInfoData.setPicturePath(jsonResult.getString("profile_image"));
		              					
		              				
		              					
		              				//	UserInfoModule.insertUserInformationProfileLogin(userInfoData,databaseObject);		

		              				
		              					//SaveSharedPreferences.saveProfileID(SignInActivity.this, Constants.PROFILE_ID);
		              					
		              					
		              				
		              					
		              					CaloryBaselineDAO baseLineData = new CaloryBaselineDAO();	

		              					baseLineData.setUserProfileId(userProfileId);
		              					baseLineData.setUserId(Constants.USER_ID);
		              					//baseLineData.setTotalIntake(String.valueOf(IntakeVal));
		              					//baseLineData.setTotalBurned(String.valueOf(BurnedVal));
		              					baseLineData.setTotalIntake(jsonResult.getString("daily_calorie_intake"));
		              					baseLineData.setTotalBurned("0");
		              					baseLineData.setWeight(jsonResult.getString("weight"));
		              					baseLineData.setWater("8");
		              					baseLineData.setSleep("7");
		              					baseLineData.setBpDia("80");
		              					baseLineData.setBpSys("120");
		              					
		              					if(jsonResult.getString("profile_image").length()>0&& !jsonResult.getString("profile_image").equalsIgnoreCase("null"))
		              					{
		              						getUserProfileImage(jsonResult.getString("profile_image"), userInfoData, baseLineData);
		              					}else{
		              						hideProgressDialog();
		              						userInfoData.setPicturePath("");
		              						UserInfoModule.insertUserInformationProfileLogin(userInfoData,databaseObject);
		              						userProfileId = UserInfoModule.getProfileId(databaseObject);
			              					Constants.PROFILE_ID = userProfileId;
			              					
		              						SaveSharedPreferences.saveProfileID(SignInActivity.this, Constants.PROFILE_ID);
		              						baseLineData.setUserProfileId(Constants.PROFILE_ID);
		        		            		baseLineData.setUserId(Constants.USER_ID);
		              						UserInfoModule.insertCaloryBaseline(baseLineData, databaseObject);
		              						
		              						
		              						
		              						unitDataWeight = new UnitItemDAO();
		              						
		              						if(weight_unit.equalsIgnoreCase("Lbs"))
		              						{
		              							Constants.gunitwt = 1;
		              							unitDataWeight.setUnitId("3");
		              						}else{
		              							Constants.gunitwt = 0;
		              							unitDataWeight.setUnitId("4");
		              						}
		              						
		              						unitDataWeight.setProfileId(Constants.PROFILE_ID);
		              						unitDataWeight.setUserId(Constants.USER_ID);
		              						unitDataWeight.setType("weight");
		              						
		              						
		              						
		              						
		              						
		              						Constants.gunitfw = 0;
		              						
		              						unitDataFood_Weight=new UnitItemDAO();
		              						
		              						unitDataFood_Weight.setProfileId(Constants.PROFILE_ID);
		              						unitDataFood_Weight.setUserId(Constants.USER_ID);
		              						unitDataFood_Weight.setType("food_weight");
		              						unitDataFood_Weight.setUnitId(String.valueOf(7));
		              						
		              						
		              						
		              					
		              						
		              						
		              						Constants.gunitbp = 0;
		              						

		              						unitDataBp = new UnitItemDAO();

		              						unitDataBp.setProfileId(Constants.PROFILE_ID);
		              						unitDataBp.setUserId(Constants.USER_ID);
		              						unitDataBp.setType("blood_pressure");
		              						unitDataBp.setUnitId(String.valueOf(5));
		              						
		              						
		              						
		              						
		              						
		              						Constants.gunitht = 0;
		              						
		              						unitDataHeight = new UnitItemDAO();

		              						unitDataHeight.setProfileId(Constants.PROFILE_ID);
		              						unitDataHeight.setUserId(Constants.USER_ID);
		              						unitDataHeight.setType("height");
		              						unitDataHeight.setUnitId(String.valueOf(1));
		              						
		              						

		              						unitModel.setUnitLog(unitDataHeight);
		              						unitModel.setUnitLog(unitDataWeight);
		              						unitModel.setUnitLog(unitDataBp);
		              						unitModel.setUnitLog(unitDataFood_Weight);
		              						
		              						
		              						unitItem = unitModel.selectUnitLogList();
		              						Log.e("Size of unit database ",String.valueOf(unitItem.size()));
		              						
		              						 mCommonFunction.showIntent(TabActivity.class);
				          						finish();
		              					}
		              				
		              				//	UserInfoModule.insertCaloryBaseline(baseLineData, databaseObject);
		              				}else{
		              					
		              					hideProgressDialog();
		              					 mCommonFunction.showIntent(TabActivity.class);
			          						finish();
		              				}
		              				
		              				
		              				
		              			} catch (JSONException e) {
		              				// TODO Auto-generated catch block
		              				e.printStackTrace();
		              			}
		                    	  
		                    	 // if(Constants.PROFILE_ID.length()>0)
		                    	//  {
		                    		 
		                    	//  }
		                    }
		                },
		                new Response.ErrorListener() {
		                    @Override
		                    public void onErrorResponse(VolleyError error) {
		                        Toast.makeText(SignInActivity.this,error.toString(),Toast.LENGTH_LONG).show();
		                    }
		                }){
		            @Override
		            protected Map<String,String> getParams(){
		                Map<String,String> params = new HashMap<String, String>();
		                
		              
		                params.put("access_key",acc_key);
		               params.put("access_token",acc_token);
		               params.put("users_id",Constants.USER_ID);
		               params.put("username",username);
		               
		               
		               Log.e("access_key",acc_key);
		               Log.e("access_token",acc_token);
		               Log.e("users_id",Constants.USER_ID);
		               Log.e("username",username);
		                return params;
		            }
		            
		            @Override
		            		public Map<String, String> getHeaders()
		            				throws AuthFailureError {
		            			// TODO Auto-generated method stub
		                Map<String,String> params = new HashMap<String, String>();
		                // Removed this line if you dont need it or Use application/json
		             //   params.put("Accept", "application/x-www-form-urlencoded");
		             //   params.put("Content-Type", "UTF-8");
		             //   params.put("access_key",acc_key);
		          //      params.put("access_token",acc_token);
		                return params;		
		            	//return super.getHeaders();
		            		}
		            
		 
		        };
		 
		        RequestQueue requestQueue = Volley.newRequestQueue(this);
		        requestQueue.add(stringRequest);
		    }
		 
		  private String getDate(long timeStamp){

			    try{
			        DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			        Date netDate = (new Date(timeStamp));
			        return sdf.format(netDate);
			    }
			    catch(Exception ex){
			    	
			    	
			        return "xx";
			    }
			} 
		  
		  
		  public void getUserProfileImage(String imageName,final UserInfoDAO userInfoData,final CaloryBaselineDAO baseLineData){
			  
			     ImageRequest imgRequest = new ImageRequest(JSONParser.IMAGE_URL+imageName, new Response.Listener<Bitmap>() {
		             @Override
		             public void onResponse(Bitmap response) {
		                    //do stuff
		            	 File newfile = null;
		            	 try {
							 newfile = savebitmap(response);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

		            		hideProgressDialog();



						if (newfile != null) {
							Log.e("File Path", "" + newfile.getAbsolutePath());

							userInfoData.setPicturePath(newfile.getAbsolutePath());
						}
		            String	userProfileId; 
		            UserInfoModule.insertUserInformationProfileLogin(userInfoData,databaseObject);	
		            		userProfileId = UserInfoModule.getProfileId(databaseObject);
          					Constants.PROFILE_ID = userProfileId;
          					
		            		SaveSharedPreferences.saveProfileID(SignInActivity.this, Constants.PROFILE_ID);
		            		baseLineData.setUserProfileId(Constants.PROFILE_ID);
		            		baseLineData.setUserId(Constants.USER_ID);
          					UserInfoModule.insertCaloryBaseline(baseLineData, databaseObject);
          					
          					Log.e("baseLineData calorie ", baseLineData.getTotalIntake());
          					
        /*  					unitDataWeight = new UnitItemDAO();
      						com.fitmi.utils.Constants.gunitwt = 1;	
      						unitDataWeight.setProfileId(Constants.PROFILE_ID);
      						unitDataWeight.setUserId(Constants.USER_ID);
      						unitDataWeight.setType("weight");
      						unitDataWeight.setUnitId("4");*/
      						
          					unitDataWeight = new UnitItemDAO();
      						
      						if(weight_unit.equalsIgnoreCase("Lbs"))
      						{
      							Constants.gunitwt = 1;
      							unitDataWeight.setUnitId("3");
      						}else{
      							Constants.gunitwt = 0;
      							unitDataWeight.setUnitId("4");
      						}
      						
      						
      						unitDataWeight.setProfileId(Constants.PROFILE_ID);
      						unitDataWeight.setUserId(Constants.USER_ID);
      						unitDataWeight.setType("weight");
      						
      						
      						Constants.gunitfw = 0;
      						
      						unitDataFood_Weight=new UnitItemDAO();
      						
      						unitDataFood_Weight.setProfileId(Constants.PROFILE_ID);
      						unitDataFood_Weight.setUserId(Constants.USER_ID);
      						unitDataFood_Weight.setType("food_weight");
      						unitDataFood_Weight.setUnitId(String.valueOf(7));
      						
      						
      						
      					
      						
      						
      						Constants.gunitbp = 0;
      						

      						unitDataBp = new UnitItemDAO();

      						unitDataBp.setProfileId(Constants.PROFILE_ID);
      						unitDataBp.setUserId(Constants.USER_ID);
      						unitDataBp.setType("blood_pressure");
      						unitDataBp.setUnitId(String.valueOf(5));
      						
      						
      						
      						
      						
      						Constants.gunitht = 0;
      						
      						unitDataHeight = new UnitItemDAO();

      						unitDataHeight.setProfileId(Constants.PROFILE_ID);
      						unitDataHeight.setUserId(Constants.USER_ID);
      						unitDataHeight.setType("height");
      						unitDataHeight.setUnitId(String.valueOf(1));
      						
      						

      						unitModel.setUnitLog(unitDataHeight);
      						unitModel.setUnitLog(unitDataWeight);
      						unitModel.setUnitLog(unitDataBp);
      						unitModel.setUnitLog(unitDataFood_Weight);
      						
      						
      						unitItem = unitModel.selectUnitLogList();
      						Log.e("Size of unit database ",String.valueOf(unitItem.size()));
      						
          					 mCommonFunction.showIntent(TabActivity.class);
       						finish();
          						
		                }
		            }, 0, 0, Bitmap.Config.ARGB_8888, 
		             new Response.ErrorListener() {
		             @Override
		             public void onErrorResponse(VolleyError error) {
		                   //do stuff
		                }
		            });
			     RequestQueue requestQueue = Volley.newRequestQueue(this);
			        requestQueue.add(imgRequest);
			     
		  }
		  public static File savebitmap(Bitmap bmp) throws IOException {
			    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			    bmp.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
			    
				String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
				String imageFileName = "JPEG_" + timeStamp + "_";
				File storageDir = Environment.getExternalStoragePublicDirectory(
						Environment.DIRECTORY_PICTURES);
				File image = File.createTempFile(
						imageFileName, /* prefix */
						".jpg", /* suffix */
						storageDir /* directory */
						);
			  
				image.createNewFile();
			    FileOutputStream fo = new FileOutputStream(image);
			    fo.write(bytes.toByteArray());
			    fo.close();
			    return image;
			}
}
