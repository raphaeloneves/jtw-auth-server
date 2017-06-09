package br.com.raphaelneves.utils;

import br.com.raphaelneves.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.*;
import java.util.*;

/**
 * Created by raphaeloneves on 08/06/2017.
 */
public class JwtUtil {

    private static final String key = "5jifF5W18ml6pHC8WY3pkD";
    public static final String TOKEN_HEADER = "Authentication";
    private static final int TOKEN_EXPIRATION = 10;

    public static String createToken(User user, String issuer){
        Date now = new Date();
        return Jwts.builder().setSubject(user.getId())
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(getExpirationDate(now))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public static Jws<Claims> decodeToken(String token){
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token);
    }

    public static String extractToken(String authorizationHeader){
        return authorizationHeader.substring("Bearer ".length()).trim();
    }

    private static Date getExpirationDate(Date date){
        System.out.print(date);
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        int time = now.get(Calendar.MINUTE) + TOKEN_EXPIRATION;
        now.set(Calendar.MINUTE, time);
        System.out.print(now.getTime());
        return now.getTime();
    }

}
