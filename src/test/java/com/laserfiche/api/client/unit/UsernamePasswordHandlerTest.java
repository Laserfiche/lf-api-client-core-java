package com.laserfiche.api.client.unit;

import com.laserfiche.api.client.model.SessionKeyInfo;
import com.laserfiche.api.client.APIServer.TokenClient;
import com.laserfiche.api.client.APIServer.TokenClientImpl;
import com.laserfiche.api.client.httphandlers.*;
import com.laserfiche.api.client.integration.BaseTest;
import com.laserfiche.api.client.model.CreateConnectionRequest;
import kong.unirest.HttpStatus;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UsernamePasswordHandlerTest extends BaseTest {
    private Request _request = new RequestImpl();

    @Test
    @Disabled("Throwing a null exception -> will have to figure out how to stub completable future type functions")
    void beforeSendAsync_NewToken_Success() {
        // Arrange
        String accessToken = "access_token";
        SessionKeyInfo mockedResponse = new SessionKeyInfo();
        mockedResponse.setAccessToken(accessToken);
        CreateConnectionRequest mockedBody = mock(CreateConnectionRequest.class);
        TokenClient mockedClient = mock(TokenClientImpl.class);
        when(mockedClient.createAccessToken(eq(anyString()), mockedBody)).thenReturn(
                CompletableFuture.completedFuture(mockedResponse));
        HttpRequestHandler handler = new UsernamePasswordHandler(repoId, username, password, baseUrl, mockedClient);

        //Act
        BeforeSendResult result = handler
                .beforeSendAsync(_request)
                .join();

        //Assert
        assertNotNull(result);
        assertNull(result.getRegionalDomain());
        assertTrue(_request
                .headers()
                .get("Authorization")
                .contains("Bearer"));
        assertNotNull(_request
                .headers()
                .get("Authorization")
                .substring(6, _request
                        .headers()
                        .get("Authorization")
                        .length() - 1));
        verify(mockedClient, times(1)).createAccessToken(anyString(), mockedBody);
    }

    @Test
    void afterSendAsync_ResponseUnauthorized_ReturnsTrue() {
        // Arrange
        HttpRequestHandler handler = new UsernamePasswordHandler(repoId, username, password, baseUrl, null);
        Response mockedResponse = mock(Response.class);
        when(mockedResponse.status()).thenReturn((short) 401);

        // Act
        handler
                .afterSendAsync(mockedResponse)
                .thenApply((shouldRetry) -> {
                    // Assert
                    assertEquals(true, shouldRetry);
                    return null;
                });
    }

    @ParameterizedTest
    @MethodSource("ResponseOtherThanUnauthorized")
    void afterSendAsync_ResponseOtherThanUnauthorized_ReturnsFalse(int status) {
        // Arrange
        HttpRequestHandler handler = new UsernamePasswordHandler(repoId, username, password, baseUrl, null);
        Response mockedResponse = mock(Response.class);
        when(mockedResponse.status()).thenReturn((short) status);

        // Act
        handler
                .afterSendAsync(mockedResponse)
                .thenApply((shouldRetry) -> {
                    // Assert
                    assertEquals(false, shouldRetry);
                    return null;
                });
    }

    private static Stream<Arguments> ResponseOtherThanUnauthorized() {
        return Stream.of(arguments(HttpStatus.OK),
                arguments(HttpStatus.NOT_FOUND),
                arguments(HttpStatus.FORBIDDEN),
                arguments(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
