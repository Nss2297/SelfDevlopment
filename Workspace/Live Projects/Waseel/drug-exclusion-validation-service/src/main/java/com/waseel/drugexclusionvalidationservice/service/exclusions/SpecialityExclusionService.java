package com.waseel.drugexclusionvalidationservice.service.exclusions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.drugexclusionvalidationservice.model.DrugExclusionModel;
import com.waseel.drugexclusionvalidationservice.model.DrugExclusionRequestModel;
import com.waseel.drugexclusionvalidationservice.model.enums.DenialCode;
import com.waseel.drugexclusionvalidationservice.model.enums.ServiceStatus;
import com.waseel.drugexclusionvalidationservice.persist.businessrules.CommonDenials;
import com.waseel.drugexclusionvalidationservice.persist.businessrules.DeptSpecPhyscAssc;
import com.waseel.drugexclusionvalidationservice.persist.businessrules.DrugExclusionDetails;
import com.waseel.drugexclusionvalidationservice.persist.businessrules.DrugExclusionMetadata;
import com.waseel.drugexclusionvalidationservice.persist.businessrules.PhysicianInfo;
import com.waseel.drugexclusionvalidationservice.persist.businessrules.Speciality;
import com.waseel.drugexclusionvalidationservice.persist.businessrules.SpecialityExclusionAssc;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.CommonDenialsRepository;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.DeptSpecPhyscAsscRepository;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.DrugExclusionDetailsRepository;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.DrugExclusionMetadataRepository;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.PhysicianInfoRepository;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.SpecialityExclusionAsscRepository;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.SpecialityRepository;

@Service
public class SpecialityExclusionService {

	@Autowired
	private PhysicianInfoRepository physicianInfoRepository;
	@Autowired
	private SpecialityRepository specialityRepository;
	@Autowired
	private DeptSpecPhyscAsscRepository deptSpecPhyscAsscRepository;
	@Autowired
	private SpecialityExclusionAsscRepository specialityExclusionAsscRepository;
	@Autowired
	private DrugExclusionDetailsRepository drugExclusionDetailsRepository;
	@Autowired
	private CommonDenialsRepository commonDenialsRepository;
	@Autowired
	private DrugExclusionMetadataRepository drugExclusionMetadataRepository;

	public List<DrugExclusionModel> checkSpecialityExclusion(DrugExclusionRequestModel requestModel) {
		PhysicianInfo physicianInfo = physicianInfoRepository
				.findByRegistrationNumber(requestModel.getPhysicianLicenseNumber());
		Speciality speciality = specialityRepository
				.findBySpecialityNameAndIsDeleted(requestModel.getPhysicianSpeciality(), false);
		if (physicianInfo != null && speciality != null) {
			BigDecimal specialityId = speciality.getSpecialityId();
			DeptSpecPhyscAssc deptSpecPhyscAssc = deptSpecPhyscAsscRepository
					.findByPhysicianInfoIdAndSpecialityIdAndIsEnabled(physicianInfo.getPhysicianInfoId(), specialityId,
							true);
			if (deptSpecPhyscAssc != null) {
				List<SpecialityExclusionAssc> specialityExclusionAsscList = specialityExclusionAsscRepository
						.findBySpecialityIdAndIsEnabled(specialityId, true);
				if (null != specialityExclusionAsscList && !specialityExclusionAsscList.isEmpty()) {
					Long payerId = Long.parseLong(requestModel.getPayerId());
					List<DrugExclusionDetails> drugExclusionDetails = new ArrayList<>();
					specialityExclusionAsscList.stream().forEach(specialityExclusionAssociation -> {
						Long exclusionId = specialityExclusionAssociation.getExclusionId();
						DrugExclusionMetadata drugExclusionMetadata = drugExclusionMetadataRepository
								.findByExclusionIdAndPayerIdAndIsDeleted(exclusionId, payerId, false);
						if (drugExclusionMetadata != null) {
							List<DrugExclusionDetails> drugExclusionDetailsList = drugExclusionDetailsRepository
									.findByExclusionIdAndIsDeleted(exclusionId, false);
							if (null != drugExclusionDetailsList && !drugExclusionDetailsList.isEmpty()) {
								drugExclusionDetailsList.stream()
										.filter(drug -> drugExclusionDetails.stream()
												.noneMatch(exclusionDrug -> exclusionDrug.getRegistrationNumber()
														.equals(drug.getRegistrationNumber())))
										.forEach(drugExclusionDetails::add);
							}
						}
					});
					return validateDrugListAgainstSpeciality(drugExclusionDetails, requestModel.getDrugList());
				}
			}
		}
		return new ArrayList<>();
	}

	private List<DrugExclusionModel> validateDrugListAgainstSpeciality(
			List<DrugExclusionDetails> drugExclusionDetailsList, List<String> drugList) {
		List<DrugExclusionModel> responseModelList = new ArrayList<>();
		String denialCodeDesc = getDenialCodeDescription();
		drugList.forEach(drug -> {
			DrugExclusionModel responseModel = new DrugExclusionModel();
			responseModel.setDrugCode(drug);
			responseModel.setStatusCode(ServiceStatus.APPROVED.value());
			if (drugExclusionDetailsList.stream()
					.anyMatch(drugExclusionDetails -> drugExclusionDetails.getRegistrationNumber().equals(drug))) {
				responseModel.setDenialCode(DenialCode.SPECIALITY_EXCLUSION.value());
				responseModel.setStatusCode(ServiceStatus.REJECTED.value());
				responseModel.setStatusDescription(denialCodeDesc.replace("<drugcode> <DrugName>", drug));
			}
			responseModelList.add(responseModel);
		});
		return responseModelList;
	}

	private String getDenialCodeDescription() {
		Optional<CommonDenials> commonDenialsOptional = commonDenialsRepository
				.findByDenialCode(DenialCode.SPECIALITY_EXCLUSION.value());
		if (commonDenialsOptional.isPresent()) {
			return commonDenialsOptional.get().getDenialDescription();
		}
		return null;
	}
}
