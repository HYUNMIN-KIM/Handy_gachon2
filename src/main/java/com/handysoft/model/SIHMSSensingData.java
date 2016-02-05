package com.handysoft.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@Table(name = "GB_SENSING_DATA")
@JsonPropertyOrder({"logDate"})
@JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.ALWAYS)
public class SIHMSSensingData {
	
	
	@Id
	@Column(name = "ROWID")
	private String rowID;
	
	@Column(name = "REG_USER_SEQ", nullable=false)
	private int seq;

	@Column(name = "YEAR", nullable=false)
	private int year;
	
	@Column(name = "MONTH", nullable=false)
	private int month;
	
	@Column(name = "DAY", nullable=false)
	private int day;
	
	//!! GMT주의
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm:ss", timezone = "GMT+9")
	@Column(name = "LOG_DT", nullable=true)
	private Date logDate;

	@Column(name = "STEPS", nullable=true)
	private int steps;

	@Column(name = "HEART_RATE", nullable=true)
	private int heart_rate;

	@Column(name = "TEMPERATURE", nullable=true)
	private float temperature;


	
	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date log_date) {
		this.logDate = log_date;
	}

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public int getHeart_rate() {
		return heart_rate;
	}

	public void setHeart_rate(int heart_rate) {
		this.heart_rate = heart_rate;
	}

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	public SIHMSSensingData(){
		
	}

	public SIHMSSensingData(String rowID, int seq, int year, int month, int day, Date logDate, int steps,
			int heart_rate, float temperature) {
		super();
		this.rowID = rowID;
		this.seq = seq;
		this.year = year;
		this.month = month;
		this.day = day;
		this.logDate = logDate;
		this.steps = steps;
		this.heart_rate = heart_rate;
		this.temperature = temperature;
	}
}
