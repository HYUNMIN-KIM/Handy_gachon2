package com.handysoft.model;

import java.io.Serializable;

public class HIClusterDataCompositeKey implements Serializable {

	private int year;
	private int month;
	private int day;
	private int type;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public HIClusterDataCompositeKey(int year, int month, int day, char type) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
		this.type = type;
	}
	
	public HIClusterDataCompositeKey(){
		
	}
}
