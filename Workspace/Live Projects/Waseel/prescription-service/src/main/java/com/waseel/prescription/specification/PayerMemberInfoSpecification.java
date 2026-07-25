package com.waseel.prescription.specification;

import com.waseel.prescription.model.common.PayerMemberInfoModel;
import com.waseel.prescription.persist.businessrules.PayerMemberInfo;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class PayerMemberInfoSpecification {

    @PersistenceContext(unitName = "businessrules")
    private EntityManager entityManager;

    public List<PayerMemberInfoModel> findByNationalIdAndPayerId(PayerMemberInfoModel payerMemberInfoModel) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PayerMemberInfoModel> query =
                criteriaBuilder.createQuery(PayerMemberInfoModel.class);
        Root<PayerMemberInfo> root = query.from(PayerMemberInfo.class);
        query.where(payerMemberInfoModel.toPredicate(root, query, criteriaBuilder));
        query.multiselect(root.get("memberName"), root.get("dob"), root.get("gender")
                , root.get("nationality"), root.get("idNumber"));
        TypedQuery<PayerMemberInfoModel> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
}
