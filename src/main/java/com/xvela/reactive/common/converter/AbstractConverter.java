package com.xvela.reactive.common.converter;


import com.xvela.reactive.common.api.BaseVo;
import com.xvela.reactive.common.entity.BaseEntity;
import lombok.NonNull;
import org.dozer.Mapper;

public abstract class AbstractConverter<T extends BaseEntity, V extends BaseVo<V>> implements Converter<T, V> {

    private Mapper mapper;
    private Class<T> tClazz;
    private Class<V> vClazz;

    public AbstractConverter(Mapper mapper, Class<T> tClazz, Class<V> vClazz) {
        this.mapper = mapper;
        this.tClazz = tClazz;
        this.vClazz = vClazz;
    }

    protected V convert(T t) {
        return mapper.map(t, vClazz);
    }

    private T convert(V v) {
        return mapper.map(v, tClazz);
    }

    @Override
    @NonNull
    public final V convertEntityToVo(T t) {
        return mapEntityToVo(convert(t), t).withUuid(t.getUuid());
    }

    @Override
    @NonNull
    public final T convertVoToEntity(V v) {
        return mapVoToEntity(convert(v), v);
    }

    @Override
    @NonNull
    public final T updateEntityFromVo(T t, V v) {
        mapper.map(v, t);
        return mapVoToEntity(t, v);
    }

    public V mapEntityToVo(V v, T t) {
        return v;
    }

    public T mapVoToEntity(T t, V v) {
        return t;
    }
}
