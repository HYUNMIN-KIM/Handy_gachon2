package com.handysoft.util;

public class SyncChangeIndexFormat {
	
	public static int format(float score)
	{
		if(score>=15)
			return 1;
		else if(score>=10)
			return 2;
		else if(score>=5)
			return 3;
		else if(score>=3)
			return 4;
		else 
			return 5;
	}

}
