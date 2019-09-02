package com.zibby.enums.ui;

import java.util.HashMap;

public class DayDifferenceMap {

	
	public static HashMap<String, Integer> returnDayDiff(){
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("MoreThan5", 3);
		map.put("LessThan5", -2);
		map.put("EqualTo5", 0);
		map.put("None", 0);
		return map;
	}
	
	public static HashMap<String, Integer> returnDaysPayFreq(){
		HashMap<String, Integer> daysMap = new HashMap<String, Integer>();
		daysMap.put("Weekly", 7);
		daysMap.put("Every other week", 14);
		daysMap.put("Twice a month", 15);
		daysMap.put("Monthly", 30);
		daysMap.put("Other", 14);
		return daysMap;
	}
	
}
