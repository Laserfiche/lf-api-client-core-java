// Copyright (c) Laserfiche
// Licensed under the MIT License. See LICENSE in the project root for license information.
package com.laserfiche.api.client.unit;

import com.laserfiche.api.client.apiserver.TokenClient;
import com.laserfiche.api.client.apiserver.TokenClientImpl;
import com.laserfiche.api.client.httphandlers.*;
import com.laserfiche.api.client.model.CreateConnectionRequest;
import com.laserfiche.api.client.model.SessionKeyInfo;
import kong.unirest.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UsernamePasswordHandlerTest {
    private final String repoId = "repoId";
    private final String username = "username";
    private final String password = "password";
    private final String baseUrl = "http://localhost:11211";
    private final Request request = new RequestImpl();

    private HttpRequestHandler handler;

    @BeforeEach
    void setUpHttpRequestHandler() {
        handler = new UsernamePasswordHandler(repoId, username, password, baseUrl, null);
    }

    @AfterEach
    void tearDownHttpRequestHandler() {
        handler.close();
    }

    @Test
    @Disabled("Throwing a null exception -> will have to figure out how to stub completable future type functions")
    void beforeSendAsync_NewToken_Success() {
        // Arrange
        String accessToken = "access_token";
        SessionKeyInfo mockedResponse = new SessionKeyInfo();
        mockedResponse.setAccessToken(accessToken);
        CreateConnectionRequest mockedBody = mock(CreateConnectionRequest.class);
        TokenClient mockedClient = mock(TokenClientImpl.class);
        when(mockedClient.createAccessToken(eq(anyString()), mockedBody)).thenReturn(mockedResponse);
        HttpRequestHandler handler = new UsernamePasswordHandler(repoId, username, password, baseUrl, mockedClient);

        // Act
        BeforeSendResult result = handler.beforeSend(request);

        // Assert
        assertNotNull(result);
        assertNull(result.getRegionalDomain());
        assertTrue(request
                .headers()
                .get("Authorization")
                .contains("Bearer"));
        assertNotNull(request
                .headers()
                .get("Authorization")
                .substring(6, request
                        .headers()
                        .get("Authorization")
                        .length() - 1));
        verify(mockedClient, times(1)).createAccessToken(anyString(), mockedBody);
    }

    @Test
    void afterSendAsync_ResponseUnauthorized_ReturnsTrue() {
        // Arrange
        Response mockedResponse = mock(Response.class);
        when(mockedResponse.status()).thenReturn((short) 401);

        // Act
        boolean shouldRetry = handler.afterSend(mockedResponse);

        // Assert
        assertTrue(shouldRetry);
    }

    @ParameterizedTest
    @MethodSource("responseOtherThanUnauthorized")
    void afterSendAsync_ResponseOtherThanUnauthorized_ReturnsFalse(int status) {
        // Arrange
        Response mockedResponse = mock(Response.class);
        when(mockedResponse.status()).thenReturn((short) status);

        // Act
        boolean shouldRetry = handler.afterSend(mockedResponse);

        // Assert
        assertFalse(shouldRetry);
    }

    private static Stream<Arguments> responseOtherThanUnauthorized() {
        return Stream.of(arguments(HttpStatus.OK),
                arguments(HttpStatus.NOT_FOUND),
                arguments(HttpStatus.FORBIDDEN),
                arguments(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
