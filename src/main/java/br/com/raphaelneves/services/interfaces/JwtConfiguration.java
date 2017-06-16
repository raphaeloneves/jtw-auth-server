package br.com.raphaelneves.services.interfaces;

/**
 * Created by raphaeloneves on 13/06/2017.
 */
public interface JwtConfiguration {

    String PROPERTIE_FILE_NAME = "jwt-configuration.properties";
    String API_KEY = "apiKey";
    String EXPIRATION_TIME_IN_MINUTES = "expirationTimeInMinutes";
    String BASE_PACKAGE = "basePackage";
    String AUTHENTICATION_PREFIX = "Bearer ";
}
