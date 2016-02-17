package com.handysoft.kmeans;

import java.util.List;

import com.handysoft.bean.SIHMConditionCalc;
import com.handysoft.model.SIHMSSensingData;
import com.handysoft.model.UserExtraInfo;
import com.handysoft.model.UserInfo;

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
		int avgHeart = 70;
		
		SIHMConditionCalc conditionCalc = new SIHMConditionCalc(
				userExtraInfo.getGender(), 
				age, 
				userExtraInfo.getHeight(), 
				userExtraInfo.getWeight(), 
				avgHeart);
		
		conditionCalc.calcPoints(sensingDataList);

		
		
		
	//점수 추가
		Instance instance = new SparseInstance();
		//TODO conditionCalc로부터 각 점수를 얻어와서 그걸 인덱스로 바꾼 후
		// instance안에 입력해야함 instance는 map을 extends 받은거라 그냥 add하면됨		
		// instance.add(0); 
		
		
		return instance;
			
	}

}
