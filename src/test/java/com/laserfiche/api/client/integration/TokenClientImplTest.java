package com.laserfiche.api.client.integration;

import com.laserfiche.api.client.model.GetAccessTokenResponse;
import com.laserfiche.api.client.oauth.TokenClient;
import com.laserfiche.api.client.oauth.TokenClientImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Cloud")
class TokenClientImplTest extends BaseTest {
    private TokenClient client;

    @BeforeEach
    void setUpHttpRequestHandler() {
        client = new TokenClientImpl(accessKey.getDomain());
    }

    @AfterEach
    void tearDownHttpRequestHandler() {
        client.close();
    }

    @Test
    void getAccessTokenFromServicePrincipal_Success() {
        GetAccessTokenResponse response = client.getAccessTokenFromServicePrincipal(servicePrincipalKey, accessKey);

        assertNotEquals(null, response);
        assertNotEquals(null, response.getAccessToken());
    }

    @Test
    void getAccessTokenFromServicePrincipal_InvalidAccessKey() {
        accessKey.setClientId("wrong client ID");

        Exception exception = assertThrows(RuntimeException.class,
                () -> client.getAccessTokenFromServicePrincipal(servicePrincipalKey, accessKey));
        assertNotNull(exception);
    }

    @Test
    void getAccessTokenFromServicePrincipal_IoError() {
        String incorrectDomain = accessKey
                .getDomain()
                .replace("laserfiche", "lf");

        try (TokenClient client = new TokenClientImpl(incorrectDomain)) {
            Exception exception = assertThrows(RuntimeException.class,
                    () -> client.getAccessTokenFromServicePrincipal(servicePrincipalKey, accessKey));
            assertTrue(IOException.class.isAssignableFrom(exception
                    .getCause()
                    .getClass()));
        }
    }
}
