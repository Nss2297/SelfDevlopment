package com.waseel.pbm.fdbvalidationservice.repository.mdss;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.waseel.pbm.fdbvalidationservice.persist.mdss.PayerValidationConfiguration;

public interface PayerValidationConfigurationRepository extends CrudRepository<PayerValidationConfiguration, Long> {
	Optional<PayerValidationConfiguration> findFirstByPayerId(String payerId);
}
