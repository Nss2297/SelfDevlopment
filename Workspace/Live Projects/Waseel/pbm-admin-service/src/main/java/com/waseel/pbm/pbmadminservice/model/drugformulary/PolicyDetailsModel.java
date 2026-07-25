package com.waseel.pbm.pbmadminservice.model.drugformulary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugFormularyPolicyAssociation;
import com.waseel.pbm.pbmadminservice.util.UserInfoUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PolicyDetailsModel implements Specification<DrugFormularyPolicyAssociation> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long drugFormularyAssociationId;
	private String policyName;
	private String policyNumber;
	private String policyClassName;
	private Long idNumber;
	private Long formularyId;
	private Integer pageNumber = 0;
	private Integer recordSize = 10;

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getRecordSize() {
		return recordSize;
	}

	public void setRecordSize(Integer recordSize) {
		this.recordSize = recordSize;
	}

	public Long getDrugFormularyAssociationId() {
		return drugFormularyAssociationId;
	}

	public void setDrugFormularyAssociationId(Long drugFormularyAssociationId) {
		this.drugFormularyAssociationId = drugFormularyAssociationId;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getPolicyClassName() {
		return policyClassName;
	}

	public void setPolicyClassName(String policyClassName) {
		this.policyClassName = policyClassName;
	}

	public Long getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(Long idNumber) {
		this.idNumber = idNumber;
	}

	public Long getFormularyId() {
		return formularyId;
	}

	public void setFormularyId(Long formularyId) {
		this.formularyId = formularyId;
	}

	public PolicyDetailsModel() {
	}

	public PolicyDetailsModel(String policyName, String policyNumber, String policyClassName, Long idNumber,
			Long formularyId) {
		this.policyName = policyName;
		this.policyNumber = policyNumber;
		this.policyClassName = policyClassName;
		this.idNumber = idNumber;
		this.formularyId = formularyId;
	}

	public PolicyDetailsModel(Long drugFormularyAssociationId, String policyName, String policyNumber,
			String policyClassName, Long idNumber) {
		this.drugFormularyAssociationId = drugFormularyAssociationId;
		this.policyName = policyName;
		this.policyNumber = policyNumber;
		this.policyClassName = policyClassName;
		this.idNumber = idNumber;
	}

	@Override
	public Predicate toPredicate(Root<DrugFormularyPolicyAssociation> root, CriteriaQuery<?> query,
			CriteriaBuilder criteriaBuilder) {
		String strMemberPolicyAssociation = "memberPolicyAssociation";
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get("drugFormularyMetadata").get("payerId"),
				UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication())));
		predicates.add(criteriaBuilder.equal(root.get("formularyId"), formularyId));
		predicates.add(criteriaBuilder.equal(root.get("drugFormularyMetadata").get("isDeleted"), false));
		predicates.add(criteriaBuilder.equal(root.get("isEnabled"), true));
		if (!StringUtils.isBlank(policyName)) {
			predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("policyInformation").get("policyHolderName")),
							"%" + policyName.toLowerCase().trim() + "%"));
		}
		if (!StringUtils.isBlank(policyNumber)) {
			predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("policyInformation").get("policyNumber")),
							"%" + policyNumber.toLowerCase().trim() + "%"));
		}
		if (!StringUtils.isBlank(policyClassName)) {
			predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("policyClasses").get("classCode")),
					"%" + policyClassName.toLowerCase().trim() + "%"));
		}
		if (idNumber != null) {
			predicates.add(criteriaBuilder
					.equal(root.get(strMemberPolicyAssociation).get("memberProfile").get("idNumber"), idNumber));

		}
		List<Predicate> predicates1 = new ArrayList<>();
		predicates1.add(criteriaBuilder.equal(root.get(strMemberPolicyAssociation).get("isCancelled"), false));
		predicates1.add(criteriaBuilder.isNull(root.get(strMemberPolicyAssociation).get("isCancelled")));

		Predicate p = criteriaBuilder.or(predicates1.get(0), predicates1.get(1));
		Predicate p1 = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		return criteriaBuilder.and(p, p1);
	}
}
