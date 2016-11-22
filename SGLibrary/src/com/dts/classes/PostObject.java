package com.dts.classes;

/*
 * FOR POSTING TO WEBSERVICE WE NEED ONLY TWO TYPES OF DATA.
 * STRING AND FILE
 * KEY
 */

public class PostObject {

	private boolean isFile;
	private String string_value;
	private String file_url;

	public boolean isFileAvailable() {
		return this.isFile;
	}

	public void setFile(boolean isFile) {
		this.isFile = isFile;
	}

	public String getString_value() {
		return string_value;
	}

	public void setString_value(String string_value) {
		this.string_value = string_value;
	}

	public String getFile_url() {
		return file_url;
	}

	public void setFile_url(String file_url) {
		this.file_url = file_url;
	}

}
