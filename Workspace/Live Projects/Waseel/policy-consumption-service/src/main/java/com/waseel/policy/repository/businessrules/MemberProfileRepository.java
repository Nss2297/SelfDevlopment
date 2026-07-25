package com.waseel.policy.repository.businessrules;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.waseel.policy.persist.businessrules.MemberProfile;

public interface MemberProfileRepository extends CrudRepository<MemberProfile, Long> {

	Optional<MemberProfile> findByIdNumber(BigDecimal idNumber);

}
