package com.laserfiche.api.client.integration;

import com.google.gson.InstanceCreator;
import com.nimbusds.jose.jwk.JWK;

import java.lang.reflect.Type;
import java.text.ParseException;

public class JwkInstanceCreator implements InstanceCreator<JWK> {

    @Override
    public JWK createInstance(Type type) {
        try {
            // Pass the dummy information to get an instance of JWK
            return JWK.parse("{\"kty\":\"EC\",\"crv\":\"P-256\",\"use\":\"sig\",\"kid\":\"1\",\"x\":\"MKBCTNIcKUSDii11ySs3526iDZ8AiTo7Tu6KPAqv7D4\",\"y\":\"4Etl6SRW2YiLUrN5vfvVHuhp7x8PxltmWWlbbM4IFyM\"}");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
