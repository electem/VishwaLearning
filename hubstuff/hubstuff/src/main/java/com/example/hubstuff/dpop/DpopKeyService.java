package com.example.hubstuff.dpop;


import org.springframework.stereotype.Service;

import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.gen.ECKeyGenerator;

@Service
public class DpopKeyService {

    private final ECKey ecJwk;

    public DpopKeyService() {
        try {
            this.ecJwk = new ECKeyGenerator(Curve.P_256)
                    .keyID("dpop-key-1")
                    .generate();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to generate DPoP key pair", e);
        }
    }

    public ECKey getKey() {
        return ecJwk;
    }

    public String getPublicJwkJson() {
        return ecJwk.toPublicJWK().toJSONString();
    }
}
