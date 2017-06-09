package br.com.raphaelneves.filters;

import br.com.raphaelneves.annotations.Jwt;
import br.com.raphaelneves.models.User;
import br.com.raphaelneves.services.AuthorizerService;
import br.com.raphaelneves.services.UserService;
import br.com.raphaelneves.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.util.Date;

@Jwt
@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtRequestFilter implements ContainerRequestFilter{

    private UserService userService = new UserService();
    private ContainerRequestContext requestContext;

    public void filter(ContainerRequestContext requestContext) {
        this.requestContext = requestContext;
        SecurityContext originalContext = requestContext.getSecurityContext();
        String header = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if(header == null || header.split(" ").length != 2 || !header.startsWith("Bearer ")){
            requestContext.setSecurityContext(new AuthorizerService(null, null, originalContext.isSecure()));
        }else{
            String token = JwtUtil.extractToken(header);
            Jws<Claims> claims = decodeToken(token);
            verifyTokenExirationDate(claims.getBody());
            requestContext.setSecurityContext(validateUserByToken(Long.valueOf(claims.getBody().getId()), originalContext));
        }
    }

    private void invalidateAccess() {
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }

    private void verifyTokenExirationDate(Claims claims){
        if(new Date().after(claims.getExpiration())){
            throw new JwtException("Token has expired in " + claims.getExpiration());
        }
    }

    private User getUserByClaimId(Long id){
        return userService.find(id);
    }

    private Jws<Claims> decodeToken(String token){
        Jws<Claims> claimsJws = null;
        try{
            claimsJws = JwtUtil.decodeToken(token);
        }catch (Exception e){
            invalidateAccess();
        }
        return claimsJws;
    }

    private AuthorizerService validateUserByToken(Long id, SecurityContext context){
        User user = getUserByClaimId(id);
        return new AuthorizerService(user.getRoles(), user, context.isSecure());
    }
}
