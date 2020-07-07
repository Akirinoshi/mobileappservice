package com.belkov.mobileappservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "Users")
public class User {

    @Id
    private UUID uuid;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    private String error;

    public User(@JsonProperty("login") String login,
                @JsonProperty("password") String password) {
        this.login = login;
        this.password = password;
    }

    public User(){};

    public UUID getUuid() {
        return uuid;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getError() {
        return error;
    }

    public void setError(String comment) {
        this.error = comment;
    }
}
