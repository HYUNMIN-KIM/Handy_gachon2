package com.handysoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.handysoft.util.ActivityFormat;
import com.handysoft.util.HeartChangeDeductFormat;
import com.handysoft.util.HeartTemperatureFormat;
import com.handysoft.util.RhythmFormat;
import com.handysoft.util.SyncTempChangeDeductFormat;
import com.handysoft.util.WikiParser;

@RestController
public class DataAnalysisRestController {

	@Autowired
	private WikiParser wikiParser;
	
	@RequestMapping("/DataAnalysis")
	public @ResponseBody String dataAnalysis(
			@RequestParam(value="content", required=true) String content,
			@RequestParam(value="point", required=true) float point
			){
		
		int grade=0;
		//ajax를 통해서 받아온 contents와 point를 처리
		if(content.equalsIgnoreCase("ConditionDetail_"))
			grade = (int)point;
		else if(content.equalsIgnoreCase("Temperature_"))
			grade = HeartTemperatureFormat.format(point);
		else if(content.equalsIgnoreCase("TemperatureChange_"))
			grade = SyncTempChangeDeductFormat.format(point);
		else if(content.equalsIgnoreCase("TemperatureRhythm_"))
			grade = RhythmFormat.format(point);
		else if(content.equalsIgnoreCase("HeartRate_"))
			grade = HeartTemperatureFormat.format(point);
		else if(content.equalsIgnoreCase("HeartRateChange_"))
			grade = HeartChangeDeductFormat.format(point);
		else if(content.equalsIgnoreCase("HeartRateRhythm_"))
			grade = RhythmFormat.format(point);
		else if(content.equalsIgnoreCase("Synchronization_"))
			grade = SyncTempChangeDeductFormat.format(point);
		else if(content.equalsIgnoreCase("Activity_"))
			grade = ActivityFormat.format(point);
		
		
		return wikiParser.point(content, grade);
	}
	
}
