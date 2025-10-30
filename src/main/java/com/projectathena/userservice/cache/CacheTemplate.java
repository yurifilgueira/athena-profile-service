package com.projectathena.userservice.cache;

import org.redisson.api.RMapCacheReactive;
import reactor.core.publisher.Mono;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class CacheTemplate<KEY, ENTITY> {

    private final RMapCacheReactive<KEY, ENTITY> cache;
    private final Duration ttl;

    public CacheTemplate(RMapCacheReactive<KEY, ENTITY> cache, Duration ttl) {
        this.cache = cache;
        this.ttl = ttl;
    }

    public Mono<ENTITY> get(KEY key, Mono<ENTITY> sourceSupplier) {
        return getFromCache(key)
                .switchIfEmpty(
                        sourceSupplier
                                .flatMap(entity -> updateCache(key, entity))
                );
    }

    private Mono<ENTITY> getFromCache(KEY key) {
        return this.cache.get(key);
    }

    private Mono<ENTITY> updateCache(KEY key, ENTITY entity) {
        return this.cache
                .fastPut(key, entity, this.ttl.toSeconds(), TimeUnit.SECONDS)
                .thenReturn(entity);
    }
}