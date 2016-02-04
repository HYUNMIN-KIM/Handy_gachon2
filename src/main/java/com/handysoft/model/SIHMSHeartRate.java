package com.handysoft.model;


public class SIHMSHeartRate {
	public final static int WARN_DP_LV1 = 1;
	public final static int WARN_DP_LV2 = 2;
	public final static int WARN_DP_LV3 = 3;
		
	private int normalLow;
	private int normalHigh;
	
	private int lowerLv1;
	private int lowerLv2;
	
	private int higherLv1;
	private int higherLv2;
	
	public SIHMSHeartRate(int age, int avgHR){
		//TODO: 나이 반영?
		this.normalLow = avgHR-2;			
		this.normalHigh = avgHR+2;
		
		this.lowerLv1 = normalLow * 9/10;
		this.lowerLv2 = normalLow * 8/10;
		
		this.higherLv1 = normalHigh * 11/10;
		this.higherLv2 = normalHigh * 12/10;
		//System.out.println(lowerLv1+"/"+lowerLv2+"/"+higherLv1+"/"+higherLv2);
	}

	public int getNormalLow() {
		return normalLow;
	}

	public int getNormalHigh() {
		return normalHigh;
	}

	public int getLowerLv1() {
		return lowerLv1;
	}

	public int getLowerLv2() {
		return lowerLv2;
	}

	public int getHigherLv1() {
		return higherLv1;
	}

	public int getHigherLv2() {
		return higherLv2;
	}
	
	
}
