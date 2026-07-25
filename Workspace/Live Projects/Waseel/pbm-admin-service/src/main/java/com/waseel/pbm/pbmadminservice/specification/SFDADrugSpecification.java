package com.waseel.pbm.pbmadminservice.specification;

import com.waseel.pbm.pbmadminservice.model.SFDADrugReponseModel;
import com.waseel.pbm.pbmadminservice.model.SFDADrugRequestModel;
import com.waseel.pbm.pbmadminservice.persist.mdss.DrugService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class SFDADrugSpecification {

    @PersistenceContext
    private EntityManager entityManager;

    public Page<SFDADrugReponseModel> findSFDADrugsWithPagination(SFDADrugRequestModel drugModel) {
        SFDADrugRequestModel sfdaDrugRequestModel;
        if (null != drugModel) {
            sfdaDrugRequestModel = new SFDADrugRequestModel(drugModel.getSfdaCode(), drugModel.getTradeName(),
                    drugModel.getScientificCode(), drugModel.getScientificName(), drugModel.getGtinCode(),
                    drugModel.getPageNumber(), drugModel.getRecordSize());
        } else {
            sfdaDrugRequestModel = new SFDADrugRequestModel();
        }
        int pageNumber = sfdaDrugRequestModel.getPageNumber();
        int recordSize = sfdaDrugRequestModel.getRecordSize();
        PageRequest pageRequest = PageRequest.of(pageNumber, recordSize);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SFDADrugReponseModel> query = criteriaBuilder.createQuery(SFDADrugReponseModel.class);
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<DrugService> root = query.from(DrugService.class);
        Root<DrugService> countQueryRoot = countQuery.from(DrugService.class);
        query.where(sfdaDrugRequestModel.toPredicate(root, criteriaBuilder));
        countQuery.where(query.getRestriction());
        query.multiselect(root.get("otherCodesValue"), root.get("display"), root.get("scientificCode"),
                root.get("ingredients"), root.get("code"), root.get("price"), root.get("granularUnit"));
        countQuery.select(criteriaBuilder.count(countQueryRoot));
        TypedQuery<SFDADrugReponseModel> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(pageNumber * recordSize);
        typedQuery.setMaxResults(recordSize);
        Long totalCount = entityManager.createQuery(countQuery).getSingleResult();
        List<SFDADrugReponseModel> result = typedQuery.getResultList();
        return PageableExecutionUtils.getPage(result, pageRequest, () -> totalCount);
    }
}
