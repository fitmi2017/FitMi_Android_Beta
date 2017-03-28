package com.db.modules;

import java.util.ArrayList;

import com.db.DatabaseHelper;
import com.fitmi.dao.BloodPressureDAO;
import com.fitmi.dao.BloodPressureSet;
import com.fitmi.dao.CalenderFirsLastDay;
import com.fitmi.utils.Constants;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BpLogModule extends BaseModule {
	
	
	ArrayList<BloodPressureSet> bpGraphData = new ArrayList<BloodPressureSet>();
	
	
	ArrayList<BloodPressureDAO> bloodPressureListData = new ArrayList<BloodPressureDAO>();
	
	ArrayList<String> total = new ArrayList<String>();

	public BpLogModule(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}
	
	
	public void insertBpInformation(BloodPressureDAO bloodPressureDate)
	{
		String queryString =  "INSERT INTO fitmi_bp_log (" +
				"user_id, userprofile_id, sys, dia, log_time, added_date )"+
				"VALUES ( '"+ bloodPressureDate.getUserId()+"' , '"+bloodPressureDate.getProfileId()+"' , '"+bloodPressureDate.getSys()+"' , "+                                                                        
				"'"+bloodPressureDate.getDia()+"' , '"+bloodPressureDate.getLogTime()+"' , '"+bloodPressureDate.getAddedtime()+"' )";		 

		db.execSQL(queryString);

		//db.close();
	}
	
	
	public ArrayList<BloodPressureDAO> getAverage()
	{
		if(bloodPressureListData.size()>0)
			bloodPressureListData.clear();

		String queryString = "SELECT AVG(sys),AVG(dia) FROM fitmi_bp_log WHERE user_id=? AND userprofile_id = ? AND added_date between '"+Constants.conditionDate+" 00:00:01' AND '"+Constants.conditionDate+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {Constants.USER_ID,Constants.PROFILE_ID});
		
		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {				

				do{
					BloodPressureDAO bloodPressureData = new BloodPressureDAO();	
					
					bloodPressureData.setSys(c.getString(0));
					bloodPressureData.setDia(c.getString(1));			
					
					bloodPressureListData.add(bloodPressureData);
				}while(c.moveToNext());

			}
		}
		return bloodPressureListData;
	}
	
	
	public ArrayList<BloodPressureDAO> getBpInformation()
	{
		if(bloodPressureListData.size()>0)
			bloodPressureListData.clear();
		
		/*String queryString = "SELECT * FROM fitmi_bp_log WHERE added_date between '"+Constants.conditionDate+" 00:00:01' AND '"+Constants.conditionDate+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {});	*/


		String queryString = "SELECT * FROM fitmi_bp_log WHERE user_id=? AND userprofile_id = ? AND added_date between '"+Constants.conditionDate+" 00:00:01' AND '"+Constants.conditionDate+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {Constants.USER_ID,Constants.PROFILE_ID});
		
		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {				

				do{
					BloodPressureDAO bloodPressureData = new BloodPressureDAO();
					
					bloodPressureData.setId(c.getString(0));
					bloodPressureData.setUserId(c.getString(1));
					bloodPressureData.setProfileId(c.getString(2));
					bloodPressureData.setSys(c.getString(3));
					bloodPressureData.setDia(c.getString(4));
					bloodPressureData.setLogTime(c.getString(5));
					bloodPressureData.setAddedtime(c.getString(6));
					
					bloodPressureListData.add(bloodPressureData);
				}while(c.moveToNext());

			}
		}
		return bloodPressureListData;
	}
	
	public ArrayList<BloodPressureDAO> getBpInformationWeekly(CalenderFirsLastDay firstLastDayObj)
	{
		if(bloodPressureListData.size()>0)
			bloodPressureListData.clear();
		
		/*String queryString = "SELECT * FROM fitmi_bp_log WHERE added_date between '"+firstLastDayObj.getFirstDay()+" 00:00:01' AND '"+firstLastDayObj.getLastDay()+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {});*/
		
		String queryString = "SELECT * FROM fitmi_bp_log WHERE user_id=? AND userprofile_id = ? AND added_date between '"+firstLastDayObj.getFirstDay()+" 00:00:01' AND '"+firstLastDayObj.getLastDay()+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {Constants.USER_ID,Constants.PROFILE_ID});

		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {				

				do{
					BloodPressureDAO bloodPressureData = new BloodPressureDAO();
					
					bloodPressureData.setId(c.getString(0));
					bloodPressureData.setUserId(c.getString(1));
					bloodPressureData.setProfileId(c.getString(2));
					bloodPressureData.setSys(c.getString(3));
					bloodPressureData.setDia(c.getString(4));
					bloodPressureData.setLogTime(c.getString(5));
					bloodPressureData.setAddedtime(c.getString(6));
					
					bloodPressureListData.add(bloodPressureData);
				}while(c.moveToNext());

			}
		}
		return bloodPressureListData;
	}
	
	
	public ArrayList<BloodPressureDAO> getBpInformationMonthly(CalenderFirsLastDay firstLastDayObj)
	{
		if(bloodPressureListData.size()>0)
			bloodPressureListData.clear();
		
		/*String queryString = "SELECT * FROM fitmi_bp_log WHERE added_date between '"+firstLastDayObj.getFirstDay()+" 00:00:01' AND '"+firstLastDayObj.getLastDay()+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {});*/
		
		String queryString = "SELECT * FROM fitmi_bp_log WHERE user_id=? AND userprofile_id = ? AND added_date between '"+firstLastDayObj.getFirstDay()+" 00:00:01' AND '"+firstLastDayObj.getLastDay()+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {Constants.USER_ID,Constants.PROFILE_ID});


		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {				

				do{
					BloodPressureDAO bloodPressureData = new BloodPressureDAO();
					
					bloodPressureData.setId(c.getString(0));
					bloodPressureData.setUserId(c.getString(1));
					bloodPressureData.setProfileId(c.getString(2));
					bloodPressureData.setSys(c.getString(3));
					bloodPressureData.setDia(c.getString(4));
					bloodPressureData.setLogTime(c.getString(5));
					bloodPressureData.setAddedtime(c.getString(6));
					
					bloodPressureListData.add(bloodPressureData);
				}while(c.moveToNext());

			}
		}
		return bloodPressureListData;
	}
	
	public BloodPressureSet getEditValue(String id)
	{
		BloodPressureSet bpObject = new BloodPressureSet();
		
		String queryString = "SELECT * FROM fitmi_bp_log WHERE id =?";

		Cursor c = db.rawQuery(queryString, new String[] {id});
		
		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {	
				
				bpObject.setSys(c.getString(3));
				bpObject.setDia(c.getString(4));				
			}
		}
		return bpObject;
		
	}
	
	
	public void editBpInformation(String sys ,String dia ,String id)
	{
		
		
		String[] args = {id};
		String query =
				"UPDATE fitmi_bp_log SET sys = '" + sys
				+ "' , dia = '"+ dia +"' WHERE id =?";

		Cursor cu = db.rawQuery(query, args);
		cu.moveToFirst();
		cu.close(); 
	}

	public boolean deleteItem(String id)
	{
		return db.delete("fitmi_bp_log", "id" + "=" + id, null) > 0;
	}
	
	/*public ArrayList<BloodPressureSet> selectDailyBloodPressureGraph(String date)
	{
		if(bpGraphData.size()>0)
			bpGraphData.clear();	
		
		String queryString = "SELECT * FROM fitmi_bp_log WHERE user_id=? AND userprofile_id = ? AND added_date between '"+date+" 00:00:01' AND '"+date+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {Constants.USER_ID,Constants.PROFILE_ID});

		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {	
				
				do {
					
					BloodPressureSet bpObject = new BloodPressureSet();
					
					bpObject.setSys(c.getString(3));
					bpObject.setDia(c.getString(4));
					bpObject.setLogTime(c.getString(5));
					
					bpGraphData.add(bpObject);
					
				} while (c.moveToNext());				
			}
		}
		return bpGraphData;
	} */
	
	public ArrayList<BloodPressureSet> avgBpDate(CalenderFirsLastDay firstLastDayObj)
	{
		if(bpGraphData.size()>0)
			bpGraphData.clear();
		
		String queryString = "SELECT AVG(sys),AVG(dia) FROM fitmi_bp_log WHERE user_id=? AND userprofile_id = ? AND added_date between '"+firstLastDayObj.getFirstDay()+" 00:00:01' AND '"+firstLastDayObj.getLastDay()+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {Constants.USER_ID,Constants.PROFILE_ID});

		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {	
				
				do {
					
					BloodPressureSet bpObject = new BloodPressureSet();
					
					bpObject.setSys(c.getString(0));
					bpObject.setDia(c.getString(1));					
					
					bpGraphData.add(bpObject);
					
				} while (c.moveToNext());				
			}
		}
		return bpGraphData;
	}
	
	
	public ArrayList<BloodPressureSet> selectDailylyBloodPressureGraph(CalenderFirsLastDay firstLastDayObj)
	{
		if(bpGraphData.size()>0)
			bpGraphData.clear();
		
		//AVG(sys),AVG(dia),log_time, added_date
		
		String queryString = "SELECT AVG(sys),AVG(dia),log_time, added_date FROM fitmi_bp_log WHERE user_id=? AND userprofile_id = ? AND added_date between '"+firstLastDayObj.getFirstDay()+" 00:00:01' AND '"+firstLastDayObj.getFirstDay()+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {Constants.USER_ID,Constants.PROFILE_ID});

		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {	
				
				do {
					
					BloodPressureSet bpObject = new BloodPressureSet();
					
					bpObject.setSys(c.getString(0));
					bpObject.setDia(c.getString(1));
					bpObject.setLogTime(c.getString(2));
					bpObject.setAddedDate(c.getString(3));				
					
					
					bpGraphData.add(bpObject);
					
				} while (c.moveToNext());				
			}
		}
		return bpGraphData;
	} 
	
	public ArrayList<BloodPressureSet> selectWeeklyBloodPressureGraph(CalenderFirsLastDay firstLastDayObj)
	{
		if(bpGraphData.size()>0)
			bpGraphData.clear();
		
		//AVG(sys),AVG(dia),log_time, added_date
		
		String queryString = "SELECT AVG(sys),AVG(dia),log_time, added_date FROM fitmi_bp_log WHERE user_id=? AND userprofile_id = ? AND added_date between '"+firstLastDayObj.getFirstDay()+" 00:00:01' AND '"+firstLastDayObj.getLastDay()+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {Constants.USER_ID,Constants.PROFILE_ID});

		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {	
				
				do {
					
					BloodPressureSet bpObject = new BloodPressureSet();
					
					bpObject.setSys(c.getString(0));
					bpObject.setDia(c.getString(1));
					bpObject.setLogTime(c.getString(2));
					bpObject.setAddedDate(c.getString(3));				
					
					
					bpGraphData.add(bpObject);
					
				} while (c.moveToNext());				
			}
		}
		return bpGraphData;
	} 
	
	public ArrayList<BloodPressureSet> selectMonthlyBloodPressureGraph(CalenderFirsLastDay firstLastDayObj)
	{
		if(bpGraphData.size()>0)
			bpGraphData.clear();
		
		String queryString = "SELECT AVG(sys),AVG(dia),log_time, added_date FROM fitmi_bp_log WHERE user_id=? AND userprofile_id = ? AND added_date between '"+firstLastDayObj.getFirstDay()+" 00:00:01' AND '"+firstLastDayObj.getLastDay()+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {Constants.USER_ID,Constants.PROFILE_ID});


		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {	
				
				do {
					
					BloodPressureSet bpObject = new BloodPressureSet();
					
					bpObject.setSys(c.getString(0));
					bpObject.setDia(c.getString(1));
					bpObject.setLogTime(c.getString(2));
					bpObject.setAddedDate(c.getString(3));
					
					bpGraphData.add(bpObject);
					
				} while (c.moveToNext());				
			}
		}
		return bpGraphData;
	}
	
	
	
	
	

	
	
	public ArrayList<String>  sumBpDaily(String date)
	{
		//String total = "";
		
		if(total.size()>0)
			total.clear();
		
		String queryString = "SELECT sum(sys),sum(dia) FROM fitmi_bp_log WHERE user_id=? AND userprofile_id =? AND added_date between '"+date+" 00:00:01' AND '"+date+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {Constants.USER_ID,Constants.PROFILE_ID});
		
		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {	
				
				total.add(c.getString(0));
				total.add(c.getString(1));
					
			}
		}		
		
		return total;			
	}
	
	
	public ArrayList<String> sumBpWeekly(CalenderFirsLastDay firstLastDayObj)
	{
		
		if(total.size()>0)
			total.clear();
		
		String queryString = "SELECT sum(sys),sum(dia) FROM fitmi_bp_log WHERE user_id=? AND userprofile_id =? AND added_date between '"+firstLastDayObj.getFirstDay()+" 00:00:01' AND '"+firstLastDayObj.getLastDay()+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {Constants.USER_ID,Constants.PROFILE_ID});
		
		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {				
				
				total.add(c.getString(0));
				total.add(c.getString(1));
			}
		}		
		
		return total;			
	}
	
	
	public ArrayList<String> sumBpMonthly(CalenderFirsLastDay firstLastDayObj)
	{
		
		if(total.size()>0)
			total.clear();
		
		String queryString = "SELECT sum(sys),sum(dia) FROM fitmi_bp_log WHERE user_id=? AND userprofile_id =? AND added_date between '"+firstLastDayObj.getFirstDay()+" 00:00:01' AND '"+firstLastDayObj.getLastDay()+" 23:59:59'";

		Cursor c = db.rawQuery(queryString, new String[] {Constants.USER_ID,Constants.PROFILE_ID});
		
		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {				
				
				total.add(c.getString(0));
				total.add(c.getString(1));		
			}
		}		
		
		return total;			
	}
}
