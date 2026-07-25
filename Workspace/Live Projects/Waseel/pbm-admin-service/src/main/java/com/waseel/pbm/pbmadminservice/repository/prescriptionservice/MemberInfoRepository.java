package com.waseel.pbm.pbmadminservice.repository.prescriptionservice;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.pbmadminservice.persist.prescriptionservice.MemberInfo;

@Repository
public interface MemberInfoRepository extends CrudRepository<MemberInfo, Long> {

	Optional<MemberInfo> findByRequestId(String requestId);


}
