package com.waseel.pbm.idfvalidationservice.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.idfvalidationservice.persist.IDFDrugToAge;

@Repository
public interface IDFDrugToAgeRepository extends CrudRepository<IDFDrugToAge, String> {

	Optional<IDFDrugToAge> findByServiceCode(String ndcDrugCode);
}
