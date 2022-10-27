package com.laserfiche.api.client.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laserfiche.api.client.deserialization.TokenClientObjectMapper;
import com.laserfiche.api.client.httphandlers.HeadersImpl;
import com.laserfiche.api.client.model.*;
import kong.unirest.Header;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.laserfiche.api.client.oauth.OAuthUtil.createBearer;
import static com.laserfiche.api.client.oauth.OAuthUtil.getOAuthApiBaseUri;


public class TokenClientImpl implements TokenClient {
    private String baseUrl;

    protected ObjectMapper objectMapper;
    public TokenClientImpl(String regionalDomain) {
        baseUrl = getOAuthApiBaseUri(regionalDomain);
        Unirest.config().setObjectMapper(new TokenClientObjectMapper());
    }

    @Override
    public CompletableFuture<GetAccessTokenResponse> getAccessTokenFromServicePrincipal(String spKey, AccessKey accessKey) {
        String bearer = createBearer(spKey, accessKey);
//        CompletableFuture<HttpResponse<GetAccessTokenResponse>> future = Unirest
//                .post(baseUrl + "Token")
//                .header("Authorization", bearer)
//                .header("Accept", "application/json")
//                .header("Content-Type", "application/x-www-form-urlencoded")
//                .field("grant_type", "client_credentials")
//                .asObjectAsync(GetAccessTokenResponse.class);
//        return future.thenApply(httpResponse -> {
//            if (httpResponse.getStatus() != 200) {
//                throw new RuntimeException(httpResponse.getStatusText());
//            }
//            return httpResponse.getBody();
//        });
        return Unirest
                .post(baseUrl + "Token")
                .header("Authorization", bearer)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .field("grant_type", "client_credentials")
                .asObjectAsync(GetAccessTokenResponse.class)
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
                        Map<String, String> headersMap = HeadersImpl.getHeadersMap(httpResponse);
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
    public CompletableFuture<GetAccessTokenResponse> getAccessTokenFromCode(String code, String redirectUri, String clientId, String clientSecret, String codeVerifier) {
        return null;
    }

    @Override
    public CompletableFuture<GetAccessTokenResponse> refreshAccessToken(String refreshToken, String clientId, String clientSecret) {
        return null;
    }

}
