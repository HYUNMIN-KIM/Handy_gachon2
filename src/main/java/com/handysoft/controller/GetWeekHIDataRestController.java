package com.handysoft.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.handysoft.bean.GraphJsonClusterData;
import com.handysoft.bean.GraphJsonData;
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

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.core.SparseInstance;
import net.sf.javaml.distance.DistanceMeasure;
import net.sf.javaml.distance.EuclideanDistance;

import java.io.Serializable;
import java.lang.reflect.Array;

@Controller
public class GetWeekHIDataRestController {
	
	@Autowired
	ClusterValueService clusterValueService;
	
	@Autowired
	UserDataService userDataService;
	
	@Autowired
	SensingDataService sensingDataService;
	
	private List<GraphJsonHIData> returnData;
	private Instance data = new SparseInstance();
	private Instance cluster = new SparseInstance();
	private Calendar c;

	@RequestMapping("/GetWeeHIData")
	public @ResponseBody List<GraphJsonHIData> getWeekDataRest(@RequestParam(value = "userid", required = true) String userid,
			@RequestParam(value = "startDate", required = false) String startDate, Model model) {
		
		returnData = new ArrayList<GraphJsonHIData>();
		c = Calendar.getInstance();
		SetCalendar.set(startDate, c);
		UserInfo userInfo = userDataService.findUserBeanByID(userid);
		UserExtraInfo userExtraInfo = userDataService.findUserExtraBeanBySeq(userInfo.getUser_seq());
		
		for(int i=0;i<7;i++)
		{
			//일주일 간의 날짜 가져오
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH)+1;
			int day = c.get(Calendar.DAY_OF_MONTH);
			int userSeq = userDataService.findUserBeanByID(userid).getUser_seq();
			
			String simpleDate = year + "/";
			if (month < 10)
				simpleDate += 0;
			simpleDate += month + "/" + day;
			
			GraphJsonHIData graphJsonHIData = new GraphJsonHIData();
			GraphJsonClusterData graphJsonClusterData = new GraphJsonClusterData();
			graphJsonHIData.setClusterData(graphJsonClusterData);
			graphJsonHIData.setDate(simpleDate);
			
			
		
			//해당 유저의 센싱 데이터를 가져와서 해당 유저의 HI값을 data에 저장하기
			
			List<SIHMSSensingData> sensingDataList 
				= sensingDataService.findSensorListBySeqAndYearAndMonthAndDay(userSeq, year, month, day);
			data.add(HISensingDataAddInstance.run(userExtraInfo, sensingDataList));
			
			
		//DB에서 cluster들을 담아옴.
			List<HIClusterData> hiClusterData =	clusterValueService.findByYearAndMonthAndDayOrderByType(year, month, day);
			
			Instance cluster =  new SparseInstance();
			double [] array = new double[hiClusterData.size()];
			int index =0;
			DistanceMeasure distance = new EuclideanDistance();
			//cluster들을 각 instance로 바꿔서 
			//유클리드디스턴스를 이용해서 list로 가장 가까운 순서대로 sorting
			for(int j=0;j<hiClusterData.size();j++)
			{
				cluster.add((Instance) hiClusterData.get(i));
				data = new SparseInstance();
			
			
				array[j] = (distance.measure(data,cluster));
				
			}
				double min = array[0];
				for(int k=0;k<array.length;k++)
				{
					if(array[k]<min)
					{
						min = array[k];
						index = k;
					}
				}
		//user가 속한 클러스터는
			graphJsonClusterData.setARank(hiClusterData.get(0).getCount());	
			graphJsonClusterData.setBRank(hiClusterData.get(1).getCount());	
			graphJsonClusterData.setCRank(hiClusterData.get(2).getCount());	
			graphJsonClusterData.setDRank(hiClusterData.get(3).getCount());	
			graphJsonClusterData.setERank(hiClusterData.get(4).getCount());	
		//user의 정보는
				graphJsonHIData.setHi(index);
				graphJsonHIData.setTi((int)data.get(0).doubleValue());	
				graphJsonHIData.setPi((int)data.get(1).doubleValue());
				graphJsonHIData.setSi((int)data.get(2).doubleValue());
				graphJsonHIData.setTvi((int)data.get(3).doubleValue());
				graphJsonHIData.setPvi((int)data.get(4).doubleValue());
				graphJsonHIData.setTvi((int)data.get(5).doubleValue());
		
		
				c.add(Calendar.DAY_OF_MONTH, 1);
		}	
	
		
		
		
		return returnData;
	
	}

}
