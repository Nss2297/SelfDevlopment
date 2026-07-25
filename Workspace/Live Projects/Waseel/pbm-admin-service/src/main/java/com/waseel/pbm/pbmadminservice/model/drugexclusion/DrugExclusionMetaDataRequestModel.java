package com.waseel.pbm.pbmadminservice.model.drugexclusion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugExclusionMetadata;

public class DrugExclusionMetaDataRequestModel implements Specification<DrugExclusionMetadata> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long exclusionId;
    private String name;
    private String createdDateFrom;
    private String createdDateTo;
    private String updatedDateFrom;
    private String updatedDateTo;

    public Long getExclusionId() {
        return exclusionId;
    }

    public void setExclusionId(Long exclusionId) {
        this.exclusionId = exclusionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedDateFrom() {
        return createdDateFrom;
    }

    public void setCreatedDateFrom(String createdDateFrom) {
        this.createdDateFrom = createdDateFrom;
    }

    public String getCreatedDateTo() {
        return createdDateTo;
    }

    public void setCreatedDateTo(String createdDateTo) {
        this.createdDateTo = createdDateTo;
    }

    public String getUpdatedDateFrom() {
        return updatedDateFrom;
    }

    public void setUpdatedDateFrom(String updatedDateFrom) {
        this.updatedDateFrom = updatedDateFrom;
    }

    public String getUpdatedDateTo() {
        return updatedDateTo;
    }

    public void setUpdatedDateTo(String updatedDateTo) {
        this.updatedDateTo = updatedDateTo;
    }

    public DrugExclusionMetaDataRequestModel(Long id, String name, String createdDateFrom, String createdDateTo,
                                             String updatedDateFrom, String updatedDateTo) {
        this.exclusionId = id;
        this.name = name;
        this.createdDateFrom = createdDateFrom;
        this.createdDateTo = createdDateTo;
        this.updatedDateFrom = updatedDateFrom;
        this.updatedDateTo = updatedDateTo;
    }

    @Override
    public Predicate toPredicate(Root<DrugExclusionMetadata> root, CriteriaQuery<?> criteriaQuery,
                                 CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (exclusionId != null && exclusionId > 0) {
            predicates.add(criteriaBuilder.equal(root.get("exclusionId"), exclusionId));
        }
        if (!StringUtils.isBlank(name)) {
            predicates.add(criteriaBuilder.like(root.get("exclusionName"), "%" + name + "%"));
        }
		if (!StringUtils.isBlank(createdDateFrom)) {
			LocalDate date = getLocalDate(createdDateFrom);
			if (date != null) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate").as(LocalDate.class), date));
			}
		}
		if (!StringUtils.isBlank(createdDateTo)) {
			LocalDate date = getLocalDate(createdDateTo);
			if (date != null) {
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdDate").as(LocalDate.class),
						date.plusDays(1)));
			}
		}
		if (!StringUtils.isBlank(updatedDateFrom)) {
			LocalDate date = getLocalDate(updatedDateFrom);
			if (date != null) {
				predicates.add(
						criteriaBuilder.greaterThanOrEqualTo(root.get("lastUpdateDate").as(LocalDate.class), date));
			}
		}
		if (!StringUtils.isBlank(updatedDateTo)) {
			LocalDate date = getLocalDate(updatedDateTo);
			if (date != null) {
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("lastUpdateDate").as(LocalDate.class),
						date.plusDays(1)));
			}
		}
        predicates.add(criteriaBuilder.equal(root.get("isDeleted"), 0));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
    
    private LocalDate getLocalDate(String date) {
		try {
			return LocalDate.parse(date.trim(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		} catch (DateTimeParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
