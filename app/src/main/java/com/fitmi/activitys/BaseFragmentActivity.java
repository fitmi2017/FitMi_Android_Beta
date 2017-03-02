package com.fitmi.activitys;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;

import com.dts.classes.AsyncTaskListener;
import com.dts.classes.AsyncTaskMain;
import com.dts.classes.CommonFunction;
import com.dts.classes.PostObject;
import com.dts.classes.Pref;
import com.fitmi.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import cn.net.aicare.pabulumlibrary.bleprofile.BleProfileService;
import cn.net.aicare.pabulumlibrary.bleprofile.BleProfileServiceReadyActivity;
import cn.net.aicare.pabulumlibrary.entity.FoodData;
import cn.net.aicare.pabulumlibrary.utils.ParseData;

public abstract class BaseFragmentActivity extends BleProfileServiceReadyActivity {
//	public class BaseFragmentActivity extends BleProfileServiceReadyActivity {

    public CommonFunction mCommonFunction;
    public Pref _Pref;

    private ProgressDialog _progressDialog;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);

        mCommonFunction = new CommonFunction(BaseFragmentActivity.this);
        _Pref = new Pref(BaseFragmentActivity.this);
    }

    public void prepareKnives() {
        ButterKnife.inject(BaseFragmentActivity.this);
    }

	/*
     * @Override public void onBackPressed() { // TODO Auto-generated method
	 * stub ActivityManager mngr = (ActivityManager)
	 * getSystemService(ACTIVITY_SERVICE); List<ActivityManager.RunningTaskInfo>
	 * taskList = mngr .getRunningTasks(20); if (taskList.get(0).numActivities
	 * == 1) { AlertDialog.Builder builder = new AlertDialog.Builder(this);
	 * builder
	 * .setMessage("You are about to quit the application! Are you sure?");
	 * builder.setTitle("Quit " + getString(R.string.app_name));
	 * builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	 * 
	 * @Override public void onClick(DialogInterface arg0, int arg1) { // TODO
	 * Auto-generated method stub BaseFragmentActivity.this.finish();
	 * overridePendingTransition( R.anim.activityback_lefttoright,
	 * R.anim.activityback_righttoleft); } });
	 * builder.setNegativeButton("Not Now", null); builder.create();
	 * builder.show(); } else { this.finish();
	 * overridePendingTransition(R.anim.activityback_lefttoright,
	 * R.anim.activityback_righttoleft); }
	 * 
	 * }
	 */

    public void postTowebservice(AsyncTaskListener _listener,
                                 HashMap<String, PostObject> map) {
        if (this != null) {
            AsyncTaskMain _asyncTaskMain = new AsyncTaskMain(_listener, map,
                    this);
            _asyncTaskMain
                    .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
        }

    }

    public void postTowebservice(AsyncTaskListener _listener,
                                 ArrayList<String> keys, ArrayList<String> values) {
        if (this != null) {
            AsyncTaskMain _asyncTaskMain = new AsyncTaskMain(_listener, keys,
                    values, this);
            _asyncTaskMain
                    .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
        }

    }

    public PostObject getPostObject(String value, boolean isFile) {
        PostObject _postObject = new PostObject();
        _postObject.setFile(isFile);
        if (isFile) {
            _postObject.setFile_url(value);
        }
        _postObject.setString_value(value);
        return _postObject;
    }

    public void showProgressMessage(String title, String message) {

        if (title.startsWith("Team")) {
            title = "MyTeamConnector";
        }

        _progressDialog = new ProgressDialog(this,
                ProgressDialog.THEME_HOLO_LIGHT);
        _progressDialog.setTitle(title);
        _progressDialog.setMessage(message);
        _progressDialog.setCancelable(false);
        _progressDialog.show();
    }

    public void hideProgressDialog() {
        _progressDialog.dismiss();
    }

    public void hideKeyboard(EditText myEditText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
    }

    public String filterJsonValue(JSONObject jobj, String key)
            throws JSONException {
        String returnStr = "";

        if (jobj.isNull(key)) {
            returnStr = "";
        } else {
            returnStr = jobj.getString(key);
        }

        return returnStr;
    }

    public boolean isEditTextEmpty(EditText editText) {

        if (editText.getText().toString().length() == 0) {
            return true;
        } else {
            return false;
        }

    }

    public boolean isSpinnerTextEmpty(Spinner spinner) {
        try {
            if (spinner.getSelectedItem() != null) {

                if (spinner.getSelectedItem().toString().length() == 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } catch (Exception e) {
            // TODO: handle exception
            return true;
        }

    }

    BroadcastReceiver _logoutReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            Log.d("onReceive", "Logout in progress");
            // At this point you should start the login activity and finish
            // this one
            finish();
        }
    };

    // private void setupBroadCastReceiver() {
    // IntentFilter intentFilter = new IntentFilter();
    // intentFilter.addAction(Constants.ACTION_LOGOUT);
    // registerReceiver(_logoutReceiver, intentFilter);
    //
    // }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        // unregisterReceiver(_logoutReceiver);
        super.onDestroy();
    }


    public static void addImageToGallery(final String filePath,
                                         final Context context) {

        ContentValues values = new ContentValues();

        values.put(Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, filePath);

        context.getContentResolver().insert(Images.Media.EXTERNAL_CONTENT_URI,
                values);
    }

    public int startIndex = 1;
    public static final int NO_ROWS_LIMIT = 20;
    public static final String STARTINDEX_KEY = "start_indx";
    public static final String TOPROWS_KEY = "top_rows";
    public boolean loadingMore = false;
    public View loadMoreView;

    public void setLoadMoreView() {
        loadMoreView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.loadmore, null, false);
    }

    public PostObject getStartIndexPOObject() {
        PostObject _postObject = new PostObject();
        _postObject.setString_value(String.valueOf(startIndex));
        return _postObject;

    }

    public void resetStartIndex() {
        this.startIndex = 1;
    }

    public PostObject getRowsLimitPOObject() {
        PostObject _postObject = new PostObject();
        _postObject.setString_value(String.valueOf(NO_ROWS_LIMIT));
        return _postObject;

    }

    public void showHomeScreen() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();

        // FragmentManager manager = getSupportFragmentManager();
        // if (manager != null) {
        // int backStackEntryCount = manager.getBackStackEntryCount();
        // if (backStackEntryCount == 0) {
        // finish();
        // }
        // Fragment fragment = manager.getFragments().get(
        // backStackEntryCount - 1);
        // fragment.onResume();
        // }

    }

    @Override
    protected void onServiceBinded(BleProfileService.LocalBinder localBinder) {

    }


    @Override
    protected void onReadRssi(int i) {

    }

    @Override
    protected void onError(String s, int i) {

    }


    @Override
    protected void getFoodData(FoodData foodData) {

    }

    @Override
    protected void getUnit(byte b) {

    }

    @Override
    protected void getBleVersion(String s) {

    }

    @Override
    protected void onLeScanCallback(BluetoothDevice bluetoothDevice, int i) {

    }

    @Override
    protected void onStartScan() {

    }

    public byte[] initSetWeighCMD(int weight) {
        if (Math.abs(weight) > SupportMenu.USER_MASK) {
            throw new UnsupportedOperationException("-65535 < weight < 65535");
        }
        byte[] b = new byte[8];
        b[0] = -84;
        b[1] = 4;
        byte[] weiBytes = int2Bytes(Math.abs(weight));
        b[2] = weiBytes[1]; // 0
        b[3] = weiBytes[0]; // 1

        if (weight < 0) {
            b[5] = ParseData.bitToByte("00000001");
        }
        b[6] = -54;
        b[7] = getByteSum(b, 2, 7);
        return b;
    }

    private byte getByteSum(byte[] b, int start, int end) {
        if (b[0] == -84) { // b[1] == 5
            int j = 0;
            int result;
            for (result = start; result < end; ++result) {
                j += b[result];
            }
            result = j & 255;
            return (byte) result;
        } else {
            return (byte) -1;
        }
    }

    public static byte[] int2Bytes(int i) {
        return new byte[]{(byte) ((i >> 8) & MotionEventCompat.ACTION_MASK), (byte) (i & MotionEventCompat.ACTION_MASK)};
    }

}
