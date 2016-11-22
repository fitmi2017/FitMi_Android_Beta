package com.fitmi.dao;

public class MealTypeDAO {
	
	private String mealTypeName = "";
	private String imageId = "0";
	private String mealTypeDefault = "0";
	private String active = "1";
	
	public String getMealTypeName() {
		return mealTypeName;
	}
	public void setMealTypeName(String mealTypeName) {
		this.mealTypeName = mealTypeName;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public String getMealTypeDefault() {
		return mealTypeDefault;
	}
	public void setMealTypeDefault(String mealTypeDefault) {
		this.mealTypeDefault = mealTypeDefault;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}

}
