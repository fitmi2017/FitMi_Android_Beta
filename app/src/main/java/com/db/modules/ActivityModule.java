package com.db.modules;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.db.DatabaseHelper;
import com.fitmi.activitys.BaseActivity;
import com.fitmi.dao.CalenderFirsLastDay;
import com.fitmi.dao.ExerciseItemDAO;
import com.fitmi.dao.PlannerMealType;
import com.fitmi.dao.SignUpDAO;
import com.fitmi.utils.Constants;
import com.fitmi.utils.DateModule;

public class ActivityModule{	

	static ArrayList<HashMap<String, String>> List = new ArrayList<HashMap<String, String>>();
	ArrayList<PlannerMealType> plannerData = new ArrayList<PlannerMealType>();

	
	

	public static ArrayList<HashMap<String, String>> get_fitmi_exercise(DatabaseHelper helper,int livestrong)
	{
		SQLiteDatabase db = helper.getWritableDatabase();

		String queryString = "select * from fitmi_exercise where livestrong_id="+livestrong+" and is_default="+1;
		//String queryString = "select * from fitmi_exercise where id="+id+" and is_default="+1;

		System.out.println("queryString="+queryString);

		Cursor cur = db.rawQuery(queryString, new String[]{ });
		List.clear();
		if(cur.getCount()>0)
		{

			if (cur.moveToFirst()) {
				HashMap<String, String> map = new HashMap<String, String>();
				//      Log.i("fetchAllItemData", cur.getString(cur.getColumnIndex(MainActivity.KEY_MEDICINE)));
				map.put(BaseActivity.id, cur.getString(cur.getColumnIndex(BaseActivity.id)));

				map.put(BaseActivity.exercise_name, cur.getString(cur.getColumnIndex(BaseActivity.exercise))); 
				map.put(BaseActivity.exercise, cur.getString(cur.getColumnIndex(BaseActivity.exercise)));
				map.put(BaseActivity.description, cur.getString(cur.getColumnIndex(BaseActivity.description)));
				map.put(BaseActivity.cals_per_hour, cur.getString(cur.getColumnIndex(BaseActivity.cals_per_hour)));
				map.put(BaseActivity.calories_burned, cur.getString(cur.getColumnIndex(BaseActivity.cals_per_hour)));
				map.put(BaseActivity.livestrong_id, cur.getString(cur.getColumnIndex(BaseActivity.livestrong_id)));

				cur.moveToNext();
				List.add(map);
			}


		}   
		cur.close();
		db.close();
		return List;

	}

	public static String execiseloginsertion(DatabaseHelper helper,ArrayList<HashMap<String, String>> list){
		long rowId=-1;
		SQLiteDatabase db=helper.getWritableDatabase();
		System.out.println(" ************execiselogItem Insert ************");

		ContentValues cv=new ContentValues();
		cv.put(BaseActivity.user_id, Constants.USER_ID);
		cv.put(BaseActivity.user_profile_id,  Constants.PROFILE_ID);        
		cv.put(BaseActivity.exercise_id, list.get(0).get(BaseActivity.id));    
		cv.put(BaseActivity.exercise_name, list.get(0).get(BaseActivity.exercise_name));
		cv.put(BaseActivity.description, list.get(0).get(BaseActivity.description));
		cv.put(BaseActivity.calories_burned, list.get(0).get(BaseActivity.calories_burned));
		cv.put(BaseActivity.total_time_minutes, 60);
		cv.put(BaseActivity.log_time, Constants.postDate);
		//change by avinash sending current seleted date
				//	cv.put(BaseActivity.date_added, new DateModule().getDateFormat());
				cv.put(BaseActivity.date_added,Constants.postDate);
		

		rowId = db.insert("fitmi_exercise_log", null, cv);

		//Y-m-d H:i:s

		db.close();
		return String.valueOf(rowId);
	}

	public static String execiseloginsertion(DatabaseHelper helper,HashMap<String, String> hashMap,long rowid,String minute){
		long rowId=-1;
		SQLiteDatabase db=helper.getWritableDatabase();
		System.out.println(" ************execiselogItem Item Insert ************");

		ContentValues cv=new ContentValues();
		cv.put(BaseActivity.user_id, Constants.USER_ID);
		cv.put(BaseActivity.user_profile_id,  Constants.PROFILE_ID);
	    cv.put(BaseActivity.exercise_id, rowid);
		
		
		cv.put(BaseActivity.exercise_name, hashMap.get(BaseActivity.exercise_name));
		cv.put(BaseActivity.description, hashMap.get(BaseActivity.description));
		//cv.put(BaseActivity.calories_burned, hashMap.get(BaseActivity.cals_per_hour));
		cv.put(BaseActivity.calories_burned, hashMap.get(BaseActivity.calories_burned));
		cv.put(BaseActivity.total_time_minutes, minute);
		cv.put(BaseActivity.log_time, Constants.postDate);
	//change by avinash sending current seleted date
		//	cv.put(BaseActivity.date_added, new DateModule().getDateFormat());
		cv.put(BaseActivity.date_added,Constants.postDate);
		rowId = db.insert("fitmi_exercise_log", null, cv);
		
		//Y-m-d H:i:s

		db.close();
		return String.valueOf(rowId);
	}

	public static long execiseinsertion(DatabaseHelper helper,HashMap<String, String> hashMap){
		long rowId=-1;
		SQLiteDatabase db=helper.getWritableDatabase();
		System.out.println(" ************execise Item Insert ************");
		boolean existing=false;
		existing=Exists(hashMap.get(BaseActivity.livestrong_id),helper);
		System.out.println(" ************execise Item Insert existing ************  "+existing);
		if(existing==false){

			ContentValues cv=new ContentValues();
			cv.put(BaseActivity.exercise, hashMap.get(BaseActivity.exercise_name));
			cv.put(BaseActivity.description, hashMap.get(BaseActivity.description));
			cv.put(BaseActivity.cals_per_hour, hashMap.get(BaseActivity.cals_per_hour));
			cv.put(BaseActivity.livestrong_id, hashMap.get(BaseActivity.livestrong_id));
			cv.put("is_default", 0);

			rowId = db.insert("fitmi_exercise", null, cv);	       
			db.close();
		}

		System.out.println(" ************execise Item Insert rowId ************ "+rowId );
		return rowId;
	}

	public static ArrayList<HashMap<String, String>> get_fitmi_exercise_log(DatabaseHelper helper)
	{
		SQLiteDatabase db = helper.getWritableDatabase();

		String queryString = "select * from fitmi_exercise_log where user_id= '"+Constants.USER_ID+"' and user_profile_id= '"+Constants.PROFILE_ID+"' and date_added between '"+Constants.conditionDate+" 00:00:01' AND '"+Constants.conditionDate+" 24:00:00'";



		Cursor cur = db.rawQuery(queryString, new String[]{});
		List.clear();
		if(cur.getCount()>0)
		{

			if (cur.moveToFirst()) {
				for(int i=0;i<cur.getCount();i++)
				{
					HashMap<String, String> map = new HashMap<String, String>();
					//      Log.i("fetchAllItemData", cur.getString(cur.getColumnIndex(MainActivity.KEY_MEDICINE)));
					map.put(BaseActivity.id, cur.getString(cur.getColumnIndex(BaseActivity.id)));

					map.put(BaseActivity.exercise_name, cur.getString(cur.getColumnIndex(BaseActivity.exercise_name)));
					map.put(BaseActivity.description, cur.getString(cur.getColumnIndex(BaseActivity.description)));
					map.put(BaseActivity.calories_burned, cur.getString(cur.getColumnIndex(BaseActivity.calories_burned)));
					//map.put(BaseActivity.cals_per_hour, cur.getString(cur.getColumnIndex(BaseActivity.cals_per_hour)));
					map.put(BaseActivity.total_time_minutes, cur.getString(cur.getColumnIndex(BaseActivity.total_time_minutes)));
					
					cur.moveToNext();
					List.add(map);
				}
			}


		}   
		cur.close();
		db.close();
		return List;

	}
	public static ArrayList<HashMap<String, String>> get_fitmi_exercise_log_usigId(String exercise_id,DatabaseHelper helper)
	{
		SQLiteDatabase db = helper.getWritableDatabase();

		String queryString = "select * from fitmi_exercise_log where user_id= '"+Constants.USER_ID+"' and exercise_id='"+exercise_id+"' and user_profile_id= '"+Constants.PROFILE_ID+"' and date_added between '"+Constants.conditionDate+" 00:00:01' AND '"+Constants.conditionDate+" 24:00:00'";



		Cursor cur = db.rawQuery(queryString, new String[]{});
		List.clear();
		if(cur.getCount()>0)
		{

			if (cur.moveToFirst()) {
				for(int i=0;i<cur.getCount();i++)
				{
					HashMap<String, String> map = new HashMap<String, String>();
					//      Log.i("fetchAllItemData", cur.getString(cur.getColumnIndex(MainActivity.KEY_MEDICINE)));
					map.put(BaseActivity.id, cur.getString(cur.getColumnIndex(BaseActivity.id)));
					map.put(BaseActivity.exercise_id, cur.getString(cur.getColumnIndex(BaseActivity.exercise_id)));
					map.put(BaseActivity.exercise_name, cur.getString(cur.getColumnIndex(BaseActivity.exercise_name)));
					map.put(BaseActivity.description, cur.getString(cur.getColumnIndex(BaseActivity.description)));
					map.put(BaseActivity.calories_burned, cur.getString(cur.getColumnIndex(BaseActivity.calories_burned)));
				//	map.put(BaseActivity.cals_per_hour, cur.getString(cur.getColumnIndex(BaseActivity.cals_per_hour)));
					map.put(BaseActivity.total_time_minutes, cur.getString(cur.getColumnIndex(BaseActivity.total_time_minutes)));
					
					cur.moveToNext();
					List.add(map);
				}
			}


		}   
		cur.close();
		db.close();
		return List;

	}
	public static boolean Exists(String livestrong_id,DatabaseHelper helper) {

		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from fitmi_exercise where livestrong_id = ?", 
				new String[] { livestrong_id });
		boolean exists = false ;
		if((cursor.getCount() > 0)){
		 exists = true;
		 
			if (cursor.moveToFirst()) {
			/*	for(int i=0;i<cursor.getCount();i++)*/
				{
				//	HashMap<String, String> map = new HashMap<String, String>();
					//      Log.i("fetchAllItemData", cur.getString(cur.getColumnIndex(MainActivity.KEY_MEDICINE)));
				//	map.put(BaseActivity.id, cursor.getString(cursor.getColumnIndex(BaseActivity.id)));
					BaseActivity.__activityId=(cursor.getString(cursor.getColumnIndex(BaseActivity.id)));
					System.out.println(" ************execise Item Constants._activityId ************ " +BaseActivity.__activityId);
				//	cursor.moveToNext();
					
				}
			}
			cursor.close();
			db.close();
		}else{
			 exists = false;
			 cursor.close();
				db.close();
		}
		return exists;
	}


	public static void editActivityTime(String newTime , DatabaseHelper helper,int id)
	{
		SQLiteDatabase db = helper.getWritableDatabase();

		String[] args = {String.valueOf(id) };
		String query =
				"UPDATE fitmi_exercise_log SET total_time_minutes = '" + newTime
				+ "' WHERE id =?";

		Cursor cu = db.rawQuery(query, args);
		cu.moveToFirst();
		
		cu.close();
		db.close();
	}


	public static String todayTotalCaloryBurn(String today , DatabaseHelper helper, Context ctx)
	{
		SQLiteDatabase db=null;
		try{ 
		db= helper.getWritableDatabase();
		}catch (Exception ex)
		{
			Log.e("Exception todayTotalCaloryBurn database","todayTotalCaloryBurn database");
		}
		String calorySumBurn = "";

		String queryString = "SELECT SUM(calories_burned) FROM fitmi_exercise_log WHERE user_id= '"+Constants.USER_ID+"' AND user_profile_id= '"+Constants.PROFILE_ID+"' AND date_added between '"+today+" 00:00:01' AND '"+today+" 24:00:00'";
		//String queryString = "SELECT SUM(calories_burned) FROM fitmi_exercise_log WHERE date_added between '"+today+" 00:00:01' AND '"+today+" 23:59:59'";

		if (db.isOpen()) {
			
		
		Cursor c = db.rawQuery(queryString, new String[] {});

		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {				


				calorySumBurn = c.getString(0);
			}
		}
		c.close();
		}
		else{
			helper = new DatabaseHelper(ctx);
			db = helper.getWritableDatabase();
			
			Cursor c = db.rawQuery(queryString, new String[] {});

			if(c.getCount()>0)
			{
				if (c.moveToFirst()) {				


					calorySumBurn = c.getString(0);
				}
			}
			c.close();
		}
		
		db.close();
		return calorySumBurn; 
	}
	
	public ArrayList<PlannerMealType>  plannerActivityCheck(DatabaseHelper helper)
	{
		
		SQLiteDatabase db = helper.getWritableDatabase();
		
		if(plannerData.size()>0)
			plannerData.clear();
		
		String queryString =  "SELECT * FROM fitmi_exercise_log WHERE user_id = '"+Constants.USER_ID+"' AND user_profile_id = '"+Constants.PROFILE_ID+"' AND date_added between '"+Constants.conditionDate+" 00:00:01' AND '"+Constants.conditionDate+" 24:00:00'";

		Cursor c = db.rawQuery(queryString, new String[] {});

		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {				

			
				PlannerMealType plannerMealData = new PlannerMealType();
				
				plannerMealData.setMealId(c.getString(3));
				plannerMealData.setPlannerDate(c.getString(9));
				
				plannerData.add(plannerMealData);
			}
		}
		c.close();
		db.close();
		return plannerData;
	}
	
	public static void deleteActivityLog(DatabaseHelper helper)
	{
		SQLiteDatabase db = helper.getWritableDatabase();
		/*String queryString =  "delete FROM fitmi_food_log WHERE meal_id = '"+plannerMealId+"' AND user_id = '"+Constants.USER_ID+"' AND user_profile_id = '"+Constants.PROFILE_ID+"' AND date_added between '"+Constants.conditionDate+" 00:00:01' AND '"+Constants.conditionDate+" 23:59:59'";
		Cursor c = db.rawQuery(queryString, new String[] {});*/
		
		db.delete("fitmi_exercise_log","user_id = '"+Constants.USER_ID+"' AND user_profile_id = '"+Constants.PROFILE_ID+"' AND date_added between '"+Constants.conditionDate+" 00:00:01' AND '"+Constants.conditionDate+" 24:00:00'",
		        null);
		db.close();
		
	}
	
	public boolean selectActivityMonthList(String firstLastDayObj,DatabaseHelper helper,String activitylId)
	{

		SQLiteDatabase db = helper.getWritableDatabase();
		
		Cursor c = null;

		String queryString =  "SELECT * FROM fitmi_exercise_log WHERE exercise_id = '"+activitylId+"' AND user_id = '"+Constants.USER_ID+"' AND user_profile_id = '"+Constants.PROFILE_ID+"' AND date_added between '"+firstLastDayObj+" 00:00:01' AND '"+firstLastDayObj+" 24:00:00'";

		c = db.rawQuery(queryString, new String[] {});

		if(c !=null && c.getCount()>0)
		{		
			c.close();
			db.close();
			return true;
		}else{
			c.close();
			db.close();
			return false;
		}
		
     
	}
	
	public static ArrayList<ExerciseItemDAO> totalCaloryBurn(DatabaseHelper helper)
	{
		SQLiteDatabase db = helper.getWritableDatabase();

		Cursor c = null;

		ArrayList<ExerciseItemDAO> calorySumList = new ArrayList<ExerciseItemDAO>();

		for(int i=1;i<=6;i++)
		{
			String queryString = "SELECT SUM(calories_burned),exercise_id,exercise_name FROM fitmi_exercise_log WHERE user_id=? AND user_profile_id = ? AND exercise_id = ? AND date_added between '"+Constants.conditionDate+" 00:00:01' AND '"+Constants.conditionDate+" 24:00:00'";

			c = db.rawQuery(queryString, new String[] {Constants.USER_ID,Constants.PROFILE_ID,String.valueOf(i)});

			if(c.getCount()>0)
			{
				if (c.moveToFirst()) {

					if(c.getString(0) !=null){
						
						ExerciseItemDAO obj = new ExerciseItemDAO();	
						obj.setExerciseSum(c.getString(0));
						obj.setExerciseId(Integer.parseInt(c.getString(1)));
						obj.setExerciseName(c.getString(2));
						
					 calorySumList.add(obj);
					}
				}
			}

		}
		c.close();
		db.close();
		return calorySumList; 
	}
	
	
	public static ArrayList<HashMap<String, String>> myActivityData(DatabaseHelper helper)
	{
		SQLiteDatabase db = helper.getWritableDatabase();

		String queryString = "select * from fitmi_exercise_log where user_id= '"+Constants.USER_ID+"' and user_profile_id= '"+Constants.PROFILE_ID+"'";// and date_added between '"+Constants.conditionDate+" 00:00:01' AND '"+Constants.conditionDate+" 23:59:59'";



		Cursor cur = db.rawQuery(queryString, new String[]{});
		List.clear();
		if(cur.getCount()>0)
		{

			if (cur.moveToFirst()) {
				for(int i=0;i<cur.getCount();i++)
				{
					HashMap<String, String> map = new HashMap<String, String>();
					//      Log.i("fetchAllItemData", cur.getString(cur.getColumnIndex(MainActivity.KEY_MEDICINE)));
					/*map.put(BaseActivity.id, cur.getString(cur.getColumnIndex(BaseActivity.id)));

					map.put(BaseActivity.exercise_name, cur.getString(cur.getColumnIndex(BaseActivity.exercise_name)));
					map.put(BaseActivity.description, cur.getString(cur.getColumnIndex(BaseActivity.description)));
					map.put(BaseActivity.calories_burned, cur.getString(cur.getColumnIndex(BaseActivity.calories_burned)));
					map.put(BaseActivity.total_time_minutes, cur.getString(cur.getColumnIndex(BaseActivity.total_time_minutes)));
*/
					map.put(BaseActivity.id, cur.getString(cur.getColumnIndex(BaseActivity.id)));
					map.put(BaseActivity.exercise_name, cur.getString(cur.getColumnIndex(BaseActivity.exercise_name)));
					map.put(BaseActivity.description, cur.getString(cur.getColumnIndex(BaseActivity.description)));
					map.put(BaseActivity.calories_burned, cur.getString(cur.getColumnIndex(BaseActivity.calories_burned)));
					map.put(BaseActivity.total_time_minutes, cur.getString(cur.getColumnIndex(BaseActivity.total_time_minutes)));
					map.put(BaseActivity.user_id, cur.getString(cur.getColumnIndex(BaseActivity.user_id)));
					map.put(BaseActivity.user_profile_id, cur.getString(cur.getColumnIndex(BaseActivity.user_profile_id)));
					map.put(BaseActivity.exercise_id, cur.getString(cur.getColumnIndex(BaseActivity.exercise_id)));
					map.put(BaseActivity.log_time, cur.getString(cur.getColumnIndex(BaseActivity.log_time)));
					map.put(BaseActivity.date_added, cur.getString(cur.getColumnIndex(BaseActivity.date_added)));
					
					cur.moveToNext();
					List.add(map);
				}
			}


		}   
		cur.close();
		db.close();
		return List;
	}
	
	public static ArrayList<HashMap<String, String>> recentActivity(DatabaseHelper helper,CalenderFirsLastDay firstLastDayObj)
	{
		SQLiteDatabase db = helper.getWritableDatabase();

		String queryString = "select * from fitmi_exercise_log where user_id= '"+Constants.USER_ID+"' and user_profile_id= '"+Constants.PROFILE_ID+"' and date_added between '"+firstLastDayObj.getFirstDay()+" 00:00:01' AND '"+firstLastDayObj.getLastDay()+" 24:00:00'";



		Cursor cur = db.rawQuery(queryString, new String[]{});
		List.clear();
		if(cur.getCount()>0)
		{

			if (cur.moveToFirst()) {
				for(int i=0;i<cur.getCount();i++)
				{
					HashMap<String, String> map = new HashMap<String, String>();
					//      Log.i("fetchAllItemData", cur.getString(cur.getColumnIndex(MainActivity.KEY_MEDICINE)));					
					
					
					map.put(BaseActivity.id, cur.getString(cur.getColumnIndex(BaseActivity.id)));
					map.put(BaseActivity.exercise_name, cur.getString(cur.getColumnIndex(BaseActivity.exercise_name)));
					map.put(BaseActivity.description, cur.getString(cur.getColumnIndex(BaseActivity.description)));
					map.put(BaseActivity.calories_burned, cur.getString(cur.getColumnIndex(BaseActivity.calories_burned)));
					map.put(BaseActivity.total_time_minutes, cur.getString(cur.getColumnIndex(BaseActivity.total_time_minutes)));
					map.put(BaseActivity.user_id, cur.getString(cur.getColumnIndex(BaseActivity.user_id)));
					map.put(BaseActivity.user_profile_id, cur.getString(cur.getColumnIndex(BaseActivity.user_profile_id)));
					map.put(BaseActivity.exercise_id, cur.getString(cur.getColumnIndex(BaseActivity.exercise_id)));
					map.put(BaseActivity.log_time, cur.getString(cur.getColumnIndex(BaseActivity.log_time)));
					map.put(BaseActivity.date_added, cur.getString(cur.getColumnIndex(BaseActivity.date_added)));
							
					cur.moveToNext();
					List.add(map);
				}
			}


		}   
		cur.close();
		db.close();
		return List;
	}
	
	public static void deleteActivityLogItem(DatabaseHelper helper,String activityId)
	{
		SQLiteDatabase db = helper.getWritableDatabase();		

		db.delete("fitmi_exercise_log","user_id = '"+Constants.USER_ID+"' AND id = '"+activityId+"' AND user_profile_id = '"+Constants.PROFILE_ID+"' AND date_added between '"+Constants.conditionDate+" 00:00:01' AND '"+Constants.conditionDate+" 24:00:00'",
				null);
		db.close();
	}
	
	
	
	public String totalCaloryBurnDaily(String today,DatabaseHelper helper)
	{	

		String calorySumBurn = "";
		SQLiteDatabase db = helper.getWritableDatabase();		

		String queryString = "SELECT SUM(calories_burned) FROM fitmi_exercise_log WHERE user_id= '"+Constants.USER_ID+"' AND user_profile_id= '"+Constants.PROFILE_ID+"' AND date_added between '"+today+" 00:00:01' AND '"+today+" 24:00:00'";
		//String queryString = "SELECT SUM(calories_burned) FROM fitmi_exercise_log WHERE date_added between '"+today+" 00:00:01' AND '"+today+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {});

		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {				


				calorySumBurn = c.getString(0);
			}
		}
		c.close();
		
		db.close();
		return calorySumBurn; 
	}
	
	
	public String totalCaloryBurnWeekly(CalenderFirsLastDay firstLastDayObj,DatabaseHelper helper)
	{	

		String calorySumBurn = "";
		SQLiteDatabase db = helper.getWritableDatabase();		

		String queryString = "SELECT SUM(calories_burned) FROM fitmi_exercise_log WHERE user_id= '"+Constants.USER_ID+"' AND user_profile_id= '"+Constants.PROFILE_ID+"' AND date_added between '"+firstLastDayObj.getFirstDay()+" 00:00:01' AND '"+firstLastDayObj.getLastDay()+" 24:00:00'";
		//String queryString = "SELECT SUM(calories_burned) FROM fitmi_exercise_log WHERE date_added between '"+today+" 00:00:01' AND '"+today+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {});

		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {				


				calorySumBurn = c.getString(0);
			}
		}
		c.close();
		db.close();
		return calorySumBurn; 
	}
	
	public String totalCaloryBurnMonthly(CalenderFirsLastDay firstLastDayObj,DatabaseHelper helper)
	{	

		String calorySumBurn = "";
		SQLiteDatabase db = helper.getWritableDatabase();		

		String queryString = "SELECT SUM(calories_burned) FROM fitmi_exercise_log WHERE user_id= '"+Constants.USER_ID+"' AND user_profile_id= '"+Constants.PROFILE_ID+"' AND date_added between '"+firstLastDayObj.getFirstDay()+" 00:00:01' AND '"+firstLastDayObj.getLastDay()+" 24:00:00'";
		//String queryString = "SELECT SUM(calories_burned) FROM fitmi_exercise_log WHERE date_added between '"+today+" 00:00:01' AND '"+today+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {});

		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {				


				calorySumBurn = c.getString(0);
			}
		}
		c.close();
		db.close();
		return calorySumBurn; 
	}
	
	
	public static String execiselogInsertionList(DatabaseHelper helper,ArrayList<HashMap<String, String>> list,String date){
		long rowId=-1;
		SQLiteDatabase db=helper.getWritableDatabase();
		System.out.println(" ************execiselogItem Insert ************");

		ContentValues cv=new ContentValues();
		cv.put(BaseActivity.user_id, Constants.USER_ID);
		cv.put(BaseActivity.user_profile_id,  Constants.PROFILE_ID);        
		cv.put(BaseActivity.exercise_id, list.get(0).get(BaseActivity.id));    
		cv.put(BaseActivity.exercise_name, list.get(0).get(BaseActivity.exercise_name));
		cv.put(BaseActivity.description, list.get(0).get(BaseActivity.description));
		cv.put(BaseActivity.calories_burned, list.get(0).get(BaseActivity.calories_burned));
		cv.put(BaseActivity.total_time_minutes, 60);
		cv.put(BaseActivity.log_time, date);
		cv.put(BaseActivity.date_added, date);

		rowId = db.insert("fitmi_exercise_log", null, cv);

		//Y-m-d H:i:s

		db.close();
		return String.valueOf(rowId);
	}

}
