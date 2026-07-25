package com.waseel.pbm.dssservice.repository.mdss;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.waseel.pbm.dssservice.persist.mdss.ServiceInfoId;
import com.waseel.pbm.dssservice.persist.mdss.Serviceinfo;

import feign.Param;

@Repository
public interface ServiceInfoRepository extends CrudRepository<Serviceinfo, ServiceInfoId>{

	@Query("Select model from Serviceinfo model where model.id.requestId = :requestId AND model.isDeletedFromProvider = 0")
	List<Serviceinfo> findByrequestId(@Param("requestId") String requestId);
	
	@Query("Select model from Serviceinfo model where model.id.requestId = :requestId AND (model.serviceCode = :serviceCode OR model.scientificCode = :serviceCode) AND model.isDeletedFromProvider = 0")
	//List<Serviceinfo> findNoDeletedByRequestIdANDServiceCode(@Param("requestId") String requestId,@Param("serviceCode") String serviceCode);
	Serviceinfo findNoDeletedByRequestIdANDServiceCodeORScientificCode(@Param("requestId") String requestId,
			@Param("serviceCode") String serviceCode);
	
	@Query("Select model from Serviceinfo model where model.id.requestId = :requestId AND model.serviceCode = :serviceCode AND model.scientificCode = :scientificCode ")
	Serviceinfo findByRequestIdANDServiceCodeANDScientificCode(@Param("requestId") String requestId,@Param("serviceCode") String serviceCode ,@Param("scientificCode") String scientificCode );
		
	@Query("Select model from Serviceinfo model where model.id.requestId = :requestId AND model.serviceCode = :serviceCode")
	Serviceinfo findByRequestIdANDServiceCode(@Param("requestId") String requestId,@Param("serviceCode") String serviceCode);
	
	@Query("Select model from Serviceinfo model where model.id.requestId = :requestId AND model.scientificCode = :scientificCode")
	Serviceinfo findByRequestIdANDScientificCode(@Param("requestId") String requestId,@Param("scientificCode") String scientificCode);
	
	@Transactional
	@Modifying
	@Query("Update Serviceinfo set isDeletedFromProvider = '1' where id.requestId = :requestId")
	int updateServiceinfoIsDeletedFlag(@Param("requestId") String requestId);
	
	@Query("Select CASE WHEN count(model) = 0 THEN 'false' ELSE 'true' END "
			+ " from Serviceinfo model where model.id.requestId = :requestId and model.isDeletedFromProvider = '1'")
	boolean findIsDeletedByrequestId(@Param("requestId") String requestId);
	
	@Query("Select model from Serviceinfo model where model.id.requestId = :requestId order by model.id.serviceId desc")
	List<Serviceinfo> findServiceIdOrderByDESC(@Param("requestId") String requestId);
	
	@Query("Select model from Serviceinfo model where model.id.requestId = :requestId and model.isCancelled = '0' and model.isDeletedFromProvider = '0'")
	List<Serviceinfo> findAllNotCancelledByrequestId(@Param("requestId") String requestId);
	
	@Query("Select model from Serviceinfo model where model.id.requestId = :requestId and model.isOverriden = '0' and model.isDeletedFromProvider = '0'")
	List<Serviceinfo> findAllNotOveeridenByrequestId(@Param("requestId") String requestId);
}