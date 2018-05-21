package com.lucaskatayama.reactive.repositories;

import com.lucaskatayama.reactive.domain.Person;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PersonRepository extends ReactiveMongoRepository <Person, String> {
    @Tailable
    Flux<Person> findAllByName(String name);
}
