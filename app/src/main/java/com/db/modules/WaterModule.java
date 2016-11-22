package com.db.modules;

import java.util.ArrayList;
import com.db.DatabaseHelper;
import com.fitmi.dao.BloodPressureSet;
import com.fitmi.dao.CalenderFirsLastDay;
import com.fitmi.dao.WaterLogDAO;
import com.fitmi.utils.Constants;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class WaterModule extends BaseModule{
	
	ArrayList<WaterLogDAO> walterList = new ArrayList<WaterLogDAO>();

	public WaterModule(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	public void setWaterLog(WaterLogDAO waterData,DatabaseHelper helper)
	{
		SQLiteDatabase db = helper.getWritableDatabase();
		
		String queryString = "INSERT INTO fitmi_water_log ("+
				"user_id, userprofile_id, oz, log_time, date_added ) VALUES ("+
				"'"+waterData.getUserId()+"', '"+waterData.getProfileId()+"', '"+waterData.getOzValue()+
				"', '"+waterData.getLogTime()+"', '"+waterData.getLogDate()+"')";

		db.execSQL(queryString);

		db.close();
	}
	
	public ArrayList<WaterLogDAO> selectWaterLogList()//DatabaseHelper helper
	{
		if(walterList.size()>0)
			walterList.clear();
		
		//SQLiteDatabase db = helper.getReadableDatabase();		
		
		String queryString = "SELECT * FROM fitmi_water_log WHERE user_id=? AND userprofile_id = ? AND date_added between '"+Constants.conditionDate+" 00:00:01' AND '"+Constants.conditionDate+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {Constants.USER_ID,Constants.PROFILE_ID});
		

		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {				

				do{
					
					WaterLogDAO waterObject = new WaterLogDAO();
					
					waterObject.setId(c.getString(0));
					waterObject.setUserId(c.getString(1));
					waterObject.setProfileId(c.getString(2));
					waterObject.setOzValue(c.getString(3));
					waterObject.setLogTime(c.getString(4));
					waterObject.setLogDate(c.getString(5));
					
					walterList.add(waterObject);
					
				}while(c.moveToNext());

			}
		}
		c.close();
		return walterList;
	}
	
	public String sumWaterOz()
	{
		String total = "";
		
		String queryString = "SELECT sum(oz) FROM fitmi_water_log WHERE user_id=? AND userprofile_id =? AND date_added between '"+Constants.conditionDate+" 00:00:01' AND '"+Constants.conditionDate+" 23:59:59'";

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
	
	
	public String sumWaterOzDaily(String date)
	{
		String total = "";
		
		String queryString = "SELECT sum(oz) FROM fitmi_water_log WHERE user_id=? AND userprofile_id =? AND date_added between '"+date+" 00:00:01' AND '"+date+" 23:59:59'";

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
	
	
	public String sumWaterOzWeekly(CalenderFirsLastDay firstLastDayObj)
	{
		String total = "";
		
		String queryString = "SELECT sum(oz) FROM fitmi_water_log WHERE user_id=? AND userprofile_id =? AND date_added between '"+firstLastDayObj.getFirstDay()+" 00:00:01' AND '"+firstLastDayObj.getLastDay()+" 23:59:59'";

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
	
	
	public String sumWaterOzMonthly(CalenderFirsLastDay firstLastDayObj)
	{
		String total = "";
		
		String queryString = "SELECT sum(oz) FROM fitmi_water_log WHERE user_id=? AND userprofile_id =? AND date_added between '"+firstLastDayObj.getFirstDay()+" 00:00:01' AND '"+firstLastDayObj.getLastDay()+" 23:59:59'";

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
	
	
	public WaterLogDAO getEditValue(String id)
	{
		WaterLogDAO waterObject = new WaterLogDAO();
		
		String queryString = "SELECT * FROM fitmi_water_log WHERE id =?";

		Cursor c = db.rawQuery(queryString, new String[] {id});
		
		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {	
				
				waterObject.setOzValue(c.getString(3));	
			}
		}
		
		c.close();
		return waterObject;
		
	}
	
	
	public void editWaterInformation(String sys,String id)
	{
		
		String[] args = {id};
		String query =
				"UPDATE fitmi_water_log SET oz = '" + sys
				+ "' WHERE id =?";

		Cursor cu = db.rawQuery(query, args);
		cu.moveToFirst();
		cu.close(); 
	}
}
