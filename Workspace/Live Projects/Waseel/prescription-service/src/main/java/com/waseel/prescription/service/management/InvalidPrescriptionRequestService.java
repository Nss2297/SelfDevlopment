package com.waseel.prescription.service.management;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.prescription.model.dispense.PrescriptionDispenseResponseModel;
import com.waseel.prescription.persist.prescriptionservice.InvalidPrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.MemberInfo;
import com.waseel.prescription.repository.prescriptionservice.InvalidPrescriptionRequestRepository;
import com.waseel.prescription.repository.prescriptionservice.MemberInfoRepository;

@Service
public class InvalidPrescriptionRequestService {

	@Autowired
	private InvalidPrescriptionRequestRepository invalidPrescriptionRequestRepository;

	@Autowired
	private MemberInfoRepository memberInfoRepository;

	boolean validateIdNumber(String idNumber) {
		boolean has10digits = StringUtils.isBlank(idNumber) || idNumber.trim().getBytes().length == 10;
		boolean hasNoWhiteSpace = !Pattern.compile("\\s").matcher(idNumber).find();
		boolean hasNoSpecialCharacter = !Pattern.compile("[~`!@#$%^&/*()=+{}|_:;',<.>?\\-\\[\\]\\\"\\\\]")
				.matcher(idNumber).find();
		boolean isNumber = !Pattern.compile("[^0-9.]").matcher(idNumber).find();
		return has10digits && hasNoWhiteSpace && hasNoSpecialCharacter && isNumber;
	}

	public void addInvalidDataForDispense(PrescriptionDispenseResponseModel invalidResponse, String requestId,
			Timestamp sendingTime) {
		MemberInfo memberInfo = getMemberInfo(requestId);
		InvalidPrescriptionRequest invalidPrescriptionRequest = new InvalidPrescriptionRequest(requestId,
				invalidResponse.getePrescriptionReferenceNumber(), sendingTime,
				new Timestamp(Calendar.getInstance().getTimeInMillis()), invalidResponse.getStatus(),
				invalidResponse.getStatusDescription(), memberInfo != null ? memberInfo.getMemberId() : null,
				memberInfo != null ? memberInfo.getIdNumber() : 0,
				memberInfo != null ? memberInfo.getPolicyNumber() : null, invalidResponse.getPayerId(),
				invalidResponse.getProviderId());
		invalidPrescriptionRequestRepository.save(invalidPrescriptionRequest);
	}
	
	public MemberInfo getMemberInfo(String requestId) {
		if (!StringUtils.isBlank(requestId)) {
			Optional<MemberInfo> memberInfo = memberInfoRepository.findByRequestId(requestId);
			if (memberInfo.isPresent()) {
				return memberInfo.get();
			}
		}
		return null;
	}

}