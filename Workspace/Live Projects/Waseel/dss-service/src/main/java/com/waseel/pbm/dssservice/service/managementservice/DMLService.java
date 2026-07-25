package com.waseel.pbm.dssservice.service.managementservice;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.pbm.dssservice.enums.EnableDisableStatus;
import com.waseel.pbm.dssservice.enums.RequestStatus;
import com.waseel.pbm.dssservice.enums.RequestType;
import com.waseel.pbm.dssservice.model.DrugList;
import com.waseel.pbm.dssservice.model.DssRequest;
import com.waseel.pbm.dssservice.model.Error;
import com.waseel.pbm.dssservice.model.Result;
import com.waseel.pbm.dssservice.persist.mdss.IcdDiagnosisInfo;
import com.waseel.pbm.dssservice.persist.mdss.IcdDiagnosisInfoId;
import com.waseel.pbm.dssservice.persist.mdss.MemberInfo;
import com.waseel.pbm.dssservice.persist.mdss.PhysicianInfo;
import com.waseel.pbm.dssservice.persist.mdss.PhysicianInfoId;
import com.waseel.pbm.dssservice.persist.mdss.RequestInfo;
import com.waseel.pbm.dssservice.persist.mdss.ServiceDecision;
import com.waseel.pbm.dssservice.persist.mdss.ServiceDecisionId;
import com.waseel.pbm.dssservice.persist.mdss.ServiceInfoId;
import com.waseel.pbm.dssservice.persist.mdss.ServiceRejectionReason;
import com.waseel.pbm.dssservice.persist.mdss.ServiceRejectionReasonId;
import com.waseel.pbm.dssservice.persist.mdss.Serviceinfo;
import com.waseel.pbm.dssservice.repository.mdss.IcdDiagnosisInfoRepository;
import com.waseel.pbm.dssservice.repository.mdss.MemberInfoRepository;
import com.waseel.pbm.dssservice.repository.mdss.PhysicianInfoRepository;
import com.waseel.pbm.dssservice.repository.mdss.RequestInfoRepository;
import com.waseel.pbm.dssservice.repository.mdss.ServiceDecisionRepository;
import com.waseel.pbm.dssservice.repository.mdss.ServiceInfoRepository;
import com.waseel.pbm.dssservice.repository.mdss.ServiceRejectionReasonRepository;

@Service
public class DMLService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DMLService.class);

	@Autowired
	private RequestInfoRepository reqInfoRepo;

	@Autowired
	private PhysicianInfoRepository physicianInfoRepo;

	@Autowired
	private MemberInfoRepository memberInfoRespo;

	@Autowired
	private IcdDiagnosisInfoRepository icd10InfoRepository;

	@Autowired
	private ServiceInfoRepository serviceInfoRepository;

	@Autowired
	private ServiceDecisionRepository serviceDecisionRepository;

	@Autowired
	private ServiceRejectionReasonRepository serviceRejectionReasonRepo;

	private static final String UNDEFINED = "undefined";

	public void saveDssRequest(DssRequest dssRequest, RequestType requestType, List<Result> serviceResult) {
		try {
			RequestInfo reqInfo = reqInfoRepo.save(populateRequestInfo(dssRequest, requestType));
			physicianInfoRepo.save(populatePhyscisionInfo(dssRequest, requestType));
			memberInfoRespo.save(populateMemberInfo(dssRequest, requestType));
			icd10InfoRepository.saveAll(populateIcd10Info(dssRequest));
			Iterable<Serviceinfo> serviceInfo = serviceInfoRepository.saveAll(populateServiceInfo(dssRequest));
			serviceDecisionRepository.saveAll(populateServiceDecision(serviceInfo, serviceResult, reqInfo));
			serviceRejectionReasonRepo.saveAll(populateServiceRejectionReason(serviceInfo, serviceResult));
			LOGGER.info("Request " + reqInfo.getRequestId() + "has been saved successfully ");
		} catch (Exception e) {
			LOGGER.error("Saving Request " + dssRequest.getRequestId() + "has been failed ");
			e.printStackTrace();
		}
	}

	public void updateFollowUpRequest(DssRequest dssRequest, List<Result> serviceResult, RequestType requestType) {
		try {
			int reqInfo = reqInfoRepo.updateRequestInfo(dssRequest.getPayerId(), dssRequest.getPharmacyId(),
					dssRequest.getRequestId());
			if (reqInfo > 0) {
				physicianInfoRepo.updatePhysicianInfo(dssRequest.getPrescriberId(), dssRequest.getRequestId());
				memberInfoRespo.save(populateMemberInfo(dssRequest, requestType));
				if (icd10InfoRepository.updateIcdDiagnosisInfoIsDeletedFlag(dssRequest.getRequestId()) > 0)
					icd10InfoRepository.saveAll(populateIcd10Info(dssRequest));
				if (serviceInfoRepository.updateServiceinfoIsDeletedFlag(dssRequest.getRequestId()) > 0) {
					Iterable<Serviceinfo> serviceInfo = serviceInfoRepository
							.saveAll(populateFollowUpServiceInfo(dssRequest));
					RequestInfo reqInfoEntity = reqInfoRepo.findByRequestId(dssRequest.getRequestId());
					serviceDecisionRepository
							.saveAll(populateServiceDecision(serviceInfo, serviceResult, reqInfoEntity));
					serviceRejectionReasonRepo.saveAll(populateServiceRejectionReason(serviceInfo, serviceResult));
				}
				LOGGER.info("Request {} has been updated successfully.", reqInfo);
			}
		} catch (Exception e) {
			LOGGER.error("Exception occures during update followup request." + e);
			e.printStackTrace();
		}
	}

	private RequestInfo populateRequestInfo(DssRequest request, RequestType requestType) {
		RequestInfo reqInfoEntity = new RequestInfo();
		if (requestType.equals(RequestType.NEW)) { // Save
			if (!StringUtils.isBlank(request.getRequestId())) {
				reqInfoEntity.setRequestId(request.getRequestId());
			}
		} else if (requestType.equals(RequestType.FOLLOWUP)) { // Update
			reqInfoEntity = reqInfoRepo.findByRequestId(request.getRequestId());
		}
		if (!StringUtils.isBlank(request.getPayerId()))
			reqInfoEntity.setPayerId(request.getPayerId());
		if (!StringUtils.isBlank(request.getPharmacyId()))
			reqInfoEntity.setProviderId(request.getPharmacyId());
		return reqInfoEntity;
	}

	private PhysicianInfo populatePhyscisionInfo(DssRequest request, RequestType requestType) {
		PhysicianInfo physcisionInfoEntity = new PhysicianInfo();
		PhysicianInfoId physcisionInfoId = new PhysicianInfoId();
		if (requestType.equals(RequestType.NEW)) { // Save
			if (request.getRequestId() != null && !request.getRequestId().isEmpty()) {
				physcisionInfoId.setRequestId(request.getRequestId());
			}
		} else if (requestType.equals(RequestType.FOLLOWUP)) { // Update
			physcisionInfoEntity = physicianInfoRepo.findByrequestId(request.getRequestId());
			physcisionInfoId = physcisionInfoEntity.getId();
		}
		if (!StringUtils.isBlank(request.getPrescriberId()))
			physcisionInfoId.setPhysicianId(request.getPrescriberId());
		if (physcisionInfoId != null)
			physcisionInfoEntity.setId(physcisionInfoId);
		return physcisionInfoEntity;
	}

	private MemberInfo populateMemberInfo(DssRequest request, RequestType requestType) {
		MemberInfo memberInfoEntity = new MemberInfo();
		if (requestType.equals(RequestType.NEW)) { // Save
			if (request.getRequestId() != null && !request.getRequestId().isEmpty()) {
				memberInfoEntity.setRequestId(request.getRequestId());
			}
		} else if (requestType.equals(RequestType.FOLLOWUP)) { // Update
			memberInfoEntity = memberInfoRespo.findByrequestId(request.getRequestId());
		}
		if (!StringUtils.isBlank(request.getMemberId()))
			memberInfoEntity.setMemberId(request.getMemberId());
		if (!StringUtils.isBlank(request.getMemberGender()))
			memberInfoEntity.setMemberGender(request.getMemberGender());
		if (request.getMemberWeight() != null)
			memberInfoEntity.setMemberWeight(request.getMemberWeight().toString());
		if (!StringUtils.isBlank(request.getDateOfBirth()))
			memberInfoEntity.setDateOfBirth(request.getDateOfBirth());
		return memberInfoEntity;
	}

	private List<IcdDiagnosisInfo> populateIcd10Info(DssRequest request) {
		List<IcdDiagnosisInfo> icd10InfoEntities = new ArrayList<>();
		if (request.getIcdCodes() != null && !request.getIcdCodes().isEmpty()) {
			request.getIcdCodes().forEach(icdCode -> {
				IcdDiagnosisInfo icd10infoEntity = new IcdDiagnosisInfo();
				IcdDiagnosisInfoId icd10infoId = new IcdDiagnosisInfoId();
				icd10infoId.setRequestId(request.getRequestId());
				icd10infoId.setIcdDiagnosisCode(icdCode);
				icd10infoEntity.setId(icd10infoId);
				icd10InfoEntities.add(icd10infoEntity);
			});
		}
		return icd10InfoEntities;
	}

	private List<Serviceinfo> populateFollowUpServiceInfo(DssRequest request) {
		List<Serviceinfo> serviceInfoEntities = new ArrayList<>();
		Serviceinfo serviceInfoEntity = null;
		ServiceInfoId serviceInfoId;
		List<DrugList> drugsList = request.getDrugList();
		List<Serviceinfo> serviceLst = serviceInfoRepository.findServiceIdOrderByDESC(request.getRequestId());
		long serviceId = serviceLst != null && !serviceLst.isEmpty() ? serviceLst.get(0).getId().getServiceId() : 0;
		List<DrugList> drugsMatched = getMatchedDrugs(serviceLst, drugsList);
		List<DrugList> drugsNotMatched = getNoneMatchedDrugs(serviceLst, drugsList);

		if (drugsMatched != null && !drugsMatched.isEmpty()) {
			// Need to update old service code
			for (DrugList drug : drugsMatched) {

				if (drug.getNdcDrugCode() != null && !drug.getNdcDrugCode().isEmpty()
						&& drug.getScientificCode() != null && !drug.getScientificCode().isEmpty()) {
					serviceInfoEntity = serviceInfoRepository.findByRequestIdANDServiceCodeANDScientificCode(
							request.getRequestId(), drug.getNdcDrugCode(), drug.getScientificCode());
				} else if (drug.getNdcDrugCode() != null && !drug.getNdcDrugCode().isEmpty()) {
					serviceInfoEntity = serviceInfoRepository.findByRequestIdANDServiceCode(request.getRequestId(),
							drug.getNdcDrugCode());
				} else if (drug.getScientificCode() != null && !drug.getScientificCode().isEmpty()) {
					serviceInfoEntity = serviceInfoRepository.findByRequestIdANDScientificCode(request.getRequestId(),
							drug.getScientificCode());
				}
				serviceInfoId = serviceInfoEntity.getId();
				serviceInfoId.setServiceId(serviceInfoEntity.getId().getServiceId());
				serviceInfoEntities.add(setDataInServiceInfo(serviceInfoEntity, serviceInfoId, request, drug));
			}
		}
		if (drugsNotMatched != null && !drugsNotMatched.isEmpty()) {
			// Need to add new service code
			for (DrugList drug : drugsNotMatched) {
				serviceInfoEntity = new Serviceinfo();
				serviceInfoId = new ServiceInfoId();
				serviceInfoId.setServiceId(++serviceId);
				serviceInfoEntities.add(setDataInServiceInfo(serviceInfoEntity, serviceInfoId, request, drug));
			}
		}
		return serviceInfoEntities;
	}

	private Serviceinfo setDataInServiceInfo(Serviceinfo serviceInfoEntity, ServiceInfoId serviceInfoId,
			DssRequest request, DrugList drug) {
		serviceInfoId.setRequestId(request.getRequestId());
		serviceInfoEntity.setId(serviceInfoId);
		if (!StringUtils.isBlank(drug.getNdcDrugCode())) {
			serviceInfoEntity.setServiceCode(drug.getNdcDrugCode());
		} else {
			serviceInfoEntity.setServiceCode(UNDEFINED);
		}
		if (drug.getDispensedQuantity() != null)
			serviceInfoEntity.setServiceQuantity(drug.getDispensedQuantity());
		if (drug.getAmount() != null)
			serviceInfoEntity.setServiceAmount(Double.parseDouble(drug.getAmount().toString()));
		if (!StringUtils.isBlank(drug.getDaysOfSupply()))
			serviceInfoEntity.setDaysOfSupply(Double.parseDouble(drug.getDaysOfSupply()));
		if (!StringUtils.isBlank(request.getDateOfService()))
			serviceInfoEntity.setServiceDate(convertStringToDate(request.getDateOfService()));
		serviceInfoEntity.setIsDeletedFromProvider(EnableDisableStatus.FALSE.value());
		return serviceInfoEntity;
	}

	private List<Serviceinfo> populateServiceInfo(DssRequest request) {
		List<Serviceinfo> serviceInfoEntities = new ArrayList<>();

		var wrapper = new Object() {
			long serviceId = 0;
		};

		if (request.getDrugList() != null && !request.getDrugList().isEmpty()) {
			request.getDrugList().forEach(service -> {
				Serviceinfo serviceInfoEntity = new Serviceinfo();
				ServiceInfoId serviceInfoId = new ServiceInfoId();
				serviceInfoId.setRequestId(request.getRequestId());
				serviceInfoId.setServiceId(wrapper.serviceId++);
				serviceInfoEntity.setId(serviceInfoId);
				if (!StringUtils.isBlank(service.getNdcDrugCode())) {
					serviceInfoEntity.setServiceCode(service.getNdcDrugCode());
				}
				if (!StringUtils.isBlank(service.getScientificCode())) {
					serviceInfoEntity.setScientificCode(service.getScientificCode());
				}
				if (StringUtils.isBlank(serviceInfoEntity.getServiceCode())
						&& StringUtils.isBlank(serviceInfoEntity.getScientificCode())) {
					serviceInfoEntity.setServiceCode(UNDEFINED);
				}
				if (service.getDispensedQuantity() != null)
					serviceInfoEntity.setServiceQuantity(service.getDispensedQuantity());
				if (service.getAmount() != null)
					serviceInfoEntity.setServiceAmount(Double.parseDouble(service.getAmount().toString()));
				if (!StringUtils.isBlank(service.getDaysOfSupply())) {
					serviceInfoEntity.setDaysOfSupply(Double.parseDouble(service.getDaysOfSupply()));
				}
				if (!StringUtils.isBlank(request.getDateOfService()))
					serviceInfoEntity.setServiceDate(convertStringToDate(request.getDateOfService()));
				serviceInfoEntities.add(serviceInfoEntity);
			});
		}
		return serviceInfoEntities;
	}

	private List<ServiceDecision> populateServiceDecision(Iterable<Serviceinfo> serviceInfo,
			List<Result> servicesResultList, RequestInfo requestInfo) {
		List<ServiceDecision> serList = new ArrayList<>();

		serviceInfo.forEach(info -> {
			ServiceDecision service = new ServiceDecision();
			ServiceDecisionId serId = new ServiceDecisionId();
			for (Result serviceResult : servicesResultList) {
				if (((serviceResult.getNdcDrugCode() == null || serviceResult.getNdcDrugCode().isEmpty())&& info.getServiceCode() != null
						&& info.getServiceCode().equalsIgnoreCase(UNDEFINED))
						|| (serviceResult.getScientificCode() != null
								&& serviceResult.getScientificCode().equals(info.getScientificCode()))
						|| (serviceResult.getNdcDrugCode() != null
								&& serviceResult.getNdcDrugCode().equals(info.getServiceCode()))) {
					serId.setRequestId(info.getId().getRequestId());
					serId.setServiceId(info.getId().getServiceId());
					service.setId(serId);
					service.setStatus(serviceResult.getStatus());
					service.setServiceInfo(info);
					serList.add(service);
					break;
				}
			}
		});

		// Set main RequestStatus in RequestInfo
		setRequestStatus(requestInfo, serList);
		return serList;
	}

	private void setRequestStatus(RequestInfo requestInfo, List<ServiceDecision> serList) {
		List<String> serviceStatusList = serList.stream().map(ServiceDecision::getStatus).collect(Collectors.toList());
		if (serviceStatusList.stream().distinct().count() == 1)
			requestInfo.setRequestStatus(serviceStatusList.get(0));
		else
			requestInfo.setRequestStatus(RequestStatus.PARTIAL_APPROVED.value());
		reqInfoRepo.save(requestInfo);
	}

	private List<ServiceRejectionReason> populateServiceRejectionReason(Iterable<Serviceinfo> serviceInfo,
			List<Result> servicesResultList) {
		List<ServiceRejectionReason> lst = new ArrayList<>();
		serviceInfo.forEach(info -> {
			for (Result serviceResult : servicesResultList) {
				if (((serviceResult.getNdcDrugCode() == null ||serviceResult.getNdcDrugCode().isEmpty()) && info.getServiceCode() != null
						&& info.getServiceCode().equalsIgnoreCase(UNDEFINED))
						|| (serviceResult.getScientificCode() != null
								&& serviceResult.getScientificCode().equals(info.getScientificCode()))
						|| (serviceResult.getNdcDrugCode() != null
								&& serviceResult.getNdcDrugCode().equals(info.getServiceCode()))) {
					if (serviceResult.getErrors() != null && !serviceResult.getErrors().isEmpty()) {
						for (Error serviceRejection : serviceResult.getErrors()) {
							ServiceRejectionReasonId serId = new ServiceRejectionReasonId();
							ServiceRejectionReason serReason = new ServiceRejectionReason();
							serId.setRejectionCode(serviceRejection.getCode());
							serId.setRejectionReason(serviceRejection.getDescription());
							serId.setRequestId(info.getId().getRequestId());
							serId.setServiceId(info.getId().getServiceId());
							serReason.setId(serId);
							lst.add(serReason);
						}
					} else {
						break;
					}
				}
			}
		});
		return lst;
	}

	private Timestamp convertStringToDate(String dateOfService) {
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = format.parse(dateOfService);
			return new Timestamp(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	private List<DrugList> getMatchedDrugs(List<Serviceinfo> serviceList, List<DrugList> dssDrugList) {
		/*
		 * drugsList.stream() .filter(drugCode -> serviceLst.stream() .anyMatch(service
		 * -> service.getServiceCode().equals(drugCode.getNdcDrugCode())||service.
		 * getScientificCode().equals(drugCode.getScientificCode()) ))
		 * .collect(Collectors.toList());
		 */

		List<DrugList> matchedDrugs = new ArrayList<>();
		for (DrugList dssDrug : dssDrugList) {
			for (Serviceinfo serviceInfo : serviceList) {

				if (dssDrug.getScientificCode() != null && !dssDrug.getScientificCode().isBlank()
						&& serviceInfo.getScientificCode() != null && !serviceInfo.getScientificCode().isBlank()
						&& dssDrug.getScientificCode().trim().equals(serviceInfo.getScientificCode().trim())
						&& dssDrug.getNdcDrugCode() != null && !dssDrug.getNdcDrugCode().isBlank()
						&& serviceInfo.getServiceCode() != null && !serviceInfo.getServiceCode().isBlank()
						&& dssDrug.getNdcDrugCode().trim().equals(serviceInfo.getServiceCode().trim())) {
					matchedDrugs.add(dssDrug);
					break;
				} else if (dssDrug.getScientificCode() != null && !dssDrug.getScientificCode().isBlank()
						&& serviceInfo.getScientificCode() != null && !serviceInfo.getScientificCode().isBlank()
						&& dssDrug.getScientificCode().trim().equals(serviceInfo.getScientificCode().trim())) {
					matchedDrugs.add(dssDrug);
					break;
				} else if (dssDrug.getNdcDrugCode() != null && !dssDrug.getNdcDrugCode().isBlank()
						&& serviceInfo.getServiceCode() != null && !serviceInfo.getServiceCode().isBlank()
						&& dssDrug.getNdcDrugCode().trim().equals(serviceInfo.getServiceCode().trim())) {
					matchedDrugs.add(dssDrug);
					break;
				}

			}
		}
		return dssDrugList.stream().filter(dssDrug -> matchedDrugs.contains(dssDrug)).collect(Collectors.toList());
	}

	private List<DrugList> getNoneMatchedDrugs(List<Serviceinfo> serviceList, List<DrugList> dssDrugList) {
		/*
		 * drugsList.stream() .filter(drugCode -> serviceLst.stream() .noneMatch(service
		 * -> service.getServiceCode().equals(drugCode.getNdcDrugCode())))
		 * .collect(Collectors.toList());
		 */

		List<DrugList> matchedDrugs = new ArrayList<>();
		for (DrugList dssDrug : dssDrugList) {
			for (Serviceinfo serviceInfo : serviceList) {
				if (dssDrug.getScientificCode() != null && !dssDrug.getScientificCode().isBlank()
						&& serviceInfo.getScientificCode() != null && !serviceInfo.getScientificCode().isBlank()
						&& dssDrug.getScientificCode().trim().equals(serviceInfo.getScientificCode().trim())
						&& dssDrug.getNdcDrugCode() != null && !dssDrug.getNdcDrugCode().isBlank()
						&& serviceInfo.getServiceCode() != null && !serviceInfo.getServiceCode().isBlank()
						&& dssDrug.getNdcDrugCode().trim().equals(serviceInfo.getServiceCode().trim())) {
					matchedDrugs.add(dssDrug);
					break;
				} else if (dssDrug.getScientificCode() != null && !dssDrug.getScientificCode().isBlank()
						&& serviceInfo.getScientificCode() != null && !serviceInfo.getScientificCode().isBlank()
						&& dssDrug.getScientificCode().trim().equals(serviceInfo.getScientificCode().trim())) {
					matchedDrugs.add(dssDrug);
					break;
				} else if (dssDrug.getNdcDrugCode() != null && !dssDrug.getNdcDrugCode().isBlank()
						&& serviceInfo.getServiceCode() != null && !serviceInfo.getServiceCode().isBlank()
						&& dssDrug.getNdcDrugCode().trim().equals(serviceInfo.getServiceCode().trim())) {
					matchedDrugs.add(dssDrug);
					break;
				}

			}
		}

		return dssDrugList.stream().filter(dssDrug -> !matchedDrugs.contains(dssDrug)).collect(Collectors.toList());
	}

}