package com.zibby.api.clients;

import com.zibby.api.authenticators.DefaultAuthenticationStrategy;
import com.zibby.api.headers.ApiHeaders;
import com.zibby.api.managers.ApiContext;
import com.zibby.api.managers.ApiManager;
import com.zibby.api.mediatypes.DefaultMediaTypes;
import com.zibby.api.pojo.SendCodeRequest;
import com.zibby.api.pojo.SendCodeResponse;

import io.restassured.http.Headers;

public class SendCode {

	ApiContext apiContext = new ApiContext();
	Headers headers = new Headers();
	SendCodeRequest sendCodeRequest = new SendCodeRequest();
	SendCodeResponse sendCodeResponse = new SendCodeResponse();

	public SendCodeResponse invoke(SendCodeRequest request) {
		
		String baseUrl = "https://qa.zibby.com/";
		
		System.out.println(baseUrl);
		
		String resourceUrl = "api/ng/zibbyjs/user/auth/retailer/";

		apiContext.setRequestClass(SendCodeRequest.class);
		apiContext.setResponseClass(SendCodeResponse.class);
		apiContext.setMediaType(DefaultMediaTypes.JSON);
		apiContext.setStrategy(new DefaultAuthenticationStrategy());
		
		headers = ApiHeaders.Common.setupHeaders();
		ApiManager apiManager = new ApiManager(apiContext, headers);
		
		sendCodeResponse = (SendCodeResponse) 	apiManager.sendRequestReturnResponse(baseUrl, resourceUrl, "post", request, null);
		
		return sendCodeResponse;
		
	}

}
