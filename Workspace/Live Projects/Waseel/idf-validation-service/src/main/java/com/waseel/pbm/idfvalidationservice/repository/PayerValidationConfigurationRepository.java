package com.waseel.pbm.idfvalidationservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.waseel.pbm.idfvalidationservice.persist.PayerValidationConfiguration;

public interface PayerValidationConfigurationRepository extends CrudRepository<PayerValidationConfiguration, Long> {

}
