package com.fitmi.dao;

public class WaterLogDAO {
	
	private String userId = "";
	private String profileId = "";
	private String ozValue = "";
	private String logTime = "";
	private String logDate = "";
	private String id = "";
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getProfileId() {
		return profileId;
	}
	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}
	public String getOzValue() {
		return ozValue;
	}
	public void setOzValue(String ozValue) {
		this.ozValue = ozValue;
	}
	public String getLogTime() {
		return logTime;
	}
	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}
	public String getLogDate() {
		return logDate;
	}
	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}

}
