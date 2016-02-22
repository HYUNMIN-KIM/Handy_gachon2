package com.handysoft;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.handysoft.kmeans.HIKmeans;
import com.handysoft.kmeans.HISensingDataAddInstance;
import com.handysoft.model.HIClusterData;
import com.handysoft.model.SIHMSSensingData;
import com.handysoft.model.UserExtraInfo;
import com.handysoft.service.ClusterValueService;
import com.handysoft.service.SensingDataService;
import com.handysoft.service.UserDataService;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;

@Component
public class ScheduledTask {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Autowired
	private SensingDataService sensingDataService;
	@Autowired
	private UserDataService userDataService;
	@Autowired
	private ClusterValueService clusterValueService;
	

	@Transactional
	@Scheduled(cron = "0 0 0 * * ?") // 매일 00시
	public void reportDayKmeans() {
		try {
			//어제날짜를 가져옴
			Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH) + 1;
			int day = c.get(Calendar.DAY_OF_MONTH) - 1;
			int seq;
			
			//모든 유저의 uxInfo를 가져옴
			List<UserExtraInfo> userExtraInfos = userDataService.findUserExtraBeanAll();

			
			// 모든 유저의 해당 연월일 센싱 데이터를 가져와
			// HISensingDataAddInstance를 이용해
			// instance로 만들어 낸 후 그걸 Dataset에 넣음
			
			Dataset data = new DefaultDataset();
			for(UserExtraInfo ux : userExtraInfos){
				seq = ux.getSeq();
				List<SIHMSSensingData> sensingDataList 
					= sensingDataService.findSensorListBySeqAndYearAndMonthAndDay(seq, year, month, day);
				data.add(HISensingDataAddInstance.run(ux, sensingDataList));
			}

			//5개 클러스터이기 때문에 최소 5개
			if(data.size() > 4){
				
				// 위 작업으로 모든 유저의 HI 데이터가 담긴 dataset을 clustering
				List<HIClusterData> hiClusterData 
							= HIKmeans.getClusters(data, year, month, day);
				
				//DB에 넣음
				clusterValueService.saveAll(hiClusterData);
			}
			
			System.out.println("Kmeans clustering complete");
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("Kmeans Schedule ERR");
		}

	}

}
