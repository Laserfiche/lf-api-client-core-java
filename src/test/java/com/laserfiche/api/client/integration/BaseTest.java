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
    @BeforeAll
    public static void setUp() {
        Dotenv dotenv = Dotenv
                .configure()
                .filename(".env")
                .systemProperties()
                .load();
        repositoryId = getEnvironmentVariable(REPOSITORY_ID);
        if (nullOrEmpty(repositoryId)) {
            throw new IllegalStateException("Environment variable REPOSITORY_ID does not exist.");
        }
        servicePrincipalKey = getEnvironmentVariable(SERVICE_PRINCIPAL_KEY);
        String accessKeyBase64 = getEnvironmentVariable(ACCESS_KEY);
        accessKey = AccessKey.createFromBase64EncodedAccessKey(accessKeyBase64);
        username = getEnvironmentVariable(USERNAME);
        password = getEnvironmentVariable(PASSWORD);
        baseUrl = getEnvironmentVariable(BASE_URL);
//        spKey = System.getenv("SERVICE_PRINCIPAL_KEY");
//        repoId = System.getenv("REPOSITORY_ID");
//        username = System.getenv("APISERVER_USERNAME");
//        password = System.getenv("APISERVER_PASSWORD");
//        baseUrl = System.getenv("APISERVER_REPOSITORY_API_BASE_URL");
//        String accessKeyBase64 = System.getenv("ACCESS_KEY");
//        if (spKey == null && accessKeyBase64 == null) {
//            // Load environment variables
//            Dotenv dotenv = Dotenv
//                    .configure()
//                    .filename(".env")
//                    .load();
//            // Read env variable
//            accessKeyBase64 = dotenv.get("ACCESS_KEY");
//            repoId = dotenv.get("REPOSITORY_ID");
//            username = dotenv.get("APISERVER_USERNAME");
//            password = dotenv.get("APISERVER_PASSWORD");
//            baseUrl = dotenv.get("APISERVER_REPOSITORY_API_BASE_URL");
//            spKey = dotenv.get("SERVICE_PRINCIPAL_KEY");
//        }
//        if (accessKeyBase64 != null){
//            accessKey = AccessKey.createFromBase64EncodedAccessKey(accessKeyBase64);
//        }
    }

    private static String getEnvironmentVariable(String environmentVariableName) {
        String environmentVariable = System.getenv(environmentVariableName);
        if (nullOrEmpty(environmentVariable)) {
            environmentVariable = System.getProperty(environmentVariableName);
            if (nullOrEmpty(environmentVariable))
                throw new IllegalStateException(
                        "Environment variable '" + environmentVariableName + "' does not exist.");
        }
        return environmentVariable;
    }

    public static boolean nullOrEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
