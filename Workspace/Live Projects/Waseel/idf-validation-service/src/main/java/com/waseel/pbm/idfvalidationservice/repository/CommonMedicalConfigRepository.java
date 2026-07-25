package com.waseel.pbm.idfvalidationservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.idfvalidationservice.persist.CommonMedicalConfig;

@Repository
public interface CommonMedicalConfigRepository extends CrudRepository<CommonMedicalConfig, String> {

}