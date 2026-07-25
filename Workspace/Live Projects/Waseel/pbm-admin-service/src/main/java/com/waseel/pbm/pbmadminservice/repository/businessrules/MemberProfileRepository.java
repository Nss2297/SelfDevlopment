package com.waseel.pbm.pbmadminservice.repository.businessrules;

import com.waseel.pbm.pbmadminservice.persist.businessrules.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long> {

    Optional<MemberProfile> findByIdNumber(Long idNumber);

    @Query(value = "SELECT MAX(\"MEMBER_PROFILE_ID\") from \"MEMBER_PROFILE\"", nativeQuery = true)
    Long findLatestId();
}
