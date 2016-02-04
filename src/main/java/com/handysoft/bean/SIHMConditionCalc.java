package com.handysoft.bean;


import java.util.Date;
import java.util.List;

import com.handysoft.model.SIHMSHeartRate;
import com.handysoft.model.SIHMSSensingData;
import com.handysoft.model.SIHMSTemperature;
import com.handysoft.util.SihmsUtils;


public class SIHMConditionCalc {
		
	String gender = null;
	int age = 0;
	int avg_heart_rate = 0;
	int height = 0;
	int weight = 0;
	
	SIHMSTemperature sihmsTemp = null;
	SIHMSHeartRate sihmsHR = null;
	
	float conditionPoint = 0;
	
	float tempPoint = 0;
	float hrPoint = 0;	
	
	float synchroDeductPoint = 0;
	float tempChangeDeductPoint = 0;
	float hrChangeDeductPoint = 0;
	float tempRhythmPoint = 0;
	float hrRhythmPoint = 0;
	float activityPoint = 0;
	int normalDataTotalCnt = 0;	
	
	float tempDeductPoint = 0;
	float hrDeductPoint = 0;		
	int abnormalSynchroCnt = 0;
	int abnormalTempChangeCnt = 0;
	int abnormalHrChangeCnt = 0;
	
	public SIHMConditionCalc(String gender, int age, int height, int weight, int avg_heart_rate) {
		super();
		this.gender = gender;
		this.age = age;
		this.avg_heart_rate = avg_heart_rate;
		this.height = height;
		this.weight = weight;
		
		sihmsTemp = new SIHMSTemperature(age); 
		sihmsHR = new SIHMSHeartRate(age, avg_heart_rate);
	}
	
	
	
	public float calcPoints(List<SIHMSSensingData> raw_data_list){
		SIHMSSensingData currentData = null;
		SIHMSSensingData pastData = null;
		for(int i=0 ; i < raw_data_list.size(); i++){
			currentData = raw_data_list.get(i);			
			
			float current_temp = currentData.getTemperature();
			int current_hr = currentData.getHeart_rate();
			int steps = currentData.getSteps();
			Date current_log_date = currentData.getLog_date();
			if(steps > 75){
				// 운동량이 거의 없는 데이터가 아닌경우 다음으로
				// 걸음 수와 심박수의 관계에 따른 운동량이 거의 없는 상태에 대한 명확한 정의 필요
				// 현재는 75걸음 초과이면 운동으로 판단
				continue;
			}
			
			// 보통 상태의 데이터 카운트
			normalDataTotalCnt++;
			
			//온도 감점
			tempDeductPoint += getTEMPDeductPoint(current_temp);
			
			// 심박 감점
			hrDeductPoint += getHRDeductPoint(current_hr);
			
			if(pastData != null){
				float past_temp = pastData.getTemperature();
				int past_hr = pastData.getHeart_rate();
				Date past_log_date = pastData.getLog_date();				
				if((current_log_date.getTime() - past_log_date.getTime())/1000 < (60*10)){ // 지난 데이터가 10분 이내의 데이터면
					// 싱크로
					if(isSynchroAbnormal(past_temp, current_temp, past_hr, current_hr)){
						abnormalSynchroCnt++;
					}
					// 온도 변동
					if(isTEMPChangeAbnormal(past_temp, current_temp)){
						abnormalTempChangeCnt++;
					}
					// 심박 변동
					if(isHRChangeAbnormal(past_hr, current_hr)){
						abnormalHrChangeCnt++;
					}
				}
			}
			// 마지막에 pastData 세팅
			pastData = currentData;
		}
		
		tempPoint = (normalDataTotalCnt - tempDeductPoint) / normalDataTotalCnt * 100;
		hrPoint = (normalDataTotalCnt - hrDeductPoint) / normalDataTotalCnt * 100;
		
		synchroDeductPoint = (abnormalSynchroCnt * SYNCHRO_DUDUCT_POINT) / normalDataTotalCnt * 100;
		tempChangeDeductPoint = (abnormalTempChangeCnt * CHANGE_DUDUCT_POINT) / normalDataTotalCnt * 100;
		hrChangeDeductPoint =  (abnormalHrChangeCnt * CHANGE_DUDUCT_POINT) / normalDataTotalCnt * 100;
		
		tempRhythmPoint = getTEMPRhythmPoint();
		hrRhythmPoint = getHRRhythmPoint();
		
		activityPoint = getActiviyPoint();
		
		conditionPoint = (tempPoint + hrPoint) / 2
				+ (- synchroDeductPoint)
				+ (- tempChangeDeductPoint)
				+ (- hrChangeDeductPoint)
				+ tempRhythmPoint
				+ hrRhythmPoint
				+ activityPoint;
		return conditionPoint;
	}
	
	public void printPoints(){
		SihmsUtils.printValues(				
				"tempPoint", tempPoint, 
				"hrPoint", hrPoint,
				"tempDeductPoint", tempDeductPoint,
				"hrDeductPoint", hrDeductPoint,
				"abnormalSynchroCnt",abnormalSynchroCnt,
				"abnormalTempChangeCnt",abnormalTempChangeCnt,
				"abnormalHrChangeCnt",abnormalHrChangeCnt,
				"synchroDeductPoint", synchroDeductPoint, 
				"tempChangeDeductPoint", tempChangeDeductPoint,
				"hrChangeDeductPoint", hrChangeDeductPoint,
				"tempRhythmPoint", tempRhythmPoint,
				"hrRhythmPoint", hrRhythmPoint,
				"activityPoint", activityPoint,
				"conditionPoint", conditionPoint
			);
	}
	
	/*
	 * 온도 감점
	 */	
	float getTEMPDeductPoint(float temp){
		float deductPoint = 0;
		if(sihmsTemp.getNormalLow() <= temp && temp <= sihmsTemp.getNormalHigh()){
			;
		}else if(temp < sihmsTemp.getNormalLow()){// 저 체온
			if(sihmsTemp.getLowerLv1() <= temp){
				deductPoint = SIHMSTemperature.WARN_DP_LV1;
			}else if(sihmsTemp.getLowerLv2() <= temp){
				deductPoint = SIHMSTemperature.WARN_DP_LV2;
			}else{
				deductPoint = SIHMSTemperature.WARN_DP_LV3;
			}
		}else if(sihmsTemp.getNormalHigh() < temp){// 고 체온
			if(temp <= sihmsTemp.getHigherLv1()){
				deductPoint = SIHMSTemperature.WARN_DP_LV1;		
			}else if(temp <= sihmsTemp.getHigherLv2()){
				deductPoint = SIHMSTemperature.WARN_DP_LV2;
			}else{
				deductPoint = SIHMSTemperature.WARN_DP_LV3;
			}
		}
		return deductPoint;
	}
	
	/*
	 * 심박 감점
	 */
	int getHRDeductPoint(int heartRate){
		int deductPoint = 0;
		if(sihmsHR.getNormalLow() <= heartRate && heartRate <= sihmsHR.getNormalHigh()){
			;
		}else if(heartRate < sihmsHR.getNormalLow()){// 저 심박
			if(sihmsHR.getLowerLv1() <= heartRate){
				deductPoint = SIHMSHeartRate.WARN_DP_LV1;
			}else if(sihmsHR.getLowerLv2() <= heartRate){
				deductPoint = SIHMSHeartRate.WARN_DP_LV2;
			}else{
				deductPoint = SIHMSHeartRate.WARN_DP_LV3;
			}
		}else if(sihmsHR.getNormalHigh() < heartRate){// 고 심박
			if(heartRate <= sihmsHR.getHigherLv1()){
				deductPoint = SIHMSHeartRate.WARN_DP_LV1;	
			}else if(heartRate <= sihmsHR.getHigherLv2()){
				deductPoint = SIHMSHeartRate.WARN_DP_LV2;
			}else{
				deductPoint = SIHMSHeartRate.WARN_DP_LV3;
			}
		}
		//System.out.println(heartRate+"/"+deductPoint);
		return deductPoint;
	}
	
	/*
	 * 씽크로 점수: 온도와 맥박이 비례 변화인 경우 정상, 반비례 변화인 경우 비정상으로 감점
	 *  - 온도는 최소 0.2도 이상, 맥박은 최소 5회 이상 변화에 대해서만 처리  
	 */
	static final float SYNCHRO_DUDUCT_POINT = 0.5f;
	static final float SYNCHRO_TEMP_THRESHOLD = 0.2f;
	static final int SYNCHRO_HR_THRESHOLD = 5;
	
	boolean isSynchroAbnormal(float past_temp, float current_temp, int past_hr, int current_hr) {
		boolean isAbnormal = false;
		int hrDiff = past_hr - current_hr;
		float tempDiff = past_temp - current_temp;
		if(Math.abs(tempDiff) >= SYNCHRO_TEMP_THRESHOLD && Math.abs(hrDiff) >= SYNCHRO_HR_THRESHOLD){
			float syncPoint = (float)hrDiff/tempDiff;	
			if(syncPoint < 0){
				isAbnormal = true;
			}
		}
		return isAbnormal;
	}
	
	/*
	 * 변동성 점수: 맥박의 변화가 심한경우 * 운동 중인 데이터는 제외해야함(분당 75걸음 이상 = 운동 중)
	 *  Lv1)정상 Lv2)정상-주의 Lv3)주의-경고 Lv4) 경고 초과
	 *  에서 2단계 이상 변하는 경우 감점 
	 */
	public static final float CHANGE_DUDUCT_POINT = 0.5f;
	
	// 변동 - 심박
	public boolean isHRChangeAbnormal(int past, int current){
		boolean isAbnormal = false;
		if(past >= sihmsHR.getNormalLow() && past <= sihmsHR.getNormalHigh()){ // 이전 수치가 정상 범위
			if(current < sihmsHR.getLowerLv1() || current > sihmsHR.getHigherLv1()){ // 현재 수치는 주의 초과
				isAbnormal = true;
				//System.out.println(past+"/"+current);
			}
		}else if(past >= sihmsHR.getLowerLv1() && past <= sihmsHR.getHigherLv1()){ // 이전 수치는 정상~주의
			if(current < sihmsHR.getLowerLv2() || current > sihmsHR.getHigherLv2()){ // 현재 수치는 경고 초과
				isAbnormal = true;
				//System.out.println(past+"/"+current);
			}
		}
		return isAbnormal;
	}
	
	// 변동 - 온도
	public boolean isTEMPChangeAbnormal(float past, float current){
		boolean isAbnormal = false;
		if(past >= sihmsTemp.getNormalLow() && past <= sihmsTemp.getNormalHigh()){ // 이전 수치가 정상 범위
			if(current < sihmsTemp.getLowerLv1() || current > sihmsTemp.getHigherLv1()){ // 현재 수치는 주의 초과
				isAbnormal = true;
				//System.out.println(past+"T/"+current);
			}
		}else if(past >= sihmsTemp.getLowerLv1() && past <= sihmsTemp.getHigherLv1()){ // 이전 수치는 정상~주의
			if(current < sihmsTemp.getLowerLv2() || current > sihmsTemp.getHigherLv2()){ // 현재 수치는 경고 초과
				isAbnormal = true;
				//System.out.println(past+"T/"+current);
			}
		}
		return isAbnormal;
	}
	
	/*
	 *  리듬
	 */
	
	// 리듬 - 심박: (오후 - 오전)*0.5 : (+)값이면 0점 처리
	static final float RHYTHM_HR_CORRECTION = 0.5f; // 값 보정
	float getHRRhythmPoint(){
		float rhythmPoint = 0;
		float avgAM = 0f;
		float avgPM = 0f;
		// TODO: avgAM, avgPM 은,, DB에서 쿼리로?
		if(avgAM >= sihmsHR.getNormalLow() && avgAM <= sihmsHR.getNormalHigh()
				&& avgPM >= sihmsHR.getNormalLow() && avgPM <= sihmsHR.getNormalHigh()){ // 오전, 오후 모두 정상 범위이면
			rhythmPoint = (avgPM - avgAM) * RHYTHM_HR_CORRECTION;
			if(rhythmPoint > 0){
				rhythmPoint = 0;
			}
		}
		return rhythmPoint ;
	}
	
	// 리듬 - 온도: (오후 - 오전)*10 : (+)값이면 0점 처리
	static final float RHYTHM_TEMP_CORRECTION = 10f; // 값 보정
	float getTEMPRhythmPoint(){
		float rhythmPoint = 0;
		float avgAM = 0f;
		float avgPM = 0f;
		// TODO: avgAM, avgPM 은,, DB에서 쿼리로?
		if(avgAM >= sihmsTemp.getNormalLow() && avgAM <= sihmsTemp.getNormalHigh()
				&& avgPM >= sihmsTemp.getNormalLow() && avgPM <= sihmsTemp.getNormalHigh()){ // 오전, 오후 모두 정상 범위이면
			rhythmPoint = (avgPM - avgAM) * RHYTHM_TEMP_CORRECTION;
			if(rhythmPoint > 0){
				rhythmPoint = 0;
			}
		}
		return rhythmPoint ;
	}
	
	/*
	 * 운동 점수: (운동 분 - 40분)/40 * 3, (Minimum: -3, Maximum: 3)
	 */
	float getActiviyPoint(){
		float activityPoint = 0;
		int activityTime = 0;
		// TODO: activityTime 은 DB에서 쿼리로?
		activityPoint = (activityTime - 40)/40 * 3;
		if(activityPoint > 3){
			activityPoint = 3;
		}else if(activityPoint < -3){
			activityPoint = -3;
		}
		return activityPoint ;
	}

	public float getConditionPoint() {
		return conditionPoint;
	}

	public void setConditionPoint(float conditionPoint) {
		this.conditionPoint = conditionPoint;
	}

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

	public int getNormalDataTotalCnt() {
		return normalDataTotalCnt;
	}

	public void setNormalDataTotalCnt(int normalDataTotalCnt) {
		this.normalDataTotalCnt = normalDataTotalCnt;
	}
}
