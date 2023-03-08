package com.application.vladcelona.eximeeting_samsung.data_classes;

import java.io.Serializable;

public class User implements Serializable {

    public String fullName, email, companyName;
    // public boolean isAuthenticated;

    public User() {}

    public User(String fullName, String email, String companyName) {
        this.fullName = fullName;
        this.email = email;
        this.companyName = companyName;
    }
}