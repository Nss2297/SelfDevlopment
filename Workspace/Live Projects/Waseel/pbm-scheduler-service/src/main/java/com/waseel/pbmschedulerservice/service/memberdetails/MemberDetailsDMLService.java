package com.waseel.pbmschedulerservice.service.memberdetails;

import com.waseel.pbmschedulerservice.clients.MemberDetailsServiceClient;
import com.waseel.pbmschedulerservice.model.enums.RequestType;
import com.waseel.pbmschedulerservice.model.memberdetails.MemberDetailsModel;
import com.waseel.pbmschedulerservice.model.memberdetails.MemberDetailsResponseModel;
import com.waseel.pbmschedulerservice.model.memberdetails.MembersResponseModel;
import com.waseel.pbmschedulerservice.model.policydetails.InvalidResponseModel;
import com.waseel.pbmschedulerservice.persist.businessrules.PolicyInformation;
import com.waseel.pbmschedulerservice.persist.businessrules.TransactionLog;
import com.waseel.pbmschedulerservice.service.MapperService;
import com.waseel.pbmschedulerservice.service.TransactionLogService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MemberDetailsDMLService {

    @Value("${receiver.code}")
    private String payerId;

    @Autowired
    private MemberDetailsServiceClient memberDetailsServiceClient;
    @Autowired
    private TransactionLogService transactionLogService;
    @Autowired
    private MapperService mapperService;

    public ResponseEntity<MembersResponseModel> getMemberDetailsByPolicyNumber(PolicyInformation policyInformation,
                                                                               int pageSize, int pageNumber) {
        ResponseEntity<MembersResponseModel> responseEntity;
        TransactionLog transactionLog = transactionLogService
                .addDataInTransactionLog(payerId, RequestType.MEMBER_DETAILS);
        try {
            responseEntity = memberDetailsServiceClient.
                    getMemberDetailsByPolicyNumber(policyInformation.getPolicyNumber(), pageSize, pageNumber);
            transactionLogService.updateDataInTransactionLog(transactionLog.getTransactionLogId(),
                    null, null, responseEntity.getStatusCode().toString(),
                    responseEntity.getStatusCode().toString(), responseEntity.getHeaders().getOrigin());
        } catch (FeignException e) {
            String status = null;
            String statusDesc = null;
            if (e.status() == HttpStatus.BAD_REQUEST.value()
                    || e.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                InvalidResponseModel invalidResponseModel = mapperService.mapInvalidResponseModel(e.contentUTF8());
                status = invalidResponseModel.getStatus();
                statusDesc = invalidResponseModel.getErrors().toString();
            }
            transactionLogService.updateDataInTransactionLog(
                    transactionLog.getTransactionLogId(), status, statusDesc, String.valueOf(e.status()), null,
                    e.request().url());
            throw new FeignException.FeignClientException(e.status(), e.getMessage(), e.request(), null, e.responseHeaders());
        }
        return responseEntity;
    }

    public ResponseEntity<MemberDetailsResponseModel> getMemberDetailsByLastUpdateDate(
            int pageSize, int pageNumber, String lastUpdateDate) {
        ResponseEntity<MemberDetailsResponseModel> responseEntity;
        TransactionLog transactionLog = transactionLogService
                .addDataInTransactionLog(payerId, RequestType.MEMBER_DETAILS);
        try {
            responseEntity = memberDetailsServiceClient.getMemberDetailsByLastUpdateDate(lastUpdateDate, pageSize, pageNumber);
            transactionLogService.updateDataInTransactionLog(transactionLog.getTransactionLogId(),
                    null, null, responseEntity.getStatusCode().toString(),
                    responseEntity.getStatusCode().toString(), responseEntity.getHeaders().getOrigin());
        } catch (FeignException e) {
            String status = null;
            String statusDesc = null;
            if (e.status() == HttpStatus.BAD_REQUEST.value()
                    || e.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                InvalidResponseModel invalidResponseModel = mapperService.mapInvalidResponseModel(e.contentUTF8());
                status = invalidResponseModel.getStatus();
                statusDesc = invalidResponseModel.getErrors().toString();
            }
            transactionLogService.updateDataInTransactionLog(
                    transactionLog.getTransactionLogId(), status, statusDesc, String.valueOf(e.status()), null,
                    e.request().url());
            throw new FeignException.FeignClientException(e.status(), e.getMessage(), e.request(), null, e.responseHeaders());
        }
        return responseEntity;
    }

    public ResponseEntity<MemberDetailsModel> getMemberDetailsByIDNumber(Long idNumber) {
        ResponseEntity<MemberDetailsModel> responseEntity;
        TransactionLog transactionLog = transactionLogService
                .addDataInTransactionLog(payerId, RequestType.MEMBER_DETAILS);
        try {
            responseEntity = memberDetailsServiceClient.getMemberDetailsByIDNumber(idNumber);
            transactionLogService.updateDataInTransactionLog(transactionLog.getTransactionLogId(),
                    null, null, responseEntity.getStatusCode().toString(),
                    responseEntity.getStatusCode().toString(), responseEntity.getHeaders().getOrigin());
        } catch (FeignException e) {
            String status = null;
            String statusDesc = null;
            if (e.status() == HttpStatus.BAD_REQUEST.value()
                    || e.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                InvalidResponseModel invalidResponseModel = mapperService.mapInvalidResponseModel(e.contentUTF8());
                status = invalidResponseModel.getStatus();
                statusDesc = invalidResponseModel.getErrors().toString();
            }
            transactionLogService.updateDataInTransactionLog(
                    transactionLog.getTransactionLogId(), status, statusDesc, String.valueOf(e.status()), null,
                    e.request().url());
            throw new FeignException.FeignClientException(e.status(), e.getMessage(), e.request(), null, e.responseHeaders());
        }
        return responseEntity;
    }
}
