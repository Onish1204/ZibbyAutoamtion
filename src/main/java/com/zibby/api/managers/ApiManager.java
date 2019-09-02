package com.zibby.api.managers;


import java.util.Calendar;
import java.util.Map;

import org.junit.Assert;

import com.zibby.api.core.IRest;

import io.restassured.http.Headers;
import io.restassured.response.Response;

public class ApiManager extends IRest {

    private String baseUri;
    private Class<? extends SerializableObject> requestClass;
    private Class<?> responseClass;
    Map<String, Object> obj;

    public String getBaseUri() {
        return baseUri;
    }

    public void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
    }

    public ApiManager(ApiContext context) {
        super(context.mediaType, context.strategy);
        this.responseClass = context.responseClass;
        this.requestClass = context.requestClass;
    }

    public ApiManager(ApiContext context, Headers headers) {
        super(context.mediaType, context.strategy, headers);
        this.responseClass = context.responseClass;
        this.requestClass = context.requestClass;
    }

    public <T extends SerializableObject, R> R sendRequest(String baseUrl, String resourceUrl, String method, T requestBody, String resourceParameters, int expectedStatusCode) {
        Response response = null;
        if (method.equalsIgnoreCase("get")) {

            if (resourceParameters == null) {
                resourceParameters = "";
            }
            response = getResponseProcessor()
                    .setLastResponseForThread(createRequest().baseUri(baseUrl).get(resourceUrl + resourceParameters));
        }

        if (method.equalsIgnoreCase("delete")) {

            if (resourceParameters == null) {
                resourceParameters = "";
            }
            response = getResponseProcessor()
                    .setLastResponseForThread(createRequest().baseUri(baseUrl).delete(resourceUrl + resourceParameters));
        }

        if (method.equalsIgnoreCase("post")) {
            response = getResponseProcessor()
                    .setLastResponseForThread(createRequest().baseUri(baseUrl).body(requestBody).post(resourceUrl));          
        }
        if (method.equalsIgnoreCase("put")) {
            response = getResponseProcessor()
                    .setLastResponseForThread(createRequest().baseUri(baseUrl).body(requestBody).put(resourceUrl));
        }
        
        Assert.assertEquals("'Success status code should be displayed'" , expectedStatusCode, response.getStatusCode());
        return (R) getResponseProcessor().getResultAfterVerifyingStatusCode(response, this.responseClass, expectedStatusCode);
    }
    
    public  <T extends SerializableObject> Response sendRequest(String baseUrl, String resourceUrl, String method, T requestBody, String resourceParameters) {
        Response response = null;
        if (method.equalsIgnoreCase("get")) {

            if (resourceParameters == null) {
                resourceParameters = "";
            }
            response = getResponseProcessor()
                    .setLastResponseForThread(createRequest().baseUri(baseUrl).get(resourceUrl + resourceParameters));
        }

        if (method.equalsIgnoreCase("delete")) {

            if (resourceParameters == null) {
                resourceParameters = "";
            }
            response = getResponseProcessor()
                    .setLastResponseForThread(createRequest().baseUri(baseUrl).delete(resourceUrl + resourceParameters));
        }
        if (method.equalsIgnoreCase("post")) {
            response = getResponseProcessor()
                    .setLastResponseForThread(createRequest().baseUri(baseUrl).content(requestBody).post(resourceUrl));
        }
        if (method.equalsIgnoreCase("put")) {
            response = getResponseProcessor()
                    .setLastResponseForThread(createRequest().baseUri(baseUrl).content(requestBody).put(resourceUrl));
        }
        response.print();
        return response;
    }
    
    public <T extends SerializableObject, R> R sendRequestReturnResponse(String baseUrl, String resourceUrl, String method, T requestBody, String resourceParameters) {
        Response response = null;
        if (method.equalsIgnoreCase("get")) {

            if (resourceParameters == null) {
                resourceParameters = "";
            }
            response = getResponseProcessor()
                    .setLastResponseForThread(createRequest().baseUri(baseUrl).get(resourceUrl + resourceParameters));
        }

        if (method.equalsIgnoreCase("delete")) {

            if (resourceParameters == null) {
                resourceParameters = "";
            }
            response = getResponseProcessor()
                    .setLastResponseForThread(createRequest().baseUri(baseUrl).delete(resourceUrl + resourceParameters));
        }
        if (method.equalsIgnoreCase("post")) {
            response = getResponseProcessor()
                    .setLastResponseForThread(createRequest().baseUri(baseUrl).content(requestBody).post(resourceUrl));
        }
        if (method.equalsIgnoreCase("put")) {
            response = getResponseProcessor()
                    .setLastResponseForThread(createRequest().baseUri(baseUrl).content(requestBody).put(resourceUrl));
        }
        response.print();
        return (R) getResponseProcessor().getResult(response, this.responseClass);
    }

    public String sendRequestAsFormParam(String baseUrl, String resourceUrl, String method, Map<String, String> requestBody, String resourceParameters, int expectedStatusCode) {
        Response response = null;
        if (method.equalsIgnoreCase("get")) {

            if (resourceParameters == null) {
                resourceParameters = "";
            }
            response = getResponseProcessor()
                    .setLastResponseForThread(createRequest().baseUri(baseUrl).get(resourceUrl + resourceParameters));
        }

        if (method.equalsIgnoreCase("post")) {
            response = getResponseProcessor()
                    .setLastResponseForThread(createRequest().baseUri(baseUrl).formParams((Map<String, String>) requestBody).post(resourceUrl));
        }
        if (method.equalsIgnoreCase("put")) {
            response = getResponseProcessor()
                    .setLastResponseForThread(createRequest().baseUri(baseUrl).formParams((Map<String, String>) requestBody).put(resourceUrl));
        }
        response.print();
        return (String) getResponseProcessor().getResultAfterVerifyingStatusCode(response, String.class, expectedStatusCode);
    }

    public <T extends SerializableObject> Response sendRequestAsFormParam(String baseUrl, String resourceUrl, String method, Map<String, String> requestBody, String resourceParameters) {
        Response response = null;
        if (method.equalsIgnoreCase("get")) {

            if (resourceParameters == null) {
                resourceParameters = "";
            }
            response = getResponseProcessor()
                    .setLastResponseForThread(createRequest().baseUri(baseUrl).get(resourceUrl + resourceParameters));
        }

        if (method.equalsIgnoreCase("post")) {
            response = getResponseProcessor()
                    .setLastResponseForThread(createRequest().baseUri(baseUrl).formParams((Map<String, String>) requestBody)
                            .post(resourceUrl));
        }
        if (method.equalsIgnoreCase("put")) {
            response = getResponseProcessor()
                    .setLastResponseForThread(createRequest().baseUri(baseUrl).formParams((Map< String, String>) requestBody
                    ).put(resourceUrl));
        }
        response.print();
        return response;
    }

    public <T> void setResponseClass(Class<T> clazz) {
        this.responseClass = clazz;
    }

    public void handledSleep(int sleepInSeconds) {
        Calendar cal = Calendar.getInstance();
        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.SECOND, sleepInSeconds);
        while (cal1.after(cal)) {
            cal = Calendar.getInstance();
        }
    }

	public <T extends SerializableObject, R> R sendRequestEncoded(String baseUrl, String resourceUrl, String method, String request,
			String resourceParameters, int expectedStatusCode) {
	  
	        Response response = null;
	        if (method.equalsIgnoreCase("get")) {

	            if (resourceParameters == null) {
	                resourceParameters = "";
	            }
	            response = getResponseProcessor()
	                    .setLastResponseForThread(createRequest().baseUri(baseUrl).get(resourceUrl + resourceParameters));
	        }

	        if (method.equalsIgnoreCase("delete")) {

	            if (resourceParameters == null) {
	                resourceParameters = "";
	            }
	            response = getResponseProcessor()
	                    .setLastResponseForThread(createRequest().baseUri(baseUrl).delete(resourceUrl + resourceParameters));
	        }

	        if (method.equalsIgnoreCase("post")) {
	            response = getResponseProcessor()
	                    .setLastResponseForThread(createRequest().baseUri(baseUrl).body(request).post(resourceUrl));          
	        }
	        if (method.equalsIgnoreCase("put")) {
	            response = getResponseProcessor()
	                    .setLastResponseForThread(createRequest().baseUri(baseUrl).body(request).put(resourceUrl));
	        }
	        response.print();
	        Assert.assertEquals("'Success status code should be displayed'" , expectedStatusCode, response.getStatusCode());
	        return (R) getResponseProcessor().getResultAfterVerifyingStatusCode(response, this.responseClass, expectedStatusCode);
	   
	}
  
}

