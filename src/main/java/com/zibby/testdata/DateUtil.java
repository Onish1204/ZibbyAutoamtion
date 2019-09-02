package com.zibby.testdata;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.fluttercode.datafactory.impl.DataFactory;

public class DateUtil {

	public static Date getDateOnDay(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.WEEK_OF_YEAR, -1);
		cal.set(Calendar.DAY_OF_WEEK, day);
		return cal.getTime();
	}

	// Returns pay dates with the pay frequency difference that is the number of
	// days between each pay period
	public static HashMap<String, String> dateGen(String payFrequency) {
		HashMap<String, String> startEndDates = new HashMap<String, String>();

		int dayDifference = 0;
		Date minDate = new Date();
		Date maxDate = new Date(System.currentTimeMillis());

		switch (payFrequency) {
		case "Weekly":
			maxDate = getDateOnDay(maxDate, 6);
			dayDifference = 7;
			break;

		case "Every other week":
			maxDate = getDateOnDay(maxDate, 6);
			dayDifference = 14;
			break;

		case "Twice a month":
			Calendar c = Calendar.getInstance();
			c.set(Calendar.DAY_OF_MONTH, 1);
			maxDate = c.getTime();
			dayDifference = 15;

			break;

		case "Monthly":
			Calendar c1 = Calendar.getInstance();
			c1.set(Calendar.DAY_OF_MONTH, 1);
			maxDate = c1.getTime();
			dayDifference = 30;

			break;
		case "Other":
			dayDifference = 7;
			break;
		}


		DataFactory df = new DataFactory();
		Date start = df.getDateBetween(minDate, maxDate);
		Date end = df.getDate(start, -dayDifference, -dayDifference + 1);

		startEndDates.put("lastPayDate", new SimpleDateFormat("MM/dd/yyyy").format(end).toString());
		startEndDates.put("nextPayDate", new SimpleDateFormat("MM/dd/yyyy").format(start).toString());

		return startEndDates;
	}
	
	//Returns PayDates with the next paydate as current date.
	public static HashMap<String, String> dateGenerator(String payFrequency, int diff){
		HashMap<String,String>  dates = new HashMap<String, String>();
		Date lastDate = new Date();
		Date nDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nDate);         
		//Covering for payFrequency Scenarios to check 5 days ahead/behind current date
		calendar.add(Calendar.DAY_OF_YEAR, diff+ 5 );
		Date nextDate = calendar.getTime();
		DataFactory df = new DataFactory();
		int dayDifference = 0;
		switch (payFrequency) {
		case "Weekly":
			dayDifference = 7;
			break;
		case "Every other week":
				dayDifference = 14;
				break;
		case "Twice a month":
			dayDifference = 15;
			break;
		case "Monthly":
			dayDifference =30;
			break;
		case "Other":
			dayDifference = 10;
			break;
				
		default:
			break;
		}
		lastDate = df.getDate(nextDate, -dayDifference, -dayDifference+1);
		dates.put("lastPayDate", new SimpleDateFormat("MM/dd/yyyy").format(lastDate).toString());
		dates.put("nextPayDate", new SimpleDateFormat("MM/dd/yyyy").format(nextDate).toString());
		return dates;
	}

	public static int getDifferenceDays(Date d1, Date d2) {
		long diff = d2.getTime() - d1.getTime();
		return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}

	public static String getRandomBirthday() {
		Random rnd;
		Date dt;
		long ms;

		// Get a new random instance, seeded from the clock
		rnd = new Random();

		// Get an Epoch value roughly between 1940 and 1999
		// -946771200000L = January 1, 1940
		// Add up to 59 years to it (using modulus on the next long)
		ms = -946771200000L + (Math.abs(rnd.nextLong()) % (59L * 365 * 24 * 60 * 60 * 1000));

		dt = new Date(ms);
		return new SimpleDateFormat("MM/dd/yyyy").format(dt).toString();
	}

	public static int randBetween(int start, int end) {
		return start + (int) Math.round(Math.random() * (end - start));
	}
	
	public static String addDaysToDate(String date, int diff) {
		Date nDate = null;
		try {
			nDate = new SimpleDateFormat("MM/dd/yyyy").parse(date);
		} catch (ParseException e) {
			
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nDate);         
		calendar.add(Calendar.DAY_OF_YEAR, diff-1);
		Date nextDate = calendar.getTime();
		return new SimpleDateFormat("MM/dd/yyyy").format(nextDate).toString();
	}
	
	public static String formatDate(String dateStr) throws ParseException {
		
		DateFormat formatter = new SimpleDateFormat("M/dd/yyyy");
		Date date = (Date)formatter.parse(dateStr);
		System.out.println(date);        

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		String formatedDate = format.format(cal.getTime());
		
		return formatedDate;
		
	}
	

}
