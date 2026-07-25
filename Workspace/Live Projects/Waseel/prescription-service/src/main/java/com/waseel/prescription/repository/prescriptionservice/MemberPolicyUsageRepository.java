package com.waseel.prescription.repository.prescriptionservice;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.prescription.persist.prescriptionservice.MemberPolicyUsage;

@Repository
public interface MemberPolicyUsageRepository extends CrudRepository<MemberPolicyUsage, Long> {

}