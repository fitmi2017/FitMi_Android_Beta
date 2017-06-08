package com.db.modules;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.db.DatabaseHelper;
import com.fitmi.dao.SignUpDAO;
import com.fitmi.utils.Constants;

public class LoginModule {

	public static int getLogin(SignUpDAO signupData ,DatabaseHelper helper)
	{
		SQLiteDatabase db = helper.getWritableDatabase();


		String queryString = "SELECT id, email_address, password, active, deleted FROM users WHERE email_address = ? AND password = ?";

		Cursor c = db.rawQuery(queryString, new String[] { signupData.getEmailAddress(), signupData.getPassword()});

		if(c.getCount()>0)
		{
			if (c.moveToLast()) {
				Constants.USER_ID = c.getString(0);
			}

			c.close();
			db.close();
			return 1;
		}    	
		else
		{
			c.close();
			db.close();
			return 0;
		}

	}

}
