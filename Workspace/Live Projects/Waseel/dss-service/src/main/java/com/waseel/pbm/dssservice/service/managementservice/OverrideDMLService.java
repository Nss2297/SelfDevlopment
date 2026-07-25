package com.waseel.pbm.dssservice.service.managementservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.pbm.dssservice.enums.EnableDisableStatus;
import com.waseel.pbm.dssservice.enums.ServiceStatus;
import com.waseel.pbm.dssservice.model.CancellationOverrideRequest;
import com.waseel.pbm.dssservice.persist.mdss.RequestInfo;
import com.waseel.pbm.dssservice.persist.mdss.ServiceDecision;
import com.waseel.pbm.dssservice.persist.mdss.ServiceDecisionId;
import com.waseel.pbm.dssservice.persist.mdss.Serviceinfo;
import com.waseel.pbm.dssservice.repository.mdss.RequestInfoRepository;
import com.waseel.pbm.dssservice.repository.mdss.ServiceDecisionRepository;
import com.waseel.pbm.dssservice.repository.mdss.ServiceInfoRepository;

@Service
public class OverrideDMLService {

	private String msgApprovedBefore = "This request is already Override before.";
	private String msgApprovedSuccess = "Requested ID Transaction Override Successfully";

	@Autowired
	private RequestInfoRepository requestInfoRepo;

	@Autowired
	private ServiceInfoRepository serviceInfoRepo;

	@Autowired
	private ServiceDecisionRepository serviceDecisionRepo;

	public String populateNotEmptyDrugList(CancellationOverrideRequest overrideRequest, RequestInfo requestInfo) {
		List<String> alreadyApprovedDrugsList = new ArrayList<>();
		List<String> approvedBeforeDrugsList = new ArrayList<>();
		List<String> notFoundDrugsList = new ArrayList<>();
		var wrapper = new Object() {
			boolean isAllNotFound = true;
			boolean isAllAlreadyApproved = true;
			boolean isAllApprovedBefore = true;
			boolean isSuccess = false;
			int isAllSucess = 0;
		};
		overrideRequest.getDrugList().forEach(drugCode -> {
			Serviceinfo service = serviceInfoRepo.findNoDeletedByRequestIdANDServiceCodeORScientificCode(overrideRequest.getRequestId(),
					drugCode);
			if (service != null) {
				wrapper.isAllNotFound = false;
				// Found servicecode
				ServiceDecision serviceDecision = serviceDecisionRepo
						.findByRequestIdAndServiceId(service.getId().getRequestId(), service.getId().getServiceId());

				if (service.getIsOverriden() == EnableDisableStatus.TRUE.value()) {
					if (serviceDecision != null && serviceDecision.getStatus().equals(ServiceStatus.APPROVED.name())) {
						wrapper.isAllAlreadyApproved = false;
						approvedBeforeDrugsList.add(service.getServiceCode()!=null && !service.getServiceCode().isEmpty() ?service.getServiceCode():service.getScientificCode());
					} else {
						wrapper.isAllSucess++;
						wrapper.isAllApprovedBefore = false;
						wrapper.isSuccess = true;
						// some of drugcodes are not approved with isOverride = 1
						ServiceDecisionId id = serviceDecision.getId();
						serviceDecision.setStatus(ServiceStatus.APPROVED.name());
						serviceDecision.setId(id);
						serviceDecisionRepo.save(serviceDecision);
					}
				} else {
					if (serviceDecision != null && serviceDecision.getStatus().equals(ServiceStatus.APPROVED.name())) {
						wrapper.isAllApprovedBefore = false;
						alreadyApprovedDrugsList.add(service.getServiceCode()!=null && !service.getServiceCode().isEmpty()?service.getServiceCode():service.getScientificCode());
					} else {
						wrapper.isAllSucess++;
						wrapper.isSuccess = true;
						wrapper.isAllAlreadyApproved = false;
						service.setIsOverriden(EnableDisableStatus.TRUE.value());
						ServiceDecisionId id = serviceDecision.getId();
						serviceDecision.setStatus(ServiceStatus.APPROVED.name());
						serviceDecision.setId(id);
						serviceDecisionRepo.save(serviceDecision);
						serviceInfoRepo.save(service);
					}
				}
			} else {
				// Not Found
				wrapper.isAllAlreadyApproved = false;
				wrapper.isAllApprovedBefore = false;
				notFoundDrugsList.add(drugCode);
			}
		});
		List<Serviceinfo> isNotOverridenList = serviceInfoRepo
				.findAllNotOveeridenByrequestId(requestInfo.getRequestId());
		if (isNotOverridenList == null || isNotOverridenList.isEmpty()) {
			// Make isOverriden = 1 when all of the services is Override
			requestInfo.setIsOverriden(EnableDisableStatus.TRUE.value());
			requestInfoRepo.save(requestInfo);
		}
		if (overrideRequest.getDrugList().size() == wrapper.isAllSucess) {
			return msgApprovedSuccess;
		} else {
			return getResponseMsgForNonEmptyList(alreadyApprovedDrugsList, approvedBeforeDrugsList, notFoundDrugsList,
					wrapper.isAllNotFound, wrapper.isAllAlreadyApproved, wrapper.isAllApprovedBefore,
					wrapper.isSuccess);
		}
	}

	private String getResponseMsgForNonEmptyList(List<String> alreadyApprovedDrugsList,
			List<String> approvedBeforeDrugsList, List<String> notFoundDrugsList, boolean isAllNotFound,
			boolean isAllAlreadyApproved, boolean isAllApprovedBefore, boolean isSuccess) {
		if (isAllNotFound) {
			return "Drugcode(s) not Found with this Request Id.";
		} else if (isAllAlreadyApproved && !isSuccess) {
			return "Service code(s) already Approved.";
		} else if (isAllApprovedBefore && !isSuccess) {
			return msgApprovedBefore;
		} else {
			return getResponseMsg(isSuccess, alreadyApprovedDrugsList, notFoundDrugsList, approvedBeforeDrugsList);
		}
	}

	private String getResponseMsg(boolean isSuccess, List<String> alreadyApprovedDrugsList,
			List<String> notFoundDrugsList, List<String> approvedBeforeDrugsList) {
		String responseMsg = "";
		String strAnd = " And ";
		String strAre = " are ";
		String strIs = " is ";
		if (isSuccess)
			responseMsg = msgApprovedSuccess;

		if (!alreadyApprovedDrugsList.isEmpty()) {
			String msg = alreadyApprovedDrugsList.size() == 1 ? strIs : strAre;
			if (responseMsg.isEmpty()) {
				responseMsg = alreadyApprovedDrugsList.toString() + msg + "already Approved";
			} else {
				responseMsg += strAnd + alreadyApprovedDrugsList.toString() + msg + "already Approved";
			}
		}

		if (!notFoundDrugsList.isEmpty()) {
			String msg = notFoundDrugsList.size() == 1 ? strIs : strAre;
			if (responseMsg.isEmpty()) {
				responseMsg = notFoundDrugsList.toString() + msg + "not found";
			} else {
				responseMsg += strAnd + notFoundDrugsList.toString() + msg + "not found";
			}
		}

		if (!approvedBeforeDrugsList.isEmpty()) {
			String msg = approvedBeforeDrugsList.size() == 1 ? strIs : strAre;
			if (responseMsg.isEmpty()) {
				responseMsg = approvedBeforeDrugsList.toString() + msg + "Override before";
			} else {
				responseMsg += strAnd + approvedBeforeDrugsList.toString() + msg + "Override before";
			}
		}
		return responseMsg;
	}

	public String populateEmptyDrugList(CancellationOverrideRequest overrideRequest, RequestInfo requestInfo) {
		var wrapper = new Object() {
			boolean isAllAlreadyApproved = true;
			boolean isAllApprovedBefore = true;
			boolean isSuccess = false;
			int isAllSucess = 0;
		};
		List<Serviceinfo> serviceCodeList = serviceInfoRepo.findByrequestId(overrideRequest.getRequestId());

		serviceCodeList.forEach(serviceinfo -> {
			ServiceDecision sDecision = serviceDecisionRepo.findByRequestIdAndServiceId(
					serviceinfo.getId().getRequestId(), serviceinfo.getId().getServiceId());
			if (serviceinfo.getIsOverriden() == EnableDisableStatus.TRUE.value()) {

				if (sDecision.getStatus().equals(ServiceStatus.APPROVED.name())) {
					wrapper.isAllAlreadyApproved = false;
				} else {
					// some of them is not approved
					wrapper.isSuccess = true;
					wrapper.isAllSucess++;
					wrapper.isAllApprovedBefore = false;
					ServiceDecisionId id = sDecision.getId();
					sDecision.setStatus(ServiceStatus.APPROVED.name());
					sDecision.setId(id);
					serviceDecisionRepo.save(sDecision);
				}
			} else {
				if (sDecision.getStatus().equals(ServiceStatus.APPROVED.name())) {
					wrapper.isAllApprovedBefore = false;
				} else {
					wrapper.isSuccess = true;
					wrapper.isAllSucess++;
					wrapper.isAllAlreadyApproved = false;
					ServiceDecisionId id = sDecision.getId();
					sDecision.setStatus(ServiceStatus.APPROVED.name());
					sDecision.setId(id);
					serviceDecisionRepo.save(sDecision);
				}
			}
		});

		if (serviceCodeList.size() == wrapper.isAllSucess) {
			// For Empty list need to set isOverriden flag =1 for already approved service
			updateDataInServiceInfo(serviceCodeList);
			upadateDataInRequestInfo(requestInfo);
			return msgApprovedSuccess;
		} else {
			return getResponseMsgForEmptyDrugList(requestInfo, wrapper.isAllAlreadyApproved,
					wrapper.isAllApprovedBefore, wrapper.isSuccess, serviceCodeList);
		}
	}

	private String getResponseMsgForEmptyDrugList(RequestInfo requestInfo, boolean isAllAlreadyApproved,
			boolean isAllApprovedBefore, boolean isSuccess, List<Serviceinfo> serviceCodeList) {
		if (isAllAlreadyApproved && !isSuccess) {
			// All already approved
			return "Service code(s) already Approved.";
		} else if (isAllApprovedBefore && !isSuccess) {
			// All approved before
			return msgApprovedBefore;
		} else {
			// Partially approved
			updateDataInServiceInfo(serviceCodeList);
			upadateDataInRequestInfo(requestInfo);
			return msgApprovedSuccess;
		}
	}

	private void upadateDataInRequestInfo(RequestInfo requestInfo) {
		requestInfo.setIsOverriden(EnableDisableStatus.TRUE.value());
		requestInfoRepo.save(requestInfo);
	}

	private void updateDataInServiceInfo(List<Serviceinfo> serviceCodeList) {
		serviceCodeList.forEach(serviceinfo -> {
			serviceinfo.setIsOverriden(EnableDisableStatus.TRUE.value());
			serviceInfoRepo.save(serviceinfo);
		});
	}
}
