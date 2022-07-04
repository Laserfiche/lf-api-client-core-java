package com.laserfiche.api.client.unit;

import com.laserfiche.api.client.integration.BaseTest;
import org.junit.jupiter.api.Test;

import static com.laserfiche.api.client.oauth.OAuthUtil.createBearer;
import static com.laserfiche.api.client.oauth.OAuthUtil.getOAuthApiBaseUri;
import static org.junit.jupiter.api.Assertions.*;

public class OAuthUtilTest extends BaseTest {
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

    @Test
    public void createBearer_ReturnsCorrectJwt() {
        String jwt = createBearer(spKey, accessKey);

        assertNotNull(jwt);

        String[] parts = jwt.split("[ .]");

        assertNotNull(parts);
        assertEquals(4, parts.length);
        assertNotNull(parts[0]);
        assertNotNull(parts[1]);
        assertNotNull(parts[2]);
        assertNotNull(parts[3]);
        assertEquals("Bearer", parts[0]);
        assertTrue(parts[1].length() > 0);
        assertTrue(parts[2].length() > 0);
        assertTrue(parts[3].length() > 0);
    }
}
