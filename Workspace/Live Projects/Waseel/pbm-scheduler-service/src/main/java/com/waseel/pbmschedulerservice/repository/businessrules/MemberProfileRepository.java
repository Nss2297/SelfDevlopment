package com.waseel.pbmschedulerservice.repository.businessrules;

import com.waseel.pbmschedulerservice.persist.businessrules.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long> {

    Optional<MemberProfile> findByIdNumber(Long idNumber);
}
