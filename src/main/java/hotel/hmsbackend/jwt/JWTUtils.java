package hotel.hmsbackend.jwt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Service
public class JWTUtils {
    private String secret ="test123";
    public Date extractExpiration(String token){
        return extractClamis(token, Claims::getExpiration);
    }
    public String extractUsername(String token){
        return extractClamis(token, Claims::getSubject);
    }
    public <T> T extractClamis(String token, Function<Claims, T> claimsResolver){
        final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public Claims extractAllClaims(String token){
            return Jwts.parser().setSigningKey(token).parseClaimsJws(token).getBody();
    }
    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    public String generateToken(String username, String role){
        Map<String ,Object> claims = new HashMap<>();
        claims.put("role", role);
       return createToken(claims, username);
    }

    // creating jwt token
    private String createToken(Map<String, Object> claims, String subject){
        return  Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+200*60*60*10))
                .signWith(SignatureAlgorithm.PS256,secret).compact();
    }
    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }
}
