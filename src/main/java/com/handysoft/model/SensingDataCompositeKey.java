package com.handysoft.model;

import java.io.Serializable;
import java.util.Date;

public class SensingDataCompositeKey implements Serializable {
	private int seq;
	private Date logDate;

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}
	
	public SensingDataCompositeKey(){
		
	}

	public SensingDataCompositeKey(int seq, Date logDate) {
		super();
		this.seq = seq;
		this.logDate = logDate;
	}

	
	
}
