package com.waseel.prescription.service.prescriptions;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.prescription.model.enums.BenefitCodeType;
import com.waseel.prescription.persist.businessrules.BenefitCodePhyscSpecAssc;
import com.waseel.prescription.persist.businessrules.PhysicianInfo;
import com.waseel.prescription.persist.businessrules.Speciality;
import com.waseel.prescription.persist.prescriptionservice.Physician;
import com.waseel.prescription.repository.businessrules.BenefitCodePhyscSpecAsscRepository;
import com.waseel.prescription.repository.businessrules.PhysicianInfoRepository;
import com.waseel.prescription.repository.businessrules.SpecialityRepository;
import com.waseel.prescription.repository.prescriptionservice.PhysicianRepository;

@Service
public class FetchBenefitCodeService {

	@Autowired
	PhysicianRepository physicianRepository;

	@Autowired
	private PhysicianInfoRepository physicianInfoRepository;

	@Autowired
	private SpecialityRepository specialityRepository;

	@Autowired
	private BenefitCodePhyscSpecAsscRepository benefitCodePhyscSpecAsscRepository;

	public String fetchBenefitCodeByRequestId(String requestId) {
		Optional<Physician> physicianOpt = physicianRepository.findByRequestId(requestId);
		if (physicianOpt.isPresent()) {
			Physician physician = physicianOpt.get();
			return fetchBenefitCodeByPhysicianDetails(physician.getPhysicianLicenseNumber(),
					physician.getPhysicianSpeciality());
		}
		return BenefitCodeType.BASIC_BENEFIT.value();
	}

	public String fetchBenefitCodeByPhysicianDetails(String physicianLicenseNumber, String physicianSpeciality) {
		Optional<PhysicianInfo> physicianInfoOpt = physicianInfoRepository
				.findByRegistrationNumber(physicianLicenseNumber);
		if (physicianInfoOpt.isPresent()) {
			Optional<Speciality> specialityOpt = specialityRepository
					.findBySpecialityNameAndIsDeleted(physicianSpeciality, false);
			if (specialityOpt.isPresent()) {
				Optional<BenefitCodePhyscSpecAssc> benefitCodePhyscSpecAsscOpt = benefitCodePhyscSpecAsscRepository
						.findBySpecialityIdAndIsEnabled(specialityOpt.get().getSpecialityId(), Boolean.TRUE);
				if (benefitCodePhyscSpecAsscOpt.isPresent()) {
					return benefitCodePhyscSpecAsscOpt.get().getBenefitCodes().getBenefitCodeName();
				}
			}
		}
		return BenefitCodeType.BASIC_BENEFIT.value();
	}
}
