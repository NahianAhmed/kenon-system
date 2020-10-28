package com.kenon.kenonapp.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "m_user_password")
public class PasswordModel {
    @Id
    @Column(name = "社員番号", nullable = false)
    private String userId;
    @Column(name = "トークン", nullable = true,updatable = true)
    private String token;
    @Column(name = "パスワード", nullable = false, updatable = false)
    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
