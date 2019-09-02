package com.zibby.api.request;

import com.zibby.api.mediatypes.BasicMediaTypes;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;

public class RequestConfigurator {

    private final RequestSpecification requestSpec;

    public RequestConfigurator(XObjectMapper mapper, BasicMediaTypes mediaType) {
        if (mediaType.getContentType().contains("x-www-form-urlencoded")) {
            this.requestSpec = RestAssured.given()
                    .config(createRestAssuredConfig(mapper))
                    .relaxedHTTPSValidation()
                    .header("User-Agent", "XRest From Xpanxion")
                    .contentType(ContentType.URLENC);
        } else {
            this.requestSpec = RestAssured.given()
                    .config(createRestAssuredConfig(mapper))
                    .relaxedHTTPSValidation()
                    .header("User-Agent", "XRest From Xpanxion")
                    .header("Accept", mediaType.getAccept())
                    .header("Content-Type", mediaType.getContentType());
            RestAssured.defaultParser = Parser.TEXT;
        }
    }

    public RequestConfigurator(XObjectMapper mapper, BasicMediaTypes mediaType, Headers headers) {
        if (mediaType.getContentType().contains("x-www-form-urlencoded")) {
            this.requestSpec = RestAssured.given()
                    .config(createRestAssuredConfig(mapper))
                    .relaxedHTTPSValidation()
                    .header("User-Agent", "XRest From Xpanxion")
                    .headers(headers)
                    .contentType(ContentType.URLENC);
        } else {
            this.requestSpec = RestAssured.given()
                    .config(createRestAssuredConfig(mapper))
                    .relaxedHTTPSValidation()
                    .header("User-Agent", "XRest From Xpanxion")
                    .header("Accept", mediaType.getAccept())
                    .header("Content-Type", mediaType.getContentType())
                    .headers(headers);
            RestAssured.defaultParser = Parser.TEXT;
        }
    }

    public RequestSpecification getRequestSpec() {
        return this.requestSpec;
    }

    private RestAssuredConfig createRestAssuredConfig(XObjectMapper mapper) {
        ObjectMapperConfig omc = ObjectMapperConfig.objectMapperConfig().defaultObjectMapper(mapper);
        RestAssuredConfig restConfig = RestAssuredConfig.newConfig()
                .httpClient(HttpClientConfig.httpClientConfig())
                .objectMapperConfig(omc)
                .encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8"));
        return restConfig;
    }

}
