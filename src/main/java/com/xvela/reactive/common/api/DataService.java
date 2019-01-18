package com.xvela.reactive.common.api;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

public interface DataService<V extends BaseVo> {

    @NonNull
    Flux<V> findAll();

    @NonNull
    Mono<V> find(String uuid);

    @NonNull
    Mono<V> create(V vo);

    @NonNull
    Mono<V> update(String uuid, V vo);

    @NonNull
    Mono<Boolean> delete(String uuid);
}
