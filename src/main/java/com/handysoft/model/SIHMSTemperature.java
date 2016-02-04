package com.handysoft.model;


public class SIHMSTemperature {
	public final static int WARN_DP_LV1 = 1;
	public final static int WARN_DP_LV2 = 2;
	public final static int WARN_DP_LV3 = 3;
		
	private float normalLow;
	private float normalHigh;
	
	private float lowerLv1;
	private float lowerLv2;
	
	private float higherLv1;
	private float higherLv2;
	
	public SIHMSTemperature(int age){
		//TODO: 나이 반영?
		this.normalLow = 35.2f;			
		this.normalHigh = 36.9f;
		
		this.lowerLv1 = 35.0f;
		this.lowerLv2 = 34.0f;
		
		this.higherLv1 = 37.5f;
		this.higherLv2 = 38.3f;
	}

	public float getNormalLow() {
		return normalLow;
	}

	public float getNormalHigh() {
		return normalHigh;
	}

	public float getLowerLv1() {
		return lowerLv1;
	}

	public float getLowerLv2() {
		return lowerLv2;
	}

	public float getHigherLv1() {
		return higherLv1;
	}

	public float getHigherLv2() {
		return higherLv2;
	}
	
	
}
