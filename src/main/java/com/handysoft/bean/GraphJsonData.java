package com.handysoft.bean;

import java.util.ArrayList;
import java.util.List;

import com.handysoft.model.SIHMSSensingData;
import com.handysoft.model.UserExtraInfo;
import com.handysoft.util.ConditionFormat;
import com.handysoft.util.FloatFormat;

public class GraphJsonData {
	private String date;
	private int conditionPoint;
	private int caloriePoint;
	private GraphJsonConditionData conditionData;
	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getConditionPoint() {
		return conditionPoint;
	}
	public void setConditionPoint(int conditionPoint) {
		this.conditionPoint = conditionPoint;
	}
	public float getCaloriePoint() {
		return caloriePoint;
	}
	public void setCaloriePoint(int caloriePoint) {
		this.caloriePoint = caloriePoint;
	}
	public GraphJsonConditionData getConditionData() {
		return conditionData;
	}
	public void setConditionData(GraphJsonConditionData conditionData) {
		this.conditionData = conditionData;
	}
	
	
	
	// 그래프에 표기되는 추가적인 정보
	public boolean setOtherInfo(UserExtraInfo userExtraInfo, float avgHeart){
		try{
			//TODO 생일을 이용한 나이 설정
			/*
			Calendar birth = Calendar.getInstance();  
			
			birth.setTime(userExtraBean.getBirthDay());  
			Calendar today = Calendar.getInstance();  
			int age = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR);  
			 */
			int age = 20;
			
			
			SIHMConditionCalc conditionCalc;
			SIHMCalorieCalc calorieCalc;
			
			// 정보 설정
			 calorieCalc = (new SIHMCalorieCalc(userExtraInfo.getGender(), age, userExtraInfo.getHeight(), userExtraInfo.getWeight(),
					(int) avgHeart));
			 conditionCalc = (new SIHMConditionCalc(userExtraInfo
					.getGender(), age, userExtraInfo.getHeight(), userExtraInfo.getWeight(),
					(int) avgHeart));
			
			//계산
			 calorieCalc.calcConsumedCalorie(this.conditionData.getSensingData());
			 conditionCalc.calcPoints(this.conditionData.getSensingData());
			
			 
			//소모 칼로리
			 this.caloriePoint = (int) calorieCalc.consumedCalorie;
			 
			//컨디션점수환산
				if(this.conditionData.getSensingData().size() == 0)
					 this.conditionPoint = 0;
				else
					this.conditionPoint =
							ConditionFormat.format(conditionCalc.getConditionPoint());
			 
			//그외 변동점수들
			this.conditionData.setActivityPoint(conditionCalc.getActivityPoint());
			this.conditionData.setHrChangeDeductPoint(conditionCalc.getHrChangeDeductPoint());
			this.conditionData.setHrPoint(conditionCalc.getHrPoint());
			this.conditionData.setHrRhythmPoint(conditionCalc.getHrRhythmPoint());
			this.conditionData.setSynchroDeductPoint(conditionCalc.getSynchroDeductPoint());
			this.conditionData.setTempChangeDeductPoint(conditionCalc.getTempChangeDeductPoint());
			this.conditionData.setTempPoint(conditionCalc.getTempPoint());
			this.conditionData.setTempRhythmPoint(conditionCalc.getTempRhythmPoint());
			
			
			
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		
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
						firstMinute = minute = originalList.get(j).getLogDate()
								.getMinutes();
						heartTotal = originalList.get(j).getHeart_rate();
						stepTotal = originalList.get(j).getSteps();
						temperatureTotal = originalList.get(j).getTemperature();
						cnt = 1;

					} else if (cnt < 4) {

						// 이전 정보와 시간 차이가 2분을 넘어간경우
						if (minute + 2 > originalList.get(j).getLogDate().getMinutes()) {
							heartTotal += originalList.get(j).getHeart_rate();
							stepTotal += originalList.get(j).getSteps();
							temperatureTotal += originalList.get(j).getTemperature();
							cnt++;
							minute = originalList.get(j).getLogDate().getMinutes();

						} else {
							SIHMSSensingData sData = new SIHMSSensingData();
							sData.setLogDate(originalList.get(j).getLogDate());
							sData.getLogDate().setSeconds(0);
							sData.getLogDate().setMinutes(firstMinute);

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
						sData.setLogDate(originalList.get(j).getLogDate());
						sData.getLogDate().setSeconds(0);
						sData.getLogDate().setMinutes(firstMinute);

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
	
}
