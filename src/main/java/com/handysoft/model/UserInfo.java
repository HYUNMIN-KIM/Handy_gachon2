package com.handysoft.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="COMM_USER")
public class UserInfo {
	
	
	@Id
	@Column(name="USER_ID", nullable=false)
	private String user_id;
	
	@Column(name="USER_NM")
	private String user_num;

	@GeneratedValue
	@Column(name="USER_SEQ", nullable=false)
	private int user_seq;


	public String getUser_id() {
		return user_id;
	}


	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}


	public String getUser_num() {
		return user_num;
	}


	public void setUser_num(String user_num) {
		this.user_num = user_num;
	}


	public int getUser_seq() {
		return user_seq;
	}


	public void setUser_seq(int user_seq) {
		this.user_seq = user_seq;
	}


	public UserInfo(String user_id, String user_num, int user_seq) {
		super();
		this.user_id = user_id;
		this.user_num = user_num;
		this.user_seq = user_seq;
	}
	

	public UserInfo(){
		
	}
	
	//TODO 필요한 정보 추가 가능성 있음
	
	
	
}
