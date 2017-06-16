package br.com.raphaelneves.config;

import br.com.raphaelneves.annotations.JwtFilter;
import br.com.raphaelneves.models.User;
import br.com.raphaelneves.models.UserLogged;
import br.com.raphaelneves.services.UserService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 * Created by raphaeloneves on 13/06/2017.
 */
@Path("users")
@JwtFilter
public class ClientRest {

    @EJB
    private UserService service;


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login")
    @PermitAll
    public Response login(User user, @Context UriInfo uriInfo){
        UserLogged logged = service.login(user, uriInfo);
        if(logged != null)
            return Response.status(Response.Status.ACCEPTED)
                    .entity(logged)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + logged.getToken())
                    .header(HttpHeaders.EXPIRES, logged.getExpiresAt())
                    .build();
        else
            return Response.status(Response.Status.UNAUTHORIZED).entity(new String("User not found.")).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/admin")
    @RolesAllowed("ADMIN")
    public Response getAdminMessage(){
        return Response.status(Response.Status.ACCEPTED).entity(new String("Admin profile")).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/guest")
    @RolesAllowed("GUEST")
    public Response getGestMessage(){
        return Response.status(Response.Status.ACCEPTED).entity(new String("Guest profile")).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/both")
    @RolesAllowed({"ADMIN", "GUEST"})
    public Response getBothMessage(){
        return Response.status(Response.Status.ACCEPTED).entity(new String("Guest profile")).build();
    }

}
