package br.com.raphaelneves.clients;

import br.com.raphaelneves.annotations.Jwt;
import br.com.raphaelneves.models.User;
import br.com.raphaelneves.models.UserLogged;
import br.com.raphaelneves.services.UserService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("users")
@Jwt
public class UserClient {

    private static UserService service = new UserService();

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response login(@Context UriInfo uriInfo, User user){
        UserLogged logged = service.login(user, uriInfo);
        if(logged != null){
            return Response.status(Response.Status.ACCEPTED)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + logged.getToken())
                    .entity(logged)
                    .build();
        }
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity("Invalid credentials! Please, try again.")
                .type(MediaType.TEXT_PLAIN_TYPE).build();
    }

    @GET
    @Path("/msg")
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed("GUEST")
    public String msg(){
        return "Perfil guest!";
    }

    @GET
    @Path("/msg/both")
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed({"GUEST", "ADMIN"})
    public String msgAllProfiles(){
        return "Perfil guest ou admin!";
    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    public Response findAll(){
        return Response.status(Response.Status.ACCEPTED)
                .entity(service.findAll()).build();
    }
}
