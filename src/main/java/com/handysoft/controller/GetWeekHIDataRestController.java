package com.handysoft.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.handysoft.bean.GraphJsonHIData;
import com.handysoft.kmeans.HISensingDataAddInstance;
import com.handysoft.model.HIClusterData;
import com.handysoft.model.SIHMSSensingData;
import com.handysoft.model.UserExtraInfo;
import com.handysoft.model.UserInfo;
import com.handysoft.service.ClusterValueService;
import com.handysoft.service.SensingDataService;
import com.handysoft.service.UserDataService;
import com.handysoft.util.SetCalendar;

import net.sf.javaml.core.Instance;
import net.sf.javaml.core.SparseInstance;
import net.sf.javaml.distance.DistanceMeasure;
import net.sf.javaml.distance.EuclideanDistance;

@Controller
public class GetWeekHIDataRestController {
	
	@Autowired
	ClusterValueService clusterValueService;
	
	@Autowired
	UserDataService userDataService;
	
	@Autowired
	SensingDataService sensingDataService;
	
	
	List<GraphJsonHIData> graphJsonHIDatas = new ArrayList<GraphJsonHIData>();
	private Instance userData;
	private Calendar c;

	@RequestMapping("/GetWeekHIData")
	public @ResponseBody List<GraphJsonHIData> getWeekDataRest(@RequestParam(value = "userid", required = true) String userid,
			@RequestParam(value = "startDate", required = false) String startDate, Model model) {
		
		c = Calendar.getInstance();
		SetCalendar.set(startDate, c);
		UserInfo userInfo = userDataService.findUserBeanByID(userid);
		UserExtraInfo userExtraInfo = userDataService.findUserExtraBeanBySeq(userInfo.getUser_seq());
		
		for(int i=0;i<7;i++)
		{
			
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH)+1;
			int day = c.get(Calendar.DAY_OF_MONTH);
			int userSeq = userDataService.findUserBeanByID(userid).getUser_seq();
			
			String simpleDate = year + "/";
			if (month < 10)
				simpleDate += 0;
			simpleDate += month + "/" + day;
			
			
			
			
		
			//해당 유저의 센싱 데이터를 가져와서 해당 유저의 HI값을 data에 저장하기
			
			List<SIHMSSensingData> sensingDataList 
				= sensingDataService.findSensorListBySeqAndYearAndMonthAndDay(userSeq, year, month, day);
			
			userData = HISensingDataAddInstance.run(userExtraInfo, sensingDataList);
			
			
		//DB에서 cluster들을 담아옴.
			List<HIClusterData> hiClusterData =	clusterValueService.findByYearAndMonthAndDayOrderByType(year, month, day);
			if(hiClusterData.size() == 0) continue;
			
			
			Instance instance = new SparseInstance();
			instance.put(0, hiClusterData.get(0).getTi());
			instance.put(1, hiClusterData.get(0).getPi());
			instance.put(2, hiClusterData.get(0).getSi());
			instance.put(3, hiClusterData.get(0).getTvi());
			instance.put(4, hiClusterData.get(0).getPvi());
			instance.put(5, hiClusterData.get(0).getAi());
			DistanceMeasure distanceMeasure = new EuclideanDistance();
			
			double distance = distanceMeasure.measure(instance, userData);
			int key = 0;
			
			for(int j=1; j<hiClusterData.size(); j++){
				
				Instance ins = new SparseInstance();
				ins.put(0, hiClusterData.get(j).getTi());
				ins.put(1, hiClusterData.get(j).getPi());
				ins.put(2, hiClusterData.get(j).getSi());
				ins.put(3, hiClusterData.get(j).getTvi());
				ins.put(4, hiClusterData.get(j).getPvi());
				ins.put(5, hiClusterData.get(j).getAi());
				
				double newDistance = distanceMeasure.measure(ins, userData);
				
				if(newDistance < distance){
					distance = newDistance;
					key = j;
				}
			}
			
			GraphJsonHIData graphJsonData = new GraphJsonHIData();
			graphJsonData.setTi(userData.get(0).intValue());
			graphJsonData.setPi(userData.get(1).intValue());
			graphJsonData.setSi(userData.get(2).intValue());
			graphJsonData.setTvi(userData.get(3).intValue());
			graphJsonData.setPvi(userData.get(4).intValue());
			graphJsonData.setAi(userData.get(5).intValue());
			graphJsonData.setDate(simpleDate);
			graphJsonData.setType(hiClusterData.get(key).getType());
			graphJsonData.setClusterData(hiClusterData);
			
			graphJsonHIDatas.add(graphJsonData);
			c.add(Calendar.DAY_OF_MONTH, 1);
		}	
	
		
		
		
		return graphJsonHIDatas;
	
	}

}
