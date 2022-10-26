package com.laserfiche.api.client.integration;

import com.laserfiche.api.client.model.AccessKey;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;


public class BaseTest {
    protected static String spKey;
    protected static AccessKey accessKey;
    protected static String repoId;
    protected static String username;
    protected static String password;
    protected static String baseUrl;
    @BeforeAll
    public static void setUp() {
        spKey = System.getenv("SERVICE_PRINCIPAL_KEY");
        repoId = System.getenv("REPOSITORY_ID");
        username = System.getenv("APISERVER_USERNAME");
        password = System.getenv("APISERVER_PASSWORD");
        baseUrl = System.getenv("APISERVER_REPOSITORY_API_BASE_URL");
        String accessKeyBase64 = System.getenv("ACCESS_KEY");
        if (spKey == null && accessKeyBase64 == null) {
            // Load environment variables
            Dotenv dotenv = Dotenv
                    .configure()
                    .filename(".env")
                    .load();
            // Read env variable
            accessKeyBase64 = dotenv.get("ACCESS_KEY");
            repoId = dotenv.get("REPOSITORY_ID");
            username = dotenv.get("APISERVER_USERNAME");
            password = dotenv.get("APISERVER_PASSWORD");
            baseUrl = dotenv.get("APISERVER_REPOSITORY_API_BASE_URL");
            spKey = dotenv.get("SERVICE_PRINCIPAL_KEY");
        }
        if (accessKeyBase64 != null){
            accessKey = AccessKey.createFromBase64EncodedAccessKey(accessKeyBase64);
        }
    }
}
