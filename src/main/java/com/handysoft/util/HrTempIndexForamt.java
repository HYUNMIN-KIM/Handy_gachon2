package com.handysoft.util;

public class HrTempIndexForamt {
	
	public static int format(float score)
	{
		if(score>=90)
			return 5;
		else if(score>=80)
			return 4;
		else if(score>=70)
			return 3;
		else if(score>=60)
			return 2;
		else 
			return 1;
		
	}

}
