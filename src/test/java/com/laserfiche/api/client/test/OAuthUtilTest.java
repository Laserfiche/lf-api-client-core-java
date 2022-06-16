package com.laserfiche.api.client.test;

import org.junit.jupiter.api.Test;

import static com.laserfiche.api.client.oauth.OAuthUtil.getOAuthApiBaseUri;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OAuthUtilTest {
    @Test
    public void getOAuthApiBaseUri_ReturnDomain() {
        String domain = "domain";
        assertEquals(String.format("https://signin.%s/oauth/", domain), getOAuthApiBaseUri(domain));
    }

    @Test
    public void getOAuthApiBaseUri_RejectsNull() {
        assertThrows(IllegalArgumentException.class, () -> getOAuthApiBaseUri(null));
    }

    @Test
    public void getOAuthApiBaseUri_RejectsEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> getOAuthApiBaseUri(""));
    }
}
