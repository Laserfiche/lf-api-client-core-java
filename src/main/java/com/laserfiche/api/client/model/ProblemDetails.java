// Copyright (c) Laserfiche.
// Licensed under the MIT License. See LICENSE in the project root for license information.
package com.laserfiche.api.client.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * A machine-readable format for specifying errors in HTTP API responses based on <a href="https://tools.ietf.org/html/rfc7807">rfc 7807</a>.
 */
public class ProblemDetails {
    private static final String OPERATION_ID_HEADER = "X-RequestId";
    private static final String API_SERVER_ERROR_MESSAGE_HEADER = "X-APIServer-Error";

    @JsonProperty("type")
    private String type = null;

    @JsonProperty("title")
    private String title = null;

    @JsonProperty("status")
    private Integer status = null;

    @JsonProperty("detail")
    private String detail = null;

    @JsonProperty("instance")
    private String instance = null;

    @JsonProperty("operationId")
    private String operationId = null;

    @JsonProperty("errorSource")
    private String errorSource = null;

    @JsonProperty("errorCode")
    private Integer errorCode = null;

    @JsonProperty("traceId")
    private String traceId = null;

    @JsonProperty("extensions")
    @JsonAnyGetter
    @JsonAnySetter
    private Map<String, Object> extensions = new HashMap<>();

    /**
     * Returns the problem type.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the problem type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns a short, human-readable summary of the problem type.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets a short, human-readable summary of the problem type.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the HTTP status code.
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Sets the HTTP status code.
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * Returns a human-readable explanation specific to this occurrence of the problem.
     */
    public String getDetail() {
        return detail;
    }

    /**
     * Sets a human-readable explanation specific to this occurrence of the problem.
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * Returns a URI reference that identifies the specific occurrence of the problem.
     */
    public String getInstance() {
        return instance;
    }

    /**
     * Sets a URI reference that identifies the specific occurrence of the problem.
     */
    public void setInstance(String instance) {
        this.instance = instance;
    }

    /**
     * Returns the operation id.
     */
    public String getOperationId() { return operationId; }

    /**
     * Sets the operation id.
     */
    public void setOperationId(String operationId) { this.operationId = operationId; }

    /**
     * Returns the error source.
     */
    public String getErrorSource() { return errorSource; }

    /**
     * Sets the error source.
     */
    public void setErrorSource(String errorSource) { this.errorSource = errorSource; }

    /**
     * Returns the error code.
     */
    public Integer getErrorCode() { return errorCode; }

    /**
     * Sets the error code.
     */
    public void setErrorCode(Integer errorCode) { this.errorCode = errorCode; }

    /**
     * Returns the trace id.
     */
    public String getTraceId() { return traceId; }

    /**
     * Sets the trace id.
     */
    public void setTraceId(String traceId) { this.traceId = traceId; }

    /**
     * Returns the extension members.
     */
    public Map<String, Object> getExtensions() {
        return extensions;
    }

    /**
     * Sets the extension members.
     */
    public void setExtensions(Map<String, Object> extensions) {
        this.extensions = extensions;
    }

    /**
     * Create a minimal {@link ProblemDetails} using HTTP response information.
     * @param statusCode The response status code.
     * @param headers The response headers.
     * @return {@link ProblemDetails}
     */
    public static ProblemDetails create(int statusCode, Map<String, String> headers) {
        ProblemDetails problemDetails = new ProblemDetails();
        problemDetails.setStatus(statusCode);

        String errorMessage = null;
        if (headers != null) {
            problemDetails.setOperationId(headers.getOrDefault(OPERATION_ID_HEADER, null));

            String headerErrorMessage = headers.getOrDefault(API_SERVER_ERROR_MESSAGE_HEADER, null);
            if (headerErrorMessage != null) {
                try {
                    errorMessage = URLDecoder.decode(headerErrorMessage, StandardCharsets.UTF_8.name());
                } catch (UnsupportedEncodingException ignored) {
                    // If exception use default error message
                }
            }
        }
        problemDetails.setTitle(errorMessage == null ? String.format("HTTP status code %s.", statusCode) : errorMessage);

        return problemDetails;
    }
}
