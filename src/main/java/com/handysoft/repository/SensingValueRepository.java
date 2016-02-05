package com.handysoft.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.handysoft.model.SIHMSSensingData;
import com.handysoft.model.SensingDataCompositeKey;

@Repository
public interface SensingValueRepository extends CrudRepository<SIHMSSensingData, SensingDataCompositeKey> {

	List<SIHMSSensingData> findBySeqAndYearAndMonthAndDayOrderByLogDateAsc(int seq, int year, int month, int day);

}
