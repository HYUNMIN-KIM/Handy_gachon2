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
	
	
	//센싱데이터가 연속적인 경우 5분단위로 평균내는것
	public List<SIHMSSensingData> sensingValueAvg(List<SIHMSSensingData> originList){
		int cnt =0;
		int hour = 0, minute = 0, firstHour = 0, firstMinute = 0, heartTotal = 0, stepTotal = 0;
		float temperatureTotal = 0;
		
		List<SIHMSSensingData> newList = new ArrayList<SIHMSSensingData>();
		
		for(int j=0; j<originList.size(); j++){
			
			hour = originList.get(j).getLogDate().getHours();
			minute = originList.get(j).getLogDate().getMinutes();
			
			if(cnt == 0){
				firstHour = originList.get(j).getLogDate().getHours();
				firstMinute = originList.get(j).getLogDate().getMinutes();
				
				heartTotal = originList.get(j).getHeart_rate();
				temperatureTotal = originList.get(j).getTemperature();
				stepTotal = originList.get(j).getSteps();
				
				cnt++;
				
			}else{
				if(cnt == 4 || 
						(j+1 < originList.size() 
								&& (hour*60 + minute) + 3 <  originList.get(j+1).getLogDate().getHours()*60 
								+  originList.get(j).getLogDate().getMinutes() )  ){
					SIHMSSensingData sData = new SIHMSSensingData();
					sData.setLogDate(originList.get(j).getLogDate());
					sData.getLogDate().setSeconds(0);
					sData.getLogDate()
					.setMinutes(originList.get(j).getLogDate().getMinutes());

					if (heartTotal != 0)
						sData.setHeart_rate(heartTotal / cnt);
					if (stepTotal != 0)
						sData.setSteps(stepTotal / cnt);
					if (temperatureTotal != 0)
						sData.setTemperature(Float.parseFloat(FloatFormat.format(temperatureTotal / cnt)));

					newList.add(sData);
					cnt = 0;
					
					
				}else{
					stepTotal += originList.get(j).getSteps();
					temperatureTotal += originList.get(j).getTemperature();
					heartTotal += originList.get(j).getHeart_rate();
					cnt++;
				}
			}
		}
		return newList;
	}
	
}
