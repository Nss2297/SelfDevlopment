package com.waseel.pbm.idfvalidationservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.waseel.pbm.idfvalidationservice.persist.DrugService;

public interface DrugServiceRepository extends CrudRepository<DrugService, String> {

	DrugService findFirstByOtherCodesValueOrderByDrugListIdDesc(String ndcDrugCode);
}
