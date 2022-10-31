package com.laserfiche.api.client.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.laserfiche.api.client.apiserver.ApiServer;
import com.laserfiche.api.client.deserialization.OffsetDateTimeDeserializer;
import com.laserfiche.api.client.deserialization.TokenClientObjectMapper;
import com.laserfiche.api.client.httphandlers.HeadersImpl;
import com.laserfiche.api.client.model.AccessKey;
import com.laserfiche.api.client.model.ApiException;
import com.laserfiche.api.client.model.GetAccessTokenResponse;
import com.laserfiche.api.client.model.ProblemDetails;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import org.threeten.bp.OffsetDateTime;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.laserfiche.api.client.oauth.OAuthUtil.createBearer;
import static com.laserfiche.api.client.oauth.OAuthUtil.getOAuthApiBaseUri;


public class TokenClientImpl extends ApiServer implements TokenClient {
    private String baseUrl;

    //protected ObjectMapper objectMapper;

    public TokenClientImpl(String regionalDomain) {
        super();
        baseUrl = getOAuthApiBaseUri(regionalDomain);
//        Unirest
//                .config()
//                .setObjectMapper(new TokenClientObjectMapper());
//        SimpleModule module = new SimpleModule();
//        module.addDeserializer(OffsetDateTime.class, new OffsetDateTimeDeserializer());
//        this.objectMapper = JsonMapper
//                .builder()
//                .addModule(module)
//                .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
//                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
//                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
//                .build();
    }

    @Override
    public CompletableFuture<GetAccessTokenResponse> getAccessTokenFromServicePrincipal(String spKey,
            AccessKey accessKey) {
        String bearer = createBearer(spKey, accessKey);
        return Unirest
                .post(baseUrl + "Token")
                .header("Authorization", bearer)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .field("grant_type", "client_credentials")
                .asObjectAsync(Object.class)
                .thenApply(httpResponse -> {
                    if (httpResponse.getStatus() == 200) {
                        try {
                            String jsonString = new JSONObject(httpResponse.getBody()).toString();
                            return objectMapper.readValue(jsonString, GetAccessTokenResponse.class);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                            return null;
                        }
                    } else {
                        ProblemDetails problemDetails;
                        try {
                            String jsonString = new JSONObject(httpResponse.getBody()).toString();
                            problemDetails = objectMapper.readValue(jsonString, ProblemDetails.class);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                            return null;
                        }
                        //Map<String, String> headersMap = HeadersImpl.getHeadersMap(httpResponse);
                        Map<String, String> headersMap = getHeadersMap(httpResponse);
                        if (httpResponse.getStatus() == 400)
                            throw new ApiException("Invalid or bad request.", httpResponse.getStatus(),
                                    httpResponse.getStatusText(), headersMap, problemDetails);
                        else if (httpResponse.getStatus() == 401)
                            throw new ApiException("Access token is invalid or expired.", httpResponse.getStatus(),
                                    httpResponse.getStatusText(), headersMap, problemDetails);
                        else if (httpResponse.getStatus() == 403)
                            throw new ApiException("Access denied for the operation.", httpResponse.getStatus(),
                                    httpResponse.getStatusText(), headersMap, problemDetails);
                        else if (httpResponse.getStatus() == 404)
                            throw new ApiException("Not found.", httpResponse.getStatus(), httpResponse.getStatusText(),
                                    headersMap, problemDetails);
                        else if (httpResponse.getStatus() == 429)
                            throw new ApiException("Rate limit is reached.", httpResponse.getStatus(),
                                    httpResponse.getStatusText(), headersMap, problemDetails);
                        else
                            throw new RuntimeException(httpResponse.getStatusText());
                    }
                });
    }

    @Override
    public CompletableFuture<GetAccessTokenResponse> getAccessTokenFromCode(String code, String redirectUri,
            String clientId, String clientSecret, String codeVerifier) {
        return null;
    }

    @Override
    public CompletableFuture<GetAccessTokenResponse> refreshAccessToken(String refreshToken, String clientId,
            String clientSecret) {
        return null;
    }

}
