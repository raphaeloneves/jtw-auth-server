
package br.com.raphaelneves.filters;

import br.com.raphaelneves.annotations.JwtFilter;
import br.com.raphaelneves.models.User;
import br.com.raphaelneves.repositories.UserRepository;
import br.com.raphaelneves.services.Authorizer;
import br.com.raphaelneves.services.UserService;
import br.com.raphaelneves.services.interfaces.JwtManager;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import javax.annotation.Priority;
import javax.ejb.EJB;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

@JwtFilter
@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtRequestFilter implements ContainerRequestFilter{

    @EJB
    private UserRepository repository;

    @EJB
    private JwtManager jwt;
    private ContainerRequestContext requestContext;
    private SecurityContext originalContext;

    public void filter(ContainerRequestContext requestContext) {
        this.requestContext = requestContext;
        originalContext = requestContext.getSecurityContext();
        String header = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if(header == null || header.split(" ").length != 2 || !header.startsWith(jwt.getAuthenticationPrefix())){
            requestContext.setSecurityContext(new Authorizer(null, null, originalContext.isSecure()));
        }else{
            Jws<Claims> claims = jwt.decode(jwt.extract(header));
            requestContext.setSecurityContext(validateUserByToken(Long.valueOf(claims.getBody().getId())));
        }
    }

    private Authorizer validateUserByToken(Long id){
        User user = repository.find(id);
        return new Authorizer(user, user.getRoles(), originalContext.isSecure());
    }
}

