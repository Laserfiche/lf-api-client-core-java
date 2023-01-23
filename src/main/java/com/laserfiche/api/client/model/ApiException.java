package com.laserfiche.api.client.model;

import java.util.Map;

/**
 * An exception thrown from error API responses.
 */
public class ApiException extends RuntimeException {
    private int statusCode;
    private Map<String, String> headers;
    private ProblemDetails problemDetails;

    public ApiException(String message, int statusCode, Map<String, String> headers, ProblemDetails problemDetails,
            Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
        this.headers = headers;
        this.problemDetails = problemDetails;
    }

    /**
     * Create an {@link ApiException}. A default {@link ProblemDetails} will be created if a null value is given.
     * @param statusCode The response status code.
     * @param headers The response headers.
     * @param problemDetails The {@link ProblemDetails} response.
     * @param cause The cause.
     * @return {@link ApiException}
     */
    public static ApiException create(int statusCode, Map<String, String> headers, ProblemDetails problemDetails,
            Throwable cause) {
        if (problemDetails == null) {
            problemDetails = ProblemDetails.create(statusCode, headers);
        }
        return new ApiException(problemDetails.getTitle(), statusCode, headers, problemDetails, cause);
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