package com.hospital.auth_service.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.hospital.auth_service.model.Employee;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

  private final SecretKey signingKey;
  private final Duration accessTokenExpiration;
  private final Duration refreshTokenExpiration;

  public JwtService(
      @Value("${jwt.secret}") String secretKeyString,
      @Value("${jwt.access-token.expiration}") Duration accessTokenExpiration,
      @Value("${jwt.refresh-token.expiration}") Duration refreshTokenExpiration) {

    this.signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKeyString));
    this.accessTokenExpiration = accessTokenExpiration;
    this.refreshTokenExpiration = refreshTokenExpiration;
  }

  public String generateAccessToken(Employee employee) {
    return generateToken(employee, accessTokenExpiration);
  }

  public String generateRefreshToken(Employee employee) {
    return generateToken(employee, refreshTokenExpiration);
  }

  private String generateToken(Employee employee, Duration expiration) {
    Instant now = Instant.now();
    Instant expiryInstant = now.plus(expiration);
    List<String> authorities = employee.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());

    return Jwts.builder()
        .header().type("JWT").and()
        .claims()
        .subject(employee.getUsername())
        .issuedAt(Date.from(now))
        .expiration(Date.from(expiryInstant))
        .add("authorities", authorities)
        .and()
        .signWith(signingKey, SIG.HS256)
        .compact();
  }
}