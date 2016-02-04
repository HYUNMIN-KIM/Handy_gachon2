package com.handysoft.bean;


import java.util.List;

import com.handysoft.model.SIHMSHeartRate;
import com.handysoft.model.SIHMSSensingData;
import com.handysoft.model.SIHMSTemperature;


public class SIHMCalorieCalc {
		
	String gender = null;
	int age = 0;
	int avg_heart_rate = 0;
	int height = 0;
	int weight = 0;
	float consumedCalorie = 0;
	SIHMSTemperature sihmsTemp = null;
	SIHMSHeartRate sihmsHR = null;
		
	public SIHMCalorieCalc(String gender, int age, int avg_heart_rate, int height, int weight) {
		super();
		this.gender = gender;
		this.age = age;
		this.avg_heart_rate = avg_heart_rate;
		this.height = height;
		this.weight = weight;
		
		sihmsTemp = new SIHMSTemperature(age); 
		sihmsHR = new SIHMSHeartRate(age, avg_heart_rate);
	}
	
	
	
	public float calcConsumedCalorie(List<SIHMSSensingData> raw_data_list){
		SIHMSSensingData currentData = null;
		//FIXME consumedCalorie 전역 변수로 설정
		for(int i=0 ; i < raw_data_list.size(); i++){
			currentData = raw_data_list.get(i);			
			
			int current_hr = currentData.getHeart_rate();
			int steps = currentData.getSteps();
			
			float mets = getMETS(steps, current_hr);
			float kcal = mets * weight / 60; // 분당 칼로리 소모량
			consumedCalorie += kcal;
		}
		return consumedCalorie;
	}
	
	float getMETS(int stpesPerMinute, int heartRate){
		float mets = 0;
		float stepLength = 0;
		if(gender.equals("M")){ //남자
			stepLength = (float)height * 0.415f;
		}else{
			stepLength = (float)height * 0.413f;
		}
		//FIXME 심박 수에 따른 stepLength 변화
		if(avg_heart_rate>=heartRate*2)
				stepLength = (float) (stepLength*2.5);
		else if(avg_heart_rate>=heartRate*1.8)
			stepLength = (float) (stepLength*2.1);
		else if(avg_heart_rate>=heartRate*1.5)
			stepLength = (float) (stepLength*1.7);
		else if(avg_heart_rate>=heartRate*1.3)
			stepLength = (float) (stepLength*1.4);
		
		/*
		 * TODO: stepLength는 심박 수(heartRate)에 따른 보정이 필요할 수도 있음, 
		 *       예를 들어 심박 수가 정상 심박보다 높으면,		 
		 *       30% 이상 높은 경우 stepLength = stepLength * 1.4
		 *       50% 이상 높은 경우 stepLength = stepLength * 1.7
		 *       80% 이상 높은 경우 stepLength = stepLength * 2.1
		 *       100% 이상 높은 경우 stepLength = stepLength * 2.5
		 *       
		 *       왜 이런 보정이 필요한가,
		 *       조깅의 경우 보통 스텝 길이가 1 ~ 1.5m
		 *       전력 질주의 경우 스텝 길이가 1.5 ~ 2m 까지 되기 때문에...
		 */
		
		
		float distance = stpesPerMinute * stepLength / 100; //미터 환산을 위해 100으로 나눔
		// mets: 운동계수, distance: 분당 이동 거리 
		if(distance < 11.7){ 
			mets = 1.3f;
		}else if(distance < 25){
			mets = 1.5f;
		}else if(distance < 53.4){
			mets = 2.0f;
		}else if(distance < 66.7){
			mets = 2.5f;
		}else if(distance < 83.4){
			mets = 2.8f;
		}else if(distance < 106.7){
			mets = 3.5f;
		}else if(distance < 121.7){
			mets = 5.0f;
		}else if(distance < 133.4){
			mets = 6.0f;
		}else if(distance < 160){
			mets = 8.0f;
		}else if(distance < 186.7){
			mets = 10.0f;
		}else if(distance < 266.7){
			mets = 13.5f;
		}else{ // 그 이상
			mets = 16.0f;
		}		
		return mets;
	}
	
	public float getcalcConsumedCalorie()
	{
		return consumedCalorie;
	}
	

}
