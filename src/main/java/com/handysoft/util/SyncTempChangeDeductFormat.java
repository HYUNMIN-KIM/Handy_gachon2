package com.handysoft.util;

public class SyncTempChangeDeductFormat {

	public static int format(float point)
	{
		int grade=0;

		if(point>=15)
			grade=1;
		else if(point<15 && point>=10)
			grade=2;
		else if(point<10 && point>=5)
			grade=3;
		else if(point<5)
			grade=4;

		return grade;
	}
}
