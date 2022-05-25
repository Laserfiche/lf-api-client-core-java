package com.laserfiche.api.client.oauth;

import com.laserfiche.api.client.ApiException;
import com.laserfiche.api.client.model.AccessKey;
import com.laserfiche.api.client.model.GetAccessTokenResponse;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.jwk.ECKey;

import java.util.Date;


public class TokenApiImpl implements TokenApiClient {
    private static String payloadTemplate = "{ \"client_id\": \"%s\", \"client_secret\": \"%s\", \"aud\": \"laserfiche.com\", \"exp\": %d, \"iat\": %d, \"nbf\": %d}";
    private TokenApi generatedClient;

    public TokenApiImpl() {
        generatedClient = new TokenApi();
    }

    @Override
    public GetAccessTokenResponse getAccessToken(String spKey, AccessKey accessKey) throws ApiException {
        String bearer = createBearer(spKey, accessKey);
        return generatedClient.tokenGetAccessToken(null, "client_credentials", null, null, null, null, null, bearer);
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
        long now = new Date().getTime();
        JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.ES256).keyID(jwk.getKeyID()).type(JOSEObjectType.JWT).build();
        Payload jwsPayload = new Payload(String.format(payloadTemplate, accessKey.getClientId(), spKey, now / 1000 + 3600, now, now));
        return new JWSObject(jwsHeader, jwsPayload);
    }

    private static void sign(JWSObject jws, ECKey jwk) throws JOSEException {
        JWSSigner signer = new ECDSASigner(jwk);
        jws.sign(signer);
    }
}
