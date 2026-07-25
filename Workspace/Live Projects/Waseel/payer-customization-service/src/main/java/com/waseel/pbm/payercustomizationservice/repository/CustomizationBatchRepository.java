package com.waseel.pbm.payercustomizationservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.payercustomizationservice.persist.CustomizationBatch;

@Repository
public interface CustomizationBatchRepository extends CrudRepository<CustomizationBatch, Long> {

}
