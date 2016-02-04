package com.handysoft.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.handysoft.model.SIHMSSensingData;
import com.handysoft.model.UserInfo;
import com.handysoft.model.UserExtraInfo;
import com.handysoft.service.SensingDataService;
import com.handysoft.service.UserDataService;
import com.handysoft.util.FloatFormat;
import com.handysoft.util.SetCalendar;

public class GraphData {

	private String date;
	private SIHMConditionCalc conditionCalc;
	private SIHMCalorieCalc calorieCalc;
	private List<SIHMSSensingData> sensingDataList;

	
	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public SIHMConditionCalc getConditionCalc() {
		return conditionCalc;
	}
	public void setConditionCalc(SIHMConditionCalc conditionCalc) {
		this.conditionCalc = conditionCalc;
	}
	public SIHMCalorieCalc getCalorieCalc() {
		return calorieCalc;
	}
	public void setCalorieCalc(SIHMCalorieCalc calorieCalc) {
		this.calorieCalc = calorieCalc;
	}


	public List<SIHMSSensingData> getSensingDataList() {
		return sensingDataList;
	}



	public void setSensingDataList(List<SIHMSSensingData> sensingDataList) {
		this.sensingDataList = sensingDataList;
	}

	
	
	
	
	
	public GraphData(){
		
	}
	
	
	
	public GraphData(String date, List<SIHMSSensingData> sensingDataList) {
		super();
		this.date = date;
		this.sensingDataList = sensingDataList;
	}
	
}
