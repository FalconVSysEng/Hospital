package com.hospital.gateway_service.filter;

import java.util.List;
import java.util.Set;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import com.hospital.gateway_service.service.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

  private final JwtService jwtService;
  private final AntPathMatcher pathMatcher = new AntPathMatcher();
  private static final Set<String> OPEN_PATHS = Set.of("/api/auth/login");

  @Override
  public int getOrder() {
    return -100;
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    String path = request.getURI().getPath();

    if (isOpenPath(path)) {
      log.debug("Ruta pública accedida: {}", path);
      return chain.filter(exchange);
    }

    var tokenOpt = jwtService.getToken(request);
    if (tokenOpt.isEmpty()) {
      return unauthorizedResponse(exchange, "No se encontró token de autorización", path);
    }

    String token = tokenOpt.get();
    if (!jwtService.isTokenValid(token)) {
      log.debug("Token inválido o expirado para ruta: {} (token: {})", path, redact(token));
      return unauthorizedResponse(exchange, "Token inválido o expirado", path);
    }

    ServerHttpRequest enrichedRequest = enrichRequestWithUserData(request, token);
    if (enrichedRequest == null) {
      log.error("No se pudo enriquecer la solicitud (resultado nulo) para la ruta: {}", path);
      return unauthorizedResponse(exchange, "Error al procesar la solicitud", path);
    }
    return chain.filter(exchange.mutate().request(enrichedRequest).build());
  }

  private ServerHttpRequest enrichRequestWithUserData(ServerHttpRequest request, String token) {
    String subject = jwtService.getSubject(token);
    List<String> authorities = jwtService.getAuthorities(token);

    log.debug("Usuario autenticado: {} con authorities: {}", subject, authorities);

    String authoritiesHeader = (authorities == null || authorities.isEmpty())
        ? ""
        : String.join(",", authorities);

    return request.mutate()
        .header("X-User-DNI", subject != null ? subject : "")
        .header("X-User-Authorities", authoritiesHeader)
        .build();
  }

  private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String message, String path) {
    log.debug("{} para ruta: {}", message, path);
    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
    return exchange.getResponse().setComplete();
  }

  private boolean isOpenPath(String path) {
    if (path == null) {
      return false;
    }
    for (String openPath : OPEN_PATHS) {
      if (openPath != null && pathMatcher.match(openPath, path)) {
        return true;
      }
    }
    return false;
  }

  private String redact(String token) {
    if (!StringUtils.hasText(token))
      return "****";
    int len = token.length();
    if (len <= 8)
      return "****";
    return "****" + token.substring(len - 6);
  }
}