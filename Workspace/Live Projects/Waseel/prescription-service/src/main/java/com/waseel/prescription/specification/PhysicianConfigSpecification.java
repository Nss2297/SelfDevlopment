package com.waseel.prescription.specification;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

import com.waseel.prescription.persist.businessrules.DeptSpecPhyscAssc;
import com.waseel.prescription.persist.businessrules.Speciality;
import com.waseel.prescription.persist.mdss.DrugService;
import com.waseel.prescription.persist.prescriptionservice.ServiceRejection;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.waseel.prescription.model.common.PhysicianConfigModel;
import com.waseel.prescription.persist.businessrules.PhysicianInfo;
import com.waseel.prescription.util.UserInfoUtil;

@Component
public class PhysicianConfigSpecification {

	@PersistenceContext(unitName = "businessrules")
	private EntityManager entityManager;

	public List<PhysicianConfigModel> findByPhysicianDetail(String physician) {
		String providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		PhysicianConfigModel configModel = new PhysicianConfigModel(Long.parseLong(providerId), physician);
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PhysicianConfigModel> query = criteriaBuilder.createQuery(PhysicianConfigModel.class);
		Root<PhysicianInfo> root = query.from(PhysicianInfo.class);
		Join<ServiceRejection, DeptSpecPhyscAssc> deptSpecPhyscAsscJoin = root.join("deptSpecPhyscAssc", JoinType.LEFT);
		Join<DeptSpecPhyscAssc, Speciality> specialityJoin = deptSpecPhyscAsscJoin.join("speciality", JoinType.LEFT);
		query.where(configModel.toPredicate(root, query, criteriaBuilder));
		query.multiselect(root.get("registrationNumber"), root.get("name"),
				root.get("category").get("categoryDescription"),
				specialityJoin.get("specialityName"));
		TypedQuery<PhysicianConfigModel> typedQuery = entityManager.createQuery(query);
		return typedQuery.getResultList();
	}
}
