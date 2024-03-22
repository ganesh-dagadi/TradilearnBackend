package org.virtuConf.demo.auth.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.virtuConf.demo.auth.dto.RegisterDTO;

@Entity
public class Person {
    @Id
    @GeneratedValue
    private Integer id;
    private String username;
    private String email;
    private String password;
    private Boolean isVerified;
    private Boolean isActive;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
    public static Person buildFromRequest(RegisterDTO register){
        Person newPerson = new Person();
        newPerson.email = register.getEmail();
        newPerson.password = register.getPassword();
        newPerson.username = register.getUsername();
        newPerson.isActive = false;
        newPerson.isVerified = false;
        return newPerson;
    }
}
