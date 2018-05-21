package com.lucaskatayama.reactive.controllers;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class CounterConfig {
    private static final String ENDPOINT = "/counter";

    @Bean
    public RouterFunction<ServerResponse> counterRouter(final CounterHandler handler) {
        return route(GET(ENDPOINT + "/stream"), handler::streamAllTweets);
    }
}
