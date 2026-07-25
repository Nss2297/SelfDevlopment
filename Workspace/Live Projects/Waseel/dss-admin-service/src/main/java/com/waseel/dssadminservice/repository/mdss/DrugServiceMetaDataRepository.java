package com.waseel.dssadminservice.repository.mdss;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.waseel.dssadminservice.persist.mdss.DrugServiceMetaData;

@Repository
public interface DrugServiceMetaDataRepository extends JpaRepository<DrugServiceMetaData, Long> {

	Optional<DrugServiceMetaData> findByDrugListId(Long drugListId);

	void deleteByDrugListId(Long drugListId);

	Optional<DrugServiceMetaData> findByEffectiveDate(Date effectiveDate);

	Optional<DrugServiceMetaData> findByFileNameAndEffectiveDate(String fileName, Date effectiveDate);

	Optional<DrugServiceMetaData> findFirstByOrderByDrugListIdDesc();
	
	Optional<DrugServiceMetaData> findFirstByEffectiveDateLessThanEqualOrderByEffectiveDateDescUploadDateTimeDesc(
			Date currentDate);
}
