package com.waseel.pbmschedulerservice.service.memberdetails;

import com.waseel.pbmschedulerservice.clients.MemberDetailsServiceClient;
import com.waseel.pbmschedulerservice.model.enums.AuditUpdatedType;
import com.waseel.pbmschedulerservice.model.enums.EntitiesName;
import com.waseel.pbmschedulerservice.model.memberdetails.*;
import com.waseel.pbmschedulerservice.persist.businessrules.MemberPolicyAssociation;
import com.waseel.pbmschedulerservice.persist.businessrules.MemberProfile;
import com.waseel.pbmschedulerservice.persist.businessrules.PolicyClasses;
import com.waseel.pbmschedulerservice.persist.businessrules.PolicyInformation;
import com.waseel.pbmschedulerservice.repository.businessrules.MemberPolicyAssociationRepository;
import com.waseel.pbmschedulerservice.repository.businessrules.MemberProfileRepository;
import com.waseel.pbmschedulerservice.repository.businessrules.PolicyClassesRepository;
import com.waseel.pbmschedulerservice.repository.businessrules.PolicyInformationRepository;
import com.waseel.pbmschedulerservice.service.AuditLogService;
import com.waseel.pbmschedulerservice.service.TransactionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MemberDetailsService {

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    @Value("${receiver.code}")
    private String payerId;

    @Autowired
    private MemberDetailsServiceClient memberDetailsServiceClient;
    @Autowired
    private MemberProfileRepository memberProfileRepository;
    @Autowired
    private MemberPolicyAssociationRepository memberPolicyAssociationRepository;
    @Autowired
    private PolicyInformationRepository policyInformationRepository;
    @Autowired
    private PolicyClassesRepository policyClassesRepository;
    @Autowired
    private AuditLogService auditLogService;
    @Autowired
    private TransactionLogService transactionLogService;
    @Autowired
    private MemberDetailsDMLService memberDetailsDMLService;

    private void getMemberDetailsByPolicyNumber(PolicyInformation policyInformation) {
        int pageSize = 100;
        int pageNumber = 0;
        boolean isLastPage = true;
        do {
            ResponseEntity<MembersResponseModel> responseEntity = memberDetailsDMLService.
                    getMemberDetailsByPolicyNumber(policyInformation, pageSize, pageNumber++);
            if (responseEntity.getStatusCodeValue() == HttpStatus.OK.value()) {
                MembersResponseModel responseModel = responseEntity.getBody();
                addOrUpdateMemberDetails(responseModel, policyInformation.getPolicyInformationId());
                isLastPage = responseModel.isLastPage();
            }
        } while (!isLastPage);
    }

    private void getMemberDetailsByLastUpdateDate() {
        int pageSize = 100;
        int pageNumber = 0;
        boolean isLastPage = true;
        String lastUpdateDate = "2023-05-01";
        do {
            ResponseEntity<MemberDetailsResponseModel> responseEntity = memberDetailsDMLService.
                    getMemberDetailsByLastUpdateDate(pageSize, pageNumber++, lastUpdateDate);
            if (responseEntity.getStatusCodeValue() == HttpStatus.OK.value()) {
                MemberDetailsResponseModel responseModel = responseEntity.getBody();
                List<MembersResponseModel> memberDetails = responseModel.getMemberDetails();
                memberDetails.forEach(memberDetail -> addOrUpdateMemberDetails(memberDetail,
                        getPolicyInfo(memberDetail.getPolicyNumber()).getPolicyInformationId()));
                isLastPage = responseModel.isLastPage();
            }
        } while (!isLastPage);
    }

    private void getMemberDetailsByIDNumber() {
        Long idNumber = 1L;
        ResponseEntity<MemberDetailsModel> responseEntity = memberDetailsDMLService.getMemberDetailsByIDNumber(idNumber);
        if (responseEntity.getStatusCodeValue() == HttpStatus.OK.value()) {
            saveMemberDetailsByIDNumber(responseEntity.getBody());
        }
    }

    @Transactional
    private void saveMemberDetailsByIDNumber(MemberDetailsModel responseModel) {
        Long memberProfileId = saveMemberProfileDetails(responseModel);
        List<MemberPolicyDetailsModel> policyDetailsList = responseModel.getPolicyDetails();
        policyDetailsList.forEach(policyDetail ->
                saveMemberPolicyAssociationDetails(policyDetail.getMemberId(), policyDetail.getMemberSince(),
                        memberProfileId, getPolicyInfo(policyDetail.getPolicyNumber()).getPolicyInformationId(),
                        policyDetail.getExpiryDate(), responseModel, policyDetail.getPolicyClass()));
    }

    private PolicyInformation getPolicyInfo(String policyNumber) {
        Optional<PolicyInformation> optionalPolicyInformation =
                policyInformationRepository.findByPayerIdAndPolicyNumber(payerId, policyNumber);
        return optionalPolicyInformation.isPresent() ? optionalPolicyInformation.get() : new PolicyInformation();
    }

    @Transactional
    private void addOrUpdateMemberDetails(MembersResponseModel responseModel,
                                          Long policyInfoId) {
        List<MemberPolicyAssociationModel> members = responseModel.getMembers();
        String expiryDate = responseModel.getExpiryDate();
        members.forEach(member -> {
            String memberId = member.getMemberId();
            String memberSince = member.getMemberSince();
            String policyClass = member.getPolicyClass();
            List<MemberDetailsModel> memberDetails = member.getBeneficiaries();
            memberDetails.forEach(memberDetail -> {
                Long memberProfileId = saveMemberProfileDetails(memberDetail);
                saveMemberPolicyAssociationDetails(memberId, memberSince, memberProfileId,
                        policyInfoId, expiryDate, memberDetail, policyClass);
            });
        });
    }

    private void saveMemberPolicyAssociationDetails(String memberId, String memberSince, Long memberProfileId,
                                                    Long policyInfoId, String expiryDate,
                                                    MemberDetailsModel memberDetail, String policyClass) {
        Optional<MemberPolicyAssociation> optionalMemberPolicyAssociation =
                memberPolicyAssociationRepository.findByMemberIdAndMemberProfileIdAndPolicyInformationId(
                        memberId, memberProfileId, policyInfoId);
        MemberPolicyAssociation memberPolicyAssociation = optionalMemberPolicyAssociation.isPresent()
                ? optionalMemberPolicyAssociation.get() : new MemberPolicyAssociation();
        memberPolicyAssociation.setMemberId(memberId);
        memberPolicyAssociation.setMemberType(memberDetail.getMemberType());
        memberPolicyAssociation.setMemberProfileId(memberProfileId);
        memberPolicyAssociation.setPolicyInformationId(policyInfoId);
        memberPolicyAssociation.setPolicyClassId(getPolicyClassId(policyInfoId, policyClass));
        try {
            if (memberSince != null) {
                memberPolicyAssociation.setMemberSince((sdf.parse(memberSince)));
            }
            Date expiry = sdf.parse(expiryDate);
            Date currentDate = new Date();
            if (expiry.compareTo(currentDate) < 0)
                memberPolicyAssociation.setCancelled(true);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        MemberPolicyAssociation association = memberPolicyAssociationRepository.save(memberPolicyAssociation);
        auditLogService.addDataInAuditLog(
                optionalMemberPolicyAssociation.isPresent() ? AuditUpdatedType.UPDATE : AuditUpdatedType.INSERT,
                association.getMemberPolicyAssociationId(), EntitiesName.MEMBER_POLICY_ASSOCIATION, association);
    }

    private Long getPolicyClassId(Long policyInfoId, String policyClass) {
        Optional<PolicyClasses> optionalPolicyClasses =
                policyClassesRepository.findByPolicyInformationIdAndClassCode(policyInfoId, policyClass);
        if (optionalPolicyClasses.isPresent())
            return optionalPolicyClasses.get().getPolicyClassId();
        return 0L;
    }

    private Long saveMemberProfileDetails(MemberDetailsModel memberDetail) {
        Long idNumber = memberDetail.getIdNumber();
        Optional<MemberProfile> optionalMemberProfile = memberProfileRepository.findByIdNumber(idNumber);
        MemberProfile memberProfile = optionalMemberProfile.isPresent() ?
                optionalMemberProfile.get() : new MemberProfile();
        memberProfile.setIdNumber(idNumber);
        memberProfile.setMemberName(memberDetail.getMemberName());
        try {
            memberProfile.setDob(sdf.parse(memberDetail.getDateOfBirth()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        memberProfile.setEmail(memberDetail.getEmail());
        memberProfile.setGender(memberDetail.getGender());
        memberProfile.setMaritalStatus(memberDetail.getMaritalStatus());
        memberProfile.setNationality(memberDetail.getNationality());
        memberProfile.setMobileNumber(memberDetail.getMobileNumber());
        MemberProfile savedMemberProfile = memberProfileRepository.save(memberProfile);
        Long memberProfileId = savedMemberProfile.getMemberProfileId();
        auditLogService.addDataInAuditLog(
                optionalMemberProfile.isPresent() ? AuditUpdatedType.UPDATE : AuditUpdatedType.INSERT,
                memberProfileId, EntitiesName.MEMBER_INFORMATION, savedMemberProfile);
        return memberProfileId;
    }
}
