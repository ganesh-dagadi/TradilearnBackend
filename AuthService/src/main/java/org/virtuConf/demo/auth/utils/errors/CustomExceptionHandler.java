package org.virtuConf.demo.auth.utils.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<Map<String , Object>> handleBadRequest(BadRequest ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("errCode", ex.getErrCode()); // Custom error code
        errorResponse.put("err", ex.getMessage()); // Error message
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthenticatedError.class)
    public  ResponseEntity<Map<String, Object>> handleUnauthenticated(UnauthenticatedError ex){
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("errCode", ex.getErrCode()); // Custom error code
        errorResponse.put("err", ex.getMessage()); // Error message
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}