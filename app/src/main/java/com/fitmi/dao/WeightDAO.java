package com.fitmi.dao;

import java.util.ArrayList;

/**
 * Created by dts_user on 26/8/15.
 */
 public class WeightDAO {

    public String header;
    public ArrayList<String> timelist=new ArrayList<>();
    public ArrayList<String> bmilist=new ArrayList<>();
    public ArrayList<String> cloudlist=new ArrayList<>();
    public ArrayList<String> waterlist=new ArrayList<>();
    public ArrayList<String> bodylist=new ArrayList<>();
    public ArrayList<String> bonelist=new ArrayList<>();

    public ArrayList<String> getBmilist() {
        return bmilist;
    }

    public void setBmilist(ArrayList<String> bmilist) {
        this.bmilist = bmilist;
    }

    public ArrayList<String> getCloudlist() {
        return cloudlist;
    }

    public void setCloudlist(ArrayList<String> cloudlist) {
        this.cloudlist = cloudlist;
    }

    public ArrayList<String> getWaterlist() {
        return waterlist;
    }

    public void setWaterlist(ArrayList<String> waterlist) {
        this.waterlist = waterlist;
    }

    public ArrayList<String> getBodylist() {
        return bodylist;
    }

    public void setBodylist(ArrayList<String> bodylist) {
        this.bodylist = bodylist;
    }

    public ArrayList<String> getBonelist() {
        return bonelist;
    }

    public void setBonelist(ArrayList<String> bonelist) {
        this.bonelist = bonelist;
    }

    public WeightDAO()
    {

    }

    public void setHeader(String s)
    {
        header=s;
    }

    public void setlist(ArrayList<String> list1)
    {
        timelist=list1;
       // poundlist=list2;
    }

    public String getHeader()
    {
        return header;
    }

    public ArrayList<String> getlist()
    {
        return timelist;
    }



}
