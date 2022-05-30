/*
 * Laserfiche OAuth 2.0 Authorization Server API
 * An ASP.NET Core web API for Laserfiche OAuth 2.0 Authorization Server<p><strong>Build# : </strong>4f7a879120e1a11fb1d3772c18859787d28d355b_.20220404.1</p>
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package com.laserfiche.api.client.oauth;

import com.laserfiche.api.client.ApiCallback;
import com.laserfiche.api.client.ApiClient;
import com.laserfiche.api.client.ApiException;
import com.laserfiche.api.client.ApiResponse;
import com.laserfiche.api.client.Configuration;
import com.laserfiche.api.client.Pair;
import com.laserfiche.api.client.ProgressRequestBody;
import com.laserfiche.api.client.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import com.laserfiche.api.client.model.GetAccessTokenResponse;
import com.laserfiche.api.client.model.ProblemDetails;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TokenApi {
    private ApiClient apiClient;

    public TokenApi() {
        this(Configuration.getDefaultApiClient());
    }

    public TokenApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for tokenGetAccessToken
     * @param clientId  (optional)
     * @param grantType  (optional)
     * @param code  (optional)
     * @param redirectUri  (optional)
     * @param scope  (optional)
     * @param refreshToken  (optional)
     * @param codeVerifier  (optional)
     * @param authorization  (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call tokenGetAccessTokenCall(Object clientId, String grantType, Object code, Object redirectUri, Object scope, Object refreshToken, Object codeVerifier, String authorization, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;
        
        // create path and map variables
        String localVarPath = "/Token";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        if (authorization != null)
            localVarHeaderParams.put("Authorization", apiClient.parameterToString(authorization));

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();
        if (clientId != null)
            localVarFormParams.put("client_id", clientId);
        if (grantType != null)
            localVarFormParams.put("grant_type", grantType);
        if (code != null)
            localVarFormParams.put("code", code);
        if (redirectUri != null)
            localVarFormParams.put("redirect_uri", redirectUri);
        if (scope != null)
            localVarFormParams.put("scope", scope);
        if (refreshToken != null)
            localVarFormParams.put("refresh_token", refreshToken);
        if (codeVerifier != null)
            localVarFormParams.put("code_verifier", codeVerifier);

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            "application/x-www-form-urlencoded"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] { "Authorization", "Basic Authorization" };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }
    
    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call tokenGetAccessTokenValidateBeforeCall(Object clientId, String grantType, Object code, Object redirectUri, Object scope, Object refreshToken, Object codeVerifier, String authorization, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        com.squareup.okhttp.Call call = tokenGetAccessTokenCall(clientId, grantType, code, redirectUri, scope, refreshToken, codeVerifier, authorization, progressListener, progressRequestListener);
        return call;

        
        
        
        
    }

    /**
     * Request for an access token. - Use authCode to get an access token for the authorization code flow. This uses grant_type, code and redirect_uri. Furthermore, client id and client secret are taken from the Basic auth header for web applications. For SPAs, the client id should be in the request body. After getting the authorization code, the application could use it to exchange for an access token by calling this endpoint. Authorization code has a short lifetime for around 10 minutes. If it&#x27;s not used within its lifetime, it will be expired and the application should start from authorization again. - Use refreshToken to get a new access token for the authorization code flow. This uses grant_type and refresh_token. Furthermore, client id and client secret are taken from the Basic auth header for web applications. For SPAs, the client id should be in the request body. - Use Bearer header to generate access token for the client credential flow. This uses grant_type and bearer auth header.
     * 
     * @param clientId  (optional)
     * @param grantType  (optional)
     * @param code  (optional)
     * @param redirectUri  (optional)
     * @param scope  (optional)
     * @param refreshToken  (optional)
     * @param codeVerifier  (optional)
     * @param authorization  (optional)
     * @return GetAccessTokenResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public GetAccessTokenResponse tokenGetAccessToken(Object clientId, String grantType, Object code, Object redirectUri, Object scope, Object refreshToken, Object codeVerifier, String authorization) throws ApiException {
        ApiResponse<GetAccessTokenResponse> resp = tokenGetAccessTokenWithHttpInfo(clientId, grantType, code, redirectUri, scope, refreshToken, codeVerifier, authorization);
        return resp.getData();
    }

    /**
     * Request for an access token. - Use authCode to get an access token for the authorization code flow. This uses grant_type, code and redirect_uri. Furthermore, client id and client secret are taken from the Basic auth header for web applications. For SPAs, the client id should be in the request body. After getting the authorization code, the application could use it to exchange for an access token by calling this endpoint. Authorization code has a short lifetime for around 10 minutes. If it&#x27;s not used within its lifetime, it will be expired and the application should start from authorization again. - Use refreshToken to get a new access token for the authorization code flow. This uses grant_type and refresh_token. Furthermore, client id and client secret are taken from the Basic auth header for web applications. For SPAs, the client id should be in the request body. - Use Bearer header to generate access token for the client credential flow. This uses grant_type and bearer auth header.
     * 
     * @param clientId  (optional)
     * @param grantType  (optional)
     * @param code  (optional)
     * @param redirectUri  (optional)
     * @param scope  (optional)
     * @param refreshToken  (optional)
     * @param codeVerifier  (optional)
     * @param authorization  (optional)
     * @return ApiResponse&lt;GetAccessTokenResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<GetAccessTokenResponse> tokenGetAccessTokenWithHttpInfo(Object clientId, String grantType, Object code, Object redirectUri, Object scope, Object refreshToken, Object codeVerifier, String authorization) throws ApiException {
        com.squareup.okhttp.Call call = tokenGetAccessTokenValidateBeforeCall(clientId, grantType, code, redirectUri, scope, refreshToken, codeVerifier, authorization, null, null);
        Type localVarReturnType = new TypeToken<GetAccessTokenResponse>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Request for an access token. - Use authCode to get an access token for the authorization code flow. This uses grant_type, code and redirect_uri. Furthermore, client id and client secret are taken from the Basic auth header for web applications. For SPAs, the client id should be in the request body. After getting the authorization code, the application could use it to exchange for an access token by calling this endpoint. Authorization code has a short lifetime for around 10 minutes. If it&#x27;s not used within its lifetime, it will be expired and the application should start from authorization again. - Use refreshToken to get a new access token for the authorization code flow. This uses grant_type and refresh_token. Furthermore, client id and client secret are taken from the Basic auth header for web applications. For SPAs, the client id should be in the request body. - Use Bearer header to generate access token for the client credential flow. This uses grant_type and bearer auth header. (asynchronously)
     * 
     * @param clientId  (optional)
     * @param grantType  (optional)
     * @param code  (optional)
     * @param redirectUri  (optional)
     * @param scope  (optional)
     * @param refreshToken  (optional)
     * @param codeVerifier  (optional)
     * @param authorization  (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call tokenGetAccessTokenAsync(Object clientId, String grantType, Object code, Object redirectUri, Object scope, Object refreshToken, Object codeVerifier, String authorization, final ApiCallback<GetAccessTokenResponse> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = tokenGetAccessTokenValidateBeforeCall(clientId, grantType, code, redirectUri, scope, refreshToken, codeVerifier, authorization, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<GetAccessTokenResponse>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
}
