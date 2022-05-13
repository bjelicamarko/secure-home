package com.asdf.myhomeback.dto;

import com.asdf.myhomeback.models.AppUser;

public class AppUserDTO {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String role;

    public AppUserDTO() {}

    public AppUserDTO(AppUser appUser) {
        this.id = appUser.getId();
        this.username = appUser.getUsername();
        this.email = appUser.getEmail();
        this.firstName = appUser.getFirstname();
        this.lastName = appUser.getLastname();
        this.role = appUser.getRoles().get(0).getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
