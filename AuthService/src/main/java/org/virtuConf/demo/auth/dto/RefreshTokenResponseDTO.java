package org.virtuConf.demo.auth.dto;

public class RefreshTokenResponseDTO {
    private String accessToken;

    public String getToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
