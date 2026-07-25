package com.waseel.prescription.repository.businessrules;

import com.waseel.prescription.persist.businessrules.PayerConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PayerConfigurationRepository
        extends JpaRepository<PayerConfiguration, String>, JpaSpecificationExecutor<PayerConfiguration> {
    Optional<PayerConfiguration> findByPayerId(String payerId);
}
