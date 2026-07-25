package com.waseel.pbm.dssservice.repository.mdss;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.waseel.pbm.dssservice.persist.mdss.IcdDiagnosisInfo;
import com.waseel.pbm.dssservice.persist.mdss.IcdDiagnosisInfoId;

import feign.Param;

@Repository
public interface IcdDiagnosisInfoRepository extends CrudRepository<IcdDiagnosisInfo, IcdDiagnosisInfoId> {
	
	@Query("Select model from IcdDiagnosisInfo model where model.id.requestId = :requestId")
	List<IcdDiagnosisInfo> findByrequestId(@Param("requestId") String requestId);

	@Transactional
	@Modifying
	@Query("Update IcdDiagnosisInfo set isDeletedFromProvider = '1' where id.requestId = :requestId")
	int updateIcdDiagnosisInfoIsDeletedFlag(@Param("requestId") String requestId);
	
	@Override
	default <S extends IcdDiagnosisInfo> Iterable<S> saveAll(Iterable<S> entities) {
		
		if(entities!= null) {
			for (IcdDiagnosisInfo entity :entities ) {
				save(entity);
			}
		}
		return null;
	}
}
