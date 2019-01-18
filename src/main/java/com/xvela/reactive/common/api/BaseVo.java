package com.xvela.reactive.common.api;

public interface BaseVo<T> {

    T withUuid(String uuId);

    String getUuid();

}
