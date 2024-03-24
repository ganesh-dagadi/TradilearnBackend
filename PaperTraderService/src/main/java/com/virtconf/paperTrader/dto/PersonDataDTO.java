package com.virtconf.paperTrader.dto;

public class PersonDataDTO {
    private Integer id;
    private String username;
    private String email;
    private Boolean isEmailVerified;
    private Boolean isActive;

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getIsEmailVerified() {
        return isEmailVerified;
    }

    public Boolean getIsActive() {
        return isActive;
    }
}
