package com.waseel.pbm.rtsservice.configuration;

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
@EnableJpaRepositories(entityManagerFactoryRef = "HiraEntityManagerFactory",
transactionManagerRef = "HiraTransactionManager",basePackages = {
		"com.waseel.pbm.rtsservice.repository.hira" })
public class HiraDataConfiguration {

	@Primary
	@Bean(name = "HiraDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.hira")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean(name = "HiraEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean hiraEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("HiraDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("com.waseel.pbm.rtsservice.persist.hira")
				.persistenceUnit("hira").build();
	}

	@Primary
	@Bean(name = "HiraTransactionManager")
	public PlatformTransactionManager hiraTransactionManager(
			@Qualifier("HiraEntityManagerFactory") EntityManagerFactory hiraTransactionManager) {
		return new JpaTransactionManager(hiraTransactionManager);
	}
}
