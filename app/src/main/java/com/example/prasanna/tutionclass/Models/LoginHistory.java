package com.example.prasanna.tutionclass.Models;

/**
 * Created by prasanna_d on 8/17/2017.
 */

public class LoginHistory {
    private Long id;
    private String email;

    public LoginHistory(String email) {
        this.email = email;
    }

    public LoginHistory(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
