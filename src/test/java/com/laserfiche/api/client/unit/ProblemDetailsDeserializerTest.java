// Copyright (c) Laserfiche
// Licensed under the MIT License. See LICENSE in the project root for license information.
package com.laserfiche.api.client.unit;

import com.laserfiche.api.client.deserialization.ProblemDetailsDeserializer;
import com.laserfiche.api.client.deserialization.TokenClientObjectMapper;
import com.laserfiche.api.client.model.ProblemDetails;
import kong.unirest.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProblemDetailsDeserializerTest {
    @Test
    void deserialize_JsonWithOnlyRequiredProblemDetailsProperties() {
        ObjectMapper objectMapper = new TokenClientObjectMapper();
        String title = "the title";
        int status = 400;
        String json = String.format("{\"title\":\"%s\",\"status\":%s}", title, status);

        ProblemDetails problemDetails = ProblemDetailsDeserializer.deserialize(objectMapper, json);

        assertEquals(status, problemDetails.getStatus());
        assertEquals(title, problemDetails.getTitle());
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
    void deserialize_JsonWithAllDocumentedProblemDetailsProperties() {
        ObjectMapper objectMapper = new TokenClientObjectMapper();
        String type = "the type";
        String title = "the title";
        int status = 400;
        String detail = "the detail";
        String instance = "the instance";
        String operationId = "the operation id";
        String errorSource = "the error source";
        int errorCode = 123;
        String traceId = "the trace id";
        String json = String.format(
                "{\"type\":\"%s\",\"title\":\"%s\",\"status\":%s,\"detail\":\"%s\",\"instance\":\"%s\",\"operationId\":\"%s\",\"errorSource\":\"%s\",\"errorCode\":%s,\"traceId\":\"%s\"}",
                type, title, status, detail, instance, operationId, errorSource, errorCode, traceId);

        ProblemDetails problemDetails = ProblemDetailsDeserializer.deserialize(objectMapper, json);

        assertEquals(status, problemDetails.getStatus());
        assertEquals(title, problemDetails.getTitle());
        assertEquals(operationId, problemDetails.getOperationId());
        assertEquals(type, problemDetails.getType());
        assertEquals(detail, problemDetails.getDetail());
        assertEquals(instance, problemDetails.getInstance());
        assertEquals(errorCode, problemDetails.getErrorCode());
        assertEquals(errorSource, problemDetails.getErrorSource());
        assertEquals(traceId, problemDetails.getTraceId());
        assertEquals(0, problemDetails.getExtensions().size());
    }

    @Test
    void deserialize_JsonWithUndocumentedProperties() {
        ObjectMapper objectMapper = new TokenClientObjectMapper();
        String title = "the title";
        int status = 400;
        String undocumentedPropertyKey = "key";
        String undocumentedPropertyValue = "value";
        String json = String.format("{\"title\":\"%s\",\"status\":%s,\"%s\":\"%s\"}", title, status,
                undocumentedPropertyKey, undocumentedPropertyValue);

        ProblemDetails problemDetails = ProblemDetailsDeserializer.deserialize(objectMapper, json);

        assertEquals(status, problemDetails.getStatus());
        assertEquals(title, problemDetails.getTitle());
        assertNull(problemDetails.getOperationId());
        assertNull(problemDetails.getType());
        assertNull(problemDetails.getDetail());
        assertNull(problemDetails.getInstance());
        assertNull(problemDetails.getErrorCode());
        assertNull(problemDetails.getErrorSource());
        assertNull(problemDetails.getTraceId());
        assertEquals(1, problemDetails.getExtensions().size());
        assertEquals(undocumentedPropertyValue, problemDetails.getExtensions().get(undocumentedPropertyKey));
    }

    @Test
    void deserialize_NullObjectMapper() {
        String title = "the title";
        int status = 400;
        String json = String.format("{\"title\":\"%s\",\"status\":%s}", title, status);

        assertThrows(RuntimeException.class, () -> {
            ProblemDetailsDeserializer.deserialize(null, json);
        });
    }

    @Test
    void deserialize_NullJson() {
        ObjectMapper objectMapper = new TokenClientObjectMapper();

        assertThrows(RuntimeException.class, () -> {
            ProblemDetailsDeserializer.deserialize(objectMapper, null);
        });
    }

    @Test
    void deserialize_JsonMissingRequiredProblemDetailsProperties() {
        ObjectMapper objectMapper = new TokenClientObjectMapper();
        String json = "{\"value\":\"123\"}";

        assertThrows(RuntimeException.class, () -> {
            ProblemDetailsDeserializer.deserialize(objectMapper, json);
        });
    }

    @Test
    void deserialize_ProvidedStringIsNotJsonFormat() {
        ObjectMapper objectMapper = new TokenClientObjectMapper();
        String json = "123";

        assertThrows(RuntimeException.class, () -> {
            ProblemDetailsDeserializer.deserialize(objectMapper, json);
        });
    }
}
