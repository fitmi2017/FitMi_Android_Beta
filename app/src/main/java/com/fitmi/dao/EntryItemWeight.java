package com.fitmi.dao;

import com.callback.Item;

public class EntryItemWeight implements Item{
	
	private String time = "";
	private String weight = "";	
	private String id = "";
	
	public EntryItemWeight(String logTime, String weight,String id) {
		// TODO Auto-generated constructor stub
		
		time = logTime;
		this.weight = weight;		
		this.id = id;
	}
	
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public boolean isSection() {
		// TODO Auto-generated method stub
		return false;
	}
}
