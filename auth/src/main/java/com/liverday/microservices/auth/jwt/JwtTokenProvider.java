package com.liverday.microservices.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Service
@SuppressWarnings("unused")
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key}")
    private String secret;

    @Value("${security.jwt.token.expires-in}")
    private Long expiresIn;

    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtTokenProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String create(String subject, Map<String, Object> data) {
        Claims claims = Jwts.claims(data).setSubject(subject);

        Date now = new Date();
        Date expiresDate = new Date(now.getTime() + expiresIn);
        Key secretKey = getKeyFromSecret();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiresDate)
                .signWith(secretKey)
                .compact();

    }

    private Key getKeyFromSecret() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(getKeyFromSecret()).build().parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean validateClaims(Claims claims) {
        try {
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    public Authentication getAuthentication(Claims claims) {
        String userName = claims.getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

        if (userDetails == null)
            return null;

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
