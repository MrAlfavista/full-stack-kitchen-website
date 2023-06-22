package com.testproject.demo.model;

import jakarta.persistence.*;


@Entity
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = "login"))
public class User {

    private int id;
    private String login;
    private String passwordSha;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordSha() {
        return passwordSha;
    }

    public void setPasswordSha(String passwordSha) {
        this.passwordSha = passwordSha;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

}
