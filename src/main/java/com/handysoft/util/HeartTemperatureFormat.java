package com.handysoft.util;

public class HeartTemperatureFormat {
	
	//온도와 맥박 점수
		public static int format(float point)
		{
			int grade=0;
			if(point>=90)
				grade=1;
			else if(point>=80)
				grade=2;
			else if(point>=70)
				grade=3;
			else if(point<70)
				grade=4;

			return grade;
		}

}
