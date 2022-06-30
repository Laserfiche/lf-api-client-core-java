# Laserfiche API Client Core Maven
Maven build implementation of various foundational APIs for Laserfiche, including authorization APIs such as OAuth 2.0 flows for secure and easy access to Laserfiche APIs.

Documentation [Laserfiche OAuth 2.0 Authorization Server API](https://developer.laserfiche.com/libraries.html).

## Overview
- `src\main\java\com\laserfiche\api\client` contains the implementation of the foundational authentication/authorization related code for APIs of various Laserfiche products.
- `src\main\java\com\laserfiche\api\client\httphandlers` contains a client implementation of OAuth 2.0 Client Credentials Flow.
- `src\main\java\com\laserfiche\api\client\oauth` contains all OAuth related client code.
- `src\main\java\com\laserfiche\api\client\oauth\OauthUtil.java` contains all utility functions and classes for Laserfiche APIs.
- `src\test\java\com\laserfiche\api\client\integration` contains all integration tests. To run them, you either use GitHub Workflows, or you could provide the `.env` files in your file system and run them there.
- `src\test\java\com\laserfiche\api\client\unit` contains all unit tests.

## How to contribute
Technically you could use any editors you like. But it's more convenient if you are using either IntelliJ or Eclipse. Here is a few useful commands for building and testing the app.

### Generate the oauth client
1. Download the `swagger-code-gen` command line tool. The repo for that library can be found [here](https://search.maven.org/search?q=a:swagger-codegen-cli).
2. From the root directory of this Git repository, run the command `java -jar swagger-codegen-cli-3.0.34.jar generate -i oauth-swagger.json -l java --artifact-id com.laserfiche.api.client.core  --api-package com.laserfiche.api.client.oauth --model-package com.laserfiche.api.client.model`


### Build, Test, and Package

See the `./workflow/main.yml`.