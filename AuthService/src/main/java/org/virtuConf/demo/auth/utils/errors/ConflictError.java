package org.virtuConf.demo.auth.utils.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictError extends Exception{
    public ConflictError(String msg){
        super(msg);
    }
}
