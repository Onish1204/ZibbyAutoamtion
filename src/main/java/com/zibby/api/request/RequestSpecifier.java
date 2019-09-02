package com.zibby.api.request;

import io.restassured.specification.RequestSpecification;

public class RequestSpecifier {

    private final RequestSpecification requestSpec;

    public RequestSpecifier(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }
}