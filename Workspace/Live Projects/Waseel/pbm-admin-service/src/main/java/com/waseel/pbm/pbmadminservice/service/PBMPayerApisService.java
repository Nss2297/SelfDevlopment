package com.waseel.pbm.pbmadminservice.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.waseel.pbm.pbmadminservice.client.DSSAdminSerivceClient;
import com.waseel.pbm.pbmadminservice.client.PBMPayerApisServiceClient;
import com.waseel.pbm.pbmadminservice.enums.PBMPayerApisMessage;
import com.waseel.pbm.pbmadminservice.model.MemberDetail;
import com.waseel.pbm.pbmadminservice.model.MemberDetailsResponseModel;
import com.waseel.pbm.pbmadminservice.model.mdss.MemberChronicDiseaseResponseModel;
import com.waseel.pbm.pbmadminservice.model.payer.PolicyClass;
import com.waseel.pbm.pbmadminservice.model.payer.PolicyMetadata;
import com.waseel.pbm.pbmadminservice.model.payer.PolicyMetadataResponseModel;

@Service
public class PBMPayerApisService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PBMPayerApisService.class);

	@Autowired
	private PBMPayerApisServiceClient pbmPayerApisServiceClient;
	@Autowired
	private DSSAdminSerivceClient dssAdminSerivceClient;

	public ResponseEntity<MemberDetailsResponseModel> getMemberDetails(Long idNumber) {
		LOGGER.info("Send IdNumber: {} to PbmPayerApis Service", idNumber);
		MemberDetailsResponseModel memberDetailResponseModel = new MemberDetailsResponseModel();
		ResponseEntity<MemberDetail> memberDetailResponseEntity = pbmPayerApisServiceClient
				.getMemberDemographicData(idNumber);
		// Call dss-admin-serivce
		ResponseEntity<List<MemberChronicDiseaseResponseModel>> memberChronicDiseaseResponseModel = dssAdminSerivceClient
				.getChronicData(idNumber.toString());
		if (null != memberDetailResponseEntity && null != memberDetailResponseEntity.getBody()
				&& memberDetailResponseEntity.getStatusCodeValue() == HttpStatus.OK.value()) {
			MemberDetail memberDetail = memberDetailResponseEntity.getBody();
			if (null != memberDetail && null != memberDetail.getPolicyInformation()
					&& !memberDetail.getPolicyInformation().isEmpty()) {
				List<PolicyMetadata> memberPolicyDetails = new ArrayList<>();
				List<String> errors = new ArrayList<>();
				memberDetail.getPolicyInformation().stream().forEach(policyInformation -> {
					String policyNumber = policyInformation.getPolicyNumber();
					ResponseEntity<PolicyMetadataResponseModel> policyMetadataResponseEntity = getPolicyDetails(
							policyNumber);
					if (null != policyMetadataResponseEntity && null != policyMetadataResponseEntity.getBody()
							&& policyMetadataResponseEntity.getStatusCodeValue() == HttpStatus.OK.value()) {
						PolicyMetadata policyMetadata = policyMetadataResponseEntity.getBody().getPolicyMetadata();
						List<PolicyClass> memberPolicyClasses = new ArrayList<>();
						policyMetadata.getPolicyClasses().stream().filter(
								policyClass -> policyClass.getClassCode().equals(policyInformation.getClassCode()))
								.forEach(memberPolicyClasses::add);
						policyMetadata.setPolicyClasses(memberPolicyClasses);
						policyMetadata.setMemberId(policyInformation.getMemberId());
						memberPolicyDetails.add(policyMetadata);
					} else {
						errors.add(PBMPayerApisMessage.ERROR_MESSAGE.value()
								.replace(PBMPayerApisMessage.ID_NUMBER.value(), idNumber.toString())
								.replace(PBMPayerApisMessage.POLICY_NUMBER.value(), policyNumber));
					}
				});
				populateMemberDetailResponseModel(memberPolicyDetails, memberDetail, errors, memberDetailResponseModel);

				if (null != memberChronicDiseaseResponseModel && null != memberChronicDiseaseResponseModel.getBody()
						&& memberChronicDiseaseResponseModel.getStatusCodeValue() == HttpStatus.OK.value()) {
					memberDetailResponseModel
							.setMemberChronicDiseaseResponseModel(memberChronicDiseaseResponseModel.getBody());
				}
			}
		}
		return ResponseEntity.ok(memberDetailResponseModel);
	}

	public ResponseEntity<PolicyMetadataResponseModel> getPolicyDetails(String policyNumber) {
		LOGGER.info("Send Policy Number: {} to PbmPayerApis Service", policyNumber);
		ResponseEntity<PolicyMetadataResponseModel> policyMetadataResponseEntity = pbmPayerApisServiceClient
				.getPolicyData(policyNumber);
		if (null != policyMetadataResponseEntity && null != policyMetadataResponseEntity.getBody()
				&& policyMetadataResponseEntity.getStatusCodeValue() == HttpStatus.OK.value()) {
			return ResponseEntity.ok(policyMetadataResponseEntity.getBody());
		}
		return policyMetadataResponseEntity;
	}

	private void populateMemberDetailResponseModel(List<PolicyMetadata> memberPolicyDetails, MemberDetail memberDetail,
			List<String> errors, MemberDetailsResponseModel memberDetailResponseModel) {
		memberDetailResponseModel.setDateOfBirth(memberDetail.getDateOfBirth());
		memberDetailResponseModel.setEmail(memberDetail.getEmail());
		if (null != errors && !errors.isEmpty()) {
			memberDetailResponseModel.setErrors(errors);
		}
		memberDetailResponseModel.setGender(memberDetail.getGender());
		memberDetailResponseModel.setIdNumber(memberDetail.getIdNumber());
		memberDetailResponseModel.setMaritalStatus(memberDetail.getMaritalStatus());
		memberDetailResponseModel.setMemberName(memberDetail.getMemberName());
		if (null != memberPolicyDetails && !memberPolicyDetails.isEmpty()) {
			memberDetailResponseModel.setMemberPolicyDetails(memberPolicyDetails);
		}
		memberDetailResponseModel.setMobileNumber(memberDetail.getMobileNumber());
		memberDetailResponseModel.setNationality(memberDetail.getNationality());
	}
}
