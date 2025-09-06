package com.codeforge.api;

import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@RestController
public class DiagController {

    private final ReactiveMongoTemplate mongoTemplate;
    private final boolean kafkaEnabled;
    private final String kafkaBootstrap;
    private final String externalKey;

    public DiagController(ReactiveMongoTemplate mongoTemplate,
                          @Value("${codeforge.kafka.enabled:false}") boolean kafkaEnabled,
                          @Value("${spring.kafka.bootstrap-servers:}") String kafkaBootstrap,
                          @Value("${EXTERNAL_API_KEY:}") String externalKey) {
        this.mongoTemplate = mongoTemplate;
        this.kafkaEnabled = kafkaEnabled;
        this.kafkaBootstrap = kafkaBootstrap;
        this.externalKey = externalKey;
    }

    @GetMapping("/api/diag")
    public Mono<Map<String, Object>> diag() {
        Map<String,Object> m = new HashMap<>();
        // Mongo check: just try simple command; ignore errors -> false
        return mongoTemplate.executeCommand("{ ping: 1 }")
            .map(doc -> {
                m.put("mongo", true);
                return m;
            })
            .onErrorResume(ex -> {
                m.put("mongo", false);
                return Mono.just(m);
            })
            .flatMap(map -> {
                boolean kafkaOk = false;
                if (kafkaEnabled && kafkaBootstrap != null && !kafkaBootstrap.isBlank()) {
                    try {
                        Properties props = new Properties();
                        props.put("bootstrap.servers", kafkaBootstrap);
                        props.put("request.timeout.ms","1000");
                        try (AdminClient client = AdminClient.create(props)) {
                            client.describeCluster();
                            kafkaOk = true; // assume ok if no exception constructing
                        }
                    } catch (Exception e) {
                        kafkaOk = false;
                    }
                }
                map.put("kafka", kafkaOk);
                map.put("external", externalKey != null && !externalKey.isBlank());
                return Mono.just(map);
            });
    }
}