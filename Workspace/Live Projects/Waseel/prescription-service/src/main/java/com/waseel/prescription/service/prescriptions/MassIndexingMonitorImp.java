package com.waseel.prescription.service.prescriptions;

import java.util.concurrent.atomic.LongAdder;

import org.hibernate.search.mapper.pojo.massindexing.MassIndexingMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MassIndexingMonitorImp implements MassIndexingMonitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MassIndexingMonitorImp.class);
    private final LongAdder documentsAdded = new LongAdder();
    private final LongAdder documentsBuilt = new LongAdder();
    private final LongAdder entitiesLoaded = new LongAdder();
    private final LongAdder totalCounter = new LongAdder();

    @Override
    public void documentsAdded(long increment) {
        documentsAdded.add(increment);
        LOGGER.info("Documents added: {}", documentsAdded.longValue());
    }

    @Override
    public void documentsBuilt(long increment) {
        documentsBuilt.add(increment);
        LOGGER.info("Documents built: {}", documentsBuilt.longValue());
    }

    @Override
    public void entitiesLoaded(long increment) {
        entitiesLoaded.add(increment);
        LOGGER.info("Entities loaded: {}", entitiesLoaded.longValue());
    }

    @Override
    public void addToTotalCount(long increment) {
        totalCounter.add(increment);
        LOGGER.info("current total count: {}", totalCounter.longValue());
    }

    @Override
    public void indexingCompleted() {
        LOGGER.info("Indexing completed");
    }

}
