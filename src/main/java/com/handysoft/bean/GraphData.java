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

	
	
	public boolean setOtherInfo(UserExtraInfo userExtraInfo, float avgHeart){
		try{
			
			Calendar birth = Calendar.getInstance();  
			//TODO birthDay setting
			/*
			birth.setTime(userExtraBean.getBirthDay());  
			Calendar today = Calendar.getInstance();  
			int age = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR);  
			 */
			int age = 20;
			
			// 정보 설정
			setCalorieCalc(new SIHMCalorieCalc(userExtraInfo.getGender(), age, userExtraInfo.getHeight(), userExtraInfo.getWeight(),
					(int) avgHeart));
			setConditionCalc(new SIHMConditionCalc(userExtraInfo
					.getGender(), age, userExtraInfo.getHeight(), userExtraInfo.getWeight(),
					(int) avgHeart));
			
			//계산
			getCalorieCalc().calcConsumedCalorie(getSensingDataList());
			getConditionCalc().calcPoints(getSensingDataList());
			
			
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		
	}
	
	
	public GraphData(){
		
	}
	
	
	// 5분마다의 평균 계산 메소드
		public List<SIHMSSensingData> sensingValueAvg(
				List<SIHMSSensingData> originalList) {

			int cnt = 0;
			int minute = 0, firstMinute = 0, heartTotal = 0, stepTotal = 0;
			float temperatureTotal = 0;

			List<SIHMSSensingData> list = new ArrayList<SIHMSSensingData>();
			for (int j = 0; j < originalList.size(); j++) {

				if (cnt == 0) {
					firstMinute = minute = originalList.get(j).getLog_date()
							.getMinutes();
					heartTotal = originalList.get(j).getHeart_rate();
					stepTotal = originalList.get(j).getSteps();
					temperatureTotal = originalList.get(j).getTemperature();
					cnt = 1;

				} else if (cnt < 4) {

					// 이전 정보와 시간 차이가 2분을 넘어간경우
					if (minute + 2 > originalList.get(j).getLog_date().getMinutes()) {
						heartTotal += originalList.get(j).getHeart_rate();
						stepTotal += originalList.get(j).getSteps();
						temperatureTotal += originalList.get(j).getTemperature();
						cnt++;
						minute = originalList.get(j).getLog_date().getMinutes();

					} else {
						SIHMSSensingData sData = new SIHMSSensingData();
						sData.setLog_date(originalList.get(j).getLog_date());
						sData.getLog_date().setSeconds(0);
						sData.getLog_date().setMinutes(firstMinute);

						if (heartTotal != 0)
							sData.setHeart_rate(heartTotal / cnt);
						if (stepTotal != 0)
							sData.setSteps(stepTotal / cnt);
						if (temperatureTotal != 0)
							sData.setTemperature(temperatureTotal / cnt);

						list.add(sData);
						cnt = 0;
					}

				} else {
					SIHMSSensingData sData = new SIHMSSensingData();
					sData.setLog_date(originalList.get(j).getLog_date());
					sData.getLog_date().setSeconds(0);
					sData.getLog_date().setMinutes(firstMinute);

					if (heartTotal != 0)
						sData.setHeart_rate(heartTotal / cnt);
					if (stepTotal != 0)
						sData.setSteps(stepTotal / cnt);
					if (temperatureTotal != 0)
						sData.setTemperature(Float.parseFloat(FloatFormat
								.format(temperatureTotal / cnt)));

					list.add(sData);
					cnt = 0;
				}
			}
			return list;
		}
	public GraphData(String date, List<SIHMSSensingData> sensingDataList) {
		super();
		this.date = date;
		this.sensingDataList = sensingDataList;
	}
	
}
