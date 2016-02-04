package com.handysoft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.handysoft.model.SIHMSSensingData;

@Repository
public interface SensingValueRepository extends JpaRepository<SIHMSSensingData, String>{

	@Query(value="SELECT ROWID, REG_USER_SEQ, TO_CHAR(LOG_DT, 'yyyy-mm-dd hh24:mi:ss') as LOG_DT, YEAR, MONTH, DAY, STEPS, HEART_RATE, TEMPERATURE "
			+ "FROM GB_SENSING_DATA "
			+ "WHERE REG_USER_SEQ=:seq AND YEAR=:year AND MONTH=:month AND DAY=:day", nativeQuery=true)
	List<SIHMSSensingData> findBySeqAndYearAndMonthAndDay(
			@Param("seq") int seq, 
			@Param("year") int year, 
			@Param("month") int month, 
			@Param("day") int day);
	
	
	
}
