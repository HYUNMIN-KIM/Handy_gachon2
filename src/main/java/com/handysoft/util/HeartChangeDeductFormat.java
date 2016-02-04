package com.handysoft.util;

public class HeartChangeDeductFormat {

	public static int format(float point)
	{
		int grade=0;

		if(point>=20)
			grade=1;
		else if(point>=15)
			grade=2;
		else if(point>=10)
			grade=3;
		else if(point<10)
			grade=4;

		return grade;
	}
}
