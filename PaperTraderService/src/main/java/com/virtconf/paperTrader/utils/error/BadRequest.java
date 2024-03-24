package com.virtconf.paperTrader.utils.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequest extends Exception{
    public BadRequest(String msg){
        super(msg);
    }
}
