package org.virtuConf.demo.auth.dto;

public class ValidateOTPDTO {
    private Integer userID;
    private String otp;

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
