package org.virtuConf.demo.auth.utils.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.GONE)
public class ResourceMissingError extends Exception{
    public ResourceMissingError(String msg){
        super(msg);
    }
}
