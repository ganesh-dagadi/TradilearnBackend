package org.virtuconf.gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;
import org.virtuconf.gateway.dto.PersonDataDTO;
import reactor.core.publisher.Mono;
@Component
public class AuthenticatorMiddlewareFilter implements GatewayFilter {
    private final ModifyRequestBodyGatewayFilterFactory factory;

    public AuthenticatorMiddlewareFilter(ModifyRequestBodyGatewayFilterFactory factory) {
        this.factory = factory;
    }
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String bearerToken = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
        System.out.print(bearerToken);
        WebClient webClient = WebClient.create("http://localhost:8080");
        return webClient
                .get()
                .uri("/auth/token/userdata")
                .header(HttpHeaders.AUTHORIZATION , bearerToken)
                .retrieve()
                .bodyToMono(PersonDataDTO.class)
                .flatMap(response->{
                    exchange.getRequest().mutate()
                            .header("AUTH_USERNAME" , response.getUsername())
                            .header("AUTH_EMAIL" , response.getEmail())
                            .header("AUTH_USERID" , response.getId().toString())
                            .header("AUTH_VERIFIED" , response.getIsEmailVerified().toString())
                            .header("AUTH_ACTIVE" , response.getIsActive().toString())
                            .build();
                    return chain.filter(exchange);
                })
                .onErrorResume(WebClientResponseException.class, ex -> {
                    // Handle WebClientResponseException (e.g., authentication failure) here
                    exchange.getResponse().setStatusCode(ex.getStatusCode());
                    // Set headers in the response
                    exchange.getResponse().getHeaders().add("Custom-Header", "header-value");
                    // Set other headers if needed
                    return exchange.getResponse().writeWith(
                            Mono.just(exchange.getResponse().bufferFactory().wrap(ex.getResponseBodyAsByteArray()))
                    );
                })
                .onErrorResume(Exception.class, ex -> {
                    // Handle other exceptions here
                    exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                    return exchange.getResponse().setComplete();
                });
    }
}

