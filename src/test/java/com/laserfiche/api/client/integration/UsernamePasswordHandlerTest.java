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

@Tag("SelfHosted")
public class UsernamePasswordHandlerTest extends BaseTest {
    private HttpRequestHandler httpRequestHandler;

    @BeforeEach
    void setUpHttpRequestHandler() {
        httpRequestHandler = new UsernamePasswordHandler(repositoryId, username, password, baseUrl, null);
    }

    @AfterEach
    void tearDownHttpRequestHandler() {
        httpRequestHandler.close();
    }

    @Test
    void beforeSendAsync_NewToken_Success() {
        Request request = new RequestImpl();

        // Act
        BeforeSendResult result = httpRequestHandler.beforeSend(request);

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
        Request request1 = new RequestImpl();
        Request request2 = new RequestImpl();

        // Act
        httpRequestHandler.beforeSend(request1);
        BeforeSendResult result = httpRequestHandler.beforeSend(request2);

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
        assertNotNull(result);
        assertNull(result.getRegionalDomain());
        assertTrue(request2
                .headers()
                .get("Authorization")
                .contains("Bearer"));
        assertNotNull(bearerTokenParameter2);
        assertEquals(bearerTokenParameter1, bearerTokenParameter2);
    }

    @Test
    void afterSendAsync_TokenRemovedWhenUnauthorized() {
        // Arrange
        Request request1 = new RequestImpl();
        Request request2 = new RequestImpl();
        httpRequestHandler.beforeSend(request1);

        // Act
        boolean shouldRetry = httpRequestHandler.afterSend(new ResponseImpl((short) HttpStatus.UNAUTHORIZED));

        // Assert
        assertTrue(shouldRetry);
        BeforeSendResult result2 = httpRequestHandler.beforeSend(request2);

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
        assertNotNull(bearerTokenParameter2);
        assertNotEquals(bearerTokenParameter1, bearerTokenParameter2);
    }

    @ParameterizedTest
    @MethodSource("failedAuthentication")
    void beforeSendAsync_FailedAuthentication_ThrowsException(String repoId, String username, String password,
            int status) {
        try (HttpRequestHandler badHandler = new UsernamePasswordHandler(repoId, username, password, baseUrl, null)) {
            Request request = new RequestImpl();
            assertThrows(RuntimeException.class, () -> badHandler.beforeSend(request));

            RuntimeException ex = assertThrows(RuntimeException.class, () -> badHandler.beforeSend(request));

            ApiException exception = (ApiException) ex;
            assertEquals(status, exception.getStatusCode());
            assertNotNull(exception
                    .getProblemDetails()
                    .getType());
            assertNotNull(exception
                    .getProblemDetails()
                    .getTitle());
        }
    }

    private static Stream<Arguments> failedAuthentication() {
        return Stream.of(arguments(repositoryId, "fake123", password, HttpStatus.UNAUTHORIZED),
                arguments(repositoryId, username, "fake123", HttpStatus.UNAUTHORIZED),
                arguments("fake123", username, password, HttpStatus.NOT_FOUND));
    }
}
