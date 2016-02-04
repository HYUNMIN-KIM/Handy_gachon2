package com.handysoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.handysoft.model.UserInfo;

@Repository
public interface UserBeanRepository extends JpaRepository<UserInfo, String>{
	
}
