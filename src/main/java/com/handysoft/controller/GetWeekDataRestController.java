package com.handysoft.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.handysoft.bean.GraphJsonConditionData;
import com.handysoft.bean.GraphJsonData;
import com.handysoft.model.SIHMSSensingData;
import com.handysoft.model.UserExtraInfo;
import com.handysoft.model.UserInfo;
import com.handysoft.service.SensingDataService;
import com.handysoft.service.UserDataService;
import com.handysoft.util.FloatFormat;
import com.handysoft.util.SetCalendar;

@RestController
public class GetWeekDataRestController {

	@Autowired
	SensingDataService sensingDataService;
	
	@Autowired
	UserDataService userDataService;
	
	
	private float avgHeart;
	private int count;
	private List<GraphJsonData> data;
	private Calendar c;
	
	@RequestMapping("/GetWeekData")
	public @ResponseBody List<GraphJsonData> getWeekDataRest(@RequestParam(value = "userid", required = true) String userid,
			@RequestParam(value = "startDate", required = false) String startDate, Model model) {
		
		data = new ArrayList<GraphJsonData>();
		c = Calendar.getInstance();
		SetCalendar.set(startDate, c);
		avgHeart = 0;
		count = 0;
		
		UserInfo userInfo = userDataService.findUserBeanByID(userid);
		UserExtraInfo userExtraInfo = userDataService.findUserExtraBeanBySeq(userInfo.getUser_seq());
		
		// 7일간의 데이터
		for(int i=0; i<7; i++){
			
			int year = c.get(Calendar.YEAR); 
			int month = c.get(Calendar.MONTH) + 1;
			int day = c.get(Calendar.DAY_OF_MONTH);
			int userSeq = userDataService.findUserBeanByID(userid).getUser_seq();
			
			
			// 년/월/일로 구성된 날짜만을 표기하는 문자열
			String simpleDate = year + "/";
			if (month < 10)
				simpleDate += 0;
			simpleDate += month + "/" + day;
			
			
			//FIXME amchart의 데이터 프로바이더 부분 수정이 불가능해 class를 추가로 사용함
			GraphJsonData graphJsonData = new GraphJsonData();
			GraphJsonConditionData graphJsonConditionData = new GraphJsonConditionData();
			graphJsonData.setConditionData(graphJsonConditionData);

			graphJsonData.setDate(simpleDate);
			graphJsonConditionData.setSensingData(
					sensingDataService.findSensorListBySeqAndYearAndMonthAndDay(userSeq, year, month, day));
			
			data.add(graphJsonData);

			
			
			// 평균 심박수 계산을 위한 작업
			for (int j = 0; j < graphJsonConditionData.getSensingData().size(); j++) {
				
				if (graphJsonConditionData.getSensingData().get(j).getSteps() <= 75) {
					avgHeart += graphJsonConditionData.getSensingData().get(j).getHeart_rate();
					count++;
				}
				
			}
			
			// calendar++
			c.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		
		avgHeart = (float)avgHeart/count;

		
		for(GraphJsonData graphData : data){
			
			//평균심박수로 구해야 하는 정보
			graphData.setOtherInfo(userExtraInfo, avgHeart);

			
			//5분마다의 평균 처리
			List<SIHMSSensingData> list 
				= graphData.sensingValueAvg(graphData.getConditionData().getSensingData());
			graphData.getConditionData().getSensingData().clear();
			graphData.getConditionData().getSensingData().addAll(list);
			
		}
		

		
		return data;
	}

}
