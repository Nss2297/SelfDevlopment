package com.waseel.smsservice.repository.hira;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.smsservice.persist.WaseelSmsConfiguration;

@Repository
public interface WaseelSmsConfigurationRepository extends CrudRepository<WaseelSmsConfiguration, Long> {

	Optional<WaseelSmsConfiguration> findByUnifonicAppIdAndIsEnabled(String unifonicAppId, String isEnabled);
}
