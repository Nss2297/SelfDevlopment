package com.waseel.prescription.repository.hira;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.waseel.prescription.persist.hira.DrugListService;
import com.waseel.prescription.persist.hira.DrugListServiceId;

public interface DrugListServiceRepository extends CrudRepository<DrugListService, DrugListServiceId> {

	
	@Query(value = "SELECT model.* from \"DrugListService\" model"
			+ " WHERE model.\"RegistrationNo\" = :drugCode"
			+ " ORDER BY"
			+ " model.\"LastUpdatedDate\"  DESC ,model.\"DrugListServiceId\"  DESC"
			+ " FETCH FIRST 1 ROW ONLY",nativeQuery = true)
	Optional<DrugListService> findByLatestUpdatedDateWithLatestDrugListServiceId(@Param("drugCode") String drugCode); 
}
