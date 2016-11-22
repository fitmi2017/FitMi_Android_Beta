package com.fitmi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class EmailSendClass {

	public void sendMail(Context context, String emailTo, String filePaths) {
		final Intent intent = new Intent(Intent.ACTION_SEND);
		System.out.println("send mail started");
		intent.setType("text/xml");
	//	
		intent.putExtra(Intent.EXTRA_EMAIL, "avinash.pandey@dreamztech.com");
		intent.putExtra(Intent.EXTRA_SUBJECT, "MetroNational Report");
		intent.putExtra(Intent.EXTRA_TEXT, "Send mail from Android");

		// Uri phtURL = Uri.parse("file:///sdcard/Report.pdf");
		Uri phtURL = Uri.parse("file://" + filePaths);
		if (phtURL != null) {
			try {

				System.out.println("PATH>>" + phtURL);
				intent.putExtra(Intent.EXTRA_STREAM, phtURL);
			} catch (Exception e) {
				// Toast.makeText( this, "Unable to attach pdf file.",
				// Toast.LENGTH_SHORT).show();
			}
		}
		try {
			// startActivityForResult(Intent.createChooser(intent,
			// "Send mail..."),0);
			System.out.println("context.getPackageName()>>" + context.getPackageName());
			Toast.makeText(context, "There are no email clients installed.",
					 Toast.LENGTH_SHORT).show();
			
			
			context.startActivity(Intent.createChooser(intent, "Send mail...")); // It
																					// need
														// to
																					// open
			
		} catch (android.content.ActivityNotFoundException ex) {
			// Toast.makeText(this, "There are no email clients installed.",
			// Toast.LENGTH_SHORT).show();
			System.out.println("Activity not found");
		}
		
		
	}
}

/*
 * public static void email(Context context, String emailTo, String emailCC,
 * String subject, String emailText, List<String> filePaths) { //need to
 * "send multiple" to get more than one attachment final Intent emailIntent =
 * new Intent(android.content.Intent.ACTION_SEND_MULTIPLE);
 * emailIntent.setType("text/plain");
 * emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new
 * String[]{emailTo}); // emailIntent.putExtra(android.content.Intent.EXTRA_CC,
 * // new String[]{emailCC}); //has to be an ArrayList ArrayList<Uri> uris = new
 * ArrayList<Uri>(); //convert from paths to Android friendly Parcelable Uri's
 * for (String file : filePaths) { File fileIn = new File(file); Uri u =
 * Uri.fromFile(fileIn); uris.add(u); }
 * emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
 * context.startActivity(Intent.createChooser(emailIntent, "Send mail...")); }
 */
