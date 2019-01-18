package com.xvela.reactive.common.services;

import com.xvela.reactive.common.api.BaseVo;
import com.xvela.reactive.common.api.DataService;
import com.xvela.reactive.common.converter.AbstractConverter;
import com.xvela.reactive.common.entity.BaseEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

import java.util.function.Supplier;

public abstract class BaseDataServiceJpa<T extends BaseEntity, V extends BaseVo<V>> implements DataService<V> {

    private final R2dbcRepository<T, String> repository;
    private final AbstractConverter<T, V> converter;

    public BaseDataServiceJpa(R2dbcRepository<T, String> baseRepository, AbstractConverter<T, V> converter) {
        this.repository = baseRepository;
        this.converter = converter;
    }

    protected Mono<V> convertEntityToVo(T t) {
        return Mono.just(converter.convertEntityToVo(t));
    }

    protected Mono<T> convertVoToEntity(V v) {
        return Mono.just(converter.convertVoToEntity(v));
    }

    protected Mono<T> updateEntityFromVo(T t, V v) {
        return Mono.just(converter.updateEntityFromVo(t, v));

    }

    private Mono<V> save(T e) {
        return repository.save(e).flatMap(this::convertEntityToVo);
    }

    @NonNull
    @Override
    public Flux<V> findAll() {
        return repository.findAll().flatMap(this::convertEntityToVo);
    }

    @NonNull
    @Override
    public Mono<V> find(String uuid) {

        return repository.findById(uuid).flatMap(this::convertEntityToVo);
    }

    @NonNull
    @Override
    public Mono<V> create(V vo) {
        return Mono.just(vo)
                .flatMap(this::convertVoToEntity)
                .flatMap(this::save);
    }

    @NonNull
    @Override
    public Mono<V> update(String uuid, V vo) {
        return updateRecord(() -> repository.findById(uuid), vo, String.format("UUId %s not found.", uuid));
    }

    protected Mono<V> updateRecord(Supplier<Mono<T>> queryFunction, V vo, String notFoundMessage) {

        return queryFunction
                .get()
                .flatMap(e -> this.updateEntityFromVo(e, vo))
                .flatMap(this::save);
    }

    @NonNull
    @Override
    public Mono<Boolean> delete(String uuid) {
        return repository.deleteById(uuid).then(Mono.just(true));
    }

}
