package com.handysoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.handysoft.model.UserExtraInfo;

@Repository
public interface UserExtraBeanRepository extends JpaRepository<UserExtraInfo, Integer>{

	
}
