package org.virtuConf.demo.auth.dto;

import java.util.HashMap;
import java.util.Map;

public class LoginResponseDTO {
    private String msg;
    private final Map<String , String> tokens= new HashMap<>();
    private PersonDataDTO user;
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

       public void setAccessToken(String accessToken) {
        tokens.put("access" , accessToken);
    }

    public Map<String , String> getTokens() {
        return tokens;
    }

    public void setRefreshToken(String refreshToken) {
        tokens.put("refresh" , refreshToken);
    }

    public PersonDataDTO getUser() {
        return user;
    }

    public void setPerson(PersonDataDTO person) {
        this.user = person;
    }
}
