package org.virtuConf.demo.auth.utils.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthenticatedError extends Exception{
    Integer errCode;
    public UnauthenticatedError(String msg){
        super(msg);
    }
    public UnauthenticatedError(String msg , Integer errCode){
        super(msg);
        this.setErrCode(errCode);
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }
}
