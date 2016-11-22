package com.fitmi.dao;

import android.widget.ImageView;

public class FoodActivityActive {
	
	private int foodActive = 0;
	private int activityActive = 0;	
	
	boolean clickBreackfast = false;
	boolean clickActivity = false;
	
	ImageView breakfast;	
	ImageView activityList;
	
	int breakfastClick = 0, activityClick = 0;
	
	String date = "";
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getBreakfastClick() {
		return breakfastClick;
	}
	public void setBreakfastClick(int breakfastClick) {
		this.breakfastClick = breakfastClick;
	}
	public int getActivityClick() {
		return activityClick;
	}
	public void setActivityClick(int activityClick) {
		this.activityClick = activityClick;
	}
	public boolean isClickBreackfast() {
		return clickBreackfast;
	}
	public void setClickBreackfast(boolean clickBreackfast) {
		this.clickBreackfast = clickBreackfast;
	}
	public boolean isClickActivity() {
		return clickActivity;
	}
	public void setClickActivity(boolean clickActivity) {
		this.clickActivity = clickActivity;
	}
	public ImageView getBreakfast() {
		return breakfast;
	}
	public void setBreakfast(ImageView breakfast) {
		this.breakfast = breakfast;
	}
	public ImageView getActivityList() {
		return activityList;
	}
	public void setActivityList(ImageView activityList) {
		this.activityList = activityList;
	}
	
	public int getFoodActive() {
		return foodActive;
	}
	public void setFoodActive(int foodActive) {
		this.foodActive = foodActive;
	}
	public int getActivityActive() {
		return activityActive;
	}
	public void setActivityActive(int activityActive) {
		this.activityActive = activityActive;
	}

}
