package com.fitmi.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.db.DatabaseHelper;
import com.db.modules.RememberMeData;
import com.db.modules.UnitModule;
import com.db.modules.UserInfoModule;
import com.dts.classes.AsyncTaskListener;
import com.dts.classes.JSONParser;
import com.dts.classes.PostObject;
import com.fitmi.R;
import com.fitmi.activitys.TabActivity;
import com.fitmi.adapter.UserInfoSpinnerAdapter;
import com.fitmi.dao.CaloryBaselineDAO;
import com.fitmi.dao.UnitItemDAO;
import com.fitmi.dao.UserInfoDAO;
import com.fitmi.utils.Constants;
import com.fitmi.utils.HandelOutfemoryException;
import com.fitmi.utils.SaveSharedPreferences;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class UserInfoFragment extends BaseFragment {

    @InjectView(R.id.Heading)
    public TextView heading;

    @InjectView(R.id.Back)
    public ImageView back;

    @InjectView(R.id.backLiner)
    LinearLayout backLiner;

    @InjectView(R.id.Settings)
    public ImageView settings;

    @InjectView(R.id.Month)
    TextView month;


    @InjectView(R.id.Day)
    TextView day;

    @InjectView(R.id.Year)
    TextView year;

    @InjectView(R.id.Feet)
    EditText feet;

    @InjectView(R.id.done_)
    EditText done_;


    @InjectView(R.id.Inches)
    EditText inches;

    @InjectView(R.id.txtWeight)
    TextView txtWeight;

    //@InjectView(R.id.Unit_Spinner)
    //Spinner unitSpinner;

    //@InjectView(R.id.Activity_Level_Spinner)
    //Spinner activityLevelSpinner;

    //@InjectView(R.id.genderSelect_Spinner)
    //Spinner genderSelectSpinner;

    //@InjectView(R.id.weightUnit_Spinner)
    //Spinner weightUnitSpinner;

    int imagePickClick = 0;

    @InjectView(R.id.firstName)
    EditText firstName;

    @InjectView(R.id.lastName)
    EditText lastName;

/*	@InjectView(R.id.DailyCalorieIntake)
    TextView DailyCalorieIntake;*/

    @InjectView(R.id.Weight)
    EditText weight;

    String gender = "Male";

    @InjectView(R.id.profileImage)
    ImageView profileImage;

    @InjectView(R.id.txtHeight)
    TextView txtHeight;

    @InjectView(R.id.txtActivityLevel)
    TextView txtActivityLevel;

    @InjectView(R.id.txtGender)
    TextView txtGender;


    private static int mYear;
    private static int mMonth;
    private static int mDay;

    public static int UNIT_SELECT_POSITION = 0;

    static final int DATE_PICKER_ID = 1111;

    String[] arrayForHeight = {"Feet", "Cm"};
    String[] arrayForActivityLevel = {"Select", "Low", "Moderate", "High"};
    String[] arrayForGender = {"Male", "Female"};
    String[] arrayForWeight = {"Kg", "Pound"};//"Unit",

    DatabaseHelper databaseObject;

    String activeLevel = "Low";

    String userProfileId = "";

    String selectPicturePath = "";
    String imagePath = "";


    // Code for our image picker select action.
    private static final int IMAGE_PICKER_SELECT = 999;

    static final int REQUEST_TAKE_PHOTO = 11111;


    float inchVal = 0, ftVal, cmVal = 0;
    float LbsVal = 0, KgVal = 0;
    float BMRVal = 0;
    int _ftintVal = 0;
    private double IntakeVal;

    private double BurnedVal;

    //String selectedDate = "";

    long calenderMilisecond = 0, todayDate = 0;

    Calendar dobDate;

    private int years;

    String firstname = "", lastname = "", daySet = "", feetSet = "", inchesSet = "", weightSet = "";

    Bundle bundle;

    int root_id = 0;


    UnitModule unitModel;
    ArrayList<UnitItemDAO> unitItem;
    UnitItemDAO itemHeight;
    UnitItemDAO itemWeight;
    UnitItemDAO unitDataWeight;

    UnitItemDAO unitDataHeight;
    UnitItemDAO unitDataBp;
    UnitItemDAO unitDataFood_Weight;

    //Added for change avinash

    @InjectView(R.id.SwitchText_CalorieIntakeinfo)
    public TextView switchOn;

    @InjectView(R.id.DailyCalorieIntakeLinear_CalorieIntakeinfo)
    public LinearLayout dailyCalorieIntakeLinear;

    @InjectView(R.id.CalorieDetails_CalorieIntakeinfo)
    public TextView calorieDetailsText;

    UserInfoModule userDb;

    String caloryIntake = "";
    String calInput = "";

    @InjectView(R.id.editTextCaloryUpdateinfo)
    EditText editTextCaloryUpdate;

    @InjectView(R.id.txtCaloryTakesetinfo)
    TextView txtCaloryTakeset;

    @InjectView(R.id.frameCalorySetinfo)
    FrameLayout frameCalorySet;

    RememberMeData calValueShared;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.fragment_user_info, null);


        bundle = this.getArguments();

        root_id = bundle.getInt("root_id");

        databaseObject = new DatabaseHelper(getActivity());
        try {

            databaseObject.createDatabase();

            databaseObject.openDatabase();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        unitModel = new UnitModule(getActivity());
        unitItem = unitModel.selectUnitLogList();
        unitDataWeight = new UnitItemDAO();
        ButterKnife.inject(this, view);
        setNullClickListener(view);
        heading.setText("User Info");

        //settings.setVisibility(View.GONE);


        ArrayAdapter<String> genderSelectAdapter = new ArrayAdapter<String>(
                getActivity(), R.layout.item_spinner, R.id.SpinnerText,
                arrayForGender);
        //genderSelectSpinner.setAdapter(genderSelectAdapter);

        ArrayAdapter<String> weightUnitAdapter = new ArrayAdapter<String>(
                getActivity(), R.layout.item_spinner, R.id.SpinnerText,
                arrayForWeight);
        //weightUnitSpinner.setAdapter(weightUnitAdapter);

        ArrayAdapter<String> heightSpinnerAdapter = new ArrayAdapter<String>(
                getActivity(), R.layout.item_spinner, R.id.SpinnerText,
                arrayForHeight);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_item, arrayForHeight);

        UserInfoSpinnerAdapter unitAdapter = new UserInfoSpinnerAdapter(getActivity(), arrayForHeight);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        //unitSpinner.setAdapter(unitAdapter);

        //unitSpinner.setAdapter(heightSpinnerAdapter);

        ArrayAdapter<String> activityLevelSpinnerAdapter = new ArrayAdapter<String>(
                getActivity(), R.layout.item_spinner, R.id.SpinnerText,
                arrayForActivityLevel);

		/*activityLevelSpinner.setAdapter(activityLevelSpinnerAdapter);
        activityLevelSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				activeLevel = activityLevelSpinner.getSelectedItem().toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});*/
        try {
            calValueShared = SaveSharedPreferences.getCalorieDetail(getActivity());
            Log.e("calValueShared ", calValueShared.get_calorieOn());

            if (calValueShared.get_calorieOn().equalsIgnoreCase("0")) {
                switchOn.setText("OFF");
                dailyCalorieIntakeLinear.setVisibility(View.VISIBLE);
                frameCalorySet.setVisibility(View.GONE);
                calorieDetailsText.setText(getResources().getString(
                        R.string.daily_calorie_intake_off));
                editTextCaloryUpdate.setText(caloryIntake);


            } else {
                switchOn.setText("ON");
                dailyCalorieIntakeLinear.setVisibility(View.GONE);
                frameCalorySet.setVisibility(View.VISIBLE);
                calorieDetailsText.setText(getResources().getString(
                        R.string.daily_calorie_intake_on));

            }
        } catch (Exception a) {
            a.printStackTrace();
        }

        if (unitItem.size() > 0) {


            itemHeight = unitItem.get(0);

            if (itemHeight.getUnitId().equalsIgnoreCase("1")) {

                //unitSpinner.setSelection(0);
                txtHeight.setText("Feet");
                feet.setHint("Feet");
                inches.setHint("Inches");

            } else {
                //unitSpinner.setSelection(1);
                //	txtHeight.setText("Cm");

                txtHeight.setText("Cm");
                feet.setHint("cm");
                inches.setHint("mm");
            }


            itemWeight = unitItem.get(1);

            if (itemWeight.getUnitId().equalsIgnoreCase("3")) {

                //weightUnitSpinner.setSelection(1);
                txtWeight.setText("Lbs");
                //txtWeight.setHint("Lbs");

            } else {
                //weightUnitSpinner.setSelection(0);

                txtWeight.setText("Kg");
                //	txtWeight.setHint("Kg");
            }
        }


        if (root_id != 1) {
            UserInfoDAO userInfo = UserInfoModule.getUserInformation(databaseObject);


            String dateString = userInfo.getDateOfBirth();

            SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
            try {
                Date date = format.parse(dateString);


                String stringMonth = (String) android.text.format.DateFormat.format("MMM", date); //Jun
                String stringyear = (String) android.text.format.DateFormat.format("yyyy", date); //2013
                String stringday = (String) android.text.format.DateFormat.format("dd", date); //20

                month.setText(stringMonth);
                day.setText(stringday);
                year.setText(stringyear);

                Calendar cal = Calendar.getInstance();
                cal.setTime(date);


                mYear = cal.get(Calendar.YEAR);
                mMonth = cal.get(Calendar.MONTH);
                mDay = cal.get(Calendar.DAY_OF_MONTH);

                dobDate = Calendar.getInstance();
                dobDate.set(mYear, mMonth, mDay);

                System.out.println(date);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            firstName.setText(userInfo.getFirstName());
            lastName.setText(userInfo.getLastName());

            feet.setText(userInfo.getHeightFt());
            inches.setText(userInfo.getHeightInc());


            if (txtWeight.getText().toString().equalsIgnoreCase("Lbs")) {

                double wt, f_wt;
                wt = Double.parseDouble(userInfo.getWeight());
                f_wt = wt * 2.2046;
                int _weightOz = 0;
                Log.e(" Getting value in oz ", String.valueOf(f_wt));
                _weightOz = (int) f_wt;
                DecimalFormat formatter = new DecimalFormat("##.##");
                String yourFormattedString = formatter.format(f_wt);
                //	dataList.get(arg0).getMealWeight()

                Log.e(" Getting value in oz ", String.valueOf(yourFormattedString));
                //	txtViewTotalWeight.setText(yourFormattedString+" lbs");
                //weight.setText(yourFormattedString);
                weight.setText(String.valueOf(_weightOz));
            } else {
                if (Constants.gunitwt == 0) {

                    weight.setText(userInfo.getWeight());

                } else {
                    double wt, f_wt;
                    wt = Double.parseDouble(userInfo.getWeight());
                    f_wt = wt * 2.2046;

                    Log.e(" Getting value in oz ", String.valueOf(f_wt));

                    DecimalFormat formatter = new DecimalFormat("##.##");
                    String yourFormattedString = formatter.format(f_wt);
                    //	dataList.get(arg0).getMealWeight()
                    Log.e(" Getting value in oz ", String.valueOf(yourFormattedString));
                    //	txtViewTotalWeight.setText(yourFormattedString+" lbs");
                    weight.setText(yourFormattedString);
                }
            }
            //	weight.setText(userInfo.getWeight());


            String myStringGender = userInfo.getGender();
            int spinnerPosition = genderSelectAdapter.getPosition(myStringGender);
            //genderSelectSpinner.setSelection(spinnerPosition);
            txtGender.setText(myStringGender);

            String myStringActivity = userInfo.getActivityLevel();
            int spinnerPositionActivity = activityLevelSpinnerAdapter.getPosition(myStringActivity);
            //activityLevelSpinner.setSelection(spinnerPositionActivity);
            txtActivityLevel.setText(myStringActivity);

            imagePath = userInfo.getPicturePath();


            if (imagePath != null && !imagePath.equalsIgnoreCase("")) {
                //setFullImageFromFilePath(dataList.get(position).getPicturePath(),holder.profilePic);
                //Bitmap myBitmap = BitmapFactory.decodeFile(imagePath);
                //profileImage.setImageBitmap(myBitmap);

                //setFullImageFromFilePath(imagePath, profileImage);
                selectPicturePath = imagePath;
                Bitmap myBitmap = HandelOutfemoryException.convertBitmap(imagePath);
                //profileImage.setImageBitmap(myBitmap);
                Picasso.with(getActivity()).load("file:" + imagePath).noFade().resize(80, 80).centerCrop().into(profileImage);
            } else {

                if (myStringGender.equalsIgnoreCase("Male")) {

                    profileImage.setImageResource(R.drawable.user_male);

                } else {

                    profileImage.setImageResource(R.drawable.user_female);
                }
            }


        } else {
            back.setVisibility(view.GONE);
        }


        //avinash

        userDb = new UserInfoModule(getActivity());

        caloryIntake = userDb.getCaloryTake();

        txtCaloryTakeset.setText(caloryIntake);
        editTextCaloryUpdate.setText(caloryIntake);

        heading.setText("Settings");
        settings.setVisibility(View.GONE);

		/*switchOn.setText("ON");
        dailyCalorieIntakeLinear.setVisibility(View.GONE);
		calorieDetailsText.setText(getResources().getString(
				R.string.daily_calorie_intake_on));*/

        switchOn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                if (switchOn.getText().toString().equalsIgnoreCase("ON")) {

                    switchOn.setText("OFF");
                    dailyCalorieIntakeLinear.setVisibility(View.VISIBLE);
                    frameCalorySet.setVisibility(View.GONE);
                    calorieDetailsText.setText(getResources().getString(
                            R.string.daily_calorie_intake_off));
                    editTextCaloryUpdate.setText(caloryIntake);
                    SaveSharedPreferences.saveCalorieDetail(getActivity(), Constants.USER_ID, "0");

                } else {

                    switchOn.setText("ON");
                    dailyCalorieIntakeLinear.setVisibility(View.GONE);
                    frameCalorySet.setVisibility(View.VISIBLE);
                    calorieDetailsText.setText(getResources().getString(
                            R.string.daily_calorie_intake_on));
                    SaveSharedPreferences.saveCalorieDetail(getActivity(), Constants.USER_ID, "1");
                }

            }
        });
		


        feet.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (!hasFocus) { // lost focus

                    //  v.setEnabled(false);
                    Log.e("feet focus", "false");
                } else {
                    //you are already enabling on button click
                    // v.setEnabled(true);

                    Log.e("feet focus", "true");
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(feet, InputMethodManager.SHOW_FORCED);
                    /// ((InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                }

            }
        });


        weight.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (!hasFocus) { // lost focus

                    //  v.setEnabled(false);
                    Log.e("feet focus", "false");
                } else {
                    //you are already enabling on button click
                    // v.setEnabled(true);

                    Log.e("feet focus", "true");
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(weight, InputMethodManager.SHOW_FORCED);
                    /// ((InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                }

            }
        });


        lastName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    clickDate();
                    return true;
                }
                return false;
            }
        });

        inches.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    dialogActivityLevel();
                    return true;
                }
                return false;
            }
        });

        weight.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    View view = getActivity().getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    dialogGendre();
                    return true;
                }
                return false;
            }
        });

        feet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                calculation();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        inches.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                calculation();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                calculation();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        return view;
    }

    @OnClick(R.id.backLiner)
    public void back() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        getActivity().onBackPressed();

    }

    @OnClick(R.id.Settings)
    public void gotoSettings() {

        SettingsFragment fragment = new SettingsFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("root_id", R.id.root_profile_frame);
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();
        transaction.add(R.id.root_profile_frame, fragment, "SettingsFragment");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack("SettingsFragment");
        transaction.commit();

    }

    @SuppressWarnings({"deprecation", "deprecation"})
    @OnClick(R.id.profileImage)
    public void selectImage() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }


        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_DARK).create();


        alertDialog.setMessage("Choose profile image");

        alertDialog.setButton("Camera", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dispatchTakePictureIntent();
                alertDialog.dismiss();
            }
        });
        alertDialog.setButton2("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                alertDialog.dismiss();

                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, IMAGE_PICKER_SELECT);
            }
        });


        alertDialog.show();

    }


    @OnClick(R.id.Save_UserInfo)
    public void save() {

        showProgressMessage("FitMi", "Saving profile data");

        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        TextView[] views = {firstName, day, feet, inches, weight};
        String[] msg = {"First name cannot be blank!", "DOB cannot be blank!", "Feet cannot be blank!", "Inch cannot be blank!", "Weight cannot be blank!"};


        firstname = firstName.getText().toString();
        lastname = lastName.getText().toString();
        daySet = day.getText().toString();
        feetSet = feet.getText().toString();
        inchesSet = inches.getText().toString();
        weightSet = weight.getText().toString();

        if (firstname.equalsIgnoreCase("")) {
            //Toast.makeText(getActivity(), "First name cannot be blank!", Toast.LENGTH_LONG).show();
            hideProgressDialog();
            showAlert("First name cannot be blank!");
            return;
        } else if (lastname.equalsIgnoreCase("")) {
            //Toast.makeText(getActivity(), "Last name cannot be blank!", Toast.LENGTH_LONG).show();
            hideProgressDialog();
            showAlert("Last name cannot be blank!");
            return;
        } else if (daySet.equalsIgnoreCase("")) {
            //Toast.makeText(getActivity(), "DOB cannot be blank!", Toast.LENGTH_LONG).show();
            hideProgressDialog();
            showAlert("DOB cannot be blank!");
            return;
        } else if (feetSet.equalsIgnoreCase("")) {
            //Toast.makeText(getActivity(), "Feet cannot be blank!", Toast.LENGTH_LONG).show();
            hideProgressDialog();
            showAlert("Feet cannot be blank!");
            return;
        } else if (inchesSet.equalsIgnoreCase("") || (Integer.parseInt(inchesSet) > 11)) {
            //Toast.makeText(getActivity(), "Inch cannot be blank!", Toast.LENGTH_LONG).show();
            hideProgressDialog();
            if (inchesSet.equalsIgnoreCase("")) {
                showAlert("Inch cannot be blank!");
            } else if ((Integer.parseInt(inchesSet) > 11)) {
                showAlert("Wrong input!");
            }
            return;
        } else if (weightSet.equalsIgnoreCase("")) {
            //Toast.makeText(getActivity(), "Weight cannot be blank!", Toast.LENGTH_LONG).show();
            hideProgressDialog();
            showAlert("Weight cannot be blank!");
            return;
        }

        calculation();
        saveValuesToDB();

    }

    @OnClick(R.id.Cancel_UserInfo)
    public void cancel() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        getActivity().onBackPressed();

    }


    @OnClick(R.id.Month)
    public void clickMonth() {

        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        DialogFragment newFragment = new SelectDateFragment();
        newFragment.show(getFragmentManager(), "DatePicker");
        newFragment.setCancelable(false);

    }

    @OnClick(R.id.Day)
    public void clickDay() {

        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        DialogFragment newFragment = new SelectDateFragment();
        newFragment.show(getFragmentManager(), "DatePicker");
        newFragment.setCancelable(false);
    }

    @OnClick(R.id.Year)
    public void clickYear() {

        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        DialogFragment newFragment = new SelectDateFragment();
        newFragment.show(getFragmentManager(), "DatePicker");
        newFragment.setCancelable(false);
    }

/*	@OnClick(R.id.lastName)
	public void clickDate() {

		DialogFragment newFragment = new SelectDateFragment();
		newFragment.show(getFragmentManager(), "DatePicker");
		newFragment.setCancelable(false);
	}*/


    public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            mYear = 0;
            mMonth = 0;
            mDay = 0;

            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePicker = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_DARK, this, mYear, mMonth, mDay);

            datePicker.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (which == DialogInterface.BUTTON_NEGATIVE) {
                        dialog.dismiss();
                    }
                }
            });


            return datePicker;
        }


        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            Log.e("onDateSet", "onDateSet");
            populateSetDate(yy, mm + 1, dd);
            feet.clearFocus();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    // Actions to do after 10 seconds
                    feet.setFocusableInTouchMode(true);
                    feet.setEnabled(true);
                    feet.requestFocus();
                }
            }, 500);
            //	feet.requestFocus();
			
			
		/*	InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(feet, InputMethodManager.SHOW_IMPLICIT);*/
        }

        public void populateSetDate(int selectedYear, int selectedMonth, int selectedDay) {


            mYear = selectedYear;
            mMonth = selectedMonth;
            mDay = selectedDay;


            month.setText((getMonth(mMonth)));
            day.setText(mDay + "");
            year.setText(mYear + "");

            //selectedDate = (getMonth(mMonth))+"/"+mDay+"/"+mYear;

            dobDate = Calendar.getInstance();
            dobDate.set(mYear, mMonth, mDay);

            calenderMilisecond = dobDate.getTimeInMillis();
            done_.setText(String.valueOf(month));

            calculation();

        }

    }


    private String getMonth(int month) {

        return new DateFormatSymbols().getMonths()[month - 1];

    }

    @OnClick(R.id.Base_Header)
    public void clickHeaderBase() {

    }

    /**
     * Photo Selection result
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_PICKER_SELECT && resultCode == Activity.RESULT_OK) {
            TabActivity activity = (TabActivity) getActivity();
            Bitmap bitmap = getBitmapFromCameraData(data, activity);
            //profileImage.setImageBitmap(bitmap);

            setFullImageFromFilePath(selectPicturePath, profileImage);

            imagePickClick = 1;
        } else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            addPhotoToGallery();
            TabActivity activity = (TabActivity) getActivity();
            // Show the full sized image.
            selectPicturePath = activity.getCurrentPhotoPath();
            setFullImageFromFilePath(activity.getCurrentPhotoPath(), profileImage);
            imagePickClick = 1;
            //setFullImageFromFilePath(activity.getCurrentPhotoPath(), mThumbnailImageView);
        } else {
            Toast.makeText(getActivity(), "Image Capture Failed", Toast.LENGTH_SHORT)
                    .show();
            imagePickClick = 0;
        }
    }


    public Bitmap getBitmapFromCameraData(Intent data, Context context) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        selectPicturePath = picturePath;
        return BitmapFactory.decodeFile(picturePath);
    }

    protected void dispatchTakePictureIntent() {
        // Check if there is a camera.
        Context context = getActivity();
        PackageManager packageManager = context.getPackageManager();
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA) == false) {
            Toast.makeText(getActivity(), "This device does not have a camera.", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        // Camera exists? Then proceed...
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        TabActivity activity = (TabActivity) getActivity();
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            // Create the File where the photo should go.
            // If you don't do this, you may get a crash in some devices.
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast toast = Toast.makeText(activity, "There was a problem saving the photo...", Toast.LENGTH_SHORT);
                toast.show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri fileUri = Uri.fromFile(photoFile);
                activity.setCapturedImageURI(fileUri);
                activity.setCurrentPhotoPath(fileUri.getPath());
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        activity.getCapturedImageURI());
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    protected File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        TabActivity activity = (TabActivity) getActivity();
        activity.setCurrentPhotoPath("file:" + image.getAbsolutePath());
        return image;
    }

    protected void addPhotoToGallery() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        TabActivity activity = (TabActivity) getActivity();
        File f = new File(activity.getCurrentPhotoPath());
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.getActivity().sendBroadcast(mediaScanIntent);
    }

    private void setFullImageFromFilePath(String imagePath, ImageView imageView) {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, bmOptions);
        //	imageView.setImageBitmap(bitmap);
        Picasso.with(getActivity()).load("file:" + imagePath).noFade().resize(80, 80).centerCrop().into(imageView);
    }


    public static Bitmap cropToSquare(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = (height > width) ? width : height;
        int newHeight = (height > width) ? height - (height - width) : height;
        int cropW = (width - height) / 2;
        cropW = (cropW < 0) ? 0 : cropW;
        int cropH = (height - width) / 2;
        cropH = (cropH < 0) ? 0 : cropH;
        Bitmap cropImg = Bitmap.createBitmap(bitmap, cropW, cropH, newWidth, newHeight);

        return cropImg;
    }

    /**
     * ==================================================
     */

    public void calculation() {
        try {
            String feetValue = feet.getText().toString();
            String inchValue = inches.getText().toString();
            if (feetValue != null && !feetValue.equals(""))
                _ftintVal = Integer.parseInt(feetValue);
            if (inchValue != null && !inchValue.equals(""))
                inchVal = Float.parseFloat(inchValue);
            int inft = 0;
            double _total, _final = 0.0, ininch = 0.0;
            if (feetValue != null && !feetValue.equals(""))
                inft = Integer.parseInt(feetValue);
            if (inchValue != null && !inchValue.equals(""))
                ininch = Float.parseFloat(inchValue);
            inft = inft * 12;
            ininch = ininch / 10;
            ininch = ininch * 12;
            _final = inft + ininch;

            _total = _final * 2.54;
            DecimalFormat formatter_two = new DecimalFormat("##.##");
            String finaltotal = formatter_two.format(_total);
            cmVal = Float.parseFloat(finaltotal);

            if (txtWeight.getText().toString().equalsIgnoreCase("Lbs")) {

                String weightValue = weight.getText().toString();
                if (weightValue != null && !weightValue.equals(""))
                    LbsVal = Float.parseFloat(weightValue);
                KgVal = (float) (LbsVal / 2.2);
                DecimalFormat formatter = new DecimalFormat("##.##");
                String _KgVal = formatter.format(KgVal);
                KgVal = Float.valueOf(_KgVal);
            } else {
                String weightValue = weight.getText().toString();
                if (weightValue != null && !weightValue.equals(""))
                    KgVal = Float.parseFloat(weightValue);

            }
            years = getDiffYears();

            if (gender.equalsIgnoreCase("Male")) {
                //For Men
                BMRVal = (float) (.789664634126607 * (66 + (13.75 * KgVal) + (5.0 * cmVal) - (6.8 * years)));
            } else if (gender.equalsIgnoreCase("Female")) {
                //For Women
                BMRVal = (float) (.791052486677913 * (665 + (9.56 * KgVal) + (1.8 * cmVal) - (4.7 * years)));
            }
            activeLevel = txtActivityLevel.getText().toString();

            if (activeLevel.equalsIgnoreCase("Low")) {
                //For Low Activity Level
                IntakeVal = BMRVal * 1.2;
            } else if (activeLevel.equalsIgnoreCase("Moderate")) {
                //For Medium Activity Level
                IntakeVal = BMRVal * 1.55;
            } else if (activeLevel.equalsIgnoreCase("High")) {
                //For High Activity Level
                IntakeVal = BMRVal * 1.725;
            }
            Log.e("IntakeVal","IntakeVal=="+IntakeVal);
            editTextCaloryUpdate.setText(String.format("%.0f", IntakeVal));
            txtCaloryTakeset.setText(String.format("%.0f", IntakeVal));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void saveValuesToDB() {

        BurnedVal = IntakeVal - BMRVal;
        UserInfoDAO userInfoData = new UserInfoDAO();

        String date = day.getText().toString() + "/" + month.getText().toString() + "/" + year.getText().toString();

        userInfoData.setUserId(Constants.USER_ID);
        userInfoData.setFirstName(firstName.getText().toString());
        userInfoData.setLastName(lastName.getText().toString());
        userInfoData.setDateOfBirth(date);


        //	userInfoData.setHeightFt(feet.getText().toString());
        //	userInfoData.setHeightInc(inches.getText().toString());
        userInfoData.setHeightFt(String.valueOf(_ftintVal));
        userInfoData.setHeightInc(String.valueOf(inchVal));
        //userInfoData.setWeight(weight.getText().toString());
        userInfoData.setWeight(String.valueOf(KgVal));

        userInfoData.setDailyCaloryIntake(String.format("%.0f", IntakeVal));

        userInfoData.setActivityLevel(activeLevel);
        userInfoData.setGender(gender);
        userInfoData.setPicturePath(selectPicturePath);

        if (root_id != 1) {
            UserInfoModule.editCalory(userInfoData, databaseObject);
            userProfileId = UserInfoModule.getProfileId(databaseObject);
            Constants.PROFILE_ID = userProfileId;
            SaveSharedPreferences.saveProfileID(getActivity(), Constants.PROFILE_ID);

        } else {
            UserInfoModule.insertUserInformation(userInfoData, databaseObject);

            userProfileId = UserInfoModule.getProfileId(databaseObject);
            Constants.PROFILE_ID = userProfileId;
            SaveSharedPreferences.saveProfileID(getActivity(), Constants.PROFILE_ID);
        }

        CaloryBaselineDAO baseLineData = new CaloryBaselineDAO();

        baseLineData.setUserProfileId(userProfileId);
        baseLineData.setUserId(Constants.USER_ID);
        //baseLineData.setTotalIntake(String.valueOf(IntakeVal));
        //baseLineData.setTotalBurned(String.valueOf(BurnedVal));
        baseLineData.setTotalIntake(String.format("%.0f", IntakeVal));
        baseLineData.setTotalBurned(String.format("%.0f", BurnedVal));
        baseLineData.setWeight(String.valueOf(KgVal));
        baseLineData.setWater("8");
        baseLineData.setSleep("7");
        baseLineData.setBpDia("80");
        baseLineData.setBpSys("120");

        if (root_id != 1) {

            UserInfoModule.editCaloryBaseline(baseLineData, databaseObject);

            String wTxt = txtWeight.getText().toString();

            if (wTxt.equalsIgnoreCase("Kg")) {

                unitDataWeight = new UnitItemDAO();
                Constants.gunitwt = 0;
                unitDataWeight.setProfileId(Constants.PROFILE_ID);
                unitDataWeight.setUserId(Constants.USER_ID);
                unitDataWeight.setType("weight");
                unitDataWeight.setUnitId("4");

            } else {

                unitDataWeight = new UnitItemDAO();
                Constants.gunitwt = 1;
                unitDataWeight.setProfileId(Constants.PROFILE_ID);
                unitDataWeight.setUserId(Constants.USER_ID);
                unitDataWeight.setType("weight");
                unitDataWeight.setUnitId("3");
            }


            if (Constants.gunitfw == 0) {
                Constants.gunitfw = 0;

                unitDataFood_Weight = new UnitItemDAO();

                unitDataFood_Weight.setProfileId(Constants.PROFILE_ID);
                unitDataFood_Weight.setUserId(Constants.USER_ID);
                unitDataFood_Weight.setType("food_weight");
                unitDataFood_Weight.setUnitId(String.valueOf(7));

            } else {
                Constants.gunitfw = 1;

                unitDataFood_Weight = new UnitItemDAO();

                unitDataFood_Weight.setProfileId(Constants.PROFILE_ID);
                unitDataFood_Weight.setUserId(Constants.USER_ID);
                unitDataFood_Weight.setType("food_weight");
                unitDataFood_Weight.setUnitId(String.valueOf(8));
            }


            if (Constants.gunitbp == 0) {
                Constants.gunitbp = 0;


                unitDataBp = new UnitItemDAO();

                unitDataBp.setProfileId(Constants.PROFILE_ID);
                unitDataBp.setUserId(Constants.USER_ID);
                unitDataBp.setType("blood_pressure");
                unitDataBp.setUnitId(String.valueOf(5));
            } else {
                Constants.gunitbp = 1;


                unitDataBp = new UnitItemDAO();

                unitDataBp.setProfileId(Constants.PROFILE_ID);
                unitDataBp.setUserId(Constants.USER_ID);
                unitDataBp.setType("blood_pressure");
                unitDataBp.setUnitId(String.valueOf(6));
            }

            String heightTxt = txtHeight.getText().toString();


            if (heightTxt.equalsIgnoreCase("Feet")) {
                Constants.gunitht = 0;

                unitDataHeight = new UnitItemDAO();

                unitDataHeight.setProfileId(Constants.PROFILE_ID);
                unitDataHeight.setUserId(Constants.USER_ID);
                unitDataHeight.setType("height");
                unitDataHeight.setUnitId(String.valueOf(1));

            } else {
                Constants.gunitht = 1;

                unitDataHeight = new UnitItemDAO();

                unitDataHeight.setProfileId(Constants.PROFILE_ID);
                unitDataHeight.setUserId(Constants.USER_ID);
                unitDataHeight.setType("height");
                unitDataHeight.setUnitId(String.valueOf(2));

            }

            unitModel.setUnitLog(unitDataHeight);
            unitModel.setUnitLog(unitDataWeight);
            unitModel.setUnitLog(unitDataBp);
            unitModel.setUnitLog(unitDataFood_Weight);

        } else {

            UserInfoModule.insertCaloryBaseline(baseLineData, databaseObject);


            String wTxt = txtWeight.getText().toString();

            if (wTxt.equalsIgnoreCase("Kg")) {

                unitDataWeight = new UnitItemDAO();
                Constants.gunitwt = 0;
                unitDataWeight.setProfileId(Constants.PROFILE_ID);
                unitDataWeight.setUserId(Constants.USER_ID);
                unitDataWeight.setType("weight");
                unitDataWeight.setUnitId("4");

            } else {

                unitDataWeight = new UnitItemDAO();
                Constants.gunitwt = 1;
                unitDataWeight.setProfileId(Constants.PROFILE_ID);
                unitDataWeight.setUserId(Constants.USER_ID);
                unitDataWeight.setType("weight");
                unitDataWeight.setUnitId("3");
            }


            String heightTxt = txtHeight.getText().toString();


            if (heightTxt.equalsIgnoreCase("Feet")) {
                Constants.gunitht = 0;

                unitDataHeight = new UnitItemDAO();

                unitDataHeight.setProfileId(Constants.PROFILE_ID);
                unitDataHeight.setUserId(Constants.USER_ID);
                unitDataHeight.setType("height");
                unitDataHeight.setUnitId(String.valueOf(1));

            } else {
                Constants.gunitht = 1;

                unitDataHeight = new UnitItemDAO();

                unitDataHeight.setProfileId(Constants.PROFILE_ID);
                unitDataHeight.setUserId(Constants.USER_ID);
                unitDataHeight.setType("height");
                unitDataHeight.setUnitId(String.valueOf(2));

            }


            Constants.gunitfw = 0;

            unitDataFood_Weight = new UnitItemDAO();

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


            unitModel.setUnitLog(unitDataHeight);
            unitModel.setUnitLog(unitDataWeight);
            unitModel.setUnitLog(unitDataBp);
            unitModel.setUnitLog(unitDataFood_Weight);
        }

//send value here
        saveUserDetails(JSONParser.HOST_URL_DYNAMIC + "fitmiwebservice/index.php/put/user/save_profile", userInfoData);
        System.out.println("Intake===" + IntakeVal + "===Burned===" + BurnedVal + "===BMR===" + BMRVal);


    }


    public int getDiffYears() {//Date first, Date last
		/*Calendar a = getCalendar(first);
		Calendar b = getCalendar(last);
		int diff = b.get(b.YEAR) - a.get(a.YEAR);
		if (a.get(a.MONTH) > b.get(b.MONTH) || 
				(a.get(a.MONTH) == b.get(b.MONTH) && a.get(a.DATE) > b.get(b.DATE))) {
			diff--;
		}*/

        // Set this to date to check
        Calendar today = Calendar.getInstance();
        int curYear = today.get(Calendar.YEAR);
        int curMonth = today.get(Calendar.MONTH);
        int curDay = today.get(Calendar.DAY_OF_MONTH);

        int year = dobDate.get(Calendar.YEAR);
        int month = dobDate.get(Calendar.MONTH);
        int day = dobDate.get(Calendar.DAY_OF_MONTH);

        int age = curYear - year;
        if (curMonth < month || (month == curMonth && curDay < day)) {
            age--;
        }

        return age;
    }

    public Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }


    /**
     * Dialog add item
     */

    public void dialogHeightUnit() {
        RadioGroup radioGroup;

        final Dialog dialog = new Dialog(getActivity()/*,R.style.Theme_Dialog*/);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.radio_dialog);

        radioGroup = (RadioGroup) dialog.findViewById(R.id.radioHeight);

        RadioButton rbFeet = (RadioButton) dialog.findViewById(R.id.radioFeet);
        RadioButton rbCm = (RadioButton) dialog.findViewById(R.id.radioCm);

        String heightTxt = txtHeight.getText().toString();

        if (heightTxt.equalsIgnoreCase("Feet")) {

            rbFeet.setChecked(true);

        } else {

            rbCm.setChecked(true);
        }

        radioGroup.getChildAt(0).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                txtHeight.setText("Feet");
                feet.setHint("Feet");
                inches.setHint("Inches");
                dialog.dismiss();
            }
        });

        radioGroup.getChildAt(1).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                txtHeight.setText("Cm");
                feet.setHint("cm");
                inches.setHint("mm");
                dialog.dismiss();
            }
        });


        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton rb = (RadioButton) group.findViewById(checkedId);

                if (null != rb && checkedId > -1) {

                    String selectText = rb.getText().toString();

                    txtHeight.setText(rb.getText());


                    if (selectText.equalsIgnoreCase("Feet")) {

                        feet.setHint("Feet");
                        inches.setHint("Inches");

                        //UNIT_SELECT_POSITION = position;


                    } else {

                        feet.setHint("cm");
                        inches.setHint("mm");

                        //UNIT_SELECT_POSITION = position;

                    }

                    dialog.dismiss();
                }

            }
        });

        dialog.show();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog.getWindow().setLayout((6 * width) / 7, LayoutParams.WRAP_CONTENT);

    }


    public void dialogWeightUnit() {
        RadioGroup radioGroup;

        final Dialog dialog = new Dialog(getActivity()/*,R.style.Theme_Dialog*/);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.radio_dialog);

        radioGroup = (RadioGroup) dialog.findViewById(R.id.radioHeight);

        RadioButton rbFeet = (RadioButton) dialog.findViewById(R.id.radioFeet);
        RadioButton rbCm = (RadioButton) dialog.findViewById(R.id.radioCm);
        TextView heading = (TextView) dialog.findViewById(R.id.userDialogHeading);

        heading.setText("Select Weight");

        rbFeet.setText("Lbs");
        rbCm.setText("Kg");

        String heightTxt = txtWeight.getText().toString();

        if (heightTxt.equalsIgnoreCase("Kg")) {

            rbCm.setChecked(true);

        } else {

            rbFeet.setChecked(true);
        }

        radioGroup.getChildAt(0).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                weight.setHint("Lbs");
                txtWeight.setText("Lbs");
                dialog.dismiss();
            }
        });

        radioGroup.getChildAt(1).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                weight.setHint("Kg");
                txtWeight.setText("Kg");
                dialog.dismiss();
            }
        });


        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton rb = (RadioButton) group.findViewById(checkedId);

                if (null != rb && checkedId > -1) {

                    String selectText = rb.getText().toString();

                    txtWeight.setText(rb.getText());


                    if (selectText.equalsIgnoreCase("Kg")) {

                        weight.setHint("Kg");


                    } else {

                        weight.setHint("Lbs");

                    }

                    dialog.dismiss();
                }

            }

        });


        dialog.show();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog.getWindow().setLayout((6 * width) / 7, LayoutParams.WRAP_CONTENT);

    }


    public void dialogActivityLevel() {


        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        RadioGroup radioGroup;

        final Dialog dialog = new Dialog(getActivity()/*,R.style.Theme_Dialog*/);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activitylevel_dialog);

        radioGroup = (RadioGroup) dialog.findViewById(R.id.radioHeight);

        RadioButton rbLow = (RadioButton) dialog.findViewById(R.id.radioLow);
        RadioButton rbmoderate = (RadioButton) dialog.findViewById(R.id.radioModerate);
        RadioButton rbHeight = (RadioButton) dialog.findViewById(R.id.radioHigh);


        String heightTxt = txtActivityLevel.getText().toString();

        if (heightTxt.equalsIgnoreCase("Low")) {

            rbLow.setChecked(true);

        } else if (heightTxt.equalsIgnoreCase("Moderate")) {

            rbmoderate.setChecked(true);
        } else {

            rbHeight.setChecked(true);
        }

        radioGroup.getChildAt(0).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String selectText = "Low";

                txtActivityLevel.setText("Low");

                activeLevel = selectText;


                dialog.dismiss();

                weight.clearFocus();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // Actions to do after 10 seconds
                        weight.setFocusableInTouchMode(true);
                        weight.setEnabled(true);
                        weight.requestFocus();
                    }
                }, 500);

            }
        });

        radioGroup.getChildAt(1).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String selectText = "Moderate";

                txtActivityLevel.setText("Moderate");

                activeLevel = selectText;


                dialog.dismiss();

                weight.clearFocus();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // Actions to do after 10 seconds
                        weight.setFocusableInTouchMode(true);
                        weight.setEnabled(true);
                        weight.requestFocus();
                    }
                }, 500);
            }
        });

        radioGroup.getChildAt(2).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String selectText = "High";

                txtActivityLevel.setText("High");

                activeLevel = selectText;


                dialog.dismiss();

                weight.clearFocus();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // Actions to do after 10 seconds
                        weight.setFocusableInTouchMode(true);
                        weight.setEnabled(true);
                        weight.requestFocus();
                    }
                }, 500);
            }
        });
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton rb = (RadioButton) group.findViewById(checkedId);

                if (null != rb && checkedId > -1) {

                    String selectText = rb.getText().toString();

                    txtActivityLevel.setText(rb.getText());

                    activeLevel = selectText;


                    dialog.dismiss();

                    weight.clearFocus();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            // Actions to do after 10 seconds
                            weight.setFocusableInTouchMode(true);
                            weight.setEnabled(true);
                            weight.requestFocus();
                            calculation();
                        }
                    }, 500);
                }

            }
        });

        dialog.show();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog.getWindow().setLayout((6 * width) / 7, LayoutParams.WRAP_CONTENT);

    }


    public void dialogGendre() {

        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        RadioGroup radioGroup;

        final Dialog dialog = new Dialog(getActivity()/*,R.style.Theme_Dialog*/);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.radio_dialog);

        radioGroup = (RadioGroup) dialog.findViewById(R.id.radioHeight);

        RadioButton rbFeet = (RadioButton) dialog.findViewById(R.id.radioFeet);
        RadioButton rbCm = (RadioButton) dialog.findViewById(R.id.radioCm);
        TextView heading = (TextView) dialog.findViewById(R.id.userDialogHeading);

        heading.setText("Select Gender");

        rbFeet.setText("Male");
        rbCm.setText("Female");

        String heightTxt = txtGender.getText().toString();
        if (imagePath == null && imagePath.equalsIgnoreCase("")) {
            if (heightTxt.equalsIgnoreCase("Male")) {

                rbFeet.setChecked(true);

                profileImage.setImageResource(R.drawable.user_male);

            } else {

                rbCm.setChecked(true);

                profileImage.setImageResource(R.drawable.user_female);
            }
        } else {
            if (heightTxt.equalsIgnoreCase("Male")) {

                rbFeet.setChecked(true);

            } else {

                rbCm.setChecked(true);

            }
        }

        radioGroup.getChildAt(0).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String selectText = "Male";

                txtGender.setText("Male");
                if (imagePath == null && imagePath.equalsIgnoreCase("")) {
                    if (selectText.equalsIgnoreCase("Male")) {

                        profileImage.setImageResource(R.drawable.user_male);

                    }
                }

                gender = selectText;
                dialog.dismiss();
            }
        });

        radioGroup.getChildAt(1).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String selectText = "Female";

                txtGender.setText("Female");
                if (imagePath == null && imagePath.equalsIgnoreCase("")) {
                    if (selectText.equalsIgnoreCase("Female")) {

                        profileImage.setImageResource(R.drawable.user_female);

                    }
                }

                gender = selectText;
                dialog.dismiss();
            }
        });

        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton rb = (RadioButton) group.findViewById(checkedId);

                if (null != rb && checkedId > -1) {

                    String selectText = rb.getText().toString();

                    txtGender.setText(rb.getText());
                    if (imagePath == null && imagePath.equalsIgnoreCase("")) {
                        if (selectText.equalsIgnoreCase("Male")) {

                            profileImage.setImageResource(R.drawable.user_male);

                        } else {

                            profileImage.setImageResource(R.drawable.user_female);
                        }
                    }

                    gender = selectText;
                    calculation();
                    dialog.dismiss();
                }

            }
        });

        dialog.show();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog.getWindow().setLayout((6 * width) / 7, LayoutParams.WRAP_CONTENT);

    }

    @OnClick(R.id.txtHeight)
    public void onclickHeight() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        dialogHeightUnit();
    }

    @OnClick(R.id.txtWeight)
    public void onclickWeight() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        dialogWeightUnit();
    }

    @OnClick(R.id.txtActivityLevel)
    public void onclickActivityLevel() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        dialogActivityLevel();
    }


    @OnClick(R.id.txtGender)
    public void onclickGender() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        dialogGendre();
    }

    private void showAlert(String msg) {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        new AlertDialog.Builder(getActivity())
                .setTitle("Alert!")
                .setMessage(msg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    public void showImage(String filePath, ImageView myImage) {

        File imgFile = new File("/sdcard/Images/test_image.jpg");

        if (imgFile.exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            myImage.setImageBitmap(myBitmap);

        }
    }


    private void saveUserDetails(String Json_Url, final UserInfoDAO _userInfoData) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Json_Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //   Toast.makeText(SignInActivity.this,response,Toast.LENGTH_LONG).show();
                        Log.e("response using volley", response.toString());
                        hideProgressDialog();
                        userProfileId = UserInfoModule.getProfileId(databaseObject);
                        Constants.PROFILE_ID = userProfileId;

                        databaseObject.closeDataBase();


                        if (imagePickClick == 1) {
                            HashMap<String, PostObject> map = new HashMap<String, PostObject>();
                            PostObject _resource = getPostObject("image_upload", false);
                            PostObject _email = getPostObject(Constants.LOGIN_MAIL_ID, false);
                            PostObject _userid = getPostObject(Constants.USER_ID.toString(), false);
                            PostObject _imagepath = getPostObject(selectPicturePath, true);//(((TabActivity)getActivity()).getCurrentPhotoPath(), true);
                            PostObject acc_key = getPostObject(Constants.Access_key, false);
                            PostObject acc_token = getPostObject(Constants.Access_token, false);

                            map.put("resource", _resource);
                            map.put("username", _email);
                            map.put("users_id", _userid);
                            map.put("profile_image", _imagepath);

                            map.put("access_key", acc_key);
                            map.put("access_token", acc_token);

                            postTowebservice(image_listener, map);
                        } else {
                            imagePickClick = 0;
                            FragmentTransaction transaction = getFragmentManager()
                                    .beginTransaction();
                            getFragmentManager().popBackStack(null,
                                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            transaction.add(R.id.root_profile_frame, new UserProfileFragment(),
                                    "UserProfileFragment");
                            transaction.commit();

                            //avinash
                            try {


                                if (switchOn.getText().toString().equalsIgnoreCase("ON")) {
                                    caloryIntake = userDb.getCaloryTake();

                                } else {


                                    caloryIntake = editTextCaloryUpdate.getText().toString();
                                }
                                //	caloryIntake = editTextCaloryUpdate.getText().toString();
                                userDb.updateCaloryTake(caloryIntake);
                                //Toast.makeText(getActivity(), "Calory updated",
                                //			Toast.LENGTH_LONG).show();

                            } catch (Exception a) {
                                a.printStackTrace();

                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   Toast.makeText(getActivity(),"Error ",Toast.LENGTH_LONG).show();
                        hideProgressDialog();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                Log.e("access_key", Constants.Access_key);
                Log.e("users_id", Constants.USER_ID);
                Log.e("username", Constants.LOGIN_MAIL_ID);

                params.put("users_id", Constants.USER_ID);
                params.put("username", Constants.LOGIN_MAIL_ID);
                params.put("access_key", Constants.Access_key);
                params.put("access_token", Constants.Access_token);
                params.put("height_ft", _userInfoData.getHeightFt());
                params.put("height_in", _userInfoData.getHeightInc());

                String _dob_ = _userInfoData.getDateOfBirth();
                String dateString = _dob_;

                SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
                try {
                    Date date = format.parse(dateString);


                    String stringMonth = (String) android.text.format.DateFormat.format("MM", date); //Jun
                    String stringyear = (String) android.text.format.DateFormat.format("yyyy", date); //2013
                    String stringday = (String) android.text.format.DateFormat.format("dd", date); //20
                    //MM/dd/yyyy
                    _dob_ = stringMonth + "/" + stringday + "/" + stringyear;

                    DateFormat formatter = new SimpleDateFormat("MM/dd/yyy");
                    Date _date = (Date) formatter.parse(_dob_);
                    _dob_ = String.valueOf(_date.getTime());
                    System.out.println("Today is " + _date.getTime());

                    System.out.println(date);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                params.put("date_of_birth", _dob_);

                params.put("activity_level", _userInfoData.getActivityLevel());
                params.put("daily_calorie_intake", _userInfoData.getDailyCaloryIntake());


                if (txtWeight.getText().toString().equalsIgnoreCase("Lbs")) {
	    				/*double wt,f_wt;
	    				wt=Double.parseDouble(_userInfoData.getWeight());
	    				f_wt=wt*2.2046;
	    				int _weightOz=0;
	    				Log.e(" Getting value in oz ", String.valueOf(f_wt));
	    				_weightOz=(int) f_wt;
	    				DecimalFormat formatter = new DecimalFormat("##.##");
	    				String yourFormattedString = formatter.format(f_wt);
	    			
	    				
	    				Log.e(" Getting value in oz ", String.valueOf(yourFormattedString));
	    		
	    				weight.setText(String.valueOf(_weightOz));*/
                    params.put("weight_units", "lbs");
                } else {
                    if (Constants.gunitwt == 0) {
	    				
	    			/*	weight.setText(_userInfoData.getWeight());*/
                        params.put("weight_units", "kg");

                    } else {
	    				/*	double wt,f_wt;
	    					wt=Double.parseDouble(_userInfoData.getWeight());
	    					f_wt=wt*2.2046;
	    					
	    					Log.e(" Getting value in oz ", String.valueOf(f_wt));
	    					
	    					DecimalFormat formatter = new DecimalFormat("##.##");
	    					String yourFormattedString = formatter.format(f_wt);
	    				
	    					Log.e(" Getting value in oz ", String.valueOf(yourFormattedString));
	    			
	    					weight.setText(yourFormattedString);*/
                        params.put("weight_units", "lbs");
                    }
                }
                params.put("weight", _userInfoData.getWeight());

                //  params.put("weight_units","lbs");
                params.put("first_name", _userInfoData.getFirstName());
                params.put("last_name", _userInfoData.getLastName());
                params.put("gender", _userInfoData.getGender());

                Log.e("access_key", Constants.Access_key);
                Log.e("access_token", Constants.Access_token);


                return params;
            }

            @Override
            public Map<String, String> getHeaders()
                    throws AuthFailureError {
                // TODO Auto-generated method stub
                Map<String, String> params = new HashMap<String, String>();
                // Removed this line if you dont need it or Use application/json
                //   params.put("Accept", "application/x-www-form-urlencoded");
                //   params.put("Content-Type", "UTF-8");
                //   params.put("access_key",acc_key);
                //      params.put("access_token",acc_token);
                return params;
                //return super.getHeaders();
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    AsyncTaskListener image_listener = new AsyncTaskListener() {

        @Override
        public void onTaskPreExecute() {
            // TODO Auto-generated method stub
            showProgressMessage(getResources().getString(R.string.app_name),
                    "Please wait......");
        }

        @Override
        public void onTaskCompleted(String result) {
            // TODO Auto-generated method stub

            hideProgressDialog();

            imagePickClick = 0;
            FragmentTransaction transaction = getFragmentManager()
                    .beginTransaction();
            getFragmentManager().popBackStack(null,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
            transaction.add(R.id.root_profile_frame, new UserProfileFragment(),
                    "UserProfileFragment");
            transaction.commit();

            //avinash
            try {


                if (switchOn.getText().toString().equalsIgnoreCase("ON")) {
                    caloryIntake = userDb.getCaloryTake();

                } else {


                    caloryIntake = editTextCaloryUpdate.getText().toString();
                }
                //	caloryIntake = editTextCaloryUpdate.getText().toString();
                userDb.updateCaloryTake(caloryIntake);
                //Toast.makeText(getActivity(), "Calory updated",
                //			Toast.LENGTH_LONG).show();

            } catch (Exception a) {
                a.printStackTrace();

            }

        }
    };

    public void clickDate() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        DialogFragment newFragment = new SelectDateFragment();
        newFragment.show(getFragmentManager(), "DatePicker");
        newFragment.setCancelable(false);
    }

}
