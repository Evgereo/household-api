package world.evgereo.household.householdapi.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import world.evgereo.household.householdapi.entity.User;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${token.signing.key}")
    private String accessKey;
    
    @Value("${token.signing.time}")
    private long accessTime;

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessTime))
                .signWith(getSecretKey(accessKey)).compact();
    }
    
    public Long getAccessId(String token) {
        return Long.valueOf(getAllClaims(token, accessKey).getSubject());
    }

    private SecretKey getSecretKey(String key) {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims getAllClaims(String token, String key) {
        return Jwts.parser()
                .verifyWith(getSecretKey(key))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}