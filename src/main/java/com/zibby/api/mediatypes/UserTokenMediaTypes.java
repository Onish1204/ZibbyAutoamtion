package com.zibby.api.mediatypes;


import javax.ws.rs.core.MediaType;

public enum UserTokenMediaTypes implements BasicMediaTypes {

    WWW_FORM_URL_ENCODED(MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_FORM_URLENCODED, "www-form-url-encoded");
    private final String contentType;
    private final String accept;
    private final String serialType;

    private UserTokenMediaTypes(String contentType, String accept, String serialType) {
        this.contentType = contentType;
        this.accept = accept;
        this.serialType = serialType;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public String getAccept() {
        // TODO Auto-generated method stub
        return accept;
    }

    @Override
    public String getSerialType() {
        // TODO Auto-generated method stub
        return serialType;
    }
}