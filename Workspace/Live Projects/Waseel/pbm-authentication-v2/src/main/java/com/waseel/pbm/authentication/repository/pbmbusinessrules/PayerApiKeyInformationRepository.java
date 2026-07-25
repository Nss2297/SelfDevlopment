package com.waseel.pbm.authentication.repository.pbmbusinessrules;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.waseel.pbm.authentication.model.pbmbusinessrules.entity.PayerApiKeyInformation;

public interface PayerApiKeyInformationRepository extends CrudRepository<PayerApiKeyInformation, Long> {

	List<PayerApiKeyInformation> findByPayerId(String payerId);
}
