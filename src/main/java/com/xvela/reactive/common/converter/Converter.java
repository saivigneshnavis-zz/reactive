package com.xvela.reactive.common.converter;


import reactor.util.annotation.NonNull;

public interface Converter<T, V> {

    @NonNull
    V convertEntityToVo(T t);

    @NonNull
    T convertVoToEntity(V v);

    @NonNull
    T updateEntityFromVo(T t, V v);
}
