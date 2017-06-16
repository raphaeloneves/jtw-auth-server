package br.com.raphaelneves.models;

import java.util.Date;

/**
 * Created by raphaeloneves on 08/06/2017.
 */
public class UserLogged {
    private String username;
    private String token;
    private Date lastLogin;
    private Date expiresAt;

    public UserLogged(String username, String token, Date lastLogin, Date expiresAt) {
        this.username = username;
        this.token = token;
        this.lastLogin = lastLogin;
        this.expiresAt = expiresAt;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public UserLogged(){}

    public void setUsername(String username) {
        this.username = username;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    public Date getLastLogin() {
        return lastLogin;
    }
}
