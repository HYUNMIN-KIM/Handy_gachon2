package com.handysoft.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="GB_USER_EXTRA_INFO")
public class UserExtraInfo {

	@Id
	@Column(name="REG_USER_SEQ", nullable=false)
	private int seq;
	
	@Column(name="GENDER")
	private String gender;
	
	@Column(name="BIRTHDAY")
	private String birthDay;
	
	@Column(name="HEIGHT")
	private int height;
	
	@Column(name="WEIGHT")
	private int weight;

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public UserExtraInfo(int seq, String gender, String birthDay, int height, int weight) {
		super();
		this.seq = seq;
		this.gender = gender;
		this.birthDay = birthDay;
		this.height = height;
		this.weight = weight;
	}
	
	public UserExtraInfo(){
		
	}
	
	
	
	
}
