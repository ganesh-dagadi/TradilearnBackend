package org.virtuconf.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VirtuConfGateway {
    public static void main(String[] args){
        SpringApplication.run(VirtuConfGateway.class , args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route("auth" , r->r.path("/auth/**")
                        .uri("http://localhost:9090/auth/")
                )
                .route("stockInfo" , r->r.path("/stockinfo/**")
                        .uri("http://localhost:9091/stockinfo/"))
                .build();

    }
}