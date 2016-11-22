package com.fitmi.dao;

import java.util.ArrayList;

/**
 * Created by dts_user on 28/8/15.
 */
public class PresureDAO {


    public String header;
    public ArrayList<String> syslist=new ArrayList<String>();
    public ArrayList<String> dialist=new ArrayList<>();
    public ArrayList<String> heartlist=new ArrayList<>();
    public ArrayList<String> timelist=new ArrayList<>();
    public PresureDAO()
    {

    }

    public ArrayList<String> getTimelist() {
        return timelist;
    }

    public void setTimelist(ArrayList<String> timelist) {
        this.timelist = timelist;
    }

    public String getHeader() {
        return header;
    }

    public ArrayList<String> getSyslist() {
        return syslist;
    }

    public void setSyslist(ArrayList<String> syslist) {
        this.syslist = syslist;
    }

    public ArrayList<String> getDialist() {
        return dialist;
    }

    public void setDialist(ArrayList<String> dialist) {
        this.dialist = dialist;
    }

    public ArrayList<String> getHeartlist() {
        return heartlist;
    }

    public void setHeartlist(ArrayList<String> heartlist) {
        this.heartlist = heartlist;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
