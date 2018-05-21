package com.lucaskatayama.reactive.configurations;

import com.lucaskatayama.reactive.domain.Person;
import com.mongodb.MongoClient;
import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

import java.io.IOException;

@Configuration
public class MongoConfig {

    private static final String MONGO_DB_URL = "localhost";

    @Value("${spring.data.mongodb.database}")
    String mongoDbDatabase;

    public @Bean MongoClient mongoClient() throws IOException {
        EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();
        mongo.setBindIp(MONGO_DB_URL);
        return mongo.getObject();
    }

    public @Bean MongoTemplate mongoTemplate() throws IOException {
        MongoTemplate template = new MongoTemplate(mongoClient(), mongoDbDatabase);
        CollectionOptions options = CollectionOptions.empty().capped().size(10000);
        template.createCollection(Person.class, options);
        return template;
    }

    public @Bean Jackson2RepositoryPopulatorFactoryBean repositoryPopulator() {
        Resource sourceData = new ClassPathResource("data/data-person.json");
        Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
        factory.setResources(new Resource[] { sourceData });
        return factory;
    }


}
