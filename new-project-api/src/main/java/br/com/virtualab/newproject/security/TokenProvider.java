package br.com.virtualab.newproject.security;

import br.com.virtualab.newproject.util.DateTimeUtils;
import br.com.virtualab.newproject.util.EncryptBasic;
import br.com.virtualab.newproject.util.Log;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private String secretKey;
    private long tokenValidityInHours;
    private long tokenValidityInHoursForRememberMe;

    public TokenProvider() {
    }

    @PostConstruct
    public void init() {
        if (SecurityProperties.JWT_SECRET.trim().equals("")) {
            throw new RuntimeException("JWT secret não pode ser uma string vazia.");
        }
        this.secretKey = SecurityProperties.JWT_SECRET;
        this.tokenValidityInHours = SecurityProperties.JWT_TOKEN_VALIDITY_IN_HOURS;
        this.tokenValidityInHoursForRememberMe = SecurityProperties.JWT_TOKEN_VALIDITY_IN_HOURS_FOR_REMEMBER_ME;
    }

    public String createToken(Authentication authentication, Boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        LocalDateTime validity = DateTimeUtils.getLocalDateTimeNow();
        if (rememberMe) {
            validity = validity.plusHours(this.tokenValidityInHoursForRememberMe);
        } else {
            validity = validity.plusHours(this.tokenValidityInHours);
        }
        return EncryptBasic.encrypt(Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setExpiration(Date.from(validity.atZone(DateTimeUtils.getZoneIdSaoPaulo()).toInstant()))
                .compact());
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            Log.info(getClass(), "Invalid JWT signature.");
            Log.trace(getClass(), "Invalid JWT signature trace: {}", e);
        } catch (MalformedJwtException e) {
            Log.info(getClass(), "Invalid JWT token.");
            Log.trace(getClass(), "Invalid JWT token trace: {}", e);
        } catch (ExpiredJwtException e) {
            Log.info(getClass(), "Expired JWT token.");
            Log.trace(getClass(), "Expired JWT token trace: {}", e);
        } catch (UnsupportedJwtException e) {
            Log.info(getClass(), "Unsupported JWT token.");
            Log.trace(getClass(), "Unsupported JWT token trace: {}", e);
        } catch (IllegalArgumentException e) {
            Log.info(getClass(), "JWT token compact of handler are invalid.");
            Log.trace(getClass(), "JWT token compact of handler are invalid trace: {}", e);
        }
        return false;
    }

}
