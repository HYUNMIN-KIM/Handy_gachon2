package com.handysoft.kmeans;


import java.util.List;
import com.handysoft.bean.SIHMConditionCalc;
import com.handysoft.model.SIHMSSensingData;
import com.handysoft.model.UserExtraInfo;
import com.handysoft.util.ActivityIndexFormat;
import com.handysoft.util.HrTempIndexFormat;
import com.handysoft.util.SyncChangeIndexFormat;

import net.sf.javaml.core.Instance;
import net.sf.javaml.core.SparseInstance;

public class HISensingDataAddInstance {

	public static Instance run(
			UserExtraInfo userExtraInfo
			, List<SIHMSSensingData> sensingDataList){

		
		int age = 20;
		/*
		//TODO 생일을 이용한 나이 설정
		if(userExtraInfo.getBirthDay() != null && userExtraInfo.getBirthDay().trim() != "" ){
			Calendar birth = Calendar.getInstance();  
			String[] uxBirth = userExtraInfo.getBirthDay().trim().split("-");
			birth.set(Calendar.YEAR, Integer.parseInt(uxBirth[0]));
			Calendar today = Calendar.getInstance();  
			age = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR);  
		}
		*/
		
		
		//FIXME 평균심박계산 
		float sumHeart = 0;
		int avgHeart;
		
		for(int i=0;i<sensingDataList.size();i++)
		{
			if(sensingDataList.get(i).getSteps() <= 75)
				sumHeart = sumHeart + sensingDataList.get(i).getHeart_rate();
		}
		
		if(sensingDataList.size() != 0)
			avgHeart = (int)sumHeart/sensingDataList.size();
		else
			avgHeart = 70;
		
		
		
		

		SIHMConditionCalc conditionCalc = new SIHMConditionCalc(
				userExtraInfo.getGender(), 
				age, 
				userExtraInfo.getHeight(), 
				userExtraInfo.getWeight(), 
				avgHeart);

		conditionCalc.calcPoints(sensingDataList);


		//점수 추가
		Instance instance = new SparseInstance();
		instance.put(0, (double) HrTempIndexFormat.format(conditionCalc.getTempPoint()));
		instance.put(1, (double) HrTempIndexFormat.format(conditionCalc.getHrPoint()));
		instance.put(2, (double) SyncChangeIndexFormat.format(conditionCalc.getSynchroDeductPoint()));
		instance.put(3, (double) SyncChangeIndexFormat.format(conditionCalc.getHrChangeDeductPoint()));
		instance.put(4, (double) SyncChangeIndexFormat.format(conditionCalc.getTempChangeDeductPoint()));
		instance.put(5, (double) ActivityIndexFormat.format(conditionCalc.getActivityPoint()));

		return instance;

	}

}
