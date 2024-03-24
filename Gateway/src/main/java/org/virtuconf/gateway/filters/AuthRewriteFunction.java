package org.virtuconf.gateway.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import org.springframework.web.server.ServerWebExchange;
import org.virtuconf.gateway.dto.PersonDataDTO;
import reactor.core.publisher.Mono;

public class AuthRewriteFunction implements RewriteFunction<String , String>{
    private final PersonDataDTO values;

    public AuthRewriteFunction(PersonDataDTO person) {
        this.values = person;
    }
    @Override
    public Publisher<String> apply(ServerWebExchange serverWebExchange, String oldBody) {
        /* do things here */
        /* example: */
        try {
            String newBody = new ObjectMapper().writeValueAsString(values);
            return Mono.just(newBody);
        } catch (Exception e) {
            /* error parsing values to json, do something else */
            return Mono.just(oldBody);
        }
    }
}