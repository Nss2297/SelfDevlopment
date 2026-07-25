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
public class CancellationDMLService {

	private String msgCancelledBefore = "This request is already Cancelled before.";
	private String msgCancelledSuccess = "Requested ID Transaction Cancelled Successfully.";

	@Autowired
	private RequestInfoRepository requestInfoRepo;

	@Autowired
	private ServiceInfoRepository serviceInfoRepo;

	@Autowired
	private ServiceDecisionRepository serviceDecisionRepo;

	public String populateNotEmptyDrugList(CancellationOverrideRequest cancelRequest, RequestInfo requestInfo) {
		List<String> alreadyRejectedDrugsList = new ArrayList<>();
		List<String> rejectedBeforeDrugsList = new ArrayList<>();
		List<String> notFoundDrugsList = new ArrayList<>();

		var wrapper = new Object() {
			boolean isAllNotFound = true;
			boolean isAllAlreadyRejected = true;
			boolean isAllRejectedBefore = true;
			boolean isSuccess = false;
			int isAllSucess = 0;
		};

		cancelRequest.getDrugList().forEach(drugCode -> {

			Serviceinfo service = serviceInfoRepo.findNoDeletedByRequestIdANDServiceCodeORScientificCode(cancelRequest.getRequestId(),
					drugCode);
			if (service != null) {
				wrapper.isAllNotFound = false;
				// Found servicecode
				ServiceDecision serviceDecision = serviceDecisionRepo
						.findByRequestIdAndServiceId(service.getId().getRequestId(), service.getId().getServiceId());

				if (service.getIsCancelled() == EnableDisableStatus.TRUE.value()) {
					if (serviceDecision.getStatus().equals(ServiceStatus.REJECTED.name())) {
						wrapper.isAllAlreadyRejected = false;
						rejectedBeforeDrugsList.add(service.getServiceCode()!=null && !service.getServiceCode().isEmpty()?service.getServiceCode():service.getScientificCode());
					} else {
						wrapper.isAllRejectedBefore = false;
						wrapper.isSuccess = true;
						wrapper.isAllSucess++;
						// some of drugcodes are not rejected with iscancelled = 1
						ServiceDecisionId id = serviceDecision.getId();
						serviceDecision.setStatus(ServiceStatus.REJECTED.name());
						serviceDecision.setId(id);
						serviceDecisionRepo.save(serviceDecision);
					}
				} else {
					if (serviceDecision.getStatus().equals(ServiceStatus.REJECTED.name())) {
						wrapper.isAllRejectedBefore = false;
						alreadyRejectedDrugsList.add(service.getServiceCode()!=null && service.getServiceCode().isEmpty()?service.getServiceCode():service.getScientificCode());
					} else {
						wrapper.isAllSucess++;
						wrapper.isSuccess = true;
						wrapper.isAllAlreadyRejected = false;
						service.setIsCancelled(EnableDisableStatus.TRUE.value());
						ServiceDecisionId id = serviceDecision.getId();
						serviceDecision.setStatus(ServiceStatus.REJECTED.name());
						serviceDecision.setId(id);
						serviceDecisionRepo.save(serviceDecision);
						serviceInfoRepo.save(service);
					}
				}
			} else {
				// Not Found
				wrapper.isAllAlreadyRejected = false;
				wrapper.isAllRejectedBefore = false;
				notFoundDrugsList.add(drugCode);
			}});
		

		List<Serviceinfo> isNotCancelledList = serviceInfoRepo
				.findAllNotCancelledByrequestId(requestInfo.getRequestId());
		if (isNotCancelledList == null || isNotCancelledList.isEmpty()) {
			// Make isCancelled = 1 when all of the services is cancelled
			requestInfo.setIsCancelled(EnableDisableStatus.TRUE.value());
			requestInfoRepo.save(requestInfo);
		}

		if (cancelRequest.getDrugList().size() == wrapper.isAllSucess) {
			return msgCancelledSuccess;
		} else {
			return getResponseMsgForNonEmptyDrugList(alreadyRejectedDrugsList, rejectedBeforeDrugsList,
					notFoundDrugsList, wrapper.isAllNotFound, wrapper.isAllAlreadyRejected, wrapper.isAllRejectedBefore,
					wrapper.isSuccess);
		}
	}

	private String getResponseMsgForNonEmptyDrugList(List<String> alreadyRejectedDrugsList,
			List<String> rejectedBeforeDrugsList, List<String> notFoundDrugsList, boolean isAllNotFound,
			boolean isAllAlreadyRejected, boolean isAllRejectedBefore, boolean isSuccess) {
		if (isAllNotFound) {
			return "Drugcode(s) not Found with this Request Id.";
		} else if (isAllAlreadyRejected && !isSuccess) {
			return "Service code(s) already Rejected.";
		} else if (isAllRejectedBefore && !isSuccess) {
			return msgCancelledBefore;
		} else {
			return getResponseMsg(isSuccess, alreadyRejectedDrugsList, notFoundDrugsList, rejectedBeforeDrugsList);
		}
	}

	private String getResponseMsg(boolean isSuccess, List<String> alreadyRejectedDrugsList,
			List<String> notFoundDrugsList, List<String> rejectedBeforeDrugsList) {
		String responseMsg = "";
		String strAnd = " And ";
		String strAre = " are ";
		String strIs = " is ";
		if (isSuccess)
			responseMsg = msgCancelledSuccess;

		if (!alreadyRejectedDrugsList.isEmpty()) {
			String msg = alreadyRejectedDrugsList.size() == 1 ? strIs : strAre;
			if (responseMsg.isEmpty()) {
				responseMsg = alreadyRejectedDrugsList.toString() + msg + "already Rejected";
			} else {
				responseMsg += strAnd + alreadyRejectedDrugsList.toString() + msg + "already Rejected";
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

		if (!rejectedBeforeDrugsList.isEmpty()) {
			String msg = rejectedBeforeDrugsList.size() == 1 ? strIs : strAre;
			if (responseMsg.isEmpty()) {
				responseMsg = rejectedBeforeDrugsList.toString() + msg + "Cancelled before";
			} else {
				responseMsg += strAnd + rejectedBeforeDrugsList.toString() + msg + "Cancelled before";
			}
		}
		return responseMsg;
	}

	public String populateEmptyDrugList(CancellationOverrideRequest cancelRequest, RequestInfo requestInfo) {
		List<Serviceinfo> serviceCodeList = serviceInfoRepo.findByrequestId(cancelRequest.getRequestId());
		var wrapper = new Object() {
			boolean isAllAlreadyRejected = true;
			boolean isAllRejectedBefore = true;
			boolean isSuccess = false;
			int isAllSucess = 0;
		};
		serviceCodeList.forEach(serviceinfo -> {
			ServiceDecision sDecision = serviceDecisionRepo.findByRequestIdAndServiceId(
					serviceinfo.getId().getRequestId(), serviceinfo.getId().getServiceId());

			if (serviceinfo.getIsCancelled() == EnableDisableStatus.TRUE.value()) {

				if (sDecision != null && sDecision.getStatus().equals(ServiceStatus.REJECTED.name())) {
					wrapper.isAllAlreadyRejected = false;
				} else {
					// some of them is not REJECTED
					wrapper.isSuccess = true;
					wrapper.isAllSucess++;
					wrapper.isAllRejectedBefore = false;
					ServiceDecisionId id = sDecision.getId();
					sDecision.setStatus(ServiceStatus.REJECTED.name());
					sDecision.setId(id);
					serviceDecisionRepo.save(sDecision);
				}
			} else {
				if (sDecision != null && sDecision.getStatus().equals(ServiceStatus.REJECTED.name())) {
					wrapper.isAllRejectedBefore = false;
				} else {
					wrapper.isSuccess = true;
					wrapper.isAllSucess++;
					wrapper.isAllAlreadyRejected = false;
					ServiceDecisionId id = sDecision.getId();
					sDecision.setStatus(ServiceStatus.REJECTED.name());
					sDecision.setId(id);
					serviceDecisionRepo.save(sDecision);
				}
			}
		});
		if (serviceCodeList.size() == wrapper.isAllSucess) {
			updateDataInServiceInfo(serviceCodeList);
			updateDatainRequestInfo(requestInfo);
			return msgCancelledSuccess;
		} else {
			return getResponseMsgForEmptyDrugList(requestInfo, wrapper.isAllAlreadyRejected,
					wrapper.isAllRejectedBefore, wrapper.isSuccess, serviceCodeList);
		}
	}

	private String getResponseMsgForEmptyDrugList(RequestInfo requestInfo, boolean isAllAlreadyRejected,
			boolean isAllRejectedBefore, boolean isSuccess, List<Serviceinfo> serviceCodeList) {
		if (isAllAlreadyRejected && !isSuccess) {
			// All already REJECTED
			return "Service code(s) already Rejected.";
		} else if (isAllRejectedBefore && !isSuccess) {
			// All REJECTED before
			return msgCancelledBefore;
		} else {
			// Partially approved
			updateDataInServiceInfo(serviceCodeList);
			updateDatainRequestInfo(requestInfo);
			return msgCancelledSuccess;
		}
	}

	private void updateDatainRequestInfo(RequestInfo requestInfo) {
		requestInfo.setIsCancelled(EnableDisableStatus.TRUE.value());
		requestInfoRepo.save(requestInfo);
	}

	private void updateDataInServiceInfo(List<Serviceinfo> serviceCodeList) {
		serviceCodeList.forEach(serviceinfo -> {
			serviceinfo.setIsCancelled(EnableDisableStatus.TRUE.value());
			serviceInfoRepo.save(serviceinfo);
		});
	}
}
