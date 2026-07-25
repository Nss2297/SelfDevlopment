package com.waseel.prescription.repository.prescriptionservice;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.prescription.persist.prescriptionservice.MemberInfo;

@Repository
public interface MemberInfoRepository extends CrudRepository<MemberInfo, Long> {

	Optional<MemberInfo> findByRequestId(String requestId);

	@EntityGraph("SummaryInquiry")
	public Optional<List<MemberInfo>> findSummaryInquiryByMemberIdAndPolicyNumberAndPrescriptionRequest_ProviderIdAndPrescriptionRequestPayerId_AndPrescriptionRequest_SendDateTimeGreaterThanEqualAndPrescriptionRequest_SendDateTimeLessThanEqual(
			String memberId, String policyNumber, String providerId, String payerId, Timestamp startDate,
			Timestamp endDate);

	@EntityGraph("SummaryInquiry")
	public Optional<List<MemberInfo>> findSummaryInquiryByIdNumberAndPrescriptionRequest_ProviderIdAndPrescriptionRequestPayerId_AndPrescriptionRequest_SendDateTimeGreaterThanEqualAndPrescriptionRequest_SendDateTimeLessThanEqual(
			Long idNumber, String providerId, String payerId, Timestamp startDate, Timestamp endDate);

}
