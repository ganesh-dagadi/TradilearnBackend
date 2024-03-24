package com.virtconf.paperTrader.dto;

public class PersonDataDTO {
    private Integer id;
    private String username;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEmailVerified(Boolean emailVerified) {
        isEmailVerified = emailVerified;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

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
