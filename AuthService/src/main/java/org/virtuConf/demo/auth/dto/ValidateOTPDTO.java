package org.virtuConf.demo.auth.dto;

public class ValidateOTPDTO {
    private Integer userId;
    private String otp;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
