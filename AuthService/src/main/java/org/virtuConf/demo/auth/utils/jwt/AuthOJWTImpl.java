package org.virtuConf.demo.auth.utils.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.virtuConf.demo.auth.utils.errors.BadRequest;
import org.virtuConf.demo.auth.utils.errors.UnauthenticatedError;

import java.time.Instant;

@Component
public class AuthOJWTImpl implements JWTInterface{
    @Value("${java.jwt_access_secret}")
    String JWT_ACCESS_SECRET;
    @Value("${java.jwt_refresh_secret}")
    String JWT_REFRESH_SECRET;

    @Override
    public String generateSinglePayloadJWT(String payload_id, String payload, Boolean isAccess, Boolean willExpire, Integer expiresIn) throws RuntimeException{
            Algorithm jwtAlgo;
            if(isAccess) {
                jwtAlgo = Algorithm.HMAC256(JWT_ACCESS_SECRET);
            }else {
                jwtAlgo = Algorithm.HMAC256(JWT_REFRESH_SECRET);
            }
            Instant expires = Instant.now();
            expires = expires.plusSeconds(expiresIn);
            String token;
            if(willExpire) {
                token = JWT.create()
                        .withClaim(payload_id, payload)
                        .withExpiresAt(expires)
                        .withIssuedAt(Instant.now())
                        .sign(jwtAlgo);
            }else {
                token = JWT.create()
                        .withClaim(payload_id, payload)
                        .withIssuedAt(Instant.now())
                        .sign(jwtAlgo);
            }
            return token;
        }

    @Override
    public String decodeAccessJWT(String token, String key) throws RuntimeException, UnauthenticatedError, BadRequest {
        Algorithm jwtAlgo = Algorithm.HMAC256(JWT_ACCESS_SECRET);
        try{
            DecodedJWT decoded = JWT.require(jwtAlgo).build().verify(token);
            return decoded.getClaim(key).asString();
        }catch(TokenExpiredException e){
            throw new UnauthenticatedError("Token expired");
        }catch (SignatureVerificationException e){
            throw new BadRequest();
        }

    }

    @Override
    public String decodeRefreshJWT(String token, String key) throws RuntimeException, UnauthenticatedError, BadRequest {
        Algorithm jwtAlgo = Algorithm.HMAC256(JWT_REFRESH_SECRET);
        try{
            DecodedJWT decoded = JWT.require(jwtAlgo).build().verify(token);
            return decoded.getClaim(key).asString();
        }catch(TokenExpiredException e){
            throw new UnauthenticatedError("Token expired");
        }catch (SignatureVerificationException e){
            throw new BadRequest();
        }
    }
}
