package com.handysoft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.handysoft.model.HIClusterData;
import com.handysoft.model.HIClusterDataCompositeKey;

@Repository
public interface ClusterValueRepository extends JpaRepository<HIClusterData, HIClusterDataCompositeKey>{
	
	List<HIClusterData> findByYearAndMonthAndDay(int year, int month, int day);
}
