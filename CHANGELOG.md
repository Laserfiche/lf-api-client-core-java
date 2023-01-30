## 2.2.0

### Fixes
- Improve the `TokenClient` error handling to consistently throw an `ApiException` when handling error status codes.
- Additional properties have been added to `ProblemDetails` to more accurately represent the API response.
- **[BREAKING]** Remove `ProblemDetails` inherit from `HashMap<String, Object>`. Undocumented properties can be retrieved using `ProblemDetails.getExtensions()` instead.
- **[BREAKING]** Remove `ApiException` `response` property. Should use `ApiException` `message` property instead. Additionally, the `ApiException` constructor has been changed to not require the `response` property.

## 2.0.0

### Features

- **[BREAKING]** Drop use of `CompleatableFuture` so all APIs are now blocking.

## 1.1.0

### Features

- Add AutoCloseable support so the resources held by the underlying HTTP client can be release automatically.

## 1.0.1

### Maintenance

- Updated the com.fasterxml.jackson.core to the latest version, refactored UsernamePasswordHandler implementation and unit tests

## 1.0.0

### Features

- Add self-hosted API Server support.