package br.com.raphaelneves.models;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User implements Principal{

    private String id;
    private String name;
    private String username;
    private String password;
    private List<Role> roles;

    public User(){
        id = UUID.randomUUID().toString();
        roles = new ArrayList<Role>();
    }

    @Override
    public boolean equals(Object o) {
        User user = (User) o;
        return ((User) o).getId().equals(this.getId());
    }

    @Override
    public int hashCode() {
        int result = getUsername().hashCode();
        result = 31 * result + getPassword().hashCode();
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
