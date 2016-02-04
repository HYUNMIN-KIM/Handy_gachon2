package com.handysoft.util;

public class RhythmFormat {

	public static int format(float point)
	{
		int grade=0;

		if(point>=0.0)
			grade=2;
		else if(point<0)
			grade=1;
		return grade;
	}
}
