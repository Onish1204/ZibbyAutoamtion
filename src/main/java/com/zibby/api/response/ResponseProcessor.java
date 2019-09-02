package com.zibby.api.response;


import io.restassured.response.Response;

public class ResponseProcessor {

    private final ThreadLocal<Response> lastResponse = new ThreadLocal<Response>();
    //the below variable is created to print the response body in report.
    public static Response lastJsonResponse;

    public <T> T getResultAfterVerifyingStatusCode(Response response, Class<T> resultType, int acceptableCode) {
        int status = response.statusCode();
        if (status != acceptableCode) {
            throw new RuntimeException("Error parsing the object: " + resultType.getSimpleName() + ". Expected Code: " + acceptableCode + "but got: " + status + ".");
        }
        response.prettyPrint();
        lastJsonResponse = response;
        return (T) response.as(resultType);
    }
    
    public <T> T getResult(Response response, Class<T> resultType) {
        response.prettyPrint();
        return (T) response.as(resultType);
    }

    public Response lastResponseForThread() {
        return lastResponse.get();
    }

    public Response setLastResponseForThread(Response resp) {
        lastResponse.set(resp);
        return resp;
    }
}