package com.laserfiche.api.client.integration;

import com.laserfiche.api.client.httphandlers.*;
import kong.unirest.HttpStatus;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("Cloud")
class OAuthClientCredentialsHandlerTest extends BaseTest {
    @Test
    void beforeSendAsync_Success() {
        HttpRequestHandler handler = new OAuthClientCredentialsHandler(spKey, accessKey);
        Request request = new RequestImpl();

        // Request access token
        CompletableFuture<BeforeSendResult> future = handler.beforeSendAsync(request);
        BeforeSendResult result = future.join();

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
        HttpRequestHandler handler = new OAuthClientCredentialsHandler(spKey, accessKey);
        Request request1 = new RequestImpl();

        // First time to request access token
        CompletableFuture<BeforeSendResult> future1 = handler.beforeSendAsync(request1);
        BeforeSendResult result1 = future1.join();

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
        CompletableFuture<BeforeSendResult> future2 = handler.beforeSendAsync(request2);
        BeforeSendResult result2 = future2.join();

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
        HttpRequestHandler handler = new OAuthClientCredentialsHandler(spKey, accessKey);
        Response mockedResponse = mock(Response.class);
        when(mockedResponse.status()).thenReturn((short) 401);

        // Request access token then simulate a 401
        CompletableFuture<BeforeSendResult> tokenFuture = handler.beforeSendAsync(new RequestImpl());
        tokenFuture
                .thenCompose(beforeSendResult -> handler.afterSendAsync(mockedResponse))
                .thenApply((shouldRetry) -> {
                    assertEquals(true, shouldRetry);
                    return null;
                });
    }

    @ParameterizedTest
    @MethodSource("falseAuthentication")
    void afterSendAsync_DoNotRetry(int status) {
        HttpRequestHandler handler = new OAuthClientCredentialsHandler(spKey, accessKey);
        Response mockedResponse = mock(Response.class);
        when(mockedResponse.status()).thenReturn((short) status);
        CompletableFuture<BeforeSendResult> tokenFuture = handler.beforeSendAsync(new RequestImpl());
        tokenFuture
                .thenCompose(beforeSendResult -> handler.afterSendAsync(mockedResponse))
                .thenApply((shouldRetry) -> {
                    assertFalse(shouldRetry);
                    return null;
                });
    }

    private static Stream<Arguments> falseAuthentication() {
        return Stream.of(arguments(HttpStatus.OK),
                arguments(HttpStatus.FORBIDDEN),
                arguments(HttpStatus.NOT_FOUND),
                arguments(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    void afterSendAsync_DoRetry_AccessTokenRemoved() {
        HttpRequestHandler handler = new OAuthClientCredentialsHandler(spKey, accessKey);
        Request request1 = new RequestImpl();
        // Request access token
        CompletableFuture<BeforeSendResult> future = handler.beforeSendAsync(request1);
        BeforeSendResult result = future.join();
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

        //Remove the access token
        Response mockedResponse = mock(Response.class);
        when(mockedResponse.status()).thenReturn((short) 401);
        future
                .thenCompose(beforeSendResult -> handler.afterSendAsync(mockedResponse))
                .thenApply((shouldRetry) -> {
                    assertEquals(true, shouldRetry);
                    return null;
                });

        //Get a new access token
        Request request2 = new RequestImpl();
        CompletableFuture<BeforeSendResult> future2 = handler.beforeSendAsync(request2);
        BeforeSendResult result2 = future2.join();
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
}
