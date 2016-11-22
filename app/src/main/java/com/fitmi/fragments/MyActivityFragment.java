package com.fitmi.fragments;

import it.sephiroth.demo.slider.widget.OnSwipeTouchListener;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.callback.ActivityTimeUpdate;
import com.callback.NotificationCalorieIntake;
import com.callback.NotificationCaloryBurn;
import com.dts.utils.Constants;

import org.apache.commons.lang3.text.WordUtils;
import org.apache.http.HttpVersion;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
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

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.db.DatabaseHelper;
import com.db.modules.ActivityModule;
import com.db.modules.FoodLoginModule;
import com.fitmi.R;
import com.fitmi.activitys.BaseActivity;
import com.fitmi.adapter.ActivitiesAdapter;
import com.fitmi.adapter.ActivityLoggingSpinnerAdapter;
import com.fitmi.adapter.FoodLoggingSpinnerAdapter;
import com.fitmi.dao.CalenderFirsLastDay;
import com.fitmi.dao.ExerciseItemDAO;
import com.fitmi.dao.FitmiFoodDAO;
import com.fitmi.dao.FitmiFoodLogDAO;

import com.fitmi.utils.DateModule;
import com.fitmi.utils.FirstLastDayWeekMonthly;
import com.fitmi.utils.NotificationTotalCaloryChange;
import com.ssl.MySSLSocketFactory;

public class MyActivityFragment extends BaseFragment implements
		ActivityTimeUpdate, NotificationCalorieIntake {

	@InjectView(R.id.Heading)
	public TextView heading;

	@InjectView(R.id.Back)
	public ImageView back;

	@InjectView(R.id.backLiner)
	LinearLayout backLiner;

	@InjectView(R.id.dailyCaloryIntake)
	TextView dailyCaloryIntake;

	@InjectView(R.id.remainCalory)
	TextView tvRemainCalory;

	@InjectView(R.id.Date)
	TextView dateTextView;

	@InjectView(R.id.totalmin)
	public TextView totalmintxt;

	@InjectView(R.id.totalcal)
	public TextView totalcaltxt;

	@InjectView(R.id.FoodLoggingSpinner)
	Spinner foodLoggingSpinner;

	@InjectView(R.id.ActivitySpinner)
	Spinner activitySpinner;

	@InjectView(R.id.ActivityListView)
	SwipeMenuListView activityListView;
//	ListView activityListView;

	@InjectView(R.id.RecentActivities)
	Button recentActivities;

	@InjectView(R.id.MyActivities)
	Button myActivity;

	@InjectView(R.id.SearchEditText)
	EditText searchEditText;
	
	@InjectView(R.id.list_autocomplete)
	ListView list_autocomplete;
	
	public static int sACTIVITY_POS = -1;

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

	@InjectView(R.id.RecentListView_ParentLinear)
	LinearLayout recentListViewParentLinear;

	@InjectView(R.id.ListBreakfastLinear)
	LinearLayout ListBreakfastLinear;

	@InjectView(R.id.TotalFrame_FoodLogging)
	FrameLayout TotalFrame_FoodLogging;

	@InjectView(R.id.RecentListView_ParentLinear)
	LinearLayout RecentListView_ParentLinear;

	@InjectView(R.id.Recent_Activity_ListView)
	ListView Recent_Activity_ListView;

	
	PlacesAutoCompleteAdapter placesAutoCompleteAdapter;
	/*
	 * @InjectView(R.id.parentHideShow) LinearLayout parentHideShow;
	 */

	@InjectView(R.id.frameFavActivityShow)
	FrameLayout frameFavActivityShow;

	@InjectView(R.id.favContainer)
	LinearLayout favContainer;

	@InjectView(R.id.logBtn)
	Button logBtn;

	Integer[] fooddrawableValues;
	Integer[] activitydrawableValues;
	Integer[] activitydrawableValuesAlies;

	String[] activityValues;
	String[] activityValuesId;
	String[] foodValues;
	DatabaseHelper databaseObject;
	ArrayList<HashMap<String, String>> List = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> searchList = new ArrayList<HashMap<String, String>>();

	ArrayList<HashMap<String, String>> ListRecent = new ArrayList<HashMap<String, String>>();

	ActivitiesAdapter activityAdapter;

	DateModule getDate = new DateModule();

	ArrayList<String> calorySumList;
	ArrayList<ExerciseItemDAO> caloryBurnList;
	DateModule dateModule = new DateModule();
	FirstLastDayWeekMonthly firstLastDayDate = new FirstLastDayWeekMonthly();
	String stringDate = "";
	private final int REQ_CODE_SPEECH_INPUT = 100;
	int calorie_per_h = 0;
	int remainCalory = 0;
	String today = "";
	String totalCaloty = "";
	String caloryInTake = "";
	FoodLoggingSpinnerAdapter flsa;
	ArrayAdapter<String> adapter;
	FoodLoginModule foodLogObj;
	ArrayList<FitmiFoodLogDAO> foodListData;
	float caloryCalculate = 0;
	boolean foodDropClick = false;
	boolean flingUpDown = false;
	boolean activityDropClick = false;
	boolean animationShow = false;
	private GestureDetector mGestureDetector;


	
	int mRootId = 0;
	boolean log = false;
	char[] activityType;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.fragment_myactivity, container,
				false);

		setNullClickListener(view);
		ButterKnife.inject(this, view);
		heading.setText("Activity");

		foodLogObj = new FoodLoginModule(getActivity());

		getActivity().registerReceiver(dateSetReceiver,
				new IntentFilter("activityUpdate"));

		com.fitmi.utils.Constants.fragmentNumber = 2;
		com.fitmi.utils.Constants.homeCaloryIntake = dailyCaloryIntake;
		com.fitmi.utils.Constants.remainCaloryBurn = tvRemainCalory;
		com.fitmi.utils.Constants.foodcalorieText = food_calorie_text;
		com.fitmi.utils.Constants.fragmentSet = false;
		com.fitmi.utils.Constants.shareIntent = true;
		com.fitmi.utils.Constants.fragmentSet = false;

		databaseObject = new DatabaseHelper(getActivity());
		try {

			databaseObject.openDatabase();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * calorySumList = FoodLoginModule.totalCalory(databaseObject);
		 * foodValues = new String[5] ;
		 * 
		 * foodValues[0]=com.fitmi.utils.Constants.TOTAL_CALORY_INTAKE;
		 * 
		 * dailyCaloryIntake.setText(com.fitmi.utils.Constants.TOTAL_CALORY_INTAKE
		 * );
		 * 
		 * for(int i=0;i<calorySumList.size();i++) { if(calorySumList.get(i)
		 * !=null) foodValues[i+1]=calorySumList.get(i); else
		 * foodValues[i+1]="0"; }
		 */

		activitydrawableValues = new Integer[] { R.drawable.calories_burned,
				R.drawable.chin_ups, R.drawable.treadmill, R.drawable.swimming,
				R.drawable.jumprope, R.drawable.boxing,
				R.drawable.lifting_weight };

		/*
		 * activityValues = new String[caloryBurnList.size()+1];
		 * activitydrawableValuesAlies = new Integer[caloryBurnList.size()+1];
		 * activityValues[0] = com.fitmi.utils.Constants.TOTAL_CALORY_BURN;
		 * activitydrawableValuesAlies[0] = activitydrawableValues[0];
		 * 
		 * for(int i=0;i<caloryBurnList.size();i++) { if(caloryBurnList.get(i)
		 * !=null){ activityValues[i+1]=caloryBurnList.get(i);
		 * activitydrawableValuesAlies[i+1] = activitydrawableValues[i+1]; }
		 * else activityValues[i+1]="0"; }
		 */

		setActivitySpinner();

		fooddrawableValues = new Integer[] { R.drawable.food,
				R.drawable.breakfast, R.drawable.lunch, R.drawable.dinner,
				R.drawable.snack };

		/*
		 * adapter = new ArrayAdapter<String>(getActivity(),
		 * R.layout.spinner_item, foodValues);
		 * 
		 * flsa = new FoodLoggingSpinnerAdapter( getActivity(), foodValues,
		 * fooddrawableValues, R.drawable.circle_green);
		 * adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
		 */

		setFoodSpinner();

		/*
		 * FoodLoggingSpinnerAdapter flsa1 = new FoodLoggingSpinnerAdapter(
		 * getActivity(), activityValues, activitydrawableValuesAlies,
		 * R.drawable.circle_pink); activitySpinner.setAdapter(flsa1);
		 */

		foodLoggingSpinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub

						food_image
								.setImageResource(fooddrawableValues[position]);
						food_calorie_text.setText(foodValues[position]);

						if (foodDropClick) {
							/*
							 * food_image
							 * .setImageResource(fooddrawableValues[position]);
							 * food_calorie_text.setText(foodValues[position]);
							 */

							FragmentTransaction transaction = getFragmentManager()
									.beginTransaction();
							FoodLoggingFragment foodLog = new FoodLoggingFragment();
							Bundle bundle = new Bundle();

							switch (position) {
							case 0:

								transaction.add(R.id.root_home_frame, foodLog,
										"FoodLoggingFragment");
								transaction
										.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

								break;

							case 1:

								bundle.putInt("mealid", 1);
								bundle.putString("foodtype", "Breakfast");
								bundle.putBoolean("replace", false);

								transaction.add(R.id.root_home_frame, foodLog,
										"FoodLoggingFragment");
								foodLog.setArguments(bundle);
								transaction
										.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

								break;
							case 2:

								bundle.putInt("mealid", 2);
								bundle.putString("foodtype", "Lunch");
								bundle.putBoolean("replace", false);

								transaction.add(R.id.root_home_frame, foodLog,
										"FoodLoggingFragment");
								foodLog.setArguments(bundle);
								transaction
										.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

								break;

							case 3:

								bundle.putInt("mealid", 3);
								bundle.putString("foodtype", "Dinner");
								bundle.putBoolean("replace", false);

								transaction.add(R.id.root_home_frame, foodLog,
										"FoodLoggingFragment");
								foodLog.setArguments(bundle);
								transaction
										.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

								break;

							case 4:

								bundle.putInt("mealid", 4);
								bundle.putString("foodtype", "Snack");
								bundle.putBoolean("replace", false);

								transaction.add(R.id.root_home_frame, foodLog,
										"FoodLoggingFragment");
								foodLog.setArguments(bundle);
								transaction
										.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

								break;
							}

							transaction.addToBackStack(null);
							transaction.commit();

						}

					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});

		activitySpinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub

						/*
						 * activity_image
						 * .setImageResource(activitydrawableValues[position]);
						 * activity_calorie_text
						 * .setText(activityValues[position]);
						 * 
						 * TextView selectedText = (TextView)
						 * parent.getChildAt(0); if (selectedText != null) {
						 * selectedText.setTextColor(Color.RED); }
						 */
						if (activityDropClick) {
							int IdValue=0;
							IdValue=Integer.parseInt(activityValuesId[position]);
							activityDropClick = false;
							switch (IdValue) {
							case 0:
								List.clear();
								List = ActivityModule.get_fitmi_exercise_log(databaseObject);
					//			setAdapter();
						//		activityAdapter.notifyDataSetChanged();
								break;

							case 1:
								List.clear();
								List = ActivityModule
								.get_fitmi_exercise_log_usigId(
										String.valueOf(1),
										databaseObject);
					
						//		setAdapter();
						//		activityAdapter.notifyDataSetChanged();
								break;
							case 2:
								List.clear();
								List = ActivityModule
								.get_fitmi_exercise_log_usigId(
										String.valueOf(2),
										databaseObject);
								
						
							//	setAdapter();
							//	activityAdapter.notifyDataSetChanged();

								break;

							case 3:
								List.clear();
								List = ActivityModule
								.get_fitmi_exercise_log_usigId(
										String.valueOf(3),
										databaseObject);
								
							//	setAdapter();
							//	activityAdapter.notifyDataSetChanged();

								break;
							case 4:
								List.clear();
								List = ActivityModule
								.get_fitmi_exercise_log_usigId(
										String.valueOf(4),
										databaseObject);
								
								setAdapter();
								activityAdapter.notifyDataSetChanged();

								break;
							case 5:
								List.clear();
								List = ActivityModule
								.get_fitmi_exercise_log_usigId(
										String.valueOf(5),
										databaseObject);
								
							//	setAdapter();
							//	activityAdapter.notifyDataSetChanged();

								break;
							case 6:
								List.clear();
								List = ActivityModule
								.get_fitmi_exercise_log_usigId(
										String.valueOf(6),
										databaseObject);
								
							//	setAdapter();
							//	activityAdapter.notifyDataSetChanged();
								break;
							}
							activityAdapter.notifyDataSetChanged();
						}
						

					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});

		if (com.fitmi.utils.Constants.activityLog) {
			ActivityModule.deleteActivityLog(databaseObject);
		}

		sACTIVITY_POS = -1;

		Bundle bundle = this.getArguments();

		if (bundle != null) {
			int id = bundle.getInt("livestrong_id", 1);
			boolean replace = bundle.getBoolean("replace", false);
			log = bundle.getBoolean("log");
			boolean myactivity_run;
			myactivity_run = bundle.getBoolean("activity", false);
			System.out.println("id=" + id);
			List.clear();
			if (id != 1) {
				if (!log && myactivity_run == false) {
					if (replace) {
						ActivityModule.deleteActivityLog(databaseObject);
					} else {
						if(id==0)
						{
							List.clear();
							List = ActivityModule.get_fitmi_exercise_log(databaseObject);
							
						}else{
						long rowid;
						boolean existing = false;
						existing = ActivityModule.Exists(String.valueOf(id),
								databaseObject);
						if (existing
								&& !BaseActivity.__activityId
										.equalsIgnoreCase("0")) {
							rowid = Long.parseLong(BaseActivity.__activityId);
							List = ActivityModule
									.get_fitmi_exercise_log_usigId(
											String.valueOf(rowid),
											databaseObject);
						} else {
							Toast.makeText(getActivity(),
									"No Value For Activity", Toast.LENGTH_LONG)
									.show();
						}
						}
						
						/*
						 * HashMap<String , String> map=new HashMap<>();
						 * //avinash // map.put(BaseActivity.livestrong_id,
						 * List.get(0).get("fitness_id"));
						 * map.put(BaseActivity.livestrong_id,
						 * List.get(0).get(BaseActivity.livestrong_id));
						 * map.put(BaseActivity.exercise_name,
						 * List.get(0).get(BaseActivity.exercise));
						 * map.put(BaseActivity.description,
						 * List.get(0).get(BaseActivity.description));
						 * map.put(BaseActivity.cals_per_hour,
						 * List.get(0).get(BaseActivity.cals_per_hour));
						 * 
						 * map.put(BaseActivity.calories_burned,
						 * List.get(0).get(BaseActivity.cals_per_hour));
						 * searchList.add(map);
						 * 
						 * dialog(searchList.size()-1);
						 * 
						 * List.clear();
						 */

						// System.out.println("listsize="+List.size());
						// ActivityModule.execiseloginsertion(databaseObject,
						// List);
						// List.clear();
						// List=ActivityModule.get_fitmi_exercise_log(databaseObject);
						// System.out.println("listsize="+List.size());
					}
				} else {
					if (replace) {
						ActivityModule.deleteActivityLog(databaseObject);
					} else {
						List = get_fitmi_exercise(id);

						HashMap<String, String> map = new HashMap<>();
						// avinash
						// map.put(BaseActivity.livestrong_id,
						// List.get(0).get("fitness_id"));
						map.put(BaseActivity.livestrong_id,
								List.get(0).get(BaseActivity.livestrong_id));
						map.put(BaseActivity.exercise_name,
								List.get(0).get(BaseActivity.exercise));
						map.put(BaseActivity.description,
								List.get(0).get(BaseActivity.description));
						map.put(BaseActivity.cals_per_hour,
								List.get(0).get(BaseActivity.cals_per_hour));

						map.put(BaseActivity.calories_burned,
								List.get(0).get(BaseActivity.cals_per_hour));
						searchList.add(map);

						dialog(searchList.size() - 1);

						List.clear();

						// System.out.println("listsize="+List.size());
						// ActivityModule.execiseloginsertion(databaseObject,
						// List);
						// List.clear();
						// List=ActivityModule.get_fitmi_exercise_log(databaseObject);
						// System.out.println("listsize="+List.size());
					}
				}
			}
		} else {
			List.clear();

			if (!log) {

				List = ActivityModule.get_fitmi_exercise_log(databaseObject);
				System.out.println("bundle null");
			}
		}

		if (com.fitmi.utils.Constants.pageLog == 0) {
			logBtn.setVisibility(View.GONE);
			back.setVisibility(View.VISIBLE);
		} else {
			logBtn.setVisibility(View.VISIBLE);
			back.setVisibility(View.GONE);
		}

		databaseObject.closeDataBase();

		// ArrayList<String> list = new ArrayList<String>();
		setAdapter();

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

			}
		};
		activityListView.setMenuCreator(creator);
		
		
		activityListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(int position,
					SwipeMenu menu, int index) {
				// ApplicationInfo item = mAppList.get(position);
				switch (index) {
				case 0:
					// open
					// open(item);

					HashMap<String, String> deleteItem = List.get(position);

					String itemId = deleteItem.get(BaseActivity.id);

					deleteActivityItem(position, itemId);
					
					break;
					
				case 1:
					HashMap<String, String> edittem = List.get(position);

					String editemId = edittem.get(BaseActivity.id);
					
					editActivityItem(position, editemId);
					break;
				
				}
				return false;
			}
		});


		activityListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if (sACTIVITY_POS == arg2) {
					sACTIVITY_POS = -1;
				} else
					sACTIVITY_POS = arg2;
			//	sACTIVITY_POS = arg2;
				activityAdapter.notifyDataSetChanged();
				

			}
		});

		//OLD LISTVIEW
/*		activityListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				sACTIVITY_POS = arg2;
				activityAdapter.notifyDataSetChanged();
				

			}

		});

		activityListView
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub

						HashMap<String, String> deleteItem = List.get(position);

						String itemId = deleteItem.get(BaseActivity.id);

						dialogDeleteItem(position, itemId);

						return false;
					}
				});*/

		//recent list view
		Recent_Activity_ListView
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {

						HashMap<String, String> selectItem = ListRecent
								.get(position);

						dialogAddItem(selectItem);

					}
				});

		//avinash changes
		placesAutoCompleteAdapter=new PlacesAutoCompleteAdapter(getActivity());
		list_autocomplete.setAdapter(placesAutoCompleteAdapter);
		
		
		list_autocomplete.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                    long id) {
               
            	dialog(position);
            	
				list_autocomplete.setVisibility(View.GONE);
				activityListView.setVisibility(View.VISIBLE);
				searchEditText.setText("");
				InputMethodManager in = (InputMethodManager) getActivity()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				in.hideSoftInputFromWindow(
						searchEditText.getApplicationWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
              
               
            }
        });
    	
		
    	list_autocomplete.setOnTouchListener(new OnTouchListener() {
              float height;
              @Override
              public boolean onTouch(View v, MotionEvent event) {
                  int action = event.getAction();
                  float height = event.getY();
                  
          		View view = getActivity().getCurrentFocus();
    			if (view != null) {  
    			    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    			    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    			}
                  if(action == MotionEvent.ACTION_DOWN){
                      this.height = height;
                      
                  }else if(action == MotionEvent.ACTION_UP){
                      if(this.height < height){
                          Log.v("Foodlog", "Scrolled up");
                      }else if(this.height > height){
                          Log.v("Foodlog", "Scrolled down");
                      }
                  }
                  return false;
              }
          });
    	
		searchEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				
				if (AsyncAutocompleteActivity !=null){
					AsyncAutocompleteActivity.cancel(true);
					
				}
				if( cs.toString().length()>1)
				{
					AsyncAutocompleteActivity = new AsyncAutocompleteActivity(cs.toString());
					AsyncAutocompleteActivity.execute("exe");
				}
				/*if(cs.length()<=0)
				{
					searchList.clear();
					
					placesAutoCompleteAdapter.notifyDataSetChanged();
				}*/
				
		
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
		
/*		searchEditText.setAdapter(new PlacesAutoCompleteAdapter(getActivity(),
				R.layout.item_autocomplete));*/
		/*searchEditText.setAdapter(placesAutoCompleteAdapter);

		searchEditText.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String str = (String) adapterView.getItemAtPosition(position);

				// changes by avinash
				
				 * if(log){ ww map.put(BaseActivity.livestrong_id,
				 * jobj.getString("fitness_id"));
				 * map.put(BaseActivity.exercise_name,
				 * jobj.getString(BaseActivity.exercise));
				 * map.put(BaseActivity.description,
				 * jobj.getString(BaseActivity.description));
				 * //map.put(BaseActivity.cals_per_hour,
				 * jobj.getString(BaseActivity.cals_per_hour));
				 * map.put(BaseActivity.calories_burned,
				 * jobj.getString(BaseActivity.cals_per_hour));
				 * 
				 * HashMap<String, String> mapList = searchList.get(position);
				 * 
				 * HashMap<String, String> map = new HashMap<String, String>();
				 * 
				 * map.put(BaseActivity.exercise_name,
				 * mapList.get("exercise_name"));
				 * map.put(BaseActivity.description,
				 * mapList.get("description"));
				 * map.put(BaseActivity.calories_burned,
				 * mapList.get("calories_burned")); map.put(BaseActivity.id,
				 * "1"); //map.put(BaseActivity.cals_per_hour,
				 * cur.getString(cur.
				 * getColumnIndex(BaseActivity.cals_per_hour)));
				 * map.put(BaseActivity.total_time_minutes, "60");
				 * 
				 * List.add(map);
				 * com.fitmi.utils.Constants.activityLogData.add(List);
				 * 
				 * }else{
				 * 
				 * System.out.println("str="+str); ArrayList<HashMap<String,
				 * String>> exerciselist = new ArrayList<HashMap<String,
				 * String>>(); exerciselist.clear();
				 * exerciselist=get_fitmi_exercise
				 * (Integer.parseInt(searchList.get
				 * (position).get(BaseActivity.livestrong_id)));
				 * 
				 * if(exerciselist.size()>0) {
				 * ActivityModule.execiseloginsertion
				 * (databaseObject,exerciselist); } else { dialog(position);
				 * 
				 * long
				 * rowid=ActivityModule.execiseinsertion(databaseObject,searchList
				 * .get(position));
				 * ActivityModule.execiseloginsertion(databaseObject
				 * ,searchList.get(position),rowid);
				 * System.out.println("rowid="+rowid); } List.clear();
				 * List=ActivityModule.get_fitmi_exercise_log(databaseObject);
				 * 
				 * dialog(position); }
				 
				dialog(position);
				// setAdapter();
				// activityAdapter.notifyDataSetChanged();

				InputMethodManager in = (InputMethodManager) getActivity()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				in.hideSoftInputFromWindow(
						searchEditText.getApplicationWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);

				searchEditText.setText("");

			}

		});
*/
		mGestureDetector = new GestureDetector(getActivity(),
				new GestureListener());

		frameFavActivityShow.setOnTouchListener(new OnTouchListener() {// myActivity

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub

						boolean eventConsumed = mGestureDetector
								.onTouchEvent(event);
						if (eventConsumed) {
							// Toast.makeText(getActivity(),"Gester",Toast.LENGTH_LONG).show();
				
							Log.e("eventConsumed", " true");
							return true;
						} else
						{
							Log.e("eventConsumed", " false");
							return false;
						}
					}
				});

		return view;

	}
	AsyncAutocompleteActivity AsyncAutocompleteActivity;

	public ArrayList<HashMap<String, String>> get_fitmi_exercise(int id) {
		return ActivityModule.get_fitmi_exercise(databaseObject, id);

	}

	public void setAdapter() {

		// avinash need to change
		activityAdapter = new ActivitiesAdapter(getActivity(), List,
				totalmintxt);
		System.out.println("listsize=" + List.size());
		if (List.size() > 0) {
			int totalmin = 0, totalcal = 0;
			for (int i = 0; i < List.size(); i++) {
				totalmin += Integer.parseInt(List.get(i).get(
						BaseActivity.total_time_minutes));
				totalcal += Integer.parseInt(List.get(i).get(
						BaseActivity.calories_burned));

			}
			totalmintxt.setText("" + totalmin + " min");// calories_burned
														// calories_burned
			totalcaltxt.setText("" + totalcal + " cal");
			activityListView.setAdapter(activityAdapter);

			com.fitmi.utils.Constants.totalcal = totalcal;

			HomeFragment tosetCalory = new HomeFragment();
			NotificationCaloryBurn callBack = (NotificationCaloryBurn) tosetCalory;

			callBack.setTotalCaloryBurn(com.fitmi.utils.Constants.totalcal);
		} else {

			totalcaltxt.setText("0 cal");
			activityListView.setAdapter(activityAdapter);
		}

		setActivitySpinner();
	}

	@OnClick(R.id.logBtn)
	public void logClick() {

		InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		in.hideSoftInputFromWindow(searchEditText.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
		getActivity().onBackPressed();
	}

	@OnClick(R.id.backLiner)
	public void back() {

		InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		in.hideSoftInputFromWindow(searchEditText.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
		getActivity().onBackPressed();

	}

	@OnClick(R.id.layoutDailyCalory)
	public void clickDailyCalory() {
		com.fitmi.utils.Constants.fromFragment = 2;
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

	@OnClick(R.id.Settings)
	public void gotoSettings() {

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
	public void clickMyExercise() {
		hideRecentDetails();
		List.clear();
		List = ActivityModule.get_fitmi_exercise_log(databaseObject);
		setAdapter();
		activityAdapter.notifyDataSetChanged();
	}

	@OnClick(R.id.imgVoice)
	public void clickVoiceSearch() {
		promptSpeechInput();
	}

	@OnClick(R.id.RecentActivities)
	public void clickRecentActivities() {

		showRecentDetails();

		deselectRecentTabs();
		recentActivities
				.setBackgroundResource(R.color.home_navyblue_select_dark);
		ListRecent.clear();
		CalenderFirsLastDay firstLastDayObj = firstLastDayDate
				.getMonthFirstDateLastDay(stringDate);
		ListRecent = ActivityModule.recentActivity(databaseObject,
				firstLastDayObj);
		sACTIVITY_POS = -1;
		// activityAdapter.notifyDataSetChanged();
		recentActivityList();
	}

	@OnClick(R.id.MyActivities)
	public void clickMyActivities() {

		showRecentDetails();
		deselectRecentTabs();
		myActivity.setBackgroundResource(R.color.home_navyblue_select_dark);
		ListRecent.clear();
		ListRecent = ActivityModule.myActivityData(databaseObject);

		sACTIVITY_POS = -1;
		// activityAdapter.notifyDataSetChanged();
		recentActivityList();
	}

	public void recentActivityList() {
		activityAdapter = new ActivitiesAdapter(getActivity(), ListRecent,
				totalmintxt);
		Recent_Activity_ListView.setAdapter(activityAdapter);
	}

	@OnClick(R.id.Base_Header)
	public void clickHeaderBase() {

	}

	private void showRecentDetails() {

		ListBreakfastLinear.setVisibility(View.GONE);
		TotalFrame_FoodLogging.setVisibility(View.GONE);

		searchEditText.setFocusable(false);
		searchEditText.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
		searchEditText.setClickable(false);
		/*
		 * if(!animationShow){ Animation bottomUp =
		 * AnimationUtils.loadAnimation(getActivity(), R.anim.bottom_up);
		 * RecentListView_ParentLinear.startAnimation(bottomUp); }
		 */

		// RecentListView_ParentLinear.setVisibility(View.VISIBLE);
		favContainer.setVisibility(View.VISIBLE);

		flingUpDown = true;

	}

	private void hideRecentDetails() {

		ListBreakfastLinear.setVisibility(View.VISIBLE);
		TotalFrame_FoodLogging.setVisibility(View.VISIBLE);

		searchEditText.setFocusable(true);
		searchEditText.setFocusableInTouchMode(true); // user touches widget on phone with touch screen
		searchEditText.setClickable(true);
		//added by avinash
		List.clear();
		List = ActivityModule.get_fitmi_exercise_log(databaseObject);
		// setAdapter();
		activityAdapter.notifyDataSetChanged();
		LayoutParams buttonparams = new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		// linerBottomItem.setLayoutParams(buttonparams);

		/*
		 * Animation bottomDown = AnimationUtils.loadAnimation(getActivity(),
		 * R.anim.bottom_down);
		 * RecentListView_ParentLinear.startAnimation(bottomDown);
		 * RecentListView_ParentLinear.setVisibility(View.GONE);
		 */

		favContainer.setVisibility(View.GONE);

		flingUpDown = false;
	}

	private void deselectRecentTabs() {

		recentActivities
				.setBackgroundResource(R.color.home_navyblue_select_light);
		myActivity.setBackgroundResource(R.color.home_navyblue_select_light);

	}

	@OnClick(R.id.Date)
	public void changeDate() {

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.add(R.id.root_home_frame, new CalendarFragment(),
				"CalendarFragment");
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

	/*private class PlacesAutoCompleteAdapter extends ArrayAdapter<String>
			implements Filterable {
		private ArrayList<String> resultList;

		public PlacesAutoCompleteAdapter(Context context, int textViewResourceId) {
			super(context, textViewResourceId);
		}

		@Override
		public int getCount() {
			return resultList.size();
		}

		@Override
		public String getItem(int index) {
			return resultList.get(index);
		}

		@Override
		public Filter getFilter() {
			Filter filter = new Filter() {
				@Override
				protected FilterResults performFiltering(CharSequence constraint) {
					FilterResults filterResults = new FilterResults();
					if (constraint != null) {
						// Retrieve the autocomplete results.
						try{
							if(resultList.size()>0){
							resultList.clear();}
							}catch(Exception e){
								e.printStackTrace();
							}
						resultList = autocomplete(constraint.toString());

						// Assign the data to the FilterResults
						filterResults.values = resultList;
						filterResults.count = resultList.size();
					}
					return filterResults;
				}

				@Override
				protected void publishResults(CharSequence constraint,
						FilterResults results) {
					if (results != null && results.count > 0) {
						notifyDataSetChanged();
					} else {
						notifyDataSetInvalidated();
					}
				}
			};
			return filter;
		}
	}
*/
	
	
	
	private class PlacesAutoCompleteAdapter extends BaseAdapter  {
		// private ArrayList<String> resultList;

		//ArrayList<FitmiFoodDAO> resultList = new ArrayList<FitmiFoodDAO>(searchList);
		float nfgram = 0;
		Context context;

		public PlacesAutoCompleteAdapter(Context context) {
			// super(context, textViewResourceId);

			this.context = context;
		}

		@Override
		public int getCount() {
			return searchList.size();
		}

		@Override
		public HashMap<String, String> getItem(int index) {
			return searchList.get(index);
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
			
	/*		resultList.add(predsJsonArray.getJSONObject(i).getString(
					BaseActivity.exercise)
					+ "  "
					+ predsJsonArray.getJSONObject(i).getString(
							BaseActivity.cals_per_hour) + " calories");*/
			try {
				HashMap<String, String> object = searchList.get(position);
				// holder = (ViewHolder) convertView.getTag();
				holder.txtName = (TextView) convertView
						.findViewById(R.id.txtName);
				
				holder.txtName.setText(object.get(
						BaseActivity.exercise_name)
						+ "  "
						+ object.get(
								BaseActivity.cals_per_hour) + " calories");
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
	
	
/*	private ArrayList<String> autocomplete(String input) {
		ArrayList<String> resultList = null;

		String typeText = input;
		String SetServerString = "";
		HttpURLConnection conn = null;
		StringBuilder jsonResults = new StringBuilder();
		try {// https://service.livestrong.com/service/fitness/exercises/?query=walking&limit=5&fill=fitness_id,exercise,description,cals_per_hour,images
			StringBuilder sb = new StringBuilder(
					"https://service.livestrong.com/service/fitness/exercises/?query="
							+ typeText
							+ "&limit=5&fill=fitness_id,exercise,description,cals_per_hour,images");
			// typeText
			// "https://service.livestrong.com/service/food/foods/?query="+typeText+"&limit=5&fill=item,item_title,cals,serving_size,item_desc,images"
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
			JSONObject jsonObj = new JSONObject(SetServerString);
			JSONArray predsJsonArray = jsonObj.getJSONArray("exercises");

			System.out.println(predsJsonArray.toString());

			if (searchList.size() > 0)
				searchList.clear();

			// Extract the Place descriptions from the results

			resultList = new ArrayList<String>(predsJsonArray.length());

			for (int i = 0; i < predsJsonArray.length(); i++) {
				JSONObject jobj = predsJsonArray.getJSONObject(i);
				HashMap<String, String> map = new HashMap<>();
				map.put(BaseActivity.livestrong_id,
						jobj.getString("fitness_id"));
				map.put(BaseActivity.exercise_name,
						jobj.getString(BaseActivity.exercise));
				map.put(BaseActivity.description,
						jobj.getString(BaseActivity.description));
				// map.put(BaseActivity.cals_per_hour,
				// jobj.getString(BaseActivity.cals_per_hour));
				map.put(BaseActivity.calories_burned,
						jobj.getString(BaseActivity.cals_per_hour));
				// in data base access using cals_per_hour avinash
				map.put(BaseActivity.cals_per_hour,
						jobj.getString(BaseActivity.cals_per_hour));
				searchList.add(map);

				resultList.add(predsJsonArray.getJSONObject(i).getString(
						BaseActivity.exercise)
						+ "  "
						+ predsJsonArray.getJSONObject(i).getString(
								BaseActivity.cals_per_hour) + " calories");

			}
			//avinash
			
		} catch (JSONException e) {
			Log.e("LOG TAG", "Cannot process JSON results", e);
		}

		
		return resultList;
	}*/

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
					Constants.CONNECTION_TIMED_OUT);

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

	
	
	
	private class AsyncAutocompleteActivity extends AsyncTask<String, Void, String> {
		   
		
		   String input;
		   public AsyncAutocompleteActivity(String input) {
			// TODO Auto-generated constructor stub
			   
			   this.input=input; 
		}
		   @Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
	//		   pDialog.setMessage("Loading..."); pDialog.show();
			   
			   super.onPreExecute();
		}
		    @Override
		    protected String doInBackground(String... urls) {
		    	// placesAutoCompleteAdapter.notifyDataSetInvalidated();
				
			String typeText = input;
					String SetServerString = "";
					HttpURLConnection conn = null;
					// StringBuilder jsonResults = new StringBuilder();
					if(input.length()>0){
					try {
				
						typeText = typeText.replaceAll(" ", "%20");
					Log.e("Input Key",typeText);
					
					StringBuilder sb = new StringBuilder(
							"https://service.livestrong.com/service/fitness/exercises/?query="
									+ typeText
									+ "&limit=5&fill=fitness_id,exercise,description,cals_per_hour,images");
			/*			StringBuilder sb = new StringBuilder(URLEncoder.encode("https://api.nutritionix.com/v2/autocomplete?q="
								+ typeText
								+ "&appId=b65138b3&appKey=220fa746203ea5217abff5970c9f8d43", "utf-8"));*/

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
		    	if(result.length()>0){

					try {
						// Create a JSON object hierarchy from the results
						JSONObject jsonObj = new JSONObject(result);
						Log.e("PREDICTIONS", jsonObj.toString());
						JSONArray predsJsonArray = jsonObj.getJSONArray("exercises");

						System.out.println(predsJsonArray.toString());

						if (searchList.size() > 0)
							searchList.clear();

						// Extract the Place descriptions from the results

						

						for (int i = 0; i < predsJsonArray.length(); i++) {
							JSONObject jobj = predsJsonArray.getJSONObject(i);
							HashMap<String, String> map = new HashMap<>();
							map.put(BaseActivity.livestrong_id,
									jobj.getString("fitness_id"));
							map.put(BaseActivity.exercise_name,
									jobj.getString(BaseActivity.exercise));
							map.put(BaseActivity.description,
									jobj.getString(BaseActivity.description));
							// map.put(BaseActivity.cals_per_hour,
							// jobj.getString(BaseActivity.cals_per_hour));
							map.put(BaseActivity.calories_burned,
									jobj.getString(BaseActivity.cals_per_hour));
							// in data base access using cals_per_hour avinash
							map.put(BaseActivity.cals_per_hour,
									jobj.getString(BaseActivity.cals_per_hour));
							searchList.add(map);

				/*			resultList.add(predsJsonArray.getJSONObject(i).getString(
									BaseActivity.exercise)
									+ "  "
									+ predsJsonArray.getJSONObject(i).getString(
											BaseActivity.cals_per_hour) + " calories");*/

						
					}
				} catch (JSONException e) {
					Log.e("LOG TAG", "Cannot process JSON results", e);
				}
		    	
		    	
		    	
				placesAutoCompleteAdapter.notifyDataSetChanged();
				list_autocomplete.setVisibility(View.VISIBLE);
				activityListView.setVisibility(View.GONE);
				
			 
		    }
		    }
	
		  }
	
	
	
	
	
	
	
	
	/**
	 * Dialog delete item
	 */

	public void dialogDeleteItem(final int position, final String itemId) {

		final Dialog dialog = new Dialog(getActivity()/* ,R.style.Theme_Dialog */);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_delete_item);

		dialog.findViewById(R.id.savebtn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						List.remove(position);
						ActivityModule.deleteActivityLogItem(databaseObject,
								itemId);
						setAdapter();
						// activityAdapter.notifyDataSetChanged();
						sACTIVITY_POS = -1;
						dialog.dismiss();
					}
				});
		dialog.findViewById(R.id.cancelbtn).setOnClickListener(
				new View.OnClickListener() {
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

	public void deleteActivityItem(final int position, final String itemId)
	{
		List.remove(position);
		ActivityModule.deleteActivityLogItem(databaseObject,
				itemId);
		setAdapter();
		// activityAdapter.notifyDataSetChanged();
		sACTIVITY_POS = -1;
	}
	
	
	
	
	
	
	public void editActivityItem(final int position, final String itemId) {
		final Dialog dialog = new Dialog(getActivity()/* ,R.style.Theme_Dialog */);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_edit_calory);
		final EditText newCalory = (EditText) dialog
				.findViewById(R.id.newCalory);

		TextView dialogTitle = (TextView) dialog.findViewById(R.id.dialogTitle);
		dialogTitle
				.setText("Enter total hours of exercise you have completed ");
		newCalory.setHint("Enter amount of hours");
		dialog.findViewById(R.id.savebtn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						if (newCalory.getText().toString().equalsIgnoreCase("")) {
							Toast.makeText(getActivity(), "Enter time",
									Toast.LENGTH_LONG).show();
							return;
						}

						String _hour = newCalory.getText().toString().trim();

						if (log) {

							/*
							 * map.put(BaseActivity.livestrong_id,
							 * jobj.getString("fitness_id"));
							 * map.put(BaseActivity.exercise_name,
							 * jobj.getString(BaseActivity.exercise));
							 * map.put(BaseActivity.description,
							 * jobj.getString(BaseActivity.description));
							 * //map.put(BaseActivity.cals_per_hour,
							 * jobj.getString(BaseActivity.cals_per_hour));
							 * map.put(BaseActivity.calories_burned,
							 * jobj.getString(BaseActivity.cals_per_hour));
							 */
							calorie_per_h = Integer
									.parseInt(List.get(position).get(
											BaseActivity.calories_burned));

							int final_calorie = calorie_per_h
									* Integer.parseInt(_hour);
							String minute = String.valueOf(Integer
									.parseInt(_hour) * 60);

							HashMap<String, String> mapList = List
									.get(position);

							HashMap<String, String> map = new HashMap<String, String>();

							map.put(BaseActivity.exercise_name,
									mapList.get("exercise_name"));
							map.put(BaseActivity.description,
									mapList.get("description"));

							map.put(BaseActivity.calories_burned,
									String.valueOf(final_calorie));
							map.put(BaseActivity.id, itemId);
							// map.put(BaseActivity.cals_per_hour,
							// cur.getString(cur.getColumnIndex(BaseActivity.cals_per_hour)));
							map.put(BaseActivity.total_time_minutes, minute);

							List.add(map);
							com.fitmi.utils.Constants.activityLogData.add(List);
						} else {

						
							calorie_per_h = Integer
									.parseInt(List.get(position).get(
											BaseActivity.calories_burned));

							int final_calorie = calorie_per_h
									* Integer.parseInt(_hour);
							String minute = String.valueOf(Integer
									.parseInt(_hour) * 60);

							List.get(position).put(
									BaseActivity.calories_burned,
									String.valueOf(final_calorie));
							
						/*	ActivityModule.execiseloginsertion(databaseObject,
									searchList.get(position), rowid, minute);*/
							
							ActivityModule.editActivityTime(minute, databaseObject, Integer.parseInt(itemId));
							System.out.println("rowid=" + itemId);

							List.clear();
							List = ActivityModule
									.get_fitmi_exercise_log(databaseObject);
						}
						setAdapter();
						activityAdapter.notifyDataSetChanged();

						dialog.dismiss();

					}
				});
		dialog.findViewById(R.id.cancelbtn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

		dialog.show();
		DisplayMetrics metrics = getActivity().getResources()
				.getDisplayMetrics();
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
		dialog.getWindow()
				.setLayout((6 * width) / 7, LayoutParams.WRAP_CONTENT);

	}
	/**
	 * Dialog add item
	 */
//changesleft
	public void dialogAddItem(final HashMap<String, String> selectItem) {

		final Dialog dialog = new Dialog(getActivity()/* ,R.style.Theme_Dialog */);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_add_item);

		dialog.findViewById(R.id.savebtn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						//changes bya avinash... if already getting excercise id then why we have to access it in excercise table
/*						long rowid = ActivityModule.execiseinsertion(
								databaseObject, selectItem);
						ActivityModule.execiseloginsertion(databaseObject,
								selectItem, rowid, selectItem.get("calories_burned"));
*/
						long rowid = Long.parseLong(selectItem.get(BaseActivity.exercise_id));
						ActivityModule.execiseloginsertion(databaseObject,
								selectItem, rowid, selectItem.get(BaseActivity.total_time_minutes));
						dialog.dismiss();
					}
				});
		dialog.findViewById(R.id.cancelbtn).setOnClickListener(
				new View.OnClickListener() {
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
	public void timeUpdate(TextView totalCalory,
			ArrayList<HashMap<String, String>> List) {
		// TODO Auto-generated method stub

		if (List.size() > 0) {
			int totalmin = 0;
			for (int i = 0; i < List.size(); i++) {
				totalmin += Integer.parseInt(List.get(i).get(
						BaseActivity.total_time_minutes));
			}
			totalCalory.setText("" + totalmin + " min");

		}

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		/*
		 * if (com.fitmi.utils.Constants.sDate.length() == 0) {
		 * //Constants.sDate = "Tuesday, February 10, 2015";
		 * 
		 * Calendar c = Calendar.getInstance();
		 * System.out.println("Current time => " + c.getTime()); //
		 * SimpleDateFormat df = new SimpleDateFormat("YYYY-MMM-dd hh:mm:ss");
		 * SimpleDateFormat df = new SimpleDateFormat("EEEE, MMM dd, yyyy");
		 * com.fitmi.utils.Constants.sDate = df.format(c.getTime());
		 * 
		 * stringDate = formattedDate;
		 * 
		 * SimpleDateFormat postFormat = new
		 * SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		 * com.fitmi.utils.Constants.postDate = postFormat.format(c.getTime());
		 * 
		 * }
		 */

		/*
		 * Calendar c = Calendar.getInstance(); SimpleDateFormat targetFormatter
		 * = new SimpleDateFormat( "EEEE, MMM dd, yyyy", Locale.ENGLISH); String
		 * formattedDate = targetFormatter.format(c.getTime());
		 * dateTextView.setText(formattedDate); stringDate = formattedDate;
		 * 
		 * 
		 * 
		 * dateTextView.setText(com.fitmi.utils.Constants.sDate);
		 */

		// changed by avinash and saikat da
		/*Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());
		// SimpleDateFormat df = new SimpleDateFormat("YYYY-MMM-dd hh:mm:ss");
		SimpleDateFormat df = new SimpleDateFormat("EEEE, MMM dd, yyyy");
		com.fitmi.utils.Constants.sDate = df.format(c.getTime());
		SimpleDateFormat postFormat = new SimpleDateFormat(
				"yyyy-MM-dd kk:mm:ss");
		com.fitmi.utils.Constants.postDate = postFormat.format(c.getTime());*/
		if (com.fitmi.utils.Constants.sTempDate.length() == 0) {
			//Constants.sDate = "Tuesday, February 10, 2015";

			Calendar c = Calendar.getInstance();
			System.out.println("Current time => " + c.getTime());	
			//	SimpleDateFormat df = new SimpleDateFormat("YYYY-MMM-dd hh:mm:ss");
			//SimpleDateFormat df = new SimpleDateFormat("EEEE, MMM dd, yyyy");
			SimpleDateFormat df = new SimpleDateFormat("EEEE, MMM dd, yyyy");
			com.fitmi.utils.Constants.sDate = df.format(c.getTime());	


			SimpleDateFormat postFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");			
			com.fitmi.utils.Constants.postDate = postFormat.format(c.getTime());


			SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
			com.fitmi.utils.Constants.conditionDate = dformat.format(c.getTime());
			System.out.println("Calender post format :"+com.fitmi.utils.Constants.postDate); 
			//Toast.makeText(getActivity(), Constants.postDate, Toast.LENGTH_LONG).show();
			
		}
		else{
			today=	com.fitmi.utils.Constants.sTempDate;
		}
		dateTextView.setText(com.fitmi.utils.Constants.sDate);
	}

	BroadcastReceiver dateSetReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			Bundle bundleBroadcast = intent.getExtras();
			dateTextView.setText(bundleBroadcast.getString("key"));

			List.clear();
			List = ActivityModule.get_fitmi_exercise_log(databaseObject);
			// setAdapter();
			activityAdapter.notifyDataSetChanged();

		}
	};

	@OnClick(R.id.txtPreview)
	public void datePreview() {
		String setDate = dateTextView.getText().toString().trim();
		setDate = dateModule.datePreview(setDate);
		dateTextView.setText(setDate);
		// changed by avinash and saikat da
		// com.fitmi.utils.Constants.conditionDate =
		// dateModule.conditionDateFormat(setDate);
		com.fitmi.utils.Constants.conditionDate = getDate
				.conditionDateFormat(setDate);
		com.fitmi.utils.Constants.postDate = getDate
				.getFormatDateSearchInsert(setDate);
		com.fitmi.utils.Constants.sTempDate=com.fitmi.utils.Constants.conditionDate;
		com.fitmi.utils.Constants.sDate=getDate.sDateFormat(setDate);

		stringDate = setDate;

		List.clear();
		List = ActivityModule.get_fitmi_exercise_log(databaseObject);
		setAdapter();
		activityAdapter.notifyDataSetChanged();
		// activityAdapter.notifyDataSetChanged();
		setFoodSpinner();

		// Toast.makeText(getActivity(), setDate, Toast.LENGTH_LONG).show();
	}

	@OnClick(R.id.txtNext)
	public void dateNext() {
		String setDate = dateTextView.getText().toString().trim();
		setDate = dateModule.dateNext(setDate);
		dateTextView.setText(setDate);
		// changed by avinash and saikat da
		// com.fitmi.utils.Constants.conditionDate =
		// dateModule.conditionDateFormat(setDate);
		com.fitmi.utils.Constants.conditionDate = getDate
				.conditionDateFormat(setDate);
		com.fitmi.utils.Constants.postDate = getDate
				.getFormatDateSearchInsert(setDate);
		com.fitmi.utils.Constants.sTempDate=com.fitmi.utils.Constants.conditionDate;
		com.fitmi.utils.Constants.sDate=getDate.sDateFormat(setDate);
		stringDate = setDate;
		List.clear();
		List = ActivityModule.get_fitmi_exercise_log(databaseObject);
		setAdapter();
		activityAdapter.notifyDataSetChanged();
		// activityAdapter.notifyDataSetChanged();
		setFoodSpinner();
		// Toast.makeText(getActivity(), setDate, Toast.LENGTH_LONG).show();
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

		switch (requestCode) {
		case REQ_CODE_SPEECH_INPUT: {
			if (resultCode == getActivity().RESULT_OK && null != data) {

				ArrayList<String> result = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				searchEditText.setText(result.get(0));
			}
			break;
		}

		}
	}

	public void setFoodSpinner() {
		caloryCalculate = 0;
		/*
		 * totalCaloty =
		 * ActivityModule.todayTotalCaloryBurn(com.fitmi.utils.Constants
		 * .conditionDate,databaseObject); if(totalCaloty==null)
		 * activity_calorie_text.setText("0"); else
		 * activity_calorie_text.setText(totalCaloty);
		 */

		caloryInTake = com.fitmi.utils.Constants.TOTAL_CALORY_INTAKE;
		calorySumList = FoodLoginModule.totalCalory(databaseObject,getActivity());

		foodListData = foodLogObj.selectAllFoodList(databaseObject);

		for (int i = 0; i < foodListData.size(); i++)
			caloryCalculate += Float
					.parseFloat(foodListData.get(i).getCalory());

		food_calorie_text.setText((int) caloryCalculate + "");

		remainCalory = (int) (Float.parseFloat(caloryInTake) - caloryCalculate);

		dailyCaloryIntake.setText(caloryInTake);

		foodValues = new String[5];

		if (remainCalory < 0) {

			tvRemainCalory.setText("0");
		} else {
			tvRemainCalory.setText(remainCalory + "");
		}
		foodValues[0] = (int) caloryCalculate + "";

		for (int i = 0; i < 4; i++) {
			if (calorySumList.get(i) != null)
				foodValues[i + 1] = calorySumList.get(i);
			else
				foodValues[i + 1] = "0";
		}

		adapter = new ArrayAdapter<String>(getActivity(),
				R.layout.spinner_item, foodValues);

		flsa = new FoodLoggingSpinnerAdapter(getActivity(), foodValues,
				fooddrawableValues, R.drawable.circle_green);
		adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

		foodLoggingSpinner.setAdapter(flsa);

		foodDropClick = false;

	}

	public void setActivitySpinner() {

		totalCaloty = ActivityModule.todayTotalCaloryBurn(
				com.fitmi.utils.Constants.conditionDate, databaseObject,getActivity());

		caloryBurnList = ActivityModule.totalCaloryBurn(databaseObject);
		activityValues = new String[caloryBurnList.size() + 1];
		activityValuesId= new String[caloryBurnList.size() + 1];
		activitydrawableValuesAlies = new Integer[caloryBurnList.size() + 1];
		activityType = new char[caloryBurnList.size() + 1];

		if (totalCaloty == null) {
			activity_calorie_text.setText("0");
			activityValues[0] = "0";
			activityValuesId[0]="0";
		} else {
			activity_calorie_text.setText(totalCaloty);
			activityValues[0] = totalCaloty + "";
			activityValuesId[0]="0";
		}

		activitydrawableValuesAlies[0] = activitydrawableValues[0];

		for (int i = 0; i < caloryBurnList.size(); i++) {
			if (caloryBurnList.get(i) != null) {

				ExerciseItemDAO obj = caloryBurnList.get(i);
				
				activityValues[i + 1] = obj.getExerciseSum();
				activityValuesId[i + 1]=String.valueOf(obj.getExerciseId());

				if (obj.getExerciseName().equalsIgnoreCase("Weight lifting")) {

					activitydrawableValuesAlies[i + 1] = activitydrawableValues[6];
					

				} else if (obj.getExerciseName().equalsIgnoreCase(
						"Boxing - general")) {

					activitydrawableValuesAlies[i + 1] = activitydrawableValues[5];
					

				} else if (obj.getExerciseName().equalsIgnoreCase(
						"Jumping rope")) {

					activitydrawableValuesAlies[i + 1] = activitydrawableValues[4];
					

				} else if (obj.getExerciseName().equalsIgnoreCase("Swimming")) {

					activitydrawableValuesAlies[i + 1] = activitydrawableValues[3];
					

				} else if (obj.getExerciseName().equalsIgnoreCase("Walking")) {

					activitydrawableValuesAlies[i + 1] = activitydrawableValues[2];
					

				} else if (obj.getExerciseName().equalsIgnoreCase("Gymnastics")) {

					activitydrawableValuesAlies[i + 1] = activitydrawableValues[1];
					
				}
			}/* else
			{
				activityValues[i + 1] = "0";
			activityType[i + 1] = 'A';
			}*/
		}

		ActivityLoggingSpinnerAdapter flsa1 = new ActivityLoggingSpinnerAdapter(
				getActivity(), activityValues, activitydrawableValuesAlies,
				R.drawable.circle_pink);
		activitySpinner.setAdapter(flsa1);
		activityDropClick = false;
	}

	class GestureListener extends GestureDetector.SimpleOnGestureListener {

		// Override s all the callback methods of
		// GestureDetector.SimpleOnGestureListener
		@Override
		public boolean onSingleTapUp(MotionEvent ev) {

			 Toast.makeText(getActivity(),"onSingleTapUp",Toast.LENGTH_LONG).show();

			 clickMyActivities();
			return true;
		}

		@Override
		public void onShowPress(MotionEvent ev) {

		}

		@Override
		public void onLongPress(MotionEvent ev) {

		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {

			return true;
		}

		@Override
		public boolean onDown(MotionEvent ev) {

			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {

			if (flingUpDown) {
				// clickMyExercise();
				hideRecentDetails();
				// clickMyActivities();
				animationShow = false;

			} else {
				showRecentDetails();
				// clickMyActivities();
				clickRecentActivities();

				animationShow = true;
			}

			return true;
		}
	}

	@Override
	public void setNewCaloryIntake(String calorie) {
		// TODO Auto-generated method stub
		com.fitmi.utils.Constants.homeCaloryIntake.setText(calorie);
		String calorieTotal = com.fitmi.utils.Constants.foodcalorieText
				.getText().toString();
		int remainCalory = (Integer.parseInt(calorie) - Integer
				.parseInt(calorieTotal));
		com.fitmi.utils.Constants.remainCaloryBurn.setText(remainCalory + "");
	}

	/**
	 * Enter time dialog
	 */

	public void dialog(final int position) {
		final Dialog dialog = new Dialog(getActivity()/* ,R.style.Theme_Dialog */);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_edit_calory);
		final EditText newCalory = (EditText) dialog
				.findViewById(R.id.newCalory);

		TextView dialogTitle = (TextView) dialog.findViewById(R.id.dialogTitle);
		dialogTitle
				.setText("Enter total hours of exercise you have completed ");
		newCalory.setHint("Enter amount of hours");
		dialog.findViewById(R.id.savebtn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						if (newCalory.getText().toString().equalsIgnoreCase("")) {
							Toast.makeText(getActivity(), "Enter time",
									Toast.LENGTH_LONG).show();
							return;
						}

						String _hour = newCalory.getText().toString().trim();

						if (log) {

							/*
							 * map.put(BaseActivity.livestrong_id,
							 * jobj.getString("fitness_id"));
							 * map.put(BaseActivity.exercise_name,
							 * jobj.getString(BaseActivity.exercise));
							 * map.put(BaseActivity.description,
							 * jobj.getString(BaseActivity.description));
							 * //map.put(BaseActivity.cals_per_hour,
							 * jobj.getString(BaseActivity.cals_per_hour));
							 * map.put(BaseActivity.calories_burned,
							 * jobj.getString(BaseActivity.cals_per_hour));
							 */
							calorie_per_h = Integer
									.parseInt(searchList.get(position).get(
											BaseActivity.calories_burned));

							int final_calorie = calorie_per_h
									* Integer.parseInt(_hour);
							String minute = String.valueOf(Integer
									.parseInt(_hour) * 60);

							HashMap<String, String> mapList = searchList
									.get(position);

							HashMap<String, String> map = new HashMap<String, String>();

							map.put(BaseActivity.exercise_name,
									mapList.get("exercise_name"));
							map.put(BaseActivity.description,
									mapList.get("description"));

							map.put(BaseActivity.calories_burned,
									String.valueOf(final_calorie));
							map.put(BaseActivity.id, "1");
							// map.put(BaseActivity.cals_per_hour,
							// cur.getString(cur.getColumnIndex(BaseActivity.cals_per_hour)));
							map.put(BaseActivity.total_time_minutes, minute);

							List.add(map);
							com.fitmi.utils.Constants.activityLogData.add(List);
						} else {

							long rowid = ActivityModule.execiseinsertion(
									databaseObject, searchList.get(position));
							if (rowid == -1) {
								rowid = Long
										.parseLong(BaseActivity.__activityId);
							}

							calorie_per_h = Integer
									.parseInt(searchList.get(position).get(
											BaseActivity.calories_burned));

							int final_calorie = calorie_per_h
									* Integer.parseInt(_hour);
							String minute = String.valueOf(Integer
									.parseInt(_hour) * 60);

							searchList.get(position).put(
									BaseActivity.calories_burned,
									String.valueOf(final_calorie));
							ActivityModule.execiseloginsertion(databaseObject,
									searchList.get(position), rowid, minute);
							System.out.println("rowid=" + rowid);

							List.clear();
							List = ActivityModule
									.get_fitmi_exercise_log(databaseObject);
						}
						setAdapter();
						activityAdapter.notifyDataSetChanged();

						dialog.dismiss();

					}
				});
		dialog.findViewById(R.id.cancelbtn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

		dialog.show();
		DisplayMetrics metrics = getActivity().getResources()
				.getDisplayMetrics();
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
		dialog.getWindow()
				.setLayout((6 * width) / 7, LayoutParams.WRAP_CONTENT);

	}
	
	// new style listview
		private int dp2px(int dp) {
			return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
					getResources().getDisplayMetrics());
		}
		
		
		   @Override
			public void onPause() {
				// TODO Auto-generated method stub
				   try{
				   getActivity().unregisterReceiver(dateSetReceiver);
				   }catch (Exception a)
				   {
					   a.printStackTrace();
				   }
				super.onPause();
			}
}
