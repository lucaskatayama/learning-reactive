package com.lucaskatayama.reactive.controllers;


import com.lucaskatayama.reactive.domain.Person;
import com.lucaskatayama.reactive.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;


@CrossOrigin(allowCredentials = "true")
@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @GetMapping("")
    public Flux<Person> getPerson() {
        return personRepository.findAll();
    }

    @PostMapping("")
    public Mono<Person> savePerson(@RequestBody Person p) {

        return personRepository.save(p);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Person>> getPersonById(@PathVariable String id) {
        return personRepository.findById(id)
                .map(found -> ResponseEntity.ok(found))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Person> streamJohnDoe() {
        return personRepository.findAllByName("John Doe").delayElements(Duration.ofSeconds(5));
    }
}
