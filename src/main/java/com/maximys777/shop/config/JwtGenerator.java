package com.maximys777.shop.config;

import com.maximys777.shop.config.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtGenerator {
    private final JwtProperties jwtProperties;

    public JwtGenerator(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public String generateJwt(Authentication auth) {
        String email = auth.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + jwtProperties.getExpiration());

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecret())
                .compact();
    }

    public String getNameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtProperties.getSecret())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try{
            Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("JWT токен истёк.");
        }
    }
}