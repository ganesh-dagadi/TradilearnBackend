package org.virtuConf.demo.auth.utils.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthenticatedError extends Exception{
    public UnauthenticatedError(String msg){
        super(msg);
    }
}
