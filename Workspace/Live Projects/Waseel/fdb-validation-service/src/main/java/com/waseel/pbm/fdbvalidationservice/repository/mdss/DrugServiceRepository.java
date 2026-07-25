package com.waseel.pbm.fdbvalidationservice.repository.mdss;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.waseel.pbm.fdbvalidationservice.persist.mdss.DrugService;

public interface DrugServiceRepository extends CrudRepository<DrugService, String> {

	@Query("select model from DrugService model where model.scientificCode like (:scientificCode) and model.drugServiceMetaData.drugListId = :druglistId")
	List<DrugService> findByScientificCodeAndDrugListId(@Param("scientificCode")String scientificCode, @Param("druglistId") long druglistId);
	
	@Query("select model from DrugService model where model.otherCodesValue like (:drugCode) and model.drugServiceMetaData.drugListId = :druglistId")
	DrugService findFirstByDrugCodeAndDrugListId(@Param("drugCode")String drugCode, @Param("druglistId") long druglistId);

	@Query("select model from DrugService model where model.otherCodesValue like (:drugCode) and model.drugServiceMetaData.drugListId = :druglistId order By model.drugServiceMetaData.drugListId desc ")
	List<DrugService> findByDrugCodeAndDrugListId(@Param("drugCode")String drugCode, @Param("druglistId") long druglistId);

}
