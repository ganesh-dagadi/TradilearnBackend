package org.virtuConf.demo.auth.dto;

import org.virtuConf.demo.auth.model.Person;

public class PersonDataDTO {
    private Integer id;
    private String username;
    private String email;
    private Boolean isEmailVerified;
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

    public Boolean getIsEmailVerified() {
        return isEmailVerified;
    }

    public void setVerified(Boolean verified) {
        isEmailVerified = verified;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public static PersonDataDTO fromPersonEntity(Person person){
        PersonDataDTO response = new PersonDataDTO();
        response.setActive(person.getActive());
        response.setId(person.getId());
        response.setEmail(person.getEmail());
        response.setUsername(person.getUsername());
        response.setVerified(person.getVerified());
        return response;
    }
}
