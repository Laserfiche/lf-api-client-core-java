package com.laserfiche.api.client.oauth;

import com.laserfiche.api.client.model.AccessKey;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.jwk.ECKey;

import java.util.Base64;
import java.util.Date;

/**
 * Helper class containing utility functions for OAuth.
 */
public class OAuthUtil {
    private OAuthUtil() {
        throw new IllegalStateException("Utility class with all static methods are not meant to be instantiated.");
    }

    /**
     * Given a Laserfiche domain, such as laserfiche.ca, returns the base URL for OAuth.
     *
     * @param domain The Laserfiche domain, for example, laserfiche.ca.
     * @return Full base URL for OAuth.
     */
    public static String getOAuthApiBaseUri(String domain) {
        if (domain == null || domain.equals("")) {
            throw new IllegalArgumentException("domain");
        }
        return String.format("https://signin.%s/oauth/", domain);
    }

    /**
     * Given a service principal key and an access key, return a string representation of the Bearer header. In the form
     * of "Bearer xxxxxx".
     *
     * @param servicePrincipalKey The service principal key created for the service principal from the Laserfiche Account Administration.
     * @param accessKey The access key exported from the Laserfiche Developer Console.
     * @return Bearer header.
     */
    public static String createBearer(String servicePrincipalKey, AccessKey accessKey) {
        // Prepare JWK
        ECKey jwk = accessKey.getJwk().toECKey();

        // Prepare JWS
        JWSObject jws = createJws(jwk, servicePrincipalKey, accessKey);

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

    /**
     * Convert a base64 encoded string to plaintext
     *
     * @param encoded Base64 encoded input
     * @return Plaintext
     */
    public static String decodeBase64(String encoded) {
        byte[] decodedBytes = Base64.getUrlDecoder().decode(encoded);
        return new String(decodedBytes);
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
