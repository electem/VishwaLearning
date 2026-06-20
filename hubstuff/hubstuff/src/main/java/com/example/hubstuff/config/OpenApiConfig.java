package com.example.hubstuff.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI dpopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("DPoP Token Service API")
                        .version("1.0.0")
                        .description("Generates DPoP proof JWTs for binding refresh tokens to a public key."));
    }
}