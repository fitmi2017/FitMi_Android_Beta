package com.dialog;

import com.dts.classes.CommonFunction;
import com.fitmi.R;
import com.fitmi.activitys.TabActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;

public class Alert {
	
	public static void showAlert(Context context,String message)
	{
		new AlertDialog.Builder(context,AlertDialog.THEME_HOLO_DARK)
	    .setTitle("FitMi")
	    .setMessage(message)
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // continue with delete
	        }
	     })
	    /*.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // do nothing
	        }
	     })*/
	    .setIcon(android.R.drawable.ic_dialog_alert)
	     .show();
	}
	
	public static void showAlertSignIn(Context context,String message,final CommonFunction mCommonFunction)
	{
		new AlertDialog.Builder(context,AlertDialog.THEME_HOLO_DARK)
	    .setTitle("FitMi")
	    .setMessage(message)
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // continue with delete
	        	mCommonFunction.showIntent(TabActivity.class);
	        }
	     })
	    /*.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // do nothing
	        }
	     })*/
	    .setIcon(android.R.drawable.ic_dialog_alert)
	     .show();
	}
	
	public static void showAlertForgotPass(Context context,String message,final CommonFunction mCommonFunction)
	{
		new AlertDialog.Builder(context, android.support.v7.appcompat.R.style.Theme_AppCompat_Light_Dialog)
	    .setTitle("FitMi")
	    .setMessage(message)
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	        }
	     })
	     .show();
	}
	public static void showDialog(Context context)
	{
		
	        final Dialog dialog=new Dialog(context/*,R.style.Theme_Dialog*/);
	        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        dialog.setContentView(R.layout.dialog_replace_add_cancel);
	        dialog.findViewById(R.id.replacebtn).setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                dialog.dismiss();
	            }
	        });
	        dialog.findViewById(R.id.addbtn).setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                dialog.dismiss();
	            }
	        });
	        dialog.findViewById(R.id.cancelbtn).setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                dialog.dismiss();
	            }
	        });
	        dialog.show();

	   
	}

}
