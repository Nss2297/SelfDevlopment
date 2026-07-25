package com.waseel.pbm.pbmadminservice.service;

import com.waseel.pbm.pbmadminservice.model.ProviderInformationModel;
import com.waseel.pbm.pbmadminservice.specification.ProviderInfoSpecification;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ProviderInformationService {

    private final Logger log = LoggerFactory.getLogger(ProviderInformationService.class);

    @Autowired
    private ProviderInfoSpecification providerSpecification;

    public Page<ProviderInformationModel> getAllProvidersInformation(int pageNumber, int recordSize, String value) {
        String valueTrim = !StringUtils.isBlank(value) ? value.trim() : value;
        log.info("Page Number :- {}, Record Size :- {}, provider value is : {}", pageNumber, recordSize, value);
        return providerSpecification.findByCodeOrSourceOrProviderNameWithPagination(pageNumber, recordSize, valueTrim);
    }

}
