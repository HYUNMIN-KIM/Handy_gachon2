package com.handysoft.model;
//!! java.sql.date는 사용하면 안 됨
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@Table(name = "GB_SENSING_DATA")
@JsonPropertyOrder({ "logDate" })
@JsonIgnoreProperties({"seq", "year", "month", "day"})
@IdClass(SensingDataCompositeKey.class)
public class SIHMSSensingData {

	@Id
	@Column(name = "REG_USER_SEQ", nullable = false)
	private int seq;

	@Id
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "GMT+9")
	@Column(name = "LOG_DT", nullable = false)
	private Date logDate;

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date log_date) {
		this.logDate = log_date;
	}

	@Column(name = "YEAR", nullable = false)
	private int year;

	@Column(name = "MONTH", nullable = false)
	private int month;

	@Column(name = "DAY", nullable = false)
	private int day;

	@Column(name = "STEPS", nullable = true)
	private int steps;

	@Column(name = "HEART_RATE", nullable = true)
	private int heart_rate;

	@Column(name = "TEMPERATURE", nullable = true)
	private float temperature;

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

	public SIHMSSensingData() {

	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
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

}
