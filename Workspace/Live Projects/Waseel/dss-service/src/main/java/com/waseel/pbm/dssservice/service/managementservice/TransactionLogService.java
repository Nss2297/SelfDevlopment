package com.waseel.pbm.dssservice.service.managementservice;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.waseel.pbm.dssservice.enums.RequestType;
import com.waseel.pbm.dssservice.enums.TransactionStatusType;
import com.waseel.pbm.dssservice.model.CancelOverrideResponse;
import com.waseel.pbm.dssservice.model.CancellationOverrideRequest;
import com.waseel.pbm.dssservice.model.DssRequest;
import com.waseel.pbm.dssservice.model.DssResponse;
import com.waseel.pbm.dssservice.persist.mdss.PayerConfig;
import com.waseel.pbm.dssservice.persist.mdss.RequestInfo;
import com.waseel.pbm.dssservice.persist.mdss.TransactionLog;
import com.waseel.pbm.dssservice.repository.mdss.PayerConfigRepository;
import com.waseel.pbm.dssservice.repository.mdss.RequestInfoRepository;
import com.waseel.pbm.dssservice.repository.mdss.TransactionLogRepository;

@Service
public class TransactionLogService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionLogService.class);

	private static final String CANCELLATION_URL = "/validate/cancellation";
	private static final String OVERRIDE_URL = "/validate/override";
	private static final String NEW_URL = "/validate/new";
	private static final String FOLLOWUP_URL = "/validate/followup";
	
	@Autowired
	private MapperService mapperservice;

	@Autowired
	private TransactionLogRepository transactionLogRepo;

	@Autowired
	private PayerConfigRepository payerConfigRepo;

	@Autowired
	private RequestInfoRepository requestInfoRepo;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private DataManipulationAuditService dataManipulationAuditService;

	public TransactionLog addTransactionLog(boolean isRequestIdExists, RequestType requestType,
			ContentCachingRequestWrapper request) {
		try {
			TransactionLog log = new TransactionLog();
			PayerConfig configuredPayer = null;
			String requestId = null;
			if (requestType.name().equals(RequestType.CANCELLATION.name())
					|| requestType.name().equals(RequestType.OVERRIDE.name())) {
				CancellationOverrideRequest cancelOverrideRequest = mapperservice.mapCancellationRequest(request);
				if (cancelOverrideRequest != null) {
					requestId = cancelOverrideRequest.getRequestId();
					RequestInfo requestInfo = requestInfoRepo.findByRequestId(cancelOverrideRequest.getRequestId());
					if (requestInfo != null) {
						configuredPayer = payerConfigRepo.findByPayerId(requestInfo.getPayerId());
						log.setPayerId(requestInfo.getPayerId());
						log.setProviderId(requestInfo.getProviderId());
					}
				}
			} else {
				// NEW AND FOLLOWUP
				DssRequest dssRequest = mapperservice.mapDssRequest(request);
				if (dssRequest != null) {
					configuredPayer = payerConfigRepo.findByPayerId(dssRequest.getPayerId());
					requestId = dssRequest.getRequestId();
					if (dssRequest.getPayerId().getBytes().length <= 20) {
						log.setPayerId(dssRequest.getPayerId());
					}
					if (dssRequest.getPharmacyId().getBytes().length <= 20) {
						log.setProviderId(dssRequest.getPharmacyId());
					}
				}
			}
			if (isRequestIdExists && requestId != null && requestId.getBytes().length <= 100)
				log.setRequestId(requestId);
			if (configuredPayer != null)
				log.setValidationRequester(configuredPayer.getId().getPbmPayerType());
			log.setTransactionId(getTransactionId(requestType));
			log.setTransactionStatus(TransactionStatusType.RECEIVED.value());
			log.setTransactionType(getStatusDescription(requestType));
			log.setReceivingRequestDateTime((new Timestamp(Calendar.getInstance().getTimeInMillis())));
			return transactionLogRepo.save(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public TransactionLog updateTransactionLog(Long transactionLogId, String httpStatus, String httpStatusDesc,
			String pbmStatus, HttpServletRequest request) {
		TransactionLog updatedTranLog = null;
		try {
			if (transactionLogId != null) {
				TransactionLog log = transactionLogRepo.findBytransactionLogId(transactionLogId);
				log.setHttpStatus(httpStatus);
				log.setHttpStatusDescription(httpStatusDesc);
				log.setPbmstatus(pbmStatus);
				log.setTransactionStatus(TransactionStatusType.SENT.value());
				log.setSendingResponseDateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
				updatedTranLog = transactionLogRepo.save(log);

				// Need to remove transactionLogId from session for avoid any conflict
				sessionService.removeTransactionLogIdFromSession(request);
				LOGGER.info("Data Updated in TransactionLog table. TransactionLogId is " + log.getTransactionLogId());
				return updatedTranLog;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return updatedTranLog;
	}

	public void updateTransactionLogForNewFollowUp(DssResponse dssResponse, DssRequest dssRequest,
			HttpServletRequest request, RequestType requestType) {
		try {
			if (dssResponse.getTransactionLogId() != null) {
				TransactionLog updatedTranLog = updateTransactionLog(dssResponse.getTransactionLogId(),
						String.valueOf(dssResponse.getHttpStatusCode()), dssResponse.getHttpStatusDescription(),
						dssResponse.getStatus(), request);
				if (updatedTranLog != null) {
					dssResponse.setTransactionLogId(null);
					dataManipulationAuditService.saveAuditLogInMongoDbForNewFollowup(dssRequest, dssResponse,
							requestType, updatedTranLog.getTransactionLogId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateTransactionLogForCancellationOverride(CancelOverrideResponse cancelOverrideResponse,
			CancellationOverrideRequest cancelOverrideRequest, HttpServletRequest request, RequestType requestType) {
		try {
			if (cancelOverrideResponse.getTransactionLogId() != null) {
				TransactionLog updatedTranLog = updateTransactionLog(cancelOverrideResponse.getTransactionLogId(),
						String.valueOf(cancelOverrideResponse.getCode()), cancelOverrideResponse.getMessage(),
						requestType.name(), request);
				if (updatedTranLog != null) {
					cancelOverrideResponse.setTransactionLogId(null);
					dataManipulationAuditService.saveAuditLogInMongoDbForCancellationOverride(cancelOverrideRequest,
							cancelOverrideResponse, requestType, updatedTranLog.getTransactionLogId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateTransactionLogForCancellationOverrideInvalidResponse(DssResponse dssResponse,
			CancellationOverrideRequest cancelRequest, HttpServletRequest request, RequestType requestType) {
		try {
			if (dssResponse.getTransactionLogId() != null) {
				TransactionLog updatedTranLog = updateTransactionLog(dssResponse.getTransactionLogId(),
						String.valueOf(dssResponse.getHttpStatusCode()), dssResponse.getHttpStatusDescription(),
						"Invalid", request);
				if (updatedTranLog != null) {
					dssResponse.setTransactionLogId(null);
					dataManipulationAuditService.saveAuditLogInMongoDbForCancellationOverrideInvalidRes(cancelRequest,
							dssResponse, requestType, updatedTranLog.getTransactionLogId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void manageTransactionLogForNewFollowUp(DssResponse dssRes, ContentCachingRequestWrapper requestWrapper) {
		// To update transactionlog
		DssRequest dssReq = mapperservice.mapDssRequest(requestWrapper);
		RequestType requestType = null;
		if(dssReq != null) {
			Optional<List<TransactionLog>> log = transactionLogRepo.findByRequestId(dssReq.getRequestId());
			if (log.isPresent() && log.get().size() > 1) {
				requestType = RequestType.FOLLOWUP;
			} else {
				requestType = RequestType.NEW;
			}
			if (dssRes != null && dssRes.getTransactionLogId() != null) {
				updateTransactionLogForNewFollowUp(dssRes, dssReq, requestWrapper, requestType);
			}
		}
	}

	public void manageTransactionLogForCancellationOverride(CancelOverrideResponse cancelRes, DssResponse dssRes,
			HttpServletRequest request, ContentCachingRequestWrapper requestWrapper) {
		// To update transactionlog
		CancellationOverrideRequest cancelReq = mapperservice.mapCancellationRequest(requestWrapper);
		RequestType requestType = null;
		if (request.getRequestURI().contains(CANCELLATION_URL)) {
			requestType = RequestType.CANCELLATION;
		} else if (request.getRequestURI().contains(OVERRIDE_URL)) {
			requestType = RequestType.OVERRIDE;
		}
		if (cancelRes != null && cancelRes.getTransactionLogId() != null) {
			updateTransactionLogForCancellationOverride(cancelRes, cancelReq, request, requestType);
		} else if (dssRes != null && dssRes.getTransactionLogId() != null) {
			updateTransactionLogForCancellationOverrideInvalidResponse(dssRes, cancelReq, request, requestType);
		}
	}

	public void manageTransactionLogIdFromResponse(HttpServletRequest request, HttpServletResponse response,
			ContentCachingResponseWrapper responseWrapper, ContentCachingRequestWrapper requestWrapper) {
		try {
			if (request.getRequestURI().contains(CANCELLATION_URL) || request.getRequestURI().contains(OVERRIDE_URL)) {
				Object cancelORDssResponse = null;
				CancelOverrideResponse cancelRes = mapperservice.mapCancellationResponse(responseWrapper);
				DssResponse dssRes = mapperservice.mapDssResponse(responseWrapper);
				// Need it for Invalid response
				if (cancelRes != null) {
					// Valid response comes
					manageTransactionLogForCancellationOverride(cancelRes, null, request, requestWrapper);
					// Remove transactionlogid from response
					cancelRes.setTransactionLogId(null);
					cancelORDssResponse = cancelRes;
				} else if (dssRes != null) {
					// Invalid Response comes
					manageTransactionLogForCancellationOverride(null, dssRes, request, requestWrapper);
					// Remove transactionlogid from response
					dssRes.setTransactionLogId(null);
					cancelORDssResponse = dssRes;
				}
				// Used to reset response
				response.resetBuffer();
				response.addHeader(HttpHeaders.CONTENT_TYPE, "Application/json");
				String cancelResponse = mapperservice.mapString(cancelORDssResponse);
				response.getOutputStream().print(cancelResponse);
			} else if(request.getRequestURI().contains(NEW_URL) || request.getRequestURI().contains(FOLLOWUP_URL)){
				// NEW AND FOLLOWUP
				DssResponse res = mapperservice.mapDssResponse(responseWrapper);
				manageTransactionLogForNewFollowUp(res, requestWrapper);
				// Remove transactionlogid from response
				res.setTransactionLogId(null);
				// Used to reset response
				response.resetBuffer();
				response.addHeader(HttpHeaders.CONTENT_TYPE, "Application/json");
				String dssResponse = mapperservice.mapString(res);
				response.getOutputStream().print(dssResponse);
			}else {
				String responseStr = new String(responseWrapper.getContentAsByteArray(), responseWrapper.getCharacterEncoding());
				response.getOutputStream().print(responseStr);
			}
			LOGGER.info("Remove transactionLog id from Response.");
			response.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Double getTransactionId(RequestType type) {
		switch (type) {
		case NEW:
			return 50.1;
		case FOLLOWUP:
			return 50.12;
		case CANCELLATION:
			return 50.13;
		case OVERRIDE:
			return 50.14;
		default:
			return null;
		}
	}

	private String getStatusDescription(RequestType type) {
		switch (type) {
		case NEW:
			return RequestType.NEW.name();
		case FOLLOWUP:
			return RequestType.FOLLOWUP.name();
		case CANCELLATION:
			return RequestType.CANCELLATION.name();
		case OVERRIDE:
			return RequestType.OVERRIDE.name();
		default:
			return RequestType.NEW.name();
		}
	}
}
