package com.codeforge.tenant;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** Simple in-memory reactive repo for CI/tests without Mongo. */
public class InMemoryTenantRepository implements TenantRepository {
    private final Map<String, Tenant> store = new ConcurrentHashMap<>();

    @Override public <S extends Tenant> Mono<S> save(S entity) {
        if (entity.getId()==null) entity.setId(java.util.UUID.randomUUID().toString());
        store.put(entity.getId(), entity);
        return Mono.just(entity);
    }
    @Override public <S extends Tenant> Flux<S> saveAll(Iterable<S> entities) {
        return Flux.fromIterable(entities).flatMap(this::save);
    }
    @Override public <S extends Tenant> Flux<S> saveAll(Publisher<S> entityStream) {
        return Flux.from(entityStream).flatMap(this::save);
    }
    @Override public Mono<Tenant> findById(String s) { return Mono.justOrEmpty(store.get(s)); }
    @Override public Mono<Tenant> findById(Publisher<String> idStream) {
        return Flux.from(idStream).next().flatMap(id -> Mono.justOrEmpty(store.get(id)));
    }
    @Override public Mono<Boolean> existsById(String s) { return Mono.just(store.containsKey(s)); }
    @Override public Mono<Boolean> existsById(Publisher<String> idStream) {
        return Flux.from(idStream).any(store::containsKey);
    }
    @Override public Flux<Tenant> findAll() { return Flux.fromIterable(store.values()); }
    @Override public Flux<Tenant> findAllById(Iterable<String> strings) {
        return Flux.fromIterable(strings).flatMap(id -> Mono.justOrEmpty(store.get(id)));
    }
    @Override public Flux<Tenant> findAllById(Publisher<String> idStream) {
        return Flux.from(idStream).flatMap(id -> Mono.justOrEmpty(store.get(id)));
    }
    @Override public Mono<Long> count() { return Mono.just((long) store.size()); }
    @Override public Mono<Void> deleteById(String s) { store.remove(s); return Mono.empty(); }
    @Override public Mono<Void> deleteById(Publisher<String> idStream) {
        return Flux.from(idStream).doOnNext(store::remove).then();
    }
    @Override public Mono<Void> delete(Tenant entity) { if (entity!=null && entity.getId()!=null) store.remove(entity.getId()); return Mono.empty(); }
    @Override public Mono<Void> deleteAllById(Iterable<? extends String> strings) { strings.forEach(store::remove); return Mono.empty(); }
    @Override public Mono<Void> deleteAll(Iterable<? extends Tenant> entities) { entities.forEach(e -> { if (e.getId()!=null) store.remove(e.getId()); }); return Mono.empty(); }
    @Override public Mono<Void> deleteAll() { store.clear(); return Mono.empty(); }
    @Override public Mono<Void> deleteAll(Publisher<? extends Tenant> entityStream) {
        return Flux.from(entityStream).doOnNext(e -> { if (e.getId()!=null) store.remove(e.getId()); }).then();
    }

    @Override public Mono<Tenant> findByName(String name) {
        return Flux.fromIterable(store.values()).filter(t -> name.equals(t.getName())).next();
    }
}