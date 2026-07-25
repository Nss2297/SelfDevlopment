package com.waseel.pbm.fdbvalidationservice.repository.medk_fdb;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.waseel.pbm.fdbvalidationservice.persist.medk_fdb.Rfmlinm1IcdDesc;
import com.waseel.pbm.fdbvalidationservice.persist.medk_fdb.Rfmlinm1IcdDescId;

public interface Rfmlinm1IcdDescRepository extends CrudRepository<Rfmlinm1IcdDesc, Rfmlinm1IcdDescId> {
	
	@Query(value = "select distinct model_Desc.id.icdCd from Rfmlinm1IcdDesc model_Desc ,"
			+ " Rfmlisr1IcdSearch model_Search where model_Desc.id.icdCd like (:icdCode%) "
			+ " and model_Desc.id.icdCdType = 07  and model_Desc.id.icdCd = model_Search.id.searchIcdCd "
			+ " and model_Search.id.icdCdType = 07 ")
	List<String> findIcdCodeV10AM(@Param("icdCode") String diagnosisCode);

}
