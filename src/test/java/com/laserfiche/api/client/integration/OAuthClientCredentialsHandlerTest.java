package com.laserfiche.api.client.integration;

import com.laserfiche.api.client.httphandlers.*;
import com.laserfiche.api.client.model.ApiException;
import kong.unirest.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("Cloud")
class OAuthClientCredentialsHandlerTest extends BaseTest {
    private HttpRequestHandler handler;

    @BeforeEach
    void setUpHttpRequestHandler() {
        handler = new OAuthClientCredentialsHandler(servicePrincipalKey, accessKey);
    }

    @AfterEach
    void tearDownHttpRequestHandler() {
        handler.close();
    }

    @Test
    void beforeSendAsync_Success() {
        Request request = new RequestImpl();

        // Request access token
        BeforeSendResult result = handler.beforeSend(request);

        assertNotEquals(null, result.getRegionalDomain());
        assertNotEquals(null, request
                .headers()
                .get("Authorization"));
        assertNotEquals("", request
                .headers()
                .get("Authorization"));
    }

    @Test
    void beforeSendAsync_CallTwiceShouldStillSucceed() {
        Request request1 = new RequestImpl();

        // First time to request access token
        BeforeSendResult result1 = handler.beforeSend(request1);

        // First request should work
        assertNotEquals(null, result1.getRegionalDomain());
        assertNotEquals(null, request1
                .headers()
                .get("Authorization"));
        assertNotEquals("", request1
                .headers()
                .get("Authorization"));

        // Subsequent request should also work
        Request request2 = new RequestImpl();
        BeforeSendResult result2 = handler.beforeSend(request2);

        assertNotEquals(null, result2.getRegionalDomain());
        assertNotEquals(null, request2
                .headers()
                .get("Authorization"));
        assertNotEquals("", request2
                .headers()
                .get("Authorization"));
        assertEquals(request1
                .headers()
                .get("Authorization"), request2
                .headers()
                .get("Authorization"));
    }

    @Test
    void afterSendAsync_ShouldRetry() {
        Response mockedResponse = mock(Response.class);
        when(mockedResponse.status()).thenReturn((short) 401);

        // Request access token then simulate a 401
        handler.beforeSend(new RequestImpl());
        boolean shouldRetry = handler.afterSend(mockedResponse);

        assertTrue(shouldRetry);
    }

    @ParameterizedTest
    @MethodSource("falseAuthentication")
    void afterSendAsync_DoNotRetry(int status) {
        Response mockedResponse = mock(Response.class);
        when(mockedResponse.status()).thenReturn((short) status);

        handler.beforeSend(new RequestImpl());
        boolean shouldRetry = handler.afterSend(mockedResponse);

        assertFalse(shouldRetry);
    }

    private static Stream<Arguments> falseAuthentication() {
        return Stream.of(arguments(HttpStatus.OK),
                arguments(HttpStatus.FORBIDDEN),
                arguments(HttpStatus.NOT_FOUND),
                arguments(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    void afterSendAsync_DoRetry_AccessTokenRemoved() {
        Request request1 = new RequestImpl();

        // Request access token
        BeforeSendResult result = handler.beforeSend(request1);
        String bearerTokenParameter1 = request1
                .headers()
                .get("Authorization")
                .substring(6, request1
                        .headers()
                        .get("Authorization")
                        .length() - 1);
        assertTrue(request1
                .headers()
                .get("Authorization")
                .contains("Bearer"));
        assertNotNull(bearerTokenParameter1);
        assertEquals(accessKey.getDomain(), result.getRegionalDomain());

        // Remove the access token
        Response mockedResponse = mock(Response.class);
        when(mockedResponse.status()).thenReturn((short) 401);
        boolean shouldRetry = handler.afterSend(mockedResponse);
        assertTrue(shouldRetry);

        //Get a new access token
        Request request2 = new RequestImpl();
        BeforeSendResult result2 = handler.beforeSend(request2);
        String bearerTokenParameter2 = request2
                .headers()
                .get("Authorization")
                .substring(6, request1
                        .headers()
                        .get("Authorization")
                        .length() - 1);
        assertTrue(request2
                .headers()
                .get("Authorization")
                .contains("Bearer"));
        assertNotNull(bearerTokenParameter2);
        assertEquals(accessKey.getDomain(), result2.getRegionalDomain());
        assertNotEquals(bearerTokenParameter1, bearerTokenParameter2);
    }

    @Test
    void beforeSendAsync_FailedAuthentication_ThrowsException() {
        try (HttpRequestHandler handler = new OAuthClientCredentialsHandler("fake1243", accessKey)) {
            Request request = new RequestImpl();
            assertThrows(RuntimeException.class, () -> handler.beforeSend(request));
            RuntimeException ex = assertThrows(RuntimeException.class, () -> handler.beforeSend(request));
            ApiException exception = (ApiException) ex;
            assertEquals(401, exception.getStatusCode());
            assertNotNull(exception
                    .getProblemDetails()
                    .get("type"));
            assertNotNull(exception
                    .getProblemDetails()
                    .get("title"));
        }
    }
}
