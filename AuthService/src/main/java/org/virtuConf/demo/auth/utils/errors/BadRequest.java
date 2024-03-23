package org.virtuConf.demo.auth.utils.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequest extends Exception{
    Integer errCode;
    public BadRequest(){
        super();
    }
    public BadRequest(String msg , Integer errCode){
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
