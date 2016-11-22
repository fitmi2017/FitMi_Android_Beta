package com.dts.utils;

import java.io.File;

import android.os.Environment;

public class Constants {

	public static final String ACTION_OPPORTUNITY_DOWNLOAD = "com.dts.teamconnector.OPPORTUNITY_DOWNLOAD";

	public static String[] TEXTLIST = { "aaa", "bbb", "ccc", "ddd", "eee",
			"fff", "ggg", "hhh" };

	public static String[] RELATED_TYPE = { "Self", "Contact",
			"Lead" };
	
	public static String[] RELATED_TYPE_APPOINTMENT = { "--Select Related To--", "Contact",
		"Lead" };

	public static String[] TASK_STATUS_TYPE = { "Pending", "Cancel",
			"Complete", "Hold" };

	public static String[] STAGE_LIST = { "Prospecting", "Qualification",
			"Need Analysis", "Value Proposition", "ID Decision Maker",
			"Perception Analysis", "Proposal/Price Quote",
			"Negotiation/Review", "Closed Win", "Closed Loss" };

	public static String[] OPPORTUNITY_TYPE = { "Existing Business",
			"New Business" };

	public static String CRMFOLDER = Environment.getExternalStorageDirectory()
			+ File.separator + "CRM" + File.separator;

	public static String[] NUMLIST = { "1", "2", "3", "4", "5", "6", "7", "8",
			"9", "10" };

	public static final String CountryListFilePath = CRMFOLDER
			+ "countrylist.txt";

	public static final String OpportunityFilePath = CRMFOLDER
			+ "opportunityfile.txt";

	public static final String LeadSourceFilePath = CRMFOLDER
			+ "leadsourcefile.txt";

	public static final String ShareListFilePath = CRMFOLDER
			+ "filesharelist.txt";

	// public static final String OpportunityTagsListFilePath = CRMFOLDER
	// + "opportunitytagslist.txt";

	public static final String NotesListFilePath = CRMFOLDER + "noteslist.txt";

	public static final String TaskDdlFilePath = CRMFOLDER + "tasklist.txt";

	public static final String CRM_FILE_PATH = CRMFOLDER + "Files/";
	
	public static final String CRM_UPLOAD_PATH = CRMFOLDER + "Uploads/";

	public static final String TOKEN = "HOST123456";

	public static final int CONNECTION_TIMED_OUT = 1000 * 30;
	//public static final int CONNECTION_TIMED_OUT = 1000 * 60 * 2; // In
																	// milliseconds
																	// (2
																	// minutes)
	public static final int CONNECTION_TIMED_OUT_FILE = 1000 * 60 * 5; // In
																		// milliseconds
																		// (5
																		// minutes)

	public static String[] getReminderMinutes() {
		String[] minutes = new String[60];
		for (int i = 0; i < 60; i++) {
			minutes[i] = String.valueOf(i + 1) + " mins";
		}

		return minutes;
	}

	public static final String ACTION_LOGOUT = "com.dts.teamconnector.ACTION_LOGOUT";

	public static boolean isSaveClicked = false;

	public static boolean isCustomerSection = false;

}
