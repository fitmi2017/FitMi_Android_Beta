package com.db.modules;

import java.util.ArrayList;

import com.db.DatabaseHelper;
import com.fitmi.dao.UnitItemDAO;
import com.fitmi.dao.WaterLogDAO;
import com.fitmi.utils.Constants;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UnitModule extends BaseModule{
	
	ArrayList<UnitItemDAO> unitItem = new ArrayList<UnitItemDAO>();
	
	String queryString = "";

	public UnitModule(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}
	
	
	public void setUnitLog(UnitItemDAO unitData)
	{
		
		if(!Exists(unitData.getProfileId(),unitData.getType())){
			
			queryString = "INSERT INTO fitmi_units_log ("+
					"user_id, user_profile_id, unit_id, type ) VALUES ("+
					"'"+unitData.getUserId()+"', '"+unitData.getProfileId()+"', '"+unitData.getUnitId()+
					"', '"+unitData.getType()+"')";
			
			db.execSQL(queryString);
		}
		else{
			
			
			String[] args = { unitData.getProfileId(), unitData.getType()};
			String query =
					"UPDATE fitmi_units_log SET unit_id ='" + unitData.getUnitId()+"' , type ='"+unitData.getType()
					+ "' WHERE user_profile_id =? AND type =?";

			Cursor cu = db.rawQuery(query, args);
			cu.moveToFirst();
		}
		

				
	}
	
	
	public ArrayList<UnitItemDAO> selectUnitLogList()
	{
		if(unitItem.size()>0)
			unitItem.clear();
		
		
		String queryString = "SELECT * FROM fitmi_units_log WHERE user_profile_id = ? ";

		Cursor c = db.rawQuery(queryString, new String[] {Constants.PROFILE_ID});
		

		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {				

				do{
					
					UnitItemDAO unitObject = new UnitItemDAO();
					
					unitObject.setUserId(c.getString(1));
					unitObject.setProfileId(c.getString(2));
					unitObject.setUnitId(c.getString(3));
					unitObject.setType(c.getString(4));
					
					unitItem.add(unitObject);
					
				}while(c.moveToNext());

			}
		}
		//db.close();
		return unitItem;
	}

	
	public boolean Exists(String profileId,String unitType) {
		
		Cursor cursor = db.rawQuery("select * from fitmi_units_log where user_profile_id = ? and type = ?", 
				new String[] { profileId,unitType });
		boolean exists = (cursor.getCount() > 0);
		cursor.close();
		return exists;
	}
}
