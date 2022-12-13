package com.laserfiche.api.client.model;

import java.util.Map;

/**
 * An exception thrown from error API responses.
 */
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

    /**
     * Returns the API problem details.
     */
    public ProblemDetails getProblemDetails() {
        return problemDetails;
    }

    /**
     * Sets the API problem details.
     */
    public void setProblemDetails(ProblemDetails problemDetails) {
        this.problemDetails = problemDetails;
    }

    /**
     * Returns the API status code.
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the API status code.
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    /**
     * Returns the API response headers.
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * Sets the API response headers.
     */
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}