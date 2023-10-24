// Copyright (c) Laserfiche.
// Licensed under the MIT License. See LICENSE in the project root for license information.
package com.laserfiche.api.client.deserialization;

import com.laserfiche.api.client.model.ProblemDetails;
import kong.unirest.ObjectMapper;

/**
 * Helper class containing for deserializing {@link ProblemDetails}.
 */
public class ProblemDetailsDeserializer {
    private ProblemDetailsDeserializer() {
        throw new IllegalStateException("Utility class with all static methods are not meant to be instantiated.");
    }

    /**
     * Deserializes a json string into a {@link ProblemDetails}.
     * @param objectMapper The object mapper used to create the {@link ProblemDetails}.
     * @param jsonString The json string that will be deserialized.
     * @return {@link ProblemDetails}
     */
    public static ProblemDetails deserialize(ObjectMapper objectMapper, String jsonString) {
        ProblemDetails problemDetails = null;
        if (objectMapper != null && jsonString != null)
            problemDetails = objectMapper.readValue(jsonString, ProblemDetails.class);
        if (problemDetails == null || problemDetails.getTitle() == null || problemDetails.getStatus() == null)
            throw new RuntimeException(
                    String.format("Unable to deserialize to %s: %s", ProblemDetails.class.getName(), jsonString));
        return problemDetails;
    }
}
