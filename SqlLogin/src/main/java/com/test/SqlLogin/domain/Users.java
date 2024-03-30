package com.test.SqlLogin.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Users {
    @Id
    private String id;
    private String pw;
    private String name;
    private boolean isAdmin;

    public Users() {
    }

    public Users(String id, String name, boolean isAdmin) {
        this.id = id;
        this.name = name;
        this.isAdmin = isAdmin;
    }
}
