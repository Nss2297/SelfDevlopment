package com.waseel.pbm.pbmadminservice.specification;

import com.waseel.pbm.pbmadminservice.model.SpecialityModel;
import com.waseel.pbm.pbmadminservice.persist.businessrules.Speciality;
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
public class SpecialitySpecification {

    @PersistenceContext(unitName = "businessrules")
    private EntityManager entityManager;

    public Page<SpecialityModel> findSpecialitiesWithPagination(int pageNumber, int recordSize,
                                                                SpecialityModel specialityModel) {
        PageRequest pageRequest = PageRequest.of(pageNumber, recordSize);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SpecialityModel> query = criteriaBuilder.createQuery(SpecialityModel.class);
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Speciality> root = query.from(Speciality.class);
        Root<Speciality> countQueryRoot = countQuery.from(Speciality.class);
        query.orderBy(criteriaBuilder.desc(root.get("lastUpdateDate")));
        query.where(specialityModel.toPredicate(root, query, criteriaBuilder));
        countQuery.where(query.getRestriction());
        query.multiselect(root.get("specialityId"), root.get("specialityName"));
        countQuery.select(criteriaBuilder.count(countQueryRoot));
        TypedQuery<SpecialityModel> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(pageNumber * recordSize);
        typedQuery.setMaxResults(recordSize);
        Long totalCount = entityManager.createQuery(countQuery).getSingleResult();
        List<SpecialityModel> result = typedQuery.getResultList();
        return PageableExecutionUtils.getPage(result, pageRequest, () -> totalCount);
    }
}
