package com.db.modules;

import java.util.ArrayList;

import com.db.DatabaseHelper;
import com.fitmi.dao.BloodPressureDAO;
import com.fitmi.dao.BloodPressureSet;
import com.fitmi.dao.CalenderFirsLastDay;
import com.fitmi.dao.WeightLogDAO;
import com.fitmi.dao.WeightSet;
import com.fitmi.utils.Constants;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class WeightLogModule extends BaseModule{
	
	
	ArrayList<WeightSet> weightGraphData = new ArrayList<WeightSet>();
	
	
	ArrayList<WeightLogDAO> weightListData = new ArrayList<WeightLogDAO>();

	public WeightLogModule(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}
	
	
	public void insertWeightInformation(WeightLogDAO weightDate)
	{
		String queryString =  "INSERT INTO fitmi_weight_log (" +
				"user_id, user_profile_id, weight, log_time, date_added )"+
				"VALUES ( '"+ weightDate.getUserId()+"' , '"+weightDate.getProfileId()+"' , '"+weightDate.getWeight()+"' , '"+                                                                        
				weightDate.getLogTime()+"' , '"+weightDate.getAddedtime()+"' )";		 

		db.execSQL(queryString);

		db.close();
	}
	
	
	public ArrayList<WeightLogDAO> getWeightInformation()
	{
		if(weightListData.size()>0)
			weightListData.clear();
		
		/*String queryString = "SELECT * FROM fitmi_weight_log WHERE date_added between '"+Constants.conditionDate+" 00:00:01' AND '"+Constants.conditionDate+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {});*/
		
		String queryString = "SELECT * FROM fitmi_weight_log WHERE user_id=? AND user_profile_id = ? AND date_added between '"+Constants.conditionDate+" 00:00:01' AND '"+Constants.conditionDate+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {Constants.USER_ID,Constants.PROFILE_ID});
		

		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {				

				do{
					WeightLogDAO weightData = new WeightLogDAO();
					
					weightData.setId(c.getString(0));
					weightData.setUserId(c.getString(1));
					weightData.setProfileId(c.getString(2));
					weightData.setWeight(c.getString(3));
					weightData.setLogTime(c.getString(4));
					weightData.setAddedtime(c.getString(5));
					
					weightListData.add(weightData);
				}while(c.moveToNext());

			}
		}
		c.close();
		return weightListData;
	}
	
	public ArrayList<WeightLogDAO> getWeightInformationWeekly(CalenderFirsLastDay firstLastDayObj)
	{
		if(weightListData.size()>0)
			weightListData.clear();
		
		/*String queryString = "SELECT * FROM fitmi_weight_log WHERE date_added between '"+firstLastDayObj.getFirstDay()+" 00:00:01' AND '"+firstLastDayObj.getLastDay()+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {});*/
		
		String queryString = "SELECT * FROM fitmi_weight_log WHERE user_id=? AND user_profile_id = ? AND date_added between '"+firstLastDayObj.getFirstDay()+" 00:00:01' AND '"+firstLastDayObj.getLastDay()+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {Constants.USER_ID,Constants.PROFILE_ID});

		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {				

				do{
					WeightLogDAO weightData = new WeightLogDAO();
					
					weightData.setId(c.getString(0));
					weightData.setUserId(c.getString(1));
					weightData.setProfileId(c.getString(2));
					weightData.setWeight(c.getString(3));
					weightData.setLogTime(c.getString(4));
					weightData.setAddedtime(c.getString(5));
					
					weightListData.add(weightData);
				}while(c.moveToNext());

			}
		}
		c.close();
		return weightListData;
	}
	
	
	public ArrayList<WeightLogDAO> getWeightInformationMonthly(CalenderFirsLastDay firstLastDayObj)
	{
		if(weightListData.size()>0)
			weightListData.clear();
		
		/*String queryString = "SELECT * FROM fitmi_weight_log WHERE date_added between '"+firstLastDayObj.getFirstDay()+" 00:00:01' AND '"+firstLastDayObj.getLastDay()+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {});*/
		
		String queryString = "SELECT * FROM fitmi_weight_log WHERE user_id=? AND user_profile_id = ? AND date_added between '"+firstLastDayObj.getFirstDay()+" 00:00:01' AND '"+firstLastDayObj.getLastDay()+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {Constants.USER_ID,Constants.PROFILE_ID});

		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {				

				do{
					WeightLogDAO weightData = new WeightLogDAO();
					
					weightData.setId(c.getString(0));
					weightData.setUserId(c.getString(1));
					weightData.setProfileId(c.getString(2));
					weightData.setWeight(c.getString(3));
					weightData.setLogTime(c.getString(4));
					weightData.setAddedtime(c.getString(5));
					
					weightListData.add(weightData);
				}while(c.moveToNext());

			}
		}
		c.close();
		return weightListData;
	}
	
	public WeightSet getEditValue(String id)
	{
		WeightSet bpObject = new WeightSet();
		
		String queryString = "SELECT * FROM fitmi_weight_log WHERE id =?";

		Cursor c = db.rawQuery(queryString, new String[] {id});
		
		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {	
				
				bpObject.setWeight(c.getString(3));
				bpObject.setLogTime(c.getString(4));				
			}
		}
		c.close();
		return bpObject;
		
	}
	
	
	public void editWeightInformation(String weight ,String id)
	{
		
		
		String[] args = {id};
		String query =
				"UPDATE fitmi_weight_log SET weight = '" + weight+"' WHERE id =?";

		Cursor cu = db.rawQuery(query, args);
		cu.moveToFirst();
		cu.close(); 
	}
	


	public boolean deleteItem(String id)
	{
		return db.delete("fitmi_weight_log", "id" + "=" + id, null) > 0;
	}
	
	public ArrayList<WeightSet> selectDailyWeightGraph(String date)
	{
		if(weightGraphData.size()>0)
			weightGraphData.clear();
		
		//String queryString = "SELECT * FROM fitmi_weight_log WHERE date_added between '"+date+" 00:00:01' AND '"+date+" 23:59:59'";
		
		String queryString = "SELECT * FROM fitmi_weight_log WHERE user_id=? AND user_profile_id = ? AND date_added between '"+Constants.conditionDate+" 00:00:01' AND '"+Constants.conditionDate+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {Constants.USER_ID,Constants.PROFILE_ID});

		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {	
				
				do {
					
					WeightSet bpObject = new WeightSet();
					
					bpObject.setWeight(c.getString(3));
					bpObject.setLogTime(c.getString(4));
					
					weightGraphData.add(bpObject);
					
				} while (c.moveToNext());				
			}
		}
		c.close();
		return weightGraphData;
	} 
	
	
	public ArrayList<WeightSet> selectDailyWeightGraph(CalenderFirsLastDay firstLastDayObj)
	{
		if(weightGraphData.size()>0)
			weightGraphData.clear();
		
		/*String queryString = "SELECT * FROM fitmi_weight_log WHERE date_added between '"+firstLastDayObj.getFirstDay()+" 00:00:01' AND '"+firstLastDayObj.getLastDay()+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {});*/
		
		String queryString = "SELECT AVG(weight),log_time, date_added FROM fitmi_weight_log WHERE user_id=? AND user_profile_id = ? AND date_added between '"+firstLastDayObj.getFirstDay()+" 00:00:01' AND '"+firstLastDayObj.getFirstDay()+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {Constants.USER_ID,Constants.PROFILE_ID});

		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {	
				
				do {
					
					WeightSet bpObject = new WeightSet();
					
					bpObject.setWeight(c.getString(0));
					bpObject.setLogTime(c.getString(1));
					bpObject.setAddedDate(c.getString(2));
					
					weightGraphData.add(bpObject);
					
				} while (c.moveToNext());				
			}
		}
		c.close();
		return weightGraphData;
	} 
	
	
	public ArrayList<WeightSet> selectWeeklyWeightGraph(CalenderFirsLastDay firstLastDayObj)
	{
		if(weightGraphData.size()>0)
			weightGraphData.clear();		

		
		String queryString = "SELECT AVG(weight),log_time, date_added FROM fitmi_weight_log WHERE user_id=? AND user_profile_id = ? AND date_added between '"+firstLastDayObj.getFirstDay()+" 00:00:01' AND '"+firstLastDayObj.getLastDay()+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {Constants.USER_ID,Constants.PROFILE_ID});

		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {	
				
				do {
					
					WeightSet bpObject = new WeightSet();
					
					bpObject.setWeight(c.getString(0));
					bpObject.setLogTime(c.getString(1));
					bpObject.setAddedDate(c.getString(2));
					
					weightGraphData.add(bpObject);
					
				} while (c.moveToNext());				
			}
		}
		c.close();
		return weightGraphData;
	}
	
	
	
	public ArrayList<WeightSet> selectMonthlyWeightGraph(CalenderFirsLastDay firstLastDayObj)
	{
		if(weightGraphData.size()>0)
			weightGraphData.clear();
		
		/*String queryString = "SELECT * FROM fitmi_weight_log WHERE date_added between '"+firstLastDayObj.getFirstDay()+" 00:00:01' AND '"+firstLastDayObj.getLastDay()+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {});*/
		
		String queryString = "SELECT AVG(weight),log_time, date_added FROM fitmi_weight_log WHERE user_id=? AND user_profile_id = ? AND date_added between '"+firstLastDayObj.getFirstDay()+" 00:00:01' AND '"+firstLastDayObj.getLastDay()+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {Constants.USER_ID,Constants.PROFILE_ID});

		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {	
				
				do {
					
					WeightSet bpObject = new WeightSet();
					
					bpObject.setWeight(c.getString(0));
					bpObject.setLogTime(c.getString(1));
					bpObject.setAddedDate(c.getString(2));
					
					weightGraphData.add(bpObject);
					
				} while (c.moveToNext());				
			}
		}
		c.close();
		return weightGraphData;
	}
	
	
	public String sumWaight(String date)
	{
		String total = "";
		//SQLiteDatabase db = helper.getReadableDatabase();
		
		/*String queryString = "SELECT sum(oz) FROM fitmi_water_log WHERE date_added between '"+Constants.conditionDate+" 00:00:01' AND '"+Constants.conditionDate+" 23:59:59'";
		
		Cursor c = db.rawQuery(queryString, new String[] {});*/
		
		String queryString = "SELECT sum(weight) FROM fitmi_weight_log WHERE user_id=? AND user_profile_id =? AND date_added between '"+date+" 00:00:01' AND '"+date+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {Constants.USER_ID,Constants.PROFILE_ID});
		
		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {
				
				
				total = c.getString(0);
				
			}
		}
		c.close();
		
		return total;			
	}
	
	public String avgWaight(String date)
	{
		String total = "";
		//SQLiteDatabase db = helper.getReadableDatabase();
		
		/*String queryString = "SELECT sum(oz) FROM fitmi_water_log WHERE date_added between '"+Constants.conditionDate+" 00:00:01' AND '"+Constants.conditionDate+" 23:59:59'";
		
		Cursor c = db.rawQuery(queryString, new String[] {});*/
		
		String queryString = "SELECT avg(weight) FROM fitmi_weight_log WHERE user_id=? AND user_profile_id =? AND date_added between '"+date+" 00:00:01' AND '"+date+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {Constants.USER_ID,Constants.PROFILE_ID});
		
		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {
				
				
				total = c.getString(0);
				
			}
		}
		
		c.close();
		return total;			
	}
	public String sumWaightWeekly(CalenderFirsLastDay firstLastDayObj)
	{
		String total = "";		
		
		String queryString = "SELECT sum(weight) FROM fitmi_weight_log WHERE user_id=? AND user_profile_id =? AND date_added between '"+firstLastDayObj.getFirstDay()+" 00:00:01' AND '"+firstLastDayObj.getLastDay()+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {Constants.USER_ID,Constants.PROFILE_ID});
		
		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {
				
				
				total = c.getString(0);
				
			}
		}
		c.close();
		return total;			
	}
	
	public String sumWaightMonthly(CalenderFirsLastDay firstLastDayObj)
	{
		String total = "";
		
		
		String queryString = "SELECT sum(weight) FROM fitmi_weight_log WHERE user_id=? AND user_profile_id =? AND date_added between '"+firstLastDayObj.getFirstDay()+" 00:00:01' AND '"+firstLastDayObj.getLastDay()+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {Constants.USER_ID,Constants.PROFILE_ID});
		
		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {				
				
				total = c.getString(0);				
			}
		}
		
		c.close();
		return total;			
	}
}
