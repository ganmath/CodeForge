package com.codeforge.api;

import com.codeforge.tenant.Tenant;
import com.codeforge.tenant.TenantRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/test/fixtures")
public class FixturesController {

    private final TenantRepository repo;
    public FixturesController(TenantRepository repo) { this.repo = repo; }

    @PostMapping(path="/seed", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Tenant> seed() {
        List<Tenant> seeds = List.of(
            new Tenant(null, "acme"),
            new Tenant(null, "globex")
        );
        return repo.deleteAll().thenMany(repo.saveAll(seeds));
    }

    @GetMapping(path="/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Tenant> list() {
        return repo.findAll();
    }

    @PostMapping(path="/purge")
    public Mono<Void> purge() {
        return repo.deleteAll();
    }
}