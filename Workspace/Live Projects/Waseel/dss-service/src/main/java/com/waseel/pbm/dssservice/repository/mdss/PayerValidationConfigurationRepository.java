package com.waseel.pbm.dssservice.repository.mdss;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.waseel.pbm.dssservice.persist.mdss.PayerValidationConfiguration;

public interface PayerValidationConfigurationRepository extends CrudRepository<PayerValidationConfiguration, Long> {

	Optional<PayerValidationConfiguration> findFirstByPayerId(String payerId);

}
