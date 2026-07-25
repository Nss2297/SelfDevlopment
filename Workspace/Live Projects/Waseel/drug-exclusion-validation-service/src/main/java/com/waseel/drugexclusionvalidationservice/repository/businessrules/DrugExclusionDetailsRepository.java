package com.waseel.drugexclusionvalidationservice.repository.businessrules;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.waseel.drugexclusionvalidationservice.persist.businessrules.DrugExclusionDetails;

import java.util.List;

@Repository
public interface DrugExclusionDetailsRepository extends JpaRepository<DrugExclusionDetails, Long> {

    List<DrugExclusionDetails> findByExclusionIdAndIsDeleted(Long exclusionId, boolean isDeleted);
    
    List<DrugExclusionDetails> findByExclusionIdInAndIsDeleted(List<Long> exclusionIds, boolean isDeleted);

}
