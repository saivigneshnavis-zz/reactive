package com.xvela.reactive.containers;

import com.xvela.reactive.common.converter.AbstractConverter;
import com.xvela.reactive.common.services.BaseDataServiceJpa;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Service;

@Service
public class ContainerServiceImpl extends BaseDataServiceJpa<Container, ContainerVo> implements ContainerService {

    public ContainerServiceImpl(R2dbcRepository<Container, String> baseRepository, AbstractConverter<Container, ContainerVo> converter) {
        super(baseRepository, converter);
    }
}
