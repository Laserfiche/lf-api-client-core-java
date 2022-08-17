package com.laserfiche.api.client.integration;

import com.laserfiche.api.client.model.AccessKey;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;

public class BaseTest {
    protected static String spKey;
    protected static AccessKey accessKey;

    @BeforeAll
    public static void setUp() throws IOException {
        spKey = System.getenv("SERVICE_PRINCIPAL_KEY");
        String accessKeyBase64 = System.getenv("ACCESS_KEY");
        if (spKey == null && accessKeyBase64 == null) {
            // Load environment variables
            Dotenv dotenv = Dotenv
                    .configure()
                    .filename(".env")
                    .load();
            // Read env variable
            accessKeyBase64 = dotenv.get("ACCESS_KEY");
            spKey = dotenv.get("SERVICE_PRINCIPAL_KEY");
        }
        accessKey = AccessKey.createFromBase64EncodedAccessKey(accessKeyBase64);
    }
}
