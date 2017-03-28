package com.fitmi.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.db.modules.RememberMeData;

public class SaveSharedPreferences {


    private static String PROFILE_ID_SHARE_NAME = "profileId";
    public static String USER_INFORMATION = "userinformation";

    public static void saveProfileID(Context context, String profileId) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PROFILE_ID_SHARE_NAME, context.MODE_PRIVATE).edit();
        editor.putString("profileid", profileId);
        editor.commit();
    }

    public static String getProfileId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PROFILE_ID_SHARE_NAME, context.MODE_PRIVATE);
        String profileId = prefs.getString("profileid", "");

        return profileId;
    }

    public static void saveLoginDetail(Context context, String userName, String password, String userId, String access_key, boolean isRemember) {
        SharedPreferences.Editor editor = context.getSharedPreferences(USER_INFORMATION, context.MODE_PRIVATE).edit();
        editor.putString("username", userName);
        editor.putString("password", password);
        editor.putString("userid", userId);
        editor.putString("access_key", access_key);
        editor.putBoolean("isRemember", isRemember);
        editor.commit();
    }

    public static RememberMeData getLoginDetail(Context context) {
        RememberMeData rememberMe = new RememberMeData();

        SharedPreferences prefs = context.getSharedPreferences(USER_INFORMATION, context.MODE_PRIVATE);
        String userName = prefs.getString("username", "");
        String password = prefs.getString("password", "");
        String userId = prefs.getString("userid", "");
        String access_key = prefs.getString("access_key", "");

        rememberMe.setUserName(userName);
        rememberMe.setPassword(password);
        rememberMe.setUserId(userId);
        rememberMe.setAccess_key(access_key);
        rememberMe.setRemember(prefs.getBoolean("isRemember", false));

        return rememberMe;
    }

    public static void saveCalorieDetail(Context context, String userId, String calorie_on) {
        SharedPreferences.Editor editor = context.getSharedPreferences(USER_INFORMATION, context.MODE_PRIVATE).edit();


        editor.putString("calorie_on", calorie_on);
        editor.commit();
    }

    public static RememberMeData getCalorieDetail(Context context) {
        RememberMeData rememberMe = new RememberMeData();

        SharedPreferences prefs = context.getSharedPreferences(USER_INFORMATION, context.MODE_PRIVATE);


        String calorie_on = prefs.getString("calorie_on", "");

        rememberMe.set_calorieOn(calorie_on);

        return rememberMe;
    }

    public static void clearAllSavedSharedPreferences(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PROFILE_ID_SHARE_NAME, context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
        editor = context.getSharedPreferences(USER_INFORMATION, context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();

    }

}
