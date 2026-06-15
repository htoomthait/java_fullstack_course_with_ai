package net.htoomaungthait.buynowdotcom.security.jwt;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import net.htoomaungthait.buynowdotcom.security.user.ShopUserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtUtils {

    @Value("${auth.token.jwtSecret}")
    private String jwtSecret;

    @Value("${auth.token.jwtExpirationMs}")
    private long expirationTime;

    @Value("${auth.token.jwtRefreshExpirationMs}")
    private long refreshExpirationTime;

    public String generateAccessTokenForUser(Authentication authentication){

        ShopUserDetails userPrincipal = (ShopUserDetails) authentication.getPrincipal();

        List<String> roles = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());



        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(calculateExpirationDate(String.valueOf(expirationTime)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();

    }

    public String generateRefreshToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(calculateExpirationDate(String.valueOf(refreshExpirationTime)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            // Log the exception or handle it as needed
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new JwtException("Invalid JWT token: " + e.getMessage());
        }
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    private Date calculateExpirationDate(String expiratonTimeString) {
        long expirationTimeMs = Long.parseLong(expiratonTimeString);
        return new Date((new Date()).getTime() + expirationTimeMs);
    }
}
