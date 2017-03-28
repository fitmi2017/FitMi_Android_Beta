package com.fitmi.dao;

import com.callback.Item;

public class SectionItemFoodlogin implements Item {

    private final String title;
    private String unit="cal";

    private String total = "0";

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }

    public SectionItemFoodlogin(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
    public String getUnit() {
        return unit;
    }

    @Override
    public boolean isSection() {
        // TODO Auto-generated method stub
        return true;
    }

}
