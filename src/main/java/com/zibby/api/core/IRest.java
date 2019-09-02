package com.zibby.api.core;

import com.zibby.api.authenticators.IAuthenticationStrategy;
import com.zibby.api.mediatypes.BasicMediaTypes;
import com.zibby.api.request.RequestConfigurator;
import com.zibby.api.request.RequestProcessor;
import com.zibby.api.request.XObjectMapper;
import com.zibby.api.response.ResponseProcessor;

import io.restassured.http.Headers;
import io.restassured.specification.RequestSpecification;

public class IRest {

    private final RequestSpecification requestSpec;
    private final RequestProcessor requestProcessor;
    private final ResponseProcessor responseProcessor;
    private final IAuthenticationStrategy strategy;
    private XObjectMapper mapper; //= new XObjectMapper();
    private Headers headers;

    public Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public IRest(BasicMediaTypes mediaType, IAuthenticationStrategy strategy) {
        RequestConfigurator requestConfig = new RequestConfigurator(mapper, mediaType);
        this.requestSpec = requestConfig.getRequestSpec();
        this.requestProcessor = new RequestProcessor(mediaType, mapper);
        this.responseProcessor = new ResponseProcessor();
        this.strategy = strategy;
    }

    public IRest(BasicMediaTypes mediaType, IAuthenticationStrategy strategy, Headers headers) {
        RequestConfigurator requestConfig = new RequestConfigurator(mapper, mediaType, headers);
        this.requestSpec = requestConfig.getRequestSpec();
        this.requestProcessor = new RequestProcessor(mediaType, mapper);
        this.responseProcessor = new ResponseProcessor();
        this.strategy = strategy;
    }

    public RequestSpecification createRequest() {
        return this.strategy.createRequest(requestSpec);
    }

    public Object serialize(Object obj) {
        return requestProcessor.serialize(obj);
    }

    public <T> T deserialize(Class<T> clazz, String text) {
        return (T) requestProcessor.deserialize(clazz, text);
    }

    public ResponseProcessor getResponseProcessor() {
        return this.responseProcessor;
    }

    public void setObjectMapper(XObjectMapper mapper) {
        this.mapper = mapper;
    }
}