package com.zibby.api.headers;

import java.util.ArrayList;
import java.util.List;

import io.restassured.http.Header;
import io.restassured.http.Headers;

public enum ApiHeaders implements APIHeader<Headers> {
    Common {
        @Override
        public Headers setupHeaders() {
            Headers headers;
            List<Header> headerList = new ArrayList<Header>();
            headerList.add(new Header("Content-Type", "application/json"));
            headerList.add(new Header("Authorization", "Bearer 4fc21c13584be131bf1016cf270a90a038e0425f"));
            headers = new Headers(headerList);
            return headers;
        }
    }
}