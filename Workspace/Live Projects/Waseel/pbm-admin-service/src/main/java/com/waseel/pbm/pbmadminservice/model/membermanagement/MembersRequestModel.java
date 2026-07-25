package com.waseel.pbm.pbmadminservice.model.membermanagement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotEmpty;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.waseel.pbm.pbmadminservice.persist.prescriptionservice.MemberInfo;
import com.waseel.pbm.pbmadminservice.util.UserInfoUtil;
import com.waseel.pbm.pbmadminservice.validator.customannotation.IsNumber;
import com.waseel.pbm.pbmadminservice.validator.customannotation.IsValidGender;
import com.waseel.pbm.pbmadminservice.validator.customannotation.NoMoreThan10Length;
import com.waseel.pbm.pbmadminservice.validator.customannotation.NoMoreThan200Length;
import com.waseel.pbm.pbmadminservice.validator.customannotation.NoMoreThan20Length;
import com.waseel.pbm.pbmadminservice.validator.customannotation.NoMoreThan56Length;
import com.waseel.pbm.pbmadminservice.validator.customannotation.NoSpecialCharacter;
import com.waseel.pbm.pbmadminservice.validator.customannotation.NotContainsWhiteSpace;

@JsonInclude(Include.NON_NULL)
public class MembersRequestModel implements Serializable, Specification<MemberInfo> {

	private static final long serialVersionUID = 8004645605525398730L;

	@NoMoreThan200Length(message = "Member Name {noMoreThan200LengthValidation}")
	private String name;

	@IsNumber(message = "National ID {onlyAllowDigits}")
	@NotContainsWhiteSpace(message = "National ID {notContainsWhiteSpace}")
	@NoSpecialCharacter(message = "National ID {noSpecialCharacterValidation}")
	@NoMoreThan10Length(message = "National ID {noMoreThan10LengthValidation}")
	private String idNumber;

	@IsValidGender(message = "Member gender {invalidGenderValidation}")
	@NoMoreThan10Length(message = "Member gender {noMoreThan10LengthValidation}")
	private String gender;

	@NoMoreThan56Length(message = "Nationality {noMoreThan56LengthValidation}")
	private String nationality;

	@NotEmpty(message = "Payer ID {notNullOrEmpty}")
	@NoMoreThan20Length(message = "Payer ID {noMoreThan20LengthValidation}")
	private String payerId;

	private Integer pageNumber = 0;

	private Integer recordSize = 10;

	public String getName() {
		return name;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public String getGender() {
		return gender;
	}

	public String getNationality() {
		return nationality;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public Integer getRecordSize() {
		return recordSize;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public void setRecordSize(Integer recordSize) {
		this.recordSize = recordSize;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	@Override
	public Predicate toPredicate(Root<MemberInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();
		managePayerIdPredicate(predicates, criteriaBuilder, root);
		if (StringUtils.isNotBlank(name)) {
			predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("memberName")),
					"%" + name.toLowerCase().strip() + "%"));
		}
		if (StringUtils.isNotBlank(idNumber)) {
			predicates.add(criteriaBuilder.like(root.get("idNumber").as(String.class), "%" + idNumber + "%"));
		}

		if (StringUtils.isNotBlank(gender)) {
			predicates.add(
					criteriaBuilder.equal(criteriaBuilder.lower(root.get("gender")), gender.toLowerCase().strip()));
		}
		if (StringUtils.isNotBlank(nationality)) {
			predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("nationality")),
					"%" + nationality.toLowerCase().strip() + "%"));
		}
		return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	}

	private void managePayerIdPredicate(List<Predicate> predicates, CriteriaBuilder criteriaBuilder,
			Root<MemberInfo> root) {
		Predicate mappedPayerIdPredicate = criteriaBuilder.or(criteriaBuilder.equal(
				criteriaBuilder.lower(root.get("prescriptionRequest").get("payerId")), payerId.toLowerCase().strip()));
		Predicate tokenPayerIdPredicate = criteriaBuilder.equal(
				criteriaBuilder.lower(root.get("prescriptionRequest").get("payerId")),
				UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication()).strip());
		Predicate payerIdPredicate = criteriaBuilder.or(mappedPayerIdPredicate, tokenPayerIdPredicate);
		predicates.add(payerIdPredicate);

	}
}
