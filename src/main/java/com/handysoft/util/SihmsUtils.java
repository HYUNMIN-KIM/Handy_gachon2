package com.handysoft.util;



/**
 * 
 * @author JungMyungHoon
 *
 */
public class SihmsUtils  {

	public static void printValues(Object... objs){
		if(objs.length > 1 && objs.length%2 == 0){
			for(int i = 0; i < objs.length; i += 2){
				System.out.println(objs[i]+" : "+objs[i+1]);
			}
		}
	}
}
