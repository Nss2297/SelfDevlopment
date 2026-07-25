package com.waseel.dssadminservice.service.indexelastic;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.schema.management.SearchSchemaManager;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.waseel.dssadminservice.persist.mdss.DrugService;

@Component
@Profile("elasticsearch")
public class IndexingService {

	private static final Logger logger = LoggerFactory.getLogger(IndexingService.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private Environment environment;

	@Transactional
	@EventListener(ApplicationReadyEvent.class)
	public void initiateIndexing() {
		logger.info("Initiating indexing...");
		SearchSession searchSession = Search.session(entityManager);

		SearchSchemaManager schemaManager = searchSession.schemaManager();
		schemaManager.createIfMissing();
		logger.info("created missing indices...");

		MassIndexer indexer = searchSession.massIndexer(DrugService.class).monitor(new MassIndexingMonitorImp());
		if (Arrays.asList(environment.getActiveProfiles()).contains("local")) {
			indexer.limitIndexedObjectsTo(500);
		}
		try {
			indexer.startAndWait();
			logger.info("All entities indexed");
		} catch (InterruptedException e) {
			logger.error("could not index entities", e);
		}
	}

}
