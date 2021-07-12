package com.oneandone.stonith.entities;

import org.springframework.http.HttpMethod;

import java.util.Map;

public class DialectConfiguration {
    private String endpoint;
    private HttpMethod method;
    private Map<String, String> headers;
    private String body;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
