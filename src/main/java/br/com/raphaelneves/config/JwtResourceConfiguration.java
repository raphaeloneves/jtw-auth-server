package br.com.raphaelneves.config;

import br.com.raphaelneves.services.interfaces.JwtConfiguration;
import br.com.raphaelneves.utils.PropertieLoader;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import javax.ws.rs.ApplicationPath;

/**
 * Created by raphaeloneves on 08/06/2017.
 */
@ApplicationPath("/")
public class JwtResourceConfiguration extends ResourceConfig implements JwtConfiguration{

    public JwtResourceConfiguration(){
        PropertieLoader loader = new PropertieLoader(PROPERTIE_FILE_NAME);
        packages((String)loader.get(BASE_PACKAGE));
        register(RolesAllowedDynamicFeature.class);
        register(JacksonFeature.class);
    }
}
