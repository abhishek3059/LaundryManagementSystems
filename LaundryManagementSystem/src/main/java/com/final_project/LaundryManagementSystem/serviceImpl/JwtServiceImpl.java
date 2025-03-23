package com.final_project.LaundryManagementSystem.serviceImpl;

import com.final_project.LaundryManagementSystem.model.User;
import com.final_project.LaundryManagementSystem.repo.UserRepo;
import com.final_project.LaundryManagementSystem.service.JwtService;
import com.final_project.LaundryManagementSystem.service.SecurityService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Data
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.secret.key}")
    private String secretKey;
    @Value("${jwt.token.expiration}")
    private long expirationTime;
    private final ApplicationContext applicationContext;
    private final UserRepo userRepo;
    @Override
    public String generateToken(String username) {
        Map<String,Object> claims = new HashMap<>();
        SecurityService service = applicationContext.getBean(SecurityService.class);
        UserDetails userDetails = service.loadUserByUsername(username);
        claims.put("username",userDetails.getUsername());
        User user = userRepo.findByUsername(username).orElseThrow(()->new BadCredentialsException("User not found"));
        claims.put("userId",user.getId());
        claims.put("user email" , user.getEmail());
        claims.put("roles",userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet()));

        return Jwts.builder()
                    .claims().add(claims)
                    .subject(username)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + expirationTime))
                    .and()
                    .signWith(getSigningKey())
                    .compact();
    }
    private SecretKey getSigningKey(){
        byte[] key = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(key);
    }


    @Override
    public String extractUserName(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T>T extractClaims(String token, Function<Claims,T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return(userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token,Claims::getExpiration);
    }

    @Override
    public boolean validateToken(String token) {
        try{
            Claims claims = extractAllClaims(token);
            return !isTokenExpired(token);
        }
        catch (Exception e){
            throw new BadCredentialsException("Invalid token");
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSigningKey())
                .build().parseSignedClaims(token).getPayload();
    }
}
