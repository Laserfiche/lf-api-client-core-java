// Copyright (c) Laserfiche.
// Licensed under the MIT License. See LICENSE in the project root for license information.
package com.laserfiche.api.client.unit;

import com.laserfiche.api.client.model.ApiException;
import com.laserfiche.api.client.model.ProblemDetails;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ApiExceptionTest {
    private static final String OPERATION_ID_HEADER = "X-RequestId";
    private static final String API_SERVER_ERROR_MESSAGE_HEADER = "X-APIServer-Error";

    @Test
    void create_WithNullValues() {
        int statusCode = 400;

        ApiException exception = ApiException.create(statusCode, null, null, null);

        assertEquals(statusCode, exception.getStatusCode());
        assertEquals(String.format("HTTP status code %s.", statusCode), exception.getMessage());
        assertNull(exception.getHeaders());
        assertNull(exception.getCause());
        assertNotNull(exception.getProblemDetails());
        assertEquals(exception.getStatusCode(), exception.getProblemDetails().getStatus());
        assertEquals(exception.getMessage(), exception.getProblemDetails().getTitle());
        assertNull(exception.getProblemDetails().getOperationId());
        assertNull(exception.getProblemDetails().getType());
        assertNull(exception.getProblemDetails().getDetail());
        assertNull(exception.getProblemDetails().getInstance());
        assertNull(exception.getProblemDetails().getErrorCode());
        assertNull(exception.getProblemDetails().getErrorSource());
        assertNull(exception.getProblemDetails().getTraceId());
        assertEquals(0, exception.getProblemDetails().getExtensions().size());
    }

    @Test
    void create_WithHeaders() {
        int statusCode = 400;
        String operationId = "The operation id";
        String decodedTitle = "There was an error. [123]";
        String encodedTitle = "There%20was%20an%20error.%20%5B123%5D";
        Map<String, String> headers = new HashMap<>();
        headers.put(OPERATION_ID_HEADER, operationId);
        headers.put(API_SERVER_ERROR_MESSAGE_HEADER, encodedTitle);

        ApiException exception = ApiException.create(statusCode, headers, null, null);

        assertEquals(statusCode, exception.getStatusCode());
        assertEquals(decodedTitle, exception.getMessage());
        assertEquals(headers.size(), exception.getHeaders().size());
        for (String key: headers.keySet()) {
            assertEquals(headers.get(key), exception.getHeaders().get(key));
        }
        assertNull(exception.getCause());
        assertNotNull(exception.getProblemDetails());
        assertEquals(exception.getStatusCode(), exception.getProblemDetails().getStatus());
        assertEquals(exception.getMessage(), exception.getProblemDetails().getTitle());
        assertEquals(operationId, exception.getProblemDetails().getOperationId());
        assertNull(exception.getProblemDetails().getType());
        assertNull(exception.getProblemDetails().getDetail());
        assertNull(exception.getProblemDetails().getInstance());
        assertNull(exception.getProblemDetails().getErrorCode());
        assertNull(exception.getProblemDetails().getErrorSource());
        assertNull(exception.getProblemDetails().getTraceId());
        assertEquals(0, exception.getProblemDetails().getExtensions().size());
    }

    @Test
    void create_WithProblemDetails() {
        int statusCode = 400;
        ProblemDetails problemDetails = new ProblemDetails();
        problemDetails.setStatus(400);
        problemDetails.setTitle("the title");
        problemDetails.setOperationId("the operation id");
        problemDetails.setType("the type");
        problemDetails.setDetail("the detail");
        problemDetails.setInstance("the instance");
        problemDetails.setErrorCode(123);
        problemDetails.setErrorSource("the error source");
        problemDetails.setTraceId("the trace id");
        problemDetails.getExtensions().put("key1", "value1");
        problemDetails.getExtensions().put("key2", "value2");

        ApiException exception = ApiException.create(statusCode, null, problemDetails, null);

        assertEquals(statusCode, exception.getStatusCode());
        assertEquals(problemDetails.getTitle(), exception.getMessage());
        assertNull(exception.getHeaders());
        assertNull(exception.getCause());
        assertNotNull(exception.getProblemDetails());
        assertEquals(problemDetails.getStatus(), exception.getProblemDetails().getStatus());
        assertEquals(problemDetails.getTitle(), exception.getProblemDetails().getTitle());
        assertEquals(problemDetails.getOperationId(), exception.getProblemDetails().getOperationId());
        assertEquals(problemDetails.getType(), exception.getProblemDetails().getType());
        assertEquals(problemDetails.getDetail(), exception.getProblemDetails().getDetail());
        assertEquals(problemDetails.getInstance(), exception.getProblemDetails().getInstance());
        assertEquals(problemDetails.getErrorCode(), exception.getProblemDetails().getErrorCode());
        assertEquals(problemDetails.getErrorSource(), exception.getProblemDetails().getErrorSource());
        assertEquals(problemDetails.getTraceId(), exception.getProblemDetails().getTraceId());
        assertEquals(problemDetails.getExtensions().size(), exception.getProblemDetails().getExtensions().size());
        for (String key: problemDetails.getExtensions().keySet()) {
            assertEquals(problemDetails.getExtensions().get(key), exception.getProblemDetails().getExtensions().get(key));
        }
    }

    @Test
    void create_WithCause() {
        int statusCode = 400;
        Throwable cause = new RuntimeException("There was an error.");

        ApiException exception = ApiException.create(statusCode, null, null, cause);

        assertEquals(statusCode, exception.getStatusCode());
        assertEquals(String.format("HTTP status code %s.", statusCode), exception.getMessage());
        assertNull(exception.getHeaders());
        assertEquals(cause, exception.getCause());
        assertNotNull(exception.getProblemDetails());
        assertEquals(exception.getStatusCode(), exception.getProblemDetails().getStatus());
        assertEquals(exception.getMessage(), exception.getProblemDetails().getTitle());
        assertNull(exception.getProblemDetails().getOperationId());
        assertNull(exception.getProblemDetails().getType());
        assertNull(exception.getProblemDetails().getDetail());
        assertNull(exception.getProblemDetails().getInstance());
        assertNull(exception.getProblemDetails().getErrorCode());
        assertNull(exception.getProblemDetails().getErrorSource());
        assertNull(exception.getProblemDetails().getTraceId());
        assertEquals(0, exception.getProblemDetails().getExtensions().size());
    }
}
