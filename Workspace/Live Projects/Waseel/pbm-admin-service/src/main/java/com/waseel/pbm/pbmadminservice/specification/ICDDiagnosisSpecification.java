package com.waseel.pbm.pbmadminservice.specification;

import com.waseel.pbm.pbmadminservice.model.ICDDiagnosisModel;
import com.waseel.pbm.pbmadminservice.persist.hira.ICDDiagnosis;
import com.waseel.pbm.pbmadminservice.repository.hira.ICDDiagnosisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class ICDDiagnosisSpecification {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    ICDDiagnosisRepository icdDiagnosisRepository;

    public Page<ICDDiagnosis> findByIcdCodeAndDescWithPagination(int pageNumber, int recordSize,
                                                                 ICDDiagnosisModel icdDiagnosisModel) {
        Pageable pageable = PageRequest.of(pageNumber, recordSize);
        return icdDiagnosisRepository.findAll(icdDiagnosisModel, pageable);
    }

}
