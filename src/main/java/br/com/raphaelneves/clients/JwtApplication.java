package br.com.raphaelneves.clients;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Created by raphaeloneves on 08/06/2017.
 */
@ApplicationPath("/")
public class JwtApplication extends ResourceConfig {
    public JwtApplication(){
        packages("br.com.raphaelneves");
        register(RolesAllowedDynamicFeature.class);
        register(JacksonFeature.class);
    }
}
