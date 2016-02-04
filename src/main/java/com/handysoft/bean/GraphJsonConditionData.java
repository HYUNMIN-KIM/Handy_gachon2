package com.handysoft.bean;

import java.util.List;

import com.handysoft.model.SIHMSSensingData;

public class GraphJsonConditionData {

	private float tempPoint;
	private float hrPoint;
	private float tempDeductPoint;
	private float hrDeductPoint;
	private float synchroDeductPoint;
	private float tempChangeDeductPoint;
	private float hrChangeDeductPoint;
	private float tempRhythmPoint;
	private float hrRhythmPoint;
	private float activityPoint;
	
	List<SIHMSSensingData> sensingData;

	public float getTempPoint() {
		return tempPoint;
	}

	public void setTempPoint(float tempPoint) {
		this.tempPoint = tempPoint;
	}

	public float getHrPoint() {
		return hrPoint;
	}

	public void setHrPoint(float hrPoint) {
		this.hrPoint = hrPoint;
	}

	public float getTempDeductPoint() {
		return tempDeductPoint;
	}

	public void setTempDeductPoint(float tempDeductPoint) {
		this.tempDeductPoint = tempDeductPoint;
	}

	public float getHrDeductPoint() {
		return hrDeductPoint;
	}

	public void setHrDeductPoint(float hrDeductPoint) {
		this.hrDeductPoint = hrDeductPoint;
	}

	public float getSynchroDeductPoint() {
		return synchroDeductPoint;
	}

	public void setSynchroDeductPoint(float synchroDeductPoint) {
		this.synchroDeductPoint = synchroDeductPoint;
	}

	public float getTempChangeDeductPoint() {
		return tempChangeDeductPoint;
	}

	public void setTempChangeDeductPoint(float tempChangeDeductPoint) {
		this.tempChangeDeductPoint = tempChangeDeductPoint;
	}

	public float getHrChangeDeductPoint() {
		return hrChangeDeductPoint;
	}

	public void setHrChangeDeductPoint(float hrChangeDeductPoint) {
		this.hrChangeDeductPoint = hrChangeDeductPoint;
	}

	public float getTempRhythmPoint() {
		return tempRhythmPoint;
	}

	public void setTempRhythmPoint(float tempRhythmPoint) {
		this.tempRhythmPoint = tempRhythmPoint;
	}

	public float getHrRhythmPoint() {
		return hrRhythmPoint;
	}

	public void setHrRhythmPoint(float hrRhythmPoint) {
		this.hrRhythmPoint = hrRhythmPoint;
	}

	public float getActivityPoint() {
		return activityPoint;
	}

	public void setActivityPoint(float activityPoint) {
		this.activityPoint = activityPoint;
	}

	public List<SIHMSSensingData> getSensingData() {
		return sensingData;
	}

	public void setSensingData(List<SIHMSSensingData> sensingData) {
		this.sensingData = sensingData;
	}
	
}
