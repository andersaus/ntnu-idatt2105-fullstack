package com.ntnu.idi.obligfour.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class User {
    private long id;
    private String username;
    private String password;

    public User() {

    }

    public User(long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}