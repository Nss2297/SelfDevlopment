package com.waseel.prescription.specification;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.waseel.prescription.model.common.ProviderInformationModel;
import com.waseel.prescription.persist.hira.AccountToAccountAssociation;
import com.waseel.prescription.persist.hira.SwitchAccount;
import com.waseel.prescription.util.UserInfoUtil;

@Component
public class ProviderInfoSpecification {
	
	private final Logger log = LoggerFactory.getLogger(ProviderInfoSpecification.class);


	@PersistenceContext(unitName = "hira")
	private EntityManager entityManager;

	public List<ProviderInformationModel> findByCodeOrSourceOrProviderName(String value) {
		String payerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		log.info("PayerId(accId) is {} to search provider information",payerId);
		ProviderInformationModel providerInformationModel = new ProviderInformationModel(value,
				new BigDecimal(payerId));
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProviderInformationModel> query = criteriaBuilder.createQuery(ProviderInformationModel.class);
		Root<AccountToAccountAssociation> root = query.from(AccountToAccountAssociation.class);
        Join<AccountToAccountAssociation, SwitchAccount> switchAccJoin = root.join("switchAccount", JoinType.INNER);
		query.where(providerInformationModel.toPredicate(root, query, criteriaBuilder));
		query.multiselect(root.get("id").get("source"), switchAccJoin.get("name"),root.get("code"));
		TypedQuery<ProviderInformationModel> typedQuery = entityManager.createQuery(query);
		return typedQuery.getResultList();
	}
}
