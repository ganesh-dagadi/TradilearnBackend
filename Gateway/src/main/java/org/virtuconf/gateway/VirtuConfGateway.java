package org.virtuconf.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.virtuconf.gateway.filters.AuthenticatorMiddlewareFilter;

@SpringBootApplication
public class VirtuConfGateway {
    @Autowired
    AuthenticatorMiddlewareFilter authFilter;
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
                .route("stockPrice" , r->r.path("/stockprice/**")
                        .uri("http://localhost:9092/stockinfo/")
                )
                .route("paperTrader", r->r.path("/papertrader/**")
                        .filters(f-> f.filter(authFilter))
                        .uri("http://localhost:9093/papertrader/**")
                )
                .build();

    }
}
