package com.fitmi.dao;

public class UserInfoDAO {
	
	private String userId = "0";
	private String heightFt = "";
	private String heightInc = "";
	private String weight = "";
	private String dateOfBirth = "";
	private String activityLevel = "";
	private String dailyCaloryIntake = "";
	private String firstName = "";
	private String lastName = "";
	private String profileId = "";
	private String gender = "";
	private String picturePath = "";
	
	
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getProfileId() {
		return profileId;
	}
	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getHeightFt() {
		return heightFt;
	}
	public void setHeightFt(String heightFt) {
		this.heightFt = heightFt;
	}
	public String getHeightInc() {
		return heightInc;
	}
	public void setHeightInc(String heightInc) {
		this.heightInc = heightInc;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getActivityLevel() {
		return activityLevel;
	}
	public void setActivityLevel(String activityLevel) {
		this.activityLevel = activityLevel;
	}
	public String getDailyCaloryIntake() {
		return dailyCaloryIntake;
	}
	public void setDailyCaloryIntake(String dailyCaloryIntake) {
		this.dailyCaloryIntake = dailyCaloryIntake;
	}	
	

}
