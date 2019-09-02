package com.zibby.api.authenticators;


import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class BasicAuthenticationStrategy implements IAuthenticationStrategy {

    private BasicAuthContext context;

    public BasicAuthenticationStrategy() {
    }

    @Override
    public RequestSpecification createRequest(RequestSpecification requestSpec) {
        return RestAssured.given(requestSpec).auth().basic(context.getUsername(), context.getPassword());
    }

    @Override
    public void setAuthContext(AuthContext context) {
        this.context = (BasicAuthContext) context;
    }
}