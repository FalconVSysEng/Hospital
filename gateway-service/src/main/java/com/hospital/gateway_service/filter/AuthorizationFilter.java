package com.hospital.gateway_service.filter;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
// import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
// import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class AuthorizationFilter implements GatewayFilter {

  private final Set<String> requiredAuthorities;
  private final boolean requireAll;

  private AuthorizationFilter(Set<String> requiredAuthorities, boolean requireAll) {
    this.requiredAuthorities = requiredAuthorities;
    this.requireAll = requireAll;
  }

  public static AuthorizationFilter ofAny(String... authorities) {
    return new AuthorizationFilter(toSet(authorities), false);
  }

  public static AuthorizationFilter ofAll(String... authorities) {
    return new AuthorizationFilter(toSet(authorities), true);
  }

  private static Set<String> toSet(String... arr) {
    if (arr == null || arr.length == 0)
      return Collections.emptySet();
    return Arrays.stream(arr)
        .filter(StringUtils::hasText)
        .collect(Collectors.toSet());
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    if (requiredAuthorities.isEmpty()) {
      log.debug("Ruta pública a nivel de Autorización: {}. Permitiendo el paso.",
          getPath(exchange));
      return chain.filter(exchange);
    }

    List<String> userAuthorities = extractAuthoritiesFromRequest(exchange.getRequest());

    if (userAuthorities.isEmpty()) {
      log.debug("No se encontraron authorities en el header para ruta: {}", getPath(exchange));
      return buildErrorResponse(exchange, HttpStatus.UNAUTHORIZED);
    }

    if (isAccessGranted(userAuthorities)) {
      log.debug("Acceso permitido a ruta: {} para authorities: {}",
          getPath(exchange), userAuthorities);
      return chain.filter(exchange);
    }

    log.debug(
        "Acceso denegado. Ruta: {} - Authorities del usuario: {} - Authorities requeridas: {} (modo: {})",
        getPath(exchange), userAuthorities, requiredAuthorities, requireAll ? "ALL" : "ANY");

    return buildErrorResponse(exchange, HttpStatus.FORBIDDEN);
  }

  private boolean isAccessGranted(List<String> userAuthorities) {
    Set<String> userAuthSet = new HashSet<>(userAuthorities);
    return requireAll
        ? userAuthSet.containsAll(requiredAuthorities)
        : userAuthSet.stream().anyMatch(requiredAuthorities::contains);
  }

  private List<String> extractAuthoritiesFromRequest(ServerHttpRequest request) {
    String header = request.getHeaders().getFirst("X-User-Authorities");
    if (header == null || !StringUtils.hasText(header)) {
      return Collections.emptyList();
    }
    return Arrays.stream(header.split(","))
        .map(String::trim)
        .filter(StringUtils::hasText)
        .collect(Collectors.toList());
  }

  private String getPath(ServerWebExchange exchange) {
    return exchange.getRequest().getURI().getPath();
  }

  private Mono<Void> buildErrorResponse(ServerWebExchange exchange, HttpStatus status) {
    exchange.getResponse().setStatusCode(status);
    return exchange.getResponse().setComplete();
  }

  // @Component
  // public static class Config {

  //   @Value("${auth.enabled:false}")
  //   private boolean defaultRequireAll;

  //   @Bean
  //   public AuthorizationFilter defaultAuthorizationFilter() {
  //     log.info("Creating default AuthorizationFilter with requireAll={}", defaultRequireAll);
  //     return new AuthorizationFilter(Collections.emptySet(), defaultRequireAll);
  //   }
  // }
}