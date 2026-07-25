package com.waseel.prescription.service.prescriptions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.prescription.expections.PrescriptionException;
import com.waseel.prescription.model.dispense.PrescriptionDispenseResponseModel;
import com.waseel.prescription.model.dispense.PrescriptionDrug;
import com.waseel.prescription.model.dispense.SuggestedDrug;
import com.waseel.prescription.model.dispense.SuggestedDrugsModel;
import com.waseel.prescription.model.enums.PolicyConsumptionStatus;
import com.waseel.prescription.model.enums.ServiceStatus;
import com.waseel.prescription.model.exclusion.DrugExclusionResponseModel;
import com.waseel.prescription.model.exclusion.DrugList;
import com.waseel.prescription.model.formulary.DrugFormularyResponseModel;
import com.waseel.prescription.model.policyconsumption.DispensibleDrugsRequestModel;
import com.waseel.prescription.model.policyconsumption.PolicyResponseModel;
import com.waseel.prescription.persist.mdss.DrugService;
import com.waseel.prescription.persist.prescriptionservice.Physician;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionApprovalDrug;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.ServiceInfo;
import com.waseel.prescription.repository.mdss.DrugServiceMetaDataRepository;
import com.waseel.prescription.repository.mdss.DrugServiceRepository;
import com.waseel.prescription.repository.prescriptionservice.PhysicianRepository;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionApprovalDrugRepository;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionRequestRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceInfoRepository;
import com.waseel.prescription.service.clienthandler.PolicyConsumptionRestHandler;
import com.waseel.prescription.service.clienthandler.RestHandler;

@Service
public class DrugSuggestionsService {
	private final Logger log = LoggerFactory.getLogger(DrugSuggestionsService.class);

	@Autowired
	private PrescriptionRequestRepository prescriptionRequestRepository;
	@Autowired
	private DrugServiceMetaDataRepository drugServiceMetaDataRepository;
	@Autowired
	private DrugServiceRepository drugServiceRepository;
	@Autowired
	private ServiceInfoRepository serviceInfoRepository;
	@Autowired
	RestHandler restHandler;
	@Autowired
	PhysicianRepository physicianRepository;
	@Autowired
	PrescriptionApprovalDrugRepository prescriptionApprovedDrugRepository;

	@Autowired
	private PolicyConsumptionRestHandler policyConsumptionRestHandler;

	@Autowired
	private FetchBenefitCodeService fetchBenefitCodeService;

	// pass isThirdPartyRestApi for default call process pass false only when it
	// invoke from dispensable API which is use for Third part call
	public SuggestedDrugsModel getSuggestedDrugs(String ePrescriptionReferenceNumber, String payerId,
			boolean isThirdPartyRestApi) throws PrescriptionException {
		SuggestedDrugsModel suggestedDrugsModel = new SuggestedDrugsModel();
		List<PrescriptionDrug> prescriptionDrugList = new ArrayList<>();
		Optional<PrescriptionRequest> prescriptionRequestOpt = prescriptionRequestRepository
				.findByePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
		if (!prescriptionRequestOpt.isPresent()) {
			log.info("No prescription was found.");
			throw new PrescriptionException(new PrescriptionDispenseResponseModel("Invalid",
					"Invalid ePrescriptionReferenceNo", ePrescriptionReferenceNumber));
		}
		PrescriptionRequest prescriptionRequest = prescriptionRequestOpt.get();
		String requestId = prescriptionRequest.getRequestId();
		String providerId = prescriptionRequest.getProviderId();
		String idNumber = prescriptionRequest.getMemberInfo().getIdNumber().toString();
		Optional<List<ServiceInfo>> scientificDrugList = Optional.empty();
		if (isThirdPartyRestApi) {
			scientificDrugList = serviceInfoRepository
					.findDrugsByRequestIdAndIsDeletedAndScientificCodeNotNull(requestId);
		} else {
			scientificDrugList = serviceInfoRepository
					.findDrugsByRequestIdAndIsDeletedAndScientificCodeNotNullAndStatus(requestId,
							ServiceStatus.APPROVED.name());
		}
		Optional<List<ServiceInfo>> brandDrugList = serviceInfoRepository
				.findDrugsByRequestIdAndIsDeletedAndScientificCodeNullAndStatus(requestId,
						ServiceStatus.APPROVED.name());
		Optional<Long> drugListIdOpt = drugServiceMetaDataRepository.getActiveDrugServiceList(new Date());
		if (drugListIdOpt.isPresent()) {
			List<String> dispensibleDrugs = new ArrayList<>();
			Long activeDrugListId = drugListIdOpt.get();
			if (brandDrugList.isPresent() && !brandDrugList.get().isEmpty()) {
				List<String> brandDrugsWithScientificCode = brandDrugList.get().stream().map(ServiceInfo::getDrugCode)
						.collect(Collectors.toList());
				Optional<List<DrugService>> brandDrugService = drugServiceRepository
						.findByDrugListIdAndOtherCodesValueIn(activeDrugListId, brandDrugsWithScientificCode);
				if (brandDrugService.isPresent() && !brandDrugService.get().isEmpty()) {
					addBrandDrugsToPrescriptionDrugList(prescriptionDrugList, brandDrugService.get(),
							brandDrugList.get());
					// set drugs for differentiating brands/generic
					dispensibleDrugs.addAll(brandDrugsWithScientificCode);
				} else {
					throw new PrescriptionException(new PrescriptionDispenseResponseModel("Invalid",
							"Couldn't fetch brand drugs details.", ePrescriptionReferenceNumber));
				}
			}
			if (scientificDrugList.isPresent() && !scientificDrugList.get().isEmpty()) {
				List<String> drugsWithScientificCode = scientificDrugList.get().stream()
						.map(ServiceInfo::getScientificCode).collect(Collectors.toList());
				Optional<List<DrugService>> drugService = drugServiceRepository
						.findByDrugListIdAndScientificCodeIn(activeDrugListId, drugsWithScientificCode);
				if (drugService.isPresent() && !drugService.get().isEmpty()) {
					List<String> drugCodeList = drugService.get().stream().map(DrugService::getOtherCodesValue)
							.collect(Collectors.toList());
					Optional<Physician> physician = physicianRepository.findByRequestId(requestId);
					if (physician.isPresent()) {
						DrugExclusionResponseModel drugExclusionResponseModel = restHandler
								.sendPrescriptionRequestToDrugExclusion(drugCodeList, requestId,
										physician.get().getPhysicianLicenseNumber(),
										physician.get().getPhysicianSpeciality(), payerId, providerId);
						List<DrugFormularyResponseModel> drugFormularyResponseModel = restHandler
								.sendPrescriptionRequestToDrugFormularyService(payerId, idNumber, drugCodeList,
										requestId);
						Optional<List<PrescriptionApprovalDrug>> approvedDrugs = prescriptionApprovedDrugRepository
								.findByEprescriptionReferenceNumber(ePrescriptionReferenceNumber);
						mapPrescriptionDrug(drugService.get(), drugExclusionResponseModel, drugFormularyResponseModel,
								prescriptionDrugList, approvedDrugs, scientificDrugList.get());
						// set drugs for differentiating brands/generic
						dispensibleDrugs.addAll(drugCodeList);
					} else {
						throw new PrescriptionException(new PrescriptionDispenseResponseModel("Invalid",
								"Couldn't fetch physician's details.", ePrescriptionReferenceNumber));
					}
				} else {
					throw new PrescriptionException(new PrescriptionDispenseResponseModel("Invalid",
							"Couldn't fetch suggested drug details.", ePrescriptionReferenceNumber));
				}
			}
			suggestedDrugsModel.setPrescriptionDrugs(prescriptionDrugList);
			managePrescriptionPolicyDetails(suggestedDrugsModel, idNumber, requestId, prescriptionRequest.getCaseType(),
					payerId, providerId, ePrescriptionReferenceNumber, dispensibleDrugs);
			return suggestedDrugsModel;
		} else {
			throw new PrescriptionException(new PrescriptionDispenseResponseModel("Invalid",
					"Couldn't fetch active drug list details.", ePrescriptionReferenceNumber));
		}
	}

	private void addBrandDrugsToPrescriptionDrugList(List<PrescriptionDrug> prescriptionDrugList,
			List<DrugService> brandDrugsList, List<ServiceInfo> serviceInfoList) {
		Map<String, ServiceInfo> drugCodeToServiceInfoMap = serviceInfoList.stream()
				.collect(Collectors.toMap(ServiceInfo::getDrugCode, serviceInfo -> serviceInfo));
		List<PrescriptionDrug> mappedDrugList = brandDrugsList.stream().map(brandDrug -> {
			PrescriptionDrug prescriptionDrug = new PrescriptionDrug();
			prescriptionDrug.setScientificCode(brandDrug.getScientificCode());
			prescriptionDrug.setScientificName(brandDrug.getIngredients());
			prescriptionDrug.setIsBrand(true);
			ServiceInfo serviceInfo = drugCodeToServiceInfoMap.get(brandDrug.getOtherCodesValue());
			String totalPrice = null;
			BigDecimal quantity = null;
			if (serviceInfo != null) {
				quantity = serviceInfo.getQuantity();
				BigDecimal unitPrice = new BigDecimal(brandDrug.getPrice());
				totalPrice = quantity.multiply(unitPrice).toString();
				prescriptionDrug.setQuantity(quantity.intValue());
			}
			SuggestedDrug suggestedDrug = new SuggestedDrug(brandDrug.getPrice(), brandDrug.getOtherCodesValue(),
					brandDrug.getDisplay(), brandDrug.getDosageForm(), brandDrug.getStrengthUnit(),
					brandDrug.getStrength(), brandDrug.getRoaSuggested(), totalPrice);
			List<SuggestedDrug> suggestedDrugList = new ArrayList<>();
			suggestedDrugList.add(suggestedDrug);
			prescriptionDrug.setSuggestedDrugs(suggestedDrugList);
			return prescriptionDrug;
		}).collect(Collectors.toList());
		prescriptionDrugList.addAll(mappedDrugList);
	}

	private void mapPrescriptionDrug(List<DrugService> drugService,
			DrugExclusionResponseModel drugExclusionResponseModel,
			List<DrugFormularyResponseModel> drugFormularyResponseModel, List<PrescriptionDrug> prescriptionDrugList,
			Optional<List<PrescriptionApprovalDrug>> approvedDrugs, List<ServiceInfo> serviceInfoList) {
		Map<String, ServiceInfo> drugCodeToServiceInfoMap = serviceInfoList.stream()
				.collect(Collectors.toMap(ServiceInfo::getScientificCode, serviceInfo -> serviceInfo));
		Map<String, List<DrugService>> groupedDrugs = drugService.stream()
				.collect(Collectors.groupingBy(DrugService::getScientificCode));
		Set<String> drugList = fetchExcludedDrugs(drugExclusionResponseModel);
		Set<String> approvedDrugList = approvedDrugs.isPresent()
				? approvedDrugs.get().stream()
						.filter(suggestedDrug -> suggestedDrug.getStatus().equalsIgnoreCase("APPROVED"))
						.map(PrescriptionApprovalDrug::getSuggestedDrugCode).collect(Collectors.toSet())
				: new HashSet<>();
		Set<String> formularyDrugList = fetchFormularyDrugs(drugFormularyResponseModel);
		for (Map.Entry<String, List<DrugService>> entry : groupedDrugs.entrySet()) {
			PrescriptionDrug prescriptionDrug = new PrescriptionDrug();
			String scientificCode = entry.getKey();
			String scientificName = entry.getValue().get(0).getIngredients();
			List<DrugService> drugsWithScientificCode = entry.getValue();
			prescriptionDrug.setScientificCode(scientificCode);
			prescriptionDrug.setScientificName(scientificName);
			List<SuggestedDrug> suggestedDrugs = drugsWithScientificCode.stream().map(drug -> {
				ServiceInfo serviceInfo = drugCodeToServiceInfoMap.get(drug.getScientificCode());
				String totalPrice = null;
				BigDecimal quantity = null;
				if (serviceInfo != null) {
					quantity = serviceInfo.getQuantity();
					BigDecimal unitPrice = new BigDecimal(drug.getPrice());
					totalPrice = quantity.multiply(unitPrice).toString();
					prescriptionDrug.setQuantity(quantity.intValue());
				}
				return new SuggestedDrug(drug.getPrice(), drug.getOtherCodesValue(), drug.getDisplay(),
						drug.getDosageForm(), drug.getStrengthUnit(), drug.getStrength(), drug.getRoaSuggested(),
						totalPrice);
			}).map(mappedDrug -> {
				boolean isExcluded = !drugList.isEmpty() && drugList.contains(mappedDrug.getSfdaCode());
				boolean isFormulary = !formularyDrugList.isEmpty()
						&& formularyDrugList.contains(mappedDrug.getSfdaCode());
				boolean isApproved = approvedDrugList.contains(mappedDrug.getSfdaCode());
				mappedDrug.setIsApproved(isApproved);
				mappedDrug.setInExclusionList(isExcluded);
				mappedDrug.setDrugFormulary(isFormulary);
				Boolean isApprovalRequired = true;
				if (mappedDrug.getIsApproved().equals(Boolean.TRUE)) {
					isApprovalRequired = false;
				} else if (mappedDrug.getDrugFormulary().equals(Boolean.FALSE)
						|| mappedDrug.getInExclusionList().equals(Boolean.TRUE)) {
					isApprovalRequired = true;
				} else {
					isApprovalRequired = false;
				}
				mappedDrug.setIsApprovalRequired(isApprovalRequired);
				return mappedDrug;
			}).sorted(Comparator.comparing(SuggestedDrug::getIsApproved, Comparator.reverseOrder())
					.thenComparing(SuggestedDrug::getDrugFormulary, Comparator.reverseOrder())
					.thenComparing(SuggestedDrug::getUnitPrice, Comparator.comparing(BigDecimal::new)))
					.collect(Collectors.toList());
			prescriptionDrug.setSuggestedDrugs(suggestedDrugs);
			prescriptionDrugList.add(prescriptionDrug);
		}
	}

	private void managePrescriptionPolicyDetails(SuggestedDrugsModel suggestedDrugsModel, String idNumber,
			String requestId, String benefitCase, String payerId, String providerId,
			String ePrescriptionReferenceNumber, List<String> dispensibleDrugs) throws PrescriptionException {
		DispensibleDrugsRequestModel dispensableDrugsRequestModel = populateDispensableDrugsRequestModel(requestId,
				benefitCase, payerId, providerId, dispensibleDrugs);
		log.info("Get policy details for prescription:- [{}]", ePrescriptionReferenceNumber);
		PolicyResponseModel policyResponseModel = policyConsumptionRestHandler
				.getPayerAndPatientShareForDispensibleDrugs(idNumber, dispensableDrugsRequestModel);
		if (policyResponseModel == null) {
			throw new PrescriptionException(new PrescriptionDispenseResponseModel("Invalid",
					"Couldn't fetch policy details.", ePrescriptionReferenceNumber));
		}
		if (StringUtils.isNotBlank(policyResponseModel.getStatus())
				&& policyResponseModel.getStatus().equals(PolicyConsumptionStatus.APPROVED.getValue())) {
			populatePolicyDetailsForSuggestedDrugs(suggestedDrugsModel, policyResponseModel);
		} else {
			throw new PrescriptionException(new PrescriptionDispenseResponseModel(ePrescriptionReferenceNumber,
					policyResponseModel.getStatus(), policyResponseModel.getStatusDescription()));
		}
	}

	private DispensibleDrugsRequestModel populateDispensableDrugsRequestModel(String requestId, String benefitCase,
			String payerId, String providerId, List<String> dispensibleDrugs) {
		String benefitCode = fetchBenefitCodeService.fetchBenefitCodeByRequestId(requestId);
		return new DispensibleDrugsRequestModel(benefitCode, benefitCase, payerId, requestId, providerId,
				dispensibleDrugs);
	}

	private void populatePolicyDetailsForSuggestedDrugs(SuggestedDrugsModel suggestedDrugsModel,
			PolicyResponseModel policyResponseModel) {
		/*
		 * suggestedDrugsModel.getPrescriptionDrugs().stream() .forEach(prescribedDrug
		 * -> prescribedDrug.getSuggestedDrugs().stream().forEach(suggestedDrug -> {
		 * String benefitCase = policyResponseModel.getDrugList().stream()
		 * .filter(categorizedDrug -> categorizedDrug.getDrugCode()
		 * .equals(suggestedDrug.getSfdaCode()))
		 * .findAny().map(DrugListModel::getBenefitCase).orElse(null);
		 */
		suggestedDrugsModel.getPrescriptionDrugs().stream()
				.forEach(prescribedDrug -> prescribedDrug.getSuggestedDrugs().stream().forEach(suggestedDrug -> {
					policyResponseModel.getDrugList().stream().filter(
							categorizedDrug -> categorizedDrug.getDrugCode().equals(suggestedDrug.getSfdaCode()))
							.findAny().map(drugListModel -> {
								suggestedDrug.setBenefitCase(drugListModel.getBenefitCase());
								suggestedDrug.setPatientShare(drugListModel.getPatientShare().toString());
								suggestedDrug.setPatientShareCurrency(drugListModel.getPatientShareCurrency());
								suggestedDrug.setMaxPatientShareAmount(drugListModel.getMaxPatientShareValue());
								suggestedDrug.setMaxPatientShareCurrency(drugListModel.getMaxPatientShareCurrency());
								return drugListModel;
							}).orElse(null);
				}));
	}

	private Set<String> fetchExcludedDrugs(DrugExclusionResponseModel drugExclusionResponseModel) {
		if (drugExclusionResponseModel != null && (drugExclusionResponseModel.getDrugList() != null
				&& !drugExclusionResponseModel.getDrugList().isEmpty())) {
			return drugExclusionResponseModel.getDrugList().stream()
					.filter(drug -> drug.getStatusCode().equalsIgnoreCase("REJECTED")).map(DrugList::getDrugCode)
					.collect(Collectors.toSet());
		}
		return Collections.emptySet();
	}

	private Set<String> fetchFormularyDrugs(List<DrugFormularyResponseModel> drugFormularyResponseModel) {
		if (null != drugFormularyResponseModel && !drugFormularyResponseModel.isEmpty()) {
			return drugFormularyResponseModel.stream()
					.filter(formularyDrug -> formularyDrug.getStatusCode().equalsIgnoreCase("APPROVED"))
					.map(DrugFormularyResponseModel::getDrugCode).collect(Collectors.toSet());
		}
		return Collections.emptySet();
	}
}
