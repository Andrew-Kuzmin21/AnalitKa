package com.kuzmin.Project_i.model;

import jakarta.validation.Valid;

public class RegistrationForm {
    @Valid
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
