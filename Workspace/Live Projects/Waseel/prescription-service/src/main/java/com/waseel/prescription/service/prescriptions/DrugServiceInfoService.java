package com.waseel.prescription.service.prescriptions;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.waseel.prescription.model.common.DrugServiceModel;
import com.waseel.prescription.model.formulary.DrugFormularyDetailsModel;
import com.waseel.prescription.repository.mdss.DrugServiceMetaDataRepository;
import com.waseel.prescription.service.clienthandler.RestHandler;
import com.waseel.prescription.specification.DrugServiceSpecification;

@Service
public class DrugServiceInfoService {

    private final Logger log = LoggerFactory.getLogger(DrugServiceInfoService.class);

    @Autowired
    private DrugServiceSpecification drugServiceSpecification;

    @Autowired
    private DrugServiceMetaDataRepository drugServiceMetaDataRepository;

    @Autowired
    private RestHandler restHandler;

    @Autowired
    private Environment environment;

    @Autowired(required = false)
    private DrugsFullTextSearchService drugsFullTextSearchService;

    public Page<DrugServiceModel> getDrugs(int pageNumber, int recordSize, String value, String payerId,
            String idNumber, String searchBy) {
        log.info("searchBy :- {}. Value :- {}", searchBy, value);
        Long formularyId = 0L;
        if (!StringUtils.isBlank(payerId) && !StringUtils.isBlank(idNumber))
            formularyId = getDrugFormularyId(payerId, idNumber);
        Long activeDrugListId = drugServiceMetaDataRepository.getActiveDrugServiceList(new Date()).get();

        if (Arrays.asList(environment.getActiveProfiles()).contains("elasticsearch")) {
           return drugsFullTextSearchService.search(value, formularyId, activeDrugListId, searchBy, pageNumber,
                    recordSize);
        }
        DrugServiceModel service = new DrugServiceModel(value, formularyId, activeDrugListId, searchBy);
        return drugServiceSpecification.findByServiceCodeAndDescriptionWithPagination(pageNumber, recordSize, service);
    }

    private Long getDrugFormularyId(String payerId, String idNumber) {
        DrugFormularyDetailsModel model = restHandler.getDrugFormularyDetails(payerId, idNumber);
        return model != null ? model.getFormularyId() : 0L;
    }
}
