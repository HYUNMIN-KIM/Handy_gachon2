package com.handysoft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.handysoft.model.HIClusterData;
import com.handysoft.repository.ClusterValueRepository;

@Service
public class ClusterValueService {

	@Autowired
	private ClusterValueRepository cvR;
	
	public void saveAll(List<HIClusterData> datas){
		cvR.save(datas);
	}
	
}
