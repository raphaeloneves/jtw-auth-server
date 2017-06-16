package br.com.raphaelneves.services;

import br.com.raphaelneves.models.Role;
import br.com.raphaelneves.models.User;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.List;

/**
 * Created by raphaeloneves on 13/06/2017.
 */
public class Authorizer implements SecurityContext {

    private User user;
    private List<Role> roles;
    private boolean isSecure;
    private static final String AUTHENTICATION_SCHEME = "JWT";

    public Authorizer(User user, List<Role> roles, boolean isSecure) {
        this.user = user;
        this.roles = roles;
        this.isSecure = isSecure;
    }

    @Override
    public Principal getUserPrincipal() {
        return user;
    }

    @Override
    public boolean isUserInRole(String role) {
        if(role != null)
            return roles.contains(new Role(role));
        return false;
    }

    @Override
    public boolean isSecure() {
        return isSecure;
    }

    @Override
    public String getAuthenticationScheme() {
        return AUTHENTICATION_SCHEME;
    }
}
