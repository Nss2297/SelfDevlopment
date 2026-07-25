package com.waseel.drugformulary.repository.mdss;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.waseel.drugformulary.model.DrugServiceModel;
import com.waseel.drugformulary.persist.mdss.DrugService;

public interface DrugServiceRepository extends JpaRepository<DrugService, Long> {

	@Query("SELECT new com.waseel.drugformulary.model.DrugServiceModel("
			+ " model.otherCodesValue,model.waseelDrugId)"
			+ " FROM DrugService model"
			+ " WHERE model.otherCodesValue IN (:drugCodes)"
			+ " AND model.drugListId = :drugListId")
	List<DrugServiceModel> findByDrugCodes(@Param("drugCodes") List<String> drugCodes,@Param("drugListId")Long drugListId);
}
