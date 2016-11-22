/*
 * Copyright (C) 2007-2011 Geometer Plus <contact@geometerplus.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 */

package com.fitmi.utils;
import java.io.*;
import java.util.Date;

import android.content.*;
import android.os.Build;
import android.os.Process;

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
	private final Context myContext;
	private String localPath;
	String extStorageDirectory = "/sdcard/Fitmi/Fitmi.txt";
	 private File cacheDir;
	 EmailSendClass email = new EmailSendClass();
	public ExceptionHandler(Context context) {
		myContext = context;
	}

	public void uncaughtException(Thread thread, Throwable exception) {
		StringWriter stackTrace = new StringWriter();
		exception.printStackTrace(new PrintWriter(stackTrace));
		
		System.err.println(stackTrace);// You can use LogCat too
		   //Find the dir to save cached images also add permission to manifastfile 
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
        {
            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"Fitmi");
        }
        else
            cacheDir=myContext.getCacheDir();
        if(!cacheDir.exists())
            cacheDir.mkdirs();

		    if (cacheDir != null) 
		    {		     
		    	 cacheDir=new File(cacheDir,"Fitmi.txt");
		    	
		    	writeToFile(stackTrace.toString(), cacheDir);
		    	//email.sendMail(myContext, "greja@objectsol.in",extStorageDirectory);
		    	//email.sendMail(myContext, " abhishekdigitalarm@gmail.com",extStorageDirectory);
		    }
//
		Process.killProcess(Process.myPid());
		System.exit(10);
	}
	   void writeToFile(String stacktrace, File filename) {
		    try {
		    	stacktrace = stacktrace+"\n Version : 6.0\n Device :"+getDeviceName()+"\nOS : "+Build.VERSION.RELEASE;
		      BufferedWriter bos = new BufferedWriter(new FileWriter(filename,true));
		      bos.write(stacktrace+"\n\n----------------------New Exception------------------------------\n\n");
		      bos.flush();
		      bos.close();
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		  }
	   
	   public String getDeviceName() {
		   String manufacturer = Build.MANUFACTURER;
		   String model = Build.MODEL;
		   if (model.startsWith(manufacturer)) {
		     return capitalize(model);
		   } else {
		     return capitalize(manufacturer) + " " + model;
		   }
		 }


		 private String capitalize(String s) {
		   if (s == null || s.length() == 0) {
		     return "";
		   }
		   char first = s.charAt(0);
		   if (Character.isUpperCase(first)) {
		     return s;
		   } else {
		     return Character.toUpperCase(first) + s.substring(1);
		   }
		 } 
}
