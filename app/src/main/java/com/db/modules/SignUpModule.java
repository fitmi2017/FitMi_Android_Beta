package com.db.modules;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.db.DatabaseHelper;
import com.fitmi.dao.SignUpDAO;
import com.fitmi.utils.Constants;

public class SignUpModule {

	
	public static void insertSignupTable(SignUpDAO signupData,DatabaseHelper helper)
    {
    	SQLiteDatabase db = helper.getWritableDatabase();
    	
    	String queryString = "INSERT INTO users ("+
									"id,email_address, password, date_created, date_modified, first_name, last_name ) VALUES ("+"'"+signupData.getUserid()+"', '"
								+signupData.getEmailAddress()+"', '"+signupData.getPassword()+"', '"+signupData.getDateCreated()+
								 "', '"+signupData.getDateModify()+"', '"+signupData.getFirstName()+"', '"+signupData.getLastName()+"')";
    	
    	db.execSQL(queryString);
    	
    	db.close();
								 
    }
	
	
	
	
	public static String getPassword(DatabaseHelper helper)
	{
		SQLiteDatabase db = helper.getWritableDatabase();
		
		String pass = "";

		String queryString = "SELECT  password FROM users WHERE email_address = ?";

		Cursor c = db.rawQuery(queryString, new String[] {Constants.LOGIN_MAIL_ID});

		if(c.getCount()>0)
		{
			if (c.moveToFirst()) {
				pass = c.getString(0);
			}

			
		}    	
		c.close();
		db.close();
			return pass;
		
	}
	
	public static void changePassword(String newPassword,DatabaseHelper helper)
	{
		SQLiteDatabase db = helper.getWritableDatabase();

		String[] args = { };
		String query = "UPDATE users SET password ='" + newPassword+"'";			

		Cursor cu = db.rawQuery(query, args);
		cu.moveToFirst();
		cu.close(); 
		
		db.close();
	}
	
}
