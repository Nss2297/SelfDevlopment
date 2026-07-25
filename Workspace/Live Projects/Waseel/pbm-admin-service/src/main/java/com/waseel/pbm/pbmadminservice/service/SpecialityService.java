package com.waseel.pbm.pbmadminservice.service;

import com.waseel.pbm.pbmadminservice.model.SpecialityModel;
import com.waseel.pbm.pbmadminservice.specification.SpecialitySpecification;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class SpecialityService {

    private final Logger log = LoggerFactory.getLogger(SpecialityService.class);

    @Autowired
    private SpecialitySpecification specialitySpecification;

    public Page<SpecialityModel> findSpecialitiesWithPagination(
            int pageNumber, int recordSize, String value) {
        log.info("Page Number :- {}, Record Size :- {}", pageNumber, recordSize);
        String valueTrim = !StringUtils.isBlank(value) ? value.trim() : value;
		SpecialityModel specialityModel = new SpecialityModel(valueTrim);
        return specialitySpecification.findSpecialitiesWithPagination(pageNumber, recordSize, specialityModel);
    }
}
