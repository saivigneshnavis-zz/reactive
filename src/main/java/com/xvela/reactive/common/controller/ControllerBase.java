package com.xvela.reactive.common.controller;

import com.xvela.reactive.common.api.BaseVo;
import com.xvela.reactive.common.api.DataService;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;


public abstract class ControllerBase<V extends BaseVo<V>> {

    private final DataService<V> service;

    public ControllerBase(DataService<V> service) {
        this.service = service;
    }

    @NonNull
    @GetMapping(value = "", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    protected Flux<Object> index() {
        return Flux.concat(service.findAll()    , Flux.just(ServerSentEvent.builder()
                .data("disconnect signal")
               .event("disconnect").build()));
       //    return service.findAll();
    }

    @NonNull
    @GetMapping("{uuid}")
    protected Mono<V> get(@PathVariable String uuid) {
        return service.find(uuid);
    }

    @NonNull
    @PostMapping("")
    protected Mono<V> post(@RequestBody V vo) {
        return service.create(vo);
    }

    @NonNull
    @PutMapping("{uuid}")
    protected Mono<V> put(@PathVariable String uuid, @RequestBody V vo) {
        return service.update(uuid, vo.withUuid(uuid));
    }

    @NonNull
    @DeleteMapping("{uuid}")
    protected Mono<Void> delete(@PathVariable String uuid) {
        return service.delete(uuid).then();
    }
}
