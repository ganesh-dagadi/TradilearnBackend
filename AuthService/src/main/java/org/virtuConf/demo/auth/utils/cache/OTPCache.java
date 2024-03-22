package org.virtuConf.demo.auth.utils.cache;

import java.util.Optional;

public interface OTPCache {
    public void setOTP(String otp , String owner);
    public void setOTP(String otp , String owner , Integer expiry);
    public Optional<String> getOTP(String identifier);
    public boolean isOtpExists(String otp);
    public boolean isIdentifierExists(String identifier);
}
