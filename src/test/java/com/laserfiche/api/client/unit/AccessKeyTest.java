package com.laserfiche.api.client.unit;

import com.laserfiche.api.client.model.AccessKey;
import com.nimbusds.jose.jwk.JWK;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class AccessKeyTest {

    @Test
    void CreateFromBase64EncodedAccessKey_ValidBase64() throws Exception {
        // Arrange
        AccessKey expectedDecodedAccessKey = new AccessKey();
        expectedDecodedAccessKey.setClientId("V5gqHxkzihZKdQTSc6DFYnkd");
        expectedDecodedAccessKey.setCustomerId("7215189634");
        expectedDecodedAccessKey.setDomain("laserfiche.ca");
        String jwkJson = "{\"kty\": \"EC\",\"crv\": \"P-256\", \"use\": \"sig\", \"kid\": \"_pk_xM5VCqEND6OULr_DNYs-GegAUJwLBP9lyFenAMh\",\"x\": \"6oZILnV7ytZPB1uz2P47_a_Ymko7SmTNuGpnzl20iCs\", \"y\": \"ZQorDAQqhY6ojHSV_dpzXxbKI0eKljZbeGQKYDfPHsE\", \"d\": \"B1oAZHCPP2Ic03fhRuXVKQpEpQdM5bqqbK7iKQU-4Uh\"}";
        expectedDecodedAccessKey.setJwk(JWK.parse(jwkJson));
        String base64EncodedAccessKey = "eyJjdXN0b21lcklkIjoiNzIxNTE4OTYzNCIsImRvbWFpbiI6Imxhc2VyZmljaGUuY2EiLCJjbGllbnRJZCI6IlY1Z3FIeGt6aWhaS2RRVFNjNkRGWW5rZCIsImp3ayI6eyJrdHkiOiJFQyIsImNydiI6IlAtMjU2IiwidXNlIjoic2lnIiwia2lkIjoiX3BrX3hNNVZDcUVORDZPVUxyX0ROWXMtR2VnQVVKd0xCUDlseUZlbkFNaCIsIngiOiI2b1pJTG5WN3l0WlBCMXV6MlA0N19hX1lta283U21UTnVHcG56bDIwaUNzIiwieSI6IlpRb3JEQVFxaFk2b2pIU1ZfZHB6WHhiS0kwZUtsalpiZUdRS1lEZlBIc0UiLCJkIjoiQjFvQVpIQ1BQMkljMDNmaFJ1WFZLUXBFcFFkTTVicXFiSzdpS1FVLTRVaCJ9fQ==";

        // Act
        AccessKey decodedAccessKey = AccessKey.createFromBase64EncodedAccessKey(base64EncodedAccessKey);

        // Assert
        assertEquals(expectedDecodedAccessKey.getDomain(), decodedAccessKey.getDomain());
        assertEquals(expectedDecodedAccessKey.getJwk(), decodedAccessKey.getJwk());
        assertEquals(expectedDecodedAccessKey.getClientId(), decodedAccessKey.getClientId());
        assertEquals(expectedDecodedAccessKey.getCustomerId(), decodedAccessKey.getCustomerId());
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "", "\n", "\t"})
    void CreateFromBase64EncodedAccessKey_IllegalArgumentExceptionThrown(String base64EncodedAccessKey) {
        assertThrows(IllegalArgumentException.class, () -> AccessKey.createFromBase64EncodedAccessKey(base64EncodedAccessKey));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> AccessKey.createFromBase64EncodedAccessKey(base64EncodedAccessKey));
        assertEquals("Input cannot be empty or null", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"YXNkYXNkYXNkYXNkYWQ=", "你好你好", "\uD83D\uDE00 \uD83D\uDE03 \uD83D\uDE04 \uD83D\uDE01"})
    void CreateFromBase64EncodedAccessKey_RunTimeExceptionThrown(String base64EncodedAccessKey) {
        assertThrows(RuntimeException.class, () -> AccessKey.createFromBase64EncodedAccessKey(base64EncodedAccessKey));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> AccessKey.createFromBase64EncodedAccessKey(base64EncodedAccessKey));
        assertTrue(Objects.equals(exception.getMessage(), "Illegal base64 character 3f") || exception.getMessage().contains("Unrecognized token") || Objects.equals(exception.getMessage(), "Input cannot be empty or null"));
    }
}
