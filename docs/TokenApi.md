# TokenApi

All URIs are relative to *https://signin.laserfiche.com/oauth*

Method | HTTP request | Description
------------- | ------------- | -------------
[**tokenGetAccessToken**](TokenApi.md#tokenGetAccessToken) | **POST** /Token | Request for an access token. - Use authCode to get an access token for the authorization code flow. This uses grant_type, code and redirect_uri. Furthermore, client id and client secret are taken from the Basic auth header for web applications. For SPAs, the client id should be in the request body. After getting the authorization code, the application could use it to exchange for an access token by calling this endpoint. Authorization code has a short lifetime for around 10 minutes. If it&#x27;s not used within its lifetime, it will be expired and the application should start from authorization again. - Use refreshToken to get a new access token for the authorization code flow. This uses grant_type and refresh_token. Furthermore, client id and client secret are taken from the Basic auth header for web applications. For SPAs, the client id should be in the request body. - Use Bearer header to generate access token for the client credential flow. This uses grant_type and bearer auth header.

<a name="tokenGetAccessToken"></a>
# **tokenGetAccessToken**
> GetAccessTokenResponse tokenGetAccessToken(clientId, grantType, code, redirectUri, scope, refreshToken, codeVerifier, authorization)

Request for an access token. - Use authCode to get an access token for the authorization code flow. This uses grant_type, code and redirect_uri. Furthermore, client id and client secret are taken from the Basic auth header for web applications. For SPAs, the client id should be in the request body. After getting the authorization code, the application could use it to exchange for an access token by calling this endpoint. Authorization code has a short lifetime for around 10 minutes. If it&#x27;s not used within its lifetime, it will be expired and the application should start from authorization again. - Use refreshToken to get a new access token for the authorization code flow. This uses grant_type and refresh_token. Furthermore, client id and client secret are taken from the Basic auth header for web applications. For SPAs, the client id should be in the request body. - Use Bearer header to generate access token for the client credential flow. This uses grant_type and bearer auth header.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.TokenApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: Basic Authorization
HttpBasicAuth Basic Authorization = (HttpBasicAuth) defaultClient.getAuthentication("Basic Authorization");
Basic Authorization.setUsername("YOUR USERNAME");
Basic Authorization.setPassword("YOUR PASSWORD");

TokenApi apiInstance = new TokenApi();
Object clientId = null; // Object | 
String grantType = "grantType_example"; // String | 
Object code = null; // Object | 
Object redirectUri = null; // Object | 
Object scope = null; // Object | 
Object refreshToken = null; // Object | 
Object codeVerifier = null; // Object | 
String authorization = "authorization_example"; // String | 
try {
    GetAccessTokenResponse result = apiInstance.tokenGetAccessToken(clientId, grantType, code, redirectUri, scope, refreshToken, codeVerifier, authorization);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TokenApi#tokenGetAccessToken");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **clientId** | [**Object**](.md)|  | [optional]
 **grantType** | **String**|  | [optional]
 **code** | [**Object**](.md)|  | [optional]
 **redirectUri** | [**Object**](.md)|  | [optional]
 **scope** | [**Object**](.md)|  | [optional]
 **refreshToken** | [**Object**](.md)|  | [optional]
 **codeVerifier** | [**Object**](.md)|  | [optional]
 **authorization** | **String**|  | [optional]

### Return type

[**GetAccessTokenResponse**](GetAccessTokenResponse.md)

### Authorization

[Authorization](../README.md#Authorization)[Basic Authorization](../README.md#Basic Authorization)

### HTTP request headers

 - **Content-Type**: application/x-www-form-urlencoded
 - **Accept**: application/json

