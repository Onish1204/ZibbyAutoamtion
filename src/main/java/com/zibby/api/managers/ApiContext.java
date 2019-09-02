package com.zibby.api.managers;


import com.zibby.api.authenticators.IAuthenticationStrategy;
import com.zibby.api.mediatypes.BasicMediaTypes;

public class ApiContext extends IContext {

    BasicMediaTypes mediaType;
    IAuthenticationStrategy strategy;
    Class<? extends SerializableObject> requestClass;
    Class<?> responseClass;
    String userName;
    String passWord;
    
    public BasicMediaTypes getMediaType() {
        return mediaType;
    }

    public void setMediaType(BasicMediaTypes mediaType) {
        this.mediaType = mediaType;
    }

    public IAuthenticationStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(IAuthenticationStrategy strategy) {
        this.strategy = strategy;
    }

    public Class<? extends SerializableObject> getRequestClass() {
        return requestClass;
    }

    public void setRequestClass(Class<? extends SerializableObject> requestClass) {
        this.requestClass = requestClass;
    }

    public Class<?> getResponseClass() {
        return responseClass;
    }

    public void setResponseClass(Class<?> responseClass) {
        this.responseClass = responseClass;
    }
    
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getPassword() {
        return userName;
    }

    public void setPassword(String password) {
        this.passWord = password;
    }
}
