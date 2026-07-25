package com.waseel.dssadminservice.repository.mdss;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.dssadminservice.persist.mdss.CustomizationBatch;
@Repository
public interface CustomizationBatchRepository extends CrudRepository<CustomizationBatch, Long> {

    @Query(value = "SELECT MAX(\"Id\") from \"CustomizationBatch\"", nativeQuery = true)
    Long findLatestId();
}
