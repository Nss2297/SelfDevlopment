package com.waseel.pbm.idfvalidationservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.idfvalidationservice.persist.ChronicDzDrugAssoc;

@Repository
public interface ChronicDzDrugAssocRepository extends CrudRepository<ChronicDzDrugAssoc, Integer> {

	@Query("select model from ChronicDzDrugAssoc model where model.chronicDzInformation.chronicDiseasesId in (:chronicDzIds ) and model.serviceCode = :serviceCode and model.isEnabled='1'")
	List<ChronicDzDrugAssoc> findByChronicDzInformationAndServiceCode(List<Integer> chronicDzIds, String serviceCode);

}
