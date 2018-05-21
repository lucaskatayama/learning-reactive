package com.lucaskatayama.reactive.controllers;


import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class CounterHandler {
    public Mono<ServerResponse> streamAllTweets(ServerRequest request) {
        Flux<Integer> counter = Flux.create((FluxSink<Integer> sink) -> SlowCounter.count(sink, 10));
        return ok().contentType(MediaType.TEXT_EVENT_STREAM).body(counter, Integer.class);
    }
}
