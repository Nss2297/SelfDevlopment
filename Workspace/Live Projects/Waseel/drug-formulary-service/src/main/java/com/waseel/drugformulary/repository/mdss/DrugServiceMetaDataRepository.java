package com.waseel.drugformulary.repository.mdss;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waseel.drugformulary.persist.mdss.DrugServiceMetaData;

public interface DrugServiceMetaDataRepository extends JpaRepository<DrugServiceMetaData, Long> {

	Optional<DrugServiceMetaData> findFirstByEffectiveDateLessThanEqualOrderByEffectiveDateDescUploadDateTimeDesc(
			Date currentDate);
}
