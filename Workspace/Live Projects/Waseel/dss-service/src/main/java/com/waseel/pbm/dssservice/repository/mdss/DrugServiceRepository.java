package com.waseel.pbm.dssservice.repository.mdss;

import com.waseel.pbm.dssservice.persist.mdss.DrugService;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DrugServiceRepository extends CrudRepository<DrugService, Long> {
	
	@Query("select model from DrugService model where model.otherCodesValue like (:ndcDrugCode) and model.drugServiceMetaData.drugListId = :druglistId")
	List<DrugService> findByOtherCodesValueAndDrugListId(@Param("ndcDrugCode")String ndcDrugCode, @Param("druglistId") long druglistId);
    
	@Query("select model from DrugService model where model.scientificCode like (:scientificCode) and model.drugServiceMetaData.drugListId = :druglistId")
	List<DrugService> findByScientificCodeAndDrugListId(@Param("scientificCode")String scientificCode, @Param("druglistId") long druglistId);
}
