package com.laserfiche.api.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * A machine-readable format for specifying errors in HTTP API responses based on <a href="https://tools.ietf.org/html/rfc7807">rfc 7807</a>.
 */
public class ProblemDetails extends HashMap<String, Object> {

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

    @JsonProperty("extensions")
    private Map<String, Object> extensions = null;

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
}
