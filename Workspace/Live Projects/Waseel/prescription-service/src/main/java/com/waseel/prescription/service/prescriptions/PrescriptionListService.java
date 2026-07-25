package com.waseel.prescription.service.prescriptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.waseel.prescription.model.prescription.PrescriptionsSearchModel;
import com.waseel.prescription.model.prescription.ProviderPrescriptionResponseModel;
import com.waseel.prescription.specification.ProviderPrescriptionSpecification;
import com.waseel.prescription.util.UserInfoUtil;

@Service
public class PrescriptionListService {

	@Autowired
	ProviderPrescriptionSpecification providerPrescriptionSpecificationRepo;

	private final Logger log = LoggerFactory.getLogger(PrescriptionListService.class);

	public Page<ProviderPrescriptionResponseModel> getProviderPrescriptions(PrescriptionsSearchModel request) {
		String providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		log.info("Page Number :- {} Record Size :- {} Provider Id :- {}", request.getPageNumber(),
				request.getRecordSize(), providerId);
		return providerPrescriptionSpecificationRepo.getProviderPrescriptionsPaginated(request);
	}
}
