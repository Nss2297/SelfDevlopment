package com.waseel.pbm.dssservice.configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "MDSSEntityManagerFactory",
        transactionManagerRef = "MDSSTransactionManager", basePackages = {"com.waseel.pbm.dssservice.repository.mdss"})

public class MDSSDataConfiguration {

    @Primary
    @Bean(name = "MDSSDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.mdss")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "MDSSEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean mdssEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("MDSSDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("com.waseel.pbm.dssservice.persist.mdss").persistenceUnit("mdss")
                .build();
    }

    @Primary
    @Bean(name = "MDSSTransactionManager")
    public PlatformTransactionManager mdssTransactionManager(
            @Qualifier("MDSSEntityManagerFactory") EntityManagerFactory mdssTransactionManager) {
        return new JpaTransactionManager(mdssTransactionManager);
    }
}
