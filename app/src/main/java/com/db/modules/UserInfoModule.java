package com.db.modules;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.db.DatabaseHelper;
import com.fitmi.dao.CaloryBaselineDAO;
import com.fitmi.dao.FitmiFoodLogDAO;
import com.fitmi.dao.UserInfoDAO;
import com.fitmi.utils.Constants;

public class UserInfoModule extends BaseModule {



	public UserInfoModule(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub 
	}

	public static void insertUserInformation(UserInfoDAO information,DatabaseHelper helper)
	{

		SQLiteDatabase db = helper.getWritableDatabase();

		String queryString =  "INSERT INTO user_profiles (" +
				"user_id, height_ft, height_in, weight, date_of_birth, activity_level, daily_calorie_intake, first_name, last_name, gender,picture)"+
				"VALUES ( '"+ information.getUserId()+"' , '"+information.getHeightFt()+"' , '"+information.getHeightInc()+"' , "+                                                                        
				"'"+information.getWeight()+"' , '"+information.getDateOfBirth()+"' , '"+information.getActivityLevel()+"', '"+information.getDailyCaloryIntake()+"' , '"+ information.getFirstName()+"' , '" +information.getLastName()+"' , '" +information.getGender()+"' ,'"+information.getPicturePath()+"' )";		 

		db.execSQL(queryString);

		db.close();
	}
	public static void insertUserInformationProfileLogin(UserInfoDAO information,DatabaseHelper helper)
	{

		SQLiteDatabase db = helper.getWritableDatabase();

		String queryString =  "INSERT INTO user_profiles (" +
				"id,user_id, height_ft, height_in, weight, date_of_birth, activity_level, daily_calorie_intake, first_name, last_name, gender,picture)"+
				"VALUES ( '"+information.getProfileId()+"' , '"+information.getUserId()+"' , '"+information.getHeightFt()+"' , '"+information.getHeightInc()+"' , "+                                                                        
				"'"+information.getWeight()+"' , '"+information.getDateOfBirth()+"' , '"+information.getActivityLevel()+"', '"+information.getDailyCaloryIntake()+"' , '"+ information.getFirstName()+"' , '" +information.getLastName()+"' , '" +information.getGender()+"' ,'"+information.getPicturePath()+"' )";		 

		db.execSQL(queryString);

		db.close();
	}

	public ArrayList<UserInfoDAO> userList()
	{


		ArrayList<UserInfoDAO> userArray = new ArrayList<UserInfoDAO>();

		String queryString =  "SELECT * FROM user_profiles WHERE user_id = '"+Constants.USER_ID+"'";

		Cursor c = db.rawQuery(queryString, null);
		if (c.moveToFirst()) {			

			do{
				UserInfoDAO userInfo = new UserInfoDAO();

				userInfo.setProfileId(c.getString(0));
				userInfo.setUserId(c.getString(1));
				userInfo.setFirstName(c.getString(2));
				userInfo.setLastName(c.getString(3));
				userInfo.setGender(c.getString(5));
				userInfo.setHeightFt(c.getString(16));	
				userInfo.setHeightInc(c.getString(17));				
				userInfo.setWeight(c.getString(18));
				userInfo.setDateOfBirth(c.getString(19));
				userInfo.setActivityLevel(c.getString(20));
				userInfo.setDailyCaloryIntake(c.getString(21));	
				userInfo.setPicturePath(c.getString(11));

				userArray.add(userInfo);

			}while(c.moveToNext());

		}
		c.close();

		return userArray;
	}


	public static String getProfileId(DatabaseHelper helper)
	{
		SQLiteDatabase db = helper.getReadableDatabase();

		String profileId = "";

		String queryString =  "SELECT * FROM user_profiles";

		Cursor c = db.rawQuery(queryString, null);
		if (c.moveToLast()) {			

			profileId = c.getString(0);

		}
		c.close();
	
		db.close();
		return profileId;
	}

	public static void insertCaloryBaseline(CaloryBaselineDAO baseLineData ,DatabaseHelper helper)
	{
		SQLiteDatabase db = helper.getWritableDatabase();

		String queryString =  "INSERT INTO fitmi_calorie_baseline (" +
				"user_profile_id, user_id, total_intake, total_burned, weight, sleep, water, bp_sys, bp_dia)"+
				"VALUES ( '"+ baseLineData.getUserProfileId()+"' , '"+baseLineData.getUserId()+"' , '"+baseLineData.getTotalIntake()+"' , "+                                                                        
				"'"+baseLineData.getTotalBurned()+"' , '"+baseLineData.getWeight()+"' , '"+baseLineData.getSleep()+"', '"+baseLineData.getWater()+"' , '"+ baseLineData.getBpSys()+"' , '" +baseLineData.getBpDia()+"' )";		 

		db.execSQL(queryString);

		db.close();
	}
	
	public static void editCaloryBaseline(CaloryBaselineDAO baseLineData ,DatabaseHelper helper)
	{
		SQLiteDatabase db = helper.getWritableDatabase();		

		ContentValues cv = new ContentValues();
		cv.put("user_profile_id",baseLineData.getUserProfileId());
		cv.put("user_id",baseLineData.getUserId());
		cv.put("total_intake",baseLineData.getTotalIntake());
		cv.put("total_burned",baseLineData.getTotalBurned());
		cv.put("weight",baseLineData.getWeight());
		cv.put("sleep",baseLineData.getSleep());
		cv.put("water",baseLineData.getWater());
		cv.put("bp_sys",baseLineData.getBpSys());
		cv.put("bp_dia",baseLineData.getBpDia());

		db.update("fitmi_calorie_baseline", cv, "user_profile_id"+"="+Constants.PROFILE_ID, null);

		db.close();	
	}


	public static CaloryBaselineDAO getCaloryBaseline(DatabaseHelper helper)
	{
		SQLiteDatabase db = helper.getReadableDatabase();
		CaloryBaselineDAO baseLineData = new CaloryBaselineDAO();

		//String queryString =  "SELECT * FROM fitmi_calorie_baseline WHERE user_profile_id = '"+Constants.PROFILE_ID+"'";
		String queryString =  "SELECT * FROM fitmi_calorie_baseline WHERE user_id = '"+Constants.USER_ID+"' AND user_profile_id = '"+Constants.PROFILE_ID+"'";

		Cursor c = db.rawQuery(queryString, null);
		if (c.moveToFirst()) {		

			baseLineData.setUserProfileId(c.getString(1));
			baseLineData.setUserId(c.getString(2));
			baseLineData.setTotalIntake(c.getString(3));
			baseLineData.setTotalBurned(c.getString(4));
			baseLineData.setWeight(c.getString(5));
			baseLineData.setSleep(c.getString(6));
			baseLineData.setWater(c.getString(7));				
			baseLineData.setBpSys(c.getString(8));
			baseLineData.setBpDia(c.getString(9));
		}
		c.close();
		
		db.close();
		return baseLineData;
	}

	public static UserInfoDAO getUserInformation(DatabaseHelper helper)
	{

		SQLiteDatabase db = helper.getReadableDatabase();

		UserInfoDAO userInfo = new UserInfoDAO();

		String queryString =  "SELECT * FROM user_profiles WHERE id = '"+Constants.PROFILE_ID+"'";
		Cursor c = db.rawQuery(queryString, null);
		if (c.moveToFirst()) {	

			userInfo.setProfileId(c.getString(0));
			userInfo.setUserId(c.getString(1));
			userInfo.setFirstName(c.getString(2));
			userInfo.setLastName(c.getString(3));
			userInfo.setHeightFt(c.getString(16));	
			userInfo.setHeightInc(c.getString(17));				
			userInfo.setWeight(c.getString(18));
			userInfo.setDateOfBirth(c.getString(19));
			userInfo.setActivityLevel(c.getString(20));
			userInfo.setDailyCaloryIntake(c.getString(21));	
			userInfo.setGender(c.getString(5));
			userInfo.setPicturePath(c.getString(11));
		}
		c.close();
	
		db.close();
		return userInfo;

	}

	public static void editCalory(UserInfoDAO information,DatabaseHelper helper)
	{
		SQLiteDatabase db = helper.getWritableDatabase();		

		ContentValues cv = new ContentValues();
		cv.put("user_id",information.getUserId());
		cv.put("height_ft",information.getHeightFt());
		cv.put("height_in",information.getHeightInc());
		cv.put("weight",information.getWeight());
		cv.put("date_of_birth",information.getDateOfBirth());
		cv.put("activity_level",information.getActivityLevel());
		cv.put("daily_calorie_intake",information.getDailyCaloryIntake());
		cv.put("first_name",information.getFirstName());
		cv.put("last_name",information.getLastName());
		cv.put("gender",information.getGender());
		cv.put("picture",information.getPicturePath());

		db.update("user_profiles", cv, "id "+"="+Constants.PROFILE_ID, null);

		db.close();	

		/*String[] args = { tagObject.getReferenceFoodId() };
		String query =
				"UPDATE fitmi_food_log SET cals =" + calory
				+ " WHERE reference_food_id =?";

		Cursor cu = db.rawQuery(query, args);
		cu.moveToFirst();
		cu.close(); */
	}


	public String getCaloryTake()
	{
		//SQLiteDatabase db = helper.getReadableDatabase();	

		String totalCalory = "";

		String queryString =  "SELECT total_intake FROM fitmi_calorie_baseline WHERE user_profile_id = '"+Constants.PROFILE_ID+"'";
		Cursor c = readDb.rawQuery(queryString, null);
		if (c.moveToFirst()) {	

			totalCalory = c.getString(0);

		}
		c.close();
		return totalCalory; 
	}


	public void updateCaloryTake(String newCalory)
	{
		//SQLiteDatabase db = helper.getWritableDatabase();

		String[] args = { Constants.PROFILE_ID };
		String query =
				"UPDATE fitmi_calorie_baseline SET total_intake ='" + newCalory
				+ "' WHERE user_profile_id = ?";

		Log.e("updateCaloryTake",args[0].toString());
		Cursor cu = db.rawQuery(query, args);
		cu.moveToFirst();
		cu.close();
	}

	/**
	 *  Weight update
	 */

	public String getWeight()
	{
		//SQLiteDatabase db = helper.getReadableDatabase();	

		String weight = "";

		String queryString =  "SELECT weight FROM fitmi_calorie_baseline WHERE user_profile_id = '"+Constants.PROFILE_ID+"'";
		Cursor c = readDb.rawQuery(queryString, null);
		if (c.moveToFirst()) {	

			weight = c.getString(0);

		}
		c.close();
		return weight; 
	}


	public void updateWeight(String newWeight)
	{
		//SQLiteDatabase db = helper.getWritableDatabase();

		String[] args = { Constants.PROFILE_ID };
		String query =
				"UPDATE fitmi_calorie_baseline SET weight =" + newWeight
				+ " WHERE user_profile_id = ?";

		Cursor cu = db.rawQuery(query, args);
		cu.moveToFirst();
		cu.close();
	}

	/**
	 *  Update sleep
	 */

	public String getSleep()
	{
		//SQLiteDatabase db = helper.getReadableDatabase();	

		String sleep = "";

		String queryString =  "SELECT sleep FROM fitmi_calorie_baseline WHERE user_profile_id = '"+Constants.PROFILE_ID+"'";
		Cursor c = readDb.rawQuery(queryString, null);
		if (c.moveToFirst()) {	

			sleep = c.getString(0);

		}
		c.close();
		return sleep; 
	}


	public void updateSleep(String newSleep)
	{
		//SQLiteDatabase db = helper.getWritableDatabase();

		String[] args = { Constants.PROFILE_ID };
		String query =
				"UPDATE fitmi_calorie_baseline SET sleep = '" + newSleep
				+ "' WHERE user_profile_id = ?";

		Cursor cu = db.rawQuery(query, args);
		cu.moveToFirst();
		cu.close();
	}

	/**
	 *  update blood pressure
	 */
	public ArrayList<String> getBloodPressure()
	{
		//SQLiteDatabase db = helper.getReadableDatabase();	

		ArrayList<String> bpData = new ArrayList<String>();

		String queryString =  "SELECT bp_sys , bp_dia FROM fitmi_calorie_baseline WHERE user_profile_id = '"+Constants.PROFILE_ID+"'";
		Cursor c = readDb.rawQuery(queryString, null);
		if (c.moveToFirst()) {	

			bpData.add(c.getString(0));
			bpData.add(c.getString(1));

		}
		c.close();
		return bpData; 
	}

	/**
	 * Update water
	 * 
	 */
	
	public String getWater()
	{
		//SQLiteDatabase db = helper.getReadableDatabase();	

		String water = "";

		String queryString =  "SELECT water FROM fitmi_calorie_baseline WHERE user_profile_id = '"+Constants.PROFILE_ID+"'";
		Cursor c = readDb.rawQuery(queryString, null);
		if (c.moveToFirst()) {	

			water = c.getString(0);

		}
		c.close();
		return water; 
	}


	public void updateWater(String newWater)
	{
		//SQLiteDatabase db = helper.getWritableDatabase();

		String[] args = { Constants.PROFILE_ID };
		String query =
				"UPDATE fitmi_calorie_baseline SET water = '" + newWater
				+ "' WHERE user_profile_id = ?";

		Cursor cu = db.rawQuery(query, args);
		cu.moveToFirst();
		cu.close();
	}
	
	/**
	 *  Edit calories burn
	 */
	
	public String getCaloriesBurn()
	{
		//SQLiteDatabase db = helper.getReadableDatabase();	

		String caloriesBurn = "";

		String queryString =  "SELECT total_burned FROM fitmi_calorie_baseline WHERE user_profile_id = '"+Constants.PROFILE_ID+"'";
		Cursor c = readDb.rawQuery(queryString, null);
		if (c.moveToFirst()) {	

			caloriesBurn = c.getString(0);

		}
		c.close();
		return caloriesBurn; 
	}


	public void updateCaloriesBurn(String newCaloriesBurn)
	{
		//SQLiteDatabase db = helper.getWritableDatabase();

		String[] args = { Constants.PROFILE_ID };
		String query =
				"UPDATE fitmi_calorie_baseline SET total_burned = '" + newCaloriesBurn
				+ "' WHERE user_profile_id = ?";

		Cursor cu = db.rawQuery(query, args);
		cu.moveToFirst();
		cu.close();
	}
	

	public void updateBloodPressure(String newSys,String newDia)
	{
		//SQLiteDatabase db = helper.getWritableDatabase();

		String[] args = { Constants.PROFILE_ID };
		String query =
				"UPDATE fitmi_calorie_baseline SET bp_sys =" + newSys+" , bp_dia = "+newDia
				+ " WHERE user_profile_id = ?";

		Cursor cu = db.rawQuery(query, args);
		cu.moveToFirst();
		cu.close();
	}

	public void deleteUser(String profileId)//DatabaseHelper helper
	{
		//SQLiteDatabase db = helper.getWritableDatabase();		

		//String queryString = "DELETE FROM user_profiles WHERE user_id = "+Constants.USER_ID+" AND id = "+profileId;

		//Cursor c = db.rawQuery(queryString, null);

		db.delete("user_profiles","user_id = "+Constants.USER_ID+" AND id = "+profileId,
				null);
	}

	public String userCount()
	{
		String profileId = "";
		
		String queryString =  "SELECT * FROM user_profiles";
		Cursor c = db.rawQuery(queryString, null);

		if (c.moveToLast()) {	

			profileId = c.getString(0);

		}
		return profileId;

	}

}
