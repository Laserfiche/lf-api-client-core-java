package com.laserfiche.api.client.oauth;

import com.laserfiche.api.client.model.AccessKey;
import com.laserfiche.api.client.model.GetAccessTokenResponse;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.jwk.ECKey;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CompletableFuture;


public class TokenClientImpl implements TokenClient {
    private OAuthClient client;

    public TokenClientImpl(String regionalDomain) {
        String baseAddress = getOAuthApiBaseUri(regionalDomain);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseAddress)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        client = retrofit.create(OAuthClient.class);
    }

    @Override
    public CompletableFuture<GetAccessTokenResponse> getAccessTokenFromServicePrincipal(String spKey, AccessKey accessKey) {
        String bearer = createBearer(spKey, accessKey);
        CompletableFuture<GetAccessTokenResponse> future = CompletableFuture.supplyAsync(() -> {
            Call<GetAccessTokenResponse> call = client.getAccessToken("client_credentials", bearer);
            Response<GetAccessTokenResponse> response = null;
            try {
                response = call.execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return response.body();
        });
        return future;
    }

    @Override
    public CompletableFuture<GetAccessTokenResponse> getAccessTokenFromCode(String code, String redirectUri, String clientId, String codeVerifier) {
        return null;
    }

    @Override
    public CompletableFuture<GetAccessTokenResponse> refreshAccessToken(String refreshToken, String clientId) {
        return null;
    }

    private static String getOAuthApiBaseUri(String domain)
    {
        if (domain == null || domain.equals("")) {
            throw new IllegalArgumentException("domain");
        }
        return String.format("https://signin.%s/oauth/", domain);
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
        String payloadTemplate = "{ \"client_id\": \"%s\", \"client_secret\": \"%s\", \"aud\": \"laserfiche.com\", \"exp\": %d, \"iat\": %d, \"nbf\": %d}";
        Payload jwsPayload = new Payload(String.format(payloadTemplate, accessKey.getClientId(), spKey, now + 1800, now, now));
        return new JWSObject(jwsHeader, jwsPayload);
    }

    private static void sign(JWSObject jws, ECKey jwk) throws JOSEException {
        JWSSigner signer = new ECDSASigner(jwk);
        jws.sign(signer);
    }
}
