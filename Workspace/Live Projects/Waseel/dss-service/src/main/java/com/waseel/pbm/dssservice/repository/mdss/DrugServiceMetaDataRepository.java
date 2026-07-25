package com.waseel.pbm.dssservice.repository.mdss;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.waseel.pbm.dssservice.persist.mdss.DrugServiceMetaData;

public interface DrugServiceMetaDataRepository extends CrudRepository<DrugServiceMetaData, Long> {

	DrugServiceMetaData findBydrugListId(Long drugListId);

	Optional<DrugServiceMetaData> findFirstByEffectiveDateLessThanEqualOrderByEffectiveDateDescUploadDateTimeDesc(Date currentDate);

}
