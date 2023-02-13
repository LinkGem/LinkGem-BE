package com.linkgem.domain.user.provider;

import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenProvider {

  @Value("${jwt.Access}")
  private String accessKey;

  @Value("${jwt.Refresh}")
  private String refreshKey;

  private Key getAccessSignKey() {
    return Keys.hmacShaKeyFor(accessKey.getBytes(StandardCharsets.UTF_8));
  }

  private Key getRefreshSignKey() {
    return Keys.hmacShaKeyFor(refreshKey.getBytes(StandardCharsets.UTF_8));
  }


  public String createAccessToken(String userId) {
    return Jwts.builder()
        .signWith(getAccessSignKey())
        .setSubject(userId)
        .setIssuer("LINK_GEM")
        .setIssuedAt(new Date())
        .setExpiration(createAccessExpireDate())
        .compact();

  }

  public String createRefreshToken(String userId) {
    return Jwts.builder()
        .signWith(getRefreshSignKey())
        .setSubject(userId)
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
    return Jwts.parserBuilder()
        .setSigningKey(getAccessSignKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Claims getRefreshTokenClaims(String refreshToken) {
    return Jwts.parserBuilder()
        .setSigningKey(getRefreshSignKey())
        .build()
        .parseClaimsJws(refreshToken)
        .getBody();

  }

  public String isValidAccessToken(String accessToken) {
    try {
      Claims accessClaims = getAccessTokenClaims(accessToken);
      return accessClaims.getSubject();
    } catch (ExpiredJwtException exception) {
      throw new BusinessException(ErrorCode.ACCESS_TOKEN_EXPIRED);
    } catch (JwtException | NullPointerException exception) {
      throw new BusinessException(ErrorCode.ACCESS_TOKEN_NOT_VALID);
    }
  }

  public String isValidRefreshToken(String token) {
    try {
      Claims refreshTokenClaims = getRefreshTokenClaims(token);
      return refreshTokenClaims.getSubject();
    } catch (NullPointerException | JwtException exception) {
      throw new BusinessException(ErrorCode.REFRESH_TOKEN_NOT_VALID);
    }
  }

  public boolean isExpiredAccessToken(String accessToken) {
    try {
      getAccessTokenClaims(accessToken);
      return false;
    } catch (ExpiredJwtException exception) {
      return true;
    } catch (Exception e) {
      throw new BusinessException(ErrorCode.ACCESS_TOKEN_NOT_VALID);
    }
  }


}
