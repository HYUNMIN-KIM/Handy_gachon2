package com.handysoft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import net.sf.javaml.core.Instance;
import net.sf.javaml.core.SparseInstance;
import net.sf.javaml.distance.AbstractDistance;
import net.sf.javaml.distance.AngularDistance;
import net.sf.javaml.distance.DistanceMeasure;
import net.sf.javaml.distance.JaccardIndexDistance;

@EnableAutoConfiguration
@ComponentScan
@EnableScheduling
@SpringBootApplication
public class HandySoftApplication{

	@Autowired
	ScheduledTask st;
	
	public static void main(String[] args) {
		SpringApplication.run(HandySoftApplication.class, args);
	}

	
}
