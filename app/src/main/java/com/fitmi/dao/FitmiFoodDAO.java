package com.fitmi.dao;

public class FitmiFoodDAO {
	
	private String itemTitle = "";
	private String calories = "";
	private String itemDescription = "";
	private String servingSize = "";
	private String livestrongId = "";
	
	
	
	private String itemId = "";
	private String itemName = "";
	private String brandName = "";
	private String nfCalories = "";
	private String nfServingSizeQty = "";	
	private String nfServingSizeUnit = "";
	private String nfServingWeightGrams = "";
	private String nfIngredientStatement = "";
	
	private boolean customButton = false;
	
	
	public boolean isCustomButton() {
		return customButton;
	}
	public void setCustomButton(boolean customButton) {
		this.customButton = customButton;
	}
	public String getItemTitle() {
		return itemTitle;
	}
	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}
	public String getCalories() {
		return calories;
	}
	public void setCalories(String calories) {
		this.calories = calories;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public String getServingSize() {
		return servingSize;
	}
	public void setServingSize(String servingSize) {
		this.servingSize = servingSize;
	}
	public String getLivestrongId() {
		return livestrongId;
	}
	public void setLivestrongId(String livestrongId) {
		this.livestrongId = livestrongId;
	}
	
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getNfCalories() {
		return nfCalories;
	}
	public void setNfCalories(String nfCalories) {
		this.nfCalories = nfCalories;
	}
	public String getNfServingSizeQty() {
		return nfServingSizeQty;
	}
	public void setNfServingSizeQty(String nfServingSizeQty) {
		this.nfServingSizeQty = nfServingSizeQty;
	}
	
	public String getNfServingSizeUnit() {
		return nfServingSizeUnit;
	}
	public void setNfServingSizeUnit(String nfServingSizeUnit) {
		this.nfServingSizeUnit = nfServingSizeUnit;
	}
	public String getNfServingWeightGrams() {
		return nfServingWeightGrams;
	}
	public void setNfServingWeightGrams(String nfServingWeightGrams) {
		this.nfServingWeightGrams = nfServingWeightGrams;
	}
	public String getNfIngredientStatement() {
		return nfIngredientStatement;
	}
	public void setNfIngredientStatement(String nfIngredientStatement) {
		this.nfIngredientStatement = nfIngredientStatement;
	}

}
