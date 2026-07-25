package com.waseel.pbm.idfvalidationservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.idfvalidationservice.persist.IDFConcurrentMedication;
import com.waseel.pbm.idfvalidationservice.persist.IDFConcurrentMedicationId;

@Repository
public interface ConcurrentMedicationRepository
		extends CrudRepository<IDFConcurrentMedication, IDFConcurrentMedicationId> {
	@Query("select model from IDFConcurrentMedication model where model.id.serviceCode like (:serviceCode)")
	Optional<List<IDFConcurrentMedication>> findByServiceCode(@Param("serviceCode") String serviceCode);
}
