package com.laserfiche.api.client.oauth;

import com.laserfiche.api.client.model.AccessKey;
import com.laserfiche.api.client.model.GetAccessTokenResponse;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.jwk.ECKey;

import java.util.Date;
import java.util.concurrent.CompletableFuture;


public class TokenApiImpl implements TokenApiClient {
    // TODO: rename class name
    private static String payloadTemplate = "{ \"client_id\": \"%s\", \"client_secret\": \"%s\", \"aud\": \"laserfiche.com\", \"exp\": %d, \"iat\": %d, \"nbf\": %d}";
    private TokenApi generatedClient;

    public TokenApiImpl(String regionalDomain) {
        String baseAddress = getOAuthApiBaseUri(regionalDomain);
        generatedClient = new TokenApi();
        generatedClient.getApiClient().setBasePath(baseAddress);
    }

    @Override
    public CompletableFuture<GetAccessTokenResponse> getAccessTokenAsync(String spKey, AccessKey accessKey) throws ApiException {
        CompletableFuture<GetAccessTokenResponse> future = new CompletableFuture<>();
        future.complete(getAccessToken(spKey, accessKey));
        return future;
    }

    private GetAccessTokenResponse getAccessToken(String spKey, AccessKey accessKey) throws ApiException {
        String bearer = createBearer(spKey, accessKey);
        return generatedClient.tokenGetAccessToken(null, "client_credentials", null, null, null, null, null, bearer);
    }

    private static String getOAuthApiBaseUri(String domain)
    {
        if (domain == null || domain.equals("")) {
            throw new IllegalArgumentException("domain");
        }
        return String.format("https://signin.%s/oauth", domain);
    }

    private static String createBearer(String spKey, AccessKey accessKey) {
        // Prepare JWK
        ECKey jwk = accessKey.getJwk().toECKey();

        // Prepare JWS
        JWSObject jws = createJws(jwk, spKey, accessKey);

        // Sign
        try {
            sign(jws, jwk);
        } catch (JOSEException e) {
            // If the EC JWK doesn't contain a private part, its extraction failed,
            // or the elliptic curve is not supported.
            return null;
        }

        // Generate bearer
        return "Bearer " + jws.serialize();
    }

    private static JWSObject createJws(ECKey jwk, String spKey, AccessKey accessKey) {
        long now = new Date().getTime() / 1000;
        JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.ES256).keyID(jwk.getKeyID()).type(JOSEObjectType.JWT).build();
        // The token will be valid for 30 minutes
        Payload jwsPayload = new Payload(String.format(payloadTemplate, accessKey.getClientId(), spKey, now + 1800, now, now));
        return new JWSObject(jwsHeader, jwsPayload);
    }

    private static void sign(JWSObject jws, ECKey jwk) throws JOSEException {
        JWSSigner signer = new ECDSASigner(jwk);
        jws.sign(signer);
    }
}
