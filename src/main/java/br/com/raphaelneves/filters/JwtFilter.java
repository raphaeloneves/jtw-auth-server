package br.com.raphaelneves.filters;

import br.com.raphaelneves.annotations.Jwt;
import br.com.raphaelneves.models.Role;
import br.com.raphaelneves.models.User;
import br.com.raphaelneves.services.AuthorizerService;
import br.com.raphaelneves.services.UserService;
import br.com.raphaelneves.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import javax.annotation.Priority;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.ArrayList;

@Jwt
@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtFilter implements ContainerRequestFilter, ContainerResponseFilter{

    private UserService userService = new UserService();

    public void filter(ContainerRequestContext requestContext) {
        SecurityContext originalContext = requestContext.getSecurityContext();
        String header = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        AuthorizerService auth = null;

        if(header == null || header.split(" ").length != 2){
            auth = new AuthorizerService(new ArrayList<Role>(), new User(), originalContext.isSecure());
        }else{
            String token = JwtUtil.extractToken(header);
            Jws<Claims> claims = null;
            try{
                claims = JwtUtil.decodeToken(token);
            }catch (Exception e){
                invalidateAccess(requestContext);
            }
            User user = userService.find(claims.getBody().getSubject());
            auth = new AuthorizerService(user.getRoles(), user, originalContext.isSecure());
            requestContext.setSecurityContext(auth);
        }
    }

    private void invalidateAccess(ContainerRequestContext requestContext) {
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }

    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        containerResponseContext.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
        containerResponseContext.getHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        containerResponseContext.getHeaders().putSingle("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }
}
