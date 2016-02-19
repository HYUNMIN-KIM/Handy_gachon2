package com.handysoft.bean;

import java.util.List;

import com.handysoft.model.HIClusterData;

public class GraphJsonHIData {
	private String date;
	private char type;
	private int hi;
	private int ti;
	private int pi;
	private int si;
	private int tvi;
	private int pvi;
	private int ai;
	private List<HIClusterData> clusterData;
	
	
	
	public char getType() {
		return type;
	}
	public void setType(char type) {
		this.type = type;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getHi() {
		return hi;
	}
	public void setHi(int hi) {
		this.hi = hi;
	}
	public int getTi() {
		return ti;
	}
	public void setTi(int ti) {
		this.ti = ti;
	}
	public int getSi() {
		return si;
	}
	public void setSi(int si) {
		this.si = si;
	}
	public int getTvi() {
		return tvi;
	}
	public void setTvi(int tvi) {
		this.tvi = tvi;
	}
	public int getPvi() {
		return pvi;
	}
	public void setPvi(int pvi) {
		this.pvi = pvi;
	}
	public int getAi() {
		return ai;
	}
	public void setAi(int ai) {
		this.ai = ai;
	}
	public List<HIClusterData> getClusterData() {
		return clusterData;
	}
	public void setClusterData(List<HIClusterData> clusterData) {
		this.clusterData = clusterData;
	}
	public int getPi() {
		return pi;
	}
	public void setPi(int pi) {
		this.pi = pi;
	}
	

}
