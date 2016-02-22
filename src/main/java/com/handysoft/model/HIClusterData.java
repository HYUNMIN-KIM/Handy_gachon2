package com.handysoft.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@Table(name = "GB_CLUSTER_DATA")
@IdClass(HIClusterDataCompositeKey.class)
@JsonPropertyOrder({ "type" })
@JsonIgnoreProperties({"hi", "year", "month", "day"})
public class HIClusterData {

	@Id
	private int year;
	@Id
	private int month;
	@Id
	private int day;
	@Id
	private int type;
	
	private double ti;
	private double pi;
	private double si;
	private double tvi;
	private double pvi;
	private double ai;
	private int count;

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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getTi() {
		return ti;
	}

	public void setTi(double ti) {
		this.ti = ti;
	}

	public double getPi() {
		return pi;
	}

	public void setPi(double pi) {
		this.pi = pi;
	}

	public double getSi() {
		return si;
	}

	public void setSi(double si) {
		this.si = si;
	}

	public double getTvi() {
		return tvi;
	}

	public void setTvi(double tvi) {
		this.tvi = tvi;
	}

	public double getPvi() {
		return pvi;
	}

	public void setPvi(double pvi) {
		this.pvi = pvi;
	}

	public double getAi() {
		return ai;
	}

	public void setAi(double ai) {
		this.ai = ai;
	}

	public HIClusterData(int year, int month, int day, double ti, double pi, double si, double tvi, double pvi,
			double ai, int count) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
		this.ti = ti;
		this.pi = pi;
		this.si = si;
		this.tvi = tvi;
		this.pvi = pvi;
		this.ai = ai;
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public HIClusterData() {

	}

	
	
	public double getHi() {
		return this.ti + this.pi + this.si + this.tvi + this.pvi + this.ai;

	}

}
