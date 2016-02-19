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

		//TODO 나이 평균심박수계산
		// 나이의 경우 이전 작성한 컨트롤러에 주석처리되어있는데
		// 그걸 util클래스로 작성해서 사용하면 됨  (임시데이터는 생일이 없어서 예외처리 해야함)
		// 평균심박수도 이전에 했던것처럼 예전 정보 일부분 가져와서 계산해야 함
		int age = 20;
		float sumHeart = 0;
		int avgHeart;
		
		//FIXME 평균심박계산 
		for(int i=0;i<sensingDataList.size();i++)
		{
			if(sensingDataList.get(i).getSteps()<75)
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
