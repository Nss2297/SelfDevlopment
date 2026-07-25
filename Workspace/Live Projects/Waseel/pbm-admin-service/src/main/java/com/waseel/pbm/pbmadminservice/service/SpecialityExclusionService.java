package com.waseel.pbm.pbmadminservice.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waseel.pbm.pbmadminservice.enums.AuditUpdatedType;
import com.waseel.pbm.pbmadminservice.enums.EntitiesName;
import com.waseel.pbm.pbmadminservice.enums.drugexclusion.ExclusionMessages;
import com.waseel.pbm.pbmadminservice.enums.drugexclusion.ExclusionType;
import com.waseel.pbm.pbmadminservice.exceptions.AdminException;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.speciality.SpecialityExclusionModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.speciality.SpecialityExclusionRequestModel;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugExclusionMetadata;
import com.waseel.pbm.pbmadminservice.persist.businessrules.ExclusionAsscTypeList;
import com.waseel.pbm.pbmadminservice.persist.businessrules.Speciality;
import com.waseel.pbm.pbmadminservice.persist.businessrules.SpecialityExclusionAssc;
import com.waseel.pbm.pbmadminservice.repository.businessrules.DrugExclusionMetadataRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.ExclusionAsscTypeListRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.SpecialityExclusionAsscRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.SpecialityRepository;
import com.waseel.pbm.pbmadminservice.util.UserInfoUtil;

@Service
public class SpecialityExclusionService {

	private final Logger log = LoggerFactory.getLogger(SpecialityExclusionService.class);
	@Autowired
	private SpecialityExclusionAsscRepository specialityExclusionAsscRepository;
	@Autowired
	private ExclusionAsscTypeListRepository exclusionAsscTypeListRepository;
	@Autowired
	private AuditLogService auditLogService;
	@Autowired
	private SpecialityRepository specialityRepository;
	@Autowired
	private DrugExclusionMetadataRepository drugExclusionMetadataRepository;

	@Transactional(value = "BusinessRulesTransactionManager")
	public SpecialityExclusionModel addSpecialityExclusion(
			SpecialityExclusionRequestModel specialityExclusionRequestModel, Long exclusionId) throws AdminException {

		Long payerId = getPayerIdFromAuthentication();
		BigDecimal specialityId = new BigDecimal(specialityExclusionRequestModel.getspecialityId());

		DrugExclusionMetadata drugExclusionMetadata = validateExclusionId(exclusionId, payerId);
		SpecialityExclusionAssc specialityExclusionAssc = createOrUpdateSpecialityExclusionAssc(exclusionId,
				specialityId);
		saveSpecialityExclusionInExclusionAsscTypeList(specialityExclusionAssc, payerId);
		updateLastUpdateDateInDrugExclusionMetadata(drugExclusionMetadata);

		return prepareSpecialityExclusionResponse(specialityExclusionAssc.getSpecialityExclusionAsscId());
	}

	private SpecialityExclusionModel prepareSpecialityExclusionResponse(Long specialityExclusionAsscId) {
		SpecialityExclusionModel specialityExclusionModel = new SpecialityExclusionModel();
		specialityExclusionModel.setSpecialityExclusionAsscId(specialityExclusionAsscId);
		return specialityExclusionModel;
	}

	public Long deleteSpecialityExclusion(Long id) throws AdminException {
		return specialityExclusionAsscRepository.findBySpecialityExclusionAsscIdAndIsEnabled(id, true)
				.map(specialityExclusionAssc -> {
					specialityExclusionAssc.setIsEnabled(false);
					specialityExclusionAssc.setLastUpdateDate(new Date());
					specialityExclusionAssc = specialityExclusionAsscRepository.save(specialityExclusionAssc);
					auditLogService.addDataInAuditLog(AuditUpdatedType.DELETE, id,
							EntitiesName.SPECIALITY_EXCLUSION_ASSC, specialityExclusionAssc);
					return specialityExclusionAssc.getExclusionId();
				}).orElseThrow(() -> new AdminException(ExclusionMessages.EXCLUSION_ASSC_ID_NOT_FOUND.value()));
	}

	private SpecialityExclusionAssc createOrUpdateSpecialityExclusionAssc(Long exclusionId, BigDecimal specialityId)
			throws AdminException {
		Optional<SpecialityExclusionAssc> specialityExclusionAsscOpt = specialityExclusionAsscRepository
				.findByExclusionIdAndSpecialityId(exclusionId, specialityId);

		if (specialityExclusionAsscOpt.isPresent()) {
			SpecialityExclusionAssc specialityExclusionAssc = specialityExclusionAsscOpt.get();

			if (Boolean.TRUE.equals(specialityExclusionAssc.getIsEnabled())) {
				throw new AdminException(ExclusionMessages.SPECIALITY_EXCLUSION_ALREADY_EXISTS.value());
			}
			specialityExclusionAssc.setIsEnabled(true);
			specialityExclusionAssc.setLastUpdateDate(new Date());

			SpecialityExclusionAssc updatedSpecialityExclusionAssc = saveSpecialityExclusionAssc(
					specialityExclusionAssc);
			manageAuditLog(AuditUpdatedType.UPDATE, updatedSpecialityExclusionAssc.getSpecialityExclusionAsscId(),
					EntitiesName.SPECIALITY_EXCLUSION_ASSC, updatedSpecialityExclusionAssc);

			return updatedSpecialityExclusionAssc;
		}

		SpecialityExclusionAssc specialityExclusionAssc = new SpecialityExclusionAssc();
		specialityExclusionAssc.setSpecialityId(specialityId);
		specialityExclusionAssc.setExclusionId(exclusionId);
		specialityExclusionAssc.setLastUpdateDate(new Date());
		specialityExclusionAssc.setIsEnabled(true);

		SpecialityExclusionAssc addedSpecialityExclusionAssc = saveSpecialityExclusionAssc(specialityExclusionAssc);
		manageAuditLog(AuditUpdatedType.INSERT, addedSpecialityExclusionAssc.getSpecialityExclusionAsscId(),
				EntitiesName.SPECIALITY_EXCLUSION_ASSC, addedSpecialityExclusionAssc);

		return addedSpecialityExclusionAssc;
	}

	public void saveSpecialityExclusionInExclusionAsscTypeList(SpecialityExclusionAssc specialityExclusionAssc,
			Long payerId) {
		if (specialityExclusionAssc != null) {
			Optional<ExclusionAsscTypeList> exclusionAsscTypeListOpt = exclusionAsscTypeListRepository
					.findByExclusionIdAndExclusionAsscIdAndExclusionTypeAndPayerId(
							specialityExclusionAssc.getExclusionId(),
							specialityExclusionAssc.getSpecialityExclusionAsscId(),
							ExclusionType.SPECIALITY_EXCLUSION.value(), payerId);
			if (exclusionAsscTypeListOpt.isEmpty()) {
				ExclusionAsscTypeList exclusionAsscTypeList = new ExclusionAsscTypeList();
				exclusionAsscTypeList.setExclusionId(specialityExclusionAssc.getExclusionId());
				exclusionAsscTypeList.setExclusionType(ExclusionType.SPECIALITY_EXCLUSION.value());
				exclusionAsscTypeList.setExclusionAsscId(specialityExclusionAssc.getSpecialityExclusionAsscId());
				exclusionAsscTypeList.setPayerId(payerId);
				exclusionAsscTypeList.setSpecialityId(specialityExclusionAssc.getSpecialityId());
				exclusionAsscTypeList
						.setExclusionTypeName(getSpecialityName(specialityExclusionAssc.getSpecialityId()));
				exclusionAsscTypeListRepository.save(exclusionAsscTypeList);
			}
		}
	}

	private void manageAuditLog(AuditUpdatedType auditUpdatedType, Long id, EntitiesName entityName,
			SpecialityExclusionAssc specialityExclusionAssc) {
		auditLogService.addDataInAuditLog(auditUpdatedType, id, entityName, specialityExclusionAssc);
	}

	private SpecialityExclusionAssc saveSpecialityExclusionAssc(SpecialityExclusionAssc specialityExclusionAssc) {
		SpecialityExclusionAssc savedSpecialityExclusionAssc = specialityExclusionAsscRepository
				.save(specialityExclusionAssc);
		log.info("Speciality exclusion added successfully for exclusionId {} specialityId{}",
				savedSpecialityExclusionAssc.getExclusionId(), savedSpecialityExclusionAssc.getSpecialityId());
		return savedSpecialityExclusionAssc;
	}

	private DrugExclusionMetadata validateExclusionId(Long exclusionId, Long payerId) throws AdminException {
		Optional<DrugExclusionMetadata> drugExclusionMetadataOpt = drugExclusionMetadataRepository
				.findByExclusionIdAndPayerIdAndIsDeleted(exclusionId, payerId, false);
		if (drugExclusionMetadataOpt.isEmpty()) {
			throw new AdminException(ExclusionMessages.EXCLUSIONID_NOT_FOUND.value());
		}
		return drugExclusionMetadataOpt.get();
	}

	private String getSpecialityName(BigDecimal specialityId) {
		Optional<Speciality> optionalSpeciality = specialityRepository.findBySpecialityIdAndIsDeleted(specialityId,
				false);
		if (optionalSpeciality.isPresent()) {
			return optionalSpeciality.get().getSpecialityName();
		}
		return null;
	}

	private void updateLastUpdateDateInDrugExclusionMetadata(DrugExclusionMetadata drugExclusionMetadata) {
		if (drugExclusionMetadata != null) {
			drugExclusionMetadata.setLastUpdateDate(new Date());
			drugExclusionMetadataRepository.save(drugExclusionMetadata);
		}
	}

	private Long getPayerIdFromAuthentication() {
		return Long.parseLong(UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication()));
	}
}
