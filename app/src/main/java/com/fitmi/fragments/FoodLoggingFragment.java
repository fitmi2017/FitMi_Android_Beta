package com.fitmi.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.speech.RecognizerIntent;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
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
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.callback.ChangeFoodContents;
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
import com.fitmi.R;
import com.fitmi.activitys.TabActivity;
import com.fitmi.adapter.ActivityLoggingSpinnerAdapter;
import com.fitmi.adapter.EntryFoodLoginAdapter;
import com.fitmi.adapter.FavFoodAdapter;
import com.fitmi.adapter.FoodAdapter;
import com.fitmi.adapter.FoodLoggingFavSpinnerAdapter;
import com.fitmi.adapter.FoodLoggingRecentSpinnerAdapter;
import com.fitmi.adapter.FoodLoggingSpinnerAdapter;
import com.fitmi.adapter.NutritionAdapter;
import com.fitmi.adapter.NutritionAdapterBoldText;
import com.fitmi.adapter.TotalFoodFooterAdapter;
import com.fitmi.dao.ExerciseItemDAO;
import com.fitmi.dao.FitmiFoodDAO;
import com.fitmi.dao.FitmiFoodLogDAO;
import com.fitmi.dao.MealTypeDAO;
import com.fitmi.dao.NutritionTypeSetget;
import com.fitmi.dao.SectionItemFoodlogin;
import com.fitmi.dao.UnitItemDAO;
import com.fitmi.dao.UserInfoDAO;
import com.fitmi.utils.Constants;
import com.fitmi.utils.DateModule;
import com.fitmi.utils.NotifyCalorieChange;
import com.fitmi.utils.SaveSharedPreferences;
import com.fitmi.utils.interFragmentScaleCommunicator;
import com.ssl.MySSLSocketFactory;

import org.apache.commons.lang3.text.WordUtils;
import org.apache.http.HttpVersion;
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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import it.sephiroth.demo.slider.widget.MultiDirectionSlidingDrawer;
import it.sephiroth.demo.slider.widget.MultiDirectionSlidingDrawer.OnDrawerCloseListener;
import it.sephiroth.demo.slider.widget.MultiDirectionSlidingDrawer.OnDrawerOpenListener;

@SuppressWarnings("deprecation")
@SuppressLint({"SimpleDateFormat", "SetTextI18n"})
public class FoodLoggingFragment extends BaseFragment implements ChangeFoodContents,
        NotifyCalorieChange, NotificationCalorieIntake, MealFavNotify {

    private String data_mode[] = new String[]{"Cal", "Pro", "Car", "Fat", "Sod", "Cho"};

    ArrayList<FitmiFoodDAO> searchList1 = new ArrayList<>();

    @InjectView(R.id.inputSearch)
    EditText inputSearch;

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

    ArrayList<NutritionTypeSetget> nutritionTypeDAOs = new ArrayList<>();

    String _food_name, _nf_calories, _serving_weight_grams, _nf_total_fat,
            _nf_saturated_fat, _nf_cholesterol, _nf_sodium,
            _nf_total_carbohydrate, _nf_dietary_fiber, _nf_sugars, _nf_protein,
            _nf_potassium, _nf_p;
    NutritionAdapter nutritionAdapter;
    NutritionAdapterBoldText nutritionAdapterBoldText;

    HashMap<String, String> datamap = new HashMap<>();

    @InjectView(R.id.imgViewSnack)
    ImageView snacksImg;
    @InjectView(R.id.imgViewBreakfast)
    ImageView breakFastImg;
    @InjectView(R.id.imgViewLunch)
    ImageView lunchImg;
    @InjectView(R.id.imgViewDinner)
    ImageView dinnerImg;

    String mealIdfromTable = "";


    ArrayList<Item> items = new ArrayList<>();
    ArrayList<Item> itemsMeal = new ArrayList<>();

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


    @InjectView(R.id.ActivitySpinner)
    Spinner activitySpinner;

    @InjectView(R.id.TotalFrame_FoodLogging)
    LinearLayout total_frame;

    @InjectView(R.id.FoodLoggingListView)
    CustomListView foodLoggingListView;

    @InjectView(R.id.FoodLoggingListView2)
    SwipeMenuListView foodLoggingListView2;

    @InjectView(R.id.listTotalFrame_FoodLogging)
    SwipeMenuListView listTotalFrame_FoodLogging;

    @InjectView(R.id.ListBreakfastLinear)
    LinearLayout listBreakfastLinear;

    @InjectView(R.id.Recent_Food_Meals_Menu)
    LinearLayout recentFoodMealsMenu;

    @InjectView(R.id.Fav_Food_Meals_Menu)
    LinearLayout favFoodMealsMenu;

    @InjectView(R.id.RecentListView_ParentLinear)
    LinearLayout recentListViewParentLinear;

    @InjectView(R.id.Date)
    TextView dateTextView;

    @InjectView(R.id.imgAddMeal)
    ImageView imgAddMeal;

    public static int sFOODLOGGING_POS = -1;
    public static int CLICKRECENTITEM = -1;
    public static int CLICKFAVITEM = -1;

    @InjectView(R.id.intake_linearlayout)
    LinearLayout intake_linearlayout;

    @InjectView(R.id.food_calorie_text)
    TextView food_calorie_text;

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

    @InjectView(R.id.shadowFrame)
    LinearLayout shadowFrame;

    @InjectView(R.id.shadowFrame1)
    LinearLayout shadowFrame1;

    Integer[] fooddrawableValues;
    Integer[] activitydrawableValues;
    Integer[] fooddrawableValuesFav;

    String[] activityValues;
    String[] foodValues;

    int mealId = -1;
    int mealIdSpinner = -1;
    float caloryCalculate = 0;
    float nutritionCalculate = 0;
    String foodType = "Meal";
    boolean replace = false;
    boolean log = false;
    boolean addMealClick = false;
    boolean recentFoodClick = true;
    boolean favFoodClick = false;

    ArrayList<FitmiFoodDAO> searchList;

    DatabaseHelper databaseObject;
    DatabaseHelper databaseObjectForFood;
    DateModule getDate = new DateModule();

    FoodLoginModule foodLogObj;
    FoodLoginModule foodLogObjForfood;

    ArrayList<FitmiFoodLogDAO> foodListData = new ArrayList<>();
    ArrayList<FitmiFoodLogDAO> mealListData;
    ArrayList<FitmiFoodLogDAO> foodListDataAlies = new ArrayList<>();

    TotalFoodFooterAdapter totalFoodFooterAdapter;
    EntryFoodLoginAdapter sectionAdapter;
    FoodAdapter foodAdapter;
    FoodAdapter foodAdapterSearch;

    ProgressDialog pDialog;

    int initListSize = 0;

    boolean newDataAdd = false;

    int newDataCount = 0;

    Bundle bundle;

    ArrayList<ExerciseItemDAO> caloryBurnList;

    private final int REQ_CODE_SPEECH_INPUT = 100;

    String caloryInTake = "";

    int remainCalory = 0;
    String today = "";
    String totalCaloty = "0";
    String currActivewt = "0";
    String currActiveCalwt = "0";
    ArrayList<String> demoSection = new ArrayList<>();

    boolean foodDropClick = false;
    boolean activityDropClick = false;
    FoodLoggingSpinnerAdapter flsa;
    ArrayAdapter<String> adapter;
    Integer[] activitydrawableValuesAlies;
    char[] activityType;
    private String unitType = " cal";

    UnitModule unitModel;

    UnitItemDAO unitDataFood_Weight;
    interFragmentScaleCommunicator scaleCommunicator;

    private Handler scaleSyncHandler;

    Runnable reCheckScaleConnection = new Runnable() {
        @Override
        public void run() {
            if (!scaleCommunicator.isScaleConnected()) {

                if (Constants.CheckSyncFirstTime) {
                    scaleCommunicator.connectDevice();
                    Constants.CheckSyncFirstTime = false;
                } else
                    scaleCommunicator.connectDevice(true);
            }
            int syncCheckInterval = 5000;
            scaleSyncHandler.postDelayed(reCheckScaleConnection, syncCheckInterval);
        }
    };

    void startReCheckScaleConnection() {
        reCheckScaleConnection.run();
    }

    void stopReCheckScaleConnection() {
        scaleSyncHandler.removeCallbacks(reCheckScaleConnection);
    }


    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_logging, container,
                false);
        setNullClickListener(view);
        ButterKnife.inject(this, view);
        heading.setText("My Meal");
        scaleCommunicator = (interFragmentScaleCommunicator) getActivity();
        scaleSyncHandler = new Handler();
        startReCheckScaleConnection();

        if (com.fitmi.utils.Constants.sTempDate.length() == 0) {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("EEEE, MMM dd, yyyy");
            com.fitmi.utils.Constants.sDate = df.format(c.getTime());
            SimpleDateFormat postFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
            com.fitmi.utils.Constants.postDate = postFormat.format(c.getTime());
            SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
            com.fitmi.utils.Constants.conditionDate = dformat.format(c.getTime());
            com.fitmi.utils.Constants.sTempDate = com.fitmi.utils.Constants.conditionDate;
        } else {
            today = com.fitmi.utils.Constants.sTempDate;
        }
        com.fitmi.utils.Constants.conditionDate = today;
        com.fitmi.utils.Constants.homeCaloryIntake = daily_calorie_text;
        com.fitmi.utils.Constants.remainCaloryBurn = remainCaloryBurn;
        com.fitmi.utils.Constants.foodcalorieText = food_calorie_text;
        com.fitmi.utils.Constants.shareIntent = true;
        com.fitmi.utils.Constants.fragmentSet = false;

        final Context context = getActivity();

        if (com.fitmi.utils.Constants.gunitfw == 0) {
            totalgram.setText("0 g");
        } else {
            totalgram.setText("0 oz");
        }

        SharedPreferences prefs = getActivity().getSharedPreferences(SaveSharedPreferences.USER_INFORMATION, getActivity().MODE_PRIVATE);
        int data_mode_index = prefs.getInt("data_mode_index", 0);
        TotalFoodFooterAdapter.food_content_type = data_mode[data_mode_index];

        getActivity().registerReceiver(dateSetReceiver,
                new IntentFilter("foodLogUpdate"));

        com.fitmi.utils.Constants.fragmentNumber = 1;

        databaseObject = new DatabaseHelper(getActivity());
        databaseObjectForFood = new DatabaseHelper(getActivity());
        try {
            databaseObject.createDatabase();
            databaseObject.openDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        pDialog = new ProgressDialog(getActivity());
        foodLogObj = new FoodLoginModule(getActivity());
        foodLogObjForfood = new FoodLoginModule(getActivity());
        bundle = this.getArguments();


        if (bundle != null) {
            mealId = bundle.getInt("mealid", 0);
            foodType = bundle.getString("foodtype");
            replace = bundle.getBoolean("replace");
            log = bundle.getBoolean("log");


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
            tareScaleAndApp();
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

        fooddrawableValues = new Integer[]{R.drawable.food,
                R.drawable.breakfast, R.drawable.lunch, R.drawable.dinner,
                R.drawable.snack};
        fooddrawableValuesFav = new Integer[]{ // R.drawable.food,
                R.drawable.breakfast, R.drawable.lunch, R.drawable.dinner,
                R.drawable.snack};

        activitydrawableValues = new Integer[]{R.drawable.calories_burned,
                R.drawable.chin_ups, R.drawable.treadmill, R.drawable.swimming,
                R.drawable.jumprope, R.drawable.boxing,
                R.drawable.lifting_weight};

        unitModel = new UnitModule(getActivity());

        if (com.fitmi.utils.Constants.gunitfw == 0) {
            com.fitmi.utils.Constants.gm_oz = 0;
        } else {
            com.fitmi.utils.Constants.gm_oz = 1;
        }


        if (mealListData != null && mealListData.size() > 0) {
            setMealAdapter();
        } else {
            setAdapter();
        }
        setFoodSpinner();
        setActivitySpinner();

        ActivityLoggingSpinnerAdapter flsa1 = new ActivityLoggingSpinnerAdapter(
                getActivity(), activityValues, activitydrawableValuesAlies,
                R.drawable.circle_pink);

        activitySpinner.setAdapter(flsa1);
        foodLoggingSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {


                if (foodDropClick) {

                    int mealIdCh = 0;

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

                    mealIdSpinner = mealIdCh;
                    mealId = mealIdCh;

                    if (position == 0) {
                        if (!log) {
                            foodListData = foodLogObj.selectAllFoodList(databaseObject);
                            setFoodlistData(foodListData, true);

                            setFoodSpinner();
                            sectionAdapter = new EntryFoodLoginAdapter(getActivity(), items,
                                    totalCalory, bundle);
                            foodLoggingListView.setAdapter(sectionAdapter);
                        }

                        foodLoggingListView2.setVisibility(View.GONE);
                        selectMealLiner.setVisibility(View.GONE);
                        searchMealLiner.setVisibility(View.GONE);
                        addMealLiner.setVisibility(View.VISIBLE);
                        total_frame.setVisibility(View.GONE);
                        foodLoggingListView.setVisibility(View.VISIBLE);


                    } else {

                        searchListAdapterListview2();
                        selectMealLiner.setVisibility(View.GONE);
                        searchMealLiner.setVisibility(View.VISIBLE);
                        addMealLiner.setVisibility(View.GONE);
                        total_frame.setVisibility(View.VISIBLE);
                    }


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });
        activitySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {


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


            }
        });


        sFOODLOGGING_POS = -1;
        CLICKRECENTITEM = -1;
        CLICKFAVITEM = -1;

        foodTypeTitle.setText("My " + foodType);

        calorieTotalTop.setText((int) nutritionCalculate + " " + TotalFoodFooterAdapter.food_content_type);

        hideRecentDetails();

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem openItem = new SwipeMenuItem(getActivity()
                        .getApplicationContext());
                openItem.setWidth(dp2px(60));
                openItem.setIcon(R.drawable.list_delete);
                openItem.setBackground(R.color.deep_pink);
                menu.addMenuItem(openItem);
                SwipeMenuItem editItem = new SwipeMenuItem(getActivity()
                        .getApplicationContext());
                editItem.setBackground(R.color.deep_yellow);
                editItem.setWidth(dp2px(60));
                editItem.setIcon(R.drawable.total_swipeedit);
                menu.addMenuItem(editItem);
            }
        };

        foodLoggingListView2.setMenuCreator(creator);

        foodLoggingListView2
                .setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(int position,
                                                   SwipeMenu menu, int index) {

                        switch (index) {
                            case 0:
                                dialogDeleteItem(position);
                                break;
                            case 1:

                                editDialog(position);
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

                        try {
                            Log.e("foodLoggingListView2",
                                    String.valueOf(addMealLiner.isShown()));

                            if (!addMealLiner.isShown() && !log) {
                                if (sFOODLOGGING_POS == arg2) {
                                    tareScaleAndApp();
                                    sFOODLOGGING_POS = -1;
                                } else {
                                    if (sFOODLOGGING_POS != -1)
                                        scaleCommunicator.tare();
                                    sFOODLOGGING_POS = arg2;
                                    updateValueTap(sFOODLOGGING_POS);
                                    ((TabActivity) getActivity()).setWeightOnDevice(Integer.parseInt(currActivewt));
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

            }
        };

        listTotalFrame_FoodLogging.setMenuCreator(creator_two);

        listTotalFrame_FoodLogging
                .setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(int position,
                                                   SwipeMenu menu, int index) {

                        switch (index) {
                            case 0:
                                if (com.fitmi.utils.Constants.isSync) {
                                    tareScaleAndApp();
                                } else {
                                    Toast.makeText(context, "Device is Disconnected. Please Sync the device again to communicate with device.", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case 1:
                                changeFoodUnit();
                                break;
                        }
                        return false;
                    }
                });

        foodLoggingListView
                .setOnItemDoubleClickListener(new OnItemDoubleTapLister() {

                    @Override
                    public void OnSingleTap(AdapterView<?> parent, View view,
                                            int position, long id) {

                        try {
                            Log.e("OnSingleTap visbility ",
                                    String.valueOf(addMealLiner.isShown()));
                            if (!addMealLiner.isShown() && !log) {
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
                    }

                    @Override
                    public void OnDoubleTap(AdapterView<?> parent, View view,
                                            int position, long id) {

                        TextView tv = null;

                        Log.e("OnDoubleTap", "OnDoubleTap");

                        if (view != null)
                            tv = (TextView) view
                                    .findViewById(R.id.textSeparator);

                        if (tv != null) {

                            int mealIdCh;
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

                            setMealAdapter();
                            selectMealLiner.setVisibility(View.GONE);
                            searchMealLiner.setVisibility(View.VISIBLE);
                            addMealLiner.setVisibility(View.GONE);
                            total_frame.setVisibility(View.VISIBLE);
                            if (!com.fitmi.utils.Constants.isSync)
                                showSyncdialog();
                            else
                                tareScaleAndApp();
                        }
                    }
                });


        foodLoggingListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

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

                    } else if (headerValue.equalsIgnoreCase("Snack")) {
                        mealIdCh = 4;
                        heading.setText("Snack");
                    }
                    mealIdSpinner = mealIdCh;
                    mealId = mealIdCh;
                    mealListData = foodLogObj.selectFoodList(
                            String.valueOf(mealIdCh), databaseObject);

                    setMealAdapter();
                    selectMealLiner.setVisibility(View.GONE);
                    searchMealLiner.setVisibility(View.VISIBLE);
                    addMealLiner.setVisibility(View.GONE);
                    total_frame.setVisibility(View.VISIBLE);


                    if (!com.fitmi.utils.Constants.isSync)
                        showSyncdialog();

                    getTotalNutrition(mealListData);
                    tareScaleAndApp();

                } else {
                    dialogShowLongclick(i);

                }


            }
        });

        drawer.setOnDrawerCloseListener(new OnDrawerCloseListener() {

            @Override
            public void onDrawerClosed() {


                searchEditText.setFocusable(true);
                searchEditText.setFocusableInTouchMode(true);

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


                searchEditText.setFocusable(false);
                searchEditText.setFocusableInTouchMode(false);

                searchEditText.setClickable(false);
                shadowFrame.setVisibility(View.VISIBLE);
                shadowFrame1.setVisibility(View.VISIBLE);
                arrow.setBackgroundResource(R.drawable.kee_arrow);

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

        placesAutoCompleteAdapter = new PlacesAutoCompleteAdapter(getActivity());
        list_autocomplete.setAdapter(placesAutoCompleteAdapter);

        list_autocomplete.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                try {

                    String path = "https://trackapi.nutritionix.com/v2/natural/nutrients/";
                    JSONObject holder = new JSONObject();

                    String name;
                    String _itemid;

                    name = searchList1.get(position).getItemName();
                    _itemid = searchList1.get(position).getItemId();
                    holder.put("query", name);
                    holder.put("timezone", "US/Eastern");

                    Log.e("Sending value", holder.toString());

                    AsyncNutritionTwo asyncNutritionTwo = new AsyncNutritionTwo(path,
                            holder, _itemid);
                    asyncNutritionTwo.execute("exe");

                } catch (Exception e) {
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

        inputSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                try {
                    if (sFOODLOGGING_POS != -1) {
                        tareScaleAndApp();
                        sFOODLOGGING_POS = -1;
                    }
                    foodAdapter.notifyDataSetChanged();
                    foodAdapterSearch.notifyDataSetChanged();
                } catch (Exception e) {
                }
                return false;
            }
        });
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2,
                                      int arg3) {

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
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });


        inputSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    View view = getActivity().getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
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

                inputSearch.setCursorVisible(true);
            }
        });

        if (!com.fitmi.utils.Constants.isSync && Constants.showSyncFirstTime) {
            Constants.showSyncFirstTime=false;
            showSyncdialog();
        }

        return view;
    }

    public void tareScaleAndApp() {
        currActiveCalwt = "0";
        currActivewt = "0";
        ArrayList<FitmiFoodLogDAO> mealListData1 = foodLogObj.selectFoodList(
                String.valueOf(mealIdSpinner),
                databaseObject);
        getTotalNutrition(mealListData1);
        totalFoodFooterAdapter = new TotalFoodFooterAdapter(
                getActivity(), this, currActivewt,
                currActiveCalwt, String
                .valueOf((int) nutritionCalculate),
                "0");
        listTotalFrame_FoodLogging.setAdapter(totalFoodFooterAdapter);
        totalFoodFooterAdapter.notifyDataSetChanged();
        scaleCommunicator.tare();
    }


    AsyncAutocompleteNutri AsyncAutocompleteNutri;

    @OnClick(R.id.layoutDailyCaloryEdit)
    public void clickDailyCalory() {

        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
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

    @OnClick(R.id.backLiner)
    public void back() {

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
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
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

    @OnClick(R.id.Base_Header)
    public void clickHeaderBase() {

    }


    private void hideRecentDetails() {

        recentListViewParentLinear.setVisibility(View.GONE);
        recentFoodMealsMenu.setVisibility(View.VISIBLE);
        favFoodMealsMenu.setVisibility(View.GONE);
        listBreakfastLinear.setVisibility(View.VISIBLE);
        flingUpDown = false;
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

        super.onResume();


        if (com.fitmi.utils.Constants.sTempDate.length() == 0) {

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


        if (scaleDisConnected != null) {
            IntentFilter intentFilter = new IntentFilter("DeviceDisconnected");
            getActivity().registerReceiver(scaleDisConnected, intentFilter);
        }

        if (currentWeight != null) {
            IntentFilter intentFilter = new IntentFilter("NEW_WEIGHT");
            getActivity().registerReceiver(currentWeight, intentFilter);
        }
        if (currentUnit != null) {
            IntentFilter intentFilter = new IntentFilter("NEW_UNIT");
            getActivity().registerReceiver(currentUnit, intentFilter);
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

    public void setAdapter() {
        caloryCalculate = 0;
        sectionAdapter = new EntryFoodLoginAdapter(getActivity(), items,
                totalCalory, bundle);

        foodLoggingListView.setVisibility(View.VISIBLE);
        foodLoggingListView2.setVisibility(View.GONE);
        foodLoggingListView.setAdapter(sectionAdapter);

        setMelFav(foodListData);

        topTotalCal(foodListData);
        food_calorie_text.setText((int) caloryCalculate + "");

        getTotalNutrition(foodListData);
        calorieTotalTop.setText((int) nutritionCalculate + " " + TotalFoodFooterAdapter.food_content_type);

    }

    void topTotalCal(ArrayList<FitmiFoodLogDAO> list) {
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).getCalory()
                    .equalsIgnoreCase("")) {

                float pergram = Float.parseFloat(list.get(i).getCalory())
                        / Float.parseFloat(list.get(i).getServingSize());
                float cal = Float.parseFloat(list.get(i).getMealWeight())
                        * pergram;
                caloryCalculate += cal;
            }
        }
        caloryCalculate = (float) (caloryCalculate);
    }

    public void setMealAdapter() {

        caloryCalculate = 0;
        foodAdapter = new FoodAdapter(getActivity(), mealListData, this);

        foodLoggingListView.setVisibility(View.GONE);
        foodLoggingListView2.setVisibility(View.VISIBLE);
        foodLoggingListView2.setAdapter(foodAdapter);

        topTotalCal(mealListData);
        food_calorie_text.setText((int) caloryCalculate + "");
        totalCalory.setText((int) caloryCalculate + "" + unitType);

        getTotalNutrition(mealListData);
        calorieTotalTop.setText((int) nutritionCalculate + " " + TotalFoodFooterAdapter.food_content_type);

        try {
            if (mealListData.size() > 0) {
                setMelFav(mealListData);
            } else {
                updateFooterAdapter();
            }
        } catch (Exception e) {
            updateFooterAdapter();
        }
    }


    public void searchListAdapter() {

        caloryCalculate = 0;
        foodAdapterSearch = new FoodAdapter(getActivity(), foodListDataAlies, this);
        foodLoggingListView.setAdapter(foodAdapterSearch);

        topTotalCal(foodListDataAlies);
        food_calorie_text.setText((int) caloryCalculate + "");
        totalCalory.setText((int) caloryCalculate + "" + unitType);

        getTotalNutrition(foodListDataAlies);
        calorieTotalTop.setText((int) nutritionCalculate + " " + TotalFoodFooterAdapter.food_content_type);

        foodAdapterSearch.notifyDataSetChanged();

        setMelFav(foodListDataAlies);
    }

    public void searchListAdapterListview2() {

        foodLoggingListView.setVisibility(View.GONE);
        foodLoggingListView2.setVisibility(View.VISIBLE);

        caloryCalculate = 0;
        foodAdapter = new FoodAdapter(getActivity(), foodListDataAlies, this);
        foodLoggingListView2.setAdapter(foodAdapter);

        topTotalCal(foodListDataAlies);
        food_calorie_text.setText((int) caloryCalculate + "");
        totalCalory.setText((int) caloryCalculate + "" + unitType);

        getTotalNutrition(foodListDataAlies);
        calorieTotalTop.setText((int) nutritionCalculate + " " + TotalFoodFooterAdapter.food_content_type);

        setFoodSpinner();

        setMelFav(foodListDataAlies);

    }

//    public void dialog() {
//
//        DateModule dateObj = new DateModule();
//
//        final String foodName = dateObj.getDefaultFoodNameDate();
//
//        final Dialog dialog = new Dialog(getActivity()/* ,R.style.Theme_Dialog */);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawableResource(
//                android.R.color.transparent);
//        dialog.setContentView(R.layout.dialog_add_meal);
//
//        final EditText mealName = (EditText) dialog.findViewById(R.id.mealName);
//
//        dialog.findViewById(R.id.savebtn).setOnClickListener(
//                new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        String mealname = mealName.getText().toString();
//
//                        if (mealname.equalsIgnoreCase("")) {
//                            Toast.makeText(getActivity(), "Enter meal name",
//                                    Toast.LENGTH_LONG).show();
//                            return;
//                        }
//
//                        databaseObject.openDatabase();
//
//                        if (mealIdfromTable.equalsIgnoreCase("")) {
//                            MealTypeDAO mealType = new MealTypeDAO();
//                            mealType.setMealTypeName(mealname);
//                            FoodLoginModule.insertMealTypeTable(mealType,
//                                    databaseObject);
//                            mealIdfromTable = FoodLoginModule.getMealId(
//                                    mealname, databaseObject);
//                        }
//
//                        for (int i = initListSize; i < foodListData.size(); i++) {
//                            FitmiFoodLogDAO obj = foodListData.get(i);
//                            obj.setMealId(mealIdfromTable);
//
//                            Log.d("Food Id", mealIdfromTable);
//                            FoodLoginModule.insertFitmifoodLogTable(obj,
//                                    databaseObject);
//                        }
//
//                        databaseObject.closeDataBase();
//                        dialog.dismiss();
//                        getActivity().onBackPressed();
//                    }
//                });
//        dialog.findViewById(R.id.cancelbtn).setOnClickListener(
//                new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//
//        breakFastImg = (ImageView) dialog.findViewById(R.id.imgViewBreakfast);
//        breakFastImg.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//
//                mealName.setText("Breakfast_" + foodName);
//
//                mealIdfromTable = FoodLoginModule.getMealId("breakfast",
//                        databaseObject);
//
//                Log.e("Breakfast click", "yes");
//                breakFastImg.setBackgroundResource(R.drawable.circle_green);
//                lunchImg.setBackgroundResource(R.drawable.circle_lightgreen);
//                dinnerImg.setBackgroundResource(R.drawable.circle_lightgreen);
//                snacksImg.setBackgroundResource(R.drawable.circle_lightgreen);
//            }
//        });
//
//        lunchImg = (ImageView) dialog.findViewById(R.id.imgViewLunch);
//        lunchImg.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//
//                mealName.setText("Lunch_" + foodName);
//
//                mealIdfromTable = FoodLoginModule.getMealId("lunch",
//                        databaseObject);
//
//                breakFastImg
//                        .setBackgroundResource(R.drawable.circle_lightgreen);
//                lunchImg.setBackgroundResource(R.drawable.circle_green);
//                dinnerImg.setBackgroundResource(R.drawable.circle_lightgreen);
//                snacksImg.setBackgroundResource(R.drawable.circle_lightgreen);
//            }
//        });
//
//        dinnerImg = (ImageView) dialog.findViewById(R.id.imgViewDinner);
//        dinnerImg.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//
//                mealName.setText("Dinner_" + foodName);
//
//                mealIdfromTable = FoodLoginModule.getMealId("dinner",
//                        databaseObject);
//
//                breakFastImg
//                        .setBackgroundResource(R.drawable.circle_lightgreen);
//                lunchImg.setBackgroundResource(R.drawable.circle_lightgreen);
//                dinnerImg.setBackgroundResource(R.drawable.circle_green);
//                snacksImg.setBackgroundResource(R.drawable.circle_lightgreen);
//            }
//        });
//
//        snacksImg = (ImageView) dialog.findViewById(R.id.imgViewSnack);
//        snacksImg.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//
//                mealName.setText("Snack_" + foodName);
//
//                mealIdfromTable = FoodLoginModule.getMealId("snack",
//                        databaseObject);
//
//                breakFastImg
//                        .setBackgroundResource(R.drawable.circle_lightgreen);
//                lunchImg.setBackgroundResource(R.drawable.circle_lightgreen);
//                dinnerImg.setBackgroundResource(R.drawable.circle_lightgreen);
//                snacksImg.setBackgroundResource(R.drawable.circle_green);
//            }
//        });
//        dialog.show();
//        DisplayMetrics metrics = getResources().getDisplayMetrics();
//        int width = metrics.widthPixels;
//
//        dialog.getWindow()
//                .setLayout((6 * width) / 7, LayoutParams.WRAP_CONTENT);
//
//    }


    public void dialogDeleteItem(final int position) {

        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_DARK).create();

        alertDialog.setMessage("Do you want to delete this item ?");

        alertDialog.setButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                if (foodListDataAlies.size() > 0) {

                    caloryCalculate = 0;
                    foodLogObj.deleteFood(foodListDataAlies.get(
                            position).getFoodLogId());
                    foodListDataAlies.remove(position);
                    foodAdapterSearch.notifyDataSetChanged();

                    topTotalCal(foodListDataAlies);
                    getTotalNutrition(foodListDataAlies);
                    setMelFav(foodListDataAlies);
                    setFoodSpinner();
                    foodAdapter.notifyDataSetChanged();
                } else if (mealListData != null
                        && mealListData.size() > 0) {

                    caloryCalculate = 0;
                    foodLogObj.deleteFood(mealListData.get(position)
                            .getFoodLogId());
                    mealListData.remove(position);
                    foodAdapter.notifyDataSetChanged();
                    topTotalCal(mealListData);

                    totalCalory.setText((int) caloryCalculate + ""
                            + unitType);
                    getTotalNutrition(foodListDataAlies);
                    setMelFav(mealListData);
                    setFoodSpinner();
                }

                alertDialog.dismiss();
            }
        });
        alertDialog.setButton2("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
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

        dialog.getWindow()
                .setLayout((6 * width) / 7, LayoutParams.WRAP_CONTENT);

    }


    @Override
    public void calorieUpdate(TextView totalCalory, Bundle bundle,
                              DatabaseHelper databaseObject) {


        FoodLoginModule foodLogObjLocal = new FoodLoginModule(getActivity());
        try {

            databaseObject.createDatabase();

            databaseObject.openDatabase();

        } catch (IOException e) {
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
        totalCalory.setText(caloryCalculate + "" + unitType);

        setMelFav(foodListData);

        new HomeFragment().setTotalCalory(caloryCalculate);
    }

    BroadcastReceiver dateSetReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {


            Bundle bundleBroadcast = intent.getExtras();
            dateTextView.setText(bundleBroadcast.getString("key"));

            if (bundle != null) {

                mealId = bundle.getInt("mealid", 0);
                foodType = bundle.getString("foodtype");

                foodListData = foodLogObj.selectFoodList(String.valueOf(mealId), databaseObject);

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

                com.fitmi.utils.Constants.conditionDate = getDate
                        .conditionDateFormat(setDate);
                com.fitmi.utils.Constants.postDate = getDate
                        .getFormatDateSearchInsert(setDate);
                com.fitmi.utils.Constants.sTempDate = com.fitmi.utils.Constants.conditionDate;
                com.fitmi.utils.Constants.sDate = getDate.sDateFormat(setDate);

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
        }
    };



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
            Log.e("meal id during",
                    String.valueOf(mealId));
            for (int i = 0; i < foodListData.size(); i++) {
                if (!foodListData.get(i).getCalory().equalsIgnoreCase(""))
                    caloryCalculate += Float.parseFloat(foodListData.get(i)
                            .getCalory());
            }

        } else {
            Log.e("meal id", String.valueOf(mealId));
            foodListData = foodLogObj.selectAllFoodList(databaseObject);
            initListSize = foodListData.size();
            setFoodlistData(foodListData, true);

            for (int i = 0; i < foodListData.size(); i++) {
                if (!foodListData.get(i).getCalory().equalsIgnoreCase(""))
                    caloryCalculate += Float.parseFloat(foodListData.get(i)
                            .getCalory());
            }
        }

        try {
            sectionAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }

        totalCalory.setText((int) caloryCalculate + "" + unitType);

        setMelFav(foodListData);


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
        setFoodSpinner();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (callActivityResult == 0) {

            switch (requestCode) {
                case REQ_CODE_SPEECH_INPUT: {
                    if (resultCode == Activity.RESULT_OK && null != data) {

                        ArrayList<String> result = data
                                .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        searchEditText.setText(result.get(0));
                    }
                    break;
                }

            }

        } else {

        }

    }



    public void setFoodlistData(ArrayList<FitmiFoodLogDAO> itemList,
                                boolean isSection) {

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
                        calorieSum = FoodLoginModule.todayTotalNutritionByMealid(
                                "1", databaseObject);

                    } else if (mealName.equalsIgnoreCase("lunch")) {
                        // mealId = 2;
                        calorieSum = FoodLoginModule.todayTotalNutritionByMealid(
                                "2", databaseObject);

                    } else if (mealName.equalsIgnoreCase("dinner")) {
                        calorieSum = FoodLoginModule.todayTotalNutritionByMealid(
                                "3", databaseObject);
                        // mealId = 3;

                    } else if (mealName.equalsIgnoreCase("snack")) {
                        // mealId = 4;
                        calorieSum = FoodLoginModule.todayTotalNutritionByMealid(
                                "4", databaseObject);
                    }

                    header.setTotal(String.valueOf((int) (Float.parseFloat(calorieSum) + 0.5)));
                    header.setUnit(TotalFoodFooterAdapter.food_content_type.toLowerCase());

                    demoSection.add(mealName);
                    items.add(header);

                }

            }

            foodItem.setFoodLogId(itemList.get(j).getFoodLogId());
            foodItem.setFoodName(itemList.get(j).getFoodName());
            foodItem.setDescription(itemList.get(j).getDescription());
            foodItem.setCalory(itemList.get(j).getCalory());
            foodItem.setCho(itemList.get(j).getCho());
            foodItem.setCar(itemList.get(j).getCar());
            foodItem.setPro(itemList.get(j).getPro());
            foodItem.setSod(itemList.get(j).getSod());
            foodItem.setFat(itemList.get(j).getFat());
            foodItem.setServingSize(itemList.get(j).getServingSize());
            foodItem.setReferenceFoodId(itemList.get(j).getReferenceFoodId());
            foodItem.setMealWeight(itemList.get(j).getMealWeight());
            items.add(foodItem);
        }
    }


    public void setFoodSpinner() {
        totalCaloty = ActivityModule.todayTotalCaloryBurn(com.fitmi.utils.Constants.conditionDate, databaseObject, getActivity());
        if (totalCaloty == null)
            activity_calorie_text.setText("0");
        else
            activity_calorie_text.setText(totalCaloty);
        caloryInTake = com.fitmi.utils.Constants.TOTAL_CALORY_INTAKE;

        remainCalory = (int) (Float.parseFloat(caloryInTake) - (int) caloryCalculate);

        daily_calorie_text.setText(caloryInTake);

        foodValues = new String[5];

        if (remainCalory < 0) {

            remainCaloryBurn.setText("0");
        } else {
            remainCaloryBurn.setText(remainCalory + "");
        }

        foodValues[0] = (int) caloryCalculate + "";

        foodValues[1] = FoodLoginModule.todayTotalCaloryByMealid(
                "1", databaseObject);
        foodValues[2] = FoodLoginModule.todayTotalCaloryByMealid(
                "2", databaseObject);
        foodValues[3] = FoodLoginModule.todayTotalCaloryByMealid(
                "3", databaseObject);
        foodValues[4] = FoodLoginModule.todayTotalCaloryByMealid(
                "4", databaseObject);


        adapter = new ArrayAdapter<>(getActivity(),
                R.layout.spinner_item, foodValues);

        flsa = new FoodLoggingSpinnerAdapter(getActivity(), foodValues,
                fooddrawableValues, R.drawable.circle_green);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        foodLoggingSpinner.setAdapter(flsa);

        foodListData = foodLogObj.selectAllFoodList(databaseObject);

        food_calorie_text.setText(foodValues[0]);
        calorieTotalTop.setText((int) nutritionCalculate + " " + TotalFoodFooterAdapter.food_content_type);

        foodDropClick = false;


    }


    public void setActivitySpinner() {

        totalCaloty = ActivityModule.todayTotalCaloryBurn(
                com.fitmi.utils.Constants.conditionDate, databaseObject, getActivity());

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
        }

        activityDropClick = false;
    }


    @Override
    public void setNewCaloryIntake(String calorie) {

        com.fitmi.utils.Constants.homeCaloryIntake.setText(calorie);
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

        if (com.fitmi.utils.Constants.isSync)
            tareScaleAndApp();

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

        if (com.fitmi.utils.Constants.isSync)
            tareScaleAndApp();

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
;

        if (com.fitmi.utils.Constants.isSync)
            tareScaleAndApp();

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


        if (com.fitmi.utils.Constants.isSync)
            tareScaleAndApp();

    }



    private BroadcastReceiver scaleDisConnected = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            tareScaleAndApp();
        }
    };


    private BroadcastReceiver currentWeight = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (sFOODLOGGING_POS >= 0)
                saveNewCurrentWeight(String.valueOf(intent.getIntExtra("weight", 0)), sFOODLOGGING_POS);
            else {
                totalFoodFooterAdapter = new TotalFoodFooterAdapter(
                        getActivity(), FoodLoggingFragment.this,
                        String.valueOf(intent.getIntExtra("weight", 0)),
                        "0",
                        String.valueOf((int) nutritionCalculate),
                        "0");
                listTotalFrame_FoodLogging.setAdapter(totalFoodFooterAdapter);
                totalFoodFooterAdapter.notifyDataSetChanged();
            }

        }
    };

    private BroadcastReceiver currentUnit = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            changeUnitFood(intent.getIntExtra("unit", 7));

            if (com.fitmi.utils.Constants.gunitfw == 0 && intent.getIntExtra("unit", 7) == 8) {
                com.fitmi.utils.Constants.gunitfw = 1;
            } else {
                com.fitmi.utils.Constants.gunitfw = 0;
            }


            try {
                totalFoodFooterAdapter.notifyDataSetChanged();
                foodAdapter.notifyDataSetChanged();
            } catch (Exception ignored) {
            }
        }
    };


    class TagSet {
        int imgId = 0;
        ArrayList<FitmiFoodLogDAO> mealidList;
    }

    @Override
    public void buttonPressed() {

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

        switch (tag.imgId) {
            case R.drawable.green_star:
                totalFoodFooterAdapter = new TotalFoodFooterAdapter(getActivity(), FoodLoggingFragment.this,
                        currActivewt, currActiveCalwt,
                        String.valueOf((int) nutritionCalculate), "0");
                listTotalFrame_FoodLogging.setAdapter(totalFoodFooterAdapter);
                isFavMealVar = "0";
                totalFoodFooterAdapter.notifyDataSetChanged();
                break;

            case R.drawable.favorites_star:
                totalFoodFooterAdapter = new TotalFoodFooterAdapter(getActivity(), FoodLoggingFragment.this,
                        currActivewt, currActiveCalwt,
                        String.valueOf((int) nutritionCalculate), "1");
                listTotalFrame_FoodLogging.setAdapter(totalFoodFooterAdapter);
                isFavMealVar = "1";
                break;
        }

    }


    public void editDialog(final int position) {

        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_DARK).create();

        if (com.fitmi.utils.Constants.gunitfw == 0) {
            alertDialog.setMessage("Edit weight (g)");
        } else {
            alertDialog.setMessage("Edit weight (oz)");
        }

        final EditText newCalory = new EditText(getActivity());

        newCalory.setInputType(InputType.TYPE_CLASS_NUMBER);
        newCalory.setBackgroundColor(getResources().getColor(R.color.black_dialbg));
        newCalory.setHint("Enter weight");
        newCalory.setTextColor(getResources().getColor(R.color.white));

        alertDialog.setView(newCalory);
        alertDialog.setButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (newCalory.getText().toString()
                        .equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Enter Weight",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                saveNewCurrentWeight(newCalory.getText().toString(), position);
                View view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                dialog.dismiss();

            }
        });
        alertDialog.setButton2("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                View view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
        DisplayMetrics metrics = getActivity().getResources()
                .getDisplayMetrics();
        int width = metrics.widthPixels;
        alertDialog.getWindow()
                .setLayout((6 * width) / 7, LayoutParams.WRAP_CONTENT);
        nextbtnVal = 0;
        newCalory.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

    }

    private void saveNewCurrentWeight(String weight, int position) {

        try {

            if (foodListDataAlies.size() > 0) {

                caloryCalculate = 0;
                FoodLoginModule.editCaloryItew(foodListDataAlies.get(position).getFoodLogId(), databaseObject, weight);
                foodListDataAlies = foodLogObj.selectFoodList(String.valueOf(mealIdSpinner), databaseObject);
                foodAdapterSearch.notifyDataSetChanged();

                foodAdapter.notifyDataSetChanged();
                getCurrentActiveCal(foodListDataAlies);
                getCurrentActiveCal(foodListDataAlies);
                getTotalNutrition(foodListDataAlies);
                topTotalCal(foodListDataAlies);
                setFoodSpinner();
                totalCalory.setText((int) caloryCalculate + ""
                        + unitType);
            } else if (mealListData != null
                    && mealListData.size() > 0) {

                caloryCalculate = 0;


                FoodLoginModule.editCaloryItew(mealListData.get(position).getFoodLogId(), databaseObject, weight);

                mealListData = foodLogObj.selectFoodList(
                        String.valueOf(mealIdSpinner),
                        databaseObject);
                foodAdapter.notifyDataSetChanged();

                totalFoodFooterAdapter.notifyDataSetChanged();
                foodAdapter.notifyDataSetChanged();
                getCurrentActiveCal(mealListData);
                getTotalNutrition(mealListData);
                topTotalCal(mealListData);
                setFoodSpinner();
            }

            currActivewt = weight;

            try {
                if (isFavMealVar.equalsIgnoreCase("0")) {
                    totalFoodFooterAdapter = new TotalFoodFooterAdapter(
                            getActivity(), FoodLoggingFragment.this,
                            currActivewt,
                            currActiveCalwt,
                            String.valueOf((int) nutritionCalculate),
                            "0");
                } else {
                    totalFoodFooterAdapter = new TotalFoodFooterAdapter(
                            getActivity(), FoodLoggingFragment.this,
                            currActivewt,
                            currActiveCalwt,
                            String.valueOf((int) nutritionCalculate),
                            "1");
                }
            } catch (Exception a) {
                totalFoodFooterAdapter = new TotalFoodFooterAdapter(
                        getActivity(), FoodLoggingFragment.this, currActivewt,
                        currActiveCalwt,
                        String.valueOf((int) nutritionCalculate),
                        "0");
                a.printStackTrace();

            }

            listTotalFrame_FoodLogging
                    .setAdapter(totalFoodFooterAdapter);
            totalFoodFooterAdapter.notifyDataSetChanged();


        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    public void onDestroy() {

        try {
            if (dateSetReceiver != null) {
                getActivity().unregisterReceiver(dateSetReceiver);
            }
        } catch (Exception a) {
            a.printStackTrace();
        }
        stopReCheckScaleConnection();
        getActivity().unregisterReceiver(scaleDisConnected);
        getActivity().unregisterReceiver(currentWeight);
        getActivity().unregisterReceiver(currentUnit);
        super.onDestroy();
    }

    private class AsyncNutritionTwo extends AsyncTask<String, Void, String> {

        String JSON_URL;
        String __id;
        JSONObject holder = new JSONObject();

        public AsyncNutritionTwo(String jsString, JSONObject object, String _id) {
            JSON_URL = jsString;
            holder = object;
            __id = _id;
        }

        @Override
        protected void onPreExecute() {

            foodLoggingListView2.setVisibility(View.VISIBLE);
            pDialog.setMessage("Loading...");
            pDialog.show();

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            String SetServerString = "";
            DefaultHttpClient Client = getNewHttpClient();
            HttpPost httpost = new HttpPost(JSON_URL);
            StringEntity se = null;
            try {
                se = new StringEntity(holder.toString());
            } catch (UnsupportedEncodingException e2) {

                e2.printStackTrace();
            }

            httpost.setEntity(se);
            httpost.setHeader("Accept", "application/json");
            httpost.setHeader("Content-Type", "application/json");
            httpost.setHeader("x-app-id", "b65138b3");
            httpost.setHeader("x-app-key", "220fa746203ea5217abff5970c9f8d43");

            ResponseHandler responseHandler = new BasicResponseHandler();
            try {
                SetServerString = (String) Client.execute(httpost, responseHandler);
            } catch (IOException e1) {

                e1.printStackTrace();
            }

            return SetServerString;
        }

        @Override
        protected void onPostExecute(String result) {

            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONArray predsJsonArray = jsonObj.getJSONArray("foods");

                Log.e("PREDICTIONS foods", predsJsonArray.toString());

                if (searchList != null && searchList.size() > 0)
                    searchList.clear();
                String itemName = null;
                String brandName = "";
                String nfCalories = null, pro = null, car = null, fat = null, sod = null, cho = null;

                String nfServingWeightGrams = null;

                for (int i = 0; i < predsJsonArray.length(); i++) {

                    try {
                        itemName = predsJsonArray.getJSONObject(i).getString(
                                "food_name");
                        brandName = predsJsonArray.getJSONObject(i).getString(
                                "brand_name");
                        nfCalories = predsJsonArray.getJSONObject(i).getString(
                                "nf_calories");
                        pro = predsJsonArray.getJSONObject(i).getString(
                                "nf_protein");
                        car = predsJsonArray.getJSONObject(i).getString(
                                "nf_total_carbohydrate");
                        fat = predsJsonArray.getJSONObject(i).getString(
                                "nf_total_fat");
                        sod = predsJsonArray.getJSONObject(i).getString(
                                "nf_sodium");
                        cho = predsJsonArray.getJSONObject(i).getString(
                                "nf_cholesterol");

                        nfServingWeightGrams = predsJsonArray.getJSONObject(i)
                                .getString("serving_weight_grams");

                    } catch (Exception a) {

                        a.printStackTrace();
                    }


                }

                if (itemName != null && nfCalories != null && nfServingWeightGrams != null) {
                    if (itemName.length() > 0 && nfCalories.length() > 0
                            && nfServingWeightGrams.length() > 0) {

                        itemName = WordUtils.capitalize(itemName);
                        boolean _exists = FoodLoginModule.ExistsFoodLog(__id,
                                String.valueOf(mealId), databaseObject);

                        if (!_exists) {
//                            FoodLoginModule.insertFitmifoodTable(itemName,
//                                    nfCalories, __id, nfServingWeightGrams, "",
//                                    databaseObject, getActivity());

                            FitmiFoodLogDAO fitmiFoodLogData = new FitmiFoodLogDAO();

                            fitmiFoodLogData.setDescription("");
                            fitmiFoodLogData.setFoodName(itemName);
                            fitmiFoodLogData.setBrandName(brandName);
                            fitmiFoodLogData.setReferenceFoodId(__id);
                            fitmiFoodLogData
                                    .setUserId(Constants.USER_ID);
                            fitmiFoodLogData
                                    .setUserProfileId(Constants.PROFILE_ID);
                            fitmiFoodLogData
                                    .setLogTime(Constants.postDate);
                            fitmiFoodLogData
                                    .setDateAdded(Constants.postDate);
                            fitmiFoodLogData.setMealId(String.valueOf(mealId));
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

                            fitmiFoodLogData.setCalory(nfCalories);
                            fitmiFoodLogData.setPro(pro);
                            fitmiFoodLogData.setCar(car);
                            fitmiFoodLogData.setFat(fat);
                            fitmiFoodLogData.setSod(sod);
                            fitmiFoodLogData.setCho(cho);
                            fitmiFoodLogData.setServingSize(nfServingWeightGrams);

                            fitmiFoodLogData.setMealWeight(scaleCommunicator.getWeight());
                            fitmiFoodLogData.setFavourite("0");

                            mealIdSpinner = mealId;
                            currActivewt = scaleCommunicator.getWeight();

                            fitmiFoodLogDataTemp = fitmiFoodLogData;
                            if (!log) {
                                FoodLoginModule.insertFitmifoodLogTable(
                                        fitmiFoodLogData, databaseObject);
                            } else {

                                if (Constants.foodLogData.size() > 0)
                                    Constants.foodLogData.clear();
                                Constants.foodLogData
                                        .add(fitmiFoodLogData);
                            }

                            if (mealListData != null && mealListData.size() > 0)
                                mealListData.clear();

                            foodListData.add(fitmiFoodLogData);

                            if (log) {
                                foodListDataAlies.add(fitmiFoodLogData);
                                Constants.foodLogData = new ArrayList<>(
                                        foodListDataAlies);
                            } else {
                                foodListDataAlies.clear();
                                foodListDataAlies = foodLogObj.selectFoodList(String.valueOf(mealId), databaseObject);
                            }
                            searchListAdapter();
                            newDataAdd = true;
                            newDataCount++;

                            new HomeFragment().setTotalCalory(caloryCalculate);

                            databaseObject.closeDataBase();
                        }
                        searchEditText.setText("");

                        try {
                            if (!addMealLiner.isShown() && !log) {
                                sFOODLOGGING_POS = foodListDataAlies.size() - 1;
                                if (foodAdapter == null) {
                                    foodAdapter = new FoodAdapter(getActivity(), foodListDataAlies,
                                            FoodLoggingFragment.this);
                                    foodLoggingListView2.setAdapter(foodAdapter);
                                }
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
                    updateFooterAdapter();
                }
            } catch (JSONException e) {
                Log.e("LOG TAG", "Cannot process JSON results", e);
            }

            pDialog.dismiss();
        }
    }

    private class PlacesAutoCompleteAdapter extends BaseAdapter {

        Context context;

        public PlacesAutoCompleteAdapter(Context context) {
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

        @SuppressLint("InflateParams")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {

                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.autocomplete_layout,
                        null);

            }

            try {
                FitmiFoodDAO object = searchList1.get(position);

                holder.txtName = (TextView) convertView
                        .findViewById(R.id.txtName);
                holder.txtName.setText(object.getItemName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return convertView;
        }

        @Override
        public long getItemId(int arg0) {

            return arg0;
        }

        public class ViewHolder {

            TextView txtName;
        }
    }

    private class AsyncAutocompleteNutri extends
            AsyncTask<String, Void, String> {

        String JSON_URL;
        String input;

        public AsyncAutocompleteNutri(String jsString, String input) {
            JSON_URL = jsString;
            this.input = input;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {

            String typeText = input;
            String SetServerString = "";
            if (input.length() > 0) {
                try {

                    typeText = typeText.replaceAll(" ", "%20");

                    DefaultHttpClient Client = getNewHttpClient();
                    HttpGet httpget = new HttpGet("https://api.nutritionix.com/v2/autocomplete?q="
                            + typeText
                            + "&appId=b65138b3&appKey=220fa746203ea5217abff5970c9f8d43");

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    SetServerString = Client.execute(httpget, responseHandler);
                    Log.e("SetServerString", "SetServerString==" + SetServerString);

                } catch (MalformedURLException e) {
                    Log.e("LOG TAG", "Error processing Places API URL", e);
                    return SetServerString;
                } catch (IOException e) {
                    Log.e("LOG TAG", "Error connecting to Places API", e);
                    return SetServerString;
                }

            }
            return SetServerString;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.length() > 0) {
                try {

                    JSONArray predsJsonArray = new JSONArray(result);
                    Log.e("PREDICTIONS", predsJsonArray.toString());

                    if (searchList1 != null) {

                        if (searchList1.size() > 0)
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

                        Collections.sort( searchList1, new Comparator<FitmiFoodDAO>(){
                            public int compare(FitmiFoodDAO d1, FitmiFoodDAO d2){
                                return d1.getItemName().compareTo(d2.getItemName());
                            }
                        });
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

            Log.e("updateValueTap ", "updateValueTap started " + position);
            if (foodListDataAlies.size() > 0) {

                caloryCalculate = 0;
                currActivewt = String.valueOf((int) (Float.parseFloat(foodListDataAlies.get(position).getMealWeight()) + 0.5));

                getCurrentActiveCal(foodListDataAlies);
                getTotalNutrition(foodListDataAlies);
                topTotalCal(foodListDataAlies);

                FoodLoginModule.editCaloryItew(foodListDataAlies.get(position).getFoodLogId(), databaseObject, foodListDataAlies.get(position).getMealWeight());

                foodListDataAlies = foodLogObj.selectFoodList(
                        String.valueOf(mealIdSpinner), databaseObject);

                totalCalory.setText((int) caloryCalculate + "" + unitType);
                totalFoodFooterAdapter.notifyDataSetChanged();
                foodAdapter.notifyDataSetChanged();
            } else if (mealListData != null && mealListData.size() > 0) {

                caloryCalculate = 0;

                currActivewt = String.valueOf((int) (Float.parseFloat(mealListData.get(position).getMealWeight()) + 0.5));

                getCurrentActiveCal(mealListData);
                getTotalNutrition(mealListData);
                topTotalCal(mealListData);

                FoodLoginModule.editCaloryItew(mealListData.get(position).getFoodLogId(), databaseObject, mealListData.get(position).getMealWeight());
                mealListData = foodLogObj.selectFoodList(
                        String.valueOf(mealIdSpinner), databaseObject);

                totalCalory.setText((int) caloryCalculate + "" + unitType);
                totalFoodFooterAdapter.notifyDataSetChanged();
                foodAdapter.notifyDataSetChanged();
            }

        } catch (Exception exception) {

            exception.printStackTrace();
        }
        if (isFavMealVar.equalsIgnoreCase("0")) {
            totalFoodFooterAdapter = new TotalFoodFooterAdapter(getActivity(), FoodLoggingFragment.this,
                    currActivewt, currActiveCalwt,
                    String.valueOf((int) nutritionCalculate), "0");
        } else {
            totalFoodFooterAdapter = new TotalFoodFooterAdapter(getActivity(), FoodLoggingFragment.this,
                    currActivewt, currActiveCalwt,
                    String.valueOf((int) nutritionCalculate), "1");
        }

        setFoodSpinner();

        listTotalFrame_FoodLogging.setAdapter(totalFoodFooterAdapter);
        totalFoodFooterAdapter.notifyDataSetChanged();

    }

    @SuppressWarnings("StatementWithEmptyBody")
    public void showSyncdialog() {
        if (com.fitmi.utils.Constants.isBluetoothOnLocal == 1
                && com.fitmi.utils.Constants.connectedTodevice == 1) {

        } else {
            dialogDevicesync();
        }
    }

    public void dialogDevicesync() {

        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_DARK).create();

        alertDialog.setTitle("Fitmi");

        alertDialog.setMessage("Kindly Sync the Kitchen Scale Device");

        alertDialog.setButton("Sync Now", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                sendKitchenScale();

            }
        });
        alertDialog.setButton2("Later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                alertDialog.dismiss();

            }
        });

        alertDialog.show();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        alertDialog.getWindow()
                .setLayout((6 * width) / 7, LayoutParams.WRAP_CONTENT);

    }

    public void sendKitchenScale() {

        UserInfoDAO userInfo;

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

    public void changeUnitFood(int units) {

        unitDataFood_Weight = new UnitItemDAO();

        unitDataFood_Weight.setProfileId(com.fitmi.utils.Constants.PROFILE_ID);
        unitDataFood_Weight.setUserId(com.fitmi.utils.Constants.USER_ID);
        unitDataFood_Weight.setType("food_weight");
        unitDataFood_Weight.setUnitId(String.valueOf(units));
        unitModel.setUnitLog(unitDataFood_Weight);

    }

    public void changeFoodContentType() {

        SharedPreferences prefs = getActivity().getSharedPreferences(SaveSharedPreferences.USER_INFORMATION, getActivity().MODE_PRIVATE);
        int data_mode_index = prefs.getInt("data_mode_index", 0);
        data_mode_index++;

        if (data_mode_index == 6)
            data_mode_index = 0;
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(SaveSharedPreferences.USER_INFORMATION, getActivity().MODE_PRIVATE).edit();
        editor.putInt("data_mode_index", data_mode_index);
        editor.commit();

        TotalFoodFooterAdapter.food_content_type = data_mode[data_mode_index];

        if (foodAdapter != null)
            foodAdapter.notifyDataSetChanged();

        updateFooterAdapter();
    }

    @Override
    public void changeFoodUnit() {
        if (com.fitmi.utils.Constants.gunitfw == 0) {
            scaleCommunicator.changeUnits("8");
        } else {
            scaleCommunicator.changeUnits("7");
        }

    }

    private void updateFooterAdapter() {
        try {
            ArrayList<FitmiFoodLogDAO> mealListData1 = foodLogObj.selectFoodList(
                    String.valueOf(mealIdSpinner),
                    databaseObject);

            getCurrentActiveCal(mealListData1);
            getTotalNutrition(mealListData1);

            try {

                if (isFavMealVar.equalsIgnoreCase("0")) {
                    totalFoodFooterAdapter = new TotalFoodFooterAdapter(
                            getActivity(), FoodLoggingFragment.this,
                            currActivewt,
                            currActiveCalwt,
                            String.valueOf((int) nutritionCalculate),
                            "0");
                } else {
                    totalFoodFooterAdapter = new TotalFoodFooterAdapter(
                            getActivity(), FoodLoggingFragment.this,
                            currActivewt,
                            currActiveCalwt,
                            String.valueOf((int) nutritionCalculate),
                            "1");
                }
            } catch (Exception a) {
                a.printStackTrace();
            }

            listTotalFrame_FoodLogging
                    .setAdapter(totalFoodFooterAdapter);
            totalFoodFooterAdapter.notifyDataSetChanged();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void getTotalNutrition(ArrayList<FitmiFoodLogDAO> mealListData1) {
        SharedPreferences prefs = getActivity().getSharedPreferences(SaveSharedPreferences.USER_INFORMATION, getActivity().MODE_PRIVATE);
        int data_mode_index = prefs.getInt("data_mode_index", 0);
//        caloryCalculate = 0;
        nutritionCalculate = 0;

        TotalFoodFooterAdapter.food_content_type = data_mode[data_mode_index];

        switch (data_mode_index) {
            case 0:
                for (int i = 0; i < mealListData1.size(); i++) {
                    if (!mealListData1.get(i).getCalory()
                            .equalsIgnoreCase("")) {

                        float pergram = Float.parseFloat(mealListData1.get(i).getCalory())
                                / Float.parseFloat(mealListData1.get(i).getServingSize());
                        float cal = Float.parseFloat(mealListData1.get(i).getMealWeight())
                                * pergram;
                        nutritionCalculate += cal;

                    }
                }
                break;

            case 1:
                for (int i = 0; i < mealListData1.size(); i++) {
                    if (!mealListData1.get(i).getPro()
                            .equalsIgnoreCase("")) {

                        float pergram = Float.parseFloat(mealListData1.get(i).getPro())
                                / Float.parseFloat(mealListData1.get(i).getServingSize());
                        float cal = Float.parseFloat(mealListData1.get(i).getMealWeight())
                                * pergram;
                        nutritionCalculate += cal;

                    }
                }
                break;

            case 2:
                for (int i = 0; i < mealListData1.size(); i++) {
                    if (!mealListData1.get(i).getCar()
                            .equalsIgnoreCase("")) {
                        float pergram = Float.parseFloat(mealListData1.get(i).getCar())
                                / Float.parseFloat(mealListData1.get(i).getServingSize());
                        float cal = Float.parseFloat(mealListData1.get(i).getMealWeight())
                                * pergram;
                        nutritionCalculate += cal;
                    }
                }
                break;

            case 3:
                for (int i = 0; i < mealListData1.size(); i++) {
                    if (!mealListData1.get(i).getFat()
                            .equalsIgnoreCase("")) {

                        float pergram = Float.parseFloat(mealListData1.get(i).getFat())
                                / Float.parseFloat(mealListData1.get(i).getServingSize());
                        float cal = Float.parseFloat(mealListData1.get(i).getMealWeight())
                                * pergram;
                        nutritionCalculate += cal;

                    }
                }
                break;

            case 4:
                for (int i = 0; i < mealListData1.size(); i++) {
                    if (!mealListData1.get(i).getSod()
                            .equalsIgnoreCase("")) {
                        float pergram = Float.parseFloat(mealListData1.get(i).getSod())
                                / Float.parseFloat(mealListData1.get(i).getServingSize());
                        float cal = Float.parseFloat(mealListData1.get(i).getMealWeight())
                                * pergram;
                        nutritionCalculate += cal;

                    }
                }
                break;

            case 5:
                for (int i = 0; i < mealListData1.size(); i++) {
                    if (!mealListData1.get(i).getCho()
                            .equalsIgnoreCase("")) {

                        float pergram = Float.parseFloat(mealListData1.get(i).getCho())
                                / Float.parseFloat(mealListData1.get(i).getServingSize());
                        float cal = Float.parseFloat(mealListData1.get(i).getMealWeight())
                                * pergram;
                        nutritionCalculate += (int) cal;

                    }
                }
                break;
        }
    }

    private void getCurrentActiveCal(ArrayList<FitmiFoodLogDAO> mealListData1) {
        SharedPreferences prefs = getActivity().getSharedPreferences(SaveSharedPreferences.USER_INFORMATION, getActivity().MODE_PRIVATE);
        int data_mode_index = prefs.getInt("data_mode_index", 0);


        switch (data_mode_index) {
            case 0:
                currActiveCalwt = (sFOODLOGGING_POS == -1) ? "0" : mealListData1.get(sFOODLOGGING_POS).getCalory();
                break;

            case 1:
                currActiveCalwt = (sFOODLOGGING_POS == -1) ? "0" : mealListData1.get(sFOODLOGGING_POS).getPro();
                break;

            case 2:
                currActiveCalwt = (sFOODLOGGING_POS == -1) ? "0" : mealListData1.get(sFOODLOGGING_POS).getCar();
                break;

            case 3:
                currActiveCalwt = (sFOODLOGGING_POS == -1) ? "0" : mealListData1.get(sFOODLOGGING_POS).getFat();
                break;

            case 4:
                currActiveCalwt = (sFOODLOGGING_POS == -1) ? "0" : mealListData1.get(sFOODLOGGING_POS).getSod();
                break;

            case 5:
                currActiveCalwt = (sFOODLOGGING_POS == -1) ? "0" : mealListData1.get(sFOODLOGGING_POS).getCho();
                break;
        }
        float cal = Float.parseFloat(currActiveCalwt);
        if (sFOODLOGGING_POS != -1) {
            float pergram = Float.parseFloat(currActiveCalwt)
                    / Float.parseFloat(mealListData1.get(sFOODLOGGING_POS).getServingSize());
            cal = Float.parseFloat(mealListData1.get(sFOODLOGGING_POS).getMealWeight())
                    * pergram;
        }

        currActiveCalwt = String.valueOf((int) cal);
    }
}