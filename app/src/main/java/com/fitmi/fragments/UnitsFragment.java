package com.fitmi.fragments;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.db.modules.UnitModule;
import com.fitmi.R;
import com.fitmi.dao.UnitItemDAO;
import com.fitmi.utils.Constants;
import com.fitmi.utils.interFragmentScaleCommunicator;

public class UnitsFragment extends BaseFragment {

    int mRootId = 0;

    @InjectView(R.id.Heading)
    public TextView heading;

    @InjectView(R.id.Back)
    public ImageView back;

    @InjectView(R.id.backLiner)
    LinearLayout backLiner;

    @InjectView(R.id.Settings)
    public ImageView settings;


    @InjectView(R.id.txtSettingFt)
    TextView txtSettingFt;

    @InjectView(R.id.txtSettingcm)
    TextView txtSettingcm;

    @InjectView(R.id.txtSettingLb)
    TextView txtSettingLb;

    @InjectView(R.id.txtSettingKg)
    TextView txtSettingKg;

    @InjectView(R.id.txtSettingMmhg)
    TextView txtSettingMmhg;

    @InjectView(R.id.txtSettingKpa)
    TextView txtSettingKpa;

    @InjectView(R.id.txtSettingAha)
    TextView txtSettingAha;

    @InjectView(R.id.txtSettingWho)
    TextView txtSettingWho;

    @InjectView(R.id.txtSettingGm)
    TextView txtSettingGm;

    @InjectView(R.id.txtSettingOz)
    TextView txtSettingOz;

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
    interFragmentScaleCommunicator scaleCommunicator;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /* Inflate the layout for this fragment */
        View view = inflater.inflate(R.layout.fragment_units, container, false);
        scaleCommunicator = (interFragmentScaleCommunicator) getActivity();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mRootId = bundle.getInt("root_id", R.id.root_home_frame);
        }

        unitModel = new UnitModule(getActivity());

        unitItem = unitModel.selectUnitLogList();

        ButterKnife.inject(this, view);
        setNullClickListener(view);

        heading.setText("Settings");
        settings.setVisibility(View.GONE);

        if (unitItem.size() > 0) {

            for (int i = 0; i < unitItem.size(); i++) {
                UnitItemDAO unitObj = unitItem.get(i);

                if (unitObj.getType().equalsIgnoreCase("height")) {

                    if (unitObj.getUnitId().equalsIgnoreCase("1")) {
                        txtSettingFt.setBackgroundResource(R.drawable.royal_blue_button_bg);
                        txtSettingFt.setTextColor(Color.WHITE);

                        txtSettingcm.setBackgroundResource(R.drawable.royal_blue_inputbox_bg);
                        txtSettingcm.setTextColor(Color.parseColor("#31A4C3"));

                        Constants.gunitht = 0;
                    } else {

                        txtSettingFt.setBackgroundResource(R.drawable.royal_blue_inputbox_bg);
                        txtSettingFt.setTextColor(Color.parseColor("#31A4C3"));

                        txtSettingcm.setBackgroundResource(R.drawable.royal_blue_button_bg);
                        txtSettingcm.setTextColor(Color.WHITE);
                        Constants.gunitht = 1;
                    }
                } else if (unitObj.getType().equalsIgnoreCase("weight")) {

                    if (unitObj.getUnitId().equalsIgnoreCase("3")) {
                        txtSettingLb.setBackgroundResource(R.drawable.royal_blue_button_bg);
                        txtSettingLb.setTextColor(Color.WHITE);

                        txtSettingKg.setBackgroundResource(R.drawable.royal_blue_inputbox_bg);
                        txtSettingKg.setTextColor(Color.parseColor("#31A4C3"));
                        Constants.gunitwt = 1;
                    } else {

                        txtSettingLb.setBackgroundResource(R.drawable.royal_blue_inputbox_bg);
                        txtSettingLb.setTextColor(Color.parseColor("#31A4C3"));

                        txtSettingKg.setBackgroundResource(R.drawable.royal_blue_button_bg);
                        txtSettingKg.setTextColor(Color.WHITE);
                        Constants.gunitwt = 0;
                    }
                } else if (unitObj.getType().equalsIgnoreCase("blood_pressure")) {

                    if (unitObj.getUnitId().equalsIgnoreCase("5")) {
                        txtSettingMmhg.setBackgroundResource(R.drawable.royal_blue_button_bg);
                        txtSettingMmhg.setTextColor(Color.WHITE);

                        txtSettingKpa.setBackgroundResource(R.drawable.royal_blue_inputbox_bg);
                        txtSettingKpa.setTextColor(Color.parseColor("#31A4C3"));
                        Constants.gunitbp = 0;
                    } else {

                        txtSettingMmhg.setBackgroundResource(R.drawable.royal_blue_inputbox_bg);
                        txtSettingMmhg.setTextColor(Color.parseColor("#31A4C3"));

                        txtSettingKpa.setBackgroundResource(R.drawable.royal_blue_button_bg);
                        txtSettingKpa.setTextColor(Color.WHITE);
                        Constants.gunitbp = 1;
                    }
                } else if (unitObj.getType().equalsIgnoreCase("food_weight")) {

                    if (unitObj.getUnitId().equalsIgnoreCase("7")) {
                        txtSettingGm.setBackgroundResource(R.drawable.royal_blue_button_bg);
                        txtSettingGm.setTextColor(Color.WHITE);

                        txtSettingOz.setBackgroundResource(R.drawable.royal_blue_inputbox_bg);
                        txtSettingOz.setTextColor(Color.parseColor("#31A4C3"));
                        Constants.gunitfw = 0;
                    } else {

                        txtSettingGm.setBackgroundResource(R.drawable.royal_blue_inputbox_bg);
                        txtSettingGm.setTextColor(Color.parseColor("#31A4C3"));

                        txtSettingOz.setBackgroundResource(R.drawable.royal_blue_button_bg);
                        txtSettingOz.setTextColor(Color.WHITE);
                        Constants.gunitfw = 1;
                    }
                }
            }


            unitDataHeight = new UnitItemDAO();

            unitDataHeight.setProfileId(Constants.PROFILE_ID);
            unitDataHeight.setUserId(Constants.USER_ID);
            unitDataHeight.setType(unitItem.get(0).getType());
            unitDataHeight.setUnitId(unitItem.get(0).getUnitId());

            unitDataWeight = new UnitItemDAO();

            unitDataWeight.setProfileId(Constants.PROFILE_ID);
            unitDataWeight.setUserId(Constants.USER_ID);
            unitDataWeight.setType(unitItem.get(1).getType());
            unitDataWeight.setUnitId(unitItem.get(1).getUnitId());

            unitDataBp = new UnitItemDAO();

            unitDataBp.setProfileId(Constants.PROFILE_ID);
            unitDataBp.setUserId(Constants.USER_ID);
            unitDataBp.setType(unitItem.get(2).getType());
            unitDataBp.setUnitId(unitItem.get(2).getUnitId());

            unitDataFood_Weight = new UnitItemDAO();

            unitDataFood_Weight.setProfileId(Constants.PROFILE_ID);
            unitDataFood_Weight.setUserId(Constants.USER_ID);
            unitDataFood_Weight.setType(unitItem.get(3).getType());
            unitDataFood_Weight.setUnitId(unitItem.get(3).getUnitId());


        } else {

            txtSettingFt.setBackgroundResource(R.drawable.royal_blue_button_bg);
            txtSettingFt.setTextColor(Color.WHITE);

            txtSettingcm.setBackgroundResource(R.drawable.royal_blue_inputbox_bg);
            txtSettingcm.setTextColor(Color.parseColor("#31A4C3"));

            txtSettingLb.setBackgroundResource(R.drawable.royal_blue_button_bg);
            txtSettingLb.setTextColor(Color.WHITE);

            txtSettingKg.setBackgroundResource(R.drawable.royal_blue_inputbox_bg);
            txtSettingKg.setTextColor(Color.parseColor("#31A4C3"));

            txtSettingMmhg.setBackgroundResource(R.drawable.royal_blue_button_bg);
            txtSettingMmhg.setTextColor(Color.WHITE);

            txtSettingKpa.setBackgroundResource(R.drawable.royal_blue_inputbox_bg);
            txtSettingKpa.setTextColor(Color.parseColor("#31A4C3"));


            Constants.gunitwt = 0;
            Constants.gunitht = 0;
            Constants.gunitbp = 0;
            Constants.gunitfw = 0;

            unitDataHeight = new UnitItemDAO();

            unitDataHeight.setProfileId(Constants.PROFILE_ID);
            unitDataHeight.setUserId(Constants.USER_ID);
            unitDataHeight.setType(unitTypeHeight);
            unitDataHeight.setUnitId(String.valueOf(unitIdHeight));

            unitDataWeight = new UnitItemDAO();

            unitDataWeight.setProfileId(Constants.PROFILE_ID);
            unitDataWeight.setUserId(Constants.USER_ID);
            unitDataWeight.setType(unitTypeWeight);
            unitDataWeight.setUnitId(String.valueOf(unitIdWeight));

            unitDataBp = new UnitItemDAO();

            unitDataBp.setProfileId(Constants.PROFILE_ID);
            unitDataBp.setUserId(Constants.USER_ID);
            unitDataBp.setType(unitTypeBp);
            unitDataBp.setUnitId(String.valueOf(unitIdBp));

            unitDataFood_Weight = new UnitItemDAO();

            unitDataFood_Weight.setProfileId(Constants.PROFILE_ID);
            unitDataFood_Weight.setUserId(Constants.USER_ID);
            unitDataFood_Weight.setType(unitTypeFw);
            unitDataFood_Weight.setUnitId(String.valueOf(unitIdFw));
        }


        return view;
    }

    @OnClick(R.id.backLiner)
    public void back() {

        getActivity().onBackPressed();

    }

    @OnClick(R.id.Cancel_Units)
    public void cancel() {

        getActivity().onBackPressed();

    }

    @OnClick(R.id.Base_Header)
    public void clickHeaderBase() {

    }

    @OnClick(R.id.txtSettingFt)
    public void clickHeightFt() {
        txtSettingFt.setBackgroundResource(R.drawable.royal_blue_button_bg);
        txtSettingFt.setTextColor(Color.WHITE);

        txtSettingcm.setBackgroundResource(R.drawable.royal_blue_inputbox_bg);
        txtSettingcm.setTextColor(Color.parseColor("#31A4C3"));

        unitIdHeight = 1;
        unitTypeHeight = "height";


        Constants.gunitht = 0;

        unitDataHeight = new UnitItemDAO();

        unitDataHeight.setProfileId(Constants.PROFILE_ID);
        unitDataHeight.setUserId(Constants.USER_ID);
        unitDataHeight.setType(unitTypeHeight);
        unitDataHeight.setUnitId(String.valueOf(unitIdHeight));

    }

    @OnClick(R.id.txtSettingcm)
    public void clickHeightCm() {
        txtSettingFt.setBackgroundResource(R.drawable.royal_blue_inputbox_bg);
        txtSettingFt.setTextColor(Color.parseColor("#31A4C3"));

        txtSettingcm.setBackgroundResource(R.drawable.royal_blue_button_bg);
        txtSettingcm.setTextColor(Color.WHITE);

        unitIdHeight = 2;
        unitTypeHeight = "height";


        Constants.gunitht = 1;


        unitDataHeight = new UnitItemDAO();

        unitDataHeight.setProfileId(Constants.PROFILE_ID);
        unitDataHeight.setUserId(Constants.USER_ID);
        unitDataHeight.setType(unitTypeHeight);
        unitDataHeight.setUnitId(String.valueOf(unitIdHeight));
    }

    @OnClick(R.id.txtSettingLb)
    public void clickWeightLb() {
        txtSettingLb.setBackgroundResource(R.drawable.royal_blue_button_bg);
        txtSettingLb.setTextColor(Color.WHITE);

        txtSettingKg.setBackgroundResource(R.drawable.royal_blue_inputbox_bg);
        txtSettingKg.setTextColor(Color.parseColor("#31A4C3"));

        unitIdWeight = 3;
        unitTypeWeight = "weight";

        Constants.gunitwt = 1;


        unitDataWeight = new UnitItemDAO();


        unitDataWeight.setProfileId(Constants.PROFILE_ID);
        unitDataWeight.setUserId(Constants.USER_ID);
        unitDataWeight.setType(unitTypeWeight);
        unitDataWeight.setUnitId(String.valueOf(unitIdWeight));

    }

    @OnClick(R.id.txtSettingKg)
    public void clickWeightKg() {
        txtSettingLb.setBackgroundResource(R.drawable.royal_blue_inputbox_bg);
        txtSettingLb.setTextColor(Color.parseColor("#31A4C3"));

        txtSettingKg.setBackgroundResource(R.drawable.royal_blue_button_bg);
        txtSettingKg.setTextColor(Color.WHITE);

        unitIdWeight = 4;
        unitTypeWeight = "weight";

        Constants.gunitwt = 0;


        unitDataWeight = new UnitItemDAO();

        unitDataWeight.setProfileId(Constants.PROFILE_ID);
        unitDataWeight.setUserId(Constants.USER_ID);
        unitDataWeight.setType(unitTypeWeight);
        unitDataWeight.setUnitId(String.valueOf(unitIdWeight));
    }

    @OnClick(R.id.txtSettingMmhg)
    public void clickBpMmhg() {
        txtSettingMmhg.setBackgroundResource(R.drawable.royal_blue_button_bg);
        txtSettingMmhg.setTextColor(Color.WHITE);

        txtSettingKpa.setBackgroundResource(R.drawable.royal_blue_inputbox_bg);
        txtSettingKpa.setTextColor(Color.parseColor("#31A4C3"));

        unitIdBp = 5;
        unitTypeBp = "blood_pressure";


        Constants.gunitbp = 0;


        unitDataBp = new UnitItemDAO();

        unitDataBp.setProfileId(Constants.PROFILE_ID);
        unitDataBp.setUserId(Constants.USER_ID);
        unitDataBp.setType(unitTypeBp);
        unitDataBp.setUnitId(String.valueOf(unitIdBp));
    }

    @OnClick(R.id.txtSettingKpa)
    public void clickBpKpa() {
        txtSettingMmhg.setBackgroundResource(R.drawable.royal_blue_inputbox_bg);
        txtSettingMmhg.setTextColor(Color.parseColor("#31A4C3"));

        txtSettingKpa.setBackgroundResource(R.drawable.royal_blue_button_bg);
        txtSettingKpa.setTextColor(Color.WHITE);

        unitIdBp = 6;
        unitTypeBp = "blood_pressure";


        Constants.gunitbp = 1;


        unitDataBp = new UnitItemDAO();

        unitDataBp.setProfileId(Constants.PROFILE_ID);
        unitDataBp.setUserId(Constants.USER_ID);
        unitDataBp.setType(unitTypeBp);
        unitDataBp.setUnitId(String.valueOf(unitIdBp));

    }


    @OnClick(R.id.txtSettingAha)
    public void clickBpAha() {
        txtSettingAha.setBackgroundResource(R.drawable.royal_blue_button_bg);
        txtSettingAha.setTextColor(Color.WHITE);

        txtSettingWho.setBackgroundResource(R.drawable.royal_blue_inputbox_bg);
        txtSettingWho.setTextColor(Color.parseColor("#31A4C3"));

    }

    @OnClick(R.id.txtSettingWho)
    public void clickBpWho() {
        txtSettingAha.setBackgroundResource(R.drawable.royal_blue_inputbox_bg);
        txtSettingAha.setTextColor(Color.parseColor("#31A4C3"));

        txtSettingWho.setBackgroundResource(R.drawable.royal_blue_button_bg);
        txtSettingWho.setTextColor(Color.WHITE);

    }

    @OnClick(R.id.txtSettingGm)
    public void clickGram() {
        txtSettingOz.setBackgroundResource(R.drawable.royal_blue_inputbox_bg);
        txtSettingOz.setTextColor(Color.parseColor("#31A4C3"));

        txtSettingGm.setBackgroundResource(R.drawable.royal_blue_button_bg);
        txtSettingGm.setTextColor(Color.WHITE);

        unitIdFw = 7;
        unitTypeFw = "food_weight";


        Constants.gunitfw = 0;

        unitDataFood_Weight = new UnitItemDAO();

        unitDataFood_Weight.setProfileId(Constants.PROFILE_ID);
        unitDataFood_Weight.setUserId(Constants.USER_ID);
        unitDataFood_Weight.setType(unitTypeFw);
        unitDataFood_Weight.setUnitId(String.valueOf(unitIdFw));
    }

    @OnClick(R.id.txtSettingOz)
    public void clickOz() {
        txtSettingGm.setBackgroundResource(R.drawable.royal_blue_inputbox_bg);
        txtSettingGm.setTextColor(Color.parseColor("#31A4C3"));

        txtSettingOz.setBackgroundResource(R.drawable.royal_blue_button_bg);
        txtSettingOz.setTextColor(Color.WHITE);

        unitIdFw = 8;
        unitTypeFw = "food_weight";


        Constants.gunitfw = 1;

        unitDataFood_Weight = new UnitItemDAO();

        unitDataFood_Weight.setProfileId(Constants.PROFILE_ID);
        unitDataFood_Weight.setUserId(Constants.USER_ID);
        unitDataFood_Weight.setType(unitTypeFw);
        unitDataFood_Weight.setUnitId(String.valueOf(unitIdFw));
    }

    private BroadcastReceiver currentUnit = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getIntExtra("unit", 7) == 7) {
                txtSettingOz.setBackgroundResource(R.drawable.royal_blue_inputbox_bg);
                txtSettingOz.setTextColor(Color.parseColor("#31A4C3"));
                txtSettingGm.setBackgroundResource(R.drawable.royal_blue_button_bg);
                txtSettingGm.setTextColor(Color.WHITE);
                unitIdFw = 7;
                unitTypeFw = "food_weight";
                Constants.gunitfw = 0;
                unitDataFood_Weight = new UnitItemDAO();
                unitDataFood_Weight.setProfileId(Constants.PROFILE_ID);
                unitDataFood_Weight.setUserId(Constants.USER_ID);
                unitDataFood_Weight.setType(unitTypeFw);
                unitDataFood_Weight.setUnitId(String.valueOf(unitIdFw));
            } else {
                txtSettingGm.setBackgroundResource(R.drawable.royal_blue_inputbox_bg);
                txtSettingGm.setTextColor(Color.parseColor("#31A4C3"));
                txtSettingOz.setBackgroundResource(R.drawable.royal_blue_button_bg);
                txtSettingOz.setTextColor(Color.WHITE);
                unitIdFw = 8;
                unitTypeFw = "food_weight";
                Constants.gunitfw = 1;
                unitDataFood_Weight = new UnitItemDAO();
                unitDataFood_Weight.setProfileId(Constants.PROFILE_ID);
                unitDataFood_Weight.setUserId(Constants.USER_ID);
                unitDataFood_Weight.setType(unitTypeFw);
                unitDataFood_Weight.setUnitId(String.valueOf(unitIdFw));
            }

            unitModel.setUnitLog(unitDataHeight);
            unitModel.setUnitLog(unitDataWeight);
            unitModel.setUnitLog(unitDataBp);
            unitModel.setUnitLog(unitDataFood_Weight);
        }
    };

    @OnClick(R.id.Save_UserInfo)
    public void clickSave() {

        unitModel.setUnitLog(unitDataHeight);
        unitModel.setUnitLog(unitDataWeight);
        unitModel.setUnitLog(unitDataBp);
        unitModel.setUnitLog(unitDataFood_Weight);

        scaleCommunicator.changeUnits(unitDataFood_Weight.getUnitId());
        getActivity().onBackPressed();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (currentUnit != null) {
            IntentFilter intentFilter = new IntentFilter("NEW_UNIT");
            getActivity().registerReceiver(currentUnit, intentFilter);
        }
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(currentUnit);
        super.onDestroy();
    }
}
