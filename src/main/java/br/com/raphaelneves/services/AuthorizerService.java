package br.com.raphaelneves.services;

import br.com.raphaelneves.models.Role;
import br.com.raphaelneves.models.User;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.List;

/**
 * Created by raphaeloneves on 09/06/2017.
 */
public class AuthorizerService implements SecurityContext {

    private List<Role> roles;
    private User user;
    private boolean isSecure;

    public AuthorizerService(List<Role> roles, User user, boolean isSecure) {
        this.roles = roles;
        this.user = user;
        this.isSecure = isSecure;
    }

    public Principal getUserPrincipal() {
        if (user != null)
            return user;
        return null;
    }

    public boolean isUserInRole(String role) {
        if (roles != null)
            return roles.contains(new Role(role));
        return false;
    }

    public boolean isSecure() {
        return isSecure;
    }

    public String getAuthenticationScheme() {
        return "JWT";
    }
}
