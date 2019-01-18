package com.xvela.reactive.containers;

import com.xvela.reactive.common.converter.BuilderConverter;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;

@Component
public class ContainerConverter extends BuilderConverter<Container, ContainerVo, ContainerVo.ContainerVoBuilder> {

    public ContainerConverter(DozerBeanMapper mapper) {
        super(mapper, Container.class, ContainerVo.class, ContainerVo.ContainerVoBuilder.class);
    }

}
