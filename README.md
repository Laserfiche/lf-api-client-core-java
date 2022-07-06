# Laserfiche API Client Core

Implementation of various foundational APIs for Laserfiche, including authorization APIs such as OAuth 2.0 flows for secure and easy access to Laserfiche APIs.

Documentation [Laserfiche OAuth 2.0 Authorization Server API](https://developer.laserfiche.com/libraries.html).

## Overview

|Directory|Details|
|-|-|
|src\main\java\com\laserfiche\api\client|Contains the implementation of the foundational authentication/authorization related code for APIs of various Laserfiche products.|
|src\main\java\com\laserfiche\api\client\httphandlers|Contains a client implementation of OAuth 2.0 Client Credentials Flow.|
|src\main\java\com\laserfiche\api\client\oauth|Contains all OAuth related client code.
|src\main\java\com\laserfiche\api\client\oauth\OauthUtil.java|Contains all utility functions and classes for Laserfiche APIs.
|src\test\java\com\laserfiche\api\client\integration|Contains all integration tests. To run them, you either use GitHub Workflows, or you could provide the `.env` files in your file system and run them there.
|src\test\java\com\laserfiche\api\client\unit|Contains all unit tests.

## How to contribute

The project is built using Maven. Any text editor or IDE that can work with Maven will be fine. At work, we use IDEs like IntelliJ and Eclipse. For more information on project structure and developer related inforamtion, see DEVELOPER.md.

### Generate the oauth client

1. Download the `swagger-codegen` command line tool. The repo for that library can be found [here](https://search.maven.org/search?q=a:swagger-codegen-cli).
2. From the root directory of this Git repository, run the command `java -jar swagger-codegen-cli-3.0.34.jar generate -i swagger.json -l java --artifact-id lf-api-client-core --api-package com.laserfiche.api.client --model-package com.laserfiche.api.client.model --library retrofit2`

### Build, Test, and Package

We use Maven, any Maven command should work properly. You can also take a look at the workflow file (`./workflow/main.yml`) for quick reference.