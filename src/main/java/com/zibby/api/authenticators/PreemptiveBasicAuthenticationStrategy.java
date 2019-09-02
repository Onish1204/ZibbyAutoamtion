package com.zibby.api.authenticators;


import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class PreemptiveBasicAuthenticationStrategy implements IAuthenticationStrategy {

    private BasicAuthContext context;

    @Override
    public RequestSpecification createRequest(RequestSpecification requestSpec) {
        return RestAssured.given(requestSpec).auth().preemptive().basic(this.context.getUsername(), this.context.getPassword());
    }

    @Override
    public void setAuthContext(AuthContext context) {
        this.context = (BasicAuthContext) context;
    }

}
