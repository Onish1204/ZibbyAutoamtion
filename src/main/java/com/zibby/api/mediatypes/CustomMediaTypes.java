package com.zibby.api.mediatypes;

import javax.ws.rs.core.MediaType;

public enum CustomMediaTypes implements BasicMediaTypes {

    INVALID_MEDIA_TYPE(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, "xml");

    private final String accept;
    private final String contentType;
    private final String serialType;

    CustomMediaTypes(String accept, String contentType, String serialType) {
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