package com.example.hubstuff.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hubstuff.dpop.DpopKeyService;
import com.example.hubstuff.dpop.DpopTokenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "DPoP", description = "Endpoints for generating DPoP proof JWTs")
public class DpopController {

    private final DpopTokenService tokenService;
    private final DpopKeyService keyService;

    public DpopController(DpopTokenService tokenService, DpopKeyService keyService) {
        this.tokenService = tokenService;
        this.keyService = keyService;
    }

    @Operation(summary = "Generate a DPoP proof JWT", 
               description = "Creates a signed dpop+jwt bound to the server's EC key pair, for the given HTTP method and URI.")
    @GetMapping("/dpop-token")
    public Map<String, String> getDpopToken(
            @Parameter(description = "HTTP method the token will be used with, e.g. POST")
            @RequestParam(defaultValue = "POST") String htm,
            @Parameter(description = "HTTP URI the token will be used with, e.g. /personal_access_tokens")
            @RequestParam(defaultValue = "/personal_access_tokens") String htu) {

        String token = tokenService.generateToken(htm, htu);
        return Map.of("dpop", token);
    }

    @Operation(summary = "Get the server's public JWK", 
               description = "Returns the public part of the EC key pair used to sign DPoP tokens.")
    @GetMapping("/dpop-public-key")
    public Map<String, String> getPublicKey() {
        return Map.of("jwk", keyService.getPublicJwkJson());
    }
}