package com.fitmi.dao;

import java.util.ArrayList;

import com.callback.Item;

public class FitmiFoodLogDAO implements Item{
	
	private String userId = "";
	private String userProfileId = "";
	private String mealId = "0";
	private String foodName = "";
	private String description ="";		
	private String calory = "0";
	private String servingSize = "";
	private String servingWeightGrams = "";
	private String referenceFoodId = "";
	private String logTime = "";
	private String dateAdded = "";
	private String foodLogId = "";
	private String mealType = "";
	private String mealWeight = "0";
	private String favourite = "";	
	private String mealFavourite = "";	
	private ArrayList<String> mealidList;
	private String brandName = "";
	
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getMealFavourite() {
		return mealFavourite;
	}
	public void setMealFavourite(String mealFavourite) {
		this.mealFavourite = mealFavourite;
	}
	
	public ArrayList<String> getMealidList() {
		return mealidList;
	}
	public void setMealidList(ArrayList<String> mealidList) {
		this.mealidList = mealidList;
	}
	public String getFavourite() {
		return favourite;
	}
	public void setFavourite(String favourite) {
		this.favourite = favourite;
	}
	public String getMealWeight() {
		return mealWeight;
	}
	public void setMealWeight(String mealWeight) {
		this.mealWeight = mealWeight;
	}
	public String getMealType() {
		return mealType;
	}
	public void setMealType(String mealType) {
		this.mealType = mealType;
	}
	public String getFoodLogId() {
		return foodLogId;
	}
	public void setFoodLogId(String foodLogId) {
		this.foodLogId = foodLogId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserProfileId() {
		return userProfileId;
	}
	public void setUserProfileId(String userProfileId) {
		this.userProfileId = userProfileId;
	}
	public String getMealId() {
		return mealId;
	}
	public void setMealId(String mealId) {
		this.mealId = mealId;
	}
	public String getFoodName() {
		return foodName;
	}
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCalory() {
		return calory;
	}
	public void setCalory(String calory) {
		this.calory = calory;
	}
	public String getServingSize() {
		return servingSize;
	}
	public void setServingSize(String servingSize) {
		this.servingSize = servingSize;
	}
	public String getReferenceFoodId() {
		return referenceFoodId;
	}
	public void setReferenceFoodId(String referenceFoodId) {
		this.referenceFoodId = referenceFoodId;
	}
	public String getLogTime() {
		return logTime;
	}
	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}
	public String getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}
	@Override
	public boolean isSection() {
		// TODO Auto-generated method stub
		return false;
	}
	public String getServingWeightGrams() {
		return servingWeightGrams;
	}
	public void setServingWeightGrams(String servingWeightGrams) {
		this.servingWeightGrams = servingWeightGrams;
	}
	

}
