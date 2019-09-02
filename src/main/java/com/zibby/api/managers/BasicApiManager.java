package com.zibby.api.managers;


import com.zibby.api.core.IRest;
import com.zibby.api.authenticators.IAuthenticationStrategy;
import com.zibby.api.mediatypes.BasicMediaTypes;

import io.restassured.http.Headers;
import io.restassured.response.Response;

public class BasicApiManager extends IRest {

    private String baseUri;

    public String getBaseUri() {
        return baseUri;
    }

    public void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
    }

    public BasicApiManager(BasicMediaTypes mediaType, IAuthenticationStrategy strategy, Headers headers) {
        super(mediaType, strategy, headers);

    }

    public Response sendGetRequest(String baseUrl, String resourceUrl, String resourceParameters) {
        Response response = null;
        if (resourceParameters == null) {
            resourceParameters = "";
        }
        response = getResponseProcessor()
                .setLastResponseForThread(createRequest().baseUri(baseUrl).get(resourceUrl + resourceParameters));
        return response;
    }

    public <T> Response sendPostRequest(String baseUrl, String resourceUrl, T requestBody) {

        Response response = null;
        response = getResponseProcessor()
                .setLastResponseForThread(createRequest().baseUri(baseUrl).content(requestBody).post(resourceUrl));
        return response;
    }
}