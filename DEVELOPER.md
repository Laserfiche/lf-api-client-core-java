## How It Works

- `HttpHandler`: uses `TokenClient` to provide public interface in interceptor fashion (beforeSend/afterSend)
  
  Currently supports (implemented by `ClientCredentialsHandler`)
  - Client Credentials Flow

- `TokenClient` (`TokenClientImpl`): uses `OAuthClient` to provide public interface to
  - Client Credentials Flow
  - Authorization Code Flow with Refresh Token

- `OAuthClient`: a Retrofit interface that generates HttpClient capable of supporting
  - Client Credentials Flow
  - Authorization Code Flow with Refresh Token

## Stack Diagram

|||
|-|-|
|HttpHandler|Public interface
|TokenClient|Public interface
|OAuthClient|Implementation that may change in the future
|Retrofit 2|External dependency that may change in the future
|OkHttp 3|Dependency of the external dependency

## Tips for Maven Projects

All of our Java projects are Maven projects. This has many benefits. Sometimes, you may want to install the dev package (jars) to your local Maven repository (the .m2 folder). You can do so via command line. But it's much easier to just run the `install` stage.
