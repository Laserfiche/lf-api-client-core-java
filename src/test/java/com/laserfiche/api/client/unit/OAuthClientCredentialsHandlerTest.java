package com.laserfiche.api.client.unit;

import com.laserfiche.api.client.httphandlers.HttpRequestHandler;
import com.laserfiche.api.client.httphandlers.OAuthClientCredentialsHandler;
import com.laserfiche.api.client.httphandlers.Response;
import com.laserfiche.api.client.integration.BaseTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OAuthClientCredentialsHandlerTest extends BaseTest {
    @Test
    void afterSendAsync_ShouldNotRetry() {
        // Arrange
        try (HttpRequestHandler handler = new OAuthClientCredentialsHandler(spKey, accessKey)) {
            Response mockedResponse = mock(Response.class);
            when(mockedResponse.status()).thenReturn((short)200);

            // Act
            handler.afterSend(mockedResponse).thenApply((shouldRetry) -> {
                // Assert
                assertEquals(false, shouldRetry);
                return null;
            });
        }
    }
}
