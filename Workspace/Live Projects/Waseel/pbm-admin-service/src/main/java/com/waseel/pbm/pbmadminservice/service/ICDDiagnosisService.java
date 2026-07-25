package com.waseel.pbm.pbmadminservice.service;

import com.waseel.pbm.pbmadminservice.model.ICDDiagnosisModel;
import com.waseel.pbm.pbmadminservice.persist.hira.ICDDiagnosis;
import com.waseel.pbm.pbmadminservice.specification.ICDDiagnosisSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ICDDiagnosisService {

    private final Logger log = LoggerFactory.getLogger(ICDDiagnosisService.class);

    @Autowired
    private ICDDiagnosisSpecification icdDiagnosisSpecification;

    public Page<ICDDiagnosis> getAllIcdCodeAndDescription(int pageNumber, int recordSize, String icdCode, String description) {
        log.info("Page Number :- {}, Record Size :- {}, description :- {}, icdCode :- {} ",
                pageNumber, recordSize, description, icdCode);
        ICDDiagnosisModel icdDiagnosisModel = new ICDDiagnosisModel(icdCode, description);
        return icdDiagnosisSpecification.findByIcdCodeAndDescWithPagination(pageNumber, recordSize, icdDiagnosisModel);
    }
}
