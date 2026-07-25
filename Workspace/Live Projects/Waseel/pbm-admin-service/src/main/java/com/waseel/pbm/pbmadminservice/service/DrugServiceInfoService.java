package com.waseel.pbm.pbmadminservice.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.waseel.pbm.pbmadminservice.model.DrugServiceModel;
import com.waseel.pbm.pbmadminservice.persist.mdss.DrugServiceMetaData;
import com.waseel.pbm.pbmadminservice.repository.mdss.DrugServiceMetaDataRepository;
import com.waseel.pbm.pbmadminservice.specification.DrugServiceSpecification;

@Service
public class DrugServiceInfoService {

	private final Logger log = LoggerFactory.getLogger(DrugServiceInfoService.class);

	@Autowired
	private DrugServiceSpecification drugServiceSpecification;

	@Autowired
	private DrugServiceMetaDataRepository drugServiceMetaDataRepository;

	public Page<DrugServiceModel> getAllServiceCodeAndDescription(int pageNumber, int recordSize, String serviceCode,
			String description) {
		log.info("Page Number :- {}, Record Size :- {}, description :- {}, serviceCode :- {} ", pageNumber, recordSize,
				description, serviceCode);
		DrugServiceModel service = new DrugServiceModel();
		service.setDescription(description);
		service.setServiceCode(serviceCode);
		Long drugListId = getDrugListId();
		service.setDrugListId(drugListId);
		return drugServiceSpecification.findByServiceCodeAndDescWithPagination(pageNumber, recordSize, service);
	}

	private Long getDrugListId() {
		return drugServiceMetaDataRepository
				.findFirstByEffectiveDateLessThanEqualOrderByEffectiveDateDescUploadDateTimeDesc(new Date())
				.map(DrugServiceMetaData::getDrugListId).orElse(0L);
	}
}
