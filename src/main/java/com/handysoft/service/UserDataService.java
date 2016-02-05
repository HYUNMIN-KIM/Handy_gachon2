package com.handysoft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.handysoft.model.UserExtraInfo;
import com.handysoft.model.UserInfo;
import com.handysoft.repository.UserBeanRepository;
import com.handysoft.repository.UserExtraBeanRepository;

@Service
public class UserDataService {
	
	
	@Autowired
	UserBeanRepository uR;
	
	@Autowired
	UserExtraBeanRepository uxR;
	
	
	public UserInfo findUserBeanByID(String userID){
		return uR.findOne(userID);
	}
	
	public UserExtraInfo findUserExtraBeanBySeq(int seq){
		return uxR.findOne(seq);
	}
	
}
