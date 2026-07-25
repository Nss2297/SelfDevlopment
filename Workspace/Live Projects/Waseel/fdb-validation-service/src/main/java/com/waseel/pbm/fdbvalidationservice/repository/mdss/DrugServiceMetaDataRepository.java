package com.waseel.pbm.fdbvalidationservice.repository.mdss;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.waseel.pbm.fdbvalidationservice.persist.mdss.DrugServiceMetaData;

public interface DrugServiceMetaDataRepository extends CrudRepository<DrugServiceMetaData, Long> {

	Optional<DrugServiceMetaData> findFirstByEffectiveDateLessThanEqualOrderByEffectiveDateDescUploadDateTimeDesc(
			Date currentDate);

}
