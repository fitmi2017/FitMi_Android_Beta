package com.fitmi.dao;

public class WeightLogDAO {
	
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	private String userId = "";
	private String profileId = "";
	private String weight = "";
	private String logTime = "";
	private String addedtime = "";
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
	
	public String getLogTime() {
		return logTime;
	}
	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}
	public String getAddedtime() {
		return addedtime;
	}
	public void setAddedtime(String addedtime) {
		this.addedtime = addedtime;
	}
	
	

}
