package com.waseel.prescription.configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
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

import liquibase.integration.spring.SpringLiquibase;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "BusinessRulesEntityManagerFactory", transactionManagerRef = "BusinessRulesTransactionManager", basePackages = {
		"com.waseel.prescription.repository.businessrules" })
public class BusinessRulesDataConfiguration {

	@Value("${spring.businessrules.liquibase.change-log:defaultValue}")
	private String changeLog;

	@Value("#{new Boolean('${spring.businessrules.liquibase.enabled}')}")
	private boolean isEnabled;

	@Value("${spring.priscriptionservice.default-schema:defaultValue}")
	private String defaultSchema;

	@Bean(name = "BusinessRulesDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.businessrules")
	DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "BusinessRulesEntityManagerFactory")
	LocalContainerEntityManagerFactoryBean businessRulesEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("BusinessRulesDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("com.waseel.prescription.persist.businessrules")
				.persistenceUnit("businessrules").build();
	}

	@Bean(name = "BusinessRulesTransactionManager")
	PlatformTransactionManager businessRulesTransactionManager(
			@Qualifier("BusinessRulesEntityManagerFactory") EntityManagerFactory businessRulesTransactionManager) {
		return new JpaTransactionManager(businessRulesTransactionManager);
	}

	@Bean(name = "businessRulesLiquibaseProperties")
	@ConfigurationProperties("spring.businessrules.liquibase.change-log")
	LiquibaseProperties businessRulesLiquibaseProperties() {
		LiquibaseProperties liquibaseProperties = new LiquibaseProperties();
		liquibaseProperties.setChangeLog(changeLog);
		liquibaseProperties.setEnabled(isEnabled);
		liquibaseProperties.setDefaultSchema(defaultSchema);
		return liquibaseProperties;
	}

	@Bean(name = "businessRulesliquibase")
	SpringLiquibase businessRulesLiquibase(
			@Qualifier("businessRulesLiquibaseProperties") LiquibaseProperties liquibaseProperties) {
		SpringLiquibase primary = new SpringLiquibase();
		primary.setShouldRun(businessRulesLiquibaseProperties().isEnabled());
		primary.setDataSource(dataSource());
		primary.setChangeLog(businessRulesLiquibaseProperties().getChangeLog());
		return primary;
	}
}
