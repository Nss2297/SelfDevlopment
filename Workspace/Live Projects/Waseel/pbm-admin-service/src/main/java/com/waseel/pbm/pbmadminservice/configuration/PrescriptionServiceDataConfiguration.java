package com.waseel.pbm.pbmadminservice.configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "PrescriptionServiceEntityManagerFactory",
transactionManagerRef = "PrescriptionServiceTransactionManager", basePackages = {
		"com.waseel.pbm.pbmadminservice.repository.prescriptionservice" })
public class PrescriptionServiceDataConfiguration {

	@Bean(name = "PrescriptionServiceDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.prescriptionservice")
	DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "PrescriptionServiceEntityManagerFactory")
	LocalContainerEntityManagerFactoryBean prescriptionServiceEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("PrescriptionServiceDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("com.waseel.pbm.pbmadminservice.persist.prescriptionservice",
				  "com.waseel.pbm.pbmadminservice.persist.hira")
				.persistenceUnit("prescriptionservice").build();
	}

	@Bean(name = "PrescriptionServiceTransactionManager")
	PlatformTransactionManager prescriptionServiceTransactionManager(
			@Qualifier("PrescriptionServiceEntityManagerFactory") EntityManagerFactory prescriptionServiceTransactionManager) {
		return new JpaTransactionManager(prescriptionServiceTransactionManager);
	}
}
