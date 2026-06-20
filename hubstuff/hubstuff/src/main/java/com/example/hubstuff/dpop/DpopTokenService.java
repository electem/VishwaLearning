package com.example.hubstuff.dpop;


import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@Service
public class DpopTokenService {

    private final DpopKeyService keyService;

    public DpopTokenService(DpopKeyService keyService) {
        this.keyService = keyService;
    }

    public String generateToken(String httpMethod, String httpUri) {
        try {
            ECKey ecJwk = keyService.getKey();

            JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES256)
                    .type(new JOSEObjectType("dpop+jwt"))
                    .jwk(ecJwk.toPublicJWK())
                    .build();

            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .claim("htm", httpMethod)
                    .claim("htu", httpUri)
                    .issueTime(new Date())
                    .claim("jti", UUID.randomUUID().toString())
                    .build();

            SignedJWT signedJWT = new SignedJWT(header, claims);
            signedJWT.sign(new ECDSASigner(ecJwk));

            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new IllegalStateException("Failed to sign DPoP JWT", e);
        }
    }
}