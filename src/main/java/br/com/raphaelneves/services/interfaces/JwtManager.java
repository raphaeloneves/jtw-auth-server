package br.com.raphaelneves.services.interfaces;

import br.com.raphaelneves.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import javax.ejb.Local;
import java.util.Date;

/**
 * Created by raphaeloneves on 13/06/2017.
 */
@Local
public interface JwtManager {

    String create(User user, String issuer);
    Jws<Claims> decode(String token);
    String extract(String header);
    void revoke(String token);
    Date generateExpirationDate(Date creationDate);
    String getAuthenticationPrefix();

}
