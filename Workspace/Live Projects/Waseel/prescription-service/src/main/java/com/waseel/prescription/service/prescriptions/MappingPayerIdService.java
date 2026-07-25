package com.waseel.prescription.service.prescriptions;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.prescription.model.enums.DssPayerTransactionType;
import com.waseel.prescription.model.prescription.PrescriptionRequestModel;
import com.waseel.prescription.persist.prescriptionservice.MappingPayerId;
import com.waseel.prescription.repository.prescriptionservice.MappingPayerIdRepository;

@Service
public class MappingPayerIdService {

	@Autowired
	private MappingPayerIdRepository mappingPayerIdRepository;

	public void mapPayerIdForDss(PrescriptionRequestModel prescriptionRequestModel, String payerId) {
		String mappedPayerId = fetchMappedPayerIdForDss(payerId);
		prescriptionRequestModel.setPayerId(mappedPayerId);
	}

	private String fetchMappedPayerIdForDss(String payerId) {
		Optional<MappingPayerId> mappingPayerIdOpt = mappingPayerIdRepository
				.findByPayerIdAndTransactionTypeAndIsEnabled(payerId, DssPayerTransactionType.PRESCRIPTION.value(),
						true);
		if (mappingPayerIdOpt.isPresent()) {
			return mappingPayerIdOpt.get().getMapperPayerId();
		}
		return "";
	}
	
	public String fetchPayerIdByMappedPayerId(String mappedPayerId) {
		return mappingPayerIdRepository
				.findByMapperPayerIdAndIsEnabledAndTransactionType(mappedPayerId, true,
						DssPayerTransactionType.PRESCRIPTION.value())
				.map(MappingPayerId::getPayerId).orElse(mappedPayerId);
	}
}