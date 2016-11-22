package com.fitmi.dao;

import com.callback.Item;

public class EntryItem implements Item{
	
	
	private String time = "";
	private String sys = "";
	private String dia = "";
	private String heartBeat = "";
	private String id = "";

	public EntryItem(String logTime, String sys2, String dia2, String heartBeat,String id) {
		// TODO Auto-generated constructor stub
		
		time = logTime;
		sys = sys2;
		dia = dia2;
		this.heartBeat = heartBeat;
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSys() {
		return sys;
	}

	public void setSys(String sys) {
		this.sys = sys;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getHeartBeat() {
		return heartBeat;
	}

	public void setHeartBeat(String heartBeat) {
		this.heartBeat = heartBeat;
	}

	@Override
	public boolean isSection() {
		// TODO Auto-generated method stub
		return false;
	}

}
