package com.handysoft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.handysoft.model.SIHMSSensingData;
import com.handysoft.model.SensingDataCompositeKey;

@Repository
public interface SensingValueRepository extends JpaRepository<SIHMSSensingData, SensingDataCompositeKey> {

	List<SIHMSSensingData> findBySeqAndYearAndMonthAndDayOrderByLogDateAsc(int seq, int year, int month, int day);
}
