package com.waseel.pbm.dssservice.service.validationservice;

import com.waseel.pbm.dssservice.enums.RequestType;
import com.waseel.pbm.dssservice.enums.ValidationMessages;
import com.waseel.pbm.dssservice.exceptions.DssException;
import com.waseel.pbm.dssservice.model.CancellationOverrideRequest;
import com.waseel.pbm.dssservice.model.DssRequest;
import com.waseel.pbm.dssservice.model.DssResponse;
import com.waseel.pbm.dssservice.persist.hira.IcdDiagnosis;
import com.waseel.pbm.dssservice.persist.mdss.PayerConfig;
import com.waseel.pbm.dssservice.persist.mdss.RequestInfo;
import com.waseel.pbm.dssservice.persist.mdss.TransactionLog;
import com.waseel.pbm.dssservice.repository.hira.IcdDiagnosisRepository;
import com.waseel.pbm.dssservice.repository.mdss.PayerConfigRepository;
import com.waseel.pbm.dssservice.repository.mdss.PayerModuleConfigurationRepository;
import com.waseel.pbm.dssservice.repository.mdss.RequestInfoRepository;
import com.waseel.pbm.dssservice.repository.mdss.TransactionLogRepository;
import com.waseel.pbm.dssservice.service.managementservice.MapperService;
import com.waseel.pbm.dssservice.service.managementservice.TransactionLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TechnicalValidationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TechnicalValidationService.class);
    private static final String INVALID = "Invalid";
    @Autowired
    PayerModuleConfigurationRepository modulesConfigurationRepo;
    @Autowired
    TransactionLogRepository transactionLogRepo;
    @Autowired
    private TransactionLogService transactionLogService;
    @Autowired
    private MapperService utilsService;
    @Autowired
    private PayerConfigRepository payerConfigRepo;
    @Autowired
    private RequestInfoRepository requestInfoRepo;
    @Autowired
    private IcdDiagnosisRepository icdDiagnosisRepo;

    public void validateNewRequest(DssRequest dssRequest, ContentCachingRequestWrapper requestWrapper,
                                   RequestType requestType) throws DssException {
        isPayerConfigured(dssRequest, requestWrapper, requestType);
        validateNotFoundAndDuplicateIcdCode(dssRequest, requestWrapper, requestType);
        isRequestExists(dssRequest, requestWrapper, requestType);
    }

	public boolean validateFollowupRequest(DssRequest dssRequest, ContentCachingRequestWrapper requestWrapper,
                                           RequestType requestType) throws DssException {
        isPayerConfigured(dssRequest, requestWrapper, requestType);
        validateNotFoundAndDuplicateIcdCode(dssRequest, requestWrapper, requestType);
        return newRequestFlagSetter(dssRequest);
    }

    public void validateCancellationOverrideReqId(CancellationOverrideRequest cancelOverrideRequest,
                                                  ContentCachingRequestWrapper requestWrapper, RequestType requestType) throws DssException {
        RequestInfo requestInfo = requestInfoRepo.findExistsRequestByRequestId(cancelOverrideRequest.getRequestId());
        /*
         * Main Purpose of Adding Transaction log check is to solve the issue in
         * production , if the request invalid they cannot cancel it.
         */
        TransactionLog validNewRequest = transactionLogRepo.findNewReqByRequestId(cancelOverrideRequest.getRequestId());
        if (requestInfo == null || validNewRequest == null) {
            throw new DssException(populateInvalidCancellationOverrideResponse(cancelOverrideRequest, requestWrapper,
                    requestType, "Request Id :" + cancelOverrideRequest.getRequestId() + " Not Exist "));
        }
    }

    private void isPayerConfigured(DssRequest dssRequest, ContentCachingRequestWrapper requestWrapper,
                                   RequestType requestType) throws DssException {

        PayerConfig configuredPayer = payerConfigRepo.findByPayerIdAndIsEnabled(dssRequest.getPayerId());
        if (configuredPayer == null) {
            throw new DssException(populateInvalidDssResponse(dssRequest, requestWrapper, requestType,
                    "Payer Id :" + dssRequest.getPayerId() + " Not Exisit / Configured in PBM System"));
        }

    }

    private void isRequestExists(DssRequest dssRequest, ContentCachingRequestWrapper requestWrapper,
                                 RequestType requestType) throws DssException {
        RequestInfo requestInfo = requestInfoRepo.findByRequestId(dssRequest.getRequestId());
        if (requestInfo != null) {
            throw new DssException(populateInvalidDssResponse(dssRequest, requestWrapper, requestType,
                    "Request Id :" + dssRequest.getRequestId() + " already Exist"));
        }
    }

    private boolean newRequestFlagSetter(DssRequest dssRequest) {
        return requestInfoRepo.findByRequestId(dssRequest.getRequestId()) == null;
    }

    public DssResponse populateInvalidDssResponse(MethodArgumentNotValidException ex,
                                                  ContentCachingRequestWrapper request, RequestType requestType) {
        DssResponse invalidDssResponse = new DssResponse();
        invalidDssResponse.setStatus(INVALID);
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            errors.add(error.getDefaultMessage());
        });
        invalidDssResponse.setErrors(errors);
        invalidDssResponse.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
        invalidDssResponse.setHttpStatusDescription(errors.toString());
        invalidDssResponse.setTransactionLogId(setTransactionLogData(true, request, requestType));
        if (requestType.name().equals(RequestType.CANCELLATION.name())
                || requestType.name().equals(RequestType.OVERRIDE.name())) {
            // CANCELLATION and OVERRIDE
            CancellationOverrideRequest cancelOverrideRequest = utilsService.mapCancellationRequest(request);
            invalidDssResponse.setRequestId(cancelOverrideRequest.getRequestId());

        } else if (requestType.name().equals(RequestType.NEW.name())
                || requestType.name().equals(RequestType.FOLLOWUP.name())) {
            // NEW AND FOLLOWUP
            DssRequest dssRequest = utilsService.mapDssRequest(request);
            invalidDssResponse.setRequestId(dssRequest.getRequestId());
        }
        return invalidDssResponse;
    }

    public DssResponse populateHTTPMsgInvalidDssResponse(ContentCachingRequestWrapper request, RequestType requestType) {
        DssResponse invalidDssResponse = new DssResponse();
        invalidDssResponse.setStatus(INVALID);
        List<String> errors = new ArrayList<>();
        errors.add("Invalid values. Please recheck and enter valid values.");
        invalidDssResponse.setErrors(errors);
        invalidDssResponse.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
        invalidDssResponse.setHttpStatusDescription(errors.toString());
        invalidDssResponse.setTransactionLogId(setTransactionLogData(false, request, requestType));
        return invalidDssResponse;
    }

    public DssResponse populateInvalidDssResponse(DssRequest dssRequest, ContentCachingRequestWrapper request,
                                                  RequestType requestType, String errorMessage) {
        DssResponse invalidDssResponse = new DssResponse();
        List<String> errors = new ArrayList<>();
        errors.add(errorMessage);
        populateInvalidResponse(invalidDssResponse, request, requestType, dssRequest.getRequestId(), errors);
        return invalidDssResponse;
    }

    public DssResponse populateInvalidDssResponseForIcds(DssRequest dssRequest, ContentCachingRequestWrapper request,
                                                         RequestType requestType, List<String> errors) {
        DssResponse invalidDssResponse = new DssResponse();
        populateInvalidResponse(invalidDssResponse, request, requestType, dssRequest.getRequestId(), errors);
        return invalidDssResponse;
    }

    private void populateInvalidResponse(DssResponse invalidDssResponse, ContentCachingRequestWrapper request,
                                         RequestType requestType, String requestId, List<String> errors) {
        invalidDssResponse.setRequestId(requestId);
        invalidDssResponse.setStatus(INVALID);
        invalidDssResponse.setErrors(errors);
        invalidDssResponse.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
        invalidDssResponse.setHttpStatusDescription(errors.toString());
        invalidDssResponse.setTransactionLogId(setTransactionLogData(true, request, requestType));
    }

    public DssResponse populateInvalidDssResponse(ContentCachingRequestWrapper request,
                                                  RequestType requestType) {
        DssResponse invalidDssResponse = new DssResponse();
        boolean isRequestExists = false;

        invalidDssResponse.setStatus("Failed");
        invalidDssResponse.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        invalidDssResponse.setHttpStatusDescription(HttpStatus.INTERNAL_SERVER_ERROR.name());

        if (requestType.name().equals(RequestType.CANCELLATION.name())
                || requestType.name().equals(RequestType.OVERRIDE.name())) {
            CancellationOverrideRequest cancelRequest = utilsService.mapCancellationRequest(request);
            if (cancelRequest != null) {
                invalidDssResponse.setRequestId(cancelRequest.getRequestId());
                isRequestExists = true;
            }
        } else if (requestType.name().equals(RequestType.NEW.name())
                || requestType.name().equals(RequestType.FOLLOWUP.name())) {
            // NEW AND FOLLOWUP
            DssRequest dssRequest = utilsService.mapDssRequest(request);
            if (dssRequest != null) {
                invalidDssResponse.setRequestId(dssRequest.getRequestId());
                isRequestExists = true;
            }
        }
        populateTransactionLogId(request, invalidDssResponse, isRequestExists, requestType);

        return invalidDssResponse;
    }

    private void populateTransactionLogId(ContentCachingRequestWrapper request, DssResponse invalidDssResponse,
                                          boolean isRequestExists, RequestType requestType) {
        HttpSession session = request.getSession();

        Long transactionLogId = (Long) session.getAttribute("TransactionLogId");

        if (transactionLogId != null) {
            LOGGER.info("Get TransactionLogId from session. TransactionLogId is : {}", transactionLogId);
            // need to set for update transaction data
            invalidDssResponse.setTransactionLogId(transactionLogId);
        } else {
            invalidDssResponse.setTransactionLogId(setTransactionLogData(isRequestExists, request, requestType));
        }

    }

    public DssResponse populateInvalidCancellationOverrideResponse(CancellationOverrideRequest cancelRequest,
                                                                   ContentCachingRequestWrapper request, RequestType requestType, String errorMessage) {
        DssResponse invalidDssResponse = new DssResponse();
        invalidDssResponse.setRequestId(cancelRequest.getRequestId());
        invalidDssResponse.setStatus(INVALID);
        List<String> errors = new ArrayList<>();
        errors.add(errorMessage);
        invalidDssResponse.setErrors(errors);
        invalidDssResponse.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
        invalidDssResponse.setHttpStatusDescription(errors.toString());
        invalidDssResponse.setTransactionLogId(setTransactionLogData(true, request, requestType));
        return invalidDssResponse;
    }

    public Long setTransactionLogData(boolean isRequestIdExists, ContentCachingRequestWrapper requestWrapper,
                                      RequestType requestType) {
        // For to add log in transactionlog table
        TransactionLog log = transactionLogService.addTransactionLog(isRequestIdExists, requestType, requestWrapper);
        if (log != null) {
            LOGGER.info("Data saved in TransactionLog table. TransactionLog Id is {}", log.getTransactionLogId());
            return log.getTransactionLogId();
        }
        return null;
    }

    private void validateNotFoundAndDuplicateIcdCode(DssRequest dssRequest, ContentCachingRequestWrapper requestWrapper,
                                                     RequestType requestType) throws DssException {
        List<String> notFoundCodes = checkNotFoundIcdCodes(dssRequest);
        List<String> duplicateIcdCodes = checkDuplicateIcdCodes(dssRequest);
        if (!notFoundCodes.isEmpty() && !duplicateIcdCodes.isEmpty()) {
            List<String> notFoundDuplicateCodes = new ArrayList<>();
            notFoundDuplicateCodes.addAll(notFoundCodes);
            notFoundDuplicateCodes.addAll(duplicateIcdCodes);
            throw new DssException(
                    populateInvalidDssResponseForIcds(dssRequest, requestWrapper, requestType, notFoundDuplicateCodes));
        } else if (!notFoundCodes.isEmpty()) {
            throw new DssException(
                    populateInvalidDssResponseForIcds(dssRequest, requestWrapper, requestType, notFoundCodes));
        } else if (!duplicateIcdCodes.isEmpty()) {
            throw new DssException(
                    populateInvalidDssResponseForIcds(dssRequest, requestWrapper, requestType, duplicateIcdCodes));
        }
    }

    private List<String> checkNotFoundIcdCodes(DssRequest dssRequest) {
        List<String> notFoundCodes = new ArrayList<>();
        List<IcdDiagnosis> icdCodeDblst = icdDiagnosisRepo.findIcdCodes(dssRequest.getIcdCodes());
        if (icdCodeDblst.isEmpty() || icdCodeDblst.size() != dssRequest.getIcdCodes().size()) {
            dssRequest.getIcdCodes().stream()
                    .filter(icdCodeDb -> icdCodeDblst.stream()
                            .noneMatch(icdCode -> icdCode.getIcdDiagnosisCode().equalsIgnoreCase(icdCodeDb)))
                    .distinct()
                    .forEach(code -> notFoundCodes.add(ValidationMessages.ICDCODE_NOTFOUND.value() + code.trim()));

        }
        return notFoundCodes;
    }

    private List<String> checkDuplicateIcdCodes(DssRequest dssRequest) {
        Set<String> items = new HashSet<>();
        List<String> duplicateIcdCodes = new ArrayList<>();
        dssRequest.getIcdCodes().stream().filter(n -> !items.add(n))
                .forEach(code -> duplicateIcdCodes.add(ValidationMessages.FOUND_DUPLICATE_ICDS.value() + code.trim()));
        return duplicateIcdCodes;
    }

}