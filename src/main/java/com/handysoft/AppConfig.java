package com.handysoft;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.handysoft.service.ClusterValueService;
import com.handysoft.service.SensingDataService;
import com.handysoft.service.UserDataService;

@Configuration
public class AppConfig {

	@Bean
	public UserDataService userDataService(){
		return new UserDataService();
	}
	
	@Bean
	public SensingDataService sensingDataService(){
		return new SensingDataService();
	}
	
	@Bean
	public ClusterValueService clusterValueService(){
		return new ClusterValueService();
	}
}
