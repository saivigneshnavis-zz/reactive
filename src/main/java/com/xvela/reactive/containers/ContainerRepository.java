package com.xvela.reactive.containers;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContainerRepository extends R2dbcRepository<Container, String> {
}
