package com.laserfiche.api.client.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.nimbusds.jose.jwk.JWK;

import java.io.IOException;
import java.text.ParseException;

public class JwkDeserializer extends StdDeserializer<JWK> {
    public JwkDeserializer() {
        this(null);
    }

    public JwkDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public JWK deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String jwtText = node.toString();
        try {
            return JWK.parse(jwtText);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
