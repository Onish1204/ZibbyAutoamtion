package com.zibby.api.authenticators;


import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class DefaultAuthenticationStrategy implements IAuthenticationStrategy {

    private AuthContext context;

    public DefaultAuthenticationStrategy() {
    }

    @Override
    public RequestSpecification createRequest(RequestSpecification requestSpec) {
        RequestSpecification givenSpec = RestAssured.given(requestSpec).log().all();
        return givenSpec.auth().none();
    }

    @Override
    public void setAuthContext(AuthContext context) {
        this.context = context;
    }
}
