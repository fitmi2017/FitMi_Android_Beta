package com.db.modules;

import java.util.ArrayList;

import com.db.DatabaseHelper;
import com.fitmi.dao.CalenderFirsLastDay;
import com.fitmi.dao.SleepLogADO;
import com.fitmi.dao.WaterLogDAO;
import com.fitmi.utils.Constants;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SleepModule extends BaseModule{
	
	ArrayList<SleepLogADO> sleepList = new ArrayList<SleepLogADO>();

	public SleepModule(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}
	
	
	public void setSleepLog(SleepLogADO sleepData)
	{		
		String queryString = "INSERT INTO fitmi_sleep_log ("+
				"user_id, user_profile_id, hours, log_time, date_added ) VALUES ("+
				"'"+sleepData.getUserId()+"', '"+sleepData.getProfileId()+"', '"+sleepData.getHours()+
				"', '"+sleepData.getLogTime()+"', '"+sleepData.getLogDate()+"')";

		db.execSQL(queryString);
	}
	
	public ArrayList<SleepLogADO> selectSleepLogList()//DatabaseHelper helper
	{
		if(sleepList.size()>0)
			sleepList.clear();
		
		//SQLiteDatabase db = helper.getReadableDatabase();		
		
		String queryString = "SELECT * FROM fitmi_sleep_log WHERE user_id=? AND user_profile_id = ? AND date_added between '"+Constants.conditionDate+" 00:00:01' AND '"+Constants.conditionDate+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {Constants.USER_ID,Constants.PROFILE_ID});
		

		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {				

				do{
					
					SleepLogADO sleepObject = new SleepLogADO();
					
					sleepObject.setId(c.getString(0));
					sleepObject.setUserId(c.getString(1));
					sleepObject.setProfileId(c.getString(2));
					sleepObject.setHours(c.getString(3));
					sleepObject.setLogTime(c.getString(4));
					sleepObject.setLogDate(c.getString(5));
					
					sleepList.add(sleepObject);
					
				}while(c.moveToNext());

			}
		}
		//db.close();
		return sleepList;
	}
	
	public String sumSleepHour()
	{
		String total = "";
		
		String queryString = "SELECT sum(hours) FROM fitmi_sleep_log WHERE user_id=? AND user_profile_id =? AND date_added between '"+Constants.conditionDate+" 00:00:01' AND '"+Constants.conditionDate+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {Constants.USER_ID,Constants.PROFILE_ID});
		
		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {				
				
				total = c.getString(0);				
			}
		}		
		
		return total;			
	}
	
	
	public String sumSleepHourDaily(String date)
	{
		String total = "";
		
		String queryString = "SELECT sum(hours) FROM fitmi_sleep_log WHERE user_id=? AND user_profile_id =? AND date_added between '"+date+" 00:00:01' AND '"+date+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {Constants.USER_ID,Constants.PROFILE_ID});
		
		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {				
				
				total = c.getString(0);				
			}
		}		
		
		return total;			
	}
	
	
	public String sumSleepHourWeekly(CalenderFirsLastDay firstLastDayObj)
	{
		String total = "";
		
		String queryString = "SELECT sum(hours) FROM fitmi_sleep_log WHERE user_id=? AND user_profile_id =? AND date_added between '"+firstLastDayObj.getFirstDay()+" 00:00:01' AND '"+firstLastDayObj.getLastDay()+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {Constants.USER_ID,Constants.PROFILE_ID});
		
		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {				
				
				total = c.getString(0);				
			}
		}		
		
		return total;			
	}
	
	
	public String sumSleepHourMonthly(CalenderFirsLastDay firstLastDayObj)
	{
		String total = "";
		
		String queryString = "SELECT sum(hours) FROM fitmi_sleep_log WHERE user_id=? AND user_profile_id =? AND date_added between '"+firstLastDayObj.getFirstDay()+" 00:00:01' AND '"+firstLastDayObj.getLastDay()+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {Constants.USER_ID,Constants.PROFILE_ID});
		
		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {				
				
				total = c.getString(0);				
			}
		}		
		
		return total;			
	}
	
	
	public SleepLogADO getEditValue(String id)
	{
		SleepLogADO sleepObject = new SleepLogADO();
		
		String queryString = "SELECT * FROM fitmi_sleep_log WHERE id =?";

		Cursor c = db.rawQuery(queryString, new String[] {id});
		
		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {	
				
				sleepObject.setHours(c.getString(3));	
			}
		}
		
		c.close();
		return sleepObject;
		
	}
	
	
	public void editSleepInformation(String hour,String id)
	{
		
		String[] args = {id};
		String query =
				"UPDATE fitmi_sleep_log SET hours = '" + hour
				+ "' WHERE id =?";

		Cursor cu = db.rawQuery(query, args);
		cu.moveToFirst();
		cu.close(); 
	}

}
