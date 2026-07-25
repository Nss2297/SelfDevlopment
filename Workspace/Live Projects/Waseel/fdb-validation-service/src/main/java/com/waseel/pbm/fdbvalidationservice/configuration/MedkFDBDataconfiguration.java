package com.waseel.pbm.fdbvalidationservice.configuration;

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
@EnableJpaRepositories(entityManagerFactoryRef = "MedkFDBEntityManagerFactory", basePackages = {
		"com.waseel.pbm.fdbvalidationservice.repository.medk_fdb" })
public class MedkFDBDataconfiguration {

	@Bean(name = "MedkFDBDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.medk-fdb")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "MedkFDBEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean medkFDBEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("MedkFDBDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("com.waseel.pbm.fdbvalidationservice.persist.medk_fdb")
				.persistenceUnit("medk_fdb").build();
	}

	@Bean(name = "MedkFDBTransactionManager")
	public PlatformTransactionManager medkFDBTransactionManager(
			@Qualifier("MedkFDBEntityManagerFactory") EntityManagerFactory medkFDBTransactionManager) {
		return new JpaTransactionManager(medkFDBTransactionManager);
	}

}
