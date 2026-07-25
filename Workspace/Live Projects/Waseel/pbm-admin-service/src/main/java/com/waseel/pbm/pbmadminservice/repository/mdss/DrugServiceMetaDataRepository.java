package com.waseel.pbm.pbmadminservice.repository.mdss;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.waseel.pbm.pbmadminservice.persist.mdss.DrugServiceMetaData;

public interface DrugServiceMetaDataRepository extends JpaRepository<DrugServiceMetaData, Long> {

	@Query(value = "SELECT \"DrugListId\" from \"DrugServiceMetaData\""
			+ " ORDER BY \"Effective_Date\"  DESC FETCH NEXT 1 ROWS ONLY", nativeQuery = true)
	Long getDataListIdOrderByEffectiveDateTime();

	Optional<DrugServiceMetaData> findFirstByEffectiveDateLessThanEqualOrderByEffectiveDateDescUploadDateTimeDesc(
			Date currentDate);

}
