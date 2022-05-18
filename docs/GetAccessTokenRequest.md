# GetAccessTokenRequest

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**clientId** | **Object** | The Client ID returned when the application was registered. |  [optional]
**grantType** | **String** | The value MUST be either of \&quot;authorization_code\&quot;, \&quot;refresh_token\&quot;, or \&quot;client_credentials\&quot;. | 
**code** | **Object** | The authorization code returned by the authorization server in the first step of the authorization code flow. |  [optional]
**redirectUri** | **Object** | This should be equal to the Redirect URI sent to /authorize endpoint in the first step of the authorization code flow. |  [optional]
**scope** | **Object** | The scope of the requested access token. |  [optional]
**refreshToken** | **Object** | The refresh token, which can be used to obtain new access tokens. |  [optional]
**codeVerifier** | **Object** | A code verifier, used to verify the code_challenge sent to /authorize endpoint in the first step of the authorization code flow. |  [optional]
