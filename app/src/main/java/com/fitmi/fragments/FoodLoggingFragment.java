package com.fitmi.fragments;

import it.sephiroth.demo.slider.widget.MultiDirectionSlidingDrawer;
import it.sephiroth.demo.slider.widget.MultiDirectionSlidingDrawer.OnDrawerCloseListener;
import it.sephiroth.demo.slider.widget.MultiDirectionSlidingDrawer.OnDrawerOpenListener;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.security.KeyStore;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.text.WordUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Entity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.speech.RecognizerIntent;
import android.support.annotation.ColorRes;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnFocusChange;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.callback.Item;
import com.callback.MealFavNotify;
import com.callback.NotificationCalorieIntake;
import com.custom.view.CustomListView;
import com.custom.view.CustomListView.OnItemDoubleTapLister;
import com.db.DatabaseHelper;
import com.db.modules.ActivityModule;
import com.db.modules.FoodLoginModule;
import com.db.modules.UnitModule;
import com.db.modules.UserInfoModule;
import com.dts.classes.CommonFunction;
import com.dts.utils.Constants;
import com.fitmi.R;
import com.fitmi.activitys.DeviceSyncService;
import com.fitmi.activitys.TabActivity;
import com.fitmi.adapter.ActivityLoggingSpinnerAdapter;
import com.fitmi.adapter.EntryFavMealAdapter;
import com.fitmi.adapter.EntryFavMealItemAdapter;
import com.fitmi.adapter.EntryFoodLoginAdapter;
import com.fitmi.adapter.EntryRecentFoodMealAdapter;
import com.fitmi.adapter.EntryRecentMealAdapter;
import com.fitmi.adapter.FavFoodAdapter;
import com.fitmi.adapter.FoodAdapter;
import com.fitmi.adapter.FoodLoggingAdapter;
import com.fitmi.adapter.FoodLoggingFavSpinnerAdapter;
import com.fitmi.adapter.FoodLoggingRecentSpinnerAdapter;
import com.fitmi.adapter.FoodLoggingSpinnerAdapter;
import com.fitmi.adapter.NutritionAdapter;
import com.fitmi.adapter.NutritionAdapterBoldText;
import com.fitmi.adapter.RecentFoodAdapter;
import com.fitmi.adapter.TotalFoodFooterAdapter;
import com.fitmi.dao.ExerciseItemDAO;
import com.fitmi.dao.FitmiFoodDAO;
import com.fitmi.dao.FitmiFoodLogDAO;
import com.fitmi.dao.MealTypeDAO;
import com.fitmi.dao.NutritionTypeSetget;
import com.fitmi.dao.SectionItemFoodlogin;
import com.fitmi.dao.UnitItemDAO;
import com.fitmi.dao.UserInfoDAO;
import com.fitmi.utils.DateModule;
import com.fitmi.utils.NotificationTotalCaloryChange;
import com.fitmi.utils.NotifyCalorieChange;
import com.ssl.MySSLSocketFactory;

public class FoodLoggingFragment extends BaseFragment implements
		NotifyCalorieChange, NotificationCalorieIntake, MealFavNotify {

	/*
	 * //unit selection Type UnitModule unitModel; ArrayList<UnitItemDAO>
	 * unitItem;
	 */
	ArrayList<FitmiFoodDAO> searchList1 = new ArrayList<FitmiFoodDAO>();
	PopupWindow mpopup2;
	@InjectView(R.id.inputSearch)
	EditText inputSearch;
	PopupWindow mpopup;
	int mRootId = 0;
	public String typeText = "";

	public static int foodSpinnerSelect = -1;
	public static int foodSpinnerSelectFav = -1;
	public static int foodSpinnerSelectRecent = -1;

	public int nextbtnVal = 0;

	String isFavMealVar;
	int _searchadded = 0;
	int callActivityResult = 0;
	boolean flingUpDown = false;

	ArrayList<NutritionTypeSetget> nutritionTypeDAOs = new ArrayList<NutritionTypeSetget>();

	String _food_name, _nf_calories, _serving_weight_grams, _nf_total_fat,
			_nf_saturated_fat, _nf_cholesterol, _nf_sodium,
			_nf_total_carbohydrate, _nf_dietary_fiber, _nf_sugars, _nf_protein,
			_nf_potassium, _nf_p;
	NutritionAdapter nutritionAdapter;
	NutritionAdapterBoldText nutritionAdapterBoldText;

	HashMap<String, String> datamap = new HashMap<String, String>();

	@InjectView(R.id.imgViewSnack)
	ImageView snacksImg;
	@InjectView(R.id.imgViewBreakfast)
	ImageView breakFastImg;
	@InjectView(R.id.imgViewLunch)
	ImageView lunchImg;
	@InjectView(R.id.imgViewDinner)
	ImageView dinnerImg;

	String mealIdfromTable = "";

	ArrayList<Button> addButton = new ArrayList<Button>();
	ArrayList<Item> items = new ArrayList<Item>();
	ArrayList<Item> itemsMeal = new ArrayList<Item>();

	FitmiFoodLogDAO fitmiFoodLogDataTemp = new FitmiFoodLogDAO();
	@InjectView(R.id.list_autocomplete)
	ListView list_autocomplete;

	PlacesAutoCompleteAdapter placesAutoCompleteAdapter;
	@InjectView(R.id.arrow)
	LinearLayout arrow;

	@InjectView(R.id.drawer)
	MultiDirectionSlidingDrawer drawer;

	@InjectView(R.id.searchMealLiner)
	LinearLayout searchMealLiner;

	@InjectView(R.id.selectMealLiner)
	LinearLayout selectMealLiner;

	@InjectView(R.id.addMealLiner)
	LinearLayout addMealLiner;

	@InjectView(R.id.layoutDailyCaloryEdit)
	LinearLayout layoutMealBurn;

	@InjectView(R.id.activity_linearlayout)
	LinearLayout activityLayout;

	@InjectView(R.id.favContainer)
	LinearLayout favContainer;

	@InjectView(R.id.frameFavShow)
	FrameLayout frameFavShow;

	@InjectView(R.id.Heading)
	public TextView heading;

	@InjectView(R.id.Back)
	public ImageView back;

	@InjectView(R.id.Settings)
	ImageView Settings;

	@InjectView(R.id.txtPreview)
	TextView txtPreview;

	@InjectView(R.id.txtNext)
	TextView txtNext;

	@InjectView(R.id.backLiner)
	LinearLayout backLiner;

	@InjectView(R.id.FoodLoggingSpinner)
	Spinner foodLoggingSpinner;

	@InjectView(R.id.spinnerFav)
	Spinner spinnerFav;

	@InjectView(R.id.spinnerRecent)
	Spinner spinnerRecent;

	@InjectView(R.id.ActivitySpinner)
	Spinner activitySpinner;

	@InjectView(R.id.TotalFrame_FoodLogging)
	LinearLayout total_frame;

	@InjectView(R.id.FoodLoggingListView)
	CustomListView foodLoggingListView;
	// swipable listview avinash
	@InjectView(R.id.FoodLoggingListView2)
	SwipeMenuListView foodLoggingListView2;

	@InjectView(R.id.listTotalFrame_FoodLogging)
	SwipeMenuListView listTotalFrame_FoodLogging;

	@InjectView(R.id.Recent_Food_Meals_ListView)
	ListView recentFoodMealsListView;

	@InjectView(R.id.ListBreakfastLinear)
	LinearLayout listBreakfastLinear;

	@InjectView(R.id.Recent_Food_Meals_Menu)
	LinearLayout recentFoodMealsMenu;

	@InjectView(R.id.Fav_Food_Meals_Menu)
	LinearLayout favFoodMealsMenu;

	@InjectView(R.id.RecentListView_ParentLinear)
	LinearLayout recentListViewParentLinear;

	@InjectView(R.id.RecentFoods_FoodLogging)
	Button recentFoods;

	@InjectView(R.id.RecentMeals_FoodLogging)
	Button recentMeals;

	@InjectView(R.id.Date)
	TextView dateTextView;

	@InjectView(R.id.imgAddMeal)
	ImageView imgAddMeal;

	public static int sFOODLOGGING_POS = -1;
	public static int CLICKRECENTITEM = -1;
	public static int CLICKFAVITEM = -1;

	public static int sFOODLOGGING_PrevPOS = -1;
	@InjectView(R.id.intake_linearlayout)
	LinearLayout intake_linearlayout;

	@InjectView(R.id.food_image)
	ImageView food_image;

	@InjectView(R.id.food_calorie_text)
	TextView food_calorie_text;

	@InjectView(R.id.activity_image)
	ImageView activity_image;

	@InjectView(R.id.activity_calorie_text)
	TextView activity_calorie_text;

	@InjectView(R.id.SearchEditText)
	AutoCompleteTextView searchEditText;

	@InjectView(R.id.totalCalory)
	TextView totalCalory;

	@InjectView(R.id.totalgram)
	TextView totalgram;

	@InjectView(R.id.foodTypeTitle)
	TextView foodTypeTitle;

	@InjectView(R.id.logBtn)
	Button logBtn;

	@InjectView(R.id.daily_calorie_text)
	TextView daily_calorie_text;

	@InjectView(R.id.remainCaloryBurn)
	TextView remainCaloryBurn;

	@InjectView(R.id.calorieTotalTop)
	TextView calorieTotalTop;

	@InjectView(R.id.favFood)
	Button favFood;

	@InjectView(R.id.favMeal)
	Button favMeal;

	@InjectView(R.id.recentFood)
	Button recentFood;

	@InjectView(R.id.recentMeal)
	Button recentMeal;

	@InjectView(R.id.fav_Food_Meals_ListView)
	ListView favListView;

	@InjectView(R.id.intake_linearlayout_fav)
	LinearLayout intake_linearlayout_fav;

	@InjectView(R.id.intake_linearlayout_recent)
	LinearLayout intake_linearlayout_recent;

	@InjectView(R.id.favourite)
	ImageView favouriteMeal;

	@InjectView(R.id.shadowFrame)
	LinearLayout shadowFrame;

	@InjectView(R.id.shadowFrame1)
	LinearLayout shadowFrame1;

	Integer[] fooddrawableValues;
	Integer[] activitydrawableValues;
	Integer[] fooddrawableValuesFav;

	String[] activityValues;
	String[] foodValues;
	String[] foodValuesFav;

	int mealId = -1;
	int mealIdSpinner = -1;
	float caloryCalculate = 0;
	String foodType = "Meal";
	boolean replace = false;
	boolean log = false;
	boolean addMealClick = false;
	boolean recentFoodClick = true;
	boolean recentMealClick = true;
	boolean favFoodClick = false;

	ArrayList<FitmiFoodDAO> searchList;// = new ArrayList<FitmiFoodDAO>();

	DatabaseHelper databaseObject;
	DatabaseHelper databaseObjectForFood;

	DateModule getDate = new DateModule();

	FoodLoginModule foodLogObj;
	FoodLoginModule foodLogObjForfood;

	String logTime = "";
	ArrayList<FitmiFoodLogDAO> foodListData = new ArrayList<FitmiFoodLogDAO>();
	ArrayList<FitmiFoodLogDAO> mealListData;

	ArrayList<FitmiFoodLogDAO> foodListDataAlies = new ArrayList<FitmiFoodLogDAO>();
	ArrayList<FitmiFoodLogDAO> _fitmifoodList = new ArrayList<FitmiFoodLogDAO>();

	ArrayList<FitmiFoodLogDAO> foodListRecent;
	ArrayList<FitmiFoodLogDAO> foodListRecentFav;
	ArrayList<FitmiFoodLogDAO> foodListRecentMeal = new ArrayList<FitmiFoodLogDAO>();
	ArrayList<FitmiFoodLogDAO> foodListRecentmeal = new ArrayList<FitmiFoodLogDAO>();
	ArrayList<FitmiFoodLogDAO> foodListRecentMealAdd = new ArrayList<FitmiFoodLogDAO>();;

	TotalFoodFooterAdapter totalFoodFooterAdapter;
	FoodLoggingAdapter flAdapter;
	RecentFoodAdapter recentFoodAdapter;
	EntryRecentMealAdapter recentMealAdapter;
	EntryFavMealItemAdapter recentMealItemAdapter;
	EntryFavMealAdapter favMealAdapter;
	EntryFoodLoginAdapter sectionAdapter;
	FavFoodAdapter favAdapter;
	FoodLoggingFavSpinnerAdapter favSpinnerAdapter;
	FoodLoggingRecentSpinnerAdapter recentSpinnerAdapter;
	FoodAdapter foodAdapter;
	FoodAdapter foodAdapterSearch;

	ProgressDialog pDialog;

	int initListSize = 0;

	boolean newDataAdd = false;

	int newDataCount = 0;

	Bundle bundle;

	ArrayList<String> calorySumList;
	ArrayList<ExerciseItemDAO> caloryBurnList;

	NotificationTotalCaloryChange callBack;
	private final int REQ_CODE_SPEECH_INPUT = 100;

	String caloryBurnInList = "";
	String caloryInTake = "";

	int remainCalory = 0;
	String today = "";
	String totalCaloty = "0";
	String currActivewt = "0";
	String currActiveCalwt = "0";
	ArrayList<String> demoSection = new ArrayList<>();

	boolean foodDropClick = false;
	boolean activityDropClick = false;
	boolean favDropClick = false;
	boolean recentDropClick = false;

	FoodLoggingSpinnerAdapter flsa;
	ArrayAdapter<String> adapter;
	Integer[] activitydrawableValuesAlies;
	char[] activityType;
	// ProgressDialog dialogp;
	Point p;
	
	
	//unit settings
	UnitModule unitModel;
	ArrayList<UnitItemDAO> unitItem;


	int unitIdHeight = 1;
	String unitTypeHeight = "height";

	int unitIdWeight = 3;
	String unitTypeWeight = "weight";

	int unitIdBp = 5;
	String unitTypeBp = "blood_pressure";

	int unitIdFw = 7;
	String unitTypeFw = "food_weight";
	
	UnitItemDAO unitDataHeight;
	UnitItemDAO unitDataWeight;
	UnitItemDAO unitDataBp;
	UnitItemDAO unitDataFood_Weight;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.fragment_food_logging, container,
				false);

		setNullClickListener(view);

		ButterKnife.inject(this, view);
		heading.setText("My Meal");

		// avinash changes date
		/*
		 * Calendar c = Calendar.getInstance(); SimpleDateFormat df = new
		 * SimpleDateFormat("yyyy-MM-dd"); today = df.format(c.getTime());
		 */
		System.out.println("com.fitmi.utils.Constants.sTempDate => "
				+ com.fitmi.utils.Constants.sTempDate);
		if (com.fitmi.utils.Constants.sTempDate.length() == 0) {
			// Constants.sDate = "Tuesday, February 10, 2015";

			Calendar c = Calendar.getInstance();
			System.out.println("Current time => " + c.getTime());
			// SimpleDateFormat df = new
			// SimpleDateFormat("YYYY-MMM-dd hh:mm:ss");
			// SimpleDateFormat df = new SimpleDateFormat("EEEE, MMM dd, yyyy");
			SimpleDateFormat df = new SimpleDateFormat("EEEE, MMM dd, yyyy");
			com.fitmi.utils.Constants.sDate = df.format(c.getTime());

			SimpleDateFormat postFormat = new SimpleDateFormat(
					"yyyy-MM-dd kk:mm:ss");
			com.fitmi.utils.Constants.postDate = postFormat.format(c.getTime());

			SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
			com.fitmi.utils.Constants.conditionDate = dformat.format(c
					.getTime());
			com.fitmi.utils.Constants.sTempDate = com.fitmi.utils.Constants.conditionDate;
			System.out.println("Calender post format :"
					+ com.fitmi.utils.Constants.postDate);
			// Toast.makeText(getActivity(), Constants.postDate,
			// Toast.LENGTH_LONG).show();

		} else {
			today = com.fitmi.utils.Constants.sTempDate;
		}
		com.fitmi.utils.Constants.conditionDate = today;

		com.fitmi.utils.Constants.homeCaloryIntake = daily_calorie_text;
		com.fitmi.utils.Constants.remainCaloryBurn = remainCaloryBurn;
		com.fitmi.utils.Constants.foodcalorieText = food_calorie_text;
		com.fitmi.utils.Constants.shareIntent = true;
		com.fitmi.utils.Constants.fragmentSet = false;

		Context context = ((TabActivity) getActivity());
		/*
		 * dialogp = new ProgressDialog(context); dialogp.setCancelable(true);
		 * dialogp.setMessage("Please wait ...");
		 * dialogp.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		 */
		if (com.fitmi.utils.Constants.gunitfw == 0) {
			totalgram.setText("0 g");
		} else {
			totalgram.setText("0 oz");
		}
		// logTime = getDate.getLogTime();

		// getActivity().registerReceiver(new FragmentReceiver(), new
		// IntentFilter("fragmentupdater"));

		getActivity().registerReceiver(dateSetReceiver,
				new IntentFilter("foodLogUpdate"));

		com.fitmi.utils.Constants.fragmentNumber = 1;

		databaseObject = new DatabaseHelper(getActivity());
		databaseObjectForFood = new DatabaseHelper(getActivity());
		try {

			databaseObject.createDatabase();

			databaseObject.openDatabase();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	//	exportDatabse("Fitmi.sqlite");
		
		/*
		 * totalFoodFooterAdapter = new TotalFoodFooterAdapter( getActivity(),
		 * currActivewt, currActiveCalwt, String .valueOf((int)
		 * caloryCalculate), "0");
		 */
		pDialog = new ProgressDialog(getActivity());
		/* pDialog.setMessage("Loading..."); pDialog.show(); */

		foodLogObj = new FoodLoginModule(getActivity());
		foodLogObjForfood = new FoodLoginModule(getActivity());
		bundle = this.getArguments();

		if (bundle != null) {
			mealId = bundle.getInt("mealid", 0);
			foodType = bundle.getString("foodtype");
			replace = bundle.getBoolean("replace");
			log = bundle.getBoolean("log");

			if (replace) {
				foodLogObj
						.deleteFoodLog(databaseObject, String.valueOf(mealId));
			}

			if (mealId == 0) {

				if (!log) {
					foodListData = foodLogObj.selectAllFoodList(databaseObject);
					setFoodlistData(foodListData, true);
				}

			} else {

				addMealClick = true;
				recentFoodClick = true;
				favFoodClick = true;

				mealListData = foodLogObj.selectFoodList(
						String.valueOf(mealId), databaseObject);
				// foodListData =
				// foodLogObj.selectFoodList(String.valueOf(mealId),databaseObject);
				// foodListDataAlies = foodListData;
				// setFoodlistData(foodListData,false);
				mealIdSpinner = mealId;
				switch (mealId) {
				case 1:

					mealId = 1;
					heading.setText("Breakfast");
					break;

				case 2:
					mealId = 2;
					heading.setText("Lunch");
					break;
				case 3:

					mealId = 3;
					heading.setText("Dinner");
					break;
				case 4:

					mealId = 4;
					heading.setText("Snack");
					break;
				}

			}

			initListSize = foodListData.size();
			selectMealLiner.setVisibility(View.GONE);
			searchMealLiner.setVisibility(View.VISIBLE);
			addMealLiner.setVisibility(View.GONE);
			total_frame.setVisibility(View.VISIBLE);
			showSyncdialog();

		} else {
			foodListData = foodLogObj.selectAllFoodList(databaseObject);
			initListSize = foodListData.size();

			setFoodlistData(foodListData, true);
		}

		if (com.fitmi.utils.Constants.pageLog == 0) {
			logBtn.setVisibility(View.GONE);
			back.setVisibility(View.VISIBLE);
		} else {
			logBtn.setVisibility(View.VISIBLE);
			back.setVisibility(View.GONE);
		}

		fooddrawableValues = new Integer[] { R.drawable.food,
				R.drawable.breakfast, R.drawable.lunch, R.drawable.dinner,
				R.drawable.snack };
		fooddrawableValuesFav = new Integer[] { // R.drawable.food,
		R.drawable.breakfast, R.drawable.lunch, R.drawable.dinner,
				R.drawable.snack };
		/*
		 * activitydrawableValues = new Integer[] {
		 * //R.drawable.calories_burned, R.drawable.chin_ups,
		 * R.drawable.treadmill, R.drawable.swimming, R.drawable.jumprope,
		 * R.drawable.boxing, R.drawable.lifting_weight };
		 */
		activitydrawableValues = new Integer[] { R.drawable.calories_burned,
				R.drawable.chin_ups, R.drawable.treadmill, R.drawable.swimming,
				R.drawable.jumprope, R.drawable.boxing,
				R.drawable.lifting_weight };
		// searchListAdapter();
		// setAdapter();
		unitModel = new UnitModule(getActivity());
		
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				// create "open" item

				// Delete Item
				SwipeMenuItem openItem = new SwipeMenuItem(getActivity()
						.getApplicationContext());
				openItem.setWidth(dp2px(60));
				openItem.setIcon(R.drawable.list_delete);
				openItem.setBackground(R.color.deep_pink);
				menu.addMenuItem(openItem);

				// Edit Item
				SwipeMenuItem editItem = new SwipeMenuItem(getActivity()
						.getApplicationContext());
				editItem.setBackground(R.color.deep_yellow);
				editItem.setWidth(dp2px(60));
				editItem.setIcon(R.drawable.total_swipeedit);
				menu.addMenuItem(editItem);

				/*
				 * // Favorite Item SwipeMenuItem starItem = new
				 * SwipeMenuItem(getActivity() .getApplicationContext());
				 * starItem.setWidth(dp2px(60)); switch (menu.getViewType()) {
				 * case 0: // create menu of type 0 (NOT FAVORITE)
				 * 
				 * starItem.setBackground(R.color.deep_green);
				 * starItem.setIcon(R.drawable.swipe_favorite);
				 * 
				 * break; case 1: // create menu of type 1 (FAVORITE)
				 * starItem.setBackground(R.color.deep_green);
				 * starItem.setIcon(R.drawable.total_favorites); break;
				 * 
				 * } menu.addMenuItem(starItem);
				 * 
				 * // Nutrition Item SwipeMenuItem nutriItem = new
				 * SwipeMenuItem(getActivity() .getApplicationContext());
				 * nutriItem.setBackground(R.color.bg_blue);
				 * nutriItem.setWidth(dp2px(60));
				 * nutriItem.setIcon(R.drawable.nutrition_facts_two);
				 * menu.addMenuItem(nutriItem);
				 */

			}
		};

		foodLoggingListView2.setMenuCreator(creator);

		foodLoggingListView2
				.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(int position,
							SwipeMenu menu, int index) {
						// ApplicationInfo item = mAppList.get(position);
						switch (index) {
						case 0:
							// open
							// open(item);

							dialogDeleteItem(position);
							break;
						case 1:

							editDialog(position);
							break;
						case 2:

							if (mealListData.size() != 0) {
								String id;
								id = mealListData.get(position).getFoodLogId();
								Log.e(" mealListData food log id after click ",
										id);

								if (mealListData.get(position).getFavourite()
										.equalsIgnoreCase("0")) {

									foodLogObj.updateFavourite(id, "1");
									mealListData.clear();

									mealListData = foodLogObj.selectFoodList(
											String.valueOf(mealIdSpinner),
											databaseObject);
								} else {
									foodLogObj.updateFavourite(id, "0");
									mealListData.clear();

									mealListData = foodLogObj.selectFoodList(
											String.valueOf(mealIdSpinner),
											databaseObject);
								}
							} else {
								String id;
								id = foodListDataAlies.get(position)
										.getFoodLogId();
								Log.e(" foodListDataAlies food log id after click ",
										id);
								if (foodListDataAlies.get(position)
										.getFavourite().equalsIgnoreCase("0")) {

									foodLogObj.updateFavourite(id, "1");
									foodListDataAlies.clear();

									foodListDataAlies = foodLogObj
											.selectFoodList(String
													.valueOf(mealIdSpinner),
													databaseObject);

								} else {
									foodLogObj.updateFavourite(id, "0");
									foodListDataAlies.clear();

									foodListDataAlies = foodLogObj
											.selectFoodList(String
													.valueOf(mealIdSpinner),
													databaseObject);
								}
							}
							notifyFoodadapter();
							break;

						case 3:
							// Toast.makeText(getActivity(),
							// "we are working on this feature ",Toast.LENGTH_LONG).show();
							Point point = new Point();

							String path = "https://trackapi.nutritionix.com/v1/natural/nutrients/";

							JSONObject holder = new JSONObject();// getJsonObjectFromMap(params);
							String n = null;
							if (mealListData.size() != 0) {
								String name;
								name = mealListData.get(position).getFoodName();
								try {
									holder.put("query", name);
									n = name;
									holder.put("timezone", "US/Eastern");
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {

								String name;
								name = foodListDataAlies.get(position)
										.getFoodName();
								try {
									holder.put("query", name);
									n = name;
									holder.put("timezone", "US/Eastern");
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
							Log.e("Sending value", holder.toString());

							// pDialog.setMessage("Loading..."); pDialog.show();

							// sendRequest(path, se);
							AsyncNutrion asyncNutrion = new AsyncNutrion(path,
									holder);
							asyncNutrion.execute("exe");
							// sendRequest(path+"{"+"timezone"+":"+"US/Eastern"+","+"query"+":"+n+"}");
							// showPopup(getActivity());

							break;

						}
						return false;
					}
				});

		foodLoggingListView2
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						try {
							Log.e("OnSingleTap visbility foodLoggingListView2 ",
									String.valueOf(addMealLiner.isShown()));

							if (!addMealLiner.isShown() && !log == true) {
								if (sFOODLOGGING_POS == arg2) {
									sFOODLOGGING_PrevPOS=sFOODLOGGING_POS;
									sFOODLOGGING_POS = -1;
									
									 updateValueTap(sFOODLOGGING_PrevPOS);	
									
									
						 
								} else
								{
									if(sFOODLOGGING_POS==-1)
									{
									sFOODLOGGING_PrevPOS=arg2;
									}else{
										sFOODLOGGING_PrevPOS=sFOODLOGGING_POS;
									}
									
									sFOODLOGGING_POS = arg2;
									
									
									 updateValueTap(sFOODLOGGING_PrevPOS);	
								}
								foodAdapter.notifyDataSetChanged();
								if (_searchadded == 1) {
									foodAdapterSearch.notifyDataSetChanged();
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
							mealListData = foodLogObj.selectFoodList(
									String.valueOf(mealIdSpinner),
									databaseObject);
							setMealAdapter();
							foodAdapter.notifyDataSetChanged();
						}

					}
				});

		SwipeMenuCreator creator_two = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {

				SwipeMenuItem openItem = new SwipeMenuItem(getActivity()
						.getApplicationContext());

				openItem.setBackground(new ColorDrawable(Color
						.rgb(208, 32, 144)));
				openItem.setWidth(dp2px(60));
				openItem.setIcon(R.drawable.total_swipone);
				openItem.setTitleSize(15);
				openItem.setBackground(R.color.deep_pink);
				menu.addMenuItem(openItem);

				SwipeMenuItem editItem = new SwipeMenuItem(getActivity()
						.getApplicationContext());
				editItem.setBackground(R.color.deep_yellow);
				editItem.setWidth(dp2px(60));
				editItem.setIcon(R.drawable.total_swipe_one);
				menu.addMenuItem(editItem);

				/*
				 * // Favorite Item SwipeMenuItem starItem = new
				 * SwipeMenuItem(getActivity() .getApplicationContext());
				 * starItem.setWidth(dp2px(60)); switch (menu.getViewType()) {
				 * case 0: // create menu of type 0 (NOT FAVORITE)
				 * 
				 * starItem.setBackground(R.color.deep_green);
				 * starItem.setIcon(R.drawable.swipe_favorite);
				 * 
				 * break; case 1: // create menu of type 1 (FAVORITE)
				 * starItem.setBackground(R.color.deep_green);
				 * starItem.setIcon(R.drawable.total_favorites); break;
				 * 
				 * } menu.addMenuItem(starItem);
				 */

			}
		};
		if (com.fitmi.utils.Constants.gunitfw == 0) {
			com.fitmi.utils.Constants.gm_oz = 0;
		} else {
			com.fitmi.utils.Constants.gm_oz = 1;
		}

		listTotalFrame_FoodLogging.setMenuCreator(creator_two);

		listTotalFrame_FoodLogging
				.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(int position,
							SwipeMenu menu, int index) {
						// ApplicationInfo item = mAppList.get(position);
						switch (index) {
						case 0:
							currActiveCalwt = "0";
							currActivewt = "0";
							totalFoodFooterAdapter = new TotalFoodFooterAdapter(
									getActivity(), currActivewt,
									currActiveCalwt, String
											.valueOf((int) caloryCalculate),
									"0");
							listTotalFrame_FoodLogging
									.setAdapter(totalFoodFooterAdapter);
							totalFoodFooterAdapter.notifyDataSetChanged();
							break;
						case 1:
						
								
							if(com.fitmi.utils.Constants.gunitfw == 0)
							{
							com.fitmi.utils.Constants.gunitfw = 1;	
							changeUnitFood();
							
							}else{
								com.fitmi.utils.Constants.gunitfw = 0;	
								changeUnitFood();
							}
									
									try{
							totalFoodFooterAdapter.notifyDataSetChanged();
							foodAdapter.notifyDataSetChanged();
									}catch (Exception e) {
										// TODO: handle exception
										
									}
							break;
						case 2:
							try {
								if (mealListData.size() != 0) {
									if (isFavMealVar.equalsIgnoreCase("0")) {
										for (int i = 0; i < mealListData.size(); i++) {
											foodLogObj.updateFavouriteMeal(
													mealListData.get(i)
															.getFoodLogId(),
													"1");
										}
										mealListData.clear();

										mealListData = foodLogObj.selectFoodList(
												String.valueOf(mealIdSpinner),
												databaseObject);
										setMelFav(mealListData);

									} else {
										for (int i = 0; i < mealListData.size(); i++) {
											foodLogObj.updateFavouriteMeal(
													mealListData.get(i)
															.getFoodLogId(),
													"0");
										}
										mealListData.clear();

										mealListData = foodLogObj.selectFoodList(
												String.valueOf(mealIdSpinner),
												databaseObject);
										setMelFav(mealListData);
									}
								} else {
									if (isFavMealVar.equalsIgnoreCase("0")) {
										for (int i = 0; i < foodListDataAlies
												.size(); i++) {
											foodLogObj.updateFavouriteMeal(
													foodListDataAlies.get(i)
															.getFoodLogId(),
													"1");
										}
										foodListDataAlies.clear();

										foodListDataAlies = foodLogObj.selectFoodList(
												String.valueOf(mealIdSpinner),
												databaseObject);
										setMelFav(foodListDataAlies);

									} else {
										for (int i = 0; i < foodListDataAlies
												.size(); i++) {
											foodLogObj.updateFavouriteMeal(
													foodListDataAlies.get(i)
															.getFoodLogId(),
													"0");
										}
										foodListDataAlies.clear();

										foodListDataAlies = foodLogObj.selectFoodList(
												String.valueOf(mealIdSpinner),
												databaseObject);
										setMelFav(foodListDataAlies);
									}

								}
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}

							if (favMealAdapter != null) {
								ArrayList<FitmiFoodLogDAO> recentMeal = recentFavMealList();
								setFoodlistDataRecentmeal(recentMeal, true);
								favMealAdapter();
								favMealAdapter.notifyDataSetChanged();
							} else if (recentMealAdapter != null) {

								ArrayList<FitmiFoodLogDAO> recentMeal = recentMealList();
								setFoodlistDataRecentmeal(recentMeal, true);
								// recentMealAdapter.notifyDataSetChanged();
								recentMealAdapter();
							}

							break;

						}
						return false;
					}
				});

		if (mealListData != null && mealListData.size() > 0) {
			setMealAdapter();
		} else {
			setAdapter();
		}
		setFoodSpinner();
		setActivitySpinner();
		setFoodFavSpinner();
		setFoodRecentSpinner();

		ActivityLoggingSpinnerAdapter flsa1 = new ActivityLoggingSpinnerAdapter(
				getActivity(), activityValues, activitydrawableValuesAlies,
				R.drawable.circle_pink);

		activitySpinner.setAdapter(flsa1);

		foodLoggingSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub

						if (foodDropClick) {

							int mealIdCh = 0;
							// caloryCalculate = 0;
							foodSpinnerSelect = position;

							if (foodListDataAlies.size() > 0)
								foodListDataAlies.clear();

							switch (position) {

							case 0:

								mealIdCh = 0;
								heading.setText("My Meal");
								foodListDataAlies = foodLogObj
										.selectAllFoodList(databaseObject);
								break;

							case 1:

								mealIdCh = 1;
								heading.setText("Breakfast");
								foodListDataAlies = foodLogObj.selectFoodList(
										String.valueOf(mealIdCh),
										databaseObject);
								break;

							case 2:
								mealIdCh = 2;
								heading.setText("Lunch");
								foodListDataAlies = foodLogObj.selectFoodList(
										String.valueOf(mealIdCh),
										databaseObject);
								break;
							case 3:

								mealIdCh = 3;
								heading.setText("Dinner");
								foodListDataAlies = foodLogObj.selectFoodList(
										String.valueOf(mealIdCh),
										databaseObject);
								break;
							case 4:

								mealIdCh = 4;
								heading.setText("Snack");
								foodListDataAlies = foodLogObj.selectFoodList(
										String.valueOf(mealIdCh),
										databaseObject);
								break;
							}
							// foodListDataAlies
							mealIdSpinner = mealIdCh;
							mealId = mealIdCh;
						//	foodDropClick = true;
							if(position==0)
							{
								if (mealId == 0) {

									if (!log) {
										
										foodListData = foodLogObj.selectAllFoodList(databaseObject);
										setFoodlistData(foodListData, true);
									//	setAdapter();
										
										setFoodSpinner();
										sectionAdapter = new EntryFoodLoginAdapter(getActivity(), items,
												totalCalory, bundle);
										foodLoggingListView.setAdapter(sectionAdapter);
									}

								}
								
							//	searchListAdapter();
								foodLoggingListView2.setVisibility(View.GONE); 
								selectMealLiner.setVisibility(View.GONE);
								searchMealLiner.setVisibility(View.GONE);
								addMealLiner.setVisibility(View.VISIBLE);
								total_frame.setVisibility(View.GONE);
								foodLoggingListView.setVisibility(View.VISIBLE); 
								
								
							}else{
							//avinash new creation method
								
							searchListAdapterListview2();
							//setFoodSpinner();
							// setMealAdapter();
							selectMealLiner.setVisibility(View.GONE);
							searchMealLiner.setVisibility(View.VISIBLE);
							addMealLiner.setVisibility(View.GONE);
							total_frame.setVisibility(View.VISIBLE);
							}
							
					
							
							
						}

					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});

		activitySpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub

						if (activityDropClick) {

							FragmentTransaction transaction = getFragmentManager()
									.beginTransaction();
							MyActivityFragment activityfragment = new MyActivityFragment();
							Bundle bundle = new Bundle();

							switch (activityType[position]) {
							case 'A':

								bundle.putInt("livestrong_id", 0);
								activityfragment.setArguments(bundle);
								if (!log) {
									transaction.add(R.id.root_home_frame,
											activityfragment,
											"MyActivityFragment");
								} else {
									transaction.add(R.id.root_planner_frame,
											activityfragment,
											"MyActivityFragment");
								}
								// root_planner_frame

								break;

							case 'W':

								bundle.putInt("livestrong_id", 5083);
								activityfragment.setArguments(bundle);
								if (!log) {
									transaction.add(R.id.root_home_frame,
											activityfragment,
											"MyActivityFragment");
								} else {
									transaction.add(R.id.root_planner_frame,
											activityfragment,
											"MyActivityFragment");
								}

								break;

							case 'B':
								bundle.putInt("livestrong_id", 2606);
								activityfragment.setArguments(bundle);
								if (!log) {
									transaction.add(R.id.root_home_frame,
											activityfragment,
											"MyActivityFragment");
								} else {
									transaction.add(R.id.root_planner_frame,
											activityfragment,
											"MyActivityFragment");
								}

								break;
							case 'J':
								bundle.putInt("livestrong_id", 5027);
								activityfragment.setArguments(bundle);
								if (!log) {
									transaction.add(R.id.root_home_frame,
											activityfragment,
											"MyActivityFragment");
								} else {
									transaction.add(R.id.root_planner_frame,
											activityfragment,
											"MyActivityFragment");
								}

								break;

							case 'S':

								bundle.putInt("livestrong_id", 5062);
								bundle.putBoolean("replace", false);
								activityfragment.setArguments(bundle);
								if (!log) {
									transaction.add(R.id.root_home_frame,
											activityfragment,
											"MyActivityFragment");
								} else {
									transaction.add(R.id.root_planner_frame,
											activityfragment,
											"MyActivityFragment");
								}

								break;
							case 'w':

								bundle.putInt("livestrong_id", 3547);
								activityfragment.setArguments(bundle);
								if (!log) {
									transaction.add(R.id.root_home_frame,
											activityfragment,
											"MyActivityFragment");
								} else {
									transaction.add(R.id.root_planner_frame,
											activityfragment,
											"MyActivityFragment");
								}

								break;
							case 'G':

								bundle.putInt("livestrong_id", 2952);
								activityfragment.setArguments(bundle);
								if (!log) {
									transaction.add(R.id.root_home_frame,
											activityfragment,
											"MyActivityFragment");
								} else {
									transaction.add(R.id.root_planner_frame,
											activityfragment,
											"MyActivityFragment");
								}

								break;
							}

							transaction
									.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
							transaction.addToBackStack(null);
							transaction.commit();
						}

					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});

		spinnerFav.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				// work here
				if (favDropClick) {

					int mealIdCh = 0;
					caloryCalculate = 0;
					foodSpinnerSelectFav = position;

					if (foodListDataAlies.size() > 0)
						foodListDataAlies.clear();

					switch (position + 1) {
					case 1:
						mealIdCh = 1;

						break;

					case 2:
						mealIdCh = 2;

						break;
					case 3:
						mealIdCh = 3;

						break;
					case 4:
						mealIdCh = 4;

						break;
					}

					/*
					 * if(foodListRecent !=null && foodListRecent.size()>0){
					 * foodListRecent.clear(); }
					 */
					recentListViewParentLinear.setVisibility(View.VISIBLE);

					// foodListRecentFav =
					// foodLogObj.selectAllFavFoodListById(String.valueOf(mealIdCh));

					/*
					 * if(foodListRecentMeal.size()>0)
					 * foodListRecentMeal.clear();
					 * 
					 * foodListRecentMeal =
					 * foodLogObj.selectAllFavFoodListById(String
					 * .valueOf(mealIdCh));
					 * setFoodlistDataRecentmeal(foodListRecentMeal,false);
					 * favFoodAdapter();
					 */

					if (foodListRecentmeal.size() > 0)
						foodListRecentmeal.clear();

					ArrayList<String> dateList = foodLogObj
							.selectRecentMealDate();

					SimpleDateFormat preFormat = new SimpleDateFormat(
							"yyyy-MM-dd hh:mm:ss");
					SimpleDateFormat postFormat = new SimpleDateFormat(
							"yyyy-MM-dd");

					String date = "";
					String meal = "";
					FitmiFoodLogDAO recentmeal = null;

					for (int i = 0; i < dateList.size(); i++) {

						try {
							Date newDate = preFormat.parse(dateList.get(i));
							date = postFormat.format(newDate);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						ArrayList<String> mealid = new ArrayList<String>();

						// foodListRecent =
						// foodLogObj.selectAllFoodListById(String.valueOf(mealIdCh),date);

						// prev change avinash
						/*
						 * foodListRecent = foodLogObj.selectAllFavFoodListById(
						 * String.valueOf(mealIdCh), date);
						 */

						foodListRecent = foodLogObj.selectAllFavMealList(
								String.valueOf(mealIdCh), date);
						recentmeal = new FitmiFoodLogDAO();
						// recentmeal.setFavourite("1");
						recentmeal.setMealFavourite("1");

						for (int k = 0; k < foodListRecent.size(); k++) {

							if (meal.equalsIgnoreCase("")) {

								if (k == 0)
									meal = foodListRecent.get(k).getFoodName();
								else
									meal = meal
											+ ","
											+ foodListRecent.get(k)
													.getFoodName();

							} else {

								meal = meal + ","
										+ foodListRecent.get(k).getFoodName();
							}

							mealid.add(foodListRecent.get(k).getFoodLogId());

							if (foodListRecent.get(k).getFavourite()
									.equalsIgnoreCase("0"))
								// recentmeal.setFavourite("0");
								// prev changes by 0 to 1avinash
								// recentmeal.setMealFavourite("0");
								recentmeal.setMealFavourite("1");
						}

						if (foodListRecent.size() > 0) {

							recentmeal.setMealidList(mealid);
							recentmeal.setFoodName(meal);
							recentmeal.setMealType(foodListRecent.get(0)
									.getMealType());

							foodListRecentmeal.add(recentmeal);

							meal = "";
						}

					}

					setFoodlistDataRecentmeal(foodListRecentmeal, false);
					favMealItemAdapter();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		spinnerRecent.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub

				if (recentDropClick) {

					int mealIdCh = 0;
					caloryCalculate = 0;
					foodSpinnerSelectRecent = position;

					if (foodListDataAlies.size() > 0)
						foodListDataAlies.clear();

					switch (position + 1) {
					case 1:
						mealIdCh = 1;

						break;

					case 2:
						mealIdCh = 2;

						break;
					case 3:
						mealIdCh = 3;

						break;
					case 4:
						mealIdCh = 4;

						break;
					}

					if (foodListRecent != null && foodListRecent.size() > 0) {
						foodListRecent.clear();
					}
					recentListViewParentLinear.setVisibility(View.VISIBLE);

					/*
					 * foodListRecent =
					 * foodLogObj.selectAllFoodListById(String.valueOf
					 * (mealIdCh));
					 * 
					 * recentFoodAdapter();
					 */

					ArrayList<String> dateList = foodLogObj
							.selectRecentMealDate();

					SimpleDateFormat preFormat = new SimpleDateFormat(
							"yyyy-MM-dd hh:mm:ss");
					SimpleDateFormat postFormat = new SimpleDateFormat(
							"yyyy-MM-dd");

					String date = "";
					String meal = "";
					FitmiFoodLogDAO recentmeal = null;

					if (foodListRecentmeal.size() > 0)
						foodListRecentmeal.clear();

					for (int i = 0; i < dateList.size(); i++) {

						try {
							Date newDate = preFormat.parse(dateList.get(i));
							date = postFormat.format(newDate);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						ArrayList<String> mealid = new ArrayList<String>();

						foodListRecent = foodLogObj.selectAllFoodListById(
								String.valueOf(mealIdCh), date);
						// foodListRecent =
						// foodLogObj.selectRecentMealList(String.valueOf(outer),date);

						recentmeal = new FitmiFoodLogDAO();
						// recentmeal.setFavourite("1");
						recentmeal.setMealFavourite("1");

						for (int k = 0; k < foodListRecent.size(); k++) {

							if (meal.equalsIgnoreCase("")) {

								if (k == 0)
									meal = foodListRecent.get(k).getFoodName();
								else
									meal = meal
											+ ","
											+ foodListRecent.get(k)
													.getFoodName();

							} else {

								meal = meal + ","
										+ foodListRecent.get(k).getFoodName();
							}

							mealid.add(foodListRecent.get(k).getFoodLogId());

							if (foodListRecent.get(k).getMealFavourite()
									.equalsIgnoreCase("0"))
								// recentmeal.setFavourite("0");
								recentmeal.setMealFavourite("0");
						}

						if (foodListRecent.size() > 0) {

							recentmeal.setMealidList(mealid);
							recentmeal.setFoodName(meal);
							recentmeal.setMealType(foodListRecent.get(0)
									.getMealType());

							foodListRecentmeal.add(recentmeal);

							meal = "";
						}
					}

					setFoodlistDataRecentmeal(foodListRecentmeal, false);
					recentMealAdapter();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		sFOODLOGGING_POS = -1;
		CLICKRECENTITEM = -1;
		CLICKFAVITEM = -1;

		// foodListRecent = foodLogObj.selectFoodList("1",databaseObject);

		foodTypeTitle.setText("My " + foodType);
		// totalCalory.setText((int)caloryCalculate+""+" cal");
		calorieTotalTop.setText((int) caloryCalculate + "" + " cal");

		foodLoggingListView
				.setOnItemDoubleClickListener(new OnItemDoubleTapLister() {

					@Override
					public void OnSingleTap(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						try {
							Log.e("OnSingleTap visbility ",
									String.valueOf(addMealLiner.isShown()));
							if (!addMealLiner.isShown() && !log == true) {
								sFOODLOGGING_POS = position;
								foodAdapter.notifyDataSetChanged();
								if (_searchadded == 1) {
									foodAdapterSearch.notifyDataSetChanged();
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
							mealListData = foodLogObj.selectFoodList(
									String.valueOf(mealIdSpinner),
									databaseObject);
							setMealAdapter();
							foodAdapter.notifyDataSetChanged();
						}
						/* avinash changes on single tap nothing have to do */
					}

					@Override
					public void OnDoubleTap(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub

						// flAdapter.notifyDataSetChanged();
						TextView tv = null;

						Log.e("OnDoubleTap", "OnDoubleTap");

						if (view != null)
							tv = (TextView) view
									.findViewById(R.id.textSeparator);

						if (tv != null) {

							int mealIdCh = 0;
							String headerValue = tv.getText().toString();

							if (headerValue.equalsIgnoreCase("Breakfast")) {

								mealIdCh = 1;
								heading.setText("Breakfast");

							} else if (headerValue.equalsIgnoreCase("Lunch")) {

								mealIdCh = 2;
								heading.setText("Lunch");

							} else if (headerValue.equalsIgnoreCase("Dinner")) {

								mealIdCh = 3;
								heading.setText("Dinner");

							} else {
								mealIdCh = 4;
								heading.setText("Snack");
							}

							mealIdSpinner = mealIdCh;
							mealId = mealIdCh;
							mealListData = foodLogObj.selectFoodList(
									String.valueOf(mealIdCh), databaseObject);

							/*
							 * for(int i=0;i<mealListData.size();i++)
							 * caloryCalculate
							 * +=Float.parseFloat(mealListData.get
							 * (i).getCalory());
							 * 
							 * totalCalory.setText((int)caloryCalculate+""+" cal"
							 * );
							 */
							// searchListAdapter();
							setMealAdapter();
							selectMealLiner.setVisibility(View.GONE);
							searchMealLiner.setVisibility(View.VISIBLE);
							addMealLiner.setVisibility(View.GONE);
							total_frame.setVisibility(View.VISIBLE);
							showSyncdialog();
						}
					}
				});

		foodLoggingListView
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, int pos, long id) {
						// TODO Auto-generated method stub

						dialogShowLongclick(pos);

						return true;
					}
				});

		recentFoodMealsListView
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View view,
							int position, long id) {
						// TODO Auto-generated method stub

						if (addMealClick) {

							if (recentFoodClick) {

								CLICKRECENTITEM = position;
								recentFoodAdapter.notifyDataSetChanged();
								FitmiFoodLogDAO recentFood = foodListRecent
										.get(position);
								recentFood
										.setLogTime(com.fitmi.utils.Constants.postDate);
								recentFood
										.setDateAdded(com.fitmi.utils.Constants.postDate);
								recentFood
										.setUserId(com.fitmi.utils.Constants.USER_ID);
								recentFood
										.setUserProfileId(com.fitmi.utils.Constants.PROFILE_ID);
								recentFood.setMealId(String.valueOf(mealId));
								recentFood.setFavourite("0");

								FoodLoginModule.insertFitmifoodLogTable(
										recentFood, databaseObject);
								// foodListData.add(recentFood);
								foodListDataAlies.add(recentFood);
								// sectionAdapter.notifyDataSetChanged();
								searchListAdapter();
								// setMealAdapter();
							} else {

								CLICKRECENTITEM = position;
								if (recentMealAdapter != null)
									recentMealAdapter.notifyDataSetChanged();

								for (int i = 0; i < foodListRecentMealAdd
										.size(); i++) {

									FitmiFoodLogDAO recentFood = foodListRecentMealAdd
											.get(position);
									recentFood
											.setLogTime(com.fitmi.utils.Constants.postDate);
									recentFood
											.setDateAdded(com.fitmi.utils.Constants.postDate);
									recentFood
											.setUserId(com.fitmi.utils.Constants.USER_ID);
									recentFood
											.setUserProfileId(com.fitmi.utils.Constants.PROFILE_ID);
									recentFood.setMealId(String.valueOf(mealId));
									recentFood.setFavourite("0");

									FoodLoginModule.insertFitmifoodLogTable(
											recentFood, databaseObject);
									// foodListData.add(recentFood);
									foodListDataAlies.add(recentFood);

								}

								searchListAdapter();

							}

						}

					}
				});

		favListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				if (addMealClick) {

					if (favFoodClick) {

						CLICKFAVITEM = position;
						favAdapter.notifyDataSetChanged();
						FitmiFoodLogDAO recentFood = foodListRecent
								.get(position);
						recentFood
								.setLogTime(com.fitmi.utils.Constants.postDate);
						recentFood
								.setDateAdded(com.fitmi.utils.Constants.postDate);
						recentFood.setUserId(com.fitmi.utils.Constants.USER_ID);
						recentFood
								.setUserProfileId(com.fitmi.utils.Constants.PROFILE_ID);
						recentFood.setMealId(String.valueOf(mealId));
						recentFood.setFavourite("0");

						FoodLoginModule.insertFitmifoodLogTable(recentFood,
								databaseObject);
						// foodListData.add(recentFood);
						foodListDataAlies.add(recentFood);
						// sectionAdapter.notifyDataSetChanged();
						searchListAdapter();
						// setMealAdapter();
					}

				}

			}
		});

		hideRecentDetails();

		// place adapter
		/*
		 * placesAutoCompleteAdapter = new PlacesAutoCompleteAdapter(
		 * getActivity(), R.layout.item_autocomplete);
		 * //searchEditText.setAdapter(placesAutoCompleteAdapter);
		 * searchEditText.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> adapterView, View
		 * view, int position, long id) { // TODO Auto-generated method stub
		 * 
		 * 
		 * 
		 * 
		 * searchEditText.setText(""); try {
		 * 
		 * 
		 * String path =
		 * "https://trackapi.nutritionix.com/v1/natural/nutrients/";
		 * autocomplete_two(searchList.get(position).getItemName(),
		 * searchList.get(position).getItemId());
		 * 
		 * JSONObject holder = new JSONObject();// getJsonObjectFromMap(params);
		 * 
		 * String name; String _itemid;
		 * 
		 * name = searchList.get(position).getItemName();
		 * _itemid=searchList.get(position).getItemId(); holder.put("query",
		 * name); holder.put("timezone", "US/Eastern");
		 * 
		 * Log.e("Sending value", holder.toString());
		 * 
		 * AsyncNutrionTwo asyncNutrionTwo= new AsyncNutrionTwo(path,
		 * holder,_itemid); asyncNutrionTwo.execute("exe");
		 * 
		 * } catch (JSONException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 * 
		 * InputMethodManager in = (InputMethodManager) getActivity()
		 * .getSystemService(Context.INPUT_METHOD_SERVICE);
		 * in.hideSoftInputFromWindow(
		 * searchEditText.getApplicationWindowToken(),
		 * InputMethodManager.HIDE_NOT_ALWAYS);
		 * 
		 * }
		 * 
		 * });
		 */
		clickRecentFoods();
		// total_frame.setVisibility(View.GONE);

		drawer.setOnDrawerCloseListener(new OnDrawerCloseListener() {

			@Override
			public void onDrawerClosed() {
				// TODO Auto-generated method stub

				searchEditText.setFocusable(true);
				searchEditText.setFocusableInTouchMode(true); // user touches
																// widget on
																// phone with
																// touch screen
				searchEditText.setClickable(true);
				if (foodAdapterSearch != null) {
					foodListDataAlies = foodLogObj.selectFoodList(
							String.valueOf(mealIdSpinner), databaseObject);
					searchListAdapter();
				}
				if (foodAdapter != null) {
					mealListData = foodLogObj.selectFoodList(
							String.valueOf(mealIdSpinner), databaseObject);
					setMealAdapter();
				}

				shadowFrame.setVisibility(View.GONE);
				shadowFrame1.setVisibility(View.GONE);
				arrow.setBackgroundResource(R.drawable.kee_arrow_strate);
				// shadowFrame.setAlpha(1.0f);

				back.setClickable(true);
				Settings.setClickable(true);
				txtPreview.setClickable(true);
				dateTextView.setClickable(true);
				txtNext.setClickable(true);
				intake_linearlayout.setClickable(true);
				activityLayout.setClickable(true);
				imgAddMeal.setClickable(true);
				breakFastImg.setClickable(true);
				snacksImg.setClickable(true);
				lunchImg.setClickable(true);
				dinnerImg.setClickable(true);
				layoutMealBurn.setClickable(true);
			}
		});

		drawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {

			@Override
			public void onDrawerOpened() {
				// TODO Auto-generated method stub

				searchEditText.setFocusable(false);
				searchEditText.setFocusableInTouchMode(false); // user touches
																// widget on
																// phone with
																// touch screen
				searchEditText.setClickable(false);
				shadowFrame.setVisibility(View.VISIBLE);
				shadowFrame1.setVisibility(View.VISIBLE);
				arrow.setBackgroundResource(R.drawable.kee_arrow);
				// shadowFrame.setAlpha(0.7f);

				back.setClickable(false);
				Settings.setClickable(false);
				txtPreview.setClickable(false);
				dateTextView.setClickable(false);
				txtNext.setClickable(false);
				intake_linearlayout.setClickable(false);
				activityLayout.setClickable(false);
				imgAddMeal.setClickable(false);
				breakFastImg.setClickable(false);
				snacksImg.setClickable(false);
				lunchImg.setClickable(false);
				dinnerImg.setClickable(false);
				layoutMealBurn.setClickable(false);

			}
		});

		favouriteMeal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				TagSet tag = (TagSet) view.getTag();

				if (tag != null) {

					switch (tag.imgId) {
					case R.drawable.green_star:
						favouriteMeal
								.setImageResource(R.drawable.favorites_star);
						tag.imgId = R.drawable.favorites_star;

						for (int i = 0; i < tag.mealidList.size(); i++) {
							foodLogObj.updateFavouriteMeal(tag.mealidList
									.get(i).getFoodLogId(), "1");
						}

						break;

					case R.drawable.favorites_star:
						favouriteMeal.setImageResource(R.drawable.green_star);
						tag.imgId = R.drawable.green_star;

						for (int i = 0; i < tag.mealidList.size(); i++) {
							foodLogObj.updateFavouriteMeal(tag.mealidList
									.get(i).getFoodLogId(), "0");
						}

						break;
					}

					if (favMealAdapter != null) {
						ArrayList<FitmiFoodLogDAO> recentMeal = recentFavMealList();
						setFoodlistDataRecentmeal(recentMeal, true);
						// favMealAdapter();
						favMealAdapter.notifyDataSetChanged();
					} else if (recentMealAdapter != null) {

						ArrayList<FitmiFoodLogDAO> recentMeal = recentMealList();
						setFoodlistDataRecentmeal(recentMeal, true);
						// recentMealAdapter.notifyDataSetChanged();
						recentMealAdapter();
					}

				} else {

					Toast.makeText(getActivity(), "Item not found",
							Toast.LENGTH_LONG).show();
				}

			}
		});

		placesAutoCompleteAdapter = new PlacesAutoCompleteAdapter(getActivity());
		list_autocomplete.setAdapter(placesAutoCompleteAdapter);

		list_autocomplete.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				try {

					String path = "https://trackapi.nutritionix.com/v1/natural/nutrients/";
					/*
					 * autocomplete_two(searchList.get(position).getItemName(),
					 * searchList.get(position).getItemId());
					 */

					JSONObject holder = new JSONObject();// getJsonObjectFromMap(params);

					String name;
					String _itemid;

					name = searchList1.get(position).getItemName();
					_itemid = searchList1.get(position).getItemId();
					holder.put("query", name);
					holder.put("timezone", "US/Eastern");

					Log.e("Sending value", holder.toString());

					AsyncNutrionTwo asyncNutrionTwo = new AsyncNutrionTwo(path,
							holder, _itemid);
					asyncNutrionTwo.execute("exe");

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				searchList1.clear();

				placesAutoCompleteAdapter.notifyDataSetChanged();
				list_autocomplete.setVisibility(View.GONE);
				inputSearch.setCursorVisible(false);
				inputSearch.setText("");
				InputMethodManager in = (InputMethodManager) getActivity()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				in.hideSoftInputFromWindow(
						inputSearch.getApplicationWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);

			}
		});

		list_autocomplete.setOnTouchListener(new View.OnTouchListener() {
			float height;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();
				float height = event.getY();

				View view = getActivity().getCurrentFocus();
				if (view != null) {
					InputMethodManager imm = (InputMethodManager) getActivity()
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
					inputSearch.setCursorVisible(false);
				}
				if (action == MotionEvent.ACTION_DOWN) {
					this.height = height;

				} else if (action == MotionEvent.ACTION_UP) {
					if (this.height < height) {
						Log.v("Foodlog", "Scrolled up");
					} else if (this.height > height) {
						Log.v("Foodlog", "Scrolled down");
					}
				}
				return false;
			}
		});

		inputSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {

				try {
					 updateValueTap(sFOODLOGGING_POS);	
					sFOODLOGGING_POS = -1;
					foodAdapter.notifyDataSetChanged();
					foodAdapterSearch.notifyDataSetChanged();
			}
		 catch (Exception e) {
		//	e.printStackTrace();
		//	foodAdapter.notifyDataSetChanged();
		}
				
				if (AsyncAutocompleteNutri != null) {
					AsyncAutocompleteNutri.cancel(true);

				}
				if (cs.toString().length() > 1) {
					AsyncAutocompleteNutri = new AsyncAutocompleteNutri("", cs
							.toString());
					AsyncAutocompleteNutri.execute("exe");
				}
				if (cs.length() <= 0) {
					searchList1.clear();

					placesAutoCompleteAdapter.notifyDataSetChanged();
					foodLoggingListView2.setVisibility(View.VISIBLE);
					list_autocomplete.setVisibility(View.GONE);
				}

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		
		inputSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
		    @Override
		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		        if (actionId == EditorInfo.IME_ACTION_DONE) {
		        	

		        	View view = getActivity().getCurrentFocus();
	    		if (view != null) {  
	    		    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	    		    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	    		}
	    		inputSearch.setCursorVisible(false);
		        
		            return true;
		        }
		        return false;
		    }
		});
		
		inputSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				inputSearch.setCursorVisible(true);
			}
		});
		/*inputSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				 if(!hasFocus) 
	                { // lost focus
					 
	                     //  v.setEnabled(false);
	
	                      
	                }
	                else
	                {

	    			
	    				
	    				
	                }
				
			}
		});
*/
		return view;
	}

	

	AsyncAutocompleteNutri AsyncAutocompleteNutri;

	@OnClick(R.id.layoutDailyCaloryEdit)
	public void clickDailyCalory() {
		
		View view = getActivity().getCurrentFocus();
		if (view != null) {  
		    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
		com.fitmi.utils.Constants.fromFragment = 1;

		mRootId = R.id.root_home_frame;

		Bundle bundle = new Bundle();
		bundle.putInt("root_id", mRootId);
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();

		DailyCalorieIntakeFragment fragment = new DailyCalorieIntakeFragment();
		fragment.setArguments(bundle);
		transaction.add(mRootId, fragment, "DailyCalorieIntakeFragment");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	@OnClick(R.id.ListBreakfastLinear)
	public void listBreakfast() {

	}

	@OnClick(R.id.backLiner)
	public void back() {

		/*
		 * if(mealId>0) { databaseObject.openDatabase();
		 * 
		 * for(int i=initListSize;i<foodListData.size();i++) { FitmiFoodLogDAO
		 * obj = foodListData.get(i); obj.setMealId(String.valueOf(mealId));
		 * 
		 * System.out.println("Food Id==================:"+mealId);
		 * //Log.d("Food Id==================:", String.valueOf(mealId));
		 * 
		 * FoodLoginModule.insertFitmifoodLogTable(obj, databaseObject); }
		 * 
		 * databaseObject.closeDataBase(); getActivity().onBackPressed(); }else
		 * if(newDataCount>0)//if(!newDataAdd)
		 * 
		 * dialogFood(); //dialog(); else
		 */
		InputMethodManager in = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		in.hideSoftInputFromWindow(searchEditText.getApplicationWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
		getActivity().onBackPressed();
	}

	@OnClick(R.id.Settings)
	public void gotoSettings() {

		View view = getActivity().getCurrentFocus();
		if (view != null) {  
		    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
		SettingsFragment fragment = new SettingsFragment();

		Bundle bundle = new Bundle();
		bundle.putInt("root_id", R.id.root_home_frame);
		fragment.setArguments(bundle);

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.add(R.id.root_home_frame, fragment, "SettingsFragment");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack("SettingsFragment");
		transaction.commit();

	}

	@OnClick(R.id.MyBreakfastFrame)
	public void clickMyBreakfast() {

		hideRecentDetails();

		if (foodListDataAlies.size() > 0) {
			for (int i = 0; i < foodListDataAlies.size(); i++)
				foodListData.add(foodListDataAlies.get(i));
		}

		setAdapter();
	}

	@OnClick(R.id.RecentFoods_FoodLogging)
	public void clickRecentFoods() {

		if (foodListRecent != null && foodListRecent.size() > 0
				&& favAdapter != null) {
			foodListRecent.clear();
			favAdapter.notifyDataSetChanged();
		}

		foodListRecent = foodLogObj.selectRecentFoodList(databaseObject);
		recentFoodAdapter();

		deselectRecentTabs();
		recentFoods.setBackgroundResource(R.color.home_pink_select_dark);
		recentListViewParentLinear.setVisibility(View.VISIBLE);
		favListView.setVisibility(View.GONE);
		favFoodMealsMenu.setVisibility(View.GONE);
		recentFoodMealsMenu.setVisibility(View.VISIBLE);

		recentFood.setBackgroundColor(getResources().getColor(
				R.color.home_green_select_dark));
		recentMeal.setBackgroundColor(getResources().getColor(
				R.color.home_green_deselect_dark));
		intake_linearlayout_recent.setBackgroundColor(getResources().getColor(
				R.color.home_green_deselect_dark));

		if (foodAdapterSearch != null) {
			foodListDataAlies = foodLogObj.selectFoodList(
					String.valueOf(mealIdSpinner), databaseObject);
			foodAdapterSearch.notifyDataSetChanged();
			// searchListAdapter();

		}

		if (foodAdapter != null) {
			mealListData = foodLogObj.selectFoodList(
					String.valueOf(mealIdSpinner), databaseObject);
			foodAdapter.notifyDataSetChanged();
			// setMealAdapter();
		}

	}

	@OnClick(R.id.imgspeech)
	public void clickVoiceSearch() {
		callActivityResult = 0;
		promptSpeechInput();
	}

	@OnClick(R.id.RecentMeals_FoodLogging)
	public void clickRecentMeals() {

		if (foodListRecent != null && foodListRecent.size() > 0) {
			foodListRecent.clear();
			recentFoodAdapter.notifyDataSetChanged();
		}

		// foodListRecentFav = foodLogObj.selectAllFavFoodList();
		// favFoodAdapter();

		if (foodListRecentMeal.size() > 0)
			foodListRecentMeal.clear();

		foodListRecentMeal = foodLogObj.selectAllFavFoodList();
		setFoodlistDataRecentmeal(foodListRecentMeal, false);
		favFoodAdapter();

		/*
		 * foodListRecent = foodLogObj.selectAllFavFoodList();
		 * recentFoodAdapter(); recentFoodAdapter.notifyDataSetChanged();
		 */
		favFoodList();

		favListView.setVisibility(View.VISIBLE);
		recentListViewParentLinear.setVisibility(View.GONE);

		favFoodMealsMenu.setVisibility(View.VISIBLE);
		recentFoodMealsMenu.setVisibility(View.GONE);

		deselectRecentTabs();
		recentMeals.setBackgroundResource(R.color.home_pink_select_dark);

		if (foodAdapterSearch != null) {
			foodListDataAlies = foodLogObj.selectFoodList(
					String.valueOf(mealIdSpinner), databaseObject);
			foodAdapterSearch.notifyDataSetChanged();
			// searchListAdapter();
		}
		if (foodAdapter != null) {
			mealListData = foodLogObj.selectFoodList(
					String.valueOf(mealIdSpinner), databaseObject);
			foodAdapter.notifyDataSetChanged();
			// setMealAdapter();
		}
	}

	@OnClick(R.id.Base_Header)
	public void clickHeaderBase() {

	}

	/*
	 * private void showRecentDetails() {
	 * 
	 * listBreakfastLinear.setVisibility(View.GONE); flingUpDown = true; }
	 */

	private void hideRecentDetails() {

		recentFoods.setBackgroundResource(R.color.home_pink_select_dark);
		recentMeals.setBackgroundResource(R.color.home_pink_select_dark);

		recentListViewParentLinear.setVisibility(View.GONE);
		recentFoodMealsMenu.setVisibility(View.VISIBLE);
		favFoodMealsMenu.setVisibility(View.GONE);
		listBreakfastLinear.setVisibility(View.VISIBLE);
		flingUpDown = false;
	}

	private void deselectRecentTabs() {
		recentFoods.setBackgroundResource(R.color.home_pink_deselect_dark);
		recentMeals.setBackgroundResource(R.color.home_pink_deselect_dark);
	}

	@OnClick(R.id.Date)
	public void changeDate() {

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();

		if (com.fitmi.utils.Constants.calendreSet == 0) {
			transaction.add(R.id.root_home_frame, new CalendarFragment(),
					"CalendarFragment");
		} else {
			transaction.add(R.id.root_planner_frame, new CalendarFragment(),
					"CalendarFragment");
		}
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	@OnClick(R.id.intake_linearlayout)
	public void IntakeLinearlayoutClick() {
		foodLoggingSpinner.performClick();
		foodDropClick = true;
	}

	@OnClick(R.id.activity_linearlayout)
	public void activityLinearlayoutClick() {
		activitySpinner.performClick();
		activityDropClick = true;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		
		if (activityReceiver != null) {
		
			IntentFilter intentFilter = new IntentFilter(
					DeviceSyncService.ACTION_FROM_SERVICE);
		
			getActivity().registerReceiver(activityReceiver, intentFilter);
		}
		// changed by avinash and saikat da
		if (com.fitmi.utils.Constants.sTempDate.length() == 0) {
			// Constants.sDate = "Tuesday, February 10, 2015";

			Calendar c = Calendar.getInstance();
			System.out.println("Current time => " + c.getTime());
			
			SimpleDateFormat df = new SimpleDateFormat("EEEE, MMM dd, yyyy");
			com.fitmi.utils.Constants.sDate = df.format(c.getTime());

			SimpleDateFormat postFormat = new SimpleDateFormat(
					"yyyy-MM-dd kk:mm:ss");
			com.fitmi.utils.Constants.postDate = postFormat.format(c.getTime());

			SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
			com.fitmi.utils.Constants.conditionDate = dformat.format(c
					.getTime());
			System.out.println("Calender post format :"
					+ com.fitmi.utils.Constants.postDate);
			

		} else {
			today = com.fitmi.utils.Constants.sTempDate;
		}
	
		dateTextView.setText(com.fitmi.utils.Constants.sDate);
	}

	/**
	 * Auto complete
	 */

	
	// need to change avinash searchapi

	@SuppressWarnings("deprecation")
	private ArrayList<FitmiFoodDAO> autocomplete(String input) {
		// ArrayList<String> resultList = null;

		ArrayList<FitmiFoodDAO> resultList = new ArrayList<FitmiFoodDAO>();

		sFOODLOGGING_POS = -1;

		// placesAutoCompleteAdapter.notifyDataSetInvalidated();
		typeText = input;
		String SetServerString = "";
		HttpURLConnection conn = null;
		// StringBuilder jsonResults = new StringBuilder();
		try {
			
			StringBuilder sb = new StringBuilder(
					"https://api.nutritionix.com/v2/autocomplete?q="
							+ typeText
							+ "&appId=b65138b3&appKey=220fa746203ea5217abff5970c9f8d43");

			DefaultHttpClient Client = getNewHttpClient();
			HttpGet httpget = new HttpGet(sb.toString());

			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			SetServerString = Client.execute(httpget, responseHandler);

		} catch (MalformedURLException e) {
			Log.e("LOG TAG", "Error processing Places API URL", e);
			return resultList;
		} catch (IOException e) {
			Log.e("LOG TAG", "Error connecting to Places API", e);
			return resultList;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

		try {
			// Create a JSON object hierarchy from the results
			// old JSONObject jsonObj = new JSONObject(SetServerString);
			// JSONArray predsJsonArray = jsonObj.getJSONArray("foods");
			// old JSONArray predsJsonArray = jsonObj.getJSONArray("hits");

			JSONArray predsJsonArray = new JSONArray(SetServerString);
			Log.e("PREDICTIONS", predsJsonArray.toString());

			if (searchList != null && searchList.size() > 0)
				searchList.clear();

			resultList.clear();
			// Extract the Place descriptions from the results
			// resultList = new ArrayList<String>(predsJsonArray.length());
			for (int i = 0; i < predsJsonArray.length(); i++) {

				String itemId = predsJsonArray.getJSONObject(i).getString("id");
				String itemName = predsJsonArray.getJSONObject(i).getString(
						"text");// predsJsonArray.getJSONObject(i).getJSONObject("fields").getString("item_name");
				
				itemName = WordUtils.capitalize(itemName);
				

				FitmiFoodDAO itemObj = new FitmiFoodDAO();
				itemObj.setItemId(itemId);
				itemObj.setItemName(itemName);
				
				itemObj.setCustomButton(false);

			
				resultList.add(itemObj);

			

			}
		} catch (JSONException e) {
			Log.e("LOG TAG", "Cannot process JSON results", e);
		}
		
		Log.e("resultList during retrive returning json ",
				String.valueOf(resultList.size()));
		return resultList;
	}



	public static HttpResponse makeRequest(String path, Map params)
			throws Exception {
		// instantiates httpclient to make request
		DefaultHttpClient httpclient = new DefaultHttpClient();

		// url with the post data
		HttpPost httpost = new HttpPost(path);

		// convert parameters into JSON object
		JSONObject holder = getJsonObjectFromMap(params);

		// passes the results to a string builder/entity
		StringEntity se = new StringEntity(holder.toString());

		// sets the post request as the resulting string
		httpost.setEntity(se);
		// sets a request header so the page receving the request
		// will know what to do with it
		httpost.setHeader("Accept", "application/json");
		httpost.setHeader("Content-type", "application/json");

		// Handles what is returned from the page
		ResponseHandler responseHandler = new BasicResponseHandler();
		return (HttpResponse) httpclient.execute(httpost, responseHandler);
	}

	private static JSONObject getJsonObjectFromMap(Map params)
			throws JSONException {

		// all the passed parameters from the post request
		// iterator used to loop through all the parameters
		// passed in the post request
		Iterator iter = params.entrySet().iterator();

		// Stores JSON
		JSONObject holder = new JSONObject();

		// using the earlier example your first entry would get email
		// and the inner while would get the value which would be 'foo@bar.com'
		// { fan: { email : 'foo@bar.com' } }

		// While there is another entry
		while (iter.hasNext()) {
			// gets an entry in the params
			@SuppressWarnings("rawtypes")
			Map.Entry pairs = (Map.Entry) iter.next();

			// creates a key for Map
			String key = (String) pairs.getKey();

			// Create a new map
			Map m = (Map) pairs.getValue();

			// object for storing Json
			JSONObject data = new JSONObject();

			// gets the value
			Iterator iter2 = m.entrySet().iterator();
			while (iter2.hasNext()) {
				Map.Entry pairs2 = (Map.Entry) iter2.next();
				data.put((String) pairs2.getKey(), (String) pairs2.getValue());
			}

			// puts email and 'foo@bar.com' together in map
			holder.put(key, data);
		}
		return holder;
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

	public void setAdapter() {
		caloryCalculate = 0;
		sectionAdapter = new EntryFoodLoginAdapter(getActivity(), items,
				totalCalory, bundle);

		foodLoggingListView.setVisibility(View.VISIBLE);
		foodLoggingListView2.setVisibility(View.GONE);
		foodLoggingListView.setAdapter(sectionAdapter);

		for (int i = 0; i < foodListData.size(); i++) {

			if (!foodListData.get(i).getCalory().equalsIgnoreCase(""))
				caloryCalculate += Float.parseFloat(foodListData.get(i)
						.getCalory());
		}

		setMelFav(foodListData);

		calorieTotalTop.setText((int) caloryCalculate + "" + " cal");
		food_calorie_text.setText((int) caloryCalculate + "");
	}

	public void setMealAdapter() {

		caloryCalculate = 0;
		foodAdapter = new FoodAdapter(getActivity(), mealListData,
				(MealFavNotify) this);

		// need to hide and show new foodLoggingListView2
		// foodLoggingListView.setAdapter(foodAdapter);
		foodLoggingListView.setVisibility(View.GONE);
		foodLoggingListView2.setVisibility(View.VISIBLE);
		foodLoggingListView2.setAdapter(foodAdapter);

		for (int i = 0; i < mealListData.size(); i++) {
			if (!mealListData.get(i).getCalory().equalsIgnoreCase(""))
				caloryCalculate += Float.parseFloat(mealListData.get(i)
						.getCalory());
		}

		calorieTotalTop.setText((int) caloryCalculate + "" + " cal");
		food_calorie_text.setText((int) caloryCalculate + "");
		totalCalory.setText((int) caloryCalculate + "" + " cal");
		try {
			if (mealListData.size() > 0) {
				setMelFav(mealListData);
			} else {
				totalFoodFooterAdapter = new TotalFoodFooterAdapter(
						getActivity(), currActivewt, currActiveCalwt,
						String.valueOf((int) caloryCalculate), "0");
				listTotalFrame_FoodLogging.setAdapter(totalFoodFooterAdapter);
				totalFoodFooterAdapter.notifyDataSetChanged();
			}
		} catch (Exception e) {
			totalFoodFooterAdapter = new TotalFoodFooterAdapter(getActivity(),
					currActivewt, currActiveCalwt,
					String.valueOf((int) caloryCalculate), "0");
			listTotalFrame_FoodLogging.setAdapter(totalFoodFooterAdapter);
			totalFoodFooterAdapter.notifyDataSetChanged();
			e.printStackTrace();
		}

	}

	
	public void setMealAdapterTwo(ArrayList<FitmiFoodLogDAO> mealListDatatwo) {
		mealListData = new ArrayList<FitmiFoodLogDAO>(mealListDatatwo);
		caloryCalculate = 0;
		foodAdapter = new FoodAdapter(getActivity(), mealListData,
				(MealFavNotify) this);

		// need to hide foodLoggingListView and show new foodLoggingListView2
		// foodLoggingListView.setAdapter(foodAdapter);
		foodLoggingListView.setVisibility(View.GONE);
		foodLoggingListView2.setVisibility(View.VISIBLE);
		foodLoggingListView2.setAdapter(foodAdapter);

		for (int i = 0; i < mealListData.size(); i++) {
			if (!mealListData.get(i).getCalory().equalsIgnoreCase(""))
				caloryCalculate += Float.parseFloat(mealListData.get(i)
						.getCalory());
		}

		calorieTotalTop.setText((int) caloryCalculate + "" + " cal");
		food_calorie_text.setText((int) caloryCalculate + "");
		totalCalory.setText((int) caloryCalculate + "" + " cal");
		try {
			if (mealListData.size() > 0) {
				setMelFav(mealListData);
			} else {
				totalFoodFooterAdapter = new TotalFoodFooterAdapter(
						getActivity(), currActivewt, currActiveCalwt,
						String.valueOf((int) caloryCalculate), "0");
				listTotalFrame_FoodLogging.setAdapter(totalFoodFooterAdapter);
				totalFoodFooterAdapter.notifyDataSetChanged();
			}
		} catch (Exception e) {
			totalFoodFooterAdapter = new TotalFoodFooterAdapter(getActivity(),
					currActivewt, currActiveCalwt,
					String.valueOf((int) caloryCalculate), "0");
			listTotalFrame_FoodLogging.setAdapter(totalFoodFooterAdapter);
			totalFoodFooterAdapter.notifyDataSetChanged();
			e.printStackTrace();
		}

	}

	public void recentFoodAdapter() {
		recentFoodAdapter = new RecentFoodAdapter(getActivity(), foodListRecent);
		recentFoodMealsListView.setAdapter(recentFoodAdapter);
		favListView.setAdapter(recentFoodAdapter);
	}

	public void recentMealAdapter() {
		recentMealAdapter = new EntryRecentMealAdapter(getActivity(), itemsMeal);
		recentFoodMealsListView.setAdapter(recentMealAdapter);
	}

	public void favFoodAdapter() {
		EntryRecentFoodMealAdapter favFoodAdapter = new EntryRecentFoodMealAdapter(
				getActivity(), itemsMeal);
		favListView.setAdapter(favFoodAdapter);
	}

	public void favMealAdapter() {
		// recentMealAdapter = new
		// EntryRecentMealAdapter(getActivity(),itemsMeal);
		favMealAdapter = new EntryFavMealAdapter(getActivity(), itemsMeal);
		favListView.setAdapter(favMealAdapter);
	}

	public void favMealItemAdapter() {
		recentMealItemAdapter = new EntryFavMealItemAdapter(getActivity(),
				itemsMeal);
		favListView.setAdapter(recentMealItemAdapter);
	}

	public void searchListAdapter() {

		caloryCalculate = 0;
		foodAdapterSearch = new FoodAdapter(getActivity(), foodListDataAlies,
				(MealFavNotify) this);
		foodLoggingListView.setAdapter(foodAdapterSearch);
		for (int i = 0; i < foodListDataAlies.size(); i++) {
			if (!foodListDataAlies.get(i).getCalory().equalsIgnoreCase(""))
				caloryCalculate += Float.parseFloat(foodListDataAlies.get(i)
						.getCalory());
		}

		calorieTotalTop.setText((int) caloryCalculate + "" + " cal");
		food_calorie_text.setText((int) caloryCalculate + "");
		totalCalory.setText((int) caloryCalculate + "" + " cal");
		foodAdapterSearch.notifyDataSetChanged();
		/*
		 * totalFoodFooterAdapter=new TotalFoodFooterAdapter(getActivity(), "",
		 * "", String.valueOf((int)caloryCalculate));
		 * listTotalFrame_FoodLogging.setAdapter(totalFoodFooterAdapter);
		 */
		setMelFav(foodListDataAlies);
	}

	public void searchListAdapterListview2() {

		
		//if(foodLoggingListView.getVisibility()==View.VISIBLE)
		//{
			foodLoggingListView.setVisibility(View.GONE); 
			foodLoggingListView2.setVisibility(View.VISIBLE); 
		//}
		caloryCalculate = 0;
		foodAdapter = new FoodAdapter(getActivity(), foodListDataAlies,
				(MealFavNotify) this);
		foodLoggingListView2.setAdapter(foodAdapter);
		for (int i = 0; i < foodListDataAlies.size(); i++) {
			if (!foodListDataAlies.get(i).getCalory().equalsIgnoreCase(""))
				caloryCalculate += Float.parseFloat(foodListDataAlies.get(i)
						.getCalory());
		}

		calorieTotalTop.setText((int) caloryCalculate + "" + " cal");
		food_calorie_text.setText((int) caloryCalculate + "");
		totalCalory.setText((int) caloryCalculate + "" + " cal");
		setFoodSpinner();
		/*
		 * totalFoodFooterAdapter=new TotalFoodFooterAdapter(getActivity(), "",
		 * "", String.valueOf((int)caloryCalculate));
		 * listTotalFrame_FoodLogging.setAdapter(totalFoodFooterAdapter);
		 */
		setMelFav(foodListDataAlies);
		
	}

	public void dialog() {

		DateModule dateObj = new DateModule();

		final String foodName = dateObj.getDefaultFoodNameDate();

		final Dialog dialog = new Dialog(getActivity()/* ,R.style.Theme_Dialog */);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawableResource(
				android.R.color.transparent);
		dialog.setContentView(R.layout.dialog_add_meal);

		final EditText mealName = (EditText) dialog.findViewById(R.id.mealName);

		// mealName.setText("Breakfast_"+foodName);

		dialog.findViewById(R.id.savebtn).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {

						String mealname = mealName.getText().toString();

						if (mealname.equalsIgnoreCase("")) {
							Toast.makeText(getActivity(), "Enter meal name",
									Toast.LENGTH_LONG).show();
							return;
						}

						databaseObject.openDatabase();

						if (mealIdfromTable.equalsIgnoreCase("")) {
							MealTypeDAO mealType = new MealTypeDAO();
							mealType.setMealTypeName(mealname);
							FoodLoginModule.insertMealTypeTable(mealType,
									databaseObject);
							mealIdfromTable = FoodLoginModule.getMealId(
									mealname, databaseObject);
						}

						for (int i = initListSize; i < foodListData.size(); i++) {
							FitmiFoodLogDAO obj = foodListData.get(i);
							obj.setMealId(mealIdfromTable);

							// System.out.println("Food Id==================:"+mealIdfromTable);
							Log.d("Food Id==================:", mealIdfromTable);
							FoodLoginModule.insertFitmifoodLogTable(obj,
									databaseObject);
						}

						databaseObject.closeDataBase();
						dialog.dismiss();
						getActivity().onBackPressed();
					}
				});
		dialog.findViewById(R.id.cancelbtn).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

		breakFastImg = (ImageView) dialog.findViewById(R.id.imgViewBreakfast);
		breakFastImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mealName.setText("Breakfast_" + foodName);

				mealIdfromTable = FoodLoginModule.getMealId("breakfast",
						databaseObject);

				Log.e("Breakfast click", "yes");
				breakFastImg.setBackgroundResource(R.drawable.circle_green);
				lunchImg.setBackgroundResource(R.drawable.circle_lightgreen);
				dinnerImg.setBackgroundResource(R.drawable.circle_lightgreen);
				snacksImg.setBackgroundResource(R.drawable.circle_lightgreen);
			}
		});

		lunchImg = (ImageView) dialog.findViewById(R.id.imgViewLunch);
		lunchImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mealName.setText("Lunch_" + foodName);

				mealIdfromTable = FoodLoginModule.getMealId("lunch",
						databaseObject);

				breakFastImg
						.setBackgroundResource(R.drawable.circle_lightgreen);
				lunchImg.setBackgroundResource(R.drawable.circle_green);
				dinnerImg.setBackgroundResource(R.drawable.circle_lightgreen);
				snacksImg.setBackgroundResource(R.drawable.circle_lightgreen);
			}
		});

		dinnerImg = (ImageView) dialog.findViewById(R.id.imgViewDinner);
		dinnerImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mealName.setText("Dinner_" + foodName);

				mealIdfromTable = FoodLoginModule.getMealId("dinner",
						databaseObject);

				breakFastImg
						.setBackgroundResource(R.drawable.circle_lightgreen);
				lunchImg.setBackgroundResource(R.drawable.circle_lightgreen);
				dinnerImg.setBackgroundResource(R.drawable.circle_green);
				snacksImg.setBackgroundResource(R.drawable.circle_lightgreen);
			}
		});

		snacksImg = (ImageView) dialog.findViewById(R.id.imgViewSnack);
		snacksImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mealName.setText("Snack_" + foodName);

				mealIdfromTable = FoodLoginModule.getMealId("snack",
						databaseObject);

				breakFastImg
						.setBackgroundResource(R.drawable.circle_lightgreen);
				lunchImg.setBackgroundResource(R.drawable.circle_lightgreen);
				dinnerImg.setBackgroundResource(R.drawable.circle_lightgreen);
				snacksImg.setBackgroundResource(R.drawable.circle_green);
			}
		});
		dialog.show();
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
		dialog.getWindow()
				.setLayout((6 * width) / 7, LayoutParams.WRAP_CONTENT);

	}

	/**
	 * Dialog delete item
	 */

	public void dialogDeleteItem(final int position) {

	/*	final Dialog dialog = new Dialog(getActivity() ,R.style.Theme_Dialog );
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawableResource(
				android.R.color.transparent);
		dialog.setContentView(R.layout.dialog_delete_item);*/
		 final AlertDialog alertDialog = new AlertDialog.Builder(getActivity(),AlertDialog.THEME_HOLO_DARK).create();
		 
			// alertDialog.setTitle("Fitmi");
		//	 alertDialog.setIcon(R.drawable.app_icon);
			 
			
	         alertDialog.setMessage("Do you want to delete this item ?");
	         
	         alertDialog.setButton("Yes", new DialogInterface.OnClickListener() {
	             public void onClick(DialogInterface dialog, int which) {

						if (foodListDataAlies.size() > 0) {

							caloryCalculate = 0;
							foodLogObj.deleteFood(foodListDataAlies.get(
									position).getFoodLogId());
							foodListDataAlies.remove(position);
							foodAdapterSearch.notifyDataSetChanged();

							for (int i = 0; i < foodListDataAlies.size(); i++) {
								if (!foodListDataAlies.get(i).getCalory()
										.equalsIgnoreCase(""))
									caloryCalculate += Float
											.parseFloat(foodListDataAlies
													.get(i).getCalory());
							}
							
							setMelFav(foodListDataAlies);
							setFoodSpinner();
						} else if (mealListData != null
								&& mealListData.size() > 0) {

							caloryCalculate = 0;
							foodLogObj.deleteFood(mealListData.get(position)
									.getFoodLogId());
							mealListData.remove(position);
							foodAdapter.notifyDataSetChanged();

							for (int i = 0; i < mealListData.size(); i++) {
								if (!mealListData.get(i).getCalory()
										.equalsIgnoreCase(""))
									caloryCalculate += Float
											.parseFloat(mealListData.get(i)
													.getCalory());
							}

							totalCalory.setText((int) caloryCalculate + ""
									+ " cal");
							/*
							 * totalFoodFooterAdapter=new
							 * TotalFoodFooterAdapter(getActivity(), "", "",
							 * String.valueOf((int)caloryCalculate));
							 * listTotalFrame_FoodLogging
							 * .setAdapter(totalFoodFooterAdapter);
							 */
							setMelFav(mealListData);
							setFoodSpinner();
						}

						alertDialog.dismiss();
					}
				});
	         alertDialog.setButton2("No",new DialogInterface.OnClickListener() {
	             @Override
	             public void onClick(DialogInterface dialog, int which) {
	            	 alertDialog.dismiss();
					}
				});

	         alertDialog.show();
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		// int height = metrics.heightPixels;
		alertDialog.getWindow()
				.setLayout((6 * width) / 7, LayoutParams.WRAP_CONTENT);

	}

	public void dialogShowLongclick(final int position) {

		final Dialog dialog = new Dialog(getActivity()/* ,R.style.Theme_Dialog */);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawableResource(
				android.R.color.transparent);
		dialog.setContentView(R.layout.dialog_longclick_item);

		dialog.findViewById(R.id.btnEdit).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {

						editDialog(position);
						dialog.dismiss();
					}
				});

		dialog.findViewById(R.id.btnDelete).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {

						dialogDeleteItem(position);

						dialog.dismiss();
					}
				});

		dialog.findViewById(R.id.btnCancel).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

		dialog.show();
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
		dialog.getWindow()
				.setLayout((6 * width) / 7, LayoutParams.WRAP_CONTENT);

	}

	// *********************** End Dialog *******************************

	@Override
	public void calorieUpdate(TextView totalCalory, Bundle bundle,
			DatabaseHelper databaseObject) {
		// TODO Auto-generated method stub

		FoodLoginModule foodLogObjLocal = new FoodLoginModule(getActivity());
		try {

			databaseObject.createDatabase();

			databaseObject.openDatabase();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (bundle != null) {
			mealId = bundle.getInt("mealid", 0);
			foodType = bundle.getString("foodtype");

			foodListData = foodLogObjLocal.selectFoodList(
					String.valueOf(mealId), databaseObject);

			initListSize = foodListData.size();

			for (int i = 0; i < foodListData.size(); i++) {

				if (!foodListData.get(i).getCalory().equalsIgnoreCase(""))
					caloryCalculate += Float.parseFloat(foodListData.get(i)
							.getCalory());
			}

		} else {
			foodListData = foodLogObjLocal.selectAllFoodList(databaseObject);
			initListSize = foodListData.size();

			for (int i = 0; i < foodListData.size(); i++) {

				if (!foodListData.get(i).getCalory().equalsIgnoreCase(""))
					caloryCalculate += Float.parseFloat(foodListData.get(i)
							.getCalory());
			}

		}
		databaseObject.closeDataBase();
		totalCalory.setText(caloryCalculate + "" + " cal");

		/*
		 * totalFoodFooterAdapter=new TotalFoodFooterAdapter(getActivity(), "",
		 * "", String.valueOf((int)caloryCalculate));
		 * listTotalFrame_FoodLogging.setAdapter(totalFoodFooterAdapter);
		 */
		setMelFav(foodListData);
		HomeFragment tosetCalory = new HomeFragment();
		NotificationTotalCaloryChange callBack = (NotificationTotalCaloryChange) tosetCalory;

		callBack.setTotalCalory(caloryCalculate);
	}

	BroadcastReceiver dateSetReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			Bundle bundleBroadcast = intent.getExtras();
			dateTextView.setText(bundleBroadcast.getString("key"));

			if (bundle != null) {

				mealId = bundle.getInt("mealid", 0);
				foodType = bundle.getString("foodtype");

				foodListData = foodLogObj.selectFoodList(
						String.valueOf(mealId), databaseObject);

				initListSize = foodListData.size();
				setFoodlistData(foodListData, false);

				for (int i = 0; i < foodListData.size(); i++) {

					if (!foodListData.get(i).getCalory().equalsIgnoreCase(""))
						caloryCalculate += Float.parseFloat(foodListData.get(i)
								.getCalory());
				}

			} else {
				String setDate = dateTextView.getText().toString().trim();
				setDate = getDate.getDateFrag(setDate);

				// dateTextView.setText(setDate);

				com.fitmi.utils.Constants.conditionDate = getDate
						.conditionDateFormat(setDate);
				com.fitmi.utils.Constants.postDate = getDate
						.getFormatDateSearchInsert(setDate);
				com.fitmi.utils.Constants.sTempDate = com.fitmi.utils.Constants.conditionDate;
				com.fitmi.utils.Constants.sDate = getDate.sDateFormat(setDate);

				foodListRecent = foodLogObj.selectFoodList(
						String.valueOf(mealId), databaseObject);
				recentFoodAdapter();
				foodListData = foodLogObj.selectAllFoodList(databaseObject);
				initListSize = foodListData.size();
				setFoodlistData(foodListData, true);

				for (int i = 0; i < foodListData.size(); i++) {
					if (!foodListData.get(i).getCalory().equalsIgnoreCase(""))
						caloryCalculate += Float.parseFloat(foodListData.get(i)
								.getCalory());
				}
				setDataPreNext();
			}

			sectionAdapter.notifyDataSetChanged();
			foodListRecent = foodLogObj.selectAllFoodList(databaseObject);

			if (recentFoodAdapter != null)
				recentFoodAdapter.notifyDataSetChanged();
		}
	};

	@OnClick(R.id.logBtn)
	public void logClick() {
		// need to add back bundle

		if (mealId > 0) {
			for (int i = 0; i < com.fitmi.utils.Constants.foodLogData.size(); i++) {

				FitmiFoodLogDAO fitmiFoodLogData = com.fitmi.utils.Constants.foodLogData
						.get(i);
				fitmiFoodLogData.setMealId(String.valueOf(mealId));
				FoodLoginModule.insertFitmifoodLogTable(fitmiFoodLogData,
						databaseObject);
			}
		}
		getActivity().onBackPressed();
	}

	public void dialogFoodLog() {
		final Dialog dialog = new Dialog(getActivity()/* ,R.style.Theme_Dialog */);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawableResource(
				android.R.color.transparent);
		dialog.setContentView(R.layout.dialog_replace_add_cancel);

		TextView dialogTitleTop = (TextView) dialog
				.findViewById(R.id.dialogTitleTop);
		dialogTitleTop.setText("Would you like to log this meal ?");

		TextView dialogTitleBottom = (TextView) dialog
				.findViewById(R.id.dialogTitleBottom);
		dialogTitleBottom.setVisibility(View.GONE);

		Button replacebtn = (Button) dialog.findViewById(R.id.replacebtn);
		replacebtn.setText("Add");

		Button cancelbtn = (Button) dialog.findViewById(R.id.cancelbtn);

		Button addbtn = (Button) dialog.findViewById(R.id.addbtn);
		addbtn.setVisibility(View.INVISIBLE);

		replacebtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				if (mealId > 0) {
					databaseObject.openDatabase();

					for (int i = initListSize; i < foodListData.size(); i++) {
						FitmiFoodLogDAO obj = foodListData.get(i);
						obj.setMealId(String.valueOf(mealId));

						System.out.println("Food Id==================:"
								+ mealId);

						FoodLoginModule.insertFitmifoodLogTable(obj,
								databaseObject);
					}

					databaseObject.closeDataBase();
					getActivity().onBackPressed();
				}

			}
		});

		cancelbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();

	}

	/**
	 * Dialog to add meal with out meal id
	 */

	public void dialogFood() {
		final Dialog dialog = new Dialog(getActivity()/* ,R.style.Theme_Dialog */);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawableResource(
				android.R.color.transparent);
		dialog.setContentView(R.layout.dialog_replace_add_cancel);

		TextView dialogTitleTop = (TextView) dialog
				.findViewById(R.id.dialogTitleTop);
		dialogTitleTop.setText("Would you like to log this meal ?");

		TextView dialogTitleBottom = (TextView) dialog
				.findViewById(R.id.dialogTitleBottom);
		dialogTitleBottom.setVisibility(View.GONE);

		Button replacebtn = (Button) dialog.findViewById(R.id.replacebtn);
		replacebtn.setText("Yes");

		Button cancelbtn = (Button) dialog.findViewById(R.id.cancelbtn);
		cancelbtn.setText("Edit");

		Button addbtn = (Button) dialog.findViewById(R.id.addbtn);
		addbtn.setText("No");

		replacebtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				dialog();
			}
		});

		addbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		cancelbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();

	}

	@OnClick(R.id.txtPreview)
	public void datePreview() {
		String setDate = dateTextView.getText().toString().trim();
		setDate = getDate.datePreview(setDate);
		dateTextView.setText(setDate);

		com.fitmi.utils.Constants.conditionDate = getDate
				.conditionDateFormat(setDate);
		com.fitmi.utils.Constants.postDate = getDate
				.getFormatDateSearchInsert(setDate);
		com.fitmi.utils.Constants.sTempDate = com.fitmi.utils.Constants.conditionDate;
		com.fitmi.utils.Constants.sDate = getDate.sDateFormat(setDate);
		setDataPreNext();
		foodListRecent = foodLogObj.selectFoodList(String.valueOf(mealId),
				databaseObject);
		recentFoodAdapter();
	}

	@OnClick(R.id.txtNext)
	public void dateNext() {
		String setDate = dateTextView.getText().toString().trim();
		setDate = getDate.dateNext(setDate);

		dateTextView.setText(setDate);

		com.fitmi.utils.Constants.conditionDate = getDate
				.conditionDateFormat(setDate);
		com.fitmi.utils.Constants.postDate = getDate
				.getFormatDateSearchInsert(setDate);
		com.fitmi.utils.Constants.sTempDate = com.fitmi.utils.Constants.conditionDate;
		com.fitmi.utils.Constants.sDate = getDate.sDateFormat(setDate);
		setDataPreNext();

		foodListRecent = foodLogObj.selectFoodList(String.valueOf(mealId),
				databaseObject);
		recentFoodAdapter();
	}

	public void setDataPreNext() {
		caloryCalculate = 0;

		if (bundle != null) {
			mealId = bundle.getInt("mealid", 0);
			foodType = bundle.getString("foodtype");

			foodListData = foodLogObj.selectFoodList(String.valueOf(mealId),
					databaseObject);
			initListSize = foodListData.size();
			setFoodlistData(foodListData, false);
			Log.e("meal id during next ", String.valueOf(mealId));
			switch (mealId) {
			case 1:

				mealId = 1;
				heading.setText("Breakfast");
				break;

			case 2:
				mealId = 2;
				heading.setText("Lunch");
				break;
			case 3:

				mealId = 3;
				heading.setText("Dinner");
				break;
			case 4:

				mealId = 4;
				heading.setText("Snack");
				break;
			}
			Log.e("meal id during next after swich case ",
					String.valueOf(mealId));
			for (int i = 0; i < foodListData.size(); i++) {
				if (!foodListData.get(i).getCalory().equalsIgnoreCase(""))
					caloryCalculate += Float.parseFloat(foodListData.get(i)
							.getCalory());
			}

		} else {
			Log.e("meal id during next Else", String.valueOf(mealId));
			foodListData = foodLogObj.selectAllFoodList(databaseObject);
			initListSize = foodListData.size();
			setFoodlistData(foodListData, true);

			for (int i = 0; i < foodListData.size(); i++) {
				if (!foodListData.get(i).getCalory().equalsIgnoreCase(""))
					caloryCalculate += Float.parseFloat(foodListData.get(i)
							.getCalory());
			}
		}
		// adapter issue
		// sectionAdapter = new
		// EntryFoodLoginAdapter(getActivity(),items,totalCalory,bundle);
		try {
			sectionAdapter.notifyDataSetChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}
		foodListRecent = foodLogObj.selectAllFoodList(databaseObject);

		totalCalory.setText((int) caloryCalculate + "" + " cal");
	//	food_calorie_text.setText((int) caloryCalculate + "");
	//	calorieTotalTop.setText((int) caloryCalculate + "" + " cal");
		setMelFav(foodListData);
		
		/*
		 * totalFoodFooterAdapter=new TotalFoodFooterAdapter(getActivity(), "",
		 * "", String.valueOf((int)caloryCalculate));
		 * listTotalFrame_FoodLogging.setAdapter(totalFoodFooterAdapter);
		 */
		/*
		 * setFoodSpinner(); if(addMealLiner.getVisibility()==View.GONE) {
		 * mealListData= foodLogObj.selectFoodList( String.valueOf(mealId),
		 * databaseObject); nextbtnVal=1; mealListData=new
		 * ArrayList<FitmiFoodLogDAO>(foodListData); setMealAdapter();
		 * 
		 * setMealAdapterTwo(foodListData);
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * if (foodAdapterSearch != null)
		 * foodAdapterSearch.notifyDataSetChanged(); if (foodAdapter != null)
		 * foodAdapter.notifyDataSetChanged();
		 * 
		 * }
		 */
		if (addMealLiner.getVisibility() == View.GONE) {
			if (foodAdapterSearch != null) {
				foodListDataAlies = foodLogObj.selectFoodList(
						String.valueOf(mealId), databaseObject);
				searchListAdapter();
			}
			if (foodAdapter != null) {
				mealListData = foodLogObj.selectFoodList(
						String.valueOf(mealId), databaseObject);
				setMealAdapter();
			}
		
		}
		if (recentFoodAdapter != null)
			recentFoodAdapter.notifyDataSetChanged();
		setFoodSpinner();

	}

	/**
	 * Showing google speech input dialog
	 * */
	private void promptSpeechInput() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
				getString(R.string.speech_prompt));
		try {
			startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
		} catch (ActivityNotFoundException a) {
			Toast.makeText(getActivity(),
					getString(R.string.speech_not_supported),
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Receiving speech input
	 * */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (callActivityResult == 0) {

			switch (requestCode) {
			case REQ_CODE_SPEECH_INPUT: {
				if (resultCode == getActivity().RESULT_OK && null != data) {

					ArrayList<String> result = data
							.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
					searchEditText.setText(result.get(0));
					/*
					 * placesAutoCompleteAdapter = new
					 * PlacesAutoCompleteAdapter( getActivity(),
					 * R.layout.item_autocomplete);
					 */
					/*
					 * searchEditText.setAdapter(new
					 * PlacesAutoCompleteAdapter(getActivity(),
					 * R.layout.item_autocomplete));
					 */
					// searchEditText.setAdapter(placesAutoCompleteAdapter);
				}
				break;
			}

			}

		} else {

			if (requestCode == 0) {
				if (resultCode == getActivity().RESULT_OK) {
					String contents = data.getStringExtra("SCAN_RESULT");
					String format = data.getStringExtra("SCAN_RESULT_FORMAT");

					barcodeScannApi(contents);

					// Toast.makeText(getActivity(),
					// "SCAN_RESULT ==: "+contents+" SCAN_RESULT_FORMAT==: "+format,
					// Toast.LENGTH_LONG).show();

					// Handle successful scan
				} else if (resultCode == getActivity().RESULT_CANCELED) {
					// Handle cancel
				}
			}
		}

	}

	/**
	 * Dialog add item
	 */

	public void dialogAddItem(final FitmiFoodLogDAO recentFood, final int pos) {
		final Dialog dialog = new Dialog(getActivity()/* ,R.style.Theme_Dialog */);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_add_item);

		TextView dialogTitle = (TextView) dialog.findViewById(R.id.dialogTitle);
		dialogTitle.setText("Do you want to delete this item?");

		dialog.findViewById(R.id.savebtn).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {

						foodLogObj.deleteFavFood(recentFood.getFoodLogId());
						foodListRecent.remove(pos);
						recentFoodAdapter.notifyDataSetChanged();

						dialog.dismiss();

					}
				});
		dialog.findViewById(R.id.cancelbtn).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

		dialog.show();
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		// int height = metrics.heightPixels;
		dialog.getWindow()
				.setLayout((6 * width) / 7, LayoutParams.WRAP_CONTENT);

	}

	public void dialogDeleteFav(final FitmiFoodLogDAO recentFood, final int pos) {
		final Dialog dialog = new Dialog(getActivity()/* ,R.style.Theme_Dialog */);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_add_item);

		TextView dialogTitle = (TextView) dialog.findViewById(R.id.dialogTitle);
		dialogTitle.setText("Do you want to delete this item?");

		dialog.findViewById(R.id.savebtn).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {

						foodListRecent.remove(pos);
						favAdapter.notifyDataSetChanged();
						foodLogObj.updateFavourite(recentFood.getFoodLogId(),
								"0");
						dialog.dismiss();
					}
				});
		dialog.findViewById(R.id.cancelbtn).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

		dialog.show();
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		// int height = metrics.heightPixels;
		dialog.getWindow()
				.setLayout((6 * width) / 7, LayoutParams.WRAP_CONTENT);

	}

	/**
	 * Dialog end
	 */

	public void setFoodlistData(ArrayList<FitmiFoodLogDAO> itemList,
			boolean isSection) {
		float caloryHeader = 0;
		String mealType = "";
		// int position = -1;
		String calorieSum = "0";

		Collections.sort(itemList, new Comparator<FitmiFoodLogDAO>() {
			public int compare(FitmiFoodLogDAO s1, FitmiFoodLogDAO s2) {
				return s1.getMealType().compareToIgnoreCase(s2.getMealType());
			}
		});

		if (items.size() > 0) {
			items.clear();
			demoSection.clear();
		}

		for (int j = 0; j < itemList.size(); j++) {
			FitmiFoodLogDAO foodItem = new FitmiFoodLogDAO();

			if (isSection) {

				String mealName = itemList.get(j).getMealType();
				String output = mealName.substring(0, 1).toUpperCase()
						+ mealName.substring(1);
				SectionItemFoodlogin header = new SectionItemFoodlogin(output);

				if (!demoSection.contains(mealName)) {

					if (mealName.equalsIgnoreCase("breakfast")) {
						// mealId = 1;
						calorieSum = FoodLoginModule.todayTotalCaloryByMealid(
								"1", databaseObject);

					} else if (mealName.equalsIgnoreCase("lunch")) {
						// mealId = 2;
						calorieSum = FoodLoginModule.todayTotalCaloryByMealid(
								"2", databaseObject);

					} else if (mealName.equalsIgnoreCase("dinner")) {
						calorieSum = FoodLoginModule.todayTotalCaloryByMealid(
								"3", databaseObject);
						// mealId = 3;

					} else if (mealName.equalsIgnoreCase("snack")) {
						// mealId = 4;
						calorieSum = FoodLoginModule.todayTotalCaloryByMealid(
								"4", databaseObject);
					}

					header.setTotal(calorieSum);

					demoSection.add(mealName);
					items.add(header);

				}

			}

			foodItem.setFoodLogId(itemList.get(j).getFoodLogId());
			foodItem.setFoodName(itemList.get(j).getFoodName());
			foodItem.setDescription(itemList.get(j).getDescription());
			foodItem.setCalory(itemList.get(j).getCalory());
			foodItem.setReferenceFoodId(itemList.get(j).getReferenceFoodId());
			foodItem.setMealWeight(itemList.get(j).getMealWeight());
			items.add(foodItem);
		}
	}

	/**
	 * Set header for recent meal
	 */

	public void setFoodlistDataRecentmeal(ArrayList<FitmiFoodLogDAO> itemlist,
			boolean isSection) {
		String mealType = "";

		if (itemsMeal.size() > 0) {
			itemsMeal.clear();
		}

		if (demoSection.size() > 0) {
			demoSection.clear();
		}

		Collections.sort(itemlist, new Comparator<FitmiFoodLogDAO>() {
			public int compare(FitmiFoodLogDAO s1, FitmiFoodLogDAO s2) {
				return s1.getMealType().compareToIgnoreCase(s2.getMealType());
			}
		});

		for (int j = 0; j < itemlist.size(); j++) {
			FitmiFoodLogDAO foodItem = new FitmiFoodLogDAO();

			if (isSection) {

				String mealName = itemlist.get(j).getMealType();
				String output = mealName.substring(0, 1).toUpperCase()
						+ mealName.substring(1);
				SectionItemFoodlogin header = new SectionItemFoodlogin(output);

				if (!demoSection.contains(mealName)) {

					demoSection.add(mealName);
					itemsMeal.add(header);
				}

			}

			foodItem.setFoodLogId(itemlist.get(j).getFoodLogId());
			foodItem.setFoodName(itemlist.get(j).getFoodName());
			foodItem.setDescription(itemlist.get(j).getDescription());
			foodItem.setCalory(itemlist.get(j).getCalory());
			foodItem.setReferenceFoodId(itemlist.get(j).getReferenceFoodId());
			foodItem.setFavourite(itemlist.get(j).getFavourite());
			foodItem.setMealFavourite(itemlist.get(j).getMealFavourite());
			foodItem.setMealWeight(itemlist.get(j).getMealWeight());
			foodItem.setMealidList(itemlist.get(j).getMealidList());
			itemsMeal.add(foodItem);
		}
	}

	/**
	 * end header recent meal
	 */

	public void setFoodSpinner() {
		Log.e("Started setFoodSpinner","setFoodSpinner");
		totalCaloty = ActivityModule.todayTotalCaloryBurn(
				com.fitmi.utils.Constants.conditionDate, databaseObject,getActivity());
		if (totalCaloty == null)
	     		activity_calorie_text.setText("0");
		else
			activity_calorie_text.setText(totalCaloty);

		caloryInTake = com.fitmi.utils.Constants.TOTAL_CALORY_INTAKE;
		try{
		calorySumList = FoodLoginModule.totalCalory(databaseObject,getActivity());
		}catch(Exception a)
		{
			Log.e("FoodLoginModule.totalCalory","found");
		}
		remainCalory = (int) (Float.parseFloat(caloryInTake) - caloryCalculate);

		daily_calorie_text.setText(caloryInTake);

		foodValues = new String[5];

		if (remainCalory < 0) {

			remainCaloryBurn.setText("0");
		} else {
			remainCaloryBurn.setText(remainCalory + "");
		}
		int calory = (int) caloryCalculate;
		foodValues[0] = calory + "";
		int sum_total=0;
		for (int i = 0; i < 4; i++) {
			if (calorySumList.get(i) != null) {

				double sum = Double.parseDouble(calorySumList.get(i));
				sum_total=sum_total+(int) sum;
				foodValues[i + 1] = (int) sum + "";
			} else
				foodValues[i + 1] = "0";
		}
		foodValues[0]=sum_total+ "";
		adapter = new ArrayAdapter<String>(getActivity(),
				R.layout.spinner_item, foodValues);

		flsa = new FoodLoggingSpinnerAdapter(getActivity(), foodValues,
				fooddrawableValues, R.drawable.circle_green);
		adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
		foodLoggingSpinner.setAdapter(flsa);
		
		foodListData = foodLogObj.selectAllFoodList(databaseObject);
		//setFoodlistData(foodListData, true);
		//setTotal(foodListData);
		food_calorie_text.setText(foodValues[0]);
		calorieTotalTop.setText(foodValues[0]+ "" + " cal");
		
		foodDropClick = false;
		
		
	}


	public void setActivitySpinner() {

		totalCaloty = ActivityModule.todayTotalCaloryBurn(
				com.fitmi.utils.Constants.conditionDate, databaseObject,getActivity());

		caloryBurnList = ActivityModule.totalCaloryBurn(databaseObject);
		activityValues = new String[caloryBurnList.size() + 1];
		activitydrawableValuesAlies = new Integer[caloryBurnList.size() + 1];
		activityType = new char[caloryBurnList.size() + 1];
		activitydrawableValuesAlies[0] = activitydrawableValues[0];

		if (totalCaloty == null) {
			activity_calorie_text.setText("0");
			activityValues[0] = "0";
			activityType[0] = 'A';
		} else {
			activity_calorie_text.setText(totalCaloty);
			activityValues[0] = totalCaloty + "";
			activityType[0] = 'A';
		}
		for (int i = 0; i < caloryBurnList.size(); i++) {
			if (caloryBurnList.get(i) != null) {

				ExerciseItemDAO obj = caloryBurnList.get(i);

				activityValues[i + 1] = obj.getExerciseSum();

				if (obj.getExerciseName().equalsIgnoreCase("Weight lifting")) {

					activitydrawableValuesAlies[i + 1] = activitydrawableValues[6];
					activityType[i + 1] = 'W';

				} else if (obj.getExerciseName().equalsIgnoreCase(
						"Boxing - general")) {

					activitydrawableValuesAlies[i + 1] = activitydrawableValues[5];
					activityType[i + 1] = 'B';

				} else if (obj.getExerciseName().equalsIgnoreCase(
						"Jumping rope")) {

					activitydrawableValuesAlies[i + 1] = activitydrawableValues[4];
					activityType[i + 1] = 'J';

				} else if (obj.getExerciseName().equalsIgnoreCase("Swimming")) {

					activitydrawableValuesAlies[i + 1] = activitydrawableValues[3];
					activityType[i + 1] = 'S';

				} else if (obj.getExerciseName().equalsIgnoreCase("Walking")) {

					activitydrawableValuesAlies[i + 1] = activitydrawableValues[2];
					activityType[i + 1] = 'w';

				} else if (obj.getExerciseName().equalsIgnoreCase("Gymnastics")) {

					activitydrawableValuesAlies[i + 1] = activitydrawableValues[1];
					activityType[i + 1] = 'G';
				}
			}
			/*
			 * else activityValues[i+1]="0";
			 */
		}

		activityDropClick = false;
	}

	/*
	 * set fav spinner adapter
	 */

	public void setFoodFavSpinner() {
		foodValuesFav = new String[4];

		foodValuesFav[0] = "Breakfast";
		foodValuesFav[1] = "Lunch";
		foodValuesFav[2] = "Dinner";
		foodValuesFav[3] = "Snack";

		adapter = new ArrayAdapter<String>(getActivity(),
				R.layout.spinner_item, foodValuesFav);

		favSpinnerAdapter = new FoodLoggingFavSpinnerAdapter(getActivity(),
				foodValuesFav, fooddrawableValuesFav, R.drawable.circle_green);
		adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

		spinnerFav.setAdapter(favSpinnerAdapter);

		favDropClick = false;
	}

	/*
	 * set recent spinner adapter
	 */

	public void setFoodRecentSpinner() {
		foodValuesFav = new String[4];

		foodValuesFav[0] = "Breakfast";
		foodValuesFav[1] = "Lunch";
		foodValuesFav[2] = "Dinner";
		foodValuesFav[3] = "Snack";

		adapter = new ArrayAdapter<String>(getActivity(),
				R.layout.spinner_item, foodValuesFav);

		recentSpinnerAdapter = new FoodLoggingRecentSpinnerAdapter(
				getActivity(), foodValuesFav, fooddrawableValuesFav,
				R.drawable.circle_green);
		adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

		spinnerRecent.setAdapter(recentSpinnerAdapter);
		recentDropClick = false;
	}

	/**
	 * Barcode scanner implementation
	 */

	@OnClick(R.id.barcodeScann)
	public void clickBarcodeScann() {
		callActivityResult = 1;

		Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		// intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
		intent.putExtra("SCAN_MODE", "ONE_D_MODE");
		startActivityForResult(intent, 0);
	}

	public void barcodeScannApi(String scannResult) {
		try {
			new RetrieveFeedTask().execute(scannResult);
		} catch (Exception e) {

			System.out.println("Exception :" + e);
		}
	}

	@Override
	public void setNewCaloryIntake(String calorie) {
		// TODO Auto-generated method stub
		com.fitmi.utils.Constants.homeCaloryIntake.setText(calorie);
		// com.fitmi.utils.Constants.remainCaloryBurn = remainCaloryBurn;
		String calorieTotal = com.fitmi.utils.Constants.foodcalorieText
				.getText().toString();
		int remainCalory = (int) (Float.parseFloat(calorie) - Float
				.parseFloat(calorieTotal));
		com.fitmi.utils.Constants.remainCaloryBurn.setText(remainCalory + "");
	}

	@OnClick(R.id.imgAddMeal)
	public void clickAddMealBtn() {
		addMealClick = true;
		selectMealLiner.setVisibility(View.VISIBLE);
		searchMealLiner.setVisibility(View.GONE);
		addMealLiner.setVisibility(View.GONE);
	//	items.clear();
		sectionAdapter.notifyDataSetChanged();
	}

	@OnClick(R.id.imgViewBreakfast)
	public void clickBreakfast() {

		selectMealLiner.setVisibility(View.GONE);
		searchMealLiner.setVisibility(View.VISIBLE);
		addMealLiner.setVisibility(View.GONE);
		total_frame.setVisibility(View.VISIBLE);

		Log.e("onclick outside", "yes");
		mealId = 1;
		heading.setText("Breakfast");
	//	items.clear();

		mealIdSpinner = 1;
		mealId = 1;
		mealListData = foodLogObj.selectFoodList(String.valueOf(mealId),
				databaseObject);
		setMealAdapter();
		selectMealLiner.setVisibility(View.GONE);
		searchMealLiner.setVisibility(View.VISIBLE);
		addMealLiner.setVisibility(View.GONE);
		total_frame.setVisibility(View.VISIBLE);
		/*
		 * foodListData = foodLogObj.selectFoodList(String.valueOf(mealId),
		 * databaseObject); setFoodlistData(foodListData, false);
		 * 
		 * sectionAdapter.notifyDataSetChanged();
		 * 
		 * setTotal(foodListData);
		 */
		/*
		 * for(int i=0;i<foodListData.size();i++){
		 * if(!foodListData.get(i).getCalory().equalsIgnoreCase(""))
		 * caloryCalculate +=Float.parseFloat(foodListData.get(i).getCalory());
		 * }
		 * 
		 * calorieTotalTop.setText((int)caloryCalculate+""+" cal");
		 * food_calorie_text.setText((int)caloryCalculate+"");
		 * totalCalory.setText((int)caloryCalculate+""+" cal");
		 */
		showSyncdialog();
	}

	@OnClick(R.id.imgViewLunch)
	public void clickLunch() {

		selectMealLiner.setVisibility(View.GONE);
		searchMealLiner.setVisibility(View.VISIBLE);
		addMealLiner.setVisibility(View.GONE);
		total_frame.setVisibility(View.VISIBLE);

		mealId = 2;
		heading.setText("Lunch");
		//items.clear();
		mealIdSpinner = 2;
		mealId = 2;
		mealListData = foodLogObj.selectFoodList(String.valueOf(mealId),
				databaseObject);
		setMealAdapter();
		selectMealLiner.setVisibility(View.GONE);
		searchMealLiner.setVisibility(View.VISIBLE);
		addMealLiner.setVisibility(View.GONE);
		total_frame.setVisibility(View.VISIBLE);
		// old change by avinash to hide like breakfast tag lunch tag
		/*
		 * foodListData = foodLogObj.selectFoodList(String.valueOf(mealId),
		 * databaseObject); setFoodlistData(foodListData, false);
		 * 
		 * sectionAdapter.notifyDataSetChanged();
		 * 
		 * setTotal(foodListData);
		 */
		/*
		 * for(int i=0;i<foodListData.size();i++){
		 * if(!foodListData.get(i).getCalory().equalsIgnoreCase(""))
		 * caloryCalculate +=Float.parseFloat(foodListData.get(i).getCalory());
		 * }
		 * 
		 * calorieTotalTop.setText((int)caloryCalculate+""+" cal");
		 * food_calorie_text.setText((int)caloryCalculate+"");
		 * totalCalory.setText((int)caloryCalculate+""+" cal");
		 */
		showSyncdialog();
	}

	@OnClick(R.id.imgViewDinner)
	public void clickDinner() {

		selectMealLiner.setVisibility(View.GONE);
		searchMealLiner.setVisibility(View.VISIBLE);
		addMealLiner.setVisibility(View.GONE);
		total_frame.setVisibility(View.VISIBLE);

		mealId = 3;
		heading.setText("Dinner");
		//items.clear();

		mealIdSpinner = 3;
		mealId = 3;
		mealListData = foodLogObj.selectFoodList(String.valueOf(mealId),
				databaseObject);
		setMealAdapter();
		selectMealLiner.setVisibility(View.GONE);
		searchMealLiner.setVisibility(View.VISIBLE);
		addMealLiner.setVisibility(View.GONE);
		total_frame.setVisibility(View.VISIBLE);
		/*
		 * foodListData = foodLogObj.selectFoodList(String.valueOf(mealId),
		 * databaseObject); setFoodlistData(foodListData, false);
		 * sectionAdapter.notifyDataSetChanged();
		 * 
		 * setTotal(foodListData);
		 */
		/*
		 * for(int i=0;i<foodListData.size();i++){
		 * if(!foodListData.get(i).getCalory().equalsIgnoreCase(""))
		 * caloryCalculate +=Float.parseFloat(foodListData.get(i).getCalory());
		 * }
		 * 
		 * calorieTotalTop.setText((int)caloryCalculate+""+" cal");
		 * food_calorie_text.setText((int)caloryCalculate+"");
		 * totalCalory.setText((int)caloryCalculate+""+" cal");
		 */
		showSyncdialog();

	}

	@OnClick(R.id.imgViewSnack)
	public void clickSnack() {

		selectMealLiner.setVisibility(View.GONE);
		searchMealLiner.setVisibility(View.VISIBLE);
		addMealLiner.setVisibility(View.GONE);
		total_frame.setVisibility(View.VISIBLE);

		mealId = 4;
		heading.setText("Snack");
		//items.clear();
		mealIdSpinner = 4;
		mealId = 4;
		mealListData = foodLogObj.selectFoodList(String.valueOf(mealId),
				databaseObject);
		setMealAdapter();
		selectMealLiner.setVisibility(View.GONE);
		searchMealLiner.setVisibility(View.VISIBLE);
		addMealLiner.setVisibility(View.GONE);
		total_frame.setVisibility(View.VISIBLE);


		setMealAdapter();
		selectMealLiner.setVisibility(View.GONE);
		searchMealLiner.setVisibility(View.VISIBLE);
		addMealLiner.setVisibility(View.GONE);
		total_frame.setVisibility(View.VISIBLE);
		showSyncdialog();
	}

	public void favFoodList() {
		foodSpinnerSelectFav = -1;
		favFoodClick = true;
		favFood.setBackgroundColor(getResources().getColor(
				R.color.home_green_select_dark));
		favMeal.setBackgroundColor(getResources().getColor(
				R.color.home_green_deselect_dark));
		intake_linearlayout_fav.setBackgroundColor(getResources().getColor(
				R.color.home_green_deselect_dark));
		recentListViewParentLinear.setVisibility(View.VISIBLE);

	}

	@OnClick(R.id.favFood)
	public void clickFavFood() {
		/*
		 * foodListRecentFav = foodLogObj.selectAllFavFoodList();
		 * favFoodAdapter();
		 */

		if (foodListRecentMeal.size() > 0)
			foodListRecentMeal.clear();

		foodListRecentMeal = foodLogObj.selectAllFavFoodList();
		setFoodlistDataRecentmeal(foodListRecentMeal, false);
		favFoodAdapter();

		favFoodList();
	}

	@OnClick(R.id.favMeal)
	public void clickfavMeal() {
		if (foodListRecentMeal.size() > 0)
			foodListRecentMeal.clear();
		// for fav food work here avinash
		favMeal.setBackgroundColor(getResources().getColor(
				R.color.home_green_select_dark));
		favFood.setBackgroundColor(getResources().getColor(
				R.color.home_green_deselect_dark));
		intake_linearlayout_fav.setBackgroundColor(getResources().getColor(
				R.color.home_green_deselect_dark));

		recentListViewParentLinear.setVisibility(View.VISIBLE);
		// foodListRecent = foodLogObj.selectAllFavFoodList();
		// favFoodAdapter();
		foodSpinnerSelectFav = -1;
		favFoodClick = false;
		ArrayList<String> dateList = foodLogObj.selectRecentMealDate();

		SimpleDateFormat preFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat postFormat = new SimpleDateFormat("yyyy-MM-dd");

		String date = "";
		String meal = "";
		String fav = "1";
		FitmiFoodLogDAO recentmeal = null;

		for (int outer = 1; outer <= 4; outer++) {

			for (int i = 0; i < dateList.size(); i++) {

				try {
					Date newDate = preFormat.parse(dateList.get(i));
					date = postFormat.format(newDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				ArrayList<String> mealid = new ArrayList<String>();
				foodListRecent = foodLogObj.selectAllFavMealList(
						String.valueOf(outer), date);

				recentmeal = new FitmiFoodLogDAO();
				// recentmeal.setFavourite("1");
				recentmeal.setMealFavourite("1");

				for (int k = 0; k < foodListRecent.size(); k++) {

					if (meal.equalsIgnoreCase("")) {

						if (k == 0)
							meal = foodListRecent.get(k).getFoodName();
						else
							meal = meal + ","
									+ foodListRecent.get(k).getFoodName();

					} else {

						meal = meal + "," + foodListRecent.get(k).getFoodName();
					}

					mealid.add(foodListRecent.get(k).getFoodLogId());

					if (foodListRecent.get(k).getMealFavourite()
							.equalsIgnoreCase("0"))
						// recentmeal.setFavourite("0");
						recentmeal.setMealFavourite("0");
				}

				if (foodListRecent.size() > 0) {

					recentmeal.setMealidList(mealid);
					recentmeal.setFoodName(meal);
					recentmeal.setMealType(foodListRecent.get(0).getMealType());

					/*
					 * if(i==0||i==2||i==4||i==6) {
					 * foodListRecentMeal.add(recentmeal); }else{
					 * 
					 * }
					 */
					foodListRecentMeal.add(recentmeal);

					meal = "";
				}

			}

		}
		/*
		 * if(foodListRecent.size()==2) {
		 * 
		 * }else if(foodListRecent.size()==4) {
		 * 
		 * }else if(foodListRecent.size()==6) {
		 * 
		 * }else if(foodListRecent.size()==8) {
		 * 
		 * }
		 */

		/*
		 * if(foodListRecentMeal.size()>0) { for(int
		 * i=0;i<foodListRecentMeal.size();i++){
		 * 
		 * FitmiFoodLogDAO dao=new FitmiFoodLogDAO(); FitmiFoodLogDAO dao2=new
		 * FitmiFoodLogDAO(); dao=foodListRecentMeal.get(i); if(i) {
		 * dao2=foodListRecentMeal.get(i-1);
		 * 
		 * } } //work avinash
		 * 
		 * ArrayList<FitmiFoodLogDAO> noRepeat = new
		 * ArrayList<FitmiFoodLogDAO>(); // List<Event> allEvents = // fill with
		 * your events. //List<Event> noRepeat = new ArrayList<Event>();
		 * 
		 * for (FitmiFoodLogDAO event : foodListRecentMeal) { boolean isFound =
		 * false; // check if the event name exists in noRepeat for
		 * (FitmiFoodLogDAO e : noRepeat) { if
		 * (e.getFoodName().equals(event.getFoodName()) &&
		 * e.getFoodLogId().equals(event.getFoodLogId())) isFound = true; }
		 * 
		 * if (!isFound) noRepeat.add(event); }
		 * 
		 * foodListRecentMeal.clear(); foodListRecentMeal=noRepeat; }
		 */

		setFoodlistDataRecentmeal(foodListRecentMeal, true);

		favMealAdapter();

	}

	@OnClick(R.id.intake_linearlayout_fav)
	public void IntakeLinearlayoutClickFav() {
		spinnerFav.performClick();
		favMeal.setBackgroundColor(getResources().getColor(
				R.color.home_green_deselect_dark));
		favFood.setBackgroundColor(getResources().getColor(
				R.color.home_green_deselect_dark));
		intake_linearlayout_fav.setBackgroundColor(getResources().getColor(
				R.color.home_green_select_dark));
		favDropClick = true;
		favFoodClick = false;
		foodSpinnerSelectRecent = -1;
	}

	@OnClick(R.id.intake_linearlayout_recent)
	public void IntakeLinearlayoutClickRecent() {
		spinnerRecent.performClick();
		recentMeal.setBackgroundColor(getResources().getColor(
				R.color.home_green_deselect_dark));
		recentFood.setBackgroundColor(getResources().getColor(
				R.color.home_green_deselect_dark));
		intake_linearlayout_recent.setBackgroundColor(getResources().getColor(
				R.color.home_green_select_dark));
		recentDropClick = true;
		recentFoodClick = false;
		// recentFoodClick = true;
		foodSpinnerSelectRecent = -1;
	}

	@OnClick(R.id.recentFood)
	public void clickRecentFood() {
		recentFood.setBackgroundColor(getResources().getColor(
				R.color.home_green_select_dark));
		recentMeal.setBackgroundColor(getResources().getColor(
				R.color.home_green_deselect_dark));
		intake_linearlayout_recent.setBackgroundColor(getResources().getColor(
				R.color.home_green_deselect_dark));

		recentListViewParentLinear.setVisibility(View.VISIBLE);
		foodSpinnerSelectRecent = -1;
		recentFoodClick = true;

		foodListRecent = foodLogObj.selectRecentFoodList(databaseObject);
		recentFoodAdapter();
	}

	@OnClick(R.id.recentMeal)
	public void clickRecentMeal() {
		if (foodListRecentMeal.size() > 0)
			foodListRecentMeal.clear();

		recentMeal.setBackgroundColor(getResources().getColor(
				R.color.home_green_select_dark));
		recentFood.setBackgroundColor(getResources().getColor(
				R.color.home_green_deselect_dark));
		intake_linearlayout_recent.setBackgroundColor(getResources().getColor(
				R.color.home_green_deselect_dark));

		foodSpinnerSelectRecent = -1;
		recentFoodClick = false;

		recentListViewParentLinear.setVisibility(View.VISIBLE);

		ArrayList<String> dateList = foodLogObj.selectRecentMealDate();

		SimpleDateFormat preFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat postFormat = new SimpleDateFormat("yyyy-MM-dd");

		String date = "";
		String meal = "";
		FitmiFoodLogDAO recentmeal = null;

		for (int outer = 1; outer <= 4; outer++) {

			for (int i = 0; i < dateList.size(); i++) {

				try {
					Date newDate = preFormat.parse(dateList.get(i));
					date = postFormat.format(newDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				ArrayList<String> mealid = new ArrayList<String>();
				foodListRecent = foodLogObj.selectRecentMealList(
						String.valueOf(outer), date);

				recentmeal = new FitmiFoodLogDAO();
				recentmeal.setMealFavourite("1");

				for (int k = 0; k < foodListRecent.size(); k++) {

					if (meal.equalsIgnoreCase("")) {

						if (k == 0)
							meal = foodListRecent.get(k).getFoodName();
						else
							meal = meal + ","
									+ foodListRecent.get(k).getFoodName();

					} else {

						meal = meal + "," + foodListRecent.get(k).getFoodName();
					}

					mealid.add(foodListRecent.get(k).getFoodLogId());

					if (foodListRecent.get(k).getMealFavourite()
							.equalsIgnoreCase("0"))
						recentmeal.setMealFavourite("0");

					foodListRecentMealAdd.add(foodListRecent.get(k));
				}

				if (foodListRecent.size() > 0) {

					recentmeal.setMealidList(mealid);
					recentmeal.setFoodName(meal);
					recentmeal.setMealType(foodListRecent.get(0).getMealType());

					if (i == 0 || i == 2 || i == 4 || i == 6) {
						foodListRecentMeal.add(recentmeal);
					} else {

					}
					// foodListRecentMeal.add(recentmeal);

					meal = "";
				}
			}
		}

		setFoodlistDataRecentmeal(foodListRecentMeal, true);

		recentMealAdapter();
	}

	/**
	 * Barcode scann thread
	 */

	class RetrieveFeedTask extends AsyncTask<String, Void, Void> {

		HttpResponse httpResponse = null;
		String response = "";

		protected void onPostExecute(Void feed) {
			// TODO: check this.exception
			// TODO: do something with the feed

			ArrayList<FitmiFoodDAO> resultListScanner = new ArrayList<FitmiFoodDAO>();
			System.out.println("Response :" + response);

			try {
				// Create a JSON object hierarchy from the results
				JSONObject jsonObj = new JSONObject(response);

				String itemId = jsonObj.getString("item_id");
				String itemName = jsonObj.getString("item_name");
				String brandName = jsonObj.getString("brand_name");
				String nfCalories = jsonObj.getString("nf_calories");
				String nfServingSizeQty = jsonObj
						.getString("nf_serving_size_qty");
				String itemDescription = jsonObj.getString("item_description");
				String nfServingSizeUnit = jsonObj
						.getString("nf_serving_size_unit");
				String nfServingWeightGrams = jsonObj
						.getString("nf_serving_weight_grams");
				String nfIngredientStatement = jsonObj
						.getString("nf_ingredient_statement");

				Log.e("itemId", itemId);
				Log.e("itemName", itemName);
				Log.e("brandName", brandName);
				Log.e("nfCalories", nfCalories);
				Log.e("nfServingSizeQty", nfServingSizeQty);
				Log.e("itemDescription", itemDescription);
				Log.e("nfServingSizeUnit", nfServingSizeUnit);
				Log.e("nfServingWeightGrams", nfServingWeightGrams);
				Log.e("nfIngredientStatement", nfIngredientStatement);

				itemName = itemName.replaceAll("[|?*<\":>+\\[\\]/']", "_");
				brandName = brandName.replaceAll("[|?*<\":>+\\[\\]/']", "_");
				itemDescription = itemDescription.replaceAll(
						"[|?*<\":>+\\[\\]/']", "_");

				FitmiFoodDAO itemObj = new FitmiFoodDAO();
				itemObj.setItemId(itemId);
				itemObj.setItemName(itemName);
				itemObj.setBrandName(brandName);
				itemObj.setNfCalories(nfCalories);
				itemObj.setNfServingSizeQty(nfServingSizeQty);
				itemObj.setItemDescription(itemDescription);
				itemObj.setNfServingSizeUnit(nfServingSizeUnit);
				itemObj.setNfServingWeightGrams(nfServingWeightGrams);
				itemObj.setNfIngredientStatement(nfIngredientStatement);
				itemObj.setCustomButton(false);
				resultListScanner.add(itemObj);

				FitmiFoodDAO itemObjj = new FitmiFoodDAO();
				itemObjj.setCustomButton(true);

				resultListScanner.add(itemObj);

				// adding to database

				FoodLoginModule.insertFitmifoodTable(resultListScanner.get(0),
						databaseObject);

				FitmiFoodLogDAO fitmiFoodLogData = new FitmiFoodLogDAO();
				FitmiFoodDAO obj = resultListScanner.get(0);
				// avinash added calorie
				Log.e("caloryCalculate Scaaning time value during adding object  ",
						String.valueOf(obj.getNfCalories()));
				fitmiFoodLogData.setCalory(obj.getNfCalories());
				// fitmiFoodLogData.setServingWeightGrams(obj.getNfServingWeightGrams());
				// fitmiFoodLogData.setCalory("0");
				fitmiFoodLogData.setDescription(obj.getItemDescription());
				fitmiFoodLogData.setFoodName(obj.getItemName());
				fitmiFoodLogData.setBrandName(obj.getBrandName());
				fitmiFoodLogData.setReferenceFoodId(obj.getItemId());
				fitmiFoodLogData.setUserId(com.fitmi.utils.Constants.USER_ID);
				fitmiFoodLogData
						.setUserProfileId(com.fitmi.utils.Constants.PROFILE_ID);
				fitmiFoodLogData.setLogTime(com.fitmi.utils.Constants.postDate);
				fitmiFoodLogData
						.setDateAdded(com.fitmi.utils.Constants.postDate);
				fitmiFoodLogData.setMealId(String.valueOf(mealId));
				// avinash added getNfServingWeightGrams
				String ss = obj.getNfServingWeightGrams();
				fitmiFoodLogData.setMealWeight(obj.getNfServingWeightGrams());
				// fitmiFoodLogData.setMealWeight("0");
				fitmiFoodLogData.setFavourite("0");
				fitmiFoodLogData.setMealFavourite("0");
				// fitmiFoodLogData.setDateAdded(getDate.getDateFormat());
				mealIdSpinner = mealId;

				if (!log) {
					FoodLoginModule.insertFitmifoodLogTable(fitmiFoodLogData,
							databaseObject);
				} else {

					if (com.fitmi.utils.Constants.foodLogData.size() > 0)
						com.fitmi.utils.Constants.foodLogData.clear();
					com.fitmi.utils.Constants.foodLogData.add(fitmiFoodLogData);
				}

				if (mealListData != null && mealListData.size() > 0)
					mealListData.clear();
			//	items.clear();
				foodListData.add(fitmiFoodLogData);
				// foodListDataAlies.add(fitmiFoodLogData);
				// sectionAdapter.notifyDataSetChanged();

				foodListDataAlies.clear();

				foodListDataAlies = foodLogObj.selectFoodList(
						String.valueOf(mealIdSpinner), databaseObject);
				searchListAdapter();
				newDataAdd = true;
				newDataCount++;

				HomeFragment tosetCalory = new HomeFragment();
				NotificationTotalCaloryChange callBack = (NotificationTotalCaloryChange) tosetCalory;

				callBack.setTotalCalory(caloryCalculate);

				databaseObject.closeDataBase();

			} catch (JSONException e) {
				Log.e("LOG TAG", "Cannot process JSON results", e);
			}

		}

		@SuppressWarnings("deprecation")
		@Override
		protected Void doInBackground(String... scannResult) {
			// TODO Auto-generated method stub
			Log.e(" Array for scanner ", scannResult[0]);
			try {

				String urli = "https://api.nutritionix.com/v1_1/item?upc="
						+ scannResult[0]
						+ "&appId=b65138b3&appKey=220fa746203ea5217abff5970c9f8d43";
				Log.e(" url for scanner", urli);
				StringBuilder sb = new StringBuilder(
						"https://api.nutritionix.com/v1_1/item?upc="
								+ scannResult[0]
								+ "&appId=b65138b3&appKey=220fa746203ea5217abff5970c9f8d43");
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet httpget = new HttpGet(sb.toString());
				httpResponse = httpClient.execute(httpget);
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(httpResponse.getEntity()
								.getContent(), "UTF-8"));
				StringBuilder builder = new StringBuilder();
				for (String line = null; (line = reader.readLine()) != null;) {
					builder.append(line).append("\n");
				}

				response = builder.toString();

			} catch (Exception e) {

			}

			return null;
		}
	}

	/*
	 * Fav tag class
	 */
	class TagSet {
		int imgId = 0;
		ArrayList<FitmiFoodLogDAO> mealidList;
	}

	@Override
	public void buttonPressed() {
		// TODO Auto-generated method stub

		/*
		 * if(foodAdapterSearch !=null){ foodListDataAlies =
		 * foodLogObj.selectFoodList
		 * (String.valueOf(mealIdSpinner),databaseObject);
		 * setMelFav(foodListDataAlies);
		 * 
		 * } if(foodAdapter !=null){ mealListData =
		 * foodLogObj.selectFoodList(String
		 * .valueOf(mealIdSpinner),databaseObject); setMelFav(mealListData); }
		 */
	}

	public void setMelFav(ArrayList<FitmiFoodLogDAO> mealList) {
		TagSet tag = new TagSet();
		if (mealList.size() > 0) {
			for (int i = 0; i < mealList.size(); i++) {
				if (mealList.get(i).getMealFavourite().equalsIgnoreCase("0")) {
					tag.imgId = R.drawable.green_star;
					break;
				} else {
					tag.imgId = R.drawable.favorites_star;
				}
			}
		} else {
			tag.imgId = R.drawable.green_star;
		}
		tag.mealidList = mealList;
		favouriteMeal.setTag(tag);

		switch (tag.imgId) {
		case R.drawable.green_star:
			// favouriteMeal.setImageResource(R.drawable.green_star);
			totalFoodFooterAdapter = new TotalFoodFooterAdapter(getActivity(),
					currActivewt, currActiveCalwt,
					String.valueOf((int) caloryCalculate), "0");
			listTotalFrame_FoodLogging.setAdapter(totalFoodFooterAdapter);
			isFavMealVar = "0";
			totalFoodFooterAdapter.notifyDataSetChanged();
			break;

		case R.drawable.favorites_star:
			// favouriteMeal.setImageResource(R.drawable.favorites_star);
			totalFoodFooterAdapter = new TotalFoodFooterAdapter(getActivity(),
					currActivewt, currActiveCalwt,
					String.valueOf((int) caloryCalculate), "1");
			listTotalFrame_FoodLogging.setAdapter(totalFoodFooterAdapter);
			isFavMealVar = "1";
			break;
		}

	}

	/**
	 * Edit dialog
	 */

	public void editDialog(final int position) {

/*		final Dialog dialog = new Dialog(getActivity() ,R.style.Theme_Dialog );
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_edit_calory);
		final EditText newCalory = (EditText) dialog
				.findViewById(R.id.newCalory);

		TextView dialogTitle = (TextView) dialog.findViewById(R.id.dialogTitle);
		dialogTitle.setText("Edit Weight");

		newCalory.setText("");*/
		
		
		
		

		 final AlertDialog alertDialog = new AlertDialog.Builder(getActivity(),AlertDialog.THEME_HOLO_DARK).create();
		 
			// alertDialog.setTitle("Fitmi");
		//	 alertDialog.setIcon(R.drawable.app_icon);
			if (com.fitmi.utils.Constants.gunitfw == 0) {
				alertDialog.setMessage("Edit weight (g)");
			} else {
				alertDialog.setMessage("Edit weight (oz)");
			}
		// alertDialog.setMessage("Edit weight");
		 
		 final EditText newCalory = new EditText(getActivity());
		 
		 newCalory.setInputType(InputType.TYPE_CLASS_NUMBER);
		newCalory.setBackgroundColor(getResources().getColor(R.color.black_dialbg));
		 newCalory.setHint("Enter weight");
		 newCalory.setTextColor(getResources().getColor(R.color.white));
		
		// newCalory.setTextAppearance(getActivity(), R.style.UserInfo_InputBoxStyleBlack);
	         
	
	         alertDialog.setView(newCalory);
         alertDialog.setButton("Save", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	            
	            	

						try {
							if (newCalory.getText().toString()
									.equalsIgnoreCase("")) {
								Toast.makeText(getActivity(), "Enter Weight",
										Toast.LENGTH_LONG).show();
								return;
							}

							String calory = newCalory.getText().toString();

							if (foodListDataAlies.size() > 0) {

								caloryCalculate = 0;
								float cals_data = 0, serving_weightdata, pergram_cal, total_cals = 0;
								String id_s = foodListDataAlies.get(position)
										.getReferenceFoodId();
								_fitmifoodList = foodLogObjForfood
										.selectFitMiFoodList(id_s,
												databaseObjectForFood);
								if (log == true) {
									if ((_fitmifoodList.get(0).getCalory()
											.length() > 0 && _fitmifoodList
											.get(0).getMealWeight().length() > 0)
											&& (!_fitmifoodList.get(0)
													.getCalory().equals("null") && !_fitmifoodList
													.get(0).getMealWeight()
													.equals("null"))) {
										cals_data = Float
												.parseFloat(_fitmifoodList.get(
														0).getCalory());
										serving_weightdata = Float
												.parseFloat(_fitmifoodList.get(
														0).getMealWeight());

										pergram_cal = cals_data
												/ serving_weightdata;

										total_cals = Float.parseFloat(calory)
												* pergram_cal;
									}
									caloryCalculate = total_cals;
									Log.e("foodListDataAlies.get(position).getFoodLogId() ",
											foodListDataAlies.get(position)
													.getFoodLogId());

									// fitmiFoodLogDataTemp.setCalory(calory);
									// fitmiFoodLogDataTemp.setMealWeight(String.valueOf(total_cals));

									FitmiFoodLogDAO fitmiFoodLogDataLog = new FitmiFoodLogDAO();
									FitmiFoodLogDAO obj = foodListDataAlies
											.get(position);

									fitmiFoodLogDataLog.setCalory(String
											.valueOf(total_cals));
									// fitmiFoodLogData.setServingWeightGrams(obj.getNfServingWeightGrams());
									// fitmiFoodLogData.setCalory("0");
									fitmiFoodLogDataLog.setDescription(obj
											.getDescription());
									fitmiFoodLogDataLog.setFoodName(obj
											.getFoodName());
									fitmiFoodLogDataLog.setBrandName(obj
											.getBrandName());
									fitmiFoodLogDataLog.setReferenceFoodId(obj
											.getReferenceFoodId());
									fitmiFoodLogDataLog
											.setUserId(com.fitmi.utils.Constants.USER_ID);
									fitmiFoodLogDataLog
											.setUserProfileId(com.fitmi.utils.Constants.PROFILE_ID);
									fitmiFoodLogDataLog
											.setLogTime(com.fitmi.utils.Constants.postDate);
									fitmiFoodLogDataLog
											.setDateAdded(com.fitmi.utils.Constants.postDate);
									// fitmiFoodLogData.setDateAdded(com.fitmi.utils.Constants.sDate);
									fitmiFoodLogDataLog.setMealId(String
											.valueOf(mealId));
									// avinash added getNfServingWeightGrams
									String ss = obj.getMealWeight();
									// at first add getNfServingWeightGrams 0
									// fitmiFoodLogData.setMealWeight(obj.getNfServingWeightGrams());

									fitmiFoodLogDataLog.setMealWeight(calory);
									fitmiFoodLogDataLog.setFavourite("0");

									try {
										if (isFavMealVar.equalsIgnoreCase("0")) {
											fitmiFoodLogDataLog
													.setMealFavourite("0");
										} else {
											fitmiFoodLogDataLog
													.setMealFavourite("1");
										}
									} catch (Exception a) {
										fitmiFoodLogDataLog
												.setMealFavourite("0");
										a.printStackTrace();

									}

									foodListDataAlies.set(position,
											fitmiFoodLogDataLog);
									if (com.fitmi.utils.Constants.foodLogData
											.size() > 0)
										com.fitmi.utils.Constants.foodLogData
												.clear();
									com.fitmi.utils.Constants.foodLogData = new ArrayList<FitmiFoodLogDAO>(
											foodListDataAlies);

								} else {
									if ((_fitmifoodList.get(0).getCalory()
											.length() > 0 && _fitmifoodList
											.get(0).getMealWeight().length() > 0)
											&& (!_fitmifoodList.get(0)
													.getCalory().equals("null") && !_fitmifoodList
													.get(0).getMealWeight()
													.equals("null"))) {
										cals_data = Float
												.parseFloat(_fitmifoodList.get(
														0).getCalory());
										serving_weightdata = Float
												.parseFloat(_fitmifoodList.get(
														0).getMealWeight());

										pergram_cal = cals_data
												/ serving_weightdata;
										total_cals = Float.parseFloat(calory)
												* pergram_cal;
									}
									// caloryCalculate=total_cals;
									Log.e("foodListDataAlies.get(position).getFoodLogId() ",
											foodListDataAlies.get(position)
													.getFoodLogId());
									FoodLoginModule.editCaloryItew(
											foodListDataAlies.get(position)
													.getFoodLogId(),
											databaseObject, String
													.valueOf(total_cals),
											calory);
									// foodListDataAlies.remove(position);

									foodListDataAlies = foodLogObj
											.selectFoodList(String
													.valueOf(mealIdSpinner),
													databaseObject);
								}
								foodAdapterSearch.notifyDataSetChanged();

								for (int i = 0; i < foodListDataAlies.size(); i++) {
									if (!foodListDataAlies.get(i).getCalory()
											.equalsIgnoreCase(""))

										caloryCalculate += Float
												.parseFloat(foodListDataAlies
														.get(i).getCalory());
								}

								totalCalory.setText((int) caloryCalculate + ""
										+ " cal");
								
								totalFoodFooterAdapter.notifyDataSetChanged();
								setFoodSpinner();
								
								_fitmifoodList.clear();
								
								foodAdapter.notifyDataSetChanged();
							} else if (mealListData != null
									&& mealListData.size() > 0) {

								caloryCalculate = 0;

								float cals_data, serving_weightdata, pergram_cal, total_cals = 0;

								String id_s = mealListData.get(position)
										.getReferenceFoodId();

								_fitmifoodList = foodLogObjForfood
										.selectFitMiFoodList(id_s,
												databaseObjectForFood);
								if ((_fitmifoodList.get(0).getCalory().length() > 0 && _fitmifoodList
										.get(0).getMealWeight().length() > 0)
										&& (!_fitmifoodList.get(0).getCalory()
												.equals("null") && !_fitmifoodList
												.get(0).getMealWeight()
												.equals("null"))) {
									cals_data = Float.parseFloat(_fitmifoodList
											.get(0).getCalory());
									serving_weightdata = Float
											.parseFloat(_fitmifoodList.get(0)
													.getMealWeight());

									/*
									 * cals_data=Float.parseFloat(mealListData.get
									 * (position).getCalory());
									 * serving_weightdata
									 * =Float.parseFloat(mealListData
									 * .get(position).getMealWeight());
									 */

									pergram_cal = cals_data
											/ serving_weightdata;
									total_cals = Float.parseFloat(calory)
											* pergram_cal;
								}
								// caloryCalculate=total_cals;
								Log.e("caloryCalculate 2  ",
										String.valueOf(caloryCalculate));

								FoodLoginModule.editCaloryItew(mealListData
										.get(position).getFoodLogId(),
										databaseObject, String
												.valueOf(total_cals), calory);
								// mealListData.remove(position);
								mealListData = foodLogObj.selectFoodList(
										String.valueOf(mealIdSpinner),
										databaseObject);
								foodAdapter.notifyDataSetChanged();

								for (int i = 0; i < mealListData.size(); i++) {
									if (!mealListData.get(i).getCalory()
											.equalsIgnoreCase(""))
										caloryCalculate += Float
												.parseFloat(mealListData.get(i)
														.getCalory());
								}

								
								setFoodSpinner();
								_fitmifoodList.clear();
								totalFoodFooterAdapter.notifyDataSetChanged();
								foodAdapter.notifyDataSetChanged();
							}

							// FoodLoginModule.editCalory(tagLocalObject,
							// databaseObject, calory);

							try {
								if (isFavMealVar.equalsIgnoreCase("0")) {
									totalFoodFooterAdapter = new TotalFoodFooterAdapter(
											getActivity(),
											currActivewt,
											currActiveCalwt,
											String.valueOf((int) caloryCalculate),
											"0");
								} else {
									totalFoodFooterAdapter = new TotalFoodFooterAdapter(
											getActivity(),
											currActivewt,
											currActiveCalwt,
											String.valueOf((int) caloryCalculate),
											"1");
								}
							} catch (Exception a) {
								totalFoodFooterAdapter = new TotalFoodFooterAdapter(
										getActivity(), currActivewt,
										currActiveCalwt,
										String.valueOf((int) caloryCalculate),
										"0");
								a.printStackTrace();

							}

							listTotalFrame_FoodLogging
									.setAdapter(totalFoodFooterAdapter);
							totalFoodFooterAdapter.notifyDataSetChanged();
							
							// totalFoodFooterAdapter.notifyDataSetChanged();

							View view = getActivity().getCurrentFocus();
							if (view != null) {  
							    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
							    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
							}
							dialog.dismiss();

						} catch (Exception exception) {
							if (alertDialog.isShowing())
								dialog.dismiss();
							View view = getActivity().getCurrentFocus();
							if (view != null) {  
							    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
							    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
							}
							exception.printStackTrace();
						}

					}
				});
	     alertDialog.setButton2("Cancel",new DialogInterface.OnClickListener() {
	         @Override
	         public void onClick(DialogInterface dialog, int which) {
	            
	        	 View view = getActivity().getCurrentFocus();
	     		if (view != null) {  
	     		    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	     		    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	     		}
	        	 alertDialog.dismiss();
					}
				});

	     alertDialog.show();
		DisplayMetrics metrics = getActivity().getResources()
				.getDisplayMetrics();
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
		alertDialog.getWindow()
				.setLayout((6 * width) / 7, LayoutParams.WRAP_CONTENT);
		nextbtnVal = 0;
		newCalory.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

	}

	public ArrayList<FitmiFoodLogDAO> recentFavMealList() {
		// if(foodListRecentMeal.size()>0)
		// foodListRecentMeal.clear();

		ArrayList<FitmiFoodLogDAO> food = new ArrayList<FitmiFoodLogDAO>();

		if (food.size() > 0)
			food.clear();

		ArrayList<String> dateList = foodLogObj.selectRecentMealDate();

		SimpleDateFormat preFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat postFormat = new SimpleDateFormat("yyyy-MM-dd");

		String date = "";
		String meal = "";
		FitmiFoodLogDAO recentmeal = null;

		for (int outer = 1; outer <= 4; outer++) {

			for (int i = 0; i < dateList.size(); i++) {

				try {
					Date newDate = preFormat.parse(dateList.get(i));
					date = postFormat.format(newDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				ArrayList<String> mealid = new ArrayList<String>();
				foodListRecent = foodLogObj.selectAllFavMealList(
						String.valueOf(outer), date);

				recentmeal = new FitmiFoodLogDAO();
				// recentmeal.setFavourite("1");
				recentmeal.setMealFavourite("1");

				for (int k = 0; k < foodListRecent.size(); k++) {

					if (meal.equalsIgnoreCase("")) {

						if (k == 0)
							meal = foodListRecent.get(k).getFoodName();
						else
							meal = meal + ","
									+ foodListRecent.get(k).getFoodName();

					} else {

						meal = meal + "," + foodListRecent.get(k).getFoodName();
					}

					mealid.add(foodListRecent.get(k).getFoodLogId());

					if (foodListRecent.get(k).getMealFavourite()
							.equalsIgnoreCase("0"))
						// recentmeal.setFavourite("0");
						recentmeal.setMealFavourite("0");
				}

				if (foodListRecent.size() > 0) {

					recentmeal.setMealidList(mealid);
					recentmeal.setFoodName(meal);
					recentmeal.setMealType(foodListRecent.get(0).getMealType());
					food.add(recentmeal);

					meal = "";
				}
			}

		}
		return food;
	}

	public ArrayList<FitmiFoodLogDAO> recentMealList() {
		if (foodListRecentMeal.size() > 0)
			foodListRecentMeal.clear();

		ArrayList<String> dateList = foodLogObj.selectRecentMealDate();

		SimpleDateFormat preFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat postFormat = new SimpleDateFormat("yyyy-MM-dd");

		String date = "";
		String meal = "";
		FitmiFoodLogDAO recentmeal = null;

		for (int outer = 1; outer <= 4; outer++) {

			for (int i = 0; i < dateList.size(); i++) {

				try {
					Date newDate = preFormat.parse(dateList.get(i));
					date = postFormat.format(newDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				ArrayList<String> mealid = new ArrayList<String>();
				foodListRecent = foodLogObj.selectRecentMealList(
						String.valueOf(outer), date);

				recentmeal = new FitmiFoodLogDAO();
				// recentmeal.setFavourite("1");
				recentmeal.setMealFavourite("1");

				for (int k = 0; k < foodListRecent.size(); k++) {

					if (meal.equalsIgnoreCase("")) {

						if (k == 0)
							meal = foodListRecent.get(k).getFoodName();
						else
							meal = meal + ","
									+ foodListRecent.get(k).getFoodName();

					} else {

						meal = meal + "," + foodListRecent.get(k).getFoodName();
					}

					mealid.add(foodListRecent.get(k).getFoodLogId());

					if (foodListRecent.get(k).getMealFavourite()
							.equalsIgnoreCase("0"))
						recentmeal.setMealFavourite("0");
				}

				if (foodListRecent.size() > 0) {

					recentmeal.setMealidList(mealid);
					recentmeal.setFoodName(meal);
					recentmeal.setMealType(foodListRecent.get(0).getMealType());

					meal = "";
				}
			}

		}
		return foodListRecentMeal;
	}

	public void setTotal(ArrayList<FitmiFoodLogDAO> foodListData) {
		caloryCalculate = 0;
		for (int i = 0; i < foodListData.size(); i++) {
			if (!foodListData.get(i).getCalory().equalsIgnoreCase(""))
				caloryCalculate += Float.parseFloat(foodListData.get(i)
						.getCalory());
		}

		calorieTotalTop.setText((int) caloryCalculate + "" + " cal");
		food_calorie_text.setText((int) caloryCalculate + "");
		totalCalory.setText((int) caloryCalculate + "" + " cal");
		/*
		 * totalFoodFooterAdapter=new TotalFoodFooterAdapter(getActivity(), "",
		 * "", String.valueOf((int)caloryCalculate));
		 * listTotalFrame_FoodLogging.setAdapter(totalFoodFooterAdapter);
		 */
		setMelFav(foodListData);
	}

	public void exportDatabse(String databaseName) {
		try {
			File sd = Environment.getExternalStorageDirectory();
			File data = Environment.getDataDirectory();

			Date today = new Date();

			if (sd.canWrite()) {
				String currentDBPath = "//data//"
						+ getActivity().getPackageName() + "//databases//"
						+ databaseName + "";
				String backupDBPath = "FitMi" + today.toString().trim() + ".db";
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

	// new style listview
	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}

	public void notifyFoodadapter() {
		foodAdapter.notifyDataSetChanged();
	}

	private BroadcastReceiver activityReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			System.out.println("activityReceiver started");
			String weightVal;
			String _status = intent.getStringExtra("success");
			if (_status.equalsIgnoreCase("0")) {
				Toast.makeText(getActivity(), "Device not sync !",
						Toast.LENGTH_SHORT).show();
			} else {

				weightVal = intent.getStringExtra("kweight");

				System.out.println("activityReceiver kweight  " + weightVal);
				setText(weightVal);
			}
			/*
			 * Toast.makeText(getActivity(), "received message in activity..!",
			 * Toast.LENGTH_SHORT) .show();
			 */
		}
	};

	private void setText(final String wString) {
		// getActivity().runOnUiThread(new Runnable() {
		// @Override
		// public void run() {

		String sourceString = "<b> Congratulations!</b> You have successfully synced your device.";

		System.out.println("setText setText");

		if (sFOODLOGGING_POS >= 0) {
			
			if ( foodListDataAlies.size() > 0) {
				String _acumulatedweight;
				int _accweight=0;
				int _intwString=0;
				_intwString=Integer.parseInt(wString);
				for(int i=0;i<foodListDataAlies.size();i++)
				{
					if(sFOODLOGGING_POS!=i)
					{
						_accweight=_accweight+Integer.parseInt(foodListDataAlies.get(i).getMealWeight());
						Log.e("Total _accweight ", String.valueOf(_accweight));
					}
					
				}
				
				if(_intwString>_accweight)
				{
					_accweight=_intwString-_accweight;
				}else{
					_accweight=0;
				}
				
				Log.e("Total _accweight after calculation ", String.valueOf(_accweight));
				try{
				updateValueKscale(sFOODLOGGING_POS, String.valueOf(_accweight));
			 }catch (Exception a)
			 {
				 Log.e("updateValueKscale foodListDataAlies ", "updateValueKscale foodListDataAlies");
			 }
			}else if (mealListData != null && mealListData.size() > 0) {
				String _acumulatedweight;
				int _accweight=0;
				int _intwString=0;
				_intwString=Integer.parseInt(wString);
				for(int i=0;i<mealListData.size();i++)
				{
					if(sFOODLOGGING_POS!=i)
					{
						_accweight=_accweight+Integer.parseInt(mealListData.get(i).getMealWeight());
						Log.e("Total _accweight ", String.valueOf(_accweight));
					}
					
				}
				
				if(_intwString>_accweight)
				{
					_accweight=_intwString-_accweight;
				}else{
					_accweight=0;
				}
				Log.e("Total _accweight after calculation ", String.valueOf(_accweight));
				 try{
				updateValueKscale(sFOODLOGGING_POS, String.valueOf(_accweight));
				 }catch (Exception a)
				 {
					 Log.e("updateValueKscale mealListData ", "updateValueKscale mealListData");
				 }
				
				
			}
			
			
			
		} else {

			currActiveCalwt = "0";
			totalFoodFooterAdapter.notifyDataSetChanged();
		}
		currActivewt = wString;
		if (isFavMealVar.equalsIgnoreCase("0")) {
			totalFoodFooterAdapter = new TotalFoodFooterAdapter(getActivity(),
					currActivewt, currActiveCalwt,
					String.valueOf((int) caloryCalculate), "0");
		} else {
			totalFoodFooterAdapter = new TotalFoodFooterAdapter(getActivity(),
					currActivewt, currActiveCalwt,
					String.valueOf((int) caloryCalculate), "1");
		}
		listTotalFrame_FoodLogging.setAdapter(totalFoodFooterAdapter);
		totalFoodFooterAdapter.notifyDataSetChanged();

		/*
		 * Toast.makeText(getActivity(), "setText Working",Toast.LENGTH_LONG
		 * ).show(); ((TabActivity) getActivity()).resetTabs(); ((TabActivity)
		 * getActivity()).homeLinear
		 * .setBackgroundColor(getResources().getColor( R.color.royal_blue));
		 * ((TabActivity) getActivity())._mViewPager.setCurrentItem(0);
		 */
		// }
		// });
	}

	public void updateValueKscale(int position, String weight) {
		try {

			Log.e("updateValueKscale","updateValueKscale running");
			if (foodListDataAlies.size() > 0) {

				caloryCalculate = 0;
				float cals_data = 0, serving_weightdata, pergram_cal, total_cals = 0;
				String id_s = foodListDataAlies.get(position)
						.getReferenceFoodId();
				_fitmifoodList = foodLogObjForfood.selectFitMiFoodList(id_s,
						databaseObjectForFood);
				//if (log == true) {
					if ((_fitmifoodList.get(0).getCalory().length() > 0 && _fitmifoodList.get(0).getMealWeight().length() > 0)
							&& (!_fitmifoodList.get(0).getCalory().equals("null") && !_fitmifoodList.get(0).getMealWeight().equals("null"))) {
						cals_data = Float.parseFloat(_fitmifoodList.get(0)
								.getCalory());
						serving_weightdata = Float.parseFloat(_fitmifoodList
								.get(0).getMealWeight());

						pergram_cal = cals_data / serving_weightdata;

						total_cals = Float.parseFloat(weight) * pergram_cal;
					}
					caloryCalculate = total_cals;
					Log.e("foodListDataAlies.get(position).getFoodLogId() ",
							foodListDataAlies.get(position).getFoodLogId());

					// fitmiFoodLogDataTemp.setCalory(calory);
					// fitmiFoodLogDataTemp.setMealWeight(String.valueOf(total_cals));

					FitmiFoodLogDAO fitmiFoodLogDataLog = new FitmiFoodLogDAO();
					FitmiFoodLogDAO obj = foodListDataAlies.get(position);

					int temp_to = 0;
					temp_to = (int) total_cals;

					currActiveCalwt = (String.valueOf(temp_to));
					fitmiFoodLogDataLog.setCalory(String.valueOf(total_cals));
					// fitmiFoodLogData.setServingWeightGrams(obj.getNfServingWeightGrams());
					// fitmiFoodLogData.setCalory("0");
					fitmiFoodLogDataLog.setDescription(obj.getDescription());
					fitmiFoodLogDataLog.setFoodName(obj.getFoodName());
					fitmiFoodLogDataLog.setBrandName(obj.getBrandName());
					fitmiFoodLogDataLog.setReferenceFoodId(obj
							.getReferenceFoodId());
					fitmiFoodLogDataLog.setFoodLogId(obj
							.getFoodLogId());
					fitmiFoodLogDataLog
							.setUserId(com.fitmi.utils.Constants.USER_ID);
					fitmiFoodLogDataLog
							.setUserProfileId(com.fitmi.utils.Constants.PROFILE_ID);
					fitmiFoodLogDataLog
							.setLogTime(com.fitmi.utils.Constants.postDate);
					fitmiFoodLogDataLog
							.setDateAdded(com.fitmi.utils.Constants.postDate);
					// fitmiFoodLogData.setDateAdded(com.fitmi.utils.Constants.sDate);
					fitmiFoodLogDataLog.setMealId(String.valueOf(mealId));
					// avinash added getNfServingWeightGrams
					String ss = obj.getMealWeight();
				

					fitmiFoodLogDataLog.setMealWeight(weight);
					fitmiFoodLogDataLog.setFavourite("0");
					fitmiFoodLogDataLog.setMealFavourite("0");

					foodListDataAlies.set(position, fitmiFoodLogDataLog);
					if (com.fitmi.utils.Constants.foodLogData.size() > 0)
						com.fitmi.utils.Constants.foodLogData.clear();
					com.fitmi.utils.Constants.foodLogData = new ArrayList<FitmiFoodLogDAO>(
							foodListDataAlies);

				
					temp_to = (int) total_cals;

					currActiveCalwt = (String.valueOf(temp_to));
					Log.e("foodListDataAlies.get(position).getFoodLogId() ",
							foodListDataAlies.get(position).getFoodLogId());
		

				for (int i = 0; i < foodListDataAlies.size(); i++) {
					if (!foodListDataAlies.get(i).getCalory()
							.equalsIgnoreCase(""))

						caloryCalculate += Float.parseFloat(foodListDataAlies
								.get(i).getCalory());
				}

				totalCalory.setText((int) caloryCalculate + "" + " cal");
				totalFoodFooterAdapter.notifyDataSetChanged();
				_fitmifoodList.clear();
				foodAdapter.notifyDataSetChanged();
			} else if (mealListData != null && mealListData.size() > 0) {
				

				caloryCalculate = 0;
				float cals_data = 0, serving_weightdata, pergram_cal, total_cals = 0;
				String id_s = mealListData.get(position)
						.getReferenceFoodId();
				_fitmifoodList = foodLogObjForfood.selectFitMiFoodList(id_s,
						databaseObjectForFood);
				
					if ((_fitmifoodList.get(0).getCalory().length() > 0 && _fitmifoodList
							.get(0).getMealWeight().length() > 0)
							&& (!_fitmifoodList.get(0).getCalory()
									.equals("null") && !_fitmifoodList.get(0)
									.getMealWeight().equals("null"))) {
						cals_data = Float.parseFloat(_fitmifoodList.get(0)
								.getCalory());
						serving_weightdata = Float.parseFloat(_fitmifoodList
								.get(0).getMealWeight());

						pergram_cal = cals_data / serving_weightdata;

						total_cals = Float.parseFloat(weight) * pergram_cal;
					}
					caloryCalculate = total_cals;
					Log.e("mealListData.get(position).getFoodLogId() ",
							mealListData.get(position).getFoodLogId());

					FitmiFoodLogDAO fitmiFoodLogDataLog = new FitmiFoodLogDAO();
					FitmiFoodLogDAO obj = mealListData.get(position);

					int temp_to = 0;
					temp_to = (int) total_cals;

					currActiveCalwt = (String.valueOf(temp_to));
					fitmiFoodLogDataLog.setCalory(String.valueOf(total_cals));
	
					fitmiFoodLogDataLog.setDescription(obj.getDescription());
					fitmiFoodLogDataLog.setFoodName(obj.getFoodName());
					fitmiFoodLogDataLog.setBrandName(obj.getBrandName());
					fitmiFoodLogDataLog.setReferenceFoodId(obj
							.getReferenceFoodId());
					fitmiFoodLogDataLog.setFoodLogId(obj
							.getFoodLogId());
					fitmiFoodLogDataLog
							.setUserId(com.fitmi.utils.Constants.USER_ID);
					fitmiFoodLogDataLog
							.setUserProfileId(com.fitmi.utils.Constants.PROFILE_ID);
					fitmiFoodLogDataLog
							.setLogTime(com.fitmi.utils.Constants.postDate);
					fitmiFoodLogDataLog
							.setDateAdded(com.fitmi.utils.Constants.postDate);
					
					fitmiFoodLogDataLog.setMealId(String.valueOf(mealId));
		
					String ss = obj.getMealWeight();
				
					fitmiFoodLogDataLog.setMealWeight(weight);
					fitmiFoodLogDataLog.setFavourite("0");
					fitmiFoodLogDataLog.setMealFavourite("0");

					mealListData.set(position, fitmiFoodLogDataLog);
					if (com.fitmi.utils.Constants.foodLogData.size() > 0)
						com.fitmi.utils.Constants.foodLogData.clear();
					com.fitmi.utils.Constants.foodLogData = new ArrayList<FitmiFoodLogDAO>(
							mealListData);

		
					temp_to = (int) total_cals;

					currActiveCalwt = (String.valueOf(temp_to));
					Log.e("mealListData.get(position).getFoodLogId() ",
							mealListData.get(position).getFoodLogId());
		
			//	foodAdapterSearch.notifyDataSetChanged();

				for (int i = 0; i < foodListDataAlies.size(); i++) {
					if (!mealListData.get(i).getCalory()
							.equalsIgnoreCase(""))

						caloryCalculate += Float.parseFloat(mealListData
								.get(i).getCalory());
				}

				totalCalory.setText((int) caloryCalculate + "" + " cal");
				totalFoodFooterAdapter.notifyDataSetChanged();
				_fitmifoodList.clear();
				foodAdapter.notifyDataSetChanged();
			
			}

			// FoodLoginModule.editCalory(tagLocalObject, databaseObject,
			// calory);
			Log.e("currActiveCalwt   ", currActiveCalwt);
			// totalFoodFooterAdapter.notifyDataSetChanged();

		} catch (Exception exception) {

			exception.printStackTrace();
		}
		// totalFoodFooterAdapter.notifyDataSetChanged();
		if (isFavMealVar.equalsIgnoreCase("0")) {
			totalFoodFooterAdapter = new TotalFoodFooterAdapter(getActivity(),
					currActivewt, currActiveCalwt,
					String.valueOf((int) caloryCalculate), "0");
		} else {
			totalFoodFooterAdapter = new TotalFoodFooterAdapter(getActivity(),
					currActivewt, currActiveCalwt,
					String.valueOf((int) caloryCalculate), "1");
		}
		
		setFoodSpinner();
	
		listTotalFrame_FoodLogging.setAdapter(totalFoodFooterAdapter);
		totalFoodFooterAdapter.notifyDataSetChanged();

	}// end method

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
		try {
			if (dateSetReceiver != null) {
				getActivity().unregisterReceiver(dateSetReceiver);
				getActivity().unregisterReceiver(activityReceiver);
			}
		} catch (Exception a) {
			a.printStackTrace();
		}

		
		super.onDestroy();
	}

	// The method that displays the popup.
	private void showPopup(final Activity context) {
		pDialog.dismiss();
		int popupWidth = 300;
		int popupHeight = 370;
		popupHeight = foodLoggingListView2.getHeight();
		popupWidth = foodLoggingListView2.getWidth();
		popupHeight = popupHeight + searchEditText.getHeight()
				+ listTotalFrame_FoodLogging.getHeight()
				+ searchEditText.getHeight();

		// Inflate the popup_layout.xml
		LinearLayout viewGroup = (LinearLayout) context
				.findViewById(R.id.popup);
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = layoutInflater.inflate(R.layout.popup_nutritionfacts,
				viewGroup);

		TextView fooditemName, foodservingsize, foodcalorie;
		/*
		 * txt_total_fat,txt_saturated_fat,
		 * txt_cholesterol,txt_sodium,txt_total_carbohydrate
		 * ,txt_dietary_fiber,txt_sugars,txt_protein,txt_potassium,txt_p;
		 */
		fooditemName = (TextView) layout.findViewById(R.id.fooditemName);
		foodservingsize = (TextView) layout.findViewById(R.id.foodservingsize);
		foodcalorie = (TextView) layout.findViewById(R.id.foodcalorie);
		/*
		 * txt_total_fat=(TextView)layout.findViewById(R.id.txt_total_fat);
		 * txt_saturated_fat
		 * =(TextView)layout.findViewById(R.id.txt_saturated_fat);
		 * txt_cholesterol=(TextView)layout.findViewById(R.id.txt_cholesterol);
		 * txt_sodium=(TextView)layout.findViewById(R.id.txt_sodium);
		 * txt_total_carbohydrate
		 * =(TextView)layout.findViewById(R.id.txt_total_carbohydrate);
		 * txt_dietary_fiber
		 * =(TextView)layout.findViewById(R.id.txt_dietary_fiber);
		 * txt_sugars=(TextView)layout.findViewById(R.id.txt_sugars);
		 * txt_protein=(TextView)layout.findViewById(R.id.txt_protein);
		 * txt_potassium=(TextView)layout.findViewById(R.id.txt_potassium);
		 * txt_p=(TextView)layout.findViewById(R.id.txt_p);
		 */

		ListView nutritionlistdataone;
		ListView nutritionlistdatatwo;

		nutritionlistdataone = (ListView) layout
				.findViewById(R.id.nutritionlistdataone);
		nutritionlistdatatwo = (ListView) layout
				.findViewById(R.id.nutritionlistdatatwo);
		// Creating the PopupWindow
		final PopupWindow popup = new PopupWindow(context);
		popup.setContentView(layout);
		popup.setWidth(popupWidth);
		popup.setHeight(popupHeight);
		popup.setFocusable(true);

		// Some offset to align the popup a bit to the right, and a bit down,
		// relative to button's position.
		int OFFSET_X = 30;
		int OFFSET_Y = 70;

		// Clear the default translucent background
		popup.setBackgroundDrawable(new BitmapDrawable());

		// Displaying the popup at the specified location, + offsets.
		popup.showAtLocation(layout, Gravity.CENTER, 0, 70);

		nutritionAdapter = new NutritionAdapter(getActivity(),
				nutritionTypeDAOs);
		nutritionAdapterBoldText = new NutritionAdapterBoldText(getActivity(),
				datamap);

		nutritionlistdataone.setAdapter(nutritionAdapterBoldText);
		nutritionlistdatatwo.setAdapter(nutritionAdapter);
		nutritionAdapter.notifyDataSetChanged();
		nutritionAdapterBoldText.notifyDataSetChanged();

		ListUtils.setDynamicHeight(nutritionlistdataone);
		ListUtils.setDynamicHeight(nutritionlistdatatwo);

		if (!_food_name.equalsIgnoreCase("null")) {
			fooditemName.setText(_food_name);
		} else {
			fooditemName.setText("____");
		}

		if (!_serving_weight_grams.equalsIgnoreCase("null")) {
			foodservingsize.setText("Serving Size:  " + _serving_weight_grams);
		} else {
			foodservingsize.setText("Serving Size:  " + "0");
		}

		if (!_nf_calories.equalsIgnoreCase("null")) {
			foodcalorie.setText(_nf_calories);
		} else {
			foodcalorie.setText("0");
		}

	}

	private void sendRequest(String JSON_URL, StringEntity se) {
		ArrayList<FitmiFoodDAO> resultList = new ArrayList<FitmiFoodDAO>();

		// placesAutoCompleteAdapter.notifyDataSetInvalidated();

		String SetServerString = "";
		HttpURLConnection conn = null;
		try {
			foodAdapter.notifyDataSetChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			int SDK_INT = android.os.Build.VERSION.SDK_INT;
			if (SDK_INT > 8) {
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
						.permitAll().build();
				StrictMode.setThreadPolicy(policy);
				// your codes here

			}
			pDialog.setMessage("Loading...");
			pDialog.show();
			DefaultHttpClient Client = getNewHttpClient();
			// HttpGet httpget = new HttpGet(sb.toString());
			HttpPost httpost = new HttpPost(JSON_URL);
			JSONObject holder = new JSONObject();// getJsonObjectFromMap(params);

			// passes the results to a string builder/entity
			// StringEntity se = new StringEntity(holder.toString());

			// sets the post request as the resulting string
			httpost.setEntity(se);
			// sets a request header so the page receving the request
			// will know what to do with it
			httpost.setHeader("Accept", "application/json");
			httpost.setHeader("Content-type", "application/json");
			httpost.setHeader("x-app-id", "b65138b3");
			httpost.setHeader("x-app-key", "220fa746203ea5217abff5970c9f8d43");

			ResponseHandler responseHandler = new BasicResponseHandler();
			SetServerString = (String) Client.execute(httpost, responseHandler);

		} catch (MalformedURLException e) {
			Log.e("LOG TAG", "Error processing Places API URL", e);

		} catch (IOException e) {
			Log.e("LOG TAG", "Error connecting to Places API", e);

		}

		try {
			showJSON(SetServerString);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void showJSON(String result) throws JSONException {

		JSONObject jsonObj = new JSONObject(result);
		JSONArray predsJsonArray;
		try {
			predsJsonArray = jsonObj.getJSONArray("foods");

			Log.e("PREDICTIONS foods", predsJsonArray.toString());

			if (searchList != null && searchList.size() > 0)
				searchList.clear();

			// Extract the Place descriptions from the results
			// resultList = new ArrayList<String>(predsJsonArray.length());
			for (int i = 0; i < predsJsonArray.length(); i++) {

				// String itemId =
				// predsJsonArray.getJSONObject(i).getString("_id");

				_food_name = predsJsonArray.getJSONObject(i).getString(
						"food_name");
				_nf_calories = predsJsonArray.getJSONObject(i).getString(
						"nf_calories");
				// String nfServingSizeQty =
				// predsJsonArray.getJSONObject(i).getString("nf_serving_size_qty");
				// String itemDescription =
				// predsJsonArray.getJSONObject(i).getString("item_description");
				// String nfServingSizeUnit =
				// predsJsonArray.getJSONObject(i).getString("nf_serving_size_unit");
				_serving_weight_grams = predsJsonArray.getJSONObject(i)
						.getString("serving_weight_grams");
				// String nfIngredientStatement =
				// predsJsonArray.getJSONObject(i).getString("nf_ingredient_statement");

				_nf_total_fat = predsJsonArray.getJSONObject(i).getString(
						"nf_total_fat");
				_nf_saturated_fat = predsJsonArray.getJSONObject(i).getString(
						"nf_saturated_fat");
				_nf_cholesterol = predsJsonArray.getJSONObject(i).getString(
						"nf_cholesterol");
				_nf_sodium = predsJsonArray.getJSONObject(i).getString(
						"nf_sodium");
				_nf_total_carbohydrate = predsJsonArray.getJSONObject(i)
						.getString("nf_total_carbohydrate");
				_nf_dietary_fiber = predsJsonArray.getJSONObject(i).getString(
						"nf_dietary_fiber");
				_nf_sugars = predsJsonArray.getJSONObject(i).getString(
						"nf_sugars");
				_nf_protein = predsJsonArray.getJSONObject(i).getString(
						"nf_protein");
				_nf_potassium = predsJsonArray.getJSONObject(i).getString(
						"nf_potassium");
				_nf_p = predsJsonArray.getJSONObject(i).getString("nf_p");
				/*
				 * Log.e("itemName", itemName); // Log.e("itemId",itemId);
				 * Log.e("brandName", brandName); Log.e("nfCalories",
				 * nfCalories); Log.e("nfServingWeightGrams",
				 * nfServingWeightGrams);
				 */
				// Log.e("itemDescription",itemName);
				// Log.e("itemDescription",itemName);
				// Log.e("itemDescription",itemName);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		showPopup(getActivity());

	}

	private class AsyncNutrion extends AsyncTask<String, Void, String> {

		String JSON_URL;
		JSONObject holder = new JSONObject();

		public AsyncNutrion(String jsString, JSONObject object) {
			// TODO Auto-generated constructor stub
			JSON_URL = jsString;
			holder = object;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			pDialog.setMessage("Loading...");
			pDialog.show();

			super.onPreExecute();
		}

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
			httpost.setEntity(se);
			httpost.setHeader("Accept", "application/json");
			httpost.setHeader("Content-Type", "application/json");
			httpost.setHeader("x-app-id", "b65138b3");
			httpost.setHeader("x-app-key", "220fa746203ea5217abff5970c9f8d43");

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

			JSONObject jsonObj = null;
			try {
				jsonObj = new JSONObject(result);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JSONArray predsJsonArray;
			try {
				predsJsonArray = jsonObj.getJSONArray("foods");

				Log.e("PREDICTIONS foods", predsJsonArray.toString());

				if (searchList != null && searchList.size() > 0)
					searchList.clear();

				for (int i = 0; i < predsJsonArray.length(); i++) {

					_food_name = predsJsonArray.getJSONObject(i).getString(
							"food_name");
					_nf_calories = predsJsonArray.getJSONObject(i).getString(
							"nf_calories");

					_serving_weight_grams = predsJsonArray.getJSONObject(i)
							.getString("serving_weight_grams");

					_nf_total_fat = predsJsonArray.getJSONObject(i).getString(
							"nf_total_fat");
					_nf_saturated_fat = predsJsonArray.getJSONObject(i)
							.getString("nf_saturated_fat");
					_nf_cholesterol = predsJsonArray.getJSONObject(i)
							.getString("nf_cholesterol");
					_nf_sodium = predsJsonArray.getJSONObject(i).getString(
							"nf_sodium");
					_nf_total_carbohydrate = predsJsonArray.getJSONObject(i)
							.getString("nf_total_carbohydrate");
					_nf_dietary_fiber = predsJsonArray.getJSONObject(i)
							.getString("nf_dietary_fiber");
					_nf_sugars = predsJsonArray.getJSONObject(i).getString(
							"nf_sugars");
					_nf_protein = predsJsonArray.getJSONObject(i).getString(
							"nf_protein");
					_nf_potassium = predsJsonArray.getJSONObject(i).getString(
							"nf_potassium");
					_nf_p = predsJsonArray.getJSONObject(i).getString("nf_p");

					datamap.clear();
					nutritionTypeDAOs.clear();

					// Html.fromHtml("<big>Serving Weight Grams  <b></b></big><small>"+_serving_weight_grams+"</small>")
					if (!_nf_total_fat.equalsIgnoreCase("null")) {
						datamap.put("_nf_total_fat", _nf_total_fat);
					} else {
						datamap.put("_nf_total_fat", "0");
					}

					if (!_nf_saturated_fat.equalsIgnoreCase("null")) {
						datamap.put("_nf_saturated_fat", _nf_saturated_fat);
					} else {
						datamap.put("_nf_saturated_fat", "0");
					}

					if (!_nf_cholesterol.equalsIgnoreCase("null")) {
						datamap.put("_nf_cholesterol", _nf_cholesterol);

					} else {
						datamap.put("_nf_cholesterol", "0");

					}

					if (!_nf_sodium.equalsIgnoreCase("null")) {
						datamap.put("_nf_sodium", _nf_sodium);
					} else {
						datamap.put("_nf_sodium", "0");
					}

					if (!_nf_total_carbohydrate.equalsIgnoreCase("null")) {
						datamap.put("_nf_total_carbohydrate",
								_nf_total_carbohydrate);
					} else {
						datamap.put("_nf_total_carbohydrate", "0");
					}

					if (!_nf_dietary_fiber.equalsIgnoreCase("null")) {
						datamap.put("_nf_dietary_fiber", _nf_dietary_fiber);
					} else {
						datamap.put("_nf_dietary_fiber", "0");
					}

					if (!_nf_sugars.equalsIgnoreCase("null")) {
						datamap.put("_nf_sugars", _nf_sugars);

					} else {
						datamap.put("_nf_sugars", "0");
					}

					if (!_nf_protein.equalsIgnoreCase("null")) {
						datamap.put("_nf_protein", _nf_protein);
					} else {
						datamap.put("_nf_protein", "0");
					}

					if (!_nf_potassium.equalsIgnoreCase("null")) {
						datamap.put("_nf_potassium", _nf_potassium);
					} else {
						datamap.put("_nf_potassium", "0");
					}

					if (!_nf_p.equalsIgnoreCase("null")) {
						datamap.put("_nf_p", _nf_p);

					} else {
						datamap.put("_nf_p", "0");

					}

					JSONArray full_nutrients = predsJsonArray.getJSONObject(i)
							.getJSONArray("full_nutrients");

					for (int j = 0; j < full_nutrients.length(); j++) {
						JSONObject jobj_nutri = full_nutrients.getJSONObject(j);
						NutritionTypeSetget nutritionTypeSetget = new NutritionTypeSetget();

						nutritionTypeSetget.setAttr_id(jobj_nutri
								.getString("attr_id"));
						nutritionTypeSetget.setUsda_tag(jobj_nutri
								.getString("usda_tag"));
						nutritionTypeSetget.setValue(jobj_nutri
								.getString("value"));
						nutritionTypeSetget.setUnit(jobj_nutri
								.getString("unit"));
						nutritionTypeSetget.setName(jobj_nutri
								.getString("name"));

						nutritionTypeDAOs.add(nutritionTypeSetget);
					}

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			showPopup(getActivity());

		}
	}

	private class AsyncNutrionTwo extends AsyncTask<String, Void, String> {

		String JSON_URL;
		String __id;
		JSONObject holder = new JSONObject();

		public AsyncNutrionTwo(String jsString, JSONObject object, String _id) {
			// TODO Auto-generated constructor stub
			JSON_URL = jsString;
			holder = object;
			__id = _id;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			foodLoggingListView2.setVisibility(View.VISIBLE);
			pDialog.setMessage("Loading...");
			pDialog.show();

			super.onPreExecute();
		}

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
			httpost.setEntity(se);
			httpost.setHeader("Accept", "application/json");
			httpost.setHeader("Content-Type", "application/json");
			httpost.setHeader("x-app-id", "b65138b3");
			httpost.setHeader("x-app-key", "220fa746203ea5217abff5970c9f8d43");

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

			try {
				// Create a JSON object hierarchy from the results
				JSONObject jsonObj = new JSONObject(result);
				// JSONArray predsJsonArray = jsonObj.getJSONArray("foods");
				JSONArray predsJsonArray = jsonObj.getJSONArray("foods");

				Log.e("PREDICTIONS foods", predsJsonArray.toString());

				if (searchList != null && searchList.size() > 0)
					searchList.clear();
				String itemName = null;
				String brandName = "";
				String nfCalories = null;
				String nfServingWeightGrams = null;
				// resultList.clear();
				// Extract the Place descriptions from the results
				// resultList = new ArrayList<String>(predsJsonArray.length());
				for (int i = 0; i < predsJsonArray.length(); i++) {

					// String itemId =
					// predsJsonArray.getJSONObject(i).getString("_id");
					try {
						itemName = predsJsonArray.getJSONObject(i).getString(
								"food_name");
						brandName = predsJsonArray.getJSONObject(i).getString(
								"brand_name");
						nfCalories = predsJsonArray.getJSONObject(i).getString(
								"nf_calories");
						// String nfServingSizeQty =
						// predsJsonArray.getJSONObject(i).getString("nf_serving_size_qty");
						// String itemDescription =
						// predsJsonArray.getJSONObject(i).getString("item_description");
						// String nfServingSizeUnit =
						// predsJsonArray.getJSONObject(i).getString("nf_serving_size_unit");
						nfServingWeightGrams = predsJsonArray.getJSONObject(i)
								.getString("serving_weight_grams");
						// String nfIngredientStatement =
						// predsJsonArray.getJSONObject(i).getString("nf_ingredient_statement");

						Log.e("itemName", itemName);
						// Log.e("itemId",itemId);
						Log.e("brandName", brandName);
						Log.e("nfCalories", nfCalories);
						Log.e("nfServingWeightGrams", nfServingWeightGrams);
						// Log.e("itemDescription",itemName);
						// Log.e("itemDescription",itemName);
						// Log.e("itemDescription",itemName);
					} catch (Exception a) {

						a.printStackTrace();
					}
	

				}

				if (itemName.length() > 0 && nfCalories.length() > 0
						&& nfServingWeightGrams.length() > 0) {

					itemName = WordUtils.capitalize(itemName);
					boolean _exists = false;
					_exists = FoodLoginModule.ExistsFoodLog(__id,
							String.valueOf(mealId), databaseObject);

					if (_exists == false) {
						FoodLoginModule.insertFitmifoodTable(itemName,
								nfCalories, __id, nfServingWeightGrams, "",
								databaseObject, getActivity());

						FitmiFoodLogDAO fitmiFoodLogData = new FitmiFoodLogDAO();
						// FitmiFoodDAO obj = searchList.get(position);

						// avinash added calorie
						Log.e("caloryCalculate value during adding object  ",
								String.valueOf(nfCalories));
						// at first add calies 0
						// fitmiFoodLogData.setCalory(obj.getNfCalories());
						fitmiFoodLogData.setCalory("0");
						// fitmiFoodLogData.setServingWeightGrams(obj.getNfServingWeightGrams());
						// fitmiFoodLogData.setCalory("0");
						fitmiFoodLogData.setDescription("");
						fitmiFoodLogData.setFoodName(itemName);
						fitmiFoodLogData.setBrandName(brandName);
						fitmiFoodLogData.setReferenceFoodId(__id);
						fitmiFoodLogData
								.setUserId(com.fitmi.utils.Constants.USER_ID);
						fitmiFoodLogData
								.setUserProfileId(com.fitmi.utils.Constants.PROFILE_ID);
						fitmiFoodLogData
								.setLogTime(com.fitmi.utils.Constants.postDate);
						fitmiFoodLogData
								.setDateAdded(com.fitmi.utils.Constants.postDate);
						// fitmiFoodLogData.setDateAdded(com.fitmi.utils.Constants.sDate);
						fitmiFoodLogData.setMealId(String.valueOf(mealId));
						// avinash added getNfServingWeightGrams
						String ss = nfServingWeightGrams;
						// at first add getNfServingWeightGrams 0
						// fitmiFoodLogData.setMealWeight(obj.getNfServingWeightGrams());
						try {
							if (isFavMealVar.equalsIgnoreCase("0")) {
								fitmiFoodLogData.setMealFavourite("0");
							} else {
								fitmiFoodLogData.setMealFavourite("1");
							}
						} catch (Exception a) {
							fitmiFoodLogData.setMealFavourite("0");
							a.printStackTrace();

						}
						fitmiFoodLogData.setMealWeight("0");
						fitmiFoodLogData.setFavourite("0");
						// fitmiFoodLogData.setMealFavourite("0");
						// fitmiFoodLogData.setDateAdded(getDate.getDateFormat());
						mealIdSpinner = mealId;

						// added by avinash
						// fitmiFoodLogDataTemp=fitmiFoodLogData;
						fitmiFoodLogDataTemp = fitmiFoodLogData;
						if (!log) {
							FoodLoginModule.insertFitmifoodLogTable(
									fitmiFoodLogData, databaseObject);
						} else {

							if (com.fitmi.utils.Constants.foodLogData.size() > 0)
								com.fitmi.utils.Constants.foodLogData.clear();
							com.fitmi.utils.Constants.foodLogData
									.add(fitmiFoodLogData);
						}

						if (mealListData != null && mealListData.size() > 0)
							mealListData.clear();
						//items.clear();
						foodListData.add(fitmiFoodLogData);
						// foodListDataAlies.add(fitmiFoodLogData);
						// sectionAdapter.notifyDataSetChanged();
						if (log == true) {
							// foodListDataAlies.clear();
							foodListDataAlies.add(fitmiFoodLogData);
							com.fitmi.utils.Constants.foodLogData = new ArrayList<FitmiFoodLogDAO>(
									foodListDataAlies);
							// com.fitmi.utils.Constants.foodLogData=foodListDataAlies;
						} else {
							foodListDataAlies.clear();

							foodListDataAlies = foodLogObj.selectFoodList(
									String.valueOf(mealId), databaseObject);
						}
						searchListAdapter();
						newDataAdd = true;
						newDataCount++;

						HomeFragment tosetCalory = new HomeFragment();
						NotificationTotalCaloryChange callBack = (NotificationTotalCaloryChange) tosetCalory;

						callBack.setTotalCalory(caloryCalculate);

						databaseObject.closeDataBase();
					}
					searchEditText.setText("");
					// exporting database
					// exportDatabse("Fitmi.sqlite");
					try {
						if (!addMealLiner.isShown() && !log == true) {
							sFOODLOGGING_POS = foodListDataAlies.size() - 1;
							foodAdapter.notifyDataSetChanged();
							_searchadded = 1;
						}
					} catch (Exception e) {
						e.printStackTrace();
						try {
							mealListData = foodLogObj.selectFoodList(
									String.valueOf(mealIdSpinner),
									databaseObject);
							setMealAdapter();
							foodAdapterSearch.notifyDataSetChanged();
							foodAdapter.notifyDataSetChanged();
						} catch (Exception as) {
							as.printStackTrace();
						}
					}
				} else {

					Toast.makeText(getActivity(), "No data found",
							Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				Log.e("LOG TAG", "Cannot process JSON results", e);
			}

			pDialog.dismiss();
		}
	}

	public static class ListUtils {
		public static void setDynamicHeight(ListView mListView) {
			ListAdapter mListAdapter = mListView.getAdapter();
			if (mListAdapter == null) {
				// when adapter is null
				return;
			}
			int height = 0;
			int desiredWidth = MeasureSpec.makeMeasureSpec(
					mListView.getWidth(), MeasureSpec.UNSPECIFIED);
			for (int i = 0; i < mListAdapter.getCount(); i++) {
				View listItem = mListAdapter.getView(i, null, mListView);
				listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
				height += listItem.getMeasuredHeight();
			}
			ViewGroup.LayoutParams params = mListView.getLayoutParams();
			params.height = height
					+ (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
			mListView.setLayoutParams(params);
			mListView.requestLayout();
		}
	}

	private class PlacesAutoCompleteAdapter extends BaseAdapter {
		// private ArrayList<String> resultList;

		// ArrayList<FitmiFoodDAO> resultList = new
		// ArrayList<FitmiFoodDAO>(searchList);
		float nfgram = 0;
		Context context;

		public PlacesAutoCompleteAdapter(Context context) {
			// super(context, textViewResourceId);

			this.context = context;
		}

		@Override
		public int getCount() {
			return searchList1.size();
		}

		@Override
		public FitmiFoodDAO getItem(int index) {
			return searchList1.get(index);
		}

		ViewHolder holder;

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			if (convertView == null) {

				holder = new ViewHolder();

				// FitmiFoodDAO object = resultList.get(position);

				// if(!object.isCustomButton()){

				// LayoutInflater inflater = (LayoutInflater)
				// getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.autocomplete_layout,
						null);

			}

			// holder.txtName.setText(object.getItemName());
			// holder.txtCalGm =
			// (TextView)convertView.findViewById(R.id.txtCalGm);
			// // holder.txtDesc =
			// (TextView)convertView.findViewById(R.id.txtDesc);
			// holder.txtCal = (TextView)convertView.findViewById(R.id.txtCal);

			/*
			 * float calory = Float.parseFloat(object.getNfCalories());
			 * 
			 * if(!object.getNfServingWeightGrams().equalsIgnoreCase("null")) {
			 * 
			 * String number = object.getNfServingWeightGrams();
			 * 
			 * nfgram = Float.parseFloat(number); nfgram = calory/nfgram;
			 * holder.txtCalGm.setText(nfgram+" cal/gm"); }else{
			 * holder.txtCalGm.setText(""); }
			 *//*
				 * if(!object.getItemDescription().equalsIgnoreCase("null"))
				 * holder.txtDesc.setText(object.getItemDescription());
				 * 
				 * if(!object.getItemName().equalsIgnoreCase("null"))
				 * holder.txtName
				 * .setText(object.getItemName()+", "+object.getBrandName());
				 * else holder.txtName.setText(object.getBrandName());
				 * holder.txtCal.setText(object.getNfCalories()+" cal");
				 */
			// }else{

			/*
			 * LayoutInflater inflater = (LayoutInflater)
			 * context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			 * convertView = inflater.inflate(R.layout.custom_meal, null);
			 * 
			 * holder.customMeal =
			 * (Button)convertView.findViewById(R.id.add_customMeal);
			 */
			// }

			Log.e("counting view", "Count " + position);
			// convertView.setTag(holder);

			// }else{
			try {
				FitmiFoodDAO object = searchList1.get(position);
				// holder = (ViewHolder) convertView.getTag();
				holder.txtName = (TextView) convertView
						.findViewById(R.id.txtName);
				holder.txtName.setText(object.getItemName());
			} catch (Exception e) {
				e.printStackTrace();
			}
			// }

			return convertView;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		public class ViewHolder {

			TextView txtName;
			// TextView txtCalGm ;
			// TextView txtDesc ;
			// TextView txtCal ;
			// Button customMeal;

		}
	}

	private class AsyncAutocompleteNutri extends
			AsyncTask<String, Void, String> {

		String JSON_URL;
		String input;

		public AsyncAutocompleteNutri(String jsString, String input) {
			// TODO Auto-generated constructor stub
			JSON_URL = jsString;
			this.input = input;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			// pDialog.setMessage("Loading..."); pDialog.show();

			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... urls) {
			// placesAutoCompleteAdapter.notifyDataSetInvalidated();

			String typeText = input;
			String SetServerString = "";
			HttpURLConnection conn = null;
			// StringBuilder jsonResults = new StringBuilder();
			if (input.length() > 0) {
				try {
					// StringBuilder sb = new
					// StringBuilder("https://service.livestrong.com/service/food/foods/?query="+typeText+"&limit=5&fill=item,item_title,cals,serving_size,item_desc,images");
					/*
					 * StringBuilder sb = new
					 * StringBuilder("https://api.nutritionix.com/v1_1/search/"
					 * +typeText+
					 * "?fields=item_name,brand_name,nf_calories,nf_serving_size_qty,item_description,nf_serving_size_unit,nf_serving_weight_grams,nf_ingredient_statement&appId=b65138b3&appKey=220fa746203ea5217abff5970c9f8d43"
					 * );
					 */
					typeText = typeText.replaceAll(" ", "%20");
					Log.e("Input Key", typeText);
					StringBuilder sb = new StringBuilder(
							"https://api.nutritionix.com/v2/autocomplete?q="
									+ typeText
									+ "&appId=b65138b3&appKey=220fa746203ea5217abff5970c9f8d43");
					/*
					 * StringBuilder sb = new StringBuilder(URLEncoder.encode(
					 * "https://api.nutritionix.com/v2/autocomplete?q=" +
					 * typeText +
					 * "&appId=b65138b3&appKey=220fa746203ea5217abff5970c9f8d43"
					 * , "utf-8"));
					 */

					DefaultHttpClient Client = getNewHttpClient();
					HttpGet httpget = new HttpGet(sb.toString());

					ResponseHandler<String> responseHandler = new BasicResponseHandler();
					SetServerString = Client.execute(httpget, responseHandler);

				} catch (MalformedURLException e) {
					Log.e("LOG TAG", "Error processing Places API URL", e);
					return SetServerString;
				} catch (IOException e) {
					Log.e("LOG TAG", "Error connecting to Places API", e);
					return SetServerString;
				} finally {
					if (conn != null) {
						conn.disconnect();
					}
				}

				/*
				 * FitmiFoodDAO itemObj = new FitmiFoodDAO();
				 * itemObj.setCustomButton(true);
				 */
				// avinash

				// resultList.add(itemObj);

				// searchList.add(itemObj);

			}
			return SetServerString;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result.length() > 0) {
				try {

					JSONArray predsJsonArray = new JSONArray(result);
					Log.e("PREDICTIONS", predsJsonArray.toString());

					if (searchList1 != null && searchList1.size() > 0)
						searchList1.clear();

					searchList1.clear();

					for (int i = 0; i < predsJsonArray.length(); i++) {

						String itemId = predsJsonArray.getJSONObject(i)
								.getString("id");
						String itemName = predsJsonArray.getJSONObject(i)
								.getString("text");

						itemName = WordUtils.capitalize(itemName);

						FitmiFoodDAO itemObj = new FitmiFoodDAO();
						itemObj.setItemId(itemId);
						itemObj.setItemName(itemName);

						itemObj.setCustomButton(false);

						searchList1.add(itemObj);

					}
				} catch (JSONException e) {
					Log.e("LOG TAG", "Cannot process JSON results", e);
				}

				placesAutoCompleteAdapter.notifyDataSetChanged();
				list_autocomplete.setVisibility(View.VISIBLE);
				foodLoggingListView2.setVisibility(View.GONE);
				foodLoggingListView.setVisibility(View.GONE);

			}
		}

	}

	
	public void updateValueTap(int position) {
		try {

			Log.e("updateValueTap ","updateValueTap started "+position);
			if (foodListDataAlies.size() > 0) {

				caloryCalculate = 0;
				float cals_data = 0, serving_weightdata, pergram_cal, total_cals = 0;
				
				

						total_cals = Float.parseFloat(foodListDataAlies.get(position).getCalory()) ;
					
					caloryCalculate = total_cals;
					Log.e("foodListDataAlies.get(position).getFoodLogId() ",
							foodListDataAlies.get(position).getFoodLogId());

					int temp_to = 0;
					temp_to = (int) total_cals;

				
					currActiveCalwt = (String.valueOf(temp_to));
					Log.e("foodListDataAlies.get(position).getFoodLogId() ",
							foodListDataAlies.get(position).getFoodLogId());
					
					Log.e("foodListDataAlies.get(position).getMealWeight() ",
							foodListDataAlies.get(position).getMealWeight());
					
					FoodLoginModule.editCaloryItew(
							foodListDataAlies.get(position).getFoodLogId(),
							databaseObject, String.valueOf(total_cals), foodListDataAlies.get(position).getMealWeight());
					

					foodListDataAlies = foodLogObj.selectFoodList(
							String.valueOf(mealIdSpinner), databaseObject);
				
			/*		try{
				foodAdapterSearch.notifyDataSetChanged();
					}catch(Exception a)
					{
						a.printStackTrace();
					}*/
				for (int i = 0; i < foodListDataAlies.size(); i++) {
					if (!foodListDataAlies.get(i).getCalory()
							.equalsIgnoreCase(""))

						caloryCalculate += Float.parseFloat(foodListDataAlies
								.get(i).getCalory());
				}

				totalCalory.setText((int) caloryCalculate + "" + " cal");
				totalFoodFooterAdapter.notifyDataSetChanged();
				_fitmifoodList.clear();
				foodAdapter.notifyDataSetChanged();
			} else if (mealListData != null && mealListData.size() > 0) {

				caloryCalculate = 0;

				float cals_data, serving_weightdata, pergram_cal, total_cals = 0;
	
					total_cals = Float.parseFloat(mealListData.get(position).getCalory());
				
				int temp_to = 0;
				temp_to = (int) total_cals;

				currActiveCalwt = (String.valueOf(temp_to));
				
				Log.e("caloryCalculate 2  ", String.valueOf(caloryCalculate));

				Log.e("currActiveCalwt   ", currActiveCalwt);
				Log.e("mealListData.get(position).getMealWeight() ",
						mealListData.get(position).getMealWeight());
				
				Log.e("mealListData.get(position).getCalory() ",
						mealListData.get(position).getCalory());
				
				Log.e("mealListData.get(position).getFoodLogId() ",
						mealListData.get(position).getFoodLogId());
				
				FoodLoginModule.editCaloryItew(mealListData.get(position)
						.getFoodLogId(), databaseObject, mealListData.get(position).getCalory(), mealListData.get(position).getMealWeight());
				// mealListData.remove(position);
				mealListData = foodLogObj.selectFoodList(
						String.valueOf(mealIdSpinner), databaseObject);
				/*try{
					foodAdapterSearch.notifyDataSetChanged();
						}catch(Exception a)
						{
							a.printStackTrace();
						}*/

				for (int i = 0; i < mealListData.size(); i++) {
					if (!mealListData.get(i).getCalory().equalsIgnoreCase(""))
						caloryCalculate += Float.parseFloat(mealListData.get(i)
								.getCalory());
				}

				totalCalory.setText((int) caloryCalculate + "" + " cal");
				_fitmifoodList.clear();
				totalFoodFooterAdapter.notifyDataSetChanged();
				foodAdapter.notifyDataSetChanged();
			}

			
			Log.e("currActiveCalwt   ", currActiveCalwt);
	

		} catch (Exception exception) {

			exception.printStackTrace();
		}
		// totalFoodFooterAdapter.notifyDataSetChanged();
		if (isFavMealVar.equalsIgnoreCase("0")) {
			totalFoodFooterAdapter = new TotalFoodFooterAdapter(getActivity(),
					currActivewt, currActiveCalwt,
					String.valueOf((int) caloryCalculate), "0");
		} else {
			totalFoodFooterAdapter = new TotalFoodFooterAdapter(getActivity(),
					currActivewt, currActiveCalwt,
					String.valueOf((int) caloryCalculate), "1");
		}
		
		setFoodSpinner();
	
		listTotalFrame_FoodLogging.setAdapter(totalFoodFooterAdapter);
		totalFoodFooterAdapter.notifyDataSetChanged();

	}// end method
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
	
		/*
		 * if(activityReceiver!=null){
		 * getActivity().unregisterReceiver(activityReceiver); }
		 */

		super.onPause();
	}
	
	
		public void showSyncdialog() {
			if (com.fitmi.utils.Constants.isBluetoothOnLocal == 1
					&& com.fitmi.utils.Constants.connectedTodevice == 1) {
	
			} else {
				dialogDevicesync();
			}
		}

	public void dialogDevicesync() {

		 final AlertDialog alertDialog = new AlertDialog.Builder(getActivity(),AlertDialog.THEME_HOLO_DARK).create();
		 
			 alertDialog.setTitle("Fitmi");
		//	 alertDialog.setIcon(R.drawable.app_icon);
			 
			
	         alertDialog.setMessage("Kindly Sync the Kitchen Scale Device");
	         
	         alertDialog.setButton("Sync Now", new DialogInterface.OnClickListener() {
	             public void onClick(DialogInterface dialog, int which) {
	            	 sendKitchenScale();
	            	 
	             }
				});
	         alertDialog.setButton2("Later",new DialogInterface.OnClickListener() {
	             @Override
	             public void onClick(DialogInterface dialog, int which) {
	            	 
	            	 alertDialog.dismiss();
	            	 
					}
				});

	         alertDialog.show();
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		// int height = metrics.heightPixels;
		alertDialog.getWindow()
				.setLayout((6 * width) / 7, LayoutParams.WRAP_CONTENT);

	}

	public void sendKitchenScale() {

		UserInfoDAO userInfo;
		UserInfoModule userDb;
		
		userDb = new UserInfoModule(getActivity());
		userInfo = UserInfoModule.getUserInformation(databaseObject);
		DeviceSyncFragment fragment = new DeviceSyncFragment();

		Bundle bundle = new Bundle();
		bundle.putInt("root_id", R.id.root_home_frame);

		bundle.putString("userName",
				userInfo.getFirstName() + " " + userInfo.getLastName());
		bundle.putString("deviceName", "FoodScale");
		bundle.putString("imgPath", userInfo.getPicturePath());
		bundle.putInt("scaleType", 1);

		fragment.setArguments(bundle);

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction
				.add(R.id.root_home_frame, fragment, "DeviceSyncFragment");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack("DeviceSyncFragment");
		transaction.commit();
	}
	
	public void changeUnitFood(){
		
		
		
		if(com.fitmi.utils.Constants.gunitfw == 0)
		{
		com.fitmi.utils.Constants.gunitfw = 0;	
		
		unitDataFood_Weight=new UnitItemDAO();
		
		unitDataFood_Weight.setProfileId(com.fitmi.utils.Constants.PROFILE_ID);
		unitDataFood_Weight.setUserId(com.fitmi.utils.Constants.USER_ID);
		unitDataFood_Weight.setType("food_weight");
		unitDataFood_Weight.setUnitId(String.valueOf(7));
		
		}else{
			com.fitmi.utils.Constants.gunitfw = 1;	
			
			unitDataFood_Weight=new UnitItemDAO();
			
			unitDataFood_Weight.setProfileId(com.fitmi.utils.Constants.PROFILE_ID);
			unitDataFood_Weight.setUserId(com.fitmi.utils.Constants.USER_ID);
			unitDataFood_Weight.setType("food_weight");
			unitDataFood_Weight.setUnitId(String.valueOf(8));
		}
		
		if(com.fitmi.utils.Constants.gunitbp == 0)
		{
		com.fitmi.utils.Constants.gunitbp = 0;	
		

		unitDataBp = new UnitItemDAO();

		unitDataBp.setProfileId(com.fitmi.utils.Constants.PROFILE_ID);
		unitDataBp.setUserId(com.fitmi.utils.Constants.USER_ID);
		unitDataBp.setType("blood_pressure");
		unitDataBp.setUnitId(String.valueOf(5));
		}else{
			com.fitmi.utils.Constants.gunitbp = 1;	
			

			unitDataBp = new UnitItemDAO();

			unitDataBp.setProfileId(com.fitmi.utils.Constants.PROFILE_ID);
			unitDataBp.setUserId(com.fitmi.utils.Constants.USER_ID);
			unitDataBp.setType("blood_pressure");
			unitDataBp.setUnitId(String.valueOf(6));
		}
		
		if (com.fitmi.utils.Constants.gunitht == 0)
		{
		com.fitmi.utils.Constants.gunitht = 0;	
		
		unitDataHeight = new UnitItemDAO();

		unitDataHeight.setProfileId(com.fitmi.utils.Constants.PROFILE_ID);
		unitDataHeight.setUserId(com.fitmi.utils.Constants.USER_ID);
		unitDataHeight.setType("height");
		unitDataHeight.setUnitId(String.valueOf(1));
		

		}
		else{
			com.fitmi.utils.Constants.gunitht = 1;	
			
			unitDataHeight = new UnitItemDAO();

			unitDataHeight.setProfileId(com.fitmi.utils.Constants.PROFILE_ID);
			unitDataHeight.setUserId(com.fitmi.utils.Constants.USER_ID);
			unitDataHeight.setType("height");
			unitDataHeight.setUnitId(String.valueOf(2));
			
	
		}
		
		if (com.fitmi.utils.Constants.gunitwt == 0) {	
		
			unitDataWeight=new UnitItemDAO();
			
			com.fitmi.utils.Constants.gunitwt = 0;	
			unitDataWeight.setProfileId(com.fitmi.utils.Constants.PROFILE_ID);
			unitDataWeight.setUserId(com.fitmi.utils.Constants.USER_ID);
			unitDataWeight.setType("weight");
			unitDataWeight.setUnitId("4");
			
			}else{
				unitDataWeight=new UnitItemDAO();
				
				com.fitmi.utils.Constants.gunitwt = 1;	
				unitDataWeight.setProfileId(com.fitmi.utils.Constants.PROFILE_ID);
				unitDataWeight.setUserId(com.fitmi.utils.Constants.USER_ID);
				unitDataWeight.setType("weight");
				unitDataWeight.setUnitId("3");
			}
		
		unitModel.setUnitLog(unitDataHeight);
		unitModel.setUnitLog(unitDataWeight);
		unitModel.setUnitLog(unitDataBp);
		unitModel.setUnitLog(unitDataFood_Weight);
		
	}
}