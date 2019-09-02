package com.zibby.util;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.zibby.pages.OTPPage;

public class SMSTest {

	private WebDriver driver;
	private OTPPage page;
	private SMSUtil sms;
	private final String fromPhoneNumber = "1234567";

	public void setup() {
		driver = new ChromeDriver();
		page = new OTPPage();
		sms = new SMSUtil();
	}

	public void smsTest() throws IOException{
       
        String phoneNumber = sms.getNumber();

        //page.enterCustomerMobileNumber(phoneNumber);
        
        String code = sms.getMessage(phoneNumber, fromPhoneNumber);
	}
    

}
