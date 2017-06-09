package br.com.raphaelneves.filters;

import br.com.raphaelneves.annotations.Jwt;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Created by raphaeloneves on 09/06/2017.
 */
@Jwt
@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtResponseFilter implements ContainerResponseFilter {
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        containerResponseContext.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
        containerResponseContext.getHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        containerResponseContext.getHeaders().putSingle("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }
}
