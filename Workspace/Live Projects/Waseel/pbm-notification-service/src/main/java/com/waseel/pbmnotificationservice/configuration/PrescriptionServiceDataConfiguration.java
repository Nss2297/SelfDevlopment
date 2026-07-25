package com.waseel.pbmnotificationservice.configuration;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
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

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "PrescriptionServiceEntityManagerFactory", transactionManagerRef = "PrescriptionServiceTransactionManager", basePackages = {
        "com.waseel.pbmnotificationservice.repository.prescriptionservice"})
public class PrescriptionServiceDataConfiguration {

    @Value("${spring.priscriptionservice.liquibase.change-log:defaultValue}")
    private String changeLog;

    @Value("#{new Boolean('${spring.priscriptionservice.liquibase.enabled}')}")
    private boolean isEnabled;

    @Value("${spring.priscriptionservice.default-schema:defaultValue}")
    private String defaultSchema;

    @Primary
    @Bean(name = "PrescriptionServiceDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.prescriptionservice")
    DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "PrescriptionServiceEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean prescriptionServiceEntityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                                   @Qualifier("PrescriptionServiceDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource)
                .packages("com.waseel.pbmnotificationservice.persist.prescriptionservice")
                .persistenceUnit("prescriptionservice").build();
    }

    @Primary
    @Bean(name = "PrescriptionServiceTransactionManager")
    PlatformTransactionManager prescriptionServiceTransactionManager(
            @Qualifier("PrescriptionServiceEntityManagerFactory") EntityManagerFactory prescriptionServiceTransactionManager) {
        return new JpaTransactionManager(prescriptionServiceTransactionManager);
    }

    @Bean(name = "priscriptionServiceLiquibaseProperties")
    @ConfigurationProperties("spring.priscriptionservice.liquibase.change-log")
    LiquibaseProperties priscriptionServiceLiquibaseProperties() {
        LiquibaseProperties liquibaseProperties = new LiquibaseProperties();
        liquibaseProperties.setChangeLog(changeLog);
        liquibaseProperties.setEnabled(isEnabled);
        liquibaseProperties.setDefaultSchema(defaultSchema);
        return liquibaseProperties;
    }

    @Bean(name = "priscriptionserviceliquibase")
    SpringLiquibase priscriptionServiceLiquibase(
            @Qualifier("priscriptionServiceLiquibaseProperties") LiquibaseProperties liquibaseProperties) {
        SpringLiquibase primary = new SpringLiquibase();
        primary.setDataSource(dataSource());
        primary.setShouldRun(priscriptionServiceLiquibaseProperties().isEnabled());
        primary.setChangeLog(priscriptionServiceLiquibaseProperties().getChangeLog());
        return primary;
    }
}
