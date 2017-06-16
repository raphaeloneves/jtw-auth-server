package br.com.raphaelneves.services;

import br.com.raphaelneves.models.User;
import br.com.raphaelneves.services.interfaces.JwtConfiguration;
import br.com.raphaelneves.services.interfaces.JwtManager;
import br.com.raphaelneves.utils.PropertieLoader;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.ejb.Stateless;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by raphaeloneves on 13/06/2017.
 */
@Stateless
public class JwtService implements JwtManager, JwtConfiguration {

    private PropertieLoader loader;
    private static String key;
    private static Integer expirationTimeinMinutes;

    public JwtService(){
        loader = new PropertieLoader(PROPERTIE_FILE_NAME);
        expirationTimeinMinutes = Integer.parseInt(loader.get(EXPIRATION_TIME_IN_MINUTES).toString());
        key = (String) loader.get(API_KEY);
    }

    @Override
    public String create(User user, String issuer) {
        Date now = new Date();
        return Jwts.builder().setSubject(user.getUsername())
                .setIssuer(issuer)
                .setId(user.getId().toString())
                .setExpiration(generateExpirationDate(now))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    @Override
    public Jws<Claims> decode(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token);
    }

    @Override
    public String extract(String header) {
        return header.substring(AUTHENTICATION_PREFIX.length()).trim();
    }

    @Override
    public void revoke(String token) {
        Jws<Claims> claims = decode(token);
        claims.getBody().setExpiration(new Date());
    }

    @Override
    public Date generateExpirationDate(Date creationDate) {
        Calendar now = Calendar.getInstance();
        int minutes = now.get(Calendar.MINUTE) + expirationTimeinMinutes;
        now.set(Calendar.MINUTE, minutes);
        return now.getTime();
    }

    @Override
    public String getAuthenticationPrefix(){
        return AUTHENTICATION_PREFIX;
    }
}
