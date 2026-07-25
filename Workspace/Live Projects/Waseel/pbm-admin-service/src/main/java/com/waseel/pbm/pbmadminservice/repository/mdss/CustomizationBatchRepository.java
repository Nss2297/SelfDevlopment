package com.waseel.pbm.pbmadminservice.repository.mdss;

import com.waseel.pbm.pbmadminservice.persist.mdss.CustomizationBatch;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomizationBatchRepository extends CrudRepository<CustomizationBatch, Long> {

    @Query(value = "SELECT MAX(\"Id\") from \"CustomizationBatch\"", nativeQuery = true)
    Long findLatestId();
}
