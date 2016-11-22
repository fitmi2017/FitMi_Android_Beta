package com.fitmi.dao;

import com.callback.Item;

public class SectionItemFoodlogin implements Item{
	
	private final String title;
	
	private String total = "0";
	  
	 public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public SectionItemFoodlogin(String title) {
	  this.title = title;	 
	 }
	  
	 public String getTitle(){
	  return title;
	 } 

	@Override
	public boolean isSection() {
		// TODO Auto-generated method stub
		return true;
	}

}
