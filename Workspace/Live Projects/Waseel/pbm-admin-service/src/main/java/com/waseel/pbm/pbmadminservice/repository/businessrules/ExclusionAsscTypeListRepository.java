package com.waseel.pbm.pbmadminservice.repository.businessrules;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.waseel.pbm.pbmadminservice.persist.businessrules.ExclusionAsscTypeList;

public interface ExclusionAsscTypeListRepository extends JpaRepository<ExclusionAsscTypeList, Long> {

	void deleteByExclusionIdAndExclusionAsscIdAndExclusionTypeAndPayerId(Long exclusionId, Long exclusionAssId,
			String exclusionType, Long payerId);

	Optional<ExclusionAsscTypeList> findByExclusionIdAndExclusionAsscIdAndExclusionTypeAndPayerId(Long exclusionId,
			Long exclusionAsscId, String value, Long payerId);

	Optional<List<ExclusionAsscTypeList>> findByExclusionId(Long exclusionId);

	@Modifying
	@Query("DELETE FROM ExclusionAsscTypeList n WHERE n.exclusionId = :exclusionId")
	void deleteAllByExclusionId(Long exclusionId);
}
