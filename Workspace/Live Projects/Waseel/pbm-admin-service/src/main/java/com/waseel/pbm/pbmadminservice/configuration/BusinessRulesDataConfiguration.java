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
@EnableJpaRepositories(entityManagerFactoryRef = "BusinessRulesEntityManagerFactory", 
	transactionManagerRef = "BusinessRulesTransactionManager", basePackages = {
		"com.waseel.pbm.pbmadminservice.repository.businessrules" })
public class BusinessRulesDataConfiguration {

	@Bean(name = "BusinessRulesDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.businessrules")
	DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "BusinessRulesEntityManagerFactory")
	LocalContainerEntityManagerFactoryBean businessRulesEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("BusinessRulesDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("com.waseel.pbm.pbmadminservice.persist.businessrules")
				.persistenceUnit("businessrules").build();
	}

	@Bean(name = "BusinessRulesTransactionManager")
	PlatformTransactionManager businessRulesTransactionManager(
			@Qualifier("BusinessRulesEntityManagerFactory") EntityManagerFactory businessRulesTransactionManager) {
		return new JpaTransactionManager(businessRulesTransactionManager);
	}
}
