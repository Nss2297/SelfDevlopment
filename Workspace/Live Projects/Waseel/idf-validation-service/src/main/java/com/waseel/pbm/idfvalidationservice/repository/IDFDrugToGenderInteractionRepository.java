package com.waseel.pbm.idfvalidationservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.idfvalidationservice.persist.IDFDrugToGenderInteraction;

@Repository
public interface IDFDrugToGenderInteractionRepository extends CrudRepository<IDFDrugToGenderInteraction, String> {

	IDFDrugToGenderInteraction findByServiceCode(String ndcDrugCode);
}
