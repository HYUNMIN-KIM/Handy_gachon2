package com.handysoft.util;

public class ConditionFormat {

	public static int format(float value){
		if(value >= 90)
			return 10;
		else if(value >= 80)
			return 9;
		else if(value >= 70)
			return 8;
		else if(value >= 60)
			return 7;
		else if(value >= 50)
			return 6;
		else if(value >= 40)
			return 5;
		else if(value >= 30)
			return 4;
		else if(value >= 20)
			return 3;
		else if(value >= 10)
			return 2;
		else
			return 1;
	}
	
}
