package tn.esprit.gestiondesmanagers.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
  private static final String SECRET_KEY="Q245wxAyjl1LiJLaoOXZVGVjVp6q10y+mcuLanaQ4Lk43JNis0zO5n01uAHA3IaHvMeWjXmD9YM0UxAjdnfLQtTINz3BraqcUqfw1nyUivZU9k5ExqLf+kfuQvENRFm2AD72H34OptwCiQ7LyuBnB40t+ekRAfsdlxGfNe2HKHlk0YC4prhpkJmtQ6P7LKVVpPVW5G9bmdO7vm1zQWLp07stn6q+EBs7YJ8szrd8ZiMlw1LJqny3Y4n0j9nrClxF30B9ahDS4gF8e9XiwPW5865xubaz99jBio6mgqyK1G5Qya20bL3ZkKKFgJxjhgTGL2kqG12yDUpc67nOpHWNcQ==\n";
    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }

    public String generatetoken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }

    public String generateToken(
        Map<String, Object> extraClaims,
        UserDetails userDetails){
        return  Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username=extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
