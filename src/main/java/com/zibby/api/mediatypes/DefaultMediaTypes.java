package com.zibby.api.mediatypes;


import javax.ws.rs.core.MediaType;

public enum DefaultMediaTypes implements BasicMediaTypes {

    XML(MediaType.APPLICATION_XML, MediaType.APPLICATION_XML, "xml"),
    JSON(MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, "json");

    private final String accept;
    private final String contentType;
    private final String serialType;

    DefaultMediaTypes(String accept, String contentType, String serialType) {
        this.accept = accept;
        this.contentType = contentType;
        this.serialType = serialType;
    }

    @Override
    public String getAccept() {
        return this.accept;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public String getSerialType() {
        return this.serialType;
    }
}