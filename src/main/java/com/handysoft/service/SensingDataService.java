package com.handysoft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.handysoft.model.SIHMSSensingData;
import com.handysoft.repository.SensingValueRepository;

@Service
public class SensingDataService {
	@Autowired
	SensingValueRepository svR;
	
	public List<SIHMSSensingData> findSensorListBySeqAndYearAndMonthAndDay(int seq, int year, int month, int day){
		return svR.findBySeqAndYearAndMonthAndDayOrderByLogDateAsc(seq, year, month, day);
	}
	
}
