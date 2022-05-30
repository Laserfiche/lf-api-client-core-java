package com.laserfiche.api.client.integration;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TokenApiImplTest {
    private Dotenv dotenv;

    @BeforeAll
    public static void setUp() {
        // Load environment variables
        Dotenv dotenv = Dotenv
                .configure()
                .filename("TestConfig.env")
                .load();
    }
}
