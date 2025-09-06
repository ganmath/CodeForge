package com.codeforge.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import java.time.Instant;
import java.util.Map;

@RestController
public class PingController {
    @GetMapping("/api/ping")
    public Mono<Map<String,Object>> ping() {
        return Mono.just(Map.of("status","ok","ts", Instant.now().toString()));
    }
}