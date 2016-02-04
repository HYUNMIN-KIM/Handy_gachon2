package com.handysoft.util;

public class ActivityFormat {

	public static int format(float point)
	{
		int grade=0;
		if(point>=100)
			grade=1;
		else if(point>=80)
			grade=2;
		else if(point>=60)
			grade=3;
		else if(point<60)
			grade=4;

		return grade;
	}
	
}
