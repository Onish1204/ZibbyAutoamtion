package com.zibby.api.authenticators;


import io.restassured.specification.RequestSpecification;

public interface IAuthenticationStrategy {

    public RequestSpecification createRequest(RequestSpecification requestSpec);

    public void setAuthContext(AuthContext context);

}