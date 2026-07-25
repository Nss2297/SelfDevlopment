package com.waseel.pbm.dssservice.repository.mdss;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.dssservice.persist.mdss.ChronicDzDrugAssoc;

@Repository
public interface ChronicDzDrugAssocRepository extends CrudRepository<ChronicDzDrugAssoc, Integer> {

	@Query("select model.chronicDzInformation.chronicDiseasesId from ChronicDzDrugAssoc model where model.chronicDzInformation.chronicDiseasesId in (:chronicDzIds ) and model.serviceCode = :serviceCode and model.isEnabled='1'")
	List<Integer> findByChronicDzIdsAndServiceCode(List<Integer> chronicDzIds, String serviceCode);

}
