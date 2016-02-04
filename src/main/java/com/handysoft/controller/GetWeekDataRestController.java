package com.handysoft.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.handysoft.bean.GraphData;
import com.handysoft.model.UserInfo;
import com.handysoft.repository.UserExtraBeanRepository;
import com.handysoft.model.SIHMSSensingData;
import com.handysoft.model.UserExtraInfo;
import com.handysoft.service.SensingDataService;
import com.handysoft.service.UserDataService;
import com.handysoft.util.SetCalendar;

@RestController
public class GetWeekDataRestController {

	@Autowired
	SensingDataService sS;
	
	@Autowired
	UserDataService uS;
	
	@Autowired
	UserExtraBeanRepository uxR;
	
	private float avgHeart;
	private int count;
	private List<GraphData> data;
	private Calendar c;
	
	@RequestMapping("/GetWeekData")
	public @ResponseBody List<GraphData> getWeekDataRest(@RequestParam(value = "userid", required = true) String userid,
			@RequestParam(value = "startDate", required = false) String startDate, Model model) {
		
		data = new ArrayList<GraphData>();
		c = Calendar.getInstance();
		SetCalendar.set(startDate, c);
		avgHeart = 0;
		count = 0;
		
		UserInfo userInfo = uS.findUserBeanByID(userid);
		UserExtraInfo userExtraInfo = uS.findUserExtraBeanBySeq(userInfo.getUser_seq());
		
		//XXX 7일간의 데이터
		for(int i=0; i<7; i++){
			
			int year = c.get(Calendar.YEAR); 
			int month = c.get(Calendar.MONTH) + 1;
			int day = c.get(Calendar.DAY_OF_MONTH);
			int userSeq = uS.findUserBeanByID(userid).getUser_seq();
			
			
			// 월 설정 - MM형식
			String simpleDate = year + "/";
			if (month < 10)
				simpleDate += 0;
			simpleDate += month + "/" + day;
			
			GraphData graphData 
			= new GraphData(simpleDate, sS.findSensorList(userSeq, year, month, day));
			
			
			data.add(graphData);
			

			// 평균 심박수 계산을 위한 작업
			for (int j = 0; j < data.get(i).getSensingDataList().size(); j++) {
				if (data.get(i).getSensingDataList().get(j).getSteps() <= 75) {
					avgHeart += data.get(i).getSensingDataList().get(j).getHeart_rate();
					count++;
				}
			}
			
			// calendar++
			c.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		
		avgHeart = (float)avgHeart/count;
		
		
		for(GraphData userGraphData : data){
			userGraphData.setOtherInfo(userExtraInfo, avgHeart);
			List<SIHMSSensingData> list 
				= userGraphData.sensingValueAvg(userGraphData.getSensingDataList());
			
			userGraphData.getSensingDataList().clear();
			userGraphData.getSensingDataList().addAll(list);
			
		}
		
		
		
		
		//model.addAttribute("data", data);

		
		return data;
	}

}
