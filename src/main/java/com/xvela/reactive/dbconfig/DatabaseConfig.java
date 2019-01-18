package com.xvela.reactive.dbconfig;

import com.xvela.reactive.containers.ContainerRepository;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.dialect.Dialect;
import org.springframework.data.r2dbc.dialect.PostgresDialect;
import org.springframework.data.r2dbc.function.DatabaseClient;
import org.springframework.data.r2dbc.function.DefaultReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.repository.support.R2dbcRepositoryFactory;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Autowired
    DataSource dataSource;

    @Bean
    PostgresqlConnectionFactory connectionFactory() {
        System.out.println("connection factory initialized");
        return new PostgresqlConnectionFactory(
                PostgresqlConnectionConfiguration.builder()
                        .host("localhost")
                        .database("n4_units")
                        .username("mnr")
                        .password("mnrpass")
                        .build());

    }

    @Bean
    DatabaseClient databaseClient(ConnectionFactory connectionFactory) {
        System.out.println("db client initialized");

        return DatabaseClient.builder()
                .connectionFactory(connectionFactory)
                .build();
    }

    @Bean
    R2dbcRepositoryFactory repositoryFactory(DatabaseClient databaseClient) {
        System.out.println("repo factory initialized");
        RelationalMappingContext relationalMappingContext = new RelationalMappingContext();
        relationalMappingContext.afterPropertiesSet();

        Dialect dialect = new PostgresDialect();
        DefaultReactiveDataAccessStrategy reactiveDataAccessStrategy = new DefaultReactiveDataAccessStrategy(dialect);
        return new R2dbcRepositoryFactory(databaseClient, relationalMappingContext, reactiveDataAccessStrategy);

    }

    @Bean
    ContainerRepository containerRepository(R2dbcRepositoryFactory factory) {

        return factory.getRepository(ContainerRepository.class);
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        final DataSourceTransactionManager txManager = new DataSourceTransactionManager();
        txManager.setDataSource(dataSource);
        return txManager;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/n4_units");
        dataSource.setUsername("mnr");
        dataSource.setPassword("mnrpass");
        return dataSource;
    }

}

