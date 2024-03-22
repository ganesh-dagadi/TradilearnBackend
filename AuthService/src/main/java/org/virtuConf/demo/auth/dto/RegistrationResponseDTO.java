package org.virtuConf.demo.auth.dto;

public class RegistrationResponseDTO {
    private String msg;
    private Integer userId;
    public void setMsg(String msg){
        this.msg = msg;
    }
    public String getMsg(){
        return this.msg;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
