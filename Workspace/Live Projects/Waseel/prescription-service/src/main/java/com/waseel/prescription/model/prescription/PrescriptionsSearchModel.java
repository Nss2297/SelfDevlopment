package com.waseel.prescription.model.prescription;

import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.util.UserInfoUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionsSearchModel implements Specification<PrescriptionRequest> {

    private static final long serialVersionUID = 1L;
    private Integer pageNumber = 0;
    private Integer recordSize = 10;
    private String referenceNo;
    private String memberId;
    private String idNumber;
    private String memberName;
    private String policyNumber;
    private String status;
    private String fromDate;
    private String endDate;
    private Boolean activePrescription = false;
    private Boolean pharmacyUser = false;
    private String providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());

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

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public Boolean getActivePrescription() {
        return activePrescription;
    }

    public void setActivePrescription(Boolean activePrescription) {
        this.activePrescription = activePrescription;
    }

	public Boolean getPharmacyUser() {
		return pharmacyUser;
	}

	public void setPharmacyUser(Boolean pharmacyUser) {
		this.pharmacyUser = pharmacyUser;
	}

	private static final String STATUS_CODE_STRING = "statusCode";
	private static final String MEMBER_INFO_STRING = "memberInfo";

	@Override
    public Predicate toPredicate(Root<PrescriptionRequest> root, CriteriaQuery<?> query,
                                 CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (!StringUtils.isBlank(referenceNo)) {
            predicates.add(criteriaBuilder.equal(root.get("ePrescriptionReferenceNumber"), referenceNo.trim()));
        }
        if (!StringUtils.isBlank(memberId)) {
            predicates.add(criteriaBuilder.equal(root.get(MEMBER_INFO_STRING).get("memberId"), memberId.trim()));
        }
        if (!StringUtils.isBlank(idNumber)) {
            predicates.add(criteriaBuilder.equal(root.get(MEMBER_INFO_STRING).get("idNumber"), idNumber.trim()));
        }
        if (!StringUtils.isBlank(memberName)) {
            Predicate memberNamePredicate = criteriaBuilder.like(root.get(MEMBER_INFO_STRING).get("memberName"),
                    "%" + memberName + "%");
            Predicate memberIdPredicate = criteriaBuilder.like(root.get(MEMBER_INFO_STRING).get("memberId"),
                    "%" + memberName + "%");
            predicates.add(criteriaBuilder.or(memberNamePredicate, memberIdPredicate));
        }
        if (!StringUtils.isBlank(policyNumber)) {
            predicates.add(criteriaBuilder.equal(root.get(MEMBER_INFO_STRING).get("policyNumber"), policyNumber.trim()));
        }
        if (!StringUtils.isBlank(status)) {
            predicates.add(criteriaBuilder.equal(root.get(STATUS_CODE_STRING), status));
        }
        if (!StringUtils.isBlank(providerId)) {
            predicates.add(criteriaBuilder.equal(root.get("providerId"), providerId));
        }

        if (!StringUtils.isBlank(fromDate)) {
            LocalDate date = null;
            try {
                date = LocalDate.parse(fromDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            } catch (DateTimeParseException e) {
                e.printStackTrace();
            }
            if (date != null) {
                predicates
                        .add(criteriaBuilder.greaterThanOrEqualTo(root.get("sendDateTime").as(LocalDate.class), date));
            }
        }

        if (!StringUtils.isBlank(endDate)) {
            LocalDate date = null;
            try {
                date = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            } catch (DateTimeParseException e) {
                e.printStackTrace();
            }
            if (date != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("sendDateTime").as(LocalDate.class),
                        date.plusDays(1)));
            }
        }
		Predicate approved = criteriaBuilder.equal(root.get(STATUS_CODE_STRING), "APPROVED");
		Predicate partialApproved = criteriaBuilder.equal(root.get(STATUS_CODE_STRING), "PARTIAL_APPROVED");
		Predicate partialDispensed = criteriaBuilder.equal(root.get(STATUS_CODE_STRING), "PARTIAL_DISPENSED");
        if (activePrescription.equals(true)) {
			Predicate predicateForActivity = criteriaBuilder.or(approved, partialApproved, partialDispensed);
            predicates.add(predicateForActivity);
        }
        if (pharmacyUser.equals(true)) {
			Predicate dispensed = criteriaBuilder.equal(root.get(STATUS_CODE_STRING), "DISPENSED");
			Predicate predicateForPharmacy = criteriaBuilder.or(approved, partialApproved, partialDispensed, dispensed);
			predicates.add(predicateForPharmacy);
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
