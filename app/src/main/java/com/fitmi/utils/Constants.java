package com.fitmi.utils;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.InjectView;

import com.fitmi.R;
import com.fitmi.dao.FitmiFoodLogDAO;

import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

public class Constants {

	// 1 Gram = 0.035274 Ounces.
	public static final String ACTION_SCALE_SUCCESSFULLY_CONNECTED = "ACTION_SCALE_SUCCESSFULLY_CONNECTED";
	public static boolean isSync = false;
	public static boolean CheckSyncFirstTime = true;
	public static String sTempDate = "";
	public static String sDate = "";
	public static String postDate = "";	
	public static String postDateOnly = "";	
	public static String conditionDate = "";
	public static final int FOOD_LOGGING_PLUS = 1;
	public static final int ACTIVITY_LOGGING_PLUS = 2;
	public static final int WEIGHT_LOGGING_PLUS = 3;
	public static String PROFILE_ID = "";
	
	public static final int HomeRootId = 0;
	public static String Access_token="fe32981fa08335a8ba36cd45f4ab505d";
	public static String Access_key="";
	
	public static int gm_oz = 0;
	
	
	public static String USER_ID = "0";
	public static String TOTAL_CALORY_INTAKE = "";
	public static String TOTAL_CALORY_BURN = "";
	public static TextView txtViewtotalCalory;
	public static TextView txtViewtotalCaloryBurn;
	public static TextView txtHomeActivityGraph;
	public static TextView txtHomeSleep;
	public static TextView homeOzValue;
	public static TextView homeCupValue;
	public static TextView homeCaloryIntake;
	public static TextView remainCaloryBurn;
	public static TextView foodcalorieText;
	public static String LOGIN_MAIL_ID = "";
	public static int fragmentNumber = 0;
	public static int totalcal;
	public static int broadCastReturn = -1; 
	public static int calendreSet = 0;
	public static int pageLog = 0;	
	public static boolean activityLog = false;
	
	public static TextView txtviewHomeFoodGraph;
	
	
	public static LinearLayout homeLinear;
	public static LinearLayout profileLinear;
	public static LinearLayout activityLinear;
	public static LinearLayout helpLinear;
	public static LinearLayout calendarLinear;
	public static ImageView HomeImg_Tab;	
	public static ImageView ProfileImg_Tab;
	public static ImageView ActivityImg_Tab;
	public static ImageView HelpImg_Tab;
	public static ImageView CalendarImg_Tab;
	
	public static int fromFragment = 0;
	public static boolean fragmentSet = false;
	public static boolean shareIntent = false;
	
	//unit type selection
	public static int gunitwt = 0;	
	public static int gunitht = 0;	
	public static int gunitbp = 0;	
	public static int gunitfw = 0;	
	
	public static int connectedTodevice = 0;	
	public static int isBluetoothOn = 0;
	public static int isBluetoothOnLocal = 0;
	
	public static ArrayList<FitmiFoodLogDAO> foodLogData = new ArrayList<FitmiFoodLogDAO>();
	public static ArrayList<ArrayList<HashMap<String, String>>> activityLogData = new ArrayList<ArrayList<HashMap<String, String>>>();
	
	
	//avinash
	//https://api.nutritionix.com/v1_1/search/Rice?fields=item_name,brand_name,nf_calories,nf_serving_size_qty,
	//item_description,nf_serving_size_unit,nf_serving_weight_grams,nf_ingredient_statement&appId=b65138b3&appKey=220fa746203ea5217abff5970c9f8d43
	public static String searchUrpart1 = "https://api.nutritionix.com/v1_1/search/";
	public static String searchUrpart2 = "?fields=item_name,brand_name,nf_calories,nf_serving_size_qty,item_description,nf_serving_size_unit,nf_serving_weight_grams,nf_ingredient_statement&appId=b65138b3&appKey=220fa746203ea5217abff5970c9f8d43";
	//end
}
