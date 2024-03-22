package org.virtuConf.demo.auth.utils.cache;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPooled;

import java.util.Optional;

@Component
public class RedisOTPCache implements OTPCache{
    @Value("${redis.host}")
    private String host;
    @Value("${redis.port}")
    private Integer port;
    private JedisPooled jedis;
    @PostConstruct
    public void postConstruct(){
        this.jedis = new JedisPooled(host, port);
    }
    @Override
    public void setOTP(String otp, String owner) {
        String key = "OTP_" + owner;
        jedis.set(key , otp);
    }

    @Override
    public void setOTP(String otp, String owner, Integer expiry) {
        String key = "OTP_" + owner;
        jedis.set(key , otp);
        jedis.expire(key , expiry);
    }

    @Override
    public Optional<String> getOTP(String identifier) {
        String key = "OTP_" + identifier;
        if(this.isIdentifierExists(identifier)) return Optional.of(jedis.get(key));
        else return Optional.empty();
    }

    @Override
    public boolean isOtpExists(String otp) {
        //No implementation in redis
        return false;
    }

    @Override
    public boolean isIdentifierExists(String identifier) {
        String key = "OTP_" + identifier;
        return jedis.exists(key);
    }
}
