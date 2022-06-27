package com.linkgem.domain.oauth;

import com.linkgem.domain.User.User;
import com.linkgem.domain.User.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenProvider {
  @Value("${jwt.Access}")
  private String accessKey;

  @Value("${jwt.Refresh}")
  private String refreshKey;

  private final UserRepository userRepository;

  private Key getAccessSignKey() {
    return Keys.hmacShaKeyFor(accessKey.getBytes(StandardCharsets.UTF_8));
  }

  private Key getRefreshSignKey() {
    return Keys.hmacShaKeyFor(refreshKey.getBytes(StandardCharsets.UTF_8));
  }


  public String createAccessToken(String email) {
    return Jwts.builder()
        .signWith(getAccessSignKey())
        .setSubject(email)
        .setIssuer("LINK_GEM")
        .setIssuedAt(new Date())
        .setExpiration(createAccessExpireDate())
        .compact();

  }

  public String createRefreshToken(String email) {
    return Jwts.builder()
        .signWith(getRefreshSignKey())
        .setSubject(email)
        .setIssuer("LINK_GEM")
        .setIssuedAt(new Date())
        .setExpiration(createRefreshExpireDate())
        .compact();

  }

  private Date createAccessExpireDate() {
    return Date.from(Instant.now().plus(1, ChronoUnit.HOURS));

  }

  private Date createRefreshExpireDate() {
    return Date.from(Instant.now().plus(7, ChronoUnit.DAYS));

  }

  private Claims getAccessTokenClaims(String token) {
    try {
      return Jwts.parserBuilder()
          .setSigningKey(getAccessSignKey())
          .build()
          .parseClaimsJws(token)
          .getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }

  private Claims getRefreshTokenClaims(String token) {
    try {
      return Jwts.parserBuilder()
          .setSigningKey(getRefreshSignKey())
          .build()
          .parseClaimsJws(token)
          .getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }
  public boolean isValidAccessToken(String token) throws Exception {
    System.out.println("isValidToken is : " +token);
    try {
      Claims accessClaims = getAccessTokenClaims(token);
      String email = accessClaims.getSubject();
      Optional<User> foundUser = userRepository.findByEmail(email);
      return foundUser.isPresent();
    } catch (JwtException | NullPointerException exception) {
      return false;
    }
  }

  public boolean isValidRefreshToken(String token) {
    try {
      Claims refreshTokenClaimsClaims = getRefreshTokenClaims(token);
      String email = refreshTokenClaimsClaims.getSubject();
      Optional<User> foundUser = userRepository.findByEmail(email);
      return foundUser.isPresent();
    } catch (ExpiredJwtException exception) {
      System.out.println("Token Expired UserID : " + exception.getClaims().getSubject());
      return false;
    } catch (JwtException exception) {
      System.out.println("Token Tampered");
      return false;
    } catch (NullPointerException exception) {
      System.out.println("Token is null");
      return false;
    }
  }

}
