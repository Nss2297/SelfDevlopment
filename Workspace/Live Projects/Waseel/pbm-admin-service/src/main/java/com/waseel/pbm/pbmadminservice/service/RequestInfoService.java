//package com.waseel.pbm.pbmadminservice.service;
//
//import com.waseel.pbm.pbmadminservice.dto.ServiceInfoDto;
//import com.waseel.pbm.pbmadminservice.enums.RequestStatus;
//import com.waseel.pbm.pbmadminservice.model.Error;
//import com.waseel.pbm.pbmadminservice.model.*;
//import com.waseel.pbm.pbmadminservice.persist.hira.SwitchAccount;
//import com.waseel.pbm.pbmadminservice.persist.mdss.IcdDiagnosisInfo;
//import com.waseel.pbm.pbmadminservice.persist.mdss.MemberInfo;
//import com.waseel.pbm.pbmadminservice.persist.mdss.PhysicianInfo;
//import com.waseel.pbm.pbmadminservice.persist.mdss.RequestInfo;
//import com.waseel.pbm.pbmadminservice.repository.hira.SwitchAccountRepository;
//import com.waseel.pbm.pbmadminservice.repository.mdss.*;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class RequestInfoService {
//
//    private final Logger log = LoggerFactory.getLogger(RequestInfoService.class);
//
//    @Autowired
//    private RequestInfoRepository requestInfoRepository;
//
//    @Autowired
//    private MemberInfoRepository memberInfoRepository;
//
//    @Autowired
//    private SwitchAccountRepository switchAccountRepository;
//
//    @Autowired
//    private ServiceInfoRepository serviceInfoRepository;
//
//    @Autowired
//    private ICDDiagnosisInfoRepository icdDiagnosisInfoRepository;
//
//    @Autowired
//    private PhysicianInfoRepository physicianInfoRepository;
//
//    public Page<RequestInfoModel> getRequestInfoList(String requestId, String memberId, String dateFrom, String dateTo,
//                                                     String payerId, int pageNumber, int recordSize) {
//        Page<RequestInfoModel> searchList = null;
//        if (!StringUtils.isBlank(requestId)) {
//            log.info("Search by RequestId: {},dateFrom: {}, dateTo:{}, payerId:{}, pageNumber:{}, recordSize:{}",
//                    requestId, dateFrom, dateTo, payerId, pageNumber, recordSize);
//            searchList = requestInfoRepository.searchByRequestIdAndPayerIdAndDate(dateFrom, dateTo, requestId, payerId,
//                    PageRequest.of(pageNumber, recordSize));
//        }
//        if (!StringUtils.isBlank(memberId)) {
//            log.info("Search by MemberId: {},dateFrom: {}, dateTo:{}, payerId:{}, pageNumber:{}, recordSize:{}",
//                    memberId, dateFrom, dateTo, payerId, pageNumber, recordSize);
//            searchList = memberInfoRepository.searchByMemberIdAndPayerIdAndDate(dateFrom, dateTo, memberId, payerId,
//                    PageRequest.of(pageNumber, recordSize));
//        }
//        return convertProviderIdToProviderName(searchList);
//    }
//
//    private Page<RequestInfoModel> convertProviderIdToProviderName(Page<RequestInfoModel> searchList) {
//        searchList.forEach(model -> model.setProviderName(getProviderName(model.getProviderName())));
//        return searchList;
//    }
//
//    private String getProviderName(String providerId) {
//        if (!StringUtils.isBlank(providerId)) {
//            Optional<SwitchAccount> switchAccountOptional = switchAccountRepository
//                    .findById(new BigDecimal(providerId));
//            if (switchAccountOptional.isPresent()) {
//                return switchAccountOptional.get().getName();
//            }
//        }
//        return "";
//    }
//
//    private void setRequestInfo(DssRequest request, DssResponse response, String requestId) {
//        Optional<RequestInfo> optionalRequestInfo = requestInfoRepository.findByRequestId(requestId);
//        optionalRequestInfo.ifPresent(requestInfo -> {
//            request.setRequestId(requestId);
//            response.setRequestId(requestId);
//            request.setPayerId(requestInfo.getPayerId());
//            request.setPharmacyId(requestInfo.getProviderId());
//        });
//    }
//
//    private void setPhysicianInfo(DssRequest request, String requestId) {
//        Optional<PhysicianInfo> optionalPhysicianInfo = physicianInfoRepository.findByRequestId(requestId);
//        optionalPhysicianInfo.ifPresent(physicianInfo ->
//                request.setPrescriberId(physicianInfo.getId().getPhysicianId())
//        );
//    }
//
//    private void setMemberInfo(DssRequest request, String requestId) {
//        Optional<MemberInfo> optionalMemberInfo = memberInfoRepository.findByRequestId(requestId);
//        optionalMemberInfo.ifPresent(memberInfo -> {
//            request.setDateOfBirth(memberInfo.getDateOfBirth());
//            request.setMemberGender(memberInfo.getMemberGender());
//            request.setMemberId(memberInfo.getMemberId());
//            request.setDateOfBirth(memberInfo.getDateOfBirth());
//            String weight = memberInfo.getMemberWeight();
//            if (!StringUtils.isBlank(weight))
//                request.setMemberWeight(BigDecimal.valueOf(Double.valueOf(weight)));
//        });
//    }
//
//    private void setICDCodes(DssRequest request, String requestId) {
//        List<IcdDiagnosisInfo> icdDiagnosisInfoList = icdDiagnosisInfoRepository
//                .findByRequestIdAndIsDeletedFromProvider(requestId);
//        List<String> icdCodes = new ArrayList<>();
//        icdDiagnosisInfoList.forEach(icdDiagnosisInfo ->
//                icdCodes.add(icdDiagnosisInfo.getId().getIcdDiagnosisCode())
//        );
//        request.setIcdCodes(icdCodes);
//    }
//
//    private void setResultData(Result result, ServiceInfoDto infoDto) {
//        result.setAmount(infoDto.getServiceAmount());
//        result.setDaysOfSupply(infoDto.getDaysOfSupply());
//        result.setStatus(infoDto.getStatus());
//        result.setDispensedQuantity(infoDto.getServiceQuantity());
//        result.setNdcDrugCode(infoDto.getServiceCode());
//    }
//
//    private void addErrors(ServiceInfoDto infoDto, List<Error> errors) {
//        if (!infoDto.getRequestStatus().equals(RequestStatus.APPROVED.value())) {
//            Error error = new Error();
//            error.setCode(infoDto.getRejectionCode());
//            error.setDescription(infoDto.getRejectionReason());
//            errors.add(error);
//        }
//    }
//
//    private void setResults(Result result, List<Result> results, List<Error> errors) {
//        if (!errors.isEmpty()) {
//            result.setErrors(errors);
//        }
//        results.add(result);
//    }
//
//    public RequestInfoResponse getRequestInfoDetail(String requestId) {
//        DssRequest request = new DssRequest();
//        DssResponse response = new DssResponse();
//        List<DrugList> drugList = new ArrayList<>();
//        List<String> errorList = new ArrayList<>();
//        List<Result> results = new ArrayList<>();
//        List<Error> errors = new ArrayList<>();
//        String serviceCode = null;
//        Result result = new Result();
//        List<ServiceInfoDto> infoDtoList = serviceInfoRepository.findServiceInfoDetails(requestId);
//        for (ServiceInfoDto infoDto : infoDtoList) {
//            String currentServiceCode = infoDto.getServiceCode();
//            if (!currentServiceCode.equals(serviceCode)) {
//                request.setDateOfService(infoDto.getServiceDate());
//                response.setStatus(infoDto.getRequestStatus());
//                if (serviceCode != null) {
//                    setResults(result, results, errors);
//                    result = new Result();
//                    errors = new ArrayList<>();
//                }
//                drugList.add(new DrugList(currentServiceCode, infoDto.getServiceQuantity()
//                        , infoDto.getServiceAmount(), infoDto.getDaysOfSupply()));
//                setResultData(result, infoDto);
//                serviceCode = currentServiceCode;
//            }
//            errorList.add(infoDto.getRejectionReason());
//            addErrors(infoDto, errors);
//        }
//        setResults(result, results, errors);
//        return setRequestResponse(request, response, drugList, results, errorList, requestId);
//    }
//
//    private RequestInfoResponse setRequestResponse(DssRequest request, DssResponse response,
//                                                   List<DrugList> drugList, List<Result> results,
//                                                   List<String> errorList, String requestId) {
//        setRequestInfo(request, response, requestId);
//        setMemberInfo(request, requestId);
//        setICDCodes(request, requestId);
//        setPhysicianInfo(request, requestId);
//        request.setDrugList(drugList);
//        response.setResults(results);
//        response.setErrors(errorList);
//        response.setHttpStatusCode(HttpStatus.OK.value());
//        RequestInfoResponse searchResponse = new RequestInfoResponse();
//        searchResponse.setDssResponse(response);
//        searchResponse.setDssRequest(request);
//        return searchResponse;
//    }
//}
