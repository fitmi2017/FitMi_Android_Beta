package com.fitmi.dao;

import java.util.ArrayList;

import android.widget.ImageView;

public class PlannerWeeklyDAO {
	
	private int snackActive = 0;
	private int lunchActive = 0;
	private int dinnerActive = 0;
	private int breakfastActive = 0;
	private String dateSet = ""; 
	
	boolean clickBreackfast = false;
	boolean clickLunch = false;
	boolean clickDinner = false;
	boolean clickSnacks = false;
	boolean clickActivity = false;
	
	ImageView breakfast;
	ImageView lunch;
	ImageView dinner;
	ImageView snack;
	ImageView activityList;
	
	int breakfastClick = 0,lunchClick = 0, snackclick = 0, dinnerClick =0, activityClick = 0;
	
	boolean breakfastActivate = false,lunchActivate = false , dinnerActivate = false , snackActivate = false , activityActivate =false;
	
	
	public boolean isBreakfastActivate() {
		return breakfastActivate;
	}
	public void setBreakfastActivate(boolean breakfastActivate) {
		this.breakfastActivate = breakfastActivate;
	}
	public boolean isLunchActivate() {
		return lunchActivate;
	}
	public void setLunchActivate(boolean lunchActivate) {
		this.lunchActivate = lunchActivate;
	}
	public boolean isDinnerActivate() {
		return dinnerActivate;
	}
	public void setDinnerActivate(boolean dinnerActivate) {
		this.dinnerActivate = dinnerActivate;
	}
	public boolean isSnackActivate() {
		return snackActivate;
	}
	public void setSnackActivate(boolean snackActivate) {
		this.snackActivate = snackActivate;
	}
	public boolean isActivityActivate() {
		return activityActivate;
	}
	public void setActivityActivate(boolean activityActivate) {
		this.activityActivate = activityActivate;
	}
	public int getBreakfastClick() {
		return breakfastClick;
	}
	public void setBreakfastClick(int breakfastClick) {
		this.breakfastClick = breakfastClick;
	}
	public int getLunchClick() {
		return lunchClick;
	}
	public void setLunchClick(int lunchClick) {
		this.lunchClick = lunchClick;
	}
	public int getSnackclick() {
		return snackclick;
	}
	public void setSnackclick(int snackclick) {
		this.snackclick = snackclick;
	}
	public int getDinnerClick() {
		return dinnerClick;
	}
	public void setDinnerClick(int dinnerClick) {
		this.dinnerClick = dinnerClick;
	}
	public int getActivityClick() {
		return activityClick;
	}
	public void setActivityClick(int activityClick) {
		this.activityClick = activityClick;
	}
	public ImageView getBreakfast() {
		return breakfast;
	}
	public void setBreakfast(ImageView breakfast) {
		this.breakfast = breakfast;
	}
	public ImageView getLunch() {
		return lunch;
	}
	public void setLunch(ImageView lunch) {
		this.lunch = lunch;
	}
	public ImageView getDinner() {
		return dinner;
	}
	public void setDinner(ImageView dinner) {
		this.dinner = dinner;
	}
	public ImageView getSnack() {
		return snack;
	}
	public void setSnack(ImageView snack) {
		this.snack = snack;
	}
	public ImageView getActivityList() {
		return activityList;
	}
	public void setActivityList(ImageView activityList) {
		this.activityList = activityList;
	}
	public boolean isClickBreackfast() {
		return clickBreackfast;
	}
	public void setClickBreackfast(boolean clickBreackfast) {
		this.clickBreackfast = clickBreackfast;
	}
	public boolean isClickLunch() {
		return clickLunch;
	}
	public void setClickLunch(boolean clickLunch) {
		this.clickLunch = clickLunch;
	}
	public boolean isClickDinner() {
		return clickDinner;
	}
	public void setClickDinner(boolean clickDinner) {
		this.clickDinner = clickDinner;
	}
	public boolean isClickSnacks() {
		return clickSnacks;
	}
	public void setClickSnacks(boolean clickSnacks) {
		this.clickSnacks = clickSnacks;
	}
	public boolean isClickActivity() {
		return clickActivity;
	}
	public void setClickActivity(boolean clickActivity) {
		this.clickActivity = clickActivity;
	}
	public String getDateSet() {
		return dateSet;
	}
	public void setDateSet(String dateSet) {
		this.dateSet = dateSet;
	}
	public int getSnackActive() {
		return snackActive;
	}
	public void setSnackActive(int snackActive) {
		this.snackActive = snackActive;
	}
	public int getLunchActive() {
		return lunchActive;
	}
	public void setLunchActive(int lunchActive) {
		this.lunchActive = lunchActive;
	}
	public int getDinnerActive() {
		return dinnerActive;
	}
	public void setDinnerActive(int dinnerActive) {
		this.dinnerActive = dinnerActive;
	}
	public int getBreakfastActive() {
		return breakfastActive;
	}
	public void setBreakfastActive(int breakfastActive) {
		this.breakfastActive = breakfastActive;
	}
	public int getActivityActive() {
		return activityActive;
	}
	public void setActivityActive(int activityActive) {
		this.activityActive = activityActive;
	}
	private int activityActive = 0;

}
