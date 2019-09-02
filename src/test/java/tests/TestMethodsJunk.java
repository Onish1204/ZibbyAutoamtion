package tests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zibby.enums.ui.DayDifferenceMap;
import com.zibby.testdata.DateUtil;

public class TestMethodsJunk {

	public static void main(String[] args) throws ParseException {
		
		System.out.println(DateUtil.dateGenerator("Weekly", 1));
		System.out.println(DateUtil.dateGenerator("Twice a month", 1));
		System.out.println(DateUtil.dateGenerator("Monthly", 1));
		System.out.println(DateUtil.dateGenerator("Every other week", 1));
		System.out.println(DateUtil.dateGenerator("Monthly", 1));


		System.out.println("*****************************");
		
		System.out.println(DateUtil.dateGenerator("Weekly", 0));
		System.out.println(DateUtil.dateGenerator("Twice a month", 0));
		System.out.println(DateUtil.dateGenerator("Monthly", 0));
		System.out.println(DateUtil.dateGenerator("Every other week", 0));
		System.out.println(DateUtil.dateGenerator("Monthly", 0));

		System.out.println("*****************************");
		
		System.out.println(DateUtil.dateGenerator("Weekly", -2));
		System.out.println(DateUtil.dateGenerator("Twice a month", -2));
		System.out.println(DateUtil.dateGenerator("Monthly", -2));
		System.out.println(DateUtil.dateGenerator("Every other week", -2));
		System.out.println(DateUtil.dateGenerator("Monthly", -2));
		
		System.out.println("*****************************");
		SimpleDateFormat sdf =new SimpleDateFormat("MM/dd/yyyy");
		Date d1 = null;
		Date d2 = null;
		try {
			d1 = sdf.parse("08/15/2019");
			d2 =    sdf.parse("08/22/2019");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		System.out.println(DateUtil.getDifferenceDays(d1, d2));
		String s3 = "3. Lease Term and Payment Schedule: This Agreement is for 7 days. It begins on 08/15/2019 ";
		
		String[] s = get2DatesFromString(s3);
		for (String string : s) {
			System.out.println(string);
		}
		
		int days = DayDifferenceMap.returnDaysPayFreq().get("Every other week");
		System.out.println("Days" + days);
		
			System.out.println(getRandomOrderID());
			
			System.out.println("Date Util : " + DateUtil.formatDate("Fri Aug 02 00:00:00 IST 2019"));
		
	}
	
	protected static String getRandomOrderID() {
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
	
	public  static String[] get2DatesFromString(String desc) {
	    int count=0;
	    String[] allMatches = new String[2];
	    Matcher m = Pattern.compile("(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\\d\\d").matcher(desc);
	    while (m.find()) {
	        allMatches[count] = m.group();
	        count++;
	    }
	    return allMatches;
	}
	
}
