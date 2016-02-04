package com.handysoft.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SetCalendar {
	// Calendar 설정 메소드 : startDate가 null이면 한 주 전 일요일로
			public static void set(String startDate, Calendar c) {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

				if (startDate == null) {
					c.add(3, -1);
					c.add(5, (c.get(7) - 1) * -1);

				} else {
					try {
						c.setTime(formatter.parse(startDate));
					} catch (Exception e) {
						// formatter parse error
						e.printStackTrace();
					}

				}
			}
}
