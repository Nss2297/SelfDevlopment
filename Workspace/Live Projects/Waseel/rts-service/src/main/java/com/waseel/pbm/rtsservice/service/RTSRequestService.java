package com.waseel.pbm.rtsservice.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.pbm.rtsservice.dto.ServiceInfoDto;
import com.waseel.pbm.rtsservice.enums.RejectionCodes;
import com.waseel.pbm.rtsservice.enums.RequestStatus;
import com.waseel.pbm.rtsservice.enums.ServiceStatus;
import com.waseel.pbm.rtsservice.model.Error;
import com.waseel.pbm.rtsservice.model.RTSRequest;
import com.waseel.pbm.rtsservice.model.RTSResponse;
import com.waseel.pbm.rtsservice.model.Result;
import com.waseel.pbm.rtsservice.persist.hira.AccountToAccountAssociationNonWaseel;
import com.waseel.pbm.rtsservice.persist.hira.SwitchAccount;
import com.waseel.pbm.rtsservice.persist.mdss.CommonRejectionReason;
import com.waseel.pbm.rtsservice.repository.hira.AccountToAccountAssociationNonWaseelRepository;
import com.waseel.pbm.rtsservice.repository.hira.SwitchAccountRepository;
import com.waseel.pbm.rtsservice.repository.mdss.CommonRejectionReasonRepository;
import com.waseel.pbm.rtsservice.repository.mdss.RTSRequestRepository;
import com.waseel.pbm.rtsservice.repository.mdss.RequestInfoRepository;

@Service
public class RTSRequestService {

	@Autowired
	RTSRequestRepository rtsRequestRepository;
	@Autowired
	CommonRejectionReasonRepository commonRejectionReasonRepository;
	@Autowired
	SwitchAccountRepository switchAccountRepository;
	@Autowired
	RequestInfoRepository requestInfoRepository;
	@Autowired
	private DMLService dmlService;
	@Autowired
	private AccountToAccountAssociationNonWaseelRepository AccountToAccountAssociationNonWaseelRepository;

	public RTSResponse validateRTSRequest(RTSRequest rtsRequest) {
		List<Result> resultList = new ArrayList<>();
		var ref = new Object() {
			boolean isAllApproved = true;
			boolean isAllRejected = true;
		};
		rtsRequest.getDrugList().forEach(drugList -> {
			boolean isValid = false;
			ServiceInfoDto serviceInfoDto = null;
			if (drugList.getDaysOfSupply() != null && !drugList.getDaysOfSupply().isEmpty()) {
				serviceInfoDto = rtsRequestRepository.findServiceInfoDetails(rtsRequest.getMemberId(),
						drugList.getNdcDrugCode(), rtsRequest.getRequestId(), rtsRequest.getPayerId());
				isValid = (serviceInfoDto != null)
						? validateDaysOfSupply(serviceInfoDto.getDaysOfSupply(), serviceInfoDto.getServiceDate(),
								rtsRequest.getDateOfService())
						: true;
			} else {
				isValid = true;
			}
			Result result = new Result();
			result.setAmount(drugList.getAmount());
			result.setDaysOfSupply(drugList.getDaysOfSupply());
			result.setDispensedQuantity(drugList.getDispensedQuantity());
			result.setNdcDrugCode(drugList.getNdcDrugCode());
			if (isValid) {
				result.setStatus(ServiceStatus.APPROVED.toString());
				ref.isAllRejected = false;
			} else {
				List<Error> errorList = new ArrayList<>();
				result.setStatus(ServiceStatus.REJECTED.toString());
				Error error = new Error();
				String rejectionCode = RejectionCodes.RTS_REJECTION_CODE.value();

				error.setCode(rejectionCode);
				CommonRejectionReason rejectionReason = commonRejectionReasonRepository
						.findByRejectionCode(rejectionCode);
				if (rejectionReason != null && serviceInfoDto != null) {
					String providerName = getProviderName(getProviderId(serviceInfoDto.getRequestId()),
							serviceInfoDto.getRequestId());
					String reason;

					if (!providerName.equalsIgnoreCase("")) {
						reason = rejectionReason.getRejectionReason().replace("<DrugCode>", drugList.getNdcDrugCode())
								.replace("<Last_Refill_Date>",
										new SimpleDateFormat("E MMM dd yyyy").format(serviceInfoDto.getServiceDate()))
								.replace("<ProviderName>", providerName);
					} else {
						reason = rejectionReason.getRejectionReason().replace("<DrugCode>", drugList.getNdcDrugCode())
								.replace("<Last_Refill_Date>",
										new SimpleDateFormat("E MMM dd yyyy").format(serviceInfoDto.getServiceDate()))
								.replace("by Provider : <ProviderName>", providerName);
					}

					error.setDescription(reason);
				}
				errorList.add(error);
				result.setErrors(errorList);
				ref.isAllApproved = false;
			}
			resultList.add(result);
		});
		return setResponse(rtsRequest, resultList, ref.isAllApproved, ref.isAllRejected);
	}

	private String getProviderId(String requestId) {
		return requestInfoRepository.findByrequestId(requestId).getProviderId();
	}

	private String getProviderName(String pharmacyId, String requestId) {
		if (!StringUtils.isBlank(pharmacyId)) {

			if (!pharmacyId.equalsIgnoreCase("99999")) {
				Optional<SwitchAccount> switchAccountOptional = switchAccountRepository
						.findById(new BigDecimal(pharmacyId));
				if (switchAccountOptional.isPresent()) {
					SwitchAccount switchAccount = switchAccountOptional.get();
					return switchAccount.getName();
				}
			} else {
				AccountToAccountAssociationNonWaseel providerInfo = AccountToAccountAssociationNonWaseelRepository
						.findProviderNameByRequestId(new BigDecimal(requestId));
				if (providerInfo != null && providerInfo.getName()!= null && !providerInfo.getName().isEmpty()) {
					return providerInfo.getName();
				}
			}

		}
		return "";
	}

	private RTSResponse setResponse(RTSRequest rtsRequest, List<Result> resultList, boolean isAllApproved,
			boolean isAllRejected) {
		RTSResponse response = new RTSResponse();
		response.setRequestId(rtsRequest.getRequestId());
		response.setResults(resultList);
		response.setTransactionLogId(rtsRequest.getTransactionLogId());
		String status = RequestStatus.PARTIAL_APPROVED.toString();
		if (isAllApproved) {
			status = RequestStatus.APPROVED.toString();
		} else if (isAllRejected) {
			status = RequestStatus.REJECTED.toString();
		}
		response.setStatus(status);
		response.setHttpStatusCode(200);

		dmlService.saveAuditLogInMongoDbAndOracle(rtsRequest, response);
		return response;
	}

	private boolean validateDaysOfSupply(Integer daysOfSupply, Timestamp serviceDateTS, String dateOfServiceStr) {
		try {
			Date serviceDate = new Date(serviceDateTS.getTime());
			Date dateOfService = new SimpleDateFormat("dd/MM/yyyy").parse(dateOfServiceStr);
			long differenceInTime = dateOfService.getTime() - serviceDate.getTime();
			long differenceInDays = ((differenceInTime / (1000 * 60 * 60 * 24)) % 365) + 1;
			return differenceInDays >= ((int) Math.ceil(daysOfSupply * 0.90));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
