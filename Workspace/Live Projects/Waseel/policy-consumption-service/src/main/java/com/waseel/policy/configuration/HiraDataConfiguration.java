package com.waseel.policy.configuration;

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
@EnableJpaRepositories(entityManagerFactoryRef = "HiraEntityManagerFactory", transactionManagerRef = "HiraTransactionManager",
	basePackages = {
		"com.waseel.policy.repository.hira" })
public class HiraDataConfiguration {

	@Bean(name = "HiraDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.hira")
	DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "HiraEntityManagerFactory")
	LocalContainerEntityManagerFactoryBean hiraEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("HiraDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("com.waseel.policy.persist.hira").persistenceUnit("hira")
				.build();
	}

	@Bean(name = "HiraTransactionManager")
	PlatformTransactionManager hiraTransactionManager(
			@Qualifier("HiraEntityManagerFactory") EntityManagerFactory hiraTransactionManager) {
		return new JpaTransactionManager(hiraTransactionManager);
	}
}
