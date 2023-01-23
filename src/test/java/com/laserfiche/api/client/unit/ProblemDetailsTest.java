package com.laserfiche.api.client.unit;

import com.laserfiche.api.client.model.ProblemDetails;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProblemDetailsTest {
    private static final String OPERATION_ID_HEADER = "X-RequestId";
    private static final String API_SERVER_ERROR_MESSAGE_HEADER = "X-APIServer-Error";

    @Test
    void create_WithAllDefaultValuesPopulated() {
        int statusCode = 400;
        String operationId = "The operation id";
        String decodedTitle = "There was an error. [123]";
        String encodedTitle = "There%20was%20an%20error.%20%5B123%5D";
        Map<String, String> headers = new HashMap<>();
        headers.put(OPERATION_ID_HEADER, operationId);
        headers.put(API_SERVER_ERROR_MESSAGE_HEADER, encodedTitle);

        ProblemDetails problemDetails = ProblemDetails.create(statusCode, headers);

        assertEquals(statusCode, problemDetails.getStatus());
        assertEquals(decodedTitle, problemDetails.getTitle());
        assertEquals(operationId, problemDetails.getOperationId());
        assertNull(problemDetails.getType());
        assertNull(problemDetails.getDetail());
        assertNull(problemDetails.getInstance());
        assertNull(problemDetails.getErrorCode());
        assertNull(problemDetails.getErrorSource());
        assertNull(problemDetails.getTraceId());
        assertEquals(0, problemDetails.getExtensions().size());
    }

    @Test
    void create_WithNullHeaderValues() {
        int statusCode = 400;
        Map<String, String> headers = new HashMap<>();
        headers.put(OPERATION_ID_HEADER, null);
        headers.put(API_SERVER_ERROR_MESSAGE_HEADER, null);

        ProblemDetails problemDetails = ProblemDetails.create(statusCode, headers);

        assertEquals(statusCode, problemDetails.getStatus());
        assertEquals(String.format("HTTP status code %s.", statusCode), problemDetails.getTitle());
        assertNull(problemDetails.getOperationId());
        assertNull(problemDetails.getType());
        assertNull(problemDetails.getDetail());
        assertNull(problemDetails.getInstance());
        assertNull(problemDetails.getErrorCode());
        assertNull(problemDetails.getErrorSource());
        assertNull(problemDetails.getTraceId());
        assertEquals(0, problemDetails.getExtensions().size());
    }

    @Test
    void create_WithNoUsefulHeadersProvided() {
        int statusCode = 400;
        Map<String, String> headers = new HashMap<>();
        headers.put("Access-Control-Allow-Origin", "*");

        ProblemDetails problemDetails = ProblemDetails.create(statusCode, headers);

        assertEquals(statusCode, problemDetails.getStatus());
        assertEquals(String.format("HTTP status code %s.", statusCode), problemDetails.getTitle());
        assertNull(problemDetails.getOperationId());
        assertNull(problemDetails.getType());
        assertNull(problemDetails.getDetail());
        assertNull(problemDetails.getInstance());
        assertNull(problemDetails.getErrorCode());
        assertNull(problemDetails.getErrorSource());
        assertNull(problemDetails.getTraceId());
        assertEquals(0, problemDetails.getExtensions().size());
    }

    @Test
    void create_WithNullHeaders() {
        int statusCode = 400;

        ProblemDetails problemDetails = ProblemDetails.create(statusCode, null);

        assertEquals(statusCode, problemDetails.getStatus());
        assertEquals(String.format("HTTP status code %s.", statusCode), problemDetails.getTitle());
        assertNull(problemDetails.getOperationId());
        assertNull(problemDetails.getType());
        assertNull(problemDetails.getDetail());
        assertNull(problemDetails.getInstance());
        assertNull(problemDetails.getErrorCode());
        assertNull(problemDetails.getErrorSource());
        assertNull(problemDetails.getTraceId());
        assertEquals(0, problemDetails.getExtensions().size());
    }
}
