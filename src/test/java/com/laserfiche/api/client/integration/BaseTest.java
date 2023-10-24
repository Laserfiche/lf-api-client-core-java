// Copyright (c) Laserfiche
// Licensed under the MIT License. See LICENSE in the project root for license information.
package com.laserfiche.api.client.integration;

import com.laserfiche.api.client.model.AccessKey;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;


public class BaseTest {
    protected static String servicePrincipalKey;
    protected static AccessKey accessKey;
    protected static String repositoryId;
    protected static String username;
    protected static String password;
    protected static String baseUrl;
    private static final String ACCESS_KEY = "ACCESS_KEY";
    private static final String SERVICE_PRINCIPAL_KEY = "SERVICE_PRINCIPAL_KEY";
    private static final String REPOSITORY_ID = "REPOSITORY_ID";
    private static final String USERNAME = "APISERVER_USERNAME";
    private static final String PASSWORD = "APISERVER_PASSWORD";
    private static final String BASE_URL = "APISERVER_REPOSITORY_API_BASE_URL";
    private static final boolean IS_NOT_GITHUB_ENVIRONMENT = nullOrEmpty(System.getenv("GITHUB_WORKSPACE"));

    @BeforeAll
    public static void setUp() {
        Dotenv dotenv = Dotenv
                .configure()
                .filename(".env")
                .systemProperties()
                .ignoreIfMissing()
                .load();
        repositoryId = getEnvironmentVariable(REPOSITORY_ID);
        servicePrincipalKey = getEnvironmentVariable(SERVICE_PRINCIPAL_KEY);
        String accessKeyBase64 = getEnvironmentVariable(ACCESS_KEY);
        accessKey = AccessKey.createFromBase64EncodedAccessKey(accessKeyBase64);
        username = getEnvironmentVariable(USERNAME);
        password = getEnvironmentVariable(PASSWORD);
        baseUrl = getEnvironmentVariable(BASE_URL);
    }

    private static String getEnvironmentVariable(String environmentVariableName) {
        String environmentVariable = System.getenv(environmentVariableName);
        if (nullOrEmpty(environmentVariable)) {
            environmentVariable = System.getProperty(environmentVariableName);
            if (nullOrEmpty(environmentVariable) && IS_NOT_GITHUB_ENVIRONMENT)
                throw new IllegalStateException(
                        "Environment variable '" + environmentVariableName + "' does not exist.");
        }
        return environmentVariable;
    }

    public static boolean nullOrEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
