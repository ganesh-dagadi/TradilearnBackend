package org.virtuConf.demo.auth.utils.jwt;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.virtuConf.demo.auth.utils.errors.BadRequest;
import org.virtuConf.demo.auth.utils.errors.UnauthenticatedError;

public interface JWTInterface {
    String generateSinglePayloadJWT(String payload_id , String payload , Boolean isAccess, Boolean willExpire , Integer expiresIn) throws RuntimeException;
    String decodeAccessJWT(String token, String key) throws TokenExpiredException, SignatureVerificationException, RuntimeException, UnauthenticatedError, BadRequest;
    String decodeRefreshJWT(String token, String key) throws TokenExpiredException, SignatureVerificationException, RuntimeException, UnauthenticatedError, BadRequest;

}
