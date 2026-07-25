package com.waseel.pbm.pbmadminservice.specification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.waseel.pbm.pbmadminservice.model.ProviderInformationModel;
import com.waseel.pbm.pbmadminservice.persist.hira.AccountToAccountAssociation;
import com.waseel.pbm.pbmadminservice.persist.hira.SwitchAccount;
import com.waseel.pbm.pbmadminservice.util.UserInfoUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.List;

@Component
public class ProviderInfoSpecification {
    private final Logger log = LoggerFactory.getLogger(ProviderInfoSpecification.class);

    @PersistenceContext(unitName = "hira")
    private EntityManager entityManager;

    public Page<ProviderInformationModel> findByCodeOrSourceOrProviderNameWithPagination(int pageNumber, int recordSize, String value) {
        String payerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
        log.info("PayerId(accId) is {} to search provider information", payerId);

        ProviderInformationModel providerInformationModel = new ProviderInformationModel(value, new BigDecimal(payerId));
        PageRequest pageRequest = PageRequest.of(pageNumber, recordSize);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProviderInformationModel> query = criteriaBuilder.createQuery(ProviderInformationModel.class);
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<AccountToAccountAssociation> root = query.from(AccountToAccountAssociation.class);
        Root<AccountToAccountAssociation> countQueryRoot = countQuery.from(AccountToAccountAssociation.class);
        Join<AccountToAccountAssociation, SwitchAccount> switchAccJoin = root.join("switchAccount", JoinType.INNER);

        query.where(providerInformationModel.toPredicate(root, query, criteriaBuilder));
        countQuery.where(query.getRestriction());
        query.multiselect(root.get("id").get("source"), switchAccJoin.get("name"), root.get("code"));
        countQuery.select(criteriaBuilder.count(countQueryRoot));

        TypedQuery<ProviderInformationModel> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(pageNumber * recordSize);
        typedQuery.setMaxResults(recordSize);

        Long totalCount = entityManager.createQuery(countQuery).getSingleResult();
        List<ProviderInformationModel> result = typedQuery.getResultList();

        return PageableExecutionUtils.getPage(result, pageRequest, () -> totalCount);
    }
}
