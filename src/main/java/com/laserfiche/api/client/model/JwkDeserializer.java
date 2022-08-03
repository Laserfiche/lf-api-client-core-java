package com.laserfiche.api.client.model;

import com.google.gson.*;
import com.nimbusds.jose.jwk.JWK;

import java.lang.reflect.Type;
import java.text.ParseException;

public class JwkDeserializer implements JsonDeserializer<JWK> {
    @Override
    public JWK deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String jwtText = jsonObject.toString();
        try {
            return JWK.parse(jwtText);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
