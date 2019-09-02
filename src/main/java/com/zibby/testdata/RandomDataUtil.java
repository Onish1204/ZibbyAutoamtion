package com.zibby.testdata;

import java.util.Random;

public class RandomDataUtil {
	
	public static String getRandomOrderID() {
		String validChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder ID = new StringBuilder();
		Random rnd = new Random();
		while (ID.length() < 5) {
			int index = (int) (rnd.nextFloat() * validChars.length());
			ID.append(validChars.charAt(index));
		}
		String orderID = ID.toString();
		return orderID;
	}

}
