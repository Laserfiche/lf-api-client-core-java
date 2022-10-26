package com.laserfiche.api.client.integration;

import com.laserfiche.api.client.httphandlers.*;
import com.laserfiche.api.client.model.ApiException;
import kong.unirest.HttpStatus;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@Tag("SelfHosted")
public class UsernamePasswordHandlerTest extends BaseTest {
    private HttpRequestHandler _httpRequestHandler;
    private List<String> _accessTokensToCleanUp;

    @Test
    void beforeSendAsync_NewToken_Success() {
        _httpRequestHandler = new UsernamePasswordHandler(repoId, username, password, baseUrl, null);
        Request request = new RequestImpl();

        // Act
        CompletableFuture<BeforeSendResult> future = _httpRequestHandler.beforeSendAsync(request);
        BeforeSendResult result = future.join();

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
    }

    @Test
    void beforeSendAsync_ExistingToken_Success() {
        // Arrange
        _httpRequestHandler = new UsernamePasswordHandler(repoId, username, password, baseUrl, null);
        Request request1 = new RequestImpl();
        Request request2 = new RequestImpl();

        // Act
        BeforeSendResult result1 = _httpRequestHandler
                .beforeSendAsync(request1)
                .join();

        BeforeSendResult result2 = _httpRequestHandler
                .beforeSendAsync(request2)
                .join();

        String bearerTokenParameter1 = request1
                .headers()
                .get("Authorization")
                .substring(6, request1
                        .headers()
                        .get("Authorization")
                        .length() - 1);
        String bearerTokenParameter2 = request2
                .headers()
                .get("Authorization")
                .substring(6, request2
                        .headers()
                        .get("Authorization")
                        .length() - 1);
        // Assert
        assertNotNull(result2);
        assertNull(result2.getRegionalDomain());
        assertTrue(request2
                .headers()
                .get("Authorization")
                .contains("Bearer"));
        assertEquals(bearerTokenParameter1, bearerTokenParameter2);
    }

    @Test
    void afterSendAsync_TokenRemovedWhenUnauthorized() {
        // Arrange
        _httpRequestHandler = new UsernamePasswordHandler(repoId, username, password, baseUrl, null);
        Request request1 = new RequestImpl();
        BeforeSendResult result1 = _httpRequestHandler
                .beforeSendAsync(request1)
                .join();

        // Act
        Boolean retry = _httpRequestHandler
                .afterSendAsync(new ResponseImpl((short) HttpStatus.UNAUTHORIZED))
                .join();

        // Assert
        assertTrue(retry);
        Request request2 = new RequestImpl();
        BeforeSendResult result2 = _httpRequestHandler
                .beforeSendAsync(request2)
                .join();

        String bearerTokenParameter1 = request1
                .headers()
                .get("Authorization")
                .substring(6, request1
                        .headers()
                        .get("Authorization")
                        .length() - 1);
        String bearerTokenParameter2 = request2
                .headers()
                .get("Authorization")
                .substring(6, request2
                        .headers()
                        .get("Authorization")
                        .length() - 1);
        assertNotNull(result2);
        assertNull(result2.getRegionalDomain());
        assertTrue(request2
                .headers()
                .get("Authorization")
                .contains("Bearer"));
        assertTrue(request2
                .headers()
                .get("Authorization")
                .contains("Bearer"));
        assertNotEquals(bearerTokenParameter1, bearerTokenParameter2);
    }

    @ParameterizedTest
    @MethodSource("FailedAuthentication")
    void beforeSendAsync_FailedAuthentication_ThrowsException(String repoId, String username, String password,
            int status) {
        _httpRequestHandler = new UsernamePasswordHandler(repoId, username, password, baseUrl, null);
        Request request = new RequestImpl();
        assertThrows(RuntimeException.class, () -> CompletableFuture.completedFuture(_httpRequestHandler
                .beforeSendAsync(request)
                .join()));
        RuntimeException ex = assertThrows(RuntimeException.class, () -> _httpRequestHandler
                .beforeSendAsync(request)
                .join());
        ApiException exception = (ApiException) ex.getCause();
        assertEquals(status, exception.getStatusCode());
        assertNotNull(exception
                .getProblemDetails().get("type"));
        assertNotNull(exception
                .getProblemDetails().get("title"));
    }

    private static Stream<Arguments> FailedAuthentication() {
        return Stream.of(arguments(repoId, "fake123", password, HttpStatus.UNAUTHORIZED),
                arguments(repoId, username, "fake123", HttpStatus.UNAUTHORIZED),
                arguments("fake123", username, password, HttpStatus.NOT_FOUND));
    }
}
