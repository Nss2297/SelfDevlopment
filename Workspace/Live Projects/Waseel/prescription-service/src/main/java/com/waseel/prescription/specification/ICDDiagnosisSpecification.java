package com.waseel.prescription.specification;

import com.waseel.prescription.model.prescription.DiagnosisCodes;
import com.waseel.prescription.model.prescription.ICDDiagnosisModel;
import com.waseel.prescription.persist.hira.ICDDiagnosis;
import com.waseel.prescription.persist.prescriptionservice.Diagnosis;
import com.waseel.prescription.repository.hira.ICDDiagnosisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class ICDDiagnosisSpecification {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    ICDDiagnosisRepository icdDiagnosisRepository;

    public Page<ICDDiagnosis> findByIcdCodeAndDescWithPagination(int pageNumber, int recordSize,
                                                                 List<ICDDiagnosisModel> icdDiagnosisModel) {
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<DiagnosisCodes> query =
//                criteriaBuilder.createQuery(DiagnosisCodes.class);
//        Root<Diagnosis> root = query.from(Diagnosis.class);
//        query.where(diagnosisCodes.toPredicate(root, query, criteriaBuilder));
//        query.multiselect(root.get("diagnosisId").get("diagnosisCode"), root.get("diagnosisType"));
//        TypedQuery<DiagnosisCodes> typedQuery = entityManager.createQuery(query);
//        typedQuery.setFirstResult(pageNumber * recordSize);
//        typedQuery.setMaxResults(recordSize);
//        return typedQuery.getResultList();
//        Pageable pageable = PageRequest.of(pageNumber, recordSize);
//        return icdDiagnosisRepository.findAll(icdDiagnosisModel, pageable);
        return null;
    }

}
