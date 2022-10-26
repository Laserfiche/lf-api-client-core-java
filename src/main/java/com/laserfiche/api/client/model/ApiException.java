package com.laserfiche.api.client.model;

import java.util.Map;

public class ApiException extends RuntimeException {
    private int statusCode;
    private String response;
    private Map<String, String> headers;
    private ProblemDetails problemDetails;

    public ApiException(String message, int statusCode, String response, Map<String, String> headers,
            ProblemDetails problemDetails) {
        super(message);
        this.statusCode = statusCode;
        this.response = response;
        this.headers = headers;
        this.problemDetails = problemDetails;
    }

    public ProblemDetails getProblemDetails() {
        return problemDetails;
    }

    public void setProblemDetails(ProblemDetails problemDetails) {
        this.problemDetails = problemDetails;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}